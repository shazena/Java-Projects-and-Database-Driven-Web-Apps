DROP DATABASE IF EXISTS HotelShazena;

CREATE DATABASE HotelShazena;

USE HotelShazena;

CREATE TABLE Guest (
    GuestId INT PRIMARY KEY AUTO_INCREMENT,
    FirstName VARCHAR(20) NOT NULL,
    LastName VARCHAR(20) NOT NULL,
    Address VARCHAR(50) NOT NULL,
    City VARCHAR(50) NOT NULL,
    State CHAR(2) NOT NULL,
    ZipCode CHAR(5) NOT NULL,
    PhoneNumber CHAR(10) NOT NULL
);

CREATE TABLE Amenity (
    AmenityId INT PRIMARY KEY AUTO_INCREMENT,
    AmenityName VARCHAR(20) NOT NULL,
    AmenityCost DECIMAL(5 , 2 ) NOT NULL
);

CREATE TABLE RoomType (
    RoomTypeId INT PRIMARY KEY AUTO_INCREMENT,
    RoomTypeName VARCHAR(15) NOT NULL,
    MinOccupancy INT NOT NULL,
    MaxOccupancy INT NOT NULL,
    ChargePerExtraPerson DECIMAL(5 , 2 ) NULL, -- Because single rooms should not have extra people
    BasePrice DECIMAL(5 , 2 ) NOT NULL
);

CREATE TABLE Room (
    RoomNumber INT PRIMARY KEY,
    ADAAccessible BIT NOT NULL,
    RoomTypeId INT NOT NULL,
    FOREIGN KEY fk_Room_RoomType (RoomTypeId)
        REFERENCES RoomType (RoomTypeId)
);

CREATE TABLE RoomAmenity (
    RoomNumber INT NOT NULL,
    AmenityId INT NOT NULL,
    PRIMARY KEY (RoomNumber , AmenityId),
    FOREIGN KEY fk_RoomAmenity_Room (RoomNumber)
        REFERENCES Room (RoomNumber),
    FOREIGN KEY fk_RoomAmenity_Amenity (AmenityId)
        REFERENCES Amenity (AmenityId)
);

CREATE TABLE Reservation (
    ReservationId INT PRIMARY KEY AUTO_INCREMENT,
    GuestId INT NOT NULL,
    FOREIGN KEY fk_Reservation_Guest (GuestId)
        REFERENCES Guest (GuestId),
    StartDate DATE NOT NULL,
    EndDate DATE NOT NULL,
    TotalCost DECIMAL(11 , 2 ) NOT NULL
);

CREATE TABLE ReservationRoom (
    ReservationId INT NOT NULL,
    RoomNumber INT NOT NULL,
    PRIMARY KEY (ReservationId , RoomNumber),
    FOREIGN KEY fk_ReservationRoom_Reservation (ReservationId)
        REFERENCES Reservation (ReservationId),
    FOREIGN KEY fk_ReservationRoom_Room (RoomNumber)
        REFERENCES Room (RoomNumber),
    QtyOfAdults INT NOT NULL,
    QtyOfChildren INT NOT NULL,
    CostPerRoom DECIMAL(11 , 2 ) NOT NULL
);

    
    

  

