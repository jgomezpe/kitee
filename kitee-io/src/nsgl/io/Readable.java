package nsgl.io;

import java.io.IOException;

import nsgl.cast.CastServer;
import nsgl.iterator.Backable;

public interface Readable {
	/**
	 * Reads an object from the given reader
	 * @param reader The input stream from which the object will be read
	 * @return An object, of the type the read service is attending, that is read from the input stream
	 * @throws IOException IOException
	 */
	Object read(Backable<Integer> reader) throws IOException;
	
	static Readable cast( Object obj ){
		if( obj == null ) return null; 
		if( obj instanceof Readable ) return (Readable)obj;
		Read cast = (Read)CastServer.service(obj,Readable.class);
		if( cast != null ) return new Readable() {
			@Override
			public Object read(Backable<Integer> reader) throws IOException {
				return cast.read(obj, reader);
			}
		}; 
		return null; 
	}
	
	/**
	 * Adds a reading method for the given object 
	 * @param caller Object that will register its reading method
	 * @param cast Reading method
	 */
	static void addCast( Object caller, Read cast ) { CastServer.setService(caller, Readable.class, cast); }	
}