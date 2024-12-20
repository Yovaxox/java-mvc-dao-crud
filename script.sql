--------------------------------------------------------
--  File created - Sunday-December-01-2024   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table LIBRO
--------------------------------------------------------

  CREATE TABLE "JAVIER"."LIBRO" 
   (	"ID" NUMBER(*,0), 
	"ISBN" NUMBER(*,0), 
	"TITULO" VARCHAR2(255 BYTE), 
	"EDITORIAL" VARCHAR2(100 BYTE), 
	"EJEMPLARES_DISPONIBLES" NUMBER(*,0), 
	"FECHA_PUBLICACION" DATE, 
	"GENERO" VARCHAR2(100 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
REM INSERTING into JAVIER.LIBRO
SET DEFINE OFF;
--------------------------------------------------------
--  DDL for Index LIBRO_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "JAVIER"."LIBRO_PK" ON "JAVIER"."LIBRO" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Trigger LIBRO_TRG
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE TRIGGER "JAVIER"."LIBRO_TRG" 
BEFORE INSERT ON LIBRO 
FOR EACH ROW 
BEGIN
  <<COLUMN_SEQUENCES>>
  BEGIN
    IF INSERTING AND :NEW.ID IS NULL THEN
      SELECT LIBRO_SEQ.NEXTVAL INTO :NEW.ID FROM SYS.DUAL;
    END IF;
  END COLUMN_SEQUENCES;
END;
/
ALTER TRIGGER "JAVIER"."LIBRO_TRG" ENABLE;
--------------------------------------------------------
--  Constraints for Table LIBRO
--------------------------------------------------------

  ALTER TABLE "JAVIER"."LIBRO" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "JAVIER"."LIBRO" MODIFY ("ISBN" NOT NULL ENABLE);
  ALTER TABLE "JAVIER"."LIBRO" MODIFY ("TITULO" NOT NULL ENABLE);
  ALTER TABLE "JAVIER"."LIBRO" MODIFY ("EDITORIAL" NOT NULL ENABLE);
  ALTER TABLE "JAVIER"."LIBRO" MODIFY ("EJEMPLARES_DISPONIBLES" NOT NULL ENABLE);
  ALTER TABLE "JAVIER"."LIBRO" MODIFY ("FECHA_PUBLICACION" NOT NULL ENABLE);
  ALTER TABLE "JAVIER"."LIBRO" MODIFY ("GENERO" NOT NULL ENABLE);
  ALTER TABLE "JAVIER"."LIBRO" ADD CONSTRAINT "LIBRO_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
