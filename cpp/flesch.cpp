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
    double syllables = 0;
    for(int j = 0; j < wordArr.size(); j++){ 
        string check = wordArr[j];
        syllables = 0;
        for(int i = 0; i < check.length(); i++){     
            if(check[check.length() - 1] == 'e' && syllables > 1){
                syllables--;
            }
            if(isVowel(check[i]) && !isVowel(check[i+1]) && !isEndofSent(check[i+1])){
                syllables++;
            }
        }
        sCount += syllables;
    }

    return sCount; 
}

int countSentences(string filename, vector<string> sentArr){
    ifstream infile;
    infile.open(filename.c_str());
    double count = 0;
    string temp;
    char ch;
    while(infile.get(ch)){
        if(ch == '.' || ch == '!' || ch == '?' || ch == ';' || ch == ':'){
            count++;
        }
    }
    return count;
}

bool isNum(string str){
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
    ifstream infile;
    infile.open(filename.c_str());
    double count = 0, alpha = 0, syllables = 0, sCount = 0;
    string temp;
    while(getline(infile, temp, ' ')){
        stringstream ss(temp);
        while(getline(ss, temp, '\n')){
            if(!isNum(temp)){
                wordArr.push_back(temp);
                count++;
            }   
        }
    }
    syllables = countSyllables(sCount, wordArr);
    alpha = syllables / count;
    return alpha;
}

double countWords(string filename, vector<string> wordArr){
    ifstream infile;
    infile.open(filename.c_str());
    double count = 0, alpha = 0;
    string temp;
    while(getline(infile, temp, ' ')){
        if(!isNum(temp)){
            wordArr.push_back(temp);
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
        cin >> filename;
        argv[1] = new char[filename.length() + 1];
        strcpy(argv[1], filename.c_str()); 
    }
    else{
        filename = argv[1];
    }
    cout << "File being used: " << filename << endl;
    vector <string> wordArr;
    vector <string> sentArr;
    alpha = getAlpha(filename, wordArr);
    beta = countWords(filename, wordArr) / countSentences(filename, sentArr);
    index = 206.835 - alpha*84.6 - beta*1.015;
    gindex = round((alpha*11.8 + beta*0.39 - 15.59) * 10.0) / 10.0;
    cout << "Index = " << round(index) << "\n";
    cout << "Grade Level Index = " << gindex << "\n";
    return 0;
}
