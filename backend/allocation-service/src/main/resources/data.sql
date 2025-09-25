INSERT INTO VEHICLE (id, registration_number, model, max_volume, max_quantity) VALUES (1, 'MH12AB1234', 'City Loader', 120.0, 200);
INSERT INTO VEHICLE (id, registration_number, model, max_volume, max_quantity) VALUES (2, 'MH12AB5678', 'Express Van', 80.0, 120);
INSERT INTO VEHICLE (id, registration_number, model, max_volume, max_quantity) VALUES (3, 'MH12AB9012', 'Mega Hauler', 200.0, 400);

ALTER TABLE VEHICLE ALTER COLUMN ID RESTART WITH 4;
