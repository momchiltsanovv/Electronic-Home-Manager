# Company API - JSON Test Examples

## Base URL
Assuming your application runs on `http://localhost:8080`

---

## 1. Create Company
**Endpoint:** `POST /companies/creation`  
**Content-Type:** `application/json`

### Example JSON Request Body:
```json
{
  "name": "Acme Corporation",
  "phone": "1234567890",
  "address": "123 Main Street, Sofia",
  "email": "contact@acme.com"
}
```

### cURL Example:
```bash
curl -X POST http://localhost:8080/companies/creation \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Acme Corporation",
    "phone": "1234567890",
    "address": "123 Main Street, Sofia",
    "email": "contact@acme.com"
  }'
```

### Validation Rules:
- `name`: Required, minimum 3 characters
- `phone`: Required, exactly 10 characters
- `address`: Required, minimum 5 characters
- `email`: Required, valid email format

---

## 2. Update Company
**Endpoint:** `PUT /companies/{id}`  
**Content-Type:** `application/json`

Replace `{id}` with the actual UUID of the company (e.g., `550e8400-e29b-41d4-a716-446655440000`)

### Example JSON Request Body (Update all fields):
```json
{
  "name": "Acme Corporation Ltd",
  "phone": "0987654321",
  "address": "456 Business Park, Sofia",
  "email": "info@acme.com"
}
```

### Example JSON Request Body (Partial update - only name):
```json
{
  "name": "Acme Corporation Ltd"
}
```

### Example JSON Request Body (Partial update - only email):
```json
{
  "email": "newemail@acme.com"
}
```

### Example JSON Request Body (Update multiple fields):
```json
{
  "name": "Acme Corporation Ltd",
  "address": "456 Business Park, Sofia"
}
```

### cURL Example (Full Update):
```bash
curl -X PUT http://localhost:8080/companies/550e8400-e29b-41d4-a716-446655440000 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Acme Corporation Ltd",
    "phone": "0987654321",
    "address": "456 Business Park, Sofia",
    "email": "info@acme.com"
  }'
```

### cURL Example (Partial Update):
```bash
curl -X PUT http://localhost:8080/companies/550e8400-e29b-41d4-a716-446655440000 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Acme Corporation Ltd"
  }'
```

### Validation Rules:
- All fields are **optional** for partial updates
- If provided:
  - `name`: Minimum 3 characters
  - `phone`: Exactly 10 characters
  - `address`: Minimum 5 characters
  - `email`: Valid email format

---

## 3. Delete Company
**Endpoint:** `DELETE /companies/{id}`  
**No JSON body required** - UUID is passed as path parameter

Replace `{id}` with the actual UUID of the company

### cURL Example:
```bash
curl -X DELETE http://localhost:8080/companies/550e8400-e29b-41d4-a716-446655440000
```

---

## Expected Responses

### Create Company (POST)
- **Success:** HTTP 201 Created (no response body)
- **Validation Error:** HTTP 400 Bad Request (IllegalArgumentException)

### Update Company (PUT)
- **Success:** HTTP 200 OK (no response body)
- **Validation Error:** HTTP 400 Bad Request (IllegalArgumentException)
- **Not Found:** HTTP 500 Internal Server Error (CompanyDoesNotExistException)

### Delete Company (DELETE)
- **Success:** HTTP 200 OK (no response body)
- **Not Found:** HTTP 500 Internal Server Error (CompanyDoesNotExistException)

---

## Quick Test Sequence

1. **Create a company:**
```bash
curl -X POST http://localhost:8080/companies/creation \
  -H "Content-Type: application/json" \
  -d '{"name":"Test Company","phone":"1234567890","address":"123 Test St","email":"test@company.com"}'
```

2. **Note the UUID from the database or logs, then update it:**
```bash
curl -X PUT http://localhost:8080/companies/YOUR-UUID-HERE \
  -H "Content-Type: application/json" \
  -d '{"name":"Updated Test Company"}'
```

3. **Delete the company:**
```bash
curl -X DELETE http://localhost:8080/companies/YOUR-UUID-HERE
```

