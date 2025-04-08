package com.workintech.fswebs17d1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workintech.fswebs17d1.controller.AnimalController;
import com.workintech.fswebs17d1.entity.Animal;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ResultAnalyzer.class)
class FswebS17d1ApplicationTests {

	@Autowired
	private Environment env;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@DisplayName("application.properties dosyası düzgün mü?")
	void serverPortIsSetTo8585() {
		String serverPort = env.getProperty("server.port");
		assertNotNull(serverPort);
		assertThat(serverPort).isEqualTo("8585");

		String courseName = env.getProperty("course.name");
		assertNotNull(courseName);

		String projectDeveloperFullname = env.getProperty("project.developer.fullname");
		assertNotNull(projectDeveloperFullname);
	}

	@Test
	@Order(1)
	public void shouldAddNewAnimal() throws Exception {
		Animal newAnimal = new Animal();
		newAnimal.setId(1);
		newAnimal.setName("maymun");

		mockMvc.perform(post("/workintech/animal")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(newAnimal)))
				.andExpect(status().isCreated())  // 201 Created bekliyoruz
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("maymun"));
	}

	@Test
	@Order(2)
	public void shouldReturnAllAnimals() throws Exception {
		mockMvc.perform(get("/workintech/animal"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray());
	}

	@Test
	@Order(3)
	void shouldReturnAnimalById() throws Exception {
		mockMvc.perform(get("/workintech/animal/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("maymun"));
	}

	@Test
	@Order(4)
	public void shouldUpdateAnimal() throws Exception {
		Animal updatedAnimal = new Animal();
		updatedAnimal.setId(1);
		updatedAnimal.setName("aslan");

		mockMvc.perform(put("/workintech/animal/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(updatedAnimal)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("aslan"));
	}

	@Test
	@Order(5)
	public void shouldDeleteAnimal() throws Exception {
		mockMvc.perform(delete("/workintech/animal/1"))
				.andExpect(status().isNoContent()); // 204 No Content bekliyoruz
	}

	@Test
	void testAnimalCreation() {
		Animal animal = new Animal();
		animal.setId(1);
		animal.setName("Lion");

		assertEquals(1, animal.getId());
		assertEquals("Lion", animal.getName());
	}
}
