package nsgl.integer;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Load implements nsgl.io.Load{
	public int load( InputStream is ) throws IOException{
	    DataInputStream dis = new DataInputStream(is);
	    return dis.readInt();
	}
	
	@Override
	public Object load(Object one, InputStream is ) throws IOException { return load(is); }	
}