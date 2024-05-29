create table Fall22_S004_8_RawMaterial(Type_Manufacturer varchar2(50) primary key check(Type_Manufacturer like 'Cement#_%_%' ESCAPE '#' or Type_Manufacturer like 'Rod#_%_%' ESCAPE '#' or Type_Manufacturer like 'Brick#_%_%' ESCAPE '#'), Price integer not null check(Price>0));

create table Fall22_S004_8_Supplier(GST char(16) primary key, Name varchar2(50) unique not null, Return_Policy char(1) check(Return_Policy='Y' or Return_Policy='N'), Delivery_Time integer not null check(Delivery_Time>=0), Credit_Duration integer not null check(Credit_Duration>=0), Type_Manufacturer varchar2(50) references Fall22_S004_8_RawMaterial(Type_Manufacturer) on delete cascade);

create table Fall22_S004_8_SupplierEmail(Email varchar2(30) primary key check(Email like '_%@_%.com'), GST char(16) references Fall22_S004_8_Supplier(GST) on delete cascade);

create table Fall22_S004_8_Address(Zip integer primary key check(Zip>0), City varchar2(20) not null);

create table Fall22_S004_8_Contractor(LicenseID varchar2(20) primary key, Name varchar2(50) unique not null, Street varchar2(50), Zip integer references Fall22_S004_8_Address(Zip) on delete cascade);

create table Fall22_S004_8_Project(ProjectName varchar2(50) primary key, Sector varchar2(15) not null check(Sector='Government' or Sector='Residential' or Sector='Commercial'), Area integer not null check(Area>0), Zip integer references Fall22_S004_8_Address(Zip) on delete cascade, Selling_Price integer not null check(Selling_Price>0), Start_Date date not null, End_Date date not null, constraint egs check(End_Date>Start_Date));

create table Fall22_S004_8_Labor(Labour_City varchar2(20) primary key, Rate integer not null check(Rate>0));

create table Fall22_S004_8_SupplierBuysRawMaterial(GST char(16) references Fall22_S004_8_Supplier(GST) on delete cascade, Type_Manufacturer varchar2(50) references Fall22_S004_8_RawMaterial(Type_Manufacturer) on delete cascade, Buy_Date date not null);

create table Fall22_S004_8_ContractorContactsSupplier(LicenseID varchar2(20) references Fall22_S004_8_Contractor(LicenseID) on delete cascade, GST char(16) references Fall22_S004_8_Supplier(GST) on delete cascade, Rating integer not null check(Rating>=0 and Rating<=5));

create table Fall22_S004_8_LabourWorksOnProject(Labour_City varchar(20) references Fall22_S004_8_Labor(Labour_City) on delete cascade, ProjectName varchar2(50) references Fall22_S004_8_Project(ProjectName) on delete cascade, LabourCount integer not null check(LabourCount>0));

