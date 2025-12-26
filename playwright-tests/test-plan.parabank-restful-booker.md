# Playwright Test Plan: Parabank & RESTful Booker

## 1. Objectives
- Validate core user flows of Parabank (UI) and RESTful Booker (API) for functional correctness.
- Ensure robust coverage of both positive and negative scenarios.
- Achieve cross-browser coverage: Chrome, Firefox, Edge (latest versions).
- Integrate test execution and results with Allure reporting as described in [shared-reporting/allure-integration.md](../shared-reporting/allure-integration.md).

## 2. Scope
- **In Scope:**  
  - Parabank UI: Login, Registration, Account Management, Funds Transfer, Bill Pay, Account History, ATM Services, Navigation, Forgot Login.
  - RESTful Booker API: Authentication, Booking CRUD, Search/Filter, Health Check.
  - Cross-browser testing (Chrome, Firefox, Edge).
  - Automated reporting integration.
- **Out of Scope:**  
  - Performance, security, and non-core features.
  - Manual/exploratory testing unless specified.

## 3. Cross-Browser Matrix
- Chrome (latest)
- Firefox (latest)
- Edge (latest)

## 4. Reporting Integration
- All test runs must generate Allure-compatible results.
- Attach screenshots, logs, and API responses to reports as per [shared-reporting/allure-integration.md](../shared-reporting/allure-integration.md).

## 5. Test Scenarios

### 5.0 Integration & Advanced Features

#### 5.0.1 API & UI Chaining (End-to-End)
- **Scenario:** Create booking via API, verify booking appears in UI.
- **Steps:**
  1. Use API to create a new booking.
  2. Login to UI as the booking owner.
  3. Navigate to bookings page.
  4. Verify the new booking is present.
- **Expected:** Booking created via API is visible in UI.
- **Reporting:** Attach API request/response and UI screenshot.

#### 5.0.2 API Contract Testing
- **Scenario:** Validate API response schema for /booking endpoints.
- **Steps:**
  1. Call /booking endpoint.
  2. Validate response matches expected schema (fields, types).
- **Expected:** Schema validation passes.
- **Reporting:** Attach schema and validation result.

#### 5.0.3 Advanced BDD
- **Scenario:** Use custom BDD steps to chain API and UI actions.
- **Steps:**
  1. Given I create a booking via API
  2. When I login to the UI
  3. Then I should see the booking in the UI

### 5.1 Parabank UI Scenarios

#### 5.1.1 Login - Valid Credentials
- **Preconditions:** User exists with known credentials.
- **Steps:**
  1. Open Parabank login page in each browser.
  2. Enter valid username and password.
  3. Click Login.
- **Expected:** User is logged in, account overview displayed.
- **Edge Cases:**
  - Session timeout after login.
  - Login with leading/trailing spaces in credentials.
- **Reporting:** Attach screenshot of overview page, log browser used.

#### 5.1.2 Login - Invalid Credentials
- **Preconditions:** User does not exist or wrong password.
- **Steps:**
  1. Open Parabank login page in each browser.
  2. Enter invalid credentials.
  3. Click Login.
- **Expected:** Error message displayed, no access granted.
- **Edge Cases:**
  - SQL injection attempt in username/password.
  - Empty fields.
- **Reporting:** Attach screenshot of error, log browser used.

#### 5.1.3 Registration - New User
- **Preconditions:** Unique registration data.
- **Steps:**
  1. Open registration page in each browser.
  2. Fill all required fields with valid data.
  3. Submit the form.
- **Expected:** Registration success, user can log in.
- **Edge Cases:**
  - Attempt to register with existing username.
  - Invalid email format.
- **Reporting:** Attach screenshot of confirmation or error, log browser used.

#### 5.1.4 Funds Transfer - Valid
- **Preconditions:** User logged in, sufficient balance.
- **Steps:**
  1. Go to Transfer Funds page in each browser.
  2. Enter valid source, destination, and amount.
  3. Submit transfer.
- **Expected:** Transfer success message, balances updated.
- **Edge Cases:**
  - Transfer amount exceeds balance.
  - Negative or zero amount.
- **Reporting:** Attach screenshot of confirmation or error, log browser used.

#### 5.1.5 Bill Pay - Invalid Account
- **Preconditions:** User logged in.
- **Steps:**
  1. Go to Bill Pay page in each browser.
  2. Enter invalid account details.
  3. Submit payment.
- **Expected:** Error message displayed, payment not processed.
- **Edge Cases:**
  - Special characters in account number.
  - Missing required fields.
- **Reporting:** Attach screenshot of error, log browser used.

#### 5.1.6 Account Overview, History, ATM, Forgot Login, Navigation
- **Scenarios:**  
  - View account balances and history.
  - Use ATM services (withdraw, deposit).
  - Recover login info.
  - Navigate to Home, About, Services, Contact, Locations.
- **Expected:** Correct data/pages displayed, errors handled gracefully.
- **Edge Cases:**
  - Attempting actions while not logged in.
  - Navigating with expired session.
- **Reporting:** Attach relevant screenshots, log browser used.

### 5.2 RESTful Booker API Scenarios

#### 5.2.1 Auth - Valid Credentials
- **Preconditions:** Valid user credentials.
- **Steps:**
  1. POST /auth with valid data.
- **Expected:** Token returned.
- **Edge Cases:**
  - Case sensitivity in username/password.
- **Reporting:** Attach request/response to report.

#### 5.2.2 Auth - Invalid Credentials
- **Preconditions:** Invalid credentials.
- **Steps:**
  1. POST /auth with invalid data.
- **Expected:** Error response.
- **Edge Cases:**
  - Empty fields.
- **Reporting:** Attach request/response to report.

#### 5.2.3 Create Booking - Valid Data
- **Preconditions:** Valid booking data.
- **Steps:**
  1. POST /booking with valid data.
- **Expected:** Booking created, ID returned.
- **Edge Cases:**
  - Large payloads.
- **Reporting:** Attach request/response.

#### 5.2.4 Create Booking - Invalid Data
- **Preconditions:** Invalid/missing fields.
- **Steps:**
  1. POST /booking with invalid data.
- **Expected:** Error response.
- **Edge Cases:**
  - Malformed JSON.
- **Reporting:** Attach request/response.

#### 5.2.5 Get Booking(s)
- **Steps:**
  1. GET /booking (all or by ID).
- **Expected:** Correct booking(s) returned.
- **Edge Cases:**
  - Non-existent ID.
- **Reporting:** Attach request/response.

#### 5.2.6 Update Booking - Unauthorized
- **Preconditions:** No/invalid token.
- **Steps:**
  1. PUT/PATCH /booking/{id} without valid token.
- **Expected:** Unauthorized error.
- **Edge Cases:**
  - Expired token.
- **Reporting:** Attach request/response.

#### 5.2.7 Update Booking - Valid
- **Preconditions:** Valid token, booking exists.
- **Steps:**
  1. PUT/PATCH /booking/{id} with valid data.
- **Expected:** Booking updated.
- **Edge Cases:**
  - Update with partial data.
- **Reporting:** Attach request/response.

#### 5.2.8 Delete Booking
- **Preconditions:** Valid token, booking exists.
- **Steps:**
  1. DELETE /booking/{id}.
- **Expected:** Booking deleted.
- **Edge Cases:**
  - Deleting already deleted booking.
- **Reporting:** Attach request/response.

#### 5.2.9 Health Check
- **Steps:**
  1. GET /ping.
- **Expected:** Service healthy response.
- **Edge Cases:**
  - Service down.
- **Reporting:** Attach request/response.

#### 5.2.10 Negative/Edge Cases
- **Scenarios:**  
  - Invalid endpoints.
  - Missing/invalid data.
  - Unauthorized access.
- **Expected:** Proper error handling.
- **Reporting:** Attach request/response.

## 6. Documentation & Submission
- All scenarios must be implemented as independent, repeatable tests.
- Each test must run across all browsers in the matrix.
- Allure reports must be generated and include screenshots, logs, and API responses.
- Submit the test plan and results in markdown and as Allure reports.

---

**References:**  
- [browser-matrix.json](../shared-config/browser-matrix.json)  
- [allure-integration.md](../shared-reporting/allure-integration.md)
