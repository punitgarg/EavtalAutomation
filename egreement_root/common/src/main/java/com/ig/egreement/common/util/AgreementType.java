package com.ig.egreement.common.util;

public enum AgreementType {

	FREE_TEXT_AGREEMENT("freetextagreement"), DYNAMIC_PDF_AGREEMENT(
			"freetextagreement"), MERGED_AGREEMENT("freetextagreement");

	private String agreementType;

	private AgreementType(String agreeType) {
		agreementType = agreeType;
	}

	public String getAgreeType() {
		return agreementType;
	}

}
