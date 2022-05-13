package pack.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.*;;

@Entity
public class Restaurant {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	private Utilisateur proprio;
	
	private String nom;
	
	private String description;
	
	@ManyToOne
	private TypeRestaurant type;
	
	@OneToMany(mappedBy="restaurant", cascade = CascadeType.ALL)
	private Collection<Menu> menus = new ArrayList<Menu>();;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Utilisateur getProprio() {
		return proprio;
	}

	public void setProprio(Utilisateur proprio) {
		this.proprio = proprio;
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

	public TypeRestaurant getType() {
		return type;
	}

	public void setType(TypeRestaurant type) {
		this.type = type;
	}

	public Collection<Menu> getMenus() {
		return menus;
	}

	public void setMenu(Collection<Menu> menu) {
		this.menus = menu;
	}

}
