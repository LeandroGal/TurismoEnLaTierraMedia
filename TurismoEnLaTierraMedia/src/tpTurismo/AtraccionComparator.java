package tpTurismo;

class AtraccionComparator implements java.util.Comparator<Atraccion> {

	@Override
	public int compare(Atraccion a, Atraccion b) {

		float restaCostos = b.getCosto() - a.getCosto();

		if (restaCostos == 0) {
			return (int) (b.getDuracion() - a.getDuracion());
		}
		return (int) restaCostos;
	}
}
