DELETE FROM roles;
INSERT INTO roles (role_name)
VALUES	  (0),
          (1),
          (2),
          (3),
          (4),
          (5);

DELETE FROM yacht_statuses;
INSERT INTO yacht_statuses (yacht_status_name)
VALUES (0),
       (1),
       (2),
       (3),
       (4),
       (5);

DELETE FROM reservation_statuses;
INSERT INTO reservation_statuses(reservation_status_name)
VALUES (0),
       (1),
       (2),
       (3),
       (4),
       (5);

DELETE FROM sailing_licenses;
INSERT INTO sailing_licenses(sailing_license_name)
VALUES (0),
       (1),
       (2),
       (3);

DELETE FROM notice_statuses;
INSERT INTO notice_statuses(notice_status_name)
VALUES (0),
       (1),
       (2);

DELETE FROM yacht_types;
INSERT INTO yacht_types(yacht_type_name)
VALUES (0),
       (1),
       (2);
