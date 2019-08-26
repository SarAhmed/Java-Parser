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
* Get a Class Body<br/>
Method: getClassBody=
  * Take as an input order(zero-indexed) of the Class.
  * Return the Class's Body as a String.
```
p.getClassBody(0);
```

* Refactor/Transform Class Body<br/>
Method: refactorClassBody
  * Take as an input the order(zero-indexed) of the Class and the new body code.
  * Returns String containing the code after the transformation.
```
p.refactorClassBody(0, "//he new code" + p.getClassBody(0));
```
* Get All Constructor Declarations<br/>
Method: getClasses
  * Takes no input.
  * Returns ArrayList of all constructors declerations as Strings.
```
p.getConstructorDecleration();
```
* Get All Methods Declarations<br/>
Method: getMethodDeclerations
  * Takes no input.
  * Returns ArrayList of all Methods declerations as Strings.
```
p.getMethodDeclerations();
```
* Get a Method Body<br/>
Method: getMethodBody
  * Take as an input the order(zero-indexed) of the Method.
  * Return the Methods's Body as a String.
```
p.getMethodBody(0);
```

* Refactor/Transform Method Body<br/>
Method: refactorMethodBody
  * Take as an input order(zero-indexed) of the Method and the new body code.
  * Returns String containing the code after the transformation.
```
p.refactorMethodBody(0, "//the new code" + p.getMethodBody(0));
```
* Get the main Method Body<br/>
Method: getMainMethodBody
  * Take as an input the order(zero-indexed) of the Method.
  * Return the Methods's Body as a String.
```
p.getMainMethodBody(0);
```

* Refactor/Transform the main Method Body<br/>
Method: refactorMethodBody
  * Takes as an input the new body code.
  * Returns String containing the code after the transformation.
```
p.refactorMainMethodBody("//the new code" + p.getMainMethodBody(0));
```

* Get all Array Assignments (i.e. arr[index] = value;)<br/>
Method: getArrayAssignment
  * Takes no input.
  * Returns ArrayList of all Array assignments as Strings.
```
p.getArrayAssignment();
```
* Refactor/Transform an Array Assignment<br/>
Method: refactorArrayAssignment
  * Takes as an input the order(zero-indexed) of the array Assignment instruction and the new code to be add instead of the chosen array assignment.
  * Returns String containing the code after the transformation.
```
p.refactorArrayAssignment(0,p.getArrayAssignment().get(0)+"//the new code");
```

