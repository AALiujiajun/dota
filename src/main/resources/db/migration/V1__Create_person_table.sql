CREATE TABLE USER
(
    ID int AUTO_INCREMENT primary  key  not  null,
    ACCOUNT_ID varchar(100),
    NAME varchar(50),
    TOKEN VARCHAR(36),
    gmt_create BIGINT,
    gmt_modified BIGINT
);