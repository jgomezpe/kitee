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
package nsgl.stringify;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.nio.charset.Charset;

import nsgl.cast.CastServer;
import nsgl.generic.array.Array;
import nsgl.generic.collection.Indexed;
import nsgl.generic.collection.IndexedStringifier;

/**
 * <p>Title: Stringifiable</p>
 *
 * <p>Description: An object that can be Stringified (stored) into a String</p>
 *
 */
public interface Stringifyable {
	/** 
	 * Default charset used by NSGL
	 */
	static final String CHARSET = "UTF-8";

	/**
	 * Stringifies the object
	 * @return An stringified version of the object
	 */
	String stringify();
	
	/**
	 * Creates an InputStream for the object's Stringified version
	 * @return An InputStream for the object's Stringified version
	 */
	default InputStream is() { return new ByteArrayInputStream(stringify().getBytes(Charset.forName(CHARSET))); }
	
	/**
	 * Initializes default comparison methods
	 */
	static void init() {
		if( (Stringifier)CastServer.service(Object.class,Stringifyable.class) != null ) return;
		CastServer.setService(Object.class, Stringifyable.class, new DefaultStringifier());
		Stringifyable.addCast(Character.class, new nsgl.character.Stringifier()); 
		Stringifyable.addCast(String.class, new nsgl.string.Stringifier()); 
		Stringifyable.addCast(Array.class, new nsgl.generic.array.Stringifier());
		Stringifyable.addCast(Indexed.class, new IndexedStringifier());
	}	
	

	/**
	 * Obtains the associated stringifying method of the object
	 * @param obj Object that will get its stringifying method
	 * @return The associated stringifying method of the object
	 */
	static Stringifier stringifier(Object obj) {
		Stringifyable.init();
		Stringifier cast = (Stringifier)CastServer.service(obj, Stringifyable.class); 
		if( cast instanceof DefaultStringifier ) {
			try{
				Class<?> cl = obj.getClass();
				Method method = cl.getMethod("stringify");
				cast = new Stringifier() {
					@Override
					public String stringify(Object obj) {
						try{ return (String)method.invoke(obj); }catch(Exception e){}
						return obj.toString();
					}
				};				
				CastServer.setService(cl, Stringifyable.class, cast);
			}catch(NoSuchMethodException e){}			
		}
		return cast;
	}
	
	/**
	 * Cast an object to Stringifyable, if possible
	 * @param obj Object to be casted to Stringifyable
	 * @return A Stringifyable version of the given object, <i>null</i> otherwise
	 */
	static Stringifyable cast( Object obj ){
		if(obj==null) return null;
		if( obj instanceof Stringifyable ) return (Stringifyable)obj;
		Stringifier cast = stringifier(obj);
		if( cast != null ) return new Stringifyable() { @Override public String stringify(){ return cast.stringify(obj); }	};
		return null;
	}

	/**
	 * Adds an Stringifying method for the given object 
	 * @param caller Object that will register its stringifying method
	 * @param cast Casting method
	 */
	static void addCast( Object caller, Stringifier cast ){
		Stringifyable.init();
		CastServer.setService(caller, Stringifyable.class, cast); 
	}
}