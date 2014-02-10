# cis_006_dfe.pl
# D. Fisher Evans
#
# Will the apple hit Newtons head, or will David stop it?

system("cls"); # Clear the screen

$timeStep = 0.01; # Set the time params
$time = 0;
$timeStop = 5;

$end = "Simulation ran for too long.\n"; # Set end text in case time runs out

$applePosX = 100; # Set apple init vars
$applePosY = 30;
$appleVelX = 0;
$appleVelY = 0;
$appleRad = 1.5/12; # Orig 1.5

$shotPosX = 0; # Set Shot init vars
$shotPosY = 5;
$shotVelX = 80;
$shotVelY = 20;
$shotRad = .5/12; # Orig 0.5

$gravity = -32;

print "Start simulation...\n\n"; # Header Text
print " Apple X | Apple Y | Shot X  | Shot Y  | Time\n";
print "---------+---------+---------+---------+---------\n";


while($time < $timeStop) # timestep loop
{
	printf " %7.2f | %7.2f | %7.2f | %7.2f | %7.2f\n", # Print out current object stats
		$applePosX,$applePosY,$shotPosX,$shotPosY,$time;

	$applePosY += $appleVelY*$timeStep; # Update apple position
	# $applePosX += $appleVelX*$timeStep; # Un-comment if appleVelX is not 0 - SPEED UP
	
	$shotPosX += $shotVelX*$timeStep; # Update Shot position
	$shotPosY += $shotVelY*$timeStep;
	
	$shotVelY += $gravity*$timeStep;# Update shot and apple Y velocity (Ignore air resistance)
	$appleVelY += $gravity*$timeStep; 
	
	$collisionDist = sqrt( ($shotPosX - $applePosX)**2 + ($shotPosY - $shotPosY)**2 ); # Get distance between centers of objects
	
	if($collisionDist <= $appleRad + $shotrad) # If colis distance is less than the radius of two objects, means they're touching/overlaping
	{
		$end = "Apple and shot has colided at most recent time step.\n";
		last;
	}
	if($shotPosX > 110 || $shotPoxY > 30 || $shotPosX < 0 || $shotPosY < 2) # If shot goes out of useable bounds
	{
		$end = "Shot out of bounds, no collision.\n";
		last;
	}
	if($applePosX > 110 || $applePoxY > 30 || $applePosX < 0 || $applePosY < 2) # If apple goes out of useable bounds
	{
		$end = "Apple out of bounds, no collision.\n";
		last;
	}
	
	$time += $timeStep; # Step time
}
print "---------+---------+---------+---------+---------\n"; # footer text
print " Apple X | Apple Y | Shot X  | Shot Y  | Time\n\n";

print $end; # Print end result