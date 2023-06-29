package com.groupware.groupware.controller;

import com.groupware.groupware.dto.LoginRequest;
import com.groupware.groupware.entity.Users;
import com.groupware.groupware.repository.UsersRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.groupware.groupware.users.Role;
import org.springframework.transaction.annotation.Transactional;

@Controller
@RequestMapping("/users/login")
public class LoginController {

    private final AuthenticationProvider authenticationProvider;
    private final UserDetailsService userDetailsService;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginController(AuthenticationProvider authenticationProvider, UserDetailsService userDetailsService, UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.authenticationProvider = authenticationProvider;
        this.userDetailsService = userDetailsService;
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ModelAndView showLoginPage(@RequestParam(required = false) String error) {
        ModelAndView modelAndView = new ModelAndView("login");
        if (error != null) {
            modelAndView.addObject("error", true);
        }
        return modelAndView;
    }

    @PostMapping("/login_proc")
    public String processLoginForm(@ModelAttribute("loginRequest") LoginRequest loginRequest) {
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsersId());
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, loginRequest.getUsersPassword(), userDetails.getAuthorities());
            Authentication authentication = authenticationProvider.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:/users/dashboard";
        } catch (Exception e) {
            return "redirect:/users/login?error";
        }
    }

    @GetMapping("/dashboard")
    public String showDashboardPage() {
        return "redirect:/dashboard.html";
    }

}

@Controller
@RequestMapping("/users/register")
@Transactional
class RegistrationController {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public ModelAndView showRegistrationPage() {
        return new ModelAndView("registration.html");
    }

    @PostMapping("/register")
    public ModelAndView registerUser(@ModelAttribute("user") Users user) {
        String encodedPassword = passwordEncoder.encode(user.getUsersPassword());
        user.setUsersPassword(encodedPassword);
        user.setRole(Role.ADMIN);

        usersRepository.save(user);

        return new ModelAndView("redirect:/users/register/registration-success");
    }

    @GetMapping("/registration-success")
    public ModelAndView showRegistrationSuccessPage() {
        return new ModelAndView("registration_success.html");
    }
}
