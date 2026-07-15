package com.mycom.myapp.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

	// jwt_1 프로젝트에서는 application.properties의 myapp.jwt.secret의 문자열로 관리하지만
	// 실제 운영은 절대 안 됨. 배포 전에 서버 환경변수에 등록 및 환경변수를 사용하는 코드로 운영

	@Value("${myapp.jwt.secret}")
	private String secretKeyStr; // HS256 서명, 검증 key 문자열
	private SecretKey secretKey; // HS256 서명, 검증 key
	private final long tokenValidDuration = 1000L * 60 * 60 * 24; // 24시간 유효

	// JwtUtil 생성 직후 호출
	@PostConstruct
	protected void init() {
		secretKey = new SecretKeySpec(
				secretKeyStr.getBytes(StandardCharsets.UTF_8),
				Jwts.SIG.HS256.key().build().getAlgorithm());
	}
	
	// Jwt 생성
	public String createToken(String username, List<String> roles) {
		Date now = new Date();
		
		return Jwts.builder()
				.subject(username)		// payload : 사용자 식별자 (sub) 
				.claim("roles", now)	// payload : 사용자 역할 목록 <- 공개 노출 
				.issuedAt(now)			// payload : 발급 시각 (iat)
				.expiration(new Date(now.getTime() + tokenValidDuration))
				.signWith(secretKey, Jwts.SIG.HS256)
				.compact();
	}
	
	// Jwt 에서 사용자 식별자
	public String getUsernameFromToken(String token) {
		return Jwts.parser()
				.verifyWith(secretKey) // 전달되는 token의 서명 검증
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject();
	}
	
	// 프론트가 전달하는 Token을 Header로부터 추출
	// 프론트와 상호 약속된 방식에 따라 프론트가 request에 저장한 토큰을 꺼내는 작업
	// http header에 X-AUTH-TOKEN 이름
	public String getTokenFromHeader(HttpServletRequest request) {
		return request.getHeader("X-AUTH-TOKEN");
	}
	/// X-AUTH-TOKEN 대신 Authorization Bearer + 빈칸하나 ::
	
	// 서명 유효
	public boolean validateToken(String token) {
		try {
			Date expiration = Jwts.parser()			
				.verifyWith(secretKey) // 전달되는 token의 서명 검증
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getExpiration();
			return expiration != null && expiration.after(new Date());
		} catch (Exception e) {
			return false; // ExpiredJwtException 등
		}
	}
}
