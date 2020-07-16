package com.landg.mfservice;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.landg.mfservice.common.CommonConstants;
import com.landg.mfservice.controller.MainframeIntegrationController;
import com.landg.mfservice.records.impl.RecordImpl;
import com.landg.mfservice.service.MainframeIntegrationServiceDao;
import com.landg.mfservice.vo.WIPServiceInput;
import com.landg.mfservice.vo.WIPServiceOutput;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WIPServiceTest {
	
	@Autowired
	MainframeIntegrationController integrationController;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private MainframeIntegrationServiceDao integrationServiceDao;
	
	@Test
	public void testRetrieveWipService_Success() throws Exception{
		
		ObjectMapper mapper = new ObjectMapper();
		WIPServiceInput wipServiceInput = new WIPServiceInput();
		
		String[] busFuncIds = {"CENQ","ADST"};
		wipServiceInput.setPolicyNum("2011000002");
		wipServiceInput.setBusFunctionId(busFuncIds);
		
		String jsonInput = mapper.writeValueAsString(wipServiceInput);
		
		MockHttpServletRequestBuilder builder =  MockMvcRequestBuilders
													.post("/MFIntegrationService/retrieveWIPDetails")
													.contentType(MediaType.APPLICATION_JSON)
													.content(jsonInput);
		
		mockMvc.perform(builder)
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(jsonPath("$.wipCheckPassed").isNotEmpty());
		
		
		wipServiceInput.setPolicyNum("2434010002");
		wipServiceInput.setBusFunctionId(busFuncIds);
		jsonInput = mapper.writeValueAsString(wipServiceInput);
		builder =  MockMvcRequestBuilders
							.post("/MFIntegrationService/retrieveWIPDetails")
							.contentType(MediaType.APPLICATION_JSON)
							.content(jsonInput);
		
		mockMvc.perform(builder)
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(jsonPath("$.wipCheckPassed").isNotEmpty());
		
	}
	
	@Test
	public void testRetrieveWipService_InvalidPolicy() throws Exception{
		
		ObjectMapper mapper = new ObjectMapper();
		WIPServiceInput wipServiceInput = new WIPServiceInput();
		
		String[] busFuncIds = {"CENQ","ADST"};
		wipServiceInput.setPolicyNum("211000000");
		wipServiceInput.setBusFunctionId(busFuncIds);
		String jsonInput = mapper.writeValueAsString(wipServiceInput);
		MockHttpServletRequestBuilder builder =  MockMvcRequestBuilders
													.post("/MFIntegrationService/retrieveWIPDetails")
													.contentType(MediaType.APPLICATION_JSON)
													.content(jsonInput);
		mockMvc.perform(builder)
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(jsonPath("$.errorMsg").value("Invalid input provided."))
					.andExpect(jsonPath("$.errorCode").value(CommonConstants.ERROR_400));


		wipServiceInput.setPolicyNum("");
		jsonInput = mapper.writeValueAsString(wipServiceInput);
		builder =  MockMvcRequestBuilders
								.post("/MFIntegrationService/retrieveWIPDetails")
								.contentType(MediaType.APPLICATION_JSON)
								.content(jsonInput);
		mockMvc.perform(builder)
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(jsonPath("$.errorMsg").value("Invalid input provided."))
					.andExpect(jsonPath("$.errorCode").value(CommonConstants.ERROR_400));
		
		
		wipServiceInput.setPolicyNum("20110000X2");
		jsonInput = mapper.writeValueAsString(wipServiceInput);
		builder =  MockMvcRequestBuilders
								.post("/MFIntegrationService/retrieveWIPDetails")
								.contentType(MediaType.APPLICATION_JSON)
								.content(jsonInput);
		mockMvc.perform(builder)
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(jsonPath("$.errorMsg").value("Invalid input provided."))
					.andExpect(jsonPath("$.errorCode").value(CommonConstants.ERROR_400));
		
	}
	
	@Test
	public void testRetrieveWipService_InvalidBusFuncFormat() throws Exception{
		
		ObjectMapper mapper = new ObjectMapper();
		WIPServiceInput wipServiceInput = new WIPServiceInput();
		
		String[] busFuncIds = {"CEN","ADST"};
		wipServiceInput.setPolicyNum("2011000002");
		wipServiceInput.setBusFunctionId(busFuncIds);
		String jsonInput = mapper.writeValueAsString(wipServiceInput);
		MockHttpServletRequestBuilder builder =  MockMvcRequestBuilders
													.post("/MFIntegrationService/retrieveWIPDetails")
													.contentType(MediaType.APPLICATION_JSON)
													.content(jsonInput);
		mockMvc.perform(builder)
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(jsonPath("$.errorMsg").value("Invalid input provided."));
		
		
		
		busFuncIds = new String[0];
		wipServiceInput.setBusFunctionId(busFuncIds);
		jsonInput = mapper.writeValueAsString(wipServiceInput);
		builder =  MockMvcRequestBuilders
							.post("/MFIntegrationService/retrieveWIPDetails")
							.contentType(MediaType.APPLICATION_JSON)
							.content(jsonInput);
		mockMvc.perform(builder)
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(jsonPath("$.errorMsg").value("Invalid input provided."))
					.andExpect(jsonPath("$.errorCode").value(CommonConstants.ERROR_400));
		
		
		busFuncIds = new String[1];
		busFuncIds[0] = "CE£Q";
		wipServiceInput.setBusFunctionId(busFuncIds);
		jsonInput = mapper.writeValueAsString(wipServiceInput);
		builder =  MockMvcRequestBuilders
							.post("/MFIntegrationService/retrieveWIPDetails")
							.contentType(MediaType.APPLICATION_JSON)
							.content(jsonInput);
		mockMvc.perform(builder)
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(jsonPath("$.errorMsg").value("Invalid input provided."))
					.andExpect(jsonPath("$.errorCode").value(CommonConstants.ERROR_400));
		
	}	
	
	@Test
	public void testRetrieveWipService_InvalidBusFunc() throws Exception{
		
		ObjectMapper mapper = new ObjectMapper();
		WIPServiceInput wipServiceInput = new WIPServiceInput();
		
		String[] busFuncIds = {"XXXX","ADST"};
		wipServiceInput.setPolicyNum("2011000002");
		wipServiceInput.setBusFunctionId(busFuncIds);
		
		String jsonInput = mapper.writeValueAsString(wipServiceInput);
		
		MockHttpServletRequestBuilder builder =  MockMvcRequestBuilders
													.post("/MFIntegrationService/retrieveWIPDetails")
													.contentType(MediaType.APPLICATION_JSON)
													.content(jsonInput);
		
		mockMvc.perform(builder)
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(jsonPath("$.errorCode").value("+4339"));
		
	}
	
	@Test
	public void testRetrieveWipServiceDAO_Exception() throws Exception{
		WIPServiceOutput wipServiceOutput = integrationServiceDao.isWIPExists(null);	
		assertEquals("500", wipServiceOutput.getErrorCode());
	}
	
	@Test
	public void testRecordImpl_Clone() throws Exception {
		RecordImpl recordImpl =  new RecordImpl(CommonConstants.LSOA40, CommonConstants.LSOA40_DESC);	
		RecordImpl recordImpl2;
		recordImpl2 = (RecordImpl) recordImpl.clone();
		assertEquals(recordImpl.getRecordName(), recordImpl2.getRecordName());
		assertEquals(recordImpl.getRecordShortDescription(), recordImpl2.getRecordShortDescription());
	}

}
