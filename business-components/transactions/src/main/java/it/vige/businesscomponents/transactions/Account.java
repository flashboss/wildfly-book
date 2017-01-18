package it.vige.businesscomponents.transactions;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Account {

	@Id
	private int number;

	private double credit;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public double getCredit() {
		return credit;
	}

	public void setCredit(double credit) {
		this.credit = credit;
	}

	public void add(double credit) {
		this.credit += credit;
	}

	public void less(double credit) {
		this.credit -= credit;
	}
}
