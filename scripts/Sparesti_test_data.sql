-- Sparesti DATABASE

use sparesti;

/*
	username varchar(255) NOT NULL
	birthdate DATE
	current_account BigInt
	email varchar(255)
	first_name varchar(255)
	last_name varchar(255)
	monthly_fixed_expenses BigInt
	monthly_income BigInt
	monthly_savings BigInt
	password varchar(255) -- Salted and hashed password
	profile_picture tinyblob
	savings_account BigInt
*/
INSERT INTO users (username, birth_date, current_account, email, first_name, last_name, monthly_fixed_expenses, monthly_income, monthly_savings, password_hash, profile_picture, savings_account) VALUES
    ("JohnSmith12", "1995-01-01", 45651009037, "john.smith@gmail.com", "John", "Smith", 10000, 10500, 500, "$2a$10$vlAdhr1/fzzqxoLmovu2.usZyDpFNWPSKPS863rIWOrHM/j181STy", NULL, 62367154487),
    ("AliceLearn64", "2000-01-01", 36987451238, "alice.learn@gmail.com", "Alice", "Learn", 9000, 95000, 500, "$2a$10$vlAdhr1/fzzqxoLmovu2.usZyDpFNWPSKPS863rIWOrHM/j181STy", NULL, 78562345100);


/*
	challenge_id BigInt NOT NULL AUTO_INCREMENT
	active bit(1) NOT NULL
	challenge_description varchar(255)
	challenge_title varchar(255)
	current_sum BigInt
	expiration_date DATETIME
	goal_sum BigInt
	recurring_interval enum('NONE','DAILY','WEEKLY','MONTHLY'),
	start_date DATETIME
	username varchar(255)
*/
INSERT INTO challenge (active, challenge_description, challenge_title, current_sum, expiration_date, goal_sum, recurring_interval, start_date, username) VALUES
    (1, "Kutt ned på alkohol kjøp", "Alkohol reduksjon", 0, "2024-05-01 20:00:00", 2000, 'MONTHLY', "2024-04-01 20:00:00", "JohnSmith12");

INSERT INTO challenge_log (challenge_id, username, challenge_title, challenge_description, goal_sum, challenge_achieved_sum, completion_date, theme, accepted) VALUES
    (2, "JohnSmith12", "Alkohol reduksjon", "Kutt ned på alkohol kjøp", 100, 100, "2024-04-01 20:00:00", 10, TRUE),
    (3, "JohnSmith12", "Reduser dagligvare forbruket", "Bruke mindre penger på unødvendige dagligvarer", 100, 100, "2024-05-01 20:00:00", 0, TRUE),
    (4, "JohnSmith12", "Reduser spising ute", "Bruke mindre penger på unødvendige restaurant og cafe besøk", 100, 100, "2024-05-02 20:00:00", 1, TRUE),
    (5, "JohnSmith12", "Reduser dagligvare forbruket", "Bruke mindre penger på unødvendige dagligvarer", 100, 100, "2024-05-08 20:00:00", 0, TRUE),
    (6, "JohnSmith12", "Reduser dagligvare forbruket", "Bruke mindre penger på unødvendige dagligvarer", 100, 100, "2024-05-09 20:00:00", 0, TRUE),
    (7, "AliceLearn64", "Alkohol reduksjon", "Kutt ned på alkohol kjøp", 100, 100, "2024-04-01 20:00:00", 10, TRUE),
    (8, "AliceLearn64", "Reduser dagligvare forbruket", "Bruke mindre penger på unødvendige dagligvarer", 100, 100, "2024-05-01 20:00:00", 0, TRUE),
    (9, "AliceLearn64", "Reduser spising ute", "Bruke mindre penger på unødvendige restaurant og cafe besøk", 100, 100, "2024-05-02 20:00:00", 1, TRUE),
    (10, "AliceLearn64", "Reduser dagligvare forbruket", "Bruke mindre penger på unødvendige dagligvarer", 100, 100, "2024-05-08 20:00:00", 0, TRUE),
    (11, "AliceLearn64", "Reduser dagligvare forbruket", "Bruke mindre penger på unødvendige dagligvarer", 100, 100, "2024-05-09 20:00:00", 0, TRUE);

/*
	milestone_id BigInt NOT NULL AUTO_INCREMENT,
	deadline_date DATETIME,
	milestone_current_sum BigInt,
	milestone_description varchar(255),
	milestone_goal_sum BigInt,
	milestone_image tinyblob,
	milestone_title varchar(255),
	start_date DATETIME,
	username varchar(255),
*/
INSERT INTO milestone (deadline_date, milestone_current_sum, milestone_description, milestone_goal_sum, milestone_image, milestone_title, start_date, username) VALUES
    ("2024-06-01 20:00:00", 1000, "Penger til europa ferie", 10000, NULL, "Ferie penger", "2024-02-01 20:00:00", "JohnSmith12"),
    ("2026-06-01 20:00:00", 100000, "Penger til hus", 1000000, NULL, "Hus", "2019-02-01 20:00:00", "JohnSmith12");

/*
	milestone_id BigInt NOT NULL AUTO_INCREMENT,
	completion_date DATETIME,
	milestone_achieved_sum BigInt,
	milestone_description varchar(255),
	milestone_goal_sum BigInt,
	milestone_image tinyblob,
	milestone_title varchar(255),
	username varchar(255),
*/
INSERT INTO milestone_log (milestone_id, completion_date, milestone_achieved_sum, milestone_description, milestone_goal_sum, milestone_image, milestone_title, username) VALUES
    (1, "2024-03-01 20:00:00", 17500, "Penger til gaming PC", 17500, NULL, "Gaming PC", "JohnSmith12"),
    (2, "2024-03-20 20:00:00", 600000, "Penger til brukt bil", 17500, NULL, "Gaming PC", "AliceLearn64"),
    (3, "2024-04-20 20:00:00", 5000, "Penger til ny sykkel", 10000, NULL, "Sykkel", "JohnSmith12"),
    (4, "2024-04-28 20:00:00", 56, "Penger til Rosenborg Bakeri brød", 57, NULL, "Brød", "JohnSmith12"),
    (5, "2024-02-20 20:00:00", 17000, "Fortnite V-Bucks", 18000, NULL, "Fortnite", "JohnSmith12"),
    (6, "2024-01-20 20:00:00", 1428, "Nye boksere", 1567, NULL, "Nye boksere", "JohnSmith12");




/*
	achievement_id BigInt NOT NULL AUTO_INCREMENT,
	achievement_description varchar(255),
	achievement_title varchar(255),
	badge tinyblob,
*/
INSERT INTO achievement (achievement_title, achievement_description) VALUES
    ("Fullfør din første sparemål", "Spar deg frem til ditt første sparemål!"),
    ("Fullfør din første utfordringer", "Fullfør din aller første spareutfordring på vei mot et sparemål!"),
    ("Fullfør 5 sparemål", "Kom i mål med fem av dine sparemål!"),
    ("Fullfør 5 utfordringer", "Fullfør fem spareutfordringer mot dine sparemål!"),
    ("Fullfør 10 sparemål", "Kom i mål med ti av dine sparemål"),
    ("Fullfør 10 utfordringer", "Fullfør ti spareutfordringer mot dine sparemål!"),
    ('Spar 1000NOK', 'Spar totalt 1000NOK på alle dine sparemål og utfordringer'),
    ('Spar 5000NOK', 'Spar totalt 5000NOK på alle dine sparemål og utfordringer'),
    ('Spar 10000NOK', 'Spar totalt 10000NOK på alle dine sparemål og utfordringer'),
    ('Spar 50000NOK', 'Spar totalt 50000NOK på alle dine sparemål og utfordringer'),
    ('Spar 100000NOK', 'Spar totalt 100000NOK på alle dine sparemål og utfordringer');



/*
	username varchar(255) NOT NULL,
	achievement_id BigInt NOT NULL,
*/
# INSERT INTO user_achievements (username, achievement_id) VALUES
#     ("JohnSmith12", 1),
#     ("JohnSmith12", 4),
#     ("AliceLearn64", 1),
#     ("AliceLearn64", 4);

INSERT INTO conditions (achievement_id, quantity, condition_type)
VALUES
    (1, 1, "MILESTONES"),
    (2, 1, "CHALLENGES"),
    (3, 5, "MILESTONES"),
    (4, 5, "CHALLENGES"),
    (5, 10, "MILESTONES"),
    (6, 10, "CHALLENGES"),
    (7, 1000, "SAVINGS"),
    (8, 50000, "SAVINGS"),
    (9, 10000, "SAVINGS"),
    (10, 50000, "SAVINGS"),
    (11, 100000, "SAVINGS");
