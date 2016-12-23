package it.vige.businesscomponents.injection.security;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

public class Movies {

	private List<Movie> movies = new ArrayList<Movie>();

	@RolesAllowed({ "Employee", "Manager" })
	public void addMovie(Movie movie) throws Exception {
		movies.add(movie);
	}

	@RolesAllowed({ "Manager" })
	public void deleteMovie(Movie movie) throws Exception {
		movies.remove(movie);
	}

	@PermitAll
	public List<Movie> getMovies() throws Exception {
		return movies;
	}
}
