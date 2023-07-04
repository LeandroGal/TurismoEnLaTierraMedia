package tpTurismo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Archivo {

	public static List<Usuario> leerArchivoUsuarios() throws IOException {
		List<Usuario> usuarios = new ArrayList<Usuario>();

		Scanner scanner = null;

		try {
			File file = new File("archivos/in/Usuarios.in");

			scanner = new Scanner(file);

			scanner.useLocale(Locale.ENGLISH);

			String linea = scanner.nextLine();

			while (scanner.hasNextLine()) {
				linea = scanner.nextLine();
				String[] partes = linea.split("\\|");

				String nombreApellido = partes[0];
				String tipoAtraccion = partes[1];
				int presupuesto = Integer.parseInt(partes[2]);
				float tiempo = Float.parseFloat(partes[3]);

				Usuario usuario = new Usuario(nombreApellido, tipoAtraccion, presupuesto, tiempo);

				usuarios.add(usuario);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}
		return usuarios;
	}

	public static List<Atraccion> leerArchivoAtracciones() throws IOException {
		List<Atraccion> atracciones = new ArrayList<Atraccion>();

		Scanner scanner = null;

		try {
			File file = new File("archivos/in/Atracciones.in");

			scanner = new Scanner(file);

			scanner.useLocale(Locale.ENGLISH);

			String linea = scanner.nextLine();

			while (scanner.hasNextLine()) {

				linea = scanner.nextLine();
				String[] partes = linea.split("\\|");

				String nombreAtraccion = partes[0];
				int costo = Integer.parseInt(partes[1]);
				float tiempo = Float.parseFloat(partes[2]);
				int cupo = Integer.parseInt(partes[3]);
				String tipoAtraccion = partes[4];

				Atraccion atraccion = new Atraccion(nombreAtraccion, costo, tiempo, cupo, tipoAtraccion);

				atracciones.add(atraccion);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}
		return atracciones;
	}

	public static List<Promocion> leerArchivoPromociones(List<Atraccion> listaAtracciones) throws IOException {
		List<Promocion> promociones = new ArrayList<Promocion>();

		Scanner scanner = null;
		try {
			File file = new File("archivos/in/Promociones.in");

			scanner = new Scanner(file);

			scanner.useLocale(Locale.ENGLISH);

			String linea = scanner.nextLine();

			while (scanner.hasNextLine()) {

				linea = scanner.nextLine();
				String[] partes = linea.split("\\|", -1);

				String nombrePromocion = partes[0];
				int tipo = Integer.parseInt(partes[1]);
				String lineaAtracciones = partes[2];
				String[] atracciones = lineaAtracciones.split(",");
				String porcentaje = partes[3]; // hay que castearlo adelante
				String precio = partes[4]; // idem
				String lineaAtraccionesGratis = partes[5];
				String[] atraccionesGratis = lineaAtraccionesGratis.split(",", -1);

				List<Atraccion> atraccionesIncluidas = new ArrayList<Atraccion>();

				for (String nombre : atracciones) {
					for (Atraccion atraccion : listaAtracciones) {
						if (nombre.equals(atraccion.getNombreAtraccion())) {
							atraccionesIncluidas.add(atraccion);
							break;
						}

					}
				}

				switch (tipo) {

				case 1: // Promoción Porcental
					PromocionPorcentual promoP = new PromocionPorcentual(nombrePromocion, atraccionesIncluidas, tipo,
							Integer.parseInt(porcentaje));
					promociones.add(promoP);
					break;
				case 2: // Promoción Absoluta
					PromocionAbsoluta promoA = new PromocionAbsoluta(nombrePromocion, atraccionesIncluidas, tipo,
							Integer.parseInt(precio));
					promociones.add(promoA);
					break;
				case 3: // Promoción AxB

					List<Atraccion> listaAtraccionesGratis = new ArrayList<Atraccion>();

					for (String nombre : atraccionesGratis) {
						for (Atraccion atraccion : listaAtracciones) {
							if (nombre.equals(atraccion.getNombreAtraccion())) {
								listaAtraccionesGratis.add(atraccion);
							}
						}
					}

					PromocionAxB promoAxB = new PromocionAxB(nombrePromocion, atraccionesIncluidas, tipo,
							listaAtraccionesGratis);
					promociones.add(promoAxB);
					break;
				default:
					break;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}
		return promociones;
	}
}