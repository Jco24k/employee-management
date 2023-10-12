#!/bin/bash


echo "Waiting for connection: $DB_HOST:$DB_PORT ...!"

while ! nc -z $DB_HOST $DB_PORT; do
  sleep 1
done

sleep 5

echo "$DB_HOST:$DB_PORT Connecting ...!"

exec java -jar app.jar
