# JDBC → Maven + MyBatis 마이그레이션

> Eclipse 기반 순수 Java JDBC 프로젝트를 Maven으로 마이그레이션하고,  
> SQL을 XML로 분리하는 MyBatis ORM을 도입했습니다.

---

## 1. 빌드 도구 전환 (Eclipse → Maven)

### 추가된 파일
| 파일 | 설명 |
|------|------|
| `pom.xml` | Maven 프로젝트 설정. mysql-connector, mybatis 의존성 선언으로 JAR 수동 관리 제거 |

### 변경된 디렉토리 구조
```
Before (Eclipse)          After (Maven)
─────────────────         ──────────────────────────
src/                      src/
  com/ureca/employee/       main/
    ...                       java/
                                com/ureca/employee/
                                  ...
                              resources/
                                mybatis-config.xml
                                mappers/
                                  EmployeeMapper.xml
                                  VacationMapper.xml
```

---

## 2. DB 접근 방식 전환 (JDBC → MyBatis)

### 핵심 변경 비교

| 항목 | JDBC (이전) | MyBatis (이후) |
|------|------------|----------------|
| DB 연결 관리 | `DBUtil` → `DriverManager.getConnection()` 매번 생성 | `MyBatisUtil` → `SqlSessionFactory` 커넥션 풀 |
| SQL 위치 | DaoImp 자바 코드 안에 문자열로 하드코딩 | `EmployeeMapper.xml`, `VacationMapper.xml`에 분리 |
| 결과 매핑 | `rs.getString("ename")` 수동 세팅 | `resultMap`으로 컬럼 ↔ 필드 자동 매핑 |
| 실행 방식 | `PreparedStatement` 직접 생성/실행/닫기 | `SqlSession.getMapper()` 호출 후 자동 닫기 |
| 예외 처리 | `try-finally`로 리소스 수동 닫기 | `try-with-resources`로 SqlSession 자동 닫기 |

---

## 3. 파일별 변경 내역

### 🆕 새로 추가된 파일

#### MyBatis 설정
| 파일 | 역할 |
|------|------|
| `src/main/resources/mybatis-config.xml` | DB 연결 설정(URL/ID/PW), typeAlias 등록, Mapper XML 경로 지정 |

#### Mapper 인터페이스 (MyBatis가 구현체 자동 생성)
| 파일 | 역할 |
|------|------|
| `src/main/java/.../dao/EmployeeMapper.java` | `insert`, `update`, `delete`, `selectOne`, `selectAll` 선언 |
| `src/main/java/.../dao/VacationMapper.java` | 휴가 유형/현황/신청 관련 10개 메서드 선언, `@Param`으로 다중 파라미터 처리 |

#### Mapper XML (실제 SQL 정의)
| 파일 | 역할 |
|------|------|
| `src/main/resources/mappers/EmployeeMapper.xml` | emp 테이블 CRUD SQL, `resultMap`으로 `ename→name`, `sal→salary` 매핑 |
| `src/main/resources/mappers/VacationMapper.xml` | VACATION / VACATION_REQUEST / VACATION_TYPE 테이블 SQL, JOIN 쿼리 resultMap 처리 |

#### 유틸리티
| 파일 | 역할 |
|------|------|
| `src/main/java/.../util/MyBatisUtil.java` | `SqlSessionFactory` 싱글턴. `mybatis-config.xml` 로드, `getSession()` 제공 |

---

### ✏️ 변경된 파일

#### `EmployeeDaoImp.java`
```java
// JDBC (이전)
Connection con = dbutil.getConnection();
PreparedStatement stmt = con.prepareStatement("insert into emp ...");
stmt.setString(1, emp.getEmpno());
stmt.executeUpdate();

// MyBatis (이후)
try (SqlSession session = MyBatisUtil.getSession(true)) {
    session.getMapper(EmployeeMapper.class).insert(emp);
}
```

#### `VacationDaoImp.java`
```java
// JDBC (이전)
Connection con = db.getConnection();
PreparedStatement stmt = con.prepareStatement("SELECT v.vacation_id, ...");
// ResultSet 수동 매핑 (30줄+)

// MyBatis (이후)
try (SqlSession session = MyBatisUtil.getSession()) {
    return session.getMapper(VacationMapper.class).selectAllVacations();
}
```

---

## 4. 커밋 히스토리

```
b9f1a5a  feat: JDBC에서 MyBatis로 마이그레이션
9cebe94  refactor: Maven 프로젝트 구조로 전환
7c31470  feat: initial JDBC implementation with Swing MVC UI
854b4d4  project setup
```

- `git show 9cebe94` → 27개 파일이 `src/com/` → `src/main/java/com/`으로 이동하는 diff 확인
- `git show b9f1a5a` → DaoImp의 JDBC 코드가 MyBatis로 교체되는 diff 확인

---

## 5. 회원가입 및 로그인 기능 추가

### DB 설정

실행 전 MySQL에서 아래 테이블을 생성해야 합니다.

```sql
CREATE TABLE IF NOT EXISTS users (
    id         INT         AUTO_INCREMENT PRIMARY KEY,
    username   VARCHAR(50) NOT NULL UNIQUE,
    password   VARCHAR(64) NOT NULL,        -- SHA-256 hex
    role       VARCHAR(20) NOT NULL DEFAULT 'USER',
    created_at TIMESTAMP   DEFAULT CURRENT_TIMESTAMP
);
```

### 앱 실행 흐름

```
앱 시작
  └── LoginDialog 표시 (모달)
        ├── 로그인 성공 → EmployeeUI (메인 화면)
        │     └── 상단 바: "환영합니다, [username]님 (ROLE)" + [로그아웃] 버튼
        │           └── 로그아웃 클릭 → 메인 화면 닫기 → LoginDialog 재표시
        └── X 버튼 → 앱 종료
```

### LoginDialog UI

| 모드 | 표시 필드 | 버튼 |
|------|----------|------|
| 로그인 | 아이디, 비밀번호 | [로그인] [회원가입] |
| 회원가입 | 아이디, 비밀번호, 비밀번호 확인 | [가입 완료] [취소] |

- 비밀번호는 `JPasswordField` 사용 (입력값 마스킹)
- Enter 키로 기본 버튼 실행
- 회원가입 완료 후 자동으로 로그인 모드로 전환

### 비밀번호 보안

외부 라이브러리 없이 Java 내장 `MessageDigest`로 SHA-256 해싱 처리합니다.

```java
// AuthServiceImp.java
MessageDigest md = MessageDigest.getInstance("SHA-256");
byte[] bytes = md.digest(input.getBytes(StandardCharsets.UTF_8));
// → 64자리 hex 문자열로 DB 저장
```

### 🆕 새로 추가된 파일

| 파일 | 역할 |
|------|------|
| `model/dto/User.java` | id, username, password, role 필드 DTO |
| `model/dao/UserMapper.java` | `insert`, `selectByUsername` 선언 |
| `resources/mappers/UserMapper.xml` | users 테이블 SQL 정의 |
| `model/service/AuthService.java` | `login()`, `register()` 인터페이스 |
| `model/service/AuthServiceImp.java` | SHA-256 해싱, UserSession 연동 구현체 |
| `view/LoginDialog.java` | 로그인/회원가입 전환 다이얼로그 |

### ✏️ 변경된 파일

| 파일 | 변경 내용 |
|------|----------|
| `mybatis-config.xml` | `User` typeAlias 추가, `UserMapper.xml` 등록 |
| `Main.java` | `launchApp()` 메서드로 리팩토링, LoginDialog 선행 표시 |
| `EmployeeUI.java` | 상단 바(환영 메시지 + 로그아웃 버튼) 추가 |

---

## 6. 로그인 인터셉터 (AuthInterceptor)

Spring MVC의 `HandlerInterceptor` 구조를 Swing에 대응하여 구현합니다.

### Spring ↔ Swing 대응 관계

| Spring MVC | Swing |
|------------|-------|
| `HandlerInterceptor` | `AuthInterceptor` |
| `HttpSession` | `UserSession` (static 싱글턴) |
| `session.getAttribute("userDto")` | `UserSession.getCurrentUser()` |
| `preHandle()` → `return false` | `preHandle()` → `return false` → 버튼 액션 차단 |
| AJAX 요청 → JSON `{"result":"login"}` | `RequestType.SERVICE_CALL` → 조용히 false 반환 |
| 페이지 요청 → `sendRedirect("/pages/login")` | `RequestType.UI_ACTION` → `LoginDialog` 재표시 |
| `session.invalidate()` | `UserSession.logout()` |

### 동작 구조

```java
// EmployeeUI.java - buttonHandler
if (authInterceptor != null && !authInterceptor.preHandle()) return; // 미인증 시 차단
// ↓ 통과 시 실행
insert() / update() / delete() / findEmployee()
```

```java
// AuthInterceptor.java
public boolean preHandle(RequestType type) {
    User user = UserSession.getCurrentUser();

    if (user == null) {                          // 거절
        if (type == SERVICE_CALL)  return false; // AJAX처럼 조용히
        else onLoginRequired.run();              // 페이지처럼 LoginDialog 표시
        return false;
    }
    return true;                                 // 통과
}
```

### RequestType 구분

| 타입 | 해당 상황 | 동작 |
|------|----------|------|
| `UI_ACTION` (기본값) | 버튼 클릭 등 사용자 액션 | LoginDialog 팝업 표시 후 false |
| `SERVICE_CALL` | 백그라운드/프로그래밍 방식 호출 | UI 없이 조용히 false 반환 |

### 🆕 새로 추가된 파일

| 파일 | 역할 |
|------|------|
| `util/UserSession.java` | 현재 로그인 사용자 보관 (HttpSession 역할) |
| `util/AuthInterceptor.java` | preHandle 기반 인증 차단 로직 |

### ✏️ 변경된 파일

| 파일 | 변경 내용 |
|------|----------|
| `AuthServiceImp.java` | 로그인 성공 시 `UserSession.login(user)` 호출 |
| `EmployeeUI.java` | `buttonHandler`에 인터셉터 체크 추가, `setAuthInterceptor()` 제공 |
| `Main.java` | `launchApp()` 시작 시 `UserSession.logout()`, 인터셉터 생성 후 주입 |
