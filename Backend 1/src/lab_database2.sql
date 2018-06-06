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
('090990-9009', 'Luigi C', 'LC');

INSERT INTO roller(rolle_id, cpr, rolle) VALUES
(1, '070770-7007', 'Admin' ),
(2, '080880-8008', 'Laborant'),
(3, '090990-9009', 'Foreman');

INSERT INTO operatoer(rolle_id, opr_status) VALUES
(1, 1),
(2, 1),
(3, 0);

INSERT INTO raavare(raavare_id, raavare_navn, leverandoer) VALUES
(1, 'dej', 'Wawelka'),
(2, 'tomat', 'Knoor'),
(3, 'tomat', 'Veaubais'),
(4, 'tomat', 'Franz'),
(5, 'ost', 'Ost og Skinke A/S'),
(6, 'skinke', 'Ost og Skinke A/S'),
(7, 'champignon', 'Igloo Frostvarer');

INSERT INTO raavarebatch(rb_id, raavare_id, maengde) VALUES
(1, 1, 1000),
(2, 2, 300),
(3, 3, 300),
(4, 5, 100),
(5, 5, 100), 
(6, 6, 100),
(7, 7, 100);

INSERT INTO recept(recept_id, recept_navn) VALUES
(1, 'margherita'),
(2, 'prosciutto'),
(3, 'capricciosa');


INSERT INTO receptkomponent(recept_id, raavare_id, nom_netto, tolerance) VALUES
(1, 1, 10.0, 0.1),
(1, 2, 2.0, 0.1),
(1, 5, 2.0, 0.1),

(2, 1, 10.0, 0.1),
(2, 3, 2.0, 0.1),  
(2, 5, 1.5, 0.1),
(2, 6, 1.5, 0.1),

(3, 1, 10.0, 0.1),
(3, 4, 1.5, 0.1),
(3, 5, 1.5, 0.1),
(3, 6, 1.0, 0.1),
(3, 7, 1.0, 0.1);

INSERT INTO produktbatch(pb_id, recept_id, status) VALUES
(1, 1, 2), 
(2, 1, 2),
(3, 2, 2),
(4, 3, 1),
(5, 3, 0);


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
create procedure NewEmployee(in oprnavn varchar(29), ini_ varchar(4), opr_status_ int(3), cpr_n varchar(11), rolle_Id int(11), rolle varchar(35))
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
