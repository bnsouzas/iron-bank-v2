package com.buddycash.ironbank.server;

import com.buddycash.ironbank.server.exceptions.AccountIdNotFoundException;
import com.buddycash.ironbank.server.exceptions.AccountInactiveException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class IdentifierFilter extends GenericFilterBean {
  @Override
  public void doFilter(
      ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
    try {
      var req = (HttpServletRequest) servletRequest;
      if (!req.getRequestURI().contains("/actuator")
          && !req.getRequestURI().contains("/favicon.ico")) {
        var accoundId = Optional.ofNullable(req.getHeader("x-account-id"));
        var accountActive =
            Optional.ofNullable(req.getHeader("x-account-active"))
                .map(active -> active.equals("true"));
        accoundId.orElseThrow(AccountIdNotFoundException::new);
        accountActive.orElseThrow(AccountInactiveException::new);
        accountActive.ifPresent(
            (active) -> {
              if (!active) {
                throw new AccountInactiveException();
              }
            });
      }
    } catch (AccountIdNotFoundException | AccountInactiveException ex) {
      var res = (HttpServletResponse) servletResponse;
      res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      res.getWriter().write("An error occurred while processing your request.");
    }

    filterChain.doFilter(servletRequest, servletResponse);
  }
}
