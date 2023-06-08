package ingemedia.proyectos.aula.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Security;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.mvc.condition.ProducesRequestCondition;

import ingemedia.proyectos.aula.exceptions.BadRequestException;
import ingemedia.proyectos.aula.responses.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.SignatureException;
import io.swagger.v3.core.util.Json;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  @Autowired
  private JwtService jwtService;
  @Autowired
  private UserDetailsService userDetailsService;
  // private final TokenRepository tokenRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    try {
      final String authHeader = request.getHeader("Authorization");
      final String jwt;
      final String userEmail;
      if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        filterChain.doFilter(request, response);
        return;
      }

      jwt = authHeader.substring(7);
      userEmail = jwtService.extractUsername(jwt);

      if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
        // var isTokenValid = tokenRepository.findByToken(jwt)
        // .map(t -> !t.isExpired() && !t.isRevoked())
        // .orElse(false);

        // verificar si el token expiro
        // System.out.println("token expirado? " + jwtService.isTokenExpired(jwt));
        if (jwtService.isTokenExpired(jwt)) {
          System.out.println("Token expirado, genere uno nuevo");
          // return "Token expirado";

        }

        if (jwtService.isTokenValid(jwt, userDetails)) {
          UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
              userDetails,
              null,
              userDetails.getAuthorities());
          authToken.setDetails(
              new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authToken);
        }
      }
      // filterChain.doFilter(request, response);

      filterChain.doFilter(request, response);

    } catch (ExpiredJwtException e) {
      System.out.println("token expirado");
      String message = e.getMessage();
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

      ErrorResponse errorResponse = new ErrorResponse("El token ha expirado ");
      response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
    } catch (SignatureException e) {
      System.out.println("Token invalido");
      String message = e.getMessage();
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      // response.addHeader("Content-Type", "application/json");
      ErrorResponse errorResponse = new ErrorResponse("Token invalido");
      response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));

    } catch (DecodingException e) {
      System.out.println("Error en la decodificacion del token");
      String message = e.getMessage();
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      ErrorResponse errorResponse = new ErrorResponse("Error en la decodificacion del token");
      response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));

    } catch (JsonProcessingException e) {
      System.out.println("Error de procesamiento de datos JSON: " + e.getMessage());
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

      ErrorResponse errorResponse = new ErrorResponse("Error de procesamiento de datos JSON: " + e.getMessage());
      response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
    }

  }
}
