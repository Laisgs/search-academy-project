package co.empathy.academy.search;

import co.empathy.academy.search.users.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.awt.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserApiTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void testAddOk() throws Exception {
        User user = new User(1, "Nombre","Apellido","user1@email.es");

        mvc.perform(MockMvcRequestBuilders.post("/users").content("{\"id\":" + user.getId()
                        +",\"name\":\"" + user.getName() + "\",\"surname\":\"" + user.getSurname() +
                        "\",\"email\":\"" + user.getEmail() + "\"}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string("User " + user.getName() + " added"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void testAddFailed() throws Exception {
        User user = new User(2, "Nombre","Apellido","user1@email.es");

        mvc.perform(MockMvcRequestBuilders.post("/users").content("{\"id\":" + user.getId()
                        +",\"name\":\"" + user.getName() + "\",\"surname\":\"" + user.getSurname() +
                        "\",\"email\":\"" + user.getEmail() + "\"}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string("User " + user.getName() + " added"))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        mvc.perform(MockMvcRequestBuilders.post("/users").content("{\"id\":" + user.getId()
                        +",\"name\":\"" + user.getName() + "\",\"surname\":\"" + user.getSurname() +
                        "\",\"email\":\"" + user.getEmail() + "\"}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string("User " + user.getName() + " already exist"))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    void testGetUser() throws Exception {
        User user = new User(3, "Nombre","Apellido","user1@email.es");

        mvc.perform(MockMvcRequestBuilders.post("/users").content("{\"id\":" + user.getId()
                        +",\"name\":\"" + user.getName() + "\",\"surname\":\"" + user.getSurname() +
                        "\",\"email\":\"" + user.getEmail() + "\"}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string("User " + user.getName() + " added"))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        mvc.perform(MockMvcRequestBuilders.get("/users/"+ user.getId()))
                .andExpect(MockMvcResultMatchers.content()
                        .json("{\"id\":" + user.getId()
                        + ",\"name\":\"" + user.getName() + "\",\"surname\":\""+ user.getSurname() +
                                "\",\"email\":" + user.getEmail() + "}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetUserFailed() throws Exception {
        User user = new User(4, "Nombre","Apellido","user1@email.es");

        mvc.perform(MockMvcRequestBuilders.post("/users").content("{\"id\":" + user.getId()
                        +",\"name\":\"" + user.getName() + "\",\"surname\":\"" + user.getSurname() +
                        "\",\"email\":\"" + user.getEmail() + "\"}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string("User " + user.getName() + " added"))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        mvc.perform(MockMvcRequestBuilders.get("/users/"+ 20))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testGetUsers() throws Exception {
        User user = new User(5, "Nombre","Apellido","user1@email.es");
        User user2 = new User(6, "Nombre6","Apellido6","user6@email.es");

        mvc.perform(MockMvcRequestBuilders.post("/users").content("{\"id\":" + user.getId()
                        +",\"name\":\"" + user.getName() + "\",\"surname\":\"" + user.getSurname() +
                        "\",\"email\":\"" + user.getEmail() + "\"}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string("User " + user.getName() + " added"))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        mvc.perform(MockMvcRequestBuilders.post("/users").content("{\"id\":" + user2.getId()
                        +",\"name\":\"" + user2.getName() + "\",\"surname\":\"" + user2.getSurname() +
                        "\",\"email\":\"" + user2.getEmail() + "\"}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string("User " + user2.getName() + " added"))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        mvc.perform(MockMvcRequestBuilders.get("/users/all"))
                .andExpect(MockMvcResultMatchers.content()
                        .json("[{\"id\":" + user.getId()
                                + ",\"name\":\"" + user.getName() + "\",\"surname\":\""+ user.getSurname() +
                                "\",\"email\":" + user.getEmail() + "},{\"id\":" + user2.getId()
                                +   ",\"name\":\"" + user2.getName() + "\",\"surname\":\""+ user2.getSurname() +
                                "\",\"email\":" + user2.getEmail() + "}]"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testEditUserOk() throws Exception {
        User user = new User(7, "Nombre","Apellido","user1@email.es");

        mvc.perform(MockMvcRequestBuilders.post("/users").content("{\"id\":" + user.getId()
                        +",\"name\":\"" + user.getName() + "\",\"surname\":\"" + user.getSurname() +
                        "\",\"email\":\"" + user.getEmail() + "\"}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string("User " + user.getName() + " added"))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        mvc.perform(MockMvcRequestBuilders.put("/users").content("{\"id\":" + user.getId()
                        +",\"name\":\"" + "nameEdited" + "\",\"surname\":\"" + user.getSurname() +
                        "\",\"email\":\"" + user.getEmail() + "\"}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string("User " + "nameEdited" + " edited"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testEditUserFailed() throws Exception {
        User user = new User(8, "Nombre","Apellido","user1@email.es");

        mvc.perform(MockMvcRequestBuilders.post("/users").content("{\"id\":" + user.getId()
                        +",\"name\":\"" + user.getName() + "\",\"surname\":\"" + user.getSurname() +
                        "\",\"email\":\"" + user.getEmail() + "\"}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string("User " + user.getName() + " added"))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        mvc.perform(MockMvcRequestBuilders.put("/users").content("{\"id\":" + "35"
                        +",\"name\":\"" + "nameEdited" + "\",\"surname\":\"" + user.getSurname() +
                        "\",\"email\":\"" + user.getEmail() + "\"}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string("User " + "nameEdited" + " do not exist"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testDeleteUserOk() throws Exception {
        User user = new User(9, "Nombre","Apellido","user1@email.es");

        mvc.perform(MockMvcRequestBuilders.post("/users").content("{\"id\":" + user.getId()
                        +",\"name\":\"" + user.getName() + "\",\"surname\":\"" + user.getSurname() +
                        "\",\"email\":\"" + user.getEmail() + "\"}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string("User " + user.getName() + " added"))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        mvc.perform(MockMvcRequestBuilders.get("/users/"+ user.getId()))
                .andExpect(MockMvcResultMatchers.content()
                        .json("{\"id\":" + user.getId()
                                + ",\"name\":\"" + user.getName() + "\",\"surname\":\""+ user.getSurname() +
                                "\",\"email\":" + user.getEmail() + "}"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        mvc.perform(MockMvcRequestBuilders.delete("/users/"+ user.getId()))
                .andExpect(MockMvcResultMatchers.content()
                        .string("User with id " + user.getId() + " deleted"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        mvc.perform(MockMvcRequestBuilders.get("/users/"+ user.getId()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testDeleteUserFailed() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/users/"+ "34"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("User with id " + "34" + " do not exist"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
