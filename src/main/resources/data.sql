INSERT INTO instructor (name)
SELECT 'Thiago Abreu' WHERE NOT EXISTS (SELECT 1 FROM instructor WHERE name = 'Thiago Abreu');

INSERT INTO instrument (name)
SELECT 'Violin' WHERE NOT EXISTS (SELECT 1 FROM instrument WHERE name = 'Violin');

INSERT INTO instrument (name)
SELECT 'Trompa' WHERE NOT EXISTS (SELECT 1 FROM instrument WHERE name = 'Trompa');

INSERT INTO instrument (name)
SELECT 'Tuba' WHERE NOT EXISTS (SELECT 1 FROM instrument WHERE name = 'Tuba');

INSERT INTO method (name)
SELECT 'Bona' WHERE NOT EXISTS (SELECT 1 FROM method WHERE name = 'Bona');

INSERT INTO method (name)
SELECT 'Schimoll' WHERE NOT EXISTS (SELECT 1 FROM method WHERE name = 'Schimoll');

INSERT INTO method (name)
SELECT 'Suzuki' WHERE NOT EXISTS (SELECT 1 FROM method WHERE name = 'Suzuki');

INSERT INTO student (name, instrument_id, instructor_id)
SELECT 'Pedro', 1, 1 WHERE NOT EXISTS (SELECT 1 FROM student WHERE name = 'Pedro');