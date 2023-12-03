/****************************************
 CLASS: List.java
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
public interface List<T> {
    public void findFirst();
    public void findNext();
    public T retrieve();
    public void update(T e);
    public void insert(T e);
    public void remove();
    public boolean full();
    public boolean empty();
    public boolean last ();
}
