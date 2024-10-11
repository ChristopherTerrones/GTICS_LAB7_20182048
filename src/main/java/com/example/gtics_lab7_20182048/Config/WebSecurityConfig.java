package com.example.gtics_lab7_20182048.Config;

import com.example.gtics_lab7_20182048.Repository.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;

import javax.sql.DataSource;
import jakarta.servlet.http.HttpSession;

@Configuration
public class WebSecurityConfig {
    private final DataSource dataSource;
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public WebSecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsManager users(DataSource dataSource) {
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        String sql1 = "select email as username, password, true from users where email = ?";
        String sql2 = "select u.email as username, r.name as authority " +
                "from users u inner join roles r on u.roleId = r.id where u.email = ?";

        users.setUsersByUsernameQuery(sql1);
        users.setAuthoritiesByUsernameQuery(sql2);
        return users;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, UsuarioRepository usuarioRepository) throws Exception {
        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/processLogin")
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler((request, response, authentication) -> {
                    HttpSession session = request.getSession();
                    DefaultSavedRequest defaultSavedRequest = (DefaultSavedRequest) session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
                    session.setAttribute("usuario", usuarioRepository.findUsuarioByEmail(authentication.getName()));

                    if (defaultSavedRequest != null) {
                        String targetUrl = defaultSavedRequest.getRedirectUrl();
                        redirectStrategy.sendRedirect(request, response, targetUrl);
                    } else {
                        String role = "";
                        for (GrantedAuthority authority : authentication.getAuthorities()) {
                            role = authority.getAuthority();
                            break;
                        }
                        switch (role) {
                            case "ADMIN":
                                response.sendRedirect("/reservas");
                                break;
                            case "GERENTE":
                                response.sendRedirect("/salas");
                                break;
                            case "CLIENTE":
                                response.sendRedirect("/obras");
                                break;
                            default:
                                response.sendRedirect("/login");
                        }
                    }
                })
                .failureUrl("/login?error=true")
                .permitAll();

        http.authorizeHttpRequests()
                .requestMatchers("/funciones/**").hasAnyAuthority("ADMIN", "GERENTE")
                .requestMatchers("/salas/**").hasAuthority("GERENTE")
                .requestMatchers("/reservas/**").hasAnyAuthority("ADMIN", "CLIENTE")
                .requestMatchers("/obras/**").hasAuthority("CLIENTE")
                .anyRequest().permitAll();

        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true);

        return http.build();
    }
}


