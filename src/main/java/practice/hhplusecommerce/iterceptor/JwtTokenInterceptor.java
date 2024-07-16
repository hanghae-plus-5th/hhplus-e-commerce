package practice.hhplusecommerce.iterceptor;

import static practice.hhplusecommerce.common.jwt.JwtTokenProvider.CLAIMS_KEY;
import static practice.hhplusecommerce.common.jwt.JwtTokenProvider.JWT_HEADER_KEY;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.naming.AuthenticationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import practice.hhplusecommerce.common.jwt.JwtTokenProvider;
import practice.hhplusecommerce.common.jwt.TokenInfoDto;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenInterceptor implements HandlerInterceptor {

  private final JwtTokenProvider jwtTokenProvider;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws AuthenticationException {

    String authorization = request.getHeader(JWT_HEADER_KEY);

    if (authorization == null) {
      throw new AuthenticationException("authorization가 없습니다.");
    }

    jwtTokenProvider.isBearerToken(authorization);

    String accessToken = authorization.substring(7);
    Claims decodeToken = jwtTokenProvider.getClaimsFormToken(accessToken);
    TokenInfoDto tokenInfo = new TokenInfoDto(decodeToken.get(CLAIMS_KEY).toString());
    request.setAttribute(JWT_HEADER_KEY, tokenInfo);
    return true;
  }
}
