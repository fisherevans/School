/* define major requirements by catalog, implementing two major rules :
 * catalog (possible sequences satisfying the catalog requirements)
 * satisfiesMajor (predicate if single course meets some requirement)
 */

/* catalog should define all possible sequences, based on major,catalog year,degree level (as or bs).
 * currently only cse degree is defined 
 */
catalog(cis,2012,Degree,Courses) :- vtcrequired(Degree,VTCCourses),
                                    cisIntroMath(IntroMath),discreteSeq(Degree,DSMath),
                                    cisIntroProg(Prog),
                                    union([cis1120,cis1150,cis1151,cis2151,cis2320,cis2230,IntroMath],DSMath,CISCore),
                                    append(CISCore,Prog,CISCourses),
                                    union(CISCourses,VTCCourses,Courses).
catalog(cse,2012,as,Courses) :- catalog(cis,2012,as,CISCourses),
								cseAsCourses([],CSECourses),
								append(CISCourses,CSECourses,Courses).
catalog(cse,2012,bs,Courses) :- catalog(cis,2012,bs,CISCourses),
								cseAsCourses([],CSECourses),
								append(CISCourses,CSECourses,ASCourses),
								cseBSCourses(CISCourses,BSCourses),
								union(ASCourses,BSCourses,Courses).

/* and generate the sequences that agree with what student has already taken */
catalog(cis,2012,Degree,PriorCourses,Courses) :- vtcrequired(Degree,PriorCourses,VTCCourses),
							(cisIntroMath(IntroMath),member(IntroMath,PriorCourses),!;cisIntroMath(IntroMath)),
							(discreteSeq(Degree,DSMath),subset(DSMath,PriorCourses),!;discreteSeq(Degree,DSMath)),
							(cisIntroProg(Prog),member(Prog,PriorCourses),!;cisIntroProg(Prog)),
							union([cis1120,cis1150,cis1151,cis1152,cis2151,cis2320,cis2230,IntroMath],DSMath,CISCore),append(CISCore,Prog,CISCourses),union(CISCourses,VTCCourses,Courses).
catalog(cse,2012,as,PriorCourses,Courses) :- catalog(cis,2012,as,PriorCourses,CISCourses),
											cseAsCourses(PriorCourses,CSECourses),
											append(CISCourses,CSECourses,Courses).
catalog(cse,2012,bs,PriorCourses,Courses) :- catalog(cis,2012,bs,PriorCourses,CISCourses),
											cseAsCourses(PriorCourses,CSECourses),
											append(CISCourses,CSECourses,ASCourses),
											cseBSCourses(CISCourses,BSCourses),
											union(ASCourses,BSCourses,Courses).
											
/* now define the detailed CIS requirements */								
cisIntroMath(mat1420).
cisIntroMath(mat1221).
cisIntroProg(Courses) :- Courses = [cis2271].
cisIntroProg(Courses) :- Courses = [cis2261,cis2262].
cseSophomore(mat1520).
cseSophomore(phi1030).
cseSophomore(bus2020).
cseAsCourses(PriorCourses,CSECourses) :- (cseSophomore(Soph),member(Soph,PriorCourses),!;cseSophomore(Soph)),CSECourses = [cis2260,cis2730,mat2021,cis2010,Soph].
discreteSeq(as,DSMath):- DSMath = [mat2120].
discreteSeq(bs,DSMath):- DSMath = [mat1520,mat3720].
discreteSeq(bs,DSMath):- discreteSeq(as,DSMath).
cseBSCourses(CoreCourses,PriorCourses,Courses) :- bsElectives(cse,CoreCourses,PriorCourses,BSElectives),append([cis3010,cis3030,cis3050,cis4150,cis4120,cis4020,cis4721,cis4722,hum2060,bus4530],BSElectives,Courses).
bsElectives(cse,CoreCourses,PriorCourses,BSElectives) :- 
							(uiCourse(UICourse),member(UICourse,PriorCourses),!;uiCourse(UICourse)),
							(cseBusElective(BusElective),member(BusElective,PriorCourses),\+ member(BusElective,CoreCourses);cseBusElective(BusElective),\+ member(BusElective,CoreCourses)),
							(cseJrElective(JrElective),member(JrElective,PriorCourses),!;cseJrElective(JrElective)),
							(cseSrElective(SrElective),member(SrElective,PriorCourses);cseSrElective(SrElective)),
							(upperCIS(CIS1),CIS1 \= UICourse, CIS1 \= JrElective, CIS1 \= SrElective,member(CIS1,PriorCourses);upperCIS(CIS1),CIS1 \= UICourse, CIS1 \= JrElective, CIS1 \= SrElective),
							(upperCIS(CIS2),CIS2 \= UICourse, CIS2 \= JrElective, CIS2 \= SrElective,CIS2 \= CIS1,member(CIS2,PriorCourses);upperCIS(CIS2),CIS2 \= UICourse, CIS2 \= JrElective, CIS2 \= SrElective,CIS2 \= CIS1),
							(upperCIS(CIS3),CIS3 \= UICourse, CIS3 \= JrElective, CIS3 \= SrElective,CIS3 \= CIS1,CIS3 \= CIS3,member(CIS3,PriorCourses);upperCIS(CIS3),CIS3 \= UICourse, CIS3 \= JrElective, CIS3 \= SrElective,CIS3 \= CIS1,CIS3 \= CIS3),
                            BSElectives=[UICourse,BusElective,JrElective,SrElective,CIS1,CIS2,CIS3].
                                        
uiCourse(cis4140).
uiCourse(cis4210).
uiCourse(cis4030).
cseBusElective(bus2440).
cseBusElective(bus2230).
cseJrElective(mat2352).
cseJrElective(X) :- upperCIS(X).
cseJrElective(bus4310).
upperCIS(cis3010).
upperCIS(cis3030).
upperCIS(cis3050).
upperCIS(cis3152).
upperCIS(cis3210).
upperCIS(cis3250).
upperCIS(cis3310).
upperCIS(cis3610).
upperCIS(cis3620).
upperCIS(cis4020).
upperCIS(cis4030).
upperCIS(cis4040).
upperCIS(cis4050).
upperCIS(cis4120).
upperCIS(cis4140).
upperCIS(cis4150).
upperCIS(cis4210).
upperCIS(cis4220).
upperCIS(cis4230).
upperCIS(cis4310).
upperELT(elt4010).
cseSrElective(mat3720).
cseSrElective(X) :- upperCIS(X).
cseSrElective(X) :- upperELT(X).

/* what courses satisfies some requirement */
satisfiesMajor(cse,_,C) :- member(C,[cis1120,cis1151,cis1152,cis2151,mat2021,cis2320,cis2230,cis2260]).
satisfiesMajor(cse,_,C) :- cisIntroProg(Courses),member(C,Courses).
satisfiesMajor(cse,_,C) :- cisIntroMath(C).
satisfiesMajor(cse,_,C) :- cseSophomore(C).
satisfiesMajor(cse,Degree,C) :- discreteSeq(Degree,DCourses),member(C,DCourses).
satisfiesMajor(cse,_,C) :- satisfiesVTC(C).
satisfiesMajor(cse,_,C) :- member(C,[cis2730,mat2021,cis2010]).
satisfiesMajor(cse,bs,C) :- upperCIS(C).
satisfiesMajor(cse,bs,C) :- cseJrElective(C).
satisfiesMajor(cse,bs,C) :- member(C,[cis4721,cis4722,hum2060,bus4530]).
satisfiesMajor(cse,bs,C) :- cseBusElective(C).
satisfiesMajor(cse,bs,C) :- cseSrElective(C).
