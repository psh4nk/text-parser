#!/usr/bin/perl
use strict;
use warnings;
use feature qw(say);

local $/ = ' ';

my $file;

if($#ARGV == -1) {
    print "NO FILENAME ENTERED.\n";
    exit(0);
}
else{
    $file = $ARGV[0]; 
}

# Open the file.
open(INFILE, "$file") or die "Cannot open $file: $!.\n";

# Initialize variables
my @lines;
my @words;
my $sentcount = 0;
my $wordcount = 0;
my $sylcount = 0;

# Count words
while(<INFILE>){
    push(@words, $_);        # put strings from file into words scalar
    $wordcount++;            # inc wordcount
}

seek INFILE, 0, 0;           # go back to start of file

my $text = do { local $/; <INFILE> };
++$sentcount while $text =~ /[.!?;:]+/g;                # use regex to count sentences
++$sylcount while $text =~ /(?!e[ds]?$)[aeiouy]+/g;     # use regex to count syllables

close INFILE or die "Cannot close $ARGV[0]: $!";        # close the file

#print "Words: $wordcount \n";          # uncomment to print word count
#print "Sentences: $sentcount \n";      # uncomment to print sentence count
#print "Syllables: $sylcount \n";       # uncomment to print syllable count

my $index = 206.835 - ($sylcount / $wordcount) * 84.6 - ($wordcount / $sentcount) * 1.015;  # set index
my $grade = ($sylcount / $wordcount) * 11.8 + ($wordcount / $sentcount) * 0.39 - 15.59;     # set grade level

# print the stuff
printf "\n\tFILE BEING USED: $file\t\n";
printf "\n\tFLESCH READABILITY INDEX:\t\n";
printf "\tIndex = %d \t\n\n", $index;
printf "\tFLESCH-KINCAID GRADE LEVEL INDEX:\t\n";
printf "\tGrade Index = %.1f \t\n\n", $grade;

close $file;
