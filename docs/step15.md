# 1. 인덱스를 적용할 API 

## 모든 조회 API 중 인덱스를 적용할 API 
- 유저로그인(유저정보조회)
- 유저잔액조회 
- 상품목록조회 ✅
- 상품상세조회 
- 인기판매조회 ✅
- 장바구니목록조회 ✅

## 적용
- 상품목록조회 : 상품목록조회 같은 경우는 검색이 가능하다고 생각했고 상품명이나, 금액에 인덱스를 적용시키면 검색시 더 빠른 조회를 기대
- 장바구니목록조회 : 장바구니 조회를 보통 User의 PK값으로 많이 하기 때문에 Cart 테이블에 user_id를 인덱스로 사용하면 성능적으로 기대 
- 인기판매조회 : 오늘로부터 3일전까지 제일 많이 팔린 상품을 조회하는데 쿼리는 아래와 같은데 

```sql
SELECT p.id AS productId, p.name AS productName, p.price AS productPrice, p.stock AS productStock, SUM(op.quantity) AS sumQuantity
FROM OrderProduct op
INNER JOIN Product p ON op.product_id = p.id
INNER JOIN `Order` o ON op.order_id = o.id
WHERE o.created_at <= :now AND o.created_at >= :minusDays3
GROUP BY p.id, p.name, p.price, p.stock
ORDER BY sumQuantity DESC, productId DESC
LIMIT 5;
```

## 미적용한 이유
- 유저로그인 : 유저로그인시 조회를 유저아이디로 하는데 유니크가 걸려서 크게 인덱스를 적용할 필요가 없다고 생각
- 유저잔액조회: 잔액조회같은 경우는 유저의 PK로 조회하기 때문에 필요 없다고 생각
- 상품사세조회: 상품PK로 조회하기 때문에 필요없다고 생각함


# 2. 인덱스 미적용 / 적용 비교
## 2-1 장바구니 목록조회
### ✅ 조건
- user_id 에 index를 적용
- 총 471384 건 데이터로 여러 유저들의 장바구니 데이터를 섞여 있을 수 있도록 데이터를 적재했다.
- 쿼리는 동일 한 쿼리로 진행한다.

```sql
explain
select *
from cart
where user_id = 1;
```

### ✅ 인덱스 미적용 / 적용 비교 
> 87ms

![스크린샷 2024-08-07 오후 11 17 24](https://github.com/user-attachments/assets/abeecaa5-d7e0-42b4-a00f-ce8cecce390b)


### ✅ 인덱스 적용
> 33ms

![image](https://github.com/user-attachments/assets/5169ea0e-771b-418f-90e7-2002667ec6ff)


### ✅ 결과
예상대로 조회에 걸린시간도 미미하긴 하지만 33ms까지 줄었고 type과 rows도 크게 성능이 증가했다.
`Type` : ALL => ref  
`rows` : 472629 => 236314  


## 2-2 상품목록조회
### ✅ 조건
- price와 name에 index를 적용
- 총 829591 건 데이터로 상품명, 금액은 중복되는 상품이 존재하도록 적재를 했다.
- 쿼리는 동일 한 쿼리로 진행한다.

- 상품명을 like로 조회
```sql
select *
from product
where name like '%꽃병5%';
```

- 금액을 between으로 조회
```sql
explain
select *
from product
where price between 5000 and 100000;
```

### ✅ 인덱스 미적용
- 상품명을 like로 조회
> 55ms

![image](https://github.com/user-attachments/assets/65ddac4a-ba3b-4520-b9b4-12abf7655c5d)

- 금액을 between으로 조회
> 157ms

![image](https://github.com/user-attachments/assets/6e9d4fe9-8b1f-4462-ace7-10328f8b60cf)



### ✅ 인덱스 적용
- 상품명을 like로 조회  
> 58ms

![image](https://github.com/user-attachments/assets/df6c2fea-5a29-4794-8c8f-47a90b679768)


- 금액을 between으로 조회
> 74ms

![image](https://github.com/user-attachments/assets/f00193ce-0246-41e9-b060-9e4895da6175)


### ✅ 결과
- `예상치 못한부분` : 상품명을 like로 `%상품%` `%상품`로 할 경우 인덱스를 걸어주기 전이랑 달라진거 없이 풀스캔을 탔고  `꽃병5%`로 해야지 `range`로 되면서 rows도 `39790` 까지 줄 수 있었다.
- 금액으로 betWeen 하는 쿼리의 rows가 778157 => 2184 로 확 줄었는데 예상보다 더 큰 인덱스를 본 것 같다.


## 2-1 상위상품조회
### ✅ 조건
- order 테이블에 created_at, OrderProduct 테이블에 order_id와 product_id 를 인덱스로 사용
- Order 테이블에 140000건 적재, OrderProduct 테이블에 140000건 적재
- 아래 쿼리로 동일하게 테스트

```sql
explain
select
    p.id,p.name, p.price, p.stock,
    sum(op.quantity) as sum_quantity
from order_product op
inner join product p on op.product_id = p.id
inner join orders o on  op.order_id = o.id
where o.created_at <= '2024-08-09 00:00:00' and  o.created_at >= '2024-08-06 00:00:00'
group by p.id,p.name, p.price, p.stock
order by sum_quantity desc
limit 5;

```


### ✅ 인덱스 미적용
> 303ms

![image](https://github.com/user-attachments/assets/c8245155-94d7-4761-a587-e2efd1e5aae4)

### ✅ 인덱스 적용
> 408ms

![image](https://github.com/user-attachments/assets/d1e057fe-a0b9-4506-b3a0-f63d3ed79072)


### ✅ 결과 
비록 order 테이블이 eq_ref => range로 떨어졌지만  orderProduct 테이블이 풀스캔에서 ref로 상승한거에 만족을 했다. 조회하는 rows도 50% 감속했고 나름 만족 했지만 
조회 속도가 303ms => 408ms 로 증가는 문제가 생겼다...

이는 특정 테이블의 인덱스가 효율적으로 적용되지 않았거나, 인덱스 적용으로 인해 다른 작업의 오버헤드가 증가했을 가능성 있을 수도 있다고 한다... 

