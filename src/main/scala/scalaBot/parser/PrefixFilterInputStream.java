package scalaBot.parser;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Vector;

public abstract class PrefixFilterInputStream extends InputStream {

    private final BufferedReader input;
    private final Charset encoding = StandardCharsets.UTF_8;
    private ByteArrayInputStream buffer;

    public PrefixFilterInputStream(InputStream is) throws IOException {
        input = new BufferedReader(new InputStreamReader(is, this.encoding));
        nextLine();
    }

    @Override
    public int read() throws IOException {
        if (buffer == null) {
            return -1;
        }
        int ch = buffer.read();
        if (ch == -1) {
            if (!nextLine()) {
                return -1;
            }
            return read();
        }
        return ch;
    }

    private boolean nextLine() throws IOException {
        String line;
        DoWithLines cl;
        while ((line = input.readLine()) != null) {
            if ((cl = getClassDoWithLines(line)) != null) { // sure, !excludeLine(line) && input.readLine() != null
                Vector<String> listRes = new Vector<>();
                listRes.add(line);
                input.mark(1);
                while (cl.takeLine(line = input.readLine())) {
                    listRes.add(line);
                    input.mark(1);
                }
                input.reset();
                line = cl.doWithLines(listRes);
                buffer = new ByteArrayInputStream(line.getBytes(encoding));
                return true;
            }
            if (!excludeLine(line)) {
                line += '\n';
                buffer = new ByteArrayInputStream(line.getBytes(encoding));
                return true;
            }
        }
        return false;
    }

    @Override
    public void close() throws IOException {
        input.close();
    }

    abstract boolean excludeLine(String line);

    abstract DoWithLines getClassDoWithLines(String line);
}

abstract class DoWithLines {
    boolean takeLine(String line) {
        return line.startsWith(" ");
    }

    abstract String doWithLines(List<String> listRes);
}
