package tp12.api;

import tp12.model.User;
import java.util.List;

public interface UserApi {
    String login(String username, String password) throws Exception;
    List<User> fetchUsers(String token) throws Exception;
    User createUser(String token, User user) throws Exception;
}
