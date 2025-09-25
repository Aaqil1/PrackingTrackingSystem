INSERT INTO PARKING_LOT (id, name, city, area) VALUES (1, 'Central Hub', 'Metropolis', 'Downtown');
INSERT INTO PARKING_LOT (id, name, city, area) VALUES (2, 'Harbor Yard', 'Coasttown', 'Pier Zone');

INSERT INTO PARKING_SLOT (id, label, size, occupied, parking_lot_id) VALUES (1, 'C-S1', 'SMALL', FALSE, 1);
INSERT INTO PARKING_SLOT (id, label, size, occupied, parking_lot_id) VALUES (2, 'C-M1', 'MEDIUM', TRUE, 1);
INSERT INTO PARKING_SLOT (id, label, size, occupied, parking_lot_id) VALUES (3, 'C-L1', 'LARGE', FALSE, 1);
INSERT INTO PARKING_SLOT (id, label, size, occupied, parking_lot_id) VALUES (4, 'H-S1', 'SMALL', FALSE, 2);
INSERT INTO PARKING_SLOT (id, label, size, occupied, parking_lot_id) VALUES (5, 'H-M1', 'MEDIUM', FALSE, 2);
INSERT INTO PARKING_SLOT (id, label, size, occupied, parking_lot_id) VALUES (6, 'H-L1', 'LARGE', TRUE, 2);

ALTER TABLE PARKING_LOT ALTER COLUMN ID RESTART WITH 3;
ALTER TABLE PARKING_SLOT ALTER COLUMN ID RESTART WITH 7;
