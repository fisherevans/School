#################################################################################################################################
# teacher's routine to play a game of yahtzee
# the student calls this program to:
#    initialize a file
#    roll the dice
#    score the previous roll into a yahtzee category (e.g. one's , small_straight, etc.)
#    end the game (and score the results)
#
# student would enter: system ("perl cis__yahtzee.pl student_name command options");
# 
# if the command is:
#   new_game ... then options is not required, prog will create a new file called yahtzee_$student_name
#                 and the file will be initialized to  new game:   X X X X X 
#   roll_dice ... then options must be a char_string of 5 chars where each char is R (for Roll) or K (for Keep)
#                 the prog will read the above file, extracting the last 5 fields of the last record
#                 it will interpret these fields as the state of the 5 dice
#                 it will roll new values for the die that correspond to R in the options
#                 it will append the file: with roll dice: new value for each die
#   score_into ... then the options must be one of the following categories in order to receive creit when the final score is tallied:
#                  ones, twos, threes, fours, fives, sixes, 3_of_a_kind, 4_of_a_kind, full_house, small_straight,
#                  large_straight, chance , yahtzee 
#                  the file will be updated so that the last record is "score_into" options : X X X X X
#   end_game ... then options is not required
#                 the program will read the file and score the game
#                 it will append the file with end_game : final score
#
#
#################################################################################################################################
#
# MAIN ROUTINE:
#

  if ($#ARGV < 1) {print "insufficient number of arguments for cis__yahtzee.pl\n";
                   print "... there must be at least 2 arguments: student's_name  yahtzee_command [options] \n";
                   exit;
                  } # there must be at least 2 arguments on the command line: student's_name command
  $student_name = $ARGV[0];
   
  $dir = "c:/perl/bin/";
  $file = $dir . "yahtzee_$student_name" . ".txt";

  $command = $ARGV[1]; # valid commands are: new_game 
                       # roll_dice  options (5_char_string)
                       # score_into options (char_string)
                       # end_game

 
  if ($command eq "new_game") {&new_game;}
  elsif ($command eq "roll_dice") {$options = $ARGV[2];
                                   &roll_dice;}
  elsif ($command eq "score_into") {$options = $ARGV[2];
                                    &score_into;}
  elsif ($command eq "end_game") {&end_game;}
  else {;}

  exit;
    



#################################################################################################################################
# subroutine: new_game
# teacher's subroutine to create a new file for a new game of yahtzee
# the student calls this program to initialize a file
# student would enter: system ("perl cis__yahtzee.pl student_name new_game");
# 
# this program will create a file called yahtzee_$student_name
#  the file will be initialized to  new game:   0 0 0 0 0 
#
sub new_game
 {
 
  open (FILE , ">$file");
  print FILE "new game :                    X X X X X \n";
  close FILE;
      
 } # end subroutine


####################################################################################################################################
# subroutine roll_dice
# teacher's routine to roll new dice within a game of yahtzee
# the student calls this program to roll dice
# student would enter: system ("perl cis__yahtzee.pl student_name roll_dice 5_char_string");
#  the 5_char_string is a string of 5 chars, each char is R (for Roll) or K (for Keep)
# 
# this program will read a file called yahtzee_$student_name
#  it will obtain the previou state of the 5 die from the last line of the file
# It will then append a new line: roll 5_char_string : new values of the 5 die
#
sub roll_dice
{
  if (! -e $file){return;}        # if file doesn't exist, then student hasn't previously requested newgame
                                  #  simply exit ... when/if student enters endgame to score result, file will not exist, and score will be 0  

  open (FILE , "$file");        # read the file into @A
  @A = <FILE>;                  # the last record (in last 5 flds): should hold the current state of the dice
  chomp @A;
  close FILE;
  open (FILE , ">>$file");      # reopen in append mode
    
  $_ = $A[$#A];                 # get the last record and parse it  
  split;                        #  $_[$#_ - 4] , $_[$#_ - 3] ... $_[$#_] = state of die 1 ,2 ... , 5 
  @dice = @_[$#_-4, $#_-3, $#_-2, $#_-1, $#_];
  

  $string5 = $options;          # get the roll instructions
                                # roll instruction has to be exactly 5 chars, each char must be R or K
                                # if not ... set dice to XXXXX
                                # when/if endgame is called, score will be 0         
  if (length($string5) != 5){print FILE "roll $string5 : illegal roll : X X X X X \n";
                             return;}

  for ($i=0;$i<=4;$i++)
   { $c1 = substr($string5, $i, 1);  # get roll instruction for each die
                                     # if R ... then gen new value (1 to 6)
     if ($c1 eq "R") { $dice[$i] = 1 + int(rand(6)); } # gen a new roll 
     elsif ($c1 eq "K") {;}
     else {print FILE "roll $string5 : illegal roll : X X X X X  \n";
           return; }
   } # end for

   print FILE "roll_dice $string5 :             @dice \n";

} # end subroutine
 ##########################################################################################################################################
# subroutine score_into
# teacher's routine to place the results of the previous roll into a valid yahtzee category
# the student calls this program to roll dice
# student would enter: system ("perl cis__yahtzee.pl student_name score_into char_string");
#  the char_string is the name of the yahtzee category
# 
# this program will read a file called yahtzee_$student_name
#  it will obtain the previou state of the 5 die from the last line of the file
# It will then append a new line: score into : category
#
sub score_into
{

if (! -e $file){return;}        # if file doesn't exist, then student hasn't previously requested newgame
                                  #  simply exit ... when/if student enters endgame to score result, file will not exist, and score will be 0  

  open (FILE , "$file");        # read the file into @A
  @A = <FILE>;                  # the last record (in last 5 flds): should hold the current state of the dice
  chomp @A;
  close FILE;
  open (FILE , ">>$file");      # reopen in append mode
  $opt = sprintf ("%16s" , $options); 
  print FILE "score_into   $opt X X X X X \n";

} # end subroutine



###########################################################################################################################################
# subroutine end_game
# teacher's routine to end and score the game
# the student calls this program to end the game
# student would enter: system ("perl cis__yahtzee.pl student_name end_game");
# 
# this program will read a file called yahtzee_$student_name
#  
# It will then append a new line: end_gme : final_score
sub end_game
{

if (! -e $file){return;}        # if file doesn't exist, then student hasn't previously requested newgame
                                # return  

  open (FILE , "$file");        # read the file into @A
  @A = <FILE>;  
#print "@A\n";
#print "last index = $#A\n";                
  chomp @A;
  close FILE;
  open (FILE , ">>$file");      # reopen in append mode

  %categories = (); # hash array keys=valid yahtzee categories  val=score for that category
  
  for ($i=0;$i<=$#A;$i++) # go through each entry in @A to score the game
   { # print "record $i = $A[$i]\n";
     if ($A[$i] =~ m/new_game/) {$roll_cnt = 0;}
     if ($A[$i] =~ m/roll_dice/) {$roll_cnt++;}
     if ($A[$i] =~ m/score_into/) # if entry is a score_into
        { if ($roll_cnt > 3) {last;} # if more than 3 rolls for this score_into
                                     # then incorrect game sequence ... score 0 for entire game
           $roll_cnt = 0;              # if roll_cnt is legal, reset it for next check
           $_ = $A[$i];          # read category to score previous dice state
           split;
           $cat = $_[1];

           $_ = $A[$i-1];            # get the previous record and parse it to read the state of the dice prior to score request 
           split;                    #  $_[$#_ - 4] , $_[$#_ - 3] ... $_[$#_] = state of die 1 ,2 ... , 5 
           @dice = @_[$#_-4, $#_-3, $#_-2, $#_-1, $#_];

           # to simplify some of the checks, create a hash array where the key is the die value and value=#of die with that val
           # for example: if dice = 1 1 5 5 4 then $W{1}=2 , $W{5}=2 , $W{4}=1 and $W{2,3,6} = 0

           # also ... need the sum of all dice for 3_of_a_kind , 4_of_a_kind and chance

           %W = (); # hash work array key=die   val=# of die matching 
           $W{1} = 0; $W{2} = 0; $W{3} = 0; $W{4} = 0; $W{5} = 0; $W{6} = 0; # init work array
           $sum = 0;
           for ($j=0;$j<=4;$j++) 
            { for ($k=1; $k<=6;$k++)
                {if ($dice[$j] == $k) {$W{$k} = $W{$k} = $W{$k}+1;} 
                } # end for k
              $sum = $sum + $dice[$j]; 
            } #end for j

           if ($cat eq "ones")  # if cat has not been used already, score it 
             { if (! exists $categories{ones}) {$categories{ones} = $W{1};}
               else {last;}    # otherwise, incorrect game sequence, score 0 for entire game
             } # end if

           elsif ($cat eq "twos")  # if cat has not been used already, score it
             { if (! exists $categories{twos}) {$categories{twos} = 2* $W{2};}
               else {last;}    # otherwise, incorrect game sequence, score 0 for entire game
             } # end if

          elsif ($cat eq "threes")  # if cat has not been used already, score it
              {if (! exists $categories{threes}) {$categories{threes} = 3* $W{3};}
               else {last;}    # otherwise, incorrect game sequence, score 0 for entire game
             } # end if

          elsif ($cat eq "fours")  # if cat has not been used already, score it
             { if (! exists $categories{fours}) {$categories{fours} = 4* $W{4};}
               else {last;}    # otherwise, incorrect game sequence, score 0 for entire game
             } # end if

          elsif ($cat eq "fives")  # if cat has not been used already, score it
             {if (! exists $categories{fives}) {$categories{fives} = 5* $W{5};}
               else {last;}    # otherwise, incorrect game sequence, score 0 for entire game
             } # end if
       
          elsif ($cat eq "sixes")  # if cat has not been used already, score it
             { if (! exists $categories{sixes}) {$categories{sixes} = 6* $W{6};}
               else {last;}    # otherwise, incorrect game sequence, score 0 for entire game
             } # end if

          elsif ($cat eq "chance")  # add all the dice
             { if (! exists $categories{chance}) {$categories{chance} = $sum;}
               else {last;}    # otherwise, incorrect game sequence, score 0 for entire game
             } # end if    

          elsif ($cat eq "small_straight")  # to verify small straight : 3 possibilities
             { if  ( (($W{1}>0) && ($W{2}>0) && ($W{3}>0) && ($W{4}>0)) ||
                     (($W{2}>0) && ($W{3}>0) && ($W{4}>0) && ($W{5}>0)) ||
                     (($W{3}>0) && ($W{4}>0) && ($W{5}>0) && ($W{6}>0)) ) {$score = 30;}
               else {$score = 0;}
               if (! exists $categories{small_straight}) {$categories{small_straight} = $score;}
               else {last;}    # otherwise, incorrect game sequence, score 0 for entire game
             } # end if
     
          elsif ($cat eq "large_straight")  # to verify large straight : 2 possibilities
             { if  ( (($W{1}>0) && ($W{2}>0) && ($W{3}>0) && ($W{4}>0) && ($W{5}>0)) ||
                     (($W{2}>0) && ($W{3}>0) && ($W{4}>0) && ($W{5}>0) && ($W{6}>0)) ) {$score = 40;}
               else {$score = 0;}
               if (! exists $categories{large_straight}) {$categories{large_straight} = $score;}
               else {last;}    # otherwise, incorrect game sequence, score 0 for entire game
             } # end if

          elsif ($cat eq "3_of_a_kind")  
             { if  (($W{1}>=3) || ($W{2}>=3) || ($W{3}>=3) || ($W{4}>=3) || ($W{5}>=3) || ($W{6}>=3)) {$score=$sum;}
               else {$score = 0;}
               if (! exists $categories{"3_of_a_kind"}) {$categories{"3_of_a_kind"} = $score;}
               else {last;}    # otherwise, incorrect game sequence, score 0 for entire game
             } # end if

          elsif ($cat eq "4_of_a_kind")  
             { if  (($W{1}>=4) || ($W{2}>=4) || ($W{3}>=4) || ($W{4}>=4) || ($W{5}>=4) || ($W{6}>=4)) {$score=$sum;}
               else {$score = 0;}
               if (! exists $categories{"4_of_a_kind"}) {$categories{"4_of_a_kind"} = $score;}
               else {last;}    # otherwise, incorrect game sequence, score 0 for entire game
             } # end if

          elsif ($cat eq "yahtzee")  
             { if  (($W{1}>=5) || ($W{2}>=5) || ($W{3}>=5) || ($W{4}>=5) || ($W{5}>=5) || ($W{6}>=5)) {$score=50;}
               else {$score = 0;}
               if (! exists $categories{yahtzee}) {$categories{yahtzee} = $score;}
               else {last;}    # otherwise, incorrect game sequence, score 0 for entire game
             } # end if

          elsif ($cat eq "full_house")  
             { if ((($W{1}==3) || ($W{2}==3) || ($W{3}==3) || ($W{4}==3) || ($W{5}==3) || ($W{6}==3)) &&
                   (($W{1}==2) || ($W{2}==2) || ($W{3}==2) || ($W{4}==2) || ($W{5}==2) || ($W{6}==2)) ) {$score=25;}
			   elsif (($W{1}>=5) || ($W{2}>=5) || ($W{3}>=5) || ($W{4}>=5) || ($W{5}>=5) || ($W{6}>=5)) {$score=25;}
               else {$score = 0;}
               if (! exists $categories{full_house}) {$categories{full_house} = $score;}
               else {last;}    # otherwise, incorrect game sequence, score 0 for entire game
             } # end if

          else {;} # incorrect category ... no penalty but score not included in any category

       } # end if score_into
   }# end for i

  if ($i <= $#A) {$total = 0; # if exit above loop early, then incorrect sequence : score 0
                  print FILE "sequencing problem detected: i=$i\n"}
  else{ $total = 0;            # sum all categories
        @k_categories = keys %categories;
        # print "$#k_categories categories found\n";
        for ($i=0;$i<=$#k_categories;$i++) {$total = $total + $categories{$k_categories[$i]};
                                             # print STDERR "$k_categories[$i] $categories{$k_categories[$i]} $total\n"
                                           }
        $bonus = $categories{ones} + $categories{twos} + $categories{threes}
               + $categories{fours} + $categories{fives} + $categories{sixes}; 
        if ($bonus >= 63) {$total = $total + 35;}
      } # end else
  print FILE "\nend_game : $total \n";
  return;
  

} # end subroutine 
     
                                             
 
