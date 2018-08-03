package services;

import persistence.models.User;

public interface UserService {
    public Long addUser(User user);
    public User getUser(Long id);
    public User authenticate(String email,String password);
}
