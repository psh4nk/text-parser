import java.util.*;
import java.io.*;

public class flesch
{
    public static void main(String[] args) throws FileNotFoundException {

        double alpha, beta, index, gindex = 0;  // instantiate necessary variables

        String filename;                        // holds filename

        // Create scanner that reads in the file, open 
        // file either from command line or from user input
        Scanner in = new Scanner(System.in);
        if (args.length == 0){
            System.out.print("Please enter a file name (must exist in current directory): ");
            filename = in.nextLine();
        }
        else{
            filename = args[0];
        }

        System.out.print("\nFile being used: ");
        System.out.print(filename);
        System.out.print("\n");

        ArrayList<String> wordArr = new ArrayList<String>(50);      // set up arraylist for all words
        ArrayList<String> sentArr = new ArrayList<String>(50);      // Set up arraylist used to count sentences

        alpha = getAlpha(filename, wordArr);                // Calculate alpha
        beta = countWords(filename, wordArr) / countSentences(filename, sentArr); // Calculate beta

        index = 206.835 - alpha * 84.6 - beta * 1.015;      // Calculate index
        gindex = alpha * 11.8 + beta * 0.39 - 15.59;        // Calculate grade index

        // Print the stuff
        System.out.print("Index = ");
        System.out.print(Math.round(index));
        System.out.print("\n");
        System.out.print("Grade Level Index = ");
        System.out.print((Math.round(gindex)  * 10) / 10.0);
        System.out.print("\n\n");
    }

    public static boolean isAlphabetic(String check){
        /*
         *  checks string to see if it's alphabetic
         */
        char[] chars = check.toCharArray();             // turn string into char array 
        for (char c : chars){                           // for all chars in the string
            if(!Character.isLetter(c)) {                // if curr is not a letter, then
                return false;                           // return false
            }
        }
        return true;
    }

    public static boolean charIsAlphabetic(char check){
        /*
         *  checks char to see if it's alphabetic
         */
        if(!Character.isLetter(check))
            return false;
        return true;    

    }

    public static boolean isVowel(char check){
        /*
         *  Checks char to see if it's a vowel
         */
        if (check ==  'a' || check ==  'e' || check ==  'i' || check ==  'o' || check ==  'u' || check ==  'y' || check ==  'A' || check ==  'E' || check ==  'I' || check ==  'O' || check ==  'U' || check ==  'Y'){
            return true;
        }
        return false;

    }

    public static int countSyllables(String word) {
    /*
     *  Count all syllables within the arraylist of words
     */
        String input = word.toLowerCase(); // convert string to lower case
        int syllables = 0, eSatEnd=0;       

        int i = input.length() - 1;         // set i to length-1 to not go out of bounds

        // count all the e's in the end
        while (i >= 0 && input.charAt(i) == 'e') {
            i--;
            eSatEnd++;
        }
        
        // if there's an e at the end, there's automatically one syllable in the word
        if (eSatEnd == 1) {
            syllables = 1;
        }

        boolean vowelBefore = false;        // holds bool of if vowel is adjacent to curr vowel
        while (i >= 0) {
            if (isVowel(input.charAt(i))) { // if there's a vowel here
                if (!vowelBefore) {         // and if no vowel before, 
                    syllables++;            // increment
                    vowelBefore = true;    
                }
            } else {
                vowelBefore = false;       
            }
            i--;
        }
        return syllables;
    }



    public static double countSentences(String filename, ArrayList<String> sentArr){
        /*
         *  Count all sentences within the infile using end-of-sentence markers
         */

        Scanner infile = null;
        try{
            infile = new Scanner(new File(filename));
        }
        catch (FileNotFoundException ex){
            System.out.println("File Not Found.");
            System.exit(0);
        }	
        double count = 0;

        while (infile.hasNextLine()){                       // While there's no EOF
            Scanner line = new Scanner(infile.nextLine()); 
            line.useDelimiter("");
            while(line.hasNext()){
                String ch = line.next();                    // get next string in infile's current line
                if (ch.contains(".") || ch.contains("!") || ch.contains("?") || ch.contains(";") || ch.contains(":")){
                    count++;
                }
            }
        }
        return count;
    }

    public static boolean isNum(String str){
        /*
         *  determine if str contains a number
         */
        
        if ((str.indexOf('0') != -1 || str.indexOf('1') != -1 || str.indexOf('2') != -1 || str.indexOf('3') != -1 || str.indexOf('4') != -1 || str.indexOf('5') != -1 || str.indexOf('6') != -1 || str.indexOf('7') != -1 || str.indexOf('8') != -1 || str.indexOf('9') != -1) && !isAlphabetic(str))
        {
            return true;
        }

        return false;
    }

    public static double getAlpha(String filename, ArrayList<String> wordArr){
        /*
         *  returns the alpha portion of the index and grade index calculation
         */
    
        double count = 0, alpha = 0, syllables = 0, sCount = 0;
        Scanner infile = null;
        try{
            infile = new Scanner(new File(filename));  
        }
        catch (FileNotFoundException ex){
            System.out.println("File Not Found.");
            System.exit(0);
        }
        String temp;
        while(infile.hasNextLine()){
            Scanner line = new Scanner(infile.nextLine());
            while(line.hasNext()){
                wordArr.add(line.next());               // add curr string to the array of words
                count++;
            }
        }
        for(int i = 0; i < wordArr.size(); i++){
            syllables+=((double)countSyllables(wordArr.get(i)));    // add the int returned from the syllables method to the syllable count
        }
        alpha = syllables / count; 
        return alpha;
    }

    public static double countWords(String filename, ArrayList<String> wordArr){
        Scanner infile = new Scanner(System.in);
        try{
            infile = new Scanner(new File(filename));
        }	
        catch (FileNotFoundException ex){
            System.out.println("File Not Found.");
            System.exit(0);
        }	
        double count = 0, alpha = 0;
        String temp;

        while (infile.hasNext()){
            temp = infile.next();               // get next word from file
            wordArr.add(temp);                  // add curr string to array of words
            count++;                            // inc amount of words
        }

        return count;
    }

}
