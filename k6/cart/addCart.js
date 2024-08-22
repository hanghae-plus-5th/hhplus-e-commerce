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

  // 장바구니추가 API 호출
  const res = http.post(`${DEFAULT_URL}/`, JSON.stringify({ productId: 40, quantity: 3 }), {headers});
  check(res, { '장바구니추가': (r) => r.status === 200 });

  sleep(1); // 사이에 휴식 시간을 둠
}