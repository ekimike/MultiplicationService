package microservices.book.multiplication;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;

import microservices.book.multiplication.service.RandomGeneratorServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;

public class RandomGeneratorServiceImplTest {
	
	private RandomGeneratorServiceImpl randomGeneratorServiceImpl; 

	@Before
	public void setUp() {
		randomGeneratorServiceImpl = new RandomGeneratorServiceImpl();
	}
	
	@Test
	public void generateRandomFactorIsBetweenExpectedLimits() throws Exception {
		List<Integer> randomFactors = IntStream.range(1, 1000)
				.map(i -> randomGeneratorServiceImpl.generateRandomFactor())
				.boxed().collect(Collectors.toList());
		
		assertThat(randomFactors).containsOnlyElementsOf(IntStream.range(11, 100)
		.boxed().collect(Collectors.toList()));
	}

}