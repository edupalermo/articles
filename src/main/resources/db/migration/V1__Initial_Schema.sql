CREATE TABLE USER (
  ID                   BIGSERIAL NOT NULL,
  LOGIN                VARCHAR(256) NOT NULL,
  PASSWORD             VARCHAR(256) NOT NULL,
  PRIMARY KEY (ID),
  CONSTRAINT UNQ_USER UNIQUE(LOGIN)
);

CREATE TABLE LANGUAGE (
  ID                   BIGSERIAL NOT NULL,
  NAME                 VARCHAR(256) NOT NULL,
  PRIMARY KEY (ID),
  CONSTRAINT UNQ_LANGUAGE UNIQUE(NAME)
);

CREATE TABLE ARTICLE (
  ID                      BIGSERIAL NOT NULL,
  LANGUAGE_ID             BIGINT NOT NULL,
  USER_ID                 BIGINT NOT NULL,
  TITLE                   VARCHAR(256) NOT NULL,
  PUBLIC                  BOOLEAN NOT NULL,
  CONTENT                 TEXT,
  REFERENCE               VARCHAR(256) NOT NULL,
  CREATED                 TIMESTAMP NOT NULL DEFAULT now(),
  PRIMARY KEY (ID),
  FOREIGN KEY (LANGUAGE_ID) REFERENCES LANGUAGE (ID)
);

-- ALTER TABLE ARTICLE ALTER COLUMN CREATED SET DEFAULT now();

CREATE TABLE USER_ARTICLE {
  USER_ID                 BIGINT NOT NULL,
  ARTICLE_ID              BIGINT NOT NULL,
  CREATED                 TIMESTAMP NOT NULL,
  PRIMARY KEY (USER_ID, ARTICLE_ID),
  FOREIGN KEY (USER_ID) REFERENCES USER (ID)
  FOREIGN KEY (ARTICLE_ID) REFERENCES ARTICLE (ID)
}

CREATE TABLE WORD (
  ID                      BIGSERIAL NOT NULL,
  LANGUAGE_ID             BIGINT NOT NULL,
  USER_ID                 BIGINT NOT NULL,
  WORD                    VARCHAR(255) NOT NULL,
  CREATED                 TIMESTAMP NOT NULL,
  PRIMARY KEY (ID),
  FOREIGN KEY (LANGUAGE_ID) REFERENCES LANGUAGE (ID),
  FOREIGN KEY (USER_ID) REFERENCES USER (ID),
  CONSTRAINT UNQ_WORD UNIQUE(LANGUAGE_ID, USER_ID, WORD)
);
