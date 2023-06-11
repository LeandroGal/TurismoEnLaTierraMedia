package tpTurismo;

public class Atraccion {

	private String nombre;
	private int costo;
	private float duracionEnHs;
	private int cupoDisponible;
	private String tipoAtraccion;

	public Atraccion(String nombre, int costo, float duracionEnHs, int cupoDisponible, String tipoAtraccion) {
		this.nombre = nombre;
		this.costo = costo;
		this.duracionEnHs = duracionEnHs;
		this.cupoDisponible = cupoDisponible;
		this.tipoAtraccion = tipoAtraccion;
	}

	public boolean actualizarCupo() {
		if (this.cupoDisponible >= 1) {
			this.cupoDisponible--;
			return true;
		}
		return false;
	}

	public int consultarCupo() {
		return this.cupoDisponible;
	}

	public int consultarCosto() {
		return this.costo;
	}

	public float consultarDuracionEnHs() {
		return this.duracionEnHs;
	}

	public String consultarTipoAtraccion() {
		return tipoAtraccion;
	}

	public String consultarNombreAtraccion() {
		return this.nombre;
	}

	@Override
	public String toString() {
		return "Atraccion [nombre = " + nombre + ", costo = " + costo + ", duracionEnHs = " + duracionEnHs
				+ ", cupoDisponible = " + cupoDisponible + ", tipoAtraccion = " + tipoAtraccion + "]";
	}

	public String imprimeAtraccion() {
		return "[nombre = " + nombre + ", duracionEnHs = " + duracionEnHs + ", tipoAtraccion = " + tipoAtraccion + "]";
	}

}
