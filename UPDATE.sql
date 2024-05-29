-- UPDATE

-- Fall22_S004_8_RawMaterial

select * from Fall22_S004_8_RawMaterial where Type_Manufacturer = 'Cement_Heidelberg';

UPDATE Fall22_S004_8_RawMaterial SET Price = 116 where Type_Manufacturer = 'Cement_Heidelberg';

select * from Fall22_S004_8_RawMaterial where Type_Manufacturer = 'Cement_Heidelberg';

-- Fall22_S004_8_Supplier

select * from Fall22_S004_8_Supplier Where GST = '10AAACB1534F1ZL';

UPDATE Fall22_S004_8_Supplier SET Credit_Duration = 45 Where GST = '10AAACB1534F1ZL';

select * from Fall22_S004_8_Supplier Where GST = '10AAACB1534F1ZL';

-- Fall22_S004_8_SupplierEmail 

-- Can't Update anything here cause one is a primary key and the other is a FK

-- Fall22_S004_8_Address

-- Cannot Update cause City is dependent on zip

-- Fall22_S004_8_Contractor

-- Cannot Update anything as any new changes would not affect the final output in any way

-- Fall22_S004_8_Project

select * from Fall22_S004_8_Project where PROJECTNAME = 'Roxbury E+';

UPDATE Fall22_S004_8_Project SET SELLING_PRICE = 8075906 where PROJECTNAME = 'Roxbury E+';

select * from Fall22_S004_8_Project where PROJECTNAME = 'Roxbury E+';

select * from Fall22_S004_8_Project where PROJECTNAME = 'Pinnacle Furnished Suites'

UPDATE Fall22_S004_8_Project SET SELLING_PRICE = 8090627 where PROJECTNAME = 'Pinnacle Furnished Suites';

select * from Fall22_S004_8_Project where PROJECTNAME = 'Pinnacle Furnished Suites'

-- Fall22_S004_8_Labor

select * from Fall22_S004_8_Labor where LABOUR_CITY = 'Lake Charles, LA';

UPDATE Fall22_S004_8_Labor SET RATE = 25 where LABOUR_CITY = 'Lake Charles, LA';

select * from Fall22_S004_8_Labor where LABOUR_CITY = 'Lake Charles, LA';

select * from Fall22_S004_8_Labor where LABOUR_CITY = 'Vineland, NJ';

UPDATE Fall22_S004_8_Labor SET RATE = 26 where LABOUR_CITY = 'Vineland, NJ';

select * from Fall22_S004_8_Labor where LABOUR_CITY = 'Vineland, NJ';

-- Fall22_S004_8_SupplierBuysRawMaterial

-- Cannot Change anything, if it has to be changed then new tuple is required containing all three parameters

-- Fall22_S004_8_ContractorContactsSupplier

select * from Fall22_S004_8_ContractorContactsSupplier where LICENSEID = 'XUN70YIU7IB';

UPDATE Fall22_S004_8_ContractorContactsSupplier SET RATING = 3 where LICENSEID = 'XUN70YIU7IB';

select * from Fall22_S004_8_ContractorContactsSupplier where LICENSEID = 'XUN70YIU7IB';

select * from Fall22_S004_8_ContractorContactsSupplier where LICENSEID = 'URP87ECN7RV';

UPDATE Fall22_S004_8_ContractorContactsSupplier SET RATING = 3 where LICENSEID = 'URP87ECN7RV';

select * from Fall22_S004_8_ContractorContactsSupplier where LICENSEID = 'URP87ECN7RV';

--Fall22_S004_8_LabourWorksOnProject

select * from Fall22_S004_8_LabourWorksOnProject where LABOUR_CITY = 'Provo-Orem, UT';

UPDATE Fall22_S004_8_LabourWorksOnProject SET LABOURCOUNT = 90 where LABOUR_CITY = 'Provo-Orem, UT';

select * from Fall22_S004_8_LabourWorksOnProject where LABOUR_CITY = 'Provo-Orem, UT';

select * from Fall22_S004_8_LabourWorksOnProject where LABOUR_CITY = 'North Texas, TX';

UPDATE Fall22_S004_8_LabourWorksOnProject SET LABOURCOUNT = 100 where LABOUR_CITY = 'North Texas, TX';

select * from Fall22_S004_8_LabourWorksOnProject where LABOUR_CITY = 'North Texas, TX';


--DELETE 

select * from Fall22_S004_8_Project where Sector = 'Government';
select * from Fall22_S004_8_Project where Sector = 'Commercial';

DELETE FROM Fall22_S004_8_Project where Area < 30000 AND Sector = 'Government';

DELETE FROM Fall22_S004_8_Project where (Area BETWEEN 30000 AND 40000) AND Sector = 'Commercial';

select * from Fall22_S004_8_Project where Sector = 'Government';
select * from Fall22_S004_8_Project where Sector = 'Commercial';


select * from Fall22_S004_8_Labor;

DELETE FROM Fall22_S004_8_Labor where Rate BETWEEN 16 AND 17;
DELETE FROM Fall22_S004_8_Labor where Labour_City LIKE '%LA';

select * from Fall22_S004_8_Labor;


select * FROM Fall22_S004_8_ContractorContactsSupplier;

DELETE FROM Fall22_S004_8_ContractorContactsSupplier WHERE Rating = 1;
DELETE FROM Fall22_S004_8_ContractorContactsSupplier WHERE LicenseID = 'SQK56CKL4CD';

select * FROM Fall22_S004_8_ContractorContactsSupplier;








