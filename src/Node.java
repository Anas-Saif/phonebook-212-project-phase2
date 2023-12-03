/****************************************
 CLASS: Node.java
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
public class Node<T> {
    public T data;
    public Node<T> next;

    public Node () {
        data = null;
        next = null;
    }

    public Node (T val) {
        data = val;
        next = null;
    }
    // Setters & Getters
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
