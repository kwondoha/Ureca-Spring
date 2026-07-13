// 브라우저 쿠키에서 CSRF 토큰(XSRF-TOKEN) 값만 꺼내는 함수
// csrfFetch() 가 내부에서 호출함 — login.html 의 hidden _csrf 와 달리 "쿠키"에서 읽음
function getCsrfTokenFromCookie() {
  // document.cookie 예: "XSRF-TOKEN=abc-123; JSESSIONID=..."
  // 정규식 /XSRF-TOKEN=([^;]+)/ → "XSRF-TOKEN=" 뒤의 값을 찾음 (; 전까지)
  // match 가 있으면 [전체문자열, 캡처한값] 배열, 없으면 null
  const match = document.cookie.match(/XSRF-TOKEN=([^;]+)/);
  // match 있음 → match[1] 이 토큰 문자열 → decodeURIComponent 로 URL 인코딩 해제 후 반환
  // match 없음 → 쿠키에 XSRF-TOKEN 없음 → null 반환 (csrfFetch 는 헤더를 안 붙임)
  return match ? decodeURIComponent(match[1]) : null;
}
// CSRF 토큰을 자동으로 붙여서 fetch 호출하는 공통 함수
// 사용 예: csrfFetch('/api/boards')  또는  csrfFetch('/logout', { method: 'POST' })
async function csrfFetch(url, options = {}) {
  // 1) 브라우저 쿠키에서 XSRF-TOKEN 값 읽기 (로그인 직후 서버가 내려줌)
  const csrfToken = getCsrfTokenFromCookie();
  // 2) 호출자가 넘긴 headers 복사 (없으면 빈 객체)
  //    { ...options.headers } — spread: options.headers 의 key/value 를 새 객체에 펼쳐 복사
  //    (원본 options.headers 를 직접 수정하지 않기 위함 — 아래에서 X-XSRF-TOKEN 을 추가하므로)
  const headers = options.headers ? { ...options.headers } : {};
  // 3) 쿠키에 토큰이 있으면 요청 헤더에 추가 (Spring Security가 이 값을 검증함)
  //    GET은 필수는 아니지만, POST/PUT/DELETE 등 상태 변경 요청에는 반드시 필요
  if (csrfToken) {
    headers['X-XSRF-TOKEN'] = csrfToken;
  }
  // 4) fetch에 넘길 최종 옵션 조립
  const fetchOptions = {
    method: options.method || 'GET',           // method 미지정 시 GET
    credentials: 'same-origin',                // 같은 사이트 쿠키(세션)를 함께 전송
    headers: headers
  };
  // 5) body 등 나머지 옵션이 있으면 그대로 반영 (예: method: 'POST', body: ...)
  if (options.body !== undefined) {
    fetchOptions.body = options.body;
  }
  // 6) 실제 HTTP 요청 실행 후 Promise 반환 (호출한 쪽에서 await response.json() 등 처리)
  return fetch(url, fetchOptions);
}
// logout — board.html 로그아웃 버튼에서 호출
// ※ 별도의 LogoutController 는 없음
//    POST /logout 은 Spring Security LogoutFilter 가 처리 (SecurityConfig.logout() 설정)
//    성공 시 logoutSuccessHandler 가 JSON {"result":"success"} 응답
async function csrfLogout() {
    // csrfFetch: 쿠키의 XSRF-TOKEN → X-XSRF-TOKEN 헤더로 첨부 후 POST
    // (로그아웃도 세션 상태 변경이므로 CSRF 필요)
    let response = await csrfFetch('/logout', {
        method: 'POST'
    });
    let data = await response.json();
    console.log(data);
    if( data.result == "success" ){
        window.location.href = '/login.html';
    } else {
        alert('로그아웃에 실패했습니다. 다시 시도해주세요.');
    }
}