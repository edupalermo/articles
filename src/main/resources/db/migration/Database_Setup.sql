-- ###################################################################################################################################
-- ## IMPORTANT BEFORE you start the Application!!! For the first initialization of your local database you have to run this queries!
-- ###################################################################################################################################

-- CREATE USER postgres WITH LOGIN
--  PASSWORD 'postgres';

CREATE DATABASE article WITH
OWNER = postgres
ENCODING = 'UTF8'
TABLESPACE = pg_default
CONNECTION LIMIT = -1;

COMMENT ON DATABASE dbs_zih_daf IS 'article database';

-- Remember to switch to database dbs_zih_daf before creating the schema below.
-- If you are not sure which database you are, execute the command select current_database();

CREATE SCHEMA scm_article AUTHORIZATION postgres;
