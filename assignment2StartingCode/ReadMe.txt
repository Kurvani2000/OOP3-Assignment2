XML Validator Program
=====================

A Java-based XML syntax validator that checks for matching tags and proper XML structure.

Requirements
------------
- Java Development Kit (JDK) 8 or later
- Command line/terminal access

Installation
------------
1. Ensure you have Java installed:
   - Run `java -version` in your terminal to verify.
   - If not installed, download from https://adoptium.net/

2. Download the program files:
   - XMLValidator.java
   - MyArrayList.java
   - MyDLL.java
   - MyDLLNode.java
   - MyQueue.java
   - MyStack.java

3. Compile the program:
   - Place all files in the same directory
   - Open terminal in that directory
   - Run: `javac implementations/*.java`

Usage
-----
Basic command:
  java implementations.XMLValidator path/to/yourfile.xml

Examples:
1. Validate a single XML file:
   java implementations.XMLValidator test.xml

2. Handle different cases:
   - Valid XML:    Program will display "No errors found"
   - Invalid XML:  Program will list specific tag mismatches

Program Output:
- "Looking for XML syntax errors..."
- Either:
  - "No errors found." (success)
  - Specific error messages with line numbers (if available)

Error Types Detected:
1. Unmatched opening tags: <tag> without </tag>
2. Extra closing tags: </tag> without opening <tag>
3. Mismatched tags: <tag1>...</tag2>

Sample Test Files
-----------------
Create these to test the validator:

1. Valid XML (valid.xml):
   <root>
     <item>Content</item>
     <selfclosing/>
   </root>

2. Invalid XML (invalid.xml):
   <root>
     <item>Content
     <extra></item>
   </root>