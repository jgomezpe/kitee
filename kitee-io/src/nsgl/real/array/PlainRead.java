/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nsgl.real.array;

import java.io.IOException;

import nsgl.iterator.Backable;

/**
 *
 * @author jgomez
 */
public class PlainRead implements Read{
	protected boolean read_dimension = true;
	protected char separator = ' ';
	protected int n=-1;
	protected nsgl.integer.PlainRead ri = new nsgl.integer.PlainRead();
	protected nsgl.real.PlainRead rr = new nsgl.real.PlainRead();
	
	public PlainRead(){}
	
	public PlainRead( char separator ){
		this.separator = separator;
	}
	
	public PlainRead( int n ){
		this.n = n;
		read_dimension = (n <=0 );
	}
	
	public PlainRead( int n, char separator ){
		this.n = n;
		this.separator = separator;
		read_dimension = (n <=0 );
	}

	public void setReadInt(nsgl.integer.PlainRead ri ){ this.ri = ri; }
	public void setReadDouble( nsgl.real.PlainRead rr ){ this.rr = rr; }
	
    @Override
    public double[] read( Backable<Integer> reader ) throws IOException{
    	nsgl.integer.Read ri = (this.ri==null)?this.ri:(nsgl.integer.Read)nsgl.io.Read.reader(Integer.class);
    	nsgl.real.Read rr = (this.rr==null)?this.rr:(nsgl.real.Read)nsgl.io.Read.reader(Double.class);
        if( read_dimension ){
        	n = ri.read(reader);
        	nsgl.io.Read.readSeparator(reader, separator);        	
        }
		double[] a = new double[n];
        for (int i = 0; i < n-1; i++) {
            a[i] = rr.read(reader);
            nsgl.io.Read.readSeparator(reader, separator);        	
        }
        if( n-1 >= 0 ) a[n-1] = rr.read(reader);
        return a;
    }
    
	@Override
	public String toString(){ return "DoubleArrayPlainRead"; }    
}