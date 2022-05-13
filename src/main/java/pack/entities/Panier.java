package pack.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.*;

@Entity
public class Panier {
	
	@Id
	@GeneratedValue
	private int id;
	
	@OneToOne(mappedBy="panier")
	private Utilisateur utilisateur;
	
	@OneToMany(mappedBy="panier", cascade = CascadeType.ALL)
	private Collection<Commande> commandes = new ArrayList<Commande>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur_id(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Collection<Commande> getCommandes() {
		return commandes;
	}

	public void setCommandes(Collection<Commande> commandes) {
		this.commandes = commandes;
	}
	
	

}
