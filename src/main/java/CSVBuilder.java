import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public class CSVBuilder implements ICSVBuilder{
    @Override
    public <E> Iterator<E> getFileIterator(Reader reader, Class<E> csvClass) throws CSVBuilderException {
        return this.getCSVBean(reader,csvClass).iterator();
    }
    @Override
    public <E> List<E> getFileList(Reader reader, Class<E> csvClass) throws CSVBuilderException {
        return this.getCSVBean(reader,csvClass).parse();
    }
        private <E> CsvToBean<E> getCSVBean(Reader reader, Class<E> csvClass) throws CSVBuilderException {
            try {
                CsvToBeanBuilder csvToBeanBuilder = new CsvToBeanBuilder(reader);
                csvToBeanBuilder.withType(csvClass);
                csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
                return csvToBeanBuilder.build();
            }
            catch (RuntimeException e) {
            throw new CSVBuilderException(e.getMessage(),StateCensusAnalyserException.exceptionType.INCORRECT_FILE);
        }
    }


}
