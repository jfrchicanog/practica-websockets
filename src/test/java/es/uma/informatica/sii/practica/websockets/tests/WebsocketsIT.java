package es.uma.informatica.sii.practica.websockets.tests;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
// Generated by Selenium IDE
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebsocketsIT {
	private static final int WAITING_TIME = 500;
	private WebDriver driver1;
	private Map<String, Object> vars1;
	JavascriptExecutor js1;
	
	private WebDriver driver2;
	private Map<String, Object> vars2;
	JavascriptExecutor js2;
	
	private static String baseURL;
	@BeforeClass
	public static void setupClass() {
		String server="localhost";
		try (InputStream is = WebsocketsIT.class.getClassLoader().getResourceAsStream("pom.properties")) {
			Properties pomProperties = new Properties();
			pomProperties.load(is);
			server=pomProperties.getProperty("server.host");
		} catch (IOException e) {
			e.printStackTrace();
		}
		baseURL="http://"+server+":8080/practica.websockets/";
	}
	
	@Before
	public void setUp() {
		driver1 = new ChromeDriver();
		js1 = (JavascriptExecutor) driver1;
		vars1 = new HashMap<String, Object>();
		
		driver2 = new ChromeDriver();
		js2 = (JavascriptExecutor) driver2;
		vars2 = new HashMap<String, Object>();
	}
	@After
	public void tearDown() {
		driver1.quit();
		driver2.quit();
	}
	@Test
	public void entrada() throws InterruptedException {
		entraUsuario(driver1, "francis");
		entraUsuario(driver2, "manolo");
		Thread.sleep(WAITING_TIME);
		// 5 | assertText | id=areaChat | >>>>>> Entra manolo
		assertThat(driver1.findElement(By.id("areaChat")).getText(), is(">>>>>> Entra manolo"));
	}
	public void entraUsuario(WebDriver webDriver, String usuario) {
		// Test name: Entrada
		// Step # | name | target | value
		// 1 | open | /practica.websockets-0.0.1-SNAPSHOT/ | 
		webDriver.get(baseURL);
		// 2 | setWindowSize | 1279x832 | 
		webDriver.manage().window().setSize(new Dimension(1279, 832));
		// 3 | type | name=ingreso:j_idt8 | francis
		webDriver.findElement(By.name("ingreso:entradaNombre")).sendKeys(usuario);
		// 4 | click | name=ingreso:j_idt9 | 
		webDriver.findElement(By.name("ingreso:botonEntrada")).click();
	}
	
	@Test
	public void mensaje2() throws InterruptedException {
		entraUsuario(driver1, "francis"); 
		entraUsuario(driver2, "manolo");
		mandaMensaje(driver1, "hola");
		Thread.sleep(WAITING_TIME);
		mandaMensaje(driver2, "adios");
		Thread.sleep(WAITING_TIME);
		// 9 | assertText | id=areaChat | >>>>>> Entra manolo\nfrancis: hola\nmanolo: hola\nmanolo: adios
		assertThat(driver1.findElement(By.id("areaChat")).getText(), is(">>>>>> Entra manolo\nfrancis: hola\nmanolo: adios"));
	}
	
	public void mandaMensaje(WebDriver webDriver, String message) {
		// 5 | click | id=entradaTexto | 
		webDriver.findElement(By.id("entradaTexto")).click();
		// 6 | type | id=entradaTexto | hola
		webDriver.findElement(By.id("entradaTexto")).sendKeys(message);
		// 7 | sendKeys | id=entradaTexto | ${KEY_ENTER}
		webDriver.findElement(By.id("entradaTexto")).sendKeys(Keys.ENTER);
	}
	@Test
	public void mensaje() throws InterruptedException {
		entraUsuario(driver1, "francis"); 
		entraUsuario(driver2, "manolo");
		mandaMensaje(driver1, "hola");
		Thread.sleep(WAITING_TIME);
		assertThat(driver2.findElement(By.id("areaChat")).getText(), is("francis: hola"));
	}
	@Test
	public void salida() throws InterruptedException {
		entraUsuario(driver1, "francis"); 
		entraUsuario(driver2, "manolo");
		driver2.findElement(By.name("salida:botonSalir")).click();
		Thread.sleep(WAITING_TIME);
		// 5 | assertText | id=areaChat | >>>>>> Entra manolo\n<<<<<< Sale manolo
		assertThat(driver1.findElement(By.id("areaChat")).getText(), is(">>>>>> Entra manolo\n<<<<<< Sale manolo"));
	}

	@Test
	public void salida2() {
		entraUsuario(driver1, "francis");
		entraUsuario(driver2, "manolo"); 
		driver1.findElement(By.name("salida:botonSalir")).click();
		// 8 | assertText | id=areaChat | <<<<<< Sale francis
		assertThat(driver2.findElement(By.id("areaChat")).getText(), is("<<<<<< Sale francis"));
	}
	@Test
	public void salida3() throws InterruptedException {
		entraUsuario(driver1, "francis"); 
		entraUsuario(driver2, "manolo");
		Thread.sleep(WAITING_TIME);
		driver2.quit();
		Thread.sleep(WAITING_TIME);
		// 5 | assertText | id=areaChat | >>>>>> Entra manolo\n<<<<<< Sale manolo
		assertThat(driver1.findElement(By.id("areaChat")).getText(), is(">>>>>> Entra manolo\n<<<<<< Sale manolo"));

	}
}
