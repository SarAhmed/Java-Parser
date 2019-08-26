package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class parser {
	static String fileName = "log.txt";
	static PrintWriter outputStream;

	static final private String START_OF_LINE = "(?<=\r\n)";
	static final private String SPECIAL_STRING = "●•·";
	static final private String STRING = "(\".*\")";
	static final private String COMMENT = "((/\\*([^*]|[\\r\\n]|(\\*+([^*/]|[\\r\\n])))*\\*+/)|(//.*))";
	static final private String CLASS_ACCESS_MODIFIERS = "(public|abstract)";
	static final private String CONSTRUCTORS_ACCESS_MODIFIERS = "(public|private|protected)";
	static final private String ATTRIBUTES_AND_METHODS_ACCESS_MODIFIERS = "(public|private|protected|static|final|abstract|transient|synchronized|volatile)";
	static final private String PRIMITIVE_DATA_TYPES_R = "(byte|short|int|long|float|double|boolean|char|void)";
	static final private String NUMBER = "(\\d+)";
	static final private String IGNORE_SPACES = "([\\s\r\n]*)";
	static final private String LINE_TERMINATORS = "("
			+ "(:|;|\\)|\\(|\\{|\\}|,|((\\+|-|/|\\*|\\!|=|>|<)?)=|\\++|-+|/|\\*|\\!|=|>|<)" + IGNORE_SPACES + ")";
	static final private String ATLEAST_ONE_SPACE = "([\\s\r\n]+)";
	static final private String PACKAGE = "(package" + ATLEAST_ONE_SPACE + ".+" + IGNORE_SPACES + ";)";
	static final private String NOT_A_SPACE = "(([^\\s]|\r\n)+?)";
	static final private String DATA_STRUCTURES_SYMB = "(<|>|\\[|\\])";
	static final private String VARIABLE_NAME = "([a-zA-Z_]\\w*)";
	static final private String PRIMITIVE_DATA_TYPES_D = "(" + PRIMITIVE_DATA_TYPES_R + ATLEAST_ONE_SPACE
			+ VARIABLE_NAME + ")";
	static final private String OBJECT_D = "(" + VARIABLE_NAME + ATLEAST_ONE_SPACE + VARIABLE_NAME + ")";
	static final private String OBJECT_R = VARIABLE_NAME;
	static final private String GENERAL_DATA_STRUCTURE_R = "(" + OBJECT_R + IGNORE_SPACES + DATA_STRUCTURES_SYMB
			+ IGNORE_SPACES + "(" + IGNORE_SPACES + OBJECT_R + "*" + IGNORE_SPACES + DATA_STRUCTURES_SYMB
			+ IGNORE_SPACES + ")+" + ")";
	static final private String DATA_TYPE_R = "(" + PRIMITIVE_DATA_TYPES_R + "|" + OBJECT_R + "|"
			+ GENERAL_DATA_STRUCTURE_R + ")";
	static final private String ARRAY_R = "(" + DATA_TYPE_R + IGNORE_SPACES + "(\\[" + IGNORE_SPACES + "\\])+" + ")";
	static final private String STACK_R = "(" + "Stack" + IGNORE_SPACES + "<" + IGNORE_SPACES + DATA_TYPE_R
			+ IGNORE_SPACES + ">" + ")";
	static final private String QUEUE_R = "(" + "Queue" + IGNORE_SPACES + "<" + IGNORE_SPACES + DATA_TYPE_R
			+ IGNORE_SPACES + ">" + ")";
	static final private String LINKEDLIST_R = "(" + "LinkedList" + IGNORE_SPACES + "<" + IGNORE_SPACES + DATA_TYPE_R
			+ IGNORE_SPACES + ">" + ")";

	static final private String GENERAL_DATA_STRUCTURE_D = "(" + "(" + ATTRIBUTES_AND_METHODS_ACCESS_MODIFIERS
			+ ATLEAST_ONE_SPACE + ")*" + "(" + "(" + "(" + GENERAL_DATA_STRUCTURE_R + "|" + OBJECT_R + ")"
			+ IGNORE_SPACES + VARIABLE_NAME + IGNORE_SPACES + "(\\[" + IGNORE_SPACES + "\\])+" + ")" + ")" + "|" + "("
			+ GENERAL_DATA_STRUCTURE_R + IGNORE_SPACES + VARIABLE_NAME + ")" + ")";

	static final private String DATA_STRUCTURES_R = "(" + ARRAY_R + "|" + STACK_R + "|" + LINKEDLIST_R + "|" + QUEUE_R
			+ "|" + GENERAL_DATA_STRUCTURE_R + ")";

	static final private String ARRAY_D = "(" + "(" + DATA_TYPE_R + IGNORE_SPACES + VARIABLE_NAME + IGNORE_SPACES
			+ "(\\[" + IGNORE_SPACES + "\\])+" + ")" +

			"|" + "(" + ARRAY_R + IGNORE_SPACES + VARIABLE_NAME + ")" + ")";
	static final private String STACK_D = "(" + "(" + ATTRIBUTES_AND_METHODS_ACCESS_MODIFIERS + ATLEAST_ONE_SPACE + ")*"
			+ STACK_R + IGNORE_SPACES + VARIABLE_NAME + ")";
	static final private String QUEUE_D = "(" + "(" + ATTRIBUTES_AND_METHODS_ACCESS_MODIFIERS + ATLEAST_ONE_SPACE + ")*"
			+ QUEUE_R + IGNORE_SPACES + VARIABLE_NAME + ")";
	static final private String LINKEDLIST_D = "(" + "(" + ATTRIBUTES_AND_METHODS_ACCESS_MODIFIERS + ATLEAST_ONE_SPACE
			+ ")*" + LINKEDLIST_R + IGNORE_SPACES + VARIABLE_NAME + ")";

	static final private String DATA_STRUCTURES_D = "(" + ARRAY_D + "|" + STACK_D + "|" + LINKEDLIST_D + "|" + QUEUE_D
			+ "|" + GENERAL_DATA_STRUCTURE_D + ")";

	static final private String DATA_TYPE_D = "(" + "(" + PRIMITIVE_DATA_TYPES_D + "|" + DATA_STRUCTURES_D + "|"
			+ OBJECT_D + ")" + ")";
	static final private String METHOD_D = "(" + "(" + ATTRIBUTES_AND_METHODS_ACCESS_MODIFIERS + ATLEAST_ONE_SPACE
			+ ")*" + IGNORE_SPACES + DATA_TYPE_D + IGNORE_SPACES + "\\(" + "(" + "(" + IGNORE_SPACES + DATA_TYPE_D
			+ IGNORE_SPACES + "," + ")*" + IGNORE_SPACES + DATA_TYPE_D + IGNORE_SPACES + ")*" + "\\)" + IGNORE_SPACES
			+ "\\{" + ")";
	static final private String ARRAY_A = "(" + VARIABLE_NAME + IGNORE_SPACES + "\\[" + IGNORE_SPACES + NOT_A_SPACE+ IGNORE_SPACES + "\\]" + IGNORE_SPACES + "=" + IGNORE_SPACES  +NOT_A_SPACE+ IGNORE_SPACES
			+ ";" + "){1}?";
	static final private String CLASS = "(" + "(" + CLASS_ACCESS_MODIFIERS + ATLEAST_ONE_SPACE + ")*" + "class"
			+ ATLEAST_ONE_SPACE + VARIABLE_NAME + IGNORE_SPACES + "\\{" + ")";
	static final private String IMPORT = "(" + "import" + ATLEAST_ONE_SPACE + ".+" + ";" + ")";
	static final private String CONSTRUCTOR = "(" + "(" + CONSTRUCTORS_ACCESS_MODIFIERS + ATLEAST_ONE_SPACE + ")*"
			+ VARIABLE_NAME + IGNORE_SPACES + "\\(" + "(" + "(" + IGNORE_SPACES + DATA_TYPE_D + IGNORE_SPACES + ","
			+ ")*" + IGNORE_SPACES + DATA_TYPE_D + IGNORE_SPACES + ")*" + "\\)" + IGNORE_SPACES + ")";
	static final private String mainMethod = "public" + ATLEAST_ONE_SPACE + "static" + ATLEAST_ONE_SPACE + "void"
			+ ATLEAST_ONE_SPACE + "main" + IGNORE_SPACES + "\\(" + IGNORE_SPACES + "String" + IGNORE_SPACES + "\\["
			+ IGNORE_SPACES + "\\]" + IGNORE_SPACES + VARIABLE_NAME + IGNORE_SPACES + "\\)" + IGNORE_SPACES + "\\{"
			+ "|" + "public" + ATLEAST_ONE_SPACE + "static" + ATLEAST_ONE_SPACE + "void" + ATLEAST_ONE_SPACE + "main"
			+ IGNORE_SPACES + "\\(" + IGNORE_SPACES + "String" + ATLEAST_ONE_SPACE + VARIABLE_NAME + IGNORE_SPACES
			+ "\\[" + IGNORE_SPACES + "\\]" + IGNORE_SPACES + "\\)" + IGNORE_SPACES + "\\{";

	public static void main(String[] args) throws IOException {

		File file = new File("C:\\Users\\SU\\Documents\\Projects\\internship\\task6\\BubbleSort.java");

		String x = readFile(file);
		x = formatCode(x);
//
//		System.out.println("\r\n".replaceAll(NOT_A_SPACE,"))))"));
//		System.out.println(getArrayAssignment(x));

	}

	public static String formatCode(String s) {

		ArrayList<String> lineTerminators = new ArrayList<String>();
		ArrayList<String> Strings = new ArrayList<String>();
		s = s.replaceAll(COMMENT + IGNORE_SPACES, " ");
		s = s.replaceFirst("^" + ATLEAST_ONE_SPACE, "");
		s = "\r\n" + s;
		Pattern pattern = Pattern.compile(STRING);
		Matcher matcher = pattern.matcher(s);
		while (matcher.find()) {
			Strings.add(matcher.group());
		}
		s = s.replaceAll(STRING, SPECIAL_STRING + "STRING" + SPECIAL_STRING);
		pattern = Pattern.compile(LINE_TERMINATORS);
		matcher = pattern.matcher(s);
		while (matcher.find()) {
			lineTerminators.add(matcher.group());
		}
		String lines[] = s.split(LINE_TERMINATORS);

		String z = "";
		int k = 0;
		for (int i = 0; i < Math.min(lines.length, lineTerminators.size()); i++, k++) {
			z += lines[i] + "\r\n" + lineTerminators.get(i) + "\r\n";

		}
		for (int m = k; m < lineTerminators.size(); m++) {
			z += (lineTerminators.get(m)) + "\r\n";
		}
		for (int m = k; m < lines.length; m++) {
			z += (lines[m]);
		}
		for (int i = 0; i < Strings.size(); i++) {
			z = z.replaceFirst(SPECIAL_STRING + "STRING" + SPECIAL_STRING, Strings.get(i));
		}
		return z;

	}

	public static String readFile(File file) {
		BufferedReader br;
		String x = "\r\n";

		try {
			br = new BufferedReader(new FileReader(file));

			String st;
			while ((st = br.readLine()) != null) {
				x += st + "\r\n";

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return x;

	}

	public static String addImportStatment(String str, String statment) {
		Pattern pattern = Pattern.compile(PACKAGE);
		Matcher matcher = pattern.matcher(str);
		String r = "";
		if (matcher.find()) {
			r = str.replaceFirst(PACKAGE, matcher.group() + "\r\n" + statment);
		} else {
			r = statment + str;
		}
		r = formatCode(r);
		return r;
	}

	public static ArrayList<String> getMethodDeclerations(String str) {
		Pattern pattern = Pattern.compile(START_OF_LINE + METHOD_D);
		Matcher matcher = pattern.matcher(str);
		ArrayList<String> r = new ArrayList<String>();
		while (matcher.find()) {
			r.add(matcher.group());
		}
		return r;
	}

	public static ArrayList<String> getArrayAssignment(String str) {
		Pattern pattern = Pattern.compile(START_OF_LINE + ARRAY_A);
		Matcher matcher = pattern.matcher(str);
		ArrayList<String> r = new ArrayList<String>();
		while (matcher.find()) {
			r.add(matcher.group());
		}
		return r;
	}

	public static ArrayList<String> getConstructorDecleration(String str) {
		Pattern pattern = Pattern.compile(START_OF_LINE + ARRAY_A);
		Matcher matcher = pattern.matcher(str);
		ArrayList<String> r = new ArrayList<String>();
		while (matcher.find()) {
			r.add(matcher.group());
		}
		return r;
	}

	public static String replaceNth(String str, String regex, String replacement, int n) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		ArrayList<String> replaced = new ArrayList<String>();
		while (matcher.find()) {
			n--;

			if (n == 0)
				break;
			replaced.add(matcher.group());
			str = str.replaceFirst(regex, SPECIAL_STRING + "REPLACE" + SPECIAL_STRING);
		}

		if (n == 0) {
			str = str.replaceFirst(regex, replacement);
		}
		for (String x : replaced) {
			str = str.replaceFirst(SPECIAL_STRING + "REPLACE" + SPECIAL_STRING, x);
		}

		return str;
	}

	public static String getNth(String str, String regex, int n) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		while (matcher.find()) {
			n--;

			if (n == 0) {
				return matcher.group();
			}
			;

		}

		return null;
	}

	public static ArrayList<String> getClasses(String str) {
		Pattern pattern = Pattern.compile(START_OF_LINE + CLASS);
		Matcher matcher = pattern.matcher(str);
		ArrayList<String> r = new ArrayList<String>();
		while (matcher.find()) {
			r.add(matcher.group());
		}
		return r;
	}

	public static String getMethodBody(String str, int methodNumber) {
		String x = getNth(str, METHOD_D, methodNumber);
		int ind = str.indexOf(x) + x.length();
		x = str.substring(ind);
		Pattern pattern = Pattern.compile(START_OF_LINE + "(\\{|\\})");
		Matcher matcher = pattern.matcher(x);
		int open = 1;
		while (matcher.find()) {
			if (matcher.group().equals("{")) {
				open++;
			} else {
				open--;
			}
			if (open == 0) {
				x = x.substring(0, matcher.start());
				break;
			}
		}

		return x;

	}

	public static String refactorMethodBody(String str, int methodNumber, String r) {
		String x = getNth(str, METHOD_D, methodNumber);
		int ind = str.indexOf(x) + x.length();
		x = str.substring(0, ind);
		str = str.substring(ind);
		r = formatCode(r);
		Pattern pattern = Pattern.compile(START_OF_LINE + "(\\{|\\})");
		Matcher matcher = pattern.matcher(str);
		int open = 1;
		while (matcher.find()) {
			if (matcher.group().equals("{")) {
				open++;
			} else {
				open--;
			}
			if (open == 0) {
				x += "\r\n" + r + "\r\n" + str.substring(matcher.start());
				break;
			}
		}

		return x;

	}

	public static String getClassBody(String str, int classNumber) {
		String x = getNth(str, CLASS, classNumber);
		int ind = str.indexOf(x) + x.length();
		x = str.substring(ind);
		Pattern pattern = Pattern.compile(START_OF_LINE + "(\\{|\\})");
		Matcher matcher = pattern.matcher(x);
		int open = 1;
		while (matcher.find()) {
			if (matcher.group().equals("{")) {
				open++;
			} else {
				open--;
			}
			if (open == 0) {
				x = x.substring(0, matcher.start());
				break;
			}
		}

		return x;

	}

	public static String refactorClassBody(String str, int classNumber, String r) {
		String x = getNth(str, CLASS, classNumber);
		int ind = str.indexOf(x) + x.length();
		x = str.substring(0, ind);
		r = formatCode(r);
		str = str.substring(ind);
		Pattern pattern = Pattern.compile(START_OF_LINE + "(\\{|\\})");
		Matcher matcher = pattern.matcher(str);
		int open = 1;
		while (matcher.find()) {
			if (matcher.group().equals("{")) {
				open++;
			} else {
				open--;
			}
			if (open == 0) {
				x += "\r\n" + r + "\r\n" + str.substring(matcher.start());
				break;
			}
		}

		return x;

	}

	public static String getMainMethodBody(String str) {

		String x = getNth(str, mainMethod, 1);
		int ind = str.indexOf(x) + x.length();
		x = str.substring(ind);
		Pattern pattern = Pattern.compile(START_OF_LINE + "(\\{|\\})");
		Matcher matcher = pattern.matcher(x);
		int open = 1;
		while (matcher.find()) {
			if (matcher.group().equals("{")) {
				open++;
			} else {
				open--;
			}
			if (open == 0) {
				x = x.substring(0, matcher.start());
				break;
			}
		}

		return x;

	}

	public static String refactorMainMethodBody(String str, String r) {
		String x = getNth(str, mainMethod, 1);
		int ind = str.indexOf(x) + x.length();
		x = str.substring(0, ind);
		r = formatCode(r);
		str = str.substring(ind);
		Pattern pattern = Pattern.compile(START_OF_LINE + "(\\{|\\})");
		Matcher matcher = pattern.matcher(str);
		int open = 1;
		while (matcher.find()) {
			if (matcher.group().equals("{")) {
				open++;
			} else {
				open--;
			}
			if (open == 0) {
				x += "\r\n" + r + "\r\n" + str.substring(matcher.start());
				break;
			}
		}

		return x;

	}

	public static String refactorArrayAssignment(String str, int number, String r) {
		Pattern pattern = Pattern.compile(START_OF_LINE + ARRAY_A);
		Matcher matcher = pattern.matcher(str);
		int n = number;
		while (matcher.find()) {
			n--;

			if (n == 0) {
				System.out.println(matcher.group());
				return str.substring(0, matcher.start()) + "\r\n" + formatCode(r) + "\r\n"
						+ str.substring(matcher.end());
			}

		}
		return str;

	}

}
