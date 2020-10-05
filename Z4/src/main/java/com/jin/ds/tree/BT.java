package com.jin.ds.tree;


import java.util.*;

public class BT {
    public static void main(String[] args) {
        BT bt = new BT();
        bt.add(60);bt.add(40);bt.add(75);bt.add(30);bt.add(33);
        bt.add(50);bt.add(42);bt.add(66);bt.add(80);bt.add(85);
        //bt.add(64);bt.add(72);bt.add(79);bt.add(85);bt.add(61);
        //bt.add(90);bt.add(87);bt.add(93);bt.add(86);bt.add(59);
        System.out.println(bt.find(42));
        System.out.println("---------------------------traverse recursive-------------------------------");
        System.out.print("preOrder : ");
        bt.traverseRecur(bt.root, 0);System.out.println();
        System.out.print("inOrder : ");
        bt.traverseRecur(bt.root, 1);System.out.println();
        System.out.print("postOrder : ");
        bt.traverseRecur(bt.root, 2);System.out.println();
        System.out.println("-----------------------------------------------------------------------------");
        /*System.out.println("----------------------------traverse non recursive---------------------------");
        System.out.print("preOrder : ");
        bt.traverseNoRecur(bt.root, 0);System.out.println();
        System.out.print("inOrder : ");
        bt.traverseNoRecur(bt.root, 1);System.out.println();
        System.out.print("postOrder : ");
        bt.traverseNoRecurPost(bt.root);System.out.println();
        System.out.println("-----------------------------------------------------------------------------");*/

        bt.del(80);
        bt.displayTree();
    }

    private static class Node {
        int d;
        Node l;
        Node r;
        Node pa;
        int level;
        int showPos;
        int firstElePos;
        int th;

        Node(int d) {
            this.d = d;
        }
        @Override
        public String toString() {
            return d+"-"+level+":"+showPos+"^"+firstElePos+"="+th+", ";
        }
    }

    Node root;
    int maxLevel;
    public void add(int d) {
        Node n = new Node(d);
        Node ptr;
        if(root == null) {
            maxLevel = ++n.level;
            root = n;
            n.th = 1;
        } else {
            ptr = root;
            while(true) {
                if(d < ptr.d) {
                    if(ptr.l != null) {
                        ptr = ptr.l;
                    } else {
                        n.pa = ptr;
                        ptr.l = n;
                        n.level = ptr.level+1;
                        if(n.level > maxLevel) maxLevel = n.level;
                        n.th=n.pa.th*2-1;
                        break;
                    }
                } else {
                    if(ptr.r != null) {
                        ptr = ptr.r;
                    } else {
                        n.pa = ptr;
                        ptr.r = n;
                        n.level = ptr.level+1;
                        if(n.level > maxLevel) maxLevel = n.level;
                        n.th=n.pa.th*2;
                        break;
                    }
                }
            }
        }
    }


    public Node find(int key) {
        Node result = root;
        while (result != null) {
            //for(;result != null;) {
            if (result.d == key) {
                break;
            } else if (key < result.d) {
                result = result.l;
            } else {
                result = result.r;
            }

        }
        return result;
    }

    public void traverseRecur(Node n, int option) {
        if(n == null) {
            return;
        }
        if(option == 0) System.out.print(n);
        traverseRecur(n.l, option);
        if(option == 1) System.out.print(n);
        traverseRecur(n.r, option);
        if(option == 2) System.out.print(n);
    }

    public void traverseNoRecur(Node n, int option) {
        Stack<Node> s = new Stack<>();
        while(true) {
            if(n!=null) {
                if(option == 0) System.out.print(n);
                s.push(n);
                n = n.l;
            } else {
                n=s.pop();
                if(option == 1) System.out.print(n);
                n=n.r;
                if(n==null && s.isEmpty()) break;
            }
        }
    }
    public void traverseNoRecurPost(Node n) {
        Stack<Node> s = new Stack<>();
        while(true) {
            if(n!=null) {
                s.push(n);
                n = n.l;
            } else {
                if(s.isEmpty()) break;
                n=s.peek();
                if(n.r != null) {
                    n.l = n.r;
                    n.r = null;
                    n = n.l;
                } else {
                    System.out.print(s.pop());
                    n=null;
                }
            }
        }
    }


    private Queue<Node> findNodesByLevel(int level) {
        Queue<Node> queue = new LinkedList<Node>();
        Node n = root;
        Stack<Node> s = new Stack<>();
        while(true) {
            if(n !=null && n.level==level) queue.offer(n);
            if(n!=null) {
                s.push(n);
                n = n.l;
            } else {
                n=s.pop();
                n=n.r;
                if(n==null && s.isEmpty()) break;
            }
        }
        return queue;
    }

    private int calcMLN() {
        int mln=0;
        for (int i = 0; i< maxLevel; i++) {
            mln += 1 << (i + 1);
        }
        return mln;
    }
    public void displayTree() {
        int mlp = calcMLN();
        int n=0,sn=0,l = 0;
        List<Queue> list = new ArrayList<>();
        for (int i = 0; i< maxLevel; i++) {
            l=i+1;
            n = 1<<i;
            sn+=1<<(i+1);
            Queue<Node> queue = findNodesByLevel(l);
            int a = 1<<(maxLevel-l+2);
            for (Node nd : queue) {
                if(nd.pa == null) {
                    nd.showPos = mlp/2;
                    nd.firstElePos = nd.showPos;
                } else {
                    Node pa = nd.pa;
                    nd.firstElePos = pa.firstElePos/2;
                    if(nd.th==1) {
                        nd.showPos = nd.firstElePos;
                    } else{
                        nd.showPos = nd.firstElePos+(nd.th-1)*a;
                    }
                }
            }
            list.add(queue);
            System.out.println(l+"("+queue+")--"+n+"--"+sn);
        }

        for (Queue<Node> q : list) {
            int fep = q.peek().firstElePos;
            int gap = 1<<(maxLevel-q.peek().level+2);
            for(int i=1;i<fep;i++) {
                System.out.print(" ");
            }
            int nextElePos = fep;
            for(int i=fep;i<=mlp;i++) {
                if(!q.isEmpty() && q.peek().showPos==i) {
                    System.out.print(q.poll().d);
                    nextElePos = i+gap;
                    i++;
                }else if(nextElePos <mlp && nextElePos ==i){
                    System.out.print("**");
                    nextElePos = i+gap;
                    i++;
                }else{
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    public void del(int d) {
        Node n = find(d);
        System.out.println(n);
        if(n != null) {
            System.out.println("del ele : "+n);
            if(n.l == null && n.r ==null) {
                if(d<n.pa.d) {
                    n.pa.l=null;
                } else {
                    n.pa.r=null;
                }
            } else if(n.l != null && n.r == null) {
                if(d<n.pa.d) {
                    n.pa.l=n.l;
                } else {
                    n.pa.r=n.l;
                }
                n.l.level=n.level;
                n.l.th = n.th;
                n.l.pa = n.pa;
            } else if(n.l == null && n.r != null) {
                if(d<n.pa.d) {
                    n.pa.l=n.r;
                } else {
                    n.pa.r=n.r;
                }
                n.r.level=n.level;
                n.r.th = n.th;
                n.r.pa = n.pa;
            }
        }
    }
    /*private Node findSuccessor() {

    }*/
}
