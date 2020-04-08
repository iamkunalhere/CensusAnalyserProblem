import java.io.Reader;
import java.util.Iterator;

public interface ICSVBuilder {
    public <E> Iterator<E> getFileIterator(Reader reader, Class<E> csvClass) throws CSVBuilderException;
}

