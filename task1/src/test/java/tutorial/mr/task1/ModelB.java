package tutorial.mr.task1;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import tutorial.mr.task1.modelB.Mapper;
import tutorial.mr.task1.modelB.Reducer;
import tutorial.mr.task1.modelB.Datum;

public class ModelB {
	private static final Logger logger = LoggerFactory.getLogger(ModelB.class);
	private List<Datum> sums = Lists.newArrayList(new Datum(1), new Datum(2), 
			new Datum(3), new Datum(4), new Datum(5), new Datum(6));
	
	@Test
	/**
	 * Sum list of integers using an ad-hoc original data structure
	 */
	public void example1() {
		// Mapper accepts Sum objects as input
		int total = sums.stream().map(new Mapper()).reduce(new Reducer()).get().getValue();
		
		assertThat( total, is(equalTo(1+2+3+4+5+6)) );
		logger.info( "The sum is : {} ", total);
	}
	
	@Ignore("You need to implement this") 
	@Test
	/**
	 * Multiply a list of elements
	 */
	public void exercise2() {
		// Your code goes here
		int total = 0;
		
		assertThat( total, is(equalTo(1*2*3*4*5*6)) );
		logger.info( "The multiplication is {} ", total);
		
	}
}
