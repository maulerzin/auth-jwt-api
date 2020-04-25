package com.auth.springjwt.authjwtapi.safety.resources;
import java.util.Collection;
import java.util.Objects;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.auth.springjwt.authjwtapi.model.Client;
import com.fasterxml.jackson.annotation.JsonIgnore;


public class ClientDetail implements UserDetails {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String username;
	private String email;
	
	@JsonIgnore
	private String password;
	
	
	public ClientDetail(Long id, String username, String email, String password) {
		this.id = id;
		this.email = email;
		this.username = username;
		this.password = password;
	}
	
	public static ClientDetail build(Client client) {
		return new ClientDetail(
				client.getId(),
				client.getUsername(),
				client.getEmail(),
				client.getPassword()
				);
	}
	
	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}


	public String getUsername() {
		return username;
	}


	public boolean isAccountNonExpired() {
		return true;
	}


	public boolean isAccountNonLocked() {
		return true;
	}

	
	public boolean isCredentialsNonExpired() {
		return true;
	}

	
	public boolean isEnabled() {
		return true;
	}


	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ClientDetail client = (ClientDetail) o;
		return Objects.equals(id, client.id);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	}

	



