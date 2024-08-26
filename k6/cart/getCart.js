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

  // 장바구니조회 API 호출
  const res = http.get(`${DEFAULT_URL}/api/cart`, {headers});
  check(res, { '장바구니조회': (r) => r.status === 200 });

  sleep(1); // 사이에 휴식 시간을 둠
}