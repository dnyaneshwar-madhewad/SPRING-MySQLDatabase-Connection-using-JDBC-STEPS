package com.cdac.app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cdac.component.CarPart;
import com.cdac.component.CarPartsInventory;

/*
 * TODO: Create Table in the DB before running this example
 * create table tbl_carpart(part_no int primary key auto_increment, part_name varchar(30), car_model varchar(35), price double, quantity int);
 */
public class App {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("app-config.xml");
		CarPartsInventory cp = (CarPartsInventory) ctx.getBean("carParts1");
		
		CarPart c = new CarPart();
		c.setPartName("Nut & Bolt");
		c.setCarModel("Maruti 800");
		c.setPrice(500);
		c.setQuantity(99);
		cp.addNewPart(c);
		
	}
}
