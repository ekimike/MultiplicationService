package microservices.book.multiplication;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import microservices.book.multiplication.controller.MultiplicationResultAttempController;
import microservices.book.multiplication.domain.MultiplicationResultAttemp;
import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.User;
import microservices.book.multiplication.service.MultiplicationService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(MultiplicationResultAttempController.class)
public class MUltiplicationResultAttempControllerTest {

	@MockBean
	private MultiplicationService multiplicationService;
	
	@Autowired
	private MockMvc mvc;
	
	//this object will be magically initialized by the initFields method below.
	private JacksonTester<MultiplicationResultAttemp> jsonResultAttempt;
	private JacksonTester<List<MultiplicationResultAttemp>> jsonResultAttemptList;
	
	@Before
	public void setup() {
		JacksonTester.initFields(this, new ObjectMapper());
	}
	
	@Test
	public void postResultReturnCorrect() throws Exception {
		genericParameterizedTest(true);
	}
	
	@Test
	public void postResultReturnNotCorrect() throws Exception {
		genericParameterizedTest(false);
	}
	
	void genericParameterizedTest(final boolean correct) throws Exception {
		//given (remember we are not testing here the service itself
		given(multiplicationService
				.checkAttempt(any(MultiplicationResultAttemp.class)))
				.willReturn(correct);
		User user = new User("juan");
		Multiplication multiplication = new Multiplication(50,70);
		MultiplicationResultAttemp attempt = new MultiplicationResultAttemp(user, multiplication, 3500, correct);
	
		//when
		MockHttpServletResponse response = mvc.perform(
				post("/results").contentType(MediaType.APPLICATION_JSON)
				.content(jsonResultAttempt.write(attempt).getJson()))
				.andReturn().getResponse();
		
		//then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(
				jsonResultAttempt.write(
						new MultiplicationResultAttemp(attempt.getUser(), 
								attempt.getMultiplication(), 
								attempt.getResultAttempt(), 
								correct)
						).getJson());
	}

	@Test
	public void getUserStats() throws Exception {
		//given
		User user = new User("foo_bar");
		Multiplication multiplication = new Multiplication(50, 70);
		MultiplicationResultAttemp attempt = new MultiplicationResultAttemp(user, multiplication, 3500, true);
		
		List<MultiplicationResultAttemp> recentAttempts = Lists.newArrayList(attempt, attempt);
		given(multiplicationService.getStatsForUser("foo_bar")).willReturn(recentAttempts);
		
		//when
		MockHttpServletResponse response = mvc.perform(get("/results").param("alias", "foo_bar")).andReturn().getResponse();
		
		//then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonResultAttemptList.write(recentAttempts).getJson());
	}
}
