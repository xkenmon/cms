package com.xkenmon.cms.admin.api;

import com.xkenmon.cms.admin.auth.JwtTokenProvider;
import com.xkenmon.cms.admin.dto.ApiMessage;
import com.xkenmon.cms.admin.dto.JwtAuthenticationResponse;
import com.xkenmon.cms.admin.dto.LoginRequest;
import com.xkenmon.cms.admin.dto.SignUpRequest;
import com.xkenmon.cms.admin.exception.ApiException;
import com.xkenmon.cms.admin.service.IUserService;
import com.xkenmon.cms.dao.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;

/**
 * @author bigmeng
 * @date 2018/8/9
 */
@RestController
@RequestMapping("/auth")
@Api("注册登录")
public class AuthApi {
    @Value("${auth.jwtExpirationInMs}")
    Long expirationInMs;

    private final
    AuthenticationManager authenticationManager;

    private final
    PasswordEncoder passwordEncoder;

    private final
    JwtTokenProvider tokenProvider;

    private final
    IUserService userService;

    @Autowired
    public AuthApi(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider, IUserService userService) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    @ApiOperation("用户登陆，token获取")
    @PostMapping
    public ApiMessage<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ApiMessage.success(new JwtAuthenticationResponse(jwt, expirationInMs));
    }

    @ApiOperation("用户注册")
    @PostMapping("signUp")
    public ApiMessage signUp(@Valid @RequestBody SignUpRequest signUpRequest) throws ApiException {
        User user = userService.selectByUserName(signUpRequest.getUsername());
        if (user != null) {
            throw new ApiException(400, "user exist!");
        }
        user = new User();
        user.setUserName(signUpRequest.getUsername());
        user.setUserPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setUserCreateTime(LocalDateTime.now());
        user.setUserEmail(signUpRequest.getEmail());
        userService.createUser(user);
        return ApiMessage.success(user.getUserId());
    }
}
