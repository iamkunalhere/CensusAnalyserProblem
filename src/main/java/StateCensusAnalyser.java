import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.Iterator;

import static java.nio.file.Files.newBufferedReader;

public class StateCensusAnalyser {

    public static void main(String[] args) throws IOException {
        StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();
        stateCensusAnalyser.loadData();
    }
    private static final String DATA_CSV_FILE_PATH = "./src/test/resources/StateCensusData.csv";
    public int loadData() throws IOException{
        int totalRecords=0;
            try(Reader reader = newBufferedReader(Paths.get(DATA_CSV_FILE_PATH)); ){
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
            }
            return totalRecords;
        }
}
