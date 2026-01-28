package tp11;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserService {

    private final ObservableList<User> users = FXCollections.observableArrayList();

    public ObservableList<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
    }
}
