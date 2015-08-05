package com.ig.egreement.common.util;

public class PrivateContractor extends Contractor {

	private String firstName;
	private String LastName;
	private String personNumber;

	private static PrivateContractor myContractor;

	private PrivateContractor() {
	}

	public static PrivateContractor getContractor() {
		if (myContractor == null) {
			myContractor = new PrivateContractor();
			myContractor.setFirstName(Configuration.CONTRACTOR_FIRST_NAME);
			myContractor.setLastName(Configuration.CONTRACTOR_LAST_NAME);
			myContractor
					.setPersonNumber(Configuration.CONTRACTOR_PERSON_NUMBER);
			myContractor.setEmailId(Configuration.CONTRACTOR_EMAIL_ID);
		}
		return myContractor;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getPersonNumber() {
		return personNumber;
	}

	public void setPersonNumber(String personNumber) {
		this.personNumber = personNumber;
	}

	@Override
	public String toString() {
		return "Contractor [firstName=" + firstName + ", LastName=" + LastName
				+ ", personNumber=" + personNumber + ", emailId=" + emailId
				+ "]";
	}

}
