package pack.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
	private List<Menu> menus = new ArrayList<Menu>();

	private CommandeEtat etat = CommandeEtat.NONE;

	private LocalDateTime lastTime;

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

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public CommandeEtat getEtat() {
		return etat;
	}

	public void setEtat(CommandeEtat etat) {
		this.etat = etat;
	}

	public LocalDateTime getLastTime() {
		return lastTime;
	}

	public void setLastTime(LocalDateTime lastTime) {
		this.lastTime = lastTime;
	}

	// region Prices

	public long getTotalPrice() {
		return getMenus().stream()
				.mapToLong(Menu::getPrix)
				.sum();
	}

	// endregion

}
