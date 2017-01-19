package it.vige.businesscomponents.transactions;

import javax.persistence.EntityManager;

public abstract class Bank {

	public void move(int accountToTake, int accountToPut, double amount) throws Exception {
		Account from = getEntityManager().find(Account.class, accountToTake);
		Account to = getEntityManager().find(Account.class, accountToPut);
		from.less(amount);
		to.add(amount);
		getEntityManager().merge(from);
		getEntityManager().merge(to);
	}

	protected abstract EntityManager getEntityManager();

}
