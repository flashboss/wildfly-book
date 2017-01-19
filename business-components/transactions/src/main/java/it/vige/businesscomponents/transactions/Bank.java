package it.vige.businesscomponents.transactions;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class Bank {

	@PersistenceContext
	private EntityManager entityManager;

	public void move(int accountToTake, int accountToPut, double amount) throws Exception {
		Account from = entityManager.find(Account.class, accountToTake);
		Account to = entityManager.find(Account.class, accountToPut);
		from.less(amount);
		to.add(amount);
		entityManager.merge(from);
		entityManager.merge(to);
	}

}
