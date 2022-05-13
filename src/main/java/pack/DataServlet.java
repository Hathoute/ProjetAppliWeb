package pack;

import pack.entities.*;

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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String operation = req.getParameter("op");
        switch(operation) {
            case "listRestau":
                req.setAttribute("restaurants", facade.liste_restau());
                req.getRequestDispatcher("liste_restau.jsp").forward(req, resp);
                break;
            case "listMenues":
                int rId = Integer.parseInt(req.getParameter("rid"));
                Restaurant r = facade.liste_restau().stream().filter(x -> x.getId() == rId).findFirst().get();

                req.setAttribute("restaurant", r);
                req.getRequestDispatcher("liste_menus.jsp").forward(req, resp);
                break;
            default:
                resp.sendError(404);
        }
    }

}
