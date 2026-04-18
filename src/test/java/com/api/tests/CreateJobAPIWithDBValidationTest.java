package com.api.tests;

import static com.api.constants.Role.FD;
import static com.api.utils.DateTimeUtil.getTimeWithDaysAgo;
import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constants.Model;
import com.api.constants.Oem;
import com.api.constants.Platform;
import com.api.constants.Problem;
import com.api.constants.Product;
import com.api.constants.ServiceLocation;
import com.api.constants.WarrantyStatus;
import com.api.request.models.CreateJobPayload;
import com.api.request.models.Customer;
import com.api.request.models.CustomerAddress;
import com.api.request.models.CustomerProduct;
import com.api.request.models.Problems;
import com.database.dao.CustomerAddressDao;
import com.database.dao.CustomerDao;
import com.database.dao.CustomerProductDao;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;
import com.database.model.CustomerProductDBModel;

import io.restassured.response.Response;

public class CreateJobAPIWithDBValidationTest {

	private CreateJobPayload createJobPayload;
	private Customer customer;
	private CustomerAddress customerAddress;
	private CustomerProduct customerProduct;

	@BeforeMethod(description = "Creating createjob api request paylaod")
	public void setUp() {
		customer = new Customer("Chetan", "AG", "7090191755", "", "agchetan18@gmail.com", "");
		customerAddress = new CustomerAddress("D 404", "Vasant Galaxy", "Mangalawar Pet", "Inorbit",
				"Laxmi Nagar", "587311", "India", "Karnataka");
		customerProduct = new CustomerProduct(getTimeWithDaysAgo(10), "83242811903268",
				"83242811903268", "83242811903268", getTimeWithDaysAgo(10), Product.NEXUS_2.getCode(),
				Model.NEXUS_2_BLUE.getCode());
		Problems problems = new Problems(Problem.SMARTPHONE_IS_RUNNING_SLOW.getCode(), "Battery Issue");
		List<Problems> problemsList = new ArrayList<Problems>();
		problemsList.add(problems);

		createJobPayload = new CreateJobPayload(ServiceLocation.SERVICE_LOCATION_A.getCode(),
				Platform.FRONT_DESK.getCode(), WarrantyStatus.IN_WARRENTY.getCode(), Oem.GOOGLE.getCode(), customer,
				customerAddress, customerProduct, problemsList);

	}

	@Test(description = "Verify if the create job api able to create Inwarranty job", groups = { "api", "regression",
			"smoke" })
	public void createJobAPITest() {

		Response response = given().spec(requestSpecWithAuth(FD, createJobPayload)).when().post("/job/create").then()
				.spec(responseSpec_OK()).body("message", equalTo("Job created successfully. "))
				.body(matchesJsonSchemaInClasspath("responseSchema/CreateJobAPIResponseSchema.json"))
				.body("data.mst_service_location_id", equalTo(1)).body("data.job_number", startsWith("JOB_")).extract()
				.response();
		
		int customerId = response.then().extract().body().jsonPath().getInt("data.tr_customer_id");
		
		CustomerDBModel customerDataFromDB = CustomerDao.getCustomerInfo(customerId);

		Assert.assertEquals(customerDataFromDB.getFirst_name(), customer.first_name());
		Assert.assertEquals(customerDataFromDB.getLast_name(), customer.last_name());
		Assert.assertEquals(customerDataFromDB.getMobile_number(), customer.mobile_number());
		Assert.assertEquals(customerDataFromDB.getMobile_number_alt(), customer.mobile_number_alt());
		Assert.assertEquals(customerDataFromDB.getEmail_id(), customer.email_id());
		Assert.assertEquals(customerDataFromDB.getEmail_id_alt(), customer.email_id_alt());

		CustomerAddressDBModel customerAddressDataFromDB = CustomerAddressDao
				.getCustomerAddressInfo(customerDataFromDB.getTr_customer_address_id());

		Assert.assertEquals(customerAddressDataFromDB.getFlat_number(), customerAddress.flat_number());
		Assert.assertEquals(customerAddressDataFromDB.getApartment_name(), customerAddress.apartment_name());
		Assert.assertEquals(customerAddressDataFromDB.getStreet_name(), customerAddress.street_name());
		Assert.assertEquals(customerAddressDataFromDB.getLandmark(), customerAddress.landmark());
		Assert.assertEquals(customerAddressDataFromDB.getArea(), customerAddress.area());
		Assert.assertEquals(customerAddressDataFromDB.getPincode(), customerAddress.pincode());
		Assert.assertEquals(customerAddressDataFromDB.getCountry(), customerAddress.country());
		Assert.assertEquals(customerAddressDataFromDB.getState(), customerAddress.state());
		
		int customerProductId = response.then().extract().body().jsonPath().getInt("data.tr_customer_product_id");
		CustomerProductDBModel customerProductDataFromDB = CustomerProductDao.getCustomerProductInfo(customerProductId);
		
		Assert.assertEquals(customerProductDataFromDB.getMst_model_id(), customerProduct.mst_model_id());
		Assert.assertEquals(customerProductDataFromDB.getImei1(), customerProduct.imei1());
		Assert.assertEquals(customerProductDataFromDB.getImei2(), customerProduct.imei2());
		Assert.assertEquals(customerProductDataFromDB.getSerial_number(), customerProduct.serial_number());
		Assert.assertEquals(customerProductDataFromDB.getDop(), customerProduct.dop());
		Assert.assertEquals(customerProductDataFromDB.getPopurl(), customerProduct.popurl());
		

	}

}
