package ma.ensias.winchesters.model;

public enum CityEnum {
	RABAT("Rabat"), MARRAKECH("Marrakech"), NEW_JERSEY("New Jersey");

	public final String label;

	private CityEnum(String label) {
		this.label = label;
	}

	public static CityEnum valueOfLabel(String label) {
		for (CityEnum e : values()) {
			if (e.label.equals(label)) {
				return e;
			}
		}
		return null;
	}
}
