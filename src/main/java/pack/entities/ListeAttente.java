package pack.entities;

import pack.entities.Commande;

import java.util.Collection;
import javax.persistence.*;

@Entity
public class ListeAttente {

	@Id
	@GeneratedValue
	private int id;
	
	@OneToMany(cascade = CascadeType.ALL)
	private Collection<Commande> commandes_attente;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Collection<Commande> getCommandes_attente() {
		return commandes_attente;
	}

	public void setCommandes_attente(Collection<Commande> commandes_attente) {
		this.commandes_attente = commandes_attente;
	}
	
	
	
}
