CREATE TABLE admin_accounts
(
    idx                 NUMBER(4)    NOT NULL,
    admin_id            VARCHAR2(50) NOT NULL,
    admin_password      VARCHAR2(50) NOT NULL
);
ALTER TABLE admin_accounts ADD
(
    CONSTRAINT admin_idx_pk PRIMARY KEY(idx)
);
CREATE SEQUENCE admin_idx_seq START WITH 1;
INSERT INTO admin_accounts
(idx, admin_id, admin_password)
VALUES
(admin_idx_seq.NEXTVAL, 'admin', 'sungil');



CREATE TABLE member_accounts
(
    idx             NUMBER(4)       NOT NULL,
    user_name       VARCHAR2(50)    NOT NULL,
    user_id         VARCHAR2(50)    NOT NULL,
    user_password   VARCHAR2(50)    NOT NULL,
    user_hak        NUMBER(4)       NOT NULL,
    user_ban        NUMBER(4)       NOT NULL,
    user_bun        NUMBER(4)       NOT NULL
);
ALTER TABLE member_accounts ADD
(
    CONSTRAINT member_idx_pk PRIMARY KEY (idx)
);
CREATE SEQUENCE member_idx_seq START WITH 1;

INSERT INTO member_accounts
(idx, user_name, user_id, user_password, user_hak, user_ban, user_bun)
VALUES
(member_idx_seq.NEXTVAL, test1, 'test1', '12345', 2, 10, 22);









