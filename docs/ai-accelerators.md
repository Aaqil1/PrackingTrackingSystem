# AI Accelerators Usage
| Task | AI Prompt | Technique | Outcome |
| --- | --- | --- | --- |
| Drafting FR/NFR | "Interpret PPT scope verbatim into functional/non-functional requirements" | Chain-of-thought outline | Produced requirements list aligned with PPT bullets. |
| Service boundary proposal | "Group PPT capabilities into microservices with single responsibilities" | Tree-of-thought | Generated six-service architecture mapping each bullet to a service. |
| Diagram generation | "Describe flows (parking allocation, routing, auto-allotment, driver swap, telemetry) for Mermaid diagrams" | Role prompting | Created consistent diagrams captured in `/docs`. |
| OpenAPI drafting | "For each microservice, expand DTOs into OpenAPI operations" | Chain-of-thought outline | Delivered OpenAPI YAML files for every service. |
| Test scaffolding | "Suggest minimal unit test per service highlighting core rule" | Few-shot reasoning | Added Spring Boot tests covering allocation, routing, telemetry, parking, job history, driver collaboration. |
| KISS/YAGNI review | "Verify no extra features beyond PPT, emphasise heuristic predictions" | Critique-and-revise | Ensured rules stay lightweight (threshold-based predictions) and avoided out-of-scope features. |
