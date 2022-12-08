---- Populate user type data
INSERT INTO user_type (type) VALUES ('superadmin')
INSERT INTO user_type (type) VALUES ('admin')
INSERT INTO user_type (type) VALUES ('officer')
INSERT INTO user_type (type) VALUES ('user')
----
---- Password is password
INSERT INTO users (first_name, last_name, password, email, username, verified, is_custom_permission, user_type_id, active)
VALUES ('Muhammad', 'Asif', '$2a$12$wx4lDphOSYxnWtXvvBJ1n.a8A8HswN6nisl9NvSxJLDgBCG3iK3Ma', 'superadmin@mailinator.com', 'superadmin', 1, 0, 1, 1);
INSERT INTO users (first_name, last_name, password, email, username, verified, is_custom_permission, user_type_id, active)
VALUES ('Niaz', 'Ali', '$2a$12$wx4lDphOSYxnWtXvvBJ1n.a8A8HswN6nisl9NvSxJLDgBCG3iK3Ma', 'admin@mailinator.com', 'admin', 1, 0, 2, 1);
INSERT INTO users (first_name, last_name, password, email, username, verified, is_custom_permission, user_type_id, active)
VALUES ('Azan', 'Ali', '$2a$12$wx4lDphOSYxnWtXvvBJ1n.a8A8HswN6nisl9NvSxJLDgBCG3iK3Ma', 'officer@mailinator.com', 'officer', 1, 0, 3, 1);
----
---- Populate the country's data
INSERT INTO country (name) VALUES ('Pakistan')
----
---- Populate the city's data
INSERT INTO city (name,country_id) VALUES('Karachi',1);
INSERT INTO city (name,country_id) VALUES('Hyderabad',1);
INSERT INTO city (name,country_id) VALUES('Lahore',1);
INSERT INTO city (name,country_id) VALUES('Multan',1);
INSERT INTO city (name,country_id) VALUES('Islamabad',1);
INSERT INTO city (name,country_id) VALUES('Quetta',1);
----
---- Populate the locality's data
INSERT INTO locality (name,city_id) VALUES ('Gulshan',1);
INSERT INTO locality (name,city_id) VALUES ('Johar',1);
INSERT INTO locality (name,city_id) VALUES ('Malir',1);
INSERT INTO locality (name,city_id) VALUES ('Nazimabad',1);
INSERT INTO locality (name,city_id) VALUES ('Maskan',1);
INSERT INTO locality (name,city_id) VALUES ('PECHS',1);
INSERT INTO locality (name,city_id) VALUES ('13D',1);
----
---- Populate company's data
INSERT INTO company (active,name) VALUES (1,'Fiber Strom');
INSERT INTO company (active,name) VALUES (1,'Transworld');
INSERT INTO company (active,name) VALUES (1,'Connect');
INSERT INTO company (active,name) VALUES (1,'Click Pay');
----
---- Populate connection_type's data
INSERT INTO connection_type (active,type) VALUES (1,'Internet');
INSERT INTO connection_type (active,type) VALUES (1,'Cable');
INSERT INTO connection_type (active,type) VALUES (1,'Both');
----
----Populate sub_locality's data
INSERT INTO sub_locality (name,locality_id) VALUES ('Block 10',1);
INSERT INTO sub_locality (name,locality_id) VALUES ('Block 5',1);
INSERT INTO sub_locality (name,locality_id) VALUES ('Block 8',1);
INSERT INTO sub_locality (name,locality_id) VALUES ('Block 7',1);
INSERT INTO sub_locality (name,locality_id) VALUES ('Block 14',2);
INSERT INTO sub_locality (name,locality_id) VALUES ('Block 13',2);
INSERT INTO sub_locality (name,locality_id) VALUES ('Block 11',2);
---- Populate package's data
INSERT INTO package (active,package_name,purchase_price,sale_price,company_id,connection_type_id)
VALUES (1,'5 MB',1000,1400,1,1);
INSERT INTO package (active,package_name,purchase_price,sale_price,company_id,connection_type_id)
VALUES (1,'8 MB',1200,1600,1,1);
INSERT INTO package (active,package_name,purchase_price,sale_price,company_id,connection_type_id)
VALUES (1,'10 MB',1400,2000,1,1);
INSERT INTO package (active,package_name,purchase_price,sale_price,company_id,connection_type_id)
VALUES (1,'50 channels',200,300,1,2);
INSERT INTO package (active,package_name,purchase_price,sale_price,company_id,connection_type_id)
VALUES (1,'100 channels',350,500,1,2);
INSERT INTO package (active,package_name,purchase_price,sale_price,company_id,connection_type_id)
VALUES (1,'200 channels',500,800,1,2);
----
---- Populate feature's data
INSERT INTO feature (name, main_menu_name, main_url, active, value) VALUES ('', 'Dashboard', 'api/v1/dashboard', 1, 'DASHBOARD')
INSERT INTO feature (name, main_menu_name, main_url, active, value) VALUES ('City', 'Area', 'api/v1/area', 1, 'CITY')
INSERT INTO feature (name, main_menu_name, main_url, active, value) VALUES ('Locality', 'Area', 'api/v1/area', 1, 'LOCALITY')
INSERT INTO feature (name, main_menu_name, main_url, active, value) VALUES ('Sub Locality', 'Area', 'api/v1/area', 1, 'SUB_LOCALITY')
INSERT INTO feature (name, main_menu_name, main_url, active, value) VALUES ('Package', 'User Profile', 'api/v1/user-profile', 1, 'PACKAGE')
INSERT INTO feature (name, main_menu_name, main_url, active, value)  VALUES ('Box/Media', 'User Profile', 'api/v1/user-profile', 1, 'BOX_MEDIA')
INSERT INTO feature (name, main_menu_name, main_url, active, value)  VALUES ('User Details', 'User Profile', 'api/v1/user-profile', 1, 'USER_DETAILS')
INSERT INTO feature (name, main_menu_name, main_url, active, value)  VALUES ('New Queries', 'User Profile', 'api/v1/user-profile', 1, 'NEW_QUERIES')
INSERT INTO feature (name, main_menu_name, main_url, active, value)  VALUES ('Dealer Details', 'Dealers', 'api/v1/dealers', 1, 'DEALER_DETAILS')
INSERT INTO feature (name, main_menu_name, main_url, active, value)  VALUES ('Recovery Officer', 'Recovery Officer', 'api/v1/recovery-officer', 1, 'RECOVERY_OFFICER')
INSERT INTO feature (name, main_menu_name, main_url, active, value)  VALUES ('Area Allocation', 'Recovery Officer', 'api/v1/recovery-officer', 1, 'AREA_ALLOCATION')
INSERT INTO feature (name, main_menu_name, main_url, active, value)  VALUES ('Bill Creator', 'Transactions', 'api/v1/transactions', 1, 'BILL_CREATOR')
INSERT INTO feature (name, main_menu_name, main_url, active, value)  VALUES ('Users Collections', 'Transactions', 'api/v1/transactions', 1, 'USERS_COLLECTIONS')
INSERT INTO feature (name, main_menu_name, main_url, active, value)  VALUES ('Dealer Collections', 'Transactions', 'api/v1/transactions', 1, 'DEALER_COLLECTIONS')
INSERT INTO feature (name, main_menu_name, main_url, active, value)  VALUES ('Allocated Collections', 'Transactions', 'api/v1/transactions', 1, 'TRANSACTIONS_ALLOCATED_COLLECTIONS')
INSERT INTO feature (name, main_menu_name, main_url, active, value)  VALUES ('Transaction Type', 'Transactions', 'api/v1/transactions', 1, 'TRANSACTION_TYPE')
INSERT INTO feature (name, main_menu_name, main_url, active, value)  VALUES ('Reprint Slip', 'Transactions', 'api/v1/transactions', 1, 'REPRINT_SLIP')
INSERT INTO feature (name, main_menu_name, main_url, active, value)  VALUES ('New Message', 'Message', 'api/v1/message', 1, 'NEW_MESSAGE')
INSERT INTO feature (name, main_menu_name, main_url, active, value)  VALUES ('Expire Message', 'Message', 'api/v1/message', 1, 'EXPIRE_MESSAGE')
INSERT INTO feature (name, main_menu_name, main_url, active, value)  VALUES ('Other Message', 'Message', 'api/v1/message', 1, 'OTHER_MESSAGE')
INSERT INTO feature (name, main_menu_name, main_url, active, value)  VALUES ('Draft Message', 'Message', 'api/v1/message', 1, 'DRAFT_MESSAGE')
INSERT INTO feature (name, main_menu_name, main_url, active, value)  VALUES ('Sent Message', 'Message', 'api/v1/message', 1, 'SENT_MESSAGE')
INSERT INTO feature (name, main_menu_name, main_url, active, value)  VALUES ('Upload Document', 'Recharge System', 'api/v1/recharge-system', 1, 'UPLOAD_DOCUMENT')
INSERT INTO feature (name, main_menu_name, main_url, active, value)  VALUES ('Recharge Summary', 'Recharge System', 'api/v1/recharge-system', 1, 'RECHARGE_SUMMARY')
INSERT INTO feature (name, main_menu_name, main_url, active, value)  VALUES ('Account Heads', 'Accounts', 'api/v1/accounts', 1, 'ACCOUNT_HEADS')
INSERT INTO feature (name, main_menu_name, main_url, active, value)  VALUES ('Account Entry', 'Accounts', 'api/v1/accounts', 1, 'ACCOUNT_ENTRY')
INSERT INTO feature (name, main_menu_name, main_url, active, value)  VALUES ('On Day Account', 'Accounts', 'api/v1/accounts', 1, 'ON_DAY_ACCOUNT')
INSERT INTO feature (name, main_menu_name, main_url, active, value)  VALUES ('Deleted Collection', 'Log', 'api/v1/log', 1, 'DELETED_COLLECTION')
INSERT INTO feature (name, main_menu_name, main_url, active, value)  VALUES ('User Collection', 'User Report', 'api/v1/user-report', 1, 'USER_REPORT_USER_COLLECTION')
INSERT INTO feature (name, main_menu_name, main_url, active, value)  VALUES ('User Defaulter', 'User Report', 'api/v1/user-report', 1, 'USER_DEFAULTER')
INSERT INTO feature (name, main_menu_name, main_url, active, value)  VALUES ('New User List', 'User Report', 'api/v1/user-report', 1, 'NEW_USER_LIST')
INSERT INTO feature (name, main_menu_name, main_url, active, value)  VALUES ('Deactivate User List', 'User Report', 'api/v1/user-report', 1, 'DEACTIVATE_USER_LIST')
INSERT INTO feature (name, main_menu_name, main_url, active, value)  VALUES ('Package Wise List', 'User Report', 'api/v1/user-report', 1, 'PACKAGE_WISE_LIST')
INSERT INTO feature (name, main_menu_name, main_url, active, value)  VALUES ('Promise Date Report', 'User Report', 'api/v1/user-report', 1, 'PROMISE_DATE_REPORT')
INSERT INTO feature (name, main_menu_name, main_url, active, value)  VALUES ('Allocated Collections', 'User Report', 'api/v1/user-report', 1, 'USER_REPORT_ALLOCATED_COLLECTIONS')
INSERT INTO feature (name, main_menu_name, main_url, active, value)  VALUES ('Allocated Defaulter', 'User Report', 'api/v1/user-report', 1, 'ALLOCATED_DEFAULTER')
INSERT INTO feature (name, main_menu_name, main_url, active, value)  VALUES ('Expiry-Wise Defaulter', 'User Report', 'api/v1/user-report', 1, 'EXPIRY_WISE_DEFAULTER')
INSERT INTO feature (name, main_menu_name, main_url, active, value)  VALUES ('Monthly Collection', 'User Report', 'api/v1/user-report', 1, 'MONTHLY_COLLECTION')
INSERT INTO feature (name, main_menu_name, main_url, active, value)  VALUES ('Dealers Collection', 'Dealers Report', 'api/v1/dealer-report', 1, 'DEALERS_COLLECTION')
INSERT INTO feature (name, main_menu_name, main_url, active, value)  VALUES ('Dealers Defaulter', 'Dealers Report', 'api/v1/dealer-report', 1, 'DEALERS_DEFAULTER')
INSERT INTO feature (name, main_menu_name, main_url, active, value)  VALUES ('New Dealers List', 'Dealers Report', 'api/v1/dealer-report', 1, 'NEW_DEALERS_LIST')
INSERT INTO feature (name, main_menu_name, main_url, active, value)  VALUES ('Accounts Report', 'Accounts Report', 'api/v1/account-report', 1, 'ACCOUNTS_REPORT')
INSERT INTO feature (name, main_menu_name, main_url, active, value)  VALUES ('On Day Balance Sheet', 'Accounts Report', 'api/v1/account-report', 1, 'ON_DAY_BALANCE_SHEET')
INSERT INTO feature (name, main_menu_name, main_url, active, value)  VALUES ('User Rights', 'Settings', 'api/v1/settings', 1, 'USER_RIGHTS')
INSERT INTO feature (name, main_menu_name, main_url, active, value)  VALUES ('Change Username/Password', 'Settings', 'api/v1/settings', 1, 'CHANGE_USERNAME_PASSWORD')
--
--SELECT 1

-- FOR SUPER ADMIN WE HAVE ALL ACCESS BY DEFAULT
-- For ADMIN
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (2, 1)
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (2, 2)
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (2, 3)
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (2, 4)
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (2, 5)
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (2, 6)
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (2, 7)
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (2, 8)
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (2, 9)
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (2, 10)
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (2, 11)
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (2, 12)
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (2, 13)
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (2, 14)
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (2, 15)
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (2, 16)
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (2, 17)
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (2, 18)
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (2, 19)
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (2, 20)
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (2, 21)
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (2, 22)
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (2, 23)
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (2, 24)
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (2, 25)
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (2, 26)
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (2, 27)
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (2, 28)
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (2, 29)
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (2, 30)
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (2, 31)
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (2, 32)
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (2, 33)
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (2, 34)
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (2, 35)
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (2, 36)
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (2, 37)
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (2, 38)
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (2, 39)
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (2, 40)
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (2, 41)
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (2, 42)
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (2, 43)
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (2, 44)
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (2, 44)

-- FOR OFFICER
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (3, 13)
INSERT INTO user_type_feature (user_type_id, feature_id) VALUES (3, 29)