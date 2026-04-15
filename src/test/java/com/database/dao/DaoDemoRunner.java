package com.database.dao;

import java.util.Iterator;
import java.util.List;

import com.dataproviders.api.bean.CreateJobBean;

public class DaoDemoRunner {

	public static void main(String[] args) {
		List<CreateJobBean>  createJobBeanList = CreateJobPayloadDataDao.getCreateJobPayloadData();
		for(CreateJobBean createJobBean : createJobBeanList) {
			System.out.println(createJobBean);
		}
	}

}
