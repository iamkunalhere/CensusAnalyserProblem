import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;

public class CSVBuilder implements ICSVBuilder{
    public <E> Iterator<E> getFileIterator(Reader reader, Class<E> csvClass) throws CSVBuilderException {
        try {
            CsvToBeanBuilder csvToBeanBuilder = new CsvToBeanBuilder(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean csvToBean = csvToBeanBuilder.build();
            return csvToBean.iterator();
        }
        catch (RuntimeException e) {
            throw new CSVBuilderException(e.getMessage(),StateCensusAnalyserException.exceptionType.INCORRECT_FILE);
        }
    }
}
