INSERT INTO CONSUMPTION_RECORD (id, vehicle_id, metric_name, expected_days, actual_days, last_checked_date)
VALUES (1, 'MH12AB1234', 'Coolant', 30, 15, '2024-05-10');
INSERT INTO CONSUMPTION_RECORD (id, vehicle_id, metric_name, expected_days, actual_days, last_checked_date)
VALUES (2, 'MH12AB1234', 'Fuel', 7, 7, '2024-05-05');
INSERT INTO CONSUMPTION_RECORD (id, vehicle_id, metric_name, expected_days, actual_days, last_checked_date)
VALUES (3, 'MH12AB5678', 'Brake Fluid', 45, 50, '2024-04-28');

ALTER TABLE CONSUMPTION_RECORD ALTER COLUMN ID RESTART WITH 4;
