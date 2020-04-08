import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public interface ICSVBuilder {
    public <E> Iterator<E> getFileIterator(Reader reader, Class<E> csvClass) throws CSVBuilderException;
    public  <E> List<E> getFileList(Reader reader, Class<E> csvClass) throws CSVBuilderException;

}

