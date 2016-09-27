package tutorial.mr.task1;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import tutorial.mr.task1.modelB.Mapper;
import tutorial.mr.task1.modelB.Reducer;
import tutorial.mr.task1.modelB.Sum;

public class ExampleB {
	private static final Logger logger = LoggerFactory.getLogger(ExampleB.class);
	// t1
	
	@Test
	public void example1() {
		Sum s1 = new Sum(1,2);
		Sum s2 = new Sum(3,4);
		Sum s3 = new Sum(5,6);
		List<Sum> sums = Lists.newArrayList(s1, s2, s3);
		
		int total = sums.stream().map(new Mapper()).reduce(new Reducer()).get();
		
		assertThat( total, is(equalTo(1+2+3+4+5+6)) );
		logger.info( "The sum is : {} ", total);
	}
}
