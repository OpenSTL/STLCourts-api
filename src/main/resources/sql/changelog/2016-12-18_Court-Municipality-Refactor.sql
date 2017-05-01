# Drop Existing Courts Table
DROP TABLE courts;

# Add New Courts, Municipalities, and Judges Tables

CREATE TABLE courts 
(
    id 						INTEGER 					NOT NULL,
    court_name				VARCHAR(50),
    phone					VARCHAR(50),
    extension				VARCHAR(15),
    website					VARCHAR(200),
 	payment_system			VARCHAR(50),
    address 				VARCHAR(50),
    city 					VARCHAR(50),
    state 					VARCHAR(25),
    zip_code 				VARCHAR(12),
    latitude 				DOUBLE 			PRECISION,
    longitude 				DOUBLE 			PRECISION
);

CREATE TABLE municipalities 
(
    id 						INTEGER 					NOT NULL,
    municipality_name		VARCHAR(50),
	court_id				INTEGER						NOT NULL
);

CREATE TABLE judges 
(
    id 						INTEGER 					NOT NULL,
    judge		 			VARCHAR(100),
	court_id				INTEGER						NOT NULL
);

# Insert Courts
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (1,'Ballwin','636.227.9468','','http://www.ballwin.mo.us/City-Government/Departments/Municipal-Court','iPayCourt','300 Park Drive','St. Louis','MO','63011','38.59729089','-90.54189364');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (2,'Bella Villa','314.638.8840','','','IPG','751 Avenue H','St. Louis','MO','63125','38.54256229','-90.28909176');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (3,'Bellefontaine Neighbors','314.867.0076','','http://www.cityofbn.com/departments/court/','iPayCourt','9641 Bellefontaine Road','St. Louis','MO','63137','38.74151895','-90.22576437');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (4,'Normandy','314.385.3300','3014','','nCourt','7700 Natural Bridge Road','St. Louis','MO','63121','38.70629459','-90.30322952');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (5,'Bel-Nor','314.381.2834','','','','8416 Natural Bridge Road','St. Louis','MO','63121','38.70896713','-90.31745003');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (6,'Bel-Ridge','314.429.2878','','http://bel-ridge.us/?page_id=90','Collector Solutions','8920 Natural Bridge Road','St. Louis','MO','63121','38.71566804','-90.32623591');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (7,'Berkeley','314.400.3700','','http://www.cityofberkeley.us/index.aspx?nid=124','iPayCourt','6120 Madison','St. Louis','MO','63134','38.74824406','-90.33447514');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (8,'Beverly Hills','314.382.6544','','','','7150 Natural Bridge Road','St. Louis','MO','63121','38.7009367','-90.29187735');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (9,'Black Jack','314.355-0400','102','http://www.cityofblackjack.com/241/Municipal-Court','iPayCourt','12500 Old Jamestown Road','St. Louis','MO','63033','38.7947506','-90.26261696');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (10,'Breckenridge Hills','314.427.1412','','','nCourt','9623 St. Charles Rock Road','St. Louis','MO','63114','38.71925635','-90.36600678');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (11,'Brentwood','314.963.8622','','http://www.brentwoodmo.org/index.aspx?nid=477','','2348 South Brentwood Boulevard','St. Louis','MO','63144','38.61842688','-90.34844504');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (12,'Bridgeton','314.739.1145','','http://www.bridgetonmo.com/departments/municipal-court','iPayCourt','12355 Natural Bridge Road','St. Louis','MO','63044','38.75626745','-90.42347742');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (13,'Calverton Park','314.524.1212','12','http://www.calvertonparkmo.com/village-court/','nCourt','52 Young Drive','St. Louis','MO','63135','38.7646557','-90.31692672');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (14,'Charlack','314.427.4715','','http://www.cityofcharlack.com/#!municipal-court/c1g4i','IPG','8401 Midland Boulevard','St. Louis','MO','63114','38.69634571','-90.33991661');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (15,'Chesterfield','636.537.4717','','http://www.chesterfield.mo.us/municipal-court.html','IPG','690 Chesterfield Parkway West','St. Louis','MO','63017','38.66190342','-90.56366971');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (16,'Clarkson Valley','636.227.8607','','http://www.clarksonvalley.org/traffic.html','iPayCourt','15933 Clayton Road','St. Louis','MO','63011','38.60490831','-90.58713047');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (17,'Clayton','314.290.8441','','http://www.claytonmo.gov/Government/Municipal_Court.htm?','iPayCourt','10 South Brentwood Boulevard','St. Louis','MO','63105','38.64984611','-90.34146558');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (18,'Cool Valley','314.521.3500','','http://www.cityofcoolvalley.com/index.php/departments/municipal-court','IPG','100 Signal Drive','St. Louis','MO','63121','38.70770154','-90.30290904');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (19,'Country Club Hills','314.261.0845','4','','','7422 Eunice Avenue','St. Louis','MO','63136','38.71945332','-90.27428266');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (20,'Town and Country','314.587.2832','','http://www.town-and-country.org/150/Municipal-Court','IPG','1011 Municipal Center Drive','St. Louis','MO','63131','38.63201598','-90.45143593');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (21,'Crestwood','314.729.4776','','http://www.cityofcrestwood.org/department/index.php?structureid=19','iPayCourt','1 Detjen Drive','St. Louis','MO','63126','38.55936592','-90.38569543');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (22,'Creve Coeur','314.432.8844','','http://www.creve-coeur.org/index.aspx?nid=101','IPG','300 North New Ballas Road','St. Louis','MO','63141','38.66179036','-90.443226');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (23,'Frontenac','314.994.3204','','','IPG','10555 Clayton Road','St. Louis','MO','63131','38.63307777','-90.41216022');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (24,'Dellwood','314.521.4339','104','http://www.cityofdellwoodmo.com/563/Municipal-Court','nCourt','1415 Chambers Road','St. Louis','MO','63135','38.74944799','-90.28180026');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (25,'Des Peres','314.835.6119','','http://www.desperesmo.org/Index.aspx?NID=96','nCourt','12325 Manchester Road','St. Louis','MO','63131','38.60332431','-90.4419091');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (26,'Edmundson','314.428.7125','117','http://www.cityofedmundson.com/index.php?option=com_content&view=article&id=7&Itemid=115','nCourt','4440 Holman Lane','St. Louis','MO','63134','38.73303513','-90.3638459');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (27,'Ellisville','636.227.3727','','http://www.ellisville.mo.us/165/Municipal-Court','','37 Weis Avenue','St. Louis','MO','63011','38.5928963','-90.59225362');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (28,'Eureka','636.938.6600','208','http://www.eureka.mo.us/departments/municipal-court/','iPayCourt','120 City Hall Drive','St. Louis','MO','63025','38.49536426','-90.62921936');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (29,'Fenton','636.343.1007','','http://fentonmo.org/index.aspx?nid=563','IPG','625 New Smizer Mill Road','St. Louis','MO','63026','38.51775109','-90.45204336');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (30,'Ferguson','314.524.5354','','http://www.fergusoncity.com/60/The-City-Of-Ferguson-Municipal-Courts','IPG','110 Church Street','St. Louis','MO','63135','38.74499337','-90.30386823');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (31,'Flordell Hills','314.382.5524','','','','7001 Brandon Avenue','St. Louis','MO','63136','38.71705529','-90.26183924');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (32,'Florissant','314.921.3322','','http://www.florissantmo.com/department/index.php?structureid=19','iPayCourt','315 Howdershell Road','St. Louis','MO','63031','38.80713686','-90.3612334');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (33,'Glendale','314.965.0000','','http://www.glendalemo.org/index.php?option=com_content&view=article&id=11&Itemid=18','iPayCourt','424 North Sappington Road','St. Louis','MO','63122','38.58970992','-90.38647565');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (34,'Grantwood Village','314.842.4409','','','','1 Missionary Way','St. Louis','MO','63123','38.54861159','-90.34399783');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (35,'St. Louis County','314.615.8760','','','Municipal Online Payments Tyler','4544 Lemay Ferry Road','St. Louis','MO','63129','38.49676468','-90.33667373');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (36,'Hanley Hills','314.725.0909','','','iPayCourt','7713 Utica Drive','St. Louis','MO','63133','38.68316018','-90.32347577');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (37,'Hazelwood','314.839.2212','','http://www.hazelwoodmo.org/city-government/departments/courts','iPayCourt','415 Elm Grove Lane','St. Louis','MO','63042','38.78499519','-90.3552467');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (38,'Hillsdale','314.381.0288','','','','6428 Jesse Jackson Avenue','St. Louis','MO','63121','38.68527443','-90.28953233');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (39,'Jennings','314.385.4670','','http://www.cityofjennings.org/court.html','','7005 Florence Avenue','St. Louis','MO','63136','38.71028485','-90.2666946');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (40,'Kinloch','314.521.3335','','','','8140 Scott','St. Louis','MO','63140','38.73771069','-90.32253562');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (41,'Kirkwood','314.822.5840','','http://www.kirkwoodmo.org/content/City-Departments/2040/municipal-court.aspx','iPayCourt','139 S. Kirkwood Road','St. Louis','MO','63122','38.58022142','-90.40643518');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (42,'Ladue','314.993.3919','','http://www.cityofladue-mo.gov/departments/municipal-court-196','iPayCourt','9435 Clayton Road','St. Louis','MO','63124','38.64080665','-90.3796107');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (43,'Lakeshire','314.631.6222','6','http://www.lakeshiremo.com/court-procedures/','','10000 Puttington Drive','St. Louis','MO','63123','38.53679413','-90.33812054');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (44,'Mackenzie','314.752.0625','','','','4400 Shrewsbury Avenue','St. Louis','MO','63119','38.59351418','-90.32669611');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (45,'Manchester','636.227.1410','','http://www.manchestermo.gov/index.asp?SEC=0BE94A96-7F3E-4567-A282-BB5616082CA4&Type=B_BASIC','Municipal Online Payments Tyler','200 Highlands Blvd. Dr.','St. Louis','MO','63011','38.59520185','-90.5048459');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (46,'Maplewood','314.646.3636','','http://www.cityofmaplewood.com/index.aspx?nid=66','iPayCourt','7601 Manchester Road','St. Louis','MO','63143','38.61291917','-90.32600283');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (47,'Marlborough','314.962.5055','','http://www.villageofmarlborough.com/Pages/MunicipalCourt.aspx','nCourt','7826 Wimbledon Drive','St. Louis','MO','63119','38.57086346','-90.33760305');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (48,'Maryland Heights','314.291.6036','','http://www.marylandheights.com/index.aspx?page=78','nCourt','11911 Dorsett Road','St. Louis','MO','63043','38.71455813','-90.43552075');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (49,'Moline Acres','314.868.2433','102/103','http://www.molineacres.org/Courts.aspx','nCourt','2449 Chambers Road','St. Louis','MO','63136','38.7511275','-90.24399078');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (50,'Northwoods','314.385.0260','','','nCourt','4600 Oakridge Boulevard','St. Louis','MO','63121','38.70600205','-90.28025478');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (51,'St. Louis County','314.615.8760','','','Municipal Online Payments Tyler','21 Village Square','St. Louis','MO','63042','38.78155359','-90.35911603');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (52,'Oakland','314.842.0778','','http://www.oaklandmo.org/municipal-court.aspx','','1320 W. Lockwood Avenue','St. Louis','MO','63122','38.58289279','-90.37908042');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (53,'Olivette','314.991.6047','','http://www.olivettemo.com/pView.aspx?id=2037&catid=29','iPayCourt','9473 Olive Boulevard','St. Louis','MO','63132','38.67413789','-90.38043126');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (54,'Overland','314.428.1224','','http://www.overlandmo.org/58/Overland-Municipal-Court','nCourt','2410 Goodale Avenue','St. Louis','MO','63114','38.7002179','-90.35906835');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (55,'Pacific','636.257.4553','','','iPayCourt','300 Hoven Drive','St. Louis','MO','63079','38.27440022','-91.1054967');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (56,'Pagedale','314.726.1200','','http://www.cityofpagedale.com/#!municipal-court/cme6','','1420 Ferguson Avenue','St. Louis','MO','63133','38.68204661','-90.30739183');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (57,'Pasadena Hills','314.382.4453','','','nCourt','3915 Roland Boulevard','St. Louis','MO','63121','38.7044589','-90.29349626');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (58,'Pasadena Park','314.385.4115','','','nCourt','7615 South Sunset','St. Louis','MO','63121','38.70988086','-90.29806144');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (59,'Pine Lawn','314.802.1043','','http://www.pinelawn.org/#!court/c78g','nCourt','6250 Steve Marve Avenue','St. Louis','MO','63121','38.69286188','-90.27816339');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (60,'Richmond Heights','314.645.1982','','http://www.richmondheights.org/departments/court/index.php','iPayCourt','7447 Dale Avenue','St. Louis','MO','63117','38.62792123','-90.3187518');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (61,'Riverview','314.868.0700','','http://www.riverviewmo.org/Courts.aspx','iPayCourt','9699 Lilac Drive','St. Louis','MO','63137','38.74009119','-90.21496583');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (62,'Rock Hill','314.962.6265','','http://www.rockhillmo.net/Courts.aspx','iPayCourt','320 West Thornton Avenue','St. Louis','MO','63119','38.60546309','-90.3623925');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (63,'St. Ann','314.428.6811','6','http://www.stannmo.org/369/Municipal-Court','nCourt','10405 St. Charles Rock Road','St. Louis','MO','63074','38.72840568','-90.38262593');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (64,'St. John','314.427.8700','6','http://www.cityofstjohn.org/index.php/municipal-court.html','nCourt','8944 St. Charles Rock Road','St. Louis','MO','63114','38.71151496','-90.35188417');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (65,'Sunset Hills','314.849.3402','','http://www.sunset-hills.com/148/Municipal-Court','','3939 South Lindbergh','St. Louis','MO','63127','38.5453508','-90.40242404');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (66,'University City','314.505.8540','','http://www.ucitymo.org/index.aspx?nid=82','iPayCourt','975 Pennsylvania Avenue','St. Louis','MO','63130','38.6640235','-90.31591854');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (67,'Uplands Park','314.383.1856','','https://uplandsparkmissouri.wordpress.com/municipal-court/','nCourt','6390 Natural Bridge Road','St. Louis','MO','63121','38.69371954','-90.28107949');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (68,'Valley Park','636.225.5696','','','iPayCourt','320 Benton Street','St. Louis','MO','63088','38.55144034','-90.4874997');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (69,'Velda City','314.382.6600','','http://www.veldacity.org/departments/municipal-court','iPayCourt','2560 Lucas & Hunt Road','St. Louis','MO','63121','38.69258924','-90.29618745');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (70,'Velda Village Hills','314.261.7221','','','nCourt','3501 Avondale Avenue','St. Louis','MO','63121','38.69312888','-90.28588658');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (71,'Vinita Park','314.428.7373','','http://www.vinitapark.org/index.php/city-departments/court-information','nCourt','8374 Midland Boulevard','St. Louis','MO','63114','38.69556481','-90.33883384');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (72,'Vinita Terrace','314.427.4488','','','','8027 Page Boulevard','St. Louis','MO','63130','38.68358813','-90.32986256');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (73,'Warson Woods','314.965.3100','','http://www.warsonwoods.com/Templates/Warson%20Woods%20City%20Hall%203.5%20(Court%20Page).html','','10015 Manchester Road','St. Louis','MO','63122','38.60313788','-90.38556887');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (74,'Webster Groves','314.963.5416','','http://www.webstergroves.org/index.aspx?nid=239','IPG','4 Lockwood Aveue','St. Louis','MO','63119','38.58883088','-90.34656765');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (75,'Wellston','314.553.8002','','','nCourt','1414 Evergreen','St. Louis','MO','63133','38.67538348','-90.28923273');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (76,'Wildwood','636.458.8277','','http://www.cityofwildwood.com/135/Municipal-Court','IPG','16860 Main Street','St. Louis','MO','63040','38.58090325','-90.63089227');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (77,'Winchester','636.391.0600','','http://www.city.winchester.mo.us/Municipal-Court/','','109 Lindy Boulevard','St. Louis','MO','63021','38.592682','-90.52712599');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (78,'Woodson Terrace','314.427.2600','','http://woodsonterrace.net/City-Clerk/Municipal-Court-Information-Online-Payment-Link/','iPayCourt','4323 Woodson Road','St. Louis','MO','63134','38.72808378','-90.35789852');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (79,'St. Louis County','314.615.8760','','http://www.stlouisco.com/LawandPublicSafety/MunicipalCourts','Municipal Online Payments Tyler','7900 Carondelet Avenue','St. Louis','MO','63105','38.64893351','-90.33859257');
INSERT INTO courts(id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude) VALUES (80,'St. Louis County','314.615.8760','','http://www.stlouisco.com/LawandPublicSafety/MunicipalCourts','Municipal Online Payments Tyler','82 Clarkson Wilson Centre','St. Louis','MO','63017','38.627512','-90.58024738');

# Insert Municipalities
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (1,'Ballwin',1);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (2,'Bella Villa',2);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (3,'Bellefontaine Neighbors',3);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (4,'Bellerive',4);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (5,'Bel-Nor',5);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (6,'Bel-Ridge',6);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (7,'Berkeley 1',7);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (8,'Beverly Hills',8);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (9,'Black Jack',9);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (10,'Breckenridge Hills',10);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (11,'Brentwood',11);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (12,'Bridgeton',12);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (13,'Calverton Park',13);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (14,'Charlack',14);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (15,'Chesterfield',15);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (16,'Clarkson Valley',16);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (17,'Clayton',17);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (18,'Cool Valley',18);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (19,'Country Club Hills',19);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (20,'Country Life Acres',20);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (21,'Crestwood',21);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (22,'Creve Coeur',22);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (23,'Crystal Lake Park',23);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (24,'Dellwood',24);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (25,'Des Peres',25);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (26,'Edmundson',26);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (27,'Ellisville',27);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (28,'Eureka',28);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (29,'Fenton',29);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (30,'Ferguson',30);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (31,'Flordell Hills',31);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (32,'Florissant',32);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (33,'Frontenac',23);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (34,'Glen Echo Park',4);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (35,'Glendale',33);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (36,'Grantwood Village',34);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (37,'Greendale',4);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (38,'Green Park',35);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (39,'Hanley Hills',36);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (40,'Hazelwood',37);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (41,'Hillsdale',38);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (42,'Huntleigh',23);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (43,'Jennings',39);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (44,'Kinloch',40);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (45,'Kirkwood',41);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (46,'Ladue',42);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (47,'Lakeshire',43);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (48,'Mackenzie',44);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (49,'Manchester',45);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (50,'Maplewood',46);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (51,'Marlborough',47);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (52,'Maryland Heights',48);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (53,'Moline Acres',49);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (54,'Normandy',4);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (55,'Northwoods',50);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (56,'Norwood Court',51);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (57,'Oakland',52);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (58,'Olivette',53);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (59,'Overland',54);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (60,'Pacific',55);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (61,'Pagedale',56);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (62,'Pasadena Hills',57);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (63,'Pasadena Park',58);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (64,'Pine Lawn',59);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (65,'Richmond Heights',60);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (66,'Riverview',61);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (67,'Rock Hill',62);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (68,'Shrewsbury',44);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (69,'St. Ann',63);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (70,'St. John',64);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (71,'Sunset Hills',65);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (72,'Sycamore Hills',64);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (73,'Town and Country',20);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (74,'Twin Oaks',35);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (75,'University City',66);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (76,'Uplands Park',67);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (77,'Valley Park',68);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (78,'Velda City',69);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (79,'Velda Village Hills',70);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (80,'Vinita Park',71);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (81,'Vinita Terrace',72);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (82,'Warson Woods',73);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (83,'Webster Groves',74);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (84,'Wellston',75);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (85,'Westwood',23);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (86,'Wilbur Park',35);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (87,'Wildwood',76);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (88,'Winchester',77);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (89,'Woodson Terrace',78);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (90,'Central St. Louis County',79);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (91,'West St. Louis County',80);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (92,'North St. Louis County',51);
INSERT INTO municipalities(id,municipality_name,court_id) VALUES (93,'South St. Louis County',35);

# Insert Judges
INSERT INTO judges(id,judge,court_id) VALUES (1,'Virginia Nye',1);
INSERT INTO judges(id,judge,court_id) VALUES (2,'William Buchholz II',2);
INSERT INTO judges(id,judge,court_id) VALUES (3,'John Duepner',3);
INSERT INTO judges(id,judge,court_id) VALUES (4,'Charles Kirksey',4);
INSERT INTO judges(id,judge,court_id) VALUES (5,'Sean O''Hagan',5);
INSERT INTO judges(id,judge,court_id) VALUES (6,'Thomas Flach',6);
INSERT INTO judges(id,judge,court_id) VALUES (7,'Jennifer Fisher',7);
INSERT INTO judges(id,judge,court_id) VALUES (8,'Brian Dunlop',8);
INSERT INTO judges(id,judge,court_id) VALUES (9,'John Duepner',9);
INSERT INTO judges(id,judge,court_id) VALUES (10,'William Buchholz II',10);
INSERT INTO judges(id,judge,court_id) VALUES (11,'Patrick Dignam',11);
INSERT INTO judges(id,judge,court_id) VALUES (12,'Joseph Larrew',12);
INSERT INTO judges(id,judge,court_id) VALUES (13,'Philip Ayers',13);
INSERT INTO judges(id,judge,court_id) VALUES (14,'William Buchholz II',14);
INSERT INTO judges(id,judge,court_id) VALUES (15,'Rick Brunk',15);
INSERT INTO judges(id,judge,court_id) VALUES (16,'April Porter',16);
INSERT INTO judges(id,judge,court_id) VALUES (17,'Joseph Dulles',17);
INSERT INTO judges(id,judge,court_id) VALUES (18,'Kevin Kelly',18);
INSERT INTO judges(id,judge,court_id) VALUES (19,'William Buchholz II',19);
INSERT INTO judges(id,judge,court_id) VALUES (20,'Dean Waldemer',20);
INSERT INTO judges(id,judge,court_id) VALUES (21,'John Newsham',21);
INSERT INTO judges(id,judge,court_id) VALUES (22,'Tim Engelmeyer',22);
INSERT INTO judges(id,judge,court_id) VALUES (23,'Andrea Niehoff',23);
INSERT INTO judges(id,judge,court_id) VALUES (24,'Donnell Smith',24);
INSERT INTO judges(id,judge,court_id) VALUES (25,'Charles Billings Jr.',25);
INSERT INTO judges(id,judge,court_id) VALUES (26,'Sam Alton',26);
INSERT INTO judges(id,judge,court_id) VALUES (27,'Donald Anderson',27);
INSERT INTO judges(id,judge,court_id) VALUES (28,'Rick Barry',28);
INSERT INTO judges(id,judge,court_id) VALUES (29,'Charles Billings Jr.',29);
INSERT INTO judges(id,judge,court_id) VALUES (30,'Roy Richter',30);
INSERT INTO judges(id,judge,court_id) VALUES (31,'Mark Burstein',31);
INSERT INTO judges(id,judge,court_id) VALUES (32,'Dan Boyle',32);
INSERT INTO judges(id,judge,court_id) VALUES (33,'William Burnet',33);
INSERT INTO judges(id,judge,court_id) VALUES (34,'Timothy Devereux',34);
INSERT INTO judges(id,judge,court_id) VALUES (35,'Celeste Endicott',35);
INSERT INTO judges(id,judge,court_id) VALUES (36,'Tonia Aldahdah',35);
INSERT INTO judges(id,judge,court_id) VALUES (37,'Steven Clark',36);
INSERT INTO judges(id,judge,court_id) VALUES (38,'Kevin Kelly',37);
INSERT INTO judges(id,judge,court_id) VALUES (39,'Philip Dennis',38);
INSERT INTO judges(id,judge,court_id) VALUES (40,'Victor Thompson',38);
INSERT INTO judges(id,judge,court_id) VALUES (41,'John Duepner',39);
INSERT INTO judges(id,judge,court_id) VALUES (42,'Charles Kirksey',40);
INSERT INTO judges(id,judge,court_id) VALUES (43,'Stephen O''Brien',41);
INSERT INTO judges(id,judge,court_id) VALUES (44,'Keith Cheung',42);
INSERT INTO judges(id,judge,court_id) VALUES (45,'Richard Bumb',44);
INSERT INTO judges(id,judge,court_id) VALUES (46,'Michael Gunn',45);
INSERT INTO judges(id,judge,court_id) VALUES (47,'Brian Dunlop',46);
INSERT INTO judges(id,judge,court_id) VALUES (48,'Ronald Wessel',47);
INSERT INTO judges(id,judge,court_id) VALUES (49,'Kevin Kelly',48);
INSERT INTO judges(id,judge,court_id) VALUES (50,'Jennifer Fisher',49);
INSERT INTO judges(id,judge,court_id) VALUES (51,'Raphael Norris',50);
INSERT INTO judges(id,judge,court_id) VALUES (52,'Daniel R. Brown',51);
INSERT INTO judges(id,judge,court_id) VALUES (53,'Craig J. Concannon',51);
INSERT INTO judges(id,judge,court_id) VALUES (54,'Chris Graville',52);
INSERT INTO judges(id,judge,court_id) VALUES (55,'Paul D''Agrosa',53);
INSERT INTO judges(id,judge,court_id) VALUES (56,'Frank Vatterott',54);
INSERT INTO judges(id,judge,court_id) VALUES (57,' Mason Klippel',54);
INSERT INTO judges(id,judge,court_id) VALUES (58,' Charles Billings',54);
INSERT INTO judges(id,judge,court_id) VALUES (59,'Daniel Leslie',55);
INSERT INTO judges(id,judge,court_id) VALUES (60,'Raphael Norris',56);
INSERT INTO judges(id,judge,court_id) VALUES (61,'Dan Hayes',57);
INSERT INTO judges(id,judge,court_id) VALUES (62,'Dan Diemar',57);
INSERT INTO judges(id,judge,court_id) VALUES (63,'Daniel Chartrand',58);
INSERT INTO judges(id,judge,court_id) VALUES (64,'Dean Plocher',59);
INSERT INTO judges(id,judge,court_id) VALUES (65,'Stephen O''Brien',60);
INSERT INTO judges(id,judge,court_id) VALUES (66,'Christopher McDonough',61);
INSERT INTO judges(id,judge,court_id) VALUES (67,'Mark Levitt',62);
INSERT INTO judges(id,judge,court_id) VALUES (68,'Sean O''Hagan',63);
INSERT INTO judges(id,judge,court_id) VALUES (69,'William Buchholz',63);
INSERT INTO judges(id,judge,court_id) VALUES (70,'Dennis Beckley',64);
INSERT INTO judges(id,judge,court_id) VALUES (71,'W. Laird Hetlage',65);
INSERT INTO judges(id,judge,court_id) VALUES (72,'Paul D''Agrosa',66);
INSERT INTO judges(id,judge,court_id) VALUES (73,'Phillip Ayers',67);
INSERT INTO judges(id,judge,court_id) VALUES (74,'Steven Clark',68);
INSERT INTO judges(id,judge,court_id) VALUES (75,'Wesley Bell (City website)',69);
INSERT INTO judges(id,judge,court_id) VALUES (76,'Raphael Norris (State)',69);
INSERT INTO judges(id,judge,court_id) VALUES (77,'Thomas Flach',70);
INSERT INTO judges(id,judge,court_id) VALUES (78,'William Goldstein',71);
INSERT INTO judges(id,judge,court_id) VALUES (79,'Daniel Chartrand',72);
INSERT INTO judges(id,judge,court_id) VALUES (80,'John Lord III',73);
INSERT INTO judges(id,judge,court_id) VALUES (81,'James Whitney',74);
INSERT INTO judges(id,judge,court_id) VALUES (82,'Charles Kirksey',75);
INSERT INTO judges(id,judge,court_id) VALUES (83,'Rick Brunk',76);
INSERT INTO judges(id,judge,court_id) VALUES (84,'Daniel Bruntrager',77);
INSERT INTO judges(id,judge,court_id) VALUES (85,'Kelly Clarkin',78);
INSERT INTO judges(id,judge,court_id) VALUES (86,'Thomas Clarkin',78);
INSERT INTO judges(id,judge,court_id) VALUES (87,' Daniel Brown',79);
INSERT INTO judges(id,judge,court_id) VALUES (88,'Celeste Endicott',79);
INSERT INTO judges(id,judge,court_id) VALUES (89,' Craig Cocannon',79);
INSERT INTO judges(id,judge,court_id) VALUES (90,' Tonia Aldahdah',79);
INSERT INTO judges(id,judge,court_id) VALUES (91,'Craig J. Cocannon',80);
