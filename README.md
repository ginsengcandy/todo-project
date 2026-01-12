# 일정 관리 프로젝트

Spring Boot와 JPA를 활용한 RESTful API 기반 일정 관리 시스템입니다.

## 📋 목차
- [프로젝트 개요](#프로젝트-개요)
- [기술 스택](#기술-스택)
- [ERD](#erd)
- [주요 기능](#주요-기능)
- [API 명세](#api-명세)
- [설치 및 실행](#설치-및-실행)

## 프로젝트 개요

사용자가 일정을 등록하고 관리할 수 있으며, 각 일정에 댓글을 작성할 수 있는 REST API 서버입니다.

### 주요 특징
- Session 기반 인증/인가
- BCrypt 암호화를 통한 안전한 비밀번호 관리
- JPA Auditing을 활용한 생성/수정 시간 자동 관리
- 소유자만 수정/삭제 가능한 권한 체계

## 기술 스택

### Backend
- Java 17
- Spring Boot 4.0.1
- Spring Data JPA
- Spring Web MVC
- Spring Validation

### Database
- MySQL 8.x

### Security
- BCrypt (at.favre.lib:bcrypt:0.10.2)

### Build Tool
- Gradle

### Others
- Lombok

## ERD

```mermaid
![ERD](./images/erd.png)
```

### 테이블 관계
- `User` : `Todo` = 1 : N
- `User` : `Comment` = 1 : N
- `Todo` : `Comment` = 1 : N

## 주요 기능

### 1. 사용자 관리 (User)
- 회원가입 (비밀번호 암호화)
- 로그인/로그아웃 (Session 기반)
- 사용자 조회 (단건/전체)
- 사용자 정보 수정
- 회원 탈퇴

### 2. 일정 관리 (Todo)
- 일정 생성 (로그인 필요)
- 일정 조회 (단건/전체/특정 사용자)
- 일정 수정 (작성자만)
- 일정 삭제 (작성자만)

### 3. 댓글 관리 (Comment)
- 댓글 작성 (로그인 필요)
- 댓글 조회 (단건/특정 일정의 전체 댓글)
- 댓글 수정 (작성자만)
- 댓글 삭제 (작성자만)

## API 명세

### 인증 API

#### 회원가입
```http
POST /signup
Content-Type: application/json

{
  "username": "홍길동",
  "email": "hong@example.com",
  "password": "password123!"
}
```

**비밀번호 요구사항**
- 8-15자
- 숫자 포함 필수
- 특수문자(!@#$%^&*) 포함 필수
- 공백 불가

#### 로그인
```http
POST /login
Content-Type: application/json

{
  "email": "hong@example.com",
  "password": "password123!"
}
```

#### 로그아웃
```http
POST /logout
```

### 사용자 API

#### 사용자 조회 (단건)
```http
GET /users/{userId}
```

#### 사용자 조회 (전체)
```http
GET /users
```

#### 사용자 정보 수정
```http
PUT /users
Content-Type: application/json

{
  "username": "김철수",
  "email": "kim@example.com",
  "password": "newpassword123!"
}
```
*로그인 필요*

#### 회원 탈퇴
```http
DELETE /users
```
*로그인 필요*

### 일정 API

#### 일정 생성
```http
POST /todos
Content-Type: application/json

{
  "title": "회의 준비",
  "content": "프로젝트 회의 자료 준비하기"
}
```
*로그인 필요*

#### 일정 조회 (단건)
```http
GET /todos/{todoId}
```

#### 일정 조회 (전체)
```http
GET /todos
```

#### 일정 조회 (특정 사용자)
```http
GET /todos?userId={userId}
```

#### 일정 수정
```http
PUT /todos/{todoId}
Content-Type: application/json

{
  "title": "수정된 제목",
  "content": "수정된 내용"
}
```
*로그인 필요, 작성자만 가능*

#### 일정 삭제
```http
DELETE /todos/{todoId}
```
*로그인 필요, 작성자만 가능*

### 댓글 API

#### 댓글 작성
```http
POST /todos/{todoId}/comments
Content-Type: application/json

{
  "author": "홍길동",
  "content": "좋은 일정이네요!"
}
```
*로그인 필요*

#### 댓글 조회 (단건)
```http
GET /todos/{todoId}/comments/{commentId}
```

#### 댓글 조회 (특정 일정의 전체 댓글)
```http
GET /todos/{todoId}/comments
```

#### 댓글 수정
```http
PUT /todos/{todoId}/comments/{commentId}
Content-Type: application/json

{
  "content": "수정된 댓글 내용"
}
```
*로그인 필요, 작성자만 가능*

#### 댓글 삭제
```http
DELETE /todos/{todoId}/comments/{commentId}
```
*로그인 필요, 작성자만 가능*

## 설치 및 실행

### 사전 요구사항
- JDK 17 이상
- MySQL 8.x
- Gradle

### 데이터베이스 설정

1. MySQL에서 데이터베이스 생성
```sql
CREATE DATABASE todos CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. `src/main/resources/application.properties` 설정
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/todos
spring.datasource.username=root
spring.datasource.password=12345678
```

### 실행 방법

1. 프로젝트 클론 또는 다운로드

2. 의존성 설치 및 빌드
```bash
./gradlew build
```

3. 애플리케이션 실행
```bash
./gradlew bootRun
```

4. 서버 접속
```
http://localhost:8080
```

## 에러 코드

| HTTP Status | Error Code | 설명 |
|------------|------------|------|
| 404 | USER_NOT_FOUND | 존재하지 않는 사용자 |
| 404 | TODO_NOT_FOUND | 존재하지 않는 일정 |
| 404 | COMMENT_NOT_FOUND | 존재하지 않는 댓글 |
| 401 | PASSWORD_MISMATCH | 비밀번호 불일치 |
| 403 | ACCESS_DENIED | 접근 권한 없음 |

## 프로젝트 구조

```
src/main/java/com/example/todo/
├── config/              # 설정 클래스
├── customErrors/        # 커스텀 예외 클래스
└── todo/
    ├── controller/      # REST 컨트롤러
    ├── dtos/           # DTO 클래스
    │   ├── commentDtos/
    │   ├── loginDtos/
    │   ├── todoDtos/
    │   └── userDtos/
    ├── entity/         # JPA 엔티티
    ├── repository/     # JPA 리포지토리
    └── service/        # 비즈니스 로직
```

## 라이선스

이 프로젝트는 학습 목적으로 제작되었습니다.