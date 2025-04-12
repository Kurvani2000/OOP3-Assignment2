package implementations;
import java.io.*;

import exceptions.EmptyQueueException;

/**
 * Program to check an XML file for syntax errors.
 * @author Yichen Hao
 * Team Impa, CPRG304-A
 * 
 * Apr. 10 2025
 */
public class XMLValidator {

	/**
	 * Executable program to check an XML file for syntax errors.
	 * @param args Path to the XML file.
	 * @throws EmptyQueueException if an internal error causes an attempt
	 * to read from an empty queue.
	 */
    public static void main(String[] args) throws EmptyQueueException {
    	
    	if (args.length == 0) {
            System.err.println("Error: No file provided.");
            return;
    	}
    	
    	String arg = args[0]; //ignore extra args
    	
    	File filePath = new File(arg);
    	if (!filePath.isFile()) {
            System.err.println("Error: File not found.");
            return;    		
    	}

		try {
		    // Read XML from the file and validate it
		    String xml = readXMLFromFile(filePath);
		    validateXML(xml);
		} catch (IOException e) {
		    System.out.println("Error reading the XML file: " + e.getMessage());
		}
    	
    	//Old code for testing
//        // Specify the folder path where the XML files are located
//        String folderPath = "res";
//
//        File folder = new File(folderPath);
//        if (folder.isDirectory()) {
//            // Get all XML files in the folder
//            File[] xmlFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".xml"));
//
//            if (xmlFiles != null && xmlFiles.length > 0) {
//                // Process each XML file
//                for (File xmlFile : xmlFiles) {
//                    System.out.println("Checking file: " + xmlFile.getName());
//                    try {
//                        // Read XML from the file and validate it
//                        String xml = readXMLFromFile(xmlFile);
//                        validateXML(xml);
//                    } catch (IOException e) {
//                        System.out.println("Error reading the XML file: " + e.getMessage());
//                    }
//                }
//            } else {
//                System.out.println("No XML files found in the specified folder.");
//            }
//        } else {
//            System.out.println("The specified path is not a directory.");
//        }
    }

    // Method to read the XML content from a file
    /**
     * Read the contents of a text file.
     * @param file XML file to be processed.
     * @return String containing the file contents.
     * @throws IOException if the file is not found.
     */
    public static String readXMLFromFile(File file) throws IOException {
        StringBuilder xmlContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                xmlContent.append(line).append("\n");
            }
        }
        return xmlContent.toString();
    }

    // Method to validate the XML
    /**
     * Checks an XML file for syntax errors and prints any errors found to console.
     * @param xml String containing a whole XML file.
     * @throws EmptyQueueException If an internal error occurs.
     */
    public static void validateXML(String xml) throws EmptyQueueException {
        MyStack<String> stack = new MyStack<>();
        MyQueue<String> errorQ = new MyQueue<>();
        MyQueue<String> extrasQ = new MyQueue<>();

        // Split the XML content into individual tags
        String[] tags = xml.split("(?=<)|(?<=>)");

        for (String tag : tags) {
            tag = tag.trim();

            if (tag.startsWith("<") && tag.endsWith("/>")) {
                // Self-closing tag, ignore
                continue;
            }
            
            if (tag.startsWith("<?") && tag.endsWith("?>")) {
                // Processing instruction, ignore
                continue;
            }

            if (tag.startsWith("<") && !tag.startsWith("</")) {
                // Start tag
                String tagName = extractTagName(tag);
                stack.push(tagName);
            } 
            else if (tag.startsWith("</")) {
                // End tag
                String tagName = extractTagName(tag);

                if (!stack.isEmpty() && stack.peek().equals(tagName)) {
                    // End tag matches the top of the stack
                	// Pop stack
                    stack.pop();
                } else if (!errorQ.isEmpty() && tagName.equals(errorQ.peek())) {
                	// Does not match top of stack
                	// Matches first in errorQ
                	// Dequeue
                    errorQ.dequeue();
                } else if (stack.isEmpty()) {
                	// Does not match top of stack
                	// Does not match first in errorQ
                    // Stack is empty
                	// Enqueue
                    errorQ.enqueue(tagName);
                } else {
                	// Does not match top of stack
                	// Does not match first in errorQ
                    // Stack is not empty
                    // Search stack for matching Start Tag
                    if (stack.contains(tagName)) {
                        // Pop matching elements from stack to errorQ
                        while (!stack.peek().equals(tagName)) {
                            errorQ.enqueue(stack.pop());
                            // Report as error
                            // System.out.println("Unmatched tag while scanning tags: " + tagName);
                        }
                        stack.pop(); // pop the matching start tag
                    } else {
                        // If no match found, add to extrasQ
                        extrasQ.enqueue(tagName);
                    }
                }
            }
        }

        // Handle remaining unmatched start tags in stack
        while (!stack.isEmpty()) {
            errorQ.enqueue(stack.pop());
        }

        // Report errors
        reportErrors(errorQ, extrasQ);
    }

    /**
     * Helper method to extract the tag name of an XML tag
     * by removing enclosing brackets and parameters.
     * @param tag An XML tag.
     * @return Name of the tag.
     */
    // Method to extract the tag name from a tag string (e.g., <Language> -> Language, </Language> -> Language)
    private static String extractTagName(String tag) {
        return tag.replaceAll("<(/?)(\\w+)(.*)>", "$2");
    }

    /**
     * Helper method to process two queues of mismatched tags
     * and print any genuine errors to the screen.
     * @param errorQ Queue containing unmatched opening tags.
     * @param extrasQ Queue containing unmatched closing tags.
     * @throws EmptyQueueException If an internal error causes an attempt
     * to read from an empty queue.
     */
    // Method to report the errors found in errorQ and extrasQ
    private static void reportErrors(MyQueue<String> errorQ, MyQueue<String> extrasQ) throws EmptyQueueException {    	
	    

        System.out.println("Looking for XML syntax errors...");   
    	boolean errorFound = false;
    	
    	//Perform until both queues empty
    	while (!(errorQ.isEmpty() && extrasQ.isEmpty())) {
    		// If exactly one queue is empty, report all errors
	    	if ((errorQ.isEmpty() && !extrasQ.isEmpty()) || (!errorQ.isEmpty() && extrasQ.isEmpty())) {
	       	
	        	// Report errors from errorQ
	            while (!errorQ.isEmpty()) {
	                System.out.println("Error: Unmatched opening tag " + errorQ.dequeue());
	                errorFound = true;
	            }
	
	            // Report errors from extrasQ
	            while (!extrasQ.isEmpty()) {
	                System.out.println("Error: Extra closing tag " + extrasQ.dequeue());
	                errorFound = true;
	            }
	    	}
	    	// If both queues not empty
	    	// Peek both queues
	    	// dequeue if match
	    	// report error if not
	    	else if (!errorQ.isEmpty() && !extrasQ.isEmpty()) {
	    		if (errorQ.peek().equals(extrasQ.peek())) {
	    			errorQ.dequeue();
	    			extrasQ.dequeue();
	    		}
	    		else {
	                System.out.println("Error: Unmatched opening tag " + errorQ.dequeue());    	
	                errorFound = true;		
	    		}
	    	}
    	}
    	
    	if (!errorFound) {
            System.out.println("No errors found.");    	    		
    	}
    }
}