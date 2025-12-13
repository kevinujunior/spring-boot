package com.sb.blogapp.config;
import com.sb.blogapp.service.UserService;
import com.sb.blogapp.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component @RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, jakarta.servlet.ServletException {
        final String authHeader = request.getHeader("Authorization");
        String username = null;
        String token = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            if (jwtUtil.validate(token)) {
                username = jwtUtil.extractUsername(token);
            }
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            String finalUsername = username;
            UserDetails userDetails = new UserDetails() {
                private final User u = userService.findByUsername(finalUsername).orElse(null);
                @Override public java.util.Collection<? extends org.springframework.security.core.GrantedAuthority> getAuthorities() { return java.util.List.of(); }
                @Override public String getPassword() { return u == null ? null : u.getPassword(); }
                @Override public String getUsername() { return finalUsername; }
                @Override public boolean isAccountNonExpired() { return true; }
                @Override public boolean isAccountNonLocked() { return true; }
                @Override public boolean isCredentialsNonExpired() { return true; }
                @Override public boolean isEnabled() { return true; }
            };
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        chain.doFilter(request, response);
    }
}
