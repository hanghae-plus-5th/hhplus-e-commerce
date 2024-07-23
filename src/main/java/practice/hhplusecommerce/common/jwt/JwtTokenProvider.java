package practice.hhplusecommerce.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.xml.bind.DatatypeConverter;
import java.util.Base64;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtTokenProvider {

  @Value("${jwt.secret-key}")
  private String SECRET_KEY;

  public final static String JWT_HEADER_KEY = "Authorization";
  public final static String BEARER = "Bearer ";
  public final static String CLAIMS_KEY_USER_NAME = "username";
  public final static String CLAIMS_KEY_USER_ID = "userId";

  private static final Long TokenValidTime = 1000L * 60;  //1분

  @PostConstruct
  protected void init() {
    SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
  }

  public String createAccessToken(String username, Long userId) {
    Claims claims = Jwts.claims();//.setSubject(userPk); // JWT payload 에 저장되는 정보단위
    claims.put(CLAIMS_KEY_USER_NAME, username);
    claims.put(CLAIMS_KEY_USER_ID, userId);
    Date now = new Date();
    return Jwts.builder()
        .setClaims(claims) // 정보 저장
        .setIssuedAt(now) // 토큰 발행 시간 정보
        .setExpiration(new Date(now.getTime() + TokenValidTime * 30)) // 30분
        .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // 사용할 암호화 알고리즘과
        .compact();
  }

  //AccessToken 유효성 검사
  public boolean isValidAccessToken(String token) {

    try {
      getClaimsFormToken(token);
      log.error("isValidAccessToken.accessToken : " + token);
    } catch (ExpiredJwtException exception) {
      log.error("isValidAccessToken.ExpiredJwtException : " + exception.getMessage());
      throw new ExpiredJwtException(exception.getHeader(), exception.getClaims(), exception.getMessage());
    } catch (JwtException exception) {
      log.error("isValidAccessToken.JwtException : " + exception.getMessage());
      throw new JwtException(exception.getMessage());
    }
    return true;
  }

  //JWT 구문분석 함수
  public Claims getClaimsFormToken(String token) {
    return Jwts.parser()
        .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
        .parseClaimsJws(token)
        .getBody();
  }

  public boolean isBearerToken(String authorization) {
    String accessToken = authorization.substring(7);

    if (!authorization.startsWith(BEARER)) {
      log.error("isBearerToken authorization : {} : ", authorization);
      throw new IllegalArgumentException("Bearer 를 붙혀주세요.");
    }

    if (!isValidAccessToken(accessToken)) {
      log.error("isBearerToken authorization : {} : ", authorization);
      throw new IllegalArgumentException("유효하지 않은 않은 토큰입니다.");
    }

    return true;
  }
}
