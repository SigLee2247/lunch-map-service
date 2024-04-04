package com.wanted.lunchmapservice.common.security.service;

import com.wanted.lunchmapservice.common.security.vo.Principal;
import com.wanted.lunchmapservice.user.entity.User;
import com.wanted.lunchmapservice.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        return new Principal(user, createAuthority());
    }

    public List<SimpleGrantedAuthority> createAuthority() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
}
