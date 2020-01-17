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

import nsgl.exception.IO;
import nsgl.recover.RegexRecover;

/**
 * <p>Title: CharRecover</p>
 *
 * <p>Description: Recovers (Load from a String) a Character</p>
 *
 */
public class Recover extends RegexRecover{
	protected static final String quotation = "'";
	protected static final String other = "[^\\\\"+quotation+"]";
	protected static final String hexa = "[0-9A-F]";
	protected static final String hexacode = hexa+"{4}";
	protected static final String unicode = "u"+hexacode;
	protected static final String code = "[\\\\"+quotation+"/bfnrt]";
	protected static final String escape = "\\\\("+code+"|"+unicode+")";
	protected static final String any = "(" + other + "|" + escape + ")";

	/**
	 * Creates a Char recover method 
	 */
	public Recover(){ super(quotation+any+quotation); }

	/**
	 * Obtains the character associated with a escape character 
	 * @param c Escape character
	 * @return The character associated with a escape character
	 */
	public static char escape( char c ){
		switch( c ){
			case '\'': case '\\': case '/': return c;
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
	 * @param input String from which the character will be recovered
	 * @param pos Starting position for loading the character
	 * @return Set of characters processed by the Char recover method
	 * @throws IOException If the process finds an error in the process (bad formed escape character)
	 */
	public static char[] get( String input, int pos ) throws IOException{
		char c = input.charAt(pos);
		if(c=='\\'){
			pos++;
			c = input.charAt(pos);
			char ec = escape(c);
			if( ec!=c ) return new char[]{ec, '2'};
			if( c=='"' ) return new char[]{'"', '2'};
			if( c=='\'' ) return new char[]{'\'', '2'};
			if(c=='u'){
				pos++;
				String uc = input.substring(pos,pos+4);
				int k=Integer.parseInt(uc,16);
				c = (char)k;
				return new char[]{c,'6'};
			}
			throw IO.exception(IO.NOESCAPE, c, pos);
		}
		return new char[] {c,'1'};
	}

	@Override
	public Object instance(String input) throws IOException{
		if( input.length()<3 || input.charAt(0)!='\'' || input.charAt(input.length()-1)!='\'' ) throw IO.exception(IO.NOCHAR, input);
		input = input.substring(1, input.length()-1);
		char[] c = get( input, 0 );
		return c[0];
	}
}