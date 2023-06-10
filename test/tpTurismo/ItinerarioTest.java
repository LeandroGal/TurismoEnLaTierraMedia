package tpTurismo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class ItinerarioTest {

	@Test
	public void NoPuedeOfertarPromocion() {

		List<Atraccion> atraccionesIncluidas = new ArrayList<Atraccion>();

		Atraccion atraccion = new Atraccion("Lothlorien", 21, 1.5f, 4, "Aventura");
		Atraccion atraccion2 = new Atraccion("Bosque Negro", 19, 3, 1, "Aventura");
		Atraccion atraccion3 = new Atraccion("Mordor", 18, 1, 4, "Aventura");
		Atraccion atraccion4 = new Atraccion("Moria", 22, 2.5f, 6, "Aventura");

		atraccionesIncluidas.add(atraccion);
		atraccionesIncluidas.add(atraccion2);
		atraccionesIncluidas.add(atraccion3);

		PromocionAbsoluta promo = new PromocionAbsoluta("Pack Super Aventura", atraccionesIncluidas, 2, 34);

		List<Atraccion> atraccionesIncluidas2 = new ArrayList<Atraccion>();
		atraccionesIncluidas2.add(atraccion4);
		atraccionesIncluidas2.add(atraccion3);

		PromocionPorcentual promo2 = new PromocionPorcentual("Pack Aventura", atraccionesIncluidas2, 1, 25);

		Itinerario itinerario = new Itinerario();

		itinerario.agregarPromocion(promo);

		boolean esperado = false;
		boolean obtenido = itinerario.puedeOfertarPromocion(promo2);

		Assert.assertEquals(esperado, obtenido);

	}

	@Test
	public void PuedeOfertarPromocion() {

		List<Atraccion> atraccionesIncluidas = new ArrayList<Atraccion>();

		Atraccion atraccion = new Atraccion("Lothlorien", 21, 1.5f, 4, "Aventura");
		Atraccion atraccion2 = new Atraccion("Bosque Negro", 19, 3, 1, "Aventura");
		Atraccion atraccion3 = new Atraccion("Mordor", 18, 1, 4, "Aventura");
		Atraccion atraccion4 = new Atraccion("Moria", 22, 2.5f, 6, "Aventura");

		atraccionesIncluidas.add(atraccion);
		atraccionesIncluidas.add(atraccion2);

		PromocionAbsoluta promo = new PromocionAbsoluta("Pack Super Aventura", atraccionesIncluidas, 2, 34);

		List<Atraccion> atraccionesIncluidas2 = new ArrayList<Atraccion>();
		atraccionesIncluidas2.add(atraccion4);
		atraccionesIncluidas2.add(atraccion3);

		PromocionPorcentual promo2 = new PromocionPorcentual("Pack Aventura", atraccionesIncluidas2, 1, 25);

		Itinerario itinerario = new Itinerario();

		itinerario.agregarPromocion(promo);

		boolean esperado = true;
		boolean obtenido = itinerario.puedeOfertarPromocion(promo2);

		Assert.assertEquals(esperado, obtenido);

	}

	@Test
	public void PuedeOfertarAtraccion() {

		List<Atraccion> atraccionesIncluidas = new ArrayList<Atraccion>();

		Atraccion atraccion = new Atraccion("Lothlorien", 21, 1.5f, 4, "Aventura");
		Atraccion atraccion2 = new Atraccion("Bosque Negro", 19, 3, 1, "Aventura");
		Atraccion atraccion3 = new Atraccion("Mordor", 18, 1, 4, "Aventura");
		Atraccion atraccion4 = new Atraccion("Moria", 22, 2.5f, 6, "Aventura");

		atraccionesIncluidas.add(atraccion);
		atraccionesIncluidas.add(atraccion2);
		atraccionesIncluidas.add(atraccion3);

		PromocionAbsoluta promo = new PromocionAbsoluta("Pack Super Aventura", atraccionesIncluidas, 2, 34);

		Itinerario itinerario = new Itinerario();

		itinerario.agregarPromocion(promo);

		boolean esperado = true;
		boolean obtenido = itinerario.puedeOfertarAtraccion(atraccion4);

		Assert.assertEquals(esperado, obtenido);

	}

	@Test
	public void NoPuedeOfertarAtraccionEnPromocionAceptadas() {

		List<Atraccion> atraccionesIncluidas = new ArrayList<Atraccion>();

		Atraccion atraccion = new Atraccion("Lothlorien", 21, 1.5f, 4, "Aventura");
		Atraccion atraccion2 = new Atraccion("Bosque Negro", 19, 3, 1, "Aventura");
		Atraccion atraccion3 = new Atraccion("Mordor", 18, 1, 4, "Aventura");

		atraccionesIncluidas.add(atraccion);
		atraccionesIncluidas.add(atraccion2);
		atraccionesIncluidas.add(atraccion3);

		PromocionAbsoluta promo = new PromocionAbsoluta("Pack Super Aventura", atraccionesIncluidas, 2, 34);

		Itinerario itinerario = new Itinerario();

		itinerario.agregarPromocion(promo);

		boolean esperado = false;
		boolean obtenido = itinerario.puedeOfertarAtraccion(atraccion2);

		Assert.assertEquals(esperado, obtenido);

	}

	@Test
	public void NoPuedeOfertarAtraccionEnAtraccionesAceptadas() {
		
		Atraccion atraccion4 = new Atraccion("Moria", 22, 2.5f, 6, "Aventura");

		Itinerario itinerario = new Itinerario();

		itinerario.agregarAtraccion(atraccion4);

		boolean esperado = false;
		boolean obtenido = itinerario.puedeOfertarAtraccion(atraccion4);

		Assert.assertEquals(esperado, obtenido);

	}
	
	@Test
	public void Archivos() {
		
		List<Atraccion> atraccionesIncluidas = new ArrayList<Atraccion>();

		Atraccion atraccion = new Atraccion("Lothlorien", 21, 1.5f, 4, "Aventura");
		Atraccion atraccion2 = new Atraccion("Bosque Negro", 19, 3, 1, "Aventura");
		Atraccion atraccion3 = new Atraccion("Mordor", 18, 1, 4, "Aventura");
		Atraccion atraccion4 = new Atraccion("Minas Tirith", 29, 1.5f, 4, "Paisaje");

		atraccionesIncluidas.add(atraccion);
		atraccionesIncluidas.add(atraccion2);
		atraccionesIncluidas.add(atraccion3);

		PromocionAbsoluta promo = new PromocionAbsoluta("Pack Super Aventura", atraccionesIncluidas, 2, 34);
		
		Itinerario itinerario = new Itinerario();

		itinerario.agregarPromocion(promo);
		itinerario.agregarAtraccion(atraccion4);
		
		Usuario usuario = new Usuario("Frodo Bolson","Aventura",64,7);
		
		itinerario.grabarItinerario(usuario);
		
		 try {
	            byte[] f1 = Files.readAllBytes(Paths.get("archivos/out_esperado/Itinerario" + usuario.consultarNombre() + ".out"));
	            byte[] f2 = Files.readAllBytes(Paths.get("archivos/out/Itinerario" + usuario.consultarNombre() + ".out"));
	            Assert.assertArrayEquals(f1, f2);
	        } catch (IOException e) {
	            e.printStackTrace(); //Muestra un poco más de info si falla el test
	            Assert.fail();
	        }
		
	}
	
	

}
