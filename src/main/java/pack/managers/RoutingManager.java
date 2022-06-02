package pack.managers;

import pack.entities.TypeUtilisateur;
import pack.entities.Utilisateur;

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
        routes.put("/deconnexion",
                new Route("/loginServlet?op=deconnexion", "Deconnexion", null, TypeUtilisateur.ONLINE.getId()));
        routes.put("/inscription",
                new Route("/WEB-INF/enregistrement.html", "Inscription", null, TypeUtilisateur.NONE.getId()));
        routes.put("/restaurants",
                new Route("/dataServlet?op=listeRestau", "Restaurants", "Restaurants", TypeUtilisateur.ALL.getId()));
        routes.put("/panier",
                new Route("/WEB-INF/user/panier.jsp", "Mon panier", "Panier", TypeUtilisateur.CLIENT.getId()));
        routes.put("/commandesEnAttente",
                new Route("/WEB-INF/user/commandes_passees.jsp", "Mes commandes", "Mes commandes", TypeUtilisateur.CLIENT.getId()));
        routes.put("/pageAdmin",
                new Route("/WEB-INF/page_admin.html", "Panel admin", "Panel admin", TypeUtilisateur.ADMIN.getId()));
        routes.put("/manageRestaurants",
                new Route("/managerServlet?op=listeRestau", "Mes restaurants", "Manager", TypeUtilisateur.MANAGER.getId()));
        routes.put("/manageAjoutRestau",
                new Route("/managerServlet?op=ajoutRestau", "Ajouter un restaurant", "Manager", TypeUtilisateur.MANAGER.getId()));
        routes.put("/livreurEnAttente",
                new Route("/livreurServlet?op=enAttente", "Commandes en attente", "Livreur", TypeUtilisateur.LIVREUR.getId()));
        routes.put("/livreurLivraisons",
                new Route("/livreurServlet?op=livraisons", "Livraisons en cours", "Livreur", TypeUtilisateur.LIVREUR.getId()));

        // Initialize Navbar
        NavbarManager.initializeTemplate(routes);
    }

    public Map<String, Route> getAllRoutes() {
        return routes;
    }

    public void loadPage(String page, String title, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utilisateur user = loginManager.getSessionUser(request.getSession());
        TypeUtilisateur tuser = user == null ? TypeUtilisateur.NONE : user.getType();
        request.setAttribute("user", user);
        request.setAttribute("userType", tuser);

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


