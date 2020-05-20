package com.b2w.desafio;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
public class DesafioApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private PlanetaRepository planetaRepository;

	@BeforeEach
	public void deleteAllBeforeTests() throws Exception {
		planetaRepository.deleteAll();
	}

	@Test
	public void shouldReturnRepositoryIndex() throws Exception {

		mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk()).andExpect(
				jsonPath("$._links.planeta").exists());
	}
	
	@Test
	public void shouldCreateEntity() throws Exception {

		mockMvc.perform(post("/planeta")
		.content(
				"{\"name\": \"PitzzaPlanet\", \"climate\":\"desconhecido\", \"terrain\":\"desconhecido\"}"))
		.andExpect(
			status().isCreated())
		.andExpect(
			header()
		.string("Location", containsString("planeta/")));
	}

	@Test
	public void shouldRetrieveEntity() throws Exception {

		MvcResult mvcResult = mockMvc.perform(post("/planeta")
	    .content(
	    	"{\"name\": \"PitzzaPlanet\", \"climate\":\"desconhecido\", \"terrain\":\"desconhecido\"}"))
		.andExpect(
			status().isCreated())
		.andReturn();

		String location = mvcResult.getResponse().getHeader("Location");
		
		mockMvc.perform(get(location))
		.andExpect(status().isOk())
		.andExpect(
			jsonPath("$.name").value("PitzzaPlanet"))
		.andExpect(
			jsonPath("$.climate").value("desconhecido")
		);
	}

	@Test
	public void shouldQueryEntity() throws Exception {

		mockMvc.perform(post("/planeta")
		.content(
			"{\"name\": \"PitzzaPlanet\", \"climate\":\"desconhecido\", \"terrain\":\"desconhecido\"}"))
		.andExpect(
			status().isCreated()
		);

		mockMvc.perform(get("/planeta/search/findByName?name={name}", "PitzzaPlanet"))
		.andExpect(
			status().isOk())
		.andExpect(
			jsonPath("$._embedded.planeta[0].name")
			.value("PitzzaPlanet")
		);
	}

	@Test
	public void shouldUpdateEntity() throws Exception {

		MvcResult mvcResult = mockMvc.perform(post("/planeta")
		.content(
			"{\"name\": \"PitzzaPlanet\", \"climate\":\"desconhecido\", \"terrain\":\"desconhecido\"}"))
		.andExpect(
			status().isCreated())
		.andReturn();

		String location = mvcResult.getResponse().getHeader("Location");

		mockMvc.perform(put(location)
		.content(
			"{\"name\": \"PitzzaPlanetEvolution\", \"climate\":\"desconhecido\", \"terrain\":\"desconhecido\"}"))
		.andExpect(
			status().isNoContent()
		);

		mockMvc.perform(get(location))
		.andExpect(status().isOk())
		.andExpect(
		    jsonPath("$.name").value("PitzzaPlanetEvolution"))
		.andExpect(
		    jsonPath("$.climate").value("desconhecido")
		);
	}

	@Test
	public void shouldPartiallyUpdateEntity() throws Exception {

		MvcResult mvcResult = mockMvc.perform(post("/planeta")
		.content(
			"{\"name\": \"PitzzaPlanet\", \"climate\":\"desconhecido\", \"terrain\":\"desconhecido\"}"))
		.andExpect(
			status().isCreated())
		.andReturn();

		String location = mvcResult.getResponse().getHeader("Location");

		mockMvc.perform(
			patch(location).content("{\"name\": \"PitzzaPlanet\"}"))
		.andExpect(
			status().isNoContent()
		);

		mockMvc.perform(get(location))
		.andExpect(status().isOk())
		.andExpect(
			jsonPath("$.name").value("PitzzaPlanet"))
		.andExpect(
			jsonPath("$.climate").value("desconhecido")
		);
	}

	@Test
	public void shouldDeleteEntity() throws Exception {

		MvcResult mvcResult = mockMvc.perform(post("/planeta").content(
				"{\"name\": \"PitzzaPlanet\", \"climate\":\"desconhecido\", \"terrain\":\"desconhecido\"}")).andExpect(
						status().isCreated()).andReturn();

		String location = mvcResult.getResponse().getHeader("Location");
		mockMvc.perform(delete(location)).andExpect(status().isNoContent());

		mockMvc.perform(get(location)).andExpect(status().isNotFound());
	}
}
