#Plume Java Bytecode Assembler

Generates Java `.class` files from plain text class file descriptors (using an assembly-like language) or directly from Java code (like ASM).

### `.pjb` Snippet

    @ Add two ints
    .class org/isk/pjba/sample/Adder
      .method add(II)I
        iload_0   @ Push to the stack the local variable at index 0
        iload_1   @ Push to the stack the local variable at index 1
        iadd      @ Pop and add the two elements at the top of the stack, and push the result to the stack
        ireturn   @ Return the top of the stack
      .methodend
    .classend

## Setup
Java 8 and Maven need to be installed and accessible from a command line:

    $ java -version
    java version "1.8.0_66"
    Java(TM) SE Runtime Environment (build 1.8.0_66-b17)
    Java HotSpot(TM) 64-Bit Server VM (build 25.66-b17, mixed mode)

    $ mvn -version
    Apache Maven 3.3.3
    ...

Both must be added to your PATH, and JAVA_HOME and MAVEN_HOME environment variables must be set.

### Compile, Test and Generate the JavaDoc

  $ mvn clean install
  #...

Note: The `maven-javadoc-plugin` goal `aggregate` is attached to the phase `install`.
