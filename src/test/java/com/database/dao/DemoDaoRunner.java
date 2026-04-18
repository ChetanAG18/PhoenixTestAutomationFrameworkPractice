package com.database.dao;

import java.sql.SQLException;

import org.testng.Assert;

import com.api.request.models.Customer;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;

public class DemoDaoRunner {

	public static void main(String[] args) throws SQLException {
//		CustomerDBModel customerDBModel = CustomerDao.getCustomerInfo(253560);
//		System.out.println(customerDBModel);
//		System.out.println(customerDBModel.getFirst_name());
//		
//		Customer customer = new Customer("Mittie", "Stroman", "503-509-9257", "", "Abdul74@gmail.com", "");
//		System.out.println(customer);
//		System.out.println(customer.first_name());
//		
//		Assert.assertEquals(customerDBModel.getFirst_name(), customer.first_name());
		
		CustomerAddressDBModel customerAddressDBModel =CustomerAddressDao.getCustomerAddressInfo(253930);
		System.out.println(customerAddressDBModel);

	}

}
