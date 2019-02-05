package microservices.book.multiplication.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import microservices.book.multiplication.domain.MultiplicationResultAttemp;
import microservices.book.multiplication.domain.User;
import microservices.book.multiplication.repository.MultiplicationResultAttemptRepository;
import microservices.book.multiplication.repository.UserRepository;
import microservices.book.multiplication.domain.Multiplication;

@Service
public class MultiplicationServiceImpl implements MultiplicationService {

	private RandomGeneratorService randomGeneratorService;
	private MultiplicationResultAttemptRepository attemptRepository;
	private UserRepository userRepository;
	
	@Autowired
	public MultiplicationServiceImpl(
			final RandomGeneratorService randomGeneratorService,
			final MultiplicationResultAttemptRepository attemptRepository,
			final UserRepository userRepository) {
		this.randomGeneratorService = randomGeneratorService;
		this.attemptRepository = attemptRepository;
		this.userRepository = userRepository;
	}
	
	@Override
	public Multiplication createRandomMultiplication() {
		int factorA = randomGeneratorService.generateRandomFactor();
		int factorB = randomGeneratorService.generateRandomFactor();
		return new Multiplication(factorA, factorB);
	}
	
	@Transactional
	@Override
	public boolean checkAttempt(final MultiplicationResultAttemp multiplicationResultAttemp) {
		System.out.println("multiplicationResultAttemp.getResultAttemp(): " + multiplicationResultAttemp.getResultAttempt());
		//check if the user already exists for that alias
		Optional<User> user = userRepository.findByAlias(multiplicationResultAttemp.getUser().getAlias());
		
		//avoid hack attempt
		Assert.isTrue(!multiplicationResultAttemp.isCorrect(), "Cannot send a correct one");
		
		//check if the attempt is correct
		boolean isCorrect = multiplicationResultAttemp.getResultAttempt() ==
				multiplicationResultAttemp.getMultiplication().getFactorA() *
				multiplicationResultAttemp.getMultiplication().getFactorB();
		
		MultiplicationResultAttemp checkedAttempt = new MultiplicationResultAttemp(
				user.orElse(multiplicationResultAttemp.getUser()),
				multiplicationResultAttemp.getMultiplication(),
				multiplicationResultAttemp.getResultAttempt(),
				isCorrect);
		
		//stores the arttempt
		attemptRepository.save(checkedAttempt);
		
		
		return isCorrect;
	}
	
	@Override
	public List<MultiplicationResultAttemp> getStatsForUser(String userAlias) {
		return attemptRepository.findTop5ByUserAliasOrderByIdDesc(userAlias);
	}

}
