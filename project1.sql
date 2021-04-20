CREATE VIEW reimbursements AS(
	SELECT er.reimb_id
	, er.reimb_amount
	, er.reimb_submitted
	, er.reimb_resolved
	, er.reimb_description
	, er.reimb_receipt
	, eu.ers_username reimb_author
	, u.ers_username reimb_resolver
	, ert.reimb_type
	, ers.reimb_status 
	FROM ers_reimbursement er
	INNER JOIN ers_users eu ON er.reimb_author = eu.ers_user_id
	FULL OUTER JOIN ers_reimbursement_type ert ON er.reimb_type_id = ert.reimb_type_id
	FULL OUTER JOIN ers_reimbursement_status ers ON er.reimb_status_id = ers.reimb_status_id
	LEFT JOIN ers_users u ON er.reimb_resolver = u.ers_user_id
	ORDER BY er.reimb_id ASC
);

CREATE VIEW users AS(
	SELECT eu.ers_user_id, 
	eu.ers_username, 
	eu.ers_password, 
	eu.user_first_name, 
	eu.user_last_name, 
	eu.user_email, 
	eur.user_role 
	FROM ers_users eu, ers_user_roles eur
	WHERE eu.ers_user_role_id = eur.ers_user_role_id
);

CREATE TABLE ers_user_roles(
	ers_user_role_id INTEGER PRIMARY KEY
	, user_role varchar(50) NOT NULL UNIQUE
);

CREATE TABLE ers_reimbursement_type(
	reimb_type_id INTEGER PRIMARY KEY
	,reimb_type varchar(60) NOT NULL UNIQUE
);

CREATE TABLE ers_reimbursement_status(
	reimb_status_id INTEGER PRIMARY KEY
	, reimb_status varchar(50) NOT NULL UNIQUE
);

CREATE TABLE ers_users(
	ers_user_id SERIAL PRIMARY KEY
	, ers_username varchar(50) NOT NULL UNIQUE
	, ers_password varchar(200) NOT NULL
	, user_first_name varchar(50) NOT NULL
	, user_last_name varchar(50) NOT NULL
	, user_email varchar(100) NOT NULL UNIQUE
	, ers_user_role_id INTEGER NOT NULL
	, CONSTRAINT user_role_fk 
	FOREIGN KEY (ers_user_role_id)
	REFERENCES ers_user_roles(ers_user_role_id)
	ON DELETE CASCADE
);

CREATE TABLE ers_reimbursement(
	reimb_id SERIAL PRIMARY KEY
	, reimb_amount DECIMAL NOT NULL
	, reimb_submitted DATE NOT NULL
	, reimb_resolved DATE
	, reimb_description varchar(300)
	, reimb_receipt BYTEA 
	, reimb_author INTEGER NOT NULL
	, reimb_resolver INTEGER
	, reimb_status_id INTEGER NOT NULL
	, reimb_type_id INTEGER NOT NULL
	, receipt_format varchar(10)
	, CONSTRAINT ers_user_fk_auth FOREIGN KEY (reimb_author)
	REFERENCES ers_users(ers_user_id) ON DELETE CASCADE
	, CONSTRAINT ers_user_fk_reslvr FOREIGN KEY (reimb_resolver)
	REFERENCES ers_users(ers_user_id) ON DELETE CASCADE
	, CONSTRAINT ers_reimbursement_status_fk FOREIGN KEY (reimb_status_id)
	REFERENCES ers_reimbursement_status(reimb_status_id) ON DELETE CASCADE
	, CONSTRAINT ers_reimbursement_type_fk FOREIGN KEY (reimb_type_id)
	REFERENCES ers_reimbursement_type(reimb_type_id) ON DELETE CASCADE
);

INSERT INTO ers_user_roles VALUES (1, 'Employee');
INSERT INTO ers_user_roles VALUES (2, 'Finance Manager');

INSERT INTO ers_reimbursement_type VALUES (1, 'Lodging');
INSERT INTO ers_reimbursement_type VALUES (2, 'Travel');
INSERT INTO ers_reimbursement_type VALUES (3, 'Food');
INSERT INTO ers_reimbursement_type VALUES (4, 'Other');

INSERT INTO ers_reimbursement_status VALUES (1, 'Approved');
INSERT INTO ers_reimbursement_status VALUES (2, 'Denied');
INSERT INTO ers_reimbursement_status VALUES (3, 'Pending');

INSERT INTO ers_users VALUES(DEFAULT, 'jsmith25', 'passsword', 'John', 'Smith', 'jsmith@gmail.com', 1);
INSERT INTO ers_users VALUES(DEFAULT, 'kholmes45', 'passsword', 'Katie', 'Holmes', 'kholmes@gmail.com', 1);
INSERT INTO ers_users VALUES(DEFAULT, 'aTiger60', 'passsword', 'Antonio', 'Tiger', 'tonyT@kelloggs.com', 1);
INSERT INTO ers_users VALUES(DEFAULT, 'sgomez99', 'passsword', 'Selina', 'Gomez', 'sgomez@msn.com', 2);
INSERT INTO ers_users VALUES(DEFAULT, 'hpotter2', 'passsword', 'Harry', 'Potter', 'hpotter@hogwartz.edu', 2);
INSERT INTO ers_users VALUES(DEFAULT, 'clarkK77', 'passsword', 'Clark', 'Kent', 'TRsuperman@gmail.com', 2);

INSERT INTO ers_reimbursement VALUES(DEFAULT, 50.25, '2021-01-01', NULL, 'I need to be reimbursed.', NULL, 1, NULL, 3, 1);
INSERT INTO ers_reimbursement VALUES(DEFAULT, 100.60, '2020-02-07', NULL, 'I bought something.', NULL, 2, NULL, 3, 1);
INSERT INTO ers_reimbursement VALUES(DEFAULT, 25.75, '2020-07-05', NULL, 'Please reimburse me.', NULL, 1, NULL, 1, 3);
INSERT INTO ers_reimbursement VALUES(DEFAULT, 150.500, '2021-08-21', NULL, NULL, NULL, 3, NULL, 3, 1);
INSERT INTO ers_reimbursement VALUES(DEFAULT, 175.23, '2018-10-19', NULL, 'Help!', NULL, 3, NULL, 1, 2);
INSERT INTO ers_reimbursement VALUES(DEFAULT, 10.00, '2001-04-05', NULL, 'I just want ten dollars.', NULL, 2, NULL, 2, 4);

ALTER TABLE ers_reimbursement ADD COLUMN receipt_format varchar(10);
TRUNCATE TABLE ers_reimbursement;

SELECT * FROM ers_user_roles;
SELECT * FROM ers_reimbursement_type;
SELECT * FROM ers_reimbursement_status;
SELECT * FROM ers_users;
SELECT * FROM ers_reimbursement;
SELECT * FROM users;
SELECT * FROM reimbursements;

DROP VIEW IF EXISTS reimbursements;
DROP VIEW IF EXISTS users;
DROP TABLE IF EXISTS ers_reimbursement;
DROP TABLE IF EXISTS ers_users;
DROP TABLE IF EXISTS ers_reimbursement_type;
DROP TABLE IF EXISTS ers_reimbursement_status;
DROP TABLE IF EXISTS ers_user_roles;






