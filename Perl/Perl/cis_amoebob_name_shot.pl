# routine to respond to student's guess of a hidden number bet 0 and 999999
# the student calls this prog to check a guess
# the student's name is arg0 and his/her guess is arg1
# the routine responds though a file: students_name_guess
# the response program creates a file with the last record = b if the guess is low ,
#    B if the guess is high and o if its correct
# 
# 

# giant amoebob from space is attacking earth
#  bbbbbbbbbbbbbbbb...bbbbbbboBBBBBBBBBBBBBBBBBBBBBBBBB...BBBBBBBB
# the nucleus is hidden somewhere within the giant cell corresponding to the integers 0 to 999999
# you only have 25 laser shots before the amoebob eats the earth
# if you aim the laser lower than the nucleus (o) , you can see the letter b
# if you hit higher than the nucleus, you can see the letter B
# if you hit the nucleus, you can see the letter o




  
  $student_name = $ARGV[0];
  $guess = $ARGV[1];
  $hidden_number = 0; # generate a hidden number for each student by adding the square of each ASCII value of each character in the student's name
  for ($i=0;$i<length($student_name);$i++) {$hidden_number = $hidden_number + (ord(substr($student_name,$i,1))**2);}
  $hidden_number = $hidden_number%1000000; # make sure number is between 0 and 999999


  $file = "cis_amoebob_result_dfe.txt"; # reponse prog will create a file with this name
  
  
  # write a "b" into the file if the guess is < the hidden number for this student
  # write a "B" into the file if the guess is > the hidden number
  # write a "o" into the file if the guess is correct

     if ($guess < $hidden_number) {$resp = "b";}
     elsif ($guess > $hidden_number) {$resp = "B";}
     else {$resp = "o";}
   open (FILE , ">$file");
   print FILE "$resp\n"; 
   exit;
                                             
 
