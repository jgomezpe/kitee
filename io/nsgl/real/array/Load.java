package nsgl.real.array;

import java.io.IOException;
import java.io.InputStream;

import nsgl.io.Loadable;

public class Load implements nsgl.io.Load{
	/**
	 * Reads an array from the input stream (the first value is the array's size and the following values are the values in the array)
	 * @param reader The reader object
	 * @throws ParamsException ParamsException
	 */
	public double[] load(InputStream is) throws IOException{
		Loadable l = Loadable.cast(0);
		int n = (int)l.load(is);
		double[] x = new double[n];
		l = Loadable.cast(0.0);
		for( int i=0; i<n; i++ ) x[i] = (double)l.load(is);
		return x;
	}

	@Override
	public Object load(Object obj, InputStream is) throws IOException{ return load(is); }    
}
