CREATE USER jdbiuser WITH PASSWORD 'jdbiuser';
ALTER USER jdbiuser with SUPERUSER;
CREATE DATABASE jdbitest;
GRANT EXECUTE ON ALL FUNCTIONS IN SCHEMA public TO jdbiuser;