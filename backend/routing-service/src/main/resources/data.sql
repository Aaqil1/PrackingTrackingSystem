INSERT INTO PARKING_OPTION (id, lot_name, city, area, distance_km, available_slots, supported_sizes)
VALUES (1, 'Central Hub', 'Metropolis', 'Downtown', 4.2, 5, 'SMALL,MEDIUM,LARGE');
INSERT INTO PARKING_OPTION (id, lot_name, city, area, distance_km, available_slots, supported_sizes)
VALUES (2, 'North Logistics', 'Metropolis', 'Uptown', 6.5, 2, 'MEDIUM,LARGE');
INSERT INTO PARKING_OPTION (id, lot_name, city, area, distance_km, available_slots, supported_sizes)
VALUES (3, 'Harbor Yard', 'Coasttown', 'Pier Zone', 2.3, 4, 'SMALL,MEDIUM');

ALTER TABLE PARKING_OPTION ALTER COLUMN ID RESTART WITH 4;
