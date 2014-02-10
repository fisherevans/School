/*

['courses.pl'].
['prereqs.pl'].
['offerings.pl'].
['majors.pl'].

*/

scheduleSemester(Major,CatYear,Degree,Semester,Year,PriorCourses,SemesterCourses) :-
    catalog(Major,CatYear,Degree,c).
