package com.example.hellosecurity;

import com.example.hellosecurity.dto.user.UserDto;
import com.example.hellosecurity.service.user.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class HelloSecurityApplication {

    public static final Logger logger = LogManager.getLogger(HelloSecurityApplication.class);
    @Autowired
    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(HelloSecurityApplication.class, args);
    }


    @EventListener(ApplicationReadyEvent.class)
    private void setUpUsers(){
        logger.info("Creating Basic Users for System");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userService.createUser(UserDto.builder().userName("Admin").password(encoder.encode("admin")).role("ROLE_ADMIN").build());
        userService.createUser(UserDto.builder().userName("userName").password(encoder.encode("userName")).role("ROLE_USER").build());
        userService.createUser(UserDto.builder().userName("user1").password(encoder.encode("user1")).role("ROLE_USER").build());
        logger.info("System Users Created");
    }
}



//@Entity
//@Getter
//@Setter
//@Table(name = "users")
//class User implements UserDetails  {
//
//    @Id
//    private String id;
//
//    @CreatedDate
//    private LocalDateTime createdAt;
//
//    @LastModifiedDate
//    private LocalDateTime modifiedAt;
//
//    private boolean enabled = true;
//
//    private String userName;
//    private String password;
//
//    private String fullName;
//
//    private Set<Role> authorities = new HashSet<>();
//
//    public User() {
//    }
//
//    public User(String userName, String password) {
//        this.userName = userName;
//        this.password = password;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return enabled;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return enabled;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return enabled;
//    }
//}

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//class Role implements GrantedAuthority {
//
//    public static final String USER_ADMIN = "USER_ADMIN";
//    public static final String AUTHOR_ADMIN = "AUTHOR_ADMIN";
//    public static final String BOOK_ADMIN = "BOOK_ADMIN";
//
//    private String authority;
//
//}
//@Repository
//interface UserRepository extends CrudRepository<User, String> {
//    Optional<User> findByUserName(String userName);
//}

//@EnableWebSecurity
////@EnableGlobalMethodSecurity()
//class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    private final UserRepository userRepo;
//
//    public SecurityConfig(UserRepository userRepo) {
//        this.userRepo = userRepo;
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userName -> userRepo
//                .findByUserName(userName)
//                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", userName)))
//        );
//    }
//
//    @Bean
//    PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.cors().and().csrf().disable();
//
//        http = http.sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and();
//
//        http = http.exceptionHandling()
//                .authenticationEntryPoint(
//                        (request, response, authException) -> { response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());}
//                ).and();
//
//        http.authorizeHttpRequests()
//                .antMatchers("/api/public/**").permitAll()
//                .antMatchers("/api/author/**").permitAll()
//                .antMatchers("/api/book/**").permitAll()
//                .antMatchers("/api/book/search").permitAll()
//                .anyRequest().authenticated();
//
//        http.addFilterBefore(
//
//                ,UsernamePasswordAuthenticationFilter.class
//        )
//    }
//
//
//}

//@Component
//class JwtTokenFilter extends OncePerRequestFilter {
//
//    private final JwtTokenUtil jwtTokenUtil;
//    private final UserRepository userRepo;
//
//    public JwtTokenFilter(JwtTokenUtil jwtTokenUtil,
//                          UserRepository userRepo) {
//        this.jwtTokenUtil = jwtTokenUtil;
//        this.userRepo = userRepo;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain chain)
//            throws ServletException, IOException {
//        // Get authorization header and validate
//        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
//        if (Strings.isEmpty(header) || !header.startsWith("Bearer ")) {
//            chain.doFilter(request, response);
//            return;
//        }
//
//        // Get jwt token and validate
//        final String token = header.split(" ")[1].trim();
//        if (!jwtTokenUtil.validate(token)) {
//            chain.doFilter(request, response);
//            return;
//        }
//
//        // Get userName identity and set it on the spring security context
//        UserDetails userDetails = userRepo
//                .findByUsername(jwtTokenUtil.getUsername(token))
//                .orElse(null);
//
//        UsernamePasswordAuthenticationToken
//                authentication = new UsernamePasswordAuthenticationToken(
//                userDetails, null,
//                userDetails == null ?
//                        List.of() : userDetails.getAuthorities()
//        );
//
//        authentication.setDetails(
//                new WebAuthenticationDetailsSource().buildDetails(request)
//        );
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        chain.doFilter(request, response);
//    }
//
//}
