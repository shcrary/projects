import java.util.*;

public class Proof {

	private TheoremSet thms;
	private ProofNode myRoot;
	private ProofNode curShowNode;
	private LineNumber myLineNumber;
	private boolean firstShow;
	private int canAssume;

	public Proof (TheoremSet theorems) {
		thms = theorems;
		myLineNumber = new LineNumber();
		firstShow = true;
		canAssume = 0;
	}

	public LineNumber nextLineNumber ( ){
		try{
			myLineNumber = myLineNumber.increment();
		} catch(IllegalLineException e){
			System.err.println("this line should never be printed");
		}
		canAssume++;
		//if(!firstShow){
		//	System.out.println(curShowNode.myLineNumber);
		//}
		return myLineNumber;
	}

	public void extendProof (String x) throws IllegalLineException, IllegalInferenceException {
		try{
			int firstSpace = x.indexOf(' ');
			if(firstSpace == -1){
				print(x);
			} else if(x.substring(0, firstSpace).equals("show")){
				numOfThings(2, x);
				curShowNode = show(x.substring(firstSpace+1), x);
			}else if(x.substring(0, firstSpace).equals("assume")){
				numOfThings(2, x);
				assume(x.substring(firstSpace+1), x);
			} else if(x.substring(0, firstSpace).equals("ic")){
				numOfThings(3, x);
				int secondSpace = x.indexOf(' ', firstSpace+1);
				ic(x.substring(firstSpace+1, secondSpace), x.substring(secondSpace+1), x);
			} else if(x.subSequence(0, firstSpace).equals("co")){
				numOfThings(4, x);
				int secondSpace = x.indexOf(' ', firstSpace+1);
				int thirdSpace = x.indexOf(' ', secondSpace+1);
				co(x.substring(firstSpace+1, secondSpace), x.substring(secondSpace+1, thirdSpace),
						x.substring(thirdSpace+1), x);
			} else if(x.subSequence(0, firstSpace).equals("mp")){
				numOfThings(4, x);
				int secondSpace = x.indexOf(' ', firstSpace+1);
				int thirdSpace = x.indexOf(' ', secondSpace+1);
				mp(x.substring(firstSpace+1, secondSpace), x.substring(secondSpace+1, thirdSpace),
						x.substring(thirdSpace+1), x);
			} else if(x.subSequence(0, firstSpace).equals("mt")){
				numOfThings(4, x);
				int secondSpace = x.indexOf(' ', firstSpace+1);
				int thirdSpace = x.indexOf(' ', secondSpace+1);
				mt(x.substring(firstSpace+1, secondSpace), x.substring(secondSpace+1, thirdSpace),
						x.substring(thirdSpace+1), x);
			} else if (x.subSequence(0, firstSpace).equals("repeat")) {
                numOfThings(3, x);
                int secondSpace = x.indexOf(' ', firstSpace + 1);
                repeat(x.substring(firstSpace + 1, secondSpace), x.substring(secondSpace + 1), x);
			} else{
				applyThm(x.substring(0, firstSpace+1), x.substring(firstSpace+1), x);
			}
			
		} catch(IllegalLineException e){
			myLineNumber.stayOnThisLineNumber();
			canAssume--;
			throw e;
		} catch(IllegalInferenceException e){
			myLineNumber.stayOnThisLineNumber();
			canAssume--;
			throw e;
		}
	}

	private static void numOfThings(int num, String x) throws IllegalLineException{
		int blankPos = 0;
		for(int i = 1; i < num; i++){
			blankPos = x.indexOf(' ', blankPos+1);
			if(blankPos == -1){
				throw new IllegalLineException("wrong number of things");
			}
		}
		blankPos = x.indexOf(' ', blankPos+1);
		if(blankPos != -1){
			throw new IllegalLineException("wrong number of things");
		}
	}

	public void print(String x) throws IllegalLineException{
		if(!x.equals("print")){
			throw new IllegalLineException("must be print or take on more arguments");
		}
		myLineNumber.stayOnThisLineNumber();
		canAssume--;
		System.out.println();
		print(myRoot);
	}

	private static void print(ProofNode p){
		if(p == null){
			return;
		}
		System.out.println(p.myLineNumber + "\t" + p.myString);
		for(int i = 0; i < p.myChildren.size(); i++){
			ProofNode child = p.myChildren.get(i);
			print(child);
		}
	}
	
	public ProofNode show(String expr, String x) throws IllegalLineException{
		canAssume = 0;
		Expression e = new Expression(expr);
		if(firstShow){
			firstShow = false;
			myRoot = new ProofNode(myLineNumber, e, null, x);
			return myRoot;
		}
		curShowNode.addChild(myLineNumber, e, x);
		int lastIndex = curShowNode.myChildren.size()-1;
		myLineNumber = myLineNumber.nesting(false);
		return curShowNode.myChildren.get(lastIndex);
	}

	// if show ~p, can assume p or ~~p
	public void assume(String expr, String x) throws IllegalLineException, IllegalInferenceException{
		if(firstShow){
			throw new IllegalLineException("must begin with show");
		}
		if(canAssume != 1){
			throw new IllegalInferenceException("cannot use assume");
		}
		Expression e = new Expression(expr);
		if(!curShowNode.myExpr.myLeft().equals(e) && !e.isNegation(curShowNode.myExpr)){
			throw new IllegalInferenceException("cannot use assume");
		}
		curShowNode.addChild(myLineNumber, e, x);
	}

	public void ic(String l1, String expr, String x) throws IllegalLineException, IllegalInferenceException{
		if(firstShow){
			throw new IllegalLineException("must begin with show");
		}
		Expression e = new Expression(expr);
		LineNumber ln1 = new LineNumber(l1);
		ProofNode p1 = search(ln1);
		// p.myExpr.print();
		// e.myRight().print();
		if(!e.myRight().equals(p1.myExpr)){
			throw new IllegalInferenceException("cannot use ic");
		}
		curShowNode.addChild(myLineNumber, e, x);
	}

	public void co(String l1, String l2, String expr, String x) throws IllegalLineException, IllegalInferenceException{
		if(firstShow){
			throw new IllegalLineException("must begin with show");
		}
		Expression e = new Expression(expr);
		LineNumber ln1 = new LineNumber(l1);
		LineNumber ln2 = new LineNumber(l2);
		ProofNode p1 = search(ln1);
		ProofNode p2 = search(ln2);
		if(!p1.myExpr.isNegation(p2.myExpr) && !p2.myExpr.isNegation(p1.myExpr)){
			throw new IllegalInferenceException("cannot use co");
		}
		//if(!p1.myExpr.equals(new Expression("~" + p2.myExpr.toString()))
		//		&& !p2.myExpr.equals(new Expression("~" + p1.myExpr.toString()))){
		//	throw new IllegalInferenceException("cannot use co");
		//}
		curShowNode.addChild(myLineNumber, e, x);
	}
	
	public void mp(String l1, String l2, String expr, String x)
            throws IllegalLineException, IllegalInferenceException {
		if(firstShow){
			throw new IllegalLineException("must begin with show");
		}
		Expression e = new Expression(expr);		
        LineNumber ln1 = new LineNumber(l1);
        LineNumber ln2 = new LineNumber(l2);
        ProofNode p1 = search(ln1);
        ProofNode p2 = search(ln2);
        boolean check1 = (p1.myExpr.myRight()).equals(e)
                && (p1.myExpr.myLeft()).equals(p2.myExpr);
        boolean check2 = (p2.myExpr.myRight()).equals(e)
                && (p2.myExpr.myLeft()).equals(p1.myExpr);
        if (!check1 && !check2) {
            throw new IllegalInferenceException("cannot use mp");
        }
        curShowNode.addChild(myLineNumber, e, x);
    }
	
	// negative sign issue
	public void mt(String l1, String l2, String expr, String x)
            throws IllegalLineException, IllegalInferenceException {
		if(firstShow){
			throw new IllegalLineException("must begin with show");
		}
		Expression e = new Expression(expr);		
        LineNumber ln1 = new LineNumber(l1);
        LineNumber ln2 = new LineNumber(l2);
        ProofNode p1 = search(ln1);
        ProofNode p2 = search(ln2);
        boolean check1 = e.isNegation(p1.myExpr.myLeft()) && p2.myExpr.isNegation(p1.myExpr.myRight());//!(new Expression("~" + p1.myExpr.myLeft().toString())).equals(e)
                //|| !(new Expression("~" + p1.myExpr.myRight())).equals(p2.myExpr);
        boolean check2 = e.isNegation(p2.myExpr.myLeft()) && p1.myExpr.isNegation(p2.myExpr.myRight());//!(new Expression("~" + p2.myExpr.myLeft().toString())).equals(e)
                //|| !(new Expression("~" + p2.myExpr.myRight())).equals(p1.myExpr);
        //System.out.println("check1: " + !(p1.myExpr.myLeft()).equals(new Expression("~" + e.toString())) + ","
        //        + !(p1.myExpr.myRight()).equals(new Expression("~" + p2.myExpr.toString())));
        //System.out.println("check2: " + !(p2.myExpr.myLeft()).equals(new Expression("~" + e.toString())) + ","
        //		+ !(p2.myExpr.myRight()).equals(new Expression("~" + p1.myExpr.toString())));
        if (!check1 && !check2) {
            throw new IllegalInferenceException("cannot use mt");
        }
        curShowNode.addChild(myLineNumber, e, x);
    }
	
	public void repeat(String l1, String expr, String x) throws IllegalLineException{
        if (firstShow) {
            throw new IllegalLineException("must begin with show");
        }
        Expression e = new Expression(expr);
        LineNumber ln1 = new LineNumber(l1);
        ProofNode p1 = search(ln1);
        if(!e.equals(p1.myExpr)){
        	throw new IllegalLineException("cannot use repeat");
        }
        curShowNode.addChild(myLineNumber, e, x);
    }

	public void applyThm(String thmName, String expr, String x) throws IllegalLineException, IllegalInferenceException{
	if(firstShow){
		throw new IllegalLineException("must begin with show");
	}
	if(!thms.contains(thmName)){
		throw new IllegalLineException("no such theorem: " + thmName);
	}
	//	Expression e = new Expression(expr);
	//	if(!thms.contains(thmName)){
	//		throw new IllegalLineException("no such theorem");
	//	}
	}
	
	public ProofNode search(LineNumber ln) throws IllegalLineException{
		return search(curShowNode, ln, true);
	}
	
	private static ProofNode search(ProofNode p, LineNumber ln, boolean siblings) throws IllegalLineException{
		if(p == null){
			throw new IllegalLineException("unable to refer to line " + ln);
		}
		int size = p.myChildren.size();
		if(!siblings){
			size--;
		}
		for(int i = 0; i < size; i++){
			ProofNode child = p.myChildren.get(i);
			if(child.myLineNumber.equals(ln)){
				return child;
			}
		}
		return search(p.parent(), ln, false);
	}

	public String toString ( ) {
		return "";
	}

	public boolean isComplete ( ) throws IllegalLineException {
		if(firstShow){
			return false;
		}
		int lastStep = curShowNode.myChildren.size()-1;
		if(lastStep == -1){
			return false;
		} else if(curShowNode.myExpr.equals(curShowNode.myChildren.get(lastStep).myExpr)){
			if(curShowNode.equals(myRoot)){
				return true;
			}
			curShowNode = curShowNode.parent();
			// System.out.println(myLineNumber);
			myLineNumber = myLineNumber.nesting(true);
			// System.out.println(myLineNumber);
		}
		return false;
	}
	
	private static class ProofNode{
		public LineNumber myLineNumber;
		public Expression myExpr;
		public ProofNode myParent;
		public ArrayList<ProofNode> myChildren;
		public String myString;
		
		public ProofNode(LineNumber lineNumber, Expression expr, ProofNode parent, String string){
			myLineNumber = lineNumber;
			myExpr = expr;
			myParent = parent;
			myChildren = new ArrayList<ProofNode>();
			myString = string;
		}

		public ProofNode parent(){
			return myParent;
		}
		
		public void addChild(LineNumber lineNumber, Expression expr, String string){
			ProofNode child = new ProofNode(lineNumber, expr, this, string);
			myChildren.add(child);
		}
	}
}
