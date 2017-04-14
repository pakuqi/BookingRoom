#!/bin/sh -e

# Add PostgreSQL repository
# Create the file /etc/apt/sources.list.d/pgdg.list
cp /vagrant/provisioning/pgdg.list /etc/apt/sources.list.d/pgdg.list

# Import the repository signing key, and update the package lists
wget --quiet -O - https://www.postgresql.org/media/keys/ACCC4CF8.asc | sudo apt-key add -

# Install PostgreSQL 9.5
apt-get update
apt-get install -y "postgresql-9.5" "postgresql-contrib-9.5"

# Setup PostgreSQL
# create dbuser same as OS login username
sudo -u postgres createuser -DRS "vagrant"

# create DB
sudo -u postgres createdb -O "vagrant" "demodb"

# set password to vagrant
sudo -u vagrant psql "demodb" < /vagrant/provisioning/password.sql

PG_CONF="/etc/postgresql/9.5/main/postgresql.conf"
PG_HBA="/etc/postgresql/9.5/main/pg_hba.conf"

# Edit postgresql.conf to change listen address to '*':
sed -i "s/#listen_addresses = 'localhost'/listen_addresses = '*'/" "$PG_CONF"

# Add to pg_hba.conf to add password auth:
echo "host    all             all             0.0.0.0/0                md5" >> "$PG_HBA"

# Restart so that all new config is loaded:
service postgresql restart


