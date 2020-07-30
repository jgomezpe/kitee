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
package nsgl.cast;

import java.util.HashMap;

/**
 * <p>Title: Cast</p>
 *
 * <p>Description: A casting operation from some object to a given class . Can register methods (classes) for doing that.</p>
 *
 */
public class CastServer{
	/**
	 * A message code for indicating that it was not possible to cast an object to a given type.
	 */
	public static final String NOCAST = "NOCAST";

	/**
	 * Collection of casting operations  
	 */
	protected static HashMap<Class<?>, HashMap<Object,Object>> pool = new HashMap<Class<?>, HashMap<Object,Object>>();

	/**
	 * Associates the casting process when trying to cast the given object to the given class 
	 * @param caller Object that will use the casting process (may be a Class)
	 * @param target Target class of the casting process
	 * @param service Casting process
	 */
	public static void setService( Object caller, Class<?> target, Object service ){
		HashMap<Object, Object> map = pool.get(target);
		if( map == null ){
			map = new HashMap<Object, Object>();
			pool.put(target, map);
		}
		map.put(caller, service);
		Class<?> cl = target.getSuperclass(); 
		if( cl != null && cl!=Object.class ) setService(caller,cl, service);
		Class<?>[] superTypes = target.getInterfaces();
		for( Class<?> c:superTypes ) setService(caller, c, service );
	}
	
	/**
	 * Gets the operation for casting object <i>obj</i> to an object of class <i>target</i>  
	 * @param obj Object to be casted to an object of class <i>target</i>
	 * @param target  Class to which the object will be casted
	 * @return The casting operation for object <i>obj</i> to an object of class <i>target</i>, <i>null</i> if there is not casting operation
	 */
	public static Object service( Object obj, Class<?> target ){
		HashMap<Object, Object> set = pool.get(target);
		if( set != null ){
			Object cast = set.get(obj); 
			if( cast != null ) return cast; 
			else return service( obj.getClass(), set );
		}
		return null;
	}

	/**
	 * Gets the casting operation from class <i>src</i> to class <i>target</i>  
	 * @param src Source class of the casting process
	 * @param target Associated casting operators for the target class
	 * @return The casting operation from class <i>src</i> to class <i>target</i>, <i>null</i> if there is not casting operation
	 */
	protected static Object service( Class<?> src, HashMap<Object, Object> target ){
		if( src==null ) return null; 
		Object srv = target.get(src);
		if( srv != null ) return srv;
		srv = service( src.getSuperclass(), target);
		if( srv != null ) return srv;
		Class<?>[] superTypes = src.getInterfaces();
		for( Class<?> c:superTypes ){
			srv = service(c, target );
			if( srv != null ) return srv;
		}
		return null;
	}	
}