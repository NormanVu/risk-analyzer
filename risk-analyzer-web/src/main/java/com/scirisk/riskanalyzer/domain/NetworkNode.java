package com.scirisk.riskanalyzer.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity()
@SuppressWarnings("serial")
public class NetworkNode implements Serializable {

	private Long id;
	private Kind kind;
	private Type type;
	private String name;
	private String desc;
	private String address;
	private Double latitude;
	private Double longitude;
	private Double riskCategory1;
	private Double riskCategory2;
	private Double riskCategory3;
	private Double recoveryTime1;
	private Double recoveryTime2;
	private Double recoveryTime3;

	@Id()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Enumerated(EnumType.STRING)
	public Kind getKind() {
		return kind;
	}

	public void setKind(Kind kind) {
		this.kind = kind;
	}

	@Enumerated(EnumType.STRING)
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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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
