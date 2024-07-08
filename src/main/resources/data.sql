INSERT INTO teacher (birthday, name)
VALUES
  ('1987-02-10', '현우진'),
  ('1976-11-26', '정승제'),
  ('1989-01-01', '현우진');

INSERT INTO student (email, nickname)
VALUES
  ('test1@example.com', '철수'),
  ('test2@example.com', '영희'),
  ('test3@example.com', '민수');

INSERT INTO lecture (teacher_id, price, created_date_time, modified_date_time, description, title, category)
VALUES
  (1, 10000, '2024-07-04 09:31:00', '2024-07-08 09:31:00', '수능 기출', '수분감 수학1', '수학'),
  (2, 20000, '2024-07-07 09:31:00', '2024-07-08 09:31:00', '수능 개념', '지구과학1 개념완성', '과학'),
  (3, 15000, '2024-07-05 09:31:00', '2024-07-08 09:31:00', '고난이도 문제', '두릴 기하와벡터', '수학');


INSERT INTO enrollment (lecture_id, student_id, created_date_time)
VALUES
  (1, 1, '2024-07-06 09:31:00'),
  (1, 3, '2024-07-05 09:31:00'),
  (2, 1, '2024-07-08 09:31:00'),
  (2, 2, '2024-07-07 09:31:00'),
  (2, 3, '2024-07-08 15:33:00');