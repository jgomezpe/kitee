package nsgl.integer;

import java.io.IOException;

import nsgl.iterator.Backable;

public interface Read extends nsgl.io.Read{
	public Integer read( Backable<Integer> reader ) throws IOException;
	
	@Override
	default Object read(Object one, Backable<Integer> reader) throws IOException { return read(reader); }	
}