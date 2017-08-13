#!/bin/sh
rm -rf build/
mkdir build
javac -classpath src/ -sourcepath src/ -d build/ src/InitializeGame.java
