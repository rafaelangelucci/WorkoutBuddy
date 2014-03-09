CREATE TABLE User(
       username char(50) PRIMARY KEY,
       password char(50)
);

CREATE TABLE Exercise(
       e_id  INT PRIMARY KEY,
       type char(10),
       name char(50),
       description char(200),
       username char(50) REFERENCES User(username)
);

CREATE TABLE Workout(
       w_id INT PRIMARY KEY,
       name char(50),
       date DATE UNIQUE,
       description char(200),
       username char(50) REFERENCES User(username)
);

CREATE TABLE Sets(
       s_id INT PRIMARY KEY,
       reps INT,
       weight INT,
       time TIME,
       priority INT,
       e_id INT REFERENCES Exercise(e_id),
       w_id INT REFERENCES Workout(w_id)	       
);

CREATE TABLE Template_Exercise(
       te_id INT PRIMARY KEY,
       e_id INT REFERENCES Exercise(e_id),
       numSets INT,
       reps INT
);

CREATE TABLE Template_Workout(
       t_id INT PRIMARY KEY,
       name char(50),
       description char(200)
       username char(50) REFERENCES User(username)
);

CREATE TABLE Template_Linker(
       te_id INT REFERENCES Template_Exercise(te_id),
       t_id INT REFERENCES Template_Workout(t_id),
       priority INT,
       primary key (te_id, t_id)
);




       