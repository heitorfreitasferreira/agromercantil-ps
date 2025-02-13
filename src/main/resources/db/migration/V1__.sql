CREATE TABLE truck_model
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    license_plate      VARCHAR(255)                            NOT NULL,
    brand              VARCHAR(255)                            NOT NULL,
    model              VARCHAR(255)                            NOT NULL,
    manufacturing_year INT                                     NOT NULL,
    fipe_price         DOUBLE PRECISION                        NOT NULL,
    CONSTRAINT pk_truckmodel PRIMARY KEY (id)
);

ALTER TABLE truck_model
    ADD CONSTRAINT uc_truckmodel_licenseplate UNIQUE (license_plate);