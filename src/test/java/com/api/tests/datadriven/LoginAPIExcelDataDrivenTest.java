package com.api.tests.datadriven;

import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.services.AuthService;
import com.dataproviders.api.bean.UserBean;

public class LoginAPIExcelDataDrivenTest {

	private static AuthService authService;
	
	@BeforeMethod(description = "Instanting the AuthService object")
	public void setUp() {
		authService = new AuthService();

	}
	
	
	@Test(description = "Verifying if login api is working for FD user",
			groups = {"api", "regression", "datadriven",  "excel"},
			dataProviderClass = com.dataproviders.DataProviderUtils.class,
			dataProvider = "LoginAPIExcelDataProvider")
	public void loginAPITest(UserBean UserBean) {
		
		authService.login(UserBean)
		.then()
			.spec(responseSpec_OK())
			.body("message", equalTo("Success"))
			.body(matchesJsonSchemaInClasspath("responseSchema/LoginResponseSchema.json"));
	}
}
