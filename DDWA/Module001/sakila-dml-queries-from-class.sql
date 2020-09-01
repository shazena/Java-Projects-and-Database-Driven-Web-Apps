USE Sakila;

SELECT * FROM Actor ORDER BY actor_id DESC;

INSERT INTO Actor (First_Name, Last_Name, Last_update)
VALUES ('Shazena', 'Khan', NOW());
 
INSERT INTO actor (first_name, Last_name, Last_update)
VALUES ('Chelsea', 'Miller', NOW()),
		('Karma', 'Dolkar', NOW());

INSERT INTO Actor (Actor_id, First_Name, Last_Name, Last_update)
VALUES (205, 'Narish', 'Singh', NOW());

INSERT INTO Actor (First_Name, Last_Name, Last_update)
VALUES ('Kristina', 'Zakharova', NOW());

SELECT * FROM film;

INSERT INTO film_actor (last_update, film_id, actor_id)
	VALUES (NOW(), 5, 202);

SELECT a.* FROM actor a
INNER JOIN film_actor fa
ON a.actor_id = fa.actor_id
INNER JOIN film f
ON f.film_id = fa.film_id
WHERE f.film_id = 5;