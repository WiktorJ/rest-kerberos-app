package test;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by wjurasz on 18.07.17.
 */
public class DummyUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        switch (s.split("@")[0]) {
            case "admin":
                return new User("admin", "admin123", true, true, true, true,
                        AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN"));
            case "test":
                return new User("test", "ala123", true, true, true, true,
                        AuthorityUtils.createAuthorityList("ROLE_USER"));
            default:
                throw new UsernameNotFoundException("There is not user " + s + " registered in this AMAZING application!");
        }
    }
}
