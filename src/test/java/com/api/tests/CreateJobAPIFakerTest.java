package com.api.tests;

import static com.api.constants.Role.FD;
import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.request.models.CreateJobPayload;
import com.api.request.models.Customer;
import com.api.utils.FakerDataGenerator;
import com.database.dao.CustomerAddressDao;
import com.database.dao.CustomerDao;
import com.database.dao.CustomerProductDao;
import com.database.dao.JobHeadDao;
import com.database.dao.MapJobProblemsDao;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;
import com.database.model.CustomerProductDBModel;
import com.database.model.JobHeadDBModel;
import com.database.model.MapJobProblemsDBModel;

import io.restassured.response.Response;

public class CreateJobAPIFakerTest {
	public static final String COUNTRY = "India";
	private CreateJobPayload createJobPayload;
	
	@BeforeMethod(description = "Creating createjob api request paylaod")
	public void setUp() {

		createJobPayload = FakerDataGenerator.generateFakeCreateJobData();

	}
	
	@Test(description = "Verify if the create job api able to create Inwarranty job", groups = {"api", "regression", "smoke"})
	public void createJobAPITest() {
		
		Response response =
		given()
			.spec(requestSpecWithAuth(FD, createJobPayload))
		.when()
			.post("/job/create")
		.then()
			.spec(responseSpec_OK())
			.body("message", equalTo("Job created successfully. "))
			.body(matchesJsonSchemaInClasspath("responseSchema/CreateJobAPIResponseSchema.json"))
			.body("data.mst_service_location_id", equalTo(1))
			.body("data.job_number", startsWith("JOB_"))
			.extract().response();
		
		int customerId = response.then().extract().body().jsonPath().getInt("data.tr_customer_id");
		
		Customer expectedCustomerData = createJobPayload.customer();
		CustomerDBModel customerDataFromDB = CustomerDao.getCustomerInfo(customerId);
		
		Assert.assertEquals(customerDataFromDB.getFirst_name(), expectedCustomerData.first_name());
		Assert.assertEquals(customerDataFromDB.getLast_name(), expectedCustomerData.last_name());
		Assert.assertEquals(customerDataFromDB.getMobile_number(), expectedCustomerData.mobile_number());
		Assert.assertEquals(customerDataFromDB.getMobile_number_alt(), expectedCustomerData.mobile_number_alt());
		Assert.assertEquals(customerDataFromDB.getEmail_id(), expectedCustomerData.email_id());
		Assert.assertEquals(customerDataFromDB.getEmail_id_alt(), expectedCustomerData.email_id_alt());
		
		
		CustomerAddressDBModel customerAddressDataFromDB = CustomerAddressDao
				.getCustomerAddressInfo(customerDataFromDB.getTr_customer_address_id());

		Assert.assertEquals(customerAddressDataFromDB.getFlat_number(),createJobPayload.customer_address().flat_number());
		Assert.assertEquals(customerAddressDataFromDB.getApartment_name(),createJobPayload.customer_address().apartment_name());
		Assert.assertEquals(customerAddressDataFromDB.getStreet_name(),createJobPayload.customer_address().street_name());
		Assert.assertEquals(customerAddressDataFromDB.getLandmark(), createJobPayload.customer_address().landmark());
		Assert.assertEquals(customerAddressDataFromDB.getArea(), createJobPayload.customer_address().area());
		Assert.assertEquals(customerAddressDataFromDB.getPincode(), createJobPayload.customer_address().pincode());
		Assert.assertEquals(customerAddressDataFromDB.getCountry(), createJobPayload.customer_address().country());
		Assert.assertEquals(customerAddressDataFromDB.getState(), createJobPayload.customer_address().state());
		
		JobHeadDBModel jobHeadDataFromDB = JobHeadDao.getJobHeadDataFromDB(customerId);
		Assert.assertEquals(jobHeadDataFromDB.getMst_oem_id(), createJobPayload.mst_oem_id());
		Assert.assertEquals(jobHeadDataFromDB.getMst_warrenty_status_id(), createJobPayload.mst_warrenty_status_id());		
		Assert.assertEquals(jobHeadDataFromDB.getMst_platform_id(), createJobPayload.mst_platform_id());
		Assert.assertEquals(jobHeadDataFromDB.getMst_service_location_id(), createJobPayload.mst_service_location_id());
		
		int customerProductId = response.then().extract().body().jsonPath().getInt("data.tr_customer_product_id");
		CustomerProductDBModel customerProductDataFromDB = CustomerProductDao.getCustomerProductInfo(customerProductId);
		
		Assert.assertEquals(customerProductDataFromDB.getMst_model_id(), createJobPayload.customer_product().mst_model_id());
		Assert.assertEquals(customerProductDataFromDB.getImei1(), createJobPayload.customer_product().imei1());
		Assert.assertEquals(customerProductDataFromDB.getImei2(), createJobPayload.customer_product().imei2());
		Assert.assertEquals(customerProductDataFromDB.getSerial_number(), createJobPayload.customer_product().serial_number());
		Assert.assertEquals(customerProductDataFromDB.getPopurl(), createJobPayload.customer_product().popurl());
		Assert.assertEquals(customerProductDataFromDB.getDop(), createJobPayload.customer_product().dop());
		
		
	}

}
