package svc.data.citations.datasources.tyler.models;

import svc.models.VIOLATION_STATUS;

public enum TylerViolationStatus {

	WARRANT_ISSUED("WARRANT ISSUED", VIOLATION_STATUS.FTA_WARRANT_ISSUED);

	private final String tylerViolationStatusString;
	private final VIOLATION_STATUS violationStatus;

	private TylerViolationStatus(String tylerViolationStatusString, VIOLATION_STATUS violationStatus) {
		this.tylerViolationStatusString = tylerViolationStatusString;
		this.violationStatus = violationStatus;
	}

	public String getTylerStatusString() {
		return this.tylerViolationStatusString;
	}

	public VIOLATION_STATUS getViolationStatus() {
		return this.violationStatus;
	}

	public static TylerViolationStatus fromTylerViolationStatusString(String tylerViolationStatusString) {
		if (tylerViolationStatusString == null) {
			return null;
		}

		for (TylerViolationStatus tylerStatus : TylerViolationStatus.values()) {
			if (tylerStatus.getTylerStatusString().equals(tylerViolationStatusString)) {
				return tylerStatus;
			}
		}
		return null;
	}
}
