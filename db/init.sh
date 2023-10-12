#!/bin/bash

set -e

mysql -u root <<-EOSQL
    CREATE USER '$DB_USER'@'%' IDENTIFIED BY '$DB_PASS';
    CREATE DATABASE IF NOT EXISTS $DB_NAME;
    GRANT ALL PRIVILEGES ON $DB_NAME.* TO '$DB_USER'@'%';
    FLUSH PRIVILEGES;
EOSQL