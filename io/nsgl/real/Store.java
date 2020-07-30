package nsgl.real;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Store implements nsgl.io.Store{
    public void store(double x, OutputStream os) throws IOException {
	DataOutputStream dos = new DataOutputStream(os);
	dos.writeDouble(x);
    }
    
    @Override
    public void store(Object obj, OutputStream os) throws IOException {	store((double)obj,os); }
}
