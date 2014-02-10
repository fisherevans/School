/* definition of basic requirements */
introEnglish(eng1060).
introEnglish(eng1061).
isLabScience(bio1020).
isLabScience(che1020).
isLabScience(phy1041).
isLabScience(phy1042).

/* is social science elective */
isSS(ant1010).
isSS(bus2440).
isSS(eco2020).
isSS(geo1010).
isSS(his1220).
isSS(psy1010).
isSS(his2070).
isSS(his2150).
isSS(his3056).
isSS(his3130).
isSS(his3165).
isSS(pos1020).
/* is arts and humanities elective */
isAH(eng1070).
isAH(eng2101).
isAH(hum2040).
isAH(hum2020).
isAH(hum3050).
isAH(hum3490).
isAH(int3060).
isAHSS(X) :- isAH(X).
isAHSS(X) :- isSS(X).
isUpperAHSS(X) :- isUpper(X),isAHSS(X).
isUpper(his3056).
isUpper(his3165).
isUpper(his3130).
isUpper(hum3050).
isUpper(hum3490).
isUpper(int3060).

/* vtcrequired generates all possible subsequences that satisfy VTC degree requirements
   first variable is degree level (as or bs), second is the list of courses */
vtcrequired(as,Courses) :- introEnglish(Eng1),isLabScience(Sci1),isAH(GenEd1),isSS(GenEd2),Courses = [Eng1,Sci1,GenEd1,GenEd2,eng2080].
vtcrequired(bs,Courses) :- vtcrequired(as,ASCourses),isLabScience(Sci2),\+ member(Sci2,ASCourses),isAHSS(GenEd3),isUpperAHSS(GenEd4),GenEd4 \= GenEd3,\+ member(GenEd4,ASCourses),\+ member(GenEd3,ASCourses),append(ASCourses,[Sci2,GenEd3,GenEd4],Courses).

/* or you can preference finding sequences that match what you have already taken */
vtcrequired(as,PriorCourses,Courses) :- ((introEnglish(Eng1),member(Eng1,PriorCourses),!);introEnglish(Eng1)),
										((isLabScience(Sci1),member(Sci1,PriorCourses),!);isLabScience(Sci1),
										((isAH(GenEd1),member(GenEd1,PriorCourses));isAH(GenEd1)),((isSS(GenEd2),member(GenEd2,PriorCourses));isSS(GenEd2))),
                                        Courses = [Eng1,Sci1,GenEd1,GenEd2,eng2080].
vtcrequired(bs,PriorCourses,Courses) :- vtcrequired(as,PriorCourses,ASCourses),
										(isLabScience(Sci2),\+ member(Sci2,ASCourses),member(Sci2,PriorCourses),!);(isLabScience(Sci2),\+ member(Sci2,ASCourses)),
										(isAHSS(GenEd3),\+ member(GenEd3,ASCourses),member(GenEd3,PriorCourses));(isAHSS(GenEd3),\+ member(GenEd3,ASCourses)),
										(isUpperAHSS(GenEd4),GenEd4 \= GenEd3,\+ member(GenEd4,ASCourses),member(GenEd4,PriorCourses));(isUpperAHSS(GenEd4),GenEd4 \= GenEd3,\+ member(GenEd4,ASCourses)),
									    append(ASCourses,[Sci2,GenEd3,GenEd4],Courses).

/* what courses satisfy a VTC wide requirement for all programs */
satisfiesVTC(C) :- introEnglish(C).
satisfiesVTC(C) :- isLabScience(C).
satisfiesVTC(C) :- isAHSS(C).
satisfiesVTC(C) :- C = eng2080.

/* some helper methods to pretty up outputs */
displayCourse(X) :- courseName(X,N),write(X),write(' '),write(N).
displayCurriculum([]).
displayCurriculum([X|Y]) :- displayCourse(X),nl,displayCurriculum(Y).

courseName(cis1120,N) :- N = 'Intro IST',!.
courseName(cis1151,N) :- N = 'Website Design',!.
courseName(cis1152,N) :- N = 'Adv Website Design',!.
courseName(cis2261,N) :- N = 'Intro Java I',!.
courseName(cis2271,N) :- N = 'Java Programming',!.
courseName(mat1221,N) :- N = 'Finite Math',!.
courseName(cis1420,N) :- N = 'Tech Math',!.
courseName(cis2151,N) :- N = 'Computer Networks I',!.
courseName(mat2120,N) :- N = 'Discrete Structure',!.
courseName(mat1520,N) :- N = 'Calc for Engineers',!.
courseName(cis2262,N) :- N = 'Intro Java II',!.
courseName(cis2230,N) :- N = 'Sys Admin',!.
courseName(cis2260,N) :- N = 'OOP',!.
courseName(cis2320,N) :- N = 'QA',!.
courseName(bus2020,N) :- N = 'Management',!.
courseName(cis2271,N) :- N = 'Java Programming',!.
courseName(phi1030,N) :- N = 'Logic',!.
courseName(cis2010,N) :- N = 'Comp Org',!.
courseName(cis2730,N) :- N = 'SE Project',!.
courseName(eng2080,N) :- N = 'Tech Comm',!.
courseName(mat2021,N) :- N = 'Stats',!.
courseName(cis3030,N) :- N = 'PL',!.
courseName(cis3050,N) :- N = 'Data Structures',!.
courseName(bus4310,N) :- N = 'Bus Info Arch',!.
courseName(mat2532,N) :- N = 'Calc II',!.
courseName(cis4150,N) :- N = 'SW Engineering',!.
courseName(cis3010,N) :- N = 'Databases',!.
courseName(bus2440,N) :- N = 'Bus Law',!.
courseName(bus2230,N) :- N = 'Marketing',!.
courseName(cis4120,N) :- N = 'Systems Analysis',!.
courseName(bus4530,N) :- N = 'Tech Proj Mgmt',!.
courseName(cis4020,N) :- N = 'OS',!.
courseName(hum2060,N) :- N = 'Cyberethics',!.
courseName(mat3720,N) :- N = 'Topics in Discrete',!.
courseName(cis4721,N) :- N = 'Sr Project I',!.
courseName(cis4722,N) :- N = 'Sr Project II',!.
courseName(cis3152,N) :- N = 'Network Programming',!.
courseName(cis3210,N) :- N = 'Routing Concepts',!.
courseName(cis3250,N) :- N = 'Adv Net Arch',!.
courseName(cis3310,N) :- N = 'AI',!.
courseName(cis3610,N) :- N = 'Topics in IT',!.
courseName(cis3629,N) :- N = 'Topics in SE',!.
courseName(cis4030,N) :- N = 'GUI Programming',!.
courseName(cis4040,N) :- N = 'Computer Security',!.
courseName(cis4050,N) :- N = 'Compiler Design',!.
courseName(cis4140,N) :- N = 'HCI',!.
courseName(cis4210,N) :- N = 'Graphics',!.
courseName(cis4220,N) :- N = 'Phys Sim',!.
courseName(cis4230,N) :- N = 'Parallel Prog',!.
courseName(cis4310,N) :- N = 'Computer Forensics',!.
courseName(elt4010,N) :- N = 'Computer Architecture',!.

courseName(X,N) :- N = X.
