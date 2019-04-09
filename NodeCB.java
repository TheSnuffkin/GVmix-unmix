package encrypt;

import javax.xml.soap.Node;

/*****************************************************************
 Represents single element of the clipboard linked list,
 holding double linked lists as data and a pointer to the next node
 @author Cade Snuffer
 *****************************************************************/
public class NodeCB {

    /** */
    private int clipBoardNumber;

    /** */
    private NodeD<Character> topOfClipBoard;

    /** Pointer to the next node in the list */
    private NodeCB next;

    /** Stored double linked list within node */
    private DoubleLinkedList<Character> data;

    /*****************************************************************
     Constructor creates a node with given data and pointers
     @param data Double linked list to-be-stored
     @param next pointer to next Node in list
     *****************************************************************/
    public NodeCB(DoubleLinkedList<Character> data, NodeCB next) {
        this.data = data;
        this.next = next;
    }

    /*****************************************************************
     Returns the data stored in node
     @return data Data stored in node
     *****************************************************************/
    public DoubleLinkedList<Character> getData() {
        return data;
    }

    /*****************************************************************
     Sets the data in a node to given data
     @param data2 Data to-be-stored
     *****************************************************************/
    public void setData(DoubleLinkedList<Character> data2) {
        this.data = data2;
    }

    /*****************************************************************
     Returns the next node in the list
     @return next Node
     *****************************************************************/
    public NodeCB getNext() {
        return next;
    }

    /*****************************************************************
     Sets the pointer to the next node
     @param next pointer to the next Node
     *****************************************************************/
    public void setNext(NodeCB next) {
        this.next = next;
    }

    public int getClipBoardNumber() {
        return clipBoardNumber;
    }

    public void setClipBoardNumber(int clipBoardNumber) {
        this.clipBoardNumber = clipBoardNumber;
    }

    public NodeD getTopOfClipBoard() {
        return topOfClipBoard;
    }

    public void setTopOfClipBoard(NodeD topOfClipBoard) {
        this.topOfClipBoard = topOfClipBoard;
    }
}
