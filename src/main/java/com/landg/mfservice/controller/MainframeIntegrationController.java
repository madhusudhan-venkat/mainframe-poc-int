package com.landg.mfservice.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.landg.mfservice.common.CommonConstants;
import com.landg.mfservice.common.CommonValidation;
import com.landg.mfservice.service.MainframeIntegrationServiceDao;
import com.landg.mfservice.vo.WIPServiceInput;
import com.landg.mfservice.vo.WIPServiceOutput;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/MFIntegrationService")
public class MainframeIntegrationController {

	@Autowired
	private MainframeIntegrationServiceDao mfIntegrationService;
	
	@Autowired
	private CommonValidation commonValidation;
	
	/**
	 * This service retrieves WIP details. It accepts policy number, business function codes as input 
	 * and return if wip exists on the policy or not.
	 * 
	 * @param wipServiceInput 
	 * @return wipServiceOutput 
	 */
	
	@RequestMapping(method = RequestMethod.POST, value="/retrieveWIPDetails") 
    @ResponseBody
    public ResponseEntity<WIPServiceOutput> isWIPExists(@RequestBody WIPServiceInput wipServiceInput) { 
		WIPServiceOutput wipServiceOutput =  new WIPServiceOutput();
		
		if(commonValidation.isValidInput(wipServiceInput)){
			wipServiceOutput = mfIntegrationService.isWIPExists(wipServiceInput);
		} else {
			wipServiceOutput.setErrorCode(CommonConstants.ERROR_400);
			wipServiceOutput.setErrorMsg("Invalid input provided.");
		}	
		
		return new ResponseEntity<WIPServiceOutput>(wipServiceOutput,HttpStatus.OK);
	}
}
