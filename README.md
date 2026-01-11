# 일정 관리 프로젝트 (Todo Management API)

Spring Boot 기반의 RESTful API 일정 관리 시스템입니다. 사용자는 회원가입 및 로그인을 통해 자신의 일정을 생성, 조회, 수정, 삭제할 수 있으며, 각 일정에 댓글을 작성할 수 있습니다.

## 📋 목차
- [기술 스택](#-기술-스택)
- [주요 기능](#-주요-기능)
- [ERD 구조](#-erd-구조)
- [API 명세](#-api-명세)
- [프로젝트 구조](#-프로젝트-구조)
- [시작하기](#-시작하기)
- [환경 설정](#-환경-설정)

## 🛠 기술 스택

### Backend
- **Java 17**
- **Spring Boot 4.0.1**
- **Spring Data JPA** - ORM 및 데이터베이스 접근
- **Spring Web MVC** - RESTful API 구현
- **Spring Validation** - 요청 데이터 검증

### Database
- **MySQL** - 메인 데이터베이스

### Security
- **BCrypt** (at.favre.lib:bcrypt:0.10.2) - 비밀번호 암호화

### Build Tool
- **Gradle**

### 기타
- **Lombok** - 보일러플레이트 코드 감소
- **JPA Auditing** - 생성일시/수정일시 자동 관리

## ✨ 주요 기능

### 1. 사용자 관리 (User)
- 회원가입 (비밀번호 암호화)
- 로그인/로그아웃 (세션 기반 인증)
- 사용자 정보 조회 (단건/전체)
- 사용자 정보 수정
- 회원 탈퇴

### 2. 일정 관리 (Todo)
- 일정 생성
- 일정 조회 (단건/전체)
- 일정 수정
- 일정 삭제
- 사용자별 일정 필터링

### 3. 댓글 관리 (Comment)
- 댓글 작성
- 댓글 조회 (단건/전체)
- 댓글 수정
- 댓글 삭제
- 일정별 댓글 필터링

### 4. 공통 기능
- 생성일시/수정일시 자동 관리 (JPA Auditing)
- 전역 예외 처리 (GlobalExceptionHandler)
- Validation을 통한 요청 데이터 검증

## 🗂 ERD 구조

```
┌─────────────────┐
│     users       │
├─────────────────┤
│ id (PK)         │
│ username        │
│ email (UNIQUE)  │
│ password        │
│ created_at      │
│ modified_at     │
└────────┬────────┘
         │ 1
         │
         │ N
┌────────┴────────┐
│     todos       │
├─────────────────┤
│ id (PK)         │
│ title           │
│ content         │
│ user_id (FK)    │
│ created_at      │
│ modified_at     │
└────────┬────────┘
         │ 1
         │
         │ N
┌────────┴────────┐
│    comments     │
├─────────────────┤
│ id (PK)         │
│ author          │
│ content         │
│ todo_id (FK)    │
│ created_at      │
│ modified_at     │
└─────────────────┘
```

### 테이블 관계
- **User : Todo = 1 : N** - 한 사용자는 여러 일정을 가질 수 있습니다.
- **Todo : Comment = 1 : N** - 한 일정은 여러 댓글을 가질 수 있습니다.

## 📡 API 명세

### 인증 API

| Method | Endpoint | Description | Request Body | Response Status |
|--------|----------|-------------|--------------|-----------------|
| POST | `/login` | 로그인 | `{ "email", "password" }` | 200 OK |
| POST | `/logout` | 로그아웃 | - | 204 No Content |

### 사용자 API

| Method | Endpoint | Description | Request Body | Response Status |
|--------|----------|-------------|--------------|-----------------|
| POST | `/users` | 회원가입 | `{ "username", "email", "password" }` | 201 Created |
| GET | `/users/{userId}` | 사용자 단건 조회 | - | 200 OK |
| GET | `/users` | 전체 사용자 조회 | - | 200 OK |
| PUT | `/users/{userId}` | 사용자 정보 수정 | `{ "username", "email", "password" }` | 200 OK |
| DELETE | `/users/{userId}` | 회원 탈퇴 | - | 200 OK |

### 일정 API

| Method | Endpoint | Description | Request Body | Response Status |
|--------|----------|-------------|--------------|-----------------|
| POST | `/users/{userId}/todos` | 일정 생성 | `{ "title", "content" }` | 201 Created |
| GET | `/users/{userId}/todos/{todoId}` | 일정 단건 조회 | - | 200 OK |
| GET | `/users/{userId}/todos` | 사용자별 일정 전체 조회 | - | 200 OK |
| PUT | `/users/{userId}/todos/{todoId}` | 일정 수정 | `{ "title", "content" }` | 200 OK |
| DELETE | `/users/{userId}/todos/{todoId}` | 일정 삭제 | - | 204 No Content |

### 댓글 API

| Method | Endpoint | Description | Request Body | Response Status |
|--------|----------|-------------|--------------|-----------------|
| POST | `/users/{userId}/todos/{todoId}/comments` | 댓글 작성 | `{ "author", "content" }` | 201 Created |
| GET | `/users/{userId}/todos/{todoId}/comments/{commentId}` | 댓글 단건 조회 | - | 200 OK |
| GET | `/users/{userId}/todos/{todoId}/comments` | 일정별 댓글 전체 조회 | - | 200 OK |
| PUT | `/users/{userId}/todos/{todoId}/comments/{commentId}` | 댓글 수정 | `{ "content" }` | 200 OK |
| DELETE | `/users/{userId}/todos/{todoId}/comments/{commentId}` | 댓글 삭제 | - | 204 No Content |

### Validation 규칙

#### 사용자 (User)
- `username`: 필수 입력
- `email`: 필수 입력, 이메일 형식, 중복 불가
- `password`: 필수 입력

#### 일정 (Todo)
- `title`: 필수 입력, 최대 20자
- `content`: 필수 입력, 최대 200자

#### 댓글 (Comment)
- `author`: 필수 입력, 최대 20자
- `content`: 필수 입력, 최대 100자

## 📁 프로젝트 구조

```
src/main/java/com/example/todo/
├── config/
│   ├── GlobalExceptionHandler.java    # 전역 예외 처리
│   └── PasswordEncoder.java           # 비밀번호 암호화
├── customErrors/
│   ├── ServerException.java           # 예외 최상위 클래스
│   ├── UserNotFoundException.java
│   ├── TodoNotFoundException.java
│   ├── CommentNotFoundException.java
│   └── PasswordMismatchException.java
├── todo/
│   ├── controller/
│   │   ├── UserController.java
│   │   ├── TodoController.java
│   │   ├── CommentController.java
│   │   └── TestController.java
│   ├── service/
│   │   ├── UserService.java
│   │   ├── TodoService.java
│   │   └── CommentService.java
│   ├── repository/
│   │   ├── UserRepository.java
│   │   ├── TodoRepository.java
│   │   └── CommentRepository.java
│   ├── entity/
│   │   ├── BaseEntity.java           # 생성/수정 시간 추상 클래스
│   │   ├── User.java
│   │   ├── Todo.java
│   │   └── Comment.java
│   └── dtos/
│       ├── userDtos/
│       │   ├── CreateUserRequest.java
│       │   ├── CreateUserResponse.java
│       │   ├── GetUserResponse.java
│       │   ├── UpdateUserRequest.java
│       │   └── UpdateUserResponse.java
│       ├── todoDtos/
│       │   ├── CreateTodoRequest.java
│       │   ├── CreateTodoResponse.java
│       │   ├── GetTodoResponse.java
│       │   ├── UpdateTodoRequest.java
│       │   └── UpdateTodoResponse.java
│       ├── commentDtos/
│       │   ├── CreateCommentRequest.java
│       │   ├── CreateCommentResponse.java
│       │   ├── GetCommentResponse.java
│       │   ├── UpdateCommentRequest.java
│       │   └── UpdateCommentResponse.java
│       └── loginDtos/
│           ├── LoginRequest.java
│           ├── LoginResponse.java
│           └── SessionUser.java
```

## 🚀 시작하기

### 사전 요구사항
- Java 17 이상
- MySQL 8.0 이상
- Gradle

### 설치 및 실행

1. **저장소 클론**
```bash
git clone 
cd todo
```

2. **MySQL 데이터베이스 생성**
```sql
CREATE DATABASE todos;
```


3. **프로젝트 빌드 및 실행**
```bash
./gradlew build
./gradlew bootRun
```

서버는 기본적으로 `http://localhost:8080`에서 실행됩니다.


### DDL 자동 생성 모드
- **개발 환경**: `create` 또는 `update`
- **운영 환경**: `validate` 또는 `none` 권장

## 📝 API 사용 예시

### 1. 회원가입
```http
POST /users
Content-Type: application/json

{
  "username": "홍길동",
  "email": "hong@example.com",
  "password": "password123"
}
```

**Response (201 Created)**
```json
{
  "id": 1,
  "username": "홍길동",
  "email": "hong@example.com",
  "createdAt": "2024-01-15T10:30:00",
  "modifiedAt": "2024-01-15T10:30:00"
}
```

### 2. 로그인
```http
POST /login
Content-Type: application/json

{
  "email": "hong@example.com",
  "password": "password123"
}
```

**Response (200 OK)**
```json
{
  "id": 1,
  "email": "hong@example.com"
}
```

### 3. 일정 생성
```http
POST /users/1/todos
Content-Type: application/json

{
  "title": "Spring Boot 공부",
  "content": "JPA와 REST API 학습하기"
}
```

**Response (201 Created)**
```json
{
  "id": 1,
  "title": "Spring Boot 공부",
  "content": "JPA와 REST API 학습하기",
  "createdAt": "2024-01-15T11:00:00"
}
```

### 4. 댓글 작성
```http
POST /users/1/todos/1/comments
Content-Type: application/json

{
  "author": "김철수",
  "content": "화이팅하세요!"
}
```

**Response (201 Created)**
```json
{
  "id": 1,
  "author": "김철수",
  "content": "화이팅하세요!",
  "createdAt": "2024-01-15T11:30:00"
}
```

### 5. 일정 조회
```http
GET /users/1/todos/1
```

**Response (200 OK)**
```json
{
  "id": 1,
  "title": "Spring Boot 공부",
  "content": "JPA와 REST API 학습하기",
  "createdAt": "2024-01-15T11:00:00",
  "modifiedAt": "2024-01-15T11:00:00"
}
```

## 🔐 보안 기능

- **비밀번호 암호화**: BCrypt 알고리즘을 사용한 안전한 비밀번호 저장
- **세션 기반 인증**: HttpSession을 통한 로그인 상태 관리
- **Validation**: Spring Validation을 통한 입력값 검증

## 🛡 예외 처리

### 커스텀 예외

모든 커스텀 예외는 `ServerException`을 상속받아 구현되었습니다.

| 예외 클래스 | HTTP Status | Error Code | 설명 |
|-----------|-------------|------------|------|
| `UserNotFoundException` | 404 NOT_FOUND | USER_NOT_FOUND | 사용자를 찾을 수 없을 때 |
| `TodoNotFoundException` | 404 NOT_FOUND | TODO_NOT_FOUND | 일정을 찾을 수 없을 때 |
| `CommentNotFoundException` | 404 NOT_FOUND | COMMENT_NOT_FOUND | 댓글을 찾을 수 없을 때 |
| `PasswordMismatchException` | 401 UNAUTHORIZED | PASSWORD_MISMATCH | 비밀번호가 일치하지 않을 때 |
| `MethodArgumentNotValidException` | 400 BAD_REQUEST | - | Validation 실패 시 |

### 예외 응답 형식

**커스텀 예외 응답 예시**
```json
{
  "message": "존재하지 않는 사용자입니다.",
  "errorCode": "USER_NOT_FOUND",
  "timestamp": "2024-01-15T10:30:00"
}
```

**Validation 실패 응답 예시**
```json
{
  "errors": {
    "email": "올바른 이메일 형식이 아닙니다.",
    "password": "비밀번호는 필수 입력 항목입니다."
  },
  "timestamp": "2024-01-15T10:30:00"
}
```

모든 예외는 `GlobalExceptionHandler`에서 일관되게 처리되어 JSON 형태로 응답합니다.

## 📌 주요 특징

1. **계층형 아키텍처**: Controller - Service - Repository 구조로 관심사 분리
2. **JPA Auditing**: BaseEntity를 통한 생성/수정 시간 자동 관리
3. **Lazy Loading**: @ManyToOne 관계에서 성능 최적화
4. **트랜잭션 관리**: @Transactional을 통한 데이터 일관성 보장
5. **DTO 패턴**: 계층 간 데이터 전달을 위한 명확한 DTO 사용

## 📄 라이선스

This project is licensed under the MIT License.

---

**개발 기간**: [프로젝트 기간]  
**개발자**: [개발자 이름]