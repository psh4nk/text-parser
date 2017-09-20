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

#Open the file.
open(INFILE, "$file") or die "Cannot open $file: $!.\n";
my @lines;
my @words;
my $sentcount = 0;
my $wordcount = 0;
my $sylcount = 0;

while(<INFILE>){
    push(@words, $_);
    $wordcount++;
}
seek INFILE, 0, 0;

my $text = do { local $/; <INFILE> };
++$sentcount while $text =~ /[.!?;:]+/g;
++$sylcount while $text =~ /(?!e[ds]?$)[aeiouy]+/g;
seek INFILE, 0, 0;

while(<INFILE>) {
    push(@lines, $_);   
}


close INFILE or die "Cannot close $ARGV[0]: $!";

#print @lines;
#print "Words: $wordcount \n";
#print "Sentences: $sentcount \n";
#print "Syllables: $sylcount \n";

my $index = 206.835 - ($sylcount / $wordcount) * 84.6 - ($wordcount / $sentcount) * 1.015;
my $grade = ($sylcount / $wordcount) * 11.8 + ($wordcount / $sentcount) * 0.39 - 15.59;

printf "\n\tFLESCH READABILITY INDEX:\t\n";
printf "\tIndex = %.1f \t\n\n", $index;
printf "\tFLESCH-KINCAID GRADE LEVEL INDEX:\t\n";
printf "\tGrade Index = %.1f \t\n\n", $grade;

#while(my $l = <$file>) {
#    print $l;
#}

close $file;
