
-- This table is specifically for access to the MySQL workbench database (this is not launched automatically); pw policy is assumed LOW
-- As admin, run the following in from the command line: mysql -u root -p

CREATE DATABASE cloud_write;

CREATE USER 'cw_dev_user'@'localhost' IDENTIFIED BY 'admin2021admin';
-- granting access from any host may be required when running MySQL from a Docker container
CREATE USER 'cw_dev_user'@'%' IDENTIFIED BY 'admin2021admin';

GRANT SELECT ON cloud_write.* to 'cw_dev_user'@'localhost';
GRANT INSERT ON cloud_write.* to 'cw_dev_user'@'localhost';
GRANT DELETE ON cloud_write.* to 'cw_dev_user'@'localhost';
GRANT UPDATE ON cloud_write.* to 'cw_dev_user'@'localhost';

GRANT SELECT ON cloud_write.* to 'cw_dev_user'@'%';
GRANT INSERT ON cloud_write.* to 'cw_dev_user'@'%';
GRANT DELETE ON cloud_write.* to 'cw_dev_user'@'%';
GRANT UPDATE ON cloud_write.* to 'cw_dev_user'@'%';
FLUSH PRIVILEGES;

-- Don't forget to check the port number with Docker containers!!