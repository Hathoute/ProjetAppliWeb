package pack.managers;

import pack.entities.TypeUtilisateur;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Startup
@Singleton
public class RoutingManager {

    private Map<String, Route> routes;

    @PostConstruct
    public void init() {
        routes = new HashMap<>();
        routes.put("/index.html",
                new Route("/WEB-INF/accueil.html", "Accueil", TypeUtilisateur.ALL.getId()));
        routes.put("/index.jsp",
                new Route("/WEB-INF/accueil.html", "Accueil", TypeUtilisateur.ALL.getId()));
        routes.put("/accueil",
                new Route("/WEB-INF/accueil.html", "Accueil", TypeUtilisateur.ALL.getId()));
        routes.put("/pageAdmin",
                new Route("/WEB-INF/page_admin.html", "Panel administrateur", TypeUtilisateur.ADMIN.getId()));
        routes.put("/ajoutRestau",
                new Route("/adminServlet?op=ajoutRestau", "Ajouter un restaurant", TypeUtilisateur.ADMIN.getId()));
        routes.put("/ajoutMenu",
                new Route("/adminServlet?op=ajoutMenu", "Ajouter un menu", TypeUtilisateur.ADMIN.getId()));
    }

    public Collection<String> getAllRoutes() {
        return routes.keySet();
    }

    public void loadPage(String page, String title, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("file", page);
        request.setAttribute("pageTitle", title);
        request.getRequestDispatcher("/WEB-INF/page_wrapper.jsp").forward(request, response);
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

class Route {
    public String url;
    public String title;
    public int allowedUsers;

    public Route(String url, String title, int userIds) {
        this.url = url;
        this.title = title;
        this.allowedUsers = userIds;
    }
}
