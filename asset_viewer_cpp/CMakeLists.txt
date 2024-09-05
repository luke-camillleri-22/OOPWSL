cmake_minimum_required(VERSION 3.10)
project(asset_viewer)

# Find Protobuf package
find_package(Protobuf REQUIRED)

# Include directories for Protobuf
include_directories(${Protobuf_INCLUDE_DIRS})

# Update the path to where the generated .pb.cc and .pb.h files are located
set(PROTO_SRC_DIR "/mnt/c/Users/Owner/OOP/asset_viewer_cpp/src/demo/src/main/proto") # Replace with the actual path

# Add the generated .pb.cc file and main.cpp to the executable
add_executable(asset_viewer
    src/main.cpp
    ${PROTO_SRC_DIR}/financial_portfolio.pb.cc
)

# Link Protobuf library
target_link_libraries(asset_viewer ${Protobuf_LIBRARIES})