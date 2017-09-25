#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <cmath>
#include <cstring>
#include <sstream>
#include <iomanip>

using namespace std;

bool isAlphabetic(string check){
    /*
     *  check if the check string is alphabetic
     */
    bool alpha = false;
    for (int i = 0; i < check.length(); i++){
        if ((check[i] >= 'a' && check[i] <= 'z') || (check[i] >= 'A' && check[i] <= 'Z')){
            alpha = true;
        }
        else{
            alpha = false;
            break;
        }
    }
    return alpha;
}

bool charIsAlphabetic(char check){
    /*
     *  check if the char check is alphabetic
     */
    bool alpha = false;
    if((check >= 'a' && check <= 'z') || (check >= 'A' && check <= 'Z')){
        alpha = true;
    }
    else {
        alpha = false;;
    }
    return alpha;
}

bool isVowel(char check){
    /*
     *  check if the char is a vowel
     */
    if(check == 'a' || check == 'e' || check == 'i' || check == 'o' || check == 'u' || check == 'y'
            || check == 'A' || check == 'E' || check == 'I' || check == 'O' || check == 'U' || check == 'Y')
        return  true;
    return false;

}

bool isEndofSent(char check){
    if(check == '.' || check == '!' || check == '?' || check == ';' || check == ':')
        return true;
    return false;     
}

double countSyllables(double &sCount, vector<string> &wordArr){
    /*
     *  Counts the syllables in the wordArr, puts count into sCount
     */
    double syllables = 0;
    for(int j = 0; j < wordArr.size(); j++){ 
        string check = wordArr[j];              // get current string from wordArr
        syllables = 0;                          // reset syllable count to 0
        for(int i = 0; i < check.length(); i++){     
            if(check[check.length() - 1] == 'e' && syllables > 1){ // if word ends in e, decrement syllables
                syllables--;
            }
            if(isVowel(check[i]) && !isVowel(check[i+1]) && !isEndofSent(check[i+1])){  
                // if check @ i char is a vowel and there isn't a following vowel, 
                // increment syllables
                syllables++;
            }
        }
        sCount += syllables;
    }

    return sCount; 
}

int countSentences(string filename, vector<string> sentArr){
    /*
     *  Counts the sentences in the file
     */
    ifstream infile;
    infile.open(filename.c_str()); // open the file
    double count = 0;
    string temp;
    char ch;
    while(infile.get(ch)){
        // if the current char is an end-of-sentence marker, 
        // increment count
        if(ch == '.' || ch == '!' || ch == '?' || ch == ';' || ch == ':'){
            count++;
        }
    }
    return count;
}

bool isNum(string str){
    /*
     *  Determine if str has a number in it
     */
    if ((str.find('0') != std::string::npos ||
                str.find('1') != std::string::npos ||
                str.find('2') != std::string::npos ||
                str.find('3') != std::string::npos ||
                str.find('4') != std::string::npos ||
                str.find('5') != std::string::npos ||
                str.find('6') != std::string::npos ||
                str.find('7') != std::string::npos ||
                str.find('8') != std::string::npos ||
                str.find('9') != std::string::npos) &&
            !isAlphabetic(str)){
        return true;
    }

    return false;
}

double getAlpha(string filename, vector<string> wordArr){
    /*
     *  returns the alpha portion of the final calculation
     */ 
    ifstream infile;
    infile.open(filename.c_str()); // open the file
    double count = 0, alpha = 0, syllables = 0, sCount = 0;
    string temp;
    while(getline(infile, temp, ' ')){
        // delimit getline with a ' '
        stringstream ss(temp);      // create a stringstream for the current string temp
        while(getline(ss, temp, '\n')){ 
            if(!isNum(temp)){               // make sure temp isn't a number
                wordArr.push_back(temp);    // push temp into wordArr
                count++;                    // inc count
            }   
        }
    }
    syllables = countSyllables(sCount, wordArr); // get the syllable count
    alpha = syllables / count;                   // calculate alpha 
    return alpha;
}

double countWords(string filename, vector<string> wordArr){
    /*
     *  counts the words in the file 
     *  and puts them into wordArr
     */
    ifstream infile;
    infile.open(filename.c_str());
    double count = 0, alpha = 0;
    string temp;
    while(getline(infile, temp, ' ')){
        if(!isNum(temp)){
            wordArr.push_back(temp); // push temp into wordArr
            count++;
        }
    }
    return count;
}

int main(int argc, char *argv[]){
    double alpha, beta, index, gindex = 0;
    string filename;
    if(argv[1] == NULL){
        cout << "Please enter a file name (must exist in current directory): ";
        cin >> filename;                    // get filename from user
        argv[1] = new char[filename.length() + 1];      
        strcpy(argv[1], filename.c_str());              // put filename into argv[1]
    }
    else{
        filename = argv[1];
    }
    cout << "File being used: " << filename << endl;
    vector <string> wordArr;
    vector <string> sentArr;
    alpha = getAlpha(filename, wordArr);                                        // set alpha
    beta = countWords(filename, wordArr) / countSentences(filename, sentArr);   // calculate and set beta
    index = 206.835 - alpha*84.6 - beta*1.015;                                  // calculate index
    gindex = round((alpha*11.8 + beta*0.39 - 15.59) * 10.0) / 10.0;             // calculate and round gindex
    cout << "Index = " << round(index) << "\n";                                 // round and print index
    cout << "Grade Level Index = " << gindex << "\n";                           // print grade index
    return 0;
}




