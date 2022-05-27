package pack.managers;


import pack.entities.TypeUtilisateur;
import pack.entities.Utilisateur;
import pack.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class NavbarManager {

    private static List<Pair<Integer, NavbarEntry>> template;

    public static void initializeTemplate(Map<String, Route> routes) {
        template = new ArrayList<>();
        Map<String, NavbarEntry> entryByTitle = new HashMap<>();
        routes.forEach((k, v) -> {
            if(v.navbarTitle == null) {
                return;
            }

            if(entryByTitle.containsKey(v.navbarTitle)) {
                NavbarEntry entry = entryByTitle.get(v.navbarTitle);
                entry.children.put(v.title, k);
            }
            else {
                NavbarEntry entry = new NavbarEntry(v.navbarTitle, new HashMap<>(), false);
                entry.children.put(v.title, k);
                entryByTitle.put(v.navbarTitle, entry);
                template.add(new Pair<>(v.allowedUsers, entry));
            }
        });
    }

    public static List<NavbarEntry> generateEntries(Utilisateur user) {
        TypeUtilisateur tu = user == null ? TypeUtilisateur.NONE : user.getType();
        return template
                .stream()
                .map(x -> x.getSecond().clone((x.getFirst() & tu.getId()) > 0))
                .filter(x -> x.visible)
                .collect(Collectors.toList());
    }
}
