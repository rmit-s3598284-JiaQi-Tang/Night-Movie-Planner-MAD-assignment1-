package com.example.movienightplanner.models;

import java.util.List;

public class MovieImpl extends AbstructMovie {

	private String id;
	private String tittle;
	private String year;
	private String posterImageName;

	public MovieImpl(String id, String tittle, String year, String posterImageName) {
		this.id = id;
		this.tittle = tittle;
		this.year = year;
		this.posterImageName = posterImageName;
	}

	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTittle() {
		return tittle;
	}

	public void setTittle(String tittle) {
		this.tittle = tittle;
	}

	@Override
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Override
	public String getPosterImageName() {
		return posterImageName;
	}

	public void setPosterImageName(String posterImageName) {
		this.posterImageName = posterImageName;
	}
}
