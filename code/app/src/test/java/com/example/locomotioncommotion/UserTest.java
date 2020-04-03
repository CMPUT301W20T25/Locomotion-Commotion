package com.example.locomotioncommotion;
import com.example.locomotioncommotion.model.Driver;
import com.example.locomotioncommotion.model.Rider;
import com.example.locomotioncommotion.model.User;

import org.junit.*;
import static org.junit.Assert.*;

public class UserTest {
    @Test
    public void testThumbsUpAndDown() {
       User user =  User.getInstance("testUser","testPassword");

        assertEquals(-1,user.getThumbsUp());
        assertEquals(-1,user.getThumbsDown());

        user.setDriver(new Driver());

       assertEquals(0,user.getThumbsUp());
       assertEquals(0,user.getThumbsDown());

       user.setThumbsUp(user.getThumbsUp()+1);
       user.setThumbsDown(user.getThumbsDown()+1);

       assertEquals(1,user.getThumbsUp());
       assertEquals(1,user.getThumbsDown());

       user.setThumbsUp(100);
       user.setThumbsDown(1000);

        assertEquals(100,user.getThumbsUp());
        assertEquals(1000,user.getThumbsDown());

        user.setThumbsUp(-1);

        assertEquals(-1,user.getThumbsUp());
    }

    @Test
    public void editDetails(){
        User user = User.getInstance("testUser","testPass");
        assertEquals("testUser", user.getUserName());
        assertEquals("testPass", user.getPassWord());

        assertEquals("",user.getEmail());
        assertEquals("",user.getPhoneNumber());
        assertEquals(null, user.getDriver());
        assertEquals(null,user.getRider());

        String email = "test@test.ca";
        String phoneNumber = "780-555-5555";
        Driver driver = new Driver();
        Rider rider = new Rider();

        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setDriver(driver);
        user.setRider(rider);

        assertEquals(email,user.getEmail());
        assertEquals(phoneNumber,user.getPhoneNumber());
        assertEquals(driver, user.getDriver());
        assertEquals(rider,user.getRider());



    }
}
