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
		List<Integer> sums = Lists.newArrayList(1,2,3,4,5,6);
		
		int total = sums.stream().map( p->p
				)
				.reduce( (p,q) -> p+q
				)
				.get();
		
		assertThat( total, is(equalTo(1+2+3+4+5+6)) );
		logger.info( "The sum is : {} ", total);
	}
}
