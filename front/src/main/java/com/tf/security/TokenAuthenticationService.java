package com.tf.security;
import com.tf.entity.TAdmin;
import com.tf.utils.CurrentUser;
import com.tf.utils.StringUtil;
import com.tf.web.config.YxdConfig;
import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
public class TokenAuthenticationService {
    public String addAuthentication(String username,
                                    String permissionNames,
                                    String phoneNum,
                                    Integer userId) {
        return Jwts.builder()
                // 保存权限（角色）
                .claim("userId", userId)
                .claim("permissionNames", permissionNames)
                .claim("phoneNum", phoneNum)
                // 用户名写入标题
                .setSubject(username)
                // 有效期设置
                .setExpiration(new Date(System.currentTimeMillis() + YxdConfig.EXPIRATION_TIME))
                // 签名设置
                .signWith(SignatureAlgorithm.HS512, YxdConfig.TOKEN_SECRET)
                .compact();
    }

    // JWT验证方法
    static Authentication getAuthentication(HttpServletRequest request) {
        // 从Header中拿到token
        String token = request.getHeader(YxdConfig.TOKEN_HEADER_STRING);
        if (StringUtil.isNotEmpty(token)) {
            String phoneNum = new String();
            String username = new String();
            Long userId = new Long(0);
            String permissionNames = new String();
            // 解析 Token
            try {
                Claims claims = Jwts.parser()
                        // 验签
                        .setSigningKey(YxdConfig.TOKEN_SECRET)
                        // 去掉 Bearer
                        .parseClaimsJws(token.replace(YxdConfig.TOKEN_SECRET, ""))
                        .getBody();
                // 拿用户名
                username = claims.getSubject();
                userId = Long.valueOf(String.valueOf(claims.get("userId")));
                permissionNames = String.valueOf(claims.get("permissionNames"));
                phoneNum = String.valueOf(claims.get("phoneNum"));

            } catch (ExpiredJwtException e) {
                System.out.println("Token过期:" + e);
                return null;
            } catch (MalformedJwtException e) {
                System.out.println("Token长度过短:" + e);
                return null;
            } catch (SignatureException e) {
                System.out.println("Token签名异常:" + e);
                return null;
            }
            // 获取用户信息供业务层使用
            request.setAttribute("userId", userId);
            request.setAttribute("phoneNum", phoneNum);
            request.setAttribute("permissionNames", permissionNames);
            request.setAttribute("username", username);
            //设置用户
            TAdmin admin = new TAdmin();
            admin.setId(Integer.parseInt(userId.toString()));
            admin.setTel(phoneNum);
            admin.setTruename(username);
            admin.setNickname(username);
            CurrentUser.set(admin);

            // 返回验证令牌
            return (phoneNum != null) ?
                    new UsernamePasswordAuthenticationToken(phoneNum, userId) :
                    null;
        }
        return null;
    }
}