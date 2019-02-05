package microservices.book.multiplication.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import microservices.book.multiplication.domain.MultiplicationResultAttemp;

public interface MultiplicationResultAttemptRepository extends CrudRepository<MultiplicationResultAttemp, Long> {

	/**
	 * 
	 * @param userAlias
	 * @return the latest 5 attempts for a given user, identified by their alias
	 */
	List<MultiplicationResultAttemp> findTop5ByUserAliasOrderByIdDesc(String userAlias);
	
}
