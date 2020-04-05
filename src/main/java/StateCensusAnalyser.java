import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.Iterator;

import static java.nio.file.Files.newBufferedReader;

public class StateCensusAnalyser {
    public static String DATA_CSV_FILE_PATH = "./src/test/resources/StateCensusData.csv";

    public static void main(String[] args) throws StateCensusAnalyserException {
        StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();
        stateCensusAnalyser.loadData(DATA_CSV_FILE_PATH);
    }

    public int loadData(String FILE_PATH) throws StateCensusAnalyserException {
        int totalRecords=0;
            try(Reader reader = newBufferedReader(Paths.get(FILE_PATH)); ){
                CsvToBean<CSVStateCensus> csvStateCensusBeanObj = new CsvToBeanBuilder(reader)
                        .withType(CSVStateCensus.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                Iterator<CSVStateCensus> csvStateCensusIterator = csvStateCensusBeanObj.iterator();
                while (csvStateCensusIterator.hasNext()) {
                    CSVStateCensus csvStateCensus = csvStateCensusIterator.next();
                    System.out.println("State: " + csvStateCensus.getState());
                    System.out.println("Population: " + csvStateCensus.getPopulation());
                    System.out.println("AreaInSquareKm: " + csvStateCensus.getAreaInSqKm());
                    System.out.println("DensityPerSquareKm: " + csvStateCensus.getDensityPerSqKm());
                    System.out.println("----------------------------");
                    totalRecords++;
                }
            }catch (IOException e) {
                throw new StateCensusAnalyserException(StateCensusAnalyserException.exceptionType.FILE_NOT_FOUND);
            }
            catch (RuntimeException e) {
                throw new StateCensusAnalyserException(StateCensusAnalyserException.exceptionType.INCORRECT_FILE);
            }
            return totalRecords;
        }
    }

