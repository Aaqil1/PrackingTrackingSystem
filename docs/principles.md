# Application of SOLID, KISS, and YAGNI
- **Single Responsibility (SOLID)** – Each microservice encapsulates one domain concern (parking, routing, allocation, history, telemetry, collaboration). Within services, controllers handle HTTP concerns while services encapsulate business rules and repositories deal with persistence.
- **Open/Closed** – DTOs and services expose stable interfaces; extension (e.g., new prediction rules) can be achieved by adding service methods without modifying controller contracts.
- **Liskov & Interface Segregation** – REST endpoints expose narrow DTO contracts; clients only depend on required fields with validation guarding misuse.
- **Dependency Inversion** – Controllers depend on service interfaces (Spring-managed services) rather than repositories directly, enabling future substitution.
- **KISS** – Predictions and analytics use explainable heuristics (threshold-based occupancy and consumption detection) instead of heavy ML components to stay demo-friendly.
- **YAGNI** – No authentication, billing, or multi-tenant abstractions were added because they were not part of the PPT scope; configuration sticks to what is required for the described flows.
