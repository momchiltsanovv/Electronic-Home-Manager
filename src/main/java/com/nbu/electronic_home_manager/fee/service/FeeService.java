package com.nbu.electronic_home_manager.fee.service;

import com.nbu.electronic_home_manager.apartment.model.Apartment;
import com.nbu.electronic_home_manager.apartment.repository.ApartmentRepository;
import com.nbu.electronic_home_manager.building.model.Building;
import com.nbu.electronic_home_manager.building.repository.BuildingRepository;
import com.nbu.electronic_home_manager.company.model.Company;
import com.nbu.electronic_home_manager.company.repository.CompanyRepository;
import com.nbu.electronic_home_manager.employee.model.Employee;
import com.nbu.electronic_home_manager.employee.repository.EmployeeRepository;
import com.nbu.electronic_home_manager.exception.ApartmentDoesNotExistException;
import com.nbu.electronic_home_manager.exception.BuildingDoesNotExistException;
import com.nbu.electronic_home_manager.exception.CompanyDoesNotExistException;
import com.nbu.electronic_home_manager.exception.EmployeeDoesNotExistException;
import com.nbu.electronic_home_manager.exception.FeeDoesNotExistException;
import com.nbu.electronic_home_manager.fee.dto.CreateFeeRequest;
import com.nbu.electronic_home_manager.fee.dto.FeeResponse;
import com.nbu.electronic_home_manager.fee.dto.MarkFeePaidRequest;
import com.nbu.electronic_home_manager.shared.dto.AmountReport;
import com.nbu.electronic_home_manager.shared.dto.AmountSummaryReport;
import com.nbu.electronic_home_manager.fee.model.Fee;
import com.nbu.electronic_home_manager.fee.repository.FeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FeeService {

    private final FeeRepository feeRepository;
    private final ApartmentRepository apartmentRepository;
    private final CompanyRepository companyRepository;
    private final BuildingRepository buildingRepository;
    private final EmployeeRepository employeeRepository;

    public FeeService(FeeRepository feeRepository, 
                     ApartmentRepository apartmentRepository,
                     CompanyRepository companyRepository,
                     BuildingRepository buildingRepository,
                     EmployeeRepository employeeRepository) {
        this.feeRepository = feeRepository;
        this.apartmentRepository = apartmentRepository;
        this.companyRepository = companyRepository;
        this.buildingRepository = buildingRepository;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public FeeResponse createFee(CreateFeeRequest request) {
        // Validate apartment exists
        Optional<Apartment> optionalApartment = apartmentRepository.findById(request.getApartmentId());
        if (optionalApartment.isEmpty()) {
            throw new ApartmentDoesNotExistException("Apartment with id " + request.getApartmentId() + " does not exist");
        }
        Apartment apartment = optionalApartment.get();

        // Validate apartment has an owner
        if (apartment.getOwner() == null) {
            throw new IllegalStateException("Cannot create fee for apartment " + request.getApartmentId() +
                                                    " because it does not have an owner assigned");
        }

        // Check if fee already exists for this apartment/month/year
        Optional<Fee> existingFee = feeRepository.findByApartmentIdAndMonthAndYear(
                request.getApartmentId(), request.getMonth(), request.getYear());
        if (existingFee.isPresent()) {
            throw new IllegalStateException("Fee already exists for apartment " + request.getApartmentId() +
                                                    " for " + request.getMonth() + " " + request.getYear());
        }

        // Calculate fees
        BigDecimal baseAmount = calculateBaseAmount(apartment);
        BigDecimal elevatorFee = calculateElevatorFee(apartment);
        BigDecimal petFee = calculatePetFee(apartment);
        BigDecimal totalAmount = baseAmount.add(elevatorFee).add(petFee);

        Fee fee = Fee.builder()
                     .apartment(apartment)
                     .month(request.getMonth())
                     .year(request.getYear())
                     .baseAmount(baseAmount)
                     .elevatorFee(elevatorFee)
                     .petFee(petFee)
                     .totalAmount(totalAmount)
                     .isPaid(false)
                     .build();

        feeRepository.save(fee);
        log.info("Fee created for apartment {} for {}/{} with total amount: {}",
                 apartment.getNumber(), request.getMonth(), request.getYear(), totalAmount);

        return mapToFeeResponse(fee);
    }

    @Transactional
    public FeeResponse markFeePaid(UUID feeId, MarkFeePaidRequest request) {
        Optional<Fee> optionalFee = feeRepository.findById(feeId);
        if (optionalFee.isEmpty()) {
            throw new FeeDoesNotExistException("Fee with id " + feeId + " does not exist");
        }

        Fee fee = optionalFee.get();
        fee.setIsPaid(true);
        fee.setPaidDate(request.getPaidDate() != null ? request.getPaidDate() : LocalDate.now());

        feeRepository.save(fee);
        log.info("Fee {} marked as paid", feeId);

        return mapToFeeResponse(fee);
    }

    public List<FeeResponse> getFeesByApartment(UUID apartmentId) {
        // Validate apartment exists
        Optional<Apartment> optionalApartment = apartmentRepository.findById(apartmentId);
        if (optionalApartment.isEmpty()) {
            throw new ApartmentDoesNotExistException("Apartment with id " + apartmentId + " does not exist");
        }

        List<Fee> fees = feeRepository.findByApartmentIdOrderByYearDescMonthDesc(apartmentId);
        return fees.stream()
                   .map(this::mapToFeeResponse)
                   .collect(Collectors.toList());
    }

    public List<FeeResponse> getFeesByBuilding(UUID buildingId) {
        List<Fee> fees = feeRepository.findByApartmentBuildingIdOrderByYearDescMonthDesc(buildingId);
        return fees.stream()
                   .map(this::mapToFeeResponse)
                   .collect(Collectors.toList());
    }

    public FeeResponse getFeeById(UUID feeId) {
        Optional<Fee> optionalFee = feeRepository.findById(feeId);
        if (optionalFee.isEmpty()) {
            throw new FeeDoesNotExistException("Fee with id " + feeId + " does not exist");
        }

        return mapToFeeResponse(optionalFee.get());
    }

    @Transactional
    public void deleteFee(UUID feeId) {
        Optional<Fee> optionalFee = feeRepository.findById(feeId);
        if (optionalFee.isEmpty()) {
            throw new FeeDoesNotExistException("Fee with id " + feeId + " does not exist");
        }

        feeRepository.delete(optionalFee.get());
        log.info("Fee {} deleted", feeId);
    }

    private BigDecimal calculateBaseAmount(Apartment apartment) {
        BigDecimal area = BigDecimal.valueOf(apartment.getArea());
        BigDecimal pricePerSquareMeter = apartment.getBuilding().getPricePerSquareMeter();
        return area.multiply(pricePerSquareMeter);
    }

    private BigDecimal calculateElevatorFee(Apartment apartment) {
        if (!apartment.getBuilding().getHasElevator()) {
            return BigDecimal.ZERO;
        }

        // Count residents over 7 years old who use the elevator
        long elevatorUsersCount = apartment.getResidents()
                                           .stream()
                                           .filter(resident -> resident.getAge() > 7 && Boolean.TRUE.equals(resident.getUsesElevator()))
                                           .count();

        BigDecimal elevatorFeePerPerson = apartment.getBuilding().getElevatorFeePerPerson();
        return elevatorFeePerPerson.multiply(BigDecimal.valueOf(elevatorUsersCount));
    }

    private BigDecimal calculatePetFee(Apartment apartment) {
        long petCount = apartment.getPets() != null ? apartment.getPets().size() : 0;
        BigDecimal petFeePerPet = apartment.getBuilding().getPetFeePerPet();
        return petFeePerPet.multiply(BigDecimal.valueOf(petCount));
    }

    private FeeResponse mapToFeeResponse(Fee fee) {
        return FeeResponse.builder()
                          .id(fee.getId())
                          .apartmentId(fee.getApartment().getId())
                          .apartmentNumber(fee.getApartment().getNumber())
                          .month(fee.getMonth())
                          .year(fee.getYear())
                          .baseAmount(fee.getBaseAmount())
                          .elevatorFee(fee.getElevatorFee())
                          .petFee(fee.getPetFee())
                          .totalAmount(fee.getTotalAmount())
                          .isPaid(fee.getIsPaid())
                          .paidDate(fee.getPaidDate())
                          .build();
    }

    public AmountReport getUnpaidAmountsByCompany(UUID companyId) {
        Optional<Company> optionalCompany = companyRepository.findById(companyId);
        if (optionalCompany.isEmpty()) {
            throw new CompanyDoesNotExistException("Company with id " + companyId + " does not exist");
        }
        
        List<Fee> unpaidFees = feeRepository.findUnpaidFeesByCompanyId(companyId);
        BigDecimal totalAmount = unpaidFees.stream()
                .map(Fee::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        return AmountReport.builder()
                .id(companyId)
                .name(optionalCompany.get().getName())
                .totalAmount(totalAmount)
                .feeCount((long) unpaidFees.size())
                .build();
    }

    public AmountSummaryReport<FeeResponse> getUnpaidAmountsByCompanyWithDetails(UUID companyId) {
        Optional<Company> optionalCompany = companyRepository.findById(companyId);
        if (optionalCompany.isEmpty()) {
            throw new CompanyDoesNotExistException("Company with id " + companyId + " does not exist");
        }
        
        List<Fee> unpaidFees = feeRepository.findUnpaidFeesByCompanyId(companyId);
        BigDecimal totalAmount = unpaidFees.stream()
                .map(Fee::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        List<FeeResponse> feeResponses = unpaidFees.stream()
                .map(this::mapToFeeResponse)
                .collect(Collectors.toList());
        
        return AmountSummaryReport.<FeeResponse>builder()
                .totalAmount(totalAmount)
                .totalCount((long) unpaidFees.size())
                .items(feeResponses)
                .build();
    }

    public AmountReport getPaidAmountsByCompany(UUID companyId) {
        Optional<Company> optionalCompany = companyRepository.findById(companyId);
        if (optionalCompany.isEmpty()) {
            throw new CompanyDoesNotExistException("Company with id " + companyId + " does not exist");
        }
        
        List<Fee> paidFees = feeRepository.findPaidFeesByCompanyId(companyId);
        BigDecimal totalAmount = paidFees.stream()
                .map(Fee::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        return AmountReport.builder()
                .id(companyId)
                .name(optionalCompany.get().getName())
                .totalAmount(totalAmount)
                .feeCount((long) paidFees.size())
                .build();
    }

    public AmountSummaryReport<FeeResponse> getPaidAmountsByCompanyWithDetails(UUID companyId) {
        Optional<Company> optionalCompany = companyRepository.findById(companyId);
        if (optionalCompany.isEmpty()) {
            throw new CompanyDoesNotExistException("Company with id " + companyId + " does not exist");
        }
        
        List<Fee> paidFees = feeRepository.findPaidFeesByCompanyId(companyId);
        BigDecimal totalAmount = paidFees.stream()
                .map(Fee::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        List<FeeResponse> feeResponses = paidFees.stream()
                .map(this::mapToFeeResponse)
                .collect(Collectors.toList());
        
        return AmountSummaryReport.<FeeResponse>builder()
                .totalAmount(totalAmount)
                .totalCount((long) paidFees.size())
                .items(feeResponses)
                .build();
    }

    public AmountReport getUnpaidAmountsByBuilding(UUID buildingId) {
        Optional<Building> optionalBuilding = buildingRepository.findById(buildingId);
        if (optionalBuilding.isEmpty()) {
            throw new BuildingDoesNotExistException("Building with id " + buildingId + " does not exist");
        }
        
        List<Fee> unpaidFees = feeRepository.findUnpaidFeesByBuildingId(buildingId);
        BigDecimal totalAmount = unpaidFees.stream()
                .map(Fee::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        return AmountReport.builder()
                .id(buildingId)
                .name(optionalBuilding.get().getAddress())
                .totalAmount(totalAmount)
                .feeCount((long) unpaidFees.size())
                .build();
    }

    public AmountSummaryReport<FeeResponse> getUnpaidAmountsByBuildingWithDetails(UUID buildingId) {
        Optional<Building> optionalBuilding = buildingRepository.findById(buildingId);
        if (optionalBuilding.isEmpty()) {
            throw new BuildingDoesNotExistException("Building with id " + buildingId + " does not exist");
        }
        
        List<Fee> unpaidFees = feeRepository.findUnpaidFeesByBuildingId(buildingId);
        BigDecimal totalAmount = unpaidFees.stream()
                .map(Fee::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        List<FeeResponse> feeResponses = unpaidFees.stream()
                .map(this::mapToFeeResponse)
                .collect(Collectors.toList());
        
        return AmountSummaryReport.<FeeResponse>builder()
                .totalAmount(totalAmount)
                .totalCount((long) unpaidFees.size())
                .items(feeResponses)
                .build();
    }

    public AmountReport getPaidAmountsByBuilding(UUID buildingId) {
        Optional<Building> optionalBuilding = buildingRepository.findById(buildingId);
        if (optionalBuilding.isEmpty()) {
            throw new BuildingDoesNotExistException("Building with id " + buildingId + " does not exist");
        }
        
        List<Fee> paidFees = feeRepository.findPaidFeesByBuildingId(buildingId);
        BigDecimal totalAmount = paidFees.stream()
                .map(Fee::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        return AmountReport.builder()
                .id(buildingId)
                .name(optionalBuilding.get().getAddress())
                .totalAmount(totalAmount)
                .feeCount((long) paidFees.size())
                .build();
    }

    public AmountSummaryReport<FeeResponse> getPaidAmountsByBuildingWithDetails(UUID buildingId) {
        Optional<Building> optionalBuilding = buildingRepository.findById(buildingId);
        if (optionalBuilding.isEmpty()) {
            throw new BuildingDoesNotExistException("Building with id " + buildingId + " does not exist");
        }
        
        List<Fee> paidFees = feeRepository.findPaidFeesByBuildingId(buildingId);
        BigDecimal totalAmount = paidFees.stream()
                .map(Fee::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        List<FeeResponse> feeResponses = paidFees.stream()
                .map(this::mapToFeeResponse)
                .collect(Collectors.toList());
        
        return AmountSummaryReport.<FeeResponse>builder()
                .totalAmount(totalAmount)
                .totalCount((long) paidFees.size())
                .items(feeResponses)
                .build();
    }

    public AmountReport getUnpaidAmountsByEmployee(UUID employeeId) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if (optionalEmployee.isEmpty()) {
            throw new EmployeeDoesNotExistException("Employee with id " + employeeId + " does not exist");
        }
        
        List<Fee> unpaidFees = feeRepository.findUnpaidFeesByEmployeeId(employeeId);
        BigDecimal totalAmount = unpaidFees.stream()
                .map(Fee::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        Employee employee = optionalEmployee.get();
        String employeeName = employee.getFirstName() + " " + employee.getLastName();
        
        return AmountReport.builder()
                .id(employeeId)
                .name(employeeName)
                .totalAmount(totalAmount)
                .feeCount((long) unpaidFees.size())
                .build();
    }

    public AmountSummaryReport<FeeResponse> getUnpaidAmountsByEmployeeWithDetails(UUID employeeId) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if (optionalEmployee.isEmpty()) {
            throw new EmployeeDoesNotExistException("Employee with id " + employeeId + " does not exist");
        }
        
        List<Fee> unpaidFees = feeRepository.findUnpaidFeesByEmployeeId(employeeId);
        BigDecimal totalAmount = unpaidFees.stream()
                .map(Fee::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        List<FeeResponse> feeResponses = unpaidFees.stream()
                .map(this::mapToFeeResponse)
                .collect(Collectors.toList());
        
        return AmountSummaryReport.<FeeResponse>builder()
                .totalAmount(totalAmount)
                .totalCount((long) unpaidFees.size())
                .items(feeResponses)
                .build();
    }

    public AmountReport getPaidAmountsByEmployee(UUID employeeId) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if (optionalEmployee.isEmpty()) {
            throw new EmployeeDoesNotExistException("Employee with id " + employeeId + " does not exist");
        }
        
        List<Fee> paidFees = feeRepository.findPaidFeesByEmployeeId(employeeId);
        BigDecimal totalAmount = paidFees.stream()
                .map(Fee::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        Employee employee = optionalEmployee.get();
        String employeeName = employee.getFirstName() + " " + employee.getLastName();
        
        return AmountReport.builder()
                .id(employeeId)
                .name(employeeName)
                .totalAmount(totalAmount)
                .feeCount((long) paidFees.size())
                .build();
    }

    public AmountSummaryReport<FeeResponse> getPaidAmountsByEmployeeWithDetails(UUID employeeId) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if (optionalEmployee.isEmpty()) {
            throw new EmployeeDoesNotExistException("Employee with id " + employeeId + " does not exist");
        }
        
        List<Fee> paidFees = feeRepository.findPaidFeesByEmployeeId(employeeId);
        BigDecimal totalAmount = paidFees.stream()
                .map(Fee::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        List<FeeResponse> feeResponses = paidFees.stream()
                .map(this::mapToFeeResponse)
                .collect(Collectors.toList());
        
        return AmountSummaryReport.<FeeResponse>builder()
                .totalAmount(totalAmount)
                .totalCount((long) paidFees.size())
                .items(feeResponses)
                .build();
    }
}

