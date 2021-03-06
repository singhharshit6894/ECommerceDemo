package com.example.demo.dto;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserDto {
	private Integer id;
	
	@NotNull(message="name cannot be null")
	@Size(min=2, max=50)
	private String name;
	
	@NotNull(message="email cannot be null")
	@Email//(message = "email should be in correct format")
	@Size(min=2, max=50)
	@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "email should be in correct format")
	private String email;
	
	@NotNull(message="contact number cannot be null")
	@Pattern(regexp="(^[1-9]{1}[0-9]{9}$)|(^$)",message="Invalid Phone Number format")
    private String phoneNumber;
	
	//@NotNull(message="password cannot be null")
	@Size(min=2, max=50)
	private String password;
	
	//@NotNull(message="Name cannot be null")
	private List<RoleDto> roleDto;
	
	public UserDto() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<RoleDto> getRoleDto() {
		return roleDto;
	}

	public void setRoleDto(List<RoleDto> roleDto) {
		this.roleDto = roleDto;
	}

	@Override
	public String toString() {
		return "UserDto [id=" + id + ", name=" + name + ", email=" + email + ", phoneNumber=" + phoneNumber
				+ ", password=" + password + ", roleDto=" + roleDto + "]";
	}
}
