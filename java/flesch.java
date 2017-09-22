import java.util.*;
import java.io.*;

public class flesch{

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
            if(!Character.isLetter(c)) {
                return false;
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
    
    public static double countSyllables(double sCount, ArrayList<String> wordArr){
        /*
         *  Count all syllables within the arraylist of words
         */
        double syllables = 0;
        for (int j = 0; j < wordArr.size(); j++){
            String check = wordArr.get(j);          // get current string at wordArr[j]
            syllables = 0;                          // set individual string syllable count to 0
            for (int i = 0; i < check.length() - 1; i++){
                if (isVowel(check.charAt(i))){      // check if char @ check[i] is a vowel
                        syllables += 1.044;
                }
            }
            sCount += syllables;                    // add current string syl count to total syl count
        }

        return sCount;
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
        
        while (infile.hasNextLine()){                   // While 
            Scanner line = new Scanner(infile.nextLine());
            line.useDelimiter("");
            while(line.hasNext()){
                String ch = line.next();
                if (ch.contains(".") || ch.contains("!") || ch.contains("?") || ch.contains(";") || ch.contains(":")){
                    count++;
                }
            }
        }
        return count;
    }

    public static boolean isNum(String str){
        if ((str.indexOf('0') != -1 || str.indexOf('1') != -1 || str.indexOf('2') != -1 || str.indexOf('3') != -1 || str.indexOf('4') != -1 || str.indexOf('5') != -1 || str.indexOf('6') != -1 || str.indexOf('7') != -1 || str.indexOf('8') != -1 || str.indexOf('9') != -1) && !isAlphabetic(str))
        {
            return true;
        }

        return false;
    }

    public static double getAlpha(String filename, ArrayList<String> wordArr){
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
                wordArr.add(line.next());
                count++;
            }
        }
        syllables = countSyllables(sCount, wordArr);
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
            temp = infile.next();
            wordArr.add(temp);
            count++;
        }

        return count;
    }

}
