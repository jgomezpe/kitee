package nsgl.string;

public class Order implements nsgl.order.Order{

	public int compare(String one, String two) { return one.compareTo(two); }

	@Override
	public int compare(Object one, Object two) { return compare((String)one, (String)two); }

}
