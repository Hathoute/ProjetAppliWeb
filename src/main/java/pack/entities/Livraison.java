package pack.entities;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
public class Livraison {

	@Id
	@GeneratedValue
	private int id;
	
	@OneToOne(mappedBy="livraison")
	private Utilisateur livreur;
	
	@OneToOne(cascade = CascadeType.REMOVE)
	private Commande commande;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Utilisateur getLivreur() {
		return livreur;
	}

	public void setLivreur(Utilisateur livreur) {
		this.livreur = livreur;
	}

	public Commande getCommande() {
		return commande;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}
	
	
	
}
