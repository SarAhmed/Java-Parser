
# Java-Parser
A user friendly Java parser that can be used to 
* Get all import statements, class declarations, class’s body, method declarations, method’s body, variable declarations, variable assignments, etc.
* Refactor and transform Java code by replacing or adding a piece of code in the Java file.

## Installing
Download the parser Package and add it to your project.

## Features
first of all instatite the constructor
* using input File
```
File file = new File("BubbleSort.java");
parser p=new  parser(file);   
```
* using input String
```
String s = "public class BubbleSort {}";
parser p=new  parser(p);
```
* Add Import Statments<br/>
Method: addImportStatment<br/>
  * Takes as an input the import statements to be added.<br/>
  * Returns String containing the code after the addition.
```
String imports = "import java.io.BufferedReader;\r\n" +
                "import java.io.File;\r\n"+
                "import java.io.FileNotFoundException;\r\n" +
                "import java.io.FileReader;\r\n";
p.addImportStatment(imports);
```
* Get All Classes Declarations<br/>
Method: getClasses
 * Takes no input.
 * Returns ArrayList of all classes declerations as Strings.
```
p.getClasses();
```
* Get a Class Body
Method: getClassBody
 * Take as an input order(zero-indexed) of the Class.
 * Return the Class's Body as a String.
```
p.getClassBody(0);
```

* Refactor/Transform Class Body
Method: refactorClassBody
 * Take as an input order(zero-indexed) of the Class and the new body code.
 * Returns String containing the code after the transformation.
```
p.refactorClassBody(0, "int x=0;" + p.getClassBody(0));
```
