# Running the Parking Tracking System

## Prerequisites
- IntelliJ IDEA 2023.3+
- Java 17+
- Node.js 18+

## Backend (Spring Boot microservices)
1. Open IntelliJ IDEA and choose **Open**.
2. Select the `backend/pom.xml` file to import the Maven multi-module project.
3. After indexing, build the project once via **Maven** pane → `parking-tracking-backend` → **Lifecycle** → `clean` then `install`.
4. Start each service in the order below (run configurations are created automatically by IntelliJ on first run):
   - `parking-service` (port 8081)
   - `routing-service` (port 8082)
   - `allocation-service` (port 8083)
   - `job-history-service` (port 8084)
   - `telemetry-service` (port 8085)
   - `driver-collaboration-service` (port 8086)

   Each service seeds demo data on startup using the in-memory H2 database. To point a service to a different database, override the `spring.datasource.*` properties in the respective `application.properties` file.

## Frontend (React)
1. In a terminal: `cd frontend`.
2. Install dependencies: `npm install`.
3. Launch the UI: `npm run dev` (defaults to http://localhost:5173).
4. The `.env` file exposes `VITE_API_BASE_URL` so the UI connects to the backend running on localhost.

## Sample Data & Usage
- Parking lots, slots, vehicles, job records, telemetry metrics, and driver swap plans are pre-seeded for demos.
- A curated Postman collection is available at `docs/postman-collection.json`.
- Example curl calls:
  ```bash
  # Allocate a slot for a medium vehicle in lot 1
  curl -X POST http://localhost:8081/parking/lots/1/arrival \
       -H 'Content-Type: application/json' \
       -d '{"vehicleSize":"MEDIUM"}'

  # Request routing suggestion for Metropolis
  curl -X POST http://localhost:8082/routing/suggestions \
       -H 'Content-Type: application/json' \
       -d '{"currentCity":"Metropolis","vehicleSize":"MEDIUM","maximumDistanceKm":10}'

  # Auto-allot vehicle for goods
  curl -X POST http://localhost:8083/allocation/auto \
       -H 'Content-Type: application/json' \
       -d '{"goodsVolume":90,"goodsQuantity":100}'
  ```

## Tests
- Backend tests: `mvn test` (run from `backend` folder).
- Frontend lint/test scripts are not required for the scope; UI relies on Vite development server for quick feedback.
