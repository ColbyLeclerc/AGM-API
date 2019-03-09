-- ****************** SqlDBM: Microsoft SQL Server ******************
-- ******************************************************************

-- ************************************** [plant]

CREATE TABLE plant
(
 plant_id         int NOT NULL ,
 name             varchar(255) NOT NULL ,
 pot_size         int NULL ,
 pot_size_units   varchar(25) NULL ,
 date_harvested   timestamp(3) NULL ,
 date_planted     timestamp(3) NULL ,
 yield            double precision NULL ,
 yield_units      varchar(25) NULL ,
 insert_timestamp timestamp(3) NOT NULL ,
 update_timestamp timestamp(3) NULL ,

 CONSTRAINT PK_plant PRIMARY KEY (plant_id)
);






-- ************************************** [enclosure]

CREATE TABLE enclosure
(
 enclosure_id     int NOT NULL ,
 title            varchar(255) NOT NULL ,
 length           double precision NULL ,
 width            double precision NULL ,
 height           double precision NULL ,
 location         varchar(255) NULL ,
 dimension_units  varchar(25) NULL ,
 insert_timestamp timestamp(3) NOT NULL ,
 update_timestamp timestamp(3) NULL ,

 CONSTRAINT PK_enclosure PRIMARY KEY (enclosure_id)
);








-- ************************************** [temp_humid_sensor_link]

CREATE TABLE temp_humid_sensor_link
(
 temp_humid_sensor_id int NOT NULL ,
 plant_id             int NOT NULL ,
 enclosure_id         int NOT NULL ,
 sensor_location      varchar(255) NOT NULL ,
 insert_timestamp     timestamp(3) NOT NULL ,

 CONSTRAINT PK_temp_humid_sensor_link PRIMARY KEY (temp_humid_sensor_id),
 CONSTRAINT FK_81 FOREIGN KEY (plant_id)  REFERENCES plant(plant_id),
 CONSTRAINT FK_84 FOREIGN KEY (enclosure_id)  REFERENCES enclosure(enclosure_id)
);

/*

create index @name on @table (@columns) -> create index @name on @table (@columns)
create index @name on @table (@columns) INCLUDE (@icolumns) -> create index @name on @table (@columns)

 */


CREATE INDEX plantIdIndexTempHumidSensorLink ON temp_humid_sensor_link (plant_id);
CLUSTER temp_humid_sensor_link USING plantIdIndexTempHumidSensorLink;

-- CREATE INDEX fkIdx_81 ON temp_humid_sensor_link USING plant_id ASC;

-- CREATE NONCLUSTERED INDEX fkIdx_81 ON [temp_humid_sensor_link]
-- --  (
-- --   plant_id ASC
-- --  )
-- --
-- -- GO
CREATE INDEX enclosureIdTempHumidSensorLink ON temp_humid_sensor_link (enclosure_id);
CLUSTER temp_humid_sensor_link USING enclosureIdTempHumidSensorLink;
--
-- CREATE NONCLUSTERED INDEX fkIdx_84 ON [temp_humid_sensor_link]
--  (
--   enclosure_id ASC
--  )
--
-- GO







-- ************************************** [soil_temp_sensor_link]

CREATE TABLE soil_temp_sensor_link
(
 soil_temp_sensor_id int NOT NULL ,
 plant_id            int NOT NULL ,
 insert_timestamp    timestamp(3) NOT NULL ,

 CONSTRAINT PK_soil_temp_sensor_link PRIMARY KEY (soil_temp_sensor_id),
 CONSTRAINT FK_73 FOREIGN KEY (plant_id)  REFERENCES plant(plant_id)
);

CREATE INDEX plantIdSoilTempSensorLink ON soil_temp_sensor_link (plant_id);
CLUSTER soil_temp_sensor_link USING plantIdSoilTempSensorLink;

-- CREATE NONCLUSTERED INDEX fkIdx_73 ON [soil_temp_sensor_link]
--  (
--   plant_id ASC
--  )
--
-- GO







-- ************************************** [soil_moisture_sensor_link]

CREATE TABLE soil_moisture_sensor_link
(
 soil_moisture_sensor_id int NOT NULL ,
 plant_id                int NOT NULL ,
 insert_timestamp        timestamp(3) NOT NULL ,

 CONSTRAINT PK_soil_moisture_sensor_link PRIMARY KEY (soil_moisture_sensor_id),
 CONSTRAINT FK_65 FOREIGN KEY (plant_id)  REFERENCES plant(plant_id)
);

CREATE INDEX plantIdSoilMoistureSensorLink ON soil_moisture_sensor_link (plant_id);
CLUSTER soil_moisture_sensor_link USING plantIdSoilMoistureSensorLink;

-- CREATE NONCLUSTERED INDEX fkIdx_65 ON [soil_moisture_sensor_link]
--  (
--   plant_id ASC
--  )
--
-- GO







-- ************************************** [plant_enclosure]

CREATE TABLE plant_enclosure
(
 plant_enclosure_id int NOT NULL ,
 plant_id           int NOT NULL ,
 enclosure_id       int NOT NULL ,
 date_created       timestamp(3) NOT NULL ,
 insert_timestamp   timestamp(3) NOT NULL ,
 update_timestamp    timestamp(3) NOT NULL ,

 CONSTRAINT PK_plant_enclosure PRIMARY KEY (plant_enclosure_id),
 CONSTRAINT FK_32 FOREIGN KEY (plant_id)  REFERENCES plant(plant_id),
 CONSTRAINT FK_46 FOREIGN KEY (enclosure_id)  REFERENCES enclosure(enclosure_id)
);

CREATE INDEX plantIdPlantEnclosure ON plant_enclosure (plant_id);
CLUSTER plant_enclosure USING plantIdPlantEnclosure;

-- CREATE NONCLUSTERED INDEX fkIdx_32 ON [plant_enclosure]
--  (
--   plant_id ASC
--  )
--
-- GO
--
CREATE INDEX enclosureIdPlantEnclosure ON plant_enclosure (enclosure_id);
CLUSTER plant_enclosure USING enclosureIdPlantEnclosure;
-- CREATE NONCLUSTERED INDEX fkIdx_46 ON [plant_enclosure]
--  (
--   enclosure_id ASC
--  )
--
-- GO







-- ************************************** [temp_humid_reading]

CREATE TABLE temp_humid_reading
(
 temp_humid_reading_id int NOT NULL ,
 temp_humid_sensor_id  int NOT NULL ,
 temp_level            decimal(5,2) NOT NULL ,
 humidity              decimal(5,2) NOT NULL ,
 temp_scale            varchar(1) NOT NULL ,
 humidity_units        varchar(20) NOT NULL ,
 time_recorded         timestamp(3) NOT NULL ,
 insert_timestamp      timestamp(3) NOT NULL ,
 update_timestamp      timestamp(3) NULL ,

 CONSTRAINT PK_temp_humid_reading PRIMARY KEY (temp_humid_reading_id),
 CONSTRAINT FK_107 FOREIGN KEY (temp_humid_sensor_id)  REFERENCES temp_humid_sensor_link(temp_humid_sensor_id)
);

CREATE INDEX tempHumidSensorId ON temp_humid_reading (temp_humid_sensor_id);
CLUSTER temp_humid_reading USING tempHumidSensorId;

-- CREATE NONCLUSTERED INDEX fkIdx_107 ON [temp_humid_reading]
--  (
--   temp_humid_sensor_id ASC
--  )
--
-- GO







-- ************************************** [soil_temp_reading]

CREATE TABLE soil_temp_reading
(
 soil_temp_reading_id int NOT NULL ,
 soil_temp_sensor_id  int NOT NULL ,
 temp_level           decimal(5,2) NOT NULL ,
 temp_scale           varchar(1) NOT NULL ,
 time_recorded        timestamp(3) NOT NULL ,
 insert_timestamp     timestamp(3) NOT NULL ,
 update_timestamp     timestamp(3) NULL ,

 CONSTRAINT PK_soil_temp_reading PRIMARY KEY (soil_temp_reading_id),
 CONSTRAINT FK_94 FOREIGN KEY (soil_temp_sensor_id)  REFERENCES soil_temp_sensor_link(soil_temp_sensor_id)
);


-- CREATE NONCLUSTERED INDEX fkIdx_94 ON [soil_temp_reading]
--  (
--   soil_temp_sensor_id ASC
--  )
--
-- GO

CREATE INDEX soilTempSensorId ON soil_temp_reading (soil_temp_sensor_id);
CLUSTER soil_temp_reading USING soilTempSensorId;







-- ************************************** [soil_moisture_reading]

CREATE TABLE soil_moisture_reading
(
 soil_moisture_reading_id int NOT NULL ,
 soil_moisture_sensor_id  int NOT NULL ,
 moisture_level           decimal(5,2) NOT NULL ,
 moisture_level_units     varchar(25) NOT NULL ,
 time_recorded            timestamp(3) NOT NULL ,
 insert_timestamp         timestamp(3) NOT NULL ,
 update_timestamp         timestamp(3) NULL ,

 CONSTRAINT PK_soil_moisture_reading PRIMARY KEY (soil_moisture_reading_id),
 CONSTRAINT FK_62 FOREIGN KEY (soil_moisture_sensor_id)  REFERENCES soil_moisture_sensor_link(soil_moisture_sensor_id)
);


-- CREATE NONCLUSTERED INDEX fkIdx_62 ON [soil_moisture_reading]
--  (
--   soil_moisture_sensor_id ASC
--  )
--
-- GO
CREATE INDEX soilMoistureIdSoilMoistureSensorId ON soil_moisture_reading (soil_moisture_sensor_id);
CLUSTER soil_moisture_reading USING soilMoistureIdSoilMoistureSensorId;

/*

DROP TABLE temp_humid_reading;
DROP TABLE soil_moisture_reading;
DROP TABLE soil_temp_reading;
DROP TABLE soil_temp_sensor_link;
DROP TABLE soil_moisture_sensor_link;
DROP TABLE temp_humid_sensor_link;
DROP TABLE plant_enclosure;
DROP TABLE plant;
DROP TABLE enclosure;

*/




