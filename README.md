## distri_tp_socket_B

### Integrantes
- Pedro Lesme
- Julio Lezcano
- Arami Nuñez
- Matias Miranda

### Requerimientos de instalación
Descargar las dependencias de Maven

### Cómo crear la estructura de Base de datos.
Ejecutar los scritps 00_crear_base.sql y 01_crear_bd.sql


### Cómo poblar los datos iniciales necesarios de Base de datos.\
Los datos iniciales necesarios se generan al ejecutar el script 01_crear_bd.sql

## Documentación de un API de servicios ofrecidos por el Servidor.
Los servicios ofrecidos por el servidor serian:
- registrar_consumo : para invocar este servicio se necesita el id del NIS asi como su estado
- desconexion_suministro : para invocar este servicio se necesita el id del NIS, asi como el estado inactivo al cual se desea cambiar
- conexion_suministro: para invocar este servicio se necesita el id del NIS, asi como el estado activo al cual se desea cambiar
- lista_activos: para este servicio se necesita el estado activo para poder invocar la lista 
- ista_inactivos : para este servicio se necesita el estado inactivo para poder invocar la lista


### Cómo compilar y ejecutar los componentes de cada servidor.
Primera mente se ejecuta el servidor que seria el archivo UDPServer que estaria en el proyecto
servidor, luego de que este listo se debe ejecutar el cliente que seria el archivo
UDPClient que estaria en el proyecto cliente.


