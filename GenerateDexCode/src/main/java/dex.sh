#!/bin/bash
rm -rf *.class *.dex *.txt
javac Hide.java
dx --dex --output hide.dex Hide.class
javac KeyGenerate.java
java KeyGenerate>result.txt
rm -rf *.class *.dex
