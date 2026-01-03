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

### Get All Companies (with Sorting)
```bash
GET /companies?sortBy=revenue
GET /companies?sortBy=revenue_asc
GET /companies?sortBy=revenue_desc
```

**Sort options:**
- `revenue` or `revenue_asc` - Sort by total revenue (collected fees) ascending
- `revenue_desc` - Sort by total revenue descending
- No `sortBy` parameter - Returns companies without sorting

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

### Get Employees with Buildings by Company (Summary Report)
```bash
GET /employees/by-company/{companyId}/buildings
```

**Response includes summary with total count and list:**
```json
{
  "totalCount": 3,
  "items": [
    {
      "id": "...",
      "firstName": "Alice",
      "lastName": "Johnson",
      "buildingCount": 5,
      "assignedBuildings": [...]
    }
  ]
}
```

### Get All Employees (with Sorting)
```bash
GET /employees?sortBy=name
GET /employees?sortBy=name_asc
GET /employees?sortBy=name_desc
GET /employees?sortBy=properties
GET /employees?sortBy=properties_asc
GET /employees?sortBy=properties_desc
```

**Sort options:**
- `name` or `name_asc` - Sort by full name (first + last) ascending
- `name_desc` - Sort by full name descending
- `properties` or `properties_asc` - Sort by number of serviced properties ascending
- `properties_desc` - Sort by number of serviced properties descending
- No `sortBy` parameter - Returns employees without sorting

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

### Get All Buildings (with Sorting)
```bash
GET /buildings?sortBy=name
GET /buildings?sortBy=name_asc
GET /buildings?sortBy=name_desc
GET /buildings?sortBy=age
GET /buildings?sortBy=age_asc
GET /buildings?sortBy=age_desc
```

**Sort options:**
- `name` or `name_asc` - Sort by address (name) ascending
- `name_desc` - Sort by address descending
- `age` or `age_asc` - Sort by building age (days since creation) ascending
- `age_desc` - Sort by building age descending
- No `sortBy` parameter - Returns buildings without sorting

---

## Residents

### Create Resident
```bash
POST /residents/creation
```

**Business Logic:**
- **If apartment has NO owner:** The resident automatically becomes the owner (they bought the apartment)
- **If apartment already has an owner:** The resident is added as a tenant (renting)

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

**Example Scenarios:**

1. **First resident in apartment (becomes owner):**
   - Apartment has no owner
   - Create resident → Resident automatically becomes owner

2. **Additional resident in apartment (becomes tenant):**
   - Apartment already has an owner
   - Create resident → Resident is added as tenant (renting)

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

**Note:** All fields are optional. You can update only the fields you want to change.

### Reassign Resident to Different Apartment
To reassign a resident to a different apartment, include both `buildingId` and `apartmentId` in the update request:
```bash
PUT /residents/{id}
```
```json
{
  "buildingId": "770e8400-e29b-41d4-a716-446655440002",
  "apartmentId": "880e8400-e29b-41d4-a716-446655440003"
}
```

**Note:** If you provide `buildingId` or `apartmentId`, you must provide both. The resident will be removed from their current apartment(s) and assigned to the new apartment.

### Delete Resident
```bash
DELETE /residents/{id}
```

### Get All Residents (with Sorting)
```bash
GET /residents?sortBy=name
GET /residents?sortBy=name_asc
GET /residents?sortBy=name_desc
GET /residents?sortBy=age
GET /residents?sortBy=age_asc
GET /residents?sortBy=age_desc
```

**Sort options:**
- `name` or `name_asc` - Sort by full name (first + last) ascending
- `name_desc` - Sort by full name descending
- `age` or `age_asc` - Sort by age ascending
- `age_desc` - Sort by age descending
- No `sortBy` parameter - Returns residents without sorting

### Get Residents in a Building (with Sorting)
```bash
GET /residents/building/{buildingId}?sortBy=name
GET /residents/building/{buildingId}?sortBy=name_asc
GET /residents/building/{buildingId}?sortBy=name_desc
GET /residents/building/{buildingId}?sortBy=age
GET /residents/building/{buildingId}?sortBy=age_asc
GET /residents/building/{buildingId}?sortBy=age_desc
```

**Sort options:**
- `name` or `name_asc` - Sort by full name (first + last) ascending
- `name_desc` - Sort by full name descending
- `age` or `age_asc` - Sort by age ascending
- `age_desc` - Sort by age descending
- No `sortBy` parameter - Returns residents without sorting

**Note:** Returns all unique residents living in any apartment within the specified building. Each resident response includes a list of apartment numbers where they live in that building.

**Response includes summary with total count and list:**
```json
{
  "totalCount": 15,
  "items": [
    {
      "id": "880e8400-e29b-41d4-a716-446655440003",
      "firstName": "John",
      "lastName": "Doe",
      "age": 35,
      "phone": "1234567890",
      "email": "john.doe@example.com",
      "usesElevator": true,
      "apartmentNumbers": [5, 12]
    }
  ]
}
```

---

## Apartments

**Note:** Apartments are automatically created when a building is created. Use the update endpoint to assign owners and residents to apartments.

### Update Apartment
```bash
PUT /apartments/{id}
```

**Business Logic:**
- **If apartment has NO owner:** When you add residents, the first resident automatically becomes the owner (they bought the apartment)
- **If apartment already has an owner:** When you add residents, they are added as tenants (renting)

**Example 1: Add residents to apartment without owner (first resident becomes owner)**
```json
{
  "residentIds": ["880e8400-e29b-41d4-a716-446655440003", "990e8400-e29b-41d4-a716-446655440004"]
}
```
**Result:** The first resident (ID: 880e8400...) automatically becomes the owner, and both residents are added.

**Example 2: Explicitly set owner, then add tenants**
```json
{
  "ownerId": "880e8400-e29b-41d4-a716-446655440003",
  "residentIds": ["880e8400-e29b-41d4-a716-446655440003", "990e8400-e29b-41d4-a716-446655440004"]
}
```
**Result:** The specified owner is set, and all residents (including the owner) are added as residents. Additional residents are tenants.

**Note:** Both `ownerId` and `residentIds` are optional. You can update just the owner, just the residents, or both.

### Delete Apartment
```bash
DELETE /apartments/{id}
```

### Get Apartments in a Building (Summary Report)
```bash
GET /apartments/building/{buildingId}
```

**Response includes summary with total count and list:**
```json
{
  "totalCount": 20,
  "items": [
    {
      "id": "...",
      "number": 1,
      "floor": 1,
      "area": 100.0,
      "ownerId": "...",
      "ownerName": "John Doe",
      "residentIds": ["..."],
      "residentCount": 2
    }
  ]
}
```

---

## Fees

**Note:** Fees are calculated automatically based on:
- Base amount: `apartment.area * building.pricePerSquareMeter`
- Elevator fee: `count(residents age > 7 AND usesElevator) * building.elevatorFeePerPerson` (only if building has elevator)
- Pet fee: `count(pets) * building.petFeePerPet`
- Total: `baseAmount + elevatorFee + petFee`

**Important:** An apartment must have an owner assigned before a fee can be created. If you attempt to create a fee for an apartment without an owner, you will receive an error.

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

### Pay Fee (Mark as Paid)
```bash
PUT /fees/{id}/pay
```

**Request body is optional.** If provided, you can specify the payment date:
```json
{
  "paidDate": "2024-01-15"
}
```

If no body is provided, the current date will be used as the payment date.

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

## Reports

### Amounts to be Paid (Unpaid Fees)

#### By Company
```bash
GET /fees/reports/unpaid/company/{companyId}
```

**Response:**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "Acme Corporation",
  "totalAmount": 15000.50,
  "feeCount": 45
}
```

#### By Building
```bash
GET /fees/reports/unpaid/building/{buildingId}
```

**Response:**
```json
{
  "id": "660e8400-e29b-41d4-a716-446655440001",
  "name": "123 Main Street, Sofia",
  "totalAmount": 3500.25,
  "feeCount": 12
}
```

#### By Employee
```bash
GET /fees/reports/unpaid/employee/{employeeId}
```

**Response:**
```json
{
  "id": "770e8400-e29b-41d4-a716-446655440002",
  "name": "Alice Johnson",
  "totalAmount": 8500.75,
  "feeCount": 28
}
```

### Amounts Paid

#### By Company
```bash
GET /fees/reports/paid/company/{companyId}
```

**Response:**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "Acme Corporation",
  "totalAmount": 45000.00,
  "feeCount": 135
}
```

#### By Building
```bash
GET /fees/reports/paid/building/{buildingId}
```

**Response:**
```json
{
  "id": "660e8400-e29b-41d4-a716-446655440001",
  "name": "123 Main Street, Sofia",
  "totalAmount": 10500.00,
  "feeCount": 35
}
```

#### By Employee
```bash
GET /fees/reports/paid/employee/{employeeId}
```

**Response:**
```json
{
  "id": "770e8400-e29b-41d4-a716-446655440002",
  "name": "Alice Johnson",
  "totalAmount": 25000.00,
  "feeCount": 82
}
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

### Get All Residents (Sorted by Name)
```bash
curl -X GET "http://localhost:8080/residents?sortBy=name_asc"
```

### Get All Residents (Sorted by Age)
```bash
curl -X GET "http://localhost:8080/residents?sortBy=age_desc"
```

### Get Residents in a Building (Sorted by Name)
```bash
curl -X GET "http://localhost:8080/residents/building/BUILDING_UUID?sortBy=name_asc"
```

### Get Residents in a Building (Sorted by Age)
```bash
curl -X GET "http://localhost:8080/residents/building/BUILDING_UUID?sortBy=age_desc"
```

### Get All Companies (Sorted by Revenue)
```bash
curl -X GET "http://localhost:8080/companies?sortBy=revenue_desc"
```

### Get All Employees (Sorted by Name)
```bash
curl -X GET "http://localhost:8080/employees?sortBy=name_asc"
```

### Get All Employees (Sorted by Number of Properties)
```bash
curl -X GET "http://localhost:8080/employees?sortBy=properties_desc"
```

### Get All Buildings (Sorted by Address)
```bash
curl -X GET "http://localhost:8080/buildings?sortBy=name_asc"
```

### Get All Buildings (Sorted by Age)
```bash
curl -X GET "http://localhost:8080/buildings?sortBy=age_desc"
```

### Get Apartments in Building (Summary Report)
```bash
curl -X GET http://localhost:8080/apartments/building/BUILDING_UUID
```

### Get Residents in Building (Summary Report)
```bash
curl -X GET "http://localhost:8080/residents/building/BUILDING_UUID?sortBy=name_asc"
```

### Get Employees with Buildings by Company (Summary Report)
```bash
curl -X GET http://localhost:8080/employees/by-company/COMPANY_UUID/buildings
```

### Get Unpaid Amounts by Company
```bash
curl -X GET http://localhost:8080/fees/reports/unpaid/company/COMPANY_UUID
```

### Get Paid Amounts by Company
```bash
curl -X GET http://localhost:8080/fees/reports/paid/company/COMPANY_UUID
```

### Get Unpaid Amounts by Building
```bash
curl -X GET http://localhost:8080/fees/reports/unpaid/building/BUILDING_UUID
```

### Get Paid Amounts by Building
```bash
curl -X GET http://localhost:8080/fees/reports/paid/building/BUILDING_UUID
```

### Get Unpaid Amounts by Employee
```bash
curl -X GET http://localhost:8080/fees/reports/unpaid/employee/EMPLOYEE_UUID
```

### Get Paid Amounts by Employee
```bash
curl -X GET http://localhost:8080/fees/reports/paid/employee/EMPLOYEE_UUID
```

