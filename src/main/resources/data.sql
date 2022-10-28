INSERT INTO MOVIES (M_YEAR, TITLE, STUDIOS, PRODUCERS, WINNER)
SELECT * FROM CSVREAD('classpath:data/movielist.csv', null, 'fieldSeparator=;');