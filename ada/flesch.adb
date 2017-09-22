with Ada.Text_IO;
with Ada.Float_Text_IO;
use Ada.Text_IO; 
with Ada.IO_Exceptions; 
use Ada.IO_Exceptions; 

procedure flesch is

    In_File     : File_Type;
    value       : Character;
    string_array: array(1..5000000) of Character;
    pos         : Integer;
    Word        : BOOLEAN := FALSE;
    wordcount   : float;
    sentcount   : float;
    sylcount    : float;
    index       : Integer;
    gradeindex  : float;

begin 

    -- Open the file:
    Ada.Text_IO.Open (File => In_File, Mode => Ada.Text_IO.In_File, Name => "KJV.txt");

    -- set length of position by incrementing through the file char by char
    pos := 0;
    while not Ada.Text_IO.End_Of_File(In_File) loop
        Ada.Text_IO.Get(File => In_File, Item => value);
        pos := pos + 1;
        string_array(pos) := value;
    end loop;

    -- will hit eof, so handle exception:
exception
    when Ada.IO_Exceptions.END_ERROR => Ada.Text_IO.Close(File => In_File);

    -- Count words:
    wordcount := 0.0; 
    for i in 1..pos
    loop                -- This loop counts the words in the string array.
                        -- If there's a duplicate space, it is ignored, enhancing speed. 
        if string_array(i) = ' ' and Word = True 
        then 
            Word := False;
        elsif string_array(i) /= ' ' and Word = False
        then
            Word := True;
            wordcount := wordcount + 1.0;
        else 
            null;
        end if; 
    end loop;

    -- Count sentences:
    sentcount := 0.0;
    for i in 1..pos
    loop                -- This loop counts the sentences in the string array.
                        -- If there's a duplicate end-of-sentence marker, it is ignored, enhancing speed. 
        if (string_array(i) = '.' and Word = True) 
            or (string_array(i) = '!' and Word = True)
            or (string_array(i) = '?' and Word = True)
            or (string_array(i) = ';' and Word = True)
            or (string_array(i) = ':' and Word = True)
        then 
            Word := False;
        elsif (string_array(i) /= '.' and Word = False)
            or (string_array(i) /= '!' and Word = False)
            or (string_array(i) /= '?' and Word = False)
            or (string_array(i) /= ';' and Word = False)
            or (string_array(i) /= ':' and Word = False)
        then
            Word := True;
            sentcount := sentcount + 1.0;
        else 
            null;
        end if;
    end loop;

    -- Count syllables:
    sylcount := 0.0;
    for i in 1..pos
    loop                    -- This loop counts the syllables in the string array.
                            -- If there's a duplicate syllable, it is ignored, enhancing speed.
        if (string_array(i) = 'a' and Word = True)
            or (string_array(i) = 'e' and Word = True) 
            or (string_array(i) = 'i' and Word = True)
            or (string_array(i) = 'o' and Word = True)
            or (string_array(i) = 'u' and Word = True)
            or (string_array(i) = 'y' and Word = True)
        then 
            Word := False;
        -- This elsif statement accounts for e's at the end of words:
        -- If there is one, it is simply ignored.
        elsif (string_array(i) = 'e' and string_array(i+1) = ' ')
                or 
                (string_array(i) = 'e' and string_array(i+1) = '.')
                or
                (string_array(i) = 'e' and string_array(i+1) = '!')
                or
                (string_array(i) = 'e' and string_array(i+1) = '?')
                or
                (string_array(i) = 'e' and string_array(i+1) = ';')
                or 
                (string_array(i) = 'e' and string_array(i+1) = ':')
        then
            Word := True;  
         elsif (string_array(i) /= 'a' and Word = False)
            or (string_array(i) /= 'e' and Word = False)
            or (string_array(i) /= 'i' and Word = False)
            or (string_array(i) /= 'o' and Word = False)
            or (string_array(i) /= 'u' and Word = False)
            or (string_array(i) /= 'y' and Word = False)
        then 
            Word := True;  
            sylcount := sylcount + 1.0;
        else 
            null;
        end if;
    end loop;

    index := 0;
    gradeindex := 0.0; 

    index :=        Integer(206.835 - (sylcount / wordcount) * 84.6 - (wordcount / sentcount) * 1.015); -- calculate flesch index
    gradeindex :=   float((sylcount / wordcount) * 11.8 + (wordcount / sentcount) * 0.39 - 15.59);      -- calculate grade index

    Ada.Text_IO.New_Line;
    --Ada.Text_IO.Put_Line( Item => "Words: " & float'Image(wordcount));        -- uncomment to include word count in output
    --Ada.Text_IO.Put_Line( Item => "Sentences: " & float'Image(sentcount));    -- uncomment to include sentence count in output 
    --Ada.Text_IO.Put_Line( Item => "Syllables: " & float'Image(sylcount));      -- uncomment to include syllable count in output
    Ada.Text_IO.Put_Line( Item => "Index: " & Integer'Image(index));
    Ada.Text_IO.Put( Item => "Grade Index: "); 
    Ada.Float_Text_IO.Put( Item => float'Truncation(gradeindex), Fore => 4, Aft => 1, Exp => 0);
    Ada.Text_IO.New_Line;
    Ada.Text_IO.New_Line;

end flesch; 
