package encrypt;

import java.io.*;
import java.util.Random;
import java.util.Scanner;

/*****************************************************************
 Mixes a given string using user commands from the command
 line
 @author Cade Snuffer
 *****************************************************************/
public class Mix {

    /** Double linked list for storing user message */
    private DoubleLinkedList<Character> message;

    /** Linked list for storing clipboards */
    private clipBdLinkedList clipBoards;

    /** Stores the commands to un-mix the message */
    private String undoCommands;

    /** Stores the input user message to be mixed */
    private String userMessage;

    /** Scanner object for user-input mix commands */
    private Scanner scan;

    /** Stores specific command from user */
    private String command;

    /*****************************************************************
     Runs the mix program by loading user message, and running
     the mixture function
     *****************************************************************/
    public static void main(String[] args) {
        Mix mix = new Mix();
        mix.userMessage = args[0];
        System.out.println (mix.userMessage);
        mix.mixture();
    }

    /*****************************************************************
     Instantiates the scanner, message list, clipboard list, and
     undo commands
     *****************************************************************/
    public Mix() {
        scan = new Scanner(System.in);
        message = new DoubleLinkedList<Character>();
        clipBoards = new clipBdLinkedList();
        undoCommands = "";
    }

    /*****************************************************************
     Reads a given string into a double linked list and
     returns the list
     @param s Given string
     @return lst New double linked list containing the string
     *****************************************************************/
    public DoubleLinkedList<Character> readToList(String s){
        DoubleLinkedList<Character> lst =  new DoubleLinkedList<Character>();
        for(int i = 0; i <s.length(); i++){
            lst.endAppend(s.charAt(i));
        }
        return lst;
    }

    /*****************************************************************
     Displays user-message and processes commands to mix message
     *****************************************************************/
    private void mixture() {
        message = readToList(userMessage);
        do {
            DisplayMessage();
            System.out.print("Command: ");
            DoubleLinkedList<Character> currMessage =  message;

            String currUndoCommands = undoCommands;

            try {
                command = scan.next();

                switch (command.charAt(0)) {
                    default:
                        System.out.println("ERROR: Invalid command");
                        break;
                    case 'Q':
                        System.out.print("Save file name: ");
                        save(scan.next());
                        System.out.println ("Final mixed up message: \"" + message+"\"");
                        System.exit(0);
                    case 'b':
                        try {
                            String s = scan.next();
                            int i = scan.nextInt();
                            insert(s, i);
                        }catch(NumberFormatException e){
                            System.out.println("FORMAT ERROR");
                        }
                        break;
                    case 't':
                        try {
                            int start = scan.nextInt();
                            int stop = scan.nextInt();
                            remove(start, stop);
                        }catch(NumberFormatException e){
                            System.out.println("FORMAT ERROR");
                        }
                        break;
                    case'd':
                        try {
                            Character s = scan.next().charAt(0);
                            removeInstances(s);
                        }catch(NumberFormatException e){
                            System.out.println("FORMAT ERROR");
                        }
                        break;
                    case 'c':
                        try {
                            copy(scan.nextInt(), scan.nextInt(), scan.nextInt());
                        }catch(NumberFormatException e){
                            System.out.println("FORMAT ERROR");
                        }
                        break;
                    case 'r':
                        Character old = scan.next().charAt(0);
                        Character rep = scan.next().charAt(0);
                        swap(old, rep);
                        break;
                    case 'x':
                        try {
                            int start = scan.nextInt();
                            int stop = scan.nextInt();
                            int clipNum = scan.nextInt();
                            cut(start, stop, clipNum);
                        }catch(NumberFormatException e){
                            System.out.println("FORMAT ERROR");
                        }
                        break;
                    case 'p':
                        try {
                            int start = scan.nextInt();
                            int clipNum = scan.nextInt();
                            paste(start, clipNum);
                        }catch (NumberFormatException e){
                            System.out.println("FORMAT ERROR");
                        }
                        break;
                    case 'z':
                        randomize();
                    case 'h':
                        helpPage();
                        break;
                }
                scan.nextLine();
                System.out.println("For demonstration purposes only:\n" + undoCommands);
            }
            catch (Exception e ) {
                System.out.println ("Error on input, previous state restored.");
                e.printStackTrace();
                scan = new Scanner(System.in);
                undoCommands = currUndoCommands;
                message = currMessage ;
            }
        } while (true);
    }

    /*****************************************************************
     Removes all instances of a given character
     in the user message
     @param s Character to be removed
     *****************************************************************/
    private void removeInstances(Character s){
        if(s == '~'){
            s = ' ';
        }
        for(int i = message.length()-1; i>=0; i--){
            if(message.get(i).equals(s)){
                undoCommands = undoCommands+"r~"+message.get(i)+"~"+i+"\n";
            }
        }
        message.removeInstances(s);
    }

    /*****************************************************************
     Inserts a given string at a given index in user message
     @param s Given string to be inserted
     @param i Specific list index
     *****************************************************************/
    private void insert(String s, int i){
        for(int j = 0; j < s.length(); j++){
            if(s.charAt(j)=='~'){
                message.addAtIndex(' ', (i+j));
                undoCommands = undoCommands + "a~"+' '+"~"+(i+j)+"\n";
            }else{
                message.addAtIndex(s.charAt(j), (i+j));
                undoCommands = undoCommands + "a~"+s.charAt(j)+"~"+(i+j)+"\n";
            }
        }
    }

    /*****************************************************************
     Swaps all instances of a given character with another given
     character in user message
     @param old Represents character to-be-replaced
     @param rep Represents character to-replace old
     *****************************************************************/
    private void swap(Character old, Character rep){
        for(int i = 0; i<message.length(); i++){
            if(message.get(i).equals(old)){
                undoCommands = undoCommands+"r~"+message.get(i)+"~"+i+"\n"+"a~"+rep+"~"+i+"\n";
            }
        }
        message.swapAll(old, rep);
    }

    /*****************************************************************
     Runs a series of b, t, r, and d commands 0-10 times on
     user message
     *****************************************************************/
    private void randomize(){
        String alpha = message.toString();
        Random r = new Random();
        for(int i = 0; i<r.nextInt(10);i++){
            int choice = r.nextInt(4);
            switch(choice){
                case 0:
                    insert(alpha.substring(r.nextInt(alpha.length())), r.nextInt(message.length()));
                    break;
                case 1:
                    int g = r.nextInt(message.length());
                    remove(g, g);
                    break;
                case 2:
                    swap(message.get(r.nextInt(message.length())), alpha.charAt(r.nextInt(alpha.length()-1)));
                    break;
                case 3:
                    removeInstances(message.get(r.nextInt(message.length()-1)));
                    break;
            }
        }
    }

    /*****************************************************************
     Removes a given index-range of the user message (inclusive)
     @param start Message index
     @param stop Message index
     *****************************************************************/
    private void remove(int start, int stop) {
        for(int i = stop; i>=start; i--){
            undoCommands = undoCommands + "r~"+message.get(i)+"~"+i+"\n";
        }
        message.removeRange(start,stop);
    }

    /*****************************************************************
     Removes a given index-range of the user message, and copies
     it to given a clipboard number
     @param start Message index start (inclusive)
     @param stop Message index stop (inclusive)
     @param clipNum Clipboard number identity
     *****************************************************************/
    private void cut(int start, int stop, int clipNum) {
        DoubleLinkedList cut = new DoubleLinkedList();
        for(int i = start; i <= stop; i++){
            cut.endAppend(message.get(i));
        }
        clipBoards.addClip(cut, clipNum);
        remove(start, stop);
    }

    /*****************************************************************
     Copies a given index-range of the user message to a given
     clipboard number
     @param start Message index start (inclusive)
     @param stop Message index stop (inclusive)
     @param clipNum Clipboard number identity
     *****************************************************************/
    private void copy(int start, int stop, int clipNum) {
        DoubleLinkedList<Character> copy = new DoubleLinkedList<Character>();
        for(int i = start; i <= stop; i++){
            copy.endAppend(message.get(i));
        }
        clipBoards.addClip(copy, clipNum);
    }

    /*****************************************************************
     Pastes the contents of an entire given clipboard to the user
     message, starting at a given index
     @param start Message index start (inclusive)
     @param clipNum Clipboard number identity
     *****************************************************************/
    private void paste(int start, int clipNum) {
        if(clipBoards.get(clipNum)==null){
            throw new NullPointerException("Clipboard nonexistant: "+clipNum);
        }
        for(int i = 0; i<clipBoards.get(clipNum).length(); i++){
            undoCommands = undoCommands+"a~"+clipBoards.get(clipNum).get(i)+"~"+(start+i)+"\n";
        }
        DoubleLinkedList paste = clipBoards.get(clipNum);
        for(int i = 0; i<clipBoards.get(clipNum).length(); i++){
            message.addAtIndex((Character)(paste.get(i)), start+i);
        }
    }

    /*****************************************************************
     Displays the user message along with their index numbers
     *****************************************************************/
    private void DisplayMessage() {
        System.out.print ("Message:\n");
        userMessage = message.toString();

        for (int i = 0; i < userMessage.length(); i++)
            System.out.format ("%3d", i);
        System.out.format ("\n");
        for (char c : userMessage.toCharArray())
            System.out.format("%3c",c);
        System.out.format ("\n");
    }

    /*****************************************************************
     Saves the undo commands to a given filename
     *****************************************************************/
    public void save(String filename) {

        PrintWriter out = null;
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(filename)));

        } catch (IOException e) {
            e.printStackTrace();
        }

        out.println(undoCommands);
        out.close();
    }

    /*****************************************************************
     Prints out a help page that shows valid commands.
     *****************************************************************/
    private void helpPage() {
        System.out.println("Commands:");
        System.out.println("\tQ filename	means, quit! " + " save to filename" );
        System.out.println("\t  ~ is used for a space character" );
        System.out.println("\t .... etc" );
        System.out.println("\th\tmeans to show this help page");
        System.out.println("\tb s #\tmeans insert String s at index #");
        System.out.println("\tt # *\tmeans remove from # to * (inclusive)");
        System.out.println("\td s\tmeans remove all instances of the character s");
        System.out.println("\tr s *\tmeans replace all s with *");
        System.out.println("\tc # * &\tmeans copy # to * (inclusive) to clipboard &");
        System.out.println("\tx # * &\tmeans copy and remove # to *(inclusive) to clipboard &");
        System.out.println("\tp # &\tmeans paste all of clipboard & to message, starting at #");
    }
}