## Template Quarkus

### About application

- Quarkus rest API
- Openapi
- Schedule
- Panache mongo repository
- Loggin filter
- Tests
- Native image

### Get up

```bash
mvn clean compile quarkus:dev
```


### Endpoint do swagger

```bash
http://localhost:8080/swagger
```


curl -X POST http://localhost:8080/v1/users -H "Content-Type: application/json" -d '{ "bio": "string", "blog": "string", "data": "2025-11-03", "location": "string", "login": "marcelbb", "name": "string" }'