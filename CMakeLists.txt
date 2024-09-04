cmake_minimum_required(VERSION 3.10)
project(FinancialPortfolio)


set(CMAKE_CXX_STANDARD 20)

include_directories(include)

add_executable(FinancialPortfolio
    src/main.cpp
    src/Stock.cpp
    src/Bond.cpp
    src/MutualFund.cpp
    src/Portfolio.cpp
    src/Broker.cpp
    src/Bank.cpp
    src/MutualFundManager.cpp
)