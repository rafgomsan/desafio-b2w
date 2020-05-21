package com.b2w.desafio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "planeta", path = "planeta")
public interface PlanetaRepository extends MongoRepository<Planeta, String> {
	public List<Planeta> findByName(@Param("name") String name);
	@Query("{ 'id' : ?0 }")
	public Optional<Planeta> findById(@Param("id") String id);
}
