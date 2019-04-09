package encrypt;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*****************************************************************
 Unmixes a string that has been mixed given the mixed-up string
 and a filename containing the undo commands
 @author Cade Snuffer
 *****************************************************************/
public class UnMix {

    /** Double linked list for storing the mixed-up message */
    private DoubleLinkedList<Character> message;

    /*****************************************************************
     Constructor reads a given mixed-up message into the double
     linked list
     @param userMessage Given mixed-up message
     *****************************************************************/
    public UnMix(String userMessage) {
        message = new DoubleLinkedList<Character>();
        for(int i = 0; i < userMessage.length(); i++){
            message.endAppend(userMessage.charAt(i));
        }
    }

    /*****************************************************************
     Runs the unmix program by calling unMixture and passing the undo
     filename and the mixed up message
     *****************************************************************/
    public static void main(String[] args) {
        UnMix v = new UnMix(args[1]);
        v.unMixture(args[0],args[1]);
    }

    /*****************************************************************
     Processes a single, given undo command
     @param command One-line string from the undo commands
     *****************************************************************/
    public String processCommand(String command) {
        Scanner scan = new Scanner(command);
        try {
            String[] com = command.split("~", 3);
            switch (com[0].charAt(0)) {
                case 'a':
                    message.remove(Integer.parseInt(com[2]));
                    break;
                case 'r':
                    message.addAtIndex(com[1].charAt(0), Integer.parseInt(com[2]));
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error in command!  Problem!!!! in undo commands");
            e.printStackTrace();
            System.exit(0);
        }
        finally {
            scan.close();
        }
        System.out.println(message.toString());
        return message.toString();
    }

    private void unMixture(String filename, String userMessage) {
        String original = UnMixUsingFile (filename, userMessage);
        System.out.println ("The Original message was: " + original);
    }

    /*****************************************************************
     Reads undo commands from given file, reverses them, and then
     processes each line from the undo commands
     @param filename Given filename that stores undo commands
     @param userMessage Given mixed-up string
     @return userMessage Unmixed string
     *****************************************************************/
    public String UnMixUsingFile(String filename, String userMessage) {
        Scanner scanner = null;
        Scanner scam = null;
        String undoCommands = "";
        try {
            scanner = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (scanner.hasNext()) {
            String command = scanner.nextLine();
            undoCommands = command+"\n"+undoCommands;
        }
        scanner.close();
        scam = new Scanner(undoCommands);
        while(scam.hasNext()){
            String command = scam.nextLine();
            userMessage = processCommand(command);
        }
        scam.close();
        System.out.print(""+undoCommands);
        return userMessage;
    }
}
