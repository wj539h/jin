package com.jin.ds.tree;

import com.jin.Const;

import java.util.*;
//self111
//self222
//self333
public class RedBlackTree {
    protected RBNode root;
    protected int maxLevel;
    private int bbc;//(bbc-bottomBlockCount)
    private int maxDataLen;//插入每一个节点的数据长度
    public static void main(String[] args) {
        RedBlackTree bst = new RedBlackTree();
        bst.add(8);bst.add(5);bst.add(15);bst.add(12);bst.add(19);
        bst.add(9);bst.add(13);bst.add(23);bst.add(10);/*bst.add(65);
        bst.add(74);bst.add(77);*///bt.add(78);bt.add(79);
        //bt.add(90);bt.add(87);bt.add(93);bt.add(86);bt.add(59);
        //bst.del(75);
        bst.refreshProp(bst.root,null);

        Const.pln(Const.full_sap);
        bst.displayTree();
        Const.pln(Const.full_sap);
    }
    public void add(int d) {
        RBNode n = new RBNode(d);
        RBNode ptr;
        if(root == null) {
            root = n;
        } else {
            ptr = root;
            while(true) {
                if(d < ptr.d) {
                    if(ptr.l != null) {
                        ptr = ptr.l;
                    } else {
                        n.pa = ptr;
                        ptr.l = n;
                        break;
                    }
                } else {
                    if(ptr.r != null) {
                        ptr = ptr.r;
                    } else {
                        n.pa = ptr;
                        ptr.r = n;
                        break;
                    }
                }
            }
        }
        fixAfterInsertion(n);
    }
    private void fixAfterInsertion(RBNode z) {
        while(z.pa != null && z.pa.color=='r') { //首先还是先判断pa, 如果pa是red,才需要改变,如果是black,根本不用动
            RBNode pa = z.pa;
            RBNode g = z.pa.pa;
            RBNode u = null;
            if(g!=null) { //得到uncle
                if(z.pa == g.l) {
                    u = g.r;
                } else {
                    u = g.l;
                }
            }
            if(u != null && u.color=='r') {//首先判断uncle的颜色,如果不为空而且是red,那么直接变色不用改变结构
                pa.color = 'b';
                u.color = 'b';
                g.color = 'r';
                z = g;
            } else{ //pa是red, uncle为空(black)或black
                RBNode pivot = null;
                if( (z.pa == g.r && pa.l == z) || (z.pa == g.l && pa.r == z) ) { //triangle pivot = pa
                    pivot = pa;
                    if(pa.r == z) {
                        //left-rotate
                        lRotate(pivot,z, (byte) 't');
                    } else if(pa.l == z){
                        //right-rotate
                        rRotate(pivot,z,(byte) 't');
                    }
                    z = pa; //triangle, 先旋转, 后z指向pa
                } else if( (z.pa == g.r && pa.r == z) || (z.pa == g.l && pa.l == z) ) { //line pivot = g
                    pivot = g;
                    if(pa.r == z) {
                        //left-rotate
                        lRotate(pivot,z, (byte)'l');
                    } else if(pa.l == z){
                        //right-rotate
                        rRotate(pivot,z, (byte)'l');
                    }
                    pa.color='b';
                    g.color='r';
                    z = g; //line, 先旋转, 后给pa和g变色, 最后z指向g
                }
            }
        }
        root.color = 'b';
    }

    private void lRotate(RBNode pivot,RBNode z, byte shape) { //shape : l or t
        RBNode pa = null;
        RBNode g = null;
        if(shape == 'l') {
            pa = z.pa;
            g = pivot;
            if(g.pa!=null)  //这块也容易忘, g的pa指向需要改一下
                if(g.pa.l==g)
                    g.pa.l=pa;
                else if(g.pa.r==g)
                    g.pa.r=pa;

            pa.pa = g.pa;
            g.pa = pa;

            g.r =  pa.l;
            pa.l = g;
        } else if (shape == 't') {
            pa = pivot;
            g = pivot.pa;

            z.pa = pa.pa;

            pa.pa = z;
            pa.r = z.l;

            z.l=pa;
            g.l=z;//不要忘了这部
        }
        if(z.pa == null)
            root = z;
        if(pa.pa == null)
            root = pa;
        if(g.pa == null)
            root = g;
    }
    private void rRotate(RBNode pivot,RBNode z, byte shape) { //shape : l or t
        RBNode pa = null;
        RBNode g = null;
        if(shape == 'l') {
            pa = z.pa;
            g = pivot;
            if(g.pa!=null)  //这块也容易忘, g的pa指向需要改一下
                if(g.pa.l==g)
                    g.pa.l=pa;
                else if(g.pa.r==g)
                    g.pa.r=pa;


            pa.pa = g.pa;
            g.pa = pa;

            g.l =  pa.r;
            pa.r = g;

        } else if (shape == 't') {
            pa = pivot;
            g = pivot.pa;

            z.pa = pa.pa;

            pa.pa = z;
            pa.l = z.r;

            z.r=pa;
            g.r=z;//不要忘了这部
        }
        if(z.pa == null)
            root = z;
        if(pa.pa == null)
            root = pa;
        if(g.pa == null)
            root = g;
    }

    //重新计算一些通用的值
    public void refreshProp( RBNode n,  RBNode pa) {
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
    Map<Integer, Map<Integer,  RBNode>> map =null;
    private void populateMap( RBNode n) {
        if(map == null)
            map = new HashMap<Integer, Map<Integer,  RBNode>>();
        Map<Integer,  RBNode> submap = map.get(Integer.valueOf(n.level));
        if(submap == null) {
            submap = new HashMap<Integer,  RBNode>();
        }
        submap.put(n.th, n);
        map.put(Integer.valueOf(n.level),submap);
    }

    //populate二叉树所有节点的th,思想也是用
    public void refreshTh( RBNode n, int th) {
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
    public  RBNode find(int key) {
         RBNode result = root;
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
    public void traverseRecur( RBNode n, int option) {
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
    public void traverseNoRecur( RBNode n, int option) {
        Stack< RBNode> s = new Stack<>();
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
    public void traverseNoRecurPost( RBNode n) {
        Stack< RBNode> s = new Stack<>();
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
    private Queue< RBNode> fillNodesByLevel(int level) {
        Queue< RBNode> queue = new LinkedList< RBNode>();

        int firstTh = (bbc+1)/(1<<level);//这个level上第一个应该有的元素(包括***或d)的位置
        Set<Integer> set = new HashSet<Integer>();
        for ( int j = firstTh; j <= bbc; j += 1 << (maxLevel - (level - 1)) ) { //
            set.add(j);
        }
         RBNode n = null;
        Map<Integer,  RBNode> submap = map.get(Integer.valueOf(level));
        for(int i=1;i<=bbc;i++) {
            n = new  RBNode(0);
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
        maxDataLen+=3;//可以改变这个值,用以显示其他属性比如说, 30(data)--2(th
        for (int i = 1; i<= maxLevel; i++) {
            Queue< RBNode> queue = fillNodesByLevel(i);
            for( RBNode n : queue) {
                if(n.type=='s')
                    System.out.print(spaces());
                else if(n.type=='a')
                    System.out.print(aster());
                else if(n.type=='d')
                    System.out.print(paddingLen(n));
            }
            System.out.println();
        }
    }

    private String paddingLen(RBNode n) {
        return String.valueOf(n.d)+"("+new String(new byte[]{n.color})+")";
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
         RBNode pa = null;
         RBNode ptr = root;
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
    private void delCase3( RBNode n,  RBNode pa) {
         RBNode succ = n.r;//先找到右边
        if(succ.l == null) {//右边没有左,那么这个右就是succ
            succ.l = n.l;
        } else {
             RBNode spa = null; //记录一下succ的pa
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

    //设置RBNode的pa,th,level
    public void populateProp(RBNode n, RBNode pa, int th) {
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


    private static class RBNode {
        int d;
        RBNode l;
        RBNode r;
        RBNode pa;
        int level; //root = 1
        int th;
        byte type = 'd';
        byte color = 'r';
        RBNode(int d) {
            this.d = d;
        }
        @Override
        public String toString() {
            return d+"-"+level+"-"+th+"-"+color+"  ";
        }
    }
}
