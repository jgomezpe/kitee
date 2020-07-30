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

import java.util.Iterator;

/**
 * <p>Title: CastIterable</p>
 *
 * <p>Description: A collection that contains casted version (to type &lt;T&gt;) of objects (from type &lt;S&gt;) in another collection.</p>
 *
 */
public abstract class CastIterable<S,T> implements Iterable<T>{
	/**
	 * Original collection
	 */
	protected Iterable<S> col=null;
	
	/**
	 * Creates a collection with casted versions of the objects in the given collection
	 * @param col Collection to be transformed
	 */
	public CastIterable( Iterable<S> col ){ this.col = col; }
	
	/**
	 * Cast operation
	 * @param x Object to be casted from class &lt;S&gt; to class &lt;T&gt;
	 * @return A casted version of the given object
	 */
	protected abstract T cast( S x );
    
	/**
	 * Gets an iterator over the casted objects
	 * @return An iterator of casted versions of the original collection
	 */
	@Override
	public Iterator<T> iterator(){ 
		Iterator<S> iter = col.iterator();
		return new Iterator<T>() {
			@Override
			public boolean hasNext(){ return iter.hasNext(); }
			
			@Override
			public T next(){ return cast(iter.next()); }			
		};
	}
}