# 🌟 Supernova

Supernova is a powerful **low-code test automation framework** designed to simplify and speed up the process of testing
APIs, Web applications, Mobile apps (Android & iOS), and Databases.

It is built on top of the following technologies:

- ✅ **Cucumber** for readable Gherkin syntax (BDD)
- ✅ **Spring** for dependency injection and configuration
- ✅ **RestAssured**, **Selenium**, **Appium**, **JDBC** (under the hood)
- ✅ **ExtentReports** for beautiful reporting out of the box

---

## 🎯 Purpose

Supernova was created with the goal of empowering both QA engineers and developers to write robust automated tests *
*with minimal code**.

Its purpose is to:

- Unify API, UI, Mobile, and DB testing under one reusable library
- Enable test steps to be reused across projects and teams
- Reduce time spent writing boilerplate test code
- Provide structured configuration, execution, and reporting

---

## 🧱 Key Features

- 🔌 Modular: Supports Web, API, Android, iOS, and Database testing in one place
- 🧪 Cucumber-Based: Gherkin scenarios with custom reusable step definitions
- ⚙️ Configurable: All settings are driven by a single `configuration.yaml` file
- 📈 Built-in Reporting: Automatically generates Extent HTML reports
- ♻️ Reusable: Easily extendable for multiple teams and multiple test projects

---

## 📂 Folder Structure (Suggested)

```
src/test/java
 └─ google                              # Your project's root package (e.g., company name)
 │   ├─ stepdefinitions                 # Your custom steps
 │   ├─ services                        # Any helper or service logic
 │   └─ utils                           # Data builders, factories, etc.
 └─ runner
 
src/test/resources
 ├─ configuration.yaml                # Main config file (web, mobile, db, etc.)
 ├─ extent.properties                 # Reporting config (ExtentReports)
 ├─ extent-config.xml                 # Extent HTML layout config
 └─ features                          # Your Cucumber .feature files
```

---

## 🧩 Integration

You can integrate Supernova into any Maven-based test project.

1. Add the dependency to your `pom.xml`
2. Create your configuration files
3. Use a Cucumber runner with `@CucumberOptions`
4. Start writing scenarios in `.feature` files using Gherkin

---

## 📚 Documentation

Supernova is structured in multiple focused modules. See the following documents for more details:

- [`spring-config.md`](spring-config.md) – How to set up your own Spring configuration
- [`runner-config.md`](runner-config.md) – How to configure your test runner class
- [`report-config.md`](report-config.md) – How to configure Extent HTML reports
- [`configuration-yaml.md`](configuration-yaml.md) – How to set up and use `configuration.yaml`

---

## 🚀 Ready to Start?

Supernova is designed to be usable out of the box, but also powerful enough to scale with your team.  
Start small, grow fast, and write less code — while testing more.

