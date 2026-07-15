package com.mycom.myapp.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.mycom.myapp.config.MyUserDetailsService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Component
@RequiredArgsConstructor
@Getter
@Slf4j
public class JwtUtil {

	private final MyUserDetailsService myUserDetailsService;
	
	// application.properties 의 myapp.jwt.secret 의 문자열로 현재 이 프로젝트는 관리
	// 실 운영은 절대 비추, github 도 비추, 배포전 배포 서버 환경변수에 등록, 환경변수를 사용하는 코드
	@Value("${myapp.jwt.secret}")
	private String secretKeyStr; // HS256 서명, 검증 key 문자열
	
	private SecretKey secretKey; // HS256 서명, 검증 key
	
	private final long tokenValidDuration = 1000L * 60 * 60 * 24; // 24 시간	
	
	// JwtUtil 생성 직후 호출
	@PostConstruct
	protected void init() {
		secretKey = new SecretKeySpec(
				secretKeyStr.getBytes(StandardCharsets.UTF_8),
				Jwts.SIG.HS256.key().build().getAlgorithm()
		);
	}
	
	// JWT 생성
	public String createToken(String username, List<String> roles) {
		Date now = new Date();
		
		return Jwts.builder()
				.subject(username)  // payload : 사용자 식별자 (sub)
				.claim("roles", roles) // payload: 사용자 역할 목록, 반복적으로 더 많은 데이터 추가 <= 공개 노출된다.
				.issuedAt(now)  // payload: 발급 시각 (iat)
				.expiration(new Date(now.getTime() + tokenValidDuration))
				.signWith(secretKey, Jwts.SIG.HS256)
				.compact();
	}
	
	// JWT 에서 사용자 식별자
	public String getUsernameFromToken(String token) {
		return Jwts.parser()
				.verifyWith(secretKey) // 전달되는 token 의 서명 검증
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject();
	}
	
	// 프론트가 전달하는 Token 을 Header 로부터 추출
	// 프론트와 상호 약속된 방식에 따라 프론트가 request 에 저장한 토큰을 꺼내는 작어
	// http header 에 X-AUTH-TOKEN 이름
	public String getTokenFromHeader(HttpServletRequest request) {
		return request.getHeader("X-AUTH-TOKEN");
	}
	
	// X-AUTH-TOKEN 대신 Authorization Bearer+빈칸하나 (앞자리 7자리 자르고 얻는 방법)
	
	// 서명 유효
	// _1 대비 서명의 유효 포함, Claims 리턴하도록 수정
	public Claims validateToken(String token) {
		try {
			// parser 를 통해서 Claims 객체를 얻고, 이를 통해서 검증
			Claims claims = Jwts.parser()
					.verifyWith(secretKey)
					.build()
					.parseSignedClaims(token)
					.getPayload();
			
			if( claims.getExpiration() != null && claims.getExpiration().before(new Date())) {
				// 현재 토큰의 만료일자가 지금보다 이전 => 만료
				return null;
			}
			
			return claims; // 유효
			
		}catch(Exception e) {
			return null;
		}
	}
	
	// DB Access 를 통한 2차 검증
	// token -> username 추출 
	// username, 권한 등을 DB Access 확인 <= MyUserDetailsService.loadUserByUsername()
	public UsernamePasswordAuthenticationToken getAuthentication(String token) {
		UserDetails userDetails = myUserDetailsService.loadUserByUsername(getUsernameFromToken(token));
		return new UsernamePasswordAuthenticationToken(
				userDetails.getUsername(), 
				"", 
				userDetails.getAuthorities());
	}
}







