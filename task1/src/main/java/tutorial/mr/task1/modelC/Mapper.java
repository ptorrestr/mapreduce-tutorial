package tutorial.mr.task1.modelC;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * A Mapper function takes a single datum and creates a new version
 * of it. The output is used to "map" such datum into a processor.
 * 
 * In this case, the input data is an object Sum and the mapping generates
 * a Result object.
 *
 */
public class Mapper<T> implements Function<InDatum, OutDatum<T>> {
	String prop;
	
	public Mapper(String prop) {
		this.prop = prop;
	}

	@SuppressWarnings("unchecked")
	@Override
	public OutDatum<T> apply(InDatum t) {
		try {
			return new OutDatum<T>( (T) PropertyUtils.getSimpleProperty(t, prop));
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}