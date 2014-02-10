# cis_005_dfe.pl
# D. Fisher Evans
#
# Calcualtes the time it would take to pay off a loan
# takes a starying amount, apr, and desired payment

system("cls"); # Clear the screen

print "Please enter the loan amount: \$"; # Ask for inputs
$loanSize = <>;
print "Please enter the APR: %";
$apr = <>;
print "Please enter your desired monthly payment: \$";
$payment = <>;

chomp($loanSize); # Chomp the inpute (remove '\0')
chomp($apr);
chomp($payment);

$minPayment = $loanSize * ($apr / 1200); # Find minimum payment

system("cls"); # clear the screen again

if($payment <= $minPayment) # If desired ayment is too low
{
	print "You need to pay more than \$$minPayment every month to pay off the loan.\n"; # Tell them they have to pay at least min.
	print "(If the loan is \$$loanSize and the APR is %$apr.)\n";
}
else # if they can actually pay it off
{
	$paid = 0; # amount paid
	$totalCost = $loanSize; #cost to pay over time
	for($loop = 0;$paid < $totalCost;$loop++) # loop through each month until they've paid it all off
	{
		$paid = $payment * $loop;
		$totalCost = $loanSize + ($minPayment*$loop);
	}
	
	$lastPayment = $paid - $totalCost; # find out cost of last payment.
	$lastPaymenr -= $payment;
	
	printf "It will take %d months to pay off \$%.2f.\nThe loan started as \$%.2f with a %%%.2f APR.\n", $loop, $totalCost, $loanSize, $apr; # Print out info
	printf "The last payment would be \$%.2f of \$%.2f.\n", $lastPayment, $payment;
}