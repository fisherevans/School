# cis_012_dfe.pl
# D. Fisher Evans
#
# Displays file/folder hierarchy
# take folder root as arg
# defaults to current dir
#
# perl cis_012_dfe <file name, may include spaces> <perl/ascii/binary/none>

use File::Basename;

system("cls");

if($ARGV[0] ne "") # Grab file to read
{
	for($loop = 0;$loop < $#ARGV;$loop++) # Work with spaces
	{ $rootDir .= $ARGV[$loop] . " "; }
}
else { $rootDir = '.'; } # else default to 9a.txt
$rootDir =~ s/[ \\]*$/\\/; # make sure only one slash at end.

$options = "";
for($loop = 0;$loop <= $#ARGV;$loop++)
{
	$options .= $ARGV[$loop];
}

print "Showing File/Folder Hierarchy for:\n";
print $rootDir . "\n\n\n";

$dirCount; # initalize counters
$fileCount;
$sizeCount;
@binaryFiles;
@asciiFiles;
@perlFiles;
$binaryCounter = 0;
$asciiCounter = 0;
$perlCounter = 0;

@dataTypes = ("B","KB","MB","GB","TB"); # names for data sizes - USED IN saySize

if(!(-d $rootDir)) { die "This doesn't seem to be a real directory:\n$rootDir"; } # Check for dir

printHier($rootDir, ""); # Start the First itir of the printing
print "\nFinished Showing File/Folder Hierarchy for:\n";
print $rootDir . "\n\n";
printf "Folders: %d\nFiles: %d\nTotal Size: %s\n\n", $dirCount, $fileCount, saySize($sizeCount); #prit final info

printf "There are %d ASCII files.\n", $asciiCounter;
printf "There are %d Binary files.\n", $binaryCounter;
printf "There are %d Perl files.\n", $perlCounter;

if($options =~ /perl/i)
{
	@perlFiles = sort{$a <=> $b} @perlFiles;
	$total = 0;
	print "\nPerl Files:\n";
	for($loop = 0;$loop <= $#perlFiles;$loop++)
	{
		print $perlFiles[$loop] . " (" . saySize(-s $perlFiles[$loop]) . ")\n";
		$total += -s $perlFiles[$loop];
	}
	print "\nTotal Size:" . saySize($total) . "\n\n";
}

if($options =~ /binary/i)
{
	@binaryFiles = sort{$a <=> $b} @binaryFiles;
	$total = 0;
	print "\nBinary Files:\n";
	for($loop = 0;$loop <= $#binaryFiles;$loop++)
	{
		print $binaryFiles[$loop] . " (" . saySize(-s $binaryFiles[$loop]) . ")\n";
		$total += -s $binaryFiles[$loop];
	}
	print "Total Size:" . saySize($total) . "\n\n";
}

if($options =~ /ascii/i)
{
	@asciiFiles = sort{$a <=> $b} @asciiFiles;
	$total = 0;
	print "\nASCII Files:\n";
	for($loop = 0;$loop <= $#asciiFiles;$loop++)
	{
		print $asciiFiles[$loop] . " (" . saySize(-s $asciiFiles[$loop]) . ")\n";
		$total += -s $asciiFiles[$loop];
	}
	print "\nTotal Size:" . saySize($total) . "\n\n";
}

sub printHier
{
	my $curFile = $_[0]; #create private values
	my $indent = $_[1]; # formatting
	print $indent . '< ' . basename($curFile) . " >\n"; # since this always calls a folder, print with < >'s
	my @dirListing; # create file/dir lists
	my @fileListing;
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
		printHier($curFile.$temp."\\", $indent . "   "); # call inner itir for each dub dir
		$dirCount++;
	}
	
	my $tempSize; # create temp size value
	foreach $temp(@fileListing)
	{
		$tempSize = -s $curFile.$temp;
		printf "%s%s (%s)\n", $indent . "   ", $temp, saySize($tempSize); # print file and size
		$fileCount++;
		$sizeCount += $tempSize; # incr. total data amount
		if(-T $curFile.$temp) { addAscii($curFile.$temp); }
		if(-B $curFile.$temp) { addBinary($curFile.$temp); }
		if($temp =~ /\.(pl|perl)$/) { addPerl($curFile.$temp); }
	}
	print $indent . '< /' . basename($curFile) . " >\n"; # since this always calls a folder, print with < >'s
}

sub saySize
{
	my $size = $_[0]; # get bytes
	my $loop;
	for($loop = 0;$loop <= $#dataTypes && $size > 1024;$loop++) # find out what what power of 1024 size fit in
	{ $size /= 1024;} # make size smaller everyt ime
	return sprintf("%.2f",$size) . " " . $dataTypes[$loop]; # return the size of the file in smaller numbers
}

sub addBinary
{
	$binaryFiles[$binaryCounter] = $_[0];
	$binaryCounter++;
}

sub addAscii
{
	$asciiFiles[$asciiCounter] = $_[0];
	$asciiCounter++;
}

sub addPerl
{
	$perlFiles[$perlCounter] = $_[0];
	$perlCounter++;
}

