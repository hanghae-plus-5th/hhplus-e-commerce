


# 1. 낙관락vs비관락vs분산락 사용 리뷰 
##  📕 유저 잔액 충전 
낙관락은 우선 충돌할 가능성이 낮을 경우 사용한다고 알고 있어서, 한 유저가 동시에 충전할 가능성이 낮기 때문에 낙관락을 사용하기로 했다.
근데 추가로 낙관락이 과연 충돌이 낮으면 비관락보다 빠른지 낙관락과 비관락을 1000명 10명 3명으로 테스드를 해봤는데
낙관락은 ` @Retryable(value = Exception.class, maxAttempts = 100)` 이 어노테이션으로 충돌을 날 때마다 재시도를 하는데 이 재시도 비용이 큰거 같다.
테스트 결과는 충격적이였다. 낙관락이 비관락보다 엄청 느렸다. 

추가록 분산락까지 적용시켜보았는데 분산락은 레디스를 이용해서 메모리에 저장하니 더 빠를 거라고 생각했는데... 비관락이 더 빨랐다. 
근데 내가 알기론 분산락이 훨씬 빠르다고 그랬는데 이제부터는 테스트를 잘못하고 있는건가 생각이 든다.

이곳 저곳 알아보니 

레디스의 분산락은 일반적으로 SETNX (Set if Not Exists)나 Redlock 알고리즘을 사용하는데 이 방법은   
네트워크 통신이 필요하고 분산 락을 얻기 위해 Redis 서버와의 네트워크 통신이 필요하기에 그 과정 (락 획득, 유지, 해제)에서 추가적인 오버헤드가 발생한다고 한다.   
그래도 분산락이 더 빠르다고 한 이유가 있을 것 같다는 생각이들어서 나중에 코치님께 여쭤봐야할 것 같다.


#### 태스트코드인데 낙관락이나 비관락이나 테스트코드는 동일하게 진행했다. 
```java
  @Test
  public void 유저잔액조회기능_유저정보없으면_에러나는지_동시성_낙관락_통합테스트() {
    //given
    String userName = "백현명";
    int chargeAmount = 1500;

    //when$
    User saveUser = userService.saveUser(userName);

    // 19개의 CompletableFuture 생성
    CompletableFuture<?>[] futures = IntStream.range(0, 10)
        .mapToObj(i -> CompletableFuture.runAsync(() -> userService.chargeUserAmount(saveUser.getId(), chargeAmount)))
        .toArray(CompletableFuture[]::new);

    // 모든 CompletableFuture가 완료될 때까지 대기
    CompletableFuture.allOf(futures).join();

    User when = userRepository.findById(saveUser.getId()).get();
    userRepository.delete(when);

    //then
    assertEquals(when.getId(), saveUser.getId());
    assertEquals(when.getName(), saveUser.getName());
    assertEquals(chargeAmount * 10, when.getAmount());
  }
}
```


## 낙관락
#### 낙관락`1000명`  
![스크린샷 2024-07-26 오전 12 21 17](https://github.com/user-attachments/assets/2aad5fc6-a389-4661-be94-29d40fa16dc1)


#### 낙관락 `10명`  
![image](https://github.com/user-attachments/assets/d1f90cf9-fdeb-4f80-8043-8fd8ca1212ab)

#### 낙관락 `3명`  
![image](https://github.com/user-attachments/assets/329c2d6e-a6dd-46ad-a451-48daca6527a7)


## 비관락
#### 비관락 `1000명`  
![스크린샷 2024-07-26 오전 12 03 56](https://github.com/user-attachments/assets/6f19f08d-8129-492a-b745-697e58588686)


#### 비관락 `10명`  
![image](https://github.com/user-attachments/assets/50fc2c82-1060-4078-be83-389f24888bd6)

#### 비관락 `3명`  
![image](https://github.com/user-attachments/assets/9d0072a8-4a11-4100-a51c-9ae46f8db5e1)

## 분산락 
#### 분산락 `1000명`
![image](https://github.com/user-attachments/assets/d0ebb453-1816-46d2-b406-ac01fb8cbbc5)

#### 분산락 `10`명
![image](https://github.com/user-attachments/assets/d5b5b899-01cf-4918-a644-475c70e07a9a)


#### 분산락 `3`명
![image](https://github.com/user-attachments/assets/6624f7fc-29d6-4068-ae06-bdcad4eb7c33)

<br>
<br>

##  📕 주문 API 
주문 API는 비관락를 바로 사용하기로 결정했다. 그 이유는   
`첫번째 분산락`에 대해서는 레디스의 분산락은 key-value로 락을 걸 수 있는데 내 어플리케이션은 주문시 상품을 여러개를 주문할 수 있어서, 여러 상품의 재고를 동시에 감소시키는 작업에서는 데이터베이스 락을 사용하는 것이 더 직관적이고 효과적일 수 있다고 생각했다.  

`두번째 낙관락`에 대해서는 주문자체는 e커머스에서 충돌이 가장 많은 곳이라고 생각하는데 거기에 상품재고와 유저의 잔액을 전부 낙관으로 처리하기에는 충돌이 배로 많을 것 같다는 생각이 했다 이렇게 `@Retry` 로 관리하더라도 재시도하는 비용이 너무커서 비관락보다 더 느릴 것으로 예상했다.  


## 비관락
#### 비관락 `1000명`  
![스크린샷 2024-07-26 오전 12 45 27](https://github.com/user-attachments/assets/9095f3a9-5714-4d83-9f86-f0274f9928ad)


#### 비관락 `10명`  
![image](https://github.com/user-attachments/assets/b4b49055-596b-4030-8fba-9e664c68b12f)


#### 비관락 `3명`  
![image](https://github.com/user-attachments/assets/cb1c6aaf-a435-4116-ab1d-88ba3af96e53)


<br>
<br>
<br>
<br>

# 2. 내가 느낀 락들의 특징과 단장점
## 📕 낙관적 락
> 데이터가 충돌할 가능성이 낮다고 가정하고, 트랜잭션이 끝날 때까지 데이터에 대한 락을 걸지 않는다.

###  `장점`
- 락을 걸지 않기 때문에 성능이 뛰어나다.

###  `단점`
- 충돌이 발생하면 재시도가 필요하다. 
- 충돌 처리 로직이 필요하다.

## 📕 비관적 락 
> 데이터가 충돌할 가능성이 높다고 가정하고, 데이터에 접근할 때마다 락을 건다.

### `장점`
- 충돌 발생 가능성이 낮아 충돌 처리 로직이 단순하다.

### `단점`
- 락을 걸기 때문에 성능이 저하될 수 있다.
- 잘못된 락 관리로 인해 데드락이 발생할 수 있다.


## 📕  분산 락 
> 분산 시스템에서 동일한 리소스에 접근할 때, 락을 걸어 동시성을 제어할 때 사용한다.

###   `장점`
- 분산환경에서 안전하게 동시성을 제어하고 데이터 일관성을 유지한다.

### `단점`
- 락을 획득하고 해제하는 과정에서 네트워크 지연이 발생할 수 있다.
- 락 관리, 만료 시간 설정 등 복잡한 로직이 필요하다.

<br>
<br>
<br>
<br>


# 3. 결론 
우선 아직 분산락을 잘 못 사용하는 걸 수도 있지만 테스트 결과상 비관락이 제일 빠르고 복잡도가 낮아서 비관락으로 동시성 문제를 전부 처리하기로 결정했다. 
그리고 분산락의 장점은 다른 로직에서 분산락으로 락을 걸고 있어도 다른 로직에서 해당 정보를 조회할수 있다는 점에서 장점이 큰 것 같다.

