package com.skshazena.flooringmastery;

import com.skshazena.flooringmastery.controller.FlooringController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Flooring Mastery Project - This CRUD application is used to hold orders for
 * TSG Flooring. It follows the MVC structure and contains a service layer.
 *
 * @author Shazena Khan
 *
 * Date Created: Jun 28, 2020
 */
public class App {

    public static void main(String[] args) throws Exception {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        FlooringController controller = ctx.getBean("controller", FlooringController.class);

        controller.run();

    }
}
