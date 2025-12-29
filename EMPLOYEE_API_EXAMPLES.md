# Employee API - JSON Test Examples

## Base URL
Assuming your application runs on `http://localhost:8080`

**Note:** Each employee must belong to a company. When creating an employee, you must provide a valid `companyId`.

---

## 1. Create Employee
**Endpoint:** `POST /employees/creation`  
**Content-Type:** `application/json`

### Example JSON Request Body:
```json
{
  "firstName": "Alice",
  "lastName": "Johnson",
  "phone": "1234567890",
  "email": "alice.johnson@company.com",
  "salary": 5000.00,
  "companyId": "550e8400-e29b-41d4-a716-446655440000"
}
```

### Another Example:
```json
{
  "firstName": "Bob",
  "lastName": "Williams",
  "phone": "0987654321",
  "email": "bob.williams@company.com",
  "salary": 4500.00,
  "companyId": "550e8400-e29b-41d4-a716-446655440000"
}
```

### cURL Example:
```bash
curl -X POST http://localhost:8080/employees/creation \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Alice",
    "lastName": "Johnson",
    "phone": "1234567890",
    "email": "alice.johnson@company.com",
    "salary": 5000.00,
    "companyId": "550e8400-e29b-41d4-a716-446655440000"
  }'
```

### Validation Rules:
- `firstName`: Required, minimum 2 characters
- `lastName`: Required, minimum 2 characters
- `phone`: Required, exactly 10 characters, must be unique
- `email`: Required, valid email format, must be unique
- `salary`: Required, must be a positive number
- `companyId`: Required, must be a valid UUID of an existing company

**Note:** The `hiredDate` field is automatically set by the system when the employee is created (using `@CreationTimestamp`).

---

## 2. Update Employee
**Endpoint:** `PUT /employees/{id}`  
**Content-Type:** `application/json`

Replace `{id}` with the actual UUID of the employee (e.g., `550e8400-e29b-41d4-a716-446655440000`)

### Example JSON Request Body (Update all updatable fields):
```json
{
  "firstName": "Alice",
  "lastName": "Johnson-Smith",
  "phone": "1234567890",
  "email": "alice.johnson.smith@company.com",
  "salary": 5500.00
}
```

### Example JSON Request Body (Partial update - only salary):
```json
{
  "salary": 5500.00
}
```

### Example JSON Request Body (Partial update - only email):
```json
{
  "email": "newemail@company.com"
}
```

### Example JSON Request Body (Partial update - name change):
```json
{
  "firstName": "Alicia",
  "lastName": "Johnson"
}
```

### Example JSON Request Body (Update multiple fields):
```json
{
  "email": "updated.email@company.com",
  "salary": 5200.00
}
```

### cURL Example (Full Update):
```bash
curl -X PUT http://localhost:8080/employees/550e8400-e29b-41d4-a716-446655440000 \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Alice",
    "lastName": "Johnson-Smith",
    "phone": "1234567890",
    "email": "alice.johnson.smith@company.com",
    "salary": 5500.00
  }'
```

### cURL Example (Partial Update):
```bash
curl -X PUT http://localhost:8080/employees/550e8400-e29b-41d4-a716-446655440000 \
  -H "Content-Type: application/json" \
  -d '{
    "salary": 5500.00
  }'
```

### Validation Rules:
- All fields are **optional** for partial updates
- If provided:
  - `firstName`: Minimum 2 characters
  - `lastName`: Minimum 2 characters
  - `phone`: Exactly 10 characters, must be unique
  - `email`: Valid email format, must be unique
  - `salary`: Must be a positive number

**Updatable Fields:**
- `firstName`
- `lastName`
- `phone`
- `email`
- `salary`

**Cannot be updated (immutable fields):**
- `companyId` (employee cannot change company via this endpoint)
- `hiredDate` (automatically set, cannot be changed)

---

## 3. Delete Employee
**Endpoint:** `DELETE /employees/{id}`  
**No JSON body required** - UUID is passed as path parameter

Replace `{id}` with the actual UUID of the employee

### cURL Example:
```bash
curl -X DELETE http://localhost:8080/employees/550e8400-e29b-41d4-a716-446655440000
```

**Important Note:** When deleting an employee who has assigned buildings, the system **automatically redistributes** all their buildings to the remaining employees from the same company. Buildings are assigned to employees with the **least number of buildings** to maintain balance. If the employee is the only employee in the company and has buildings, the deletion will fail with an error.

---

## Expected Responses

### Create Employee (POST)
- **Success:** HTTP 201 Created (no response body)
- **Validation Error:** HTTP 400 Bad Request (IllegalArgumentException)
- **Company Not Found:** HTTP 500 Internal Server Error (CompanyDoesNotExistException)

### Update Employee (PUT)
- **Success:** HTTP 200 OK (no response body)
- **Validation Error:** HTTP 400 Bad Request (IllegalArgumentException)
- **Not Found:** HTTP 500 Internal Server Error (EmployeeDoesNotExistException)

### Delete Employee (DELETE)
- **Success:** HTTP 200 OK (no response body)
- **Not Found:** HTTP 500 Internal Server Error (EmployeeDoesNotExistException)
- **Cannot Delete:** HTTP 500 Internal Server Error (IllegalStateException) - if employee is the only employee in company and has buildings assigned

---

## Quick Test Sequence

1. **First, create a company (if you don't have one):**
```bash
curl -X POST http://localhost:8080/companies/creation \
  -H "Content-Type: application/json" \
  -d '{"name":"Test Company","phone":"1234567890","address":"123 Test St","email":"test@company.com"}'
```

2. **Note the company UUID, then create an employee:**
```bash
curl -X POST http://localhost:8080/employees/creation \
  -H "Content-Type: application/json" \
  -d '{"firstName":"Alice","lastName":"Johnson","phone":"1234567890","email":"alice@company.com","salary":5000.00,"companyId":"YOUR-COMPANY-UUID-HERE"}'
```

3. **Note the employee UUID, then update it:**
```bash
curl -X PUT http://localhost:8080/employees/YOUR-EMPLOYEE-UUID-HERE \
  -H "Content-Type: application/json" \
  -d '{"salary":5500.00}'
```

4. **Delete the employee:**
```bash
curl -X DELETE http://localhost:8080/employees/YOUR-EMPLOYEE-UUID-HERE
```

---

## 4. Get Employees with Buildings by Company
**Endpoint:** `GET /employees/by-company/{companyId}/buildings`  
**Content-Type:** `application/json` (response)

Replace `{companyId}` with the actual UUID of the company

This endpoint returns all employees of a company along with their assigned buildings.

### Example Response:
```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "firstName": "Alice",
    "lastName": "Johnson",
    "phone": "1234567890",
    "email": "alice.johnson@company.com",
    "salary": 5000.00,
    "hiredDate": "2024-01-15",
    "buildingCount": 2,
    "assignedBuildings": [
      {
        "id": "660e8400-e29b-41d4-a716-446655440001",
        "address": "123 Main Street, Sofia, Bulgaria",
        "floors": 5,
        "totalApartments": 20,
        "builtArea": 2500.50,
        "commonAreas": 500.25,
        "hasElevator": true,
        "pricePerSquareMeter": 1500.00,
        "elevatorFeePerPerson": 25.50,
        "petFeePerPet": 15.00,
        "createdDate": "2024-01-20",
        "updatedDate": "2024-01-20"
      },
      {
        "id": "770e8400-e29b-41d4-a716-446655440002",
        "address": "456 Park Avenue, Plovdiv, Bulgaria",
        "floors": 3,
        "totalApartments": 12,
        "builtArea": 1800.00,
        "commonAreas": 300.00,
        "hasElevator": false,
        "pricePerSquareMeter": 1200.00,
        "elevatorFeePerPerson": 0.00,
        "petFeePerPet": 12.00,
        "createdDate": "2024-02-01",
        "updatedDate": "2024-02-01"
      }
    ]
  },
  {
    "id": "880e8400-e29b-41d4-a716-446655440003",
    "firstName": "Bob",
    "lastName": "Williams",
    "phone": "0987654321",
    "email": "bob.williams@company.com",
    "salary": 4500.00,
    "hiredDate": "2024-02-10",
    "buildingCount": 1,
    "assignedBuildings": [
      {
        "id": "990e8400-e29b-41d4-a716-446655440004",
        "address": "789 Business Park, Varna, Bulgaria",
        "floors": 4,
        "totalApartments": 16,
        "builtArea": 2000.00,
        "commonAreas": 400.00,
        "hasElevator": true,
        "pricePerSquareMeter": 1400.00,
        "elevatorFeePerPerson": 20.00,
        "petFeePerPet": 10.00,
        "createdDate": "2024-02-15",
        "updatedDate": "2024-02-15"
      }
    ]
  }
]
```

### Example Response (Employee with no buildings):
```json
[
  {
    "id": "aa0e8400-e29b-41d4-a716-446655440005",
    "firstName": "Charlie",
    "lastName": "Brown",
    "phone": "1122334455",
    "email": "charlie.brown@company.com",
    "salary": 4800.00,
    "hiredDate": "2024-03-01",
    "buildingCount": 0,
    "assignedBuildings": []
  }
]
```

### cURL Example:
```bash
curl -X GET http://localhost:8080/employees/by-company/550e8400-e29b-41d4-a716-446655440000/buildings
```

### Expected Responses:
- **Success:** HTTP 200 OK with JSON array of employees and their buildings
- **Company Not Found:** HTTP 500 Internal Server Error (CompanyDoesNotExistException)
- **Empty List:** HTTP 200 OK with empty array `[]` if company has no employees

### Response Fields:
- Each employee object contains:
  - `id`: Employee UUID
  - `firstName`: Employee first name
  - `lastName`: Employee last name
  - `phone`: Employee phone number
  - `email`: Employee email
  - `salary`: Employee salary
  - `hiredDate`: Date when employee was hired
  - `buildingCount`: Number of buildings assigned to this employee
  - `assignedBuildings`: Array of building objects assigned to this employee

- Each building object contains:
  - `id`: Building UUID
  - `address`: Building address
  - `floors`: Number of floors
  - `totalApartments`: Total number of apartments
  - `builtArea`: Built area in square meters
  - `commonAreas`: Common areas in square meters
  - `hasElevator`: Whether building has elevator
  - `pricePerSquareMeter`: Price per square meter
  - `elevatorFeePerPerson`: Elevator fee per person
  - `petFeePerPet`: Pet fee per pet
  - `createdDate`: Date when building was created
  - `updatedDate`: Date when building was last updated

---

## Important Notes

### Employee-Company Relationship
- Each employee **must** belong to a company
- The company relationship is set during creation and cannot be changed via the update endpoint
- If you need to transfer an employee to a different company, you would need to delete and recreate them (or implement a separate transfer endpoint)

### Employee-Building Relationship
- Each building is managed by exactly one employee
- When an employee is deleted, their assigned buildings need to be reassigned (this should be handled by business logic)
- The employee assignment to buildings is managed separately (not through this CRUD API)

### Phone and Email Uniqueness
- Both `phone` and `email` must be unique across all employees
- If you try to create or update an employee with a duplicate phone or email, you'll get a database constraint violation

### Salary
- Salary is stored as `BigDecimal` for precise decimal handling
- Must be a positive number
- Can be updated when an employee gets a raise

