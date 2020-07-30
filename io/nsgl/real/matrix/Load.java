package nsgl.real.matrix;

import java.io.IOException;
import java.io.InputStream;

import nsgl.io.Loadable;

public class Load implements nsgl.io.Load{
	public double[][] load( InputStream is ) throws IOException{
		Loadable l = Loadable.cast(0);
		int n = (int)l.load(is);
		double[][] x = new double[n][];
		l = Loadable.cast(new double[] {0.0});
		for( int i=0; i<n; i++ ) x[i] = (double[])l.load(is);
		return x;	    
	}
	
	@Override
	public Object load( Object obj, InputStream is ) throws IOException{ return load(is); }
}