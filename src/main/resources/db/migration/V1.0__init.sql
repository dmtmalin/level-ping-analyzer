CREATE TABLE ping_geo_data (
  id BIGINT,
  lat DOUBLE PRECISION NOT NULL,
  lng DOUBLE PRECISION NOT NULL,
  receive_at TIMESTAMP NOT NULL,
  send_at TIMESTAMP,

  CONSTRAINT pk_ping_geo_data PRIMARY KEY (id)
);
CREATE SEQUENCE ping_geo_data_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE INDEX idx_ping_geo_data_lat ON ping_geo_data USING btree (lat);
CREATE INDEX idx_ping_geo_data_lng ON ping_geo_data USING btree (lng);
CREATE INDEX idx_ping_geo_data_receive_at ON ping_geo_data USING btree (receive_at);