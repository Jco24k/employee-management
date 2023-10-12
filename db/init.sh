#!/bin/bash

set -e

mysql -u root <<-EOSQL
    CREATE USER '$MYSQL_USER'@'%' IDENTIFIED BY '$MYSQL_PASSWORD';
    CREATE DATABASE IF NOT EXISTS $MYSQL_DATABASE;
    GRANT ALL PRIVILEGES ON $MYSQL_DATABASE.* TO '$MYSQL_USER'@'%';
    FLUSH PRIVILEGES;
EOSQL