#!/usr/bin/python3

# Program that takes a file and returns a Java class of questions

# 0th command - this command
# 1st command - output file (w/o `.java` at the end)
# 2nd command - package name

import sys

file = "questions.txt"

print("// Automatically generated in utils/questions.py\n\npackage " + sys.argv[2] + ";\n")
print("public class " + sys.argv[1] + " {\n")

print("public static String[][] questions = {", end = "")
with open(file) as f:
	add_to_line = "\n"
	while line := f.readline():
		print(add_to_line + "\t{ \"", end = "");
		print(line.strip(), end = "");
		print("\", \"", end = "")
		add_to_beginning = ""
		while (line := f.readline()).strip() != "":
			print(add_to_beginning + line.strip(), end = "")
			add_to_beginning = " "
		print("\" }", end = "")
		add_to_line = ",\n"

print("\n};\n\n}")
