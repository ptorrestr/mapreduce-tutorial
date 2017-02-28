package tutorial.mr.task1;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tutorial.mr.task1.modelA.Mapper;
import tutorial.mr.task1.modelA.Reducer;

import com.google.common.collect.Lists;

public class ModelA {
	private static final Logger logger = LoggerFactory.getLogger(ModelA.class);
	private List<Integer> sums = Lists.newArrayList(1,2,3,4,5,6);
	
	@Test
	/**
	 * Sum a list of integers
	 */
	public void example1() {
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
	
	@Ignore("You need to implement this") 
	@Test
	/**
	 * Multiply a list of elements
	 */
	public void exercise1() {
		// Execute map
		/* Your code goes here */	
		
		int total = 0;	
		assertThat(total, is(equalTo(1*2*3*4*5*6)) );
	}
	
	class Mapper2 implements Function<Integer, Integer> {
		@Override
		public Integer apply(Integer t) {
			// Your code should be here
			return null;
		}
	}
	
	class Reducer2 implements BinaryOperator<Integer> {
		@Override
		public Integer apply(Integer t, Integer u) {
			// Your code should be here
			return null;
		}	
	}
}
