package pack;

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

@WebServlet("/managerServlet")
public class ManagerServlet extends HttpServlet {

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
            req.setAttribute("message", "Vous devez être connecté pour effectuer cette action.");
            routingManager.loadPageFromRoute("/index.html", TypeUtilisateur.NONE, req, resp);
            return;
        }

        if(user.getType() != TypeUtilisateur.MANAGER) {
            req.setAttribute("message", "Vous ne managez aucun restaurant.");
            routingManager.loadPageFromRoute("/index.html", TypeUtilisateur.NONE, req, resp);
            return;
        }

        String operation = req.getParameter("op");

        switch(operation) {
            case "listeAttente":
                int rid = Integer.parseInt(req.getParameter("rid"));
                Restaurant r = facade.getRestaurant(rid);
                req.setAttribute("listeAttente", facade.getListeAttente(r));
                routingManager.loadPage("/WEB-INF/manager/liste_attente.jsp", "Commandes en attente", req, resp);
                break;
            case "listeRestau":
                req.setAttribute("restaurants", facade.getManagedRestaurants(user));
                routingManager.loadPage("/WEB-INF/liste_restau.jsp", "Restaurants", req, resp);
                break;
            case "gererRestau":
                int restauId = Integer.parseInt(req.getParameter("rid"));
                req.setAttribute("restaurant", facade.getManagedRestaurant(user, restauId));
            case "ajoutRestau":
                req.setAttribute("types", facade.getRestaurantTypes());
                routingManager.loadPage("/WEB-INF/manager/gerer_restau.jsp", "Ajout restaurant", req, resp);
                break;
            case "gererMenu":
                int menuId = Integer.parseInt(req.getParameter("mid"));
                Menu m = facade.getMenu(menuId);
                req.setAttribute("menu", m);
                req.setAttribute("produits", facade.getMenuProduits(m));
            case "ajoutMenu":
                int restaurantId = Integer.parseInt(req.getParameter("rid"));
                req.setAttribute("restaurant", facade.getRestaurant(restaurantId));
                routingManager.loadPage("/WEB-INF/manager/gerer_menu.jsp", "Ajout menu", req, resp);
                break;
            case "gererProduit":
                int produitId = Integer.parseInt(req.getParameter("pid"));
                req.setAttribute("produit", facade.getProduit(produitId));
            case "ajoutProduit":
                int mId = Integer.parseInt(req.getParameter("mid"));
                req.setAttribute("menu", facade.getMenu(mId));
                routingManager.loadPage("/WEB-INF/manager/gerer_produit.jsp", "Ajout produit", req, resp);
                break;
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
            case "validerCommande":
                int commandeId = Integer.parseInt(req.getParameter("commandeId"));
                Commande c = facade.getCommande(commandeId);
                facade.validerCommande(c);
                req.setAttribute("listeAttente", facade.getListeAttente(c.getRestaurant()));
                routingManager.loadPage("/WEB-INF/manager/liste_attente.jsp", "Commandes en attente", req, resp);
                break;
            case "ajoutRestau":
            case "modifierRestau":
                manageRestaurant(operation, req, resp);
                req.setAttribute("restaurants", facade.getManagedRestaurants(user));
                routingManager.loadPage("/WEB-INF/liste_restau.jsp", "Restaurants", req, resp);
                break;
            case "modifierMenu":
            case "ajoutMenu":
                manageMenu(operation, req, resp);
                req.setAttribute("restaurants", facade.getManagedRestaurants(user));
                routingManager.loadPage("/WEB-INF/liste_restau.jsp", "Restaurants", req, resp);
                break;
            case "ajoutProduit":
            case "modifierProduit":
                manageProduit(operation, req, resp);
                req.setAttribute("restaurants", facade.getManagedRestaurants(user));
                routingManager.loadPage("/WEB-INF/liste_restau.jsp", "Restaurants", req, resp);
                break;
            default:
                routingManager.forwardTo404(req, resp);
        }
    }

    private void manageRestaurant(String operation, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Utilisateur user = loginManager.getSessionUser(req.getSession());

        String nom = req.getParameter("nom");
        String description = req.getParameter("desc");
        int typeId = Integer.parseInt(req.getParameter("type"));
        TypeRestaurant type = facade.getRestaurantType(typeId);
        if(type == null) {
            req.setAttribute("message", "TypeRestaurant introuvable");
            routingManager.loadPage("/WEB-INF/gerer_restau.jsp", "Liste des restaurants", req, resp);
            return;
        }

        if(operation.equals("modifierRestau")) {
            int editId = Integer.parseInt(req.getParameter("editId"));
            Restaurant r = facade.getManagedRestaurant(user, editId);
            if(req.getParameter("supprimer") != null) {
                facade.removeRestaurant(user, r);
            }
            else {
                r.setNom(nom);
                r.setDescription(description);
                r.setType(type);
                facade.merge(r);
            }
        }
        else {
            Restaurant r = facade.addRestaurant(nom, description, user, type);
            req.setAttribute("message", "Restaurant " + r.getNom() + " ajouté avec succès");
        }
    }

    private void manageProduit(String operation, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Utilisateur user = loginManager.getSessionUser(req.getSession());

        String nom = req.getParameter("nom");
        String description = req.getParameter("desc");

        if(operation.equals("modifierProduit")) {
            // Too tired to handle security stuff...
            int editId = Integer.parseInt(req.getParameter("editId"));
            Produit p = facade.getProduit(editId);
            if(req.getParameter("supprimer") != null) {
                facade.removeProduit(p);
            }
            else {
                p.setNom(nom);
                p.setDescription(description);
                facade.merge(p);
            }
        }
        else {
            int menuId = Integer.parseInt(req.getParameter("menu"));
            Menu menu = facade.getMenu(menuId);
            Produit p = facade.addProduit(nom, description, menu.getRestaurant());
            facade.addProduitToMenu(p, menu);
            req.setAttribute("message", "Produit " + p.getNom() + " ajouté avec succès");
        }
    }

    private void manageMenu(String operation, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Utilisateur user = loginManager.getSessionUser(req.getSession());

        String nom = req.getParameter("nom");
        long prix = Long.parseLong(req.getParameter("prix"));

        if(operation.equals("modifierMenu")) {
            // Too tired to handle security stuff...
            int editId = Integer.parseInt(req.getParameter("editId"));
            Menu m = facade.getMenu(editId);
            if(req.getParameter("supprimer") != null) {
                facade.removeMenu(m);
            }
            else {
                m.setName(nom);
                m.setPrix(prix);
                facade.merge(m);
            }
        }
        else {
            int rId = Integer.parseInt(req.getParameter("restau"));
            Restaurant r = facade.getRestaurant(rId);
            Menu m = facade.addMenu(nom, prix, new ArrayList<>(), r);
            req.setAttribute("message", "Menu " + m.getName() + " ajouté avec succès");
        }
    }
}
