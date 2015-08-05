package com.ig.egreement.common.util;

public class EnterpriseContractor extends Contractor {

	private String orgNumber;
	private String referencePerson;
	

	private static EnterpriseContractor myContractor;

	private EnterpriseContractor() {
	}

	public static EnterpriseContractor getContractor() {
		if (myContractor == null) {
			myContractor = new EnterpriseContractor();
			myContractor
					.setOrgNumber(Configuration.ENTERPRISE_CONTRACTOR_ORG_NUMBER);
			myContractor
					.setReferencePerson(Configuration.ENTERPRISE_CONTRACTOR_REFERENCE_PERSON);
			myContractor.setEmailId(Configuration.ENTERPRISE_CONTRACTOR_EMAIL);
		}
		return myContractor;
	}

	public String getOrgNumber() {
		return orgNumber;
	}

	public void setOrgNumber(String orgNumber) {
		this.orgNumber = orgNumber;
	}

	public String getReferencePerson() {
		return referencePerson;
	}

	public void setReferencePerson(String referencePerson) {
		this.referencePerson = referencePerson;
	}

	@Override
	public String toString() {
		return "Contractor [orgNumber=" + orgNumber + ", reference Person="
				+ referencePerson + ", emailId=" + emailId + "]";
	}

}
