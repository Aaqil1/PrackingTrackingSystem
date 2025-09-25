INSERT INTO DRIVER_SWAP_PLAN (id, origin_branch, destination_branch, departure_date, drivers_involved, saved_wait_time_hours, notes)
VALUES (1, 'Central Hub', 'Coast Branch', '2024-05-18', 'Driver A, Driver B', 3.5, 'Return trip piggyback to save layover');
INSERT INTO DRIVER_SWAP_PLAN (id, origin_branch, destination_branch, departure_date, drivers_involved, saved_wait_time_hours, notes)
VALUES (2, 'North Depot', 'Central Hub', '2024-05-20', 'Driver C, Driver D', 2.0, 'Swap for overnight return');

ALTER TABLE DRIVER_SWAP_PLAN ALTER COLUMN ID RESTART WITH 3;
