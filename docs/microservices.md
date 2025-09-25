# Microservices and Identification Rationale
- **Parking Service (8081)** – Derived from the requirement to manage multiple parking lots, track slots of varying sizes, allocate on arrival/departure, and predict extra capacity.
- **Routing Service (8082)** – Directly maps to re-routing vehicles to the right parking spot across cities and areas.
- **Allocation Service (8083)** – Covers vehicle onboarding and automatic vehicle allotment proportional to goods volume/quantity.
- **Job History Service (8084)** – Implements tracking of old job works with persistent history and retrieval endpoints.
- **Telemetry Service (8085)** – Handles vehicle consumption data to predict and flag over-consumption such as coolant depletion.
- **Driver Collaboration Service (8086)** – Manages branch collaboration for driver swapping, planning return trips, and calculating saved wait-time.

Each service encapsulates a distinct bullet from the PPT, ensuring single responsibility, smaller deployable units, and alignment with SOLID and KISS.
