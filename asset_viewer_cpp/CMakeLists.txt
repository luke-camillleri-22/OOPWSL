cmake_minimum_required(VERSION 3.10)
project(AssetViewer)

find_package(Protobuf REQUIRED)

include_directories(${PROTOBUF_INCLUDE_DIRS})
include_directories(${CMAKE_CURRENT_BINARY_DIR})

# Add the source files
add_executable(asset_viewer src/main.cpp)

target_link_libraries(asset_viewer ${PROTOBUF_LIBRARIES})
