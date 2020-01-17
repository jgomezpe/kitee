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
package nsgl.recover;

import java.io.IOException;

import nsgl.character.CharacterSequence;

/**
 * <p>Title: Recover</p>
 *
 * <p>Description: Abstract Recovering method (Load from a CharacterSequence) an Object</p>
 *
 */
public interface Recover {
	/**
	 * Recovers from a CharacterSequence an object
	 * @param input CharSequence from which the object will be recovered
	 * @return The recovered object
	 * @throws IOException If an object could not be recovered from the CharSequence 
	 */
	Object recover( CharacterSequence input ) throws IOException;
	
	/**
	 * 
	 * Recovers from a CharacterSequence an object
	 * @param target Target object of the recovering process (usually a class type)
	 * @param input CharSequence from which the object will be recovered
	 * @return The recovered object
	 * @throws IOException If an object could not be recovered from the CharSequence 
	 */
	static Object apply( Object target, CharacterSequence input ) throws IOException{
		return Recoverable.cast(target).recover(input);
	}
	
	/**
	 * 
	 * Recovers from a CharacterSequence an object
	 * @param target Target object of the recovering process (usually a class type)
	 * @param input String from which the object will be recovered
	 * @return The recovered object
	 * @throws IOException If an object could not be recovered from the String 
	 */
	static Object apply( Object target, String input ) throws IOException{ return Recover.apply(target,new CharacterSequence(input)); }	
}