package models;

import models.*;
import org.junit.*;
import static org.junit.Assert.*;
import play.test.WithApplication;
import static play.test.Helpers.*;

public class ModelsTest extends WithApplication {
    @Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase()));
    }
    

    /*public void createAndRetrieveUser() {
        new User("bob@gmail.com", "Bob", "secret").save();
        User bob = User.find.where().eq("email", "bob@gmail.com").findUnique();
        assertNotNull(bob);
        assertEquals("Bob", bob.name);
    }*/
    
    /*@Test
    public void tryAuthenticateUser() {
        new User("70110024", "kamiya", "kamiya").save();
        
        assertNotNull(User.authenticate("70110024", "kamiya"));
        assertNull(User.authenticate("70110024", "badpassword"));
        assertNull(User.authenticate("tom@gmail.com", "kamiya"));
    }*/
}