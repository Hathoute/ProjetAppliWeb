package pack;

import pack.entities.Utilisateur;
import pack.managers.LoginManager;
import pack.managers.RoutingManager;

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
    private RoutingManager routingManager;
    @EJB
    private LoginManager loginManager;

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("identifiant");
        String password = request.getParameter("mdp");
        String userType = request.getParameter("userType");

        if(username == null || password == null) {
            routingManager.loadPage("accueil.html", "Accueil", request, response);
            return;
        }

        //TODO: Implement login of other types of users
        Utilisateur user = loginManager.correctCredentials(username, password);
        if(user == null) {
            routingManager.loadPage("accueil.html", "Identifiants incorrects - Accueil", request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/dataServlet?op=listRestau");
    }

}
