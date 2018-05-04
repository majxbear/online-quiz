/*==============================================================*/
/* Table: QUIZ_USER                                             */
/*==============================================================*/
CREATE TABLE QUIZ_USER  (
   ID                   NUMBER(20)                      NOT NULL,
   USERNAME             VARCHAR2(127),
   PASSWORD             VARCHAR2(127),
   USER_TYPE            INT,
   REALNAME             VARCHAR2(100),
   EMAIL                VARCHAR2(100),
   CONSTRAINT PK_QUIZ_USER PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: Q_QUESTION                                            */
/*==============================================================*/
CREATE TABLE Q_QUESTION  (
   ID                   NUMBER(20)                      NOT NULL,
   NAME                 VARCHAR2(3000),
   QUESTION_TEXT        VARCHAR2(3000),
   KEYWORDS             VARCHAR2(200),
   QTYPE                INT,
   GENERAL_FEEDBACK     VARCHAR2(3000),
   DEFAULT_MARK         DECIMAL,
   CREATEDBY            NUMBER(20),
   MODIFIEDBY           NUMBER(20),
   UUID_FLAG            VARCHAR2(100),
   ATTEMPTS_ALLOWED     INT,
   ATTEMPT_PENALTY      DECIMAL,
   CONSTRAINT PK_Q_QUESTION PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: Q_QUESTION_ANSWER                                     */
/*==============================================================*/
CREATE TABLE Q_QUESTION_ANSWER  (
   ID                   NUMBER(20)                      NOT NULL,
   QUESTION_ID          NUMBER(20),
   ANSWER               VARCHAR2(3000),
   GRADE                DECIMAL,
   FEEDBACK             VARCHAR2(3000),
   ANSWER_ORDER         INT,
   IS_RIGHT_ANSWER      INT,
   CONSTRAINT PK_Q_QUESTION_ANSWER PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: Q_QUESTION_HINT                                       */
/*==============================================================*/
CREATE TABLE Q_QUESTION_HINT  (
   ID                   NUMBER(20)                      NOT NULL,
   QUESTION_ID          NUMBER(20),
   HINT                 VARCHAR2(3000),
   HINT_ORDER           INT,
   CONSTRAINT PK_Q_QUESTION_HINT PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: Q_QUESTION_TYPE                                       */
/*==============================================================*/
CREATE TABLE Q_QUESTION_TYPE  (
   ID                   NUMBER(20)                      NOT NULL,
   CODE_NUMBER          VARCHAR2(200),
   TYPE_NAME            VARCHAR2(200),
   STATUS               INT,
   INTRO                VARCHAR2(1000),
   IS_DEFAULT           INT,
   SHORT_NAME           VARCHAR2(200),
   CONSTRAINT PK_Q_QUESTION_TYPE PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: Q_QUIZ                                                */
/*==============================================================*/
CREATE TABLE Q_QUIZ  (
   ID                   NUMBER(20)                      NOT NULL,
   NAME                 VARCHAR2(1000),
   INTRO                VARCHAR2(3000),
   TIMEOPEN             DATE,
   TIMECLOSE            DATE,
   ATTEMPTS_ALLOWED     INT,
   MINUTES_ALLOWED      INT,
   GRADE_METHOD         INT,
   FEEDBACK_TYPE        INT,
   REVIEWATTEMPT        INT,
   REVIEWCORRECTNESS    INT,
   REVIEWMARKS          INT,
   REVIEWSPECIALFEEDBACK INT,
   REVIEWGENERALFEEDBACK INT,
   REVIEWRIGHTANSWER    INT,
   REVIEWSCOREFEEDBACK  INT,
   LEVEL1_FEEDBACK_TEXT VARCHAR2(3000),
   LEVEL2_FEEDBACK_TEXT VARCHAR2(3000),
   LEVEL3_FEEDBACK_TEXT VARCHAR2(3000),
   LEVEL4_FEEDBACK_TEXT VARCHAR2(3000),
   LEVEL5_FEEDBACK_TEXT VARCHAR2(3000),
   UUID_FLAG            VARCHAR2(100),
   CONSTRAINT PK_Q_QUIZ PRIMARY KEY (ID)
);

COMMENT ON COLUMN Q_QUIZ.NAME IS
'测验名称';

COMMENT ON COLUMN Q_QUIZ.INTRO IS
'简介';

COMMENT ON COLUMN Q_QUIZ.TIMEOPEN IS
'开始时间';

COMMENT ON COLUMN Q_QUIZ.TIMECLOSE IS
'关闭时间';

COMMENT ON COLUMN Q_QUIZ.ATTEMPTS_ALLOWED IS
'允许作答次数';

COMMENT ON COLUMN Q_QUIZ.GRADE_METHOD IS
'计分方法';

COMMENT ON COLUMN Q_QUIZ.FEEDBACK_TYPE IS
'反馈模式';

COMMENT ON COLUMN Q_QUIZ.REVIEWATTEMPT IS
'本次作答';

COMMENT ON COLUMN Q_QUIZ.REVIEWCORRECTNESS IS
'正误';

COMMENT ON COLUMN Q_QUIZ.REVIEWMARKS IS
'得分';

COMMENT ON COLUMN Q_QUIZ.REVIEWSPECIALFEEDBACK IS
'特殊反馈';

COMMENT ON COLUMN Q_QUIZ.REVIEWGENERALFEEDBACK IS
'通用题目反馈';

COMMENT ON COLUMN Q_QUIZ.REVIEWRIGHTANSWER IS
'正确答案';

COMMENT ON COLUMN Q_QUIZ.REVIEWSCOREFEEDBACK IS
'整体得分反馈';

/*==============================================================*/
/* Table: Q_QUIZ_QUESTION                                       */
/*==============================================================*/
CREATE TABLE Q_QUIZ_QUESTION  (
   ID                   NUMBER(20)                      NOT NULL,
   QUIZ_ID              NUMBER(20),
   QUESTION_ID          NUMBER(20),
   CONSTRAINT PK_Q_QUIZ_QUESTION PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: Q_USER_EXPECTED_QUIZ                                  */
/*==============================================================*/
CREATE TABLE Q_USER_EXPECTED_QUIZ  (
   ID                   NUMBER(20)                      NOT NULL,
   QUIZ_ID              NUMBER(20),
   USERID               NUMBER(20),
   TIMEOPEN             DATE,
   TIMECLOSE            DATE,
   CURRENT_FEEDBACK_TYPE INT,
   MINUTES_ALLOWED      INT,
   ATTEMPTS_ALLOWED     INT,
   EXAM_TYPE            INT,
   CONSTRAINT PK_Q_USER_EXPECTED_QUIZ PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: Q_USER_QUESTION_ATTEMPT                               */
/*==============================================================*/
CREATE TABLE Q_USER_QUESTION_ATTEMPT  (
   ID                   NUMBER(20)                      NOT NULL,
   QUESTION_ID          NUMBER(20),
   QUIZ_ATTEMPT_ID      NUMBER(20),
   QUESTION_ATTEMPT_SEQ INT,
   USER_ANSWER          VARCHAR2(3000),
   HINT_ID              NUMBER(20),
   GRADE                DECIMAL,
   FLAGGED              INT,
   COMPLETED            INT,
   ATTEMPTS_REMAINED    INT,
   RESULT_FLAG          INT,
   TIMESTART            DATE,
   TIMEFINISH           DATE,
   TIMESUBMIT           DATE,
   CENTAINTY            INT,
   USER_IDEA            VARCHAR2(3000),
   AWARD_POINT          NUMBER(20,2),
   TEACHER_FEEDBACK     VARCHAR2(3000),
   AWARD_RATE           NUMBER(20,2),
   CONSTRAINT PK_Q_USER_QUESTION_ATTEMPT PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: Q_USER_QUIZ_ATTEMPT                                   */
/*==============================================================*/
CREATE TABLE Q_USER_QUIZ_ATTEMPT  (
   ID                   NUMBER(20)                      NOT NULL,
   QUIZ_ID              NUMBER(20),
   USERID               NUMBER(20),
   QUIZ_ATTEMPT_SEQ     INT,
   PREVIEW_TIMES        INT,
   STATE                INT,
   TIMESTART            DATE,
   TIMEFINISH           DATE,
   TIMEMODIFIED         DATE,
   SUMGRADES            DECIMAL,
   CURRENT_FEEDBACK_TYPE INT,
   ATTEMPTS_REMAINED    INT,
   CONSTRAINT PK_Q_USER_QUIZ_ATTEMPT PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: USER_LOGIN                                            */
/*==============================================================*/
CREATE TABLE USER_LOGIN  (
   ID                   DEC(20)                         NOT NULL,
   USER_ID              DEC(20),
   USERNAME             VARCHAR2(100 BYTE)              NOT NULL,
   TOKEN                VARCHAR2(100 BYTE)              NOT NULL,
   LOGIN_TIME           DATE                            NOT NULL,
   CONSTRAINT PK_USER_LOGIN PRIMARY KEY (ID)
);

ALTER TABLE Q_QUESTION
   ADD CONSTRAINT FK_Q_QUESTI_REFERENCE_Q_QUIZ_2 FOREIGN KEY (CREATEDBY)
      REFERENCES QUIZ_USER (ID)
      ON DELETE CASCADE;

ALTER TABLE Q_QUESTION
   ADD CONSTRAINT FK_Q_QUESTI_REFERENCE_QUIZ_USE FOREIGN KEY (MODIFIEDBY)
      REFERENCES QUIZ_USER (ID)
      ON DELETE CASCADE;

ALTER TABLE Q_QUESTION_ANSWER
   ADD CONSTRAINT FK_Q_QUESTI_REFERENCE_Q_QUEST2 FOREIGN KEY (QUESTION_ID)
      REFERENCES Q_QUESTION (ID)
      ON DELETE CASCADE;

ALTER TABLE Q_QUESTION_HINT
   ADD CONSTRAINT FK_Q_QUESTI_REFERENCE_Q_QUEST4 FOREIGN KEY (QUESTION_ID)
      REFERENCES Q_QUESTION (ID)
      ON DELETE CASCADE;

ALTER TABLE Q_QUIZ_QUESTION
   ADD CONSTRAINT FK_Q_QUIZ_Q_REFERENCE_Q_QUIZ FOREIGN KEY (QUIZ_ID)
      REFERENCES Q_QUIZ (ID)
      ON DELETE CASCADE;

ALTER TABLE Q_QUIZ_QUESTION
   ADD CONSTRAINT FK_Q_QUIZ_Q_REFERENCE_Q_QUESTI FOREIGN KEY (QUESTION_ID)
      REFERENCES Q_QUESTION (ID)
      ON DELETE CASCADE;

ALTER TABLE Q_USER_EXPECTED_QUIZ
   ADD CONSTRAINT FK_Q_USER_E_REFERENCE_Q_QUIZ FOREIGN KEY (QUIZ_ID)
      REFERENCES Q_QUIZ (ID)
      ON DELETE CASCADE;

ALTER TABLE Q_USER_EXPECTED_QUIZ
   ADD CONSTRAINT FK_Q_USER_E_REFERENCE_QUIZ_USE FOREIGN KEY (USERID)
      REFERENCES QUIZ_USER (ID)
      ON DELETE CASCADE;

ALTER TABLE Q_USER_QUESTION_ATTEMPT
   ADD CONSTRAINT FK_Q_USER_Q_REFERENCE_Q_QUESTI FOREIGN KEY (HINT_ID)
      REFERENCES Q_QUESTION_HINT (ID);

ALTER TABLE Q_USER_QUESTION_ATTEMPT
   ADD CONSTRAINT FK_Q_USER_Q_REFERENCE_Q_QUEST2 FOREIGN KEY (QUESTION_ID)
      REFERENCES Q_QUESTION (ID)
      ON DELETE CASCADE;

ALTER TABLE Q_USER_QUESTION_ATTEMPT
   ADD CONSTRAINT FK_Q_USER_Q_REFERENCE_Q_USER_Q FOREIGN KEY (QUIZ_ATTEMPT_ID)
      REFERENCES Q_USER_QUIZ_ATTEMPT (ID)
      ON DELETE CASCADE;

ALTER TABLE Q_USER_QUIZ_ATTEMPT
   ADD CONSTRAINT FK_Q_USER_Q_REFERENCE_Q_QUIZ FOREIGN KEY (QUIZ_ID)
      REFERENCES Q_QUIZ (ID)
      ON DELETE CASCADE;

ALTER TABLE Q_USER_QUIZ_ATTEMPT
   ADD CONSTRAINT FK_Q_USER_Q_REFERENCE_EOL_LES2 FOREIGN KEY (USERID)
      REFERENCES QUIZ_USER (ID)
      ON DELETE CASCADE;

ALTER TABLE USER_LOGIN
   ADD CONSTRAINT FK_USER_LOG_REFERENCE_QUIZ_USE FOREIGN KEY (USER_ID)
      REFERENCES QUIZ_USER (ID);

CREATE SEQUENCE SEQ_Q_QUESTION_HITS;
CREATE SEQUENCE SEQ_Q_QUESTION;
CREATE SEQUENCE SEQ_Q_QUESTION_ANSWER;
CREATE SEQUENCE SEQ_Q_QUESTION_TYPE;
CREATE SEQUENCE SEQ_Q_QUIZ;
CREATE SEQUENCE SEQ_Q_QUIZ_QUESTION;
CREATE SEQUENCE SEQ_SYS_USER;
CREATE SEQUENCE SEQ_Q_USER_QUESTION_ATTEMPT;
CREATE SEQUENCE SEQ_Q_USER_QUIZ_ATTEMPT;
CREATE SEQUENCE SEQ_Q_USER_EXPECTED_QUIZ;


insert into Q_QUESTION_TYPE (ID, CODE_NUMBER, TYPE_NAME, STATUS, INTRO, IS_DEFAULT, SHORT_NAME)
values (1, '001', '单项选择题', null, null, 0, 'sc');
insert into Q_QUESTION_TYPE (ID, CODE_NUMBER, TYPE_NAME, STATUS, INTRO, IS_DEFAULT, SHORT_NAME)
values (2, '002', '多项选择题', null, null, 0, 'mc');

insert into QUIZ_USER (ID, USERNAME, PASSWORD, USER_TYPE,REALNAME, EMAIL)
values (0, 'admin', 'admin', 0, '超级管理员', null);
insert into QUIZ_USER (ID, USERNAME, PASSWORD, USER_TYPE,REALNAME, EMAIL)
values (1, 'quiz', 'quiz', 1, '测试用户', null);
commit;