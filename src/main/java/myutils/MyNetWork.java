package myutils;

import com.pff.*;
import java.util.*;
import com.mongodb.*;

// my class is MyNetWork. 
public class MyNetWork {

    // i declare a private variable (final for var) which is a string and named PST
	private final String PST;

	// I define a builder (constructeur) which takes the same name than the class 
	public MyNetWork(String pstFilename){
		this.PST = pstFilename;
	}

    


    //programme MAIN
    public static void main(String[] args) {
        //I get the first arg with which my class will be called
        String pstFilename = args[0];

        // I build an object from the MyNetWork class which takes this PST as an argument
        // and I call on this object my function GetInfo
        MyNetWork helloObj = new MyNetWork(pstFilename);
        helloObj.GetInfo();
    }

   // public static void GetInfo(String filename) {
    public  void GetInfo() {
        try {
            PSTFile pstFile = new PSTFile(PST);
            // show pst name 
            System.out.println(pstFile.getMessageStore().getDisplayName());

            // show rootfolder
            //processFolder(pstFile.getRootFolder());
              getEmailList(pstFile.getRootFolder());

        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    // processFolder permet de naviguer dans les subfolders.
    int depth = -1;
    public void processFolder(PSTFolder folder)
            throws PSTException, java.io.IOException
    {
        depth++;
        // the root folder doesn't have a display name
        if (depth > 0) {
            printDepth();
            System.out.println(folder.getDisplayName());
        }

        // go through the folders...
        if (folder.hasSubfolders()) {
            Vector<PSTFolder> childFolders = folder.getSubFolders();
            for (PSTFolder childFolder : childFolders) {
                processFolder(childFolder);
            }
        }

        // and now the emails for this folder
        if (folder.getContentCount() > 0) {
            depth++;
            PSTMessage email = (PSTMessage)folder.getNextChild();
            while (email != null) {
                printDepth();
                System.out.println("Email: "+email.getSubject());
                email = (PSTMessage)folder.getNextChild();
            }
            depth--;
        }
        depth--;
    }

    // get list of emails.
    int depth2 = -1;
    public void getEmailList(PSTFolder folder)
            throws PSTException, java.io.IOException
    {
        depth2++;
        // go through the folders...
        if (folder.hasSubfolders()) {
            Vector<PSTFolder> childFolders = folder.getSubFolders();
            for (PSTFolder childFolder : childFolders) {
                getEmailList(childFolder);
            }
        }

        // MongoDB connection
        MongoClient mongo = new MongoClient( "localhost" , 27017 );
        DB db = mongo.getDB("outlook");
        DBCollection contact = db.getCollection("contact");


        // and now the emails for this folder
        if (folder.getContentCount() > 0) {
            depth2++;
            PSTMessage email = (PSTMessage)folder.getNextChild();

            while (email != null) {
                // write title of email
                System.out.println("Email: "+email.getSubject());
                
                //for each email, get vector of recipients (including sender)
                System.out.println(">>Contact: "+email.getSenderName());
                int nbContact = email.getNumberOfRecipients();
                for (int x = 0; x <= nbContact-1; x++) {
                    System.out.println(">>Contact: "+email.getRecipient(x).getDisplayName());

                    // feed MongoDB
                    BasicDBObject document = new BasicDBObject();
                    document.put("name", email.getRecipient(x).getDisplayName());
                    document.put("email", email.getSubject());
                    document.put("body", email.getBody());

                    contact.insert(document);
                }
                //System.out.println(">>: "+email.getRecipient(0).getDisplayName());

                email = (PSTMessage)folder.getNextChild();
            }
            depth2--;
        }
        depth2--;
    }


    // get contacts
    public void GetNetWork(PSTContact contact)
            throws PSTException, java.io.IOException
    {
        System.out.println("");
    }




    public void printDepth() {
        for (int x = 0; x < depth-1; x++) {
            System.out.print(" | ");
        }
        System.out.print(" |- ");
    }

}

