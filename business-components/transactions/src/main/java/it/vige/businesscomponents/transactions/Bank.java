package it.vige.businesscomponents.transactions;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class Bank {

	@PersistenceContext
	private EntityManager entityManager;

	public void move(int accountToTake, int accountToPut, double amount) {
		Account from = entityManager.find(Account.class, accountToTake);
		Account to = entityManager.find(Account.class, accountToPut);
		from.less(amount);
		to.add(amount);
		entityManager.merge(from);
		entityManager.merge(to);
	}
}
