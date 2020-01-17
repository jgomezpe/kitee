package nsgl.real;

import java.io.IOException;

import nsgl.iterator.Backable;

public interface Read extends nsgl.io.Read{
	public Double read(Backable<Integer> reader) throws IOException;

	@Override
	default Object read(Object obj, Backable<Integer> reader) throws IOException{ return read(reader); }
}