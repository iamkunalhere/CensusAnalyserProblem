import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

import static java.nio.file.Files.newBufferedReader;

public class StateCensusAnalyser {

    public int loadIndianCensusData(String csvFilePath) throws StateCensusAnalyserException {
        try (Reader reader = newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder icsvBuilder = CSVBuilderFactory.icsvBuilder();
            List<CSVStateCensusPojo> csvFileList = icsvBuilder.getFileList(reader,CSVStateCensusPojo.class);
            return csvFileList.size();
        } catch (IOException e) {
            throw new StateCensusAnalyserException(e.getMessage(),StateCensusAnalyserException.exceptionType.FILE_NOT_FOUND);
        } catch (CSVBuilderException e) {
            throw new StateCensusAnalyserException(e.getMessage(),e.type);
        }catch (RuntimeException e) {
            throw new StateCensusAnalyserException(e.getMessage(), StateCensusAnalyserException.exceptionType.INCORRECT_FILE);
        }
    }
    public int loadStateCodes(String csvFilePath) throws StateCensusAnalyserException {
        try (Reader reader = newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder icsvBuilder = CSVBuilderFactory.icsvBuilder();
            List<CSVStateCodePojo> csvFileList = icsvBuilder.getFileList(reader,CSVStateCodePojo.class);
            return csvFileList.size();
        } catch (IOException e) {
            throw new StateCensusAnalyserException(e.getMessage(),StateCensusAnalyserException.exceptionType.FILE_NOT_FOUND);
        } catch (CSVBuilderException e) {
            throw new StateCensusAnalyserException(e.getMessage(),e.type);
        }catch (RuntimeException e) {
            throw new StateCensusAnalyserException(e.getMessage(), StateCensusAnalyserException.exceptionType.INCORRECT_FILE);
        }
    }
    private <E> int getCount(Iterator<E> iterator) {
        Iterable<E> iterable = () -> iterator;
        int totalRecords = (int) StreamSupport.stream(iterable.spliterator(), false).count();
        return totalRecords;
    }

}
