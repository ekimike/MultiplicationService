package microservices.book.multiplication.service;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class RandomGeneratorServiceImpl  implements RandomGeneratorService {
	
	final static int 
		MINIMUN_FACTOR = 11, 
		MAXIMUN_FACTOR = 99;

	@Override
	public int generateRandomFactor() {
		return new Random().nextInt((MAXIMUN_FACTOR - MINIMUN_FACTOR) + 1) + MINIMUN_FACTOR;
	}

}
