--------------------------------------------------------
--  File created - Sexta-feira-Julho-05-2024   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Type ARRAY_T
--------------------------------------------------------

--FM_MAP_SS
--FM_ARRAY_TAB_MAP_SS

CREATE OR REPLACE EDITIONABLE TYPE "ARRAY_T" is varray(6) of int(38);


/

--------------------------------------------------------
--  DDL for Type FM_COL_MAP_SS
--------------------------------------------------------

CREATE OR REPLACE EDITIONABLE TYPE "FM_COL_MAP_SS" force -- Valida qual o nome da coluna, o seu tipo de dados se ?brigat? ou n? o grupo 
AS --  a que pertence, e se precisa de uma fun? tmap para que nela seja efetuada uma transforma?
  OBJECT
  (
    NOME        VARCHAR2(100),
    OBRIGATORIO CHAR(1),
    TIPO        VARCHAR2(50),
    GRUPO       NUMBER(1),
    REF         VARCHAR2(200),
    REF2        VARCHAR2(200),
    TMAP        VARCHAR2(50),
    /**
    * Empty constructor
    **/
    CONSTRUCTOR
  FUNCTION FM_COL_MAP_SS
    RETURN SELF
  AS
    RESULT );
/
--------------------------------------------------------
--  DDL for Type FM_FILTRO_MAP_SS
--------------------------------------------------------

CREATE OR REPLACE EDITIONABLE TYPE "FM_FILTRO_MAP_SS" force
AS
  OBJECT
  (
    REF      VARCHAR2(200),
    CONDICAO VARCHAR2(200),
    /**
    * Empty constructor
    **/
    CONSTRUCTOR
    FUNCTION FM_FILTRO_MAP_SS
      RETURN SELF
    AS
      RESULT );
/
CREATE OR REPLACE EDITIONABLE TYPE BODY "FM_COL_MAP_SS" 
AS
  constructor
  FUNCTION FM_COL_MAP_SS
    RETURN self
  AS
    result
  AS
  BEGIN
    RETURN;
  END;
END;


/
--------------------------------------------------------
--  DDL for Type FM_LISTA
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TYPE "FM_LISTA" 
IS
  TABLE OF VARCHAR2(4000);


/
CREATE OR REPLACE EDITIONABLE TYPE BODY "FM_FILTRO_MAP_SS" 
AS
  constructor
  FUNCTION FM_FILTRO_MAP_SS
    RETURN self
  AS
    result
  AS
  BEGIN
    RETURN;
  END;
END;


/
--------------------------------------------------------
--  DDL for Type FM_ARRAY_COL_MAP_SS
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TYPE "FM_ARRAY_COL_MAP_SS" 
IS
  TABLE OF FM_COL_MAP_SS;


/

--------------------------------------------------------
--  DDL for Type FM_ARRAY_FILTRO_MAP_SS
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TYPE "FM_ARRAY_FILTRO_MAP_SS" 
IS
  TABLE OF FM_FILTRO_MAP_SS;


/

--------------------------------------------------------
--  DDL for Type FM_TAB_MAP_SS
--------------------------------------------------------

CREATE OR REPLACE EDITIONABLE TYPE "FM_TAB_MAP_SS" force
AS
  OBJECT
  (
    NOME VARCHAR2(100),
    COLS FM_ARRAY_COL_MAP_SS,
    FILTROS FM_ARRAY_FILTRO_MAP_SS,
    /**
    * Empty constructor
    **/
    CONSTRUCTOR
    FUNCTION FM_TAB_MAP_SS
      RETURN SELF
    AS
      RESULT );
/

CREATE OR REPLACE EDITIONABLE TYPE BODY "FM_TAB_MAP_SS" 
AS
  constructor
  FUNCTION FM_TAB_MAP_SS
    RETURN self
  AS
    result
  AS
  BEGIN
    RETURN;
  END;
END;


/

--------------------------------------------------------
--  DDL for Type FM_ARRAY_TAB_MAP_SS
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TYPE "FM_ARRAY_TAB_MAP_SS" 
IS
  TABLE OF FM_TAB_MAP_SS;


/

--------------------------------------------------------
--  DDL for Type FM_MAP_SS
--------------------------------------------------------

CREATE OR REPLACE EDITIONABLE TYPE "FM_MAP_SS" force
AS
  OBJECT
  (
    TABS FM_ARRAY_TAB_MAP_SS,
    /**
    * Empty constructor
    **/
    CONSTRUCTOR
    FUNCTION FM_MAP_SS
      RETURN SELF
    AS
      RESULT );
/
--------------------------------------------------------
--  DDL for Type VARCHAR2S_TABLE
--------------------------------------------------------

CREATE OR REPLACE EDITIONABLE TYPE "VARCHAR2S_TABLE" AS TABLE OF VARCHAR2(4000);

/
CREATE OR REPLACE EDITIONABLE TYPE BODY "FM_MAP_SS" 
AS
  constructor
  FUNCTION FM_MAP_SS
    RETURN self
  AS
    result
  AS
  BEGIN
    RETURN;
  END;
END;

/

--------------------------------------------------------
--  DDL for Function REGEXP_ESCAPE
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE FUNCTION "REGEXP_ESCAPE" (
  expression VARCHAR2
) RETURN VARCHAR2 DETERMINISTIC
AS
BEGIN
  RETURN REGEXP_REPLACE( expression, '([$^[()+*?{\|])', '\\\1', 1, 0, 'c' );
END;

/

--------------------------------------------------------
--  DDL for Function GET_FILE_SUFFIX
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE FUNCTION "GET_FILE_SUFFIX" 
   (p_path IN VARCHAR2)
   RETURN varchar2
IS
   v_file_suffix VARCHAR2(10);
BEGIN
   v_file_suffix := SUBSTR(p_path,(INSTR(p_path,'.',-1,1)+1),length(p_path)); -- +1 ?ara retornar a extens?sem o ponto
   RETURN v_file_suffix;-- csv em vez de .csv
EXCEPTION
   WHEN OTHERS THEN
      RETURN NULL;
END;

/

--------------------------------------------------------
--  DDL for Function SPLITLIST
--------------------------------------------------------

CREATE OR REPLACE EDITIONABLE FUNCTION "SPLITLIST" (
  list  VARCHAR2,
  delim VARCHAR2 := ','
) RETURN VARCHAR2s_Table DETERMINISTIC
AS
  pattern   VARCHAR2(256);
  len       BINARY_INTEGER;
  t_items   VARCHAR2s_Table := VARCHAR2s_Table();
BEGIN
  IF list IS NULL THEN
    NULL;
  ELSIF delim IS NULL THEN
    t_items.EXTEND( LENGTH( list ) );
    FOR i IN 1 .. LENGTH( list ) LOOP
      t_items(i) := SUBSTR( list, i, 1 );
    END LOOP;
  ELSE
    pattern   := '(.*?)($|' || REGEXP_ESCAPE( delim ) || ')';
    len       := REGEXP_COUNT( list, pattern ) - 1;
    t_items.EXTEND( len );
    IF len = 1 THEN
      t_items(1) := list;
    ELSE
      FOR i IN 1 .. len LOOP
        t_items(i) := REGEXP_SUBSTR( list, pattern, 1, i, NULL, 1 );
      END LOOP;
    END IF;
  END IF;
  RETURN t_items;
END;

/
--------------------------------------------------------
--  DDL for Package MY_DEBUG
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE PACKAGE "MY_DEBUG" IS
debug CONSTANT BOOLEAN := FALSE;
trace CONSTANT BOOLEAN := TRUE;  
END my_debug;

/
--------------------------------------------------------
--  DDL for Package AS_XLSX
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE PACKAGE "AS_XLSX" is
  /**********************************************
  **
  ** Author: Anton Scheffer
  ** Date: 19-02-2011
  ** Website: http://technology.amis.nl/blog
  ** See also: http://technology.amis.nl/blog/?p=10995
  **
  ** Changelog:
  **   Date: 21-02-2011
  **     Added Aligment, horizontal, vertical, wrapText
  **   Date: 06-03-2011
  **     Added Comments, MergeCells, fixed bug for dependency on NLS-settings
  **   Date: 16-03-2011
  **     Added bold and italic fonts
  **   Date: 22-03-2011
  **     Fixed issue with timezone's set to a region(name) instead of a offset
  **   Date: 08-04-2011
  **     Fixed issue with XML-escaping from text
  **   Date: 27-05-2011
  **     Added MIT-license
  **   Date: 11-08-2011
  **     Fixed NLS-issue with column width
  **   Date: 29-09-2011
  **     Added font color
  **   Date: 16-10-2011
  **     fixed bug in add_string
  **   Date: 26-04-2012
  **     Fixed set_autofilter (only one autofilter per sheet, added _xlnm._FilterDatabase)
  **     Added list_validation = drop-down 
  **   Date: 27-08-2013
  **     Added freeze_pane
  **   Date: 17-03-2017
  **     Added preserve all the white space <t xml:space="preserve">
  **   Date: 21-03-2017
  **     Added setRowHeight
  **   Date: 20-07-2017
  **     Fixed the definition of names (<definedName>) that did not work when the sheet name is set
  **   Date: 20-07-2017
  **     Added hyperlink by location
  **
  ******************************************************************************
  ******************************************************************************
  Copyright (C) 2011, 2012 by Anton Scheffer

  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:

  The above copyright notice and this permission notice shall be included in
  all copies or substantial portions of the Software.

  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
  THE SOFTWARE.

  ******************************************************************************
  ******************************************** */
  --
  type tp_alignment is record(
    vertical   varchar2(11),
    horizontal varchar2(16),
    wrapText   boolean);
  --
  procedure clear_workbook;
  --
  procedure new_sheet(p_sheetname varchar2 := null);
  --
  function OraFmt2Excel(p_format varchar2 := null) return varchar2;
  --
  function get_numFmt(p_format varchar2 := null) return pls_integer;
  --
  function get_font(p_name      varchar2,
                    p_family    pls_integer := 2,
                    p_fontsize  number := 11,
                    p_theme     pls_integer := 1,
                    p_underline boolean := false,
                    p_italic    boolean := false,
                    p_bold      boolean := false,
                    p_rgb       varchar2 := null -- this is a hex ALPHA Red Green Blue value
                    ) return pls_integer;
  --
  function get_fill(p_patternType varchar2,
                    p_fgRGB       varchar2 := null -- this is a hex ALPHA Red Green Blue value
                    ) return pls_integer;
  --
  function get_border(p_top    varchar2 := 'thin',
                      p_bottom varchar2 := 'thin',
                      p_left   varchar2 := 'thin',
                      p_right  varchar2 := 'thin')

   return pls_integer;
  --
  function get_alignment(p_vertical   varchar2 := null,
                         p_horizontal varchar2 := null,
                         p_wrapText   boolean := null)
   return tp_alignment;
  --
  procedure cell(p_col       pls_integer,
                 p_row       pls_integer,
                 p_value     number,
                 p_numFmtId  pls_integer := null,
                 p_fontId    pls_integer := null,
                 p_fillId    pls_integer := null,
                 p_borderId  pls_integer := null,
                 p_alignment tp_alignment := null,
                 p_sheet     pls_integer := null);
  --
  procedure cell(p_col       pls_integer,
                 p_row       pls_integer,
                 p_value     varchar2,
                 p_numFmtId  pls_integer := null,
                 p_fontId    pls_integer := null,
                 p_fillId    pls_integer := null,
                 p_borderId  pls_integer := null,
                 p_alignment tp_alignment := null,
                 p_sheet     pls_integer := null);
  --
  procedure cell(p_col       pls_integer,
                 p_row       pls_integer,
                 p_value     date,
                 p_numFmtId  pls_integer := null,
                 p_fontId    pls_integer := null,
                 p_fillId    pls_integer := null,
                 p_borderId  pls_integer := null,
                 p_alignment tp_alignment := null,
                 p_sheet     pls_integer := null);
  --
  procedure hyperlink(p_col   pls_integer,
                      p_row   pls_integer,
                      p_url   varchar2,
                      p_value varchar2 := null,
                      p_sheet pls_integer := null);
  --
  procedure hyperlink_loc(p_col      pls_integer,
                          p_row      pls_integer,
                          p_location varchar2);
  --
  procedure comment(p_col    pls_integer,
                    p_row    pls_integer,
                    p_text   varchar2,
                    p_author varchar2 := null,
                    p_width  pls_integer := 150 -- pixels
                   ,
                    p_height pls_integer := 100 -- pixels
                   ,
                    p_sheet  pls_integer := null);
  --
  procedure mergecells(p_tl_col pls_integer -- top left
                      ,
                       p_tl_row pls_integer,
                       p_br_col pls_integer -- bottom right
                      ,
                       p_br_row pls_integer,
                       p_sheet  pls_integer := null);
  --
  procedure list_validation(p_sqref_col   pls_integer,
                            p_sqref_row   pls_integer,
                            p_tl_col      pls_integer -- top left
                           ,
                            p_tl_row      pls_integer,
                            p_br_col      pls_integer -- bottom right
                           ,
                            p_br_row      pls_integer,
                            p_style       varchar2 := 'stop' -- stop, warning, information
                           ,
                            p_title       varchar2 := null,
                            p_prompt      varchar := null,
                            p_show_error  boolean := false,
                            p_error_title varchar2 := null,
                            p_error_txt   varchar2 := null,
                            p_sheet       pls_integer := null);
  --
  procedure list_validation(p_sqref_col    pls_integer,
                            p_sqref_row    pls_integer,
                            p_defined_name varchar2,
                            p_style        varchar2 := 'stop' -- stop, warning, information
                           ,
                            p_title        varchar2 := null,
                            p_prompt       varchar := null,
                            p_show_error   boolean := false,
                            p_error_title  varchar2 := null,
                            p_error_txt    varchar2 := null,
                            p_sheet        pls_integer := null);
  --
  procedure defined_name(p_tl_col     pls_integer -- top left
                        ,
                         p_tl_row     pls_integer,
                         p_br_col     pls_integer -- bottom right
                        ,
                         p_br_row     pls_integer,
                         p_name       varchar2,
                         p_sheet      pls_integer := null,
                         p_localsheet pls_integer := null);
  --
  procedure set_column_width(p_col pls_integer, p_width number, p_sheet pls_integer := null);
  --
  procedure set_column(p_col       pls_integer,
                       p_numFmtId  pls_integer := null,
                       p_fontId    pls_integer := null,
                       p_fillId    pls_integer := null,
                       p_borderId  pls_integer := null,
                       p_alignment tp_alignment := null,
                       p_sheet     pls_integer := null);
  --
  procedure set_row_height(p_row pls_integer, p_height number, p_sheet pls_integer := null);
  --
  procedure set_row(p_row       pls_integer,
                    p_numFmtId  pls_integer := null,
                    p_fontId    pls_integer := null,
                    p_fillId    pls_integer := null,
                    p_borderId  pls_integer := null,
                    p_alignment tp_alignment := null,
                    p_sheet     pls_integer := null);
  --
  procedure freeze_rows(p_nr_rows pls_integer := 1, p_sheet pls_integer := null);
  --
  procedure freeze_cols(p_nr_cols pls_integer := 1, p_sheet pls_integer := null);
  --
  procedure freeze_pane(p_col pls_integer, p_row pls_integer, p_sheet pls_integer := null);
  --
  procedure set_autofilter(p_column_start pls_integer := null,
                           p_column_end   pls_integer := null,
                           p_row_start    pls_integer := null,
                           p_row_end      pls_integer := null,
                           p_sheet        pls_integer := null);
  --
  function finish return blob;
  --
  procedure save(p_directory varchar2, p_filename varchar2);
  --
  procedure query2sheet(p_sql            varchar2,
                        p_column_headers boolean := true,
                        p_directory      varchar2 := null,
                        p_filename       varchar2 := null,
                        p_sheet          pls_integer := null);
  --
end;

/
--------------------------------------------------------
--  DDL for Function CHECK_LIST_INTERSECT
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE FUNCTION "CHECK_LIST_INTERSECT" (
  list1 VARCHAR2,
  list2 VARCHAR2
) RETURN NUMBER DETERMINISTIC
AS
BEGIN
  IF splitList( list1 ) MULTISET INTERSECT splitList( list2 ) IS EMPTY THEN
    RETURN 0;
  ELSE
    RETURN 1;
  END IF;
END;

/


--------------------------------------------------------
--  DDL for Type LISTA2
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TYPE "LISTA2" 
IS
  TABLE OF VARCHAR2(250);


/
--------------------------------------------------------
--  DDL for Type MYTYPE
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TYPE "MYTYPE" AS VARRAY(20) OF VARCHAR2(50);

/
--------------------------------------------------------
--  DDL for Type NESTED_TYP
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TYPE "NESTED_TYP" IS TABLE OF NUMBER;


/
--------------------------------------------------------
--  DDL for Type NUM
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TYPE "NUM" IS VARRAY(6) OF INTEGER; 

/
--------------------------------------------------------
--  DDL for Type SYSTPG/5JLJXNQuSxKdiGedikdg==
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TYPE "SYSTPG/5JLJXNQuSxKdiGedikdg==" AS TABLE OF "SYS"."SQL_BIND"

/

  GRANT EXECUTE ON "SYSTPG/5JLJXNQuSxKdiGedikdg==" TO PUBLIC;
--------------------------------------------------------
--  DDL for Type TABELA
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TYPE "TABELA" 
IS
  TABLE OF LISTA2;


/
--------------------------------------------------------
--  DDL for Type T_ARRAY
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TYPE "T_ARRAY" IS TABLE OF VARCHAR2(3900);

/
--------------------------------------------------------
--  DDL for Type T_TEAMS
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TYPE "T_TEAMS" is table of varchar2(15);


/

--------------------------------------------------------
--  DDL for Sequence GERA_ID_BCK_MARC
--------------------------------------------------------

   CREATE SEQUENCE  "GERA_ID_BCK_MARC"  MINVALUE 1 MAXVALUE 9999999999 INCREMENT BY 1 START WITH 741 CACHE 20 NOORDER  NOCYCLE   ;
--------------------------------------------------------
--  DDL for Sequence GERA_NMEC_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "GERA_NMEC_SEQ"  MINVALUE 1 MAXVALUE 9999999999 INCREMENT BY 1 START WITH 96 CACHE 20 NOORDER  NOCYCLE   ;
--------------------------------------------------------
--  DDL for Sequence GERA_NUM_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "GERA_NUM_SEQ"  MINVALUE 1 MAXVALUE 9999999999 INCREMENT BY 1 START WITH 52 CACHE 20 NOORDER  NOCYCLE   ;
--------------------------------------------------------
--  DDL for Sequence GERA_NUM_USER
--------------------------------------------------------

   CREATE SEQUENCE  "GERA_NUM_USER"  MINVALUE 0 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 61 CACHE 20 NOORDER  NOCYCLE   ;
--------------------------------------------------------
--  DDL for Sequence SIAR_S_FERIADO
--------------------------------------------------------

   CREATE SEQUENCE  "SIAR_S_FERIADO"  MINVALUE 1 MAXVALUE 999999 INCREMENT BY 1 START WITH 31 CACHE 10 NOORDER  NOCYCLE   ;
--------------------------------------------------------
--  DDL for Sequence SIAR_S_PARAMETRO
--------------------------------------------------------

   CREATE SEQUENCE  "SIAR_S_PARAMETRO"  MINVALUE 1 MAXVALUE 9999999999 INCREMENT BY 1 START WITH 142 CACHE 20 NOORDER  NOCYCLE   ;
--------------------------------------------------------
--  DDL for Sequence SIAR_S_PLAFOND
--------------------------------------------------------

   CREATE SEQUENCE  "SIAR_S_PLAFOND"  MINVALUE 1 MAXVALUE 999999 INCREMENT BY 1 START WITH 1 CACHE 10 NOORDER  NOCYCLE   ;
--------------------------------------------------------
--  DDL for Sequence SIAR_S_PRATO
--------------------------------------------------------

   CREATE SEQUENCE  "SIAR_S_PRATO"  MINVALUE 1 MAXVALUE 999999 INCREMENT BY 1 START WITH 26 CACHE 10 NOORDER  NOCYCLE   ;
--------------------------------------------------------
--  DDL for Sequence SIAR_S_REFEICAO
--------------------------------------------------------

   CREATE SEQUENCE  "SIAR_S_REFEICAO"  MINVALUE 1 MAXVALUE 999999 INCREMENT BY 1 START WITH 31 CACHE 10 NOORDER  NOCYCLE   ;
--------------------------------------------------------
--  DDL for Sequence SUS_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "SUS_SEQ"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 47001 CACHE 20 NOORDER  NOCYCLE   ;
--------------------------------------------------------
--  DDL for Sequence TAB01_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "TAB01_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 100001 CACHE 20 NOORDER  NOCYCLE   ;

--------------------------------------------------------
--  DDL for Table BCK_SIAR_MARCACOES
--------------------------------------------------------

  CREATE TABLE "BCK_SIAR_MARCACOES" 
   (	"ID_MARCBCK" NUMBER, 
	"NUM_MECANOG" NUMBER, 
	"DTA_REFEICAO" DATE, 
	"COD_REFEICAO" NUMBER, 
	"COD_PRATO" NUMBER, 
	"DTA_DESATIVO" DATE, 
	"DTA_REGISTO" DATE, 
	"VERIFICACAO" CHAR(1 BYTE), 
	"EVENTO" VARCHAR2(10 BYTE), 
	"DATA" DATE, 
	"UTILIZADOR" VARCHAR2(20 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;

-------------------------------------------------------
--  DDL for Table CLUBES
--------------------------------------------------------

  CREATE TABLE "CLUBES" 
   (	"CLUB_ID" NUMBER(10,0), 
	"CLUB_NAME" VARCHAR2(50 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;

--------------------------------------------------------
--  DDL for Table CUSTOMERS
--------------------------------------------------------

  CREATE TABLE "CUSTOMERS" 
   (	"CUST_ID" NUMBER, 
	"CUST_LAST_NAME" VARCHAR2(40 BYTE), 
	"CUTS_CITY" VARCHAR2(30 BYTE), 
	"CUTS_CREDIT_LIMIT" NUMBER, 
	"CUST_CATEGORY" VARCHAR2(20 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;

--------------------------------------------------------
--  DDL for Table DEBUG_OUTPUT
--------------------------------------------------------

  CREATE TABLE "DEBUG_OUTPUT" 
   (	"MSG" VARCHAR2(100 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;


--------------------------------------------------------
--  DDL for Table EMP
--------------------------------------------------------

  CREATE TABLE "EMP" 
   (	"EMP_ID" NUMBER(2,0), 
	"EMP_NAME" VARCHAR2(20 BYTE), 
	"EMP_SALARY" NUMBER(5,0)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
  

--------------------------------------------------------
--  DDL for Table EMPLOYEES
--------------------------------------------------------

  CREATE TABLE "EMPLOYEES" 
   (	"EMPLOYEE_ID" NUMBER(6,0), 
	"FIRST_NAME" VARCHAR2(20 BYTE), 
	"LAST_NAME" VARCHAR2(25 BYTE), 
	"HIRE_DATE" DATE, 
	"JOB_ID" VARCHAR2(10 BYTE), 
	"SALARY" NUMBER(8,2), 
	"COMMISSION_PCT" NUMBER(2,2), 
	"MANAGER_ID" NUMBER(6,0), 
	"DEPARTAMENT_ID" NUMBER(4,0)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;

--------------------------------------------------------
--  DDL for Table EMP_TEMP
--------------------------------------------------------

  CREATE TABLE "EMP_TEMP" 
   (	"DEPTNO" NUMBER(2,0), 
	"JOB" VARCHAR2(18 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;

--------------------------------------------------------
--  DDL for Table FERIADOS_PORTUGAL
--------------------------------------------------------

  CREATE TABLE "FERIADOS_PORTUGAL" 
   (	"DTA_FERIADO" DATE, 
	"DESCRIPTION" VARCHAR2(200 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;

--------------------------------------------------------
--  DDL for Table MATCHES
--------------------------------------------------------

  CREATE TABLE "MATCHES" 
   (	"JORNADA" NUMBER(2,0), 
	"EQUIPACASA" VARCHAR2(30 BYTE), 
	"EQUIPAFORA" VARCHAR2(30 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table PRODUCTS
--------------------------------------------------------

  CREATE TABLE "PRODUCTS" 
   (	"PROD_ID" NUMBER(4,0), 
	"PROD_NAME" VARCHAR2(10 BYTE), 
	"PROD_LIST_PRICE" NUMBER(8,2), 
	"PROD_VALID" VARCHAR2(1 BYTE)
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE( INITIAL 65536 NEXT 1048576 MINEXTENTS 1
  FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;

--------------------------------------------------------
--  DDL for Table SIAR_ANULACOES
--------------------------------------------------------

  CREATE TABLE "SIAR_ANULACOES" 
   (	"NUM_MEC" NUMBER, 
	"COD_REFEICAO" NUMBER, 
	"COD_PRATO" NUMBER, 
	"DTA_REFEICAO" DATE, 
	"DTA_DESATIVO" DATE, 
	"DTA_REGISTO_INICIAL" DATE, 
	"DTA_REGISTO_FINAL" DATE
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table SIAR_DOMINIOS
--------------------------------------------------------

  CREATE TABLE "SIAR_DOMINIOS" 
   (	"VALOR" NUMBER(10,2), 
	"DOMINIO" VARCHAR2(5 BYTE), 
	"DESC_DOMINIO" VARCHAR2(100 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table SIAR_EMENTAS
--------------------------------------------------------

  CREATE TABLE "SIAR_EMENTAS" 
   (	"DTA_REFEICAO" DATE, 
	"COD_REFEICAO" NUMBER, 
	"COD_PRATO" NUMBER, 
	"DESC_EMENTA" VARCHAR2(250 BYTE), 
	"IMAGEM_NOME" VARCHAR2(100 BYTE), 
	"IMAGEM" LONG RAW
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table SIAR_FERIADO
--------------------------------------------------------

  CREATE TABLE "SIAR_FERIADO" 
   (	"COD_FERIADO" NUMBER, 
	"DESC_FERIADO" VARCHAR2(250 BYTE), 
	"DTA_FERIADO" VARCHAR2(10 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;

--------------------------------------------------------
--  DDL for Table SIAR_MARCACOES
--------------------------------------------------------

  CREATE TABLE "SIAR_MARCACOES" 
   (	"NUM_MECANOG" NUMBER, 
	"DTA_REFEICAO" DATE, 
	"COD_REFEICAO" NUMBER, 
	"COD_PRATO" NUMBER, 
	"DTA_DESATIVO" DATE, 
	"DTA_REGISTO" DATE, 
	"VERIFICACAO" CHAR(1 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table SIAR_PARAMETROS
--------------------------------------------------------

  CREATE TABLE "SIAR_PARAMETROS" 
   (	"COD_PARAMETRO" NUMBER, 
	"VALOR_PARAMETRO" VARCHAR2(5 BYTE), 
	"DESCRICAO_PARAMETRO" VARCHAR2(250 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table SIAR_PLAFOND
--------------------------------------------------------

  CREATE TABLE "SIAR_PLAFOND" 
   (	"COD_PLAFOND" NUMBER, 
	"NUM_MECANOG" NUMBER, 
	"VALOR_CARREGADO" NUMBER(38,2), 
	"DTA_CARREGAMENTO" DATE
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE( INITIAL 65536 NEXT 1048576 MINEXTENTS 1
  FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table SIAR_PRATO
--------------------------------------------------------

  CREATE TABLE "SIAR_PRATO" 
   (	"COD_PRATO" NUMBER, 
	"DESC_PRATO" VARCHAR2(100 BYTE), 
	"DTA_REGISTO" DATE
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table SIAR_REFEICAO
--------------------------------------------------------

  CREATE TABLE "SIAR_REFEICAO" 
   (	"COD_REFEICAO" NUMBER, 
	"DESC_REFEICAO" VARCHAR2(100 BYTE), 
	"DTA_REGISTO" DATE
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table SIAR_UTILIZADORES
--------------------------------------------------------

  CREATE TABLE "SIAR_UTILIZADORES" 
   (	"NUM_MECANOG" NUMBER, 
	"NOME_UTILIZADOR" VARCHAR2(100 BYTE), 
	"SENHA" VARCHAR2(38 BYTE), 
	"DTA_DESATIVO" DATE, 
	"PLAFOND" NUMBER, 
	"IMAGEM_NOME" VARCHAR2(100 BYTE), 
	"IMAGEM" BLOB, 
	"EMAIL" VARCHAR2(250 BYTE), 
	"ACTIVO" VARCHAR2(1 CHAR) DEFAULT 'S', 
	"CREATION_DATE" DATE DEFAULT sysdate
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" 
 LOB ("IMAGEM") STORE AS SECUREFILE (
  TABLESPACE "USERS" ENABLE STORAGE IN ROW CHUNK 8192
  NOCACHE LOGGING  NOCOMPRESS  KEEP_DUPLICATES 
  STORAGE(INITIAL 106496 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)) ;
--------------------------------------------------------
--  DDL for Table STRING_TAB
--------------------------------------------------------

  CREATE TABLE "STRING_TAB" 
   (	"STRING1" VARCHAR2(100 BYTE)
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table SUPERHEROIS
--------------------------------------------------------

  CREATE TABLE "SUPERHEROIS" 
   (	"FIRST_NAME" VARCHAR2(15 BYTE), 
	"LAST_NAME" VARCHAR2(15 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table SYS_USERS
--------------------------------------------------------

  CREATE TABLE "SYS_USERS" 
   (	"ID" NUMBER, 
	"NUM_MECANOG" NUMBER(6,0), 
	"NOME" VARCHAR2(50 CHAR), 
	"SENHA" VARCHAR2(30 CHAR), 
	"ACTIVO" VARCHAR2(1 CHAR) DEFAULT 'S', 
	"CREATED_BY" VARCHAR2(30 CHAR), 
	"CREATION_DATE" DATE, 
	"LAST_UPDATED_BY" VARCHAR2(30 CHAR), 
	"LAST_UPDATE_DATE" DATE, 
	"PRO_PROFISSID_PESSOAL_ID" NUMBER, 
	"ALTERAR_PASS" VARCHAR2(1 CHAR) DEFAULT 'S', 
	"SPMS_SN" VARCHAR2(1 BYTE) DEFAULT 'N', 
	"VALIDADE_PASS" DATE
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE( INITIAL 65536 NEXT 1048576 MINEXTENTS 1
  FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
-------------------------------
--  DDL for Table VALIDA_TEMP
--------------------------------------------------------

  CREATE TABLE "VALIDA_TEMP" 
   (	"NOME1" VARCHAR2(100 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table VALIDA_TEMP2
--------------------------------------------------------

  CREATE TABLE "VALIDA_TEMP2" 
   (	"NOME2" VARCHAR2(100 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for View VW_SUPERHEROIS
--------------------------------------------------------

  CREATE OR REPLACE FORCE EDITIONABLE VIEW "VW_SUPERHEROIS" ("FIRST_NAME", "LAST_NAME") AS 
  SELECT first_name,last_name FROM Superherois
;

--------------------------------------------------------
--  DDL for Index EMP_COL1_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "EMP_COL1_PK" ON "EMP" ("EMP_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index PK_EMENTA
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_EMENTA" ON "SIAR_EMENTAS" ("DTA_REFEICAO", "COD_REFEICAO", "COD_PRATO") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index PK_MARCACAO
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_MARCACAO" ON "SIAR_MARCACOES" ("NUM_MECANOG", "DTA_REFEICAO", "COD_REFEICAO") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index PK_PARAMETRO
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_PARAMETRO" ON "SIAR_PARAMETROS" ("COD_PARAMETRO") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index PK_PRATO
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_PRATO" ON "SIAR_PRATO" ("COD_PRATO") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index PK_REFEICAO
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_REFEICAO" ON "SIAR_REFEICAO" ("COD_REFEICAO") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index PK_UTILIZADORES
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_UTILIZADORES" ON "SIAR_UTILIZADORES" ("NUM_MECANOG") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;

--------------------------------------------------------
--  DDL for Index SYS_C0012566
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0012566" ON "BCK_SIAR_MARCACOES" ("ID_MARCBCK") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;


--------------------------------------------------------
--  DDL for Index UTI_NOME
--------------------------------------------------------

  CREATE INDEX "UTI_NOME" ON "SIAR_UTILIZADORES" ("NOME_UTILIZADOR") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Trigger BACKUP_MARCACOES_DEL
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TRIGGER "BACKUP_MARCACOES_DEL" 
BEFORE UPDATE OF DTA_DESATIVO ON SIAR_MARCACOES  
FOR EACH ROW
DECLARE
   v_username varchar2(10);
BEGIN
   -- Find username of person performing the INSERT into the table
   SELECT user INTO v_username
   FROM dual;
   IF :old.DTA_DESATIVO IS NULL THEN
       -- Insert record into audit table
       INSERT INTO BCK_SIAR_MARCACOES 
       ( 
         ID_MARCBCK ,
         NUM_MECANOG, 
         DTA_REFEICAO, 
         COD_REFEICAO, 
         COD_PRATO, 
         DTA_DESATIVO, 
         DTA_REGISTO, 
         VERIFICACAO,
         EVENTO,
         DATA,
         UTILIZADOR)
       VALUES
       ( 
        GERA_ID_BCK_MARC.NEXTVAL,
        :old.NUM_MECANOG,
        :old.DTA_REFEICAO,
        :old.COD_REFEICAO,
        :old.COD_PRATO,
        sysdate,
        :old.DTA_REGISTO,
        :old.VERIFICACAO,
        'Desativada',
        sysdate,
        v_username
        );
    ELSE
       INSERT INTO BCK_SIAR_MARCACOES 
       ( 
         ID_MARCBCK ,
         NUM_MECANOG, 
         DTA_REFEICAO, 
         COD_REFEICAO, 
         COD_PRATO, 
         DTA_DESATIVO, 
         DTA_REGISTO, 
         VERIFICACAO,
         EVENTO,
         DATA,
         UTILIZADOR)
       VALUES
       ( 
        GERA_ID_BCK_MARC.NEXTVAL,
        :old.NUM_MECANOG,
        :old.DTA_REFEICAO,
        :old.COD_REFEICAO,
        :old.COD_PRATO,
        null,
        :old.DTA_REGISTO,
        :old.VERIFICACAO,
        'Remarcada',
        sysdate,
        v_username
        );
    END IF;
END;
/
ALTER TRIGGER "BACKUP_MARCACOES_DEL" ENABLE;
--------------------------------------------------------
--  DDL for Trigger BACKUP_MARCACOES_INS
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TRIGGER "BACKUP_MARCACOES_INS" 
AFTER INSERT ON SIAR_MARCACOES
FOR EACH ROW
DECLARE
   v_username varchar2(10);
BEGIN
   -- Find username of person performing the INSERT into the table
   SELECT user INTO v_username
   FROM dual;
       -- Insert record into audit table
       INSERT INTO BCK_SIAR_MARCACOES 
       ( 
         ID_MARCBCK ,
         NUM_MECANOG, 
         DTA_REFEICAO, 
         COD_REFEICAO, 
         COD_PRATO, 
         DTA_DESATIVO, 
         DTA_REGISTO, 
         VERIFICACAO,
         EVENTO,
         DATA,
         UTILIZADOR)
       VALUES
       ( 
        GERA_ID_BCK_MARC.NEXTVAL,
        :new.NUM_MECANOG,
        :new.DTA_REFEICAO,
        :new.COD_REFEICAO,
        :new.COD_PRATO,
        :new.DTA_DESATIVO,
        :new.DTA_REGISTO,
        :new.VERIFICACAO,
        'Inserido',
        sysdate,
        v_username
        );
END;

/
ALTER TRIGGER "BACKUP_MARCACOES_INS" ENABLE;
--------------------------------------------------------
--  DDL for Procedure CHECK_PRICE
--------------------------------------------------------
set define off;

CREATE OR REPLACE EDITIONABLE PROCEDURE "CHECK_PRICE" (p_prod_id number)is
v_price products.prod_list_price%type;
BEGIN
   select  prod_list_price into v_price
   from products
   where prod_id = p_prod_id;
   if v_price not between 20.0 and 30.0 then
      RAISE_APPLICATION_ERROR(-20100, 'Price not in range');    
   end if;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
         dbms_output.put_line('ERRO: ' || DBMS_UTILITY.FORMAT_ERROR_STACK || DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);   
END;

/
--------------------------------------------------------
--  DDL for Trigger CHECK_PRICE_TRG
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TRIGGER "CHECK_PRICE_TRG" 
 before insert or update of prod_id, prod_list_price
  on products FOr EACH ROW
        WHEN (new.prod_id <> nvl(old.prod_id,0) or new.prod_list_price <> NVL(old.prod_list_price,0)) BEGIN
 check_price(:new.prod_id);
end;

/
ALTER TRIGGER "CHECK_PRICE_TRG" ENABLE;
--------------------------------------------------------
--  DDL for Trigger CUSTOMERS_CREDIT_POLICY_TRG
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TRIGGER "CUSTOMERS_CREDIT_POLICY_TRG" 
    FOR UPDATE OR INSERT ON customers    
    COMPOUND TRIGGER     
    TYPE r_customers_type IS RECORD (    
        cust_id   customers.cust_id%TYPE, 
        cuts_credit_limit  customers.cuts_credit_limit%TYPE    
    );    

    TYPE t_customers_type IS TABLE OF r_customers_type  
        INDEX BY PLS_INTEGER;    

    t_customer   t_customers_type;    

    AFTER EACH ROW IS    
    BEGIN  
        t_customer (t_customer.COUNT + 1).cust_id :=    
            :NEW.cust_id;    
        t_customer (t_customer.COUNT).cuts_credit_limit := :NEW.cuts_credit_limit;
    END AFTER EACH ROW;    

    AFTER STATEMENT IS    
        l_max_credit   customers.cuts_credit_limit%TYPE;    
    BEGIN      
        SELECT MIN (cuts_credit_limit) * 5    
            INTO l_max_credit    
            FROM customers
            WHERE cuts_credit_limit > 0;

        FOR indx IN 1 .. t_customer.COUNT    
        LOOP                                      
            IF l_max_credit < t_customer (indx).cuts_credit_limit    
            THEN    
                UPDATE customers    
                SET cuts_credit_limit = l_max_credit    
                WHERE cust_id = t_customer (indx).cust_id;    
            END IF;    
        END LOOP;    
    END AFTER STATEMENT;    
END; 
/
ALTER TRIGGER "CUSTOMERS_CREDIT_POLICY_TRG" ENABLE;
--------------------------------------------------------
--  DDL for Procedure ATI_DES_UTI
--------------------------------------------------------
set define off;

  CREATE OR REPLACE EDITIONABLE PROCEDURE "ATI_DES_UTI" 
IS
x_weekend_date EXCEPTION;
BEGIN
    IF to_char(sysdate,'D') in (1,2,3,4,5,6) AND TO_CHAR(SYSDATE, 'HH24:MI') BETWEEN '09:00' AND '19:30' THEN  
       RAISE x_weekend_date;
    END IF; 
EXCEPTION
WHEN x_weekend_date THEN
 DBMS_OUTPUT.PUT_LINE('Can not activate/deactivate users during office hours?');
END;

/
--------------------------------------------------------
--  DDL for Trigger REG_ATUA_UTILIZADORES
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TRIGGER "REG_ATUA_UTILIZADORES" 
BEFORE UPDATE OF dta_desativo
   ON SIAR_UTILIZADORES
FOR EACH ROW
BEGIN
     ATI_DES_UTI;
END Reg_Atua_Utilizadores;
/
ALTER TRIGGER "REG_ATUA_UTILIZADORES" ENABLE;


--------------------------------------------------------
--  DDL for Procedure DEBUGGING
--------------------------------------------------------
set define off;

  CREATE OR REPLACE EDITIONABLE PROCEDURE "DEBUGGING" (msg varchar2)AS
PRAGMA AUTONOMOUS_TRANSACTION;
BEGIN
  insert into debug_output values(msg);
  Commit;
END debugging;

/
--------------------------------------------------------
--  DDL for Procedure DELETE_DETAILS
--------------------------------------------------------
set define off;

  CREATE OR REPLACE EDITIONABLE PROCEDURE "DELETE_DETAILS" (p_id number)as
msg varchar2(100);
name_prod varchar(100);
BEGIN
  select PROD_NAME
  into name_prod
  FROM products where prod_id = p_id;
  DELETE FROM products WHERE prod_id = p_id;
  dbms_output.put_line('O Produto '||name_prod||'-'||'ID: '|| p_id||' foi removido com sucesso!');
  COMMIT;
EXCEPTION
 when others then
   msg := substr(sqlerrm,100);
   debugging (msg);
END delete_details;

/
--------------------------------------------------------
--  DDL for Procedure GET_USERS
--------------------------------------------------------
set define off;

  CREATE OR REPLACE EDITIONABLE PROCEDURE "GET_USERS" (id_ number, nome_ CHARACTER, nome_completo out  CHARACTER)
AS
    TYPE users_nome_t IS TABLE OF siar_utilizadores.nome_utilizador%TYPE
    INDEX BY PLS_INTEGER; 
    l_Users_nome   users_nome_t;
BEGIN
select nome_utilizador  BULK COLLECT INTO l_Users_nome from siar_utilizadores where num_mecanog = id_ and
upper(nome_utilizador) like '%' || upper(nome_) || '%';
 FOR indx IN 1 .. l_Users_nome.COUNT
     LOOP
         --DBMS_OUTPUT.PUT_LINE('Números Mecanográfic:' || l_Users_nome(indx));
         nome_completo := l_Users_nome(indx);
     END LOOP;
END;

/
--------------------------------------------------------
--  DDL for Procedure KILL_SESSION
--------------------------------------------------------
set define off;

  CREATE OR REPLACE EDITIONABLE PROCEDURE "KILL_SESSION" ( session_id in varchar2,
serial_num in varchar2)
AS
cur INTEGER;
ret INTEGER;
string VARCHAR2(100);
BEGIN
 string :=
        'ALTER SYSTEM KILL SESSION' || CHR(10) ||
CHR(39)||session_id||','||serial_num||CHR(39);
-- string :=
--          'ALTER SYSTEM DISCONNECT SESSION' || CHR(10) ||
-- CHR(39)||session_id||','||serial_num||CHR(39)||CHR(10)||
--' POST_TRANSACTION';
   cur := dbms_sql.open_cursor;
   dbms_sql.parse(cur,string,dbms_sql.v7);
   ret := dbms_sql.execute(cur)  ;
   dbms_sql.close_cursor(cur);
EXCEPTION
   WHEN OTHERS THEN
      raise_application_error(-20001,'Error in execution',TRUE);
      IF dbms_sql.is_open(cur) THEN
        dbms_sql.close_cursor(cur);
      END IF;
END;

/
--------------------------------------------------------
--  DDL for Procedure MY_PROC1
--------------------------------------------------------
set define off;

  CREATE OR REPLACE EDITIONABLE PROCEDURE "MY_PROC1" IS
BEGIN
  $IF my_debug.debug $THEN 
    DBMS_OUTPUT.put_line('Debugging ON'); 
  $ELSE 
   DBMS_OUTPUT.put_line('Debugging OFF'); 
  $END
END my_proc1;

/
--------------------------------------------------------
--  DDL for Procedure MY_PROC2
--------------------------------------------------------
set define off;

  CREATE OR REPLACE EDITIONABLE PROCEDURE "MY_PROC2" IS
BEGIN
  $IF my_debug.trace $THEN 
   DBMS_OUTPUT.put_line('Tracing ON'); 
  $ELSE
    DBMS_OUTPUT.put_line('Tracing OFF'); 
  $END
END my_proc2;

/
--------------------------------------------------------
--  DDL for Procedure P_GEN_EXCEL_REPORT_DIR
--------------------------------------------------------
set define off;

  CREATE OR REPLACE EDITIONABLE PROCEDURE "P_GEN_EXCEL_REPORT_DIR" (
   P_TNAME in VARCHAR2,
   P_CNAMES in VARCHAR2,
   P_WHERE in VARCHAR2,
   P_DIR in VARCHAR2,
   P_FILENAME in VARCHAR2)
IS
L_OUTPUT UTL_FILE.FILE_TYPE;
L_THECURSOR INTEGER DEFAULT DBMS_SQL.OPEN_CURSOR;
L_COLUMNVALUE VARCHAR(4000);
L_STATUS INTEGER;
L_QUERY VARCHAR2(32000) DEFAULT 'select * from '|| P_TNAME;
L_COLCNT NUMBER := 0;
L_SEPARATOR VARCHAR(1);
L_DESCTBL DBMS_SQL.DESC_TAB;
L_WHERE VARCHAR2(4000) DEFAULT '1=1';
L_CNAMES VARCHAR2(10000);
L_TNAMES VARCHAR2(32000);
L_QUERY1 VARCHAR2(32000);
L_CNT NUMBER;
extensao varchar(5);
BEGIN
     IF P_WHERE IS NOT NULL THEN
      L_WHERE := P_WHERE;
     END IF;
     IF P_CNAMES IS NOT NULL THEN
      L_CNAMES := P_CNAMES;
      L_QUERY :='SELECT'||' '||L_CNAMES||' '||'FROM'||' '||P_TNAME||' '||'WHERE'||' '||L_WHERE;  
     END IF;
     DBMS_OUTPUT.PUT_LINE(L_QUERY);
     BEGIN
       L_QUERY1 := 'select count(*) from '||P_TNAME||' '||' WHERE '||' '||L_WHERE;
       EXECUTE IMMEDIATE L_QUERY1 INTO L_CNT ;
       AS_XLSX.QUERY2SHEET(L_QUERY);
       IF L_CNT <= 60000 THEN
           extensao := get_file_suffix(P_FILENAME);
           if extensao = 'xls' THEN
               AS_XLSX.SAVE( P_DIR, REPLACE(UPPER(P_FILENAME),'XLS','xls'));
               DBMS_OUTPUT.PUT_LINE('xls file generation');
           elsif extensao = 'xlsx' THEN
               AS_XLSX.SAVE( P_DIR, UPPER(P_FILENAME));
               DBMS_OUTPUT.PUT_LINE('xlsx file generation');
           end if;  
       ELSE
           AS_XLSX.SAVE( P_DIR, REPLACE(UPPER(P_FILENAME),'XLS','xlsx'));
           DBMS_OUTPUT.PUT_LINE('xlsx file generation');
       END IF;
EXCEPTION 
  WHEN UTL_FILE.INVALID_PATH THEN
    RAISE_APPLICATION_ERROR(-20000, 'File location is invalid.');
  WHEN UTL_FILE.INVALID_MODE THEN
    RAISE_APPLICATION_ERROR(-20001, 'The open_mode parameter in FOPEN is invalid.');
  WHEN UTL_FILE.INVALID_FILEHANDLE THEN
    RAISE_APPLICATION_ERROR(-20002, 'File handle is invalid.');
  WHEN UTL_FILE.INVALID_OPERATION THEN
    RAISE_APPLICATION_ERROR(-20003, 'File could not be opened or operated on as requested.');
  WHEN UTL_FILE.READ_ERROR THEN
    RAISE_APPLICATION_ERROR(-20004, 'Operating system error occurred during the read operation.');
  WHEN UTL_FILE.WRITE_ERROR THEN
    RAISE_APPLICATION_ERROR(-20005, 'Operating system error occurred during the write operation.');
  WHEN UTL_FILE.INTERNAL_ERROR THEN
    RAISE_APPLICATION_ERROR(-20006, 'Unspecified PL/SQL error.');
  WHEN UTL_FILE.CHARSETMISMATCH THEN
    RAISE_APPLICATION_ERROR(-20007, 'A file is opened using FOPEN_NCHAR, but later I/O ' ||
                                    'operations use nonchar functions such as PUTF or GET_LINE.');
  WHEN UTL_FILE.FILE_OPEN THEN
    RAISE_APPLICATION_ERROR(-20008, 'The requested operation failed because the file is open.');
  WHEN UTL_FILE.INVALID_MAXLINESIZE THEN
    RAISE_APPLICATION_ERROR(-20009, 'The MAX_LINESIZE value for FOPEN() is invalid; it should ' || 
                                    'be within the range 1 to 32767.');
  WHEN UTL_FILE.INVALID_FILENAME THEN
    RAISE_APPLICATION_ERROR(-20010, 'The filename parameter is invalid.');
  WHEN UTL_FILE.ACCESS_DENIED THEN
    RAISE_APPLICATION_ERROR(-20011, 'Permission to access to the file location is denied.');
  WHEN UTL_FILE.INVALID_OFFSET THEN
    RAISE_APPLICATION_ERROR(-20012, 'The ABSOLUTE_OFFSET parameter for FSEEK() is invalid; ' ||
                                    'it should be greater than 0 and less than the total ' ||
                                    'number of bytes in the file.');
  WHEN UTL_FILE.DELETE_FAILED THEN
    RAISE_APPLICATION_ERROR(-20013, 'The requested file delete operation failed.');
  WHEN UTL_FILE.RENAME_FAILED THEN
    RAISE_APPLICATION_ERROR(-20014, 'The requested file rename operation failed.');
  WHEN OTHERS THEN
    RAISE;
   END;
EXCEPTION 
  WHEN UTL_FILE.INVALID_PATH THEN
    RAISE_APPLICATION_ERROR(-20000, 'File location is invalid.');
  WHEN UTL_FILE.INVALID_MODE THEN
    RAISE_APPLICATION_ERROR(-20001, 'The open_mode parameter in FOPEN is invalid.');
  WHEN UTL_FILE.INVALID_FILEHANDLE THEN
    RAISE_APPLICATION_ERROR(-20002, 'File handle is invalid.');
  WHEN UTL_FILE.INVALID_OPERATION THEN
    RAISE_APPLICATION_ERROR(-20003, 'File could not be opened or operated on as requested.');
  WHEN UTL_FILE.READ_ERROR THEN
    RAISE_APPLICATION_ERROR(-20004, 'Operating system error occurred during the read operation.');
  WHEN UTL_FILE.WRITE_ERROR THEN
   RAISE_APPLICATION_ERROR(-20005, 'Operating system error occurred during the write operation.');
  WHEN UTL_FILE.INTERNAL_ERROR THEN
    RAISE_APPLICATION_ERROR(-20006, 'Unspecified PL/SQL error.');
  WHEN UTL_FILE.CHARSETMISMATCH THEN
    RAISE_APPLICATION_ERROR(-20007, 'A file is opened using FOPEN_NCHAR, but later I/O ' ||
                                    'operations use nonchar functions such as PUTF or GET_LINE.');
  WHEN UTL_FILE.FILE_OPEN THEN
    RAISE_APPLICATION_ERROR(-20008, 'The requested operation failed because the file is open.');
  WHEN UTL_FILE.INVALID_MAXLINESIZE THEN
    RAISE_APPLICATION_ERROR(-20009, 'The MAX_LINESIZE value for FOPEN() is invalid; it should ' || 
                                   'be within the range 1 to 32767.');
  WHEN UTL_FILE.INVALID_FILENAME THEN
    RAISE_APPLICATION_ERROR(-20010, 'The filename parameter is invalid.');
  WHEN UTL_FILE.ACCESS_DENIED THEN
    RAISE_APPLICATION_ERROR(-20011, 'Permission to access to the file location is denied.');
  WHEN UTL_FILE.INVALID_OFFSET THEN
    RAISE_APPLICATION_ERROR(-20012, 'The ABSOLUTE_OFFSET parameter for FSEEK() is invalid; ' ||
                                   'it should be greater than 0 and less than the total ' ||
                                    'number of bytes in the file.');
  WHEN UTL_FILE.DELETE_FAILED THEN
    RAISE_APPLICATION_ERROR(-20013, 'The requested file delete operation failed.');
  WHEN UTL_FILE.RENAME_FAILED THEN
    RAISE_APPLICATION_ERROR(-20014, 'The requested file rename operation failed.');
  WHEN OTHERS THEN
    RAISE;
END P_GEN_EXCEL_REPORT_DIR;

/
--------------------------------------------------------
--  DDL for Procedure PRINT_NESTED_TABLE
--------------------------------------------------------
set define off;

  CREATE OR REPLACE EDITIONABLE PROCEDURE "PRINT_NESTED_TABLE" (the_nt nested_typ) AS 
output VARCHAR2(128);
BEGIN
   IF the_nt IS NULL THEN
        DBMS_OUTPUT.PUT_LINE('Results: <NULL>');
        RETURN;
     END IF;
     IF the_nt.COUNT = 0 THEN
        DBMS_OUTPUT.PUT_LINE('Results: empty set');
        RETURN;
     END IF;
     FOR i IN the_nt.FIRST .. the_nt.LAST
     LOOP
        output := output || the_nt(i) || ' ';
     END LOOP;
     DBMS_OUTPUT.PUT_LINE('Results: ' || output);
END PRINT_NESTED_TABLE;



/
--------------------------------------------------------
--  DDL for Procedure PROC1
--------------------------------------------------------
set define off;

  CREATE OR REPLACE EDITIONABLE PROCEDURE "PROC1" (n1 in out number, n2 in out number) is
Begin
--:n1 := 20;
n1 := 20;
dbms_output.put_line(n1);
--:n2 := 30;
n2 := 30;
dbms_output.put_line(n2);
end; --  variaveis de liga? n?podem ser defenidas dentro de um procedimento



/
--------------------------------------------------------
--  DDL for Procedure READ_FILE
--------------------------------------------------------
set define off;

  CREATE OR REPLACE EDITIONABLE PROCEDURE "READ_FILE" (dirname varchar2, txtfile varchar2)IS
f_file UTL_FILE.FILE_TYPE;
v_buffer varchar2(200);
BEGIN
f_file := UTL_FILE.FOPEN(dirname, txtfile, 'R');
LOOP
UTL_FILE.GET_LINE(f_file,v_buffer);
DBMS_output.put_line(v_buffer);
END LOOP;
UTL_FILE.FCLOSE(f_file);
EXCEPTION
WHEN NO_DATA_FOUND THEN
NULL;
END read_file;

/
--------------------------------------------------------
--  DDL for Procedure RESET_SEQ
--------------------------------------------------------
set define off;

  CREATE OR REPLACE EDITIONABLE PROCEDURE "RESET_SEQ" ( p_seq_name in varchar2 )
is
    l_val number;
begin
    execute immediate
    'select ' || p_seq_name || '.nextval from dual' INTO l_val;

    execute immediate
    'alter sequence ' || p_seq_name || ' increment by -' || l_val || 
                                                          ' minvalue 0';

    execute immediate
    'select ' || p_seq_name || '.nextval from dual' INTO l_val;

    execute immediate
    'alter sequence ' || p_seq_name || ' increment by 1 minvalue 0';
end;

/
--------------------------------------------------------
--  DDL for Procedure RUN_QUERY
--------------------------------------------------------
set define off;

  CREATE OR REPLACE EDITIONABLE PROCEDURE "RUN_QUERY" (p_sql IN VARCHAR2
                                     ,p_dir IN VARCHAR2
                                     ,p_header_file IN VARCHAR2
                                     ,p_data_file IN VARCHAR2 := NULL) IS
  v_finaltxt  VARCHAR2(4000);
  v_v_val     VARCHAR2(4000);
  v_n_val     NUMBER;
  v_d_val     DATE;
  v_ret       NUMBER;
  c           NUMBER;
  d           NUMBER;
  col_cnt     INTEGER;
  f           BOOLEAN;
  rec_tab     DBMS_SQL.DESC_TAB;
  col_num     NUMBER;
  v_fh        UTL_FILE.FILE_TYPE;
  v_samefile  BOOLEAN := (NVL(p_data_file,p_header_file) = p_header_file);
BEGIN
  c := DBMS_SQL.OPEN_CURSOR;
  DBMS_SQL.PARSE(c, p_sql, DBMS_SQL.NATIVE);
  d := DBMS_SQL.EXECUTE(c);
  DBMS_SQL.DESCRIBE_COLUMNS(c, col_cnt, rec_tab);
  FOR j in 1..col_cnt
  LOOP
    CASE rec_tab(j).col_type
      WHEN 1 THEN DBMS_SQL.DEFINE_COLUMN(c,j,v_v_val,2000);
      WHEN 2 THEN DBMS_SQL.DEFINE_COLUMN(c,j,v_n_val);
      WHEN 12 THEN DBMS_SQL.DEFINE_COLUMN(c,j,v_d_val);
    ELSE
      DBMS_SQL.DEFINE_COLUMN(c,j,v_v_val,2000);
    END CASE;
  END LOOP;
  -- This part outputs the HEADER
  v_fh := UTL_FILE.FOPEN(upper(p_dir),p_header_file,'w',32767);
  FOR j in 1..col_cnt
  LOOP
    v_finaltxt := ltrim(v_finaltxt||';'||lower(rec_tab(j).col_name),';');
  END LOOP;
  --  DBMS_OUTPUT.PUT_LINE(v_finaltxt);
  UTL_FILE.PUT_LINE(v_fh, v_finaltxt);
  IF NOT v_samefile THEN
    UTL_FILE.FCLOSE(v_fh);
  END IF;
  --
  -- This part outputs the DATA
  IF NOT v_samefile THEN
    v_fh := UTL_FILE.FOPEN(upper(p_dir),p_data_file,'w',32767);
  END IF;
  LOOP
    v_ret := DBMS_SQL.FETCH_ROWS(c);
    EXIT WHEN v_ret = 0;
    v_finaltxt := NULL;
    FOR j in 1..col_cnt
    LOOP
      CASE rec_tab(j).col_type
        WHEN 1 THEN DBMS_SQL.COLUMN_VALUE(c,j,v_v_val);
                    v_finaltxt := ltrim(v_finaltxt||';"'||v_v_val||'"',';');
        WHEN 2 THEN DBMS_SQL.COLUMN_VALUE(c,j,v_n_val);
                    v_finaltxt := ltrim(v_finaltxt||';'||v_n_val,';');
        WHEN 12 THEN DBMS_SQL.COLUMN_VALUE(c,j,v_d_val);
                    v_finaltxt := ltrim(v_finaltxt||';'||to_char(v_d_val,'DD/MM/YYYY HH24:MI:SS'),';');
      ELSE
        DBMS_SQL.COLUMN_VALUE(c,j,v_v_val);
        v_finaltxt := ltrim(v_finaltxt||';"'||v_v_val||'"',';');
      END CASE;
    END LOOP;
  --  DBMS_OUTPUT.PUT_LINE(v_finaltxt);
    UTL_FILE.PUT_LINE(v_fh, v_finaltxt);
  END LOOP;
  UTL_FILE.FCLOSE(v_fh);
  DBMS_SQL.CLOSE_CURSOR(c);
END;

/
--------------------------------------------------------
--  DDL for Procedure SEND_MAIL
--------------------------------------------------------
set define off;

  CREATE OR REPLACE EDITIONABLE PROCEDURE "SEND_MAIL" (p_to        IN VARCHAR2,
                                       p_from      IN VARCHAR2,
                                       p_subject   IN VARCHAR2,
                                       p_message   IN VARCHAR2,
                                       p_smtp_host IN VARCHAR2,
                                       p_smtp_port IN NUMBER DEFAULT 25)
AS
  l_mail_conn   UTL_SMTP.connection;
BEGIN
  l_mail_conn := UTL_SMTP.open_connection(p_smtp_host, p_smtp_port);
  UTL_SMTP.helo(l_mail_conn, p_smtp_host);
  UTL_SMTP.mail(l_mail_conn, p_from);
  UTL_SMTP.rcpt(l_mail_conn, p_to);

  UTL_SMTP.open_data(l_mail_conn);

  UTL_SMTP.write_data(l_mail_conn, 'Date: ' || TO_CHAR(SYSDATE, 'DD-MON-YYYY HH24:MI:SS') || UTL_TCP.crlf);
  UTL_SMTP.write_data(l_mail_conn, 'To: ' || p_to || UTL_TCP.crlf);
  UTL_SMTP.write_data(l_mail_conn, 'From: ' || p_from || UTL_TCP.crlf);
  UTL_SMTP.write_data(l_mail_conn, 'Subject: ' || p_subject || UTL_TCP.crlf);
  UTL_SMTP.write_data(l_mail_conn, 'Reply-To: ' || p_from || UTL_TCP.crlf || UTL_TCP.crlf);

  UTL_SMTP.write_data(l_mail_conn, p_message || UTL_TCP.crlf || UTL_TCP.crlf);
  UTL_SMTP.close_data(l_mail_conn);

  UTL_SMTP.quit(l_mail_conn);
END;

/
--------------------------------------------------------
--  DDL for Procedure TEST_OUT_PARAMS
--------------------------------------------------------
set define off;

  CREATE OR REPLACE EDITIONABLE PROCEDURE "TEST_OUT_PARAMS" (
        user_name in varchar,
        hello_msg out varchar,
        session_id out varchar) is
nome  varchar(10);
begin
select user into nome from dual;
        hello_msg := 'Hello, ' || nome;
        session_id := SYS_CONTEXT('USERENV', 'SESSIONID');
end;

/

--------------------------------------------------------
--  DDL for Procedure TEST_SCHEDULE
--------------------------------------------------------
set define off;

  CREATE OR REPLACE EDITIONABLE PROCEDURE "TEST_SCHEDULE" (p_teams in varchar2)
is
/*TYPE clubes_ids_t IS TABLE OF clubes.club_id%TYPE
             INDEX BY PLS_INTEGER; 
TYPE clubes_names_t IS TABLE OF clubes.club_name%TYPE
              INDEX BY VARCHAR2(50);
TYPE t_club_tab IS TABLE OF clubes.club_name%TYPE;
l_club_ids   clubes_ids_t;
l_clubes_names_t clubes_names_t;
l_clubes_it t_club_tab;
jogos number := 0;
n_enctr number := 0;
existe char(1) := 0;
CURSOR c_worker(equi1 NUMBER,equi2 number,id number)
  IS SELECT equipa1,equipa2
  FROM matches2; 
  --WHERE jornada = id and equipa1 not in (equi1,equi2) and equipa2 not in (equi1,equi2);
type array_t is varray(10) of int(38);
jornadas array_t;
TYPE t_worker IS TABLE OF NUMBER INDEX BY PLS_INTEGER;
t_equi_1 t_worker;
t_equi_2 t_worker;
*/
cursor c_teams is
select trim(regexp_substr(p_teams,'[^,]+', 1, level)) team
from dual
connect by regexp_substr(p_teams, '[^,]+', 1, level) is not null;
 /*cursor c_epoca is
select jornada,equipacasa,equipafora
from matches order by jornada asc;
cursor c_jornada is
select distinct(jornada)
from matches order by jornada asc;*/

v_jornada  matches.jornada%TYPE;
v_equipacasa  matches.equipacasa%TYPE;
v_equipafora  matches.equipafora%TYPE;

v_teams t_teams := t_teams();
v_team_shift varchar2(60);
v_weeks number;
v_count number:=0;
v_date date;
BEGIN
for r_teams in c_teams loop
        v_count := v_count +  1;
        v_teams.extend();
        v_teams(v_count) := r_teams.team;
        --dbms_output.put_line('v_teams(v_count): '|| v_teams(v_count));
        --dbms_output.put_line('r_teams.team: '|| r_teams.team);
end loop;
/*if mod(v_count, 2) != 0 then // para o caso em que o numero de equipas ?npar, por exemplo h? equipas
        v_count := v_count +  1;
        v_teams.extend();
        v_teams(v_count) := 'DUMMAY';
end if;*/
      /*for i in 1..v_count loop
        dbms_output.put_line(v_teams(i));
      end loop;*/
      v_weeks := v_count - 1; --Define o numero de jornadas consoante o numero de equipas
      dbms_output.put_line('Weeks: '|| v_weeks||' Count: '||v_count);
      for week in 1..v_weeks loop
        for i in 1..v_count/2 loop
        --dbms_output.put_line('v_teams(i): '|| v_teams(i));
        --dbms_output.put_line('v_teams(v_count-i+1): '|| v_teams(v_count-i));
          if week = 1 then
          --dbms_output.put_line('Jornada '||week||':    '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          insert into MATCHES values(1,v_teams(i),v_teams(v_count-i+1));
          --dbms_output.put_line(week+v_weeks||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
          end if;
         if week = 2 then
          --dbms_output.put_line('Jornada '||week||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
          insert into MATCHES values(2,v_teams(v_count-i+1),v_teams(i));
          --dbms_output.put_line('Jornada '||week||':    '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          --dbms_output.put_line(week+v_weeks||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
         end if;
         if week = 3 then
          --dbms_output.put_line('Jornada '||week||':    '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          insert into MATCHES values(3,v_teams(i),v_teams(v_count-i+1));
          --dbms_output.put_line(week+v_weeks||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
         end if;
         if week = 4 then
          --dbms_output.put_line('Jornada '||week||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
          insert into MATCHES values(4,v_teams(v_count-i+1),v_teams(i));
          --dbms_output.put_line('Jornada '||week||':    '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          --dbms_output.put_line(week+v_weeks||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
         end if;
         if week = 5 then
          --dbms_output.put_line('Jornada '||week||':    '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          insert into MATCHES values(5,v_teams(i),v_teams(v_count-i+1));
          --dbms_output.put_line(week+v_weeks||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
         end if;
         if week = 6 then
          --dbms_output.put_line('Jornada '||week||':    '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          --dbms_output.put_line('Jornada '||6||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
          insert into MATCHES values(6,v_teams(v_count-i+1),v_teams(i));
          --dbms_output.put_line('Jornada '||2||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
         end if;
         if week = 7 then
          --dbms_output.put_line('Jornada '||week||':    '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          --dbms_output.put_line('Jornada '||7||':     '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          insert into MATCHES values(7,v_teams(i),v_teams(v_count-i+1));
          --dbms_output.put_line('Jornada '||2||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
         end if;
          if week = 8 then
          --dbms_output.put_line('Jornada '||week||':    '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          --dbms_output.put_line('Jornada '||8||':     '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
          insert into MATCHES values(8,v_teams(v_count-i+1),v_teams(i));
          --dbms_output.put_line('Jornada '||2||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
         end if;
         if week = 9 then
          --dbms_output.put_line('Jornada '||week||':    '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          --dbms_output.put_line('Jornada '||9||':     '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          insert into MATCHES values(9,v_teams(i),v_teams(v_count-i+1));
          --dbms_output.put_line('Jornada '||2||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
         end if;
         if week = 10 then
          --dbms_output.put_line('Jornada '||week||':    '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          --dbms_output.put_line('Jornada '||10||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
          insert into MATCHES values(10,v_teams(v_count-i+1),v_teams(i));
          --dbms_output.put_line('Jornada '||2||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
         end if;
         if week = 11 then
          --dbms_output.put_line('Jornada '||week||':    '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          --dbms_output.put_line('Jornada '||9||':     '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          insert into MATCHES values(11,v_teams(i),v_teams(v_count-i+1));
          --dbms_output.put_line('Jornada '||2||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
         end if;
         if week = 12 then
          --dbms_output.put_line('Jornada '||week||':    '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          --dbms_output.put_line('Jornada '||10||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
          insert into MATCHES values(12,v_teams(v_count-i+1),v_teams(i));
          --dbms_output.put_line('Jornada '||2||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
         end if;
         if week = 13 then
          --dbms_output.put_line('Jornada '||week||':    '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          --dbms_output.put_line('Jornada '||9||':     '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          insert into MATCHES values(13,v_teams(i),v_teams(v_count-i+1));
          --dbms_output.put_line('Jornada '||2||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
         end if;
         if week = 14 then
          --dbms_output.put_line('Jornada '||week||':    '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          --dbms_output.put_line('Jornada '||10||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
          insert into MATCHES values(14,v_teams(v_count-i+1),v_teams(i));
          --dbms_output.put_line('Jornada '||2||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
         end if;
         if week = 15 then
          --dbms_output.put_line('Jornada '||week||':    '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          --dbms_output.put_line('Jornada '||9||':     '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          insert into MATCHES values(15,v_teams(i),v_teams(v_count-i+1));
          --dbms_output.put_line('Jornada '||2||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
         end if;
         if week = 16 then
          --dbms_output.put_line('Jornada '||week||':    '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          --dbms_output.put_line('Jornada '||10||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
          insert into MATCHES values(16,v_teams(v_count-i+1),v_teams(i));
          --dbms_output.put_line('Jornada '||2||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
         end if;
         if week = 17 then
          --dbms_output.put_line('Jornada '||week||':    '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          --dbms_output.put_line('Jornada '||9||':     '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          insert into MATCHES values(17,v_teams(i),v_teams(v_count-i+1));
          --dbms_output.put_line('Jornada '||2||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
         end if;
         if week+v_weeks= 18 then
          --dbms_output.put_line('Jornada '||week||':    '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          --dbms_output.put_line('Jornada '||10||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
          insert into MATCHES values(18,v_teams(v_count-i+1),v_teams(i));
          --dbms_output.put_line('Jornada '||2||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
         end if;
         if week+v_weeks= 19 then
          --dbms_output.put_line('Jornada '||week||':    '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          --dbms_output.put_line('Jornada '||9||':     '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          insert into MATCHES values(19,v_teams(i),v_teams(v_count-i+1));
          --dbms_output.put_line('Jornada '||2||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
         end if;
         if week+v_weeks= 20 then
          --dbms_output.put_line('Jornada '||week||':    '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          --dbms_output.put_line('Jornada '||10||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
          insert into MATCHES values(20,v_teams(v_count-i+1),v_teams(i));
          --dbms_output.put_line('Jornada '||2||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
         end if;
         if week+v_weeks= 21 then
          --dbms_output.put_line('Jornada '||week||':    '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          --dbms_output.put_line('Jornada '||9||':     '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          insert into MATCHES values(21,v_teams(i),v_teams(v_count-i+1));
          --dbms_output.put_line('Jornada '||2||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
         end if;
         if week+v_weeks= 22 then
          --dbms_output.put_line('Jornada '||week||':    '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          --dbms_output.put_line('Jornada '||10||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
          insert into MATCHES values(22,v_teams(v_count-i+1),v_teams(i));
          --dbms_output.put_line('Jornada '||2||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
         end if;
         if week+v_weeks= 23 then
          --dbms_output.put_line('Jornada '||week||':    '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          --dbms_output.put_line('Jornada '||9||':     '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          insert into MATCHES values(23,v_teams(i),v_teams(v_count-i+1));
          --dbms_output.put_line('Jornada '||2||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
         end if;
         if week+v_weeks= 24 then
          --dbms_output.put_line('Jornada '||week||':    '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          --dbms_output.put_line('Jornada '||10||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
          insert into MATCHES values(24,v_teams(v_count-i+1),v_teams(i));
          --dbms_output.put_line('Jornada '||2||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
         end if;
         if week+v_weeks= 25 then
          --dbms_output.put_line('Jornada '||week||':    '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          --dbms_output.put_line('Jornada '||9||':     '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          insert into MATCHES values(25,v_teams(i),v_teams(v_count-i+1));
          --dbms_output.put_line('Jornada '||2||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
         end if;
         if week+v_weeks= 26 then
          --dbms_output.put_line('Jornada '||week||':    '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          --dbms_output.put_line('Jornada '||10||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
          insert into MATCHES values(26,v_teams(v_count-i+1),v_teams(i));
          --dbms_output.put_line('Jornada '||2||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
         end if;
         if week+v_weeks= 27 then
          --dbms_output.put_line('Jornada '||week||':    '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          --dbms_output.put_line('Jornada '||9||':     '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          insert into MATCHES values(27,v_teams(i),v_teams(v_count-i+1));
          --dbms_output.put_line('Jornada '||2||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
         end if;
         if week+v_weeks= 28 then
          --dbms_output.put_line('Jornada '||week||':    '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          --dbms_output.put_line('Jornada '||10||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
          insert into MATCHES values(28,v_teams(v_count-i+1),v_teams(i));
          --dbms_output.put_line('Jornada '||2||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
         end if;
         if week+v_weeks= 29 then
          --dbms_output.put_line('Jornada '||week||':    '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          --dbms_output.put_line('Jornada '||9||':     '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          insert into MATCHES values(29,v_teams(i),v_teams(v_count-i+1));
          --dbms_output.put_line('Jornada '||2||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
         end if;
         if week+v_weeks= 30 then
          --dbms_output.put_line('Jornada '||week||':    '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          --dbms_output.put_line('Jornada '||10||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
          insert into MATCHES values(30,v_teams(v_count-i+1),v_teams(i));
          --dbms_output.put_line('Jornada '||2||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
         end if;
         if week+v_weeks= 31 then
          --dbms_output.put_line('Jornada '||week||':    '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          --dbms_output.put_line('Jornada '||9||':     '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          insert into MATCHES values(31,v_teams(i),v_teams(v_count-i+1));
          --dbms_output.put_line('Jornada '||2||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
         end if;
         if week+v_weeks= 32 then
          --dbms_output.put_line('Jornada '||week||':    '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          --dbms_output.put_line('Jornada '||10||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
          insert into MATCHES values(32,v_teams(v_count-i+1),v_teams(i));
          --dbms_output.put_line('Jornada '||2||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
         end if;
         if week+v_weeks= 33 then
          --dbms_output.put_line('Jornada '||week||':    '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          --dbms_output.put_line('Jornada '||9||':     '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          insert into MATCHES values(33,v_teams(i),v_teams(v_count-i+1));
          --dbms_output.put_line('Jornada '||2||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
         end if;
         if week+v_weeks= 34 then
          --dbms_output.put_line('Jornada '||week||':    '||v_teams(i)||'   X    '||v_teams(v_count-i+1));
          --dbms_output.put_line('Jornada '||10||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
          insert into MATCHES values(34,v_teams(v_count-i+1),v_teams(i));
          --dbms_output.put_line('Jornada '||2||':    '||v_teams(v_count-i+1)||'   X    '||v_teams(i));
         end if;
        end loop;
       v_team_shift := v_teams(v_count);
        for i in 1..v_count-2 loop
          v_teams(v_count-i+1) := v_teams(v_count-i);
        end loop;
        v_teams(2) := v_team_shift;
       end loop; 
commit;
/*FOR  j IN c_jornada LOOP
DBMS_OUTPUT.PUT_LINE('------------ Jornada '||j.jornada||'------------------------');
FOR  i IN c_epoca LOOP
EXIT WHEN c_epoca%NOTFOUND;
if (j.jornada = i.jornada) then
DBMS_OUTPUT.PUT_LINE(i.equipacasa ||'  X  ' ||i.equipafora);
end if;
END LOOP;
DBMS_OUTPUT.PUT_LINE('-----------------------------------------------');
END LOOP;*/
EXCEPTION
   WHEN NO_DATA_FOUND then
         NULL;
   WHEN OTHERS
   THEN
      IF SQLCODE = -24381
      THEN
         FOR indx IN 1 .. SQL%BULK_EXCEPTIONS.COUNT
         LOOP
     DBMS_OUTPUT.put_line(SQL%BULK_EXCEPTIONS(indx).ERROR_INDEX|| ': '|| SQL%BULK_EXCEPTIONS(indx).ERROR_CODE);
      END LOOP;
      END IF;
end;

/

--------------------------------------------------------
--  DDL for Package CURS_PKG
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE PACKAGE "CURS_PKG" is
procedure open;
procedure next(p_n number := 1);
procedure close;
end curs_pkg;



/
--------------------------------------------------------
--  DDL for Package FM_MIG3
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE PACKAGE "FM_MIG3" AUTHID CURRENT_USER
AS
  FUNCTION LOAD_XML_TO_MAP_SS(
      dir         VARCHAR2,
      xmlFileName VARCHAR2)
    RETURN fm_map_ss;
END FM_MIG3;


/
--------------------------------------------------------
--  DDL for Package FM_PREMIG
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE PACKAGE "FM_PREMIG" AUTHID CURRENT_USER
AS
  PROCEDURE DISABLE_CONSTRAINTS;
  -- guarda na pasta  FM_TC.mainDir o ficheiro DISABLED_CONSTRAINTS_<USER>.CSV
  -- as constraints que no final da migra? nao devem ser ligadas
  PROCEDURE GUARDA_CSV_DISABLED_CONS;
  PROCEDURE DISABLE_TRIGGERS;
  -- guarda na pasta FM_TC.mainDir o ficheiro DISABLED_TRIGGERS_<USER>.CSV
  -- os triggers que no final da migra? nao devem ser ligados
  PROCEDURE GUARDA_CSV_DISABLED_TRGS;
  -- inactivo
  -- PROCEDURE UNUSABLE_UNIQUE_INDEXES;
  --PROCEDURE RESET_SEQS_SONHO;
  -- cria tabela de mapeamento entre episodios e agendamentos da lista de espera invalidos siged e sonho
  -- chamado no final do procedimento do reset_seqs_sonho
  --PROCEDURE CREATE_MIGCHL_EPISODIOS;
  --PROCEDURE CREATE_MIGCHL_AGD_BLO_S_SALA;
  -- EXCECOES:
  -- sys_grupos especialidades (INACTIVA)
  -- isencoes extras
  --funcionarios - helder coista 4005, dominios, con_Ref_outras
  --secretariado
  --PROCEDURE INSERE_EXCECOES;
  -- este procedimento tem a funcao de criar os "alter table" para eliminar as colunas que
  -- nao sao necessarias no SIGED e aind cria o ficheiro "selects_bcp_siged.csv"
  -- que fica na pasta LOAD_TABS para exportar apenas as colunas que nao sao eliminadas no "alter table"
  --PROCEDURE FILTRA_COLS_CRIA_QS_SIGED;
  -- cria o ficheiro de control (.ctl) fazendo queries a tabela do sistema all_tab_columns
  -- ?evado em conta o data type
  -- percorre todas as tabelas do schema de migra? excepto a CONSTRAINTS_SIGED, ZZAUX e ESPECIALIDADES_SIGEED (obsoleta)
  --PROCEDURE CREATE_CTL_FILES_SQLLDR;
END FM_PREMIG;


/
--------------------------------------------------------
--  DDL for Package FM_TC
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE PACKAGE "FM_TC" AUTHID CURRENT_USER
AS
  --
  --
  -- CONSTANTES
  FUNCTION mainDir
    RETURN VARCHAR2;-- VARCHAR2(2) := 'FM';
  FUNCTION migUsr
    RETURN VARCHAR2;-- VARCHAR2(9) := 'SIAR'
  FUNCTION xmlMap
    RETURN VARCHAR2; -- sonho_siged_map.xml
  /*FUNCTION userGenerico
    RETURN NUMBER; -- num_funcionario onde o utilizador ?000*/
  FUNCTION isencaoMigracao
    RETURN NUMBER; -- cod=60, codigo de isencao de migracao, registos nao agendados
  FUNCTION cod_am_taxa_consulta
    RETURN NUMBER; -- cod=600, codigo ato medico de consulta na sys_rad_taxas
  FUNCTION cod_subsistema_desconhecido
    RETURN NUMBER; --990004, entidade desconhecida
  --
TYPE LISTA2
IS
  TABLE OF VARCHAR2(250);
  --
  FUNCTION seqs_para_reset_extra_xml
    RETURN lista2; -- lista de seqs que nao estao no mapeamento (xml) e no entanto ?ecessario fazer o reset.
  --
  FUNCTION refs_extra_xml_e_constraints
    RETURN lista2; -- lista de referenciasquenao estao no mapeamento nem na tabela CONSTRAINTS_SIGED e sao necessarias para a migracao
  --
  FUNCTION tabs_migracao_extra_xml
    RETURN lista2; -- lista de tabelas do SONHO envolvidas na migra? que n?est?contempladas no xml de mapeamento
  --
  FUNCTION tabs_migchl_sem_template
    RETURN lista2; -- lista de tabelas do modulo MIGCHL (modulo de migracao do SONHO) nao preenchidas via template e sim via os procedimentos de migra?
  --
  FUNCTION tabs_prioritarias(
      prioridade NUMBER)
    RETURN lista2; -- lista das tabelas do XML de mapeamento que devem ser carregadas antes das restantes,
  --pois existem algumas tabelas que sao dependentes do carregamento destas
  --
  FUNCTION cod_medico_siged_que_sao_espec
    RETURN lista2; -- devolve uma lista de cod_medico do SIGED que s?especialidades para usar em "NOT IN", a levar em conta para a tabela MIGCHL_CON_MED_ESP
  --e nos campos de cod_med_esp
  --
  --
  -- TIPOS
TYPE LISTA
IS
  TABLE OF VARCHAR2(4000);
  --
TYPE LISTA_N
IS
  TABLE OF NUMBER;
  --
TYPE TABELA
IS
  TABLE OF LISTA2;
  --
TYPE TAB
IS
  TABLE OF LISTA;
  --
  -- Get where stmt para filtrar ao extrair do SIGED, BD SQLServer
  FUNCTION GET_AUXWHERE_FILTRA_FONTE(
      tab VARCHAR2)
    RETURN VARCHAR2;
  --
  -- Get tab de pedidos relativamente ?abela de exames do SIGED
  FUNCTION GET_TAB_PEDIDOS_SIGED(
      tab    VARCHAR2,
      cod_am VARCHAR2 := NULL)
    RETURN VARCHAR2;
  --
  FUNCTION TABS_COM_NUM_SEQ(
      del_ou_upd VARCHAR2)
      return LISTA2;
  --
END FM_TC;


/
--------------------------------------------------------
--  DDL for Package PKG1
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE PACKAGE "PKG1" is
pragma serially_reusable;
num number := 0;
procedure init_pkg_state(n number);
procedure print_pkg_state;
procedure Ati_Des_Uti(dta_hoje in varchar,res out Varchar2);
end pkg1;

/

  GRANT EXECUTE ON "PKG1" TO PUBLIC;
--------------------------------------------------------
--  DDL for Package PKUTENTE
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE PACKAGE "PKUTENTE" is
--pragma serially_reusable;
num number := 0;
procedure Calcula_Idade(Idade in Date,res out Varchar2);
procedure Valida_Nome(Nome1 in varchar ,Nome_ant in varchar,res out Varchar2);
procedure Valida_Nome_Array(Nome_anti in varchar ,Nome_ori in varchar,res out Varchar2);
procedure Valida_Password(pass in varchar ,Num_mec in number,res out Varchar2);
procedure Altera_Password(pass in varchar ,Num_mec in number,res out Varchar2);
function  Codifica_Telefone(Phone in varchar) return varchar;
end PKUTENTE;

/
--------------------------------------------------------
--  DDL for Package STRING_FNC
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE PACKAGE "STRING_FNC" 
IS
TYPE t_array IS TABLE OF VARCHAR2(3900)
INDEX BY BINARY_INTEGER;
FUNCTION SPLIT2ARRAY (p_in_string VARCHAR2, p_delim VARCHAR2) RETURN t_array;
END;

/
--------------------------------------------------------
--  DDL for Package Body AS_XLSX
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE PACKAGE BODY "AS_XLSX" is
  --
  c_LOCAL_FILE_HEADER        constant raw(4) := hextoraw('504B0304'); -- Local file header signature
  c_END_OF_CENTRAL_DIRECTORY constant raw(4) := hextoraw('504B0506'); -- End of central directory signature
  --
  type tp_XF_fmt is record(
    numFmtId  pls_integer,
    fontId    pls_integer,
    fillId    pls_integer,
    borderId  pls_integer,
    alignment tp_alignment);
  type tp_col_fmts is table of tp_XF_fmt index by pls_integer;
  type tp_row_fmts is table of tp_XF_fmt index by pls_integer;
  type tp_widths is table of number index by pls_integer;
  type tp_heights is table of number index by pls_integer;
  type tp_cell is record(
    value number,
    style varchar2(50));
  type tp_cells is table of tp_cell index by pls_integer;
  type tp_rows is table of tp_cells index by pls_integer;
  type tp_autofilter is record(
    column_start pls_integer,
    column_end   pls_integer,
    row_start    pls_integer,
    row_end      pls_integer);
  type tp_autofilters is table of tp_autofilter index by pls_integer;
  type tp_hyperlink is record(
    cell     varchar2(10),
    url      varchar2(1000),
    location varchar2(1000));
  type tp_hyperlinks is table of tp_hyperlink index by pls_integer;
  subtype tp_author is varchar2(32767 char);
  type tp_authors is table of pls_integer index by tp_author;
  authors tp_authors;
  type tp_comment is record(
    text   varchar2(32767 char),
    author tp_author,
    row    pls_integer,
    column pls_integer,
    width  pls_integer,
    height pls_integer);
  type tp_comments is table of tp_comment index by pls_integer;
  type tp_mergecells is table of varchar2(21) index by pls_integer;
  type tp_validation is record(
    type             varchar2(10),
    errorstyle       varchar2(32),
    showinputmessage boolean,
    prompt           varchar2(32767 char),
    title            varchar2(32767 char),
    error_title      varchar2(32767 char),
    error_txt        varchar2(32767 char),
    showerrormessage boolean,
    formula1         varchar2(32767 char),
    formula2         varchar2(32767 char),
    allowBlank       boolean,
    sqref            varchar2(32767 char));
  type tp_validations is table of tp_validation index by pls_integer;
  type tp_sheet is record(
    rows        tp_rows,
    widths      tp_widths,
    heights     tp_heights,
    name        varchar2(100),
    freeze_rows pls_integer,
    freeze_cols pls_integer,
    autofilters tp_autofilters,
    hyperlinks  tp_hyperlinks,
    col_fmts    tp_col_fmts,
    row_fmts    tp_row_fmts,
    comments    tp_comments,
    mergecells  tp_mergecells,
    validations tp_validations);
  type tp_sheets is table of tp_sheet index by pls_integer;
  type tp_numFmt is record(
    numFmtId   pls_integer,
    formatCode varchar2(100));
  type tp_numFmts is table of tp_numFmt index by pls_integer;
  type tp_fill is record(
    patternType varchar2(30),
    fgRGB       varchar2(8));
  type tp_fills is table of tp_fill index by pls_integer;
  type tp_cellXfs is table of tp_xf_fmt index by pls_integer;
  type tp_font is record(
    name      varchar2(100),
    family    pls_integer,
    fontsize  number,
    theme     pls_integer,
    RGB       varchar2(8),
    underline boolean,
    italic    boolean,
    bold      boolean);
  type tp_fonts is table of tp_font index by pls_integer;
  type tp_border is record(
    top    varchar2(17),
    bottom varchar2(17),
    left   varchar2(17),
    right  varchar2(17));
  type tp_borders is table of tp_border index by pls_integer;
  type tp_numFmtIndexes is table of pls_integer index by pls_integer;
  type tp_strings is table of pls_integer index by varchar2(32767 char);
  type tp_str_ind is table of varchar2(32767 char) index by pls_integer;
  type tp_defined_name is record(
    name  varchar2(32767 char),
    ref   varchar2(32767 char),
    sheet pls_integer);
  type tp_defined_names is table of tp_defined_name index by pls_integer;
  type tp_book is record(
    sheets        tp_sheets,
    strings       tp_strings,
    str_ind       tp_str_ind,
    str_cnt       pls_integer := 0,
    fonts         tp_fonts,
    fills         tp_fills,
    borders       tp_borders,
    numFmts       tp_numFmts,
    cellXfs       tp_cellXfs,
    numFmtIndexes tp_numFmtIndexes,
    defined_names tp_defined_names);
  workbook tp_book;
  --
  procedure blob2file(p_blob blob, p_directory varchar2 := 'MY_DIR', p_filename varchar2 := 'my.xlsx') is
    t_fh  utl_file.file_type;
    t_len pls_integer := 32767;
  begin
    t_fh := utl_file.fopen(p_directory, p_filename, 'wb');
    for i in 0 .. trunc((dbms_lob.getlength(p_blob) - 1) / t_len) loop
      utl_file.put_raw(t_fh, dbms_lob.substr(p_blob, t_len, i * t_len + 1));
    end loop;
    utl_file.fclose(t_fh);
  end;
  --
  function raw2num(p_raw raw, p_len integer, p_pos integer) return number is
  begin
    return utl_raw.cast_to_binary_integer(utl_raw.substr(p_raw, p_pos, p_len), utl_raw.little_endian);
  end;
  --
  function little_endian(p_big number, p_bytes pls_integer := 4) return raw is
  begin
    return utl_raw.substr(utl_raw.cast_from_binary_integer(p_big, utl_raw.little_endian), 1, p_bytes);
  end;
  --
  function blob2num(p_blob blob, p_len integer, p_pos integer) return number is
  begin
    return utl_raw.cast_to_binary_integer(dbms_lob.substr(p_blob, p_len, p_pos), utl_raw.little_endian);
  end;
  --
  procedure add1file(p_zipped_blob in out blob, p_name varchar2, p_content blob) is
    t_now        date;
    t_blob       blob;
    t_len        integer;
    t_clen       integer;
    t_crc32      raw(4) := hextoraw('00000000');
    t_compressed boolean := false;
    t_name       raw(32767);
  begin
    t_now := sysdate;
    t_len := nvl(dbms_lob.getlength(p_content), 0);
    if t_len > 0 then
      t_blob       := utl_compress.lz_compress(p_content);
      t_clen       := dbms_lob.getlength(t_blob) - 18;
      t_compressed := t_clen < t_len;
      t_crc32      := dbms_lob.substr(t_blob, 4, t_clen + 11);
    end if;
    if not t_compressed then
      t_clen := t_len;
      t_blob := p_content;
    end if;
    if p_zipped_blob is null then
      dbms_lob.createtemporary(p_zipped_blob, true);
    end if;
    t_name := utl_i18n.string_to_raw(p_name, 'AL32UTF8');
    dbms_lob.append(p_zipped_blob,
                    utl_raw.concat(c_LOCAL_FILE_HEADER -- Local file header signature
                                  ,
                                   hextoraw('1400') -- version 2.0
                                  ,
                                   case when t_name = utl_i18n.string_to_raw(p_name, 'US8PC437') then
                                   hextoraw('0000') -- no General purpose bits
                                   else hextoraw('0008') -- set Language encoding flag (EFS)
                                   end,
                                   case when t_compressed then hextoraw('0800') -- deflate
                                   else hextoraw('0000') -- stored
                                   end,
                                   little_endian(to_number(to_char(t_now, 'ss')) / 2 +
                                                 to_number(to_char(t_now, 'mi')) * 32 +
                                                 to_number(to_char(t_now, 'hh24')) * 2048,
                                                 2) -- File last modification time
                                  ,
                                   little_endian(to_number(to_char(t_now, 'dd')) +
                                                 to_number(to_char(t_now, 'mm')) * 32 +
                                                 (to_number(to_char(t_now, 'yyyy')) - 1980) * 512,
                                                 2) -- File last modification date
                                  ,
                                   t_crc32 -- CRC-32
                                  ,
                                   little_endian(t_clen) -- compressed size
                                  ,
                                   little_endian(t_len) -- uncompressed size
                                  ,
                                   little_endian(utl_raw.length(t_name), 2) -- File name length
                                  ,
                                   hextoraw('0000') -- Extra field length
                                  ,
                                   t_name -- File name
                                   ));
    if t_compressed then
      dbms_lob.copy(p_zipped_blob, t_blob, t_clen, dbms_lob.getlength(p_zipped_blob) + 1, 11); -- compressed content
    elsif t_clen > 0 then
      dbms_lob.copy(p_zipped_blob, t_blob, t_clen, dbms_lob.getlength(p_zipped_blob) + 1, 1); --  content
    end if;
    if dbms_lob.istemporary(t_blob) = 1 then
      dbms_lob.freetemporary(t_blob);
    end if;
  end;
  --
  procedure finish_zip(p_zipped_blob in out blob) is
    t_cnt             pls_integer := 0;
    t_offs            integer;
    t_offs_dir_header integer;
    t_offs_end_header integer;
    t_comment         raw(32767) := utl_raw.cast_to_raw('Implementation by Anton Scheffer');
  begin
    t_offs_dir_header := dbms_lob.getlength(p_zipped_blob);
    t_offs            := 1;
    while dbms_lob.substr(p_zipped_blob, utl_raw.length(c_LOCAL_FILE_HEADER), t_offs) =
          c_LOCAL_FILE_HEADER loop
      t_cnt := t_cnt + 1;
      dbms_lob.append(p_zipped_blob,
                      utl_raw.concat(hextoraw('504B0102') -- Central directory file header signature
                                    ,
                                     hextoraw('1400') -- version 2.0
                                    ,
                                     dbms_lob.substr(p_zipped_blob, 26, t_offs + 4),
                                     hextoraw('0000') -- File comment length
                                    ,
                                     hextoraw('0000') -- Disk number where file starts
                                    ,
                                     hextoraw('0000') -- Internal file attributes => 
                                     --     0000 binary file
                                     --     0100 (ascii)text file
                                    ,
                                     case when
                                     dbms_lob.substr(p_zipped_blob,
                                                     1,
                                                     t_offs + 30 + blob2num(p_zipped_blob, 2, t_offs + 26) - 1) in
                                     (hextoraw('2F') -- /
                                    ,
                                      hextoraw('5C') -- \
                                      ) then hextoraw('10000000') -- a directory/folder
                                     else hextoraw('2000B681') -- a file
                                     end -- External file attributes
                                    ,
                                     little_endian(t_offs - 1) -- Relative offset of local file header
                                    ,
                                     dbms_lob.substr(p_zipped_blob,
                                                     blob2num(p_zipped_blob, 2, t_offs + 26),
                                                     t_offs + 30) -- File name
                                     ));
      t_offs := t_offs + 30 + blob2num(p_zipped_blob, 4, t_offs + 18) -- compressed size
                + blob2num(p_zipped_blob, 2, t_offs + 26) -- File name length 
                + blob2num(p_zipped_blob, 2, t_offs + 28); -- Extra field length
    end loop;
    t_offs_end_header := dbms_lob.getlength(p_zipped_blob);
    dbms_lob.append(p_zipped_blob,
                    utl_raw.concat(c_END_OF_CENTRAL_DIRECTORY -- End of central directory signature
                                  ,
                                   hextoraw('0000') -- Number of this disk
                                  ,
                                   hextoraw('0000') -- Disk where central directory starts
                                  ,
                                   little_endian(t_cnt, 2) -- Number of central directory records on this disk
                                  ,
                                   little_endian(t_cnt, 2) -- Total number of central directory records
                                  ,
                                   little_endian(t_offs_end_header - t_offs_dir_header) -- Size of central directory
                                  ,
                                   little_endian(t_offs_dir_header) -- Offset of start of central directory, relative to start of archive
                                  ,
                                   little_endian(nvl(utl_raw.length(t_comment), 0), 2) -- ZIP file comment length
                                  ,
                                   t_comment));
  end;
  --
  function alfan_col(p_col pls_integer) return varchar2 is
  begin
    return case when p_col > 702 then chr(64 + trunc((p_col - 27) / 676)) || chr(65 + mod(trunc((p_col - 1) / 26) - 1,
                                                                                          26)) || chr(65 +
                                                                                                      mod(p_col - 1,
                                                                                                          26)) when p_col > 26 then chr(64 +
                                                                                                                                        trunc((p_col - 1) / 26)) || chr(65 +
                                                                                                                                                                        mod(p_col - 1,
                                                                                                                                                                            26)) else chr(64 +
                                                                                                                                                                                          p_col) end;
  end;
  --
  function col_alfan(p_col varchar2) return pls_integer is
  begin
    return ascii(substr(p_col, -1)) - 64 + nvl((ascii(substr(p_col, -2, 1)) - 64) * 26, 0) + nvl((ascii(substr(p_col,
                                                                                                               -3,
                                                                                                               1)) - 64) * 676,
                                                                                                 0);
  end;
  --
  procedure clear_workbook is
    t_row_ind pls_integer;
  begin
    for s in 1 .. workbook.sheets.count() loop
      t_row_ind := workbook.sheets(s).rows.first();
      while t_row_ind is not null loop
        workbook.sheets(s).rows(t_row_ind).delete();
        t_row_ind := workbook.sheets(s).rows.next(t_row_ind);
      end loop;
      workbook.sheets(s).rows.delete();
      workbook.sheets(s).widths.delete();
      workbook.sheets(s).autofilters.delete();
      workbook.sheets(s).hyperlinks.delete();
      workbook.sheets(s).col_fmts.delete();
      workbook.sheets(s).row_fmts.delete();
      workbook.sheets(s).comments.delete();
      workbook.sheets(s).mergecells.delete();
      workbook.sheets(s).validations.delete();
    end loop;
    workbook.strings.delete();
    workbook.str_ind.delete();
    workbook.fonts.delete();
    workbook.fills.delete();
    workbook.borders.delete();
    workbook.numFmts.delete();
    workbook.cellXfs.delete();
    workbook.defined_names.delete();
    workbook := null;
  end;
  --
  procedure new_sheet(p_sheetname varchar2 := null) is
    t_nr  pls_integer := workbook.sheets.count() + 1;
    t_ind pls_integer;
  begin
    workbook.sheets(t_nr).name := nvl(dbms_xmlgen.convert(translate(p_sheetname, 'a/\[]*:?', 'a')),
                                      'Sheet' || t_nr);
    if workbook.strings.count() = 0 then
      workbook.str_cnt := 0;
    end if;
    if workbook.fonts.count() = 0 then
      t_ind := get_font('Calibri');
    end if;
    if workbook.fills.count() = 0 then
      t_ind := get_fill('none');
      t_ind := get_fill('gray125');
    end if;
    if workbook.borders.count() = 0 then
      t_ind := get_border('', '', '', '');
    end if;
  end;
  --
  procedure set_col_width(p_sheet pls_integer, p_col pls_integer, p_format varchar2) is
    t_width  number;
    t_nr_chr pls_integer;
  begin
    if p_format is null then
      return;
    end if;
    if instr(p_format, ';') > 0 then
      t_nr_chr := length(translate(substr(p_format, 1, instr(p_format, ';') - 1), 'a\"', 'a'));
    else
      t_nr_chr := length(translate(p_format, 'a\"', 'a'));
    end if;
    t_width := trunc((t_nr_chr * 7 + 5) / 7 * 256) / 256; -- assume default 11 point Calibri
    if workbook.sheets(p_sheet).widths.exists(p_col) then
      if(workbook.sheets(p_sheet).widths(p_col) is null or workbook.sheets(p_sheet).widths(p_col) <=0) then
        workbook.sheets(p_sheet).widths(p_col) := greatest(workbook.sheets(p_sheet).widths(p_col), t_width);
      end if;
    else
      workbook.sheets(p_sheet).widths(p_col) := greatest(t_width, 8.43);
    end if;
  end;
  --
  function OraFmt2Excel(p_format varchar2 := null) return varchar2 is
    t_format varchar2(1000) := substr(p_format, 1, 1000);
  begin
    t_format := replace(replace(t_format, 'hh24', 'hh'), 'hh12', 'hh');
    t_format := replace(t_format, 'mi', 'mm');
    t_format := replace(replace(replace(t_format, 'AM', '~~'), 'PM', '~~'), '~~', 'AM/PM');
    t_format := replace(replace(replace(t_format, 'am', '~~'), 'pm', '~~'), '~~', 'AM/PM');
    t_format := replace(replace(t_format, 'day', 'DAY'), 'DAY', 'dddd');
    t_format := replace(replace(t_format, 'dy', 'DY'), 'DAY', 'ddd');
    t_format := replace(replace(t_format, 'RR', 'RR'), 'RR', 'YY');
    t_format := replace(replace(t_format, 'month', 'MONTH'), 'MONTH', 'mmmm');
    t_format := replace(replace(t_format, 'mon', 'MON'), 'MON', 'mmm');
    return t_format;
  end;
  --
  function get_numFmt(p_format varchar2 := null) return pls_integer is
    t_cnt      pls_integer;
    t_numFmtId pls_integer;
  begin
    if p_format is null then
      return 0;
    end if;
    t_cnt := workbook.numFmts.count();
    for i in 1 .. t_cnt loop
      if workbook.numFmts(i).formatCode = p_format then
        t_numFmtId := workbook.numFmts(i).numFmtId;
        exit;
      end if;
    end loop;
    if t_numFmtId is null then
      t_numFmtId := case
                      when t_cnt = 0 then
                       164
                      else
                       workbook.numFmts(t_cnt).numFmtId + 1
                    end;
      t_cnt := t_cnt + 1;
      workbook.numFmts(t_cnt).numFmtId := t_numFmtId;
      workbook.numFmts(t_cnt).formatCode := p_format;
      workbook.numFmtIndexes(t_numFmtId) := t_cnt;
    end if;
    return t_numFmtId;
  end;
  --
  function get_font(p_name      varchar2,
                    p_family    pls_integer := 2,
                    p_fontsize  number := 11,
                    p_theme     pls_integer := 1,
                    p_underline boolean := false,
                    p_italic    boolean := false,
                    p_bold      boolean := false,
                    p_rgb       varchar2 := null -- this is a hex ALPHA Red Green Blue value
                    ) return pls_integer is
    t_ind pls_integer;
  begin
    if workbook.fonts.count() > 0 then
      for f in 0 .. workbook.fonts.count() - 1 loop
        if (workbook.fonts(f)
           .name = p_name and workbook.fonts(f).family = p_family and workbook.fonts(f)
           .fontsize = p_fontsize and workbook.fonts(f).theme = p_theme and workbook.fonts(f)
           .underline = p_underline and workbook.fonts(f).italic = p_italic and workbook.fonts(f)
           .bold = p_bold and
            (workbook.fonts(f).rgb = p_rgb or (workbook.fonts(f).rgb is null and p_rgb is null))) then
          return f;
        end if;
      end loop;
    end if;
    t_ind := workbook.fonts.count();
    workbook.fonts(t_ind).name := p_name;
    workbook.fonts(t_ind).family := p_family;
    workbook.fonts(t_ind).fontsize := p_fontsize;
    workbook.fonts(t_ind).theme := p_theme;
    workbook.fonts(t_ind).underline := p_underline;
    workbook.fonts(t_ind).italic := p_italic;
    workbook.fonts(t_ind).bold := p_bold;
    workbook.fonts(t_ind).rgb := p_rgb;
    return t_ind;
  end;
  --
  function get_fill(p_patternType varchar2, p_fgRGB varchar2 := null) return pls_integer is
    t_ind pls_integer;
  begin
    if workbook.fills.count() > 0 then
      for f in 0 .. workbook.fills.count() - 1 loop
        if (workbook.fills(f)
           .patternType = p_patternType and nvl(workbook.fills(f).fgRGB, 'x') = nvl(upper(p_fgRGB), 'x')) then
          return f;
        end if;
      end loop;
    end if;
    t_ind := workbook.fills.count();
    workbook.fills(t_ind).patternType := p_patternType;
    workbook.fills(t_ind).fgRGB := upper(p_fgRGB);
    return t_ind;
  end;
  --
  function get_border(p_top    varchar2 := 'thin',
                      p_bottom varchar2 := 'thin',
                      p_left   varchar2 := 'thin',
                      p_right  varchar2 := 'thin') return pls_integer is
    t_ind pls_integer;
  begin
    if workbook.borders.count() > 0 then
      for b in 0 .. workbook.borders.count() - 1 loop
        if (nvl(workbook.borders(b).top, 'x') = nvl(p_top, 'x') and
           nvl(workbook.borders(b).bottom, 'x') = nvl(p_bottom, 'x') and
           nvl(workbook.borders(b).left, 'x') = nvl(p_left, 'x') and
           nvl(workbook.borders(b).right, 'x') = nvl(p_right, 'x')) then
          return b;
        end if;
      end loop;
    end if;
    t_ind := workbook.borders.count();
    workbook.borders(t_ind).top := p_top;
    workbook.borders(t_ind).bottom := p_bottom;
    workbook.borders(t_ind).left := p_left;
    workbook.borders(t_ind).right := p_right;
    return t_ind;
  end;
  --
  function get_alignment(p_vertical   varchar2 := null,
                         p_horizontal varchar2 := null,
                         p_wrapText   boolean := null) return tp_alignment is
    t_rv tp_alignment;
  begin
    t_rv.vertical   := p_vertical;
    t_rv.horizontal := p_horizontal;
    t_rv.wrapText   := p_wrapText;
    return t_rv;
  end;
  --
  function get_XfId(p_sheet     pls_integer,
                    p_col       pls_integer,
                    p_row       pls_integer,
                    p_numFmtId  pls_integer := null,
                    p_fontId    pls_integer := null,
                    p_fillId    pls_integer := null,
                    p_borderId  pls_integer := null,
                    p_alignment tp_alignment := null) return varchar2 is
    t_cnt    pls_integer;
    t_XfId   pls_integer;
    t_XF     tp_XF_fmt;
    t_col_XF tp_XF_fmt;
    t_row_XF tp_XF_fmt;
  begin
    if workbook.sheets(p_sheet).col_fmts.exists(p_col) then
      t_col_XF := workbook.sheets(p_sheet).col_fmts(p_col);
    end if;
    if workbook.sheets(p_sheet).row_fmts.exists(p_row) then
      t_row_XF := workbook.sheets(p_sheet).row_fmts(p_row);
    end if;
    t_XF.numFmtId  := coalesce(p_numFmtId, t_col_XF.numFmtId, t_row_XF.numFmtId, 0);
    t_XF.fontId    := coalesce(p_fontId, t_col_XF.fontId, t_row_XF.fontId, 0);
    t_XF.fillId    := coalesce(p_fillId, t_col_XF.fillId, t_row_XF.fillId, 0);
    t_XF.borderId  := coalesce(p_borderId, t_col_XF.borderId, t_row_XF.borderId, 0);
    t_XF.alignment := coalesce(p_alignment, t_col_XF.alignment, t_row_XF.alignment);
    if (t_XF.numFmtId + t_XF.fontId + t_XF.fillId + t_XF.borderId = 0 and t_XF.alignment.vertical is null and
       t_XF.alignment.horizontal is null and not nvl(t_XF.alignment.wrapText, false)) then
      return '';
    end if;
    if (t_XF.numFmtId > 0 and workbook.numFmtIndexes.exists(t_XF.numFmtId)) then
      set_col_width(p_sheet, p_col, workbook.numFmts(workbook.numFmtIndexes(t_XF.numFmtId)).formatCode);
    end if;
    t_cnt := workbook.cellXfs.count();
    for i in 1 .. t_cnt loop
      if (workbook.cellXfs(i)
         .numFmtId = t_XF.numFmtId and workbook.cellXfs(i).fontId = t_XF.fontId and workbook.cellXfs(i)
         .fillId = t_XF.fillId and workbook.cellXfs(i).borderId = t_XF.borderId and
          nvl(workbook.cellXfs(i).alignment.vertical, 'x') = nvl(t_XF.alignment.vertical, 'x') and
          nvl(workbook.cellXfs(i).alignment.horizontal, 'x') = nvl(t_XF.alignment.horizontal, 'x') and
          nvl(workbook.cellXfs(i).alignment.wrapText, false) = nvl(t_XF.alignment.wrapText, false)) then
        t_XfId := i;
        exit;
      end if;
    end loop;
    if t_XfId is null then
      t_cnt := t_cnt + 1;
      t_XfId := t_cnt;
      workbook.cellXfs(t_cnt) := t_XF;
    end if;
    return 's="' || t_XfId || '"';
  end;
  --
  procedure cell(p_col       pls_integer,
                 p_row       pls_integer,
                 p_value     number,
                 p_numFmtId  pls_integer := null,
                 p_fontId    pls_integer := null,
                 p_fillId    pls_integer := null,
                 p_borderId  pls_integer := null,
                 p_alignment tp_alignment := null,
                 p_sheet     pls_integer := null) is
    t_sheet pls_integer := nvl(p_sheet, workbook.sheets.count());
  begin
    workbook.sheets(t_sheet).rows(p_row)(p_col).value := p_value;
    workbook.sheets(t_sheet).rows(p_row)(p_col).style := null;
    workbook.sheets(t_sheet).rows(p_row)(p_col).style := get_XfId(t_sheet,
                                                                  p_col,
                                                                  p_row,
                                                                  p_numFmtId,
                                                                  p_fontId,
                                                                  p_fillId,
                                                                  p_borderId,
                                                                  p_alignment);
  end;
  --
  function add_string(p_string varchar2) return pls_integer is
    t_cnt pls_integer;
  begin
    if (p_string is null) then 
      return null; 
    end if;
    if workbook.strings.exists(p_string) then
      t_cnt := workbook.strings(p_string);
    else
      t_cnt := workbook.strings.count();
      workbook.str_ind(t_cnt) := p_string;
      workbook.strings(nvl(p_string, '')) := t_cnt;
    end if;
    workbook.str_cnt := workbook.str_cnt + 1;
    return t_cnt;
  end;
  --
  procedure cell(p_col       pls_integer,
                 p_row       pls_integer,
                 p_value     varchar2,
                 p_numFmtId  pls_integer := null,
                 p_fontId    pls_integer := null,
                 p_fillId    pls_integer := null,
                 p_borderId  pls_integer := null,
                 p_alignment tp_alignment := null,
                 p_sheet     pls_integer := null) is
    t_sheet     pls_integer := nvl(p_sheet, workbook.sheets.count());
    t_alignment tp_alignment := p_alignment;
  begin
    workbook.sheets(t_sheet).rows(p_row)(p_col).value := add_string(nvl(p_value, ''));
    if t_alignment.wrapText is null and instr(p_value, chr(13)) > 0 then
      t_alignment.wrapText := true;
    end if;
    workbook.sheets(t_sheet).rows(p_row)(p_col).style := 't="s" ' || get_XfId(t_sheet,
                                                                              p_col,
                                                                              p_row,
                                                                              p_numFmtId,
                                                                              p_fontId,
                                                                              p_fillId,
                                                                              p_borderId,
                                                                              t_alignment);
  end;
  --
  procedure cell(p_col       pls_integer,
                 p_row       pls_integer,
                 p_value     date,
                 p_numFmtId  pls_integer := null,
                 p_fontId    pls_integer := null,
                 p_fillId    pls_integer := null,
                 p_borderId  pls_integer := null,
                 p_alignment tp_alignment := null,
                 p_sheet     pls_integer := null) is
    t_numFmtId pls_integer := p_numFmtId;
    t_sheet    pls_integer := nvl(p_sheet, workbook.sheets.count());
  begin
    workbook.sheets(t_sheet).rows(p_row)(p_col).value := p_value - to_date('01-01-1904', 'DD-MM-YYYY');
    if t_numFmtId is null and not (workbook.sheets(t_sheet).col_fmts.exists(p_col) and workbook.sheets(t_sheet).col_fmts(p_col)
        .numFmtId is not null) and not (workbook.sheets(t_sheet).row_fmts.exists(p_row) and workbook.sheets(t_sheet).row_fmts(p_row)
        .numFmtId is not null) then
      t_numFmtId := get_numFmt('dd/mm/yyyy');
    end if;
    workbook.sheets(t_sheet).rows(p_row)(p_col).style := get_XfId(t_sheet,
                                                                  p_col,
                                                                  p_row,
                                                                  t_numFmtId,
                                                                  p_fontId,
                                                                  p_fillId,
                                                                  p_borderId,
                                                                  p_alignment);
  end;
  --
  procedure hyperlink(p_col   pls_integer,
                      p_row   pls_integer,
                      p_url   varchar2,
                      p_value varchar2 := null,
                      p_sheet pls_integer := null) is
    t_ind   pls_integer;
    t_sheet pls_integer := nvl(p_sheet, workbook.sheets.count());
  begin
    workbook.sheets(t_sheet).rows(p_row)(p_col).value := add_string(nvl(p_value, p_url));
    workbook.sheets(t_sheet).rows(p_row)(p_col).style := 't="s" ' ||
                                                         get_XfId(t_sheet,
                                                                  p_col,
                                                                  p_row,
                                                                  '',
                                                                  get_font('Calibri',
                                                                           p_theme     => 10,
                                                                           p_underline => true));
    t_ind := workbook.sheets(t_sheet).hyperlinks.count() + 1;
    workbook.sheets(t_sheet).hyperlinks(t_ind).cell := alfan_col(p_col) || p_row;
    workbook.sheets(t_sheet).hyperlinks(t_ind).url := p_url;
  end;
  --
  procedure hyperlink_loc(p_col      pls_integer,
      	                  p_row      pls_integer,
                          p_location varchar2) is
    t_ind   pls_integer;
    t_sheet pls_integer := workbook.sheets.count();
  begin
    workbook.sheets(t_sheet).rows(p_row)(p_col).style := 't="s" ' ||
                                                         get_XfId(t_sheet, p_col, p_row, '',
                                                                  get_font('Calibri',
                                                                           p_theme     => 10,
                                                                           p_underline => true));
    t_ind := workbook.sheets(t_sheet).hyperlinks.count() + 1;
    workbook.sheets(t_sheet).hyperlinks(t_ind).cell := alfan_col(p_col) || p_row;
    workbook.sheets(t_sheet).hyperlinks(t_ind).location := p_location;
  end;
  --
  procedure comment(p_col    pls_integer,
                    p_row    pls_integer,
                    p_text   varchar2,
                    p_author varchar2 := null,
                    p_width  pls_integer := 150,
                    p_height pls_integer := 100,
                    p_sheet  pls_integer := null) is
    t_ind   pls_integer;
    t_sheet pls_integer := nvl(p_sheet, workbook.sheets.count());
  begin
    t_ind := workbook.sheets(t_sheet).comments.count() + 1;
    workbook.sheets(t_sheet).comments(t_ind).row := p_row;
    workbook.sheets(t_sheet).comments(t_ind).column := p_col;
    workbook.sheets(t_sheet).comments(t_ind).text := dbms_xmlgen.convert(p_text);
    workbook.sheets(t_sheet).comments(t_ind).author := dbms_xmlgen.convert(p_author);
    workbook.sheets(t_sheet).comments(t_ind).width := p_width;
    workbook.sheets(t_sheet).comments(t_ind).height := p_height;
  end;
  --
  procedure mergecells(p_tl_col pls_integer -- top left
                      ,
                       p_tl_row pls_integer,
                       p_br_col pls_integer -- bottom right
                      ,
                       p_br_row pls_integer,
                       p_sheet  pls_integer := null) is
    t_ind   pls_integer;
    t_sheet pls_integer := nvl(p_sheet, workbook.sheets.count());
  begin
    t_ind := workbook.sheets(t_sheet).mergecells.count() + 1;
    workbook.sheets(t_sheet).mergecells(t_ind) := alfan_col(p_tl_col) || p_tl_row || ':' ||
                                                  alfan_col(p_br_col) || p_br_row;
  end;
  --
  procedure add_validation(p_type        varchar2,
                           p_sqref       varchar2,
                           p_style       varchar2 := 'stop' -- stop, warning, information
                          ,
                           p_formula1    varchar2 := null,
                           p_formula2    varchar2 := null,
                           p_title       varchar2 := null,
                           p_prompt      varchar := null,
                           p_show_error  boolean := false,
                           p_error_title varchar2 := null,
                           p_error_txt   varchar2 := null,
                           p_sheet       pls_integer := null) is
    t_ind   pls_integer;
    t_sheet pls_integer := nvl(p_sheet, workbook.sheets.count());
  begin
    t_ind := workbook.sheets(t_sheet).validations.count() + 1;
    workbook.sheets(t_sheet).validations(t_ind).type := p_type;
    workbook.sheets(t_sheet).validations(t_ind).errorstyle := p_style;
    workbook.sheets(t_sheet).validations(t_ind).sqref := p_sqref;
    workbook.sheets(t_sheet).validations(t_ind).formula1 := p_formula1;
    workbook.sheets(t_sheet).validations(t_ind).error_title := p_error_title;
    workbook.sheets(t_sheet).validations(t_ind).error_txt := p_error_txt;
    workbook.sheets(t_sheet).validations(t_ind).title := p_title;
    workbook.sheets(t_sheet).validations(t_ind).prompt := p_prompt;
    workbook.sheets(t_sheet).validations(t_ind).showerrormessage := p_show_error;
  end;
  --
  procedure list_validation(p_sqref_col   pls_integer,
                            p_sqref_row   pls_integer,
                            p_tl_col      pls_integer -- top left
                           ,
                            p_tl_row      pls_integer,
                            p_br_col      pls_integer -- bottom right
                           ,
                            p_br_row      pls_integer,
                            p_style       varchar2 := 'stop' -- stop, warning, information
                           ,
                            p_title       varchar2 := null,
                            p_prompt      varchar := null,
                            p_show_error  boolean := false,
                            p_error_title varchar2 := null,
                            p_error_txt   varchar2 := null,
                            p_sheet       pls_integer := null) is
  begin
    add_validation('list',
                   alfan_col(p_sqref_col) || p_sqref_row,
                   p_style => lower(p_style),
                   p_formula1 => '$' || alfan_col(p_tl_col) || '$' || p_tl_row || ':$' ||
                                 alfan_col(p_br_col) || '$' || p_br_row,
                   p_title => p_title,
                   p_prompt => p_prompt,
                   p_show_error => p_show_error,
                   p_error_title => p_error_title,
                   p_error_txt => p_error_txt,
                   p_sheet => p_sheet);
  end;
  --
  procedure list_validation(p_sqref_col    pls_integer,
                            p_sqref_row    pls_integer,
                            p_defined_name varchar2,
                            p_style        varchar2 := 'stop' -- stop, warning, information
                           ,
                            p_title        varchar2 := null,
                            p_prompt       varchar := null,
                            p_show_error   boolean := false,
                            p_error_title  varchar2 := null,
                            p_error_txt    varchar2 := null,
                            p_sheet        pls_integer := null) is
  begin
    add_validation('list',
                   alfan_col(p_sqref_col) || p_sqref_row,
                   p_style => lower(p_style),
                   p_formula1 => p_defined_name,
                   p_title => p_title,
                   p_prompt => p_prompt,
                   p_show_error => p_show_error,
                   p_error_title => p_error_title,
                   p_error_txt => p_error_txt,
                   p_sheet => p_sheet);
  end;
  --
  procedure defined_name(p_tl_col     pls_integer -- top left
                        ,
                         p_tl_row     pls_integer,
                         p_br_col     pls_integer -- bottom right
                        ,
                         p_br_row     pls_integer,
                         p_name       varchar2,
                         p_sheet      pls_integer := null,
                         p_localsheet pls_integer := null) is
    t_ind        pls_integer;
    t_sheet      pls_integer := nvl(p_sheet, workbook.sheets.count());
    t_sheet_name varchar(100);
  begin
    if(workbook.sheets.exists(workbook.sheets.count) and workbook.sheets(workbook.sheets.count).name is not null) then
      t_sheet_name := workbook.sheets(workbook.sheets.count).name;
    else
      t_sheet_name := 'Sheet' || t_sheet;
    end if;
    t_sheet_name := '''' || t_sheet_name || '''';

    t_ind := workbook.defined_names.count() + 1;
    workbook.defined_names(t_ind).name := p_name;
    workbook.defined_names(t_ind).ref := t_sheet_name || '!$' || alfan_col(p_tl_col) || '$' ||
                                         p_tl_row || ':$' || alfan_col(p_br_col) || '$' || p_br_row;
    workbook.defined_names(t_ind).sheet := p_localsheet;
  end;
  --
  procedure set_column_width(p_col pls_integer, p_width number, p_sheet pls_integer := null) is
  begin
    workbook.sheets(nvl(p_sheet, workbook.sheets.count())).widths(p_col) := p_width;
  end;
  --
  procedure set_column(p_col       pls_integer,
                       p_numFmtId  pls_integer := null,
                       p_fontId    pls_integer := null,
                       p_fillId    pls_integer := null,
                       p_borderId  pls_integer := null,
                       p_alignment tp_alignment := null,
                       p_sheet     pls_integer := null) is
    t_sheet pls_integer := nvl(p_sheet, workbook.sheets.count());
  begin
    workbook.sheets(t_sheet).col_fmts(p_col).numFmtId := p_numFmtId;
    workbook.sheets(t_sheet).col_fmts(p_col).fontId := p_fontId;
    workbook.sheets(t_sheet).col_fmts(p_col).fillId := p_fillId;
    workbook.sheets(t_sheet).col_fmts(p_col).borderId := p_borderId;
    workbook.sheets(t_sheet).col_fmts(p_col).alignment := p_alignment;
  end;
  --
  procedure set_row_height(p_row pls_integer, p_height number, p_sheet pls_integer := null) is
  begin
    workbook.sheets(nvl(p_sheet, workbook.sheets.count())).heights(p_row) := p_height;
  end;
  --
  procedure set_row(p_row       pls_integer,
                    p_numFmtId  pls_integer := null,
                    p_fontId    pls_integer := null,
                    p_fillId    pls_integer := null,
                    p_borderId  pls_integer := null,
                    p_alignment tp_alignment := null,
                    p_sheet     pls_integer := null) is
    t_sheet pls_integer := nvl(p_sheet, workbook.sheets.count());
  begin
    workbook.sheets(t_sheet).row_fmts(p_row).numFmtId := p_numFmtId;
    workbook.sheets(t_sheet).row_fmts(p_row).fontId := p_fontId;
    workbook.sheets(t_sheet).row_fmts(p_row).fillId := p_fillId;
    workbook.sheets(t_sheet).row_fmts(p_row).borderId := p_borderId;
    workbook.sheets(t_sheet).row_fmts(p_row).alignment := p_alignment;
  end;
  --
  procedure freeze_rows(p_nr_rows pls_integer := 1, p_sheet pls_integer := null) is
    t_sheet pls_integer := nvl(p_sheet, workbook.sheets.count());
  begin
    workbook.sheets(t_sheet).freeze_cols := null;
    workbook.sheets(t_sheet).freeze_rows := p_nr_rows;
  end;
  --
  procedure freeze_cols(p_nr_cols pls_integer := 1, p_sheet pls_integer := null) is
    t_sheet pls_integer := nvl(p_sheet, workbook.sheets.count());
  begin
    workbook.sheets(t_sheet).freeze_rows := null;
    workbook.sheets(t_sheet).freeze_cols := p_nr_cols;
  end;
  --
  procedure freeze_pane(p_col pls_integer, p_row pls_integer, p_sheet pls_integer := null) is
    t_sheet pls_integer := nvl(p_sheet, workbook.sheets.count());
  begin
    workbook.sheets(t_sheet).freeze_rows := p_row;
    workbook.sheets(t_sheet).freeze_cols := p_col;
  end;
  --
  procedure set_autofilter(p_column_start pls_integer := null,
                           p_column_end   pls_integer := null,
                           p_row_start    pls_integer := null,
                           p_row_end      pls_integer := null,
                           p_sheet        pls_integer := null) is
    t_ind   pls_integer;
    t_sheet pls_integer := nvl(p_sheet, workbook.sheets.count());
  begin
    t_ind := 1;
    workbook.sheets(t_sheet).autofilters(t_ind).column_start := p_column_start;
    workbook.sheets(t_sheet).autofilters(t_ind).column_end := p_column_end;
    workbook.sheets(t_sheet).autofilters(t_ind).row_start := p_row_start;
    workbook.sheets(t_sheet).autofilters(t_ind).row_end := p_row_end;
    defined_name(p_column_start,
                 p_row_start,
                 p_column_end,
                 p_row_end,
                 '_xlnm._FilterDatabase',
                 t_sheet,
                 t_sheet - 1);
  end;
  --
  procedure add1xml(p_excel in out nocopy blob, p_filename varchar2, p_xml clob) is
    t_tmp        blob;
    dest_offset  integer := 1;
    src_offset   integer := 1;
    lang_context integer;
    warning      integer;
  begin
    lang_context := dbms_lob.DEFAULT_LANG_CTX;
    dbms_lob.createtemporary(t_tmp, true);
    dbms_lob.converttoblob(t_tmp,
                           p_xml,
                           dbms_lob.lobmaxsize,
                           dest_offset,
                           src_offset,
                           nls_charset_id('AL32UTF8'),
                           lang_context,
                           warning);
    add1file(p_excel, p_filename, t_tmp);
    dbms_lob.freetemporary(t_tmp);
  end;
  --
  function finish return blob is
    t_excel      blob;
    t_xxx        clob;
    t_tmp        varchar2(32767 char);
    t_str        varchar2(32767 char);
    t_c          number;
    t_h          number;
    t_w          number;
    t_cw         number;
    t_cell       varchar2(1000 char);
    t_row_ind    pls_integer;
    t_col_min    pls_integer;
    t_col_max    pls_integer;
    t_col_ind    pls_integer;
    t_len        pls_integer;
    ts           timestamp := systimestamp;
    t_row_height varchar2(100);
  begin
    dbms_lob.createtemporary(t_excel, true);
    t_xxx := '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<Types xmlns="http://schemas.openxmlformats.org/package/2006/content-types">
<Default Extension="rels" ContentType="application/vnd.openxmlformats-package.relationships+xml"/>
<Default Extension="xml" ContentType="application/xml"/>
<Default Extension="vml" ContentType="application/vnd.openxmlformats-officedocument.vmlDrawing"/>
<Override PartName="/xl/workbook.xml" ContentType="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet.main+xml"/>';
    for s in 1 .. workbook.sheets.count() loop
      t_xxx := t_xxx || '
<Override PartName="/xl/worksheets/sheet' || s ||
               '.xml" ContentType="application/vnd.openxmlformats-officedocument.spreadsheetml.worksheet+xml"/>';
    end loop;
    t_xxx := t_xxx || '
<Override PartName="/xl/theme/theme1.xml" ContentType="application/vnd.openxmlformats-officedocument.theme+xml"/>
<Override PartName="/xl/styles.xml" ContentType="application/vnd.openxmlformats-officedocument.spreadsheetml.styles+xml"/>
<Override PartName="/xl/sharedStrings.xml" ContentType="application/vnd.openxmlformats-officedocument.spreadsheetml.sharedStrings+xml"/>
<Override PartName="/docProps/core.xml" ContentType="application/vnd.openxmlformats-package.core-properties+xml"/>
<Override PartName="/docProps/app.xml" ContentType="application/vnd.openxmlformats-officedocument.extended-properties+xml"/>';
    for s in 1 .. workbook.sheets.count() loop
      if workbook.sheets(s).comments.count() > 0 then
        t_xxx := t_xxx || '
<Override PartName="/xl/comments' || s ||
                 '.xml" ContentType="application/vnd.openxmlformats-officedocument.spreadsheetml.comments+xml"/>';
      end if;
    end loop;
    t_xxx := t_xxx || '
</Types>';
    add1xml(t_excel, '[Content_Types].xml', t_xxx);
    t_xxx := '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<cp:coreProperties xmlns:cp="http://schemas.openxmlformats.org/package/2006/metadata/core-properties" xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:dcterms="http://purl.org/dc/terms/" xmlns:dcmitype="http://purl.org/dc/dcmitype/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
<dc:creator>' || sys_context('userenv', 'os_user') || '</dc:creator>
<cp:lastModifiedBy>' || sys_context('userenv', 'os_user') ||
             '</cp:lastModifiedBy>
<dcterms:created xsi:type="dcterms:W3CDTF">' ||
             to_char(current_timestamp, 'yyyy-mm-dd"T"hh24:mi:ssTZH:TZM') ||
             '</dcterms:created>
<dcterms:modified xsi:type="dcterms:W3CDTF">' ||
             to_char(current_timestamp, 'yyyy-mm-dd"T"hh24:mi:ssTZH:TZM') || '</dcterms:modified>
</cp:coreProperties>';
    add1xml(t_excel, 'docProps/core.xml', t_xxx);
    t_xxx := '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<Properties xmlns="http://schemas.openxmlformats.org/officeDocument/2006/extended-properties" xmlns:vt="http://schemas.openxmlformats.org/officeDocument/2006/docPropsVTypes">
<Application>Microsoft Excel</Application>
<DocSecurity>0</DocSecurity>
<ScaleCrop>false</ScaleCrop>
<HeadingPairs>
<vt:vector size="2" baseType="variant">
<vt:variant>
<vt:lpstr>Worksheets</vt:lpstr>
</vt:variant>
<vt:variant>
<vt:i4>' || workbook.sheets.count() || '</vt:i4>
</vt:variant>
</vt:vector>
</HeadingPairs>
<TitlesOfParts>
<vt:vector size="' || workbook.sheets.count() || '" baseType="lpstr">';
    for s in 1 .. workbook.sheets.count() loop
      t_xxx := t_xxx || '
<vt:lpstr>' || workbook.sheets(s).name || '</vt:lpstr>';
    end loop;
    t_xxx := t_xxx || '</vt:vector>
</TitlesOfParts>
<LinksUpToDate>false</LinksUpToDate>
<SharedDoc>false</SharedDoc>
<HyperlinksChanged>false</HyperlinksChanged>
<AppVersion>14.0300</AppVersion>
</Properties>';
    add1xml(t_excel, 'docProps/app.xml', t_xxx);
    t_xxx := '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
<Relationship Id="rId3" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/extended-properties" Target="docProps/app.xml"/>
<Relationship Id="rId2" Type="http://schemas.openxmlformats.org/package/2006/relationships/metadata/core-properties" Target="docProps/core.xml"/>
<Relationship Id="rId1" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument" Target="xl/workbook.xml"/>
</Relationships>';
    add1xml(t_excel, '_rels/.rels', t_xxx);
    t_xxx := '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<styleSheet xmlns="http://schemas.openxmlformats.org/spreadsheetml/2006/main" xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006" mc:Ignorable="x14ac" xmlns:x14ac="http://schemas.microsoft.com/office/spreadsheetml/2009/9/ac">';
    if workbook.numFmts.count() > 0 then
      t_xxx := t_xxx || '<numFmts count="' || workbook.numFmts.count() || '">';
      for n in 1 .. workbook.numFmts.count() loop
        t_xxx := t_xxx || '<numFmt numFmtId="' || workbook.numFmts(n).numFmtId || '" formatCode="' || workbook.numFmts(n)
                .formatCode || '"/>';
      end loop;
      t_xxx := t_xxx || '</numFmts>';
    end if;
    t_xxx := t_xxx || '<fonts count="' || workbook.fonts.count() || '" x14ac:knownFonts="1">';
    for f in 0 .. workbook.fonts.count() - 1 loop
      t_xxx := t_xxx || '<font>' || case
                 when workbook.fonts(f).bold then
                  '<b/>'
               end || case
                 when workbook.fonts(f).italic then
                  '<i/>'
               end || case
                 when workbook.fonts(f).underline then
                  '<u/>'
               end || '<sz val="' ||
               to_char(workbook.fonts(f).fontsize, 'TM9', 'NLS_NUMERIC_CHARACTERS=.,') || '"/>
<color ' || case
                 when workbook.fonts(f).rgb is not null then
                  'rgb="' || workbook.fonts(f).rgb
                 else
                  'theme="' || workbook.fonts(f).theme
               end || '"/>
<name val="' || workbook.fonts(f).name || '"/>
<family val="' || workbook.fonts(f).family || '"/>
<scheme val="none"/>
</font>';
    end loop;
    t_xxx := t_xxx || '</fonts>
<fills count="' || workbook.fills.count() || '">';
    for f in 0 .. workbook.fills.count() - 1 loop
      t_xxx := t_xxx || '<fill><patternFill patternType="' || workbook.fills(f).patternType || '">' || case
                 when workbook.fills(f).fgRGB is not null then
                  '<fgColor rgb="' || workbook.fills(f).fgRGB || '"/>'
               end || '</patternFill></fill>';
    end loop;
    t_xxx := t_xxx || '</fills>
<borders count="' || workbook.borders.count() || '">';
    for b in 0 .. workbook.borders.count() - 1 loop
      t_xxx := t_xxx || '<border>' || case
                 when workbook.borders(b).left is null then
                  '<left/>'
                 else
                  '<left style="' || workbook.borders(b).left || '"/>'
               end || case
                 when workbook.borders(b).right is null then
                  '<right/>'
                 else
                  '<right style="' || workbook.borders(b).right || '"/>'
               end || case
                 when workbook.borders(b).top is null then
                  '<top/>'
                 else
                  '<top style="' || workbook.borders(b).top || '"/>'
               end || case
                 when workbook.borders(b).bottom is null then
                  '<bottom/>'
                 else
                  '<bottom style="' || workbook.borders(b).bottom || '"/>'
               end || '</border>';
    end loop;
    t_xxx := t_xxx || '</borders>
<cellStyleXfs count="1">
<xf numFmtId="0" fontId="0" fillId="0" borderId="0"/>
</cellStyleXfs>
<cellXfs count="' || (workbook.cellXfs.count() + 1) || '">
<xf numFmtId="0" fontId="0" fillId="0" borderId="0" xfId="0"/>';
    for x in 1 .. workbook.cellXfs.count() loop
      t_xxx := t_xxx || '<xf numFmtId="' || workbook.cellXfs(x).numFmtId || '" fontId="' || workbook.cellXfs(x)
              .fontId || '" fillId="' || workbook.cellXfs(x).fillId || '" borderId="' || workbook.cellXfs(x)
              .borderId || '">';
      if (workbook.cellXfs(x).alignment.horizontal is not null or workbook.cellXfs(x)
         .alignment.vertical is not null or workbook.cellXfs(x).alignment.wrapText) then
        t_xxx := t_xxx || '<alignment' || case
                   when workbook.cellXfs(x).alignment.horizontal is not null then
                    ' horizontal="' || workbook.cellXfs(x).alignment.horizontal || '"'
                 end || case
                   when workbook.cellXfs(x).alignment.vertical is not null then
                    ' vertical="' || workbook.cellXfs(x).alignment.vertical || '"'
                 end || case
                   when workbook.cellXfs(x).alignment.wrapText then
                    ' wrapText="true"'
                 end || '/>';
      end if;
      t_xxx := t_xxx || '</xf>';
    end loop;
    t_xxx := t_xxx || '</cellXfs>
<cellStyles count="1">
<cellStyle name="Normal" xfId="0" builtinId="0"/>
</cellStyles>
<dxfs count="0"/>
<tableStyles count="0" defaultTableStyle="TableStyleMedium2" defaultPivotStyle="PivotStyleLight16"/>
<extLst>
<ext uri="{EB79DEF2-80B8-43e5-95BD-54CBDDF9020C}" xmlns:x14="http://schemas.microsoft.com/office/spreadsheetml/2009/9/main">
<x14:slicerStyles defaultSlicerStyle="SlicerStyleLight1"/>
</ext>
</extLst>
</styleSheet>';
    add1xml(t_excel, 'xl/styles.xml', t_xxx);
    t_xxx := '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<workbook xmlns="http://schemas.openxmlformats.org/spreadsheetml/2006/main" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships">
<fileVersion appName="xl" lastEdited="5" lowestEdited="5" rupBuild="9302"/>
<workbookPr date1904="true" defaultThemeVersion="124226"/>
<bookViews>
<workbookView xWindow="120" yWindow="45" windowWidth="19155" windowHeight="4935"/>
</bookViews>
<sheets>';
    for s in 1 .. workbook.sheets.count() loop
      t_xxx := t_xxx || '
<sheet name="' || workbook.sheets(s).name || '" sheetId="' || s || '" r:id="rId' || (9 + s) ||
               '"/>';
    end loop;
    t_xxx := t_xxx || '</sheets>';
    if workbook.defined_names.count() > 0 then
      t_xxx := t_xxx || '<definedNames>';
      for s in 1 .. workbook.defined_names.count() loop
        t_xxx := t_xxx || '
<definedName name="' || workbook.defined_names(s).name || '"' || case
                   when workbook.defined_names(s).sheet is not null then
                    ' localSheetId="' || to_char(workbook.defined_names(s).sheet) || '"'
                 end || '>' || workbook.defined_names(s).ref || '</definedName>';
      end loop;
      t_xxx := t_xxx || '</definedNames>';
    end if;
    t_xxx := t_xxx || '<calcPr calcId="144525"/></workbook>';
    add1xml(t_excel, 'xl/workbook.xml', t_xxx);
    t_xxx := '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<a:theme xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main" name="Office Theme">
<a:themeElements>
<a:clrScheme name="Office">
<a:dk1>
<a:sysClr val="windowText" lastClr="000000"/>
</a:dk1>
<a:lt1>
<a:sysClr val="window" lastClr="FFFFFF"/>
</a:lt1>
<a:dk2>
<a:srgbClr val="1F497D"/>
</a:dk2>
<a:lt2>
<a:srgbClr val="EEECE1"/>
</a:lt2>
<a:accent1>
<a:srgbClr val="4F81BD"/>
</a:accent1>
<a:accent2>
<a:srgbClr val="C0504D"/>
</a:accent2>
<a:accent3>
<a:srgbClr val="9BBB59"/>
</a:accent3>
<a:accent4>
<a:srgbClr val="8064A2"/>
</a:accent4>
<a:accent5>
<a:srgbClr val="4BACC6"/>
</a:accent5>
<a:accent6>
<a:srgbClr val="F79646"/>
</a:accent6>
<a:hlink>
<a:srgbClr val="0000FF"/>
</a:hlink>
<a:folHlink>
<a:srgbClr val="800080"/>
</a:folHlink>
</a:clrScheme>
<a:fontScheme name="Office">
<a:majorFont>
<a:latin typeface="Cambria"/>
<a:ea typeface=""/>
<a:cs typeface=""/>
<a:font script="Jpan" typeface="MS P????"/>
<a:font script="Hang" typeface="?? ??"/>
<a:font script="Hans" typeface="??"/>
<a:font script="Hant" typeface="????"/>
<a:font script="Arab" typeface="Times New Roman"/>
<a:font script="Hebr" typeface="Times New Roman"/>
<a:font script="Thai" typeface="Tahoma"/>
<a:font script="Ethi" typeface="Nyala"/>
<a:font script="Beng" typeface="Vrinda"/>
<a:font script="Gujr" typeface="Shruti"/>
<a:font script="Khmr" typeface="MoolBoran"/>
<a:font script="Knda" typeface="Tunga"/>
<a:font script="Guru" typeface="Raavi"/>
<a:font script="Cans" typeface="Euphemia"/>
<a:font script="Cher" typeface="Plantagenet Cherokee"/>
<a:font script="Yiii" typeface="Microsoft Yi Baiti"/>
<a:font script="Tibt" typeface="Microsoft Himalaya"/>
<a:font script="Thaa" typeface="MV Boli"/>
<a:font script="Deva" typeface="Mangal"/>
<a:font script="Telu" typeface="Gautami"/>
<a:font script="Taml" typeface="Latha"/>
<a:font script="Syrc" typeface="Estrangelo Edessa"/>
<a:font script="Orya" typeface="Kalinga"/>
<a:font script="Mlym" typeface="Kartika"/>
<a:font script="Laoo" typeface="DokChampa"/>
<a:font script="Sinh" typeface="Iskoola Pota"/>
<a:font script="Mong" typeface="Mongolian Baiti"/>
<a:font script="Viet" typeface="Times New Roman"/>
<a:font script="Uigh" typeface="Microsoft Uighur"/>
<a:font script="Geor" typeface="Sylfaen"/>
</a:majorFont>
<a:minorFont>
<a:latin typeface="Calibri"/>
<a:ea typeface=""/>
<a:cs typeface=""/>
<a:font script="Jpan" typeface="MS P????"/>
<a:font script="Hang" typeface="?? ??"/>
<a:font script="Hans" typeface="??"/>
<a:font script="Hant" typeface="????"/>
<a:font script="Arab" typeface="Arial"/>
<a:font script="Hebr" typeface="Arial"/>
<a:font script="Thai" typeface="Tahoma"/>
<a:font script="Ethi" typeface="Nyala"/>
<a:font script="Beng" typeface="Vrinda"/>
<a:font script="Gujr" typeface="Shruti"/>
<a:font script="Khmr" typeface="DaunPenh"/>
<a:font script="Knda" typeface="Tunga"/>
<a:font script="Guru" typeface="Raavi"/>
<a:font script="Cans" typeface="Euphemia"/>
<a:font script="Cher" typeface="Plantagenet Cherokee"/>
<a:font script="Yiii" typeface="Microsoft Yi Baiti"/>
<a:font script="Tibt" typeface="Microsoft Himalaya"/>
<a:font script="Thaa" typeface="MV Boli"/>
<a:font script="Deva" typeface="Mangal"/>
<a:font script="Telu" typeface="Gautami"/>
<a:font script="Taml" typeface="Latha"/>
<a:font script="Syrc" typeface="Estrangelo Edessa"/>
<a:font script="Orya" typeface="Kalinga"/>
<a:font script="Mlym" typeface="Kartika"/>
<a:font script="Laoo" typeface="DokChampa"/>
<a:font script="Sinh" typeface="Iskoola Pota"/>
<a:font script="Mong" typeface="Mongolian Baiti"/>
<a:font script="Viet" typeface="Arial"/>
<a:font script="Uigh" typeface="Microsoft Uighur"/>
<a:font script="Geor" typeface="Sylfaen"/>
</a:minorFont>
</a:fontScheme>
<a:fmtScheme name="Office">
<a:fillStyleLst>
<a:solidFill>
<a:schemeClr val="phClr"/>
</a:solidFill>
<a:gradFill rotWithShape="1">
<a:gsLst>
<a:gs pos="0">
<a:schemeClr val="phClr">
<a:tint val="50000"/>
<a:satMod val="300000"/>
</a:schemeClr>
</a:gs>
<a:gs pos="35000">
<a:schemeClr val="phClr">
<a:tint val="37000"/>
<a:satMod val="300000"/>
</a:schemeClr>
</a:gs>
<a:gs pos="100000">
<a:schemeClr val="phClr">
<a:tint val="15000"/>
<a:satMod val="350000"/>
</a:schemeClr>
</a:gs>
</a:gsLst>
<a:lin ang="16200000" scaled="1"/>
</a:gradFill>
<a:gradFill rotWithShape="1">
<a:gsLst>
<a:gs pos="0">
<a:schemeClr val="phClr">
<a:shade val="51000"/>
<a:satMod val="130000"/>
</a:schemeClr>
</a:gs>
<a:gs pos="80000">
<a:schemeClr val="phClr">
<a:shade val="93000"/>
<a:satMod val="130000"/>
</a:schemeClr>
</a:gs>
<a:gs pos="100000">
<a:schemeClr val="phClr">
<a:shade val="94000"/>
<a:satMod val="135000"/>
</a:schemeClr>
</a:gs>
</a:gsLst>
<a:lin ang="16200000" scaled="0"/>
</a:gradFill>
</a:fillStyleLst>
<a:lnStyleLst>
<a:ln w="9525" cap="flat" cmpd="sng" algn="ctr">
<a:solidFill>
<a:schemeClr val="phClr">
<a:shade val="95000"/>
<a:satMod val="105000"/>
</a:schemeClr>
</a:solidFill>
<a:prstDash val="solid"/>
</a:ln>
<a:ln w="25400" cap="flat" cmpd="sng" algn="ctr">
<a:solidFill>
<a:schemeClr val="phClr"/>
</a:solidFill>
<a:prstDash val="solid"/>
</a:ln>
<a:ln w="38100" cap="flat" cmpd="sng" algn="ctr">
<a:solidFill>
<a:schemeClr val="phClr"/>
</a:solidFill>
<a:prstDash val="solid"/>
</a:ln>
</a:lnStyleLst>
<a:effectStyleLst>
<a:effectStyle>
<a:effectLst>
<a:outerShdw blurRad="40000" dist="20000" dir="5400000" rotWithShape="0">
<a:srgbClr val="000000">
<a:alpha val="38000"/>
</a:srgbClr>
</a:outerShdw>
</a:effectLst>
</a:effectStyle>
<a:effectStyle>
<a:effectLst>
<a:outerShdw blurRad="40000" dist="23000" dir="5400000" rotWithShape="0">
<a:srgbClr val="000000">
<a:alpha val="35000"/>
</a:srgbClr>
</a:outerShdw>
</a:effectLst>
</a:effectStyle>
<a:effectStyle>
<a:effectLst>
<a:outerShdw blurRad="40000" dist="23000" dir="5400000" rotWithShape="0">
<a:srgbClr val="000000">
<a:alpha val="35000"/>
</a:srgbClr>
</a:outerShdw>
</a:effectLst>
<a:scene3d>
<a:camera prst="orthographicFront">
<a:rot lat="0" lon="0" rev="0"/>
</a:camera>
<a:lightRig rig="threePt" dir="t">
<a:rot lat="0" lon="0" rev="1200000"/>
</a:lightRig>
</a:scene3d>
<a:sp3d>
<a:bevelT w="63500" h="25400"/>
</a:sp3d>
</a:effectStyle>
</a:effectStyleLst>
<a:bgFillStyleLst>
<a:solidFill>
<a:schemeClr val="phClr"/>
</a:solidFill>
<a:gradFill rotWithShape="1">
<a:gsLst>
<a:gs pos="0">
<a:schemeClr val="phClr">
<a:tint val="40000"/>
<a:satMod val="350000"/>
</a:schemeClr>
</a:gs>
<a:gs pos="40000">
<a:schemeClr val="phClr">
<a:tint val="45000"/>
<a:shade val="99000"/>
<a:satMod val="350000"/>
</a:schemeClr>
</a:gs>
<a:gs pos="100000">
<a:schemeClr val="phClr">
<a:shade val="20000"/>
<a:satMod val="255000"/>
</a:schemeClr>
</a:gs>
</a:gsLst>
<a:path path="circle">
<a:fillToRect l="50000" t="-80000" r="50000" b="180000"/>
</a:path>
</a:gradFill>
<a:gradFill rotWithShape="1">
<a:gsLst>
<a:gs pos="0">
<a:schemeClr val="phClr">
<a:tint val="80000"/>
<a:satMod val="300000"/>
</a:schemeClr>
</a:gs>
<a:gs pos="100000">
<a:schemeClr val="phClr">
<a:shade val="30000"/>
<a:satMod val="200000"/>
</a:schemeClr>
</a:gs>
</a:gsLst>
<a:path path="circle">
<a:fillToRect l="50000" t="50000" r="50000" b="50000"/>
</a:path>
</a:gradFill>
</a:bgFillStyleLst>
</a:fmtScheme>
</a:themeElements>
<a:objectDefaults/>
<a:extraClrSchemeLst/>
</a:theme>';
    add1xml(t_excel, 'xl/theme/theme1.xml', t_xxx);
    for s in 1 .. workbook.sheets.count() loop
      t_col_min := 16384;
      t_col_max := 1;
      t_row_ind := workbook.sheets(s).rows.first();
      while t_row_ind is not null loop
        t_col_min := least(t_col_min, workbook.sheets(s).rows(t_row_ind).first());
        t_col_max := greatest(t_col_max, workbook.sheets(s).rows(t_row_ind).last());
        t_row_ind := workbook.sheets(s).rows.next(t_row_ind);
      end loop;
      t_xxx := '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<worksheet xmlns="http://schemas.openxmlformats.org/spreadsheetml/2006/main" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships" xmlns:xdr="http://schemas.openxmlformats.org/drawingml/2006/spreadsheetDrawing" xmlns:x14="http://schemas.microsoft.com/office/spreadsheetml/2009/9/main" xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006" mc:Ignorable="x14ac" xmlns:x14ac="http://schemas.microsoft.com/office/spreadsheetml/2009/9/ac">
<dimension ref="' || alfan_col(t_col_min) || workbook.sheets(s).rows.first() || ':' ||
               alfan_col(t_col_max) || workbook.sheets(s).rows.last() || '"/>
<sheetViews>
<sheetView' || case
                 when s = 1 then
                  ' tabSelected="1"'
               end || ' workbookViewId="0">';
      if workbook.sheets(s).freeze_rows > 0 and workbook.sheets(s).freeze_cols > 0 then
        t_xxx := t_xxx ||
                 ('<pane xSplit="' || workbook.sheets(s).freeze_cols || '" ' || 'ySplit="' || workbook.sheets(s)
                 .freeze_rows || '" ' || 'topLeftCell="' || alfan_col(workbook.sheets(s).freeze_cols + 1) ||
                 (workbook.sheets(s).freeze_rows + 1) || '" ' ||
                 'activePane="bottomLeft" state="frozen"/>');
      else
        if workbook.sheets(s).freeze_rows > 0 then
          t_xxx := t_xxx || '<pane ySplit="' || workbook.sheets(s).freeze_rows || '" topLeftCell="A' ||
                   (workbook.sheets(s).freeze_rows + 1) || '" activePane="bottomLeft" state="frozen"/>';
        end if;
        if workbook.sheets(s).freeze_cols > 0 then
          t_xxx := t_xxx || '<pane xSplit="' || workbook.sheets(s).freeze_cols || '" topLeftCell="' ||
                   alfan_col(workbook.sheets(s).freeze_cols + 1) ||
                   '1" activePane="bottomLeft" state="frozen"/>';
        end if;
      end if;
      t_xxx := t_xxx || '</sheetView>
</sheetViews>
<sheetFormatPr defaultRowHeight="15" x14ac:dyDescent="0.25"/>';
      if workbook.sheets(s).widths.count() > 0 then
        t_xxx     := t_xxx || '<cols>';
        t_col_ind := workbook.sheets(s).widths.first();
        while t_col_ind is not null loop
          t_xxx     := t_xxx || '<col min="' || t_col_ind || '" max="' || t_col_ind || '" width="' ||
                       to_char(workbook.sheets(s).widths(t_col_ind), 'TM9', 'NLS_NUMERIC_CHARACTERS=.,') ||
                       '" customWidth="1"/>';
          t_col_ind := workbook.sheets(s).widths.next(t_col_ind);
        end loop;
        t_xxx := t_xxx || '</cols>';
      end if;
      t_xxx     := t_xxx || '<sheetData>';
      t_row_ind := workbook.sheets(s).rows.first();
      t_tmp     := null;
      while t_row_ind is not null loop
        /* row height */
        if (workbook.sheets(s).heights.exists(t_row_ind) and workbook.sheets(s).heights(t_row_ind) > 0) then
           t_row_height := 'ht="'||to_char(workbook.sheets(s).heights(t_row_ind))||'" customHeight="1"';
        else 
          t_row_height :='';
        end if;
        t_tmp     := t_tmp || '<row r="' || t_row_ind || '" spans="' || t_col_min || ':' || t_col_max || '" '||t_row_height||'>';
        t_len     := length(t_tmp);
        t_col_ind := workbook.sheets(s).rows(t_row_ind).first();
        while t_col_ind is not null loop
          t_cell := '<c r="' || alfan_col(t_col_ind) || t_row_ind || '"' || ' ' || workbook.sheets(s)
                   .rows(t_row_ind)(t_col_ind)
                   .style || '><v>' || to_char(workbook.sheets(s).rows(t_row_ind)(t_col_ind).value,
                                               'TM9',
                                               'NLS_NUMERIC_CHARACTERS=.,') || '</v></c>';
          if t_len > 32000 then
            dbms_lob.writeappend(t_xxx, t_len, t_tmp);
            t_tmp := null;
            t_len := 0;
          end if;
          t_tmp     := t_tmp || t_cell;
          t_len     := t_len + length(t_cell);
          t_col_ind := workbook.sheets(s).rows(t_row_ind).next(t_col_ind);
        end loop;
        t_tmp     := t_tmp || '</row>';
        t_row_ind := workbook.sheets(s).rows.next(t_row_ind);
      end loop;
      t_tmp := t_tmp || '</sheetData>';
      t_len := length(t_tmp);
      dbms_lob.writeappend(t_xxx, t_len, t_tmp);
      for a in 1 .. workbook.sheets(s).autofilters.count() loop
        t_xxx := t_xxx || '<autoFilter ref="' ||
                 alfan_col(nvl(workbook.sheets(s).autofilters(a).column_start, t_col_min)) ||
                 nvl(workbook.sheets(s).autofilters(a).row_start, workbook.sheets(s).rows.first()) || ':' ||
                 alfan_col(coalesce(workbook.sheets(s).autofilters(a).column_end,
                                    workbook.sheets(s).autofilters(a).column_start,
                                    t_col_max)) ||
                 nvl(workbook.sheets(s).autofilters(a).row_end, workbook.sheets(s).rows.last()) || '"/>';
      end loop;
      if workbook.sheets(s).mergecells.count() > 0 then
        t_xxx := t_xxx || '<mergeCells count="' || to_char(workbook.sheets(s).mergecells.count()) || '">';
        for m in 1 .. workbook.sheets(s).mergecells.count() loop
          t_xxx := t_xxx || '<mergeCell ref="' || workbook.sheets(s).mergecells(m) || '"/>';
        end loop;
        t_xxx := t_xxx || '</mergeCells>';
      end if;
      --
      if workbook.sheets(s).validations.count() > 0 then
        t_xxx := t_xxx || '<dataValidations count="' || to_char(workbook.sheets(s).validations.count()) || '">';
        for m in 1 .. workbook.sheets(s).validations.count() loop
          t_xxx := t_xxx || '<dataValidation' || ' type="' || workbook.sheets(s).validations(m).type || '"' ||
                   ' errorStyle="' || workbook.sheets(s).validations(m).errorstyle || '"' ||
                   ' allowBlank="' || case
                     when nvl(workbook.sheets(s).validations(m).allowBlank, true) then
                      '1'
                     else
                      '0'
                   end || '"' || ' sqref="' || workbook.sheets(s).validations(m).sqref || '"';
          if workbook.sheets(s).validations(m).prompt is not null then
            t_xxx := t_xxx || ' showInputMessage="1" prompt="' || workbook.sheets(s).validations(m)
                    .prompt || '"';
            if workbook.sheets(s).validations(m).title is not null then
              t_xxx := t_xxx || ' promptTitle="' || workbook.sheets(s).validations(m).title || '"';
            end if;
          end if;
          if workbook.sheets(s).validations(m).showerrormessage then
            t_xxx := t_xxx || ' showErrorMessage="1"';
            if workbook.sheets(s).validations(m).error_title is not null then
              t_xxx := t_xxx || ' errorTitle="' || workbook.sheets(s).validations(m).error_title || '"';
            end if;
            if workbook.sheets(s).validations(m).error_txt is not null then
              t_xxx := t_xxx || ' error="' || workbook.sheets(s).validations(m).error_txt || '"';
            end if;
          end if;
          t_xxx := t_xxx || '>';
          if workbook.sheets(s).validations(m).formula1 is not null then
            t_xxx := t_xxx || '<formula1>' || workbook.sheets(s).validations(m).formula1 || '</formula1>';
          end if;
          if workbook.sheets(s).validations(m).formula2 is not null then
            t_xxx := t_xxx || '<formula2>' || workbook.sheets(s).validations(m).formula2 || '</formula2>';
          end if;
          t_xxx := t_xxx || '</dataValidation>';
        end loop;
        t_xxx := t_xxx || '</dataValidations>';
      end if;
      --
      if workbook.sheets(s).hyperlinks.count() > 0 then
        t_xxx := t_xxx || '<hyperlinks>';
        for h in 1 .. workbook.sheets(s).hyperlinks.count() loop
          if(workbook.sheets(s).hyperlinks(h).location is not null) then
            t_xxx := t_xxx || '<hyperlink ref="' || workbook.sheets(s).hyperlinks(h).cell || '" location="' || workbook.sheets(s).hyperlinks(h).location || '"/>';
          else
            t_xxx := t_xxx || '<hyperlink ref="' || workbook.sheets(s).hyperlinks(h).cell || '" r:id="rId' || h || '"/>';
          end if;
        end loop;
        t_xxx := t_xxx || '</hyperlinks>';
      end if;
      t_xxx := t_xxx ||
               '<pageMargins left="0.7" right="0.7" top="0.75" bottom="0.75" header="0.3" footer="0.3"/>';
      if workbook.sheets(s).comments.count() > 0 then
        t_xxx := t_xxx || '<legacyDrawing r:id="rId' || (workbook.sheets(s).hyperlinks.count() + 1) ||
                 '"/>';
      end if;
      --
      t_xxx := t_xxx || '</worksheet>';
      add1xml(t_excel, 'xl/worksheets/sheet' || s || '.xml', t_xxx);
      if workbook.sheets(s).hyperlinks.count() > 0 or workbook.sheets(s).comments.count() > 0 then
        t_xxx := '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">';
        if workbook.sheets(s).comments.count() > 0 then
          t_xxx := t_xxx || '<Relationship Id="rId' || (workbook.sheets(s).hyperlinks.count() + 2) ||
                   '" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/comments" Target="../comments' || s ||
                   '.xml"/>';
          t_xxx := t_xxx || '<Relationship Id="rId' || (workbook.sheets(s).hyperlinks.count() + 1) ||
                   '" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/vmlDrawing" Target="../drawings/vmlDrawing' || s ||
                   '.vml"/>';
        end if;
        for h in 1 .. workbook.sheets(s).hyperlinks.count() loop
          t_xxx := t_xxx || '<Relationship Id="rId' || h ||
                   '" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/hyperlink" Target="' || workbook.sheets(s).hyperlinks(h).url ||
                   '" TargetMode="External"/>';
        end loop;
        t_xxx := t_xxx || '</Relationships>';
        add1xml(t_excel, 'xl/worksheets/_rels/sheet' || s || '.xml.rels', t_xxx);
      end if;
      --
      if workbook.sheets(s).comments.count() > 0 then
        declare
          cnt        pls_integer;
          author_ind tp_author;
          --          t_col_ind := workbook.sheets( s ).widths.next( t_col_ind );
        begin
          authors.delete();
          for c in 1 .. workbook.sheets(s).comments.count() loop
            authors(workbook.sheets(s).comments(c).author) := 0;
          end loop;
          t_xxx      := '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<comments xmlns="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
<authors>';
          cnt        := 0;
          author_ind := authors.first();
          while author_ind is not null or authors.next(author_ind) is not null loop
            authors(author_ind) := cnt;
            t_xxx := t_xxx || '<author>' || author_ind || '</author>';
            cnt := cnt + 1;
            author_ind := authors.next(author_ind);
          end loop;
        end;
        t_xxx := t_xxx || '</authors><commentList>';
        for c in 1 .. workbook.sheets(s).comments.count() loop
          t_xxx := t_xxx || '<comment ref="' || alfan_col(workbook.sheets(s).comments(c).column) ||
                   to_char(workbook.sheets(s).comments(c)
                           .row || '" authorId="' || authors(workbook.sheets(s).comments(c).author)) || '">
<text>';
          if workbook.sheets(s).comments(c).author is not null then
            t_xxx := t_xxx ||
                     '<r><rPr><b/><sz val="9"/><color indexed="81"/><rFont val="Tahoma"/><charset val="1"/></rPr><t xml:space="preserve">' || workbook.sheets(s).comments(c)
                    .author || ':</t></r>';
          end if;
          t_xxx := t_xxx ||
                   '<r><rPr><sz val="9"/><color indexed="81"/><rFont val="Tahoma"/><charset val="1"/></rPr><t xml:space="preserve">' || case
                     when workbook.sheets(s).comments(c).author is not null then
                      '
'
                   end || workbook.sheets(s).comments(c).text || '</t></r></text></comment>';
        end loop;
        t_xxx := t_xxx || '</commentList></comments>';
        add1xml(t_excel, 'xl/comments' || s || '.xml', t_xxx);
        t_xxx := '<xml xmlns:v="urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel">
<o:shapelayout v:ext="edit"><o:idmap v:ext="edit" data="2"/></o:shapelayout>
<v:shapetype id="_x0000_t202" coordsize="21600,21600" o:spt="202" path="m,l,21600r21600,l21600,xe"><v:stroke joinstyle="miter"/><v:path gradientshapeok="t" o:connecttype="rect"/></v:shapetype>';
        for c in 1 .. workbook.sheets(s).comments.count() loop
          t_xxx := t_xxx || '<v:shape id="_x0000_s' || to_char(c) ||
                   '" type="#_x0000_t202"
style="position:absolute;margin-left:35.25pt;margin-top:3pt;z-index:' || to_char(c) ||
                   ';visibility:hidden;" fillcolor="#ffffe1" o:insetmode="auto">
<v:fill color2="#ffffe1"/><v:shadow on="t" color="black" obscured="t"/><v:path o:connecttype="none"/>
<v:textbox style="mso-direction-alt:auto"><div style="text-align:left"></div></v:textbox>
<x:ClientData ObjectType="Note"><x:MoveWithCells/><x:SizeWithCells/>';
          t_w   := workbook.sheets(s).comments(c).width;
          t_c   := 1;
          loop
            if workbook.sheets(s).widths.exists(workbook.sheets(s).comments(c).column + t_c) then
              t_cw := 256 * workbook.sheets(s).widths(workbook.sheets(s).comments(c).column + t_c);
              t_cw := trunc((t_cw + 18) / 256 * 7); -- assume default 11 point Calibri
            else
              t_cw := 64;
            end if;
            exit when t_w < t_cw;
            t_c := t_c + 1;
            t_w := t_w - t_cw;
          end loop;
          t_h   := workbook.sheets(s).comments(c).height;
          t_xxx := t_xxx || to_char('<x:Anchor>' || workbook.sheets(s).comments(c).column || ',15,' || workbook.sheets(s).comments(c).row ||
                                    ',30,' || (workbook.sheets(s).comments(c).column + t_c - 1) || ',' ||
                                    round(t_w) || ',' ||
                                    (workbook.sheets(s).comments(c).row + 1 + trunc(t_h / 20)) || ',' ||
                                    mod(t_h, 20) || '</x:Anchor>');
          t_xxx := t_xxx || to_char('<x:AutoFill>False</x:AutoFill><x:Row>' ||
                                    (workbook.sheets(s).comments(c).row - 1) || '</x:Row><x:Column>' ||
                                    (workbook.sheets(s).comments(c).column - 1) ||
                                    '</x:Column></x:ClientData></v:shape>');
        end loop;
        t_xxx := t_xxx || '</xml>';
        add1xml(t_excel, 'xl/drawings/vmlDrawing' || s || '.vml', t_xxx);
      end if;
      --
    end loop;
    t_xxx := '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
<Relationship Id="rId1" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/sharedStrings" Target="sharedStrings.xml"/>
<Relationship Id="rId2" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/styles" Target="styles.xml"/>
<Relationship Id="rId3" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/theme" Target="theme/theme1.xml"/>';
    for s in 1 .. workbook.sheets.count() loop
      t_xxx := t_xxx || '
<Relationship Id="rId' || (9 + s) ||
               '" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/worksheet" Target="worksheets/sheet' || s ||
               '.xml"/>';
    end loop;
    t_xxx := t_xxx || '</Relationships>';
    add1xml(t_excel, 'xl/_rels/workbook.xml.rels', t_xxx);
    t_xxx := '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<sst xmlns="http://schemas.openxmlformats.org/spreadsheetml/2006/main" count="' ||
             workbook.str_cnt || '" uniqueCount="' || workbook.strings.count() || '">';
    t_tmp := null;
    for i in 0 .. workbook.str_ind.count() - 1 loop
      /* if left space */
      if(substr(workbook.str_ind(i), 1, 1) = CHR(32)) then
        t_str := '<si><t xml:space="preserve">';                             
      else
        t_str := '<si><t>';
      end if;
      t_str := t_str || dbms_xmlgen.convert(substr(workbook.str_ind(i), 1, 32000)) || '</t></si>';
      if length(t_tmp) + length(t_str) > 32000 then
        t_xxx := t_xxx || t_tmp;
        t_tmp := null;
      end if;
      t_tmp := t_tmp || t_str;
    end loop;
    t_xxx := t_xxx || t_tmp || '</sst>';
    add1xml(t_excel, 'xl/sharedStrings.xml', t_xxx);
    finish_zip(t_excel);
    clear_workbook;
    return t_excel;
  end;
  --
  procedure save(p_directory varchar2, p_filename varchar2) is
  begin
    blob2file(finish, p_directory, p_filename);
  end;
  --
  procedure query2sheet(p_sql            varchar2,
                        p_column_headers boolean := true,
                        p_directory      varchar2 := null,
                        p_filename       varchar2 := null,
                        p_sheet          pls_integer := null) is
    t_sheet     pls_integer;
    t_c         integer;
    t_col_cnt   integer;
    t_desc_tab  dbms_sql.desc_tab2;
    d_tab       dbms_sql.date_table;
    n_tab       dbms_sql.number_table;
    v_tab       dbms_sql.varchar2_table;
    t_bulk_size pls_integer := 200;
    t_r         integer;
    t_cur_row   pls_integer;
  begin
    if p_sheet is null then
      new_sheet;
    end if;
    t_c := dbms_sql.open_cursor;
    dbms_sql.parse(t_c, p_sql, dbms_sql.native);
    dbms_sql.describe_columns2(t_c, t_col_cnt, t_desc_tab);
    for c in 1 .. t_col_cnt loop
      if p_column_headers then
        cell(c, 1, t_desc_tab(c).col_name, p_sheet => t_sheet);
      end if;
      --      dbms_output.put_line( t_desc_tab( c ).col_name || ' ' || t_desc_tab( c ).col_type );
      case
        when t_desc_tab(c).col_type in (2, 100, 101) then
          dbms_sql.define_array(t_c, c, n_tab, t_bulk_size, 1);
        when t_desc_tab(c).col_type in (12, 178, 179, 180, 181, 231) then
          dbms_sql.define_array(t_c, c, d_tab, t_bulk_size, 1);
        when t_desc_tab(c).col_type in (1, 8, 9, 96, 112) then
          dbms_sql.define_array(t_c, c, v_tab, t_bulk_size, 1);
        else
          null;
      end case;
    end loop;
    --
    t_cur_row := case
                   when p_column_headers then
                    2
                   else
                    1
                 end;
    t_sheet   := nvl(p_sheet, workbook.sheets.count());
    --
    t_r := dbms_sql.execute(t_c);
    loop
      t_r := dbms_sql.fetch_rows(t_c);
      if t_r > 0 then
        for c in 1 .. t_col_cnt loop
          case
            when t_desc_tab(c).col_type in (2, 100, 101) then
              dbms_sql.column_value(t_c, c, n_tab);
              for i in 0 .. t_r - 1 loop
                if n_tab(i + n_tab.first()) is not null then
                  cell(c, t_cur_row + i, n_tab(i + n_tab.first()), p_sheet => t_sheet);
                end if;
              end loop;
              n_tab.delete;
            when t_desc_tab(c).col_type in (12, 178, 179, 180, 181, 231) then
              dbms_sql.column_value(t_c, c, d_tab);
              for i in 0 .. t_r - 1 loop
                if d_tab(i + d_tab.first()) is not null then
                  cell(c, t_cur_row + i, d_tab(i + d_tab.first()), p_sheet => t_sheet);
                end if;
              end loop;
              d_tab.delete;
            when t_desc_tab(c).col_type in (1, 8, 9, 96, 112) then
              dbms_sql.column_value(t_c, c, v_tab);
              for i in 0 .. t_r - 1 loop
                if v_tab(i + v_tab.first()) is not null then
                  cell(c, t_cur_row + i, v_tab(i + v_tab.first()), p_sheet => t_sheet);
                end if;
              end loop;
              v_tab.delete;
            else
              null;
          end case;
        end loop;
      end if;
      exit when t_r != t_bulk_size;
      t_cur_row := t_cur_row + t_r;
    end loop;
    dbms_sql.close_cursor(t_c);
    if (p_directory is not null and p_filename is not null) then
      save(p_directory, p_filename);
    end if;
  exception
    when others then
      if dbms_sql.is_open(t_c) then
        dbms_sql.close_cursor(t_c);
      end if;
  end;
end;

/
--------------------------------------------------------
--  DDL for Package Body CURS_PKG
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE PACKAGE BODY "CURS_PKG" is
cursor cur_c is
   select deptno from emp_temp;
procedure open is
Begin
 null;
end open;
procedure next(p_n number := 1)is
Begin
 null;
end next;
procedure close is
Begin
 null;
end close;
end curs_pkg;

/
--------------------------------------------------------
--  DDL for Package Body FM_MIG3
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE PACKAGE BODY "FM_MIG3" 
AS
  --
  -- CONVERSAO XML PARA OBJETO DO TIPO FM_MAP_SS
  FUNCTION LOAD_XML_TO_MAP_SS(
      dir         VARCHAR2,
      xmlFileName VARCHAR2)
    RETURN fm_map_ss
  AS
    xmlClob CLOB;
    xmlFile BFILE;
    x XMLType;
    src_offset  NUMBER := 1 ;
    dest_offset NUMBER := 1 ;
    lang_ctx    NUMBER := DBMS_LOB.DEFAULT_LANG_CTX;
    warning     INTEGER;
    --
    map_ss FM_MAP_SS := fm_map_ss();
  BEGIN
    -- carrega xml
    xmlFile := BFILENAME(dir, xmlFileName);
    DBMS_LOB.CREATETEMPORARY(xmlClob, true);
    DBMS_LOB.FILEOPEN(xmlFile, DBMS_LOB.FILE_READONLY);
    DBMS_LOB.LOADCLOBFROMFILE(xmlClob, xmlFile, DBMS_LOB.LOBMAXSIZE, src_offset, dest_offset, DBMS_LOB.DEFAULT_CSID, lang_ctx, warning);
    x := XMLType.createXML(xmlClob);
    DBMS_LOB.FILECLOSEALL();
    DBMS_LOB.FREETEMPORARY(xmlClob);
    -- convert xml
    x.toobject(map_ss);
    RETURN map_ss;
  END;
END FM_MIG3;


/
--------------------------------------------------------
--  DDL for Package Body FM_PREMIG
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE PACKAGE BODY "FM_PREMIG" 
AS
  --
  --
  PROCEDURE DISABLE_CONSTRAINTS
  AS
    tipos_c lista2 := lista2('V', 'C', 'R', 'U', 'P');
    stmts lista2;
  BEGIN
    --
    GUARDA_CSV_DISABLED_CONS;
    --
    BEGIN
      -- esta query para alem das constraints do utilizador vai tambem selecionar
      -- as constraints de outros schemas que sao dependentes de constraints deste utilizador (schema)
      SELECT 'ALTER TABLE '
        ||OWNER
        ||'.'
        ||TABLE_NAME
        ||' MODIFY CONSTRAINT '
        ||CONSTRAINT_NAME
        ||' DISABLE' bulk collect
      INTO stmts
      FROM ALL_CONSTRAINTS
      WHERE R_OWNER      =USER
      AND OWNER         != USER
      AND CONSTRAINT_TYPE='R';
    EXCEPTION
    WHEN NO_DATA_FOUND THEN
      dbms_output.put_line('DEPENDENTES');
      dbms_output.put_line(SQLERRM);
    END;
    --
    IF stmts.count > 0 THEN
      FOR s IN stmts.first .. stmts.last
      LOOP
        NULL;
        BEGIN
          -- dbms_output.put_line(stmts(s));
          EXECUTE IMMEDIATE stmts(s);
        EXCEPTION
        WHEN OTHERS THEN
          dbms_output.put_line('DEPENDENTES' || chr(10) || stmts(s) || chr(10)|| SQLERRM || chr(10)||dbms_utility.format_error_backtrace);
        END;
      END LOOP;
    END IF;
    --
    -- CONSTRAINTS DO SGD_CHL (USER)
    --
    FOR t IN tipos_c.first .. tipos_c.last
    LOOP
      BEGIN
        SELECT 'ALTER TABLE '
          ||OWNER
          ||'.'
          ||TABLE_NAME
          ||' MODIFY CONSTRAINT '
          ||CONSTRAINT_NAME
          ||' DISABLE' bulk collect
        INTO stmts
        FROM ALL_CONSTRAINTS
        WHERE OWNER        =USER
        AND CONSTRAINT_TYPE=tipos_c(t);
      EXCEPTION
      WHEN NO_DATA_FOUND THEN
        dbms_output.put_line(tipos_c(t));
        dbms_output.put_line(SQLERRM);
      END;
      --
      IF stmts.count > 0 THEN
        FOR s IN stmts.first .. stmts.last
        LOOP
          NULL;
          BEGIN
            -- dbms_output.put_line(stmts(s));
            EXECUTE IMMEDIATE stmts(s);
          EXCEPTION
          WHEN OTHERS THEN
            dbms_output.put_line(tipos_c(t) || chr(10) || stmts(s) || chr(10)|| SQLERRM || chr(10)||dbms_utility.format_error_backtrace);
          END;
        END LOOP;
      END IF;
    END LOOP;
  END;
--
--
  PROCEDURE GUARDA_CSV_DISABLED_CONS
  AS
    fhandle utl_file.file_type;
    disabled_cons lista2;
  BEGIN
    SELECT constraint_name bulk collect
    INTO disabled_cons
    FROM all_constraints
    WHERE status           ='DISABLED'
    AND owner              =USER;
    IF disabled_cons.count > 0 THEN
      fhandle             := utl_file.fopen(FM_TC.mainDir, 'DISABLED_CONSTRAINTS_'|| USER || '.csv', 'W');
      FOR i IN disabled_cons.first .. disabled_cons.last
      LOOP
        utl_file.put_line(fhandle, disabled_cons(i));
      END LOOP;
      utl_file.fclose(fhandle);
    END IF;
  END;
--
--
  PROCEDURE DISABLE_TRIGGERS
  AS
  BEGIN
    GUARDA_CSV_DISABLED_TRGS;
    --
    FOR r IN
    ( SELECT trigger_name FROM all_triggers WHERE owner=USER
    )
    LOOP
      dbms_utility.exec_ddl_statement('alter trigger ' || r.trigger_name || ' DISABLE');
    END LOOP;
  END;
--
--
  PROCEDURE GUARDA_CSV_DISABLED_TRGS
  AS
    fhandle utl_file.file_type;
    disabled_trgs lista2 ;
  BEGIN
    SELECT trigger_name bulk collect
    INTO disabled_trgs
    FROM all_triggers
    WHERE owner            =USER
    AND status             ='DISABLED';
    IF disabled_trgs.count > 0 THEN
      fhandle             := utl_file.fopen(FM_TC.mainDir, 'DISABLED_TRIGGERS_'|| USER || '.csv', 'W');
      FOR i IN disabled_trgs.first .. disabled_trgs.last
      LOOP
        utl_file.put_line(fhandle, disabled_trgs(i));
      END LOOP;
      utl_file.fclose(fhandle);
    END IF;
  END;
--
--
-- inactivo
--  PROCEDURE UNUSABLE_UNIQUE_INDEXES
--  AS
--  BEGIN
--    FOR r IN
--    (SELECT INDEX_NAME
--    FROM ALL_INDEXES
--    WHERE owner   =USER
--    AND uniqueness='UNIQUE'
--    AND status    ='VALID'
--    )
--    LOOP
--    BEGIN
--      dbms_utility.exec_ddl_statement('alter index ' || r.index_name || ' UNUSABLE');
--      EXCEPTION WHEN OTHERS THEN
--      DBMS_OUTPUT.PUT_LINE('Excecao index: ' || r.index_name);
--      END;
--    END LOOP;
--  END;
--
--
 /*PROCEDURE RESET_SEQS_SONHO
  AS
    map_ss FM_MAP_SS;
    seqs FM_TC.lista2;
  BEGIN
    seqs   := FM_TC.seqs_para_reset_extra_xml;
    map_ss := FM_MIG3.LOAD_XML_TO_MAP_SS(FM_TC.mainDir, FM_TC.xmlMap);
    -- loop tabelas
    FOR t IN map_ss.tabs.first .. map_ss.tabs.last
    LOOP
      FOR c IN map_ss.tabs(t).cols.first .. map_ss.tabs(t).cols.last
      LOOP
        IF upper(fm_aux.get_col_from_ref(map_ss.tabs(t).cols(c).ref)) = 'NEXTVAL' THEN
          IF fm_aux.get_tab_from_ref(map_ss.tabs(t).cols(c).ref) NOT member OF seqs THEN
            seqs.extend;
            seqs(seqs.count) := fm_aux.get_tab_from_ref(map_ss.tabs(t).cols(c).ref);
          END IF;
        END IF;
      END LOOP; -- loop cols
    END LOOP;   --loop tabs
    --
    FOR i IN seqs.first .. seqs.last
    LOOP
      BEGIN
        -- nas sequencias do numero do episodio, a sequencia nao comeca em um mas sim com dois digitos do ano seguido de 000001
        IF instr(upper(seqs(i)), '_EPISODIO') > 0 THEN
          fm_aux.reset_seq(seqs(i), to_number(TO_CHAR(sysdate, 'yy') || '000000') );
          dbms_output.put_line('RESET ' || seqs(i) || ' para ' || TO_CHAR(sysdate, 'yy') || '000000');
        ELSE
        if lower(seqs(i))!= 'ide_s_num_sequencial' then
          fm_aux.reset_seq(seqs(i));
          dbms_output.put_line('RESET ' || seqs(i));
          end if;
        END IF;
      EXCEPTION
      WHEN OTHERS THEN
        dbms_output.put_line('ERRO na ' || seqs(i));
        dbms_output.put_line(SQLERRM || CHR(10)||dbms_utility.format_error_backtrace);
      END;
    END LOOP;
    --
    -- cria tabela de migracao com mapeamento dos episodios
    CREATE_MIGCHL_EPISODIOS;
    --CREATE_MIGCHL_AGD_BLO_S_SALA;
    --
  END;*/
--
--
/*PROCEDURE CREATE_MIGCHL_EPISODIOS
  AS
    prefixo VARCHAR2(6) := 'MIGCHL';
  BEGIN
    BEGIN
      EXECUTE immediate 'CREATE TABLE ' || prefixo || '_EPISODIOS ('
      || 'COD_DOENTE VARCHAR2(15),'
      || 'NUM_EPS_SIGED VARCHAR2(10),'
      || 'COD_MOD_SONHO_SIGED VARCHAR2(10),'
      || 'NUM_EPS_SONHO NUMBER)';
    EXCEPTION
    WHEN OTHERS THEN
      IF SQLCODE=-955 THEN
        EXECUTE immediate 'delete from ' || prefixo || '_EPISODIOS'; --apaga linhas
      END IF;
    END;
  END;*/
--
--
/* PROCEDURE CREATE_MIGCHL_AGD_BLO_S_SALA
  AS
    prefixo VARCHAR2(6) := 'MIGCHL';
  BEGIN
    BEGIN
      EXECUTE immediate 'CREATE TABLE '||prefixo||'_AGD_BLO_SEM_SALA(NUMLIS NUMBER, COD_DOENTE VARCHAR2(15), DTA_AGENDA DATE)';
    EXCEPTION
    WHEN OTHERS THEN
      IF SQLCODE=-955 THEN
        EXECUTE immediate 'delete from ' || prefixo || '_AGD_BLO_SEM_SALA'; --apaga linhas
      END IF;
    END;
  END;*/
--
--
/*PROCEDURE INSERE_EXCECOES
  AS
    aux FM_TC.lista2;
    aux_number NUMBER;
  BEGIN
    IF FM_AUX.query_is_empty('select count(*) from BLO_ASSEPSIAS WHERE COD_ASSEPSIA=4') THEN
      INSERT
      INTO BLO_ASSEPSIAS
        (
          COD_ASSEPSIA,
          DES_ASSEPSIA
        )
        VALUES
        (
          4,
          'LIMPA-CONTAMINADA'
        );
    END IF;
    IF FM_AUX.query_is_empty('select count(*) from sys_dominios where dominio=''SUBPROV'' and valor=''P5''') THEN
      INSERT
      INTO sys_dominios
        (
          dominio,
          valor,
          descricao
        )
        VALUES
        (
          'SUBPROV',
          'P5',
          'REFERENCIA?O INTERNA EM PAPEL - PRIMEIRA 1'
        );
    END IF;
    IF FM_AUX.query_is_empty('select count(*) from sys_funcionarios where utilizador=''9000''') THEN
      aux_number := SYS_S_NUM_FUNCIONARIO.nextval;
      INSERT
      INTO sys_funcionarios
        (
          num_funcionario,
          NUM_MECANOGRAFICO,
          nome_funcionario,
          cod_grupo_funcional,
          cod_secretariado,
          login,
          activo,
          senha,
          utilizador
        )
        VALUES
        (
          aux_number,
          9000,
          'USER GENERICO',
          3,
          0,
          'SGD_CHL',
          'S',
          sys_pack_pwd.incripta('sonhov2'),
          '9000'
        );
      INSERT
      INTO MIGCHL_SYS_FUNCIONARIOS
        (
          NUM_FUNCIONARIO,
          USERLOGIN,
          COD_SECRETARIADO
        )
        VALUES
        (
          aux_number,
          '9000',
          NULL
        );
    END IF;
    IF FM_AUX.query_is_empty('select count(*) from sys_funcionarios where utilizador=''9005''') THEN
      aux_number := SYS_S_NUM_FUNCIONARIO.nextval;
      INSERT
      INTO sys_funcionarios
        (
          num_funcionario,
          NUM_MECANOGRAFICO,
          nome_funcionario,
          cod_grupo_funcional,
          cod_secretariado,
          login,
          activo,
          senha,
          utilizador
        )
        VALUES
        (
          aux_number,
          9005,
          'Helder Costa',
          3,
          0,
          'SGD_CHL',
          'S',
          sys_pack_pwd.incripta('sonhov2'),
          '9005'
        );

    END IF;*/

    /*IF FM_AUX.query_is_empty('select count(*) from con_ref_outras where nome_outras_ref=''REFERENCIA EM PAPEL''') THEN
      INSERT
      INTO con_ref_outras
        (
          seq_outras,
          nome_outras_ref,
          dta_registo,
          num_mecanografico
        )
        VALUES
        (
          con_s_ref_outras.nextval,
          'REFERENCIA EM PAPEL',
          sysdate,
          (SELECT num_funcionario FROM sys_funcionarios WHERE utilizador=4005
          )
        );
    END IF;
    IF FM_AUX.query_is_empty('select count(*) from sys_isencoes where cod_isencao=60') THEN
      INSERT
      INTO sys_isencoes
        (
          cod_isencao,
          des_isencao,
          justificacao
        )
        VALUES
        (
          60,
          'Migra? de Dados Outras Plataformas',
          'Isentar Registos Provenientes de Outras Plataformas'
        );
    END IF;
    IF FM_AUX.query_is_empty('select count(*) from sys_isencoes where cod_isencao=32') THEN
      INSERT
      INTO sys_isencoes
        (
          cod_isencao,
          des_isencao
        )
        VALUES
        (
          32,
          'ATOS MEDICOS REALIZADOS NO DECURSO DE RASTREIOS'
        );
    END IF;
    IF FM_AUX.query_is_empty('select count(*) from sys_isencoes where cod_isencao=33') THEN
      INSERT
      INTO sys_isencoes
        (
          cod_isencao,
          des_isencao
        )
        VALUES
        (
          33,
          'ATO DISPENS TM/ H.DIA (D.ONCO, NEUROLOG DEGENERAT/DESMIELIZ)'
        );
    END IF;
    IF FM_AUX.query_is_empty('select count(*) from sys_isencoes where cod_isencao=37') THEN
      INSERT
      INTO sys_isencoes
        (
          cod_isencao,
          des_isencao
        )
        VALUES
        (
          37,
          'CRIANCAS/JOVENS EM PROCESSO PROMOCAO E PROTECAO (ART?35 LEI PROTECAO CRIANCAS E JOVENS)'
        );
    END IF;
    IF FM_AUX.query_is_empty('select count(*) from sys_isencoes where cod_isencao=38') THEN
      INSERT
      INTO sys_isencoes
        (
          cod_isencao,
          des_isencao
        )
        VALUES
        (
          38,
          'MENORES EM CUMPRIMENTO MEDIDA TUTELAR INTERNAMENTO (LEI 166/99)'
        );
    END IF;
    IF FM_AUX.query_is_empty('select count(*) from sys_isencoes where cod_isencao=39') THEN
      INSERT
      INTO sys_isencoes
        (
          cod_isencao,
          des_isencao
        )
        VALUES
        (
          39,
          'CRIANCAS/JOVENS INTEGRADOS EM RESPOSTAS SOCIAS ACOLHIMENTO (D.LEI 314/78)'
        );
    END IF;
    IF FM_AUX.query_is_empty('select count(*) from sys_isencoes where cod_isencao=40') THEN
      INSERT
      INTO sys_isencoes
        (
          cod_isencao,
          des_isencao
        )
        VALUES
        (
          40,
          'REQUERENTES ASILO/REFUGIADOS E RESPETIVOS CONJUGES/DESCENDENTES'
        );
    END IF;
    IF FM_AUX.query_is_empty('select count(*) from sys_isencoes where cod_isencao=41') THEN
      INSERT
      INTO sys_isencoes
        (
          cod_isencao,
          des_isencao
        )
        VALUES
        (
          41,
          'FUNCIONARIOS R.C.T.F.P. - ACIDENTE TRABALHO'
        );
    END IF;*/

    -- MIGCHL_MED_SIGED_Q_SAO_ESPEC, vai buscar a parametrizacao em FM_TC
    /*DELETE
    FROM MIGCHL_MED_SIGED_Q_SAO_ESPEC;
    aux := FM_TC.cod_medico_siged_que_sao_espec;
    forall i IN aux.first .. aux.last
    INSERT INTO MIGCHL_MED_SIGED_Q_SAO_ESPEC VALUES
      (aux(i)
      );*/
    --
    -- grupos funcionais
   /* DELETE
    FROM SYS_GRUPOS_FUNCIONAIS
    WHERE upper(DES_GRUPO_FUNCIONAL)='PROFESSOR'
    OR upper(DES_GRUPO_FUNCIONAL)   ='EDUCADOR';
    FOR r IN
    (SELECT cod_GRUPO_FUNCIONAL
    FROM sys_grupos_funcionais
    WHERE COD_GRUPO_FUNCIONAL>10
    )
    LOOP
      UPDATE sys_grupos_funcionais
      SET cod_grupo_funcional  =cod_grupo_funcional + 2
      WHERE cod_grupo_funcional=r.cod_grupo_funcional;
    END LOOP;
    IF FM_AUX.query_is_empty('select COUNT(*) from sys_grupos_funcionais where upper(DES_GRUPO_FUNCIONAL)=''PROFESSOR''') THEN
      INSERT
      INTO sys_grupos_funcionais
        (
          cod_grupo_funcional,
          des_grupo_funcional
        )
        VALUES
        (
          11,
          'PROFESSOR'
        );
    END IF;
    IF FM_AUX.query_is_empty('select COUNT(*) from sys_grupos_funcionais where upper(DES_GRUPO_FUNCIONAL)=''EDUCADOR''') THEN
      INSERT
      INTO sys_grupos_funcionais
        (
          cod_grupo_funcional,
          des_grupo_funcional
        )
        VALUES
        (
          12,
          'EDUCADOR'
        );
    END IF;
    --
  END;
--
--
  PROCEDURE FILTRA_COLS_CRIA_QS_SIGED
  AS
    allRefs FM_TC.LISTA2;
    tabs FM_TC.LISTA2 := FM_TC.LISTA2();
    primeiro    BOOLEAN;
    primeiro2   BOOLEAN;
    tabelaAtual VARCHAR2(100) := '';
    stmts FM_TC.LISTA         := FM_TC.LISTA();
    selects FM_TC.LISTA       := FM_TC.LISTA();
    auxCols  VARCHAR2(4000);
    auxCols2 VARCHAR2(4000);
    auxWhere VARCHAR2(1000);
    --
    fhandle utl_file.file_type;
  BEGIN
    allRefs := FM_AUX.GET_ALL_REFS;
    FOR i IN allRefs.first .. allRefs.last
    LOOP
      IF FM_AUX.GET_TAB_FROM_REF
        (
          allRefs(i)
        )
        NOT member OF tabs THEN
        tabs.extend;
        tabs(tabs.count) := FM_AUX.GET_TAB_FROM_REF(allRefs(i));
      END IF;
    END LOOP;
    -- ORDER BY AQUI E IMPORTANTE PARA MANTER A INTEGRIDADE
    -- COM O FICHEIRO CTL (SQL LOADER) QUE ?CRIADO NO PROXIMO PROCEDIMENTO
    FOR r IN
    (SELECT table_name,
        column_name
      FROM all_tab_columns
      WHERE owner=FM_TC.migUsr
      ORDER BY table_name,
        column_id
    )
    LOOP
      IF r.table_name member OF tabs THEN
        IF tabelaAtual   != r.table_name OR tabelaAtual IS NULL THEN
          IF tabelaAtual IS NOT NULL THEN
            IF auxCols   IS NOT NULL THEN
              stmts.extend;
              stmts(stmts.count) := 'ALTER TABLE ' || FM_TC.migUsr || '.' || tabelaAtual || ' DROP (' || auxCols || ')';
            END IF;
            IF auxCols2 IS NOT NULL THEN
              --
              -- GET auxWhere para o SIGED, especificacao no package FM_TC
              auxWhere := FM_TC.GET_AUXWHERE_FILTRA_FONTE(tabelaAtual);
              --
              selects.extend;
              selects(selects.count) := tabelaAtual || ';SELECT ' || auxCols2 || ' FROM ' || tabelaAtual || auxWhere || ';';
            END IF;
          END IF;
          tabelaAtual := r.table_name;
          auxCols     := '';
          auxCols2    := '';
          primeiro    := TRUE;
          primeiro2   := TRUE;
        END IF;
        IF r.table_name || '.' || r.column_name member OF allRefs THEN
          IF primeiro2 THEN
            primeiro2 := FALSE;
          ELSE
            auxCols2 := auxCols2 || ', ';
          END IF;
          IF r.column_name='ACCESS_' THEN --excecao por access ser uma palavra reservada
            auxCols2     := auxCols2 || 'ACCESS';
          ELSE
            auxCols2 := auxCols2 || r.column_name;
          END IF;
        ELSE
          IF primeiro THEN
            primeiro := FALSE;
          ELSE
            auxCols := auxCols || ', ';
          END IF;
          auxCols := auxCols || r.column_name;
        END IF;
      END IF; -- member of tabs
    END LOOP;
    --
    IF tabelaAtual IS NOT NULL THEN -- inserir ultimo.
      IF auxCols   IS NOT NULL THEN
        stmts.extend;
        stmts(stmts.count) := 'ALTER TABLE ' || FM_TC.migUsr || '.' || tabelaAtual || ' DROP (' || auxCols || ')';
      END IF;
      IF auxCols2 IS NOT NULL THEN
        --
        -- GET auxWhere para o SIGED, especificacao no package FM_TC
        auxWhere := FM_TC.GET_AUXWHERE_FILTRA_FONTE(tabelaAtual);
        --
        selects.extend;
        selects(selects.count) := tabelaAtual || ';SELECT ' || auxCols2 || ' FROM ' || tabelaAtual || auxWhere || ';';
      END IF;
    END IF;
    --
    --
    IF stmts.count > 0 THEN
      FOR i IN stmts.first .. stmts.last
      LOOP
        EXECUTE immediate stmts
        (
          i
        )
        ;
        dbms_output.put_line(stmts(i));
      END LOOP;
    END IF;
    --
    IF selects.count > 0 THEN
      fhandle       := utl_file.fopen('LOAD_TABS', 'selects_bcp_siged.csv', 'W', 4096);
      FOR i IN selects.first .. selects.last
      LOOP
        utl_file.put_line
        (
          fhandle, selects(i)
        )
        ;
        --dbms_output.put_line(selects(i));
      END LOOP;
      utl_file.fclose(fhandle);
    END IF;
  END;
--
--
  PROCEDURE CREATE_CTL_FILES_SQLLDR
  AS
    fhandle utl_file.file_type;
    CURSOR c1 (tabName VARCHAR2)
    IS
      SELECT column_name,
        data_type
      FROM all_tab_columns
      WHERE owner   =FM_TC.migUsr
      AND table_name=tabName
      ORDER BY column_id;
    -- ORDER BY IMPORTANTE AQUI PARA MANTER INTEGRIDADE DA INFORMACAO
    -- COM O PROCEDIMENTO ANTERIOR
    tabs FM_TC.LISTA := FM_TC.LISTA();
    cols FM_TC.LISTA;
    dataTypes FM_TC.LISTA;
    primeiro              BOOLEAN;
    aux                   VARCHAR2(4096);
    dateFormatForCtlFile1 VARCHAR2(50)  := ' "TO_DATE(REGEXP_REPLACE(:';
    dateFormatForCtlFile2 VARCHAR2(100) := ', ''\\.[[:digit:]]{1,}'', ''''), ''yyyy-mm-dd hh24:mi:ss'')"';
    --
    file_exists BOOLEAN;
    fsize       NUMBER;
    bsize       NUMBER;
  BEGIN
    FOR r IN
    (SELECT table_name FROM ALL_tables WHERE owner=FM_TC.migUsr
    )
    LOOP
      IF r.table_name != 'ZZAUX' AND r.table_name != 'CONSTRAINTS_SIGED' THEN
        tabs.extend;
        tabs(tabs.count) := r.table_name;
      END IF;
    END LOOP;
    --
    FOR t IN tabs.first .. tabs.last
    LOOP
      --
      --
      --utl_file.fgetattr('LOAD_TABS', 'ctl_'||tabs(t)||'.ctl', file_exists, fsize, bsize);
      --IF file_exists = FALSE THEN
        --dbms_output.put_line(tabs(t));
        cols      := FM_TC.LISTA();
        dataTypes := FM_TC.LISTA();
        primeiro  := TRUE;
        aux       := '';
        OPEN c1(tabs(t));
        FETCH c1 bulk collect INTO cols, dataTypes;
        CLOSE c1;
        -- os limitadores dos CSV foram escolhidos de forma a nao entrar em conflito com qualquer texto
        -- nem haver duvidas, por isso que sao invulgares
        fhandle := utl_file.fopen('LOAD_TABS', 'ctl_'||tabs(t)||'.ctl', 'W', 4096);
        utl_file.put_line(fhandle, 'load data');
        utl_file.put_line(fhandle, 'CHARACTERSET UTF16'); --IMPORTANTE! TER CUIDADO COM O CHARACTERSET...
        utl_file.put_line(fhandle, 'infile "dsv_'||tabs(t)||'.dsv" "str ''$%%$''"');
        utl_file.put_line(fhandle, 'insert');
        utl_file.put_line(fhandle, 'into table ' || tabs(t));
        utl_file.put_line(fhandle, 'fields terminated by ";$" optionally enclosed by "#$"');
        utl_file.put_line(fhandle, 'TRAILING NULLCOLS');
        FOR c IN cols.first .. cols.last
        LOOP
          IF primeiro THEN
            aux      := '(';
            primeiro := false;
          ELSE
            aux := aux || ', ';
          END IF;
          aux := aux || cols(c);
          -- tratamento especial para datas
          IF dataTypes(c) = 'DATE' THEN
            aux          := aux || dateFormatForCtlFile1 || cols(c) || dateFormatForCtlFile2;
          END IF;
          -- exececoes
          -- EXCECOES QUE JA FORAM DETECTADAS
          -- novas excecoes podem ser aqui adicionadas.
          CASE
          WHEN tabs(t)  ='BPMCEX' AND cols(c)='OBSERV' THEN
            aux        := aux || ' CHAR(500)';
          WHEN tabs(t)  ='FALRGMOV' AND cols(c)='OBSERV' THEN
            aux        := aux || ' CHAR(150)';
          WHEN tabs(t)  ='BPMIMG' AND cols(c)='INF_RELEV' THEN
            aux        := aux || ' CHAR(400)';
          WHEN tabs(t)  ='BPMIMG' AND cols(c)='EXM_COMPL' THEN
            aux        := aux || ' CHAR(300)';
          WHEN tabs(t)  ='BPMIMG' AND cols(c)='RECUSA' THEN
            aux        := aux || ' CHAR(4000)';
          WHEN tabs(t)  ='BPMGST' AND (cols(c)='OBSERV' OR cols(c)='INF_CLI') THEN
            aux        := aux || ' CHAR(300)';
          WHEN (tabs(t) ='BFIDMG' OR tabs(t)='BITINT') AND cols(c)='OBS_INT' THEN
            aux        := aux || ' CHAR(200)';
          ELSE
            NULL;
          END CASE;
        END LOOP;
        aux := aux || ')';
        --dbms_output.put_line(aux);
        utl_file.put_line(fhandle, aux);
        utl_file.fclose(fhandle);
      --END IF;
      --
      --
    END LOOP;
  END;*/
--
--
END FM_PREMIG;


/
--------------------------------------------------------
--  DDL for Package Body FM_TC
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE PACKAGE BODY "FM_TC" 
AS
  -- CONSTANTES
  FUNCTION mainDir
    RETURN VARCHAR2
  AS
  BEGIN
    RETURN 'FM';
  END;
--
  FUNCTION migUsr
    RETURN VARCHAR2
  AS
  BEGIN
    RETURN 'SIGED_ORA';
  END;
--
  FUNCTION xmlMap
    RETURN VARCHAR2
  AS
  BEGIN
    RETURN 'sonho_siged_map.xml';
  END;
--
  /*FUNCTION userGenerico
    RETURN NUMBER
  AS
    res NUMBER;
    CURSOR c1
    IS
      SELECT num_funcionario FROM MIGCHL_SYS_FUNCIONARIOS WHERE userlogin='9000';
  BEGIN
    OPEN c1;
    FETCH c1 INTO res;
    CLOSE c1;
    RETURN res;
  END;*/
--
  FUNCTION isencaoMigracao
    RETURN NUMBER
  AS
  BEGIN
    RETURN 60;
  END;
--
  FUNCTION cod_am_taxa_consulta
    RETURN NUMBER
  AS
  BEGIN
    RETURN 600;
  END;
--
  FUNCTION cod_subsistema_desconhecido
    RETURN NUMBER
  AS
  BEGIN
    RETURN 990004;
  END;
--
--
--
-- no procedimento FM_PREMIG.RESET_SEQS_SONHO
-- que tem a funcao de fazer resett a todoas as sequencias do SONHO envolvidas na migracao,
-- apenas vai buscar as que estao no XML de mapeamento, as "extras" que sao utilizados
-- em admissoes, e agendamentos (por exemplo), estao aqui
  FUNCTION seqs_para_reset_extra_xml
    RETURN lista2
  AS
    seqs lista2 := lista2('ide_s_num_sequencial', 'con_s_cod_med_esp',
    /*'SYS_S_NUM_FUNCIONARIO',*/
    'ide_s_num_taxa',
    /*'con_s_ref_outras',*/
    /*AGENDA EXAMES*/
    'ide_s_num_acto_medico', 'cli_s_cod_reg_rad', 'cli_s_cod_registo', 'cli_s_ord_marc_rad', 'cli_s_ord_marcacao', 'cli_s_cod_vaga', 'cli_s_cod_requisicaomcdt',
    /*AGENDA CONSULTAS*/
    'id_marc_1med_seq', 'id_marc_2med_seq','id_marc_1esp_seq','id_marc_2esp_seq','con_s_ord_marcacao','con_s_cod_vaga',
    /*RAD*/
    'ide_s_num_episodio_am',
    /*LAB*/
    'ide_s_num_episodio',
    /*CON*/
    'con_s_con_episodio',
    /*INT*/
    'int_s_int_episodio', 'int_s_num_transferencia',
    /*URG*/
    'urg_s_urg_episodio_old',
    /*HDI*/
    'hdi_s_hdi_episodio',
    /*BLO*/
    'blo_s_blo_num_admissao',
    /*TEMPLATES*/
    'int_s_num_registo_cama', 'seq_chmoradas' );
  BEGIN
    RETURN seqs;
  END;
--
-- A funcao FM_AUX.GET_ALL_REFS obtem todas as referencias do sistema nao-SONHO a partir do XML de mapeamento
-- e da tabela CONSTRAINTS_SIGED. No entanto existem referencias extras que tambem devem ser levadas em conta
-- na migracao. Sao estas.
  FUNCTION refs_extra_xml_e_constraints
    RETURN lista2
  AS
    refs lista2 := LISTA2(
    /**/
    'BCEDNT.SIT_REGIST','BCEDNT.NUM_EPS', 'BCEMAD.COD_UTENTE', 'BCEMAD.SIT_REGIST',
    /*'BCEMAD.TAXA_MOD', 'BCEMAD.NUM_RECIBO',*/
    'BCELEA.MOTIVO', 'BCELEA.COD_MEDICO', 'BCELEA.TIP_CNS', 'BCELEA.DATA_MRC', 'BPMCEX.NUM_MARCA',
    /**/
    'BURDNT.COD_DOENTE', 'BURDNT.DAT_SAI', 'BURDNT.HOR_SAI', 'BURDNT.DESTINO',
    /**/
    'BGNEPS.TIPO_EPS', 'BGNEPS.DATA_EPS', 'BGNEPS.NUM_DNT', 'BGNEPS.NUM_EPS',
    /**/
    'BGNEPS2.TIPO_EPS', 'BGNEPS2.DATA_EPS', 'BGNEPS2.DNT_NUM', 'BGNEPS2.NUM_EPS',
    /**/
    'BGNMED.NUM_CED','BGNMED.COD_SRV', 'BGNMED.COD_SSR',
    /*AGENDA EXAMES GERAIS*/
    'BEXAGD.NUM_EPS', 'BEXNMR.COD_DOENTE',
    /*AGENDA EXAMES IMAGIOLOGIA*/
    'BIMAGD.NUM_EPS', 'BIMNMR.COD_DOENTE',
    /*AGENDA MFR*/
    'BFIAGD.COD_MEDICO','BFIDMG.NUM_E',
    /*PEDIDOS*/
    'BPMIMG.NUM_EPS', 'BPMIMG.ESTADO', 'BPMECG.NUM_EPS', 'BPMGST.NUM_EPS',
    /*INTERNAMENTO*/
    'BITINT.NUM_INT', 'BITINT.NUM_EPS', 'BITINT.SUBSER',
    /*AGENDA CONSULTAS*/
    'BGNHMD.EXAMES_DI2', 'BGNHMD.EXAMES_DI3', 'BGNHMD.COD_HORA', 'BGNHMD.HORA_INIC', 'BGNHMD.DATA_ALT', 'BGNHMD.DATA_CRIA',
    /**/
    'BGNXMD.COD_HORA', 'BGNAGD.COD_P1', 'BGNAGD.NUM_MARCA',
    /*AGENDA EXAMES*/
    'BEXHMD.EXAMES_DIA', 'BIMHMD.EXAMES_DIA','BEXHMD.HORA_INIC', 'BIMHMD.HORA_INIC', 'BFIPLN.HORA_INIC', 'BEXHMD.TEMPO_EXM', 'BIMHMD.TEMPO_EXM',
    /**/
    'BEXXMD.COD_HORA', 'BIMXMD.COD_HORA', 'BEXHMD.COD_HORA', 'BIMHMD.COD_HORA',
    /*LIC - Admissao Bloco*/
    'FBLPBO.ESTADO',
    /*BGNDNT*/
    'BGNDNT.DNT_PROCE', 'BGNDNT.PROC_HDA', 'BGNDNT.NUMMEC', 'BGNDNT.DNT_SIT' );
  BEGIN
    RETURN refs;
  END;
--
-- utilizado no script auxiliar de verificar as tabelas vazias e get todas as tabelas envolvidas na migra?
  FUNCTION tabs_migracao_extra_xml
    RETURN lista2
  AS
    tabs lista2 := lista2('CLI_HORA_RAD', 'CLI_VAGAS_RAD', 'CON_VAGAS_1MED', 'CON_VAGAS_2MED','CON_VAGAS_1ESP', 'CON_VAGAS_2ESP', 'IDE_PROC_CONV', 'IDE_TAXAS', 'CON_ESPEC_GRUPOS', 'SYS_SALAS_FISICAS', 'SYS_SALAS_ASSOC_SALA');
  BEGIN
    RETURN tabs;
  END;
--
-- utilizado para truncate tabelas de migracao, por exemplo
  FUNCTION tabs_migchl_sem_template
    RETURN lista2
  AS
    tabs lista2 := lista2('MIGCHL_CON_MED_ESP', 'MIGCHL_ACTOS_MEDICOS', 'MIGCHL_EPISODIOS', 'MIGCHL_HORAS_OBITO_INVALIDA', 'MIGCHL_UTENTES_CP_INVALIDOS');
  BEGIN
    RETURN tabs;
  END;
--
  FUNCTION tabs_prioritarias(
      prioridade NUMBER)
    RETURN lista2
  AS
    --
    tabs1 lista2 := lista2('MIGCHL_CON_MED_ESP'); -- maxima prioridade
    tabs2 lista2 := lista2('CON_MED_ESP');-- prioridade intermedia 1
    tabs3 lista2 := lista2('IDE_OUTROS_DADOS', 'SYS_VALIDADE_ISENCOES', 'CON_AGE_PARAMETROS', 'CLI_AGENDA');-- prioridade intermedia 2
    tabs4 lista2 := lista2('CON_REGISTADAS', 'INT_TRANSFERENCIAS'); -- prioridade intermedia 3
  BEGIN
    IF prioridade = 1 THEN
      RETURN tabs1;
    elsif prioridade = 2 THEN
      RETURN tabs2;
    elsif prioridade = 3 THEN
      RETURN tabs3;
    elsif prioridade = 4 THEN
      RETURN tabs4;
    ELSE
      RETURN tabs1 multiset
      UNION tabs2 multiset
      UNION tabs3 multiset
      UNION tabs4;
    END IF;
  END;
--
  FUNCTION GET_AUXWHERE_FILTRA_FONTE(
      tab VARCHAR2)
    RETURN VARCHAR2
  AS
  BEGIN
    CASE upper(tab)
    WHEN 'BCEDNT' THEN
      RETURN ' WHERE SIT_REGIST!=''A'' AND DATA_ADM > ''2013-01-01''';
    WHEN 'BCELEA' THEN
      RETURN ' WHERE MOTIVO=''001''';
    WHEN 'BCEMAD' THEN
      RETURN ' WHERE SIT_REGIST!=''A'' AND DATA_ADM > ''2013-01-01''';
    WHEN 'BEXAGD' THEN
      RETURN ' WHERE ESTADO=''M''';
    WHEN 'BFIAGD' THEN
      RETURN ' WHERE ESTADO=''M''';
    WHEN 'BGNAGD' THEN
      RETURN ' WHERE ESTADO=''M''';
    WHEN 'BGNEPS' THEN
      RETURN ' WHERE TIPO_EPS <> ''U'' AND TIPO_EPS <> ''UT''';
      --    WHEN 'BGNEPS2' THEN
      --      RETURN ' WHERE TIPO_EPS <> ''U'' AND TIPO_EPS <> ''UT''';
    WHEN 'BIMAGD' THEN
      RETURN ' WHERE ESTADO=''M''';
    WHEN 'BLERPC' THEN
      RETURN ' WHERE ESTADO <> ''37'' AND ESTADO <> ''64''';
    WHEN 'BURDNT' THEN
      RETURN ' WHERE DESTINO=''05''';
    WHEN 'FBLPBO' THEN
      RETURN ' WHERE ESTADO=''31''';
    ELSE
      RETURN NULL;
    END CASE;
  END;
--
  FUNCTION cod_medico_siged_que_sao_espec
    RETURN lista2
  AS
    codigos_medicos lista2 := lista2('1000', '2000', '4662',
    /**/
    '4663', '4664', '4665', '4667', '4669', '88801', '88802',
    /**/
    '88803', '88804', '88805', '88806', '88807', '88808', '88809', '88810', '88811');
  BEGIN
    RETURN codigos_medicos;
  END;
--
  FUNCTION GET_TAB_PEDIDOS_SIGED(
      tab    VARCHAR2,
      cod_am VARCHAR2 := NULL)
    RETURN VARCHAR2
  AS
  BEGIN
    CASE
    WHEN tab = 'BIMAGD' THEN
      RETURN 'BPMIMG';
    WHEN tab = 'BEXAGD' AND (cod_am IS NULL OR cod_am != 'C40301') THEN -- cod ECG
      RETURN 'BPMGST';
    WHEN tab = 'BEXAGD' AND cod_am = 'C40301' THEN -- cod ECG
      RETURN 'BPMECG';
    ELSE
      RETURN NULL;
    END CASE;
  END;
--
  FUNCTION TABS_COM_NUM_SEQ(
      del_ou_upd VARCHAR2)
    RETURN LISTA2
  AS
    map_ss FM_MAP_SS;
    tabs_del lista2 := lista2('ARQ_ENTRADAS');
    tabs_upd lista2 := lista2('IDE_TAXAS', 'IDE_RAD_DOENTE', 'IDE_RAD_EPISODIOS'); --excecao de IDE para updates, as outras sao del
  BEGIN
    map_ss := FM_MIG3.LOAD_XML_TO_MAP_SS(FM_TC.mainDir, FM_TC.xmlMap);
    <<loopTabs>>
    FOR t IN map_ss.tabs.first .. map_ss.tabs.last
    LOOP
      FOR c IN map_ss.tabs(t).cols.first .. map_ss.tabs(t).cols.last
      LOOP
        IF map_ss.tabs(t).cols(c).nome = 'NUM_SEQUENCIAL' THEN
          IF instr(map_ss.tabs(t).nome,'IDE_') = 1 AND map_ss.tabs(t).nome NOT member OF tabs_upd AND map_ss.tabs(t).nome NOT member OF tabs_del THEN
            tabs_del.extend;
            tabs_del(tabs_del.count) := map_ss.tabs(t).nome;
          ELSIF map_ss.tabs(t).nome NOT member OF tabs_upd AND map_ss.tabs(t).nome NOT member OF tabs_del THEN
            tabs_upd.extend;
            tabs_upd(tabs_upd.count) := map_ss.tabs(t).nome;
          END IF;
          CONTINUE loopTabs;
        END IF;
      END LOOP; -- loop colunas
    END LOOP; -- loop tabelas
    --
    IF upper(del_ou_upd) = 'DELETE' THEN
      RETURN tabs_del;
    ELSIF upper(del_ou_upd) = 'UPDATE' THEN
      RETURN tabs_upd;
    ELSE
      RETURN NULL;
    END IF;
  END;
--
END FM_TC;


/
--------------------------------------------------------
--  DDL for Package Body PKG1
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE PACKAGE BODY "PKG1" is
pragma SERIALLY_REUSABLE;
Procedure init_pkg_state (n number) is
begin
  pkg1.num := n;
  dbms_output.put_line('num:' || pkg1.num);
end;
procedure print_pkg_state is
begin
  dbms_output.put_line('num:' || pkg1.num);
end;
procedure Ati_Des_Uti(dta_hoje varchar,res out Varchar2) is
    x_weekend_date EXCEPTION;
  Begin  
        BEGIN
            IF to_char(to_date(dta_hoje,'dd-MM-yyyy'),'D') in (2,3,4,5,6) AND TO_CHAR(sysdate,'HH24:MI') BETWEEN '09:00' AND '19:30' THEN  
               res:='Can not activate/desactivate users during office hours!';
            END IF; 
        EXCEPTION
        WHEN x_weekend_date THEN
         DBMS_OUTPUT.PUT_LINE('Can not activate/deactivate users during office hours?');
        END;
  END;
end pkg1;

/

  GRANT EXECUTE ON "PKG1" TO PUBLIC;
--------------------------------------------------------
--  DDL for Package Body PKUTENTE
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE PACKAGE BODY "PKUTENTE" is
--pragma serially_reusable;
    procedure Calcula_Idade(Idade Date,res out varchar2) is
    anos number;
    messes number;
    dias number;
    begin
    select trunc(months_between(sysdate,idade) / 12)into anos from dual;
    select trunc(months_between(sysdate,Idade)-(trunc(months_between(sysdate,Idade) / 12) * 12))
    into messes from dual;
    select trunc(sysdate)
        - add_months(Idade, trunc(months_between(sysdate,Idade))) into dias from duaL;
    
       if anos = 1 then -- têm um ano
                     if messes = 1 then -- 1 messes
                             if dias = 1 then
                              res := 'O utente têm ' || anos || ' ano, um ' || messes ||' messes e '|| dias ||' dia.';
                             elsif dias > 1 then
                              res := 'O utente têm ' || anos || ' ano, um ' || messes ||' messes e '|| dias ||' dias.';
                             else
                              res := 'O utente têm ' || anos || ' ano, um ' || messes ||' messes';  
                             end if;
                     elsif messes > 1 then -- mais que um messes
                             if dias = 1 then
                              res := 'O utente têm ' || anos || ' ano, ' || messes ||' messes e '|| dias ||' dia.';
                             elsif dias > 1 then
                              res := 'O utente têm ' || anos || ' ano, ' || messes ||' messes e '|| dias ||' dias.';
                             else
                              res := 'O utente têm ' || anos || ' ano, ' || messes ||' messes';  
                             end if;
                     else
                      res := 'O utente têm ' || anos || ' ano';
                     end if;
       elsif anos > 1 then -- têm mais de um ano 
                        if messes = 1 then -- 1 messes
                             if dias = 1 then
                              res := 'O utente têm ' || anos || ' anos, um ' || messes ||' messes e '|| dias ||' dia.';
                             elsif dias > 1 then
                              res := 'O utente têm ' || anos || ' anos, um ' || messes ||' messes e '|| dias ||' dias.';
                             else
                              res := 'O utente têm ' || anos || ' anos, um ' || messes ||' messes';  
                             end if;
                       elsif messes > 1 then -- mais que um messes
                             if dias = 1 then
                              res := 'O utente têm ' || anos || ' anos, ' || messes ||' messes e '|| dias ||' dia.';
                             elsif dias > 1 then
                              res := 'O utente têm ' || anos || ' anos, ' || messes ||' messes e '|| dias ||' dias.';
                             else
                              res := 'O utente têm ' || anos || ' anos, ' || messes ||' messes';  
                             end if;
                        else 
                          res := 'O utente têm ' || anos || ' anos';
                        end if;
      else
                       if messes = 1 then -- 1 messes
                             if dias = 1 then
                              res := 'O utente têm '|| messes ||' messes e '|| dias ||' dia.';
                             elsif dias > 1 then
                              res := 'O utente têm '|| messes ||' messes e '|| dias ||' dias.';
                             else
                              res := 'O utente têm '|| messes ||' messes';  
                             end if;
                      elsif messes > 1 then -- mais que um messes
                             if dias = 1 then
                              res := 'O utente têm '|| messes ||' messes e '|| dias ||' dia.';
                             elsif dias > 1 then
                              res := 'O utente têm '|| messes ||' messes e '|| dias ||' dias.';
                             else
                              res := 'O utente têm '|| messes ||' messes';   
                             end if;
                      else
                         if dias = 1 or dias = 0 then
                              if dias = 0 then
                                dias := 1;
                              end if;
                              res := 'O utente têm '|| dias ||' dia.';
                         elsif dias > 1 then
                              res := 'O utente têm '|| dias ||' dias.';
                         end if;     
                      end if;
    end if;
    end Calcula_Idade;

    procedure Valida_Nome(Nome1 varchar ,nome_ant varchar,res out Varchar2)is
        STR VARCHAR(100):=Nome1;
        STR2 VARCHAR(100):=nome_ant;
        nome_nov varchar(160);
        nome_antigo varchar(160);
        nome_maior varchar(160);
        nome_menor varchar(160);
        number_nome_nov number;
        number_nome_antigo number;
        valor_limite number := 2;
        valor_limite2 number := 0;
        tamanho_nome number;
        cont_dif number := 0;
        vStartIdx binary_integer;
        vEndIdx   binary_integer;
        vCurValue varchar2(1000);
    BEGIN
    SELECT LENGTH(STR) - LENGTH(REPLACE(STR, ' ', ''))
    into number_nome_antigo
    FROM DUAL;
    SELECT LENGTH(STR2) - LENGTH(REPLACE(STR2, ' ', ''))
    into number_nome_nov
    FROM DUAL; --retorna o Naemro de espa?os em branco contidos na string
    
    if(number_nome_antigo > number_nome_nov)then
      tamanho_nome := number_nome_antigo - number_nome_nov;
    elsif (number_nome_antigo < number_nome_nov)then
      tamanho_nome :=  number_nome_nov - number_nome_antigo;
    else
      tamanho_nome :=  number_nome_nov - number_nome_antigo;
    end if;
    --dbms_output.put_line('->tamanho_nome'||tamanho_nome);
    if tamanho_nome > valor_limite then
      res := 'O novo nome s? pode ter em rela??o ao antigo'||' '||valor_limite||' '||'altera??es';
    else
      nome_nov := replace(STR,' ',',');
      nome_antigo :=replace(STR2,' ',',');
      if length(nome_nov) > length(nome_antigo) then
          nome_maior := nome_nov;
          nome_menor := nome_antigo;
      else
          nome_maior := nome_antigo;
          nome_menor := nome_nov;
      end if;
    
      vStartIdx := 0;
      vEndIdx   := instr(nome_maior, ','); 
     while(vEndIdx > 0) loop
        vCurValue := substr(upper(nome_maior), vStartIdx+1, vEndIdx - vStartIdx - 1);
        -- call proc here
            --dbms_output.put_line('->'||vCurValue||'<-');
            select check_list_intersect(upper(nome_menor), vCurValue) 
            into cont_dif from dual;
            if cont_dif = 0 then
              valor_limite2 := valor_limite2 + 1;
            end if;
            --dbms_output.put_line('->cont_dif'||cont_dif);   
            vStartIdx := vEndIdx;
            vEndIdx := instr(upper(nome_maior), ',', vStartIdx + 1);
        End loop;   
        vCurValue := substr(upper(nome_maior), vStartIdx+1);
         select check_list_intersect(upper(nome_menor), vCurValue) 
            into cont_dif from dual;
            if cont_dif = 0 then
              valor_limite2 := valor_limite2 + 1;
            end if;
      if valor_limite2 > valor_limite then
        res := 'O novo nome só pode ter em relação ao antigo'||' '||valor_limite||' '||'alterações';
      end if;  
    end if;
    END Valida_Nome;

    procedure Valida_Nome_Array(Nome_anti varchar ,Nome_ori varchar,res out Varchar2) is
        STR VARCHAR(100):=Nome_anti;
        STR2 VARCHAR(100):=Nome_ori;
        J NUMBER;
        nome_nov varchar(160);   
        nome_ant varchar(160);
        number_nome_nov number;   
        number_nome_antigo number;
        valor_limite number := 2; 
        tamanho_nome number; 
        conta_nnovo number := 0;
        cont_dif number := 0; 
        conta_nantigo number := 0;
        vStartIdx binary_integer; 
        vStartIdx2 binary_integer;
        vCurValue varchar2(1000);
        vCurValue2 varchar2(1000);
        vEndIdx   binary_integer;
        vEndIdx2   binary_integer;
        type TStringArray is table of varchar2(200);
        array1 TStringArray; array2 TStringArray;
        total_array number;
    BEGIN
    select count(*)
    into conta_nnovo
    from valida_temp;
    if conta_nnovo > 0 then
     delete from valida_temp;
    end if;
    select count(*)
    into conta_nantigo
    from valida_temp2;
    if conta_nantigo > 0 then
     delete from valida_temp2;
    end if;
    SELECT LENGTH(STR) - LENGTH(REPLACE(STR, ' ', ''))
    into number_nome_antigo
    FROM DUAL;
    SELECT LENGTH(STR2) - LENGTH(REPLACE(STR2, ' ', ''))
    into number_nome_nov
    FROM DUAL; --retorna o Naemro de espacos em branco contidos na string
    
    if(number_nome_antigo > number_nome_nov)then
      tamanho_nome := number_nome_antigo - number_nome_nov;
    elsif (number_nome_antigo < number_nome_nov)then
      tamanho_nome :=  number_nome_nov - number_nome_antigo;
    else
      tamanho_nome :=  number_nome_nov - number_nome_antigo;
    end if;
    if tamanho_nome > valor_limite then
      DBMS_OUTPUT.PUT_LINE('O novo nome so pode ter em relacao ao antigo'||' '||valor_limite||' '||'alteracoes');
    else
          nome_ant := replace(STR,' ',',');
          nome_nov :=replace(STR2,' ',',');
          vStartIdx := 0;
          vEndIdx   := instr(nome_ant, ','); 
          while(vEndIdx > 0) loop
            vCurValue := substr(nome_ant, vStartIdx+1, vEndIdx - vStartIdx - 1);
            vStartIdx := vEndIdx;
            vEndIdx := instr(nome_ant, ',', vStartIdx + 1);  
            Insert into Valida_temp(nome1) values(vCurValue);
          end loop;
          vCurValue := substr(nome_ant, vStartIdx+1);
          Insert into Valida_temp(nome1) values(vCurValue);
          vStartIdx2 := 0;
          vEndIdx2   := instr(nome_nov, ','); 
          while(vEndIdx2 > 0) loop
            vCurValue2 := substr(nome_nov, vStartIdx2+1, vEndIdx2 - vStartIdx2 - 1);
            vStartIdx2 := vEndIdx2;
            vEndIdx2 := instr(nome_nov, ',', vStartIdx2 + 1);
            Insert into Valida_temp2(nome2) values(vCurValue2);
          end loop;
          vCurValue2 := substr(nome_nov, vStartIdx2+1);
          Insert into Valida_temp2(nome2) values(vCurValue2);
        
          select upper(Nome1) bulk collect into array1
          from valida_temp
          order by rownum asc;
          select upper(Nome2) bulk collect into array2
          from valida_temp2
          order by rownum asc;
        
          for i in 1..array1.Count loop
                 for j in 1..array2.Count loop
                  if (i = J)then
                   if array1(i)=array2(i)then 
                    DBMS_OUTPUT.put_line('Igual:'||rpad(j,2)||array2(i));
                  else
                    cont_dif := cont_dif + 1;
                   end if;
                  end if;
                 end loop;
          end loop;
        total_array := array2.Count - array1.Count;
        if total_array <= 0 then
         select  -1 * total_array 
         into 
           total_array
            from  dual;
        end if;
        DBMS_OUTPUT.put_line( 'total: '||total_array);  
    end if;
    cont_dif := cont_dif + total_array;
    DBMS_OUTPUT.put_line( 'DifereNaas: '||cont_dif);
    if cont_dif > valor_limite then
        DBMS_OUTPUT.PUT_LINE('O novo nome s? pode ter em rela??o ao antigo'||' '||valor_limite||' '||'altera??es');
    end if;    
    commit;
    END Valida_Nome_Array;

    procedure Valida_Password(pass varchar ,Num_mec number,res out Varchar2) is
    Conta_Maisculas number := 0;
    Conta_Minusculas number := 0;
    Conta_Mai_total number := 0;
    Conta_min_total number := 0;
    Conta_Caracteres number := 0;
    Conta_Numeros number := 0;
    Conta_Carac_tot number := 0;
    Conta_Num_tot number := 0;
    v_length number(38);
    v_out varchar2(30);
    is_viewable BOOLEAN := true;
    res_calc number := 0; -- dois parametros nmec e pass
    data_max date;
    senha varchar(30);
    v_pass SYS_USERS.SENHA%type;
    v_ID SYS_USERS.ID%type;
    CURSOR data_atualizacao(Num_mec number)IS
    SELECT max(last_update_date),senha
    FROM SYS_USERS
    WHERE NUM_MECANOG = Num_mec and ACTIVO = 'S'
    GROUP BY NUM_MECANOG,senha;
    CURSOR hist_pass IS
         SELECT senha,ID
          FROM (SELECT *
                FROM sys_users
                WHERE NUM_MECANOG = Num_mec  
                order by creation_date desc) 
         WHERE ROWNUM <= 24;
    BEGIN
    OPEN data_atualizacao(Num_mec);
    FETCH data_atualizacao
    INTO data_max,senha;
    IF data_atualizacao%NOTFOUND THEN
      DBMS_OUTPUT.PUT_LINE('O n?mero mecanogr?fico' ||Num_mec|| 'n?o existe na tabela_1!');
    END IF;
    CLOSE data_atualizacao;
    res_calc := trunc(sysdate) - trunc(data_max);
    v_length := length(pass);
    /*if (data_max is not null and (res_calc >= dias_totais)) then
     DBMS_OUTPUT.PUT_LINE('A sua password expira dentro de '||to_number(dias_limite-dias_totais)||' dia(s).');
     is_viewable := true;
    elsif(data_max is not null and (res_calc >= dias_limite)) then
     DBMS_OUTPUT.PUT_LINE('A sua password expirou!');
     is_viewable := false;
    end if;*/
    if v_length < 12 then
         DBMS_OUTPUT.PUT_LINE('A password deve ter pelo menos 12 caracteres');
         is_viewable := false;
    else
          for i in 1..v_length
            Loop
              v_out  := substr(pass,i,1);
              SELECT LENGTH(TRIM(TRANSLATE(v_out, 'ABCDEFGHIKLMNOPQRSTUVXYWZ',' ')))
              into Conta_Maisculas FROM dual;
              if Conta_Maisculas is null then
                Conta_Mai_total := Conta_Mai_total +1;
              end if;
              SELECT LENGTH(TRIM(TRANSLATE(v_out, 'abcdefghiklmnopqrstuvxywz',' ')))
              into Conta_Minusculas FROM dual;
              if Conta_Minusculas is null then
                Conta_min_total := Conta_min_total +1;
              end if;
              SELECT LENGTH(TRIM(TRANSLATE(v_out, '.0123456789',' ')))
              into Conta_Numeros FROM dual;
              if Conta_Numeros is null then
                Conta_Num_tot := Conta_Num_tot +1;
              end if;
              SELECT LENGTH(TRIM(TRANSLATE(v_out, '!@#$%^&*\`{}[]:";''<>?,./',' ')))
              into Conta_Caracteres FROM dual;
              if Conta_Caracteres is null then
                Conta_Carac_tot := Conta_Carac_tot +1;
              end if;
            End loop;
    
            if Conta_Mai_total < 3 then
             DBMS_OUTPUT.PUT_LINE('A password deve ter pelo menos 3 caracteres Maiusculos');
             is_viewable := false;
            end if; 
            if Conta_min_total < 3 then
             DBMS_OUTPUT.PUT_LINE('A password deve ter pelo menos 3 caracteres Minusculos');
             is_viewable := false;
            end if; 
            if Conta_Carac_tot < 1 then
             DBMS_OUTPUT.PUT_LINE('A password deve ter pelo menos 1 caracter especial');
             is_viewable := false;
            end if; 
            if Conta_Num_tot < 3 then
             DBMS_OUTPUT.PUT_LINE('A password deve ter pelo menos 3 números');
             is_viewable := false;
            end if; 
            if (instr(pass, ' ')) > 0 then 
              DBMS_OUTPUT.PUT_LINE('A password não pode ter espaços em branco');
              is_viewable := false;
            end if;
            if (instr(pass, '_')) > 0 then 
              DBMS_OUTPUT.PUT_LINE('A password não pode conter ' ||'_');
              is_viewable := false;
            end if;
        end if;
       OPEN hist_pass;
        LOOP
          FETCH hist_pass into v_pass,v_ID;
          IF upper(v_pass) = upper(pass) then
           DBMS_OUTPUT.PUT_LINE('A password '|| v_pass ||' já foi usada recentemente use uma outra.');
           dbms_output.put_line('Na Linha: '||v_ID);
           is_viewable := false;
           EXIT;
          end if;
          EXIT WHEN hist_pass%ROWCOUNT >= 24;
        END LOOP;
        IF hist_pass%NOTFOUND THEN
           DBMS_OUTPUT.PUT_LINE('O número mecanográfico' ||Num_mec|| 'não existe na tabela_2!');
        END IF;
        CLOSE hist_pass;    
     IF is_viewable THEN
       IF data_max IS NOT NULL THEN
         UPDATE SYS_USERS SET ACTIVO = 'N',LAST_UPDATE_DATE = sysdate WHERE NUM_MECANOG = Num_mec;
         INSERT INTO SYS_USERS(NUM_MECANOG,NOME,SENHA,CREATION_DATE,LAST_UPDATE_DATE) 
          VALUES(Num_mec,'Rui Filipe Gon?alves Pereira',pass,sysdate,sysdate);
          dbms_output.put_line('Password atualizada com sucesso!');
         COMMIT;
       ELSE
        INSERT INTO SYS_USERS(NUM_MECANOG,NOME,SENHA,CREATION_DATE,LAST_UPDATE_DATE) 
         VALUES(Num_mec,'Rui Filipe Gon?alves Pereira',pass,sysdate,sysdate);
         dbms_output.put_line('Password inserida com sucesso!');
        COMMIT;
       END IF;
      END IF;    
    END Valida_Password;
    
    procedure Altera_Password(pass varchar ,Num_mec number,res out Varchar2) is
    Conta_Maisculas number := 0;
    Conta_Minusculas number := 0;
    Conta_Mai_total number := 0;
    Conta_min_total number := 0;
    Conta_Caracteres number := 0;
    Conta_Numeros number := 0;
    Conta_Carac_tot number := 0;
    Conta_Num_tot number := 0;
    v_length number(38);
    v_out varchar2(30);
    is_viewable BOOLEAN := true; 
    data_max date;
    senha2 varchar(100);
    v_pass siar_utilizadores.SENHA%type;
    name_user siar_utilizadores.nome_utilizador%type;
    CURSOR data_atualizacao(Num_mec number)IS
    SELECT max(DTA_DESATIVO),senha
    FROM siar_utilizadores
    WHERE NUM_MECANOG = Num_mec and ACTIVO = 'S'
    GROUP BY NUM_MECANOG,senha;
    CURSOR hist_pass IS
         SELECT senha
          FROM (SELECT *
                FROM siar_utilizadores
                WHERE NUM_MECANOG = Num_mec  
                order by creation_date desc) 
         WHERE ROWNUM <= 24;
    CURSOR nome IS
         SELECT nome_utilizador
          FROM siar_utilizadores
                WHERE NUM_MECANOG = Num_mec;     
    BEGIN
    OPEN data_atualizacao(Num_mec);
    FETCH data_atualizacao
    INTO data_max,senha2;
    IF data_atualizacao%NOTFOUND THEN
     res:= 'O número mecanográfico' ||Num_mec||' não existe na tabela_1!';
    END IF;
    CLOSE data_atualizacao;
    OPEN nome;
    FETCH nome
    INTO name_user;
    CLOSE nome;
    v_length := length(pass);
    if v_length < 7 then
         res:='A password deve ter pelo menos 7 caracteres';
         is_viewable := false;
    else
          for i in 1..v_length
            Loop
              v_out  := substr(pass,i,1);
              SELECT LENGTH(TRIM(TRANSLATE(v_out, 'ABCDEFGHIKLMNOPQRSTUVXYWZ',' ')))
              into Conta_Maisculas FROM dual;
              if Conta_Maisculas is null then
                Conta_Mai_total := Conta_Mai_total +1;
              end if;
              SELECT LENGTH(TRIM(TRANSLATE(v_out, 'abcdefghiklmnopqrstuvxywz',' ')))
              into Conta_Minusculas FROM dual;
              if Conta_Minusculas is null then
                Conta_min_total := Conta_min_total +1;
              end if;
              SELECT LENGTH(TRIM(TRANSLATE(v_out, '.0123456789',' ')))
              into Conta_Numeros FROM dual;
              if Conta_Numeros is null then
                Conta_Num_tot := Conta_Num_tot +1;
              end if;
              SELECT LENGTH(TRIM(TRANSLATE(v_out, '!@#$%^&*\`{}[]:";''<>?,./',' ')))
              into Conta_Caracteres FROM dual;
              if Conta_Caracteres is null then
                Conta_Carac_tot := Conta_Carac_tot +1;
              end if;
            End loop;
            if Conta_Mai_total < 2 then
            res:='A password deve ter pelo menos 2 caracteres Maiusculos';
             is_viewable := false;
            end if; 
            if Conta_min_total < 3 then
             res:='A password deve ter pelo menos 3 caracteres Minusculos';
             is_viewable := false;
            end if; 
            if Conta_Carac_tot < 1 then
             res:='A password deve ter pelo menos 1 caracter especial';
             is_viewable := false;
            end if; 
            if Conta_Num_tot < 3 then
             res:='A password deve ter pelo menos 3 números';
             is_viewable := false;
            end if; 
            if (instr(pass, ' ')) > 0 then 
              res:='A password não pode ter espaços em branco';
              is_viewable := false;
            end if;
            if (instr(pass, '_')) > 0 then 
              res:='A password não pode conter ' ||'_';
              is_viewable := false;
            end if;
        end if;
       OPEN hist_pass;
        LOOP
          FETCH hist_pass into v_pass;
          IF upper(v_pass) = upper(pass) then
           res:='A password '|| v_pass ||' já foi usada recentemente use uma outra.';
           is_viewable := false;
           EXIT;
          end if;
          EXIT WHEN hist_pass%ROWCOUNT >= 24 or hist_pass%NOTFOUND;
        END LOOP;
        CLOSE hist_pass;    
     IF is_viewable THEN
         UPDATE siar_utilizadores SET SENHA = pass WHERE NUM_MECANOG = Num_mec;
         res:='Password atualizada com sucesso!';
         COMMIT;
      END IF;    
    END Altera_Password;
    
function  Codifica_Telefone(Phone in varchar)
return varchar
is
 v_out varchar2(20);
Begin
    v_out := regexp_replace(substr(Phone,1,5), '[0-9]', 'X')||substr(Phone,6,1)||CHR(32)||substr(Phone,-3,3); 
    Return v_out;
END Codifica_Telefone;
END PKUTENTE;

/
--------------------------------------------------------
--  DDL for Package Body STRING_FNC
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE PACKAGE BODY "STRING_FNC" 
IS
FUNCTION SPLIT2ARRAY (p_in_string VARCHAR2, p_delim VARCHAR2) RETURN t_array
IS

i       number :=0;
pos     number :=0;
lv_str  varchar2(300) := p_in_string;
strings t_array;
BEGIN
--— determine first chuck of string
pos := instr(lv_str,p_delim,1,1);
IF pos = 0 THEN
strings(1) := lv_str;
END IF ;
--— while there are chunks left, loop
WHILE ( pos != 0) LOOP
--—increment counter
i := i + 1;
--— create array element for chuck of string
strings(i) := substr(lv_str,1,pos-1);
--— remove chunk from string
lv_str := substr(lv_str,pos+1,length(lv_str));
--— determine next chunk
pos := instr(lv_str,p_delim,1,1);
--— no last chunk, add to array
IF pos = 0 THEN
strings(i+1) := lv_str;
END IF;
END LOOP;
--— return array
RETURN strings;
END SPLIT2ARRAY;
END;

/

--------------------------------------------------------
--  DDL for Function DFIT
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE FUNCTION "DFIT" RETURN nuMBER is
cnt number := 0;
BEGIN
   cnt := cnt +1;
   RETURN 45;
END DFIT;



/
--------------------------------------------------------
--  DDL for Function DIAS_UTEIS
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE FUNCTION "DIAS_UTEIS" (dt1 date, dt2 date) return number is
  l_date date;
  dta_hoje date;
  weekday_count number := 0;
  --CURSOR c1 IS select dta_feriado from Feriados_Portugal;
  --TYPE name_typ IS TABLE OF c1%ROWTYPE INDEX BY PLS_INTEGER;
  --all_datas name_typ;
  dta_imprep date := to_date('13/02/2018','dd/mm/yyyy');
  preco number(7,2) := 8.8;
  TYPE feriado IS TABLE OF FERIADOS_PORTUGAL.DTA_FERIADO%TYPE
       INDEX BY PLS_INTEGER; 
  feriado_T  feriado;
begin
SELECT dta_feriado BULK COLLECT INTO feriado_T FROM Feriados_Portugal;

--for l_date  in to_char(to_date('27/09/2017', 'dd/mm/yyyy'), 'J') .. to_char(to_date('05/10/2017', 'dd/mm/yyyy'), 'J') loop
--for l_date  in to_char(to_date(dt1,'dd/mm/yyyy'), 'J') .. to_char(to_date(dt2, 'dd/mm/yyyy'), 'J') loop
for l_date  in to_char(dt1, 'J') .. to_char(dt2, 'J') loop
      dta_hoje := to_char(to_date(l_date, 'J'));
      --dbms_output.put_line('A data de hoje ? ||to_char(dta_hoje,'Dy'));
      if to_char(dta_hoje,'Dy') not in ('S?','Dom') then 
          weekday_count := weekday_count+1;
      end if;
      if dta_hoje = dta_imprep then
          weekday_count := weekday_count-1;
      end if;
end loop;
      FOR indx IN 1 ..  feriado_T.COUNT LOOP
        dbms_output.put_line('Os registos s? '|| feriado_T(indx));
      END LOOP;
dbms_output.put_line('Entre hoje e a data X v?ocorrer: ' ||weekday_count || ' dias.'||chr(10)||'Vai receber de quilometros '||weekday_count * preco||' euros');
return weekday_count;
end;


/
--------------------------------------------------------
--  DDL for Function FM_FORMAT_GRUPO_FUNCAO
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE FUNCTION "FM_FORMAT_GRUPO_FUNCAO" (
    nome  IN VARCHAR2,
    tipo  IN VARCHAR2,
    grupo IN NUMBER,
    ref   IN VARCHAR2,
    ref2  IN VARCHAR2,
    tmap  IN VARCHAR2,
    tab   IN VARCHAR2)
  RETURN VARCHAR2
AS
  res          VARCHAR2(800);
  l_ref        VARCHAR2(400);
  auxSubstr    VARCHAR2(5);
  auxSubstr2   VARCHAR2(100);
  auxTimeStamp VARCHAR2(25);
BEGIN
  -- ASSUME SE QUE REF2 ESTA NA MESMA TABELA QUE REF
  IF ref2 IS NOT NULL THEN
    -- REF2 especiais
    l_ref := 'NVL(TRIM(' || ref || '), TRIM(' || ref2 || '))';
  ELSE
    l_ref := 'TRIM(' || ref || ')';
  END IF;
--
CASE grupo
WHEN 2 THEN
  CASE
  WHEN UPPER(TMAP)                  ='HORAS' THEN
    res                            := 'FM_FORMAT_HORAS('||l_ref||')';
  WHEN UPPER(TMAP)                  ='TELEMOVEL' THEN
    res                            := 'FM_FILTRA_TELEMOVEL('||l_ref||')';
  WHEN UPPER(TMAP)                  ='TELEFONE' THEN
    res                            := 'FM_FILTRA_TELEFONE('||l_ref||')';
  WHEN UPPER(TMAP)                  ='COD_POSTAL' THEN
    res                            := 'FM_FORMAT_POSTAL('||l_ref||', '''||upper(TMAP)||''')';
  WHEN UPPER(TMAP)                  ='SEQ_POSTAL' THEN
    res                            := 'FM_FORMAT_POSTAL('||l_ref||', '''||upper(TMAP)||''')';
  WHEN instr(UPPER(TMAP), 'SUBSTR') = 1 THEN
    IF instr(UPPER(TMAP), '-')      = 7 THEN
      auxSubstr                    := SUBSTR(UPPER(TMAP), 8);
      res                          := 'CASE WHEN LENGTH('||l_ref||') > '||auxSubstr||' THEN SUBSTR('||l_ref||', -'||auxSubstr||') ELSE '||l_ref||' END';
    ELSE
      auxSubstr := SUBSTR(UPPER(TMAP), 7);
      res       := 'CASE WHEN LENGTH('||l_ref||') > '||auxSubstr||' THEN SUBSTR('||l_ref||', 1, '||auxSubstr||') ELSE '||l_ref||' END';
    END IF;
  WHEN instr(UPPER(TMAP), 'PRIMEIRO_ULT_') = 1 THEN
    res                                   := 'FM_FILTRA_PRIMEIRO_ULT_NOME('||l_ref||', ' || SUBSTR(tmap, 14) || ')';
  WHEN UPPER(TMAP)                         ='DISTRITOS' THEN
    res                                   := 'FM_FILTRA_DISTRITOS('||l_ref||')';
  WHEN instr(UPPER(TMAP), 'LENGTH')        = 1 THEN
    res                                   := 'CASE WHEN LENGTH('||l_ref||') = '||SUBSTR(TMAP, 7)||' THEN '||l_ref||' ELSE NULL END';
  WHEN UPPER(TMAP)                         ='CC_BI_NUM' THEN
    res                                   := 'CASE WHEN SUBSTR('||l_ref||', 1, 2) = ''CC'' OR SUBSTR('||l_ref||', 1, 2) = ''BI'' THEN SUBSTR('||l_ref||', 3) ELSE REPLACE('||l_ref||', chr(0), '''') END';
  WHEN UPPER(TMAP)                         ='CC_BI_TIPO' THEN
    res                                   := 'CASE WHEN SUBSTR('||l_ref||', 1, 2) = ''CC'' OR SUBSTR('||l_ref||', 1, 2) = ''BI'' THEN ''B'' ELSE NULL END'; -- 'B' na SYS_DOMINIOS, dominio 'DOCI' significa Bilhete de Identidade/Cartao de Cidadao
  WHEN UPPER(TMAP)                         ='TIPO_CONTACTO' THEN
    res                                   := 'FM_FORMAT_TIPO_CONTACTO('||l_ref||')';
  WHEN INSTR(UPPER(TMAP), 'OBITOS_')       = 1 THEN
    res                                   := 'MIN(' || l_ref || ')';
    IF UPPER(TMAP)                         = 'OBITOS_HORA' THEN
      res                                 := 'FM_FORMAT_HORAS('||res||')';
    END IF;
  WHEN instr(UPPER(TMAP), 'NULL_OR_NOT_IS_') = 1 THEN
    IF instr(UPPER(TMAP), 'S')               = LENGTH(TMAP) THEN --S no fim, ou seja, null ?? not null ?im
      res                                   := 'CASE WHEN '||l_ref||' IS NULL THEN ''N'' ELSE ''S'' END';
    ELSE
      res := 'CASE WHEN '||l_ref||' IS NULL THEN ''S'' ELSE ''N'' END';
    END IF;
  WHEN instr(UPPER(TMAP), 'IECO')= 1 THEN
    auxSubstr                   := SUBSTR(TMAP, 5, 2); -- num isencao ate dois digitos
    res                         := 'CASE WHEN TRIM(' || ref || ')='''|| auxSubstr || ''' OR TRIM(' || ref2 || ')='''|| auxSubstr || ''' THEN ';
    auxSubstr2                  := SUBSTR(TMAP, 8);
    IF auxSubstr2               IS NULL THEN
      res                       := res || '''S'' ELSE ''N'' END';
    ELSE
      res := res || auxSubstr2 || ' ELSE NULL END';
    END IF;
  WHEN UPPER(TMAP) = 'ZERO_E_NULO' THEN
    res           := 'CASE WHEN '||l_ref||'=0 THEN NULL ELSE '||l_ref||' END';
  WHEN UPPER(TMAP) = 'ESPEC_NOME' THEN
    res           := 'FM_GET_ESPEC_NOME('||l_ref||')';
  WHEN UPPER(TMAP) = 'CHECK_COD_CS' THEN
    res           := 'CASE WHEN LENGTH(' || l_ref || ')=9 THEN FM_GETVAL.G3(to_char(' || l_ref || '), ''COD_CS'') WHEN LENGTH(' || l_ref || ')=7 THEN ' || l_ref || ' ELSE NULL END';
  ELSE
    res := l_ref;
  END CASE;
WHEN 3 THEN
  res:= 'FM_GETVAL.G3(to_char(' || l_ref || '), ''' || tmap || ''')';
  --WHEN 4 THEN
  --res:= 'FM_GETVAL.G4(to_char(' || l_ref || '), ''' || nome || ''', '''||ref||''', '''||tmap||''', '''||tab||''')';
  --res := l_ref;
ELSE
  NULL;
END CASE;
-- check se ?umber
IF instr(upper(tipo), 'NUMBER') > 0 AND grupo != 4 THEN
  res                          := 'fm_to_number('||res||')';
  IF upper(tmap)                = 'ESPEC_COD2' THEN
    res                        := 'DISTINCT ' || res;
  END IF;
  --res := regexp_replace(upper(res), ' AS (\w+)(\d{2}_){3}\d{6}$', '');
  --res := 'case fm_is_number('||res||') when 1 then to_number('||res||') else null end';
END IF;
-- prevenir erro da descricao da coluna ao usar o procedimento fm_query_csv
--IF LENGTH(res) > 40 THEN
--EXECUTE immediate 'select to_char(systimestamp, ''hh24_mi_ss_FF'') from dual' INTO auxTimeStamp;
--res := res || ' AS COL_'||ABS(dbms_random.random)||'_'||auxTimeStamp;
--END IF;
--
RETURN res;
END;


/
--------------------------------------------------------
--  DDL for Function FM_FORMAT_HORAS
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE FUNCTION "FM_FORMAT_HORAS" (
    valor IN VARCHAR2)
  RETURN NUMBER
AS
  res      NUMBER;
  horas    NUMBER;
  minutos  NUMBER;
  segundos NUMBER;
  valor_   VARCHAR2(50);
BEGIN
  BEGIN
    dbms_output.put_line('INSTR(valor, '':'' - 1)'|| ' - ' ||INSTR(valor, ':')); -- 
    IF INSTR(valor, ':') > 0 THEN -- vai devolver a posi? da string na qual encontra : pela primeira vez
      horas := to_number(regexp_substr(valor, '[^:]+'));-- devolve as horas ou seja todos os valores at?ncontrar dois pontos pela primeira vez
      minutos := to_number(SUBSTR(regexp_substr(valor, ':[^:]+'), 2)); -- devolve todos os valores entre os primeiros : e os segundos : 
      -- ou seja :30 e depois unicamente os minutos 30
      IF instr(valor, ':', 1, 2) > 0 THEN -- devolve a posi? do segundo : ou seja 6 no exemplo '10:30:12', pois valida se h?egundos
        segundos := to_number(regexp_substr(valor, '[^:]+$')); -- devolve todos os valores que v?depois dos 2? : ou se ja os Segundos 
      ELSE
        segundos := 0;
      END IF;
      res := horas * 3600 + minutos * 60 + segundos;
      --ELSIF INSTR(valor, ',') > 0 THEN
    ELSE
      valor_ := valor;
      IF instr(valor, '.') > 0 THEN
        valor_ := REPLACE(valor, '.', ',');
      END IF;
      horas := TRUNC(to_number(valor_));
      minutos := (to_number(valor_) - TRUNC(to_number(valor_)))*100;
      --
      -- possiveis correcoes
      IF horas = 24 THEN
        horas := 0;
      elsif horas > 24 THEN
        RETURN NULL;
        --horas := trunc(to_number(valor)/10);
        --minutos := (to_number(valor)/10 - trunc(to_number(valor)/10))*100;
      END IF;
      res := horas * 3600 + minutos * 60;
      --ELSE
      --res := NULL;
    END IF;
  EXCEPTION
  WHEN OTHERS THEN
    --dbms_output.put_line('!!!ERRO!!!');
    --dbms_output.put_line(SQLCODE);
    res := NULL;
  END;
RETURN res;
END;


/
--------------------------------------------------------
--  DDL for Function FM_GET_TAB_FROM_REF
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE FUNCTION "FM_GET_TAB_FROM_REF" (
    ref         IN VARCHAR2)
  RETURN VARCHAR2
AS
  res VARCHAR2(200);
  BEGIN
  res := REGEXP_SUBSTR(ref, '[^\.]+');
  RETURN res;
END;


/
--------------------------------------------------------
--  DDL for Function F2
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE FUNCTION "F2" (p_p1 NUMBER)
RETURN NUMBER PARALLEL_ENABLE IS
BEGIN
 RETURN p_p1 * 2;
end f2; 

/
--------------------------------------------------------
--  DDL for Function GERA_PASS_USER
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE FUNCTION "GERA_PASS_USER" (NUM_MEC IN NUMBER)
RETURN VARCHAR IS
CURSOR Gera_pass IS
select Num_Mecanog,creation_date,Nome_Utilizador,Senha 
from siar_utilizadores where Num_Mecanog = NUM_MEC;
REC Gera_pass%ROWTYPE;
vStartIdx binary_integer;vEndIdx binary_integer; 
vCurValue varchar2(2000); lvOutPut varchar2(2000); nome_nov varchar(160);ult_valor varchar(20);
res number;
BEGIN
OPEN Gera_pass;
     LOOP
     FETCH Gera_pass INTO REC;
     IF rec.Num_Mecanog IS NULL THEN 
         dbms_output.put_line('O n?mero mecanogr?co '||NUM_MEC|| ' n?existe!');
         EXIT;
     END IF;
     nome_nov := replace(REC.Nome_Utilizador,' ',',');
      EXIT WHEN Gera_pass%NOTFOUND;
        vStartIdx := 0;
        vEndIdx   := instr(nome_nov, ','); 
        while(vEndIdx > 0) loop
            --dbms_output.put_line('vEndIdx: '|| to_char(vEndIdx));
            --dbms_output.put_line('vStartIdx+: '|| to_char(vStartIdx+1));
            --dbms_output.put_line('vEndIdx - vStartIdx - 1: '||to_char(vEndIdx - vStartIdx - 1));
            vCurValue := substr(nome_nov, vStartIdx+1, vEndIdx - vStartIdx - 1); --Devolve Rui
            vCurValue := substr(vCurValue,1,2);
            IF vCurValue is null THEN
              vCurValue := vCurValue;
              lvOutPut := lvOutPut || vCurValue;
            ELSE
              vCurValue := vCurValue || '.';
              lvOutPut := lvOutPut || vCurValue;
            END IF;
            vStartIdx := vEndIdx;
            vEndIdx := instr(nome_nov, ',', vStartIdx + 1); 
     END LOOP;
     vCurValue := lvOutPut || substr(nome_nov, vStartIdx+1);
     dbms_output.put_line('O valor ?'||vCurValue);
     select regexp_replace(REC.senha, '[^[:digit:]]', '') 
     into ult_valor 
     from siar_utilizadores 
     where nome_utilizador not in('Admnistrador','Gestor') and num_mecanog = rec.Num_Mecanog;
     --res := is_number(ult_valor);
     END LOOP;
     CLOSE Gera_pass;
     dbms_output.put_line('?'||ult_valor);
     --dbms_output.put_line('A data de cria?: '||to_char(REC.creation_date,'DD/MM/YYYY'));     
     --dbms_output.put_line('A data de cria?: '||to_char(REC.creation_date,'YY'));  
     IF vCurValue IS NOT NULL THEN
        If ult_valor is not null THEN
            ult_valor := ult_valor + 1;
            --vCurValue := lvOutPut || substr(nome_nov, vStartIdx+1)||'_'||GERA_NUM_USER.NEXTVAL;
            vCurValue := lvOutPut || substr(nome_nov, vStartIdx+1)||ult_valor;
            dbms_output.put_line('Passe Reniciada para :'||vCurValue);  
        ELSE    
            vCurValue := lvOutPut || substr(nome_nov, vStartIdx+1);
            IF vCurValue = REC.senha THEN
               vCurValue := lvOutPut || substr(nome_nov, vStartIdx+1)||'1';
            END IF;
            dbms_output.put_line('A sua passe ?'||vCurValue); 
        END IF;
     END IF;
     --update siar_utilizadores set senha = vCurValue where num_mecanog = REC.Num_Mecanog;
     --commit;
RETURN vCurValue;
END;

/
--------------------------------------------------------
--  DDL for Function GERA_PASS_USER_NOME
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE FUNCTION "GERA_PASS_USER_NOME" (NOME IN VARCHAR)
RETURN VARCHAR IS
CURSOR Gera_pass IS
select max(Num_Mecanog),max(Senha) as Senha
from siar_utilizadores where upper(nome_utilizador) = upper(nome);
REC Gera_pass%ROWTYPE;
vStartIdx binary_integer;vEndIdx binary_integer; 
vCurValue varchar2(2000); lvOutPut varchar2(2000); nome_nov varchar(160);ult_valor varchar(20);
res number;
BEGIN
OPEN Gera_pass;
     LOOP
     FETCH Gera_pass INTO REC;
     nome_nov := replace(NOME,' ',',');
      EXIT WHEN Gera_pass%NOTFOUND;
        vStartIdx := 0;
        vEndIdx   := instr(nome_nov, ','); 
        while(vEndIdx > 0) loop
            dbms_output.put_line('vEndIdx: '|| to_char(vEndIdx));
            dbms_output.put_line('vStartIdx+: '|| to_char(vStartIdx+1));
            dbms_output.put_line('vEndIdx - vStartIdx - 1: '||to_char(vEndIdx - vStartIdx - 1));
            vCurValue := substr(nome_nov, vStartIdx+1, vEndIdx - vStartIdx - 1); --Devolve Rui
            vCurValue := substr(vCurValue,1,2);
            dbms_output.put_line('vCurValue1: '||vCurValue);
            IF vCurValue is null THEN
              vCurValue := vCurValue;
              dbms_output.put_line('vCurValue2: '||vCurValue);
              lvOutPut := lvOutPut || vCurValue;
              dbms_output.put_line('lvOutPut: '||lvOutPut);
            ELSE
              vCurValue := vCurValue || '.';
              dbms_output.put_line('vCurValue2: '||vCurValue);
              lvOutPut := lvOutPut || vCurValue;
              dbms_output.put_line('lvOutPut: '||lvOutPut);
            END IF;
            vStartIdx := vEndIdx;
            vEndIdx := instr(nome_nov, ',', vStartIdx + 1); 
            dbms_output.put_line('vEndIdx: '||vEndIdx); -- Lugar onde est? virgula
     END LOOP;
     vCurValue := lvOutPut || substr(nome_nov, vStartIdx+1);
     dbms_output.put_line('O valor ?'||vCurValue);
     select max(regexp_replace(senha, '[^[:digit:]]', '')) 
     into ult_valor 
     from siar_utilizadores a 
     where nome_utilizador not in('Admnistrador','Gestor') and upper(nome_utilizador) = upper(Nome);
     --res := is_number(ult_valor);
     END LOOP;
     CLOSE Gera_pass;
     dbms_output.put_line('?'||ult_valor);
     IF vCurValue IS NOT NULL THEN
        If ult_valor is not null THEN
            ult_valor := ult_valor + 1;
            --vCurValue := lvOutPut || substr(nome_nov, vStartIdx+1)||'_'||GERA_NUM_USER.NEXTVAL;
            vCurValue := lvOutPut || substr(nome_nov, vStartIdx+1)||ult_valor;
            dbms_output.put_line('Passe Reniciada para :'||vCurValue);  
        ELSE    
            vCurValue := lvOutPut || substr(nome_nov, vStartIdx+1);
            IF vCurValue = REC.senha THEN
               vCurValue := lvOutPut || substr(nome_nov, vStartIdx+1)||'1';
            END IF;
            dbms_output.put_line('A sua passe ?'||vCurValue); 
        END IF;
     END IF;
     --update siar_utilizadores set senha = vCurValue where num_mecanog = REC.Num_Mecanog;
     --commit;
RETURN vCurValue;
END;

/
--------------------------------------------------------
--  DDL for Function IS_NUMBER
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE FUNCTION "IS_NUMBER" (p_string IN VARCHAR2)
   RETURN INT
IS
   v_new_num NUMBER;
BEGIN
   v_new_num := TO_NUMBER(p_string);
   RETURN 1;
EXCEPTION
WHEN VALUE_ERROR THEN
   RETURN 0;
END is_number;

/
--------------------------------------------------------
--  DDL for Function LISTA_ORDENADA
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE FUNCTION "LISTA_ORDENADA" (num1 in int, num2 in int,num3 in int,num4 in int,num5 in int,num6 in int)
return array_t IS
   --type array_t2 is varray(6) of int(38);
   --array array_t := array_t(5,4,8,9,2,7);
   array array_t;
   temp int(38);curr int(38);prev int(38);n int(38); 
begin
    array := array_t(num1,num2,num3,num4,num5,num6);
    n:= array.count;
    for i in 2..array.count loop
       curr := i;
       prev := i-1;
       --dbms_output.put_line(' key_I '||  curr);
       --dbms_output.put_line('O valor ?|| prev);
          while(array(prev)> array(Curr))loop
           --dbms_output.put_line('array(Curr) '|| array(Curr));
           --dbms_output.put_line('array(prev) '|| array(prev));
           temp := array(curr);
           array(curr):= array(prev);
           array(prev):= temp;
           curr:= curr-1;
           prev:= prev-1;
             IF curr=1 THEN
                 EXIT;
             END IF;                 
          end loop;
  END LOOP;
  return array;
   /*for i in 1..array.count loop
          dbms_output.put_line('O valor ?|| array(i));
   end loop;*/
end;



/
--------------------------------------------------------
--  DDL for Function MENOR_VALOR
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE FUNCTION "MENOR_VALOR" (num1 in int, num2 in int,num3 in int,num4 in int,num5 in int,num6 in int,minvalor out int)
return number IS
   --type array_t is varray(6) of int(38);
   --array array_t := array_t(5,4,8,9,2,7);
   array array_t;
   minindex int(38); temp int(38);
   --minvalor int(38); 
begin
array := array_t(num1,num2,num3,num4,num5,num6);
  for i in 1..array.count loop
        minvalor := array(i);
        minindex := i;
          for j in 1..array.count loop -- loop para percorre o proximo elemento da lista e comparar com o minimo atual
            if(array(j)< minvalor)then -- se for menor que o minimo atual ?eita uma troca de valores
               minvalor := array(j);
               minindex :=j;
            end if;
          end loop;
           if(minvalor < array(i))then -- Valida se o minimo obtido ?enor que o minimo na variav?minvalor
              temp := array(i);
              array(i) := array(minindex);
              array(minindex) := temp;
            end if;
  END LOOP;
  --return array;
 return minvalor;
 -- dbms_output.put_line('O menor elemento ?|| minvalor);
 /*for i in 1..array.count loop
          dbms_output.put_line('O valor ?|| array(i));
   end loop;*/
end;



/
--------------------------------------------------------
--  DDL for Function ORDENACAO_BOLHA
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE FUNCTION "ORDENACAO_BOLHA" (num1 in int, num2 in int,num3 in int,num4 in int,num5 in int,num6 in int)
return array_t IS
-- declare 
   --type array_t is varray(6) of int(38);
   --array array_t := array_t(5,4,8,9,2,7);
   swapped BOOLEAN;
   array array_t;
   temp int(38);curr int(38);prev int(38);n int(38); 
begin
    array := array_t(num1,num2,num3,num4,num5,num6);
LOOP
swapped := false;
FOR i IN 2 .. array.LAST
LOOP
IF array(i-1) > array(i)
THEN
-- swap records
temp := array(i);
array(i) := array(i-1);
array(i-1) := temp;
/*
Marque essa troca ocorreu. nota que marcamos como verdade s?ntro
O bloco if, o que significa que um swap realmente ocorreu
*/ 
swapped := true;
END IF;
END LOOP;
-- Se passamos pela mesa sem trocar, terminamos, ent?saia
EXIT WHEN NOT swapped;
END LOOP; 
  return array;
          --curr := i;
          --prev := i-1;
          --dbms_output.put_line(' key_I '||  curr);
          --dbms_output.put_line('O valor ?|| prev);
          --while(array(prev)> array(Curr))loop
           /*temp := array(curr);
           array(curr):= array(prev);
           array(prev):= temp;
           curr:= curr-1;
           prev:= prev-1;*/
             /*IF curr=1 THEN
                 EXIT;
             END IF;*/                 
   /*for i in 1..array.count loop
          dbms_output.put_line('O valor ?|| array(i));
   end loop;*/
end;



/
--------------------------------------------------------
--  DDL for Function REPLACE_NAME
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE FUNCTION "REPLACE_NAME" (nome CHAR,char_old char,char_new char) RETURN CHAR IS
   CURSOR emp_cursor IS
   select EMPLOYEE_ID,FIRST_NAME from employees;
   --total_wages NUMBER(11, 2) := 0;
   --counter NUMBER(10) := 1;

   TYPE fname IS TABLE OF employees.FIRST_NAME%TYPE;
   TYPE emp_id IS TABLE OF Employees.Employee_Id%TYPE;
   fnames fname;
   emp_ids emp_id;
BEGIN
   OPEN emp_cursor;
   FETCH emp_cursor BULK COLLECT INTO emp_ids,fnames;
   CLOSE emp_cursor;

   FOR inx1 IN 1..emp_ids.count LOOP
     if fnames(inx1) = nome Then
      fnames(inx1):= REPLACE(fnames(inx1), char_old, char_new);
      dbms_output.put_line('O nome ?'|| fnames(inx1));
     end if;
       --emp_record.commission_pct := NVL(emp_record.commission_pct, 0);
       /*total_wages := total_wages + emp_record.salary
                   + emp_record.commission_pct;
       DBMS_OUTPUT.PUT_LINE('Loop number = ' || counter ||
          '; Wages = '|| TO_CHAR(total_wages)); 
       counter := counter + 1;*/ 
   END LOOP;
   FORALL x IN emp_ids.first .. emp_ids.last
       UPDATE employees SET first_name = fnames(x) WHERE Employee_Id = emp_ids(x);
   RETURN nome;
END;

/
--------------------------------------------------------
--  DDL for Synonymn PLITBLM
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE PUBLIC SYNONYM "PLITBLM" FOR "SYS"."PLITBLM";
--------------------------------------------------------
--  DDL for Synonymn DUAL
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE PUBLIC SYNONYM "DUAL" FOR "SYS"."DUAL";
--------------------------------------------------------
--  DDL for Synonymn DBMS_OUTPUT
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE PUBLIC SYNONYM "DBMS_OUTPUT" FOR "SYS"."DBMS_OUTPUT";
--------------------------------------------------------
--  DDL for Synonymn DBMS_UTILITY
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE PUBLIC SYNONYM "DBMS_UTILITY" FOR "SYS"."DBMS_UTILITY";
--------------------------------------------------------
--  DDL for Synonymn DBMS_SQL
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE PUBLIC SYNONYM "DBMS_SQL" FOR "SYS"."DBMS_SQL";
--------------------------------------------------------
--  DDL for Synonymn UTL_FILE
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE PUBLIC SYNONYM "UTL_FILE" FOR "SYS"."UTL_FILE";
--------------------------------------------------------
--  DDL for Synonymn UTL_SMTP
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE PUBLIC SYNONYM "UTL_SMTP" FOR "SYS"."UTL_SMTP";
--------------------------------------------------------
--  DDL for Synonymn UTL_TCP
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE PUBLIC SYNONYM "UTL_TCP" FOR "SYS"."UTL_TCP";
--------------------------------------------------------
--  DDL for Synonymn DBMS_LOB
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE PUBLIC SYNONYM "DBMS_LOB" FOR "SYS"."DBMS_LOB";
--------------------------------------------------------
--  DDL for Synonymn DBMS_XMLGEN
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE PUBLIC SYNONYM "DBMS_XMLGEN" FOR "SYS"."DBMS_XMLGEN";
--------------------------------------------------------
--  DDL for Synonymn UTL_COMPRESS
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE PUBLIC SYNONYM "UTL_COMPRESS" FOR "SYS"."UTL_COMPRESS";
--------------------------------------------------------
--  DDL for Synonymn UTL_I18N
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE PUBLIC SYNONYM "UTL_I18N" FOR "SYS"."UTL_I18N";
--------------------------------------------------------
--  DDL for Synonymn UTL_RAW
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE PUBLIC SYNONYM "UTL_RAW" FOR "SYS"."UTL_RAW";
--------------------------------------------------------
--  DDL for Synonymn XMLTYPE
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE PUBLIC SYNONYM "XMLTYPE" FOR "SYS"."XMLTYPE";
--------------------------------------------------------
--  DDL for Synonymn ALL_CONSTRAINTS
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE PUBLIC SYNONYM "ALL_CONSTRAINTS" FOR "SYS"."ALL_CONSTRAINTS";
--------------------------------------------------------
--  DDL for Synonymn ALL_TRIGGERS
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE PUBLIC SYNONYM "ALL_TRIGGERS" FOR "SYS"."ALL_TRIGGERS";
--------------------------------------------------------
--  Constraints for Table BCK_SIAR_MARCACOES
--------------------------------------------------------

  ALTER TABLE "BCK_SIAR_MARCACOES" ADD PRIMARY KEY ("ID_MARCBCK")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;

--------------------------------------------------------
--  Constraints for Table CUSTOMERS
--------------------------------------------------------

  ALTER TABLE "CUSTOMERS" MODIFY ("CUTS_CITY" NOT NULL ENABLE);
  ALTER TABLE "CUSTOMERS" MODIFY ("CUST_LAST_NAME" NOT NULL ENABLE);
  ALTER TABLE "CUSTOMERS" MODIFY ("CUST_ID" NOT NULL ENABLE);

--------------------------------------------------------
--  Constraints for Table EMP
--------------------------------------------------------

  ALTER TABLE "EMP" ADD CONSTRAINT "EMP_COL1_PK" PRIMARY KEY ("EMP_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;

--------------------------------------------------------
--  Constraints for Table EMPLOYEES
--------------------------------------------------------

  ALTER TABLE "EMPLOYEES" MODIFY ("JOB_ID" NOT NULL ENABLE);
  ALTER TABLE "EMPLOYEES" MODIFY ("HIRE_DATE" NOT NULL ENABLE);
  ALTER TABLE "EMPLOYEES" MODIFY ("LAST_NAME" NOT NULL ENABLE);
  ALTER TABLE "EMPLOYEES" MODIFY ("EMPLOYEE_ID" NOT NULL ENABLE);

--------------------------------------------------------
--  Constraints for Table FERIADOS_PORTUGAL
--------------------------------------------------------

  ALTER TABLE "FERIADOS_PORTUGAL" MODIFY ("DTA_FERIADO" NOT NULL ENABLE);

--------------------------------------------------------
--  Constraints for Table PRODUCTS
--------------------------------------------------------

  ALTER TABLE "PRODUCTS" MODIFY ("PROD_LIST_PRICE" NOT NULL ENABLE);
  ALTER TABLE "PRODUCTS" MODIFY ("PROD_NAME" NOT NULL ENABLE);
  ALTER TABLE "PRODUCTS" MODIFY ("PROD_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table SIAR_EMENTAS
--------------------------------------------------------

  ALTER TABLE "SIAR_EMENTAS" ADD CONSTRAINT "PK_EMENTA" PRIMARY KEY ("DTA_REFEICAO", "COD_REFEICAO", "COD_PRATO")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
--------------------------------------------------------
--  Constraints for Table SIAR_MARCACOES
--------------------------------------------------------

  ALTER TABLE "SIAR_MARCACOES" ADD CONSTRAINT "PK_MARCACAO" PRIMARY KEY ("NUM_MECANOG", "DTA_REFEICAO", "COD_REFEICAO")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
--------------------------------------------------------
--  Constraints for Table SIAR_PARAMETROS
--------------------------------------------------------

  ALTER TABLE "SIAR_PARAMETROS" ADD CONSTRAINT "PK_PARAMETRO" PRIMARY KEY ("COD_PARAMETRO")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
--------------------------------------------------------
--  Constraints for Table SIAR_PRATO
--------------------------------------------------------

  ALTER TABLE "SIAR_PRATO" ADD CONSTRAINT "PK_PRATO" PRIMARY KEY ("COD_PRATO")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
--------------------------------------------------------
--  Constraints for Table SIAR_REFEICAO
--------------------------------------------------------

  ALTER TABLE "SIAR_REFEICAO" ADD CONSTRAINT "PK_REFEICAO" PRIMARY KEY ("COD_REFEICAO")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
--------------------------------------------------------
--  Constraints for Table SIAR_UTILIZADORES
--------------------------------------------------------

  ALTER TABLE "SIAR_UTILIZADORES" ADD CONSTRAINT "PK_UTILIZADORES" PRIMARY KEY ("NUM_MECANOG")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;

--------------------------------------------------------
--  Ref Constraints for Table SIAR_ANULACOES
--------------------------------------------------------

  ALTER TABLE "SIAR_ANULACOES" ADD CONSTRAINT "FK_PRATO2" FOREIGN KEY ("COD_PRATO")
	  REFERENCES "SIAR_PRATO" ("COD_PRATO") ENABLE;
  ALTER TABLE "SIAR_ANULACOES" ADD CONSTRAINT "FK_REFEICAO2" FOREIGN KEY ("COD_REFEICAO")
	  REFERENCES "SIAR_REFEICAO" ("COD_REFEICAO") ENABLE;
  ALTER TABLE "SIAR_ANULACOES" ADD CONSTRAINT "FK_UTILIZADOR2" FOREIGN KEY ("NUM_MEC")
	  REFERENCES "SIAR_UTILIZADORES" ("NUM_MECANOG") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table SIAR_EMENTAS
--------------------------------------------------------

  ALTER TABLE "SIAR_EMENTAS" ADD CONSTRAINT "FK_PR2" FOREIGN KEY ("COD_PRATO")
	  REFERENCES "SIAR_PRATO" ("COD_PRATO") ENABLE;
  ALTER TABLE "SIAR_EMENTAS" ADD CONSTRAINT "FK_REF2" FOREIGN KEY ("COD_REFEICAO")
	  REFERENCES "SIAR_REFEICAO" ("COD_REFEICAO") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table SIAR_MARCACOES
--------------------------------------------------------

  ALTER TABLE "SIAR_MARCACOES" ADD CONSTRAINT "FK_PRATO" FOREIGN KEY ("COD_PRATO")
	  REFERENCES "SIAR_PRATO" ("COD_PRATO") ENABLE;
  ALTER TABLE "SIAR_MARCACOES" ADD CONSTRAINT "FK_REFEICAO" FOREIGN KEY ("COD_REFEICAO")
	  REFERENCES "SIAR_REFEICAO" ("COD_REFEICAO") ENABLE;
  ALTER TABLE "SIAR_MARCACOES" ADD CONSTRAINT "FK_UTILIZADOR" FOREIGN KEY ("NUM_MECANOG")
	  REFERENCES "SIAR_UTILIZADORES" ("NUM_MECANOG") ENABLE;
