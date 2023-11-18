package com.sparta.rollingpaper.secuity;

import com.sparta.rollingpaper.entity.User;
import com.sparta.rollingpaper.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new UsernameNotFoundException("등록된 사용자가 아닙니다: " + userId));

        return new UserDetailsImpl(user);
    }}
