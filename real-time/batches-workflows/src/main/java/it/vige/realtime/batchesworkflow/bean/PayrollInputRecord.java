package it.vige.realtime.batchesworkflow.bean;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PayrollInputRecord {

	@Id
	private int id;

	private int baseSalary;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(int baseSalary) {
		this.baseSalary = baseSalary;
	}
}
