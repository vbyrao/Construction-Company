-- (1) --
-- Find the city with maximum number of contractors

select a.city, count(c.licenseId)
from Fall22_S004_8_Contractor c join Fall22_S004_8_Address a using(Zip)
group by a.city having count(c.licenseId) = (
select max(something) from (
select count(c1.licenseId) something FROM Fall22_S004_8_Contractor c1 join Fall22_S004_8_Address a1 using(Zip) group by a1.city
)
);

-- (2) --
-- Average Area of all 3 sectors in Dallas City

select x.sectorType, x.avg_area from (
select p.sector sectorType, avg(p.area) as avg_area 
from Fall22_S004_8_Project p join Fall22_S004_8_Address a using(Zip) 
where a.city = 'Dallas' 
group by p.sector
) x;

-- (3) --
-- Average price for each raw material type

select SUBSTR(Type_Manufacturer, 0, INSTR(Type_Manufacturer,'_',1,1)-1) "Type", avg(price) "Average" 
from Fall22_S004_8_RawMaterial rm join Fall22_S004_8_SupplierBuysRawMaterial sbr using(Type_Manufacturer) 
group by SUBSTR(Type_Manufacturer, 0, INSTR(Type_Manufacturer,'_',1,1)-1);

-- (4) --
-- Finds the Average selling price of the project in each sector

SELECT SECTOR, AVG(SELLING_PRICE) AS AVG_SELLING_PRICE 
FROM Fall22_S004_8_Project  
GROUP BY ROLLUP(SECTOR)
having AVG(SELLING_PRICE)>500000;

-- (5)
-- Find the avg no of labourcount per city per sector

SELECT a.city, p.sector, avg(lwp.LabourCount) 
from Fall22_S004_8_Project p join Fall22_S004_8_LabourWorksOnProject lwp using(ProjectName) join Fall22_S004_8_Address a USING(zip)
GROUP BY CUBE(a.city, p.sector) 
having avg(lwp.LabourCount)>50
order by a.city desc;

-- (6)
-- Top 3 zips with highest total selling price across all sectors.

select p1.zip, a.city, SUM(p1.selling_price) as selling_price, 
rank() OVER (ORDER BY SUM(p1.selling_price) desc) as Rank
from Fall22_S004_8_Project p1, Fall22_S004_8_Address a
where a.zip = p1.zip
GROUP BY a.city, p1.zip  
fetch first 3 rows only;
