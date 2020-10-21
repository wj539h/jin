package com.jin.ds.tree;

import com.jin.Const;

import java.util.*;

public class BinarySearchTree {

    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();
        bst.add(60);bst.add(40);bst.add(75);bst.add(30);bst.add(99);
        bst.add(50);bst.add(42);bst.add(66);bst.add(80);bst.add(85);
        /*bst.add(64);bst.add(72);bst.add(76);bst.add(84);bst.add(65);
        bst.add(74);bst.add(77);*///bt.add(78);bt.add(79);
        //bt.add(90);bt.add(87);bt.add(93);bt.add(86);bt.add(59);
        Const.pln(bst.find(42));
        //bst.del(75);
        bst.refreshProp(bst.root,null);
        Const.pln(Const.half_sap+"traverse recursive"+Const.half_sap);
        
        Const.p("preOrder : ");
        bst.traverseRecur(bst.root, 0);Const.pln();
        Const.p("inOrder : ");
        bst.traverseRecur(bst.root, 1);Const.pln();
        Const.p("postOrder : ");
        bst.traverseRecur(bst.root, 2);Const.pln();

        /*Const.pln(Const.full_sap);
        Const.pln(half_sapa+"traverse non recursive"+half_sapa);
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

        bst.del(75);

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
        int th;
        byte type = 'd';

        Node(int d) {
            this.d = d;
        }
        @Override
        public String toString() {
            return d+"-"+level+"-"+th+"  ";
        }
    }

    protected Node root;
    protected int maxLevel;
    private int bbc;//(bbc-bottomBlockCount)
    private int maxDataLen;//插入每一个节点的数据长度
    public void add(int d) {
        Node n = new Node(d);
        Node ptr;
        if(root == null) {
            root = n;
        } else {
            ptr = root;
            while(true) {
                if(d < ptr.d) {
                    if(ptr.l != null) {
                        ptr = ptr.l;
                    } else {
                        ptr.l = n;
                        break;
                    }
                } else {
                    if(ptr.r != null) {
                        ptr = ptr.r;
                    } else {
                        ptr.r = n;
                        break;
                    }
                }
            }
        }
    }

    //重新计算一些通用的值
    public void refreshProp(Node n, Node pa) {
        if (n == null)  return;

        n.pa = pa;
        //n.level = n.d == root.d ? (n.level + 1) : (pa.level + 1);
        if(n.d == root.d) {
            if(n.level==0)
                n.level++;
        }else{
            n.level = pa.level + 1;
        }
        if (n.level > maxLevel)
            maxLevel = n.level;
        if(String.valueOf(n.d).length()>maxDataLen )
            maxDataLen = String.valueOf(n.d).length();
        refreshProp(n.l, n);
        refreshProp(n.r, n);
    }

    //这个Map是为了displayTree方便而用的, key是level,
    //value是一个子map, key是vNode的th,值是Node
    Map<Integer, Map<Integer,Node>> map =null;
    private void populateMap(Node n) {
        if(map == null)
            map = new HashMap<Integer, Map<Integer,Node>>();
        Map<Integer,Node> submap = map.get(Integer.valueOf(n.level));
        if(submap == null) {
            submap = new HashMap<Integer,Node>();
        }
        submap.put(n.th, n);
        map.put(Integer.valueOf(n.level),submap);
    }

    //populate二叉树所有节点的th,思想也是用
    public void refreshTh(Node n, int th) {
        if (n == null) {
            return;
        }
        int a = 0;
        if(th == -1) {
            n.th = (bbc+1)/2;
        } else{
            n.th = th;
        }
        a = (int)Math.pow(2,(double)(maxLevel-n.level-1));
        populateMap(n);
        refreshTh(n.l, n.th-a);
        refreshTh(n.r, n.th+a);
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

    //每一行都放入bbc个Node, 包括space, asterisk, data
    //比如maxLevel=4, bbc=15, 如果level是3, 那么就放入sp**spspsp33spspsp42sp**sp
    private Queue<Node> fillNodesByLevel(int level) {
        Queue<Node> queue = new LinkedList<Node>();

        int firstTh = (bbc+1)/(1<<level);//这个level上第一个应该有的元素(包括***或d)的位置
        Set<Integer> set = new HashSet<Integer>();
        for ( int j = firstTh; j <= bbc; j += 1 << (maxLevel - (level - 1)) ) { //
            set.add(j);
        }
        Node n = null;
        Map<Integer,Node> submap = map.get(Integer.valueOf(level));
        for(int i=1;i<=bbc;i++) {
            n = new Node(0);
            n.type='s';
            if(set.contains(Integer.valueOf(i))) {
                n.type= 'a';
            }
            if(submap.containsKey(Integer.valueOf(i))) {
                n = submap.get(Integer.valueOf(i));
            }
            queue.offer(n);
        }
        return queue;
    }

    //计算最底层应有的block(bbc-bottomBlockCount)
    private int bottomBlockCount() {
        int bbc=0;
        for (int i = 1; i <= maxLevel; i++) {
            bbc += 1<<(i-1);
        }
        return bbc;
    }

    public void displayTree() {
        bbc = bottomBlockCount();
        refreshTh(root,-1);
        List<Queue> list = new ArrayList<>();
        //maxDataLen+=3;//可以改变这个值,用以显示其他属性比如说, 30(data)--2(th
        for (int i = 1; i<= maxLevel; i++) {
            Queue<Node> queue = fillNodesByLevel(i);
            for(Node n : queue) {
                if(n.type=='s')
                    System.out.print(spaces());
                else if(n.type=='a')
                    System.out.print(aster());
                else if(n.type=='d')
                    System.out.print(paddingLen(n.d));
            }
            System.out.println();
        }
    }

    private String paddingLen(int d) {
        String sd = String.valueOf(d);
        int diff = maxDataLen-sd.length();
        for(int i=0;i<diff;i++)
            sd="0"+sd;
        return sd;
    }
    private String aster() {
        String s = "";
        for(int i=0;i<maxDataLen;i++)
            s+="*";
        return s;
    }
    private String spaces() {
        String s = "";
        for(int i=0;i<maxDataLen;i++)
            s+=" ";
        return s;
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
        //删除后需要重置这些值
        maxLevel = 0;
        map = null;
        refreshProp(root, null);
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
