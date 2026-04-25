# NopCommerce Admin — Cucumber Automation Framework

[![Automation Tests](https://github.com/AkashGowda-23/NopE-CommerceAppCucumber/actions/workflows/ci.yml/badge.svg)](https://github.com/AkashGowda-23/NopE-CommerceAppCucumber/actions/workflows/ci.yml)

A **production-grade** Selenium + Cucumber (BDD) test automation framework for the [NopCommerce Admin Demo](https://admin-demo.nopcommerce.com) portal, built with Java 17 and Maven.

---

## 🏗️ Framework Architecture

```
src/
├── test/
│   ├── java/
│   │   ├── base/
│   │   │   ├── BaseTest.java        # WebDriver lifecycle, waits, screenshots
│   │   │   └── ConfigReader.java    # config.properties loader (CI -D override)
│   │   ├── hooks/
│   │   │   └── Hooks.java           # Before/After/AfterStep Cucumber hooks
│   │   ├── PageObject/
│   │   │   └── Login.java           # Page Object for the login page
│   │   ├── runner/
│   │   │   └── TestRunner.java      # JUnit + CucumberOptions entry point
│   │   └── stepDefinition/
│   │       └── Steps.java           # Gherkin step implementations
│   └── resources/
│       ├── config.properties        # Runtime config (browser, URL, credentials)
│       ├── extent.properties        # Extent Reports config
│       ├── log4j2.xml               # Logging config
│       └── features/
│           └── Login.feature        # Gherkin scenarios
```

---

## ⚙️ Prerequisites

| Tool | Version |
|------|---------|
| Java | 17+ |
| Maven | 3.8+ |
| Google Chrome | Latest |

> **No manual ChromeDriver download needed.** Selenium 4.x uses the built-in Selenium Manager to auto-download the correct driver.

---

## 🚀 Running Tests

### Run all tests
```bash
mvn test
```

### Run only smoke tests
```bash
mvn test -Dcucumber.filter.tags="@smoke"
```

### Run in headless mode (for CI)
```bash
mvn test -Dheadless=true
```

### Override credentials (never commit real creds)
```bash
mvn test -Dadmin.email=admin@yourstore.com -Dadmin.password=admin
```

### Run with Firefox
```bash
mvn test -Dbrowser=firefox
```

---

## 📊 Reports

| Report | Location |
|--------|-----------|
| Extent HTML | `target/extent-reports/SparkReport.html` |
| Cucumber HTML | `target/cucumber-reports/cucumber.html` |
| Cucumber JSON | `target/cucumber-reports/cucumber.json` |
| Allure Results | `target/allure-results/` |
| Logs | `target/logs/automation.log` |
| Screenshots | `target/screenshots/` |

To view the Allure report locally:
```bash
mvn allure:serve
```

---

## 🔖 Cucumber Tags

| Tag | Purpose |
|-----|---------|
| `@smoke` | Fast sanity checks — run before every deployment |
| `@regression` | Full regression suite — run nightly |
| `@negative` | Negative/invalid-input scenarios |
| `@wip` | Work-in-progress — excluded from CI automatically |

---

## 🔐 CI/CD — GitHub Actions

The pipeline (`.github/workflows/ci.yml`) runs on every push and PR:
- Launches Chrome in headless mode on Ubuntu
- Injects credentials via GitHub Secrets (`ADMIN_EMAIL`, `ADMIN_PASSWORD`)
- Uploads Extent Reports, JSON reports, screenshots, and logs as artefacts
- Auto-retries flaky tests once (`rerunFailingTestsCount=1`)

Set up secrets in **Settings → Secrets → Actions**:
```
ADMIN_EMAIL     → admin@yourstore.com
ADMIN_PASSWORD  → admin
```

---

## 🧑‍💻 Author

**Akash B R** — Automation Testing Specialist | Selenium WebDriver Expert | Bengaluru, India
