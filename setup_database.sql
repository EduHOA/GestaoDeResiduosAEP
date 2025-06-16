CREATE DATABASE IF NOT EXISTS waste_management_dev;
CREATE USER IF NOT EXISTS 'wasteuser'@'localhost' IDENTIFIED BY 'wastepassword';
GRANT ALL PRIVILEGES ON waste_management_dev.* TO 'wasteuser'@'localhost';
FLUSH PRIVILEGES; 