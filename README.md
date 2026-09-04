# Java Backend API Feature Demo

Hi — I am Md Tanvir Alam ([tanvir-ux](https://github.com/tanvir-ux)). This repo is a **demo Spring Boot skeleton** I put together for the Freelancer brief [Java Backend API Feature Development](https://www.freelancer.com/projects/api-development/Java-Backend-API-Feature-Development/details): a clean REST layout with fetch + submit style endpoints, controller / service / repository layers, JPA, JSON, HTTP status codes, and a couple of JUnit tests.

It is not the client's production feature. It shows how I structure the work so real tables and rules can map onto the same layers quickly.

## What you get

- Spring Boot 3.3 / Java 17 / Maven
- Layered packages: `api`, `service`, `repository`, `domain`, `config`
- Demo **Item** resource:
  - `GET /api/items` — fetch (list)
  - `POST /api/items` — submit (create, `201 Created`)
  - plus get by id, update, delete with proper status codes
- JPA + in-memory **H2** by default (local run with zero DB setup)
- Optional **MySQL** profile + `application-mysql.yml.example`
- Spring Security that runs out of the box (HTTP Basic `demo` / `demo`; optional JWT stub profile)
- 1–2 simple unit tests for the service layer

## How to run

```bash
./mvnw spring-boot:run
```

Try the demo feature:

```bash
# public health
curl -s http://localhost:8080/api/health

# fetch (list)
curl -u demo:demo -s http://localhost:8080/api/items

# submit (create)
curl -u demo:demo -s -X POST http://localhost:8080/api/items \
  -H 'Content-Type: application/json' \
  -d '{"name":"Feature payload","details":"Mirrors client submit","status":"SUBMITTED"}'
```

Package / tests:

```bash
./mvnw -q test
./mvnw -q -DskipTests package
```

### MySQL (optional)

Default stays on H2. For MySQL, see `src/main/resources/application-mysql.yml.example`, set credentials, then:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=mysql
```

## Layout

```
com.tanvir.featuredemo
├── FeatureDemoApplication
├── api/          # REST controllers + exception handling
├── config/       # Security, JWT stub, data seeder
├── domain/       # JPA entities (Item)
├── service/      # Business logic
└── repository/   # Spring Data JPA
```

## Mapping to a real brief

On a live engagement I would replace `Item` with the client's entities, point the datasource at their MySQL schema, keep the same controller → service → repository split, and add validation / status codes that match their API contract. Auth would move from the Basic/JWT stub to whatever they already use.

## Default credentials (local only)

| User | Password |
|------|----------|
| demo | demo |

Do not ship these. Turn on real auth for anything beyond a local demo.

---

GitHub: [tanvir-ux](https://github.com/tanvir-ux)
