INSERT INTO  teachers(id, first_name, last_name) VALUES
(101, 'HC', 'Verma'),
(102, 'Nawal Kishore', 'Sharma'),
(103, 'Surya', 'Kumar');

INSERT INTO groups(id, name) VALUES
(101, 'Group A'),
(102, 'Group B'),
(103, 'Group C');

INSERT INTO students(id, first_name, last_name, group_id) VALUES
(101, 'Vishal', 'Chauhan', 101),
(102, 'Bhanu', 'Pratap', 102),
(103, 'Kratika', 'Sharma', 103);

INSERT INTO subjects(id,title) VALUES
(101,'Mathematics'),
(102,'Physics'),
(103,'Chemistry');

INSERT INTO marks(id, student_id, subject_id, date, marks) VALUES
(101, 101, 101, NOW(), 76),
(102, 102, 101, NOW(), 56),
(103, 103, 101, NOW(), 88),
(104, 101, 102, NOW(), 66),
(105, 102, 102, NOW(), 79),
(106, 103, 102, NOW(), 77),
(107, 101, 103, NOW(), 79),
(108, 102, 103, NOW(), 90),
(109, 103, 103, NOW(), 96),
(110, 101, 101, NOW(), 86),
(111, 102, 102, NOW(), 56),
(112, 103, 103, NOW(), 46);


INSERT INTO  subject_teacher_mapping(id, subject_id, teacher_id, group_id) VALUES
(101, 102, 101, 101),
(102, 102, 101, 102),
(103, 102, 101, 103),
(104, 101, 103, 101),
(105, 101, 103, 102),
(106, 101, 103, 103),
(107, 103, 102, 101),
(108, 103, 102, 102),
(109, 103, 102, 103);
