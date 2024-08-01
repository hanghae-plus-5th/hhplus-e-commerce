
## 조회 API 중 캐싱을 적용시킬 API 체크 ✅
- 장바구니목록조회 
- 상품목록조회  ✅
- 상품 상세 조회
- 인기판매상품조회 ✅
- 유저 잔액 조회

## 선정 이유
`우선 캐싱을 한다는 것은 조회 성능을 상승시키기 위함이라고 생각`하는데 이것만해도 `C,U,D 에는 캐싱이 해당되지 않아서 제외`했다. 

그리고 조회성 API 중에 선정을 했는데 그 중에도 상세조회라는가 장바구니목록조회 유저 잔액조회 같은 경우는 key값이 각각 필요해서 다 캐싱처리하다가는 메모리가 부족할 것이라고 생각이 들어서 제외시켰다.

더불어 `인기판매상품조회 같은 경우에는 GROUP BY와 SUM 등 통계쿼리가 발생해 성능적으로 불리`한테 이것을 `캐싱 처리하게 된다면 많은 이점이 있을 것으로 예상`이 된다. 

그리고 `상품목록조회 같은 경우`는 현재는 페이징처리되어 있지 않아 `상품이 추가되거나 삭제되거나 수정되지 않는 이상은 똑같은 데이터가 반환될 것으로 예상`되어 상품목록조회도 `캐싱처리로 큰 이점을 볼 수 있을 것으로 예상`해서 선정했다.


## 캐싱 처리
- 인기판매상품조회, 상품목록조회에 ` @Cacheable` 를 적용시켜서 캐싱할 수 있도록 했습니다.

```java
  @Transactional
  @Cacheable(value = "getTop5ProductsLast3Days")
  public List<OrderCommand.Top5ProductsLast3DaysResponse> getTop5ProductsLast3Days() {
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime minusDays3 = now.minusDays(3);
    List<Tuple> top5ProductsLast3Days = orderProductRepository.getTop5ProductsLast3Days(now, minusDays3);
    return top5ProductsLast3Days.stream().map(OrderCommand.Top5ProductsLast3DaysResponse::new).toList();
  }
```

```java
  @Transactional
  @Cacheable(value = "getProductList", key = "0")
  public List<Product> getProductList() {
    return productRepository.findAll();
  }
```

<img width="1272" alt="image" src="https://github.com/user-attachments/assets/d1c58aea-b7ce-418f-b0d2-edba9ab669dd">

- 인기판매상품 같은 경우는 실시간으로 변동이 존재할 수 있으며, 한 시간마다 캐시를 제거하면 큰 효과를 볼 수 없을 수도 있다고 판단해 3시간 TTL을 주어서 3시간마다 캐시가 삭제되도록 설정했습니다.
```java
    Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
    cacheConfigurations.put("getTop5ProductsLast3Days", cacheConfig.entryTtl(Duration.ofHours(3)));
```

- 상품목록조회 같은 경우에는 상품추가와 삭제, 수정이 일어나면 캐시가 제거되어야한다고 생각해서 삭제, 수정 API를 만들어   @CacheEvict 를 적용했습니다.

```java
  @Transactional
  @CacheEvict(value = "getProductList", key = "0")
  public void deleteProduct(Long productId) {
    productRepository.deleteById(productId);
  }

  @Transactional
  @CacheEvict(value = "getProductList", key = "0")
  public Product updateProduct(ProductCommand.Update command) {
    Product product = productRepository.findById(command.id()).orElseThrow(() -> new NotFoundException("상품", true));
    product.update(command.name(), command.stock(), command.price());
    return product;
  }
```


## 성능 테스트

### 상품 목록조회
```java
  @Test
  @DisplayName("상품 목록조회 캐시 성능 통합테스트")
  public void getProductList_cache__success() {
    CompletableFuture<?>[] futures = IntStream.range(0, 1000)
        .mapToObj(i -> CompletableFuture.runAsync(() -> transactionalHandler.runWithTransaction(() ->  productService.getProductList())))
        .toArray(CompletableFuture[]::new);

    CompletableFuture.allOf(futures).join();

  }
```
- 캐싱처리 되었을 때


![image](https://github.com/user-attachments/assets/420d4bfb-0b29-420a-911e-6e67048d467d)


- 캐싱처리 안되었을 때


![image](https://github.com/user-attachments/assets/3214b066-580a-4899-9ff8-8410f99b848a)




### 인기상품조회
```java
  @Test
  @DisplayName("인기상품조회 캐시 성능 통합테스트")
  public void getTop5ProductsLast3Days_cache__success() {
    //given
    //when
    CompletableFuture<?>[] futures = IntStream.range(0, 1000)
        .mapToObj(i -> CompletableFuture.runAsync(() -> transactionalHandler.runWithTransaction(() ->  orderService.getTop5ProductsLast3Days())))
        .toArray(CompletableFuture[]::new);

    CompletableFuture.allOf(futures).join();

  }
```


- 캐싱처리 했을 때

![image](https://github.com/user-attachments/assets/f900b03f-0bf3-4ae2-a402-89c3b47aebd5)


- 캐싱처리 안되었을 때

![image](https://github.com/user-attachments/assets/6570df25-46af-4287-a2e2-7cce302ee9b4)

