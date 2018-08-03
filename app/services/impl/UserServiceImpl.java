package services.impl;

import persistence.models.User;
import services.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public Long addUser(User user) {
        user.id = 1L;
        return new Long(1);
    }

    @Override
    public User authenticate(String email, String password) {
        User u = new User();
        u.id = 1L;
        u.email = "dizhihui@gmail.com";
        u.name = "zhihui";
        return u;
    }

    @Override
    public User getUser(Long id) {
        User user = new User();
        user.id = 1L;
        return user;
    }
}
