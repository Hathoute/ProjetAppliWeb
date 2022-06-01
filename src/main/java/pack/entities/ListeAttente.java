package pack.entities;

import pack.entities.Commande;

import java.util.Collection;
import java.util.Queue;
import javax.persistence.*;

@Entity
public class ListeAttente {

	@Id
	@GeneratedValue
	private int id;
	
	@OneToMany(cascade = CascadeType.ALL)
	private Collection<Commande> commandes;

	@OneToOne
	private Restaurant restaurant;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Collection<Commande> getCommandes() {
		return commandes;
	}

	public void setCommandes(Collection<Commande> commandes) {
		this.commandes = commandes;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
}
