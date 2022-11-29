package com.home.MyWorkTime.service;

import com.home.MyWorkTime.entity.VasRoles;
import com.home.MyWorkTime.entity.VasUserModel;
import com.home.MyWorkTime.repository.VasUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashSet;
import java.util.Set;

public class VasUserModelDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private VasUserRepository vasUserRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        VasUserModel vasUserModel = vasUserRepository.findByUserName(username);

        Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>();

        for (VasRoles vasRole : vasUserModel.getVasRoles()){
            grantedAuthoritySet.add(new SimpleGrantedAuthority(String.valueOf(vasRole.getUser_level_access())));
        }
        return new User(vasUserModel.getUserName(), vasUserModel.getUserPassword(), grantedAuthoritySet);
    }
}
