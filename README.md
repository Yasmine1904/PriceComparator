# Price Comparator

A console-based Java application that loads product and discount data from CSV files, lets you explore and analyze prices across stores, optimize a shopping basket, and set price alerts.

## Table of Contents

- [Description](#description)  
- [Requirements](#requirements)  
- [Installation](#installation)  
- [Running the App](#running-the-app)  
  - [From the Command Line](#from-the-command-line)  
  - [From IntelliJ IDEA (or any Gradle-enabled IDE)](#from-intellij-idea-or-any-gradle-enabled-ide)  
- [Usage](#usage)  
- [Project Structure](#project-structure)  
- [Resources](#resources)  

## Description

This application:

1. **Loads product data** from `src/main/resources/products/` CSV files  
2. **Loads discount data** from `src/main/resources/discounts/` CSV files  
3. Presents an interactive console **menu** so you can:  
   - List all available products  
   - List all available discounts  (make sure to have available discounts, check the dates)
   - Propose a cost-optimized shopping basket given a list of items  
   - Show all currently active discounts  
   - Show all discounts added in the last 24 hours  (make sure to enter a discount in the last 24h, unless it will not work)
   - Filter historical prices by date range  
   - Rank products by “value per unit” (price ÷ quantity)  
   - List all active price alerts (i.e., products whose current price is at or below your alert threshold)  

All CSV parsing and printing is implemented with OpenCSV beans and custom `CsvDataLoader`/`CsvPrintUtils` utilities. Business logic lives in `Service.java`; data models live in the `Model` module.

## Requirements

- Java 17 or higher  
- Internet access **not** required (all data is local CSV)  
- Gradle Wrapper (included)  

## Installation

**Clone the repository**  
   
   git clone https://github.com/your-username/PriceComparatorM.git
   cd PriceComparatorM/Backend

**Running the App**
From the Command Line
From the Backend directory run:
./gradlew run
This will compile all modules (Model, Repository, Service), load your CSV data, and launch the interactive menu.

**From IntelliJ IDEA (or any Gradle-enabled IDE)**
Open the Gradle tool window.

Under :Backend → Tasks → application double-click run.

To pass program arguments or change options, edit the Run Configuration (“Program arguments” field).

**Usage**
When you run the app, you’ll see a menu like:

=== MENIU PRINCIPAL ===
1 – List all products
2 – List all discounts
3 – Propose optimized basket
4 – Show active discounts
5 – Show discounts in last 24h
6 – Filter price history
7 – Top value per unit
8 – Active price alerts
0 – Exit

Enter the number of the action you want, then follow any prompts (e.g., entering item names, date ranges, or numeric limits). Enter 0 to exit.

**Project Structure**

PriceComparatorM/
└── Backend/
    ├── build.gradle              # Main Gradle build
    ├── settings.gradle           # Includes Model, Repository, Service
    ├── gradlew*                  # Gradle wrapper scripts
    ├── src/
    │   ├── main/
    │   │   ├── java/
    │   │   │   └── Main.java     # Entry point + console menu
    │   │   └── resources/
    │   │       ├── products/     # Sample product CSVs
    │   │       ├── discounts/    # Sample discount CSVs
    │   │       └── priceAlerts/  # Stored alerts
    │   └── test/                 # (empty) unit tests
    ├── Model/                    # Data model module (Product, Discount, etc.)
    ├── Repository/               # CSV loader + printer utilities
    └── Service/                  # Business logic (optimizeBasket, filters, alerts)


**Resources**

OpenCSV for bean binding (com.opencsv.bean.CsvToBeanBuilder)

Java Time API for date handling (java.time.LocalDate)

Gradle for build and run automation
