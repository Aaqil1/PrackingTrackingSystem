INSERT INTO JOB_RECORD (id, vehicle_id, description, goods_volume, job_date, status)
VALUES (1, 'MH12AB1234', 'Groceries to Downtown', 60.0, '2024-05-01', 'COMPLETED');
INSERT INTO JOB_RECORD (id, vehicle_id, description, goods_volume, job_date, status)
VALUES (2, 'MH12AB5678', 'Electronics hub transfer', 90.0, '2024-05-12', 'IN_PROGRESS');

ALTER TABLE JOB_RECORD ALTER COLUMN ID RESTART WITH 3;
