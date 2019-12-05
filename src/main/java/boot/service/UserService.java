package boot.service;

import boot.model.User;

import java.util.List;

public interface UserService {
    List<User> getAll() ;

    void add(User t);

    User getById(int id);

    void delete(int id);

    void updateUser(User user);

    User getByLogin(String login);
}
