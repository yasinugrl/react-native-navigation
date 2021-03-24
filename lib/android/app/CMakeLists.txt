
cmake_minimum_required(VERSION 3.4.1)

add_library( # Specifies the name of the library.
             jsi_demo

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             src/main/cpp/demo/jsi_demo.cpp )

find_library( # Sets the name of the path variable.
        log-lib

        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)

target_link_libraries( # Specifies the target library.
        jsi_demo

        # Links the target library to the log library
        # included in the NDK.
        ${log-lib})
include_directories(src/main/cpp/demo/include/)
