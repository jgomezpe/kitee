package nsgl.trace;

public class Count implements Tracer{
	protected int count=0;
	
	@Override
	public void add(Object caller, Object... obj) { count++; }

	@Override
	public void clear() { count=0; }

	@Override
	public void close() {}

	@Override
	public Object get() { return count; }
}