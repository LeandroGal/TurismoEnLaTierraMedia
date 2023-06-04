package tpTurismo;

public class Usuario {

	private String nombreApellido;
	private String tipoAtraccionPreferida;
	private int presupuesto;
	private float horasDisponibles;

	public Usuario(String nombreApellido, String atraccionPreferida, int presupuesto, float horasDisponibles) {
		this.nombreApellido = nombreApellido;
		this.tipoAtraccionPreferida = atraccionPreferida;
		this.presupuesto = presupuesto;
		this.horasDisponibles = horasDisponibles;
	}

	public boolean actualizarPresupuestoYTiempo(int precio, float tiempo) {

		if (this.presupuesto >= precio && this.horasDisponibles >= tiempo) {
			this.presupuesto -= precio;
			this.horasDisponibles -= tiempo;
			return true;
		}
		return false;
	}

	public int consultarPresupuesto() {
		return this.presupuesto;
	}

	public float consultarTiempo() {
		return this.horasDisponibles;
	}

	@Override
	public String toString() {
		return "Usuario [nombreApellido=" + nombreApellido + ", tipoAtraccionPreferida=" + tipoAtraccionPreferida
				+ ", presupuesto=" + presupuesto + ", horasDisponibles=" + horasDisponibles + "]";
	}
	
	public String consultarAtraccionPreferida() {
		return this.tipoAtraccionPreferida;
	}

}
