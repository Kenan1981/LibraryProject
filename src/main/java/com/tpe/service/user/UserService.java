package com.tpe.service.user; // checked


import com.tpe.entity.concretes.user.User;
import com.tpe.entity.enums.RoleType;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.exception.UnauthorizedException;
import com.tpe.payload.mappers.UserMapper;
import com.tpe.payload.messages.ErrorMessages;
import com.tpe.payload.messages.SuccessMessages;
import com.tpe.payload.request.UserRequest;
import com.tpe.payload.response.ResponseMessage;
import com.tpe.payload.response.UserResponse;
import com.tpe.repository.LoanRepository;
import com.tpe.repository.UserRepository;
import com.tpe.payload.helper.MethodHelper;
import com.tpe.payload.helper.PageableHelper;
import com.tpe.payload.helper.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UniquePropertyValidator uniquePropertyValidator;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final PageableHelper pageableHelper;
    private final MethodHelper methodHelper;
    private final RoleService roleService ;
    private final LoanRepository loanRepository;

    public User registerUser(UserRequest userRequest) {
        // Check for duplicate email and phone
        uniquePropertyValidator.checkDuplicate(userRequest.getEmail(), userRequest.getPhone());

        // Create a new User instance
        UserRequest newUser = new UserRequest();

        // Map userRequest to newUser
        // User user = userMapper.mapUserRequestToUser(newUser);
        User user=userMapper.mapUserRequestToUser(newUser);


        // Set the default role to MEMBER
        user.setRole(roleService.getRole(RoleType.MEMBER));

        // Encode and set the password
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        // Save the new user to the repository
        userRepository.save(user);

        return user;
    }


    public User getAuthenticatedUser(String email) {

        if (!userRepository.existsByEmail(email)){
            throw new UsernameNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE));
        }

        return userRepository.findByEmail(email);

    }

//    public List<Loan> findUserLoans(String username, Pageable pageable) {
//        return loanRepository.findByUserByLoans(username, pageable);
//    }


    public Page<UserResponse> getUsersByPage(int page, int size, String sort, String type, String userRole) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);
        return userRepository.findByUserByRole(userRole, pageable)
                .map(userMapper::mapUserToUserResponse);
    }

    public ResponseMessage<UserResponse> getUserById(Long userId) {
        // Kullanıcıyı veritabanından bul veya bulunamazsa hata fırlat
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE, userId))
        );

        // Kullanıcıyı UserResponse nesnesine dönüştür
        UserResponse userResponse = userMapper.mapUserToUserResponse(user);

        // Başarılı cevap mesajı oluştur ve geri dön
        return ResponseMessage.<UserResponse>builder()
                .message(SuccessMessages.USER_FOUND)
                .httpStatus(HttpStatus.OK)
                .object(userResponse)
                .build();
    }



    public User createUser(UserRequest userRequest, RoleType creatorRole) {
        // Benzersizlik kontrolü (email ve telefon)
        uniquePropertyValidator.checkDuplicate(userRequest.getEmail(), userRequest.getPhone());

        // Yetki kontrolü
        if (creatorRole == RoleType.ADMIN ||
                (creatorRole == RoleType.EMPLOYEE && userRequest.getRoleType() == RoleType.MEMBER)) {

            // Yeni kullanıcı nesnesi oluştur
            UserRequest newUser = new UserRequest();

            // userRequest'teki verileri newUser nesnesine map et
            User user = userMapper.mapUserRequestToUser(newUser);

            // Doğum tarihi opsiyonel alan
            if (userRequest.getBirthDate() != null) {
                user.setBirthDate(userRequest.getBirthDate());
            }

            // Rolü ayarla
            user.setRole(userRequest.getRole());

            // Şifreyi şifrele ve ayarla
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

            // Yeni kullanıcıyı veritabanına kaydet ve geri dön
            return userRepository.save(user);
        } else {
            // Yetkisiz işlem
            throw new UnauthorizedException("You are not authorized to create this type of user.");
        }
    }


    public ResponseMessage<UserResponse> updateUser(UserRequest userRequest, Long userId) {
        User user = methodHelper.isUserExist(userId);
        // !!! bulit_in kontrolu
        methodHelper.checkBuiltIn(user);
        //!!! update isleminde gelen request de unique olmasi gereken eski datalar hic degismedi ise
        // dublicate kontrolu yapmaya gerek yok :
        uniquePropertyValidator.checkUniqueProperties(user, userRequest);
        //!!! DTO --> POJO
        User updatedUser = userMapper.mapUserRequestToUpdatedUser(userRequest, userId);
        // !!! Password Encode
        updatedUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        updatedUser.setRole(user.getRole());
        User savedUser = userRepository.save(updatedUser);

        return ResponseMessage.<UserResponse>builder()
                .message(SuccessMessages.USER_UPDATE_MESSAGE)
                .httpStatus(HttpStatus.OK)
                .object(userMapper.mapUserToUserResponse(savedUser))
                .build();
    }

    public String deleteUserById(Long id) {

        User user = methodHelper.isUserExist(id);

        if (Boolean.TRUE.equals(user.getBuiltIn())) {
            throw new ConflictException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);

        }

        userRepository.deleteById(id);
        return SuccessMessages.USER_DELETE;
    }

    public int countAllAdmins() {
        return userRepository.countAdmin(RoleType.ADMIN);
    }

    public ResponseMessage<UserResponse> saveUser(UserRequest userRequest, String role) {

        //!!! email -  phoneNumber unique mi kontrolu ??
        uniquePropertyValidator.checkDuplicate(userRequest.getEmail(),userRequest.getPhone());
        //!!! DTO --> POJO
        User user = userMapper.mapUserRequestToUser(userRequest);
        // !!! Rol bilgisi setleniyor
        if(role.equalsIgnoreCase(RoleType.ADMIN.name())){
            if(Objects.equals(userRequest.getEmail(),"admin@admin.com")){
                user.setBuiltIn(true);
            }
            user.setRole(roleService.getRole(RoleType.ADMIN));
        } else if (role.equalsIgnoreCase("Employee")) {
            user.setRole(roleService.getRole(RoleType.EMPLOYEE));
        } else if (role.equalsIgnoreCase("Member")) {
            user.setRole(roleService.getRole(RoleType.MEMBER));
        }
        // !!! password encode ediliyor
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);

        return ResponseMessage.<UserResponse>builder()
                .message(SuccessMessages.USER_CREATE)
                .object(userMapper.mapUserToUserResponse(savedUser))
                .build() ;
    }
}