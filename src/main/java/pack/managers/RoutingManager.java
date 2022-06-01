package pack.managers;

import pack.entities.TypeUtilisateur;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Startup
@Singleton
public class RoutingManager {

    @EJB
    private LoginManager loginManager;

    private Map<String, Route> routes;

    @PostConstruct
    public void init() {
        routes = new HashMap<>();
        routes.put("/index.html",
                new Route("/dataServlet?op=accueil", "Accueil", "Accueil", TypeUtilisateur.ALL.getId()));
        routes.put("/index.jsp",
                new Route("/dataServlet?op=accueil", "Accueil", "Accueil", TypeUtilisateur.ALL.getId()));
        routes.put("/accueil",
                new Route("/dataServlet?op=accueil", "Accueil", "Accueil", TypeUtilisateur.ALL.getId()));
        routes.put("/connexion",
                new Route("/WEB-INF/connexion.html", "Connexion", null, TypeUtilisateur.NONE.getId()));
        routes.put("/inscription",
                new Route("/WEB-INF/enregistrement.html", "Inscription", null, TypeUtilisateur.NONE.getId()));
        routes.put("/restaurants",
                new Route("/dataServlet?op=listRestau", "Restaurants", "Restaurants", TypeUtilisateur.ALL.getId()));
        routes.put("/pageAdmin",
                new Route("/WEB-INF/page_admin.html", "Panel admin", "Panel admin", TypeUtilisateur.ADMIN.getId()));
        routes.put("/ajoutRestau",
                new Route("/adminServlet?op=ajoutRestau", "Ajouter un restaurant", "Panel admin", TypeUtilisateur.ADMIN.getId()));
        routes.put("/ajoutMenu",
                new Route("/adminServlet?op=ajoutMenu", "Ajouter un menu", "Panel admin", TypeUtilisateur.ADMIN.getId()));
        routes.put("/panier",
                new Route("/WEB-INF/panier.jsp", "Mon panier", "Panier", TypeUtilisateur.CLIENT.getId()));
        routes.put("/commandesEnAttente",
                new Route("/WEB-INF/commandes_passees.jsp", "Mes commandes", "Mes commandes", TypeUtilisateur.CLIENT.getId()));

        // Initialize Navbar
        NavbarManager.initializeTemplate(routes);
    }

    public Map<String, Route> getAllRoutes() {
        return routes;
    }

    public void loadPage(String page, String title, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("user", loginManager.getSessionUser(request.getSession()));

        if(request.getAttribute("wrapperLoaded") != null) {
            // We already loaded the wrapper, just return content.
            request.getRequestDispatcher(page).include(request, response);
            return;
        }

        request.setAttribute("wrapperLoaded", true);
        request.setAttribute("file", page);
        request.setAttribute("pageTitle", title);
        request.getRequestDispatcher("/WEB-INF/page_wrapper.jsp").forward(request, response);
    }

    public void forwardTo404(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        loadPage("/WEB-INF/notfound.html", "Page not found", request, response);
    }

    public void loadPageFromRoute(String routeUrl, TypeUtilisateur type, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(!routes.containsKey(routeUrl)) {
            loadPage("/WEB-INF/notfound.html", "Page not found", request, response);
            return;
        }

        Route r = routes.get(routeUrl);
        if((type.getId() & r.allowedUsers) == 0) {
            loadPage("/WEB-INF/unauthorized.html", "Unauthorized", request, response);
            return;
        }


        loadPage(r.url, r.title, request, response);
    }
}


