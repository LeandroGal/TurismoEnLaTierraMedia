package tpTurismo;

public class Usuario {

	private String nombreApellido;
	private String tipoAtraccionPreferida;
	private int presupuesto;
	private float tiempoDisponible;

	public Usuario(String nombreApellido, String atraccionPreferida, int presupuesto, float horasDisponibles) {
		this.nombreApellido = nombreApellido;
		this.tipoAtraccionPreferida = atraccionPreferida;
		this.presupuesto = presupuesto;
		this.tiempoDisponible = horasDisponibles;
	}

	public String getNombreApellido() {
		return this.nombreApellido;
	}
	
	public String getTipoAtraccionPreferida() {
		return this.tipoAtraccionPreferida;
	}
	
	public int getPresupuesto() {
		return this.presupuesto;
	}

	public float getTiempoDisponible() {
		return this.tiempoDisponible;
	}
	

	public boolean actualizarPresupuestoYTiempo(int precio, float tiempo) {

		if (this.presupuesto >= precio && this.tiempoDisponible >= tiempo) {
			this.presupuesto -= precio;
			this.tiempoDisponible -= tiempo;
			return true;
		}
		return false;
	}

	
}
