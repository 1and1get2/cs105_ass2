/*************************************************************************
    > File Name: Calculator.java
    > Author: Derek Zhu
    > E-mail: 1and1get2@gmail.com 
    > Created Time: Wed 19 Sep 2012 13:22:06 NZST
    > 
 ************************************************************************/



class Calculator{
	public static void main(String[] args){
		System.out.println("A simple calculator by qzhu496.\nEvaluating ...");
		Program p = new Program();
		p.start(args[0]);
	}
}

class Program{
	final String ALL_VALID = "0123456789+-/*()[]{}";
	final String PARENTHESES = "()";
	final String OPERATORS = "+_*/";
	
	
	public void start(String infix){
		System.out.println("Infix Expression: " + infix);
		if (checkSyntax(infix)) trans(infix);
	}
	
	private boolean checkSyntax(String infix){
		
		StackReferenceBased stack = new StackReferenceBased(); 
		boolean balancedSoFar = true;
		String status = null;
		
		for (int i = 0; i < infix.length(); i++){
			// check if all put valid
			char inputChar= infix.charAt(i);
			if(ALL_VALID.indexOf(inputChar)  != -1){	// valid
				if (inputChar == '(' || inputChar == '{' || inputChar == '['){
					// left brackets
					stack.push(inputChar);
				} else if (inputChar == ')' || inputChar == '}' || inputChar == ']'){
					// right brackets
					if (stack.isEmpty()){
						balancedSoFar = false;
						status = "No matching open brace";
						break;
					} else {
						char charFromStack = stack.peek().toString().charAt(0);
						if (match(charFromStack, inputChar)){
							stack.pop();
//							System.out.println("match");
						} else {
							balancedSoFar = false;
							status = "No matching open brace";
							break;
						}
					}
				} else {
					// ignore anything else
					continue;
				}
			} else {										// in valid
				balancedSoFar = false;
				status = "invalid input";
				break;
			}
			//System.out.println("stack.isEmpty(): " + stack.isEmpty());
		}
		if (stack.isEmpty()){
			//System.out.println("match");
		} else {
			balancedSoFar = false;
			status = "too many open braces";
		} 
		if (status != null) System.out.println(status);
		return balancedSoFar;
	}
	private boolean match(char oldChar, char newChar){
		// newChar: new input char; oldChar: old char pop from stack
		if ((oldChar == '(' && newChar == ')') || 
				(oldChar == '[' && newChar == ']') || 
				(oldChar == '{' && newChar == '}' )){
			//System.out.println("match: " + oldChar + " " + newChar);
			return true;
		}
		return false;
	}
	private void trans(String infix){
		
	}
}