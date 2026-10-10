# Enterprise RAG

A Spring Boot + Spring AI service that exposes a chat endpoint, built as the foundation for a
Retrieval-Augmented Generation (RAG) pipeline.

> **Current status:** This project today is a stateless LLM chat API. The retrieval layer
> (document ingestion, embeddings, vector store) is intentionally on the roadmap — the "generation"
> half of RAG is wired up and working, the "retrieval" half is next.

## Why this matters (AI perspective)

Large language models answer from their training data, which is frozen, generic, and sometimes wrong.
**RAG** grounds an LLM's answers in your own private/enterprise data at query time:

- **Less hallucination** — answers are backed by retrieved source documents.
- **Fresh knowledge** — update a document, not the model; no retraining required.
- **Attributable** — retrieved chunks can be cited, so users can verify the source.

This repo implements the *generation* side: a request goes to an LLM through Spring AI's
`ChatClient` with a controlled system prompt. The remaining work is to retrieve relevant context
and inject it into that prompt before generation.

## Architecture

```
POST /api/chat
      │  { "question": "..." }
      ▼
ChatController  (validation, HTTP layer)
      ▼
ChatService     (system prompt + LLM call)
      ▼
ChatClient  ──►  OpenAI  gpt-4o-mini
      │
      ▼
{ "answer": "..." }
```

The system prompt defining the assistant's behavior lives in `ChatService.java`.

## Tech stack

| Layer      | Technology                      |
|------------|---------------------------------|
| Language   | Java 21                         |
| Framework  | Spring Boot 4.1.1               |
| AI         | Spring AI 2.0.0 (OpenAI starter)|
| Model      | `gpt-4o-mini`                   |
| Build      | Maven                           |
| Testing    | JUnit 5 + Mockito               |

## Getting started

**Prerequisites:** JDK 21, Maven (or use the included wrapper), and an OpenAI API key.

Set the API key (read from `application.yaml`):

```bash
export OPENAI_API_KEY=sk-...
```

Run the service (listens on port `8080`):

```bash
./mvnw spring-boot:run
```

Run the tests:

```bash
./mvnw test
```

## API reference

### `POST /api/chat`

| Field      | Type   | Required | Description              |
|------------|--------|----------|--------------------------|
| `question` | string | yes      | The user's question.     |

Response: `{ "answer": "<model response>" }`

```bash
curl -X POST http://localhost:8080/api/chat \
  -H "Content-Type: application/json" \
  -d '{"question": "What is RAG?"}'
```

```json
{ "answer": "RAG combines retrieval and generation..." }
```

## Testing

- `ChatControllerTest` — unit test of the HTTP layer with a mocked `ChatService` (no network calls).
- `EnterpriseRagApplicationTests` — verifies the Spring context loads.

## Roadmap (RAG)

1. **Document ingestion** — load and chunk enterprise documents (loaders + splitters).
2. **Embeddings + vector store** — embed chunks and store them (e.g. in-memory, pgvector, or Chroma).
3. **Retrieval wiring** — add a retrieval advisor so relevant chunks are injected into the prompt.
4. **Citations & memory** — return source references and support multi-turn conversations.

## Project layout

```
src/main/java/com/portfolio/ai/enterpriserag/
├── EnterpriseRagApplication.java   # Spring Boot entry point
├── ChatController.java             # POST /api/chat
└── ChatService.java                # system prompt + ChatClient call
src/main/resources/application.yaml # model + API key configuration
src/test/java/...                   # controller + context tests
```

## Author

<!-- Add your name, GitHub, and portfolio links here. -->
