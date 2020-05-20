package com.b2w.desafio;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SwApiService {
	
	@Autowired
	private PlanetaRepository repository;
	
	private Planeta[] planetas = null;

	public SwApiService() {
		super();
	}

	@Autowired
	RestTemplate restTemplate;
	
	public String getPlanetList() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity <String> entity = new HttpEntity<String>(headers);
		return restTemplate.exchange("https://swapi.dev/api/planets/", HttpMethod.GET, entity, String.class).getBody();
	}
	
	public Planeta[] loadData() throws IOException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);;
		Planeta[] planetas = null;
	    try {
	    	repository.deleteAll();
			JsonNode node = mapper.readTree(getPlanetList());
			planetas = mapper.readValue(node.get("results").toString(), Planeta[].class);
			for(Planeta p : planetas) {
	    		repository.save(new Planeta(p.getName(), 
	    				                    p.getClimate(), 
	    				                    p.getTerrain(), 
	    				                    p.getFilms(),
	    				                    p.getFilms().length));
	    	}
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return planetas;
	}
	
	public Integer getAparicoesFilmesCount(String planeta) {
		Integer aparicoes = 0;
    	for(Planeta p : planetas) {
    		if( planeta.contentEquals(p.getName())){
    			aparicoes += p.getFilms().length;
    		}
    	}
	    return aparicoes;
	}
}

