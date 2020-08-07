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

import java.io.IOException;

import nsgl.parse.Regex;

/**
 * <p>Title: CharRecover</p>
 *
 * <p>Description: Recovers (Load from a String) a Character</p>
 *
 */
public class Parse extends Regex{
	public static final String TAG = "char";

	/**
	 * Creates a Char recover method 
	 */
	public Parse(){ super("'([^\\\\\"\n\t\r]|\\\\[bfnrt0'\\\\\\\\]|\\\\u[0-9A-F]{4})'" , TAG); }

	/**
	 * Obtains the character associated with a escape character 
	 * @param c Escape character
	 * @return The character associated with a escape character
	 */
	public static char escape( char c ){
		switch( c ){
			case 'b': return '\b';
			case 'f': return '\f';
			case 'n': return '\n';
			case 'r': return '\r';
			case 't': return '\t';
			case '0': return '\0';
			default: return c; 
		}
	}

	/**
	 * Obtains the characters processed by the Char recover method
	 * @param matched String from which the character will be recovered
	 * @param pos Starting position for loading the character
	 * @return Set of characters processed by the Char recover method
	 * @throws IOException If the process finds an error in the process (bad formed escape character)
	 */
	public static char[] get( CharacterSequence input, int pos, boolean single ) throws IOException{
		char c = input.charAt(pos);
		if(c=='\\'){
			pos++;
			c = input.charAt(pos);
			char ec = escape(c);
			if( ec!=c ) return new char[]{ec, '2'};
			if( c=='"' && !single ) return new char[]{'"', '2'};
			if( c=='\'' && single ) return new char[]{'\'', '2'};
			if( c=='\\' ) return new char[]{'\\', '2'};
			if(c=='u'){
				pos++;
				String uc = input.subSequence(pos,pos+4).toString();
				int k=Integer.parseInt(uc,16);
				c = (char)k;
				return new char[]{c,'6'};
			}
			throw input.exception("No escape char", pos);
		}
		return new char[] {c,'1'};
	}

	@Override
	protected Object instance(CharacterSequence input, String matched) throws IOException{
		char[] c = get( input, 1, true );
		return c[0];
	}
	
	public static void main( String[] args ) {
	    Parse p = new Parse();
	    CharacterSequence txt = new CharacterSequence("'\\''");
	    try {
		Object c = p.parse(txt);
		System.out.println(c);
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
}