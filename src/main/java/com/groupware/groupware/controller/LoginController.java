package com.groupware.groupware.controller;

import com.groupware.groupware.dto.LoginRequest;
import com.groupware.groupware.service.UsersDetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final UsersDetailsService usersDetailsService;

    public LoginController(AuthenticationManager authenticationManager, UsersDetailsService usersDetailsService) {
        this.authenticationManager = authenticationManager;
        this.usersDetailsService = usersDetailsService;
    }

    @PostMapping("/login_proc")
    public String processLoginForm(@RequestBody LoginRequest loginRequest) {
        try {
            UserDetails userDetails = usersDetailsService.loadUserByUsername(loginRequest.getUsername());
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, loginRequest.getPassword(), userDetails.getAuthorities());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:/dashboard";
        } catch (Exception e) {
            // 로그인 실패 처리
            return "redirect:/login?error";
        }
    }

    @GetMapping("/dashboard")
    public String showDashboard() {
        return "dashboard";
    }
}