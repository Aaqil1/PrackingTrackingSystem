# Parking Tracking System Design

## Overview
The platform orchestrates parking utilization, vehicle onboarding and routing, vehicle-to-goods allocation, job tracking, telemetry anomaly detection, and inter-branch driver collaboration. Each requirement from the source brief is mapped to explicit services, data flows, and UI capabilities within the monorepo.

## Requirement Mapping
| Requirement | Design Response |
| --- | --- |
| Utilize parking space effectively across lots in multiple cities | Parking Service models lots and slots with size-aware allocations and occupancy tracking. H2 stores seeded lots per geography, surfaced via React dashboards. |
| Re-route vehicles to the right parking spot | Routing Service evaluates candidate lots near the vehicle's current area, filtering by slot compatibility and capacity signals from the Parking Service dataset. |
| Parking slots of different sizes | `SlotSize` enum (SMALL, MEDIUM, LARGE) drives compatibility rules so larger vehicles can only take suitable slots, enforced on arrival workflows. |
| Arrival/departure allocation and space anticipation | Parking Service exposes `/arrival`, `/departure`, and `/prediction` endpoints. Prediction uses live occupancy ratio to flag when 75%+ capacity triggers a rental recommendation. |
| Predict need for additional slots to rent | Dashboard and API return `needMore` boolean when occupancy exceeds the configurable threshold, aligning with rental planning. |
| Vehicle onboarding | Allocation Service offers CRUD for vehicles, enabling the onboarding UI to persist fleet metadata such as dimensions and payload capacity. |
| Automatic allotment of vehicles based on goods volume/quantity | Allocation Service `/auto` endpoint evaluates onboarded fleet capacity to return the best-fit vehicle using proportional scoring between goods metrics and vehicle class. |
| Keep track of old job works | Job History Service stores completed logistics tasks with route, cargo, and cost details, exposed via list/detail UI. |
| Predict consumption & flag over-consumption (e.g., coolant in 15 vs 30 days) | Telemetry Service ingests expected vs. actual consumption intervals, producing anomaly flags when variance exceeds 20%, surfaced in the dashboard. |
| Collaboration for driver swapping, planned return trips, and wait-time savings | Driver Collaboration Service persists swap plans including shift start/end, connecting trip start, idle baselines, and hourly rates to compute saved hours and cost. React planner screen visualizes shift windows and savings. |

## Parking Allocation Scenario
1. **Arrival Trigger** – When a vehicle announces arrival, the Parking Service receives the vehicle size via `/parking/lots/{lotId}/arrival`.
2. **Slot Evaluation** – Available slots are filtered by `SlotSize.canFit(vehicleSize)` ensuring proportional space usage.
3. **Optimal Slot Selection** – Slots are ordered from smallest to largest viable size to keep larger slots free for heavier vehicles.
4. **Allocation & Persistence** – Slot occupancy flips to true in H2 via JPA, and the UI refreshes occupancy and predictive metrics.
5. **Departure & Release** – `/parking/lots/{lotId}/departure` releases the slot; occupancy recalculates, influencing predictions.
6. **Capacity Forecast** – `/parking/lots/{lotId}/prediction` returns occupancy percentage and rental recommendation. Dashboard cards highlight when extra slots should be rented.

## Driver Shift & Swap Scenario
1. **Shift Planning Inputs** – Branch managers capture swap details (origin/destination, drivers, shift start/end, return trip start, historic idle baseline, hourly rate) in the React planner.
2. **Server-side Optimization** – Driver Collaboration Service calculates actual idle gap from shift end to return trip, subtracts it from the baseline idle to compute saved wait hours, and multiplies by hourly rate to quantify monetary savings.
3. **Collaboration Record** – Persisted `DriverSwapPlan` retains shift windows, savings, and notes for cross-branch visibility.
4. **Return Trip Coordination** – Summary endpoint aggregates upcoming swaps for a date window, showing total hours and cost saved so dispatchers can assign connecting trips within driver shift limits.
5. **Shift Governance** – Stored shift start/end times let branches audit whether shifts stay within policy and adjust future allocations accordingly.

## Service Responsibilities
- **Parking Service** – Lot/slot CRUD, arrival/departure workflows, capacity prediction.
- **Routing Service** – Finds alternative lots based on city, distance, and slot suitability.
- **Allocation Service** – Maintains fleet metadata, performs goods-to-vehicle auto-allocation.
- **Job History Service** – Archives logistics jobs for accountability and analytics.
- **Telemetry Service** – Flags consumption anomalies using expected vs. actual cycle comparisons.
- **Driver Collaboration Service** – Plans driver swaps, shift schedules, wait-time and cost savings.

## Data Considerations
- H2 is default for rapid setup; properties support swap to production DBs.
- Entities align with diagrams in `/docs` to maintain traceability between design artifacts and implementation.
- Seed data primes dashboards and APIs for demos without manual setup.

## Guiding Principles in Practice
- **SOLID** – Services encapsulate single responsibilities (e.g., `ParkingManagementService` only handles parking logic), controllers stay thin with DTO validation.
- **KISS** – Rule-based heuristics (occupancy threshold, proportional allocation) avoid unnecessary ML complexity.
- **YAGNI** – No authentication/authorization or extra roles beyond demo flows; predictions use simple thresholds to satisfy scope without over-engineering.

## Frontend Integration
- React pages call dedicated Axios clients per service, aligning with backend boundaries.
- Global styling ensures forms and tables remain accessible while focusing on data clarity.
- `.env` provides backend base URL to simplify IntelliJ run configurations across environments.

## Operational Notes
- `RUN.md` documents service port map, start order, and sample curl commands for end-to-end testing.
- Postman collection mirrors REST contracts, including the enriched driver swap payload for shift planning and savings calculations.
- Unit tests cover key services (e.g., driver swap savings computation) to safeguard business rules.
