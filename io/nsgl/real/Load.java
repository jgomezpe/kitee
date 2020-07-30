package nsgl.real;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;


public class Load implements nsgl.io.Load{
	public double load( InputStream is ) throws IOException{
	    DataInputStream dis = new DataInputStream(is);
	    return dis.readDouble();
	}
	
	@Override
	public Object load(Object one, InputStream is ) throws IOException { return load(is); }	
}