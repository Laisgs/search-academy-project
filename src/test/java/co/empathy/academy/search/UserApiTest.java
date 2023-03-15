package co.empathy.academy.search;

import co.empathy.academy.search.users.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class UserApiTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void testAddOk() throws Exception {
        User user = new User(1, "Nombre","Apellido","user1@email.es");
/*
        mvc.perform(MockMvcRequestBuilders.post("/users/add")).content(user)
                .andExpect(MockMvcResultMatchers.content().json("User " + user.getName() + " added"))
                .andExpect(MockMvcResultMatchers.status().isCreated());

 */
    }
}
