import java.util.*;
import java.io.*;

public class flesch{

    public static void main(String[] args) throws FileNotFoundException {
        double alpha, beta, index, gindex = 0;
        String filename;
        Scanner in = new Scanner(System.in);
        if (args.length == 0){
            System.out.print("Please enter a file name (must exist in current directory): ");
            filename = in.nextLine();
        }
        else{
            filename = args[0];
        }
        System.out.print("File being used: ");
        System.out.print(filename);
        System.out.print("\n");
        ArrayList<String> wordArr = new ArrayList<String>(50);
        ArrayList<String> sentArr = new ArrayList<String>(50);
        alpha = getAlpha(filename, wordArr);
        beta = countWords(filename, wordArr) / countSentences(filename, sentArr);
        System.out.print("Alpha: ");
        System.out.print(alpha);
        System.out.print(", Beta: ");
        System.out.print(beta);
        System.out.print("\n");
        index = 206.835 - alpha * 84.6 - beta * 1.015;
        gindex = alpha * 11.8 + beta * 0.39 - 15.59;
        System.out.print("Index = ");
        System.out.print(Math.round(index));
        System.out.print("\n");
        System.out.print("Grade Level Index = ");
        System.out.print(gindex);
        System.out.print("\n");
    }

    public static boolean isAlphabetic(String check){
        char[] chars = check.toCharArray();
        for (char c : chars){
            if(!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean charIsAlphabetic(char check){
        if(!Character.isLetter(check))
            return false;
        return true;    
   
    }

    public static boolean isVowel(char check){
        if (check ==  'a' || check ==  'e' || check ==  'i' || check ==  'o' || check ==  'u' || check ==  'y' || check ==  'A' || check ==  'E' || check ==  'I' || check ==  'O' || check ==  'U' || check ==  'Y'){
            return true;
        }
        return false;

    }

    public static boolean isEndofSent(char check){
        if (check == '.' || check == '!' || check == '?' || check == ';' || check == ':'){
            return true;
        }
        return false;
    }

    public static double countSyllables(double sCount, ArrayList<String> wordArr){
        double syllables = 0;
        for (int j = 0; j < wordArr.size(); j++){
            String check = wordArr.get(j);
            syllables = 0;
            for (int i = 0; i < check.length() - 1; i++){

                //System.out.print("Curr word: ");
                //System.out.println(check);
                
                if (check.charAt(check.length() - 1) == 'e' && syllables > 1){
                    //System.out.print("Last char: ");
                    //System.out.print(check.charAt(check.length() - 1));
                    //System.out.print("\n");
                    syllables--;
                }
                if (isVowel(check.charAt(i))){ // && !isVowel(check.charAt(i - 1))){
                        syllables++;
                        //System.out.println(check);
                }
            }
            //System.out.print("This word has ");
            //System.out.print(syllables);
            //System.out.println(" syllables");
            sCount += syllables;
        }

        return sCount;
    }

    public static double countSentences(String filename, ArrayList<String> sentArr){
        Scanner infile = null;
        try{
            infile = new Scanner(new File(filename));
        }
        catch (FileNotFoundException ex){
            System.out.println("File Not Found.");
            System.exit(0);
        }	
        double count = 0;
        String temp, ch;
        while (infile.hasNextLine()){
            Scanner line = new Scanner(infile.nextLine());
            line.useDelimiter("");
            while(line.hasNext()){
                ch = line.next();
                //System.out.println(ch);
                if (ch.contains(".") || ch.contains("!") || ch.contains("?") || ch.contains(";") || ch.contains(":")){
                    count++;
                }
            }
        }
        System.out.print("Sentences: ");
        System.out.print(count);
        System.out.print("\n");
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
        System.out.print("Words: ");
        System.out.print(count);
        System.out.print("\n");
        System.out.print("Syllables: ");
        System.out.print(syllables);
        System.out.print("\n");
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
            //if (!isNum(temp))
            //{
            wordArr.add(temp);
            count++;
            //System.out.print(temp);
            //}
        }

        return count;
    }

}
