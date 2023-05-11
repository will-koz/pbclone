#!/usr/bin/bash

# Script for generating a .jar file that can be used on any system, as well as a .txt file that
# contains all of the java code for this project (school project)

FILES=$(ls | grep .java)

tail $FILES --lines=+1 > final.txt
