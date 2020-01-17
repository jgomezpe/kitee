package nsgl.io;

import java.io.IOException;
import java.io.Writer;

import nsgl.stringify.Stringifier;

public class WriteWrap implements Write{
	@Override
	public void write(Object obj, Writer writer) throws IOException {
		writer.write(Stringifier.apply(obj));
	}
}