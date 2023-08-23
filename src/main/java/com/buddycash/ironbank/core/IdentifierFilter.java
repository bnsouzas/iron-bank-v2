package com.buddycash.ironbank.core;

import com.buddycash.ironbank.core.exceptions.AccountIdNotFoundException;
import com.buddycash.ironbank.core.exceptions.AccountInactiveException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Optional;

@Component
public class IdentifierFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var req = (HttpServletRequest) servletRequest;
        var accoundId = Optional.ofNullable(req.getHeader("x-account-id"));
        var accountActive = Optional.ofNullable(req.getHeader("x-account-active"))
                .map(active -> active.equals("true"));
        accoundId.orElseThrow(AccountIdNotFoundException::new);
        accountActive.orElseThrow(AccountInactiveException::new);
        accountActive.ifPresent((active) -> {
            if (!active) throw new AccountInactiveException();
        });
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
