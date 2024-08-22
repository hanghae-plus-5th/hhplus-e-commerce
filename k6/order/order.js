import http from 'k6/http';
import { check, sleep } from 'k6';

import {options, DEFAULT_URL} from "../common/option.js";

export { options };

export default function () {
  //로그인해서 토큰 얻기
  const loginRes = http.post(`${DEFAULT_URL}/api/user/login`, JSON.stringify({
    name: '백현명'
  }), { headers: { 'Content-Type': 'application/json' } });

  check(loginRes, { 'login succeeded': (r) => r.status === 200 });

  const authToken = loginRes.json('token');

  const headers = {
    'Authorization': `Bearer ${authToken}`,
    'Content-Type': 'application/json'
  };

  // 주문하기 API 호출
  const orderPayload = {
    productList: [
      {
        id: 40,
        quantity: 1,
      }
    ]
  };

  const res = http.post(`${DEFAULT_URL}/api/order`, JSON.stringify(orderPayload), {headers});
  check(res, { '주문': (r) => r.status === 200 });
}