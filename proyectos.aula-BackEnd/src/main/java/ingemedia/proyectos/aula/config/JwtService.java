package ingemedia.proyectos.aula.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import ingemedia.proyectos.aula.request.Rol;
import ingemedia.proyectos.aula.responses.UsuarioResponseJwt;

@Service
public class JwtService {

  private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public String generateToken(UserDetails userDetails, String codigo, String nombre, String apellido, String correo) {
    Map<String, Object> claims = new HashMap<>();
    System.out.println("info pal usuario " + userDetails);
    System.out.println("ROL? : " + userDetails.getAuthorities().stream().findFirst().get().getAuthority());
    System.out.println("Usuario? :" + userDetails.getClass().toString());
    claims.put("rol", userDetails.getAuthorities().stream().findFirst().get().getAuthority());
    // agregar el codigo de usuario al token
    Rol rol = Rol.valueOf(userDetails.getAuthorities().stream().findFirst().get().getAuthority());
    // claims.put("codigo", codigo);
    // claims.put("nombre", nombre);
    // claims.put("apellido", apellido);
    System.out.println("CORREO: " + correo);
    UsuarioResponseJwt user = new UsuarioResponseJwt(codigo, nombre, apellido, correo, rol);
    claims.put("usuario", user);
    return generateToken(claims, userDetails);
  }

  public String generateToken(
      Map<String, Object> extraClaims,
      UserDetails userDetails) {
    return Jwts
        .builder()
        .setClaims(extraClaims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 6))
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  // public boolean isTokenExpired(String token) {
  // return extractExpiration(token).before(new Date());
  // }

  public boolean isTokenExpired(String token) {
    try {
      final Date expiration = extractExpiration(token);
      return expiration.before(new Date());
    } catch (ExpiredJwtException e) {
      return true;
    }
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
    return Jwts
        .parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
