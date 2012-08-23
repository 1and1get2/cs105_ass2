public class StackReferenceBased 
                  implements StackInterface {
  private Node top;

  public StackReferenceBased() {
    top = null; 
  }  // end default constructor
  
  public boolean isEmpty() {
    return top ==  null;
  }  // end isEmpty

  public void push(Object newItem) {
    top = new Node(newItem, top);
  }  // end push
  
  public Object pop() throws StackException {
    if (!isEmpty()) {
      Node temp = top;
      top = top.getNext();
      return temp.getItem();
    }
    else {
      throw new StackException("StackException on " +
                               "pop: stack empty");
    }  // end if
  }  // end pop
  
  public void popAll() {
    top = null;
  }  // end popAll
  
  public Object peek() throws StackException {
    if (!isEmpty()) {
      return top.getItem();
    }
    else {
      throw new StackException("StackException on " +
                               "peek: stack empty");
    }  // end if
  }  // end peek
					  
  // for debugging purpose only
  public String toStringForDebugging() {
	if (top ==  null)
		return "empty";
	
	String info = "top ";
	Node currentN = top;
	while (currentN != null) {
		info = info + currentN.getItem() + " ";
		currentN = currentN.getNext();
	}
	return info + " bottom";
  }
}  // end StackReferenceBased
