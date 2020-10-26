package com.jin.ds.tree;

public class Tree234 {
    private Node root;
    private static class DataItem {
        public long dData;          // one data item

        public DataItem(long dd) {
            dData = dd;
        }

        public void displayItem() { // display item, format "/27"
            System.out.print("/" + dData);
        }
    }


    private static class Node {
        private static final int ORDER = 4;
        private int numItems;
        private Node parent;
        private Node childArray[] = new Node[ORDER];
        private DataItem itemArray[] = new DataItem[ORDER - 1];

        // connect child to this node
        public void connectChild(int childNum, Node child) {
        }

        // disconnect child from this node, return it
        /*public Node disconnectChild(int childNum) {
        }

        public Node getChild(int childNum) {
        }

        public Node getParent() {
        }

        public boolean isLeaf() {
        }

        public int getNumItems() {
        }

        public DataItem getItem(int index) {   // get DataItem at index
        }

        public boolean isFull() {
        }

        public int findItem(long key) {
        }

        public int insertItem(DataItem newItem) {
        }

        public DataItem removeItem() {       // remove largest item
        }*/

        public void displayNode() {        // format "/24/56/74/"
            for (int j = 0; j < numItems; j++)
                itemArray[j].displayItem();   // "/56"
            System.out.println("/");         // final "/"
        }
    }  // end class Node
}
