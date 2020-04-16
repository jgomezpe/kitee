package nsgl.io;

import java.io.Closeable;
import java.util.Iterator;

import nsgl.generic.Collection;
import nsgl.iterator.BTWrap;
import nsgl.iterator.IteratorPosition;

public abstract class Reader implements Collection<Integer>, Closeable{
	/**
	 * Linefeed character
	 */
	public static int LINEFEED = (int) '\n';
	/**
	 * Carriage return character
	 */
	public static int CARRIAGERETURN = (int) '\r';
	
	public static final int ROW = 1; 
	public static final int COL = 2; 
	
	protected boolean closed = false;
	protected int c;
	protected int src;
	
	public Reader(){ this(0); }
	public Reader(int src){ this.src=src; }
	
	public int src(){ return src; }
	public void setSrc( int src ){ this.src = src; }	
	
	protected Iterator<Integer> iter = new  Iterator<Integer>(){
		@Override
		public boolean hasNext() { return c!=-1; }

		@Override
		public Integer next() {
			int t = c;
			c=get();
			return t;
		}		
	};
	
	protected class ReaderIterator extends BTWrap<Integer>{
		public ReaderIterator(Iterator<Integer> iter) {
			super(iter);
			extra[0]=new IteratorPosition(src(),new int[3]);
		}

		@Override
		protected IteratorPosition pos(Integer c) {
			IteratorPosition p = (IteratorPosition)extra[(n+pos-1)%n];
			if (c == CARRIAGERETURN || (c == LINEFEED && (int)data [pos] != CARRIAGERETURN)) {
				return new IteratorPosition(src(), new int[]{p.pos()[ROW] + 1, 0});
			}else{
				int row = p.pos()[ROW];
				int col = p.pos()[COL];
				if (c != LINEFEED) col++;
				return new IteratorPosition(src(),new int[] {row,col} );
			}		
		}
	}

	@Override
	public Iterator<Integer> iterator() {
		return new ReaderIterator(iter);
	}
		
	@Override
	public boolean isEmpty() { return closed; }
	
	public abstract int get(); 
}