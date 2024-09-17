# 1. hhplus-e-commerce

- java 21
- SpringBoot 3.3.1
- MySql/JPA
- Redis
- Kafaka (kafka-ui : http://localhost:8081/, 토픽자동생성)
- JUnit + AssertJ
- Layered Architecture Based
- swagger (http://localhost:8080/swagger-ui/index.html, 유저로그인 => Authorize에 토큰)




# 2. 개발 환경 셋팅
redis, mysql, kafka를 전부 `application-setting-docker-compose.yml` (dockerCompose)로 관리하는중

```
docker-compose -f application-setting-docker-compose.yml up -d
```




# 3. 시나리오 분석 및 작업계획
## 📕 Milestone
[https://github.com/users/whitewise95/projects/4/views/1](https://github.com/users/whitewise95/projects/4/views/1)

## 📕 시퀀스 다이어그램
[https://github.com/whitewise95/hhplus-e-commerce/tree/main/docs](https://github.com/whitewise95/hhplus-e-commerce/tree/main/docs)

### 📕 ERD 설계
[https://www.erdcloud.com/d/k2Bg73mHyuFcitshf](https://www.erdcloud.com/d/k2Bg73mHyuFcitshf)

### 📕 API 명세
[https://beakhyeonmyeongs-organization.gitbook.io/api/](https://beakhyeonmyeongs-organization.gitbook.io/api/)
