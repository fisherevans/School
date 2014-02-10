/* what courses are offered by semester and year
 * based (inaccurately of 2012-2013 williston offerings
 */

/* simple predicate to control odd/even back to 2011 */
oddYear(2011).
oddYear(Y) :- M is Y-2,M > 2010,oddYear(M).
evenYear(Y) :- \+ oddYear(Y).
offered(ant1010,fall,_).
offered(bio1020,fall,_).
offered(bus2020,fall,_).
offered(bus2230,spring,_).
offered(bus2440,fall,_).
offered(bus4310,fall,_).
offered(bus4530,spring,_).
offered(che1020,fall,_).
offered(cis1120,fall,_).
offered(cis1151,fall,_).
offered(cis1152,spring,_).
offered(cis2010,spring,Y) :- Y \= 2013.
offered(cis2151,spring,_).
offered(cis2230,fall,_).
offered(cis2235,spring,_).
offered(cis2260,fall,_).
offered(cis2261,fall,_).
offered(cis2262,spring,_).
offered(cis2271,fall,_).
offered(cis2320,fall,_).
offered(cis2411,spring,_).
offered(cis2450,fall,_).
offered(cis2730,spring,_).
offered(cis3010,spring,_).
offered(cis3030,fall,_).
offered(cis3050,fall,_).
offered(cis3152,spring,_).
offered(cis3210,fall,_).
offered(cis3250,spring,_).
offered(cis3310,spring,Y) :- oddYear(Y).
offered(cis4020,fall,_).
offered(cis4040,spring,_).
offered(cis4050,spring,Y) :- evenYear(Y).
offered(cis4120,spring,_).
offered(cis4140,fall,Y) :- evenYear(Y).
offered(cis4150,fall,_).
offered(cis4210,fall,Y) :- evenYear(Y).
offered(cis4220,spring,Y) :- oddYear(Y).
offered(cis4230,fall,Y) :- evenYear(Y).
offered(cis4721,fall,_).
offered(cis4722,spring,_).
offered(eco2020,fall,_).
offered(eng1042,fall,_).
offered(eng1060,_,_).
offered(eng1061,fall,_).
offered(eng1070,_,_).
offered(eng2080,_,_).
offered(eng2101,spring,_).
offered(geo1010,spring,_).
offered(his1220,fall,_).
offered(his2150,fall,_).
offered(his2070,fall,_).
offered(his3056,spring,_).
offered(his3130,fall,_).
offered(his3165,fall,_).
offered(hum2020,spring,_).
offered(hum2040,fall,_).
offered(hum2060,fall,_).
offered(hum3050,fall,_).
offered(int3060,fall,_).
offered(hum3490,spring,_).
offered(mat1221,fall,_).
offered(mat1420,fall,_).
offered(mat1520,spring,_).
offered(mat2120,spring,_).
offered(mat2021,spring,_).
offered(mat2532,fall,_).
offered(mat3170,fall,_).
offered(phy1042,fall,_).
offered(pos1020,spring,_).
offered(psy1010,_,_).
