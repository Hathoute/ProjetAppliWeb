package pack;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.rmi.CORBA.Util;

import org.hibernate.Hibernate;
import pack.entities.*;
import pack.managers.LoginManager;
import pack.util.MD5Hash;

@Startup
@Singleton
public class Facade {

    @PersistenceContext
    EntityManager em;

    @PostConstruct
    public void init() {
        // TODO: Remove
        addUser("user1", MD5Hash.hash("password"), "User 1", TypeUtilisateur.CLIENT);
        addUser("user2", MD5Hash.hash("password2"), "User 2", TypeUtilisateur.CLIENT);
        addUser("livreur1", MD5Hash.hash("password3"), "Livreur 1", TypeUtilisateur.LIVREUR);
        Utilisateur manager = addUser("manager1", MD5Hash.hash("password3"), "Manager 1", TypeUtilisateur.MANAGER);
        addUser("admin1", MD5Hash.hash("password4"), "Admin 1", TypeUtilisateur.ADMIN);

        TypeRestaurant fastFood = addTypeRestaurant("Fast Food");

        Restaurant r = addRestaurant("Restau 1", "Desc 1", manager, fastFood);

        List<Produit> produits = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            produits.add(addProduit("Produit " + i, "Description prod " + i, r));
        }

        Random rand = new Random();
        for(int i = 1; i < 4; i++) {
            List<Produit> sublist = new ArrayList<>();
            int sz = 1 + rand.nextInt(5);
            for (int j = 0; j < sz; j++) {
                sublist.add(produits.get(rand.nextInt(produits.size())));
            }

            Collection<Menu> menus = r.getMenus();
            menus.add(addMenu("Menu name " + i, 100 + rand.nextInt(1000), sublist, r));
            r.setMenu(menus);
        }
    }

    //region Primitive add operations

    public Utilisateur addUser(String username, String password, String fullname, TypeUtilisateur type) {
        Utilisateur u = new Utilisateur();
        u.setUsername(username);
        u.setPassword(password);
        u.setFullname(fullname);
        u.setType(type);
        u.setPanier(new Panier());

        em.persist(u);

        return u;
    }

    public Restaurant addRestaurant(String nom, String description, Utilisateur proprio, TypeRestaurant type) {
        if(proprio.getType() != TypeUtilisateur.MANAGER) {
            throw new IllegalArgumentException("Restaurant owner must be a Manager");
        }

        Restaurant r = new Restaurant();
        r.setNom(nom);
        r.setDescription(description);
        r.setProprio(proprio);
        r.setType(type);
        r.setMenu(new ArrayList<>());

        em.persist(r);
        r.setListeAttente(addListeAttente(r));

        return r;
    }

    public ListeAttente addListeAttente(Restaurant r) {
        ListeAttente l = new ListeAttente();
        l.setCommandes(new ArrayDeque<>());
        l.setRestaurant(r);

        em.persist(l);

        return l;
    }

    public TypeRestaurant addTypeRestaurant(String nom) {
        TypeRestaurant tr = new TypeRestaurant();
        tr.setName(nom);

        em.persist(tr);

        return tr;
    }

    public Menu addMenu(String name, long prix, Collection<Produit> produits, Restaurant resto) {
        Menu m = new Menu();

        m.setName(name);
        m.setPrix(prix);
        m.setProduits(produits);
        m.setRestaurant(getRestaurant(resto.getId()));

        em.persist(m);

        return m;
    }

    public Produit addProduit(String nom, String desc, Restaurant r) {
        Produit p = new Produit();
        p.setNom(nom);
        p.setDescription(desc);
        p.setRestaurant(r);

        em.persist(p);

        return p;
    }

    public void addPanier(Utilisateur u, Collection<Commande> listeC) {
        Panier p = new Panier();
        p.setUtilisateur_id(u);
        p.setCommandes(listeC);
        em.persist(p);
    }

    public Commande addCommande(Panier p, Restaurant r, List<Menu> liste_m) {
        Commande c = new Commande();
        c.setPanier(p);
        c.setRestaurant(r);
        c.setMenus(liste_m);
        c.setEtat(CommandeEtat.NONE);
        em.persist(c);

        return c;
    }

    public Livraison addLivraison(Commande c, Utilisateur liv) {
        Livraison l = new Livraison();
        l.setCommande(c);
        l.setLivreur(liv);

        em.persist(l);

        return l;
    }

    //endregion


    //region Primitive get operations

    public Restaurant getRestaurant(int id) {
        return em.find(Restaurant.class, id);
    }

    public Collection<TypeRestaurant> getRestaurantTypes() {
        return em.createQuery("select rt from TypeRestaurant rt", TypeRestaurant.class)
                .getResultList();
    }

    public TypeRestaurant getRestaurantType(int id) {
        return em.find(TypeRestaurant.class, id);
    }

    public Menu getMenu(int id) {
        return em.find(Menu.class, id);
    }

    public Collection<Menu> getMenus() {
        return em.createQuery("select m from Menu m", Menu.class).getResultList();
    }

    public Collection<Utilisateur> getUsers() {
        return em.createQuery("select u from Utilisateur u", Utilisateur.class)
                .getResultList();
    }

    public Utilisateur getUser(int id) {
        return em.find(Utilisateur.class, id);
    }

    public Collection<Utilisateur> getUsers(TypeUtilisateur type) {
        return em.createQuery("select u from Utilisateur u where u.type = :type", Utilisateur.class)
                .setParameter("type", type)
                .getResultList();
    }

    public Panier getPanier(int id) {
        return em.find(Panier.class, id);
    }

    public Collection<Panier> liste_paniers(Utilisateur utilisateur){
        return em.createQuery("select p from Panier p where p.utilisateur = :utilisateur", Panier.class)
                .setParameter("utilisateur", utilisateur)
                .getResultList();
    }

    public Livraison getLivraison(int id) {
        return em.find(Livraison.class, id);
    }

    public Commande getCommande(int id) {
        return em.find(Commande.class, id);
    }

    public Collection<Restaurant> getRestaurants(){
        return em.createQuery("select r from Restaurant r", Restaurant.class).getResultList();
    }

    public Produit getProduit(int id) {
        return em.find(Produit.class, id);
    }

    //endregion

    //region User operations

    public Panier getUserPanier(Utilisateur u) {
        Panier p = getPanier(u.getPanier().getId());
        p.getCommandes().parallelStream().forEach(x -> Hibernate.initialize(x.getMenus()));
        return p;
    }

    public void addMenuToPanier(Utilisateur u, Menu m) {
        Panier p = getUserPanier(u);
        Commande c = p.getCommandes()
                .stream()
                .filter(x -> x.getEtat() == CommandeEtat.NONE && x.getRestaurant().getId() == m.getRestaurant().getId())
                .findFirst()
                .orElseGet(() -> addCommande(p, m.getRestaurant(), new ArrayList<>()));

        c.getMenus().add(m);
        c.setMenus(c.getMenus());
    }

    public void removeCommandeFromPanier(Utilisateur u, int commandeId) {
        Panier p = getUserPanier(u);
        Commande c = p.getCommandes()
                .stream()
                .filter(x -> x.getEtat() == CommandeEtat.NONE && x.getId() == commandeId)
                .findFirst()
                .orElse(null);

        if(c != null) {
            em.remove(c);
            p.getCommandes().remove(c);
        }
    }

    public void removeMenuFromPanier(Utilisateur u, int commandeId, int menuId) {
        Panier p = getUserPanier(u);
        Commande c = p.getCommandes()
                .stream()
                .filter(x -> x.getEtat() == CommandeEtat.NONE && x.getId() == commandeId)
                .findFirst()
                .orElse(null);

        Menu m = getMenu(menuId);

        if (c != null && m != null) {
            c.getMenus().remove(m);
            c.setMenus(c.getMenus());

            if(c.getMenus().size() == 0) {
                removeCommandeFromPanier(u, commandeId);
            }
        }
    }

    public void validerPanier(Utilisateur u) {
        Panier p = getUserPanier(u);
        for (Commande c : p.getCommandes()) {
            if(c.getEtat() != CommandeEtat.NONE) {
                continue;
            }

            c.setEtat(CommandeEtat.EN_ATTENTE);
            c.setLastTime(LocalDateTime.now());
            ListeAttente l = c.getRestaurant().getListeAttente();
            l.getCommandes().add(c);
            l.setCommandes(l.getCommandes());
        }
    }

    //endregion

    //region Manager operations

    public Collection<Restaurant> getManagedRestaurants(Utilisateur u) {
        return em.createQuery("select r from Restaurant r where r.proprio=:user", Restaurant.class)
                .setParameter("user", u)
                .getResultList();
    }

    public Restaurant getManagedRestaurant(Utilisateur u, int rid) {
        Restaurant r = getRestaurant(rid);
        return r != null && r.getProprio().getId() == u.getId() ? r : null;
    }

    public Collection<Produit> getRestaurantProduits(Restaurant r) {
        return em.createQuery("select p from Produit p where p.restaurant=:restau", Produit.class)
                .setParameter("restau", r)
                .getResultList();
    }

    public void removeRestaurant(Utilisateur u, Restaurant r) {
        if(r.getProprio().getId() != u.getId()) {
            return;
        }

        em.remove(getRestaurant(r.getId()));
    }

    public void addProduitToMenu(Produit p, Menu m) {
        p = getProduit(p.getId());
        m = getMenu(m.getId());

        m.getProduits().add(p);
        m.setProduits(m.getProduits());
    }

    public Collection<Produit> getMenuProduits(Menu m) {
        Collection<Produit> produits = getMenu(m.getId()).getProduits();
        Hibernate.initialize(produits);
        return produits;
    }

    public void removeProduit(Produit p) {
        Produit prod = getProduit(p.getId());
        prod.getRestaurant().getProduits().remove(prod);
        prod.getMenus().forEach(x -> x.getProduits().remove(prod));
        em.remove(prod);
    }

    public void removeMenu(Menu m) {
        Menu menu = getMenu(m.getId());
        menu.getRestaurant().getMenus().remove(menu);
        em.remove(menu);
    }

    public ListeAttente getListeAttente(Restaurant r) {
        r = getRestaurant(r.getId());
        ListeAttente l = r.getListeAttente();
        l.getCommandes().forEach(x -> Hibernate.initialize(x.getMenus()));
        return l;
    }

    public void validerCommande(Commande c) {
        c = getCommande(c.getId());
        c.setEtat(CommandeEtat.EN_ATTENTE_LIVRAISON);
        c.setLastTime(LocalDateTime.now());
        ListeAttente l = getListeAttente(c.getRestaurant());
        l.getCommandes().remove(c);
    }

    //endregion

    public <T> void merge(T mr) {
        em.merge(mr);
    }

    public <T> void remove(T mr) {
        em.remove(em.merge(mr));
    }

}
