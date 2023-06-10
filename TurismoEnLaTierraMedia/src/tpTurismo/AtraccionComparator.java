package tpTurismo;

class AtraccionComparator implements java.util.Comparator<Atraccion> {

	@Override
	public int compare(Atraccion a, Atraccion b) {

		float restaCostos = b.consultarCosto() - a.consultarCosto();

		if (restaCostos == 0) {
			return (int) (b.consultarDuracionEnHs() - a.consultarDuracionEnHs());
		}
		return (int) restaCostos;
	}
}
