
create table Course
(
courseNumber INT PRIMARY KEY,
courseName varchar2(20)  NOT NULL,
timeInterval varchar2(20),
courseCost INT,
numOfStudent INT,
maxNumOfStudent INT,
courseFor varchar2(10),
managerSSN INT,
constraint manager_fk FOREIGN KEY(managerSSN ) REFERENCES employee(employeeSSN)
);



create table Join_to
(
studentSSN INT   NOT NULL,
courseNumber INT   NOT NULL,
PRIMARY KEY (studentSSN ,courseNumber),
FOREIGN KEY(studentSSN ) REFERENCES student(studentSSN),
FOREIGN KEY(courseNumber) REFERENCES course(courseNumber)
);

create table Gives
(
trainerSSN INT NOT NULL,
courseNumber INT NOT NULL,
PRIMARY KEY (trainerSSN ,courseNumber),
FOREIGN KEY(trainerSSN) REFERENCES employee(employeeSSN),
FOREIGN KEY(courseNumber) REFERENCES course(courseNumber)
);

create table Need
(
equipmentName varchar2(20)   NOT NULL,
courseNumber INT   NOT NULL,
PRIMARY KEY (equipmentName,courseNumber),
FOREIGN KEY(equipmentName) REFERENCES Equipment(equipmentName),
FOREIGN KEY(courseNumber) REFERENCES course(courseNumber)
);
