package it.vige.businesscomponents.injection;

import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.List;
import java.util.concurrent.Callable;

import javax.ejb.EJBAccessException;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.vige.businesscomponents.injection.security.EmployeeBean;
import it.vige.businesscomponents.injection.security.ManagerBean;
import it.vige.businesscomponents.injection.security.Movie;
import it.vige.businesscomponents.injection.security.Movies;

@RunWith(Arquillian.class)
public class SecurityTestCase {

	@Inject
	private Movies movies;

	@Inject
	private ManagerBean manager;

	@Inject
	private EmployeeBean employee;

	@Deployment
	public static JavaArchive createWebDeployment() {
		final JavaArchive jar = create(JavaArchive.class, "security-test.jar");
		jar.addPackage(Movie.class.getPackage());
		jar.addAsManifestResource(new FileAsset(new File("src/main/resources/META-INF/beans-empty.xml")), "beans.xml");
		return jar;
	}

	@Test
	public void testAsManager() throws Exception {
		manager.call(new Callable<Object>() {
			public Object call() throws Exception {

				Movie movie1 = new Movie("Quentin Tarantino", "Reservoir Dogs", 1992);
				Movie movie2 = new Movie("Joel Coen", "Fargo", 1996);
				Movie movie3 = new Movie("Joel Coen", "The Big Lebowski", 1998);
				movies.addMovie(movie1);
				movies.addMovie(movie2);
				movies.addMovie(movie3);

				List<Movie> list = movies.getMovies();
				assertEquals("List.size()", 3, list.size());

				movies.deleteMovie(movie1);
				movies.deleteMovie(movie2);
				movies.deleteMovie(movie3);

				assertEquals("Movies.getMovies()", 0, movies.getMovies().size());
				return null;
			}
		});
	}

	@Test
	public void testAsEmployee() throws Exception {
		employee.call(new Callable<Object>() {
			public Object call() throws Exception {
				Movie movie1 = new Movie("Quentin Tarantino", "Reservoir Dogs", 1992);
				Movie movie2 = new Movie("Joel Coen", "Fargo", 1996);
				Movie movie3 = new Movie("Joel Coen", "The Big Lebowski", 1998);
				movies.addMovie(movie1);
				movies.addMovie(movie2);
				movies.addMovie(movie3);

				List<Movie> list = movies.getMovies();
				assertEquals("List.size()", 3, list.size());

				try {
					movies.deleteMovie(movie1);
					fail("Employees should not be allowed to delete");
				} catch (EJBAccessException e) {
					// Good, Employees cannot delete things
				}

				// The list should still be three movies long
				assertEquals("Movies.getMovies()", 3, movies.getMovies().size());
				return null;
			}
		});
	}

	@Test
	public void testUnauthenticated() throws Exception {
		try {
			movies.addMovie(new Movie("Quentin Tarantino", "Reservoir Dogs", 1992));
			fail("Unauthenticated users should not be able to add movies");
		} catch (EJBAccessException e) {
			// Good, guests cannot add things
		}

		try {
			movies.deleteMovie(null);
			fail("Unauthenticated users should not be allowed to delete");
		} catch (EJBAccessException e) {
			// Good, Unauthenticated users cannot delete things
		}

		try {
			// Read access should be allowed

			movies.getMovies();
		} catch (EJBAccessException e) {
			fail("Read access should be allowed");
		}
	}
}
