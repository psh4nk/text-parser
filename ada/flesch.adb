with Ada.Text_IO;
use Ada.Text_IO;

procedure flesch is

        In_File     : File_Type;
        value       : Character;

begin 

    Ada.Text_IO.Open (File => In_File, Mode => Ada.Text_IO.In_File, Name => "KJV.txt");

    while not End_Of_File(In_File) loop
            Ada.Text_IO.Get(File => In_File, Item => value);
            Ada.Text_IO.Put(Item => value);
    end loop;

    Ada.Text_IO.Close(File => In_File);

end flesch; 
