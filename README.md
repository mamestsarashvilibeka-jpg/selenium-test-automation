# 🤖 Selenium Test Automation Framework

A production-ready Java Selenium test automation framework built with industry best practices — Page Object Model, parallel execution, CI/CD integration, and rich reporting.

[![CI](https://github.com/mamestsarashvilibeka-jpg/selenium-test-automation/actions/workflows/ci.yml/badge.svg)](https://github.com/mamestsarashvilibeka-jpg/selenium-test-automation/actions)
[![Java](https://img.shields.io/badge/Java-11-orange.svg)](https://adoptium.net/)
[![Selenium](https://img.shields.io/badge/Selenium-4.18-green.svg)](https://selenium.dev/)
[![TestNG](https://img.shields.io/badge/TestNG-7.9-blue.svg)](https://testng.org/)
[![Maven](https://img.shields.io/badge/Maven-3.9-red.svg)](https://maven.apache.org/)

---

## 📋 Features

| Feature | Details |
|---|---|
| **Architecture** | Page Object Model (POM) with PageFactory |
| **Test Runner** | TestNG with XML suite configuration |
| **Browsers** | Chrome, Firefox, Edge (via WebDriverManager) |
| **Parallel Execution** | Thread-safe ThreadLocal WebDriver |
| **Reporting** | Allure Reports + TestNG HTML Reports |
| **Logging** | Log4j2 with rolling file appender |
| **CI/CD** | GitHub Actions (headless, nightly, manual trigger) |
| **Test Data** | Java Faker for dynamic data generation |
| **Screenshots** | Auto-capture on test failure + Allure attachment |

---

## 🏗️ Project Structure

```
selenium-automation/
├── .github/
│   └── workflows/
│       └── ci.yml                    # GitHub Actions pipeline
├── src/
│   └── test/
│       ├── java/com/automation/
│       │   ├── config/
│       │   │   └── ConfigReader.java  # Singleton config (properties + JVM overrides)
│       │   ├── listeners/
│       │   │   └── TestListener.java  # TestNG listener (logging, reporting)
│       │   ├── pages/
│       │   │   ├── BasePage.java      # Shared element interaction methods
│       │   │   ├── LoginPage.java     # Login page object
│       │   │   ├── InventoryPage.java # Product listing page
│       │   │   ├── CartPage.java      # Shopping cart page
│       │   │   └── CheckoutPage.java  # Checkout pages (step 1 & 2)
│       │   ├── tests/
│       │   │   ├── BaseTest.java      # Driver lifecycle + Allure screenshot
│       │   │   ├── LoginTest.java     # 6 login test cases + DataProvider
│       │   │   ├── InventoryTest.java # 6 product catalog tests
│       │   │   └── CheckoutTest.java  # 3 E2E checkout tests
│       │   └── utils/
│       │       ├── DriverManager.java # ThreadLocal WebDriver factory
│       │       ├── WaitUtils.java     # Explicit wait helpers
│       │       └── ScreenshotUtils.java # Screenshot capture
│       └── resources/
│           ├── config.properties      # Framework configuration
│           ├── testng.xml            # Test suites (smoke + regression)
│           └── log4j2.xml            # Logging configuration
├── screenshots/                       # Auto-created on failure
├── pom.xml                           # Maven dependencies
└── README.md
```

---

## 🚀 Quick Start

### Prerequisites
- Java 11+
- Maven 3.8+
- Chrome browser (auto-managed by WebDriverManager)

### 1. Clone the repository
```bash
git clone https://github.com/yourusername/selenium-automation.git
cd selenium-automation
```

### 2. Run all tests
```bash
mvn clean test
```

### 3. Run with specific browser
```bash
mvn clean test -Dbrowser=firefox
```

### 4. Run in headless mode (CI)
```bash
mvn clean test -Dheadless=true
```

### 5. Run smoke tests only
```bash
mvn clean test -Dgroups=smoke
```

### 6. Generate Allure report
```bash
mvn allure:serve
```

---

## 🧪 Test Suites

### Login Tests (`LoginTest.java`)
| Test | Description |
|---|---|
| `testSuccessfulLogin` | Valid credentials → inventory page |
| `testInvalidPasswordShowsError` | Wrong password → error message |
| `testLockedOutUser` | Locked user → specific error |
| `testEmptyCredentialsShowError` | Empty form → validation |
| `testLogout` | Login then logout → redirects |
| `testMultipleValidUsers` | DataProvider for user types |

### Inventory Tests (`InventoryTest.java`)
| Test | Description |
|---|---|
| `testProductsDisplayed` | Products visible on page load |
| `testSortProductsAZ` | Sort A→Z validation |
| `testSortProductsZA` | Sort Z→A validation |
| `testSortByPriceLowToHigh` | Price ascending sort |
| `testAddProductToCart` | Cart badge increments |
| `testCartContainsCorrectItems` | Cart shows correct item |

### Checkout Tests (`CheckoutTest.java`)
| Test | Description |
|---|---|
| `testCompleteCheckoutFlow` | Full E2E: add → cart → checkout → confirm |
| `testCheckoutSummaryDisplayed` | Order total shown on review |
| `testCartToCheckoutNavigation` | Cart to checkout navigation |

---

## ⚙️ Configuration

All settings in `src/test/resources/config.properties`. Override via JVM flags:

```properties
browser=chrome           # chrome | firefox | edge
headless=false           # true for CI/CD
implicit.wait=10         # seconds
explicit.wait=15         # seconds
```

**Override at runtime:**
```bash
mvn test -Dbrowser=firefox -Dheadless=true
```

---

## 📊 Reporting

### Allure Report (recommended)
```bash
# Generate and open in browser
mvn allure:serve

# Generate static report
mvn allure:report
# Open: target/site/allure-maven-plugin/index.html
```

### TestNG Report
```
target/surefire-reports/index.html
```

---

## 🔄 CI/CD

GitHub Actions runs automatically on:
- **Push** to `main` or `develop`
- **Pull Requests** to `main`
- **Nightly** at 2:00 AM UTC
- **Manual** via workflow dispatch (choose browser)

Artifacts uploaded per run:
- Allure HTML report
- Allure raw results
- Screenshots (failures only)

---

## 🛠️ Design Patterns & Best Practices

- **Page Object Model** — UI logic separated from test logic
- **ThreadLocal Driver** — safe parallel test execution
- **Explicit Waits** — no `Thread.sleep()`, ever
- **Fluent API** — method chaining for readable test code
- **Allure Annotations** — `@Epic`, `@Feature`, `@Story`, `@Severity`
- **DataProvider** — data-driven tests without external dependency
- **Singleton Config** — single source of truth for properties
- **Auto Screenshot on Failure** — attached to Allure report

---

## 📬 Contact

Built by a QA Automation Engineer.  
Available for freelance on [Upwork](https://www.upwork.com/freelancers/~01bb63b811505a439a).

---

*Target application: [SauceDemo](https://www.saucedemo.com/) — a purpose-built demo e-commerce app for automation practice.*
