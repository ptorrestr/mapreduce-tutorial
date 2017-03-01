package tutorial.mr.task1;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.hamcrest.number.IsCloseTo.closeTo;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tutorial.mr.task1.modelC.InDatum;

import com.google.common.collect.Lists;

public class ModelD {
	private static final Logger logger = LoggerFactory.getLogger(ModelD.class);
	List<InDatum> sums = Lists.newArrayList(new InDatum(1, 0.1, "s1"), new InDatum(2, 0.2, "s2" ), 
			new InDatum(3, 0.3, "s3"), new InDatum(4, 0.4, "s4"), new InDatum(5, 0.5, "s5"),
			new InDatum(6, 0.6, "s6"));
	
	@Test
	public void example5() {
		/*
		 * We severally reduce the number of lines by using functional programming
		 * Java-8 supports functional programming through lambda functions
		 *
		 */
		int total = sums.stream()
				.map( p->p.getOp1() ) // Map into a new type (in this case we preserve the type)
				.reduce( (p,q) -> p+q) // Reduce two values into a new one by adding them
				.get(); 
		// See that you do not require to define any datatype since the Java compiler will do it
		// for you
		
		assertThat( total, is(equalTo(1+2+3+4+5+6)) );
		logger.info( "The sum is : {} ", total);
	}
	
	@Ignore("You need to implement this")
	@Test
	public void exercise4() {
		// TODO: Sum doubles (op2)
		/* Your code goes here*/
		
		double total = 0;
		
		/* end */
		assertThat(total, closeTo(0.1+0.2+0.3+0.4+0.5+0.6, 0.001) );
		logger.info( "The sum is : {} ", total);
	}
	
	@Ignore("You need to implement this")
	@Test
	public void exercise5() {
		// TODO: Concatenate Strings (op3)
		/* Your code goes here*/
		
		String total = "";
		
		/* end */
		assertThat(total, containsString("s1"));
		assertThat(total, containsString("s2"));
		assertThat(total, containsString("s3"));
		assertThat(total, containsString("s4"));
		assertThat(total, containsString("s5"));
		assertThat(total, containsString("s6"));
		
		logger.info( "The string is : {} ", total);
	}
}
