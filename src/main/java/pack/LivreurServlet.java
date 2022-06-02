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
import java.util.Collection;

@WebServlet("/livreurServlet")
public class LivreurServlet extends HttpServlet {

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

        if(user.getType() != TypeUtilisateur.LIVREUR) {
            req.setAttribute("message", "Vous n'êtes pas un livreur.");
            routingManager.loadPageFromRoute("/index.html", user.getType(), req, resp);
            return;
        }

        String operation = req.getParameter("op");

        switch(operation) {
            case "enAttente":
                req.setAttribute("commandes", facade.getCommandesEnAttenteLivraison());
                routingManager.loadPage("/WEB-INF/livreur/commandes_disponibles.jsp", "Commandes à livrer", req, resp);
                break;
            case "livraisons":
                req.setAttribute("livraisons", facade.getLivreurLivraisons(user));
                routingManager.loadPage("/WEB-INF/livreur/livraisons_en_cours.jsp", "Commandes à livrer", req, resp);
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

        if(user.getType() != TypeUtilisateur.LIVREUR) {
            req.setAttribute("message", "Vous n'êtes pas un livreur.");
            routingManager.loadPageFromRoute("/index.html", user.getType(), req, resp);
            return;
        }

        String operation = req.getParameter("op");

        switch(operation) {
            case "livrer":
                int cid = Integer.parseInt(req.getParameter("cid"));
                Commande c = facade.getCommande(cid);
                if(!facade.assignCommandeToLivreur(c, user)) {
                    req.setAttribute("message", "Cette commande n'est plus disponible.");
                }
                req.setAttribute("livraisons", facade.getLivreurLivraisons(user));
                routingManager.loadPage("/WEB-INF/livreur/livraisons_en_cours.jsp", "Commandes à livrer", req, resp);
                break;
            case "validerLivraison":
                int lid = Integer.parseInt(req.getParameter("lid"));
                facade.setFinishedLivraison(facade.getLivraison(lid));
                req.setAttribute("livraisons", facade.getLivreurLivraisons(user));
                routingManager.loadPage("/WEB-INF/livreur/livraisons_en_cours.jsp", "Commandes à livrer", req, resp);
                break;
            default:
                routingManager.forwardTo404(req, resp);
        }
    }
}
