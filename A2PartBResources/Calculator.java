/*************************************************************************
 * > File Name: Calculator.java > Author: Derek Zhu > E-mail:
 * 1and1get2@gmail.com > Created Time: Wed 19 Sep 2012 13:22:06 NZST >
 ************************************************************************/

class Calculator {
	public static void main(String[] args) {
		System.out.println("A simple calculator by qzhu496.\nEvaluating ...");
		Program p = new Program();
		p.start(args[0]);
	}
}

class Program {
	// final String ALL_VALID = "0123456789+-/*()[]{}";
	final String PARENTHESES = "()[]{}";
	final String OPERATORS = "+-*/";
	final String DIGITS = "1234567890";
	final String ALL_VALID = PARENTHESES + OPERATORS + DIGITS;

	public void start(String infix) {
		System.out.println("Infix Expression: " + infix);
		if (checkSyntax(infix))
			System.out.println("Result: " + getResult(trans(infix)));
	}

	private boolean checkSyntax(String infix) {

		StackReferenceBased stack = new StackReferenceBased();
		boolean balancedSoFar = true;
		String status = null;

		for (int i = 0; i < infix.length(); i++) {
			// check if all put valid
			char inputChar = infix.charAt(i);
			if (ALL_VALID.indexOf(inputChar) != -1) { // valid
				if (infix.indexOf("()") != -1){
					status = "expression contain empty bracket \'()\'";
					balancedSoFar = false;
					break;
				}
				if (infix.charAt(0) == ')'){
					status = "expression begin with /')/'";
					balancedSoFar = false;
					break;
				}
				if (OPERATORS.indexOf(infix.charAt(0)) != -1){
					status = "expression begin with operator " + infix.charAt(0);
					balancedSoFar = false;
					break;
				}
				if (DIGITS.indexOf(infix.charAt(i)) != -1 && i+1 < infix.length() && infix.charAt(i+1) == '('){
					status = "digit followed by \'(\'";
					balancedSoFar = false;
					break;
				}
				if (OPERATORS.indexOf(infix.charAt(infix.length()-1)) != -1){
					status = "expression end with operator " + infix.charAt(0);
					balancedSoFar = false;
					break;
				}
				if (infix.charAt(i) == '/' && i+1 < infix.length() && infix.charAt(i+1) == 0){
					status = "division by zero";
					balancedSoFar = false;
					break;
				}
				if (infix.charAt(infix.length()-1) == '('){
					status = "expression end with /'(/'";
					balancedSoFar = false;
					break;
				}
				if (inputChar == '(' || inputChar == '{' || inputChar == '[') {
					if (i+1 < infix.length() && OPERATORS.indexOf(infix.charAt(i+1)) != -1){
						status = "\'(\' followed by operator";
						balancedSoFar = false;
						break;
					}
					// left brackets
					stack.push(inputChar);
				} else if (inputChar == ')' || inputChar == '}'
						|| inputChar == ']') {
					// right brackets
					if (i+1 < infix.length() && DIGITS.indexOf(infix.charAt(i+1)) != -1){
						status = "\')\' followed by digit";
						balancedSoFar = false;
						break;
					}
					if (stack.isEmpty()) {
						balancedSoFar = false;
						status = "No matching open brace";
						break;
					} else {
						char charFromStack = stack.peek().toString().charAt(0);
						if (match(charFromStack, inputChar)) {
							stack.pop();
							// System.out.println("match");
						} else {
							balancedSoFar = false;
							status = "No matching open brace";
							break;
						}
					}
				} else if(OPERATORS.indexOf(infix.charAt(i)) != -1 && i < infix.length()-1 && OPERATORS.indexOf(infix.charAt(i+1)) != -1){
					// if this and next char are both  operator
					status = "two consecutive operators";
					balancedSoFar = false;
					break;
				} else if(OPERATORS.indexOf(infix.charAt(i)) != -1 && i < infix.length()-1 && infix.charAt(i+1) == ')'){
					// if this char is operator and next one is
					status = "operator followed by \')\'";
					balancedSoFar = false;
					break;
				} else {
					// ignore any cases else
					continue;
				}
				// System.out.println("stack: " + stack.toStringForDebugging());
			} else { // in valid
				balancedSoFar = false;
				status = "invalid input: \"" + inputChar + "\"";
				break;
			}
			// System.out.println("stack.isEmpty(): " + stack.isEmpty());
		}
		if (stack.isEmpty()) {
			// System.out.println("match");
		} else {
			balancedSoFar = false;
			if (status == null)
				status = "too many open braces";
		}
		if (status != null)
			System.out.println(status);
		return balancedSoFar;
	}

	private boolean match(char oldChar, char newChar) {
		// newChar: new input char; oldChar: old char pop from stack
		if ((oldChar == '(' && newChar == ')')
				|| (oldChar == '[' && newChar == ']')
				|| (oldChar == '{' && newChar == '}')) {
			// System.out.println("match: " + oldChar + " " + newChar);
			return true;
		}
		return false;
	}

	/*
	 * Converting Infix Expressions to Equivalent Postfix Expressions 
	 * • operand – append it to the output string postfixExp 
	 * • “(“ – push onto the stack 
	 * • operator 
	 * 		– If the stack is empty, push the operator onto the stack 
	 * 		– If the stack is not empty 
	 * 			• pop the operators of greater or equal precedence from the 
	 * 			stack and append them to postfixExp. Stop when encounter either
	 * 			a “(“ or an operator of lower precedence or when the stack is empty. 
	 * 			• push the new operator onto the stack. • “)” – pop the operators 
	 * 			off the stack and append them to the end of postfixExp until encounter
	 * 			the match “(“ 
	 * • end of the string – append the remaining contents of the stack to postfixExp 
	 * (Page 378 - Pseudo algorithm)
	 */
	private String trans(String infix) {
		String postfixExp = "";
		StackReferenceBased stack = new StackReferenceBased();
		
		for (int i = 0; i < infix.length(); i++) {
			// if it's a number, and if next one is alse a number
			while(i < infix.length() && DIGITS.indexOf(infix.charAt(i)) != -1){
				// TODO: not sure should i put space here or not
				postfixExp += infix.charAt(i);
				i++;
			}
			// deal with space
			if (i != 0 && DIGITS.indexOf(infix.charAt(i-1)) != -1){
				postfixExp += " ";
			}
			if (i == infix.length()) break;
			if (infix.charAt(i) == '('){	// || [ || {
				stack.push('(');
			} else if (OPERATORS.indexOf(infix.charAt(i)) != -1){
				// operators
				if(stack.isEmpty()){
					// if stack is empty
					stack.push(infix.charAt(i));
				} else {
					// if stack is not empty
					//		* pop the operators of greater or equal precedence from the 
					//		* stack and append them to postfixExp. Stop when encounter either
					//		* a “(“ or an operator of lower precedence or when the stack is empty.
					char thisOperator = infix.charAt(i);
					char operatorFromStack = stack.peek().toString().charAt(0);
					//boolean t ;	// t for debug
					// TODO: double check about greaterOrEqualPreOperator
					// try: 12-(23+34*45)/56 : 12 23 34 45 * + 56 / - 
					//		(8+12)*3/(14-9) : 8 12 + 3 * 14 9 - /
					while (!stack.isEmpty() && operatorFromStack != '(' && greaterOrEqualPreOperator(thisOperator, operatorFromStack)){
						operatorFromStack = stack.peek().toString().charAt(0);
						postfixExp += stack.pop().toString().charAt(0) + " ";
						if (!stack.isEmpty() && stack.peek().toString().charAt(0) == '('){
							if (!stack.isEmpty()) stack.pop();
							break;
						}
						// System.out.println("stack: " + stack.toStringForDebugging() + " postfix: " + postfixExp);
					}
/*					while(!(
							(operatorFromStack = stack.peek().toString().charAt(0)) == '('||
							!(t = greaterOrEqualPreOperator(thisOperator, operatorFromStack) )|| 
							stack.isEmpty()
							)){
						postfixExp += operatorFromStack + " ";
						System.out.println("stack: " + stack.toStringForDebugging() + " postfix: " + postfixExp);
					}*/
					stack.push(infix.charAt(i));
				}
			} else if(infix.charAt(i) == ')'){
				// “)” – pop the operators off the stack and append them to 
				// 		 the end of postfixExp until encounter the match “(“
				char temp;
				while(!(stack.isEmpty() || (temp = stack.pop().toString().charAt(0)) == '(')){
					postfixExp += temp + " ";
				}
			}
		}
		// end of the string
		while(!stack.isEmpty()){
			postfixExp += stack.pop().toString().charAt(0) + " ";
		} 
		System.out.println("potfix String: " + postfixExp);
		return postfixExp;
	}
	private boolean greaterOrEqualPreOperator(char ee, char er){
		// if er >= ee true else false
		// ee < er, ee = -+ er = */ : fasle
		return ((er == '+' || er == '-') && (ee == '*' || ee == '/')) ? false : true; 
	}
	private int getResult(String postfix){
		StackReferenceBased stack = new StackReferenceBased();
		String[] postfixArr = string2Array(postfix);
		
		for(String str:postfixArr){
			//System.out.print(str + " ");
			// if str is a operator
			if(OPERATORS.indexOf(str) == -1){
				stack.push(str);
			} else {
//				// if it is a number
				Object a1 = stack.pop();
				Object a2 = stack.pop();
				//System.out.println("a1: " + a1 + " a2: " + a2);
				int num1 = Integer.parseInt((String)a1);//(Integer)stack.pop();
				int num2 = Integer.parseInt((String)a2);
//				int num2 = Integer.parseInt((String)stack.pop());
				int res = compute(str, num1, num2);
				stack.push("" + res);
			}
			//System.out.println(stack.toStringForDebugging());
		}
		
		return Integer.parseInt((String) stack.pop());
	}
	private String[] string2Array(String postfix){
		String[] postfixArr = postfix.split(" ");
		//System.out.println(postfixArr);
		return postfixArr;
	}
	private int compute (String ch, int num2, int num1){

/*		switch(ch){
		case "+" : return num1 + num2; break;
		case "-" : return num1 - num2; break;
		case "*" : return num1 * num2; break;
		case "/" : return num1 / num2; break;
		}*/
		if (ch.equals("+")) return num1 + num2;
		if (ch.equals("-")) return num1 - num2;
		if (ch.equals("*")) return num1 * num2;
		if (ch.equals("/")) return num1 / num2;
		
		return 0;
	}
}