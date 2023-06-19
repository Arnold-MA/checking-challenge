# API de Verificación de Vuelo - Documentación

Este proyecto es una API desarrollada en Spring Boot que proporciona un endpoint para obtener la lista de pasajeros de un vuelo específico. Utiliza una base de datos MySQL que contiene las tablas descritas a continuación.

## Tablas de la base de datos

El proyecto utiliza las siguientes tablas en la base de datos MySQL:

- `passengers`: Representa a los pasajeros y tiene una relación de uno a muchos con la tabla `boarding_pass`.
- `boarding_pass`: Representa los pases de abordar y se relaciona de muchos a uno con las tablas `purchase`, `seat_type`, `seat`, `flight` y `passenger`.
- `purchase`: Representa las compras y se relaciona de uno a muchos con la tabla `boarding_pass`.
- `seat_type`: Representa los tipos de asientos y se relaciona de uno a muchos con la tabla `boarding_pass` y `seat`.
- `seat`: Representa los asientos y se relaciona de uno a muchos con la tabla `boarding_pass` y de muchos a uno con las tablas `seat_type` y `airplane`.
- `airplane`: Representa los aviones y se relaciona de uno a muchos con las tablas `flight` y `seat`.
- `flight`: Representa los vuelos y se relaciona de uno a muchos con la tabla `boarding_pass` y de muchos a uno con la tabla `airplane`.

## Endpoint

El endpoint proporcionado por la API permite obtener la lista de pasajeros de un vuelo específico.

**URL del endpoint**: `https://checking-challenge-production.up.railway.app/flight/{id}/passengers`

- `{id}`: ID del vuelo para el cual se desea obtener la lista de pasajeros.

**Método HTTP**: GET

**Parámetros de consulta**:

- No se requieren parámetros de consulta adicionales.

**Respuesta**:

La respuesta de la API será en formato JSON y contendrá la lista de pasajeros del vuelo especificado. La estructura de la respuesta es la siguiente:

```json
{
  "code": 200,
  "data": {
    "flightId": 1,
    "takeoffDateTime": 1688207580,
    "takeoffAirport": "Aeropuerto Internacional Arturo Merino Benitez, Chile",
    "landingDateTime": 1688221980,
    "landingAirport": "Aeropuerto Internacional Jorge Chávez, Perú",
    "airplaneId": 1,
    "passengers": [
      {
        "passengerId": 90,
        "dni": 983834822,
        "name": "Marisol",
        "age": 44,
        "country": "México",
        "boardingPassId": 24,
        "purchaseId": 47,
        "seatTypeId": 1,
        "seatId": 1
      },
      { ... }
    ]
  }
}
```
En caso de que el vuelo especificado no exista o no tenga pasajeros, se devolverá una respuesta vacía ([]).

## Uso de la API

Para utilizar la API y obtener la lista de pasajeros de un vuelo, sigue los siguientes pasos:

1. Realiza una solicitud GET a la URL del endpoint especificando el ID del vuelo en la ruta.
   Ejemplo: `https://checking-challenge-production.up.railway.app/flight/1/passengers`
   (Reemplaza "1" con el ID del vuelo que quieres ver).

2. La API procesará la solicitud y devolverá la lista de pasajeros del vuelo en formato JSON.

3. Analiza la respuesta de la API para obtener los datos de los pasajeros.
