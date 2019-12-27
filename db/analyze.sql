CREATE TABLE tmp1 (id INT PRIMARY KEY, URL TEXT, S3URL TEXT, Title TEXT, CompanyName TEXT, Workplace TEXT, HeadOffice TEXT, PostalCode TEXT, Address TEXT, Recruiter TEXT, Phone TEXT, Fax TEXT, Email TEXT, Industry TEXT, Remarks TEXT, PostPeriod TEXT, Rank TEXT, Website TEXT);
CREATE TABLE tmp2 (id INT, URL TEXT, S3URL TEXT, CorporateNum TEXT, BranchNum INT, Title TEXT, CompanyName TEXT, Workplace TEXT, HeadOffice TEXT, PostalCode TEXT, Address TEXT, Recruiter TEXT, Phone TEXT, Fax TEXT, Email TEXT, Industry TEXT, Remarks TEXT, PostPeriod TEXT, Rank TEXT, Website TEXT);
INSERT INTO tmp1 SELECT rowid, * FROM WebData;
INSERT INTO tmp2 SELECT p.id, p.URL, p.S3URL, pp.corporateNumber, 9999, p.Title, p.CompanyName, p.Workplace, p.HeadOffice, p.PostalCode, p.Address, p.Recruiter, p.Phone, p.Fax, p.Email, p.Industry, p.Remarks, p.PostPeriod, p.Rank, p.Website FROM CompanyData as pp, tmp1 as p WHERE pp.name = p.CompanyName and substr(pp.postCode,1,3) <> substr(p.PostalCode,1,3) GROUP BY p.URL ORDER BY p.id;
INSERT INTO tmp2 SELECT r.id, r.URL, r.S3URL, r.CorporateNum, (SELECT count(*) FROM tmp2 as r1 WHERE r1.CompanyName = r.CompanyName and r1.PostalCode < r.PostalCode) + 1, r.Title, r.CompanyName, r.Workplace, r.HeadOffice, r.PostalCode, r.Address, r.Recruiter, r.Phone, r.Fax, r.Email, r.Industry, r.Remarks, r.PostPeriod, r.Rank, r.Website FROM tmp2 as r ORDER BY r.id;
DELETE FROM tmp2 WHERE BranchNum = 9999;
DELETE FROM tmp2 WHERE PostalCode = "-";
INSERT INTO tmp2 SELECT p.id, p.URL, p.S3URL, pp.corporateNumber, 0, p.Title, p.CompanyName, p.Workplace, p.HeadOffice, p.PostalCode, p.Address, p.Recruiter, p.Phone, p.Fax, p.Email, p.Industry, p.Remarks, p.PostPeriod, p.Rank, p.Website FROM CompanyData as pp, tmp1 as p WHERE pp.name = p.CompanyName and substr(pp.postCode,1,3) = substr(p.PostalCode,1,3) GROUP BY p.URL ORDER BY p.id;
INSERT INTO tmp2 SELECT p.id, p.URL, p.S3URL, "-", 0, p.Title, p.CompanyName, p.Workplace, p.HeadOffice, p.PostalCode, p.Address, p.Recruiter, p.Phone, p.Fax, p.Email, p.Industry, p.Remarks, p.PostPeriod, p.Rank, p.Website FROM tmp1 as p WHERE NOT EXISTS(SELECT * FROM tmp2 as r WHERE p.id = r.id) GROUP BY p.URL ORDER BY p.id;
CREATE TABLE BranchData (URL TEXT, S3URL TEXT, CorporateNum TEXT, BranchNum TEXT, Title TEXT, CompanyName TEXT, Workplace TEXT, HeadOffice TEXT, PostalCode TEXT, Address TEXT, Recruiter TEXT, Phone TEXT, Fax TEXT, Email TEXT, Industry TEXT, Remarks TEXT, PostPeriod TEXT, Rank TEXT, Website TEXT);
INSERT INTO BranchData SELECT URL, S3URL, CorporateNum, BranchNum, Title, CompanyName, Workplace, HeadOffice, PostalCode, Address, Recruiter, Phone, Fax, Email, Industry, Remarks, PostPeriod, Rank, Website FROM tmp2 GROUP BY URL ORDER BY id;
DROP TABLE tmp1;
DROP TABLE tmp2;
VACUUM;