from __future__ import division
import numpy as np
import pylab
import math

# Constants ###########################
pi = np.pi
g = 9.8
cd = 0.5
p = 1.225
m = 15.0
a = 2.5

# Pre-Req ###########################
v = 20
a = 30*pi/180.
vxInit = np.cos(a)*v
vyInit = np.sin(a)*v

# Time ###########################
start = 0
end = 4
h = 0.1
time = np.arange(start, end+h, h)

# Setup data arrays
numValues = 4
data = [[0 for col in range(time.size)] for row in range(numValues)]


# Initial Values ###########################
data[0][0] = 0 # x
data[1][0] = 0 # y
data[2][0] = vxInit # vx
data[3][0] = vyInit # vy

# Exact ####################
xExact = vxInit*time
yExact = vyInit*time - 0.5*g*time*time

# RK4 Equation Array ###########################
def rk4Eqns(t, d):
    result = np.zeros(numValues)
    v = math.sqrt(d[2]**2 + d[3]**2)
    result[0] = d[2] # x
    result[1] = d[3] # y
    result[2] = -p*cd*a*v/2.0/m  # vx
    result[3] = -g - p*cd*a*v/2.0/m # vy
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
    currentD = [ data[0][index], data[1][index], data[2][index], data[3][index] ]
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
pylab.plot(time, data[0], "r-", label="X RK4 (Drag)")
pylab.plot(time, xExact, "ro", label="X Exact (No Drag)")
pylab.plot(time, data[1], "b-", label="Y RK4 (Drag)")
pylab.plot(time, yExact, "bo", label="Y Exact (No Drag)")
#pylab.plot(data[0], data[1], "g-", label="Path RK4")
#pylab.plot(xExact, yExact, "go", label="Path Exact")

# Plot settings  ###########################
pylab.legend(loc=0)
pylab.xlabel('Time (Seconds)')
pylab.ylabel('Position (Meters)')
pylab.title('Question 1b')
pylab.grid(True)
pylab.savefig('fisher_lab4_q1b')

pylab.show()

