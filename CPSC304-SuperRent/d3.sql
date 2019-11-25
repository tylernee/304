drop table Reservations cascade constraints;
drop table Rentals cascade constraints;
drop table Vehicles cascade constraints;
drop table VehicleTypes cascade constraints;
drop table Customers cascade constraints;
drop table Returns cascade constraints;

Create table VehicleTypes(
                             vtname varchar(20),
                             features varchar(20),
                             wrate integer,
                             drate integer,
                             hrate integer,
                             wirate integer,
                             dirate integer,
                             hirate integer,
                             krate integer,
                             Primary Key (vtname)
);

insert into VehicleTypes values('Economy', 'Leather Seats', 22, 34, 50, 34, 26, 234, 2342);
insert into VehicleTypes values('Compact', 'Heated Seats', 13, 24, 30, 44, 16, 264, 1342);
insert into VehicleTypes values('Mid-size', 'Leather Seats', 25, 37, 53, 37, 29, 237, 2345);
insert into VehicleTypes values('Standard', 'Heated Seats', 28, 40, 56, 40, 32, 240, 2348);
insert into VehicleTypes values('Full-size', 'Backup camera', 31, 43, 59, 43, 35, 243, 2351);
insert into VehicleTypes values('SUV', 'Automated parking', 34, 47, 62, 47, 38, 247, 2360);
insert into VehicleTypes values('Truck', 'Autopilot', 44, 57, 72, 57, 48, 257, 2370);

Create table Customers(
                          cellphone varchar(20),
                          name varchar(20),
                          address varchar(30),
                          dlicense varchar(20),
                          Primary Key (dlicense)
);

insert into Customers values('649-333-4343', 'Pierre Moucho', '123 Cambridge Street', '1424-3234-2314');
insert into Customers values('645-374-3533', 'Juan de Fuca', '432 Nanaimo Avenue', '3424-3234-2314');
insert into Customers values('139-022-0220', 'David Du', '1341 No 3 Road', '1444-1644-1840');
insert into Customers values('382-097-2244', 'John Smith', '48 W 41 Avenue', '3758-1847-3485');
insert into Customers values('384-209-5830', 'Richard Donald', '155 SW Marine Drive', '4437-9918-4827');
insert into Customers values('604-284-5828', 'Donnie Golf', '482 Cordova Street', '2394-1726-5793');

Create table Vehicles(
                         vid integer,
                         vlicense varchar(20),
                         make varchar(20),
                         model varchar(20),
                         year integer,
                         color varchar(20),
                         odometer integer,
                         status varchar(20),
                         vtname varchar(20),
                         location varchar(30),
                         city varchar(30),
                         Primary Key (vid),
                         FOREIGN KEY (vtname) REFERENCES VehicleTypes(vtname)
);

insert into Vehicles values(1, 'D48-316', 'Honda', 'Civic', 2015, 'White', 2748, 'for_rent', 'Compact', 'Lougheed', 'Burnaby');
insert into Vehicles values(2, 'D48-DJ8', 'Ford', 'Focus', 2017, 'Red', 1031, 'for_rent', 'Compact', 'Downtown', 'Vancouver');
insert into Vehicles values(3, '394-RID', 'Kia', 'Forte', 2010, 'Blue', 44569, 'for_rent', 'Compact', 'Downtown', 'Vancouver');
insert into Vehicles values(4, 'AB4-34D', 'Toyota', 'Camry', 2010, 'Blue', 23432,'for_sale','Economy', 'Downtown', 'Vancouver');
insert into Vehicles values(5, 'AB4-34D', 'Toyota', 'Camry', 2010, 'Blue', 15232,'for_rent','Compact', 'Eastside', 'Vancouver');
insert into Vehicles values(6, 'J84-582', 'Hyundai', 'Elantra', 2016, 'Grey', 10239, 'for_rent', 'Compact', 'Eastside', 'Vancouver');
insert into Vehicles values(7, '88J-RUT', 'Kia', 'Soul', 2016, 'Yellow', 3920, 'for_rent', 'Compact', 'Lougheed', 'Burnaby');
insert into Vehicles values(8, 'FU9-FJR', 'Chevrolet', 'Cruze', 2015, 'Blue', 9908, 'for_rent', 'Compact', 'Downtown', 'Vancouver');
insert into Vehicles values(9, 'FJ0-WOI', 'Mazda', '3', 2017, 'Red', 2093, 'for_rent', 'Compact', 'Eastside', 'Vancouver');
insert into Vehicles values(10, '482-GJE', 'Audi', 'A1', 2016, 'Red', 4029, 'for_rent', 'Economy', 'Eastside', 'Vancouver');

--branches: Downtown Vancouver, Eastside Vancouver, Metrotown Burnaby, Center Richmond, Airport Richmond




Create table Reservations(
                             confNo integer,
                             vid integer,
                             vtname varchar(20),
                             dlicense varchar(20),
                             fromDate varchar(90),
                             fromTime varchar(90),
                             toDate varchar(90),
                             toTime varchar(90),
                             Primary Key (confNo),
                             FOREIGN KEY (vtname) REFERENCES VehicleTypes(vtname),
                             FOREIGN KEY (dlicense) REFERENCES Customers(dlicense),
                             FOREIGN KEY (vid) REFERENCES  Vehicles(vid)
);



Create table Rentals(
                        rid integer,
                        vid integer,
                        dlicense varchar(20),
                        fromDate varchar(90),
                        fromTime varchar(90),
                        toDate varchar(90),
                        toTime varchar(90),
                        odometer integer,
                        cardName varchar(30),
                        cardNo varchar(20),
                        ExpDate varchar(90),
                        confNo integer,
                        Primary Key (rid),
                        FOREIGN KEY (vid) REFERENCES Vehicles(vid),
                        FOREIGN KEY (dlicense) REFERENCES Customers(dlicense),
                        FOREIGN KEY (confNo) REFERENCES Reservations(confNo)
                            ON DELETE SET NULL
);

Create table Returns(
                        Rrid integer,
                        rdate varchar(90),
                        rtime varchar(90),
                        odometer integer,
                        fulltank integer,
                        cost integer,
                        Primary Key (Rrid),
                        FOREIGN KEY (Rrid) REFERENCES Rentals(rid)
);

