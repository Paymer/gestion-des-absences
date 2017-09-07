package back.TestAbsence;



import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.controller.ControllerAbsence;
import dev.entite.Absence;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { ControllerAbsence.class })
public class TestAbsence {



	@Autowired
	private MockMvc mvc;
	@Autowired
	private ObjectMapper mapper;



	@Test
	public void PostTest() throws Exception {


		String json = mapper.writeValueAsString(new Absence());

		mvc.perform(post("/absence/demande")
	    	       .contentType(MediaType.APPLICATION_JSON)
	    	       .content(json)
	    	       .accept(MediaType.APPLICATION_JSON))
	    	       .andExpect(status().isOk());
	}

}
