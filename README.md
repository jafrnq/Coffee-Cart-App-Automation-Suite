# Coffee Cart App - Automation Suite

This project is a Selenium-based automation test suite developed for practicing automated UI testing on the Coffee Cart web application. It includes tests for various scenarios using Maven, TestNG, and Allure for reporting, with cross-browser capabilities implemented for Chrome, Edge, and Firefox.

## Table of Contents
- [Project Overview](#project-overview)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Setup and Installation](#setup-and-installation)
- [Running Tests](#running-tests)
- [Allure Reports](#allure-reports)
- [Contributing](#contributing)
- [License](#license)

## Project Overview

The **Coffee Cart App Automation Practice** is designed to test the Coffee Cart web application. It covers multiple test cases to validate the cart functionality, including item addition, cart updating, and checkout processes. The test suite also implements cross-browser testing across Chrome, Edge, and Firefox.

## Technologies Used
- **Java**
- **Selenium WebDriver**
- **TestNG**
- **Maven**
- **Allure Reports**
- **Browsers Supported**: Chrome, Firefox, Edge

## Project Structure
```plaintext
Coffee-Cart-App-AutomationPractice/
├── src/
│   ├── test/
│   │   ├── java/
│   │   │   ├── base/
│   │   │   ├── tests/
│   │   │   ├── utilities/
│   │   │   └── listeners/
├── pom.xml
└── README.md
```


- **Base**: Contains base classes like `BaseTest`, which is responsible for the test setup and teardown.
- **Tests**: All TestNG test classes related to the Coffee Cart app.
- **Utilities**: Utility classes such as `CoffeeCartUtilityMethods` used for reusable test logic.
- **Listeners**: Contains TestNG listeners for better test management and reporting.

## Setup and Installation (For Local testing)

1. Clone the repository:
    ```bash
    git clone https://github.com/jafrnq/Coffee-Cart-App-AutomationPractice.git
    ```
   
2. Navigate into the project directory:
    ```bash
    cd Coffee-Cart-App-AutomationPractice
    ```

3. Install dependencies via Maven:
    ```bash
    mvn clean install
    ```

4. The Project uses Selenium 4 so browser drivers should be automatically installed.

### Running Tests

To run all tests using TestNG, use the following Maven command:

```bash
mvn test
```

To execute tests for specific browsers (Edge/Firefox), you can set the browser parameter in your TestNG suite XML or pass it through Maven as:

### Allure Reports

This project uses Allure for reporting test results. After running the tests, generate the Allure report using:

```bash
mvn allure:report
mvn allure:serve
```

This will automatically open the generated report in your default browser.

## GitHub Actions CI/CD
This project uses GitHub Actions for continuous integration and automated test execution. Every time you push changes to the repository, the workflow will automatically run your tests and generate Allure reports.

## Running Tests via GitHub Actions
1. Navigate to your repository on GitHub
2. Click on the "Actions" tab at the top of your repository
3. In the left sidebar, click on "Run Tests and Generate Allure Report"
4. Click the "Run workflow" button
   - Select the branch you want to run the tests on
   - Click "Run workflow" to start the execution
5. A new workflow run will be initiated, and you can click on it to see the progress
6. Once completed, you can view the test results and Allure report in the workflow artifacts

### Viewing Test Results
- The test results will be available in the Actions tab after each workflow run
- Allure reports are automatically generated and published to GitHub Pages
- You can access the latest Allure report by clicking on the GitHub Pages link in the latest "pages-build-deployment" build or in your repository settings.

## Contributing
If you would like to contribute, feel free to open an issue or submit a pull request. Please make sure to follow best practices for Java and Selenium projects.

## License
This project is licensed under the MIT License - see the LICENSE file for details.
