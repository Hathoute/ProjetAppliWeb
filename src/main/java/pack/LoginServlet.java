package pack;

import pack.entities.TypeUtilisateur;
import pack.entities.Utilisateur;
import pack.managers.LoginManager;
import pack.managers.RoutingManager;
import pack.util.MD5Hash;

import javax.ejb.EJB;
import javax.rmi.CORBA.Util;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {

    @EJB
    private Facade facade;
    @EJB
    private RoutingManager routingManager;
    @EJB
    private LoginManager loginManager;

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        String operation = request.getParameter("op");
        switch (operation) {
            case "inscription":
                handleRegister(request, response);
                break;
            case "connexion":
                handleLogin(request, response);
                break;
        }

        routingManager.forwardTo404(request, response);
    }

    private void handleRegister(HttpServletRequest request,
                             HttpServletResponse response) throws ServletException, IOException {


        String fullname = request.getParameter("fullname");
        String user = request.getParameter("user");
        String mail = request.getParameter("mail");
        String password = request.getParameter("password");

        if(fullname.length() < 4) {
            failWith("Le nom doit être de longueur supérieur ou égale à 4.", request, response);
            return;
        }
        if (user.length() < 4) {
            failWith("L'identifiant doit être de longueur supérieur ou égale à 4.", request, response);
            return;
        }
        if (loginManager.usernameExists(user)) {
            failWith("Cet identifiant éxiste déja.", request, response);
            return;
        }
        if (password.length() < 8) {
            failWith("Le mot de passe doit être de longueur supérieur ou égale à 8.", request, response);
            return;
        }

        facade.addUser(user, MD5Hash.hash(password), fullname, TypeUtilisateur.NONE);
        loginManager.invalidate();
        request.setAttribute("message", "Compte créé avec succès.");
        routingManager.loadPageFromRoute("/connexion", TypeUtilisateur.NONE, request, response);
    }

    private void failWith(String message, HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("message", "Erreur: " + message);
        routingManager.loadPageFromRoute("/inscription", TypeUtilisateur.NONE, request, response);
    }

    private void handleLogin(HttpServletRequest request,
                             HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("identifiant");
        String password = request.getParameter("mdp");

        if(username == null || password == null) {
            routingManager.loadPageFromRoute("/connexion", TypeUtilisateur.NONE, request, response);
            return;
        }

        //TODO: Implement login of other types of users
        TypeUtilisateur tu = loginManager.login(username, password, request.getSession());
        if(tu == TypeUtilisateur.NONE) {
            request.setAttribute("message", "Identifiants incorrects");
            routingManager.loadPageFromRoute("/connexion", TypeUtilisateur.NONE, request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/dataServlet?op=listRestau");
    }

}
