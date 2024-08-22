import http from 'k6/http';
import { check, sleep } from 'k6';

import {options, DEFAULT_URL} from "../common/option.js";

export { options };

export default function () {
  //상위상품조회(인기상품조회) API 호출
  const res = http.get(`${DEFAULT_URL}/api/product/top`);
  check(res, { '상위상품조회': (r) => r.status === 200 });

  sleep(1); // 사이에 휴식 시간을 둠
}