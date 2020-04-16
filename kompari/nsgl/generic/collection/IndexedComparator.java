package nsgl.generic.collection;

import nsgl.compare.Comparable;
import nsgl.compare.Comparator;
import nsgl.generic.Sized;
import nsgl.generic.collection.Indexed;

public class IndexedComparator  implements Comparator{
	public boolean eq(Indexed<?,?> one, Indexed<?,?> two) {
		if( one instanceof Sized ) {
			int n = ((Sized)one).size();
			int m = ((Sized)two).size();
			if( n!=m ) return false;
			for( Object obj : one ) if( !Comparable.cast(one.obtain(obj)).eq(two.obtain(obj)) ) return false;
			return true;
		}else {	
			for( Object obj : one ) if( !Comparable.cast(one.obtain(obj)).eq(two.obtain(obj)) ) return false; 
			for( Object obj : two ) if( !Comparable.cast(two.obtain(obj)).eq(one.obtain(obj)) ) return false; 
			return true;
		}
	}
	
	@Override
	public boolean eq(Object one, Object two) {
		if( one==two ) return true;
		if( one instanceof Indexed ) return eq((Indexed<?,?>)one, (Indexed<?,?>)two);
		return false;
	}
}
