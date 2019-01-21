-- ###################################################################################################################################
-- ## IMPORTANT BEFORE you start the Application!!! For the first initialization of your local database you have to run this queries!
-- ###################################################################################################################################

-- CREATE USER postgres WITH LOGIN
--  PASSWORD 'postgres';

CREATE DATABASE dbs_zih_daf WITH
OWNER = postgres
ENCODING = 'UTF8'
LC_COLLATE = 'en_US.utf8'
LC_CTYPE = 'en_US.utf8'
TABLESPACE = pg_default
CONNECTION LIMIT = -1;

COMMENT ON DATABASE dbs_zih_daf IS 'dbs_zih_daf database';

-- Remember to switch to database dbs_zih_daf before creating the schema below.
-- If you are not sure which database you are, execute the command select current_database();

CREATE SCHEMA scm_zih_daf AUTHORIZATION postgres;
