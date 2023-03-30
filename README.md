# UniDB README - Rishik Sarkar
HW3 for 01:198:336 (Principles of Information and Data Management) by Rishik Sarkar (rs1894).
This is a README file describing the MySQL Database Random Data Generation and Loading process

------------------------

## Random Data Generation
All random data generation and scraping was done using Python, Jupyter Notebook, and Beautiful Soup. 
All the relevant source code can be found in **Random Data Generator.ipynb**.

------------------------

### Generating Random Students:
* Read data from two text files *first-names.txt* and *last-names.txt* that were obtained from the internet
* Used the Python *random* module to generate a list of 100 random first and last name combinations, and generated a 9-digit id for each student
  * Verified that id was unique for each student during generation
* Formatted the *first_name*, *last_name*, and *id* of each student into a String resembling a SQL INSERT Query and stored as a list
* *Pickled* list of commands into **student-cmd-list.ob**

------------------------

### Generating Random Majors/Minors:
* Created list *dept_list* containing list of departments: *Bio, Chem, CS, Eng, Math, Phys*
* Generated a random count of *majors* and *minors* for each student id in the previously created list of students, and saved list as *degree_count*
  * Both randomized numbers are **between 1 and 2 (inclusive)**
  * *Pickled* list into **degree-count.ob**
#### Majors
* Created list *major_cmd_list* to hold SQL INSERT Query Strings for student majors
* For every *id* and its respective major count in *dept_list*, generated random department name(s) from *dept_list* and added SQL INSERT Query String to *major_cmd_list*
  * Ensured that major names were not duplicates, since a student cannot have two majors in the same field
* *Pickled* list of commands into **majors-cmd-list.ob**
* Created a dictionary of generated majors for each student id, called *major_dict*
#### Minors
* Created list *minor_cmd_list* to hold SQL INSERT Query Strings for student minors
* Same process as **random major generation**, as outlined above
  * Ensured that a student's minors were unique: not duplicates of each other, or the student's majors (verified using *major_dict*)
* *Pickled* list of commands into **minors-cmd-list.ob**

------------------------

### Generating List of Classes:
* Created list *courses* to 
