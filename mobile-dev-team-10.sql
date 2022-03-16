CREATE DATABASE hotelapp;
use hotelapp;

CREATE TABLE Trip (
	TripID int not null AUTO_INCREMENT PRIMARY KEY,
    TripOrigin varchar(20),
    TripDest varchar(20),
    GroupSize int,
    IsFirstClass int,
    TicketPrice int,
    NumOfNights int,
    NightCost int,
    TotalCost int
);

INSERT INTO Trip (TripOrigin, TripDest, GroupSize, IsFirstClass, TicketPrice, NumOfNights, NightCost, TotalCost)
VALUES ('Toronto', 'Tokyo', 2,  0, 100, 3, 150, 650);


