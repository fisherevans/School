# cis_012_dfe.pl
# D. Fisher Evans
#
# Displays file/folder hierarchy
# take folder root as arg
# defaults to current dir

use File::Basename;

system("cls");

if($ARGV[0] ne "") # Grab file to read
{
	for($loop = 0;$loop <= $#ARGV;$loop++) # Work with spaces
	{ $rootDir .= $ARGV[$loop] . " "; }
}
else { $rootDir = '.'; } # else default to 9a.txt
$rootDir =~ s/[ \\]*$/\\/; # make sure only one slash at end.

print "Showing File/Folder Hierarchy for:\n";
print $rootDir . "\n\n\n";

$dirCount; # initalize counters
$fileCount;
$sizeCount;

@dataTypes = ("B","KB","MB","GB","TB"); # names for data sizes - USED IN saySize

if(!(-d $rootDir)) { die "This doesn't seem to be a real directory:\n$rootDir"; } # Check for dir

printHier($rootDir, 0); # Start the First itir of the printing
print "\nShowing File/Folder Hierarchy for:\n";
print $rootDir . "\n\n";
printf "Folders: %d\nFiles: %d\nTotal Size: %s\n", $dirCount, $fileCount, saySize($sizeCount); #prit final info

sub printHier
{
	my $curFile = $_[0]; #create private values
	my $indent = $_[1]; # formatting
	my @dirListing; # create file/dir lists
	my @fileListing;
	print sayIndent($indent -1) . '[' . basename($curFile) . "]\n"; # since this always calls a folder, print with < >'s
	opendir DIR, "$curFile"; # open the root dir
	my $temp;
	while($temp = readdir DIR) # Loop throug directory 
	{
		if($temp =~ /^[^.]/) # filter hidden folders/files
		{
			if(-d $curFile.$temp) { push(@dirListing, $temp);} # if it's a directory, stick it in die list
			else { push(@fileListing, $temp); } # else stick it in files
		}
	}
	closedir DIR;

	@fileListing = sort { -s $a <=> -s $b } @fileListing; #sort Files by size
	
	foreach $temp(@dirListing) # print dirs first
	{
		printHier($curFile.$temp."\\", $indent+1); # call inner itir for each dub dir
		$dirCount++;
	}
	
	my $tempSize; # create temp size value
	foreach $temp(@fileListing)
	{
		$tempSize = -s $curFile.$temp;
		printf "%s%s (%s)\n", sayIndent($indent), $temp, saySize($tempSize); # print file and size
		$fileCount++;
		$sizeCount += $tempSize; # incr. total data amount
	}
	print sayIndent($indent -1) . '[/' . basename($curFile) . "]\n"; # since this always calls a folder, print with < >'s
}

sub saySize
{
	my $size = $_[0]; # get bytes
	my $loop;
	for($loop = 0;$loop <= $#dataTypes && $size > 1024;$loop++) # find out what what power of 1024 size fit in
	{ $size /= 1024;} # make size smaller everyt ime
	return sprintf("%.2f",$size) . " " . $dataTypes[$loop]; # return the size of the file in smaller numbers
}

sub sayIndent
{
	my $loop;
	my $indent;
	for($loop = 0;$loop <= $_[0];$loop++)
	{ $indent .= "|   "; }
	return $indent;
}

