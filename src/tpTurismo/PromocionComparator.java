package tpTurismo;

public class PromocionComparator implements java.util.Comparator<Promocion> {

	@Override
	public int compare(Promocion a, Promocion b) {

		float restaCostos = b.calculoPrecioFinal() - a.calculoPrecioFinal();

		if (restaCostos == 0) {
			return (int) (b.calculoTiempoTotal() - a.calculoTiempoTotal());
		}
		return (int) restaCostos;
	}

}
