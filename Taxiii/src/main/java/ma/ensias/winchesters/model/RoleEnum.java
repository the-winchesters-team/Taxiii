package ma.ensias.winchesters.model;

public enum RoleEnum {
	ADMIN("Admin"), TAXI_DRIVER("Taxi Driver"), CLIENT("Client");

	public final String label;

	private RoleEnum(String label) {
		this.label = label;
	}

	public static RoleEnum valueOfLabel(String label) {
		for (RoleEnum e : values()) {
			if (e.label.equals(label)) {
				return e;
			}
		}
		return null;
	}
}
