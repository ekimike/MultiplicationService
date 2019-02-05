package microservices.book.multiplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import microservices.book.multiplication.domain.MultiplicationResultAttemp;
import microservices.book.multiplication.service.MultiplicationService;

@RestController
@RequestMapping("/results")
public final class MultiplicationResultAttempController {

	private final MultiplicationService multiplicationService;
	
	@Autowired
	MultiplicationResultAttempController(final MultiplicationService multiplicationService) {
		this.multiplicationService = multiplicationService;
	}
	
	@PostMapping
	ResponseEntity<MultiplicationResultAttemp> postResult(@RequestBody MultiplicationResultAttemp multiplicationResultAttemp) {
		boolean isCorrect = multiplicationService.checkAttempt(multiplicationResultAttemp);
		MultiplicationResultAttemp attemptCopy = new MultiplicationResultAttemp(
				multiplicationResultAttemp.getUser(),
				multiplicationResultAttemp.getMultiplication(),
				multiplicationResultAttemp.getResultAttempt(),
				isCorrect);
		
		return ResponseEntity.ok(attemptCopy);
	}

	@GetMapping
	ResponseEntity<List<MultiplicationResultAttemp>> getStatistics(@RequestParam("alias") String alias) {
		return ResponseEntity.ok(multiplicationService.getStatsForUser(alias));
	}
}
