package com.api.tests;

import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.constants.Role;
import com.api.request.models.Search;
import com.api.services.JobService;
import com.api.utils.SpecUtil;

@Listeners(com.listeners.APITestListener.class)
public class SearchJobAPITest {
	
	private Search searchJobAPIPaylaod;
	private static final String JOB_NUMBER = "JOB_266703";
	private JobService jobService;
	
	@BeforeMethod(description = "Instantiating the JobService object and Creating the SearchJobAPIRequest paylaod")
	public void setup() {
		jobService = new JobService();
		searchJobAPIPaylaod = new Search(JOB_NUMBER);
	}
	
	@Test(description = "Verify if the search api is working properly", groups = {"e2e", "smoke", "api"})
	public void searchJobAPITest() {
		jobService.search(Role.FD, searchJobAPIPaylaod)
		.then()
			.spec(SpecUtil.responseSpec_OK())
			.body("message", Matchers.equalTo("Success"))
			.body("data", Matchers.notNullValue());
	}

}
