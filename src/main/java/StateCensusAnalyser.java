import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

import static java.nio.file.Files.newBufferedReader;

public class StateCensusAnalyser {

    public int loadIndianCensusData(String csvFilePath) throws StateCensusAnalyserException {
        try (Reader reader = newBufferedReader(Paths.get(csvFilePath));) {
            Iterator<CSVStateCensusPojo> csvStateCensusIterator = this.getFileIterator(reader,CSVStateCensusPojo.class);
            return this.getCount(csvStateCensusIterator);
        } catch (IOException e) {
            throw new StateCensusAnalyserException(StateCensusAnalyserException.exceptionType.FILE_NOT_FOUND);
        } catch (RuntimeException e) {
            throw new StateCensusAnalyserException(StateCensusAnalyserException.exceptionType.INCORRECT_FILE);
        }
    }
    public int loadStateCodes(String csvFilePath) throws StateCensusAnalyserException {
        try (Reader reader = newBufferedReader(Paths.get(csvFilePath));) {
            Iterator<CSVStateCodePojo> csvStateCensusIterator = this.getFileIterator(reader,CSVStateCodePojo.class);
            return this.getCount(csvStateCensusIterator);
        } catch (IOException e) {
            throw new StateCensusAnalyserException(StateCensusAnalyserException.exceptionType.FILE_NOT_FOUND);
        } catch (RuntimeException e) {
            throw new StateCensusAnalyserException(StateCensusAnalyserException.exceptionType.INCORRECT_FILE);
        }
    }
    public <E> Iterator<E> getFileIterator(Reader reader, Class<E> csvClass) throws StateCensusAnalyserException {
        try {
            CsvToBeanBuilder csvToBeanBuilder = new CsvToBeanBuilder(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean csvToBean = csvToBeanBuilder.build();
            return csvToBean.iterator();
        }
        catch (RuntimeException e) {
            throw new StateCensusAnalyserException(StateCensusAnalyserException.exceptionType.INCORRECT_FILE);
        }
    }
    private <E> int getCount(Iterator<E> iterator)
    {
        Iterable<E> iterable = () -> iterator;
        int totalRecords = (int) StreamSupport.stream(iterable.spliterator(), false).count();
        return totalRecords;
    }

}
