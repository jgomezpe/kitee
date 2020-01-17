package nsgl.integer.array;

import java.io.IOException;

import nsgl.iterator.Backable;


/**
 * <p>Integer array persistent method</p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 * 
 * @author Jonatan Gomez Perdomo
 * @version 1.0
 */

public class PlainRead implements Read{
	protected boolean read_dimension = true;
	protected char separator = ' ';
	protected int n=-1;
	protected nsgl.integer.Read ri;
	
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
	
	public void setReadIn(nsgl.integer.Read ri){ this.ri = ri; }
	
   /**
     * Reads an array from the input stream (the first value is the array's size and the following values are the values in the array)
     * @param reader The reader object
     * @throws ParamsException ParamsException
     */
    public int[] read(Backable<Integer> reader) throws IOException{
    	nsgl.integer.Read ri = (this.ri==null)?this.ri:(nsgl.integer.Read)nsgl.io.Read.reader(Integer.class);
        if( read_dimension ){
        	n = ri.read(reader);
        	nsgl.io.Read.readSeparator(reader, separator);        	
        }
        int[] a = new int[n];
        for (int i = 0; i < n-1; i++) {
            a[i] = ri.read(reader);
            nsgl.io.Read.readSeparator(reader, separator);        	
        }
        if( n-1 >= 0 ) a[n-1] = ri.read(reader);
        return a;
    }   
}