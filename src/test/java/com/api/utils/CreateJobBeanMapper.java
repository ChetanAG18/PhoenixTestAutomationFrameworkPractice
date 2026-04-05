package com.api.utils;

import java.util.ArrayList;
import java.util.List;

import com.api.request.models.CreateJobPayload;
import com.api.request.models.Customer;
import com.api.request.models.CustomerAddress;
import com.api.request.models.CustomerProduct;
import com.api.request.models.Problems;
import com.dataproviders.api.bean.CreateJobBean;

public class CreateJobBeanMapper {
	
	private CreateJobBeanMapper() {
		
	}
	
	public static CreateJobPayload mapper(CreateJobBean bean) {
		
		int serviceLocationId = Integer.parseInt(bean.getMst_service_location_id());
		int platformId = Integer.parseInt(bean.getMst_platform_id());
		int warrantyStatusId = Integer.parseInt(bean.getMst_warrenty_status_id());
		int oemId = Integer.parseInt(bean.getMst_oem_id());
		
		Customer customer = new Customer(bean.getCustomer__first_name(), bean.getCustomer__last_name(), bean.getCustomer__mobile_number(), bean.getCustomer__mobile_number_alt(),
				bean.getCustomer__email_id(), bean.getCustomer__email_id_alt());
		
		CustomerAddress customerAddress = new CustomerAddress(bean.getCustomer_address__flat_number(),
				bean.getCustomer_address__apartment_name(),
				bean.getCustomer_address__street_name(),
				bean.getCustomer_address__landmark(),
				bean.getCustomer_address__area(),
				bean.getCustomer_address__pincode(),
				bean.getCustomer_address__country(),
				bean.getCustomer_address__state());
		
		int productId = Integer.parseInt(bean.getCustomer_product__product_id());
		int modelId = Integer.parseInt(bean.getCustomer_product__mst_model_id());
		CustomerProduct customerProduct = new CustomerProduct(bean.getCustomer_product__dop(), 
				bean.getCustomer_product__serial_number(),
				bean.getCustomer_product__imei1(),
				bean.getCustomer_product__imei2(), 
				bean.getCustomer_product__popurl(),
				productId,
				modelId);
		
		List<Problems> problemsList = new ArrayList<Problems>();
		int problemId = Integer.parseInt(bean.getProblems__id());
		Problems problems = new Problems(problemId, bean.getProblems__remark());
		problemsList.add(problems);
		
		CreateJobPayload createJobPayload = new CreateJobPayload(serviceLocationId, platformId, warrantyStatusId, oemId,
				customer,
				customerAddress,
				customerProduct,
				problemsList);
		
		return createJobPayload;
	}

}
