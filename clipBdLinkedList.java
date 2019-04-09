package encrypt;
/*****************************************************************
 Linked list structure for storing double linked lists
 @author Cade Snuffer
 *****************************************************************/
public class clipBdLinkedList {

    /** NodeCB object as marker for top of list */
    private NodeCB top;

    /** NodeCB object as marker for end of list */
    private NodeCB tail;

    /** NodeCB object as marker for currently referenced in the list*/
    private NodeCB cursor;

    /*****************************************************************
     Constructor creates an empty list
     *****************************************************************/
    public clipBdLinkedList() {
        tail = top = null;
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
     Adds to a given clipboard. If the clipboard exists, the data is
     added to the end of that clipboard. If the clipboard does not
     exist, it creates a empty nodes until it creates the new
     clipboard at the clipNum given
     @param data Double linked list containing data to-be-added
     @param clipNum specific clipNum to access or create
     *****************************************************************/
    public void addClip(DoubleLinkedList<Character> data, int clipNum){
        if(length()==0){
            top = tail = new NodeCB(null, null);
            //if list is empty, create a new top, but containing no data
        }
        if(get(clipNum)!=null){
            //if the clipNum exists, there will pointers to it
            cursor = top;
            for(int i = 0; i<clipNum; i++){
                cursor = cursor.getNext();
            }
            DoubleLinkedList<Character> temp = cursor.getData();
            for(int j = 0; j<data.length(); j++){
                temp.endAppend((data.get(j)));
            }
            cursor.setData(temp);
        }else{
            //if the clipNum does not exist, we create empty pointers until we reach it
            cursor = top;
            for(int i = 0; i<clipNum; i++){
                if(cursor.getNext()==null){
                    cursor.setNext(new NodeCB(null,null));
                }
                cursor = cursor.getNext();
            }
            try {
                cursor.setData(data);
            }catch(NullPointerException e){
                System.out.println(data.toString());
            }
        }
    }

    /*****************************************************************
     Returns the list stored at a given index
     @param position particular list index
     @return list stored at position
     *****************************************************************/
    public DoubleLinkedList get(int position) {
        cursor = top;
        for (int i = 0; i < position; i++) {
            if(cursor==null){
                return null;
            }
            cursor = cursor.getNext();
        }
        try {
            return cursor.getData();
        } catch (NullPointerException e) {
            return null;
        }
    }
}
