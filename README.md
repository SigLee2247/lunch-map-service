# 지리기반 맛집 추천 웹 서비스 ‘Lunch Map’

>💡 [공공 데이터](https://www.data.go.kr/tcs/dss/selectDataSetList.do?dType=API&keyword=%EA%B2%BD%EA%B8%B0%EB%8F%84+%EC%9D%BC%EB%B0%98%EC%9D%8C%EC%8B%9D%EC%A0%90&operator=AND&detailKeyword=&publicDataPk=&recmSe=&detailText=&relatedKeyword=&commaNotInData=&commaAndData=&commaOrData=&must_not=&tabId=&dataSetCoreTf=&coreDataNm=&sort=_score&relRadio=&orgFullName=&orgFilter=&org=&orgSearch=&currentPage=1&perPage=10&brm=&instt=&svcType=&kwrdArray=&extsn=&coreDataNmArray=&pblonsipScopeCode=)를 활용하여 지역 음식점 목록을 자동으로 업데이트 합니다.
><br/>사용자 위치에 따라 맛집 및 메뉴를 추천하여 더 나은 다양한 음식 경험을 제공하려 합니다.

<br/>
<br/>

### 구현 내용
1. 위치 기반 맛집 조회 API
2. 데이터 파이프라인 (데이터 수집, 전처리, 저장, 자동화)
3. 시군구 맛집 조회 API
4. 맛집 상세 정보 조회 API
5. 사용자 API 구현

<br/>

### 프로젝트 실행 방법

1. 프로젝트 Clone

```shell
https://github.com/SigLee2247/lunch-map-service.git
```

2. 프로젝트 파일 이동

```shell
cd lunch-map-service
```

3. 프로젝트 실행

```shell
./gradlew build
java -jar build/libs/lunchmapservice-0.0.1-SNAPSHOT.jar
```




## ERD

**ERD Link**:  https://www.erdcloud.com/d/jxePYzGRdPpPsytQY

![image](https://github.com/wanted-pre-onboarding-7th-backend-T/lunch-map-service/assets/65665065/16969622-6172-4d68-a554-ce92b94e96f2)

<br/>

## Skills

<div>
  <img src="https://img.shields.io/badge/Springboot.3.1.5-80EA62?style=flat-square&logo=Springboot&logoColor=black"/>
  <img src="https://img.shields.io/badge/Spring DATA JPA-80EA62?style=flat-square&logo=Spring&logoColor=black"/>
  <img src="https://img.shields.io/badge/SpringSecurity6.1.5-80EA62?style=flat-square&logo=Spring&logoColor=black"/>
  <img src="https://img.shields.io/badge/JWT-000000?style=flat-square&logo=JWT&logoColor=white"/>
</div>
<div>
  <img src="https://img.shields.io/badge/REDIS-dc382c?style=flat-square&logo=REDIS&logoColor=white"/>
  <img src="https://img.shields.io/badge/MySQL-764ABC?style=flat-square&logo=MySQL&logoColor=white">
  <img src="https://img.shields.io/badge/QueryDSL-764ABC?style=flat-square&logo=QueryDSL&logoColor=white">
  <img src="https://img.shields.io/badge/junit5-25A162?style=flat-square&logo=junit5&logoColor=white"/>
</div>

<br/>

## 요구사항 분석

요구사항 : [요구사항 링크](https://www.notion.so/a9a2ec57b65545e4be7da370c4649007?pvs=21)


<br/>

## API Reference

### User Domain

<details>
  <summary>사용자 회원가입 <b><code>[POST] /api/users</code></b></summary><br/>

- `계정명`, `패스워드` 입력하여 회원가입

  <br/>

**Request**

| 전달 방식 | Name | Type | Description |
  | --- | --- | --- | --- |
| Body | username | String | 사용자 계정명 |
| Body | password | String | 사용자 비밀번호 |

  ```json
  {
    "username": "test",
    "password": "abcd1234"
  }
  ```

  <br/>

**Response**

| StatusCode | Message | Description |
  | --- | --- | --- |
| 201 |  | 사용자 등록 성공 |
| 400 | 필수값이 입력되지 않았습니다. | 사용자 등록 시 필수값 누락 |
| 409 | 이미 사용 중인 E-mail 입니다. | 사용자 등록 시 중복 E-mail |

  ```json
  {
    "data": {
      "userId": 1
    },
    "message": "OK",
    "code": 201,
    "timeStamp": "2023-11-02 13:15:11"
  }
  ```
</details>

<details>
  <summary>사용자 로그인 <b><code>[POST] /api/login</code></b></summary><br/>

- `계정명`, `패스워드` 이용한 로그인 `JWT 토큰 활용`

  <br/>

**Request**

| 전달 방식 | Name | Type | Description |
  | --- | --- | --- | --- |
| Body | username | String | 사용자 계정명 |
| Body | password | String | 사용자 비밀번호 |

  ```json
  {
    "username": "test",
    "password": "abcd1234"
  }
  ```

  <br/>

**Response**

| StatusCode | Message | Description |
  | --- | --- | --- |
| 200 |  | 로그인 성공 |
| 401 | Unauthorized | 로그인 시 문제가 발생함 |

</details>

<details>
  <summary>사용자 설정 업데이트 <b><code>[PATCH] /api/users/{userId}</code></b></summary><br/>

- 사용자의 위치인 `위도`, `경도`와 `점심 추천 기능 사용 여부`를 업데이트
- 변경을 원하지 않는 파라미터는 생략 후 전달

  <br/>

**Request**

|    전달 방식    |       Name       |                  Type                  |             Description |
|:-----------:|:----------------:|:--------------------------------------:|------------------------:|
|    Body     |       lat        |                 Double                 |                  사용자 위도 |
|    Body     |       lon        |                 Double                 |                  사용자 경도 |
|    Body     |  serviceAccess   | Enum <br/>(`LUNCH`, `DINNER`, `NONE`)  |            사용자 점심 추천 여부 |
|    Body     |     username     |                 String                 |                  사용자 이름 |



  ```json
  {
    "latitude": "test",
    "longitude": "abcd1234",
    "serviceAccess": "LUNCH",
    "username": "siglee"
  }
  ```

  <br/>

**Response**

| StatusCode | Message | Description |
  | --- | --- | --- |
| 200 |  | 사용자 정보 업데이트 성공 |
| 401 | Unauthorized | 로그인 시 문제가 발생함 |

</details>

<details>
  <summary>사용자 정보 조회 <b><code>[GET] /api/users/{userId}</code></b></summary><br/>

- `패스워드`를 제외한 모든 사용자 정보 반환

  <br/>

**Request**

| 전달 방식 | Name | Type | Description |
  | --- | --- | --- | --- |
| Path Variable | usersId | Long | 사용자 식별자 |

  <br/>

**Response**

| StatusCode | Message | Description |
  | --- | --- | --- |
| 201 |  | 사용자 정보 조회 성공 |
| 401 | Unauthorized | 로그인 시 문제가 발생함 |

  ```json
  {
    "data": {
      "id": 1,
      "userName": "testName",
      "latitude": 132.123456,
      "longitude": 32.58694,
      "serviceAccess": "LUNCH"
    },
    "message": "OK",
    "code": 200,
    "timeStamp": "2023-11-02 13:15:11"
  }
  ```
</details>

### Restaurant Domain

<details>
  <summary>맛집 상세 조회 <b><code>[GET] /api/restaurants/{restaurantId}</code></b></summary><br/>

- `맛집 모든 필드`와 `평가` 상세 리스트 포함하여 조회
- `평가` 상세 리스트는 최신순으로 조회

  <br/>

**Request**

| 전달 방식 | Name | Type | Description |
  | --- | --- | --- | --- |
| Path Variable | restaurantId | Long | 맛집 식별자 |

  <br/>

**Response**

| StatusCode | Message | Description |
  | --- | --- | --- |
| 200 |  | 맛집 상세 조회 성공 |
| 404 | 맛집 정보가 존재하지 않습니다. | 맛집 데이터 없음 |
| 401 | Unauthorized | 로그인 시 문제가 발생함 |

  ```json
  {
    "data": {
      "restaurantId": 1,
      "restaurantName": "아도니스",
      "lotNumberAddress": "경기도 가평군 상면 행현리 602-3번지",
      "roadNameAddress": "경기도 가평군 상면 수목원로 314-2",
      "zipCode": "12448",
      "longitude": 37.7516678333,
      "latitude": 127.3588076752,
      "location": {
        "cityName": "경기도",
        "countryName": "가평군",
        "longitude": 37.7516678333,
        "latitude": 127.3588076752
      },
      "averageScore": 0.0,
      "ratingList": [
        {
          "content": "리뷰 본문",
          "username": "작성자 이름",
          "score": 0
        }
      ]
    },
    "message": "OK",
    "code": 200,
    "timeStamp": "2023-11-02 13:15:11"
  }
  ```
</details>

<details>
  <summary>시군구 맛집 목록 조회 <b><code>[GET] /api/restaurants</code></b></summary><br/>

- 해당 `도시`, `시군구` 내의 맛집 목록 조회

  <br/>

**Request**

| 전달 방식 | Name | Type | Description | 필수값 |
  | --- | --- | --- | --- | --- |
| Parameter | cityName | String | 도시(도, 시(광역시)) | False |
| Parameter | countryName | String | 시, 군, 구 | False |


  <br/>

**Response**

| StatusCode | Message | Description |
  | --- | --- | --- |
| 200 |  | 시군구 맛집 목록 조회 성공 |
| 401 | Unauthorized | 로그인 시 문제가 발생함 |

  ```json
  {
    "data": {
      "content": [
        {
          "restaurantId": 1, 
          "restaurantName": "아도니스",
          "lotNumberAddress": "경기도 가평군 상면 행현리 602-3번지", 
          "roadNameAddress": "경기도 가평군 상면 수목원로 314-2",
          "longitude": 37.7516678333, 
          "latitude": 127.3588076752,
          "location": {
            "cityName": "경기도",
            "countryName": "가평군",
            "longitude": 37.7516678333,
            "latitude": 127.3588076752
          },				
          "averageScore": 0.0
        }
      ],
      "pageable": {
        "offset": 0,
        "size": 30,
        "totalElements": 30,
        "last": true,
        "numberOfElements": 100,
        "first": true,
        "totalPages": 10
      }
    },
    "message": "OK",
    "code": 200,
    "timeStamp": "2023-11-02 13:15:11"
  }
  ```
</details>

<details>
  <summary>위치 기반 맛집 목록 조회 <b><code>[GET] /api/users/nearby</code></b></summary><br/>

- 해당 `위도`, `경도` 위치를 기반으로 `반경` km 내의 맛집 목록 조회
- 요청 좌표와 식당 사이의 거리인 `거리순` 과 `평점순` 조회 가능

  <br/>

**Request**

| 전달 방식 | Name | Type | Description | 필수값 |
  | --- | --- | --- | --- | --- |
| Parameter | currentLongitude | String | 추천 받을 위치 경도 | True |
| Parameter | currentLatitude | String | 추천 받을 위치 위도 | True |
| Parameter | range | Double | 추천 맛집 반경 | True |
| Parameter | sorting | String | 정렬 기준 | False (거리순) |

  <br/>

**Response**

| StatusCode | Message | Description |
  | --- | --- | --- |
| 200 |  | 위치 기반 맛집 목록 조회 성공 |
| 400 | 필수값이 입력되지 않았습니다. | 맛집 조회 시 필수값 누락 |
| 401 | Unauthorized | 로그인 시 문제가 발생함 |

  ```json
  {
    "data": [
      {
        "id": 1,
        "locationId": 1,
        "name": "아도니스",
        "lotNumberAddress": "경기도 가평군 상면 행현리 602-3번지", 
        "roadNameAddress": "경기도 가평군 상면 수목원로 314-2",
        "zipCode": 1234,
        "longitude": 37.7516678333,
        "latitude": 127.3588076752,
        "averageScore": 0.0
      }
    ],
    "message": "OK",
    "code": 200,
    "timeStamp": "2023-11-02 13:15:11"
  }
  ```
</details>

<details>
    <summary>맛집 평가 <b><code>[POST] /api/restaurants/evaluation/{restaurantId}</code></b></summary>

- 사용자가 작성하는 음식점 리뷰
- 평점은 0 ~ 10까지의 자연수만 줄 수 있으며 입력 필수
- 간단한 리뷰는 필수가 아니다.

**Request**

| 전달 방식         | Name         | Type    | Description      | 필수값  |
  |---------------|--------------|---------|------------------|------|
| Boody         | score        | Integer | 평점               | True |
| Parameter     | content      | String  | 리뷰 댓글            | False |
| Path Variable | restaurantId | Long    | 맛집 식별자           | True |
| Login         | 로그인 여부       | Long    | 로그인 시 사용자 식별자 조회 | True |

**Response**

| StatusCode | Message | Description |
| --- | --- |  |
| 200 |  | 리뷰 등록 성공 |
| 400 | 필수값이 입력되지 않았습니다. | 평점 작성 누락 |
| 401 | Unauthorized | 로그인 시 문제가 발생함 |

  ```json
  {
    "data": [
      {
        "restaurantId": 1
      }
    ],
    "message": "OK",
    "code": 200,
    "timeStamp": "2023-11-02 13:15:11"
  }
  ```

</details>

<br/>

## 구현 및 성능 개선

### RawRestaurant, Restaurant 테이블의 분리

- RawRestaurant 테이블은 공공 데이터 포털에서 제공하는 API 요청으로 받아온 데이터만을 저장하는 원본 데이터베이스
- Restaurant 테이블은 RawRestaurant 테이블의 일부 데이터와, Lunch Map 서비스에서 사용되는 데이터를 저장을 위한 데이터베이스
- **두 테이블 분리 시 insert, update가 2배 더 발생** but **테이블 분리를 통해 보다 유연하고 독립적인 테이블 주고 가질 수 있음**

### 대용량 insert, update에 대한 쿼리 최적화 **160s -> 34s 단축**
<img src="https://github.com/SigLee2247/lunch-map-service/assets/116015708/a1cca23e-3e5f-41a9-9500-9e6a270a52db" alt="image" width="400" height="300">


- JDBC properties 설정을 통한 쿼리 최적화
  - `jdbc.batch_size`, `order_inserts`, `order_updates` 설정 → 여러 개의 **SQL 쿼리 종류 별 정렬**
  - `rewriteBatchedStatements=true` 설정 → JDBC 내부적으로 각각의 insert문을 **하나의 bulk insert로 수정**
- Restaurant, Location 테이블의 키 매핑 전략 `IDENTITY` → `SEQUENCE`로 수정
    - Restaurant, Location 테이블은 대용량 insert, update가 발생하는 테이블
    - `IDENTITY` 전략의 경우 앞의 설정에도 불구하고 하나의 쿼리가 하나의 DB Connection으로 발생 → **키 seq를 DB로부터 미리 할당 받아오도록 하기 위해 `SEQUENCE` 전략**으로 수정

### Scheduling을 위한 라이브러리 선택

- 고려한 라이브러리 종류
    - Quartz Scheduler (외장형 라이브러리)
    - Spring Boot Scheduling (내장형 라이브러리)

<details>
  <summary>Quartz Scheduler 장단점</summary><br/>

**장점**

1. 기본 제공되는 Spring Boot Scheduling 대비  **세부적인 설정 가능**
- 서로 의존성 있는 **스케줄 작업의 실행 및 실패 시 간단하게 제어** 가능
- 즉 작업 실패 시 재동작 트리거를 손쉽게 설정할 수 있음
3. DB 기반으로 스케줄러 간의 Clustering 기능 제공 (로드밸런싱 사용 시 장점)
4. In-memory Job Scheduler 제공
5. 다양한 플러그인의 존재
6. **Scheduler 와 Job의 분리**
- Job이 추가 되었을 때 스케줄러를 재배포 하게 되면 스케줄러가 중단되고 실행되는 작업이 많을 때는 재배포 타이밍을 잡기 어려워진다. → 이를 해결하기 위해 Job 과 Scheduler의 분리를 통해 별도 배포 가능

**단점**

1. 외부 의존성 사용으로 인한 **의존성 추가**
2. 클러스터링을 위해 DB Table 생성
3. Clustering 기능 제공하지만 단순한 random 방식이라 완벽한 Cluster 간의 로드 분산 X
4. Fixed Delay 타입 보장 X (실행된 이후 특정 시점 뒤 실행 방식)
5. 내장형 Scheduler 대비 **불필요한 설정 추가**
- 내장형 Scheduler 사용 시 간단한 어노테이션으로 사용이 가능

</details>

<details>
  <summary>Spring Boot Scheduling 장단점</summary><br/>

- 스프링에서 제공하는 내장형 스케줄러
- 특정 주기로 실행할 작업 정의 및 관리 가능
- 1개의 스레드를 활용해 스케줄링 진행 → 반복 실행해도 동일한 스레드에서 작업을 진행

**장점**

1. 내장 라이브러리로 추가적인 의존성 불필요
2. `@Scheduled` 어노테이션을 통해 간단한 제어 가능

**단점**

1. 로드 밸런싱 등 특정 APP의 인스턴스를 여러 개 생성 시, 같은 스케줄링이 여러 번 실행되는 것을 방지하기 위해 ShedLock 필요 → `@TryLock` 어노테이션으로 가능
2. 인메모리 스케줄러로 스케줄링 tasks 관련 정보는 메모리에서 관리 → 어플리케이션이 재시작되거나 중단되면 기존 tasks 정보 모두 사라짐, 즉 하나의 어플리케이션 메모리에서 동작하므로 분산 시스템이나 MSA 구조에는 적합X
3. task 간 의존성을 부여하기 힘듦, 예를 들어 **A task 실행 → B task 실행**과 같은 tasks 간 실행 순서를 정의하기 어려우며 특정 task 실행 실패 시 동작을 지정할 수 없음


</details>

<br/>

**사용한 Scheduling**

> Spring boot에서 내장하고 있는 Spring Boot Scheduling 사용

- 프로젝트 내의 스케줄링의 규모가 크지 않기 때문에 내장형 Scheduling으로도 구현할 수 있을 것 이라 판단
- 추후 고도화 작업을 통해 필요 시 Quartz Scheduler 변경 예정
    - 로드 밸런싱 등을 통해 같은 기능을 하는 다수의 APP 인스턴스의 활성화
    - 추후 Scheduling 간의 복잡한 의존성이 발생하거나, 실패 시 진행할 의존성 추가가 필요한 경우 변경

**Spring Boot Scheduler 공식문서**

- https://www.baeldung.com/spring-task-scheduler
- https://docs.spring.io/spring-framework/reference/integration/scheduling.html

**Quartz 참고 문서**

- https://homoefficio.github.io/2019/09/28/Quartz-스케줄러-적용-아키텍처-개선-1/
- https://devhj.tistory.com/47
- https://examples.javacodegeeks.com/java-development/enterprise-java/quartz/java-quartz-architecture-example/
- https://velog.io/@park2348190/Spring-Boot-환경의-Quartz-Scheduler-활용

<br/>


## SequenceDiagram
### 평점 추가 시퀀스 다이어 그램
```mermaid
sequenceDiagram
	controller ->> service : evaluateRestaurant()
	service ->> repository : findById()
	repository -->> service : entity
	service ->> restaurant : addRating()
	restaurant ->> restaurant : calculateAverageScore()
	Note over restaurant, restaurant: avgScore 계산 <br> 소숫점 첫째자리 반올림 
	service --> controller : response Data 전달

```
