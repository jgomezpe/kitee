package nsgl.real.matrix;

import java.io.IOException;

import nsgl.iterator.Backable;

public interface Read extends nsgl.io.Read{
	public double[][] read( Backable<Integer> reader ) throws IOException;
	default Object read( Object obj, Backable<Integer> reader ) throws IOException{ return read(reader); }
}