package nsgl.io;

import java.io.IOException;
import java.io.OutputStream;

import nsgl.cast.CastServer;

public interface Store {
	/**
	 * Writes an object to the given writer
	 * @param obj Object to write
	 * @param writer The writer object
	 * @throws IOException IOException
	 */
	public void store(Object obj, OutputStream os) throws IOException;
	
	static Store store(Object obj) { return (Store)CastServer.service(obj,Storable.class); } 

}
