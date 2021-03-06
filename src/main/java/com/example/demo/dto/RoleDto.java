package com.example.demo.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RoleDto {
	private Integer id;
	
	//@NotNull(message="name cannot be null")
	@Size(min=2, max=50)
	private String name;
//	
//	@JsonIgnore
//	private List<UserDto> userDto;
//	
	public RoleDto() {
		super();
	}
	
	public RoleDto(@Size(min = 2, max = 50) String name) {
		super();
		this.name = name;
	}
	
	public RoleDto(Integer id, @NotNull(message = "name cannot be null") @Size(min = 2, max = 50) String name) {
		super();
		this.id = id;
		this.name = name;
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
	
//	@JsonIgnore
//	public List<UserDto> getUserDto() {
//		return userDto;
//	}
//
//	public void setUserDto(List<UserDto> userDto) {
//		this.userDto = userDto;
//	}

	@Override
	public String toString() {
		return "RoleDto [id=" + id + ", name=" + name + "]";
	}
}
