START TRANSACTION;

SET FOREIGN_KEY_CHECKS=0;

TRUNCATE TABLE tasks;
TRUNCATE TABLE employees;

SET FOREIGN_KEY_CHECKS=1;
COMMIT;