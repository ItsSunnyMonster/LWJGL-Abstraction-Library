# LWJGL-Abstraction-Library
 An abstraction library for LWJGL written in Kotlin
 It should also work for Java applications.
 
# Building
1. Run ```$ .\gradlew build```(Windows) or ```$ ./gradlew build```(Mac/Linux) in the root directory to build the entire project (including the library tester). The build should be found at ```LibraryTester/build/distributions```
2. To build the library into a fat jar to be linked to another project, run ```$ .\gradlew shadowJar```. The generated jar should be found at ```Library/build/libs/Library-x.x.x-all.jar```
3. This also generates a fat jar for the library tester. It should be at ```LibraryTester/build/libs/LibraryTester-x.x.x-all.jar```
4. Use the ```run``` task to run the tester
