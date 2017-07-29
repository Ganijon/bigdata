# Project: Comparative analysis of map-reduce algorithms computing relative frequency matrix

## Objective

In this project we will create algorithms to predict event probability after certain event happened.

Example: Cross-selling: People who bought “item one” have bought the following items : “item two”, “item three”, “item four”.

For the purpose of this project we assume that historical customer data is available in the following form.

Each record contains the product IDs of all the product bought by one customer.

Items bought by a customer, listed in the order she bought it: 

  B12 C31 D76 A12 B76 B12 D76 C31 A10 B12 D76      
  
Items bought by another customer, listed in the order she bought it

  C31 D76 B12 A12 C31 D76 B12 A12 D76 A12 D76 	  
…

Let the neighborhood of X, N(X) be set of all term after X and before the next X.

Example: If data block is [a b c a d e]
  N(a) = {b, c}
  N(b) = {c, a, d, e}
  N(c) = {a, d, e}
  N(a) ={d, e}
  N(d) = {e}
  N(e) = {}


## Implementation Plan

### Part 1. Set up a single node cluster and optionally an eclipse development environment to create and test programs.
    Install VMWare or VirtualBox hypervisor
    Install Cloudera Hadoop virtual machine
    Deploy and test WordCount.java

### Part 2. Implement Pairs algorithm to compute relative frequencies.
    Create Java classes (.java files)
    Show input, output and batch file to execute program at command line in Hadoop.

### Part 3. Implement Stripes algorithm to compute relative frequencies.
    Create Java classes (.java files)
    Show input, output and batch file to execute program at command line in Hadoop.

### Part 4. Implement Pairs in Mapper and Stripes in Reducer to compute relative frequencies.
    Create Java classes (.java files)
    Show input, output and batch file to execute program at command line in Hadoop.

### Part 5. Metrics Collection and Comparative Analysis
    CPU utilization
    RAM utilization
    Network utilization
    Input records emitted
    Output records emitted
