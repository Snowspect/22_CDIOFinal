/* must be dropped in this order to avoid constraint violations */
DROP TABLE IF EXISTS produktbatchkomponent;
DROP TABLE IF EXISTS produktbatch;
DROP TABLE IF EXISTS operatoer;
DROP TABLE IF EXISTS roller;
DROP TABLE IF EXISTS personer;
DROP TABLE IF EXISTS receptkomponent;
DROP TABLE IF EXISTS recept;
DROP TABLE IF EXISTS raavarebatch;
DROP TABLE IF EXISTS raavare;

CREATE TABLE personer(cpr varchar(11) PRIMARY KEY, opr_navn TEXT, ini TEXT) ENGINE=innoDB;

CREATE TABLE roller(rolle_id INT, cpr varchar(11), rolle varchar(35),
	PRIMARY KEY (rolle_id, cpr),
	FOREIGN KEY (cpr) REFERENCES personer(cpr)) ENGINE=innoDB;
 
CREATE TABLE operatoer(rolle_id INT PRIMARY KEY, opr_status BOOLEAN, FOREIGN KEY (rolle_id) REFERENCES roller(rolle_id)) ENGINE=innoDB;

CREATE TABLE raavare(raavare_id INT PRIMARY KEY, raavare_navn TEXT, leverandoer TEXT) ENGINE=innoDB;

CREATE TABLE raavarebatch(rb_id INT PRIMARY KEY, raavare_id INT, maengde REAL, 
   FOREIGN KEY (raavare_id) REFERENCES raavare(raavare_id)) ENGINE=innoDB;

CREATE TABLE recept(recept_id INT PRIMARY KEY, recept_navn TEXT) ENGINE=innoDB;

CREATE TABLE receptkomponent(recept_id INT, raavare_id INT, nom_netto REAL, tolerance REAL, 
   PRIMARY KEY (recept_id, raavare_id), 
   FOREIGN KEY (recept_id) REFERENCES recept(recept_id), 
   FOREIGN KEY (raavare_id) REFERENCES raavare(raavare_id)) ENGINE=innoDB;

CREATE TABLE produktbatch(pb_id INT PRIMARY KEY, status INT, recept_id INT, 
   FOREIGN KEY (recept_id) REFERENCES recept(recept_id)) ENGINE=innoDB;

CREATE TABLE produktbatchkomponent(pb_id INT, rb_id INT, tara REAL, netto REAL, rolle_id INT, 
   PRIMARY KEY (pb_id, rb_id), 
   FOREIGN KEY (pb_id) REFERENCES produktbatch(pb_id), 
   FOREIGN KEY (rb_id) REFERENCES raavarebatch(rb_id), 
   FOREIGN KEY (rolle_id) REFERENCES operatoer(rolle_id)) ENGINE=innoDB; 

INSERT INTO personer(cpr, opr_navn, ini) VALUES
('070770-7007', 'Angelo A', 'AA' ),
('080880-8008', 'Antonella B', 'AB'),
('090990-9009', 'Luigi C', 'LC'),
('030330-3003', 'Walter W', 'WW'),
('040440-4004', 'Harry P', 'HP'),
('050550-5005', 'Arnold S', 'AS'),
('060660-6006', 'Pablo E', 'PE'),
('020220-2002', 'Donald T', 'DT'),
('010110-1001', 'Jesse P', 'JE');

INSERT INTO roller(rolle_id, cpr, rolle) VALUES
(1, '070770-7007', 'Admin'),
(2, '080880-8008', 'Laborant'),
(3, '090990-9009', 'Foreman'),
(4, '030330-3003', 'Admin'),
(5, '040440-4004', 'Laborant'),
(6, '050550-5005', 'Laborant'),
(7, '060660-6006', 'Foreman'),
(8, '020220-2002', 'Laborant'),
(9, '010110-1001', 'Foreman');

INSERT INTO operatoer(rolle_id, opr_status) VALUES
(1, 1),
(2, 1),
(3, 0),
(4, 1),
(5, 1),
(6, 1),
(7, 1),
(8, 1),
(9, 1);

INSERT INTO raavare(raavare_id, raavare_navn, leverandoer) VALUES
(1, 'Ibuprofen', 'Big Pharma'),
(2, 'Aluminium', 'Novo Nordisk'),
(3, 'Codein', 'Novo Nordisk'),
(4, 'Kulsyre', 'Matas'),
(5, 'Salmiak', 'Apoteket A/S'),
(6, 'Saltsyre', 'Medifirma A/S'),
(7, 'Salt', 'Salt Inc.');

INSERT INTO raavarebatch(rb_id, raavare_id, maengde) VALUES
(1, 1, 1000),
(2, 2, 300),
(3, 3, 300),
(4, 5, 100),
(5, 5, 100), 
(6, 6, 100),
(7, 7, 100);

INSERT INTO recept(recept_id, recept_navn) VALUES
(1, 'Pinex'),
(2, 'Ipren'),
(3, 'Kodimagnyl'),
(4, 'Panodil'),
(5, 'Aspirin'),
(6, 'Morfin');


INSERT INTO receptkomponent(recept_id, raavare_id, nom_netto, tolerance) VALUES
(1, 1, 4.0, 0.1),
(1, 2, 2.0, 0.1),
(1, 5, 2.0, 0.1),

(2, 1, 2.0, 0.1),
(2, 3, 2.0, 0.1),  
(2, 5, 1.5, 0.1),
(2, 6, 1.5, 0.1),

(3, 1, 5.0, 0.1),
(3, 4, 1.5, 0.1),
(3, 5, 1.5, 0.1),
(3, 6, 1.0, 0.1),
(3, 7, 1.0, 0.1),

(4, 1, 2.5, 0.1),
(4, 3, 2.0, 0.1),  
(4, 7, 1.5, 0.1),
(4, 2, 3.5, 0.1),

(5, 1, 1.0, 0.1),
(5, 5, 5.0, 0.1),  
(5, 7, 1.5, 0.1),

(6, 2, 4.0, 0.1),
(6, 7, 2.0, 0.1); 

INSERT INTO produktbatch(pb_id, recept_id, status) VALUES
(1, 1, 2), 
(2, 1, 2),
(3, 2, 2),
(4, 3, 1),
(5, 3, 0),
(6, 1, 0),
(7, 3, 0),
(8, 2, 0);


INSERT INTO produktbatchkomponent(pb_id, rb_id, tara, netto, rolle_id) VALUES
(1, 1, 0.5, 10.05, 1),
(1, 2, 0.5, 2.03, 1),
(1, 4, 0.5, 1.98, 1),

(2, 1, 0.5, 10.01, 2),
(2, 2, 0.5, 1.99, 2),
(2, 5, 0.5, 1.47, 2),

(3, 1, 0.5, 10.07, 1),
(3, 3, 0.5, 2.06, 2),
(3, 4, 0.5, 1.55, 1),
(3, 6, 0.5, 1.53, 2),

(4, 1, 0.5, 10.02, 3),
(4, 5, 0.5, 1.57, 3),
(4, 6, 0.5, 1.03, 3),
(4, 7, 0.5, 0.99, 3);

delimiter //
create procedure NewEmployee(in oprnavn varchar(29), ini_ varchar(4), opr_status_ boolean, cpr_n varchar(11), rolle_Id int(11), rolle varchar(35))
begin 


declare exit handler for sqlexception
	begin 
	rollback;

END;

start transaction;

insert into personer 
(cpr, opr_navn, ini) values(cpr_n, oprnavn, ini_);

insert into roller 
(rolle_id, cpr, rolle) values(rolle_Id, cpr_n, rolle);

insert into operatoer
(rolle_id, opr_status) values(rolle_Id, opr_status_);

commit;
end; //
delimiter ;
 
delimiter //
create procedure UpdateEmployee(in oprnavn varchar(29), ini_ varchar(4), rolle_Id int(11), rolle varchar(35), cpr_n varchar(11))
begin 


declare exit handler for sqlexception
	begin 
	rollback;

END;

start transaction;

update personer
set opr_navn = oprnavn, ini = ini_
where cpr = cpr_n;

update roller 
set  rolle = rolle
where cpr = cpr_n and rolle_id = rolle_Id;

commit;
end; //
delimiter ;





delimiter //
create procedure CreateProduktBatchKomp(in pb_id int(11), rb_id int(11), tara double, netto double, rolle_id int(11))
begin 

declare exit handler for sqlexception
	begin 
	rollback;
END;

start transaction;

INSERT INTO produktbatchkomponent
(pb_id, rb_id, tara, netto, rolle_id) VALUES (pb_id, rb_id, tara, netto, rolle_id);

commit;
end; //
delimiter ;





delimiter //
create procedure MakeProBaKompRow(in par_pb_id int(8), par_rb_id int(8), par_tara double(4,2), par_netto double(4,2), par_rolle_id int(3))
begin 

declare exit handler for sqlexception
	begin 
	rollback;
END;

start transaction;

insert into produktbatchkomponent  
(pb_id, rb_id, tara, netto, rolle_id) values(par_pb_id, par_rb_id, par_tara, par_netto, par_rolle_id);

update raavarebatch set maengde = (maengde - par_netto) where rb_id = par_rb_id;

commit;
end; //
delimiter ;


-- Admin 			Check
-- Pharmasict		Check	- Laver pizza
-- Foreman			Check	- Holder styr på hvem arbejder og produkter
-- Operator			Check	- Vejer ingredienser af

-- produktbatchkomponent er der hvor operator indsætter deres vejede ingredienser

CREATE VIEW operatorView as
select * from raavare
natural join receptkomponent
natural left join recept
order by recept_id;

CREATE VIEW PharmacistView as 
select recept_navn, raavare_navn, raavare_id from recept
natural join receptkomponent
natural join raavare;



CREATE VIEW ForemanView as 
select rb_id, rolle_id, pb_id, opr_navn, ini, opr_status, status, raavare_navn, leverandoer, maengde, recept_id from operatoer
natural join personer
natural join roller
natural join produktbatchkomponent
natural join produktbatch
natural join raavare
natural join raavarebatch
natural join recept
order by pb_id;
