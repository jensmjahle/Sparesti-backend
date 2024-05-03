

use sparesti;




/*
	User 1
	Username  : varchar : JohnSmith12
	Firstname : varchar : John
	Lastname  : varchar : Smith
*/
INSERT INTO mock_customer (username, first_name, last_name) VALUES ("JohnSmith12", "John", "Smith");

-- Accounts for User 1 : JohnSmith12

/*
	Accounts  
	AccountNr   : BigInt  : xxxxxxxxxxx 
	Username    : varchar : JohnSmith12
	Balance     : BigInt  :  
	Currency    : varchar : NOK
	AccountType : varchar : 
	AccountName : varchar :	
*/
INSERT INTO mock_account (account_nr, username, balance, currency, account_type, account_name) VALUES 
(80684789604, "JohnSmith12", 46975345, "NOK", "Aksjesparekonto", "Fond"),
(15232755015, "JohnSmith12", 66630577, "NOK", "Aksjesparekonto", "Aksjer"),
(62367154487, "JohnSmith12", 42600275, "NOK", "Sparekonto", "Spare"),
(45651009037, "JohnSmith12", 12320020, "NOK", "Brukskonto", "Bruks");


-- Transactions for Account 1 : JohnSmith12

/*
	Transaction 
	AccountNr        : BigInt   : xxxxxxxxxxx
	TransactionTitle : varchar  :
	Time             : DATETIME :
	DebtorAccount    : BigInt   : xxxxxxxxxxx
	DebtorName       : varchar  :
	CreditorAccount  : BigInt   : xxxxxxxxxxx
	CreditorName     : varchar  :
	amount           : BigInt   :
	currency         : varchar  : NOK
*/
-- Sending from account 1 : 80684789604
INSERT INTO mock_transaction (account_nr, transaction_title, time_stamp, debtor_account, debtor_name, creditor_account, creditor_name, amount, currency) VALUES 
(80684789604, "Salg av fond", "2024-02-01 12:30:00", 80684789604, "Fond", 45651009037, "Bruks", -5123, "NOK"),
(80684789604, "Salg av fond", "2024-03-30 12:30:00", 80684789604, "Fond", 45651009037, "Bruks", -10123, "NOK"),
(80684789604, "Salg av fond", "2024-04-20 12:30:00", 80684789604, "Fond", 45651009037, "Bruks", -15123, "NOK");
-- Transfer to account 1  : 80684789604
INSERT INTO mock_transaction (account_nr, transaction_title, time_stamp, debtor_account, debtor_name, creditor_account, creditor_name, amount, currency) VALUES 
(80684789604, "Kjøp av fond", "2024-02-20 14:30:00", 45651009037, "Bruks", 80684789604, "Fond", +3000, "NOK"),
(80684789604, "Kjøp av fond", "2024-03-15 10:30:00", 45651009037, "Bruks", 80684789604, "Fond", +2000, "NOK");

-- Sending from account 2 : 15232755015
INSERT INTO mock_transaction (account_nr, transaction_title, time_stamp, debtor_account, debtor_name, creditor_account, creditor_name, amount, currency) VALUES 
(15232755015, "Salg av aksje", "2024-03-10 12:30:00", 15232755015, "Aksjer", 45651009037, "Bruks", -5123, "NOK");

-- Transfer to account 2 : 15232755015
INSERT INTO mock_transaction (account_nr, transaction_title, time_stamp, debtor_account, debtor_name, creditor_account, creditor_name, amount, currency) VALUES 
(15232755015, "Kjøp av aksje", "2024-03-20 12:30:00", 45651009037, "Bruks", 15232755015, "Aksjer", +1000, "NOK");

-- Sending from account 3 : 62367154487
INSERT INTO mock_transaction (account_nr, transaction_title, time_stamp, debtor_account, debtor_name, creditor_account, creditor_name, amount, currency) VALUES
(62367154487, "Overføring fra sparekonto", "2024-03-15 12:30:00", 62367154487, "Spare", 45651009037, "Bruks", -2000, "NOK");

-- Transfer to account 3 : 62367154487
INSERT INTO mock_transaction (account_nr, transaction_title, time_stamp, debtor_account, debtor_name, creditor_account, creditor_name, amount, currency) VALUES
(62367154487, "Overføring til sparekonto", "2024-04-15 12:30:00", 45651009037, "Bruks", 62367154487, "Spare", +1500, "NOK");

-- Sending from account 4 : 45651009037
INSERT INTO mock_transaction (account_nr, transaction_title, time_stamp, debtor_account, debtor_name, creditor_account, creditor_name, amount, currency) VALUES
-- Februar
(45651009037, "Kjøp av fond", "2024-02-20 14:30:00", 45651009037, "Bruks", 80684789604, "Fond", -3000, "NOK"),
(45651009037, "AtB", "2024-02-21 08:45:00", 45651009037, "Bruks", 52076164659, "AtB", -585, "NOK"),
(45651009037, "Vinmonopolet", "2024-02-23 15:00:00", 45651009037, "Bruks", 19973799516, "Vinmonopolet", -2700, "NOK"),
(45651009037, "Lille Szechaun Byåsen", "2024-02-23 18:00:00",  45651009037, "Bruks", 22833465929, "Lille Szechaun Byåsen", -400, "NOK"),
(45651009037, "Tier", "2024-02-24 15:20:00", 45651009037, "Bruks", 93118874001, "Tier", -35, "NOK"),
(45651009037, "Starbucks", "2024-02-24 16:00:00", 45651009037, "Bruks", 13515847198, "Starbucks", -100, "NOK"),
(45651009037, "Boozt.com", "2024-02-24 18:00:00", 45651009037, "Bruks", 59149472386, "Boozt.com", -1200, "NOK"),
(45651009037, "Norwegian Air Shuttle", "2024-02-26 12:00:00", 45651009037, "Bruks", 11717568325, "Norwegian Air Shuttle", -1200, "NOK"),
-- Mars
(45651009037, "Coop Extra", "2024-03-01 14:10:00", 45651009037, "Bruks", 17727156891, "Coop Extra", -476, "NOK"),
(45651009037, "Studio 26", "2024-03-01 20:35:00", 45651009037, "Bruks", 53690605486, "Studio 26", -120, "NOK"),
(45651009037, "Studio 26", "2024-03-01 20:40:00", 45651009037, "Bruks", 53690605486, "Studio 26", -200, "NOK"),
(45651009037, "Studio 26", "2024-03-01 20:45:00", 45651009037, "Bruks", 53690605486, "Studio 26", -140, "NOK"),
(45651009037, "Studio 26", "2024-03-01 20:50:00", 45651009037, "Bruks", 53690605486, "Studio 26", -150, "NOK"),
(45651009037, "Studio 26", "2024-03-01 20:55:00", 45651009037, "Bruks", 53690605486, "Studio 26", -170, "NOK"),
(45651009037, "Studio 26", "2024-03-01 21:30:00", 45651009037, "Bruks", 53690605486, "Studio 26", -120, "NOK"),
(45651009037, "Studio 26", "2024-03-01 21:40:00", 45651009037, "Bruks", 53690605486, "Studio 26", -120, "NOK"),
(45651009037, "Studio 26", "2024-03-01 21:50:00", 45651009037, "Bruks", 53690605486, "Studio 26", -120, "NOK"),
(45651009037, "Club Downtown", "2024-03-01 22:30:00", 45651009037, "Bruks", 68829545787, "Club Downtown", -120, "NOK"),
(45651009037, "Club Downtown", "2024-03-01 22:40:00", 45651009037, "Bruks", 68829545787, "Club Downtown", -120, "NOK"),
(45651009037, "Club Downtown", "2024-03-01 22:50:00", 45651009037, "Bruks", 68829545787, "Club Downtown", -120, "NOK"),
(45651009037, "Club Downtown", "2024-03-01 23:00:00", 45651009037, "Bruks", 68829545787, "Club Downtown", -120, "NOK"),
(45651009037, "Studentersamfundet", "2024-03-01 23:30:00", 45651009037, "Bruks", 37039414487, "Studentersamfundet", -100, "NOK"),
(45651009037, "Sesam Burger", "2024-03-02 00:20:00", 45651009037, "Bruks", 85585440430, "Sesam Burger", -200, "NOK"),
(45651009037, "Foodora", "2024-03-02 17:30:00", 45651009037, "Bruks", 43863795963, "Foodora", -300, "NOK"),
(45651009037, "SiT Kafe", "2024-03-04 13:50:00", 45651009037, "Bruks", 56934565515, "SiT Kafe", -120, "NOK"),
(45651009037, "SiT Kafe", "2024-03-08 13:50:00", 45651009037, "Bruks", 56934565515, "SiT Kafe", -120, "NOK"),
(45651009037, "SiT Kafe", "2024-03-14 13:50:00", 45651009037, "Bruks", 56934565515, "SiT Kafe", -120, "NOK"),
(45651009037, "Kjøp av fond", "2024-03-15 10:30:00", 45651009037, "Bruks", 80684789604, "Fond", -2000, "NOK"),
(45651009037, "SiT Kafe", "2024-03-19 13:50:00", 45651009037, "Bruks", 56934565515, "SiT Kafe", -120, "NOK"),
(15232755015, "Kjøp av aksje", "2024-03-20 09:30:00", 45651009037, "Bruks", 15232755015, "Aksjer", -1000, "NOK"),
(45651009037, "REMA1000", "2024-03-20 12:30:00", 45651009037, "Bruks", 11510553602, "REMA1000", -517, "NOK"),
(45651009037, "AtB", "2024-03-21 08:45:00", 45651009037, "Bruks", 52076164659, "AtB", -585, "NOK"),
(45651009037, "Ryde", "2024-03-22 15:20:00", 45651009037, "Bruks", 29343339488, "Ryde", -35, "NOK"),
(45651009037, "Vinmonopolet", "2024-03-23 15:00:00", 45651009037, "Bruks", 19973799516, "Vinmonopolet", -4700, "NOK"),
(45651009037, "Pizzabakeren", "2024-03-23 18:00:00", 45651009037, "Bruks", 81208986306, "Pizzabakeren", -179, "NOK"),
(45651009037, "Bunnpris", "2024-03-25 18:10:00", 45651009037, "Bruks", 24613929754, "Bunnpris", -57, "NOK"),
(45651009037, "SiT Kafe", "2024-03-28 13:50:00", 45651009037, "Bruks", 56934565515, "SiT Kafe", -120, "NOK"),
(45651009037, "Clas Ohlson", "2024-03-29 08:55:00", 45651009037, "Bruks", 72138386450, "Clas Ohlson", -517, "NOK"),
(45651009037, "Den Gode Nabo", "2024-03-30 19:55:00", 45651009037, "Bruks", 96586542651, "Den Gode Nabo", -120, "NOK"),
(45651009037, "Den Gode Nabo", "2024-03-30 20:15:00", 45651009037, "Bruks", 96586542651, "Den Gode Nabo", -120, "NOK"),
(45651009037, "Den Gode Nabo", "2024-03-30 20:35:00", 45651009037, "Bruks", 96586542651, "Den Gode Nabo", -120, "NOK"),
(45651009037, "Den Gode Nabo", "2024-03-30 20:55:00", 45651009037, "Bruks", 96586542651, "Den Gode Nabo", -120, "NOK"),
(45651009037, "Wolt", "2024-03-31 19:30:00", 45651009037, "Bruks", 67751801398, "Wolt", -200, "NOK"),
-- April
(45651009037, "Komplett", "2024-04-03 09:40:00", 45651009037, "Bruks", 44455139114, "Komplett", -231, "NOK"),
(45651009037, "Starbucks", "2024-04-08 16:30:00", 45651009037, "Bruks", 13515847198, "Starbucks", -200, "NOK"),
(45651009037, "SiT Kafe", "2024-04-12 13:50:00", 45651009037, "Bruks", 56934565515, "SiT Kafe", -120, "NOK"),
(45651009037, "Circle K", "2024-04-14 17:00:00", 45651009037, "Bruks", 14747574266, "Circle K", -670, "NOK"),
(45651009037, "Overføring til sparekonto", "2024-04-15 12:30:00", 45651009037, "Bruks", 62367154487, "Spare", -1500, "NOK");

-- Transfer to account 4  : 45651009037
INSERT INTO mock_transaction (account_nr, transaction_title, time_stamp, debtor_account, debtor_name, creditor_account, creditor_name, amount, currency) VALUES 
(45651009037, "Salg av fond", "2024-02-01 12:30:00", 80684789604, "Fond", 45651009037, "Bruks", +5123, "NOK"),
(45651009037, "Salg av aksje", "2024-03-10 12:30:00", 15232755015, "Aksjer", 45651009037, "Bruks", +5123, "NOK"),
(45651009037, "Overføring fra sparekonto", "2024-03-15 12:30:00", 62367154487, "Spare", 45651009037, "Bruks", +2000, "NOK"),
(45651009037, "Salg av fond", "2024-03-30 12:30:00", 80684789604, "Fond", 45651009037, "Bruks", +10123, "NOK"),
(45651009037, "Salg av fond", "2024-04-20 12:30:00", 80684789604, "Fond", 45651009037, "Bruks", +15123, "NOK");






/*
	User 2
	Username  : varchar : JaneSmith53
	Firstname : varchar : Jane
	Lastname  : varchar : Smith
*/
INSERT INTO mock_customer (username, first_name, last_name) VALUES ("JaneSmith53", "Jane", "Smith");

/*
	Account  
	AccountNr   : BigInt  : xxxxxxxxxxx
	Username    : varchar : 
	Balance     : BigInt  : xxxxxxx
	Currency    : varchar : NOK
	AccountType : varchar : 
	AccountName : varchar :	
*/
-- Legg til kontoer for JaneSmith53
INSERT INTO mock_account (account_nr, username, balance, currency, account_type, account_name) VALUES 
(98765432101, "JaneSmith53", 50000000, "NOK", "Aksjesparekonto", "Fond"),
(24561087324, "JaneSmith53", 10000000, "NOK", "Aksjesparekonto", "Aksjer"),
(78562345098, "JaneSmith53", 30000000, "NOK", "Sparekonto", "Spare"),
(36987451236, "JaneSmith53", 2000000, "NOK", "Brukskonto", "Bruks");


/*
	Transaction 
	AccountNr        : BigInt   : xxxxxxxxxxx
	TransactionTitle : varchar  :
	Time             : DATETIME :
	DebtorAccount    : BigInt   : xxxxxxxxxxx
	DebtorName       : varchar  :
	CreditorAccount  : BigInt   : xxxxxxxxxxx
	CreditorName     : varchar  :
	amount           : BigInt   :
	currency         : varchar  : NOK
*/

-- Sending from account 1 : 98765432101
INSERT INTO mock_transaction (account_nr, transaction_title, time_stamp, debtor_account, debtor_name, creditor_account, creditor_name, amount, currency) VALUES 
(98765432101, "Salg av fond", "2024-02-01 12:30:00", 98765432101, "Fond", 36987451236, "Bruks", -5123, "NOK"),
(98765432101, "Salg av fond", "2024-03-30 12:30:00", 98765432101, "Fond", 36987451236, "Bruks", -10123, "NOK"),
(98765432101, "Salg av fond", "2024-04-20 12:30:00", 98765432101, "Fond", 36987451236, "Bruks", -15123, "NOK");
-- Transfer to account 1  : 98765432101
INSERT INTO mock_transaction (account_nr, transaction_title, time_stamp, debtor_account, debtor_name, creditor_account, creditor_name, amount, currency) VALUES 
(98765432101, "Kjøp av fond", "2024-02-20 14:30:00", 36987451236, "Bruks", 98765432101, "Fond", +3000, "NOK"),
(98765432101, "Kjøp av fond", "2024-03-15 10:30:00", 36987451236, "Bruks", 98765432101, "Fond", +2000, "NOK");

-- Sending from account 2 : 24561087324
INSERT INTO mock_transaction (account_nr, transaction_title, time_stamp, debtor_account, debtor_name, creditor_account, creditor_name, amount, currency) VALUES 
(24561087324, "Salg av aksje", "2024-03-10 12:30:00", 24561087324, "Aksjer", 36987451236, "Bruks", -5123, "NOK");

-- Transfer to account 2 : 24561087324
INSERT INTO mock_transaction (account_nr, transaction_title, time_stamp, debtor_account, debtor_name, creditor_account, creditor_name, amount, currency) VALUES 
(24561087324, "Kjøp av aksje", "2024-03-20 12:30:00", 36987451236, "Bruks", 24561087324, "Aksjer", +1000, "NOK");

-- Sending from account 3 : 78562345098
INSERT INTO mock_transaction (account_nr, transaction_title, time_stamp, debtor_account, debtor_name, creditor_account, creditor_name, amount, currency) VALUES
(78562345098, "Overføring fra sparekonto", "2024-03-15 12:30:00", 78562345098, "Spare", 36987451236, "Bruks", -2000, "NOK");

-- Transfer to account 3 : 78562345098
INSERT INTO mock_transaction (account_nr, transaction_title, time_stamp, debtor_account, debtor_name, creditor_account, creditor_name, amount, currency) VALUES
(78562345098, "Overføring til sparekonto", "2024-04-15 12:30:00", 36987451236, "Bruks", 78562345098, "Spare", +1500, "NOK");

-- Sending from account 4 : 36987451236
INSERT INTO mock_transaction (account_nr, transaction_title, time_stamp, debtor_account, debtor_name, creditor_account, creditor_name, amount, currency) VALUES
-- Februar
(36987451236, "Kjøp av fond", "2024-02-20 14:30:00", 36987451236, "Bruks", 98765432101, "Fond", -3000, "NOK"),
(36987451236, "AtB", "2024-02-21 08:45:00", 36987451236, "Bruks", 52076164659, "AtB", -585, "NOK"),
(36987451236, "Nelly.com", "2024-02-22 10:45:00", 36987451236, "Bruks", 72242066953, "Nelly.com", -500, "NOK"),
(36987451236, "Vinmonopolet", "2024-02-23 15:00:00", 36987451236, "Bruks", 19973799516, "Vinmonopolet", -500, "NOK"),
(36987451236, "Lille Szechaun Byåsen", "2024-02-23 18:00:00",  36987451236, "Bruks", 22833465929, "Lille Szechaun Byåsen", -400, "NOK"),
(36987451236, "Tier", "2024-02-24 15:20:00", 36987451236, "Bruks", 93118874001, "Tier", -35, "NOK"),
(36987451236, "Starbucks", "2024-02-24 16:00:00", 36987451236, "Bruks", 13515847198, "Starbucks", -100, "NOK"),
(36987451236, "Boozt.com", "2024-02-24 18:00:00", 36987451236, "Bruks", 59149472386, "Boozt.com", -1200, "NOK"),
(36987451236, "Nelly.com", "2024-02-25 10:45:00", 36987451236, "Bruks", 72242066953, "Nelly.com", -3000, "NOK"),
(36987451236, "Norwegian Air Shuttle", "2024-02-26 12:00:00", 36987451236, "Bruks", 11717568325, "Norwegian Air Shuttle", -1200, "NOK"),
-- Mars
(36987451236, "Coop Extra", "2024-03-01 14:10:00", 36987451236, "Bruks", 17727156891, "Coop Extra", -476, "NOK"),
(36987451236, "Studio 26", "2024-03-01 20:40:00", 36987451236, "Bruks", 53690605486, "Studio 26", -200, "NOK"),
(36987451236, "Studio 26", "2024-03-01 21:30:00", 36987451236, "Bruks", 53690605486, "Studio 26", -120, "NOK"),
(36987451236, "Studio 26", "2024-03-01 21:50:00", 36987451236, "Bruks", 53690605486, "Studio 26", -120, "NOK"),
(36987451236, "Club Downtown", "2024-03-01 22:30:00", 36987451236, "Bruks", 68829545787, "Club Downtown", -120, "NOK"),
(36987451236, "Club Downtown", "2024-03-01 22:50:00", 36987451236, "Bruks", 68829545787, "Club Downtown", -120, "NOK"),
(36987451236, "Studentersamfundet", "2024-03-01 23:30:00", 36987451236, "Bruks", 37039414487, "Studentersamfundet", -100, "NOK"),
(36987451236, "Sesam Burger", "2024-03-02 00:20:00", 36987451236, "Bruks", 85585440430, "Sesam Burger", -200, "NOK"),
(36987451236, "Foodora", "2024-03-02 17:30:00", 36987451236, "Bruks", 43863795963, "Foodora", -300, "NOK"),
(36987451236, "SiT Kafe", "2024-03-04 13:50:00", 36987451236, "Bruks", 56934565515, "SiT Kafe", -120, "NOK"),
(36987451236, "Nelly.com", "2024-03-05 13:50:00", 36987451236, "Bruks", 72242066953, "Nelly.com", -2500, "NOK"),
(36987451236, "SiT Kafe", "2024-03-08 13:50:00", 36987451236, "Bruks", 56934565515, "SiT Kafe", -120, "NOK"),
(36987451236, "SiT Kafe", "2024-03-14 13:50:00", 36987451236, "Bruks", 56934565515, "SiT Kafe", -120, "NOK"),
(36987451236, "Kjøp av fond", "2024-03-15 10:30:00", 36987451236, "Bruks", 98765432101, "Fond", -2000, "NOK"),
(36987451236, "SiT Kafe", "2024-03-19 13:50:00", 36987451236, "Bruks", 56934565515, "SiT Kafe", -120, "NOK"),
(24561087324, "Kjøp av aksje", "2024-03-20 09:30:00", 36987451236, "Bruks", 98765432101, "Aksjer", -1000, "NOK"),
(36987451236, "REMA1000", "2024-03-20 12:30:00", 36987451236, "Bruks", 11510553602, "REMA1000", -517, "NOK"),
(36987451236, "AtB", "2024-03-21 08:45:00", 36987451236, "Bruks", 52076164659, "AtB", -585, "NOK"),
(36987451236, "Ryde", "2024-03-22 15:20:00", 36987451236, "Bruks", 29343339488, "Ryde", -35, "NOK"),
(36987451236, "Vinmonopolet", "2024-03-23 15:00:00", 36987451236, "Bruks", 19973799516, "Vinmonopolet", -1000, "NOK"),
(36987451236, "Pizzabakeren", "2024-03-23 18:00:00", 36987451236, "Bruks", 81208986306, "Pizzabakeren", -179, "NOK"),
(36987451236, "Nelly.com", "2024-03-24 13:50:00", 36987451236, "Bruks", 72242066953, "Nelly.com", -1500, "NOK"),
(36987451236, "Bunnpris", "2024-03-25 18:10:00", 36987451236, "Bruks", 24613929754, "Bunnpris", -57, "NOK"),
(36987451236, "SiT Kafe", "2024-03-28 13:50:00", 36987451236, "Bruks", 56934565515, "SiT Kafe", -120, "NOK"),
(36987451236, "Clas Ohlson", "2024-03-29 08:55:00", 36987451236, "Bruks", 72138386450, "Clas Ohlson", -517, "NOK"),
(36987451236, "Den Gode Nabo", "2024-03-30 19:55:00", 36987451236, "Bruks", 96586542651, "Den Gode Nabo", -120, "NOK"),
(36987451236, "Den Gode Nabo", "2024-03-30 20:35:00", 36987451236, "Bruks", 96586542651, "Den Gode Nabo", -120, "NOK"),
(36987451236, "Wolt", "2024-03-31 19:30:00", 36987451236, "Bruks", 67751801398, "Wolt", -200, "NOK"),
-- April
(36987451236, "Komplett", "2024-04-03 09:40:00", 36987451236, "Bruks", 44455139114, "Komplett", -231, "NOK"),
(36987451236, "Nelly.com", "2024-04-05 13:50:00", 36987451236, "Bruks", 72242066953, "Nelly.com", -2000, "NOK"),
(36987451236, "Starbucks", "2024-04-08 16:30:00", 36987451236, "Bruks", 13515847198, "Starbucks", -200, "NOK"),
(36987451236, "SiT Kafe", "2024-04-12 13:50:00", 36987451236, "Bruks", 56934565515, "SiT Kafe", -120, "NOK"),
(36987451236, "Boozt.com", "2024-04-13 18:00:00", 36987451236, "Bruks", 59149472386, "Boozt.com", -600, "NOK"),
(36987451236, "Circle K", "2024-04-14 17:00:00", 36987451236, "Bruks", 14747574266, "Circle K", -670, "NOK"),
(36987451236, "Overføring til sparekonto", "2024-04-15 12:30:00", 36987451236, "Bruks", 78562345098, "Spare", -1500, "NOK");

-- Transfer to account 4  : 36987451236
INSERT INTO mock_transaction (account_nr, transaction_title, time_stamp, debtor_account, debtor_name, creditor_account, creditor_name, amount, currency) VALUES 
(36987451236, "Salg av fond", "2024-02-01 12:30:00", 98765432101, "Fond", 36987451236, "Bruks", +5123, "NOK"),
(36987451236, "Salg av aksje", "2024-03-10 12:30:00", 98765432101, "Aksjer", 36987451236, "Bruks", +5123, "NOK"),
(36987451236, "Overføring fra sparekonto", "2024-03-15 12:30:00", 78562345098, "Spare", 36987451236, "Bruks", +2000, "NOK"),
(36987451236, "Salg av fond", "2024-03-30 12:30:00", 98765432101, "Fond", 36987451236, "Bruks", +10123, "NOK"),
(36987451236, "Salg av fond", "2024-04-20 12:30:00", 98765432101, "Fond", 36987451236, "Bruks", +15123, "NOK");





/*
	User 3
	Username  : varchar : JackTanner39
	Firstname : varchar : Jack
	Lastname  : varchar : Tanner
*/
INSERT INTO mock_customer (username, first_name, last_name) VALUES ("JackTanner39", "Jack", "Tanner");

/*
	Account  
	AccountNr   : BigInt  : xxxxxxxxxxx
	Username    : varchar : 
	Balance     : BigInt  : xxxxxxx
	Currency    : varchar : NOK
	AccountType : varchar : 
	AccountName : varchar :	
*/
-- Legg til kontoer for JackTanner39
INSERT INTO mock_account (account_nr, username, balance, currency, account_type, account_name) VALUES 
(98765432102, "JackTanner39", 70000000, "NOK", "Aksjesparekonto", "Fond"),
(24561087325, "JackTanner39", 20000000, "NOK", "Aksjesparekonto", "Aksjer"),
(78562345099, "JackTanner39", 40000000, "NOK", "Sparekonto", "Spare"),
(36987451237, "JackTanner39", 3000000, "NOK", "Brukskonto", "Bruks");

/*
	Transaction 
	AccountNr        : BigInt   : xxxxxxxxxxx
	TransactionTitle : varchar  :
	Time             : DATETIME :
	DebtorAccount    : BigInt   : xxxxxxxxxxx
	DebtorName       : varchar  :
	CreditorAccount  : BigInt   : xxxxxxxxxxx
	CreditorName     : varchar  :
	amount           : BigInt   :
	currency         : varchar  : NOK
*/

-- Sending from account 1 : 98765432102
INSERT INTO mock_transaction (account_nr, transaction_title, time_stamp, debtor_account, debtor_name, creditor_account, creditor_name, amount, currency) VALUES 
(98765432102, "Salg av fond", "2024-02-01 12:30:00", 98765432102, "Fond", 36987451237, "Bruks", -5123, "NOK"),
(98765432102, "Salg av fond", "2024-03-30 12:30:00", 98765432102, "Fond", 36987451237, "Bruks", -10123, "NOK"),
(98765432102, "Salg av fond", "2024-04-20 12:30:00", 98765432102, "Fond", 36987451237, "Bruks", -15123, "NOK");
-- Transfer to account 1  : 98765432102
INSERT INTO mock_transaction (account_nr, transaction_title, time_stamp, debtor_account, debtor_name, creditor_account, creditor_name, amount, currency) VALUES 
(98765432102, "Kjøp av fond", "2024-02-20 14:30:00", 36987451237, "Bruks", 98765432102, "Fond", +3000, "NOK"),
(98765432102, "Kjøp av fond", "2024-03-15 10:30:00", 36987451237, "Bruks", 98765432102, "Fond", +2000, "NOK");

-- Sending from account 2 : 24561087325
INSERT INTO mock_transaction (account_nr, transaction_title, time_stamp, debtor_account, debtor_name, creditor_account, creditor_name, amount, currency) VALUES 
(24561087325, "Salg av aksje", "2024-03-10 12:30:00", 24561087325, "Aksjer", 36987451237, "Bruks", -5123, "NOK");

-- Transfer to account 2 : 24561087325
INSERT INTO mock_transaction (account_nr, transaction_title, time_stamp, debtor_account, debtor_name, creditor_account, creditor_name, amount, currency) VALUES 
(24561087325, "Kjøp av aksje", "2024-03-20 12:30:00", 36987451237, "Bruks", 24561087325, "Aksjer", +1000, "NOK");

-- Sending from account 3 : 78562345099
INSERT INTO mock_transaction (account_nr, transaction_title, time_stamp, debtor_account, debtor_name, creditor_account, creditor_name, amount, currency) VALUES
(78562345099, "Overføring fra sparekonto", "2024-03-15 12:30:00", 78562345099, "Spare", 36987451237, "Bruks", -2000, "NOK");

-- Transfer to account 3 : 78562345099
INSERT INTO mock_transaction (account_nr, transaction_title, time_stamp, debtor_account, debtor_name, creditor_account, creditor_name, amount, currency) VALUES
(78562345099, "Overføring til sparekonto", "2024-04-15 12:30:00", 36987451237, "Bruks", 78562345099, "Spare", +1500, "NOK");

-- Sending from account 4 : 36987451237
INSERT INTO mock_transaction (account_nr, transaction_title, time_stamp, debtor_account, debtor_name, creditor_account, creditor_name, amount, currency) VALUES
-- Februar
(36987451237, "Kjøp av fond", "2024-02-20 14:30:00", 36987451237, "Bruks", 98765432102, "Fond", -3000, "NOK"),
(36987451237, "AtB", "2024-02-21 08:45:00", 36987451237, "Bruks", 52076164659, "AtB", -585, "NOK"),
(36987451237, "Nelly.com", "2024-02-22 10:45:00", 36987451237, "Bruks", 72242066953, "Nelly.com", -500, "NOK"),
(36987451237, "Vinmonopolet", "2024-02-23 15:00:00", 36987451237, "Bruks", 19973799516, "Vinmonopolet", -500, "NOK"),
(36987451237, "Lille Szechaun Byåsen", "2024-02-23 18:00:00",  36987451237, "Bruks", 22833465929, "Lille Szechaun Byåsen", -400, "NOK"),
(36987451237, "Tier", "2024-02-24 15:20:00", 36987451237, "Bruks", 93118874001, "Tier", -35, "NOK"),
(36987451237, "Starbucks", "2024-02-24 16:00:00", 36987451237, "Bruks", 13515847198, "Starbucks", -100, "NOK"),
(36987451237, "Boozt.com", "2024-02-24 18:00:00", 36987451237, "Bruks", 59149472386, "Boozt.com", -1200, "NOK"),
(36987451237, "Norwegian Air Shuttle", "2024-02-26 12:00:00", 36987451237, "Bruks", 11717568325, "Norwegian Air Shuttle", -1200, "NOK"),
(36987451237, "Elkjøp", "2024-02-27 13:00:00", 36987451237, "Bruks", 35544631408, "Elkjøp", -20000, "NOK"),
-- Mars
(36987451237, "Coop Extra", "2024-03-01 14:10:00", 36987451237, "Bruks", 17727156891, "Coop Extra", -476, "NOK"),
(36987451237, "Studio 26", "2024-03-01 20:40:00", 36987451237, "Bruks", 53690605486, "Studio 26", -200, "NOK"),
(36987451237, "Studio 26", "2024-03-01 21:30:00", 36987451237, "Bruks", 53690605486, "Studio 26", -120, "NOK"),
(36987451237, "Studio 26", "2024-03-01 21:50:00", 36987451237, "Bruks", 53690605486, "Studio 26", -120, "NOK"),
(36987451237, "Club Downtown", "2024-03-01 22:30:00", 36987451237, "Bruks", 68829545787, "Club Downtown", -120, "NOK"),
(36987451237, "Club Downtown", "2024-03-01 22:50:00", 36987451237, "Bruks", 68829545787, "Club Downtown", -120, "NOK"),
(36987451237, "Studentersamfundet", "2024-03-01 23:30:00", 36987451237, "Bruks", 37039414487, "Studentersamfundet", -100, "NOK"),
(36987451237, "Sesam Burger", "2024-03-02 00:20:00", 36987451237, "Bruks", 85585440430, "Sesam Burger", -200, "NOK"),
(36987451237, "Foodora", "2024-03-02 17:30:00", 36987451237, "Bruks", 43863795963, "Foodora", -300, "NOK"),
(36987451237, "SiT Kafe", "2024-03-04 13:50:00", 36987451237, "Bruks", 56934565515, "SiT Kafe", -120, "NOK"),
(36987451237, "SiT Kafe", "2024-03-08 13:50:00", 36987451237, "Bruks", 56934565515, "SiT Kafe", -120, "NOK"),
(36987451237, "SiT Kafe", "2024-03-14 13:50:00", 36987451237, "Bruks", 56934565515, "SiT Kafe", -120, "NOK"),
(36987451237, "Kjøp av fond", "2024-03-15 10:30:00", 36987451237, "Bruks", 98765432102, "Fond", -2000, "NOK"),
(36987451237, "SiT Kafe", "2024-03-19 13:50:00", 36987451237, "Bruks", 56934565515, "SiT Kafe", -120, "NOK"),
(24561087325, "Kjøp av aksje", "2024-03-20 09:30:00", 36987451237, "Bruks", 98765432102, "Aksjer", -1000, "NOK"),
(36987451237, "REMA1000", "2024-03-20 12:30:00", 36987451237, "Bruks", 11510553602, "REMA1000", -517, "NOK"),
(36987451237, "AtB", "2024-03-21 08:45:00", 36987451237, "Bruks", 52076164659, "AtB", -585, "NOK"),
(36987451237, "Ryde", "2024-03-22 15:20:00", 36987451237, "Bruks", 29343339488, "Ryde", -35, "NOK"),
(36987451237, "Vinmonopolet", "2024-03-23 15:00:00", 36987451237, "Bruks", 19973799516, "Vinmonopolet", -1000, "NOK"),
(36987451237, "Pizzabakeren", "2024-03-23 18:00:00", 36987451237, "Bruks", 81208986306, "Pizzabakeren", -179, "NOK"),
(36987451237, "Bunnpris", "2024-03-25 18:10:00", 36987451237, "Bruks", 24613929754, "Bunnpris", -57, "NOK"),
(36987451237, "SiT Kafe", "2024-03-28 13:50:00", 36987451237, "Bruks", 56934565515, "SiT Kafe", -120, "NOK"),
(36987451237, "Clas Ohlson", "2024-03-29 08:55:00", 36987451237, "Bruks", 72138386450, "Clas Ohlson", -517, "NOK"),
(36987451237, "Den Gode Nabo", "2024-03-30 19:55:00", 36987451237, "Bruks", 96586542651, "Den Gode Nabo", -120, "NOK"),
(36987451237, "Den Gode Nabo", "2024-03-30 20:35:00", 36987451237, "Bruks", 96586542651, "Den Gode Nabo", -120, "NOK"),
(36987451237, "Wolt", "2024-03-31 19:30:00", 36987451237, "Bruks", 67751801398, "Wolt", -200, "NOK"),
-- April
(36987451237, "Komplett", "2024-04-03 09:40:00", 36987451237, "Bruks", 44455139114, "Komplett", -231, "NOK"),
(36987451237, "Elkjøp", "2024-04-05 13:00:00", 36987451237, "Bruks", 35544631408, "Elkjøp", -20000, "NOK"),
(36987451237, "Starbucks", "2024-04-08 16:30:00", 36987451237, "Bruks", 13515847198, "Starbucks", -200, "NOK"),
(36987451237, "SiT Kafe", "2024-04-12 13:50:00", 36987451237, "Bruks", 56934565515, "SiT Kafe", -120, "NOK"),
(36987451237, "Boozt.com", "2024-04-13 18:00:00", 36987451237, "Bruks", 59149472386, "Boozt.com", -600, "NOK"),
(36987451237, "Circle K", "2024-04-14 17:00:00", 36987451237, "Bruks", 14747574266, "Circle K", -670, "NOK"),
(36987451237, "Overføring til sparekonto", "2024-04-15 12:30:00", 36987451237, "Bruks", 78562345099, "Spare", -1500, "NOK");

-- Transfer to account 4  : 36987451237
INSERT INTO mock_transaction (account_nr, transaction_title, time_stamp, debtor_account, debtor_name, creditor_account, creditor_name, amount, currency) VALUES 
(36987451237, "Salg av fond", "2024-02-01 12:30:00", 98765432102, "Fond", 36987451237, "Bruks", +5123, "NOK"),
(36987451237, "Salg av aksje", "2024-03-10 12:30:00", 98765432102, "Aksjer", 36987451237, "Bruks", +5123, "NOK"),
(36987451237, "Overføring fra sparekonto", "2024-03-15 12:30:00", 78562345099, "Spare", 36987451237, "Bruks", +2000, "NOK"),
(36987451237, "Salg av fond", "2024-03-30 12:30:00", 98765432102, "Fond", 36987451237, "Bruks", +10123, "NOK"),
(36987451237, "Salg av fond", "2024-04-20 12:30:00", 98765432102, "Fond", 36987451237, "Bruks", +15123, "NOK");














/*
	User 4
	Username  : varchar : AliceLearn64
	Firstname : varchar : Alice
	Lastname  : varchar : Learn
*/
INSERT INTO mock_customer (username, first_name, last_name) VALUES ("AliceLearn64", "Alice", "Learn");

/*
	Account
	AccountNr   : BigInt  : xxxxxxxxxxx
	Username    : varchar :
	Balance     : BigInt  : xxxxxxx
	Currency    : varchar : NOK
	AccountType : varchar :
	AccountName : varchar :
*/
-- Legg til kontoer for AliceLearn64
INSERT INTO mock_account (account_nr, username, balance, currency, account_type, account_name) VALUES
                                                                                           (98765432103, "AliceLearn64", 83154600, "NOK", "Aksjesparekonto", "Fond"),
                                                                                           (24561087326, "AliceLearn64", 39325123, "NOK", "Aksjesparekonto", "Aksjer"),
                                                                                           (78562345100, "AliceLearn64", 51289570, "NOK", "Sparekonto", "Spare"),
                                                                                           (36987451238, "AliceLearn64", 4876355, "NOK", "Brukskonto", "Bruks");

/*
	Transaction
	AccountNr        : BigInt   : xxxxxxxxxxx
	TransactionTitle : varchar  :
	Time             : DATETIME :
	DebtorAccount    : BigInt   : xxxxxxxxxxx
	DebtorName       : varchar  :
	CreditorAccount  : BigInt   : xxxxxxxxxxx
	CreditorName     : varchar  :
	amount           : BigInt   :
	currency         : varchar  : NOK
*/

-- Sending from account 1 : 98765432103
INSERT INTO mock_transaction (account_nr, transaction_title, time_stamp, debtor_account, debtor_name, creditor_account, creditor_name, amount, currency) VALUES
(98765432103, "Salg av fond", "2024-02-01 12:30:00", 98765432103, "Fond", 36987451238, "Bruks", -5123, "NOK"),
(98765432103, "Salg av fond", "2024-03-30 12:30:00", 98765432103, "Fond", 36987451238, "Bruks", -10123, "NOK"),
(98765432103, "Salg av fond", "2024-04-20 12:30:00", 98765432103, "Fond", 36987451238, "Bruks", -15123, "NOK");

-- Transfer to account 1  : 98765432103
INSERT INTO mock_transaction (account_nr, transaction_title, time_stamp, debtor_account, debtor_name, creditor_account, creditor_name, amount, currency) VALUES
(98765432103, "Kjøp av fond", "2024-02-20 14:30:00", 36987451238, "Bruks", 98765432103, "Fond", +3000, "NOK"),
(98765432103, "Kjøp av fond", "2024-03-15 10:30:00", 36987451238, "Bruks", 98765432103, "Fond", +2000, "NOK");

-- Sending from account 2 : 24561087326
INSERT INTO mock_transaction (account_nr, transaction_title, time_stamp, debtor_account, debtor_name, creditor_account, creditor_name, amount, currency) VALUES
    (24561087326, "Salg av aksje", "2024-03-10 12:30:00", 24561087326, "Aksjer", 36987451238, "Bruks", -5123, "NOK");

-- Transfer to account 2 : 24561087326
INSERT INTO mock_transaction (account_nr, transaction_title, time_stamp, debtor_account, debtor_name, creditor_account, creditor_name, amount, currency) VALUES
    (24561087326, "Kjøp av aksje", "2024-03-20 12:30:00", 36987451238, "Bruks", 24561087326, "Aksjer", +1000, "NOK");

-- Sending from account 3 : 78562345099
INSERT INTO mock_transaction (account_nr, transaction_title, time_stamp, debtor_account, debtor_name, creditor_account, creditor_name, amount, currency) VALUES
    (78562345100, "Overføring fra sparekonto", "2024-03-15 12:30:00", 78562345099, "Spare", 36987451238, "Bruks", -2000, "NOK");

-- Transfer to account 3 : 78562345099
INSERT INTO mock_transaction (account_nr, transaction_title, time_stamp, debtor_account, debtor_name, creditor_account, creditor_name, amount, currency) VALUES
    (78562345100, "Overføring til sparekonto", "2024-04-15 12:30:00", 36987451238, "Bruks", 78562345099, "Spare", +1500, "NOK");

-- Sending from account 4 : 36987451238
INSERT INTO mock_transaction (account_nr, transaction_title, time_stamp, debtor_account, debtor_name, creditor_account, creditor_name, amount, currency) VALUES
-- Februar
(36987451238, "Kjøp av fond", "2024-02-20 14:30:00", 36987451238, "Bruks", 98765432103, "Fond", -3000, "NOK"),
(36987451238, "AtB", "2024-02-21 08:45:00", 36987451238, "Bruks", 52076164659, "AtB", -585, "NOK"),
(36987451238, "Nelly.com", "2024-02-22 10:45:00", 36987451238, "Bruks", 72242066953, "Nelly.com", -500, "NOK"),
(36987451238, "Vinmonopolet", "2024-02-23 15:00:00", 36987451238, "Bruks", 19973799516, "Vinmonopolet", -500, "NOK"),
(36987451238, "Lille Szechaun Byåsen", "2024-02-23 18:00:00",  36987451238, "Bruks", 22833465929, "Lille Szechaun Byåsen", -400, "NOK"),
(36987451238, "Tier", "2024-02-24 15:20:00", 36987451238, "Bruks", 93118874001, "Tier", -35, "NOK"),
(36987451238, "Starbucks", "2024-02-24 16:00:00", 36987451238, "Bruks", 13515847198, "Starbucks", -100, "NOK"),
(36987451238, "Boozt.com", "2024-02-24 18:00:00", 36987451238, "Bruks", 59149472386, "Boozt.com", -1200, "NOK"),
(36987451238, "Norwegian Air Shuttle", "2024-02-26 12:00:00", 36987451238, "Bruks", 11717568325, "Norwegian Air Shuttle", -1200, "NOK"),

-- Mars
(36987451238, "Coop Extra", "2024-03-01 14:10:00", 36987451238, "Bruks", 17727156891, "Coop Extra", -476, "NOK"),
(36987451238, "Studio 26", "2024-03-01 20:40:00", 36987451238, "Bruks", 53690605486, "Studio 26", -200, "NOK"),
(36987451238, "Studio 26", "2024-03-01 21:30:00", 36987451238, "Bruks", 53690605486, "Studio 26", -120, "NOK"),
(36987451238, "Studio 26", "2024-03-01 21:50:00", 36987451238, "Bruks", 53690605486, "Studio 26", -120, "NOK"),
(36987451238, "Club Downtown", "2024-03-01 22:30:00", 36987451238, "Bruks", 68829545787, "Club Downtown", -120, "NOK"),
(36987451238, "Club Downtown", "2024-03-01 22:50:00", 36987451238, "Bruks", 68829545787, "Club Downtown", -120, "NOK"),
(36987451238, "Studentersamfundet", "2024-03-01 23:30:00", 36987451238, "Bruks", 37039414487, "Studentersamfundet", -100, "NOK"),
(36987451238, "Sesam Burger", "2024-03-02 00:20:00", 36987451238, "Bruks", 85585440430, "Sesam Burger", -200, "NOK"),
(36987451238, "Foodora", "2024-03-02 17:30:00", 36987451238, "Bruks", 43863795963, "Foodora", -300, "NOK"),
(36987451238, "SiT Kafe", "2024-03-04 13:50:00", 36987451238, "Bruks", 56934565515, "SiT Kafe", -120, "NOK"),
(36987451238, "SiT Kafe", "2024-03-08 13:50:00", 36987451238, "Bruks", 56934565515, "SiT Kafe", -120, "NOK"),
(36987451238, "SiT Kafe", "2024-03-14 13:50:00", 36987451238, "Bruks", 56934565515, "SiT Kafe", -120, "NOK"),
(36987451238, "Kjøp av fond", "2024-03-15 10:30:00", 36987451238, "Bruks", 98765432103, "Fond", -2000, "NOK"),
(36987451238, "SiT Kafe", "2024-03-19 13:50:00", 36987451238, "Bruks", 56934565515, "SiT Kafe", -120, "NOK"),
(24561087326, "Kjøp av aksje", "2024-03-20 09:30:00", 36987451238, "Bruks", 98765432103, "Aksjer", -1000, "NOK"),
(36987451238, "REMA1000", "2024-03-20 12:30:00", 36987451238, "Bruks", 11510553602, "REMA1000", -517, "NOK"),
(36987451238, "AtB", "2024-03-21 08:45:00", 36987451238, "Bruks", 52076164659, "AtB", -585, "NOK"),
(36987451238, "Ryde", "2024-03-22 15:20:00", 36987451238, "Bruks", 29343339488, "Ryde", -35, "NOK"),
(36987451238, "Vinmonopolet", "2024-03-23 15:00:00", 36987451238, "Bruks", 19973799516, "Vinmonopolet", -1000, "NOK"),
(36987451238, "Pizzabakeren", "2024-03-23 18:00:00", 36987451238, "Bruks", 81208986306, "Pizzabakeren", -179, "NOK"),
(36987451238, "Bunnpris", "2024-03-25 18:10:00", 36987451238, "Bruks", 24613929754, "Bunnpris", -57, "NOK"),
(36987451238, "SiT Kafe", "2024-03-28 13:50:00", 36987451238, "Bruks", 56934565515, "SiT Kafe", -120, "NOK"),
(36987451238, "Clas Ohlson", "2024-03-29 08:55:00", 36987451238, "Bruks", 72138386450, "Clas Ohlson", -517, "NOK"),
(36987451238, "Den Gode Nabo", "2024-03-30 19:55:00", 36987451238, "Bruks", 96586542651, "Den Gode Nabo", -120, "NOK"),
(36987451238, "Den Gode Nabo", "2024-03-30 20:35:00", 36987451238, "Bruks", 96586542651, "Den Gode Nabo", -120, "NOK"),
(36987451238, "Wolt", "2024-03-31 19:30:00", 36987451238, "Bruks", 67751801398, "Wolt", -200, "NOK"),
-- April
(36987451238, "Komplett", "2024-04-03 09:40:00", 36987451238, "Bruks", 44455139114, "Komplett", -231, "NOK"),
(36987451238, "Starbucks", "2024-04-08 16:30:00", 36987451238, "Bruks", 13515847198, "Starbucks", -200, "NOK"),
(36987451238, "SiT Kafe", "2024-04-12 13:50:00", 36987451238, "Bruks", 56934565515, "SiT Kafe", -120, "NOK"),
(36987451238, "Boozt.com", "2024-04-13 18:00:00", 36987451238, "Bruks", 59149472386, "Boozt.com", -600, "NOK"),
(36987451238, "Circle K", "2024-04-14 17:00:00", 36987451238, "Bruks", 14747574266, "Circle K", -670, "NOK"),
(36987451238, "Overføring til sparekonto", "2024-04-15 12:30:00", 36987451238, "Bruks", 78562345099, "Spare", -1500, "NOK");

-- Transfer to account 4  : 36987451238
INSERT INTO mock_transaction (account_nr, transaction_title, time_stamp, debtor_account, debtor_name, creditor_account, creditor_name, amount, currency) VALUES
(36987451238, "Salg av fond", "2024-02-01 12:30:00", 98765432103, "Fond", 36987451238, "Bruks", +5123, "NOK"),
(36987451238, "Salg av aksje", "2024-03-10 12:30:00", 98765432103, "Aksjer", 36987451238, "Bruks", +5123, "NOK"),
(36987451238, "Overføring fra sparekonto", "2024-03-15 12:30:00", 78562345099, "Spare", 36987451238, "Bruks", +2000, "NOK"),
(36987451238, "Salg av fond", "2024-03-30 12:30:00", 98765432103, "Fond", 36987451238, "Bruks", +10123, "NOK"),
(36987451238, "Salg av fond", "2024-04-20 12:30:00", 98765432103, "Fond", 36987451238, "Bruks", +15123, "NOK");

-- Removes 25% of the mock_transactions by timestamp
DELETE FROM mock_transaction
ORDER BY time_stamp
LIMIT 59;