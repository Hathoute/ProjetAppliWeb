package pack;

import pack.entities.TypeUtilisateur;
import pack.entities.Utilisateur;
import pack.managers.LoginManager;
import pack.managers.RoutingManager;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Servlet
 */
@WebServlet(name = "router", loadOnStartup = 1)
public class Servlet extends HttpServlet {
	@EJB
	private RoutingManager routingManager;
	@EJB
	private LoginManager loginManager;

	@Override
	public void init(ServletConfig config) throws ServletException {
		ServletRegistration r = config.getServletContext().getServletRegistration("router");
		for (String route : routingManager.getAllRoutes()) {
			r.addMapping(route);
		}
		//r.addMapping("*.html");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilisateur u = loginManager.getSessionUser(request.getSession());
		routingManager.loadPageFromRoute(request.getServletPath(),
				u == null ? TypeUtilisateur.NONE : u.getType(),
				request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
