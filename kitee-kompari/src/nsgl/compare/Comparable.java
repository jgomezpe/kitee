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
package nsgl.compare;

import java.lang.reflect.Method;

import nsgl.cast.CastServer;
import nsgl.generic.Comparator;
import nsgl.generic.collection.Indexed;
import nsgl.generic.collection.IndexedComparator;

/**
 * <p>Title: Comparable</p>
 *
 * <p>Description: An object that can be compared (if equals or not) to other object</p>
 *
 */
public interface Comparable {
	/**
     * Determines if the object is equal to object two
     * @param two The second object to compare
     * @return (one==two)
     */
    boolean eq(Object two);

    /**
     * Determines if the object is equal to object two
     * @param two The second object to compare
     * @return (one==two)
     */
    default boolean ne(Object two){ return !eq(two); }   
    
	/**
	 * Initializes default comparison methods
	 */
	static void init() {
		if( CastServer.service(Object.class,Comparable.class) != null ) return;
		Comparable.addCast(Object.class, new DefaultComparator() );
		Comparable.addCast(Indexed.class, new IndexedComparator());
	}    

	/**
	 * Obtains a comparator method for the given object
	 * @param obj Object that will get its comparator method
	 * @return A comparator method for the given object
	 */
	static Comparator comparator( Object obj ){
		Comparable.init();
		Comparator cast = (Comparator)CastServer.service(obj,Comparable.class);
		if( cast instanceof DefaultComparator ) {
			try{
				Class<?> cl = obj.getClass();
				Method method = cl.getMethod("eq");
				cast = new Comparator() {
					@Override
					public boolean eq(Object one, Object two) {
						try{ return (boolean)method.invoke(one, two); }catch(Exception e){}
						return false;
					}
				};
				CastServer.setService(cl, Comparable.class, cast);
			}catch(NoSuchMethodException e){}			
		}
		return cast;
	}
	
    /**
	 * Cast an object to Comparable, if possible
	 * @param obj Object to be casted to Comparable
	 * @return A comparable version of the given object, <i>null</i> otherwise
	 */
	static Comparable cast( Object obj ){
		if(obj==null) return null;
		if( obj instanceof Comparable ) return (Comparable)obj;
		Comparator cast = comparator(obj);
		return new Comparable(){ @Override public boolean eq(Object x){ return cast.eq(obj,x); } };
	}
	
	/**
	 * Adds a Comparable casting method for the given object 
	 * @param caller Object that will register its casting method
	 * @param cast Casting method
	 */
	static void addCast( Object caller, Comparator cast ){ CastServer.setService(caller, Comparable.class, cast); }	
}