# -SPRING-MySQL-Database-Connection-using-JDBC-STEPS

=============================================
  SPRING MySQL Database Connection using JDBC STEPS
=============================================
Step1: Create maven project
                               Group id: com.cdac
                               Artifact id: spring-database-connection
                               Version : 1.0
===========================================================


Step 2:  Create file Spring-intro/src/main/resources app-config.xml file
               And add following dependencies 


**********************************************************************************
<?xml version="1.0" encoding="UTF-8"?>
<beans 
	xmlns="http://www.springframework.org/schema/beans"
	xmlns:context="http://www.springframework.org/schema/context"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="
        http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

 	<context:component-scan base-package="com.cdac.component" />

</beans>

**********************************************************************************

===========================================================

Step 3: add following dependencies  pom.xml file

 	<dependencies>
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-context</artifactId>
			<version>5.3.15</version>
		</dependency>
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-jdbc</artifactId>
			<version>5.3.15</version>
		</dependency>
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<version>8.0.26</version>
		</dependency>
		<dependency>
			<groupId>junit</groupId>
			<artifactId>junit</artifactId>
			<version>4.13.2</version>
		</dependency>
	</dependencies>



=====================================================
Step 4: create Interface file CarPartsInventory.java  file in
               com.cdac.component  package

package com.cdac.component;
import java.util.List;
public interface CarPartsInventory {
	public void addNewPart(CarPart carPart);
	public List<CarPart> getAvailableParts();
}


=====================================================

Step5: create CarPart.java file in com.cdac.app package

package com.cdac.component;
public class CarPart {

	private int partNo;
	private String partName;
	private String carModel;
	private double price;
	private int quantity;
	
	public int getPartNo() {
		return partNo;
	}
	public void setPartNo(int partNo) {
		this.partNo = partNo;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public String getCarModel() {
		return carModel;
	}
	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}



===========================================================
Step 6: implement  CarPartsInventory interface in CarPartsInventoryimp1
 And also add JDBC  database connection

package com.cdac.component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Component;

@Component("carParts1")
public class CarPartsInventoryImpl1 implements CarPartsInventory {

	public void addNewPart(CarPart carPart) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/training", "root", "cdac");
			stmt = conn.prepareStatement("INSERT INTO tbl_carpart(part_name, car_model, price, quantity) VALUES(?, ?, ?, ?)");
			stmt.setString(1, carPart.getPartName());
			stmt.setString(2, carPart.getCarModel());
			stmt.setDouble(3, carPart.getPrice());
			stmt.setInt(4, carPart.getQuantity());
			stmt.executeUpdate(); //DML
		}
		catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace(); //rather we should throw some user defined exception
		}
		finally {
			try { conn.close(); } catch(Exception e) { }
		}
	}

	public List<CarPart> getAvailableParts() {
		return null;
	}

	
}






=============================================================
Step 7: add class file App.java file in com.cdac.app package

package com.cdac.app;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.cdac.component.CarPart;
import com.cdac.component.CarPartsInventory;

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


 

=============================================================
Step 8: write following Query in SQL
TODO: Create Table in the DB before running this example

create table tbl_carpart(part_no int primary key auto_increment, part_name varchar(30), car_model varchar(35), price double, quantity int);

=========================================
         See OUTPUT in MySQL
========================================

+---------+------------+------------+-------+----------+----------+
| part_no | part_name  | car_model  | price | quantity   |
+---------+------------+------------+-------+----------+----------+
|       1     | Nut & Bolt   | Maruti 800  |   500 |       99     |     
+---------+------------+------------+-------+----------+----------+


