package com.scirisk.riskanalyzer.domain;

public class Facility {

	private String id;
	private Kind kind;
	private Type type;
	private String name;
	private String description;
	private String address;
	private Double latitude;
	private Double longitude;
	private Double riskCategory1;
	private Double riskCategory2;
	private Double riskCategory3;
	private Double recoveryTime1;
	private Double recoveryTime2;
	private Double recoveryTime3;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Kind getKind() {
		return kind;
	}

	public void setKind(Kind kind) {
		this.kind = kind;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getRiskCategory1() {
		return riskCategory1;
	}

	public void setRiskCategory1(Double riskCategory1) {
		this.riskCategory1 = riskCategory1;
	}

	public Double getRiskCategory2() {
		return riskCategory2;
	}

	public void setRiskCategory2(Double riskCategory2) {
		this.riskCategory2 = riskCategory2;
	}

	public Double getRiskCategory3() {
		return riskCategory3;
	}

	public void setRiskCategory3(Double riskCategory3) {
		this.riskCategory3 = riskCategory3;
	}

	public Double getRecoveryTime1() {
		return recoveryTime1;
	}

	public void setRecoveryTime1(Double recoveryTime1) {
		this.recoveryTime1 = recoveryTime1;
	}

	public Double getRecoveryTime2() {
		return recoveryTime2;
	}

	public void setRecoveryTime2(Double recoveryTime2) {
		this.recoveryTime2 = recoveryTime2;
	}

	public Double getRecoveryTime3() {
		return recoveryTime3;
	}

	public void setRecoveryTime3(Double recoveryTime3) {
		this.recoveryTime3 = recoveryTime3;
	}

	public static enum Kind {
		company, supplier
	}

	public static enum Type {
		independent, correlated
	}

}
