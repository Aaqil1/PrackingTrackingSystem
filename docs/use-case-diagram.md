```mermaid
%%{init: {'theme': 'neutral'}}%%
usecaseDiagram
  actor FleetManager as "Fleet Operations Manager"
  actor YardSupervisor as "Yard Supervisor"
  actor Planner as "Logistics Planner"
  actor Analyst as "Maintenance Analyst"
  actor Coordinator as "Branch Coordinator"

  rectangle ParkingTrackingSystem {
    usecase UC1 as "Monitor parking lots"
    usecase UC2 as "Allocate slot on arrival"
    usecase UC3 as "Release slot on departure"
    usecase UC4 as "Predict slot requirements"
    usecase UC5 as "Re-route vehicles"
    usecase UC6 as "Onboard vehicle"
    usecase UC7 as "Auto-assign vehicle to goods"
    usecase UC8 as "Maintain job history"
    usecase UC9 as "Review telemetry"
    usecase UC10 as "Detect consumption anomaly"
    usecase UC11 as "Plan driver swap"
    usecase UC12 as "Summarise wait-time savings"
  }

  FleetManager --> UC1
  FleetManager --> UC4
  FleetManager --> UC5
  YardSupervisor --> UC2
  YardSupervisor --> UC3
  Planner --> UC6
  Planner --> UC7
  Planner --> UC8
  Analyst --> UC9
  Analyst --> UC10
  Coordinator --> UC11
  Coordinator --> UC12
```
