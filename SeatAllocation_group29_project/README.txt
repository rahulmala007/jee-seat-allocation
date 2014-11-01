IMP:
We want to transfer a lateday which we used in one of our earlier labs namely "Lab09(git, doxygen and beamer): In-lab Submission (Part2)".
We would rather like to get penalty on this previously submitted lab.
Hence we used up all of our given freedays(3) for this project.
Please take this into consideration. 

GitHub Repository Link:
https://github.com/siddharthbulia/cs251_group29_project.git , we are not providing any git repository in this submission as it will make the size very big.You can simpley clone it from the above link
MORE IMP:
Honour Code :

We pledge on our honour that I have not given or received any unauthorized assistance on this assignment or any previous task.


Individual Percentage:
Shubham Jadhav 	(130050011) : 90%
Siddharth Bulia (130050012) : 90%
Amit Malav 	(130050032) : 90%



We used the following previous knowledge learned in this course for this project.
Makefile
git
latex
bibtex
beamer
css
and so on...
and we learned java, JavaDoc,python,django for for this project.

FEW WORDS....
Firstly Sir, thanks for a wonderful course.
Looking behind,
We learned a lot from this course in such a short span of time..
Hope to see you as an Instructor in future.

Project Code Description:

Merit-Order-Allocation:
Description of Classes:
1) Tuple: Objects of this class stores the necessary information about a candidate(like, gen. rank, cat.rank, pd rank uniqueId etc.)
	Created for quick(stores less memory space than a object of Candidate Class) accessing of Candidates;

2) Candidate: 	Class that stores the candidate data like uniqueID, Category, Allocation status, Allocated Programme etc.

	functions inside Candidate class:
	A) getTuple(): 
		gives the Tuple object corresponding to the Candidate;

	B)Expandpref1():
		Each programme in a institute has category wise seat quota. For one student to get different seat preferences for the given programme we needed to store his/her category along with programme code. Expandpref1() expands the preference by adding a prefix in course code(ex. if the Candidate is of SC category, he is eligible for both general seats and category seats of that programme. so we appended course code string to nos(0 to 7) for respective birth-categories with help of this function).

	C) Expendpref2(): This also functions same as Expandpref1() except it is used in de-reservation.
		at the time of de-reservation candidates who failed to get a seat in 1st round participate in de-reservation round and vacant seats in their preference scope are opened for them.

	3) CandComparatorgen:
		class for sorting 2 Candidate objects with respect to their rank(for both gen or category) 
		it is used to make MeritList.

	4) VirtualProgramme: Most important class for the code.
		Objects of this class are the programmes candidates filling preferances for.
	
	Data Members of the class: Almost all the data members store the progress of programme bar(how much seats are left, no. of candidates and there details that have been already allocated this programme)


Functions inside Candidate class:


	IsCandidateok(): Checks whether the candidate eligible to take different seats (ex. Candidate, having general rank 0 but has got rank in his own Category, can not qualify for general seat).

	IsVacant(): Checks whether the programme has vacant seats in it, adding to this this also checks if one candidate who is on border and disqualified for the programme by just 1 rank, has rank equal to closing rank( equal rank case), in that case it gives that candidate seat and increase the programme quota by 1;

	isAllocated: Returns a bool, true if candidate is already allocated(to prevent repeating);

	5) VirtualProgrammeMap: Class for mapping course code to its Virtual Programme
							HashMap: takes modified code as key and returns Virtual Programme

	6) DsProgramme: Virtual programmes for allocating seats to DS category students.

	Implementation of allocating seats to DS students:
		When storing choices of programmes, first character of string programme code represents the college. So we are storing first character in a 		Arraylist of characters(excluding repeatation).
	now every college wrt each character has DS quota of 2 in total.
	We created DsProgrammeMap to map college programme object to character string.
	And then allocate the seats to all DS students untill quota fills up. unallocated students will now go through the same algo as general category 	 students. And allocated DS students will not participate in that allocation as they have alread been allocated

	7)MeritList: data members: Merit list of all categories that are sorted later.

	8)MeritOrderAdmission(): Algorithm runs in here and also take input same as GaleShapleyAdmission;

	Final Output: Gives the desired out put in a file named "outputMeritOrderAdmission.csv" in the same folder as of Main.java


some extra classes and functions are used  for gs as stated below
Gale-Shapley-Algorithm
	Description of Classes:
	TupleComparator<category> 
		A comparator class to sort the tuples of virtual program <category>

Candidate
	boolean isWait
		stores true if the candidate is waitlisted
	boolean isdone
		stores if candidate is waitlisted

	expandpref()
		expands the preferences list of candidate based on his/her category and taking into consideration deresvation(used for gs only).

VirtualProgramme

	sortc()
		A function of Candidate class which assigns a comparator depending upon the cat of VP

	filter()
		Keeps the first q tuples and deletes the rest 

MeritList1
	[GS version]
	Implements a Hashmap with key unique id of candidate and object Tuple.
	Used for fast access of ranks.
	They are not appended, rather candidates (tuples) are stored in an array and are sorted accordingly as defined by the comparator.

	GaleShapleyAdmission
		Implements the algorithm.

Main
	Creates the objects of both the classes.
	Redirects the output in corresponding files.

Work Distribution:
Siddharth Bulia did the whole lab 11, comprising all of django, css, etc.
Shubham Jadhav implemented the GaleShapley Algorithm part and creating the constructor.
Amit Malav implemented the free version.

PS
Program takes input from the same dir.
Output files are in the same directory.

Input files:
choices.csv
ranklist.csv
programs.csv
Input testcases are present in folder named test_cases

Output files
outputGaleShapleyAdmission.csv
outputGaleShapleyAdmission.csv

Web application:
For making the web application for providing a interface to the candidates. For this we have used the django framework of python.
The name of my project is admission. I have made one app in this namely Candidate which have two models "course" and "Candidate". All the function definitions are written in admission/views.py file and all the templates are stored admission/templates folder.We enabled "static" in the project to make use of css and images.The url's are stored in admission/url.py file.The static files are present in static folder.All the data files for the initialising the databases are stored admission/data folder.We have used two templates for the project.One for login form and one for account pages.
The web application provide the candidates a number of features.
	-- The project have one Superuser who can initialise the databases.One should not change the username of Superuser, however the password and other details can be changed.
	-- It have a login page, from one can login to their account.Forgot password feature is also enabled to help someone retrieve their password by asnwering the security question.By default, the question is the What_is_your_date_of_birth? and the answer is his/her date of birth.
	-- In the home page,we have a options like, feature to predict the colleges which user can get by his rank given the college or branch.
	-- One can also update his profile by using updateProfile feature.
	-- By updateProfile feature, candidate can give his/her preferences or remove his/her old preferences.One thing the candidate have to keep in mind is that he fill his preferences by order , he cannot change the order of them. If he have to do so, he needs to remove some preferences and add them in order again.
	-- It also have a feature to predict rank for some other ranks by the college and branches
	
The structure of our project is:

admission
|-- admission
|   |-- data
|   |   |-- combined_data.csv
|   |   `-- ranklist.csv
|   |-- __init__.py
|   |-- __init__.pyc
|   |-- models.py
|   |-- __pycache__
|   |   |-- __init__.cpython-34.pyc
|   |   |-- settings.cpython-34.pyc
|   |   |-- urls.cpython-34.pyc
|   |   |-- views.cpython-34.pyc
|   |   `-- wsgi.cpython-34.pyc
|   |-- settings.py
|   |-- settings.pyc
|   |-- templates
|   |   |-- base.html
|   |   |-- BranchPref.html
|   |   |-- home_page.html
|   |   |-- index.html
|   |   |-- logged_in_user_list.html
|   |   |-- login_page.html
|   |   |-- rank_based.html
|   |   |-- security.html
|   |   |-- update.html
|   |   `-- updateProfile.html
|   |-- urls.py
|   |-- urls.pyc
|   |-- views.py
|   |-- views.pyc
|   |-- wsgi.py
|   `-- wsgi.pyc
|-- Candidate
|   |-- admin.py
|   |-- admin.pyc
|   |-- __init__.py
|   |-- __init__.pyc
|   |-- migrations
|   |   |-- 0001_initial.py
|   |   |-- 0001_initial.pyc
|   |   |-- 0002_candidate_dob.py
|   |   |-- 0002_candidate_dob.pyc
|   |   |-- 0003_auto_20141101_0245.py
|   |   |-- 0003_auto_20141101_0245.pyc
|   |   |-- 0004_auto_20141101_1938.py
|   |   |-- 0004_auto_20141101_1938.pyc
|   |   |-- __init__.py
|   |   |-- __init__.pyc
|   |   `-- __pycache__
|   |       |-- 0001_initial.cpython-34.pyc
|   |       |-- 0002_candidate_dob.cpython-34.pyc
|   |       |-- 0003_auto_20141101_0245.cpython-34.pyc
|   |       `-- __init__.cpython-34.pyc
|   |-- models.py
|   |-- models.pyc
|   |-- __pycache__
|   |   |-- admin.cpython-34.pyc
|   |   |-- __init__.cpython-34.pyc
|   |   `-- models.cpython-34.pyc
|   |-- tests.py
|   `-- views.py
|-- db.sqlite3
|-- manage.py
`-- static
    |-- entrar_shadow-pack
    |   |-- css
    |   |   `-- style.css
    |   |-- images
    |   |   |-- Thumbs.db
    |   |   `-- user.png
    |   `-- index.html
    `-- slickgray
        |-- images
        |   |-- background.png
        |   |-- leftmenu_bottom.png
        |   |-- leftmenu_link.png
        |   |-- leftmenu_top.png
        |   |-- link_background.png
        |   |-- main_back.png
        |   |-- main_bottom.png
        |   |-- main_top.png
        |   `-- preview.jpg
        |-- index.html
        `-- style.css

References:
http://www.djangobook.com
https://docs.djangoproject.com/en/1.7/
http://bryantsmith.com/template/slickgray/
http://w3layouts.com/entrar-shadow-flat-form-template/
http://stackoverflow.com






Sources:
google.co.in
stackoverflow
java tutorials
bibtex documentation
django tutorials
PS: We took some help in creating testcases from other group members for verification sake
