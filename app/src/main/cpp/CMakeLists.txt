cmake_minimum_required(VERSION 3.22.1)
project(hk_native_backend LANGUAGES CXX)

add_library(hk_native_backend SHARED native-lib.cpp)

find_library(log-lib log)

target_compile_features(hk_native_backend PRIVATE cxx_std_17)
target_link_libraries(hk_native_backend PRIVATE ${log-lib})
