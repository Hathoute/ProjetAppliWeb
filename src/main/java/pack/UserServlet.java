package pack;

import org.hibernate.Hibernate;
import pack.entities.*;
import pack.managers.LoginManager;
import pack.managers.RoutingManager;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/userServlet")
public class UserServlet extends HttpServlet {

    @EJB
    Facade facade;
    @EJB
    private RoutingManager routingManager;
    @EJB
    private LoginManager loginManager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Utilisateur user = loginManager.getSessionUser(req.getSession());
        if(user == null) {
            req.setAttribute("message", "Vous devez être connecté pour cette action.");
            routingManager.loadPageFromRoute("/index.html", TypeUtilisateur.NONE, req, resp);
            return;
        }

        String operation = req.getParameter("op");

        switch(operation) {
            default:
                routingManager.forwardTo404(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Utilisateur user = loginManager.getSessionUser(req.getSession());
        if(user == null) {
            req.setAttribute("message", "Vous devez être connecté pour cette action.");
            routingManager.loadPageFromRoute("/index.html", TypeUtilisateur.NONE, req, resp);
            return;
        }

        String operation = req.getParameter("op");

        switch(operation) {
            case "ajoutPanier":
                //TODO: handle possible exceptions...
                int menuId = Integer.parseInt(req.getParameter("menuId"));
                Menu m = facade.getMenu(menuId);
                facade.addMenuToPanier(user, m);
                routingManager.loadPageFromRoute("/panier", user.getType(), req, resp);
                break;
            case "retierMenuPanier":
                int mId = Integer.parseInt(req.getParameter("menuId"));
                int commandeId = Integer.parseInt(req.getParameter("commandeId"));
                facade.removeMenuFromPanier(user, commandeId, mId);
                routingManager.loadPageFromRoute("/panier", user.getType(), req, resp);
                break;
            case "retierCommandePanier":
                int cmdId = Integer.parseInt(req.getParameter("commandeId"));
                facade.removeCommandeFromPanier(user, cmdId);
                routingManager.loadPageFromRoute("/panier", user.getType(), req, resp);
                break;
            case "validerPanier":
                facade.validerPanier(user);
                routingManager.loadPageFromRoute("/commandesEnAttente", user.getType(), req, resp);
                break;
            default:
                routingManager.forwardTo404(req, resp);
        }
    }
}
