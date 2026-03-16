Transferencias Service — Proyecto de Aprendizaje
Descripción
Microservicio de ejemplo desarrollado con fines formativos durante el período de prácticas. No contiene datos, credenciales ni información perteneciente a la empresa.
El escenario simula una caja rural que necesita procesar transferencias entre cuentas, consultando un sistema bancario legado mediante SOAP/WSDL y persistiendo el resultado en base de datos.

Stack tecnológico

Java 17 + Spring Boot 3.2.x
Arquitectura Hexagonal + DDD — dominio desacoplado de la infraestructura
Apache Camel 4.x — orquestación de llamadas al sistema legado con reintentos y manejo de errores
Apache CXF — cliente SOAP generado automáticamente desde WSDL simulado
JPA + H2 — persistencia en base de datos en memoria
JUnit 5 + Mockito — tests unitarios por capas


Aclaraciones importantes

Este proyecto es únicamente un ejercicio de aprendizaje personal.

El WSDL es simulado y no corresponde a ningún sistema real
La URL del sistema legado (banco.soap.url) apunta a un entorno local inexistente
No se han utilizado ni replicado datos, contratos, WSDLs ni arquitecturas reales de la empresa
La base de datos es H2 en memoria, sin persistencia real



Estructura del proyecto
src/main/java/com/cajarural/transferencias/
├── domain/              ← lógica de negocio pura, sin frameworks
│   ├── model/
│   └── port/
├── application/         ← casos de uso
└── infrastructure/      ← adaptadores (REST, Camel, CXF, JPA)
    ├── in/rest/
    └── out/
        ├── soap/
        └── persistence/

Cómo ejecutar
bash# Generar clases desde el WSDL
mvn generate-sources

# Arrancar la aplicación
mvn spring-boot:run

# Ejecutar tests
mvn test

# Consola H2 (base de datos en memoria)
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:transferencias