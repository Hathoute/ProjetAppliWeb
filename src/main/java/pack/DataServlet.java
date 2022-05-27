package pack;

import pack.entities.*;
import pack.managers.RoutingManager;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/dataServlet")
public class DataServlet extends HttpServlet {

    @EJB
    Facade facade;
    @EJB
    private RoutingManager routingManager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String operation = req.getParameter("op");

        switch(operation) {
            case "accueil":
                req.setAttribute("restosVedette", facade.getRestaurants());
                req.setAttribute("menusJour", facade.getMenus());
                routingManager.loadPage("/WEB-INF/accueil.jsp", "Accueil", req, resp);
                break;
            case "listRestau":
                req.setAttribute("restaurants", facade.getRestaurants());
                routingManager.loadPage("/WEB-INF/liste_restau.jsp", "Liste des restaurants", req, resp);
                break;
            case "listMenues":
                int rId = Integer.parseInt(req.getParameter("rid"));
                Restaurant r = facade.getRestaurant(rId);

                req.setAttribute("restaurant", r);
                routingManager.loadPage("/WEB-INF/liste_menus.jsp",
                        "Liste des menus - " + r.getNom(),
                        req, resp);
                break;
            default:
                routingManager.forwardTo404(req, resp);
        }
    }

}
