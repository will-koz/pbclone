#!/usr/bin/python3

# Program that takes a list of ordered pairs of files and names and prints Java code created by
# them

# 0th argument - this command
# 1st argument - output file (w/o `.java` at the end)
# 2nd argument - package name;

import sys

# Remember that this is run from the directory above (don't add `../` before everything)
files = [
	("client/index.css", "index_css"),
	("client/index.html", "index_html"),
	("client/index.js", "index_js")
]

print("// Automatically generated in utils/strings.py\n\npackage " + sys.argv[2] + ";\n")
print("public class " + sys.argv[1] + " {")

for i in files:
	print("\tpublic static String " + i[1] + " = \"", end = "")
	with open(i[0]) as file:
		for line in file:
			print(line.encode("unicode_escape").decode("utf-8").replace("\"", "\\\""), end = "")
	print("\";");

print("}")
