USE HotelShazena;

INSERT INTO Guest (GuestId, FirstName, LastName, Address, City, State, ZipCode, PhoneNumber) VALUES
	(1, 'Shazena', 'Khan', '143 Hoover Ave', 'Briarwood', 'NY', '11435', '7184411816'),
    (2, 'Mack', 'Simmer', '379 Old Shore Street', 'Council Bluffs', 'IA', '51501', '2915530508'),
    (3, 'Bettyann', 'Seery', '750 Wintergreen Dr.', 'Wasilla', 'AK', '99654', '4782779632'),
    (4, 'Duane', 'Cullison', '9662 Foxrun Lane', 'Harlingen', 'TX', '78552', '3084940198'),
    (5, 'Karie', 'Yang', '9378 W. Augusta Ave', 'West Deptford', 'NJ', '08096', '2147300298'),
    (6, 'Aurore', 'Lipton', '762 Wild Rose Street', 'Saginaw', 'MI', '48601', '3775070974'),
    (7, 'Zachery', 'Luechtefeld', '7 Poplar Dr.', 'Arvada', 'CO', '80003', '8144852615'),
    (8, 'Jeremiah', 'Pendergrass', '70 Oakwood St.', 'Zion', 'IL', '60099', '2794910960'),
    (9, 'Walter', 'Holaway', '7556 Arrowhead St.', 'Cumberland', 'RI', '02864', '4463966785'),
    (10, 'Wilfred', 'Vise', '77 West Surrey Street', 'Oswego', 'NY', '13126', '8347271001'),
    (11, 'Maritza', 'Tilton', '939 Linda Rd.', 'Burke', 'VA', '22015', '4463516860'),
    (12, 'Joleen', 'Tison', '87 Queen St.', 'Drexel Hill', 'PA', '19026', '2318932755');

INSERT INTO Amenity (AmenityId, AmenityName, AmenityCost) VALUES
	(1, 'Microwave', 0),
    (2, 'Jacuzzi', 25),
    (3, 'Refrigerator', 0),
    (4, 'Oven', 0);
    
INSERT INTO RoomType (RoomTypeId, RoomTypeName, MinOccupancy, MaxOccupancy, ChargePerExtraPerson, BasePrice) VALUES
	(1, 'Single', 2, 2, NULL, 149.99),
    (2, 'Double', 2, 4, 10, 174.99),
    (3, 'Suite', 3, 8, 20, 399.99);
    
INSERT INTO Room (RoomNumber, ADAAccessible, RoomTypeId) VALUES
	(201, 0, 2),
    (202, 1, 2),
    (203, 0, 2),
    (204, 1, 2),
    (205, 0, 1),
    (206, 1, 1),
    (207, 0, 1),
    (208, 1, 1),
    (301, 0, 2),
    (302, 1, 2),
    (303, 0, 2),
    (304, 1, 2),
    (305, 0, 1),
    (306, 1, 1),
    (307, 0, 1),
    (308, 1, 1),
    (401, 1, 3),
    (402, 1, 3);
    
INSERT INTO RoomAmenity (RoomNumber, AmenityId) VALUES
	(201, 1),
    (201, 2),
    (202, 3),
    (203, 1),
    (203, 2),
    (204, 3),
    (205, 1),
    (205, 2),
    (205, 3),
    (206, 1),
    (206, 3),
    (207, 1),
    (207, 2),
    (207, 3),
    (208, 1),
    (208, 3),
    (301, 1),
    (301, 2),
    (302, 3),
    (303, 1),
    (303, 2),
    (304, 3),
    (305, 1),
    (305, 2),
    (305, 3),
    (306, 1),
    (306, 3),
    (307, 1),
    (307, 2),
    (307, 3),
    (308, 1),
    (308, 3),
    (401, 1),
    (401, 3),
    (401, 4),
    (402, 1),
    (402, 3),
    (402, 4);
    
INSERT INTO Reservation (ReservationId, GuestId, StartDate, EndDate, TotalCost) VALUES
	(1, 2, '2023-02-02', '2023-02-04', 299.98),
    (2, 3, '2023-02-05', '2023-02-10', 999.95),
    (3, 4, '2023-02-22', '2023-02-24', 349.98),
    (4, 5, '2023-03-06', '2023-03-07', 199.99),
    (5, 1, '2023-03-17', '2023-03-20', 524.97),
    (6, 6, '2023-03-18', '2023-03-23', 924.95),
    (7, 7, '2023-03-29', '2023-03-31', 349.98),
    (8, 8, '2023-03-31', '2023-04-05', 874.95),
    (9, 9, '2023-04-09', '2023-04-13', 799.96),
    (10, 10, '2023-04-23', '2023-04-24', 174.99),
    (11, 11, '2023-05-30', '2023-06-02', 1199.97),
    (12, 12, '2023-06-10', '2023-06-14', 1199.92),
    (13, 6, '2023-06-17', '2023-06-18', 184.99),
    (14, 1, '2023-06-28', '2023-07-02', 699.96),
    (15, 9, '2023-07-13', '2023-07-14', 184.99),
    (16, 10, '2023-07-18', '2023-07-21', 1259.97),
    (17, 3, '2023-07-28', '2023-07-29', 199.99),
    (18, 3, '2023-08-30', '2023-09-01', 349.98),
    (19, 2, '2023-09-16', '2023-09-17', 149.99),
    (20, 5, '2023-09-13', '2023-09-15', 399.98),
    (21, 4, '2023-11-22', '2023-11-25', 1199.97),
    (22, 2, '2023-11-22', '2023-11-25', 1049.94),
    (23, 11, '2023-12-24', '2023-12-28', 699.96);

INSERT INTO ReservationRoom (ReservationId, RoomNumber, QtyOfAdults, QtyOfChildren, CostPerRoom) VALUES
	(1, 308, 1, 0, 299.98),
    (2, 203, 2, 1, 999.95),
    (3, 305, 2, 0, 349.98),
    (4, 201, 2, 2, 199.99),
    (5, 307, 1, 1, 524.97),
    (6, 302, 3, 0, 924.95),
    (7, 202, 2, 2, 349.98),
    (8, 304, 2, 0, 874.95),
    (9, 301, 1, 0, 799.96),
    (10, 207, 1, 1, 174.99),
    (11, 401, 2, 4, 1199.97),
    (12, 206, 2, 0, 599.96),
    (12, 208, 1, 0, 599.96),
    (13, 304, 3, 0, 184.99),
    (14, 205, 2, 0, 699.96),
    (15, 204, 3, 1, 184.99),
    (16, 401, 4, 2, 1259.97),
    (17, 303, 2, 1, 199.99),
    (18, 305, 1, 0, 349.98),
    (19, 208, 2, 0, 149.99),
    (20, 203, 2, 2, 399.98),
    (21, 401, 2, 2, 1199.97),
    (22, 206, 2, 0, 449.97),
    (22, 301, 2, 2, 599.97),
    (23, 302, 2, 0, 699.96);

-- -----------------------------------------------------------------------------------    
-- -----------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------

-- Delete Jermiah Pendergrass and his reservations.

SELECT GuestId, FirstName, LastName FROM Guest WHERE FirstName LIKE 'Jeremiah' AND LastName LIKE 'Pendergrass';
-- Now we know he has GuestId 8.

SELECT * FROM Reservation WHERE GuestId = 8;
-- Now we know his reservation number is 8.

SELECT * FROM ReservationRoom WHERE ReservationId = 8;
-- This record needs to be deleted first

DELETE FROM ReservationRoom WHERE ReservationId = 8;
DELETE FROM Reservation WHERE ReservationId = 8;
DELETE FROM Guest WHERE GuestId = 8;


-- Note to self: For when you have to change one item, do a selection query that pulls in the guestid for the name and nest it in another query, maybe?
-- Note to self: When changing over to a calculated field, you need to fill in the reservation table without the total, then fill in the ReservationRoom Table, then go back and update all the fields to match the right totals. 