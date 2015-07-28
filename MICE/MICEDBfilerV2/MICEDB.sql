CREATE TABLE ANSTALLD
(
  AID Integer NOT NULL,
  NAMN Varchar(255),
  TELEFON Varchar(255),
  MAIL Varchar(255),
  PRIMARY KEY (AID)
);
CREATE TABLE ARBETAR_I
(
  AID Integer NOT NULL,
  SID Integer NOT NULL,
  PRIMARY KEY (AID,SID)
);
CREATE TABLE HAR_KOMPETENS
(
  AID Integer NOT NULL,
  KID Integer NOT NULL,
  PID Integer NOT NULL,
  KOMPETENSNIVA Integer,
  PRIMARY KEY (AID,KID,PID)
);
CREATE TABLE INNEFATTAR
(
  SID Integer NOT NULL,
  PID Integer NOT NULL,
  PRIMARY KEY (SID,PID)
);
CREATE TABLE KOMPETENSDOMAN
(
  KID Integer NOT NULL,
  BENAMNING Varchar(255),
  BESKRIVNING Varchar(255),
  PRIMARY KEY (KID)
);
CREATE TABLE PLATTFORM
(
  PID Integer NOT NULL,
  BENAMNING Varchar(255),
  PRODUCENT Varchar(255),
  BESKRIVNING Varchar(255),
  PRIMARY KEY (PID)
);
CREATE TABLE PROJEKTLEDARE
(
  AID Integer NOT NULL,
  ANTAL_PROJETLEDAR_UPPDRAG Integer,
  PRIMARY KEY (AID)
);
CREATE TABLE SPECIALIST
(
  AID Integer NOT NULL,
  PRIMARY KEY (AID)
);
CREATE TABLE SPELPROJEKT
(
  SID Integer NOT NULL,
  BETECKNING Varchar(255),
  STARTDATUM Date,
  RELEASEDATUM Date,
  AID Integer,
  PRIMARY KEY (SID)
);
/********************* VIEWS **********************/

/******************* EXCEPTIONS *******************/

/******************** TRIGGERS ********************/


ALTER TABLE ARBETAR_I ADD
  FOREIGN KEY (AID) REFERENCES SPECIALIST (AID) ON DELETE CASCADE;
ALTER TABLE ARBETAR_I ADD
  FOREIGN KEY (SID) REFERENCES SPELPROJEKT (SID) ON DELETE CASCADE;
ALTER TABLE HAR_KOMPETENS ADD
  FOREIGN KEY (AID) REFERENCES SPECIALIST (AID) ON DELETE CASCADE;
ALTER TABLE HAR_KOMPETENS ADD
  FOREIGN KEY (KID) REFERENCES KOMPETENSDOMAN (KID) ON DELETE CASCADE;
ALTER TABLE HAR_KOMPETENS ADD
  FOREIGN KEY (PID) REFERENCES PLATTFORM (PID) ON DELETE CASCADE;
ALTER TABLE INNEFATTAR ADD
  FOREIGN KEY (SID) REFERENCES SPELPROJEKT (SID) ON DELETE CASCADE;
ALTER TABLE INNEFATTAR ADD
  FOREIGN KEY (PID) REFERENCES PLATTFORM (PID) ON DELETE CASCADE;

ALTER TABLE SPELPROJEKT ADD
  FOREIGN KEY (AID) REFERENCES PROJEKTLEDARE (AID);

ALTER TABLE SPECIALIST ADD
  FOREIGN KEY (AID) REFERENCES ANSTALLD (AID) ON DELETE CASCADE;

ALTER TABLE PROJEKTLEDARE ADD
  FOREIGN KEY (AID) REFERENCES ANSTALLD (AID) ON DELETE CASCADE;




GRANT DELETE, INSERT, REFERENCES, SELECT, UPDATE
 ON ANSTALLD TO  SYSDBA WITH GRANT OPTION;

GRANT DELETE, INSERT, REFERENCES, SELECT, UPDATE
 ON ARBETAR_I TO  SYSDBA WITH GRANT OPTION;

GRANT DELETE, INSERT, REFERENCES, SELECT, UPDATE
 ON HAR_KOMPETENS TO  SYSDBA WITH GRANT OPTION;

GRANT DELETE, INSERT, REFERENCES, SELECT, UPDATE
 ON INNEFATTAR TO  SYSDBA WITH GRANT OPTION;

GRANT DELETE, INSERT, REFERENCES, SELECT, UPDATE
 ON KOMPETENSDOMAN TO  SYSDBA WITH GRANT OPTION;

GRANT DELETE, INSERT, REFERENCES, SELECT, UPDATE
 ON PLATTFORM TO  SYSDBA WITH GRANT OPTION;

GRANT DELETE, INSERT, REFERENCES, SELECT, UPDATE
 ON PROJEKTLEDARE TO  SYSDBA WITH GRANT OPTION;

GRANT DELETE, INSERT, REFERENCES, SELECT, UPDATE
 ON SPECIALIST TO  SYSDBA WITH GRANT OPTION;

GRANT DELETE, INSERT, REFERENCES, SELECT, UPDATE
 ON SPELPROJEKT TO  SYSDBA WITH GRANT OPTION;