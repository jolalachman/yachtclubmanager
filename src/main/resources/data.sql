INSERT INTO roles (role_name, permission)
VALUES	  (0, 'CREATE:RESERVATION,READ:MY_RESERVATION,UPDATE:MY_RESERVATION,READ:YACHT'),
          (1, 'CREATE:RESERVATION,READ:RESERVATION,UPDATE:RESERVATION,READ:YACHT,CREATE:PAYMENT,READ:PAYMENT,UPDATE:PAYMENT'),
          (2, 'CREATE:YACHT,READ:YACHT,UPDATE:YACHT'),
          (3, 'CREATE:YACHT,READ:YACHT,UPDATE:YACHT'),
          (4, 'CREATE:USER,READ:USER,UPDATE:USER,DELETE:USER'),
          (5, '-');

INSERT INTO yacht_statuses (yacht_status_name)
VALUES (0),
       (1),
       (2),
       (3),
       (4),
       (5);

INSERT INTO reservation_statuses(reservation_status_name)
VALUES (0),
       (1),
       (2),
       (3),
       (4);

INSERT INTO sailing_licenses(sailing_license_name)
VALUES (0),
       (1),
       (2),
       (3);

