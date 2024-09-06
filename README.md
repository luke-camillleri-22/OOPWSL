
# OOP Project

## Introduction
This project demonstrates object-oriented programming concepts through three distinct applications:
- **Asset Viewer (C++)**: A C++ application for asset visualization.
- **Financial Portfolio (Java)**: A Java-based portfolio management system.
- **Financial Portfolio (C++)**: Another C++ application that manages a financial portfolio, separate from the asset viewer.

Each application showcases various design patterns and principles in object-oriented programming.

## Project Structure

### 1. Asset Viewer (C++)
The `asset_viewer_cpp` directory contains the source code for a C++ application that visualizes financial assets.

- **Source Code**: Located in `OOP/asset_viewer_cpp/src`.
  - Example: `main.cpp`
- **Protobuf Files**: Located in `OOP/asset_viewer_cpp/src/demo/src/main/proto/`.
  - Example: `financial_portfolio.pb.cc`
- **Build Files**: CMake is used for building the project, with configurations in `OOP/asset_viewer_cpp/build`.

### 2. Financial Portfolio (Java)
The `demo` directory contains a Java-based financial portfolio management system.

- **Source Code**: Located in `OOP/demo/src/main/java/com/example/`.
  - Example classes: `App.java`, `AssetBuilder.java`, `Bank.java`, `Broker.java`
- **Maven Build System**: The `pom.xml` file is located in the `OOP/demo/` directory.

### 3. Financial Portfolio (C++)
The `OOP` directory contains another C++ application for managing a financial portfolio, distinct from the asset viewer.

- **Source Code**: Located in `OOP/src`.
  - Example: `main.cpp`
- **Build Files**: Located in `OOP/out/build/GCC 11.3.0 x86_64-linux-gnu`.

## Installation

### C++ Applications (Asset Viewer and Financial Portfolio)
1. Ensure you have CMake installed.
2. Navigate to the respective directory (either `OOP/asset_viewer_cpp` or `OOP/out/build/GCC 11.3.0 x86_64-linux-gnu`).
3. Run the following command to build the applications:
    ```bash
    make
    ```

### Java Application (Financial Portfolio)
1. Ensure you have Maven installed.
2. Navigate to the `OOP/demo` directory.
3. Build the project using Maven:
    ```bash
    mvn clean install
    ```

## Usage

### 1. Asset Viewer (C++)
After building the asset viewer project, run the application with:
```bash
./asset_viewer (path to protobuf bin file)
```
This will launch the asset viewer, allowing you to view financial assets.

### 2. Financial Portfolio (Java)
After building the Java application, you can run it with:
```bash
mvn exec:java -Dexec.mainClass="com.example.App"
```
This will start the portfolio management application in Java.

### 3. Financial Portfolio (C++)
Once built, you can run the C++ financial portfolio application by executing:
```bash
./FinancialPortfolio
```
This application allows you to manage financial portfolios from the console.

## License
This project is licensed under the MIT License. See the `LICENSE` file for details.
