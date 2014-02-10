from __future__ import division
import numpy as np
import pylab
import math

# Constants ###########################
pi = np.pi

G = 6.67 * (10.**(-11.))
Me = 5.97 * (10.**(24.))


# Pre-Req ###########################

# Time ###########################
start = 0
end = 50000
h = 1
time = np.arange(start, end+h, h)

# Setup data arrays
numValues = 4
data = [[0 for col in range(time.size)] for row in range(numValues)]


# Initial Values ###########################
data[0][0] = 7. * (10.**6.) # x
data[1][0] = 0. # y
data[2][0] = 0. # vx
data[3][0] = 11000. # vy

# Exact ####################

# RK4 Equation Array ###########################
def rk4Eqns(t, d):
    result = np.zeros(numValues)
    result[0] = d[2]
    result[1] = d[3]
    result[2] = -1.*(G*Me)/((d[0]*d[0] + d[1]*d[1])**(3./2.))*d[0]
    result[3] = -1.*(G*Me)/((d[0]*d[0] + d[1]*d[1])**(3./2.))*d[1]
    return result

# Functions for RK4
def addArrays(a1, a2):
    result = np.zeros(len(a1))
    for i in range(len(a1)):
        result[i] = a1[i] + a2[i]
    return result

def getRK4(d, k1, k2, k3, k4):
    result = np.zeros(len(d))
    for i in range(len(d)):
        result[i] = d[i] + (h*(k1[i] + 2.0*k2[i] + 2.0*k3[i] + k4[i])/6.0)
    return result
    

# RK4 Loop
for index in range(0, time.size-1):
    currentD = np.zeros(numValues)
    for i in range(numValues):
        currentD[i] = data[i][index]
        
    t = time[index]

    if(math.sqrt(currentD[0]*currentD[0] + currentD[1]*currentD[1]) <= 6.38 * (10.**6.)):
        print "HIT EARTH!"
        sys.exit()

    k1 = rk4Eqns(t, currentD)
    k2 = rk4Eqns(t + h/2.0, addArrays(currentD,k1*h/2.0))
    k3 = rk4Eqns(t + h/2.0, addArrays(currentD,k2*h/2.0))
    k4 = rk4Eqns(t + h, addArrays(currentD,k3*h))

    nextD = getRK4(currentD, k1, k2, k3, k4)

    for i in range(numValues):
        data[i][index+1] = nextD[i]  
# End RK4 Loop

# Plot it ####################################################
pylab.plot(data[0], data[1], "r-", label="Path RK4")
#pylab.plot(data[0], data[1], "g-", label="Path RK4")
#pylab.plot(xExact, yExact, "go", label="Path Exact")

# Plot settings  ###########################
pylab.legend(loc=0)
pylab.xlabel('X (Meters)')
pylab.ylabel('Y (Meters)')
pylab.title('Question 2 - Hyperbolic - VY0 = 11000')
pylab.grid(True)
pylab.savefig('fisher_exam1_q2_hyperbolic')

pylab.show()

