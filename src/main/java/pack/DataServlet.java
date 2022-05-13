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
import java.util.Arrays;
import java.util.List;

@WebServlet("/dataServlet")
public class DataServlet extends HttpServlet {

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
        if(user == null) {
            req.setAttribute("message", "Vous devez être connecté pour continuer...");
            routingManager.loadPage("accueil.html", "Accueil", req, resp);
            return;
        }

        switch(operation) {
            case "listRestau":
                req.setAttribute("restaurants", facade.liste_restau());
                routingManager.loadPage("liste_restau.jsp", "Liste des restaurants", req, resp);
                break;
            case "listMenues":
                int rId = Integer.parseInt(req.getParameter("rid"));
                Restaurant r = facade.getRestaurant(rId);

                req.setAttribute("restaurant", r);
                routingManager.loadPage("liste_menus.jsp",
                        "Liste des menus - " + r.getNom(),
                        req, resp);
                break;
            default:
                resp.sendError(404);
        }
    }

}
