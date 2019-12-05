package boot.service;

import boot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository dao;

    @Autowired
    public UserDetailsServiceImpl(UserRepository dao) {
        this.dao = dao;

    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        return dao.findByLogin(login);
    }
}
