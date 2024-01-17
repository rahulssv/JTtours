package com.jt.model.auth;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User{
	
	private String password ;
	@Id
	private String userName ;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
}
