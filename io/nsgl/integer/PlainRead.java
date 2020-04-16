/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nsgl.integer;

import java.io.IOException;

import nsgl.exception.IO;
import nsgl.iterator.Backable;
import nsgl.iterator.Trackable;

/**
 *
 * @author jgomez
 */
public class PlainRead implements Read{
	
	public static void readDigitStar( Backable<Integer> reader, StringBuilder sb ){
		boolean flag = true;
		while( reader.hasNext() && flag ){
			int c = reader.next();
			flag = Character.isDigit(c);
			if( flag ) sb.append((char)c);
		}
		if( !flag ) reader.back();
	}

	public static void removeSpaces( Backable<Integer> reader ){
		boolean flag = true;
		while( reader.hasNext() && flag ){ flag = Character.isSpaceChar(reader.next()); }
		if( !flag ) reader.back();
	}

	@SuppressWarnings("unchecked")
	public static String number( Backable<Integer> reader ) throws IOException{
		if( reader.hasNext() ){
			StringBuilder sb = new StringBuilder();
			int c = reader.next();
			if( c=='-' || c=='+' ){
				sb.append((char)c);
				if( !reader.hasNext() ) throw IO.exception(IO.EOI);
				c = reader.next();
			}	
			if( Character.isDigit(c) ){ 
				sb.append((char)c);
				readDigitStar(reader, sb);
				return sb.toString();
			}
			if( reader instanceof Trackable ){
				int[] pos = ((Trackable<Integer>)reader).pos().pos();
				Object[] args = new Object[pos.length+1];
				for( int i=0; i<pos.length; i++) args[i] = pos[i];
				args[pos.length] = (char)c;
				throw IO.exception(IO.UNEXPECTED, args);
			}else throw IO.exception(IO.UNEXPECTED, (char)c);
		}		
		throw IO.exception(IO.EOI);
	}
	
	@Override
	public Integer read(Backable<Integer> reader) throws IOException{
		removeSpaces(reader);
		return Integer.parseInt(number(reader));
    }
	
	@Override
	public String toString(){ return "IntegerPlainRead"; }		
}
