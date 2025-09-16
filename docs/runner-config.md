# Runner Configuration

---

## 🏃‍♂️ Cucumber Test Runner Setup

To execute your tests using Supernova, you need to configure a Cucumber runner class that uses TestNG and includes your
step definition and support packages.

Here’s an example runner setup:

```java
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "classpath:features",
        glue = {"domains", "shared", "stepdefinitions", "google"},  // 👈 include your project's root package
        plugin = {
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"  // ✅ for reporting
        }
)
public class Runner extends AbstractTestNGCucumberTests {
}
```

---

## 🧩 Explanation of Key Options

### `features = "classpath:features"`

- Specifies the path to your `.feature` files.
- `"classpath:features"` assumes they are located in `src/test/resources/features`.

### `glue = {...}`

- Lists the packages to scan for step definitions and hooks.
- Always include `domains`, `shared`, and `stepdefinitions`.
- Add your custom root package (e.g., `google`) to allow Spring to find your custom steps.

### `plugin = {...}`

- Supernova uses [ExtentReports](https://github.com/extent-framework/extentreports-cucumber7-adapter) as the default
  reporting tool.
- This plugin auto-generates HTML reports after each test execution.

---

✅ With this setup, you can run your tests using `Runner.java` and get structured reports via ExtentReports.


---

## 🛠️ Customization & Hooks

- The runner is **fully customizable** — adapt it to your project's specific needs.
- You can also use Cucumber hooks like `@Before`, `@After` by placing them inside any class within the specified `glue`
  packages.
  These can be used for setup, teardown, logging, database reset, and more inside Runner.class