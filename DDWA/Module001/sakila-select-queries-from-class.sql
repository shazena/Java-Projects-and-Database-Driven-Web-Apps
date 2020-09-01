USE sakila;

SELECT a.address, c.city, a.postal_code, t.country FROM Address a
INNER JOIN City c
ON a.city_id = c.city_id
INNER JOIN Country t
ON c.country_id = t.country_id; 

SELECT * from address;

SELECT * from city;

SELECT t.country, COUNT(*) FROM Address a
INNER JOIN City c
ON a.city_id = c.city_id
INNER JOIN Country t
ON c.country_id = t.country_id
GROUP BY t.country
ORDER BY COUNT(*) DESC;


SELECT a.first_name, a.last_name, f.title from Actor a
INNER JOIN Film_Actor b
ON a.actor_id = b.actor_id
INNER JOIN Film f
ON b.film_id = f.film_id;

SELECT a.first_name, a.last_name, COUNT(*) from Actor a
INNER JOIN Film_Actor b
ON a.actor_id = b.actor_id
INNER JOIN Film f
ON b.film_id = f.film_id
GROUP BY a.actor_id
ORDER BY COUNT(*) DESC;

SELECT film_id, title, language_id, original_language_id FROM Film;
SELECT * from language;

SELECT a.name, COUNT(*) FROM Language a
INNER JOIN Film b
ON a.language_id = b.language_id
GROUP BY a.language_id;

SELECT l.name, COUNT(f.language_id) FROM film f
INNER JOIN language l
ON f.language_id = l.language_id
GROUP BY l.name;


-- How many customers does each store have? Show me the Store Id and the number of customers they have. 

SELECT a.store_id, count(*) FROM store a
INNER JOIN Customer b
ON a.store_id = b.store_id
GROUP BY a.store_id;

SELECT a.store_id, count(*) FROM customer a
INNER JOIN Store b
ON a.store_id = b.store_id
GROUP BY a.store_id;

SELECT a.store_id, count(*) FROM customer a
INNER JOIN Store b
ON b.store_id = a.store_id
GROUP BY a.store_id;



-- top 5 staff members with most payments processed

SELECT s.staff_id, s.first_name, s.last_name, COUNT(p.payment_id) FROM Staff s
INNER JOIN Payment p
ON s.staff_id = p.staff_id
GROUP BY s.staff_id
ORDER BY COUNT(p.payment_id) DESC
LIMIT 5;

