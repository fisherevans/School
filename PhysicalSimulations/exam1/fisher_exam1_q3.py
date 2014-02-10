from __future__ import division
import numpy as np
import pylab
import math

# Constants ###########################
pi = np.pi

K1 = 0.5
l1 = 5.
m1 = 100.
l2 = 5.
K2 = 0.5
m2 = 5.


# Pre-Req ###########################

# Time ###########################
start = 0
end = 360
h = 0.01
time = np.arange(start, end+h, h)

# Setup data arrays
numValues = 4
data = [[0 for col in range(time.size)] for row in range(numValues)]


# Initial Values ###########################
data[0][0] = 1 # x1
data[1][0] = 10 # x2
data[2][0] = 0 # vx1
data[3][0] = 0 # vx2

# Exact ####################

# RK4 Equation Array ###########################
def rk4Eqns(t, d):
    result = np.zeros(numValues)
    x1 = d[0]
    x2 = d[1]
    result[0] = d[2]
    result[1] = d[3]
    result[2] = -1.0*K1/m1*(x1-l1) + K2/m2*(x2-x1-l2)
    result[3] = -1.0*K2/m2*(x2-x1-l2)
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

    k1 = rk4Eqns(t, currentD)
    k2 = rk4Eqns(t + h/2.0, addArrays(currentD,k1*h/2.0))
    k3 = rk4Eqns(t + h/2.0, addArrays(currentD,k2*h/2.0))
    k4 = rk4Eqns(t + h, addArrays(currentD,k3*h))

    nextD = getRK4(currentD, k1, k2, k3, k4)

    for i in range(numValues):
        data[i][index+1] = nextD[i]  
# End RK4 Loop

# Plot it ####################################################
#pylab.plot(time, data[0], "r-", label="X1 RK4")
#pylab.plot(time, data[1], "b-", label="X2 RK4")
pylab.plot(data[0], data[1], "b-", label="Vs RK4")
#pylab.plot(data[0], data[1], "g-", label="Path RK4")
#pylab.plot(xExact, yExact, "go", label="Path Exact")

# Plot settings  ###########################
pylab.legend(loc=0)
pylab.xlabel('X (Meters)')
pylab.ylabel('Y (Meters)')
pylab.title('Question 3 - Vs')
pylab.grid(True)
pylab.savefig('fisher_exam1_q3_vs')

pylab.show()

