package pack;

import pack.entities.Restaurant;
import pack.entities.TypeRestaurant;
import pack.entities.TypeUtilisateur;
import pack.entities.Utilisateur;
import pack.managers.LoginManager;
import pack.managers.RoutingManager;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/adminServlet")
public class AdminServlet extends HttpServlet {

    @EJB
    Facade facade;
    @EJB
    private RoutingManager routingManager;
    @EJB
    private LoginManager loginManager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String operation = req.getParameter("op");

        Utilisateur user = loginManager.getSessionUser(req.getSession());
        if(user == null || (user.getType().getId() & TypeUtilisateur.ADMIN.getId()) == 0) {
            req.setAttribute("message", "Accès restreint: vous devez être un administrateur");
            routingManager.loadPage("/WEB-INF/accueil.html", "Accueil", req, resp);
            return;
        }

        switch (operation) {
            case "ajoutRestau":
                req.setAttribute("types", facade.getRestaurantTypes());
                req.setAttribute("managers", facade.getUsers(TypeUtilisateur.MANAGER));
                routingManager.loadPage("/WEB-INF/ajout_restau.jsp", "Ajouter un restaurant", req, resp);
                return;
            default:
                routingManager.forwardTo404(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String operation = req.getParameter("op");

        Utilisateur user = loginManager.getSessionUser(req.getSession());
        if(user == null || (user.getType().getId() & TypeUtilisateur.ADMIN.getId()) == 0) {
            req.setAttribute("message", "Accès restreint: vous devez être un administrateur");
            routingManager.loadPage("/WEB-INF/accueil.html", "Accueil", req, resp);
            return;
        }

        switch(operation) {
            case "ajoutRestau":
                String nom = req.getParameter("nom");
                String description = req.getParameter("desc");
                int owner = Integer.parseInt(req.getParameter("manager"));
                int typeId = Integer.parseInt(req.getParameter("type"));
                Utilisateur manager = facade.getUser(owner);
                if(manager == null) {
                    req.setAttribute("message", "Utilisateur introuvable");
                    routingManager.loadPage("/WEB-INF/ajout_restau.jsp", "Liste des restaurants", req, resp);
                    return;
                }

                TypeRestaurant type = facade.getRestaurantType(typeId);
                if(type == null) {
                    req.setAttribute("message", "TypeRestaurant introuvable");
                    routingManager.loadPage("/WEB-INF/ajout_restau.jsp", "Liste des restaurants", req, resp);
                    return;
                }

                Restaurant r = facade.addRestaurant(nom, description, manager, type);
                req.setAttribute("message", "Restaurant " + r.getNom() + " ajouté avec succès");
                doGet(req, resp);
                break;
            case "ajoutMenu":
                req.setAttribute("restaurants", facade.liste_restau());
                routingManager.loadPage("/WEB-INF/liste_restau.jsp", "Liste des restaurants", req, resp);
                break;
            default:
                resp.sendError(404);
        }
    }

}
