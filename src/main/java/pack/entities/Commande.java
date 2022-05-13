package pack.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.*;

@Entity
public class Commande {
	
	@Id
	@GeneratedValue
	private int id;
	
	@ManyToOne
	private Panier panier;
	
	@ManyToOne
	private Restaurant restaurant;
	
	@ManyToMany
	private Collection<Menu> menus = new ArrayList<Menu>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Panier getPanier() {
		return panier;
	}

	public void setPanier(Panier panier) {
		this.panier = panier;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Collection<Menu> getMenus() {
		return menus;
	}

	public void setMenus(Collection<Menu> menus) {
		this.menus = menus;
	}
	
	

}
