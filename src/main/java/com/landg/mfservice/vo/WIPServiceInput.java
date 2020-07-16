package com.landg.mfservice.vo;

public class WIPServiceInput {
	
	private String policyNum;
	
	private String[] busFunctionId;

	/**
	 * @return the policyNum
	 */
	public String getPolicyNum() {
		return policyNum;
	}

	/**
	 * @param policyNum the policyNum to set
	 */
	public void setPolicyNum(String policyNum) {
		this.policyNum = policyNum;
	}

	/**
	 * @return the busFunctionId
	 */
	public String[] getBusFunctionId() {
		return busFunctionId;
	}

	/**
	 * @param busFunctionId the busFunctionId to set
	 */
	public void setBusFunctionId(String[] busFunctionId) {
		this.busFunctionId = busFunctionId;
	}
	
}