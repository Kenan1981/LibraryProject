package com.tpe;

import com.tpe.entity.concretes.business.Role;
import com.tpe.entity.enums.RoleType;
import com.tpe.payload.request.UserRequest;
import com.tpe.repository.RoleRepository;
import com.tpe.service.RoleService;
import com.tpe.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.Date;

@SpringBootApplication
public class LibrayProjectApplication  {

	private final RoleService roleService;
	private final RoleRepository roleRepository;
	private final UserService userService;

	public LibrayProjectApplication(RoleService roleService, RoleRepository roleRepository, UserService userService) {
		this.roleService = roleService;
		this.roleRepository = roleRepository;
		this.userService = userService;
	}

	public static void main(String[] args) {
		SpringApplication.run(LibrayProjectApplication.class, args);
	}


//	@Override
//	public void run(String... args) throws Exception {
//
//		// Role tablomu dolduracagim ama once bos mu diye kontrol edecegim
//		if(roleService.getAllUserRole().isEmpty()){
//
//			Role admin = new Role();
//			admin.setRoleType(RoleType.ADMIN);
//			admin.setName("Admin");
//			roleRepository.save(admin);
//
//			Role employee = new Role();
//			employee.setRoleType(RoleType.EMPLOYEE);
//			employee.setName("Employee");
//			roleRepository.save(employee);
//
//			Role member = new Role();
//			member.setRoleType(RoleType.MEMBER);
//			member.setName("Member");
//			roleRepository.save(member);
//
//		}
//
//		// Built_in Admin olusturuluyor eger sistemde Admin yoksa
//		if(userService.countAllAdmins()==0){
//
//			UserRequest adminRequest = new UserRequest();
//			adminRequest.setEmail("admin@admin.com");
//			adminRequest.setPassword("123456");
//			adminRequest.setFirstName("Admin");
//			adminRequest.setLastName("Admin");
//			adminRequest.setAddress("Bektas, Istanbul");
//			adminRequest.setPhone("111-111-1111");
//			adminRequest.setBirthDate(LocalDate.of(1990,01,01));
//			userService.saveUser(adminRequest,"Admin");
//		}
//
//	}
}
