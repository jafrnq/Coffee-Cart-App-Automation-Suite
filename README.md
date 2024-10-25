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

## Setup and Installation

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

## Running Tests

To run all tests using TestNG, use the following Maven command:

```bash
mvn test
```

To execute tests for specific browsers (Edge/Firefox), you can set the browser parameter in your TestNG suite XML or pass it through Maven as:

## Allure Reports

This project uses Allure for reporting test results. After running the tests, generate the Allure report using:

```bash
mvn allure:report
mvn allure:serve
```

This will automatically open the generated report in your default browser.

## Contributing
If you would like to contribute, feel free to open an issue or submit a pull request. Please make sure to follow best practices for Java and Selenium projects.

## License
This project is licensed under the MIT License - see the LICENSE file for details.

```vbnet 
Copy and paste this into your `README.md` file on GitHub. Let me know if you need any adjustments!
```
