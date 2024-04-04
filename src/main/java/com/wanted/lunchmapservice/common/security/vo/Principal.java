package com.wanted.lunchmapservice.common.security.vo;

import io.jsonwebtoken.Claims;
import java.util.List;
import lombok.Getter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Getter
public class Principal extends User {

    private final Long id;

    public Principal(com.wanted.lunchmapservice.user.entity.User user, List<SimpleGrantedAuthority> authorities) {
        super(user.getUserName(), user.getPassword(), authorities);
        this.id = user.getId();
    }

    public Principal(Claims claims) {
        super(claims.getSubject(), "",
                AuthorityUtils.commaSeparatedStringToAuthorityList(claims.get("authorities", String.class)));
        this.id = claims.get("id", Long.class);
    }
}
