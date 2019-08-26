
# Java-Parser
A user friendly Java parser that can be used to 
* Get all import statements, class instantiations, class’s body, method declarations, method’s body, variable declarations, variable assignments, etc.
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
`

```


