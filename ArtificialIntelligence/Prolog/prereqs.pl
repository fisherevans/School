/* describe the pre-requisites for taking each course.
 * a course must satisfy at least one out of each inner list.
 * so, for 1152, they must have 1151 and one of 2271, 2261, 2025
 * Note that the english and math have place tests as pre-reqs, and 2271 has a studentChoice
 */
prereq(cis1152,Courses) :- Courses = [[cis1151],[cis2271,cis2261,cis2025]],!.
prereq(cis2271,Courses) :- Courses = [[cis2261,studentChoice2271]],!.
prereq(cis2010,Courses) :- Courses = [[cis2271,cis2262]],!.
prereq(cis2151,Courses) :- Courses = [[cis2271,cis2261,cis2025]],!.
prereq(cis2230,Courses) :- Courses = [[cis2271,cis2262,cis2025]],!.
prereq(cis2235,Courses) :- Courses = [[cis2151],[cis2230]],!.
prereq(cis2260,Courses) :- Courses = [[cis2271,cis2262,cis2025]],!.
prereq(cis2262,Courses) :- Courses = [[cis2261,cis2271]],!.
prereq(cis2320,Courses) :- Courses = [[cis2262,cis2271]],!.
prereq(cis2730,Courses) :- Courses = [[cis2262,cis2271]],!.
prereq(cis3050,Courses) :- Courses = [[cis2262,cis2271]],!.
prereq(cis3152,Courses) :- Courses = [[cis2151],[cis2010,cis2025,cis2022]],!.
prereq(cis3210,Courses) :- Courses = [[cis2151]],!.
prereq(cis3250,Courses) :- Courses = [[cis2151],[cis2230],[cis3210]],!.
prereq(cis3310,Courses) :- Courses = [[cis2271,cis2025,cis2262],[mat1420,mat1520,mat2021]],!.
prereq(cis4010,Courses) :- Courses = [[cis3050],[cis2010,cis2025,cis2022],[cis2230]],!.
prereq(cis4040,Courses) :- Courses = [[cis2151],[cis2230],[cis2271,cis2025,cis2262]],!.
prereq(cis4050,Courses) :- Courses = [[cis3030],[cis3050]],!.
prereq(cis4140,Courses) :- Courses = [[cis2260,cis1152]],!.
prereq(cis4210,Courses) :- Courses = [[mat1520]],!.
prereq(cis4220,Courses) :- Courses = [[mat2532],[cis3050],[phy1041]],!.
prereq(cis4230,Courses) :- Courses = [[mat2230],[cis3050]],!.
prereq(cis4310,Courses) :- Courses = [[cis2251],[cis2235]],!.
prereq(AHSS,Courses) :- isAHSS(AHSS),Courses = [[eng1060,eng1061]],!.
prereq(eng1060,Courses) :- Courses = [[eng1042,engPlace2]],!.
prereq(eng1061,Courses) :- Courses = [[engPlace3]],!.
prereq(eng2080,Courses) :- Courses = [[eng1060,eng1061]],!.
prereq(mat1221,Courses) :- Courses = [[mat1210,matPlace3]],!.
prereq(mat1420,Courses) :- Courses = [[mat1340,matPlace4]],!.
prereq(mat1520,Courses) :- Courses = [[mat1420,matPlace5]],!.
prereq(mat2021,Courses) :- Courses = [[mat1100,mat1210,mat1221,mat1420]],!.
prereq(mat2120,Courses) :- Courses = [[mat1210,mat1221,mat1420]],!.
prereq(mat2532,Courses) :- Courses = [[mat1520]],!.
prereq(mat2533,Courses) :- Courses = [[mat2532]],!.
prereq(mat3720,Courses) :- Courses = [[mat1520]],!.
prereq(phy1042,Courses) :- Courses = [[phy1041],[mat1420]],!.
prereq(_,Courses) :- Courses = [].

/* coreqs must be taken previously or concurrently */
coreq(cis3030,Courses) :- Courses = [[cis3050]],!.
coreq(cis3250,Courses) :- Courses = [[cis2230]],!.
coreq(cis4210,Courses) :- Courses = [[cis3050]],!.
coreq(phy1041,Courses) :- Courses = [[mat1420]],!.
coreq(_,Courses) :- Courses = [].

