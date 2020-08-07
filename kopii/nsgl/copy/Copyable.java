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
package nsgl.copy;

import java.lang.reflect.Array;
import java.lang.reflect.Method;

import nsgl.cast.CastServer;

/**
 * <p>Title: Cloneable</p>
 *
 * <p>Description: An object that can produce clones</p>
 *
 */
public interface Copyable {	
	/**
	 * Creates a clone of a given object
	 * @return A clone of the object
	 */
	Object copy();	
	
	/**
	 * Obtains a cloner method for the given object
	 * @param obj Object that will get its cloning method
	 * @return A cloner method for the given object
	 */
	static Copier copier( Object obj ){
		Copier cast = (Copier)CastServer.service(obj,Copyable.class);
		if( cast == null ){
			try{
				Class<?> cl = obj.getClass();
				Method method = cl.getMethod("clone");
				cast = new Copier() {
					@Override
					public Object copy(Object obj) {
						try{ return method.invoke(obj); }catch(Exception e){}
						return obj;
					}
				};
				CastServer.setService(cl, Copyable.class, cast);
			}catch(NoSuchMethodException e){}
		}
		return cast;
	}
	
	/**
	 * Cast an object to Cloneable, if possible
	 * @param obj Object to be casted to Cloneable
	 * @return A cloneable version of the given object.
	 */
	static Copyable cast( Object obj ){
		if(obj==null) return null;
		if( obj instanceof Copyable ) return (Copyable)obj;
		Class<?> c = obj.getClass();
		if( c.isArray() ) {
			return new Copyable() {
			    @Override
			    public Object copy() {
			    	int n = Array.getLength(obj);
				Object clone = Array.newInstance(c.getComponentType(), n);
				if( c.getComponentType().isPrimitive() )
				    System.arraycopy(obj, 0, clone, 0, n);
				else 
				    for( int i=0; i<n; i++ ) Array.set(clone, i, cast(Array.get(obj, i)).copy());
				return clone;
			    }
			};
		}
		if( c.isPrimitive() || obj instanceof Number || obj instanceof Character || obj instanceof String ) {
		    return new Copyable() {
		        @Override
		        public Object copy() {
		            return obj;
		        }
		    };
		}
		
		Copier cast = copier(obj);
		if( cast != null ) {
        		return new Copyable() {
        			@Override 
        			public Object copy(){ return cast.copy(obj); } 
        		};
		}
		
		return null;
	}
	
	/**
	 * Adds a Cloning method for the given object 
	 * @param caller Object that will register its cloning method
	 * @param cast Cloning method
	 */
	static void addCast( Object caller, Copier cast ){ CastServer.setService(caller, Copyable.class, cast); }	
}