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
package nsgl.string;

import java.io.IOException;

import nsgl.character.CharacterSequence;
import nsgl.parse.Regex;

/**
 * <p>Title: StringRecover</p>
 *
 * <p>Description: Recovers (Load from a String) an String</p>
 *
 */
public class Parse extends Regex{
	public static final String TAG = "String";

	/**
	 * Creates a recovering method for strings
	 */
	public Parse() { super("\"([^\t\n\r\f\"\\\\]*(\\\\[tnrfu\"\\\\])*)*\"", TAG); }

	@Override
	public Object instance(CharacterSequence input, String matched) throws IOException{
		if( matched.charAt(0) != '"' ) throw input.exception("·Invalid "+TAG+"· ", 0);
		if( matched.charAt(matched.length()-1) != '"') throw input.exception("·Invalid "+TAG+"· ", matched.length()-1);
		StringBuilder sb = new StringBuilder();
		int n = matched.length()-1;
		int pos = 1;
		while(pos<n) {
			sb.append( nsgl.character.Parse.get(input, matched, pos, false) );
			pos += nsgl.character.Parse.length;
		}
		return sb.toString();
	}	
}