package microservices.book.multiplication;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import microservices.book.multiplication.domain.MultiplicationResultAttemp;
import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.User;
import microservices.book.multiplication.repository.MultiplicationResultAttemptRepository;
import microservices.book.multiplication.repository.UserRepository;
import microservices.book.multiplication.service.MultiplicationServiceImpl;
import microservices.book.multiplication.service.RandomGeneratorService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;


public class MultiplicationServiceImplTest {
	
	private MultiplicationServiceImpl multiplicationServiceImpl;
	
	@Mock
	private RandomGeneratorService randomGeneratorService;
	
	@Mock
	private MultiplicationResultAttemptRepository attemptRepository;
	
	@Mock
	private UserRepository userRepository;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		multiplicationServiceImpl = new MultiplicationServiceImpl(randomGeneratorService, attemptRepository, userRepository);
	}
	
	@Test
	public void createRandomMultiplicationTest() {
		given(randomGeneratorService.generateRandomFactor()).willReturn(50,30);
		
		Multiplication multiplication = multiplicationServiceImpl.createRandomMultiplication();
		
		assertThat(multiplication.getFactorA()).isEqualTo(50);
		assertThat(multiplication.getFactorB()).isEqualTo(30);
		
	}
	
	@Test
	public void checkCorrectAttempTest() {
		//given
		Multiplication multiplication = new Multiplication(50, 60);
		User user = new User("foo_bar");
		MultiplicationResultAttemp attempt = new MultiplicationResultAttemp(user, multiplication, 3000,false);
		MultiplicationResultAttemp verifiedAttempt = new MultiplicationResultAttemp(user,multiplication,3000,true);
		given(userRepository.findByAlias("foo_bar")).willReturn(Optional.empty());
		
		//when
		boolean attempResult = multiplicationServiceImpl.checkAttempt(attempt);
		
		//assert
		assertThat(attempResult).isTrue();
	}
	
	@Test
	public void checkWrongAttempTest() {
		//given
		Multiplication multiplication = new Multiplication(50, 60);
		User user = new User("foo_bar");
		MultiplicationResultAttemp attempt = new MultiplicationResultAttemp(user, multiplication, 3010, false);
		given(userRepository.findByAlias("foo_bar")).willReturn(Optional.empty());
		
		//when
		boolean attempResult = multiplicationServiceImpl.checkAttempt(attempt);
		
		//assert 
		assertThat(attempResult).isFalse();
		verify(attemptRepository).save(attempt);
	}
	
	@Test
	public void retrieveStatsTest() {
		//given
		Multiplication multiplication = new Multiplication(50,60);
		User user = new User("foo_bar");
		MultiplicationResultAttemp attempt1 = new MultiplicationResultAttemp(user, multiplication, 3010, false);
		MultiplicationResultAttemp attempt2 = new MultiplicationResultAttemp(user, multiplication, 3051, false);
		
		List<MultiplicationResultAttemp> latestAttempts = Lists.newArrayList(attempt1, attempt2);
		given(userRepository.findByAlias("foo_bar")).willReturn(Optional.empty());
		given(attemptRepository.findTop5ByUserAliasOrderByIdDesc("foo_bar")).willReturn(latestAttempts);
		
		//when
		List<MultiplicationResultAttemp> latestAttemptResult = multiplicationServiceImpl.getStatsForUser("foo_bar");
		
		//then
		assertThat(latestAttemptResult).isEqualTo(latestAttempts);
		
		
		
	}

}
