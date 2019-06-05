select * from employee;
select * from department;
select * from employeetitle;
select * from eventtype;
select * from request;
select * from Status;
select * from requeststory;

select count(*) from requeststory where requestid = 1;

select e.EmployeeId, ReportsTo from employee e inner join request r on e.employeeid = r.employeeid;
select e.employeeid, reportsto, title from employee e inner join employeetitle et on e.employeetitle = et.titleid;