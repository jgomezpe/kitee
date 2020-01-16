package nsgl.real;

/**
 * <p>Compares to Doubles</p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 * 
 * @author Jonatan Gomez Perdomo
 * @version 1.0
 */

public class L2HOrder implements Order {
	   /**
     * Determines if the first Integer is less than (in some order) the second Integer (one<two)
     * @param one First Integer
     * @param two Second Integer
     * @return (one<two)
     */
    public int compare(Double one, Double two){ return one.compareTo(two); }

    /**
     * Determines if the first Integer is less than (in some order) the second Integer (one<two)
     * @param one First Integer
     * @param two Second Integer
     * @return (one<two)
     */
	@Override
    public int compare(Object one, Object two){ return compare((Double)one,(Double)two); }
}