package pack.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.persistence.*;

@Entity
public class Panier {
	
	@Id
	@GeneratedValue
	private int id;
	
	@OneToOne(mappedBy="panier")
	private Utilisateur utilisateur;
	
	@OneToMany(mappedBy="panier", cascade = CascadeType.ALL)
	private Collection<Commande> commandes;

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

	// region Prices

	public Collection<Commande> getCommandesByEtat(CommandeEtat etat) {
		return getCommandes().stream()
				.filter(x -> x.getEtat() == etat)
				.collect(Collectors.toList());
	}

	public long getTotalPrice() {
		return getCommandesByEtat(CommandeEtat.NONE)
				.stream()
				.mapToLong(Commande::getTotalPrice)
				.sum();
	}

	// endregion

}
