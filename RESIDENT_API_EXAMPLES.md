# Resident API - JSON Test Examples

## Base URL
Assuming your application runs on `http://localhost:8080`

**Note:** The same `Resident` entity is used for both apartment owners and residents. A person can be an owner of some apartments and a resident of others.

---

## 1. Create Resident
**Endpoint:** `POST /residents/creation`  
**Content-Type:** `application/json`

### Example JSON Request Body:
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "age": 35,
  "phone": "1234567890",
  "email": "john.doe@example.com",
  "usesElevator": true
}
```

### Another Example (Resident who doesn't use elevator):
```json
{
  "firstName": "Jane",
  "lastName": "Smith",
  "age": 28,
  "phone": "0987654321",
  "email": "jane.smith@example.com",
  "usesElevator": false
}
```

### Example (Child resident under 7 years):
```json
{
  "firstName": "Emma",
  "lastName": "Doe",
  "age": 5,
  "phone": "1122334455",
  "email": "emma.doe@example.com",
  "usesElevator": false
}
```

### cURL Example:
```bash
curl -X POST http://localhost:8080/residents/creation \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "age": 35,
    "phone": "1234567890",
    "email": "john.doe@example.com",
    "usesElevator": true
  }'
```

### Validation Rules:
- `firstName`: Required, minimum 2 characters
- `lastName`: Required, minimum 2 characters
- `age`: Required, must be a positive integer
- `phone`: Required, exactly 10 characters
- `email`: Required, valid email format
- `usesElevator`: Required, boolean value (used for elevator fee calculation for residents over 7 years)

---

## 2. Update Resident
**Endpoint:** `PUT /residents/{id}`  
**Content-Type:** `application/json`

Replace `{id}` with the actual UUID of the resident (e.g., `550e8400-e29b-41d4-a716-446655440000`)

### Example JSON Request Body (Update all fields):
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

### Example JSON Request Body (Partial update - only name):
```json
{
  "firstName": "Jonathan"
}
```

### Example JSON Request Body (Partial update - only email):
```json
{
  "email": "newemail@example.com"
}
```

### Example JSON Request Body (Partial update - only age and elevator usage):
```json
{
  "age": 37,
  "usesElevator": true
}
```

### Example JSON Request Body (Update multiple fields):
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "updated.email@example.com"
}
```

### cURL Example (Full Update):
```bash
curl -X PUT http://localhost:8080/residents/550e8400-e29b-41d4-a716-446655440000 \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "age": 36,
    "phone": "1234567890",
    "email": "john.doe.new@example.com",
    "usesElevator": false
  }'
```

### cURL Example (Partial Update):
```bash
curl -X PUT http://localhost:8080/residents/550e8400-e29b-41d4-a716-446655440000 \
  -H "Content-Type: application/json" \
  -d '{
    "email": "newemail@example.com"
  }'
```

### Validation Rules:
- All fields are **optional** for partial updates
- If provided:
  - `firstName`: Minimum 2 characters
  - `lastName`: Minimum 2 characters
  - `age`: Must be a positive integer
  - `phone`: Exactly 10 characters
  - `email`: Valid email format
  - `usesElevator`: Boolean value

**Updatable Fields:**
- `firstName`
- `lastName`
- `age`
- `phone`
- `email`
- `usesElevator`

---

## 3. Delete Resident
**Endpoint:** `DELETE /residents/{id}`  
**No JSON body required** - UUID is passed as path parameter

Replace `{id}` with the actual UUID of the resident

### cURL Example:
```bash
curl -X DELETE http://localhost:8080/residents/550e8400-e29b-41d4-a716-446655440000
```

**Note:** Be careful when deleting residents who are owners of apartments or residents in apartments. Make sure to handle these relationships appropriately in your business logic.

---

## Expected Responses

### Create Resident (POST)
- **Success:** HTTP 201 Created (no response body)
- **Validation Error:** HTTP 400 Bad Request (IllegalArgumentException)

### Update Resident (PUT)
- **Success:** HTTP 200 OK (no response body)
- **Validation Error:** HTTP 400 Bad Request (IllegalArgumentException)
- **Not Found:** HTTP 500 Internal Server Error (ResidentDoesNotExistException)

### Delete Resident (DELETE)
- **Success:** HTTP 200 OK (no response body)
- **Not Found:** HTTP 500 Internal Server Error (ResidentDoesNotExistException)

---

## Quick Test Sequence

1. **Create a resident:**
```bash
curl -X POST http://localhost:8080/residents/creation \
  -H "Content-Type: application/json" \
  -d '{"firstName":"John","lastName":"Doe","age":35,"phone":"1234567890","email":"john.doe@example.com","usesElevator":true}'
```

2. **Note the UUID from the database or logs, then update it:**
```bash
curl -X PUT http://localhost:8080/residents/YOUR-UUID-HERE \
  -H "Content-Type: application/json" \
  -d '{"email":"updated.email@example.com"}'
```

3. **Delete the resident:**
```bash
curl -X DELETE http://localhost:8080/residents/YOUR-UUID-HERE
```

---

## Important Notes

### Resident as Owner vs Resident
- The same `Resident` entity represents both apartment owners and residents
- A person can be:
  - An **owner** of one or more apartments (via `ownedApartments` relationship)
  - A **resident** living in one or more apartments (via `residenceApartments` relationship)
  - Both owner and resident (e.g., owns apartment A but lives in apartment B)

### Elevator Fee Calculation
- The `usesElevator` field is used for fee calculation
- Only residents **over 7 years old** who use the elevator are charged the elevator fee
- This field can be updated if a resident's elevator usage changes

### Phone and Email Uniqueness
- `phone` must be unique across all residents
- `email` does not have a unique constraint in the model, but should be unique in practice

