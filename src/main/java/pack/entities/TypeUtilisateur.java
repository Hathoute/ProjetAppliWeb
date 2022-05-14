package pack.entities;

public enum TypeUtilisateur {
	NONE(1),
	CLIENT(1<<1),
	LIVREUR(1<<2),
	MANAGER(1<<3),
	ADMIN(1<<4),
	ALL(NONE.id | CLIENT.id | LIVREUR.id | MANAGER.id | ADMIN.id);

	private final int id;

	TypeUtilisateur(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
