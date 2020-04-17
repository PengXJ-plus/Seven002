package mx.admin.modules.security.rest;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import mx.admin.aop.limit.AnonymousAccess;
import mx.admin.aop.log.Log;
import mx.admin.modules.security.security.AuthenticationInfo;
import mx.admin.modules.security.security.AuthorizationUser;
import mx.admin.modules.security.security.JwtUser;
import mx.admin.modules.security.utils.JwtTokenUtil;
import mx.admin.utils.EncryptUtils;
import mx.admin.utils.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author jie
 * @date 2018-11-23
 * 授权、根据token获取用户详细信息
 */
@Slf4j
@RestController
@RequestMapping("auth")
@Api(tags = "系统权限控制接口")
public class AuthenticationController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${loginCode.expiration}")
    private Long expiration;

    @Value("${rsa.private_key}")
    private String privateKey;

    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;

    public AuthenticationController(JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    /**
     * 登录授权
     * @param authUser
     * @return
     */
    @Log("用户登录")
    @ApiOperation("登陆授权")
    @AnonymousAccess
    @PostMapping(value = "/login")
    public ResponseEntity login(@Validated @RequestBody AuthorizationUser authUser){
        RSA rsa = new RSA(privateKey, null);
        String password = new String(rsa.decrypt(authUser.getPassword(), KeyType.PrivateKey));

        final JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(authUser.getUsername());
        String pw = EncryptUtils.encryptPassword(password);
        if(!jwtUser.getPassword().equals(EncryptUtils.encryptPassword(pw))){
            throw new AccountExpiredException("密码错误！");
        }

        if(!jwtUser.isEnabled()){
            throw new AccountExpiredException("账号已停用，请联系管理员！");
        }

        // 生成令牌
        final String token = jwtTokenUtil.generateToken(jwtUser);

        // 返回 token
        return ResponseEntity.ok(new AuthenticationInfo(token,jwtUser));
    }

    /**
     * 获取用户信息
     * @return
     */
    @GetMapping(value = "/info")
    public ResponseEntity getUserInfo(){
        UserDetails userDetails = SecurityContextHolder.getUserDetails();
        JwtUser jwtUser = (JwtUser)userDetailsService.loadUserByUsername(userDetails.getUsername());
        return ResponseEntity.ok(jwtUser);
    }



}
