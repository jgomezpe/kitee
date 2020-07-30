package nsgl.integer.array;

import java.io.IOException;
import java.io.InputStream;

import nsgl.io.Loadable;

public class Load implements nsgl.io.Load{
	/**
	 * Reads an array from the input stream (the first value is the array's size and the following values are the values in the array)
	 * @param reader The reader object
	 * @throws ParamsException ParamsException
	 */
	public int[] load(InputStream is) throws IOException{
		Loadable l = Loadable.cast(0);
		int n = (int)l.load(is);
		int[] x = new int[n];
		for( int i=0; i<n; i++ ) x[i] = (int)l.load(is);
		return x;
	}

	@Override
	public Object load(Object obj, InputStream is) throws IOException{ return load(is); }    
}