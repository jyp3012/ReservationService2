package zerobase.reservationservice2.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import zerobase.reservationservice2.service.MemberService;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {

    @Value("${spring.jwt.secret}")
    private String secretKey;

    private static final String KEY_ROLES = "roles";
    private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60; // 1 hour
    private final MemberService memberService;


    /*
     * 토근 생성(발급)
     */
    public String generateToken(String userId, String roles) {

        Claims claims = Jwts.claims().setSubject(userId);
        claims.put(KEY_ROLES, roles);


        var key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        //토큰 생성시간
        var now = new Date();
        //토큰 만료시간
        var expiredDate = new Date(now.getTime() + TOKEN_EXPIRE_TIME);


        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(key)
                .compact();
    }

    //jwt 토큰으로 부터 인증정보를 받아오는 메소드
    public Authentication getAuthentication(String jwt) {
        UserDetails userDetails = memberService.loadUserByUsername(getUserId(jwt));

        // spring 에서 지원하는 token
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // token 에 저장 돼 있는 useId 값을 가져오는 메소드
    public String getUserId(String token) {
        return this.parseClaims(token).getSubject();
    }

    // 토큰의 유효성 체크 ( 토큰이 비어 있는지, 토큰의 만료 시간이 현재 시간보다 전인지)
    public boolean validateToken(String token) {
        if (!StringUtils.hasText(token)) {
            return false;
        }

        var claims = this.parseClaims(token);
        return !claims.getExpiration().before(new Date());
    }

    // token 확인
    private Claims parseClaims(String token) {

        var key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            // TODO
            return e.getClaims();
        }
    }
}
