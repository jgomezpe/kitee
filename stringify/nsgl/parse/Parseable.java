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
package nsgl.parse;

import java.io.IOException;

import nsgl.cast.CastServer;
import nsgl.character.CharacterSequence;

/**
 * <p>Title: Recoverable</p>
 *
 * <p>Description: An object that can be recovered (load) from a CharSequence</p>
 *
 */
public interface Parseable {
	/**
	 * Recovers an object from an String
	 * @param input String used for recovering the object
	 * @return The recovered object
	 * @throws IOException If an object could not be recovered from the String 
	 */
	default Object parse(String input) throws IOException{ return parse(new CharacterSequence(input)); }

	/**
	 * Recovers an object from a CharacterSequence
	 * @param input CharacterSequence used for recovering the object
	 * @return The recovered object
	 * @throws IOException If an object could not be recovered from the CharacterSequence 
	 */
	Object parse(CharacterSequence input) throws IOException;
	
	/**
	 * Gets the recovering method associated to the given class 
	 * @param cl Class that will get its recovering method
	 * @return The recovering method associated to the given class, <i>null</i> otherwise
	 */
	static void init() {
		if( CastServer.service(String.class,Parseable.class) != null ) return;
		CastServer.setService(String.class, Parseable.class, new nsgl.string.Parse() ); 
		Parseable.addCast(Character.class, new nsgl.character.Parse()); 
		Parseable.addCast(Integer.class, new nsgl.integer.Parse()); 
		Parseable.addCast(Double.class, new nsgl.real.Parse());
		Parseable.addCast(byte[].class, new nsgl.blob.Parse());
	}
	
	/**
	 * Obtains the Recovering method associated to the given object 
	 * @param obj Object that will get its recovering method
	 * @return The recovering method of the given object, <i>null</i> otherwise 
	 */
	static Parse recoverer(Class<?> obj) {
		Parseable.init();
		return (Parse)CastServer.service(obj,Parseable.class);
	}
	
	/**
	 * Obtains the Recovering method associated to the given object 
	 * @param obj Object that will get its recovering method
	 * @return The recovering method of the given object, <i>null</i> otherwise 
	 */
	static Parse recoverer(Object obj) {
		Parseable.init();
		Parse cast = (Parse)CastServer.service(obj,Parseable.class);
		if( cast == null ) cast = Parseable.recoverer((obj.getClass()==Class.class)?(Class<?>)obj:obj.getClass());
		return cast;
	}
	
	/**
	 * Cast an object to Recoverable, if possible
	 * @param obj Object to be casted to Recoverable
	 * @return A Recoverable version of the given object, <i>null</i> otherwise
	 */
	static Parseable cast( Object obj ){
		if(obj==null) return null;
		if( obj instanceof Parseable ) return (Parseable)obj;
		Parse cast = recoverer(obj);
		if( cast==null ) return null;
		return new Parseable() {
			@Override 
			public Object parse(CharacterSequence input) throws IOException{ return cast.parse(input); } 
		};	
	}
	
	/**
	 * Adds an Stringifying method for the given object 
	 * @param caller Object that will register its stringifying method
	 * @param cast Casting method
	 */
	static void addCast( Object caller, Parse cast ){
		Parseable.init();
		CastServer.setService(caller, Parseable.class, cast); 
	}	
}