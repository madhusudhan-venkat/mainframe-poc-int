package com.landg.mfservice.common;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.landg.mfservice.vo.WIPServiceInput;

@Component
public class CommonValidation {

	public boolean isValidInput(WIPServiceInput wipServiceInput) {
		boolean flag = true;
		
		if(StringUtils.isEmpty(wipServiceInput.getPolicyNum()) || 
				!StringUtils.isNumeric(wipServiceInput.getPolicyNum()) || 
					StringUtils.length(wipServiceInput.getPolicyNum()) != 10) {
			flag = false;
		} 

		if (ArrayUtils.isEmpty(wipServiceInput.getBusFunctionId())) {
			flag  = false;
		} else {
			for(String busFuncId : wipServiceInput.getBusFunctionId()) {
				if(StringUtils.length(busFuncId) != 4 || 
						!StringUtils.isAlpha(busFuncId)) {
					flag = false;
				}
			}
		}
		return flag;
	}
	
}
