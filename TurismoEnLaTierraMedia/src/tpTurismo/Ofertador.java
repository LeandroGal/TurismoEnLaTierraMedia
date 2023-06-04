package tpTurismo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Ofertador {

	private float precioFinal;
	private float precioInicial;
	private float tiempo;
	private List<Atraccion> atracciones;

	public static void main(String[] args) throws IOException {

		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		List<Atraccion> listaAtracciones = new ArrayList<Atraccion>();
		List<Promocion> listaPromociones = new ArrayList<Promocion>();

		try {
			listaUsuarios = Archivo.leerArchivoUsuarios();
			listaAtracciones = Archivo.leerArchivoAtracciones();
			listaPromociones = Archivo.leerArchivoPromociones(listaAtracciones);
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (Usuario usuario : listaUsuarios) {
			System.out.println(usuario);
			String preferida = usuario.consultarAtraccionPreferida();

			List<Atraccion> atraccionesPreferidas = new ArrayList<Atraccion>();
			List<Atraccion> atraccionesRestantes = new ArrayList<Atraccion>();

			for (Atraccion atraccion : listaAtracciones) {
				if (usuario.consultarAtraccionPreferida().equals(atraccion.consultarTipoAtraccion())) {
					atraccionesPreferidas.add(atraccion);
				} else {
					atraccionesRestantes.add(atraccion);
				}
			}
			Collections.sort(atraccionesPreferidas, new AtraccionComparator());
			Collections.sort(atraccionesRestantes, new AtraccionComparator());

			// PAQUETES
			List<Promocion> promocionesPreferidas = new ArrayList<Promocion>();
			List<Promocion> promocionesRestantes = new ArrayList<Promocion>();

			for (Promocion promocion : listaPromociones) {
				if (usuario.consultarAtraccionPreferida().equals(promocion.consultarTipoAtraccion())) {

					promocionesPreferidas.add(promocion);
				} else {
					promocionesRestantes.add(promocion);
				}

			}
			Collections.sort(promocionesPreferidas, new PromocionComparator());
			Collections.sort(promocionesRestantes, new PromocionComparator());

			Itinerario itinerario = new Itinerario();

			// mientras recorro y acepta: resto presupuesto, tiempo,
			// cupo atraccion y en caso de ser promo restar de todas
			// por promo debo consultar cupo de todas atracciones incluidas
			// traer menor cupo de las atracciones incluidas

			// resto de las listas que traje de los archivos.

			// for por promo preferida
			Scanner scanner = new Scanner(System.in);
			for (Promocion promo : promocionesPreferidas) {

				int indicePromoAOfertar = listaPromociones.indexOf(promo);
				// listaPromociones.get(indicePromoAOfertar);

				if (listaPromociones.get(indicePromoAOfertar).tieneCupo()
						&& usuario.consultarPresupuesto() >= promo.calculoPrecioFinal()
						&& usuario.consultarTiempo() >= promo.calculoTiempoTotal() && 
						itinerario.puedeOfertarPromocion(promo)) {
					System.out.println("Promocion: ");

					System.out.println("-Atracciones incluidas: ");
					promo.imprimirAtracciones();
					System.out.println("-Duracion: " + promo.consultarTiempoTotal() + " Hs");
					System.out.println("Precio original: " + promo.consultarPrecioInicial());
					System.out.println("Precio final: " + promo.consultarPrecioFinal());

					System.out.println("Acepta la promocion? (S/N )");

					String opcion = scanner.next();

					while (!opcion.equalsIgnoreCase("S") && !opcion.equalsIgnoreCase("N")) {
						System.out.println("Opción inválida. Por favor, ingresa 'S' o 'N'.");
						System.out.print("¿Acepta sugerencia? (S/N): ");
						opcion = scanner.next();
					}

					if (opcion.equalsIgnoreCase("S")) {
						System.out.println("Promocion Aceptada");

						itinerario.agregarPromocion(promo);
						usuario.actualizarPresupuestoYTiempo(promo.consultarPrecioFinal(),
								promo.consultarTiempoTotal());
						// actualizo cupo de promo y atracciones
						listaPromociones.get(indicePromoAOfertar).actualizarCupo();
						List<Atraccion> atraccionesIncluidas = new ArrayList<Atraccion>();
						atraccionesIncluidas = listaPromociones.get(indicePromoAOfertar).getAtraccionesIncluidas();
						for (Atraccion atraccion : atraccionesIncluidas) {
							int indice = listaAtracciones.indexOf(atraccion);
							listaAtracciones.get(indice).actualizarCupo();
						}

					} else {
						System.out.println("Promocion rechazada");
					}

				} else {
					continue;
				}
				
			}
			
			// for por atraccion preferida
			
			

			// for promo restante

			// for promo atraccion restante
			
			scanner.close();
			break; // corta en el primero
		}



	}

}
