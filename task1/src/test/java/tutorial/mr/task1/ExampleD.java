package tutorial.mr.task1;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

public class ExampleD {
	private static final Logger logger = LoggerFactory.getLogger(ExampleD.class);
	
	@Test
	public void example1() {
		// Create a list of element to process
		List<Integer> sums = Lists.newArrayList(1,2,3,4,5,6);
		
		/*
		 * Mapper and reducer can add several lines of code depending on task. We can avoid this
		 * using Lambda expressions available in Java-8
		 */
		int total = sums.stream()
				.map( p->p ) // Map into a new type (in this case we preserve the type)
				.reduce( (p,q) -> p+q) // Reduce two values into a new one by adding them
				.get(); 
		// See that you do not require to define any datatype since the Java compiler will do it
		// for you
		
		assertThat( total, is(equalTo(1+2+3+4+5+6)) );
		logger.info( "The sum is : {} ", total);
	}
}
