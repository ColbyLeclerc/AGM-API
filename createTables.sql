-- ****************** SqlDBM: Microsoft SQL Server ******************
-- ******************************************************************
--TODO convert to postgresql for documentation
-- ************************************** [enclosure]

CREATE TABLE [enclosure]
(
 [enclosure_id]     int NOT NULL ,
 [title]            varchar(255) NOT NULL ,
 [length]           double(5,2) NULL ,
 [width]            double(5,2) NULL ,
 [height]           double(5,2) NULL ,
 [location]         varchar(255) NULL ,
 [dimension_units]  varchar(25) NULL ,
 [insert_timestamp] AS getdate() ,
 [update_timestamp] AS getdate() ,

 CONSTRAINT [PK_enclosure] PRIMARY KEY CLUSTERED ([enclosure_id] ASC)
);
GO








-- ************************************** [plant]

CREATE TABLE [plant]
(
 [plant_id]         int NOT NULL ,
 [enclosure_id]     int NOT NULL ,
 [name]             varchar(255) NOT NULL ,
 [pot_size]         int NULL ,
 [pot_size_units]   varchar(25) NULL ,
 [date_harvested]   datetime NULL ,
 [date_planted]     datetime NULL ,
 [yield]            double(5,2) NULL ,
 [yield_units]      varchar(25) NULL ,
 [insert_timestamp] AS getdate() ,
 [update_timestamp] AS getdate() ,

 CONSTRAINT [PK_plant] PRIMARY KEY CLUSTERED ([plant_id] ASC),
 CONSTRAINT [FK_113] FOREIGN KEY ([enclosure_id])  REFERENCES [enclosure]([enclosure_id])
);
GO


CREATE NONCLUSTERED INDEX [fkIdx_113] ON [plant]
 (
  [enclosure_id] ASC
 )

GO







-- ************************************** [sensor]

CREATE TABLE [sensor]
(
 [sensor_id]        int NOT NULL ,
 [plant_id]         int NULL ,
 [enclosure_id]     int NULL ,
 [type]             varchar(50) NOT NULL ,
 [is_enclosure]     bit NOT NULL ,
 [is_plant]         bit NOT NULL ,
 [insert_timestamp] AS getdate() ,
 [update_timestamp] AS getdate() ,

 CONSTRAINT [PK_sensor] PRIMARY KEY CLUSTERED ([sensor_id] ASC),
 CONSTRAINT [FK_134] FOREIGN KEY ([plant_id])  REFERENCES [plant]([plant_id]),
 CONSTRAINT [FK_137] FOREIGN KEY ([enclosure_id])  REFERENCES [enclosure]([enclosure_id])
);
GO


CREATE NONCLUSTERED INDEX [fkIdx_134] ON [sensor]
 (
  [plant_id] ASC
 )

GO

CREATE NONCLUSTERED INDEX [fkIdx_137] ON [sensor]
 (
  [enclosure_id] ASC
 )

GO







-- ************************************** [temp_humid_reading]

CREATE TABLE [temp_humid_reading]
(
 [temp_humid_reading_id] int NOT NULL ,
 [sensor_id]             int NOT NULL ,
 [temp_level]            decimal(5,2) NOT NULL ,
 [humidity]              decimal(5,2) NOT NULL ,
 [temp_scale]            varchar(1) NOT NULL ,
 [humidity_units]        varchar(20) NOT NULL ,
 [time_recorded]         datetime NOT NULL ,
 [insert_timestamp]      AS getdate() ,
 [update_timestamp]      AS getdate() ,

 CONSTRAINT [PK_temp_humid_reading] PRIMARY KEY CLUSTERED ([temp_humid_reading_id] ASC),
 CONSTRAINT [FK_146] FOREIGN KEY ([sensor_id])  REFERENCES [sensor]([sensor_id])
);
GO


CREATE NONCLUSTERED INDEX [fkIdx_146] ON [temp_humid_reading]
 (
  [sensor_id] ASC
 )

GO







-- ************************************** [soil_temp_reading]

CREATE TABLE [soil_temp_reading]
(
 [soil_temp_reading_id] int NOT NULL ,
 [sensor_id]            int NOT NULL ,
 [temp_level]           decimal(5,2) NOT NULL ,
 [temp_scale]           varchar(1) NOT NULL ,
 [time_recorded]        datetime NOT NULL ,
 [insert_timestamp]     AS getdate() ,
 [update_timestamp]     AS getdate() ,

 CONSTRAINT [PK_soil_temp_reading] PRIMARY KEY CLUSTERED ([soil_temp_reading_id] ASC),
 CONSTRAINT [FK_143] FOREIGN KEY ([sensor_id])  REFERENCES [sensor]([sensor_id])
);
GO


CREATE NONCLUSTERED INDEX [fkIdx_143] ON [soil_temp_reading]
 (
  [sensor_id] ASC
 )

GO







-- ************************************** [soil_moisture_reading]

CREATE TABLE [soil_moisture_reading]
(
 [soil_moisture_reading_id] int NOT NULL ,
 [sensor_id]                int NOT NULL ,
 [moisture_level]           decimal(5,2) NOT NULL ,
 [moisture_level_units]     varchar(25) NOT NULL ,
 [time_recorded]            datetime NOT NULL ,
 [insert_timestamp]         AS getdate() ,
 [update_timestamp]         AS getdate() ,

 CONSTRAINT [PK_soil_moisture_reading] PRIMARY KEY CLUSTERED ([soil_moisture_reading_id] ASC),
 CONSTRAINT [FK_140] FOREIGN KEY ([sensor_id])  REFERENCES [sensor]([sensor_id])
);
GO


CREATE NONCLUSTERED INDEX [fkIdx_140] ON [soil_moisture_reading]
 (
  [sensor_id] ASC
 )

GO







