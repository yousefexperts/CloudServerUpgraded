package co.insight.starter.firebase.creator.firebase;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.spring.insight.api.cases.service.FirebaseService;
import com.spring.insight.api.cases.service.exception.FirebaseTokenException;
import com.spring.insight.api.cases.service.exception.FirebaseTokenExceptionMessages;
import com.spring.insight.api.cases.web.rest.util.StringUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FirebaseFilter extends OncePerRequestFilter {

    private static String HEADER_NAME = "X-Authorization-Firebase";
    private FirebaseService firebaseService;
    public FirebaseFilter(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String xAuth = request.getHeader(HEADER_NAME);
        if (StringUtil.isBlank(xAuth)) {
            throw new FirebaseTokenException(FirebaseTokenExceptionMessages.TOKEN_HEADER_NOT_FOUND);
        } else {
            FirebaseTokenHolder holder = firebaseService.parseToken(xAuth);
            Authentication auth = new FirebaseAuthenticationToken(holder.getUid(), holder);
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request, response);
        }
    }
}
