# cis_011_dfe.pl
# D. Fisher Evans
#
# Reads a given file char by char displaying the ASCII and HEX value for each.

if($ARGV[0] eq "") # if not enough arg.s passed
{
	die "Use the format:\n"
	   ."perl cis_011_dfe.pl <filename>\n\n";
}

$file = $ARGV[0]; # grab file name

open(INPUTFILE, "$file") || die "Failed to open file."; # open it

binmode INPUTFILE; #binary reading

$offset = 0; # temp vars
$buffer = "";

@ascii = (); # arrays to store data
@hex = ();

while($offset < (-s INPUTFILE)) # loop through EACH character (byte)
{
	sysseek INPUTFILE,$offset,WHENCE; # set position, then read that byte.
	read INPUTFILE, $buffer, 1, 0;
	
	$ascii[$offset] = $buffer; # save ASCII
	$hex[$offset] = sprintf("%02X ",ord($buffer)); # and HEX
	
	$offset++;
}

$col = 15; #define # of columns (formatting)

print "\n +"; # print first grid for first row.
for($i2 = 0;$i2 < $col;$i2++)
{ print "----+"; }
	
MAIN: for($i = 0;$i <= $#ascii;$i += $col) # loop through each row
{	
	print "\n |";
	for($i2 = $i;$i2 - $i < $col;$i2++) # loop through columns for Char subrow
	{
		if($i2 <= $#ascii) { printf " %1s  |", $ascii[$i2]; }
		else { print "    |"; }
	}
	
	print "\n |";
	for($i2 = $i;$i2 - $i < $col;$i2++) # loop through columns for HEX subrow
	{
		if($i2 <= $#ascii) { printf " %02X |", $hex[$i2]; }
		else { print "    |"; }
	}
	
	print "\n +";
	for($i2 = $i;$i2 - $i < $col;$i2++) # loop through columns for formatting grid
	{ print "----+"; }
}

print "\n\n\n"; # footer text
print " The above info displays the ASCII value and its \n";
print " corresponding HEX value for each character in:\n";
print " ./$file\n";