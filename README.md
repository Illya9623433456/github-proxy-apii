# GitHub Proxy API

A simple Spring Boot 4 proxy application built with Java 25 and Gradle (Kotlin DSL) to fetch non-fork GitHub repositories with their branches.

## Tech Stack
- Java 25
- Spring Boot 4.0.0-M1
- Gradle (Kotlin DSL)
- WireMock (for integration tests)

## Architecture
The application follows a simple Controller-Service-Client architecture packaged inside a single package, respecting visibility modifiers as requested. It uses `RestClient` for communicating with the GitHub v3 API.

## How to Run
To run the application locally, use the following command:
```bash
./gradlew bootRun

How to Test
Integration tests use WireMock to emulate the GitHub API without any mocking frameworks. To run tests:

./gradlew test

API Endpoint
GET /api/repositories/{username}

'''
Example Response (Success)
[
  {
    "repositoryName": "repo-one",
    "ownerLogin": "testuser",
    "branches": [
      {
        "name": "main",
        "lastCommitSha": "12345abcdef"
      }
    ]
  }
]
'''