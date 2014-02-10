# FILE : evans.pl
# AUTHOR : D. Fisher Evans
# LAST MODIFIED :  March 13th, 2012
# DISC : Displays file/folder hierarchy for a given directory and sorts it between acii, binary and perl
#          - take folder root as arg
#          - defaults to current dir
# USAGE : perl evans.pl <folder directory - may contain spaces>

system("cls"); # clear the screen
if($ARGV[0] ne "")   # grab the given directory
{ for($loop = 0;$loop <= $ARGV;$loop++) { $rootDir .= $ARGV[$loop] . " "; } } # loop through args and add the space in between
else { print "\n Defaulting to the current directory...\n"; $rootDir = '.'; } # if none given, default to the current root directory
$rootDir =~ s/[ \/\\]*$/\//; # get rid of any leading spaces, and make sure there is a slah at the end.
if(!(-d $rootDir)) { die "This doesn't seem to be a real directory:\n$rootDir"; } # check to see if the given direcotry exists

$allSize = 0; $binarySize = 0; $asciiSize = 0; $perlSize = 0; #init global vars to keep track of size and files
@allFiles; @binaryFiles; @asciiFiles; @perlFiles;
print "\n Gathering file information for:\n"; # Give them something to look at while the program grabs the file info
print " " . $rootDir;
getHier($rootDir); # grab the file/folder info

@allFiles = sort { -s $b <=> -s $a } @allFiles; # sort files largest to lowest size for all arrays
@binaryFiles = sort { -s $b <=> -s $a } @binaryFiles;
@asciiFiles = sort { -s $b <=> -s $a } @asciiFiles;
@perlFiles = sort { -s $b <=> -s $a } @perlFiles;

printMainTable(*allFiles, *allSize, "All"); # print the main table
printSubTable(*asciiFiles, *asciiSize, "ASCII"); # print the sub tables in order
printSubTable(*binaryFiles, *binarySize, "Binary");
printSubTable(*perlFiles, *perlSize, "Perl");

sub getHier { # Takes a directory as a string
	my $curFile = $_[0]; my $temp; my $tempSize; # grab given directory, and make private vars for the tempfile handle and size
	my @dirListing;  my @fileListing; # create temp arrays for files and folders
	opendir DIR, "$curFile";  # open the given directory
	while($temp = readdir DIR) { # go through each item
		if($temp =~ /^[^.]/) { # check for . files (hidden, self or parent)
			if(-d $curFile.$temp) { push(@dirListing, $curFile.$temp);} # push it in the temp file array
			else { push(@fileListing, $curFile.$temp); } # else int he temp dir. array
		}
	}
	closedir DIR; # close the given dir
	foreach $temp(@fileListing) { # go through each file
		$tempSize = -s $temp; # grab the size of the file
		if($tempSize > 0) { # if it's an actual file
		push(@allFiles, $temp); $allSize += $tempSize; # push it into the global file list
			if(-T $temp)                { push(@asciiFiles, $temp); $asciiSize += $tempSize; } # push it into appropiate global arrays for ascii, binary and perl
			if(-B $temp)                { push(@binaryFiles, $temp); $binarySize += $tempSize; }
			if($temp =~ /\.(pl|perl)$/) { push(@perlFiles, $temp); $perlSize += $tempSize; }
		}
	}
	foreach $temp(@dirListing) { getHier($temp."/",); } # for each directory, call this routine again.
}
sub printMainTable { # takes array pointer to file list, total size of the file type, file type name
	local (*files, *size, $name) = @_; # put the passed vars in the right spots
	print "\n\n\n    ---===  " . $name . " File Information ===---\n\n"; # print header
	if($size != 0) { # if there are actually any files of that type
		print "  Size (Bytes) | T | B | P | File\n"." --------------+---+---+---+----------------------------\n"; # print table header
		for(my $loop = 0;$loop <= $#files;$loop++) # loop through each file
		{ printf "  %12s | %s | %s\n", -s $files[$loop], getFlags($files[$loop]), $files[$loop]; } # print the size, the flags (see below), file name
		printInfo($#files + 1, $size); # once table is made, print the total/avg (see below)
	} else { print " There were no files found.\n"; } # if no files, say so.
}
sub printSubTable { # takes array pointer to file list, total size of the file type, file type name
	local (*files, *size, $name) = @_; # put the passed vars in the right spots
	print "\n\n\n    ---===  " . $name . " File Information ===---\n\n"; # print header
	if($size != 0) { # if there are actually any files of that type
		print "  Size (Bytes) | File\n"." --------------+----------------------------\n"; # print table header
		for(my $loop = 0;$loop <= $#files;$loop++) # loop through each file
		{ printf "  %12s | %s\n", -s $files[$loop], $files[$loop]; } # print the size, file name
		printInfo($#files + 1, $size); # once table is made, print the total/avg (see below)
	} else { print " There were no " . $name . " files found.\n"; } # if no files, say so.
}
sub getFlags { # takes a file name with location
	my $file = $_[0]; my $asciiFlag = " "; my $perlFlag = " "; my $binaryFlag = " "; # grab file name, and init all flags to spaces
	if(-T $file)                { $asciiFlag = "T"; } # if the file matches, change the corresponding flag from space
	if(-B $file)                { $binaryFlag = "B"; }
	if($file =~ /\.(pl|perl)$/) { $perlFlag = "P"; }
	return sprintf("%1s | %1s | %1s", $asciiFlag, $binaryFlag, $perlFlag); # returnt he formatted string '_ | _ | _'
}
sub printInfo { # takes number of files, total file size
	print "\n Total Files : " . $_[0] . "\n"; # print the number of file file
	print "  Total Size : " . $_[1] . " Bytes\n"; # print the total size passes
	print "   Avg. Size : " . ($_[1]/$_[0]) . " Bytes\n"; # print the ration of size/#
}