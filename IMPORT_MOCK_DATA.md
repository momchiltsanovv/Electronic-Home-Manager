# Import Mock Data Guide

This guide helps you import the comprehensive mock data from `MOCK_DATA.json` for testing all features.

**Base URL:** `http://localhost:8080`

---

## Overview

The mock data includes:
- **3 Companies**
- **7 Employees** (distributed across companies)
- **7 Buildings** (with 124 total apartments)
- **15 Residents** (distributed across buildings)
- **14 Fees** (mix of paid and unpaid)

---

## Step 1: Import Companies

Create all companies first and save their IDs.

```bash
# Company 1: Acme Property Management
curl -X POST http://localhost:8080/companies/creation \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Acme Property Management",
    "phone": "1234567890",
    "address": "123 Business Park, Sofia, Bulgaria",
    "email": "contact@acme.com"
  }'
# Save ID as COMPANY_1_ID

# Company 2: Global Real Estate Services
curl -X POST http://localhost:8080/companies/creation \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Global Real Estate Services",
    "phone": "0987654321",
    "address": "456 Corporate Avenue, Plovdiv, Bulgaria",
    "email": "info@globalrealestate.com"
  }'
# Save ID as COMPANY_2_ID

# Company 3: Premium Housing Solutions
curl -X POST http://localhost:8080/companies/creation \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Premium Housing Solutions",
    "phone": "1122334455",
    "address": "789 Financial District, Varna, Bulgaria",
    "email": "support@premiumhousing.bg"
  }'
# Save ID as COMPANY_3_ID
```

---

## Step 2: Import Employees

Create employees for each company.

```bash
# Company 1 Employees
curl -X POST http://localhost:8080/employees/creation \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Alice",
    "lastName": "Johnson",
    "phone": "1111111111",
    "email": "alice.johnson@acme.com",
    "salary": 5000.00,
    "companyId": "COMPANY_1_ID"
  }'
# Save ID as EMPLOYEE_1_ID

curl -X POST http://localhost:8080/employees/creation \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Bob",
    "lastName": "Smith",
    "phone": "2222222222",
    "email": "bob.smith@acme.com",
    "salary": 5500.00,
    "companyId": "COMPANY_1_ID"
  }'
# Save ID as EMPLOYEE_2_ID

curl -X POST http://localhost:8080/employees/creation \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Carol",
    "lastName": "Williams",
    "phone": "3333333333",
    "email": "carol.williams@acme.com",
    "salary": 5200.00,
    "companyId": "COMPANY_1_ID"
  }'
# Save ID as EMPLOYEE_3_ID

# Company 2 Employees
curl -X POST http://localhost:8080/employees/creation \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "David",
    "lastName": "Brown",
    "phone": "4444444444",
    "email": "david.brown@globalrealestate.com",
    "salary": 5800.00,
    "companyId": "COMPANY_2_ID"
  }'
# Save ID as EMPLOYEE_4_ID

curl -X POST http://localhost:8080/employees/creation \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Emma",
    "lastName": "Davis",
    "phone": "5555555555",
    "email": "emma.davis@globalrealestate.com",
    "salary": 5400.00,
    "companyId": "COMPANY_2_ID"
  }'
# Save ID as EMPLOYEE_5_ID

# Company 3 Employees
curl -X POST http://localhost:8080/employees/creation \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Frank",
    "lastName": "Miller",
    "phone": "6666666666",
    "email": "frank.miller@premiumhousing.bg",
    "salary": 6000.00,
    "companyId": "COMPANY_3_ID"
  }'
# Save ID as EMPLOYEE_6_ID

curl -X POST http://localhost:8080/employees/creation \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Grace",
    "lastName": "Wilson",
    "phone": "7777777777",
    "email": "grace.wilson@premiumhousing.bg",
    "salary": 5600.00,
    "companyId": "COMPANY_3_ID"
  }'
# Save ID as EMPLOYEE_7_ID
```

---

## Step 3: Import Buildings

Create buildings. Apartments will be automatically created.

```bash
# Building 1: 123 Main Street (Company 1, Employee 1)
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
    "companyId": "COMPANY_1_ID"
  }'
# Save ID as BUILDING_1_ID
# Get apartments: curl -X GET http://localhost:8080/apartments/building/BUILDING_1_ID

# Building 2: 456 Oak Avenue (Company 1, Employee 2)
curl -X POST http://localhost:8080/buildings/creation \
  -H "Content-Type: application/json" \
  -d '{
    "address": "456 Oak Avenue, Sofia, Bulgaria",
    "floors": 3,
    "totalApartments": 12,
    "builtArea": 1800.00,
    "commonAreas": 300.00,
    "hasElevator": false,
    "pricePerSquareMeter": 1200.00,
    "elevatorFeePerPerson": 0.00,
    "petFeePerPet": 12.00,
    "companyId": "COMPANY_1_ID"
  }'
# Save ID as BUILDING_2_ID

# Building 3: 789 Pine Road (Company 1, Employee 3)
curl -X POST http://localhost:8080/buildings/creation \
  -H "Content-Type: application/json" \
  -d '{
    "address": "789 Pine Road, Sofia, Bulgaria",
    "floors": 4,
    "totalApartments": 16,
    "builtArea": 2200.75,
    "commonAreas": 400.50,
    "hasElevator": true,
    "pricePerSquareMeter": 1400.00,
    "elevatorFeePerPerson": 20.00,
    "petFeePerPet": 18.00,
    "companyId": "COMPANY_1_ID"
  }'
# Save ID as BUILDING_3_ID

# Building 4: 321 Elm Street (Company 2, Employee 4)
curl -X POST http://localhost:8080/buildings/creation \
  -H "Content-Type: application/json" \
  -d '{
    "address": "321 Elm Street, Plovdiv, Bulgaria",
    "floors": 6,
    "totalApartments": 24,
    "builtArea": 3000.00,
    "commonAreas": 600.00,
    "hasElevator": true,
    "pricePerSquareMeter": 1600.00,
    "elevatorFeePerPerson": 30.00,
    "petFeePerPet": 20.00,
    "companyId": "COMPANY_2_ID"
  }'
# Save ID as BUILDING_4_ID

# Building 5: 654 Maple Drive (Company 2, Employee 5)
curl -X POST http://localhost:8080/buildings/creation \
  -H "Content-Type: application/json" \
  -d '{
    "address": "654 Maple Drive, Plovdiv, Bulgaria",
    "floors": 2,
    "totalApartments": 8,
    "builtArea": 1200.00,
    "commonAreas": 200.00,
    "hasElevator": false,
    "pricePerSquareMeter": 1100.00,
    "elevatorFeePerPerson": 0.00,
    "petFeePerPet": 10.00,
    "companyId": "COMPANY_2_ID"
  }'
# Save ID as BUILDING_5_ID

# Building 6: 987 Cedar Lane (Company 3, Employee 6)
curl -X POST http://localhost:8080/buildings/creation \
  -H "Content-Type: application/json" \
  -d '{
    "address": "987 Cedar Lane, Varna, Bulgaria",
    "floors": 7,
    "totalApartments": 28,
    "builtArea": 3500.25,
    "commonAreas": 700.50,
    "hasElevator": true,
    "pricePerSquareMeter": 1700.00,
    "elevatorFeePerPerson": 35.00,
    "petFeePerPet": 25.00,
    "companyId": "COMPANY_3_ID"
  }'
# Save ID as BUILDING_6_ID

# Building 7: 147 Birch Boulevard (Company 3, Employee 7)
curl -X POST http://localhost:8080/buildings/creation \
  -H "Content-Type: application/json" \
  -d '{
    "address": "147 Birch Boulevard, Varna, Bulgaria",
    "floors": 4,
    "totalApartments": 16,
    "builtArea": 2100.00,
    "commonAreas": 350.00,
    "hasElevator": true,
    "pricePerSquareMeter": 1450.00,
    "elevatorFeePerPerson": 22.00,
    "petFeePerPet": 16.00,
    "companyId": "COMPANY_3_ID"
  }'
# Save ID as BUILDING_7_ID
```

**Important:** After creating each building, get the apartments:
```bash
curl -X GET http://localhost:8080/apartments/building/BUILDING_X_ID
```
Save apartment IDs by apartment number (e.g., `APARTMENT_1_BUILDING_1_ID`, `APARTMENT_2_BUILDING_1_ID`, etc.)

---

## Step 4: Import Residents

Create residents. They will be automatically assigned to apartments.

```bash
# Building 1 Residents
curl -X POST http://localhost:8080/residents/creation \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "age": 45,
    "phone": "1000000001",
    "email": "john.doe@example.com",
    "usesElevator": true,
    "buildingId": "BUILDING_1_ID",
    "apartmentId": "APARTMENT_1_BUILDING_1_ID"
  }'
# Save ID as RESIDENT_1_ID

curl -X POST http://localhost:8080/residents/creation \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Jane",
    "lastName": "Doe",
    "age": 42,
    "phone": "1000000002",
    "email": "jane.doe@example.com",
    "usesElevator": true,
    "buildingId": "BUILDING_1_ID",
    "apartmentId": "APARTMENT_1_BUILDING_1_ID"
  }'
# Save ID as RESIDENT_2_ID

curl -X POST http://localhost:8080/residents/creation \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Mike",
    "lastName": "Johnson",
    "age": 35,
    "phone": "1000000003",
    "email": "mike.johnson@example.com",
    "usesElevator": false,
    "buildingId": "BUILDING_1_ID",
    "apartmentId": "APARTMENT_2_BUILDING_1_ID"
  }'
# Save ID as RESIDENT_3_ID

curl -X POST http://localhost:8080/residents/creation \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Richard",
    "lastName": "Martin",
    "age": 48,
    "phone": "1000000004",
    "email": "richard.martin@example.com",
    "usesElevator": true,
    "buildingId": "BUILDING_1_ID",
    "apartmentId": "APARTMENT_3_BUILDING_1_ID"
  }'
# Save ID as RESIDENT_13_ID

# Building 2 Residents
curl -X POST http://localhost:8080/residents/creation \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Sarah",
    "lastName": "Brown",
    "age": 28,
    "phone": "2000000001",
    "email": "sarah.brown@example.com",
    "usesElevator": false,
    "buildingId": "BUILDING_2_ID",
    "apartmentId": "APARTMENT_1_BUILDING_2_ID"
  }'
# Save ID as RESIDENT_4_ID

# Building 3 Residents
curl -X POST http://localhost:8080/residents/creation \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "David",
    "lastName": "Wilson",
    "age": 50,
    "phone": "3000000001",
    "email": "david.wilson@example.com",
    "usesElevator": true,
    "buildingId": "BUILDING_3_ID",
    "apartmentId": "APARTMENT_1_BUILDING_3_ID"
  }'
# Save ID as RESIDENT_5_ID

curl -X POST http://localhost:8080/residents/creation \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Jennifer",
    "lastName": "Thompson",
    "age": 36,
    "phone": "3000000002",
    "email": "jennifer.thompson@example.com",
    "usesElevator": false,
    "buildingId": "BUILDING_3_ID",
    "apartmentId": "APARTMENT_2_BUILDING_3_ID"
  }'
# Save ID as RESIDENT_14_ID

# Building 4 Residents
curl -X POST http://localhost:8080/residents/creation \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Emily",
    "lastName": "Martinez",
    "age": 32,
    "phone": "4000000001",
    "email": "emily.martinez@example.com",
    "usesElevator": true,
    "buildingId": "BUILDING_4_ID",
    "apartmentId": "APARTMENT_1_BUILDING_4_ID"
  }'
# Save ID as RESIDENT_6_ID

curl -X POST http://localhost:8080/residents/creation \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Robert",
    "lastName": "Taylor",
    "age": 38,
    "phone": "4000000002",
    "email": "robert.taylor@example.com",
    "usesElevator": false,
    "buildingId": "BUILDING_4_ID",
    "apartmentId": "APARTMENT_1_BUILDING_4_ID"
  }'
# Save ID as RESIDENT_7_ID

curl -X POST http://localhost:8080/residents/creation \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Charles",
    "lastName": "Garcia",
    "age": 44,
    "phone": "4000000003",
    "email": "charles.garcia@example.com",
    "usesElevator": true,
    "buildingId": "BUILDING_4_ID",
    "apartmentId": "APARTMENT_2_BUILDING_4_ID"
  }'
# Save ID as RESIDENT_15_ID

# Building 5 Residents
curl -X POST http://localhost:8080/residents/creation \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Lisa",
    "lastName": "Anderson",
    "age": 29,
    "phone": "5000000001",
    "email": "lisa.anderson@example.com",
    "usesElevator": false,
    "buildingId": "BUILDING_5_ID",
    "apartmentId": "APARTMENT_1_BUILDING_5_ID"
  }'
# Save ID as RESIDENT_8_ID

# Building 6 Residents
curl -X POST http://localhost:8080/residents/creation \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "James",
    "lastName": "Thomas",
    "age": 55,
    "phone": "6000000001",
    "email": "james.thomas@example.com",
    "usesElevator": true,
    "buildingId": "BUILDING_6_ID",
    "apartmentId": "APARTMENT_1_BUILDING_6_ID"
  }'
# Save ID as RESIDENT_9_ID

curl -X POST http://localhost:8080/residents/creation \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Maria",
    "lastName": "Jackson",
    "age": 41,
    "phone": "6000000002",
    "email": "maria.jackson@example.com",
    "usesElevator": true,
    "buildingId": "BUILDING_6_ID",
    "apartmentId": "APARTMENT_1_BUILDING_6_ID"
  }'
# Save ID as RESIDENT_10_ID

# Building 7 Residents
curl -X POST http://localhost:8080/residents/creation \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "William",
    "lastName": "White",
    "age": 33,
    "phone": "7000000001",
    "email": "william.white@example.com",
    "usesElevator": true,
    "buildingId": "BUILDING_7_ID",
    "apartmentId": "APARTMENT_1_BUILDING_7_ID"
  }'
# Save ID as RESIDENT_11_ID

curl -X POST http://localhost:8080/residents/creation \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Patricia",
    "lastName": "Harris",
    "age": 27,
    "phone": "7000000002",
    "email": "patricia.harris@example.com",
    "usesElevator": false,
    "buildingId": "BUILDING_7_ID",
    "apartmentId": "APARTMENT_2_BUILDING_7_ID"
  }'
# Save ID as RESIDENT_12_ID
```

---

## Step 5: Assign Residents to Apartments

Assign residents to apartments. The first resident automatically becomes the owner if no owner exists.

```bash
# Apartment 1, Building 1 - John Doe becomes owner, Jane Doe is resident
curl -X PUT http://localhost:8080/apartments/APARTMENT_1_BUILDING_1_ID \
  -H "Content-Type: application/json" \
  -d '{
    "residentIds": ["RESIDENT_1_ID", "RESIDENT_2_ID"]
  }'

# Apartment 2, Building 1 - Mike Johnson becomes owner
curl -X PUT http://localhost:8080/apartments/APARTMENT_2_BUILDING_1_ID \
  -H "Content-Type: application/json" \
  -d '{
    "residentIds": ["RESIDENT_3_ID"]
  }'

# Apartment 3, Building 1 - Richard Martin becomes owner
curl -X PUT http://localhost:8080/apartments/APARTMENT_3_BUILDING_1_ID \
  -H "Content-Type: application/json" \
  -d '{
    "residentIds": ["RESIDENT_13_ID"]
  }'

# Apartment 1, Building 2 - Sarah Brown becomes owner
curl -X PUT http://localhost:8080/apartments/APARTMENT_1_BUILDING_2_ID \
  -H "Content-Type: application/json" \
  -d '{
    "residentIds": ["RESIDENT_4_ID"]
  }'

# Apartment 1, Building 3 - David Wilson becomes owner
curl -X PUT http://localhost:8080/apartments/APARTMENT_1_BUILDING_3_ID \
  -H "Content-Type: application/json" \
  -d '{
    "residentIds": ["RESIDENT_5_ID"]
  }'

# Apartment 2, Building 3 - Jennifer Thompson becomes owner
curl -X PUT http://localhost:8080/apartments/APARTMENT_2_BUILDING_3_ID \
  -H "Content-Type: application/json" \
  -d '{
    "residentIds": ["RESIDENT_14_ID"]
  }'

# Apartment 1, Building 4 - Emily Martinez becomes owner, Robert Taylor is resident
curl -X PUT http://localhost:8080/apartments/APARTMENT_1_BUILDING_4_ID \
  -H "Content-Type: application/json" \
  -d '{
    "residentIds": ["RESIDENT_6_ID", "RESIDENT_7_ID"]
  }'

# Apartment 2, Building 4 - Charles Garcia becomes owner
curl -X PUT http://localhost:8080/apartments/APARTMENT_2_BUILDING_4_ID \
  -H "Content-Type: application/json" \
  -d '{
    "residentIds": ["RESIDENT_15_ID"]
  }'

# Apartment 1, Building 5 - Lisa Anderson becomes owner
curl -X PUT http://localhost:8080/apartments/APARTMENT_1_BUILDING_5_ID \
  -H "Content-Type: application/json" \
  -d '{
    "residentIds": ["RESIDENT_8_ID"]
  }'

# Apartment 1, Building 6 - James Thomas becomes owner, Maria Jackson is resident
curl -X PUT http://localhost:8080/apartments/APARTMENT_1_BUILDING_6_ID \
  -H "Content-Type: application/json" \
  -d '{
    "residentIds": ["RESIDENT_9_ID", "RESIDENT_10_ID"]
  }'

# Apartment 1, Building 7 - William White becomes owner
curl -X PUT http://localhost:8080/apartments/APARTMENT_1_BUILDING_7_ID \
  -H "Content-Type: application/json" \
  -d '{
    "residentIds": ["RESIDENT_11_ID"]
  }'

# Apartment 2, Building 7 - Patricia Harris becomes owner
curl -X PUT http://localhost:8080/apartments/APARTMENT_2_BUILDING_7_ID \
  -H "Content-Type: application/json" \
  -d '{
    "residentIds": ["RESIDENT_12_ID"]
  }'
```

---

## Step 6: Create Fees

Create fees for apartments that have owners.

```bash
# Building 1 Fees
curl -X POST http://localhost:8080/fees/creation \
  -H "Content-Type: application/json" \
  -d '{
    "apartmentId": "APARTMENT_1_BUILDING_1_ID",
    "month": "JANUARY",
    "year": 2024
  }'
# Save ID as FEE_1_ID

curl -X POST http://localhost:8080/fees/creation \
  -H "Content-Type: application/json" \
  -d '{
    "apartmentId": "APARTMENT_1_BUILDING_1_ID",
    "month": "FEBRUARY",
    "year": 2024
  }'
# Save ID as FEE_2_ID

curl -X POST http://localhost:8080/fees/creation \
  -H "Content-Type: application/json" \
  -d '{
    "apartmentId": "APARTMENT_2_BUILDING_1_ID",
    "month": "JANUARY",
    "year": 2024
  }'
# Save ID as FEE_3_ID

curl -X POST http://localhost:8080/fees/creation \
  -H "Content-Type: application/json" \
  -d '{
    "apartmentId": "APARTMENT_2_BUILDING_1_ID",
    "month": "FEBRUARY",
    "year": 2024
  }'
# Save ID as FEE_4_ID

curl -X POST http://localhost:8080/fees/creation \
  -H "Content-Type: application/json" \
  -d '{
    "apartmentId": "APARTMENT_3_BUILDING_1_ID",
    "month": "JANUARY",
    "year": 2024
  }'
# Save ID as FEE_5_ID

# Building 2 Fees
curl -X POST http://localhost:8080/fees/creation \
  -H "Content-Type: application/json" \
  -d '{
    "apartmentId": "APARTMENT_1_BUILDING_2_ID",
    "month": "JANUARY",
    "year": 2024
  }'
# Save ID as FEE_6_ID

# Building 3 Fees
curl -X POST http://localhost:8080/fees/creation \
  -H "Content-Type: application/json" \
  -d '{
    "apartmentId": "APARTMENT_1_BUILDING_3_ID",
    "month": "JANUARY",
    "year": 2024
  }'
# Save ID as FEE_7_ID

curl -X POST http://localhost:8080/fees/creation \
  -H "Content-Type: application/json" \
  -d '{
    "apartmentId": "APARTMENT_2_BUILDING_3_ID",
    "month": "JANUARY",
    "year": 2024
  }'
# Save ID as FEE_8_ID

# Building 4 Fees
curl -X POST http://localhost:8080/fees/creation \
  -H "Content-Type: application/json" \
  -d '{
    "apartmentId": "APARTMENT_1_BUILDING_4_ID",
    "month": "JANUARY",
    "year": 2024
  }'
# Save ID as FEE_9_ID

curl -X POST http://localhost:8080/fees/creation \
  -H "Content-Type: application/json" \
  -d '{
    "apartmentId": "APARTMENT_2_BUILDING_4_ID",
    "month": "JANUARY",
    "year": 2024
  }'
# Save ID as FEE_10_ID

# Building 5 Fees
curl -X POST http://localhost:8080/fees/creation \
  -H "Content-Type: application/json" \
  -d '{
    "apartmentId": "APARTMENT_1_BUILDING_5_ID",
    "month": "JANUARY",
    "year": 2024
  }'
# Save ID as FEE_11_ID

# Building 6 Fees
curl -X POST http://localhost:8080/fees/creation \
  -H "Content-Type: application/json" \
  -d '{
    "apartmentId": "APARTMENT_1_BUILDING_6_ID",
    "month": "JANUARY",
    "year": 2024
  }'
# Save ID as FEE_12_ID

# Building 7 Fees
curl -X POST http://localhost:8080/fees/creation \
  -H "Content-Type: application/json" \
  -d '{
    "apartmentId": "APARTMENT_1_BUILDING_7_ID",
    "month": "JANUARY",
    "year": 2024
  }'
# Save ID as FEE_13_ID

curl -X POST http://localhost:8080/fees/creation \
  -H "Content-Type: application/json" \
  -d '{
    "apartmentId": "APARTMENT_2_BUILDING_7_ID",
    "month": "JANUARY",
    "year": 2024
  }'
# Save ID as FEE_14_ID
```

---

## Step 7: Mark Fees as Paid

Mark some fees as paid (leave some unpaid for testing unpaid reports).

```bash
# Paid fees
curl -X PUT http://localhost:8080/fees/FEE_1_ID/pay \
  -H "Content-Type: application/json" \
  -d '{"paidDate": "2024-01-15"}'

curl -X PUT http://localhost:8080/fees/FEE_2_ID/pay \
  -H "Content-Type: application/json" \
  -d '{"paidDate": "2024-02-10"}'

curl -X PUT http://localhost:8080/fees/FEE_3_ID/pay \
  -H "Content-Type: application/json" \
  -d '{"paidDate": "2024-01-20"}'

# FEE_4_ID remains unpaid

curl -X PUT http://localhost:8080/fees/FEE_5_ID/pay \
  -H "Content-Type: application/json" \
  -d '{"paidDate": "2024-01-18"}'

curl -X PUT http://localhost:8080/fees/FEE_6_ID/pay \
  -H "Content-Type: application/json" \
  -d '{"paidDate": "2024-01-12"}'

curl -X PUT http://localhost:8080/fees/FEE_7_ID/pay \
  -H "Content-Type: application/json" \
  -d '{"paidDate": "2024-01-22"}'

# FEE_8_ID remains unpaid

curl -X PUT http://localhost:8080/fees/FEE_9_ID/pay \
  -H "Content-Type: application/json" \
  -d '{"paidDate": "2024-01-14"}'

curl -X PUT http://localhost:8080/fees/FEE_10_ID/pay \
  -H "Content-Type: application/json" \
  -d '{"paidDate": "2024-01-16"}'

# FEE_11_ID remains unpaid

curl -X PUT http://localhost:8080/fees/FEE_12_ID/pay \
  -H "Content-Type: application/json" \
  -d '{"paidDate": "2024-01-19"}'

curl -X PUT http://localhost:8080/fees/FEE_13_ID/pay \
  -H "Content-Type: application/json" \
  -d '{"paidDate": "2024-01-21"}'

# FEE_14_ID remains unpaid
```

---

## Step 8: Test All Features

Now you can test all features with comprehensive data:

### Test Sorting
```bash
# Companies by revenue
curl -X GET "http://localhost:8080/companies?sortBy=revenue_desc"

# Employees by name
curl -X GET "http://localhost:8080/employees?sortBy=name_asc"

# Employees by properties
curl -X GET "http://localhost:8080/employees?sortBy=properties_desc"

# Buildings by address
curl -X GET "http://localhost:8080/buildings?sortBy=name_asc"

# Buildings by age
curl -X GET "http://localhost:8080/buildings?sortBy=age_desc"

# Residents by name
curl -X GET "http://localhost:8080/residents?sortBy=name_asc"

# Residents by age
curl -X GET "http://localhost:8080/residents?sortBy=age_desc"
```

### Test Reports

All reports return **Summary and Detailed** information:
- **Summary**: Total count and/or total amount
- **Detailed**: Complete list of items

#### a. Properties Serviced by Each Employee in a Given Company
```bash
curl -X GET http://localhost:8080/employees/by-company/COMPANY_1_ID/buildings
```

**Response Structure:**
```json
{
  "totalCount": 3,
  "items": [
    {
      "id": "employee-uuid",
      "firstName": "Alice",
      "lastName": "Johnson",
      "phone": "1234567890",
      "email": "alice@company.com",
      "salary": 5000.00,
      "buildings": [
        {
          "id": "building-uuid",
          "address": "123 Main Street, Sofia",
          "floors": 5,
          "totalApartments": 20
        }
      ]
    }
  ]
}
```

#### b. Apartments in a Building
```bash
curl -X GET http://localhost:8080/apartments/building/BUILDING_1_ID
```

**Response Structure:**
```json
{
  "totalCount": 20,
  "items": [
    {
      "id": "apartment-uuid",
      "number": 101,
      "floor": 1,
      "area": 75.50,
      "ownerId": "resident-uuid",
      "residentIds": ["resident-uuid-1", "resident-uuid-2"]
    }
  ]
}
```

#### c. Residents in a Building
```bash
curl -X GET "http://localhost:8080/residents/building/BUILDING_1_ID?sortBy=name_asc"
```

**Response Structure:**
```json
{
  "totalCount": 5,
  "items": [
    {
      "id": "resident-uuid",
      "firstName": "John",
      "lastName": "Doe",
      "age": 35,
      "phone": "1234567890",
      "email": "john@example.com",
      "usesElevator": true,
      "apartmentNumbers": [101, 102]
    }
  ]
}
```

#### d. Amounts to be Paid (Unpaid Fees)

**By Company:**
```bash
curl -X GET http://localhost:8080/fees/reports/unpaid/company/COMPANY_1_ID
```

**By Building:**
```bash
curl -X GET http://localhost:8080/fees/reports/unpaid/building/BUILDING_1_ID
```

**By Employee:**
```bash
curl -X GET http://localhost:8080/fees/reports/unpaid/employee/EMPLOYEE_1_ID
```

**Response Structure:**
```json
{
  "totalAmount": 15000.50,
  "totalCount": 45,
  "items": [
    {
      "id": "fee-uuid",
      "apartmentId": "apartment-uuid",
      "apartmentNumber": 101,
      "month": "JANUARY",
      "year": 2024,
      "baseAmount": 300.00,
      "elevatorFee": 25.50,
      "petFee": 15.00,
      "totalAmount": 340.50,
      "isPaid": false,
      "paidDate": null
    }
  ]
}
```

#### e. Amounts Paid

**By Company:**
```bash
curl -X GET http://localhost:8080/fees/reports/paid/company/COMPANY_1_ID
```

**By Building:**
```bash
curl -X GET http://localhost:8080/fees/reports/paid/building/BUILDING_1_ID
```

**By Employee:**
```bash
curl -X GET http://localhost:8080/fees/reports/paid/employee/EMPLOYEE_1_ID
```

**Response Structure:**
```json
{
  "totalAmount": 45000.00,
  "totalCount": 135,
  "items": [
    {
      "id": "fee-uuid",
      "apartmentId": "apartment-uuid",
      "apartmentNumber": 102,
      "month": "JANUARY",
      "year": 2024,
      "baseAmount": 300.00,
      "elevatorFee": 25.50,
      "petFee": 0.00,
      "totalAmount": 325.50,
      "isPaid": true,
      "paidDate": "2024-01-15"
    }
  ]
}
```

---

## Data Summary

After importing, you'll have:
- **3 Companies** (Acme, Global Real Estate, Premium Housing)
- **7 Employees** (3 in Company 1, 2 in Company 2, 2 in Company 3)
- **7 Buildings** (124 total apartments)
- **15 Residents** (distributed across buildings)
- **14 Fees** (10 paid, 4 unpaid)

This provides comprehensive test data for all features!


