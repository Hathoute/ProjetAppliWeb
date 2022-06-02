package pack.entities;

import pack.entities.Menu;

import java.util.Collection;

import javax.persistence.*;

@Entity
public class Produit {
	
	@Id
	@GeneratedValue
	private int id;
	
	private String nom;
	
	private String description;
	
	@ManyToMany(mappedBy="produits")
	private Collection<Menu> menus;

	@ManyToOne
	private Restaurant restaurant;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
