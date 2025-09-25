INSERT INTO DRIVER_SWAP_PLAN (id, origin_branch, destination_branch, departure_date, drivers_involved,
                              shift_start_time, shift_end_time, return_trip_start_time,
                              saved_wait_time_hours, saved_wait_cost, notes)
VALUES (1, 'Central Hub', 'Coast Branch', '2024-05-18', 'Driver A, Driver B',
        '07:00:00', '15:00:00', '16:30:00', 2.5, 125.0, 'Return trip piggyback to save layover');
INSERT INTO DRIVER_SWAP_PLAN (id, origin_branch, destination_branch, departure_date, drivers_involved,
                              shift_start_time, shift_end_time, return_trip_start_time,
                              saved_wait_time_hours, saved_wait_cost, notes)
VALUES (2, 'North Depot', 'Central Hub', '2024-05-20', 'Driver C, Driver D',
        '06:30:00', '14:30:00', '15:00:00', 1.5, 75.0, 'Swap for overnight return');

ALTER TABLE DRIVER_SWAP_PLAN ALTER COLUMN ID RESTART WITH 3;
