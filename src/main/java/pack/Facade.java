package pack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.rmi.CORBA.Util;

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
        em.createQuery("delete from Utilisateur u").executeUpdate();
        em.createQuery("delete from TypeRestaurant tr").executeUpdate();
        em.createQuery("delete from Restaurant r").executeUpdate();

        addUser("user1", MD5Hash.hash("password"), "User 1", TypeUtilisateur.CLIENT);
        addUser("user2", MD5Hash.hash("password2"), "User 2", TypeUtilisateur.CLIENT);
        addUser("livreur1", MD5Hash.hash("password3"), "Livreur 1", TypeUtilisateur.LIVREUR);
        Utilisateur manager = addUser("manager1", MD5Hash.hash("password3"), "Manager 1", TypeUtilisateur.MANAGER);
        addUser("admin1", MD5Hash.hash("password4"), "Admin 1", TypeUtilisateur.ADMIN);

        TypeRestaurant fastFood = addTypeRestaurant("Fast Food");

        Restaurant r = addRestaurant("Restau 1", "Desc 1", manager, fastFood);

        List<Produit> produits = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            produits.add(addProduit("Produit " + i, "Description prod " + i));
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

    public Utilisateur addUser(String username, String password, String fullname, TypeUtilisateur type) {
        Utilisateur u = new Utilisateur();
        u.setUsername(username);
        u.setPassword(password);
        u.setFullname(fullname);
        u.setType(type);

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

        return r;
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
        m.setRestaurant(resto);

        em.persist(m);

        return m;
    }

    public Produit addProduit(String nom, String desc) {
        Produit p = new Produit();
        p.setNom(nom);
        p.setDescription(desc);

        em.persist(p);

        return p;
    }

    public Restaurant getRestaurant(int id) {
        return em.find(Restaurant.class, id);
    }

    public Menu getMenu(int id) {
        return em.find(Menu.class, id);
    }

    public Collection<Restaurant> liste_restau(){
        return em.createQuery("select m from Restaurant m", Restaurant.class).getResultList();
    }

    public Collection<Utilisateur> getUsers() {
        return em.createQuery("select u from Utilisateur u", Utilisateur.class)
                .getResultList();
    }


}
