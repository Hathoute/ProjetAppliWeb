package pack.entities;

import java.util.Collection;

import javax.persistence.*;

@Entity
public class Utilisateur {
	
	
	@Id
	@GeneratedValue
	private int id;

	@Column(unique = true)
	private String username;
	
	private String password;
	
	private String fullname;
	
	private TypeUtilisateur type;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Livraison livraison;
	
	@OneToMany(mappedBy="proprio")
	private Collection<Restaurant> restaurants;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Panier panier;
	
	public Livraison getLivraison() {
		return livraison;
	}
	
	public void setLivraison(Livraison livraison) {
		this.livraison = livraison;
	}
	
	public Collection<Restaurant> getRestaurants(){
		return restaurants;
	}
	
	public void setRestaurant(Collection<Restaurant> restaurants) {
		this.restaurants = restaurants;
	}
	
	public Panier getPanier() {
		return panier;
	}
	
	public void setPanier(Panier panier) {
		this.panier = panier;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public TypeUtilisateur getType() {
		return type;
	}

	public void setType(TypeUtilisateur type) {
		this.type = type;
	}

}
