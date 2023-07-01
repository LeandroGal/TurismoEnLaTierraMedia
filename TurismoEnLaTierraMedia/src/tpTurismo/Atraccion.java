package tpTurismo;

public class Atraccion {

	private String nombre;
	private int costo;
	private float duracion;
	private int cupoDisponible;
	private String tipoAtraccion;

	public Atraccion(String nombre, int costo, float duracionEnHs, int cupoDisponible, String tipoAtraccion) {
		this.nombre = nombre;
		this.costo = costo;
		this.duracion = duracionEnHs;
		this.cupoDisponible = cupoDisponible;
		this.tipoAtraccion = tipoAtraccion;
	}

	public int getCupo() {
		return this.cupoDisponible;
	}

	public int getCosto() {
		return this.costo;
	}

	public float getDuracion() {
		return this.duracion;
	}

	public String getTipoAtraccion() {
		return this.tipoAtraccion;
	}

	public String getNombreAtraccion() {
		return this.nombre;
	}

	public boolean actualizarCupo() {
		if (this.cupoDisponible >= 1) {
			this.cupoDisponible--;
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Atraccion [nombre = " + nombre + ", costo = " + costo + ", duracionEnHs = " + duracion
				+ ", cupoDisponible = " + cupoDisponible + ", tipoAtraccion = " + tipoAtraccion + "]";
	}

	public String imprimeAtraccion() {
		return "[nombre = " + nombre + ", duracionEnHs = " + duracion + ", tipoAtraccion = " + tipoAtraccion + "]";
	}

}