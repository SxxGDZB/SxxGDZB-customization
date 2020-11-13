package com.kiwihouse.web;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.kiwihouse.dao.mapper.AlarmMapper;

@Component
@Order(value = 1)
public class Startup implements CommandLineRunner{
	@Autowired
	AlarmMapper alarmMapper;
@Override
public void run(String... strings) throws Exception {
    System.out.println("##################初始化admin权限##################");
    Document doc = DocumentHelper.createDocument();
    Element ele = doc.addElement("table");
    	List<ReturnCode> list = alarmMapper.selectCode();
    	list.forEach(xx ->{
    		 Element Customers = ele.addElement("Customers");
    	        Customers.addAttribute("Code", xx.getCode().toString());
    	        Customers.addAttribute("msg", xx.getMsg());
    	        try {
    	            XMLWriter writer = new XMLWriter(new FileWriter(new File(
    	                    "F://Customertest.xml")));
    	            writer.write(doc);
    	            writer.close();
    	        } catch (Exception e) {
    	            // TODO: handle exception
    	        }
    	});
//    	WriteAttributes();
    }
public static void WriteAttributes() {
    Document doc = DocumentHelper.createDocument();
    Element ele = doc.addElement("table");
    for (int i = 1; i < 5; i++) {
        Element Customers = ele.addElement("Customers");
        Customers.addAttribute("CustomerID", "ALFKI" + i);
        Customers.addAttribute("CompanyName", "Alfreds Futterkiste" + i);
        Customers.addAttribute("ContactName", "Maria Anders" + i);
        Customers.addAttribute("ContactTitle", "Sales Representative" + i);
        Customers.addAttribute("Address", "Obere Str. 57");
        Customers.addAttribute("City", "beijin");
        Customers.addAttribute("PostalCode", "12209");
        Customers.addAttribute("Country", "Germany");
        Customers.addAttribute("Phone", "030-0074321");
        Customers.addAttribute("Fax", "030-0076545");
        try {
            XMLWriter writer = new XMLWriter(new FileWriter(new File(
                    "F://Customertest.xml")));
            writer.write(doc);
            writer.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

}
}


