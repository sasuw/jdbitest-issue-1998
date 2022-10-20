Project for testing bug https://github.com/jdbi/jdbi/issues/1998

Before starting test, create Postgresql DB with

name: jdbitest

user: jdbiuser

password: jdbiuser

```
CREATE USER jdbiuser WITH PASSWORD 'jdbiuser';
ALTER USER jdbiuser with SUPERUSER;
CREATE DATABASE jdbitest;
GRANT EXECUTE ON ALL FUNCTIONS IN SCHEMA public TO jdbiuser;
```
