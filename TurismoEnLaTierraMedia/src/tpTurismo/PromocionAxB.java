package tpTurismo;

import java.util.ArrayList;
import java.util.List;

public class PromocionAxB extends Promocion {

	private List<Atraccion> atraccionesGratis=new ArrayList<Atraccion>();

	public PromocionAxB(String nombrePromocion, List<Atraccion> atraccionesIncluidas, int tipoPromocion,
			List<Atraccion> atraccionesGratis) {
		
		super(nombrePromocion, atraccionesIncluidas, tipoPromocion);
//		this.nombrePromocion=nombrePromocion;
//		this.atraccionesIncluidas.addAll(atraccionesIncluidas);
//		this.tipoAtraccion=tipoAtraccion;
		this.atraccionesGratis.addAll(atraccionesGratis);

	}

	@Override
	protected int calculoPrecioFinal() {
		int precio = 0;
		for (Atraccion atraccion : this.atraccionesIncluidas) {
			precio += atraccion.consultarCosto();
		}

		return precio;
	}

	@Override
	protected float calculoTiempoTotal() {
		float tiempo = 0;
		for (Atraccion elem : this.atraccionesIncluidas) {
			tiempo += elem.consultarDuracionEnHs();
		}

		for (Atraccion elem2 : this.atraccionesGratis) {
			tiempo += elem2.consultarDuracionEnHs();
		}

		return tiempo;
	}

	@Override
	public String toString() {
		return super.toString()+"PromocionAxB [atraccionesGratis=" + atraccionesGratis + "]";
	}

}
