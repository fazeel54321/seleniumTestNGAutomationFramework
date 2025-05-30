# 🧪 Selenium Testng Automation Framework

A dynamic test automation framework built using **Java**, **Selenium**, **TestNG**, **ExtentReports**, and **WebDriverManager**. It supports **parallel execution**, **data driven testing**, and **dynamic test selection** via **Excel**, and generates logs, screenshots, and rich HTML reports for each test run.

---

## 📁 Project Structure

```
AutomationFramework/
├── src/
├── pom.xml
├── testng.xml (generated dynamically)
├── Testdata/                 # Excel files to control test scenarios
├── Results/
│   ├── Logs/                 # Execution logs (per thread)
│   ├── Screenshots/          # Screenshots of failed test cases
│   └── Reports/              # Custom HTML reports
```

---

## 🚀 How to Run

You only need one command:

```bash
mvn clean test -DbatchName=Sauce -DdataProviderThreadCount=5
```

- `batchName`: Refers to the Excel file name under `./Testdata/` (e.g., `Sauce.xlsx`)
- `dataProviderThreadCount`: Number of parallel threads for executing tests (if dataProviderThreadCount not provided tests will run in single thread by default)

> The framework dynamically creates `testng.xml` based on these parameters before test execution.

---

## ✅ Prerequisites

- Java 11+
- Maven 3.x
- (Optional) Appium server and mobile setup for mobile tests

---

## 📊 Features

- ✅ **Dynamic Test Execution**: Excel files define which test methods run, along with their test data.
- ⚡ **Parallel Execution**: Controlled via `dataProviderThreadCount` Maven variable.
- 📘 **Excel Integration**: Resides in `./Testdata/`. Each row defines a test method and its input.
- 📂 **Logs**: Created per thread under `./Results/Logs`.
- 📸 **Screenshots**: Captured for failed test cases and stored in `./Results/Screenshots`.
- 📝 **Custom HTML Reports**: Automatically generated in `./Results/Reports`, showing status and screenshots.

---

## 📦 Dependencies

- Selenium Java (4.8.3)
- TestNG (7.9.0)
- Apache POI (5.2.5)
- ExtentReports (5.0.9)
- WebDriverManager (5.9.1)
- Apache Commons Lang (3.13.0)

All handled via Maven.

---

## 🛠️ Customize

- Add new Excel files to `./Testdata/` for different test batches.
- Modify the `Web`, `Driver`, and `Utilities` classes to add more interactions and logic.
- HTML report customization can be extended using ExtentReports API.

---

## 📌 Note

- This framework does **not use Log4j**.
- Logs are plain text files generated manually via a custom logger.

---

## 📬 Contributing

Pull requests are welcome! For major changes, please open an issue first to discuss your ideas.
