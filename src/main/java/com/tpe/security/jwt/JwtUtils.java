package com.tpe.security.jwt;

import com.tpe.security.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
@Slf4j
public class JwtUtils
{

   private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);

   @Value("${backendapi.app.jwtSecret}")
   private String jwtSecret;

   @Value("${backendapi.app.jwtExpirationMs}")
    private long jwtExpirationMs;




  /* public void someMethod() {
       log.info("this is an error");
   }*/
    //generate jwt
    public String generateJwtToken(Authentication authentication)
    {
      UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal(); //It gives  successfully logg in user.

        return generateToken(userDetails.getUsername());

    }
    //creating valid jwt token
    public String generateToken(String email)
    {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512,jwtSecret)
                .compact();
    }


    //validate jwt
    public boolean validateJwtToken(String jwtToken)
    {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwtToken);
            return true;
        } catch (ExpiredJwtException e) {
           LOGGER.error("Jwt token is expired : {}",e.getMessage());
        } catch (UnsupportedJwtException e) {
            LOGGER.error("Jwt token is unsupported : {}",e.getMessage());
        } catch (MalformedJwtException e) {
            LOGGER.error("Jwt token is invalid : {}",e.getMessage());
        } catch (SignatureException e) {
            LOGGER.error("Jwt token signature is invalid : {}",e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.error("Jwt token is empty : {}",e.getMessage());
        }
        return false;
    }

    // getUsernameFrom jwt
    public String  getUsernameFromToken(String token)
    {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }



}
