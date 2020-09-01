USE HotelShazena;

-- 1. Write a query that returns a list of reservations 
-- that end in July 2023, including the name of the guest, 
-- the room number(s), and the reservation dates. 

SELECT 
    FirstName, LastName, RoomNumber, StartDate, EndDate
FROM
    Reservation r
        INNER JOIN
    Guest g ON r.GuestId = g.GuestId
        INNER JOIN
    ReservationRoom rr ON r.ReservationId = rr.ReservationId
WHERE
    EndDate BETWEEN '2023-07-01' AND '2023-07-31';

-- ------------------------------------------------------------------------------------
-- 4 Rows Returned
-- FirstName	LastName	RoomNumber	StartDate	EndDate
-- Shazena		Khan		205			6/28/2023	7/2/2023
-- Walter		Holaway		204			7/13/2023	7/14/2023
-- Wilfred		Vise		401			7/18/2023	7/21/2023
-- Bettyann		Seery		303			7/28/2023	7/29/2023

-- ------------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------------
-- 2. Write a query that returns a list of all reservations 
-- for rooms with a jacuzzi, displaying the guest's name, 
-- the room number, and the dates of the reservation.

SELECT 
    g.FirstName, g.LastName, rr.RoomNumber, r.StartDate, r.EndDate
FROM
    Reservation r
        INNER JOIN
    Guest g ON r.GuestId = g.GuestId
        INNER JOIN
    ReservationRoom rr ON r.ReservationId = rr.ReservationId
        INNER JOIN
    Room ro ON rr.RoomNumber = ro.RoomNumber
        INNER JOIN
    RoomAmenity ra ON ro.RoomNumber = ra.RoomNumber
        INNER JOIN
    Amenity a ON ra.AmenityId = a.AmenityId
WHERE
    AmenityName = 'Jacuzzi';
    -- ????????????????????????????????????????????????????? Number of inner joins
-- ------------------------------------------------------------------------------------
-- 11 rows returned
-- FirstName	LastName	RoomNumber	StartDate	EndDate
-- Karie		Yang		201			3/6/2023	3/7/2023
-- Bettyann		Seery		203			2/5/2023	2/10/2023
-- Karie		Yang		203			9/13/2023	9/15/2023
-- Shazena		Khan		205			6/28/2023	7/2/2023
-- Wilfred		Vise		207			4/23/2023	4/24/2023
-- Walter		Holaway		301			4/9/2023	4/13/2023
-- Mack			Simmer		301			11/22/2023	11/25/2023
-- Bettyann		Seery		303			7/28/2023	7/29/2023
-- Duane		Cullison	305			2/22/2023	2/24/2023
-- Bettyann		Seery		305			8/30/2023	9/1/2023
-- Shazena		Khan		307			3/17/2023	3/20/2023

-- ------------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------------
-- 3. Write a query that returns all the rooms reserved 
-- for a specific guest, including the guest's name, the 
-- room(s) reserved, the starting date of the reservation, 
-- and how many people were included in the reservation. 
-- (Choose a guest's name from the existing data.)

SELECT 
    FirstName,
    LastName,
    rr.RoomNumber,
    StartDate,
    QtyOfAdults + QtyOfChildren AS TotalGuests
FROM
    ReservationRoom rr
        INNER JOIN
    Room ro ON rr.RoomNumber = ro.RoomNumber
        INNER JOIN
    Reservation r ON rr.ReservationId = r.ReservationId
        INNER JOIN
    Guest g ON r.GuestId = g.GuestId
WHERE
    FirstName = 'MACK'
        AND LastName = 'Simmer';
        -- Research if = vs LIKE is case sensitive. = for knowing the actual value
-- ------------------------------------------------------------------------------------
-- 4 rows returned
-- FirstName	LastName	RoomNumber	StartDate	TotalPeople
-- Mack			Simmer		308			2/2/2023		1
-- Mack			Simmer		208			9/16/2023		2
-- Mack			Simmer		206			11/22/2023		2
-- Mack			Simmer		301			11/22/2023		4

-- ------------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------------
-- 4. Write a query that returns a list of rooms, reservation
-- ID, and per-room cost for each reservation. The results 
-- should include all rooms, whether or not there is a 
-- reservation associated with the room.

SELECT 
    ro.RoomNumber, rr.ReservationId, rr.CostPerRoom
FROM
    Room ro
        LEFT JOIN
    ReservationRoom rr ON ro.RoomNumber = rr.RoomNumber
--         LEFT JOIN
--     Reservation r ON rr.ReservationId = r.ReservationId
ORDER BY ro.RoomNumber;
-- I chose to order this one by room number to make it more easy to see that all 
-- rooms were included in the final list. 
-- ------------------------------------------------------------------------------------
-- 26 rows returned
-- RoomNumber	ReservationId	CostPerRoom
-- 201				4				199.99
-- 202				7				349.98
-- 203				2				999.95
-- 203				20				399.98
-- 204				15				184.99
-- 205				14				699.96
-- 206				12				599.96
-- 206				22				449.97
-- 207				10				174.99
-- 208				12				599.96
-- 208				19				149.99
-- 301				9				799.96
-- 301				22				599.97
-- 302				6				924.95
-- 302				23				699.96
-- 303				17				199.99
-- 304				13				184.99
-- 305				3				349.98
-- 305				18				349.98
-- 306				NULL			NULL
-- 307				5				524.97
-- 308				1				299.98
-- 401				11				1199.97
-- 401				16				1259.97
-- 401				21				1199.97
-- 402				NULL			NULL

-- ------------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------------
-- 5. Write a query that returns all the rooms accommodating
-- at least three guests and that are reserved on any 
-- date in April 2023.

SELECT 
    rr.RoomNumber,
    g.FirstName,
    g.LastName,
    QtyOfAdults + QtyOfChildren AS TotalGuests
FROM
    Reservation r
        INNER JOIN
    Guest g ON r.GuestId = g.GuestId
        INNER JOIN
    ReservationRoom rr ON r.ReservationId = rr.ReservationId
WHERE
    QtyOfAdults + QtyOfChildren >= 3
        AND ((r.StartDate BETWEEN '2023-04-01' AND '2023-04-30') OR (r.EndDate BETWEEN '2023-04-01' AND '2023-04-30'));
        
-- ------------------------------------------------------------------------------------
-- 0 rows returned

-- ------------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------------
-- 6. Write a query that returns a list of all guest 
-- names and the number of reservations per guest, 
-- sorted starting with the guest with the most reservations 
-- and then by the guest's last name.

SELECT 
    FirstName, LastName, COUNT(r.ReservationId) AS TotalReservations
FROM
    Guest g
        LEFT JOIN
    Reservation r ON g.GuestId = r.GuestId
        LEFT JOIN
    ReservationRoom rr ON r.ReservationId = rr.ReservationId
GROUP BY FirstName , LastName
ORDER BY TotalReservations DESC , LastName;
-- ------------------------------------------------------------------------------------
-- 11 rows returned
-- FirstName	LastName	TotalReservations
-- Mack			Simmer			4
-- Bettyann		Seery			3
-- Duane		Cullison		2
-- Walter		Holaway			2
-- Shazena		Khan			2
-- Aurore		Lipton			2
-- Maritza		Tilton			2
-- Joleen		Tison			2
-- Wilfred		Vise			2
-- Karie		Yang			2
-- Zachery		Luechtefeld		1

-- ------------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------------
-- 7. Write a query that displays the name, address, and 
-- phone number of a guest based on their phone number. 
-- (Choose a phone number from the existing data.)

SELECT 
    FirstName, LastName, Address, PhoneNumber
FROM
    Guest g
WHERE
    PhoneNumber = '7184411816';
-- ------------------------------------------------------------------------------------
-- 1 row returned
-- FirstName	LastName	Address			PhoneNumber
-- Shazena		Khan		143 Hoover Ave	7184411816
