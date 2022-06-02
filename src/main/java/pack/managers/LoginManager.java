package pack.managers;

import pack.Facade;
import pack.entities.TypeUtilisateur;
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
import javax.rmi.CORBA.Util;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

class UserMinimalData {
    public int id;
    public String username;
    public String password;
    public TypeUtilisateur type;

    public UserMinimalData(Utilisateur u) {
        this.id = u.getId();
        this.username = u.getUsername();
        this.password = u.getPassword();
        this.type = u.getType();
    }
}

@Singleton
@Startup
@DependsOn({"Facade"})
public class LoginManager {

    public CachedObject<Map<String, UserMinimalData>> users;

    @EJB
    private Facade facade;
    private Map<String, HttpSession> sessions = new HashMap<>();

    @PostConstruct
    public void init() {
        users = new CachedObject<>(() -> facade.getUsers()
                .parallelStream()
                .collect(Collectors.toMap(Utilisateur::getUsername, UserMinimalData::new)));
    }

    public void invalidate() {
        users.invalidate();
    }

    public boolean usernameExists(String username) {
        return users.getObject().containsKey(username);
    }

    public TypeUtilisateur login(String username, String password, HttpSession session) {
        if(!usernameExists(username)) {
            return TypeUtilisateur.NONE;
        }

        UserMinimalData user = users.getObject().get(username);
        if(!user.password.equals(MD5Hash.hash(password))) {
            return TypeUtilisateur.NONE;
        }

        session.setAttribute("user", username);
        session.setMaxInactiveInterval(600);
        sessions.put(user.username, session);

        return user.type;
    }

    public void logout(HttpSession session) {
        Utilisateur user = getSessionUser(session);
        if(user == null) {
            return;
        }

        session.removeAttribute("user");
        sessions.remove(user.getUsername());
    }

    public Utilisateur getSessionUser(HttpSession session) {
        String username = (String) session.getAttribute("user");

        if(!sessions.containsKey(username)) {
            return null;
        }
        if(!sessions.get(username).getId().equals(session.getId())) {
            return null;
        }

        UserMinimalData umd = users.getObject().get(username);
        return facade.getUser(umd.id);
    }
}
