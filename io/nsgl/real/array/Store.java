package nsgl.real.array;

import java.io.IOException;
import java.io.OutputStream;

import nsgl.io.Storable;

public class Store implements nsgl.io.Store{
    public void store(double[] x, OutputStream os) throws IOException {
	Storable.cast(x.length).store(os);
	for( int i=0; i<x.length; i++ ) {
		Storable.cast(x[i]).store(os);
	}
    }
    
    @Override
    public void store(Object obj, OutputStream os) throws IOException {	store((double[])obj,os); }
}
