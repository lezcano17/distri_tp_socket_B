CREATE TABLE cambio
(
  id_entidad integer NOT NULL,
  cambio_venta float8,
  cambio_compra float8,
  CONSTRAINT pk_entidad PRIMARY KEY (id_entidad)
);