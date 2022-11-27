package com.mungwin.payngwinteller.security.security;

import com.mungwin.payngwinteller.domain.model.iam.App;
import com.mungwin.payngwinteller.domain.model.iam.Role;
import com.mungwin.payngwinteller.domain.repository.iam.AppRepository;
import com.mungwin.payngwinteller.domain.repository.iam.RoleRepository;
import com.mungwin.payngwinteller.security.contstants.RoleDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("appDetailsService")
@Transactional
public class AppDetailsService implements UserDetailsService {
    private AppRepository appRepository;

    @Autowired
    public void setAppRepository(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<App> optionalApp = appRepository.findFirstByEmail(username);
        if (optionalApp.isPresent()) {
            App app = optionalApp.get();
            Role role = RoleDefaults.findById(app.getRoleId().toString())
                    .orElseGet(Role::new);
            logger.info("Authenticated: {}", username);
            AppSecurityContextHolder.setPrincipal(new AppSecurityContextHolder.Principal(app, role));
            return new org.springframework.security.core.userdetails.User(
                    username, username, true, true, true, true,
                    getGrantedAuthorities(role.getAuthorities())
            );
        }
        throw new UsernameNotFoundException("Username: " + "not found");
    }
    private List<String> findUserRoleAuthorities(Role role) {
        return role.getAuthorities();
    }
    private List<GrantedAuthority> getGrantedAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
