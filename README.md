# UniDB README - Rishik Sarkar
HW3 for 01:198:336 (Principles of Information and Data Management) by Rishik Sarkar (rs1894).
This is a README file describing the MySQL Database Random Data Generation and Loading process

------------------------

## Random Data Generation
* All random data generation and scraping was done using Python, Jupyter Notebook, and Beautiful Soup. 
* All the relevant source code can be found in **Random Data Generator.ipynb**.
* All *pickle* **.ob** files can be found in the **data** Folder.
* All SQL Query **.txt** files can be found in the **query** Folder.

------------------------

### Generating Random Students:
* Read data from two text files *first-names.txt* and *last-names.txt* that were obtained from the internet
* Used the Python *random* module to generate a list of 100 random first and last name combinations, and generated a 9-digit id for each student
  * Verified that id was unique for each student during generation
* Formatted the *first_name*, *last_name*, and *id* of each student into a String resembling a SQL INSERT Query and stored as a list
* *Pickle dumped* list of commands into **student-cmd-list.ob**

------------------------

### Generating Random Majors/Minors:
* Created list *dept_list* containing list of departments: *Bio, Chem, CS, Eng, Math, Phys*
* Generated a random count of *majors* and *minors* for each student id in the previously created list of students, and saved list as *degree_count*
  * Both randomized numbers are **between 1 and 2 (inclusive)**
  * *Pickle dumped* list into **degree-count.ob**
#### Majors
* Created list *major_cmd_list* to hold SQL INSERT Query Strings for student majors
* For every *id* and its respective major count in *dept_list*, generated random department name(s) from *dept_list* and added SQL INSERT Query String to *major_cmd_list*
  * Ensured that major names were not duplicates, since a student cannot have two majors in the same field
* *Pickle dumped* list of commands into **majors-cmd-list.ob**
* Created a dictionary of generated majors for each student id, called *major_dict*
#### Minors
* Created list *minor_cmd_list* to hold SQL INSERT Query Strings for student minors
* Same process as **random major generation**, as outlined above
  * Ensured that a student's minors were unique: not duplicates of each other, or the student's majors (verified using *major_dict*)
* *Pickle dumped* list of commands into **minors-cmd-list.ob**

------------------------

### Generating List of Classes:
* Created list *courses* to store classes from all departments
* Used *Beautiful Soup* and *requests* to scrape course data from relevant **Rutgers University Course Descriptions** websites into lists for each department:
  * [Biology](https://biology.camden.rutgers.edu/undergraduate-program/undergraduate-course-descriptions/) - *Bio*
  * [Chemistry](https://chem.rutgers.edu/academics/undergraduate-program/undergraduate-course-descriptions) - *Chem*
  * [Computer Science](https://www.cs.rutgers.edu/academics/undergraduate/course-synopses) - *CS*
  * [English](https://english.rutgers.edu/academics/undergraduate-91/undergraduate-course-descriptions/fall-2022/all-course-descriptions-fall-2022.html) - *Eng*
  * [Mathematics](https://www.math.rutgers.edu/academics/undergraduate/courses) - *Math*
  * [Physics](https://physics.camden.rutgers.edu/physics-courses/) - *Phys*
* Made sure that the scraped course names were unique and properly formatted as Strings in the form [ *01:XXX:XXX course_name* ]
  * Aggregated data from all class lists into *courses*
* Randomly allocated a *credit* count (either 3 or 4) to each course in *courses* and stored final list as *courses_list*
  * *Pickle dumped* list of classes into **course-names.ob**
* Created list of commands *classes_cmd_list* and stored the relevant SQL INSERT Query String for each course in *courses*
  * *Pickle dumped* list of commands into **classes-cmd-list.ob**

------------------------

### Allocating Random Classes to Students:
Steps for random allocation of classes into *IsTaking* and *HasTaken*.
#### Random IsTaking Classes:
* Created a list containing *student ids* and a random number of *current classes* (4 or 5) called *is_taking_count*
* For each student in *is_taking_count*, generated a random list of either 4 or 5 unique classes, and stored every *student-classes* pair within a dictionary *is_taking*
  * *Pickle dumped* dictionary into **is-taking-list.ob**
* Generated SQL INSERT Query Strings for each student-class pair (one statement for every class per student) in *is_taking*, and stored commands in *is_taking_cmd_list*
  * *Pickle dumped* list of commands into **istaking-cmd-list.ob**
#### Random HasTaken Classes:
* Letter Grade List Generation:
  * Created list *possible_grades* containing letter grades **'A', 'B', 'C', 'D',** and **'F'** in varying quantities
    * 10 As, 6 Bs, 3 Cs, 1 D, 1 F
      * Thus, **P(A) = 0.48, P(B) = 0.29, P(C) = 0.14, P(D) = P(F) = 0.05**
    * This was done to increase the chances of getting certain grades over others in order to positively skew student GPAs: and cause the average GPA to be close to **3.15**
    * Without modifying probabilities, the average GPA was around **2.0**--which seemed unrealistic
* Class Count Generation:
  * Created list *has_taken_count* containing student ids and a random integer between 0 and 34 (inclusive)
    * Total credits *t* is: 0 <= *t* < 120, thus total classes *c* is around: 0 <= *c* < 35, since there are an average of 3.5 credits per course and 120 / 3.5 = 35
  * Verified that the number of *FR, SO, JR,* and *SR* were approximately equal
* For each student in *has_taken_count*, generated a random list of unique classes (additionally verifying with *is_taking*) and a random letter grade from *possible_grades* and stored aggregate as list *has_taken*
  * Ensured that the average GPA was around **3.15** and verified that the students were equally distributed into each school year
  * *Pickle dumped* list of past classes into **has-taken-list.ob**
* Generated SQL INSERT Query Strings for each student-class pair (one statement for every class per student) in *has_taken*, and stored commands in *has_taken_cmd_list*
  * *Pickle dumped* list of commands into **hastaken-cmd-list.ob**

------------------------

### Writing Queries to Text Files

