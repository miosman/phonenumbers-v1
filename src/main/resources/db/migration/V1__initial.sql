CREATE TABLE IF NOT EXISTS phonenumbers(
    subscriber_number VARCHAR(20) PRIMARY KEY,
    customer_id INT,
    status VARCHAR(10)
);

CREATE TABLE IF NOT EXISTS customers(
    id INT PRIMARY KEY,
    name VARCHAR(20)
);

INSERT INTO customers VALUES(1, 'Bill');
INSERT INTO customers VALUES(2, 'William');
INSERT INTO customers VALUES(3, 'Karl');
INSERT INTO customers VALUES(4, 'Moe');

INSERT INTO phonenumbers VALUES('455555551',1,'ACTIVE');
INSERT INTO phonenumbers VALUES('455555552',1,'IN_ACTIVE');
INSERT INTO phonenumbers VALUES('455555553',1,'IN_ACTIVE');
INSERT INTO phonenumbers VALUES('455555554',1,'ACTIVE');
INSERT INTO phonenumbers VALUES('455555555',2,'ACTIVE');
INSERT INTO phonenumbers VALUES('455555556',2,'ACTIVE');
INSERT INTO phonenumbers VALUES('455555557',3,'ACTIVE');
INSERT INTO phonenumbers VALUES('455555558',3,'ACTIVE');
INSERT INTO phonenumbers VALUES('455555559',4,'ACTIVE');
INSERT INTO phonenumbers VALUES('455555550',4,'ACTIVE');
INSERT INTO phonenumbers VALUES('455555515',4,'ACTIVE');
INSERT INTO phonenumbers VALUES('455555525',4,'ACTIVE');
INSERT INTO phonenumbers VALUES('455555535',4,'ACTIVE');
INSERT INTO phonenumbers VALUES('455555545',4,'ACTIVE');
INSERT INTO phonenumbers VALUES('455555565',4,'ACTIVE');