# Problem Description

Y University has decided to consolidate the functionality of three small overlapping database systems which support applications for 1) teaching (e.g. instructor assignment and evaluation), for 2) registration (e.g. online course status, waiting lists), and for 3) student records (e.g. transcript generation).

The resulting new system will support the following enterprise description: Professors and graduate teaching assistants (GTAs) are assigned as a team to administer the sections of each class being offered in a semester. At the end of the semester, they get a "team rating" (professors and GTAs together get one rating per section). To support the assignment of professors to sections, a record is kept of which class each professor can teach. Classes can have one or more prerequisite classes. Students can take several sections each semester, and receive a grade for taking each section. Students may end up waiting for some sections, and receive a "rank" (determining the order they will be admitted if other students drop). However, no more than 10 students can wait on a class at the same time. Note that GTAs are students, however they differ in that they have a salary. All people (e.g. students, professors) are uniquely identified by their social security number. All classes are identified by department name (e.g. "EECS") and course number (e.g. "3421"). Sections of classes are distinguished by their section number (e.g. "N").

 

Given this functional description of the business processes at YU:

Draw an ER-diagram for the database, identifying the following: (i) all the entity sets, (ii) all the relationship sets and their cardinalities (key constraints, i.e. "many to many", "one to one", etc.), and (iii) the key for each entity set (and weak entity set, if any). You can invent your own attribute(s) for the entity sets (in addition to any mentioned). 

Indicate (what and why) feature(s)/property(ies) in the above description that are cannot captured by your ER-diagram.
