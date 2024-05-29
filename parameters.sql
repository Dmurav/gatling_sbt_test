DROP TABLE parameters;

CREATE TABLE IF NOT EXISTS parameters (
	searchCriterion text NOT NULL,
	searchComputerName text NOT NULL
);

INSERT INTO parameters (searchCriterion, searchComputerName) VALUES
	('Macbook', 'MacBook Pro'),
	('eee', 'ASUS Eee PC 1005PE'),
	('Aorus', 'Aorus 15 xe');

SELECT * FROM parameters;