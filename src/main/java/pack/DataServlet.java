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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String operation = req.getParameter("op");

        switch(operation) {
            case "listRestau":
                req.setAttribute("restaurants", facade.liste_restau());
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
