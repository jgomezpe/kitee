package nsgl.real.array;

import java.io.IOException;
import java.io.Writer;

public interface Write extends nsgl.io.Write{
	public void write(double[] obj, Writer out) throws IOException; 
	default void write(Object obj, Writer out) throws IOException{ write((double[])obj,out); } 
}