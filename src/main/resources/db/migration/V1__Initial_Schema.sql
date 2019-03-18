CREATE TABLE TBL_SYSTEM_USER (
  ID                   BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
  LOGIN                VARCHAR(256) NOT NULL,
  PASSWORD             VARCHAR(256) NOT NULL,
  PRIMARY KEY (ID),
  CONSTRAINT UNQ_SYSTEM_USER UNIQUE(LOGIN)
);

CREATE TABLE TBL_LANGUAGE (
  ID                   BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
  NAME                 VARCHAR(256) NOT NULL,
  PRIMARY KEY (ID),
  CONSTRAINT UNQ_LANGUAGE UNIQUE(NAME)
);

CREATE TABLE TBL_ARTICLE (
  ID                      BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
  LANGUAGE_ID             BIGINT NOT NULL,
  SYSTEM_USER_ID          BIGINT NOT NULL,
  TITLE                   VARCHAR(256) NOT NULL,
  IS_PUBLIC               BOOLEAN NOT NULL,
  CONTENT                 CLOB,
  REFERENCE               VARCHAR(256) NOT NULL,
  CREATED                 TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (ID),
  FOREIGN KEY (LANGUAGE_ID) REFERENCES TBL_LANGUAGE (ID)
);

-- ALTER TABLE ARTICLE ALTER COLUMN CREATED SET DEFAULT now();

CREATE TABLE TBL_SYSTEM_USER_ARTICLE (
  SYSTEM_USER_ID          BIGINT NOT NULL,
  ARTICLE_ID              BIGINT NOT NULL,
  CREATED                 TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (SYSTEM_USER_ID, ARTICLE_ID),
  FOREIGN KEY (SYSTEM_USER_ID) REFERENCES TBL_SYSTEM_USER(ID),
  FOREIGN KEY (ARTICLE_ID) REFERENCES TBL_ARTICLE(ID)
);

CREATE TABLE TBL_WORD (
  ID                      BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
  LANGUAGE_ID             BIGINT NOT NULL,
  SYSTEM_USER_ID          BIGINT NOT NULL,
  WORD                    VARCHAR(255) NOT NULL,
  CREATED                 TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (ID),
  FOREIGN KEY (LANGUAGE_ID) REFERENCES TBL_LANGUAGE(ID),
  FOREIGN KEY (SYSTEM_USER_ID) REFERENCES TBL_SYSTEM_USER(ID),
  CONSTRAINT UNQ_WORD UNIQUE(LANGUAGE_ID, SYSTEM_USER_ID, WORD)
);

CREATE TABLE TBL_PARAMETER (
  ID                      BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
  NAME                    VARCHAR(255) NOT NULL,
  VALUE                   VARCHAR(255) NOT NULL,
  PRIMARY KEY (ID),
  CONSTRAINT UNQ_PARAMETER UNIQUE(NAME)
);