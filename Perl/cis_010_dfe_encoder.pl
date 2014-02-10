# cis_010_dfe.pl
# D. Fisher Evans
#
# Requires a ./cis_010_dfe_basefile
#
# Encodes a file into a jpg by XORing it with a given passkey.

if($ARGV[0] eq "" || $ARGV[1] eq "") # if not enough arguments are passed
{
	die "Use the format:\n"
	   ."perl cis_010_dfe_decoder.pl <filename> <passkey>\n\n"
	   ."This program will encode a file that is to be decoded by:\n"
	   ."cis_010_dfe_decoder.pl\n";
}

$file = $ARGV[0]; # get args
$key = $ARGV[1];

open (INPUTFILE , "+<$file") || die "Your file ($file) does not exist...\n"; # open all files
open (BASEFILE , "cis_010_dfe_base") || die "You are missing the required file:\ncis_010_dfe_base\n";
open (OUTPUTFILE , ">cis_010_dfe_tempfile.jpg") || die "The program could not create a the output file...";

binmode INPUTFILE; # set all modes to binary
binmode BASEFILE;
binmode OUTPUTFILE;

$newFileLength = (-s INPUTFILE); # get length of base, and file to be encoded
$baseFileLength = (-s BASEFILE);

print "\nInput File Size :  $newFileLength Bytes\n"; # print info
print "Base File Size  :  $baseFileLength Bytes\n\n";

$blobSize = 1024; # 1kb a loop

while (length($key) < $blobSize) # extend key
{
	$key .= $key;
}

$key = substr($key, 0, $blobSize); # set key to same size as blob

$writeOffset = 0; # set the beginning pos's for files
$readOffset = 0;

$binaryBuffer = ""; # temp scalar to store the blob

while($writeOffset < $baseFileLength) # go through each blob
{
	if ($baseFileLength - $writeOffset < $blobSize) # if the last chunk is shorter than the blob
	{
		$blobSize = $baseFileLength - $writeOffset;
	}
	read BASEFILE, $binaryBuffer, $blobSize, 0; # get the next blob
	sysseek BASEFILE,$writeOffset,WHENCE; # set cursor
	syswrite OUTPUTFILE, $binaryBuffer, $blobSize, 0; # write the blob to the output file
	$writeOffset += $blobSize; # move cursor pos
}

$blobSize = 1024; # reset blob from last loop

while ($readOffset < $newFileLength) # go through each blob
{
	if ($newFileLength - $readOffset < $blobSize)
	{
		$blobSize = $newFileLength - $readOffset;
		$key = substr($key, 0, $blobSize) # decrease the key too
	}
	read INPUTFILE, $binaryBuffer, $blobSize, 0; # get the next blob
	$binaryBuffer = $binaryBuffer ^ $key; # encode it
	sysseek INPUTFILE,$readOffset,WHENCE; # set cursor
	syswrite OUTPUTFILE, $binaryBuffer, $blobSize, 0; # write the blob to the output file
	$readOffset += $blobSize; # move cursor pos
}

close (INPUTFILE); # close the files
close (OUTPUTFILE);
close (BASEFILE);

$file =~ s{\.[^.]+$}{}; # regex to get rid of last ext (period with anything but period after

rename("cis_010_dfe_tempfile.jpg",$file."_encoded.jpg"); # rename the temp file


print "Encoding done. The encoded file is:\n"
	 .$file."_encoded.txt\n";