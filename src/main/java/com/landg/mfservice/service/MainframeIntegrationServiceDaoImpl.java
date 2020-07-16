package com.landg.mfservice.service;

import java.util.UUID;

import javax.resource.cci.ConnectionFactory;
import javax.resource.cci.Interaction;
import javax.resource.cci.Record;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jca.cci.core.support.CciDaoSupport;

import com.ibm.connector2.cics.ECIInteractionSpec;
import com.landg.mfservice.common.CommonConstants;
import com.landg.mfservice.records.impl.RecordImpl;
import com.landg.mfservice.records.vo.LSOA40Record;
import com.landg.mfservice.vo.WIPServiceInput;
import com.landg.mfservice.vo.WIPServiceOutput;

public class MainframeIntegrationServiceDaoImpl extends CciDaoSupport implements MainframeIntegrationServiceDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(MainframeIntegrationServiceDao.class);
		
	public WIPServiceOutput isWIPExists(WIPServiceInput wipServiceInput){
		ConnectionFactory connectionFactory=getConnectionFactory();
		
		RecordImpl inputRecord = new RecordImpl();
		
		inputRecord.setRecordShortDescription(CommonConstants.LSOA40_DESC);
		inputRecord.setRecordName(CommonConstants.LSOA40);
		
		RecordImpl outputRecord = new RecordImpl(inputRecord.getRecordName(), inputRecord.getRecordShortDescription());
		
		WIPServiceOutput wipServiceOutput = new WIPServiceOutput();
		
		ECIInteractionSpec is = new ECIInteractionSpec();
		Interaction interaction = null;
		is.setFunctionName(CommonConstants.LSOA40);
		
		try {
			assignLSOA40InputValues(wipServiceInput, inputRecord);
			
			interaction = connectionFactory.getConnection().createInteraction();
			interaction.execute(is, (Record) inputRecord, (Record) outputRecord);

			assignLSOA40OutputValues(wipServiceOutput, outputRecord);
			
		} catch (Exception e) {
			LOGGER.error("Unexpected exception has occurred:"+e.getMessage());
			wipServiceOutput.setErrorCode(CommonConstants.ERROR_500);
			wipServiceOutput.setErrorMsg("Unexpected exception occurred:"+e.getMessage());
		}
			
		return wipServiceOutput;
	}
	
	private static void assignLSOA40InputValues(WIPServiceInput wipServiceInput, RecordImpl inputRecord) {
		
		byte[] buffer = new byte[1333];
		int i=0;
		
		LSOA40Record lsoa40Record =  new LSOA40Record(buffer, false);
		
		lsoa40Record.setSoaz1PrcCorrGuid(UUID.randomUUID().toString());
		lsoa40Record.setSoa40PolNo(wipServiceInput.getPolicyNum());
		lsoa40Record.setSoa40NoOfBusFuncIds(wipServiceInput.getBusFunctionId().length);
		
		for(String busFunc : wipServiceInput.getBusFunctionId()) {
			lsoa40Record.setSoa40BusFuncId(i++, busFunc);			
		}
		
		inputRecord.setBytes(lsoa40Record.getByteBuffer());
	}
	
	private void assignLSOA40OutputValues(WIPServiceOutput wipServiceOutput, RecordImpl outputRecord) {
		
		LSOA40Record lsoa40Record =  new LSOA40Record(outputRecord.getBytes(), true); 
		
		int returnCode =  lsoa40Record.getSoaz2ReturnCode();

		if(returnCode == 0) {
			String wipFlag = lsoa40Record.getSoa40WipCheckPassed();
			wipServiceOutput.setWipCheckPassed(wipFlag);
			if(CommonConstants.NO.equalsIgnoreCase(wipFlag)) {
				wipServiceOutput.setWipRetryDate(lsoa40Record.getSoa40WipRetryDate());
				wipServiceOutput.setWipBusFunc(lsoa40Record.getSoa40WipBusFuncId());
			}
		} else {
			wipServiceOutput.setErrorCode(lsoa40Record.getSoaz2ErrorMsgNum());
			wipServiceOutput.setErrorMsg(lsoa40Record.getSoaz2ErrorMsgTxt());
		}		
	}
				
}