# Report Configuration

---

## 📊 Supernova Report System

Supernova uses **ExtentReports** as its default reporting tool.  
It provides rich HTML reports out of the box without any custom setup in code.

To enable reporting, you **must include two configuration files** in your project:

---

## 1️⃣ extent-config.xml

This file defines the layout and appearance of the generated HTML report.

📁 Path: `src/test/resources/extent-config.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<extentreports>
    <configuration>
        <theme>standard</theme>
        <encoding>UTF-8</encoding>
        <protocol>https</protocol>
        <documentTitle>google</documentTitle>
        <reportName>google</reportName>
        <testViewChartLocation>top</testViewChartLocation>
    </configuration>
</extentreports>
```

🔹 You can customize `documentTitle` and `reportName` based on your project.

---

## 2️⃣ extent.properties

This file tells the framework where to output the report and which config to use.

📁 Path: `src/test/resources/extent.properties`

```properties
extent.reporter.spark.start=true
extent.reporter.spark.config=src/test/resources/extent-config.xml
extent.reporter.spark.out=target/report-output/report.html
```

🔸 This configuration will generate a report at: `target/report-output/report.html`

---

## ⚠️ Important Notes

- Both files are **mandatory** for report generation.
- Make sure the paths are correct, especially if you are using custom Maven build plugins or different folder structures.
- The report is automatically generated after each test run using your Cucumber runner class.

---

✅ With this setup, you’ll get detailed, beautiful HTML reports without needing to write any custom reporting logic.
