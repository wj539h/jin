package com.jin.ds.tree;

import com.jin.Const;

import java.util.*;

public class BinarySearchTree {

    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();
        bst.add(60);bst.add(40);bst.add(75);bst.add(30);bst.add(33);
        bst.add(50);bst.add(42);bst.add(66);bst.add(80);bst.add(85);
        bst.add(64);bst.add(72);bst.add(76);bst.add(84);bst.add(65);
        bst.add(74);bst.add(77);//bt.add(78);bt.add(79);
        //bt.add(90);bt.add(87);bt.add(93);bt.add(86);bt.add(59);
        Const.pln(bst.find(42));
        //bst.del(75);
        bst.populateProp(bst.root,null,1);
        Const.pln(Const.half_sap+"traverse recursive"+Const.half_sap);
        
        Const.p("preOrder : ");
        bst.traverseRecur(bst.root, 0);Const.pln();
        Const.p("inOrder : ");
        bst.traverseRecur(bst.root, 1);Const.pln();
        Const.p("postOrder : ");
        bst.traverseRecur(bst.root, 2);Const.pln();
        
        Const.pln(Const.full_sap);
        /*Const.pln(half_sapa+"traverse non recursive"+half_sapa);
        Const.p("preOrder : ");
        bt.traverseNoRecur(bt.root, 0);Const.pln();
        Const.p("inOrder : ");
        bt.traverseNoRecur(bt.root, 1);Const.pln();
        Const.p("postOrder : ");
        bt.traverseNoRecurPost(bt.root);Const.pln();
        Const.pln("-----------------------------------------------------------------------------");*/

        Const.pln(Const.full_sap);
        bst.displayTree();
        Const.pln(Const.full_sap);
    }

    public static class Node {
        int d;
        Node l;
        Node r;
        Node pa;
        int level; //root = 1
        int showPos; //此节点的位置
        int firstElePos; //每行第一个元素的位置
        int th; //当前行的第几个, l.th = pa.th*2 - 1, r.th = pa.th*2

        Node(int d) {
            this.d = d;
        }
        @Override
        public String toString() {
            return d+"-"+level+"-"+showPos+"-"+firstElePos+"-"+th+", ";
        }
    }

    protected Node root;
    protected int maxLevel;
    public void add(int d) {
        Node n = new Node(d);
        Node ptr;
        if(root == null) {
            //maxLevel = ++n.level;
            root = n;
            //n.th = 1;
        } else {
            ptr = root;
            while(true) {
                if(d < ptr.d) {
                    if(ptr.l != null) {
                        ptr = ptr.l;
                    } else {
                        //n.pa = ptr;
                        ptr.l = n;
                        //n.level = ptr.level+1;
                        //if(n.level > maxLevel) maxLevel = n.level;
                        //n.th=n.pa.th*2-1;
                        break;
                    }
                } else {
                    if(ptr.r != null) {
                        ptr = ptr.r;
                    } else {
                        //n.pa = ptr;
                        ptr.r = n;
                        //n.level = ptr.level+1;
                        //if(n.level > maxLevel) maxLevel = n.level;
                        //n.th=n.pa.th*2;
                        break;
                    }
                }
            }
        }
    }

    //设置Node的pa,th,level
    public void populateProp(Node n, Node pa, int th) {
        if (n == null) {
            return;
        }
        n.pa = pa;
        n.level = n.d == root.d ? (n.level + 1) : (pa.level + 1);
        if (n.level > maxLevel) maxLevel = n.level;
        n.th = th;
        populateProp(n.l, n,n.th * 2 - 1);
        populateProp(n.r, n,n.th * 2);
    }

    //查找
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

    //遍历, 0先序,1中序,2后续
    public void traverseRecur(Node n, int option) {
        if(n == null) {
            return;
        }
        if(option == 0) Const.p(n);
        traverseRecur(n.l, option);
        if(option == 1) Const.p(n);
        traverseRecur(n.r, option);
        if(option == 2) Const.p(n);
    }

    //先序中序非递归实现
    public void traverseNoRecur(Node n, int option) {
        Stack<Node> s = new Stack<>();
        while(true) {
            if(n!=null) {
                if(option == 0) Const.p(n);
                s.push(n);
                n = n.l;
            } else {
                n=s.pop();
                if(option == 1) Const.p(n);
                n=n.r;
                if(n==null && s.isEmpty()) break;
            }
        }
    }
    //后序非递归实现
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
                    Const.p(s.pop());
                    n=null;
                }
            }
        }
    }

    //同一行上的所有Node,放到queue里
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

    //一行中的最大位置, Max line position
    private int calcMlp() {
        int mln=0;
        for (int i = 1; i<= maxLevel; i++) {
            mln += 1 << i ;
        }
        return mln;
    }

    public void displayTree() {
        int mlp = calcMlp();
        List<Queue> list = new ArrayList<>();
        for (int i = 1; i<= maxLevel; i++) {
            Queue<Node> queue = findNodesByLevel(i);
            int a = 1<<(maxLevel-i+2);
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
            Const.pln(i+" - ("+queue+")");
        }

        for (Queue<Node> q : list) {
            int fep = q.peek().firstElePos;
            int gap = 1<<(maxLevel-q.peek().level+2);
            for(int i=1;i<fep;i++) {
                Const.p(" ");
            }
            int nextElePos = fep;
            for(int i=fep;i<=mlp;i++) {
                if(!q.isEmpty() && q.peek().showPos==i) {
                    Const.p(q.poll().d);
                    nextElePos = i+gap;
                    i++;
                }else if(nextElePos <mlp && nextElePos ==i){
                    Const.p("**");
                    nextElePos = i+gap;
                    i++;
                }else{
                    Const.p(" ");
                }
            }
            Const.pln();
        }
    }

    //删除节点
    public void del(int d) {
        //先查找, pa是查找到节点的父节点
        Node pa = null;
        Node ptr = root;
        boolean found = false;
        while (ptr != null) {
            if(d<ptr.d) {
                pa = ptr;
                ptr = ptr.l;
            } else if(d>ptr.d) {
                pa = ptr;
                ptr = ptr.r;
            } else if(d==ptr.d){
                found = true;
                break;
            }
        }
        if(!found) {Const.pln("Not found : "+ptr);return;}
        Const.pln("Del_element : "+ptr);
        //分三种情况
        if(ptr.l == null && ptr.r ==null) { //case1
            if(d<pa.d)
                pa.l = null;
            else
                pa.r = null;
        } else if(ptr.l != null && ptr.r == null) { //case2-l
            if(d<pa.d) {
                pa.l=ptr.l;
            } else {
                pa.r=ptr.l;
            }
        } else if(ptr.l == null && ptr.r != null) { //case2-r
            if(d<pa.d) {
                pa.l=ptr.r;
            } else {
                pa.r=ptr.r;
            }
        } else if(ptr.l != null && ptr.r != null) { //case3
            delCase3(ptr, pa);
        }
    }

    //节点的左右都有

    /**
                                    60
                    40                              75
            30              50              66              80
        **      33      42      **      64      72      76      85
     **  **  **  **  **  **  **  **  **  65  **  74  **  77  84  **
     */
    private void delCase3(Node n, Node pa) {
    	Node succ = n.r;//先找到右边
    	if(succ.l == null) {//右边没有左,那么这个右就是succ
            succ.l = n.l;
        } else {
    	    Node spa = null; //记录一下succ的pa
            while(succ.l != null) {//一直去找左,直到为空
                spa = succ;
                succ = succ.l;
            }
            if(succ.r != null) { //如果succ的r不为空, 将succ的pa的l指向succ的r
                spa.l = succ.r; //如果删除的是75,那么succ是76,此时76的r是77,76的pa是80,需要把80的r置成77
            }
            //如果删除的是75,那么succ是76, 把76的lher指向要删的75的l和r
            succ.l = n.l;
            succ.r = n.r;
        }
    	if(pa!=null) { //如果删除的是75, 75的pa的l(比pa小)或r(比pa大)指向succ
            if (n.d < pa.d) {
                pa.l = succ;
            } else {
                pa.r = succ;
            }
        }
    	if(n.d==root.d) {//删除root节点, 让root指向一下succ
    	    root = succ;
        }
    }
}
