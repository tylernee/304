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

insert into VehicleTypes values('Economy', 'Leather Seats', 10, 10, 10, 10, 10, 10, 10);
insert into VehicleTypes values('Compact', 'Heated Seats', 13, 13, 13, 13, 13, 13, 13);
insert into VehicleTypes values('Mid-Size', 'Leather Seats', 25, 25, 25, 25, 25, 25, 25);
insert into VehicleTypes values('Standard', 'Heated Seats', 28, 28, 28, 28, 28, 28, 28);
insert into VehicleTypes values('Full-Size', 'Backup camera', 31, 31, 31, 31, 31, 31, 31);
insert into VehicleTypes values('SUV', 'Automated parking', 34, 34, 34, 34, 34, 34, 34);
insert into VehicleTypes values('Truck', 'Autopilot', 11, 11, 11, 11, 11, 11, 11);

Create table Customers(
                          cellphone varchar(20),
                          name varchar(20),
                          address varchar(30),
                          dlicense varchar(20),
                          Primary Key (dlicense)
);

insert into Customers values('649-333-4343', 'Pierre Moucho', '123 Cambridge Street', '142432342314');
insert into Customers values('645-374-3533', 'Juan de Fuca', '432 Nanaimo Avenue', '342488889994');
insert into Customers values('145-374-3533', 'Juan de Who', '432 Nanaimo Avenue', '242488889994');
insert into Customers values('245-374-3533', 'Juan de Me', '432 Nanaimo Avenue', '142488889994');
insert into Customers values('345-374-3533', 'Juan de You', '432 Nanaimo Avenue', '542488889994');
insert into Customers values('445-374-3533', 'Juan de Two', '432 Nanaimo Avenue', '642488889994');
insert into Customers values('545-374-3533', 'Juan de Three', '432 Nanaimo Avenue', '742488889994');
insert into Customers values('745-374-3533', 'Juan de Third', '432 Nanaimo Avenue', '842488889994');
insert into Customers values('845-374-3533', 'Juan de Fourth', '432 Nanaimo Avenue', '942488889994');
insert into Customers values('945-374-3533', 'Juan de Juan', '432 Nanaimo Avenue', '102488889994');
insert into Customers values('235-374-3533', 'Juan de Khan', '432 Nanaimo Avenue', '112488889994');
insert into Customers values('495-374-3533', 'Juan de Bold', '432 Nanaimo Avenue', '122488889994');
insert into Customers values('135-544-3533', 'Juan de Scared', '432 Nanaimo Avenue', '132488889994');
insert into Customers values('234-968-3533', 'Juan de Puca', '432 Nanaimo Avenue', '144488889994');
insert into Customers values('264-374-3546', 'Juan de Tuca', '432 Nanaimo Avenue', '155488889994');
insert into Customers values('649-372-3983', 'Juan de Muca', '432 Nanaimo Avenue', '166488889994');



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
                             FOREIGN KEY (dlicense) REFERENCES Customers(dlicense)			 
);

insert into Reservations values(1,4,'Economy','142432342314','2001-07-27','09:00:30','2001-07-29', '09:00:30');
insert into Reservations values(2,5,'Compact','342488889994','2002-08-17','02:40:35','2002-08-18', '02:40:35');
insert into Reservations values(3,5,'Compact','142488889994','2019-08-17','02:40:35','2019-08-18', '02:40:35');
insert into Reservations values(4,5,'Compact','142488889994','2019-09-17','02:40:35','2019-09-20', '02:40:35');
insert into Reservations values(5,6,'Mid-Size','942488889994','2019-08-17','02:40:35','2019-08-20', '02:40:35');
insert into Reservations values(6,6,'Mid-Size','942488889994','2020-01-17','02:40:35','2020-01-20', '02:40:35');
insert into Reservations values(7,12,'Economy','144488889994','2019-08-17','02:40:35','2019-08-20', '02:40:35');
insert into Reservations values(8,12,'Economy','144488889994','2019-09-17','02:40:35','2019-09-20', '02:40:35');
insert into Reservations values(9,13,'Economy','166488889994','2019-08-17','02:40:35','2019-08-20', '02:40:35');
insert into Reservations values(10,13,'Economy','166488889994','2019-11-17','02:40:35','2019-11-20', '02:40:35');
insert into Reservations values(11,10,'Truck','166488889994','2019-11-20','02:40:35','2019-12-25', '02:40:35');
insert into Reservations values(12,11,'Economy','112488889994','2019-11-20','02:40:35','2019-12-25', '02:40:35');
insert into Reservations values(13,6,'Mid-Size','112488889994','2001-07-27','09:00:30','2001-07-29', '09:00:30');
insert into Reservations values(14,7,'Standard','112488889994','2000-07-27','09:00:30','2000-07-29', '09:00:30');


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
                        cardNo integer,
                        expDate varchar(90),
                        confNo integer,
                        Primary Key (rid),
                        FOREIGN KEY (dlicense) REFERENCES Customers(dlicense),
                        FOREIGN KEY (confNo) REFERENCES Reservations(confNo) ON DELETE SET NULL
);

insert into Rentals values(1,10,'166488889994','2019-11-20','02:40:35','2019-12-25', '02:40:35', 15232, 'Juan de Muca', 48374898478, '2019-12-31', 11);
insert into Rentals values(2,4,'142432342314','2001-07-27','09:00:30','2001-07-29', '09:00:30', 23432, 'Juan de Me', 58374898478, '2019-12-31', 1);
insert into Rentals values(3,5,'342488889994','2002-08-17','02:40:35','2002-08-18', '02:40:35', 15232, 'Juan de Fuca', 68374898478, '2019-12-31', 2);
insert into Rentals values(4,5,'142488889994','2019-08-17','02:40:35','2019-08-18', '02:40:35', 15232, 'Juan de Me', 58374898478, '2019-12-31', 3);
insert into Rentals values(5,5,'142488889994','2019-11-20','02:40:35','2019-12-25', '02:40:35', 15232, 'Juan de Me', 58374898478, '2019-12-31', 4);
insert into Rentals values(6,6,'942488889994','2019-08-17','02:40:35','2019-08-20', '02:40:35', 15232, 'Juan de Fourth', 78374898478, '2019-12-31', 5);
insert into Rentals values(7,12,'144488889994','2019-08-17','02:40:35','2019-08-20', '02:40:35', 15232, 'Juan de Puca', 88374898478, '2019-12-31', 7);
insert into Rentals values(8,12,'144488889994','2019-09-17','02:40:35','2019-09-20', '02:40:35', 15232, 'Juan de Puca', 88374898478, '2019-12-31', 8);
insert into Rentals values(9,13,'166488889994','2019-08-17','02:40:35','2019-08-20', '02:40:35', 15232, 'Juan de Muca', 48374898478, '2019-12-31', 9);
insert into Rentals values(10,13,'166488889994','2019-11-17','02:40:35','2019-11-20', '02:40:35', 15232, 'Juan de Muca', 48374898478, '2019-12-31', 10);
insert into Rentals values(11,11,'112488889994','2019-11-20','02:40:35','2019-12-25', '02:40:35', 15232, 'Juan de Khan', 98374898478, '2019-12-31', 12);
insert into Rentals values(12,6,'112488889994','2001-07-27','09:00:30','2001-07-29', '09:00:30', 15232, 'Juan de Khan', 11234898478, '2007-12-31', 13);
insert into Rentals values(13,7,'112488889994','2000-07-27','09:00:30','2000-07-29', '09:00:30', 15232, 'Juan de Khan', 11234898478, '2007-12-31', 14);


Create table Vehicles(
                         vid integer,
                         vlicense varchar(20),
                         make varchar(20),
                         model varchar(20),
                         year varchar(4),
                         color varchar(20),
                         odometer integer,
                         status varchar(20),
                         vtname varchar(20),
                         location varchar(30),
                         city varchar(30),
                         Primary Key (vid),
                         FOREIGN KEY (vtname) REFERENCES VehicleTypes(vtname)
);

insert into Vehicles values(4, 'AB4-34D', 'Toyota', 'Camry', 2001, 'Blue', 23432,'reserved','Economy', 'Downtown', 'Vancouver');
insert into Vehicles values(5, 'BB4-34D', 'Toyota', 'Camry', 2001, 'Blue', 15232,'reserved','Compact', 'Eastside', 'Vancouver');
insert into Vehicles values(6, 'CB4-34D', 'Toyota', 'Camry', 2001, 'Blue', 15232,'reserved','Mid-Size', 'Lougheed', 'Burnaby');
insert into Vehicles values(7, 'DB5-34D', 'Toyota', 'Camry', 2001, 'Pink', 15232,'for_rent','Standard', 'Lougheed', 'Burnaby');
insert into Vehicles values(8, 'EE4-34D', 'Toyota', 'Camry', 2002, 'Green', 15232,'for_rent','Full-Size', 'Lougheed', 'Burnaby');
insert into Vehicles values(9, 'FB4-34D', 'Toyota', 'Camry', 2002, 'Purple', 15232,'for_sale','SUV', 'Lougheed', 'Burnaby');
insert into Vehicles values(10, 'GB4-34D', 'Toyota', 'Camry', 2002, 'White', 15232,'rented','Truck', 'Downtown', 'Vancouver');
insert into Vehicles values(11, 'HB4-34D', 'Toyota', 'Camry', 2002, 'Black', 15232,'rented','Economy', 'Downtown', 'Vancouver');
insert into Vehicles values(12, 'IB4-34D', 'Toyota', 'Camry', 2002, 'Gray', 15232,'reserved','Economy', 'Downtown', 'Vancouver');
insert into Vehicles values(13, 'JB4-34D', 'Toyota', 'Camry', 2002, 'Yellow', 15232,'reserved','Economy', 'Eastside', 'Vancouver');
insert into Vehicles values(14, 'KB4-34D', 'Toyota', 'Camry', 2002, 'Red', 15232,'for_rent','SUV', 'Lougheed', 'Burnaby');


Create table Returns(
                        rid integer,
                        rdate varchar(90),
                        odometer integer,
                        fulltank integer,
                        rvalue integer,
                        Primary Key (rid),
                        FOREIGN KEY (rid) REFERENCES Rentals(rid)
);

insert into Returns values(2,'2001-07-29',20000, 1, 100);
insert into Returns values(3,'2002-08-18',30000, 1, 200);
insert into Returns values(4,'2019-08-18',40000, 0, 300);
insert into Returns values(6,'2019-08-20',50000, 0, 400);
insert into Returns values(7,'2019-08-20',60000, 0, 600);
insert into Returns values(8,'2019-09-20',70000, 0, 700);
insert into Returns values(9,'2019-08-20',22000, 0, 900);
insert into Returns values(10,'2019-11-20',24000, 1, 1000);
insert into Returns values(12,'2001-07-29',25000, 0, 1100);
insert into Returns values(13,'2000-07-29',26000, 1, 1200);




