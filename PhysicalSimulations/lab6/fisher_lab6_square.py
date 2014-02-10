from __future__ import division
import numpy as np
import pylab

# Constants ###########################
pi = np.pi

k = 1.0
m = 4.0
c = 0.5

f0 = 40
of = np.sqrt(m/k)

# Pre-Req ###########################

# Time ###########################
start = 0
end = 20
h = 0.001
time = np.arange(start, end+h, h)

# Setup data arrays
numValues = 2
data = [[0 for col in range(time.size)] for row in range(numValues)]


# Initial Values ###########################
data[0][0] = 5 # x
data[1][0] = 0 # vx

# Exact ####################

# RK4 Equation Array ###########################
def rk4Eqns(t, d):
    result = np.zeros(numValues)
    result[0] = d[1] # x
    result[1] = -1.0*k*m*d[0] - c*d[1] + 0.5*f0*np.abs(np.sin(of*t)+1) # vx
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
    currentD = [ data[0][index], data[1][index] ]
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
pylab.plot(time, data[0], "r-", label="Spring X RK4")
#pylab.plot(data[0], data[1], "g-", label="Path RK4")
#pylab.plot(xExact, yExact, "go", label="Path Exact")

# Plot settings  ###########################
pylab.legend(loc=0)
pylab.xlabel('Time (Seconds)')
pylab.ylabel('Position (Meters)')
pylab.title('Question 2 (Square) (Different 2 Freq)')
pylab.grid(True)
pylab.savefig('fisher_lab6_q2_sqrt')

pylab.show()

