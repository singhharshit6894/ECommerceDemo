package com.example.demo.controller;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserDto;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ExceptionResponse;
import com.example.demo.exception.InvalidInputException;
import com.example.demo.login.Login;
import com.example.demo.service.EmailService;
import com.example.demo.service.IUserService;

import redis.clients.jedis.Jedis;

@RestController
@RequestMapping("/api")
public class UserController {
	@Autowired
	private IUserService userService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private Login loginClass;
	
	//private ExceptionResponse error = new ExceptionResponse();
	
	private Jedis jedis = new Jedis("127.0.0.1", 6379);
	
	@GetMapping("/userActive/{id}")
	public String activeUser(@PathVariable(value = "id") Integer userId)
	{
		emailService.activation(userId);
		return "Your Account is Activated !!!";
	}
	
	@PostMapping("/user")
	public UserDto insertUser(@RequestBody @Valid UserDto userDto) throws InvalidInputException, CustomException, UnsupportedEncodingException {
		return userService.insertUser(userDto);
	}
	
	@GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAll(HttpServletRequest httpServletRequest, CustomException exception) {
		String token= httpServletRequest.getHeader("Authorization");
		
		System.out.println("token " + token);
		
		String username = jedis.get(token);
		
		System.out.println("username " + username);
		if(username!=null) {
			HttpHeaders responseHeader = new HttpHeaders();
			return new ResponseEntity<>(userService.displayAllUsers(), responseHeader, HttpStatus.OK);
			}
		else{
			ExceptionResponse error = new ExceptionResponse( LocalDateTime.now(), HttpStatus.valueOf(exception.getCode()),
					exception.getCode(),"Validation Failed", "Unique Value Exception", exception.getMessage());
			return new ResponseEntity<>(error,HttpStatus.UNAUTHORIZED);
			}
		}
	
	@GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public UserDto getStudentById(@PathVariable(value = "id") @Valid Integer userId, 
			@RequestHeader(value = "userName") String userName,
			@RequestHeader(value = "password") String password) throws CustomException {
		UserDto userDto = new UserDto();
		Boolean loginSuccess = loginClass.login(userName, password,userId);
		
		if (loginSuccess == true) {
			userDto=userService.displayById(userId);
		}
		else{
			throw new CustomException("Login Error");
		}
		return userDto;
		//return userService.displayById(userId);
	}
	
	@PutMapping("/user")
	public UserDto updateUser(@RequestBody @Valid UserDto userDto,
			@RequestHeader(value = "userName") String userName,
			@RequestHeader(value = "password") String password) throws InvalidInputException, CustomException {
		
		Integer id = userDto.getId();
		Boolean loginSuccess = loginClass.login(userName, password,id);
		if (loginSuccess == true) {
			userDto = userService.updateUser(userDto);
			}
		else {
			throw new CustomException("Login Error");
			}
		return userDto;
		//return userService.updateUser(userDto);
	}
	
	@DeleteMapping("/user/{id}")
	public String delete(@PathVariable(value = "id") Integer userId) {
		userService.delete(userId);
		return "deleted";
	}
	
	/*@PostMapping("/user")
	public User createStudent(@RequestBody UserDto user) {
	    return userService.insertStudent(user);
	}
	*/
	/*@Autowired
	IStudentRepo studentrepository;
	
	@GetMapping("/students")
	public List<Student> getAllStudents() {
		return studentrepository.findAll();
	}
	
	@GetMapping("/students/{id}")
	public Optional<Student> getStudentById(@PathVariable(value = "id") Integer studentId) {
		return studentrepository.findById(studentId);
	}
	
	@PostMapping("/students")
	public ResponseEntity<Object> createStudent(@RequestBody Student student) {
		Student savedStudent = studentrepository.save(student);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedStudent.getStudentId()).toUri();

		return ResponseEntity.created(location).build();
	}
	@PostMapping("/students")
	public Student createStudent(@RequestBody Student student) {
	    return studentrepository.save(student);
	}
	
	@DeleteMapping("/students/{id}")
	public void deleteStudent(@PathVariable(value = "id") Integer studentId) {
		studentrepository.deleteById(studentId);
	}
	
	@PutMapping("/students/{id}")
	public Student updateStudent(@PathVariable(value = "id") Integer studentId,@RequestBody Student studentDetails) {
		Optional<Student> student = studentrepository.findById(studentId);
		
		studentDetails.setStudentName(studentDetails.getStudentName());
		studentDetails.setStudentEmail(studentDetails.getStudentEmail());
		studentDetails.setStudentPasscode(studentDetails.getStudentPasscode());
		studentDetails.setStudentId(studentId);
		
		Student updateStudent = studentrepository.save(studentDetails);
		
		return updateStudent;
	}*/
}
