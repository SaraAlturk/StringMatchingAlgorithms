# String Matching Algorithms

This repository contains implementations and analysis of the Brute Force and Horspool string matching algorithms in Java.

## Table of Contents

- [Introduction](#introduction)
- [Project Structure](#project-structure)
- [Algorithms Implemented](#algorithms-implemented)
- [Analysis](#analysis)

## Introduction

The project implements and compares the performance of the Brute Force and Horspool string matching algorithms.

It reads a specified number of lines from an input text file [Input Text File](StringMatching%20Algorithms/input.txt), generates random patterns, and searches for these patterns within the text using both algorithms.

The running times are measured and compared to analyze the efficiency in nanoseconds.

## Project Structure
The structure can be divided into the following key components:

### Main Class (`StringMatching`)

The main class serves as the entry point for executing all the processes involved in the project. It handles user inputs for determining the number of lines to be read from the text file, the length of the patterns, and the number of patterns to be generated. It also orchestrates the execution of the core algorithms and manages input/output operations.

### Input Handling

The project starts by reading data from an input file:  [Input Text File](StringMatching%20Algorithms/input.txt), storing the data in a `String` variable after converting it to lowercase. This step ensures consistency during pattern matching, as case sensitivity could lead to incorrect results. Efficient input handling is crucial for managing text data and preparing it for the pattern generation and matching phases.

### Pattern Generation

Patterns are generated randomly from the input text based on user-defined parameters, such as the pattern length and number of patterns. These patterns are then stored in an array and saved to an output file (`patterns.txt`). This modular approach allows for easy access and management of generated patterns, which are subsequently used for testing the efficiency of the algorithms.

### Algorithms

The project implements three key algorithms:

- **Brute Force String Matching Algorithm**: This algorithm performs a straightforward comparison of the pattern with each substring in the text until a match is found or all possible positions are exhausted.

- **Horspool's String Matching Algorithm**: Horspool's algorithm uses a precomputed shift table to skip sections of the text when mismatches occur, thereby optimizing the search process.

- **Shift Table Creation**: The shift table is constructed using the `createShiftTable` method, which uses a `HashMap` to determine how many characters can be skipped upon mismatch, thus reducing the overall number of comparisons.



## Algorithms Implemented
 1) Brute-Force string matching algorithm :
    
  ```pseudo

   /Implements brute-force string matching
    //Input: An array T [0..n − 1] of n characters representing a text and
    // an array P[0..m − 1] of m characters representing a pattern
    //Output: The index of the first character in the text that starts a
    // matching substring or −1 if the search is unsuccessful
    for i ←0 to n − m do
    j ←0
    while j <m and P[j ]= T [i + j ] do
    j ←j + 1
    if j = m return i
    return −1
 ```
   
2) Horspool’s string matching algorithm & its Shift Table:

  ```pseudo
ALGORITHM ShiftTable(P[0..m − 1])
    // Fills the shift table used by Horspool’s and Boyer-Moore algorithms
    // Input: Pattern P[0..m − 1] and an alphabet of possible characters
    // Output: Table[0..size − 1] indexed by the alphabet's characters and
    //         filled with shift sizes computed by formula
    for i ← 0 to size − 1 do
        Table[i] ← m
    for j ← 0 to m − 2 do
        Table[P[j]] ← m − 1 − j
    return Table
    
   ALGORITHM HorspoolMatching(P [0..m − 1], T [0..n − 1])
    //Input: Pattern P[0..m − 1] and text T [0..n − 1]
    //Output: The index of the left end of the first matching substring
    // or −1 if there are no matches
    ShiftTable(P [0..m − 1])          //generate Table of shifts
    i ←m − 1                                //position of the pattern’s right end
    while i ≤ n − 1 do
    k←0                            //number of matched characters
    while k ≤ m − 1 and P[m − 1− k]= T [i − k] do
    k←k + 1
    if k = m return i − m + 1
    else i ←i + Table[T [i]]
    return −1
  ```


## Results and Analysis

The results indicate that, in multiple test runs given our input file, **Horspool's algorithm frequently performed worse than the brute force approach**. 
Specifically, when reading larger numbers of lines or when using shorter patterns, the average search time for Horspool's approach tended to be higher compared to the brute force approach. For instance:

- **First Run**:
  - **Parameters**: 10 lines of text, 20-character patterns
  - **Brute Force Algorithm**: Average running time of **4,031,667 ns**
  - **Horspool Algorithm**: Average running time of **5,091,125 ns**

- **Second Run**:
  - **Parameters**: 5 lines of text, 20-character patterns
  - **Brute Force Algorithm**: Average running time of **4,197,250 ns**
  - **Horspool Algorithm**: Average running time of **3,211,542 ns** (performed better in this instance)

Horspool's algorithm requires the creation of a shift table, which introduces an additional preprocessing step. This overhead is more noticeable with smaller input sizes, as the preprocessing time is not effectively amortized over the search process.

The preprocessing using a `HashMap` and a `HashSet` added a layer of complexity that did not significantly benefit the search due to the characteristics of the randomly generated text. This made the Horspool algorithm less efficient compared to the brute force approach in our cases, as brute force does not involve any additional preprocessing.

Moreover, the text and patterns were randomly generated, which sometimes resulted in less favorable conditions for Horspool's optimizations. The randomness could lead to cases where most characters in the pattern appear frequently in the text, resulting in minimal skip values and making Horspool's advantage less apparent.

Additionally, the characteristics of the [Input Text File](StringMatching%20Algorithms/input.txt) played a significant role in determining the efficiency of the algorithms. The lack of structured and diverse data limited the performance benefits that could be gained from Horspool's shifting mechanism.

However, Horspool's algorithm can be highly efficient in larger, more structured datasets where its ability to skip large portions of text is advantageous. The results underscore the importance of understanding the context and characteristics of the input data when choosing an appropriate string matching algorithm.
