
import java.util.Scanner;

<<<<<<< Updated upstream:src/PostfixCalcEngine.java
public class PostfixCalcEngine {
=======
public class Postfix {
>>>>>>> Stashed changes:src/Postfix.java

	private StackAsList postFixStack;
	private StackAsList expression;
	final private Character[] operator = { '+', '-', '*', '/', '^' };
	private Scanner scanner = new Scanner(System.in);
	final private Character flagChar = '_';

<<<<<<< Updated upstream:src/PostfixCalcEngine.java
=======
	public static void main(String[] args) throws StackUnderflow, StackOverflow {
		// Postfix fix = new Postfix();
	}

>>>>>>> Stashed changes:src/Postfix.java
	public void run(String infix) throws StackUnderflow, StackOverflow {
		String post = infixToPostfix(infix);
		if (post == "Invalid Expression")
			System.out.println(post);
		else
			System.out.println(evaluate(post));
	}

	public int evaluate(String pfx) throws StackOverflow, StackUnderflow {
		expression = new StackAsList();
		boolean flag = false;
		boolean negative = false;

		// The first value of the term is negative if the first character is a minus and
		// second character is a digit
		/*
		 * if (firstIsNegative(pfx.substring(0, 2))) negative = true;
		 */

		for (int n = 0; n < pfx.length(); n++) {
			Character c = pfx.charAt(n);

			if (isNegative(pfx, n))
				negative = true;
			if (Character.isDigit(c) && flag) { // multi digit value
				int multdigit = ((int) popTop(expression)) * 10 + Character.getNumericValue(c);
				expression.push(multdigit);
				flag = true;
			} else if (negative && flag) {// If first value is negative do something
				String negate = "";
				// write the hole number (while -> multi digit) from the stack into a String
				if (!expression.isEmpty()) {
					negate += popTop(expression);
				}
				// get the integer value of it
				int neg = Integer.parseInt(negate);
				// /* negate the value and push a negative integer into the stack
				neg -= 2 * neg;
				expression.push(neg);
				// */
				// becomes false so the if-condition do not get executed again
				negative = false;
				// becomes false to mark end of the number
				flag = false;
			} else if (Character.isDigit(c) && !flag) {
				expression.push(Character.getNumericValue(c));
				flag = true;
			} else if (c == flagChar) { // end fo digits
				flag = false;

			} else if (isOperator(c)) {

				int lhs = 0;
				int rhs = 0;

				/*
				 * Important condition when handling negative values! e.g. -1+3 -> first char is
				 * a operator but at this moment the stack is empty so it would cause an error
				 * when setting the values for lhs and rhs. So it was a good choice I made
				 * during the last lab ^^
				 */
				if (!expression.isEmpty()) {
					try {
						rhs = (int) popTop(expression);
						lhs = (int) popTop(expression);

						switch (c) {
						case '+':
							expression.push((lhs + rhs));
							break;
						case '-':
							expression.push((lhs - rhs));
							break;
						case '*':
							expression.push((lhs * rhs));
							break;
						case '/':
							expression.push((rhs / lhs));
							break;
						case '^':
							double base = lhs;
							double expo = rhs;
							expression.push((int) (Math.pow(base, expo)));
							break;
						}
					} catch (Exception e) {
						expression.push(rhs);
					}
				}
			}
		}
		// System.out.println("Evaluation");
		return (int) popTop(expression);
	}

	public String infixToPostfix(String ifx) throws StackUnderflow, StackOverflow {
		postFixStack = new StackAsList();
		String postFix = "";
		ifx = ifx.replaceAll(" ", ""); // remove possible empty spaces from the String
		boolean open = false;
		boolean close = false;
		boolean flag = false;
		boolean negative = false;
		postFix += flagChar;

		for (int i = 0; i < ifx.length(); i++) {
			Character c = ifx.charAt(i);

			if (isNegative(ifx, i))
				negative = true;
			if (Character.isLetter(c)) {
				System.out.println("Enter a value for the variable " + c);
				String variable = scanner.nextLine().replaceAll(" ", "");
				c = variable.charAt(0);
				if (Character.isLetter(c))
					return "Invalid Expression";
			}

			// multi digit evaluation – at the end of a digit a '_' separator is set
			if ((!Character.isDigit(c)) && flag) {
				if (negative) { // if the value is negative do something
					/*
					 * At this moment the postFix contains the hole first number, e.g. postFix =
					 * 120. The postFixStack contains the '-' so we pop it of and add it to the
					 * front of postFix -> 120 = -120 To mark the end of the value we set the
					 * flagChar.
					 */
					for (int a = postFix.length() - 1; a >= 0; a--) {
						Character p = postFix.charAt(a);
						if (p == flagChar) {
							String s = postFix.substring((a + 1), postFix.length());
							postFix = postFix.substring(0, a + 1);
							postFix += popTop(postFixStack) + s + flagChar;
							a = -1;
						}
					}
					// postFix = popTop(postFixStack) + postFix + flagChar;
					// becomes false so the if-condition do not get executed again
					negative = false;
				} else {
					if (!(postFix.charAt(postFix.length() - 1) == flagChar))
						postFix += flagChar;
					flag = false;
				}
			} else if (Character.isDigit(c)) {
				postFix += c;
				flag = true;
			}

			if (c.equals('(')) {
				postFixStack.push(c);
				open = true;
			}

			if (c == ')') {
				close = true;
				while (!postFixStack.isEmpty() && !postFixStack.top().equals('(')) {
					postFix += popTop(postFixStack);
					postFix += flagChar;
				}
				if (!postFixStack.isEmpty() && !postFixStack.top().equals('(') || !open)
					return "Invalid Expression";
				else
					postFixStack.pop();
			}

			if (isOperator(c)) {
				while (!postFixStack.isEmpty() && checkOperator(c) <= checkOperator((char) postFixStack.top())) {
					if (postFixStack.top().equals('('))
						return "Invalid Expression";

					postFix += popTop(postFixStack);
					postFix += flagChar;
				}
				postFixStack.push(c);
			}

		}
		while (!postFixStack.isEmpty()) {
			postFix += popTop(postFixStack);
		}
		if (open != close)
			return "Invalid Expression";
		/*
		 * System.out.println("Postfix"); System.out.println("> " + postFix);
		 */
		return postFix;
	}

	private boolean isNegative(String s, int pointer) {

		/*
		 * If the previous checked character is a '-' and the current checked character
		 * is a digit return true.
		 * 
		 * @param pointer points at the char that gets checked in the for loop which
		 * called this method
		 * 
		 * @param s contains the term from the method that should be check for negative
		 * values at the current position of the for-loop
		 * 
		 * @return true when value is negative, else false
		 */
		/*
		 *  can not check if pointer is >= 2 because then negative digit at first
		 *  value are not tested but if we just test the current and previous
		 *  character it could happen that (10-6) is handled as 10 and -6 because
		 *  of the brackets.
		 */
		if (pointer >= 1) {
			Character pp = Character.MIN_VALUE;
			/*
			 * If it works we have a digit infront of the operator
			 * which means we a regular expression and not a negative number.
			 * If a exception get thrown we ignore it and just compare 
			 * if there is an operator and a digit.
			 */
			try {
				pp = s.charAt(pointer-2);
			}catch (Exception e) {
				
			}
			Character p = s.charAt((pointer - 1));
			Character c = s.charAt(pointer);
			if (checkOperator(p) == (int) '-' && Character.isDigit(pp)) {
				return false;
			} else if(checkOperator(p) == (int) '-' && Character.isDigit(c)){
				return true;
			}else {
				return false;
			}
		} else 
			return false;
	}

	private int checkOperator(Character c) {
		/*
		 * @AlexeJ: is this a precedence check?
		 * 
		 * @Tony: yes
		 */

		for (int i = 0; i < operator.length; i++)
			if (c == operator[i]) {
				switch (c) {
				case '+':
					return (int) '+';

				case '-':
					return (int) '-';

				case '*':
					return ((int) '*' + 6);// make the dec value of '*' greater than '+' or '-'

				case '/':
					return (int) '/';

				case '^':
					return (int) '^';
				}
			}
		return -1;

	}

	private boolean isOperator(char token) {
		if (token == '+' || token == '-' || token == '*' || token == '/' || token == '^') {
			return true;
		} else
			return false;
	}

	private Object popTop(StackAsList stack) throws StackUnderflow {
		Object o = stack.top();
		stack.pop();
		return o;
	}

	private String readInfix() throws StackOverflow, StackUnderflow {
		scanner = new Scanner(System.in);
		boolean finished = false;
		System.out.println("Enter an infix expression.");
		System.out.print("> ");
		String ifx = scanner.nextLine().replaceAll(" ", "");

		while (!finished) {
			if (ifx.equals("") || ifx == null) {
				System.out.println(
						"The infix expression you have entered is unvalid. You should not use any letters. Enter a new one. ");
				System.out.print("> ");
				ifx = scanner.nextLine().replaceAll(" ", "");
			} else
				finished = true;
		}
		return ifx;
	}
}
