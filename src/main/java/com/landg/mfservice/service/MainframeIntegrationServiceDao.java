package com.landg.mfservice.service;

import com.landg.mfservice.vo.WIPServiceInput;
import com.landg.mfservice.vo.WIPServiceOutput;

public interface MainframeIntegrationServiceDao {

		public WIPServiceOutput isWIPExists(WIPServiceInput wipServiceInput);
		
}

