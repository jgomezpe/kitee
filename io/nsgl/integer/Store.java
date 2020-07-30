package nsgl.integer;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Store implements nsgl.io.Store{
    public void store(int x, OutputStream os) throws IOException {
	DataOutputStream dos = new DataOutputStream(os);
	dos.writeInt(x);
    }
    
    @Override
    public void store(Object obj, OutputStream os) throws IOException {	store((int)obj,os); }
}