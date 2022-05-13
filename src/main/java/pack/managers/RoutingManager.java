package pack.managers;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Startup
@Singleton
public class RoutingManager {

    private Map<String, String> customRoutes;

    @PostConstruct
    public void init() {
        customRoutes = new HashMap<>();
        customRoutes.put("/", "/accueil.html");
        customRoutes.put("/index.html", "/accueil.html");
    }

    public void loadPage(String page, String title, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("file", page);
        request.setAttribute("pageTitle", title);
        request.getRequestDispatcher("page_wrapper.jsp").forward(request, response);
    }
}
