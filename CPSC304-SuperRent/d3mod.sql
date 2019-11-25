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
insert into Customers values('645-374-3533', 'Juan de Fuca', '432 Nanaimo Avenue', '3424-8888-9994');
insert into Customers values('111-111-1111', 'Brock Lesnar', '123 10th Avenue', '2222-2222-2222');

insert into Customers values('604-604-6004', 'Jorge Masvidal', '999 South side', '999-333-2221');
insert into Customers values('998-254-3391', 'Nate Diaz', '333 Long Beach', '894-536-1124');
insert into Customers values('846-514-8741', 'Amanda Nunes', '999 Jersey St', '591-5099-8888');


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

insert into Reservations values(1, , 'Economy','1424-3234-2314',TO_DATE('2001-07-27','YYYY-MM-dd'),TO_TIMESTAMP('2001-07-27:09:00:30','YYYY-MM-DD:HH24:MI:SS'),TO_DATE('2001/07/29','YYYY-MM-dd'), TO_TIMESTAMP('2001-07-29:09:00:30','YYYY-MM-DD:HH24:MI:SS'));
insert into Reservations values(2,,'Compact','3424-8888-9994',TO_DATE('2002-08-17','YYYY-MM-DD'),TO_TIMESTAMP('2002-08-17:02:40:35','YYYY-MM-DD:HH24:MI:SS'),TO_DATE('2002/08/18','YYYY-MM-DD'), TO_TIMESTAMP('2002-08-18:02:40:35','YYYY-MM-DD:HH24:MI:SS'));

insert into Reservations values(3, ,'Truck', '999-333-2221', TO_DATE('2019-11-23', 'YYYY-MM-dd'), TO_TIMESTAMP('2019-11-23:10:00:00', 'YYYY-MM-dd:HH24:MI:SS'), TO_DATE('2019-12-01', 'YYYY-MM-dd'), TO_TIMESTAMP('2019-11-23:10:00:00', 'YYYY-MM-DD:HH24:MI:SS'));
insert into Reservations values(4, ,'Standard', '894-536-1124', TO_DATE('2019-11-23', 'YYYY-MM-dd'), TO_TIMESTAMP('2019-11-23:10:00:00', 'YYYY-MM-dd:HH24:MI:SS'), TO_DATE('2019-12-03', 'YYYY-MM-dd'), TO_TIMESTAMP('2019-11-23:11:00:00', 'YYYY-MM-DD:HH24:MI:SS'));
insert into Reservations values(5, ,'Mid-size', '591-5099-8888', TO_DATE('2019-11-22', 'YYYY-MM-dd'), TO_TIMESTAMP('2019-11-23:10:00:00', 'YYYY-MM-dd:HH24:MI:SS'), TO_DATE('2019-12-03', 'YYYY-MM-dd'), TO_TIMESTAMP('2019-11-23:11:00:00', 'YYYY-MM-DD:HH24:MI:SS'));




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
                        ExpDate varchar(90),
                        confNo integer,
                        Primary Key (rid),
                        FOREIGN KEY (dlicense) REFERENCES Customers(dlicense),
                        FOREIGN KEY (confNo) REFERENCES Reservations(confNo) ON DELETE SET NULL
);

insert into Rentals values(10, 7, '999-333-2221', TO_DATE('2019-11-23', 'YYYY-MM-dd'), TO_TIMESTAMP('2019-11-23:10:00:00', 'YYYY-MM-dd:HH24:MI:SS'), TO_DATE('2019-12-01', 'YYYY-MM-dd'), TO_TIMESTAMP('2019-11-23:10:00:00', 'YYYY-MM-DD:HH24:MI:SS'), 35000, 'masvidal', '4821999111234', '12/22', 3);

insert into Rentals values(11, 8, '894-536-1124',TO_DATE('2019-11-23', 'YYYY-MM-dd'), TO_TIMESTAMP('2019-11-23:10:00:00', 'YYYY-MM-dd:HH24:MI:SS'), TO_DATE('2019-12-03', 'YYYY-MM-dd'), TO_TIMESTAMP('2019-11-23:11:00:00', 'YYYY-MM-DD:HH24:MI:SS'), 35000, 'ndiaz', '123456781234', '01/24', 4);

insert into Rentals values(12, 9, '591-5099-8888', TO_DATE('2019-11-22', 'YYYY-MM-dd'), TO_TIMESTAMP('2019-11-23:10:00:00', 'YYYY-MM-dd:HH24:MI:SS'), TO_DATE('2019-12-03', 'YYYY-MM-dd'), TO_TIMESTAMP('2019-11-23:11:00:00', 'YYYY-MM-DD:HH24:MI:SS'), 50000, 'nunes', '987654321', '02/23', 5);


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

insert into Vehicles values(4, 'AB4-34D', 'Toyota', 'Camry', 2010, 'Blue', 23432,'for_sale','Economy', 'Downtown', 'Vancouver');
insert into Vehicles values(5, 'BB4-34D', 'Toyota', 'Camry', 2010, 'Blue', 15232,'reserved','Compact', 'Eastside', 'Vancouver');
insert into Vehicles values(6, 'CB4-34D', 'Toyota', 'Camry', 2010, 'Blue', 15232,'reserved','Economy', 'Lougheed', 'Burnaby');

insert into Vehicles values(7, 'CB4-99A', 'Ford', 'Focus', 2015, 'Red', 35000, 'reserved', 'Truck',
'Lougheed', 'Burnaby');
insert into Vehicles values(8, 'CB5-10B', 'Acura', 'TLX', 2018, 'Black', 35000, 'reserved', 'Standard',
'Lougheed', 'Burnaby');
insert into Vehicles values(9, 'CD6-45A', 'Mercedez', 'C-class', 2018, 'White', 50000, 'reserved', 'Mid-size', 'Eastside', 'Vancouver');

insert into Vehicles values(10, 'AD6-22K', 'Honda', 'Accord', 2018, 'Gray', 46000, 'for_rent', 'Standard', 'Downtown', 'Vancouver');
insert into Vehicles values(11, 'AD6-24K', 'Honda', 'Accord', 2018, 'White', 46000, 'for_rent', 'Standard', 'Downtown', 'Vancouver');
insert into Vehicles values(12, 'AC6-27A', 'Ford', 'F-150', 2017, 'Black', 46000, 'for_rent', 'Truck', 'Downtown', 'Vancouver');
insert into Vehicles values(13, 'AC6-2FK', 'Ford', 'F-150', 2017, 'Blue', 46000, 'for_rent', 'Truck', 'Downtown', 'Vancouver');
insert into Vehicles values(14, 'AD6-22K', 'Mitsubishi', 'FV', 2015, 'White', 46000, 'for_rent', 'SUV', 'Downtown', 'Vancouver');

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



