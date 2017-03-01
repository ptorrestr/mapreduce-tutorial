package tutorial.mr.task1;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.hamcrest.core.StringContains.containsString;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tutorial.mr.task1.modelC.Mapper;
import tutorial.mr.task1.modelC.Reducer;
import tutorial.mr.task1.modelC.InDatum;

import com.google.common.collect.Lists;

public class ModelC {
	private static final Logger logger = LoggerFactory.getLogger(ModelC.class);
	List<InDatum> sums = Lists.newArrayList(new InDatum(1, 0.1, "s1"), new InDatum(2, 0.2, "s2" ), 
			new InDatum(3, 0.3, "s3"), new InDatum(4, 0.4, "s4"), new InDatum(5, 0.5, "s5"),
			new InDatum(6, 0.6, "s6"));
	
	@Test
	public void example3() {
		// Mapper accepts Sum objects as input and Reducer accepts Results objects as input
		int total = sums.stream().map(new Mapper()).reduce(new Reducer()).get().getValue();
		
		assertThat( total, is(equalTo(1+2+3+4+5+6)) );
		logger.info( "The sum is : {} ", total);
	}
	
	@Ignore("You need to implement this")
	@Test
	public void exercise3() {
		// Compute the sum of the 2 argument for InDatum
		double total = 0;
		
		assertThat(total, closeTo(2.1, 0.001) );
	}
	
	@Ignore("You need to implement this")
	@Test
	public void exercise4() {
		String total = "";	
		assertThat(total, containsString("s1"));
		assertThat(total, containsString("s2"));
		assertThat(total, containsString("s3"));
		assertThat(total, containsString("s4"));
		assertThat(total, containsString("s5"));
		assertThat(total, containsString("s6"));
	}
}
