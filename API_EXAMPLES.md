# API Examples - All Endpoints

Base URL: `http://localhost:8080`

---

## Companies

### Create Company
```bash
POST /companies/creation
```
```json
{
  "name": "Acme Corporation",
  "phone": "1234567890",
  "address": "123 Main Street, Sofia",
  "email": "contact@acme.com"
}
```

### Update Company
```bash
PUT /companies/{id}
```
```json
{
  "name": "Acme Corporation Ltd",
  "phone": "0987654321",
  "address": "456 Business Park, Sofia",
  "email": "info@acme.com"
}
```

### Delete Company
```bash
DELETE /companies/{id}
```

---

## Employees

### Create Employee
```bash
POST /employees/creation
```
```json
{
  "firstName": "Alice",
  "lastName": "Johnson",
  "phone": "1234567890",
  "email": "alice.johnson@company.com",
  "salary": 5000.00,
  "companyId": "COPY_COMPANY_ID"
}
```

### Update Employee
```bash
PUT /employees/{id}
```
```json
{
  "firstName": "Alice",
  "lastName": "Johnson-Smith",
  "phone": "1234567890",
  "email": "alice.smith@company.com",
  "salary": 5500.00
}
```

### Delete Employee
```bash
DELETE /employees/{id}
```

### Get Employees with Buildings by Company
```bash
GET /employees/by-company/{companyId}/buildings
```

---

## Buildings

### Create Building
**Note:** When a building is created, apartments are automatically created based on `totalApartments`. Apartments are distributed evenly across floors, and area is calculated as `(builtArea - commonAreas) / totalApartments`.

```bash
POST /buildings/creation
```
```json
{
  "address": "123 Main Street, Sofia, Bulgaria",
  "floors": 5,
  "totalApartments": 20,
  "builtArea": 2500.50,
  "commonAreas": 500.25,
  "hasElevator": true,
  "pricePerSquareMeter": 1500.00,
  "elevatorFeePerPerson": 25.50,
  "petFeePerPet": 15.00,
  "companyId": "550e8400-e29b-41d4-a716-446655440000"
}
```

### Update Building
```bash
PUT /buildings/{id}
```
```json
{
  "pricePerSquareMeter": 1600.00,
  "elevatorFeePerPerson": 0.00,
  "petFeePerPet": 18.00
}
```

### Delete Building
```bash
DELETE /buildings/{id}
```

---

## Residents

### Create Resident
```bash
POST /residents/creation
```
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "age": 35,
  "phone": "1234567890",
  "email": "john.doe@example.com",
  "usesElevator": true,
  "buildingId": "550e8400-e29b-41d4-a716-446655440000",
  "apartmentId": "660e8400-e29b-41d4-a716-446655440001"
}
```

### Update Resident
```bash
PUT /residents/{id}
```
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "age": 36,
  "phone": "1234567890",
  "email": "john.doe.new@example.com",
  "usesElevator": false
}
```

### Delete Resident
```bash
DELETE /residents/{id}
```

---

## Apartments

**Note:** Apartments are automatically created when a building is created. Use the update endpoint to assign owners and residents to apartments.

### Update Apartment
```bash
PUT /apartments/{id}
```
```json
{
  "ownerId": "880e8400-e29b-41d4-a716-446655440003",
  "residentIds": ["880e8400-e29b-41d4-a716-446655440003"]
}
```

### Delete Apartment
```bash
DELETE /apartments/{id}
```

---

## Fees

**Note:** Fees are calculated automatically based on:
- Base amount: `apartment.area * building.pricePerSquareMeter`
- Elevator fee: `count(residents age > 7 AND usesElevator) * building.elevatorFeePerPerson` (only if building has elevator)
- Pet fee: `count(pets) * building.petFeePerPet`
- Total: `baseAmount + elevatorFee + petFee`

### Create Fee
```bash
POST /fees/creation
```
```json
{
  "apartmentId": "660e8400-e29b-41d4-a716-446655440001",
  "month": "JANUARY",
  "year": 2024
}
```

### Mark Fee as Paid
```bash
PUT /fees/{id}/pay
```
```json
{
  "paidDate": "2024-01-15"
}
```

### Get Fees by Apartment
```bash
GET /fees/apartment/{apartmentId}
```

### Get Fees by Building
```bash
GET /fees/building/{buildingId}
```

### Get Fee by ID
```bash
GET /fees/{id}
```

### Delete Fee
```bash
DELETE /fees/{id}
```

---

## Quick cURL Examples

### Company
```bash
curl -X POST http://localhost:8080/companies/creation \
  -H "Content-Type: application/json" \
  -d '{"name":"Acme Corp","phone":"1234567890","address":"123 Main St","email":"info@acme.com"}'
```

### Employee
```bash
curl -X POST http://localhost:8080/employees/creation \
  -H "Content-Type: application/json" \
  -d '{"firstName":"Alice","lastName":"Johnson","phone":"1234567890","email":"alice@company.com","salary":5000.00,"companyId":"COMPANY_UUID"}'
```

### Building
```bash
curl -X POST http://localhost:8080/buildings/creation \
  -H "Content-Type: application/json" \
  -d '{"address":"123 Main St","floors":5,"totalApartments":20,"builtArea":2500.50,"commonAreas":500.25,"hasElevator":true,"pricePerSquareMeter":1500.00,"elevatorFeePerPerson":25.50,"petFeePerPet":15.00,"companyId":"COMPANY_UUID"}'
```

### Resident
```bash
curl -X POST http://localhost:8080/residents/creation \
  -H "Content-Type: application/json" \
  -d '{"firstName":"John","lastName":"Doe","age":35,"phone":"1234567890","email":"john@example.com","usesElevator":true,"buildingId":"BUILDING_UUID","apartmentId":"APARTMENT_UUID"}'
```

### Get Employees with Buildings
```bash
curl -X GET http://localhost:8080/employees/by-company/COMPANY_UUID/buildings
```

### Update Apartment
```bash
curl -X PUT http://localhost:8080/apartments/APARTMENT_UUID \
  -H "Content-Type: application/json" \
  -d '{"ownerId":"RESIDENT_UUID","residentIds":["RESIDENT_UUID"]}'
```

### Create Fee
```bash
curl -X POST http://localhost:8080/fees/creation \
  -H "Content-Type: application/json" \
  -d '{"apartmentId":"APARTMENT_UUID","month":"JANUARY","year":2024}'
```

### Mark Fee as Paid
```bash
curl -X PUT http://localhost:8080/fees/FEE_UUID/pay \
  -H "Content-Type: application/json" \
  -d '{"paidDate":"2024-01-15"}'
```

### Get Fees by Apartment
```bash
curl -X GET http://localhost:8080/fees/apartment/APARTMENT_UUID
```

### Get Fees by Building
```bash
curl -X GET http://localhost:8080/fees/building/BUILDING_UUID
```

