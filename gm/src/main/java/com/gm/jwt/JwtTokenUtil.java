package com.gm.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.gm.user.User;
import com.gm.user.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import static java.lang.System.currentTimeMillis;

@Component
public class JwtTokenUtil {

    static final long EXPIRATIONTIME = 864_000_000; // 10 days
    static final String SECRET = "ThisIsASecret";
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    public String generateToken(String username) {
        User user = userRepository.findByEmail(username);
        if (user != null) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("roles", user.getRole().getUserRole());
            claims.put("id", user.getId());

            return TOKEN_PREFIX + " " + Jwts.builder()
                    .setClaims(claims)
                    .setSubject(username)
                    .setExpiration(new Date(currentTimeMillis() + EXPIRATIONTIME))
                    .signWith(SignatureAlgorithm.HS512, SECRET)
                    .compact();
        }
        return null;
    }

    private Claims getBody(String token) {
        if (token != null) {
            try {
                return Jwts.parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                        .getBody();
            } catch (Exception exception) {
                return null;
            }
        }
        return null;
    }

    public String getUsername(String token) {
        Pair<String, Date> pair = getUsEx(token);
        if (pair != null) {
            return pair.getLeft();
        }
        return null;
    }

    public Date getExpiration(String token) {
        Pair<String, Date> pair = getUsEx(token);
        if (pair != null) {
            return pair.getRight();
        }
        return null;
    }

    public Pair<String, Date> getUsEx(String token) {
        Claims body = getBody(token);
        if (body != null) {
            return new Pair<>(body.getSubject(), body.getExpiration());
        }
        return null;
    }

    public Boolean validateToken(String token) {
        Date expiration = getExpiration(token);
        String username = getUsername(token);
        if (expiration != null && username != null) {
            return getExpiration(token).after(new Date(currentTimeMillis())) &&
                    userDetailsService.loadUserByUsername(username) != null;
        }
        return false;
    }

    public String refreshToken(String oldToken) {
        if (validateToken(oldToken)) {
            return generateToken(getUsername(oldToken));
        }
        return null;
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            String user = getUsername(token);

            String roles = getBody(token).get("roles", String.class);
            List<GrantedAuthority> grantedAuths =
                    AuthorityUtils.commaSeparatedStringToAuthorityList(roles);

            return user != null ?
                    new UsernamePasswordAuthenticationToken(user, null,
                            grantedAuths) :
                    null;
        }
        return null;
    }

    public String getUsername(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        return getUsername(token);
    }

    public Boolean validate(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        return validateToken(token);
    }
}