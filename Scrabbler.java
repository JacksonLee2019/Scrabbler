/*
 * Programmer: Jackson Lee
 * Program: Scrabbler
 * Purpose: determine the point value of a word file, given a letter values file
 * Date: 2/26/16
 */
import java.util.Scanner;
import java.io.*;
public class Scrabbler {
    private static String[] letters = new String[26];
    private static int[] points = new int[26];
    private static String[] words;
    private static int[] scores;
    //main method
    public static void main(String[]args) {
        Scrabbler scrabble = new Scrabbler();
        scrabble.getLetterFile();
        scrabble.getWordFile();
    }
    //gets the letter file from user
    private void getLetterFile() {
        System.out.print("Please choose a letter-value file: ");
        Scanner scan = new Scanner(System.in);
        String letterFile = scan.nextLine();
        readLetterFile(letterFile);
    }
    //reads the letter file
    private void readLetterFile(String inFile) {
        try {
            Scanner scan = new Scanner(new File(inFile));
            
            while(scan.hasNext()) {
                for(int i = 0; i < letters.length; i++) {
                    letters[i] = scan.next();
                    points[i] = scan.nextInt();
                }
            }
            
            scan.close();
        } catch(IOException e) {
            System.out.println("Error opening the file " + inFile);
            System.out.print("Program terminated.");
            System.exit(0);
        }
    }
    //gets the word file from the user
    private void getWordFile() {
        System.out.print("Please choose a word file: ");
        Scanner scan = new Scanner(System.in);
        String wordFile = scan.nextLine();
        readWordFile(wordFile);
    }
    //reads the word file
    private void readWordFile(String inFile) {
        try {
            //gets the number of words in the file
            Scanner scan = new Scanner(new File(inFile));
            int numWords = 0;
            while(scan.hasNext()) {
                scan.next();
                numWords++;
            }
            
            scan.close();
            
            //reads the words into the array
            Scanner scanWords = new Scanner(new File(inFile));
            words = new String[numWords];
            while(scanWords.hasNext()) {
                for(int i = 0; i < words.length; i++) {
                    words[i] = scanWords.next();
                }
            }
            
            scanWords.close();
            
            System.out.println("--------------------");
            
            //gets the total number of points ineach word and puts it into an array
            scores = new int[numWords];
            int wordPoints = 0;
            for(int i = 0; i < words.length; i++) {
                for(int j = 0; j < words[i].length(); j++) {
                    for(int k = 0; k < letters.length; k++) {
                        if(Character.toString(words[i].charAt(j)).equals(letters[k])) {
                            wordPoints = wordPoints + points[k];
                        }
                    }
                }
                scores[i] = wordPoints;
                wordPoints = 0;
                
            }
            
            //finds the worst word
            int worstScore = Integer.MAX_VALUE;
            String worstWord = " ";
            for(int i = 0; i < words.length; i++) {
                if(scores[i] < worstScore) {
                    worstScore = scores[i];
                    worstWord = words[i];
                } 
            }
            
            System.out.println("Worst word: " + worstWord + " " + worstScore);
            
            //finds the best word
            int bestScore = 0;
            String bestWord = " ";
            for(int i = 0; i < words.length; i++) {
                if(scores[i] > bestScore) {
                    bestScore = scores[i];
                    bestWord = words[i];
                } 
            }
            
            System.out.println("Best word: " + bestWord + " " + bestScore);
            
            //creates an output file with the data
            File dataFile = new File("output.txt");
            dataFile.createNewFile();
            
            PrintWriter printer = new PrintWriter(dataFile);
            
            for(int i =0; i < words.length; i++) {
                printer.println(words[i] + " " + scores[i]);
            }
            printer.flush();
            printer.close();
        } catch(IOException e) {
            System.out.println("Error opening the file " + inFile);
            System.out.println("Program terminated.");
            System.exit(0);
        }
    }
}