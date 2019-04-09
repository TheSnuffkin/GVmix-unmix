package encrypt;

import javax.xml.soap.Node;
/*****************************************************************
 Represents a doubly-linked list holding E data
 @author Cade Snuffer
 *****************************************************************/
public class DoubleLinkedList<E> {

    /** NodeD object as marker for top of list */
    protected NodeD<E> top;
    /** NodeD object as current node reference */
    protected NodeD<E> cursor;

    /*****************************************************************
     Constructor creates an empty list
     *****************************************************************/
    public DoubleLinkedList() {
        top = null;
        cursor = null;
    }

    /*****************************************************************
     Returns length of the list
     @return i length of list
     *****************************************************************/
    public int length(){
        cursor = top;
        int i = 0;
        while(cursor != null){
            cursor = cursor.getNext();
            i++;
        }
        return i;
    }

    /*****************************************************************
     Returns the data stored at a given index
     @param position particular list index
     @return data stored at position
     *****************************************************************/
    public E get(int position) {
        try{
            cursor = top;
            for (int i = 0; i < position; i++)
                cursor = cursor.getNext();
            return cursor.getData();
        }catch(NullPointerException e){
            return null;
        }
    }

    /*****************************************************************
     Returns the Node object stored at a given index
     @param position particular list index
     @return NodeD object at position
     *****************************************************************/
    public NodeD<E> getNode(int position) {
        cursor = top;
        for (int i = 0; i < position; i++) {
            cursor = cursor.getNext();
        }
        return cursor;

    }

    /*****************************************************************
     Add an element to the top of the list
     @param data element to be appended to list
     *****************************************************************/
    public void append(E data){
        if(top==null){
            top = new NodeD<E>(data, null, null);
        }else {
            NodeD<E> temp = new NodeD<E>(data, top, null);
            top.setPrev(temp);
            top = temp;
        }
    }

    /*****************************************************************
     Add an element to the end of the list
     @param data element to be added to end
     *****************************************************************/
    public void endAppend(E data){
        if(top == null){
            top = new NodeD<E>(data, null, null);
        }else{
            cursor = top;
            while(cursor.getNext() != null){
                cursor = cursor.getNext();
            }
            NodeD<E> temp = new NodeD<E>(data, null, cursor);
            cursor.setNext(temp);
        }
    }

    /*****************************************************************
     Add an element to a particular index
     @param data element to be stored at position
     @param position particular list index
     *****************************************************************/
    public void addAtIndex(E data, int position){
        if(top == null){
            top = new NodeD<E>(data, null, null);
        }else if (position < 0 || position > length()){
            throw new IllegalArgumentException("invalid position : "+position);
        }else if(position == length()){
            endAppend(data);
        }else{
            if(position==0){
                append(data);
            }else {
                cursor = top;
                for (int i = 0; i < position - 1; i++) {
                    cursor = cursor.getNext();
                }
                NodeD<E> temp = cursor.getNext();
                cursor.setNext(new NodeD<E>(data, temp, cursor));
                temp.setPrev(cursor.getNext());
            }
        }
    }

    /*****************************************************************
     Removes an element at a particular index
     @param position particular list index
     @throws IndexOutOfBoundsException
     *****************************************************************/
    public void remove(int position) {
        try{
            if(position >= 0 && position < length()){
                if(top == null)
                    System.out.println("EMPTY LIST");
                else{
                    cursor = top;
                    if(position==0){
                        if(top.getNext()==null){
                            top = null;
                        }else {
                            top.getNext().setPrev(null);
                            top = top.getNext();
                        }
                    }else if(position == length()-1){
                        getNode(length()-2).setNext(null);
                    }else {
                        cursor = top;
                        for (int i = 0; i < position-1; i++) {
                            cursor = cursor.getNext();
                        }
                        NodeD<E> toDel = cursor.getNext();
                        NodeD<E> after = cursor.getNext().getNext();
                        cursor.setNext(after);
                        after.setPrev(cursor);
                    }
                }
            }
            else {
                throw new IndexOutOfBoundsException();
            }
        }catch(NumberFormatException e){
            throw new NumberFormatException("whoops");
        }
    }

    /*****************************************************************
     Removes all occurrences of a given element in the list
     @param data element to be removed
     *****************************************************************/
    public void removeInstances(E data){
        cursor = top;
        if(cursor==null){
            System.out.println("EMPTY MESSAGE");
            return;
        }
        int n = 1;
        if(cursor.getData().equals(data)){
            remove(0);
            n--;
        }
        while(cursor.getNext()!=null){
            cursor = cursor.getNext();
            if(cursor.getData().equals(data)){
                remove(n);
                n--;
            }
            n++;
        }
    }

    /*****************************************************************
     Removes a range of elements from the list, starting and
     stopping at given indices
     @param start given start index
     @param stop given stop index
     *****************************************************************/
    public void removeRange(int start, int stop){
        NodeD<E> temp = top;

        if(top == null) {
            System.out.println("EMPTY LIST");
        }else if((start < 0) || (stop >= length())) {
            System.out.println("\nERROR: Index out of bounds!\n");
            throw new IndexOutOfBoundsException();
        }else if(stop < start) {
            System.out.println("\nERROR: Stop cannot be less than start!\n");
            throw new IndexOutOfBoundsException();
        }else if(start == 0){
            if(top.getNext()==null){
                top=null;
            }
            else if(stop==length()-1){
                top=null;
            }
            else {
                top = getNode(stop);
                top = top.getNext();
                top.setPrev(null);
            }
        }else if (stop == length()-1){
            getNode(start-1).setNext(null);
        }else{
            for(int i = 0; i < start-1; i++){
                temp = temp.getNext();
            }
            NodeD<E> temp2 = temp;
            for(int i = start-1; i <= stop; i++){
                temp2 = temp2.getNext();
            }
            temp.setNext(temp2);
            temp2.setPrev(temp);
        }
    }

    /*****************************************************************
     Swaps all instances of given data with other given
     data in list
     @param data Represents data to-be-replaced
     @param rep Represents data to-replace old
     *****************************************************************/
    public void swapAll(E data, E rep){
        int i = 0;
        cursor = top;
        while(cursor.getNext()!=null){
            if(cursor.getData().equals(data)){
                remove(i);
                addAtIndex(rep, i);
            }
            cursor=getNode(i+1);
            i++;
        }
        if(cursor.getData().equals(data)){
            endAppend(rep);
            remove(i);
        }
    }

    /*****************************************************************
     Returns the list as a string
     @return retVal String containing all values of the list
     *****************************************************************/
    public String toString() {
        String retVal = "";
        NodeD<E> cur = top;
        while (cur != null) {
            retVal += cur.getData();
            cur = cur.getNext();
        }

        return retVal;
    }
}
