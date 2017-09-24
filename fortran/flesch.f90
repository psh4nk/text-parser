
program reader
    implicit none
    character (LEN=50000000) :: long_string
    integer :: pos, i, filesize
    real    :: words, syllables, sents, alpha, beta, index, gradeindex

    call read_file(long_string, filesize)

    pos = 1
    words = 0
    loop: do
    i = verify(long_string(pos:), ' ')      ! find next non blank char
    if (i == 0) exit loop                   ! not found, move to next char
    words = words + 1                       ! found something
    pos = pos + i - 1                       ! move to next section
    i = scan(long_string(pos:), ' ')        ! see if there's an adjacent whitespace
    if (i == 0) exit loop                   ! no dupe, move to next char
    pos = pos + i - 1                       ! move to next section
    end do loop
    
    pos = 1
    sents = 0
    sentloop: do
    i = verify(long_string(pos:), '.!?;:')      ! find next end-of-sentence marker
    if (i == 0) exit sentloop                   ! not found, move to next char
    sents = sents + 1                           ! found something
    pos = pos + i - 1                           ! move to next
    i = scan(long_string(pos:), '.!?;:')        ! see if there's an adjacent eos char
    if (i == 0) exit sentloop                   ! if no eos char found, move on
    pos = pos + i - 1                           ! move to next
    end do sentloop
    
    pos = 1
    syllables = 0
    sylloop: do
    i = verify(long_string(pos:), 'aeiouy')     ! find next vowel
    if (i == 0) exit sylloop                    ! if not found, move on
    syllables = syllables + 1                   ! if found, increment
    pos = pos + i - 1                           ! move to next
    i = scan(long_string(pos:), 'aeiouy')       ! see if there's an adjacent vowel
    if (i == 0) exit sylloop                    ! if no vowel found here, move on
    pos = pos + i - 1                           ! move to next
    end do sylloop
    
    alpha = syllables / words;                          ! calc alpha
    beta  = words / sents;                              ! calc beta
    index = 206.835 - alpha * 84.6 - beta * 1.015;      ! calc index
    gradeindex = alpha * 11.8 + beta * 0.39 - 15.59;    ! calc grade index 
    6 format(" Index: ", f5.0);                          ! set up index to be truncated
    5 format(" Grade Index: ", f5.1);                    ! set up grade index to be truncated
    print 6,  index;                                    ! print but truncate index
    print 5, gradeindex;                                ! print but truncate grade index
    print *, " "

end program reader

subroutine read_file( string, counter ) 

    character (LEN=20) :: string
    integer :: counter
    character (LEN=1) :: input

    call getarg(1, string)      ! get filename from command line arg and put it in the string 
    open (unit=5,status="old",access="direct",form="unformatted",recl=1,file=string) ! open the file
    print *, ""
    print *, "File being used: ", string        ! print what file is being scanned
    counter=1
    100 read (5,rec=counter,err=200) input      ! read the file
    string(counter:counter) = input             
    counter=counter+1                           ! count the chars
    goto 100                                    ! loop back to 100
    200 continue
    counter=counter-1

    close(5)
end subroutine read_file

