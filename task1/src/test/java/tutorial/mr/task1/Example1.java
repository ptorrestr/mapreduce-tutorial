package tutorial.mr.task1;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import tutorial.mr.task1.model.Mapper;
import tutorial.mr.task1.model.Reducer;
import tutorial.mr.task1.model.Sum;

public class Example1 {
	private static final Logger logger = LoggerFactory.getLogger(Example1.class);
	// t1
	
	@Test
	public void example1() {
		Sum s1 = new Sum(1,2);
		Sum s2 = new Sum(3,4);
		Sum s3 = new Sum(5,6);
		List<Sum> sums = Lists.newArrayList(s1, s2, s3);
		
		int total = sums.stream().map(new Mapper()).reduce(new Reducer()).get();
		
		logger.info( "The sum is : {} ", total);
	}
}
