package com.jin.ds.tree;

public class Tree234 {
    private Node root;

    private static class DataItem {
        public int d;          // one data item

        public DataItem(int dd) {
            d = dd;
        }

        public void displayItem() { // display item, format "/27"
            System.out.print("/" + d);
        }
    }


    private static class Node {
        public static final int ORDER = 4;
        public int numItems;
        public Node parent;
        public Node childArray[];
        public DataItem itemArray[];

        // connect child to this node
        public void connectChild(int childNum, Node child) {
            if (childArray == null) {
                childArray = new Node[ORDER];
            }
            childArray[childNum] = child;
            if (child != null)
                child.parent = this;
        }

        // disconnect child from this node, return it
        public Node disconnectChild(int childNum) {
            Node tempNode = childArray[childNum];
            childArray[childNum] = null;
            return tempNode;
        }


        public boolean isLeaf() {
            return childArray == null;
        }

        public boolean isFull() {
            return numItems == ORDER - 1;
        }

        public int findItem(int key) {
            for (int j = 0; j < ORDER - 1; j++) {
                if (itemArray[j] == null)
                    break;
                else if (itemArray[j].d == key)
                    return j;
            }
            return -1;
        }

        public int insertItem(DataItem nIt) {
            int tmp =0;
            if(itemArray == null) {
                itemArray = new DataItem[ORDER - 1];
                itemArray[0] = nIt;
            }else{
                for (int i = 0;i< itemArray.length; i++) {
                    if(itemArray[i] == null) {
                        tmp = i;
                        break;
                    }
                }
                for (int i = itemArray.length-1; i >= 0; i--) {
                    if(itemArray[i] != null && itemArray[i].d > nIt.d) {
                        itemArray[i+1] = itemArray[i];
                        tmp = i;
                    }
                }
                itemArray[tmp]=nIt;
            }
            numItems++;
            return tmp;
        }

        public DataItem removeItem() {       // remove largest item
            DataItem temp = itemArray[numItems - 1];  // save item
            itemArray[numItems - 1] = null;           // disconnect it
            numItems--;                             // one less item
            return temp;
        }

        public void displayNode() {        // format "/24/56/74/"
            for (int j = 0; j < numItems; j++)
                itemArray[j].displayItem();   // "/56"
            System.out.println("/");         // final "/"
        }
    }








    public void test() {
        Node n = new Node();
        //DataItem d = new DataItem(80);
        n.insertItem(new DataItem(80));
        n.insertItem(new DataItem(20));
        n.insertItem(new DataItem(68));
        n.displayNode();
    }

    public static void main(String[] args) {
        Tree234 t = new Tree234();
        t.insert(70);
        t.insert(50);
        t.insert(30);
        t.insert(40);
        t.insert(20);
        t.insert(80);
        t.insert(25);
        t.insert(90);
        t.insert(75);
        t.insert(10);
        t.displayTree();
    }
    public static DataItem di(int d) {
        return new DataItem(d);
    }





    public void insert(int d) {
        DataItem di = new DataItem(d);
        Node cur = root;
        if(cur == null) {
            root = new Node();
            root.insertItem(di);
        } else {
            while(true) {
                if(cur.isLeaf()) {
                    if(cur.isFull()) {
                        cur = split(cur,d); //如果是叶节点, 但是满了, 就要劈开
                    }else{
                        cur.insertItem(di); //如果是叶节点, 没满直接insertItem
                        break;
                    }
                } else {
                    if(cur.isFull()) {
                        cur = split(cur,d);//如果不是叶节点, 但是满了, 也要劈开
                    } else {
                        cur = getCorrectChild(cur, d); //如果不是叶节点, cur指向, 那个正确的子节点
                    }
                }
            }
        }
    }


    public Node split(Node cur, int d) {
        if(cur == root) {
            Node sr = new Node();
            sr.insertItem(cur.removeItem()); // 移除C,放到新分裂出来的右节点(splited right)的第一个

            root = new Node();
            root.insertItem(cur.removeItem());//移除B,放到新分裂出来的上面的节点的第一个,作为新的root,

            root.connectChild(0,cur);
            root.connectChild(1,sr);

            sr.parent = root;

            if(!cur.isLeaf()) {
                sr.connectChild(0, cur.disconnectChild(2));//新分裂出的右节点sr的m-link放原来节点的o-link
                sr.connectChild(1, cur.disconnectChild(3));//新分裂出的右节点sr的n-link放原来节点的p-link
            }

            cur = root;
        } else {
            Node sr = new Node();
            sr.insertItem(cur.removeItem()); // 移除C,放到新分裂出来的右节点(splited right)的第一个

            Node pa = cur.parent;
            int tmp = pa.insertItem(cur.removeItem());//移除B,放到新分裂出来的上面的节点的第一个,作为新的root,
            for(int i=Node.ORDER-1;i>tmp+1;i--) {
                pa.childArray[i] = pa.childArray[i-1];
            }
            pa.childArray[tmp+1] = sr;

            sr.parent = pa;

            if(!cur.isLeaf()) {
                sr.connectChild(0, cur.disconnectChild(2));//新分裂出的右节点sr的m-link放原来节点的o-link
                sr.connectChild(1, cur.disconnectChild(3));//新分裂出的右节点sr的n-link放原来节点的p-link
            }

            //这块其实可以不按照书上代码,可以写成 cur = pa,这样的话,就会提前分裂,因为当pa满了,就会再次分裂(这个是我的做法,所以可以不传第二个参数d)
            //不过书上代码也还是挺好的,因为刚分裂的一个节点,新加入的数据肯定是要被插入pa下面的那两个分裂子节点的
            cur = getCorrectChild(pa,d);
        }
        return cur;
    }

    public Node getCorrectChild(Node cur, int d) {
        DataItem[] ia = cur.itemArray;
        int childInd = 0;
        for (int i = 0; i < ia.length; i++) {
            DataItem di = ia[i];
            if(di ==null || d<di.d) {
                childInd = i;
                break;
            }
        }
        return cur.childArray[childInd];
    }
    public void displayTree() {
        recDisplayTree(root, 0, 0);
    }
    private void recDisplayTree(Node thisNode, int level,
                                int childNumber) {
        System.out.print("level=" + level + " child=" + childNumber + " ");
        thisNode.displayNode();               // display this node

        // call ourselves for each child of this node
        int numItems = thisNode.numItems;
        for (int j = 0; j < numItems + 1; j++) {
            if (thisNode.childArray != null)
                recDisplayTree(thisNode.childArray[j], level + 1, j);
            else
                return;
        }
    }
}
