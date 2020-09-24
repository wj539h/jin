package com.jin.ds.tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BinaryTree {

    private static class Node implements Comparable<Integer> {
	int iData;
	Node left;
	Node right;
	int pos;
	int subNum = 0;
	int level = 0;
	
	int lStart = 0;
	int lEnd = 0;
	int rStart = 0;
	int rEnd = 0;

	public Node(int iData) {
	    this.iData = iData;
	}

	public int getSubStatus() {
	    if (left != null && right != null) {
		subNum = 2;
	    } else if (left != null && right == null) {
		subNum = 10;
	    } else if (left == null && right != null) {
		subNum = 11;
	    }
	    return subNum;
	}

	@Override
	public String toString() {
	    return iData+"|"+level+"|"+pos+", ";
	}

	@Override
	public int compareTo(Integer o) {
	    int result = 0;
	    if(this.iData > o)
		result = 1;
	    else if(this.iData < o)
		result = -1;
		
	    return result;
	}
    }
    private int nItems;
    private Node root;
    private int level;
    private int bottomNum;
    private String space = "  ";

    public Node find(int key) {
	Node result = root;
	while (result != null) {
	//for(;result != null;) {
	    if (result.iData == key) {
		break;
	    } else if (key < result.iData) {
		result = result.left;
	    } else {
		result = result.right;
	    }
	    
	}
	return result;
    }
    
    public Node findParent(int key) {
	Node curr = root;
	Node parent = root;
	while (curr != null) {
	//for(;result != null;) {
	    if (curr.iData == key) {
		break;
	    } else if (key < curr.iData) {
		parent = curr;
		curr = curr.left;
	    } else {
		parent = curr;
		curr = curr.right;
	    }
	}
	return parent;
    }

    public Node findRecursive(Node node, int key) {
	Node result = null;
	if (node == null || node.iData == key) {
	    result = node;
	} else if (key < node.iData) {
	    result = findRecursive(node.left, key);
	} else {
	    result = findRecursive(node.right, key);
	}
	return result;
    }

    public void insert(int id) {
	Node newNode = new Node(id);
	Node parent = null;
	Node curr = null;
	if (root == null) {
	    root = newNode;
	    root.level++;
	} else {
	    curr = root;
	    parent = root;
	    while (true) {
		parent = curr;
		if (id < curr.iData) {
		    if (curr.left == null) {
			parent.left = newNode;
			break;
		    } else {
			curr = curr.left;
		    }
		} else if (id > curr.iData) {
		    if (curr.right == null) {
			parent.right = newNode;
			break;
		    } else {
			curr = curr.right;
		    }
		} else {
		    System.out.println("already exist");
		    return;
		}
	    }
	}
	nItems++;
    }
    
    public void populateLevel(Node node) {
	if(node == null) {
	    return;
	}
    }
    
    public void inOrderRecur(Node node) {
	if(node == null) {
	    return;
	}
	inOrderRecur(node.left);
	System.out.print(node.iData+" , ");
	inOrderRecur(node.right);
    }
    
    public void inOrder(Node root) {
	Stack<Node> stack = new Stack<Node>();
	stack.push(root);
	Node curr = root;
	while(true) {
	    if( curr.left != null ) {
		curr = curr.left;
		stack.push(curr);
	    } else {
		if(!stack.empty()) {
		    curr = stack.pop();
		    System.out.print(curr);
		    curr.left = curr.right;
		} else {
		    break;
		}
	    }
	}
    }
    
    public void preOrder(Node root) {
	Stack<Node> stack = new Stack<Node>();
	stack.push(root);
	Node curr = root;
	System.out.print(curr);
	while(true) {
	    if( curr.left != null ) {
		curr = curr.left;
		System.out.print(curr);
		stack.push(curr);
	    } else {
		if(!stack.empty()) {
		    curr = stack.pop();
		    curr.left = curr.right;
		} else {
		    break;
		}
	    }
	}
    }
    
    public void postOrder(Node root) {
	Stack<Node> stack = new Stack<Node>();
	stack.push(root);
	Node curr = root;
	while(true) {
	    if( curr.left != null ) {
		curr = curr.left;
		stack.push(curr);
	    } else {
		if(!stack.empty()) {
		    curr = stack.peek();
		    if(curr.right == null) {
			System.out.print(stack.pop());
		    }
		    curr.left = curr.right;
		    curr.right = null;
		} else {
		    break;
		}
	    }
	}
    }
    
    public void postOrder1(Node root) {
	Stack<Node> stack = new Stack<Node>();
	stack.push(root);
	
	Stack<Node> reverseStack = new Stack<Node>();
	while(!stack.empty()) {
	    Node curr = stack.pop();
	    int lel = curr.level;
	    if(this.level<lel)
		this.level = lel;
	    
	    reverseStack.push(curr);
	    
	    if(curr.left != null) {
		stack.push(curr.left);
		curr.left.level=lel+1;
	    }
	    
	    if(curr.right != null) {
		stack.push(curr.right);
		curr.right.level=lel+1;
	    }
	}
	this.bottomNum = 1<<(this.level-1);
	while(!reverseStack.empty()) {
	    System.out.print(reverseStack.pop());
	}
    }
    
    /**
     *                  60
     *          40              75
     *      30      50      66      80         
     *    12  31  42  51                  
     */
    public void levelTraversal(Node root) {
	Queue<Node> queue = new LinkedList<Node>();
	int width = bottomNum+(bottomNum-1);
	root.lStart = 0;
	root.lEnd = width/2;
	root.rStart = root.lEnd+1;
	root.rEnd = width;
	root.pos=root.lEnd-root.lStart;
	queue.offer(root);
	
	while(!queue.isEmpty()) {
	    Node curr = queue.poll();
	    int lel = curr.level;
	    System.out.print(curr);
	    
	    
	    Node cl = curr.left;
	    if(cl != null) {
		queue.offer(cl);
		cl.level=lel+1;
		
		cl.lStart = curr.lStart;
		cl.rEnd = curr.lEnd;
		
		cl.pos=(curr.lStart+curr.lEnd)/2;
		cl.lEnd = cl.pos;
		cl.rStart = cl.lEnd+1;
	    }
	    
	    Node cr = curr.right;
	    if(cr != null) {
		queue.offer(cr);
		cr.level=lel+1;
		
		cr.lStart = curr.rStart;
		cr.rEnd = curr.rEnd;
		
		cr.pos=(curr.rStart+curr.rEnd)/2;
		
		cr.lEnd = cr.pos;
		cr.rStart = cr.lEnd+1;
	    }
	    
	}
	//this.bottomNum = 1<<(this.level-1);
    }
    
    public void preOrderRecur(Node node) {
	if(node == null) {
	    return;
	}
	System.out.print(node.iData+" , ");
	preOrderRecur(node.left);
	preOrderRecur(node.right);
    }
    
    public void postOrderRecur(Node node) {
	if(node == null) {
	    return;
	}
	postOrderRecur(node.left);
	postOrderRecur(node.right);
	System.out.print(node.iData+" , ");
    }
    
    
    public void delete(int id) {
	Node delNode = find(id);
	Node parentNode = findParent(id);
	if(delNode == null) {
	    System.out.println("can't find "+id);
	    return;
	}
	
	int subNodeStatus = delNode.getSubStatus();
	if(parentNode.compareTo(id)==0) {
	    if(subNodeStatus == 10) {
		root = root.left;
	    } else if (subNodeStatus == 11) {
		root = root.right;
	    } else if (subNodeStatus == 2) {
		int successor = findSuccessor(parentNode);
		root.iData = successor;
	    }
	} else {
	    
	}
	
	/*if(subNodeNum == 0) {
	    
	} else if (subNodeNum == 10) {
	    
	} else if (subNodeNum == 11) {
	    
	} else if (subNodeNum == 2) {
	    
	}*/
	
	nItems--;
    }
    
    private int findSuccessor(Node curr) {
	int result = 0;
	return result;
    }
    
    private int nodeSubNum(Node delNode) {
	int n = 0;
	if(delNode.left != null && delNode.right != null) {
	    n = 2;
	} else if(delNode.left != null && delNode.right == null) {
	    n = 1;
	} else if(delNode.left == null && delNode.right != null) {
	    n = 11;
	}
	return n;
    }
    
    
    public void displayTree() {
	Stack<Node> globalStack = new Stack<Node>();
	globalStack.push(root);
	int nBlanks = 32;
	boolean isRowEmpty = false;
	System.out.println("......................................................");
	while (isRowEmpty == false) {
	    Stack<Node> localStack = new Stack<Node>();
	    isRowEmpty = true;

	    for (int j = 0; j < nBlanks; j++)
		System.out.print(' ');

	    while (globalStack.isEmpty() == false) {
		Node temp = (Node) globalStack.pop();
		if (temp != null) {
		    System.out.print(temp.iData);
		    localStack.push(temp.left);
		    localStack.push(temp.right);

		    if (temp.left != null || temp.right != null)
			isRowEmpty = false;
		} else {
		    System.out.print("--");
		    localStack.push(null);
		    localStack.push(null);
		}
		for (int j = 0; j < nBlanks * 2 - 2; j++)
		    System.out.print(' ');
	    } // end while globalStack not empty
	    System.out.println();
	    nBlanks /= 2;
	    while (localStack.isEmpty() == false)
		globalStack.push(localStack.pop());
	} // end while isRowEmpty is false
	System.out.println("......................................................");
    }

    public static void main(String[] args) {
	BinaryTree bt = new BinaryTree(); 
	bt.insert(60);bt.insert(40);bt.insert(75);bt.insert(30);
	bt.insert(50);bt.insert(42);/*bt.insert(80);bt.insert(51);
	bt.insert(66);bt.insert(31);bt.insert(12);bt.insert(80);bt.insert(64);bt.insert(72);
	bt.insert(79);bt.insert(85);bt.insert(61);
	bt.insert(90);bt.insert(87);bt.insert(93);bt.insert(86);*/
	
	System.out.print("inOrderRecur : ");
	bt.inOrderRecur(bt.root);
	System.out.println();
	
	System.out.print("preOrderRecur : ");
	bt.preOrderRecur(bt.root);
	System.out.println();
	
	System.out.print("postOrderRecur : ");
	bt.postOrderRecur(bt.root);
	System.out.println();
	
	int findInt = 42;
	Node found = bt.find(findInt);//theTree.findRecursive(bt.root, 50); 
	Node parent = bt.findParent(findInt);
	if (found != null) {
	    System.out.println("Found the node with key : " + found.iData + ", parent : "+parent.iData);
	} else {
	    System.out.println("Could not find");
	}
	
	/*System.out.print("inOrder : ");
	bt.inOrder(bt.root);
	System.out.println();
	
	System.out.print("preOrder : ");
	bt.preOrder(bt.root);
	System.out.println();*/
	
	System.out.print("postOrder : ");
	bt.postOrder1(bt.root);
	System.out.println();
	
	System.out.print("levelTraversal : ");
	bt.levelTraversal(bt.root);
	System.out.println();
	System.out.println("insert elements : "+bt.nItems+", level : "+bt.level+", bottomNum : "+bt.bottomNum);
	
	bt.displayTree();
    } 
} 
