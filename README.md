# orderControlSystemProject
Este es un proyecto demo para la gestion de ordenes de entrega de un sistema de logistica.

-  La razon principal del proyecto, es el manejo correcto de las ordenes de compras que cada cliente genere. Esto con el fin de poder mantener una correcta administracion de la data, asegurando su disponibilidad y su escalabilidad en caso que haya una gran demanda de clientes.

## El proyecto esta desarrollado con las siguientes tecnologias (Stack):
-   Java SpringBoot 3+
-   MongoDB
-   Redis
-   Kafka
-   Lombok
-   Map Struct
-   Maven

## Caracteristicas del Proyecto:
- El microservicio es capaz de realizar operaciones CRUD sobre las ordenes.
- Se utiliza MongoDB como base de datos.
- Se utiliza Redis para el cacheo de consultas frecuentes ( GET /orders/{id})
-   Se utiliza Kafka para la publicacion de eventos en cambios de estado (orders.events)
- La aplicacion se ejecuta en el puerto 8080

## Ejecutar el proyecto con Docker Compose
Es considerado la forma mas sencilla de ejecutar un servicio y todas sus dependencias.

**Requisitos**
- Tener instalado Docker
- Tener instalado Docker Compose

### Pasos:

1.  **Construir la aplicación (JAR):**

    ./mvnw clean package -DskipTests

2.  **Construir la imagen de Docker:**
    (Asegúrate de que el `Dockerfile` esté en la raíz del proyecto)

    docker build -t orderControlSystem:0.0.1-SNAPSHOT .


3.  **Levantar los servicios:**
    (Asegúrate de tener un archivo ".env.example" en la raíz)

    docker-compose up -d

## Ejecutar el proyecto de forma local
Si prefieres ejecutar el proyecto en tu equipo local, perfectamente podrias con los siguientes pasos:

**Requisitos**
-   Tener docker instalado
-   Tener Java instalado

### Pasos a seguir
1. **Levantar solo las dependencias**
    docker-compose up -d mongodb redis kafka zookeeper

2. **Verificar application.properties**
    Asegurar que las propiedades apunten a localhost

    spring.data.mongodb.host=localhost
    spring.data.redis.host=localhost
    spring.kafka.bootstrap-servers=localhost:9093

3. **Ejecuta la aplicacion**
    mvn spring-boot:run

## APIs Disponibles

- Endpoint `POST /orders` → crea una nueva orden.
- Endpoint `GET /orders/{id}` → obtiene una orden por ID.
- Endpoint `GET /orders?status=NEW&customerId=123` → lista órdenes filtradas.
- Endpoint `PATCH /orders/{id}/status` → cambia el estado y publica un evento en Kafka.

### Formas de uso

Para poder probar los endpoints disponibles, puedes utilizar la extension **ThunderClient** que esta disponible en Visual Studio Code.

***POST /orders**
Para ejecutar el POST /orders, colocamos en la barra de navegacion el URL localhost:8080/orders
Nos aseguramos que el protocolo a ejecutar sea POST
Luego nos redirigimos a la seccion de Body y adjuntamos la data como simulacion que viene de un frontend:

{
  "customerId": "cliente-002",
  "items": [
    { "sku": "SKU-AC-123", "quantity": 20, "price": 749.0 },
    { "sku": "SKU-BF-456", "quantity": 9, "price": 220.5 }
  ]
}

El endpoint retornara un codigo de status 201 junto con la info de la orden.



***GET /orders/{id}**

Para hacer uso del endpoint, cambiamos el protocolo a GET, colocamos en la barra de navegacion el URL localhost:8080/orders/60c9ba24-b9c9-43c4-90f4-499f07a1485d, el cual adjuntamos el codigo de orden que deseamos y le retornara codigo de status 200 con su contenido. Cabe mencionar que la data del endpoint, estara almacenada por 60 segundos en cache, es decir, si antes de los 60 segundos, la endpoint se vuelve a llamar, la data sera retornada desde cache y no desde la base de datos.

***GET /orders?status=NEW&customerId=123**

Para hacer uso del endpoint, nos aseguramos que el protocolo seleccionado sea GET, colocamos en la barra de navegacion el URL localhost:8080/orders?status=NEW&customerID=cliente-002, el cual podran notar que adjuntamos el status que queremos saber y a quien le perteneces y le retornara codigo de status 200 con su contenido.

***PATCH /orders/{id}/status**

Para hacer uso del endpoint, nos aseguramos que el protocolo seleccionado sea PATCH, colocamos en la barra de navegacion el URL localhost:8080/orders/60c9ba24-b9c9-43c4-90f4-499f07a1485d/status, luego vamos a la pestana de Body y adjuntamos este json como simulacion que dicho cambio viene desde un frontend:
{
  "newStatus": "IN_PROGRESS"
}

El endpoint retornara codigo de status 200 junto con la data actualizada, de igual forma se generar un evento en kafka y si dicha orden estaba en cache, sera invalidada ya que la data ha sido actualizada.