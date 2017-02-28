package tutorial.mr.task1;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tutorial.mr.task1.modelA.Mapper;
import tutorial.mr.task1.modelA.Reducer;

import com.google.common.collect.Lists;

public class ExampleA {
	private static final Logger logger = LoggerFactory.getLogger(ExampleA.class);
	
	@Test
	public void example1() {
		// Create a list of element to process
		List<Integer> sums = Lists.newArrayList(1,2,3,4,5,6);
		
		// Execute map reduce. Just one line of code!
		int total = sums.stream().map(new Mapper()).reduce(new Reducer()).get();
		
		/*
		 * 1- generate a Java-8 stream.
		 * 2- Map each element in the list according to Mapper function
		 * 3- Reduce the output of mapper according to Reducer function
		 * 4- Obtain the result of the reduction.
		 */
		
		assertThat( total, is(equalTo(1+2+3+4+5+6)) );
		logger.info( "The sum is : {} ", total);
	}
}
