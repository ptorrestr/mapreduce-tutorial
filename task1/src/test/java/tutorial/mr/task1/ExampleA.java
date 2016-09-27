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
		List<Integer> sums = Lists.newArrayList(1,2,3,4,5,6);
		
		int total = sums.stream().map(new Mapper()).reduce(new Reducer()).get();
		
		assertThat( total, is(equalTo(1+2+3+4+5+6)) );
		logger.info( "The sum is : {} ", total);
	}
}
