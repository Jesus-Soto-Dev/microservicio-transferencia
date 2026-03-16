# Transferencias Service — Proyecto de Aprendizaje

## Descripción

Microservicio de ejemplo desarrollado con fines formativos. **No contiene datos, credenciales ni información perteneciente a alguna empresa.**

## Stack tecnológico

- Java 17 + Spring Boot 3.2.x
- Arquitectura Hexagonal + DDD
- Apache Camel 4.x
- Apache CXF — cliente SOAP desde WSDL simulado
- JPA + H2
- JUnit 5 + Mockito

## Aclaraciones importantes

Este proyecto es únicamente un ejercicio de aprendizaje personal.

- El WSDL es simulado y no corresponde a ningún sistema real
- La URL del sistema legado apunta a un entorno local inexistente
- No se han utilizado datos, contratos ni arquitecturas reales de la empresa
- La base de datos es H2 en memoria, sin persistencia real

## Cómo ejecutar

```bash
mvn generate-sources
mvn spring-boot:run
mvn test
```
