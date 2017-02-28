package tutorial.mr.task1;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tutorial.mr.task1.modelC.Mapper;
import tutorial.mr.task1.modelC.Reducer;
import tutorial.mr.task1.modelC.Result;
import tutorial.mr.task1.modelC.Sum;

import com.google.common.collect.Lists;

public class ModelC {
	private static final Logger logger = LoggerFactory.getLogger(ModelC.class);
	List<Sum> sums = Lists.newArrayList(new Sum(1,2), new Sum(3,4), new Sum(5,6));
	
	@Test
	public void example1() {
		// Mapper accepts Sum objects as input and Reducer accepts Results objects as input
		Result total = sums.stream().map(new Mapper()).reduce(new Reducer()).get();
		
		assertThat( total.getResult(), is(equalTo(1+2+3+4+5+6)) );
		logger.info( "The sum is : {} ", total.getResult());
	}
}