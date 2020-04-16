package nsgl.real.matrix;


import java.io.IOException;

import nsgl.iterator.Backable;

public class PlainRead implements Read{
	public static final String integer = "Read.integer";
	public static final String real = "Read.double";

	protected boolean read_dimension = true;
	
	protected int n;
	protected int m;

	protected nsgl.integer.PlainRead ri = new nsgl.integer.PlainRead();
	protected nsgl.real.PlainRead rr = new nsgl.real.PlainRead();
	
	/**
	 * Character used for separating the values in the array
	 */
	protected char separator = ' ';

	/**
	 * Creates an integer array persistent method that uses an space for separatng the array values
	 */
	public PlainRead(){ this(' '); }

	/**
	 * Creates a double matrix persistent method that uses the give charater for separating the matrix values
	 * @param separator Character used for separating the matrix values
	 */
	public PlainRead(char separator) { this.separator = separator; }

	/**
	 * Creates a double matrix persistent method that uses the give charater for separating the matrix values
	 * @param separator Character used for separating the matrix values
	 */
	public PlainRead(int n, int m) { this(n,m,' '); }

	/**
	 * Creates a double matrix persistent method that uses the give charater for separating the matrix values
	 * @param separator Character used for separating the matrix values
	 */
	public PlainRead(int n, int m, char separator) {
		this.separator = separator;
		this.read_dimension = false;
		this.n=n;
		this.m=m;
	}

	public void setReadInt(nsgl.integer.PlainRead ri ){ this.ri = ri; }
	public void setReadDouble( nsgl.real.PlainRead rr ){ this.rr = rr; }
	
	/**
	 * Reads an array from the input stream (the first value is the array's size and the following values are the values in the array)
	 * @param reader The reader object
	 * @throws IOException IOException
	 */
	public double[][] read(Backable<Integer> reader) throws IOException {
    	nsgl.integer.Read ri = (this.ri==null)?this.ri:(nsgl.integer.Read)nsgl.io.Read.reader(Integer.class);
    	nsgl.real.Read rr = (this.rr==null)?this.rr:(nsgl.real.Read)nsgl.io.Read.reader(Double.class);
		if( read_dimension ){
			n = ri.read(reader);
			nsgl.io.Read.readSeparator(reader, separator);
			m = ri.read(reader);
			nsgl.io.Read.readSeparator(reader, separator);            
		}
		double[][] a = new double[n][m];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++)
				a[i][j] = rr.read(reader);
		return a;
	}
	
	@Override
	public String toString(){ return "DoubleMatrixPlainRead"; }	
}