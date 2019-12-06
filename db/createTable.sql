DROP TABLE IF EXISTS WebTable;
DROP TABLE IF EXISTS TmpTable1;
DROP TABLE IF EXISTS TmpTable2;
DROP TABLE IF EXISTS ResultTable;
CREATE TABLE WebTable (URL TEXT, Title TEXT, CompanyName TEXT, Workplace TEXT, HeadOffice TEXT, PostalCode TEXT, Address TEXT, Recruiter TEXT, Phone TEXT, Fax TEXT, Email TEXT, Industry TEXT, Remarks TEXT, PostPeriod TEXT, Rank TEXT, Website TEXT);
CREATE TABLE TmpTable1 (id INT PRIMARY KEY, URL TEXT, Title TEXT, CompanyName TEXT, Workplace TEXT, HeadOffice TEXT, PostalCode TEXT, Address TEXT, Recruiter TEXT, Phone TEXT, Fax TEXT, Email TEXT, Industry TEXT, Remarks TEXT, PostPeriod TEXT, Rank TEXT, Website TEXT);
CREATE TABLE TmpTable2 (id INT, URL TEXT, CorporateNum TEXT, BranchNum INT, Title TEXT, CompanyName TEXT, Workplace TEXT, HeadOffice TEXT, PostalCode TEXT, Address TEXT, Recruiter TEXT, Phone TEXT, Fax TEXT, Email TEXT, Industry TEXT, Remarks TEXT, PostPeriod TEXT, Rank TEXT, Website TEXT);
CREATE TABLE ResultTable (URL TEXT, CorporateNum TEXT, BranchNum TEXT, Title TEXT, CompanyName TEXT, Workplace TEXT, HeadOffice TEXT, PostalCode TEXT, Address TEXT, Recruiter TEXT, Phone TEXT, Fax TEXT, Email TEXT, Industry TEXT, Remarks TEXT, PostPeriod TEXT, Rank TEXT, Website TEXT);
