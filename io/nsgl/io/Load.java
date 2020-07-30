package nsgl.io;

import java.io.IOException;
import java.io.InputStream;

import nsgl.cast.CastServer;

public interface Load {
	/**
	 * Reads an object from the given stream
	 * @param is The input stream from which the object will be read
	 * @return An object, of the type the load service is attending, that is load from the input stream
	 * @throws IOException IOException
	 */
	Object load(Object obj, InputStream is) throws IOException;

	static Load loader(Object obj) { return (Load)CastServer.service(obj,Loadable.class); } 
}