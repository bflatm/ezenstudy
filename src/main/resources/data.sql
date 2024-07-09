INSERT INTO teacher (birthday, name)
VALUES
  ('1987-02-10', '현우진'),
  ('1976-11-26', '정승제'),
  ('1989-01-01', '현우진');

INSERT INTO student (email, nickname, password)
VALUES
  ('test1@example.com', '철수', '7293ee3c60737ccf93f2aeb6af3a666d01f060fe495ae9b69bcdf7df934437d7'), -- 'mypw1!'
  ('test2@example.com', '영희', 'd34eb4914a78fffa6d68663556a153cfbbc959fce06675e7015b28faa8e53c68'), -- 'mypw2!'
  ('test3@example.com', '민수', '760860966656230153e2103951e992f19324e16d5ea66c3c8df8b35a245df076'), -- 'mypw3!'
  ('abc@gmail.com', '도라에몽', '033b83d92431548e13424903c235a9922af56dd34d53c9b72b37cf158489213e'); -- 'abc123!'

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