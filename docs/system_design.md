# Pracking Tracking System – Architecture & Design Artifacts

## Requirements Capturing

### Functional Requirements (FR)
| ID | Requirement | Rationale |
|----|-------------|-----------|
| FR-1 | Users shall register and authenticate to access personalized tracking dashboards. | Ensures secure access to tracking information. |
| FR-2 | Customers shall create, update, and archive shipment records. | Maintains comprehensive tracking information. |
| FR-3 | The system shall provide real-time status updates for each shipment. | Enables transparency and customer trust. |
| FR-4 | Users shall subscribe to notification preferences (email/SMS/push). | Supports proactive communication. |
| FR-5 | Operations staff shall manage exceptions (lost, delayed, damaged). | Supports issue resolution workflows. |
| FR-6 | The system shall expose APIs for third-party logistics partners. | Facilitates ecosystem integrations. |
| FR-7 | Administrators shall view analytics dashboards for performance KPIs. | Supports data-driven decision making. |

### Non-Functional Requirements (NFR)
| ID | Requirement | Target |
|----|-------------|--------|
| NFR-1 | Availability | 99.5% monthly uptime |
| NFR-2 | Performance | < 2 seconds average response time under 500 concurrent users |
| NFR-3 | Scalability | Horizontal scaling supported via container orchestration |
| NFR-4 | Security | Compliance with OWASP ASVS Level 2 and encrypted data at rest |
| NFR-5 | Observability | Centralized logging, distributed tracing, and metric dashboards |
| NFR-6 | Maintainability | Modular microservice architecture with automated CI/CD |
| NFR-7 | Privacy | GDPR-compliant consent and data retention policies |

## Actors
| Actor | Description |
|-------|-------------|
| Customer | Tracks shipments, manages notifications, and views history. |
| Operations Agent | Handles exceptions, resolves issues, and updates shipment metadata. |
| Logistics Partner | Pushes carrier status updates via partner APIs/webhooks. |
| Administrator | Configures services, monitors KPIs, and manages user roles. |
| Notification Provider | External gateway that delivers SMS/email/push alerts. |

## Sequence Diagram – Real-time Tracking Update
```mermaid
sequenceDiagram
    autonumber
    participant C as Customer
    participant UI as Web App
    participant TS as Tracking Service
    participant ES as Event Stream
    participant NS as Notification Service
    C->>UI: Request shipment status
    UI->>TS: GET /shipments/{trackingId}
    TS->>ES: Subscribe to status events
    ES-->>TS: Push carrier update event
    TS-->>UI: Return status + estimated delivery
    TS->>NS: Publish notification (if subscribed)
    NS-->>C: Deliver status alert
```

## Use-case Diagram
```mermaid
graph LR
    Customer((Customer))
    Ops((Operations Agent))
    Admin((Administrator))
    Partner((Logistics Partner))
    UC1[(Track Shipment)]
    UC2[(Manage Notifications)]
    UC3[(Resolve Exceptions)]
    UC4[(Integrate Carrier Updates)]
    UC5[(View Analytics)]
    Customer --> UC1
    Customer --> UC2
    Ops --> UC3
    Partner --> UC4
    Admin --> UC5
    Admin --> UC3
    Customer --> UC3
```

## Microservices Landscape
| Service | Responsibility | Identification Rationale |
|---------|----------------|--------------------------|
| Identity Service | Authentication, authorization, role management. | Security concerns are isolated and reusable across channels. |
| Tracking Service | Stores shipment data, aggregates carrier events, exposes tracking APIs. | Core domain logic benefiting from independent scaling. |
| Notification Service | Manages user preferences and dispatches alerts via providers. | High I/O workload suited for asynchronous processing. |
| Exception Management Service | Handles incident workflows, tasks, and audit logs. | Business-critical but distinct workflow requiring specialized persistence. |
| Analytics Service | ETL pipeline feeding dashboards and KPI reports. | Compute-intensive workloads decoupled from transactional systems. |
| Partner Integration Gateway | Normalizes carrier APIs/webhooks into internal event format. | Integrations change frequently; isolation reduces blast radius. |

## API Specification (Representative Endpoints)
| Method & Path | Description | Request Schema | Response Schema |
|---------------|-------------|----------------|-----------------|
| `POST /api/v1/auth/login` | Authenticate a user. | `{ "email": string, "password": string }` | `{ "token": string, "expiresIn": number }` |
| `GET /api/v1/shipments/{trackingId}` | Retrieve shipment status. | Path param `trackingId: string` | `{ "trackingId": string, "status": string, "eta": string, "events": Event[] }` |
| `POST /api/v1/shipments` | Create a new shipment record. | `{ "sender": Party, "recipient": Party, "route": Route }` | `{ "trackingId": string, "createdAt": string }` |
| `PATCH /api/v1/notifications/preferences` | Update notification preferences. | `{ "channels": string[], "quietHours": string }` | `{ "success": boolean }` |
| `POST /api/v1/exceptions` | Log a shipment exception. | `{ "trackingId": string, "type": string, "notes": string }` | `{ "exceptionId": string, "status": string }` |
| `GET /api/v1/analytics/kpi` | Fetch KPI snapshot. | Query params `from`, `to`. | `{ "onTimeRate": number, "avgDelivery": number }` |

## Class Diagram
```mermaid
classDiagram
    class User {
        +UUID id
        +string name
        +string email
        +Role role
    }
    class Shipment {
        +UUID trackingId
        +Address sender
        +Address recipient
        +Status status
        +List~Event~ events
        +Date eta
    }
    class Event {
        +Date timestamp
        +string location
        +string description
        +Status status
    }
    class NotificationPreference {
        +UUID userId
        +List~Channel~ channels
        +TimeRange quietHours
    }
    class ExceptionCase {
        +UUID caseId
        +UUID trackingId
        +string type
        +string notes
        +CaseStatus status
    }
    User "1" -- "1" NotificationPreference
    Shipment "1" -- "*" Event
    Shipment "1" -- "*" ExceptionCase
    User "1" -- "*" Shipment : manages
```

## Flow Chart – Shipment Tracking Request
```mermaid
flowchart TD
    A[Start] --> B{Authenticated?}
    B -- No --> C[Redirect to Login]
    C --> B
    B -- Yes --> D[Enter Tracking ID]
    D --> E[Call Tracking Service API]
    E --> F{Status found?}
    F -- No --> G[Display not found message]
    F -- Yes --> H[Render status + ETA]
    H --> I[Offer notification subscription]
    I --> J[End]
```

## State Diagram – Shipment Lifecycle
```mermaid
stateDiagram-v2
    [*] --> Created
    Created --> InTransit : Carrier pickup
    InTransit --> OutForDelivery : Local distribution
    OutForDelivery --> Delivered : Proof of delivery
    InTransit --> Exception : Delay/damage reported
    Exception --> Resolved : Issue handled
    Resolved --> OutForDelivery
    Delivered --> [*]
```

## Activity Diagram – Exception Handling Workflow
```mermaid
flowchart TD
    start((Start)) --> identify[Identify anomaly]
    identify --> log[Log exception case]
    log --> assign[Assign to operations agent]
    assign --> investigate[Investigate root cause]
    investigate --> decision{Carrier input required?}
    decision -- Yes --> requestInfo[Request carrier details]
    requestInfo --> updateCase[Update case with findings]
    decision -- No --> updateCase
    updateCase --> notify[Notify customer]
    notify --> close{Issue resolved?}
    close -- No --> investigate
    close -- Yes --> end((End))
```

## Object Diagram – Sample Runtime Instances
```mermaid
classDiagram
    class Shipment_1234 {
        <<object>>
        trackingId = "PKG1234"
        status = "OutForDelivery"
        eta = "2024-05-12"
    }
    class Event_1 {
        <<object>>
        timestamp = "2024-05-10T09:00Z"
        location = "Warehouse"
        status = "InTransit"
    }
    class Event_2 {
        <<object>>
        timestamp = "2024-05-12T07:30Z"
        location = "Local Facility"
        status = "OutForDelivery"
    }
    class ExceptionCase_77 {
        <<object>>
        caseId = "EXC77"
        type = "Delay"
        status = "Resolved"
    }
    class User_Alice {
        <<object>>
        userId = "USR001"
        role = "Customer"
    }
    Shipment_1234 -- Event_1
    Shipment_1234 -- Event_2
    Shipment_1234 -- ExceptionCase_77
    User_Alice -- Shipment_1234
```

## ER Diagram
```mermaid
erDiagram
    USER ||--o{ SHIPMENT : manages
    SHIPMENT ||--o{ EVENT : logs
    SHIPMENT ||--o{ EXCEPTION_CASE : raises
    USER ||--|| NOTIFICATION_PREFERENCE : configures
    SHIPMENT }o--o{ PARTNER_UPDATE : ingests
    EVENT }o--|| PARTNER : generated_by
```

## Guiding Principles Applied
- **SOLID**: Services encapsulate single responsibilities (e.g., Tracking Service) and interfaces (APIs) favor dependency inversion via message queues and contracts.
- **KISS**: Adopted straightforward event-driven patterns and well-defined APIs to reduce complexity in distributed workflows.
- **YAGNI**: Deferred advanced routing optimizations and machine-learning predictions until driven by validated requirements.

## Prompt Used & Technique
- **Prompt**: "Design a microservices-based Package Tracking System and provide textual UML/mermaid diagrams for requirements, actors, sequence, use-case, APIs, and data models."
- **Technique**: Applied *Structured Prompting* by listing explicit artifacts needed, ensuring comprehensive coverage before content generation.
