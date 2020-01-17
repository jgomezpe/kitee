/**
 * <p>Copyright: Copyright (c) 2019</p>
 *
 * <h3>License</h3>
 *
 * Copyright (c) 2019 by Jonatan Gomez-Perdomo. <br>
 * All rights reserved. <br>
 *
 * <p>Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * <ul>
 * <li> Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * <li> Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * <li> Neither the name of the copyright owners, their employers, nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * </ul>
 * <p>THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 *
 *
 * @author <A HREF="http://disi.unal.edu.co/profesores/jgomezpe"> Jonatan Gomez-Perdomo </A>
 * (E-mail: <A HREF="mailto:jgomezpe@unal.edu.co">jgomezpe@unal.edu.co</A> )
 * @version 1.0
 */
package nsgl.character;

import nsgl.integer.array.DynArray;

/**
 * <p>Title: CharacterSequence</p>
 *
 * <p>Description: Special CharSequence used for the nsgl architecture</p>
 *
 */
public class CharacterSequence implements CharSequence{
	/**
	 * Initial position  of the CharSequence in the inner CharSequence
	 */
	protected int loc;
	/**
	 * Inner CharSequence
	 */
	protected CharSequence str;

	/**
	 * <p>Title: Rows</p>
	 *
	 * <p>Description: Helper class for tracking the position (row,column) of characters in the CharSequence</p>
	 *
	 */
	protected class Rows extends DynArray{
		/**
		 * Gets the absolute position of the last found row 
		 * @return Absolute position of the last found row
		 */
		public int last() { return get(size-1); }
		
		/**
		 * Gets the position (row,column) of the character at the given index (relative to the location of the CharSequence)
		 * @param index Character position to be located (relative to the location of the CharSequence)
		 * @return Position (row,column) of the character at the given index position (relative to the location of the CharSequence)
		 */
		public int[] pos( int index ) {
			int a=0;
			int b=size-1;
			while(a+1<b && get(a)<index && index<get(b)){
				int m = (a+b)/2;
				if(index==get(m)) a=b=m;
				else if(index<get(m)) b=m-1;
				else a=m+1;
			}
			if( get(b)<=index ) return new int[]{b, index-get(b)};
			return new int[]{a, index-get(a)};
		}
		
		/**
		 * Creates the helper array for tracking row start positions
		 */
		public Rows(){ super(); add(0); }
	}; 
	
	/**
	 * Array with the absolute row start positions  
	 */
	protected Rows rows = new Rows(); 
	
	/**
	 * Creates a CharacterSequence over the given CharSequence
	 * @param str CharSequence that will be wrapped by the CharSequence
	 */
	public CharacterSequence(CharSequence str){ this.str = str; }
	
	/**
	 * Computes the rows information up to the given character absolute index 
	 * @param upto Absolute index indicating up to which position will be the row information computed 
	 */
	protected void checkpos( int upto ) {
		for( int i=Math.max(loc, rows.last()); i<upto; i++ ) if(str.charAt(i)=='\n') rows.add(i+1);
	}
	
	/**
	 * Moves the relative start position of the CharSequence
	 * @param delta Amount of characters the CharSequence will be displaced 
	 * @return The updated start position of the CharSequence
	 */
	public int shift( int delta ){
		checkpos(loc+delta);
		loc += delta;
		if(loc<0) loc=0;
		return loc;
	}
	
	/**
	 * Returns the current start position of the CharSequences
	 * @return Current start position of the CharSequences
	 */
	public int loc() { return loc; }

	@Override
	public int length(){ return str.length()-loc; }

	@Override
	public char charAt(int index) {
		checkpos(loc+index);
		return str.charAt(index+loc); 
	}
	
	/**
	 * Resets the start position to zero
	 */
	public void clear() { loc=0; }
	
	/**
	 * Obtains the relative position (row,column) of the given character index location
	 * @param index Character index location to be analyzed
	 * @return The relative position (row,column) of the given character index location
	 */
	public int[] pos( int index ) {
		int[] pos = absolute_pos(index);
		int[] locpos = absolute_pos(0);
		if( pos[0] > locpos[0] ) pos[0] -= locpos[0];
		else if( pos[0] == locpos[0] ){
			pos[0] = 0; 
			if( pos[1] > locpos[1] ) pos[1] -= locpos[1];
			else pos[1] = 0;
		}else{
			pos[0] = pos[1] = 0;
		}
		return pos;
	}

	/**
	 * Obtains the absolute position (row,column) of the given character index location
	 * @param index Character index location to be analyzed
	 * @return The absolute position (row,column) of the given character index location
	 */
	public int[] absolute_pos( int index ) {
		int p = loc+index;
		checkpos(p);
		return rows.pos(p);
	}


	@Override
	public CharSequence subSequence(int start, int end){
		checkpos(loc+end);
		return str.subSequence(start+loc, end+loc); 
	}
	
	@Override
	public String toString() { return str.subSequence(loc, str.length()).toString(); }
}