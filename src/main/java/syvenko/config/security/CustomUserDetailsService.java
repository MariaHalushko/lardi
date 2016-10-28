package syvenko.config.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import syvenko.model.Account;
import syvenko.model.services.AccountService;

import java.util.Collection;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountService.findByLogin(username);
        if (account == null) {
            throw new UsernameNotFoundException(String.format("User %s does not exist!", username));
        }
        return new UserRepositoryUserDetails(account);
    }

    private final static class UserRepositoryUserDetails extends Account implements UserDetails {

        private UserRepositoryUserDetails(Account account) {
            super(account);
        }

        @Override
        @JsonIgnore
        public Collection<? extends GrantedAuthority> getAuthorities() {
            //In our case there is only one role
            return AuthorityUtils.createAuthorityList("ROLE_ACCOUNT");
        }

        @Override
        @JsonIgnore
        public String getUsername() {
            return getLogin();
        }

        @Override
        @JsonIgnore
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        @JsonIgnore
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        @JsonIgnore
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        @JsonIgnore
        public boolean isEnabled() {
            return true;
        }

    }

}
