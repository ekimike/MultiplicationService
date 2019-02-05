package microservices.book.multiplication.service;

import microservices.book.multiplication.domain.MultiplicationResultAttemp;

import java.util.List;

import microservices.book.multiplication.domain.Multiplication;

/**
 * Creates a multiplication object with two randomly-generated factors
 * between 11 and 99
 * 
 * @return a Multiplication object with random factors
 *
 */
public interface MultiplicationService {
	
	Multiplication createRandomMultiplication();
	boolean checkAttempt(final MultiplicationResultAttemp multiplicationResultAttemp);
	List<MultiplicationResultAttemp> getStatsForUser(String userAlias);
	
}
