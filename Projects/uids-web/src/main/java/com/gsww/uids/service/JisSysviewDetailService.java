package com.gsww.uids.service;

import com.gsww.uids.entity.JisSysviewDetail;

public interface JisSysviewDetailService {

	public JisSysviewDetail findByIid(int iid);
	
	public void delete(JisSysviewDetail jisSysviewDetail);

	JisSysviewDetail findByTranscationId(String transcationId);
	
	void save(JisSysviewDetail jisSysviewDetail);
}
