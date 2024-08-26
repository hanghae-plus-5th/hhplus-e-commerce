import http from 'k6/http';
import { check, sleep } from 'k6';

import {options, DEFAULT_URL} from "../common/option.js";

export { options };

export default function () {

  //로그인
  const loginRes = http.post(`${DEFAULT_URL}/api/user/login`, JSON.stringify({
    name: '백현명'
  }), { headers: { 'Content-Type': 'application/json' } });

  check(loginRes, { 'login succeeded': (r) => r.status === 200 });

  const authToken = loginRes.json('token');

  const headers = {
    'Authorization': `Bearer ${authToken}`,
    'Content-Type': 'application/json'
  };

  // 상품상세조회 API 호출
  let res = http.get(`${DEFAULT_URL}/api/product`);
  check(res, { '상품목록조회': (r) => r.status === 200 });

  // 상품 목록에서 랜덤으로 상품 ID 선택
  const products = res.json(); // 상품 목록을 JSON 형식으로 파싱
  const randomProduct = products[Math.floor(Math.random() * products.length)]; // 랜덤으로 상품 선택
  const productId = randomProduct.id; // 선택된 상품의 ID 가져오기

  // 상품상세조회 API 호출
   res = http.get(`${DEFAULT_URL}/api/product/${productId}`);
  check(res, { '상품상세조회': (r) => r.status === 200 });

  // 장바구니추가 API 호출
   res = http.post(`${DEFAULT_URL}/`, JSON.stringify({ productId: productId, quantity: 3 }), {headers});
  check(res, { '장바구니추가': (r) => r.status === 200 });

  // 장바구니조회 API 호출
  res = http.get(`${DEFAULT_URL}/api/cart`, {headers});
  check(res, { '장바구니조회': (r) => r.status === 200 });

  // 주문하기 API 호출
  const orderPayload = {
    productList: [
      {
        id: productId,
        quantity: 1,
      }
    ]
  };

  res = http.post(`${DEFAULT_URL}/api/order`, JSON.stringify(orderPayload), {headers});
  check(res, { '주문': (r) => r.status === 200 });

  sleep(1); // 사이에 휴식 시간을 둠
}