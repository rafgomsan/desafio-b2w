package com.b2w.desafio;

import java.util.Arrays;

import org.springframework.data.annotation.Id;

public class Planeta {

	@Id private String id;
	
	private String name;
	private String climate;
	private String terrain;
	private String[] films;
	private Integer aparicoes;
	
	public Integer getAparicoes() {
		return aparicoes;
	}

	public void setAparicoes(Integer aparicoes) {
		this.aparicoes = aparicoes;
	}

	public Planeta() {
		super();
	}

	public Planeta(String name, String climate, String terrain, String[] films) {
		super();
		this.name = name;
		this.climate = climate;
		this.terrain = terrain;
		this.films = films;
	}
	public Planeta(String name, String climate, String terrain, String[] films, Integer aparicoes) {
		super();
		this.name = name;
		this.climate = climate;
		this.terrain = terrain;
		this.films = films;
		this.aparicoes = aparicoes;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClimate() {
		return climate;
	}
	public void setClimate(String climate) {
		this.climate = climate;
	}
	public String getTerrain() {
		return terrain;
	}
	public void setTerrain(String terrain) {
		this.terrain = terrain;
	}
	public String[] getFilms() {
		return films;
	}
	public void setFilms(String[] films) {
		this.films = films;
	}

	@Override
	public String toString() {
		return "Planeta [name=" + name + ", climate=" + climate + ", terrain=" + terrain + ", films="
				+ Arrays.toString(films) + "]";
	}

}

