# Building API - JSON Test Examples

## Base URL
Assuming your application runs on `http://localhost:8080`

---

## 1. Create Building
**Endpoint:** `POST /buildings/creation`  
**Content-Type:** `application/json`

### Example JSON Request Body:
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

### Another Example (Building without elevator):
```json
{
  "address": "456 Park Avenue, Plovdiv, Bulgaria",
  "floors": 3,
  "totalApartments": 12,
  "builtArea": 1800.00,
  "commonAreas": 300.00,
  "hasElevator": false,
  "pricePerSquareMeter": 1200.00,
  "elevatorFeePerPerson": 0.00,
  "petFeePerPet": 12.00,
  "companyId": "550e8400-e29b-41d4-a716-446655440000"
}
```

### cURL Example:
```bash
curl -X POST http://localhost:8080/buildings/creation \
  -H "Content-Type: application/json" \
  -d '{
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
  }'
```

### Validation Rules:
- `address`: Required, minimum 5 characters
- `floors`: Required, must be a positive integer
- `totalApartments`: Required, must be a positive integer
- `builtArea`: Required, must be a positive number
- `commonAreas`: Required, must be a positive number
- `hasElevator`: Required, boolean value
- `pricePerSquareMeter`: Required, must be a positive number
- `elevatorFeePerPerson`: Required, must be zero or positive (0.00 for buildings without elevator)
- `petFeePerPet`: Required, must be a positive number
- `companyId`: Required, must be a valid UUID of an existing company

**Important:** When a building is created, it is **automatically assigned** to the employee with the **least number of buildings** from the specified company. If the company has no employees, the creation will fail.

**Note:** The following fields cannot be updated after creation (they are immutable):
- address
- floors
- totalApartments
- builtArea
- commonAreas
- hasElevator

---

## 2. Update Building
**Endpoint:** `PUT /buildings/{id}`  
**Content-Type:** `application/json`

Replace `{id}` with the actual UUID of the building (e.g., `550e8400-e29b-41d4-a716-446655440000`)

### Example JSON Request Body (Update all updatable fields):
```json
{
  "pricePerSquareMeter": 1600.00,
  "elevatorFeePerPerson": 0.00,
  "petFeePerPet": 18.00
}
```

### Example JSON Request Body (Partial update - only price):
```json
{
  "pricePerSquareMeter": 1700.00
}
```

### Example JSON Request Body (Update multiple fields):
```json
{
  "pricePerSquareMeter": 1650.00,
  "petFeePerPet": 20.00
}
```

### cURL Example (Full Update):
```bash
curl -X PUT http://localhost:8080/buildings/550e8400-e29b-41d4-a716-446655440000 \
  -H "Content-Type: application/json" \
  -d '{
    "pricePerSquareMeter": 1600.00,
    "elevatorFeePerPerson": 0.00,
    "petFeePerPet": 18.00
  }'
```

### cURL Example (Partial Update):
```bash
curl -X PUT http://localhost:8080/buildings/550e8400-e29b-41d4-a716-446655440000 \
  -H "Content-Type: application/json" \
  -d '{
    "pricePerSquareMeter": 1700.00
  }'
```

### Validation Rules:
- All fields are **optional** for partial updates
- If provided:
  - `pricePerSquareMeter`: Must be a positive number
  - `elevatorFeePerPerson`: Must be zero or positive (0.00 or greater)
  - `petFeePerPet`: Must be a positive number

**Updatable Fields Only:**
- `pricePerSquareMeter`
- `elevatorFeePerPerson`
- `petFeePerPet`

**Cannot be updated (immutable fields):**
- `address`
- `floors`
- `totalApartments`
- `builtArea`
- `commonAreas`
- `hasElevator`

---

## 3. Delete Building
**Endpoint:** `DELETE /buildings/{id}`  
**No JSON body required** - UUID is passed as path parameter

Replace `{id}` with the actual UUID of the building

### cURL Example:
```bash
curl -X DELETE http://localhost:8080/buildings/550e8400-e29b-41d4-a716-446655440000
```

---

## Expected Responses

### Create Building (POST)
- **Success:** HTTP 201 Created (no response body)
- **Validation Error:** HTTP 400 Bad Request (IllegalArgumentException)

### Update Building (PUT)
- **Success:** HTTP 200 OK (no response body)
- **Validation Error:** HTTP 400 Bad Request (IllegalArgumentException)
- **Not Found:** HTTP 500 Internal Server Error (BuildingDoesNotExistException)

### Delete Building (DELETE)
- **Success:** HTTP 200 OK (no response body)
- **Not Found:** HTTP 500 Internal Server Error (BuildingDoesNotExistException)

---

## Quick Test Sequence

1. **First, create a company (if you don't have one):**
```bash
curl -X POST http://localhost:8080/companies/creation \
  -H "Content-Type: application/json" \
  -d '{"name":"Test Company","phone":"1234567890","address":"123 Test St","email":"test@company.com"}'
```

2. **Create an employee for the company:**
```bash
curl -X POST http://localhost:8080/employees/creation \
  -H "Content-Type: application/json" \
  -d '{"firstName":"John","lastName":"Doe","phone":"1234567890","email":"john@company.com","salary":5000.00,"companyId":"YOUR-COMPANY-UUID-HERE"}'
```

3. **Create a building (will be automatically assigned to employee with least buildings):**
```bash
curl -X POST http://localhost:8080/buildings/creation \
  -H "Content-Type: application/json" \
  -d '{"address":"123 Test Street, Sofia","floors":4,"totalApartments":16,"builtArea":2000.00,"commonAreas":400.00,"hasElevator":true,"pricePerSquareMeter":1400.00,"elevatorFeePerPerson":20.00,"petFeePerPet":10.00,"companyId":"YOUR-COMPANY-UUID-HERE"}'
```

4. **Note the UUID from the database or logs, then update it:**
```bash
curl -X PUT http://localhost:8080/buildings/YOUR-UUID-HERE \
  -H "Content-Type: application/json" \
  -d '{"pricePerSquareMeter":1500.00}'
```

5. **Delete the building:**
```bash
curl -X DELETE http://localhost:8080/buildings/YOUR-UUID-HERE
```

