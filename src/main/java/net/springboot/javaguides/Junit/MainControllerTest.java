package net.springboot.javaguides.Junit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import net.springboot.javaguides.controller.MainController;


@SpringBootTest
public class MainControllerTest {

	@Autowired
	private ApplicationContext context;
	
	@Test
	public void testLogin(){
		MainController mainController = context.getBean(MainController.class);
		assertEquals("login", mainController.login());
	}
	
	@Test
	public void testHome(){
		MainController mainController = context.getBean(MainController.class);
		assertEquals("index", mainController.home());
	}

}
