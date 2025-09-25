# Functional Requirements
1. The system shall manage multiple parking lots across cities, tracking slot size, occupancy, and availability in real time.
2. The system shall allocate arriving vehicles to slots based on the vehicle size and current occupancy.
3. The system shall release parking slots on vehicle departure and update occupancy metrics.
4. The system shall predict when additional parking slots are required based on occupancy thresholds.
5. The system shall provide routing recommendations to re-direct vehicles to the most suitable parking lot by city, area, distance, and supported slot size.
6. The system shall onboard vehicles with capacity details (volume and quantity) for allocation use.
7. The system shall automatically assign vehicles to goods requests based on capacity proportionality between goods and vehicles.
8. The system shall record job history entries with vehicle, goods volume, status, and timeline details.
9. The system shall expose past job records for review and drill-down.
10. The system shall record vehicle telemetry consumption metrics (e.g., coolant) including expected versus actual usage days.
11. The system shall flag telemetry consumption anomalies where actual usage falls materially below the expected baseline.
12. The system shall support branch driver swap planning, including origin, destination, trip dates, involved drivers, and saved wait-time hours.
13. The system shall summarise driver swap plans to highlight wait-time savings for upcoming periods.
14. The system shall expose REST APIs for all above capabilities and provide a React UI to operate them end-to-end.

# Non-Functional Requirements
1. The solution shall be deployable locally using Java 17+, Spring Boot microservices, and a React frontend.
2. Each microservice shall default to an in-memory H2 database and allow property-based overrides for production databases.
3. REST APIs shall validate inputs and return descriptive error messages with consistent error envelopes.
4. Services shall follow SOLID principles with clear separation of controllers, services, repositories, and DTOs.
5. The design shall adhere to KISS and YAGNI by implementing only the PPT scope with lightweight prediction rules and explainable heuristics.
6. The system shall seed reference data for immediate demo readiness.
7. The frontend shall communicate with backend services via configurable environment properties.
8. Code shall include unit or integration tests illustrating key service logic paths.
9. Documentation shall include architecture diagrams, API specifications, and operational instructions.
10. The stack shall run end-to-end in IntelliJ IDEA with minimal additional setup as per RUN instructions.
