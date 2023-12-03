/****************************************
 CLASS: BSTNode.java
 CSC212 Data structures - Project phase II
 Fall 2023
 EDIT DATE:
 03-12-2023
 TEAM:
 Logic
 AUTHORS:
 Anas Saif (443106538)
 Abdullah Alothman (443101712)
 Mohammed Lazhar (443102272)
 ****************************************/
public class BSTNode<T> {
    public String key;
    public T data;
    public BSTNode<T> left, right;

    /**
     * Creates a new instance of BSTNode
     */
    public BSTNode(String k, T val) {
        key = k;
        data = val;
        left = right = null;
    }

    public BSTNode(String k, T val, BSTNode<T> l, BSTNode<T> r) {
        key = k;
        data = val;
        left = l;
        right = r;
    }
}
