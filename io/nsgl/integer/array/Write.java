package nsgl.integer.array;

import java.io.IOException;
import java.io.Writer;

public interface Write extends nsgl.io.Write{
    /**
     * Writes an array to the given writer (writes the size and the values) using the associated separator char
     * @param obj array to write
     * @param out The writer object
     * @throws ParamsException ParamsException
     */
    public void write(int[] obj, Writer out) throws IOException;
    
    default void write(Object obj, Writer out ) throws IOException{ write( (int[])obj, out); }
}