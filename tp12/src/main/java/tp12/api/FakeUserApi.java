package tp12.api;

import tp12.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FakeUserApi implements UserApi {

    private String validToken;
    private final List<User> storage = new ArrayList<>();

    private void simulateNetworkDelay() throws InterruptedException {
        Thread.sleep(700); // délai réaliste
    }

    @Override
    public String login(String username, String password) throws Exception {
        simulateNetworkDelay();

        if ("admin".equals(username) && "admin".equals(password)) {
            validToken = UUID.randomUUID().toString();
            return validToken;
        }

        throw new Exception("Identifiants invalides.");
    }

    @Override
    public List<User> fetchUsers(String token) throws Exception {
        simulateNetworkDelay();
        checkToken(token);

        // On renvoie une copie pour simuler un vrai backend
        return new ArrayList<>(storage);
    }

    @Override
    public User createUser(String token, User user) throws Exception {
        simulateNetworkDelay();
        checkToken(token);

        storage.add(user);
        return user;
    }

    private void checkToken(String token) throws Exception {
        if (token == null || !token.equals(validToken)) {
            throw new Exception("Non autorisé (token invalide).");
        }
    }
}
