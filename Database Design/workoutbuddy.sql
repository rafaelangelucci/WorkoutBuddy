CREATE TABLE User(
       username char(50) PRIMARY KEY,
       password char(200)
);

CREATE TABLE Exercise(
       e_id  INT PRIMARY KEY AUTO_INCREMENT,
       type char(10),
       name char(50),
       description char(200),
       username char(50),
       FOREIGN KEY(username) REFERENCES User(username)
);

CREATE TABLE Workout(
       w_id INT PRIMARY KEY AUTO_INCREMENT,
       name char(50),
       date DATE UNIQUE,
       description char(200),
       username char(50),
       FOREIGN KEY(username) REFERENCES User(username)
);

CREATE TABLE Sets(
       s_id INT PRIMARY KEY AUTO_INCREMENT,
       reps INT,
       weight INT,
       time TIME,
       priority INT,
       e_id INT,
       w_id INT,
       FOREIGN KEY(e_id) REFERENCES Exercise(e_id),
       FOREIGN KEY(w_id) REFERENCES Workout(w_id)
);

CREATE TABLE Template_Exercise(
       te_id INT PRIMARY KEY AUTO_INCREMENT,
       e_id INT,
       numSets INT,
       reps INT,
       FOREIGN KEY(e_id) REFERENCES Exercise(e_id)
);

CREATE TABLE Template_Workout(
       t_id INT PRIMARY KEY AUTO_INCREMENT,
       name char(50),
       description char(200),
       username char(50),
       FOREIGN KEY(username) REFERENCES User(username)
);

CREATE TABLE Template_Linker(
       te_id INT,
       t_id INT,
       priority INT,
       primary key (te_id, t_id),
       FOREIGN KEY(t_id) REFERENCES Template_Workout(t_id),
       FOREIGN KEY(te_id) REFERENCES Template_Exercise(te_id)
);




       