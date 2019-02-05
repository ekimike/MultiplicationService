package microservices.book.multiplication;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import microservices.book.multiplication.service.RandomGeneratorService;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RandomGeneratorServiceTest {

	@Autowired
	private RandomGeneratorService randomGeneratorService;
	
	
	@Test
	public void generateRandomFactorIsBetweenExpectedLimits() throws Exception {
		/**
		 * We use a Java 8 Stream of the first 1000 numbers to mimic a for loop. 
		 * Then, we transform each number with map to a random int factor, we box each one to an Integer object, and finally we collect them into a list. 
		 * @throws Exception
		 */
		//when a good sample of randomly generated factors is generated
		List<Integer> randomFactors = IntStream.range(0,1000)
				.map(i -> randomGeneratorService.generateRandomFactor())
				.boxed().collect(Collectors.toList());
		
		//then all of then should be between 11 and 100
		assertThat(randomFactors).containsOnlyElementsOf(IntStream.range(11, 100)
				.boxed().collect(Collectors.toList()));
	}

}
