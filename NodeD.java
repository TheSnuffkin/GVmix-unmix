package encrypt;

import java.io.Serializable;

/*****************************************************************
 Represents a single element of a double linked list, holding
 generic data and pointers to next and previous nodes
 *****************************************************************/
public class NodeD<E> implements Serializable {

    /** Generic data stored in Node */
    public E data;

    /** Pointer to the next node in the list */
    public NodeD next;

    /** Pointer to the previous node in the list */
    public NodeD prev;

    public NodeD() {
        super();
    }

    /*****************************************************************
     Constructor creates a node with given data and pointers
     @param data Generic data to-be-stored
     @param next pointer to next Node in list
     @param prev pointer to previous Node in the list
     *****************************************************************/
    public NodeD(E data, NodeD next, NodeD prev) {
        this.data = data;
        this.next = next;
        this.prev = prev;
    }

    /*****************************************************************
     Returns the data stored in node
     @return data Data stored in node
     *****************************************************************/
    public E getData() {
        return data;
    }

    /*****************************************************************
     Sets the data in a node to given data
     @param data2 Data to-be-stored
     *****************************************************************/
    public void setData(E data2) {
        this.data = data2;
    }

    /*****************************************************************
     Returns the next node in the list
     @return next Node
     *****************************************************************/
    public NodeD getNext() {
        return next;
    }

    /*****************************************************************
     Sets the pointer to the next node
     @param next pointer to the next Node
     *****************************************************************/
    public void setNext(NodeD next) {
        this.next = next;
    }

    /*****************************************************************
     Returns the previous node in the list
     @return previous Node
     *****************************************************************/
    public NodeD getPrev() {
        return prev;
    }

    /*****************************************************************
     Sets the pointer to the previous node
     @param prev pointer to the previous Node
     *****************************************************************/
    public void setPrev(NodeD prev) {
        this.prev = prev;
    }
}
