package com.auth.springjwt.authjwtapi.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.auth.springjwt.authjwtapi.model.Client;

@Repository
public interface ClientRepo  extends JpaRepository<Client, Long>{
	Optional<Client> findByUsername(String username);
		Boolean existsByUsername(String username);
		Boolean existsByEmail(String email);

}
