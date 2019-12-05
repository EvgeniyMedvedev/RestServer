package boot.security;

import boot.service.TokenAuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;


public class JWTAuthorizationFilter extends AbstractAuthenticationProcessingFilter {

    public JWTAuthorizationFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("login");
        String password = new BCryptPasswordEncoder(11).encode(request.getParameter("password"));
        String pass = request.getParameter("password");

        System.out.printf("JWTAuthorizationFilter.attemptAuthentication: username/password= %s,%s", username, pass);
        System.out.println();

        return getAuthenticationManager()
                .authenticate(new UsernamePasswordAuthenticationToken(username, pass, Collections.emptyList()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) {

        System.out.println("JWTAuthorizationFilter.successfulAuthentication:");

        // Write Authorization to Headers of Response.
        TokenAuthenticationService.addAuthentication(response, authResult.getName());

        String authorizationString = response.getHeader("Authorization");

        System.out.println("Authorization String = " + authorizationString);
    }
}
