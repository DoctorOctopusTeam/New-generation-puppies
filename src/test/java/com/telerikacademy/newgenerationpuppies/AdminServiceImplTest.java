package com.telerikacademy.newgenerationpuppies;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.telerikacademy.newgenerationpuppies.adminservice.AdministartorService;
import com.telerikacademy.newgenerationpuppies.models.User;
import com.telerikacademy.newgenerationpuppies.repos.adminrepository.AdminRepository;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.servlet.http.HttpServletRequest;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AdminServiceImplTest {

    @MockBean
    private AdminRepository adminRepository;
    @Autowired
    private AdministartorService administartorService;
    @Autowired
    HttpServletRequest httpServletRequest;
    private User user;
    private String role;
    private String repeatPassword;
    private String userName;
    private String newPassword;

    @Before
    public void setUp(){
        user = new User();
        user.setUserName("abc");
        user.setPassword("123");
        user.setEmail("abc@abv.bg");
        role = "ROLE_ADMIN";
        repeatPassword = "";
        userName = "BamBam";
    }
    @After
    public void destroy(){
        user = null;
        role = null;
        repeatPassword = null;
    }
    @Test
    public void SaveUser_ReturnsResponseEntityErrorMessage_UserNameAlreadyUsed_WhenUserNameIsInUse() throws Exception{
        //Arrange
        Mockito.when(adminRepository.findUser(user.getUserName()))
        .thenReturn(user);
        //Act
        ResponseEntity responseEntity = administartorService.saveUser(user, role, repeatPassword);
        //Assert
        Assert.assertEquals(returnResponseEntity("Username is already used!", user), responseEntity);
    }

    @Test
    public void SaveUser_ReturnsResponseEntityErrorMessage_UserNameOrPasswordNotEntered_WhenNotEntered() throws Exception {
        //Arrange
        user.setUserName("");
        user.setPassword("");
        //Act
        ResponseEntity responseEntity = administartorService.saveUser(user, role, repeatPassword);
        //Assert
        Assert.assertEquals(returnResponseEntity("Username or/and password not entered!", user), responseEntity);
    }

    @Test
    public void SaveUser_ReturnsResponseEntityErrorMessage_PasswordFieldsMustMatch_WhenTheyDontMatch() throws Exception {
        //Arrange
        repeatPassword = "!";
        user.setPassword("123");
        //Act
        ResponseEntity responseEntity = administartorService.saveUser(user, role, repeatPassword);
        //Assert
        Assert.assertEquals(returnResponseEntity("Both password fields must match!", user), responseEntity);
    }

    @Test
    public void SaveUser_ReturnsResponseEntityErrorMessage_EmailRequired_WhenEmailNotEntered() throws Exception {
        //Arrange
        repeatPassword = "abc";
        user.setPassword("abc");
        user.setEmail("");
        //Act
        ResponseEntity responseEntity = administartorService.saveUser(user, role, repeatPassword);
        //Assert
        Assert.assertEquals(returnResponseEntity("Email is required!", user), responseEntity);
    }

    @Test
    public void SaveUser_ReturnsResponseEntity_OK_WhenAllIsSet() throws Exception {
        //Arrange
        repeatPassword = "abc";
        user.setPassword("abc");
        Mockito.when(adminRepository.saveUser(user, role, repeatPassword))
                .thenReturn(new ResponseEntity<>(user, HttpStatus.OK));
        //Act
        ResponseEntity responseEntity = administartorService.saveUser(user, role, repeatPassword);
        //Assert
        Assert.assertEquals(new ResponseEntity<>(user, HttpStatus.OK), responseEntity);
    }

    @Test
    public  void updateClient_ReturnResponseEntityErroressage_NoUserSpecified_WhenNoUsernameEntered(){
        //Arrange
        userName = "";
        //Act
        ResponseEntity responseEntity = administartorService.updateClient(userName, user, httpServletRequest );
        //Assert
        Assert.assertEquals(returnResponseEntity("No user specified!", user), responseEntity);
    }

    @Test
    public  void updateClient_ReturnResponseEntityErroressage_NoSuchUser_WhenNoSuchUser(){
        //Arrange
        userName = "BamBam";
        Mockito.when(adminRepository.findUser(userName))
                .thenReturn(null);
        //Act
        ResponseEntity responseEntity = administartorService.updateClient(userName, user, httpServletRequest );
        //Assert
        Assert.assertEquals(returnResponseEntity("No such user in the database!", null), responseEntity);
    }

    @Test
    public  void changePassword_ReturnResponseEntityErroressage_BothFieldsMustMatch_WhenTheyDoNot(){
        //Arrange
        newPassword = "123";
        repeatPassword = "456";
        //Act
        ResponseEntity responseEntity = administartorService.changePassword(newPassword, repeatPassword, httpServletRequest);
        //Assert
        Assert.assertEquals(returnResponseEntity("Both fields must have identical entries!", null), responseEntity);
    }

    @Test
    public  void changePassword_ReturnResponseEntityErroressage_CanNotBeEmptyString_WhenItIs(){
        //Arrange
        newPassword = "";
        repeatPassword = "";
        //Act
        ResponseEntity responseEntity = administartorService.changePassword(newPassword, repeatPassword, httpServletRequest);
        //Assert
        Assert.assertEquals(returnResponseEntity("Password can not be empty string!", null), responseEntity);
    }

    @Test
    public  void deleteUser_ReturnResponseEntityErroressage_NoSuchUser_WhenThereIsNone(){
        //Arrange
        Mockito.when(adminRepository.findUser(userName)).thenReturn(null);
        //Act
        ResponseEntity responseEntity = administartorService.deleteUser("");
        //Assert
        Assert.assertEquals(returnResponseEntity("No such user in tha database!", null), responseEntity);
    }























    public ResponseEntity returnResponseEntity(String message, User user){
        return ResponseEntity.badRequest()
                .header("Access-Control-Expose-Headers","Error")
                .header("Error", message)
                .body(user);
    }
}
