package pack.managers;

import pack.Facade;
import pack.entities.Utilisateur;
import pack.util.Base16Encoder;
import pack.util.CachedObject;
import pack.util.CachedObjectProvider;
import pack.util.MD5Hash;

import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Singleton
@Startup
@DependsOn({"Facade"})
public class LoginManager {

    public CachedObject<Map<String, Utilisateur>> users;

    @EJB
    private Facade facade;

    @PostConstruct
    public void init() {
        users = new CachedObject<>(() -> facade.getUsers()
                .parallelStream()
                .collect(Collectors.toMap(Utilisateur::getUsername, x -> x)));
    }

    public void invalidate() {
        users.invalidate();
    }

    public boolean usernameExists(String username) {
        return users.getObject().containsKey(username);
    }

    public Utilisateur correctCredentials(String username, String password) {
        if(!usernameExists(username)) {
            return null;
        }

        Utilisateur user = users.getObject().get(username);
        if(!user.getPassword().equals(MD5Hash.hash(password))) {
            return null;
        }

        return user;
    }
}
