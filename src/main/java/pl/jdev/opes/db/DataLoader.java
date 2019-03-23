package pl.jdev.opes.db;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import pl.jdev.opes.db.dto.InstrumentDto;
import pl.jdev.opes.db.repo.InstrumentRepository;
import pl.jdev.opes_commons.domain.instrument.InstrumentType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Component
public class DataLoader implements ApplicationRunner {
    @Autowired
    private WebApplicationContext ctx;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        loadInstrumentData();
    }

    private void loadInstrumentData() {
        List<InstrumentDto> instruments = null;
        List<List<String>> parsedCSV;
        try {
            List<String> instRecords = loadRecords(new ClassPathResource("/db/instrument_data_load.csv").getFile());
            parsedCSV = parseCSV(instRecords);
            instruments = parsedCSV.stream()
                    .map(entry -> new InstrumentDto(
                            entry.get(0),
                            entry.get(1),
                            InstrumentType.valueOf(entry.get(2)),
                            Boolean.FALSE,
                            Boolean.FALSE))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println(String.format("Unable to read instrument data:\n%s", e));
        }
        try {
            assert instruments != null : "Instrument list is null!";
            System.out.println("Adding instruments:");
            instruments.forEach(System.out::println);
            InstrumentRepository repo = ctx.getBean(InstrumentRepository.class);
            repo.saveAll(instruments);
        } catch (AssertionError e) {
            System.out.println(String.format("Unable to save instrument data:\n%s", e));
        }
    }

    private List<String> loadRecords(File file) throws IOException {
        List<String> records = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                records.add(line);
            }
        }
        return records;
    }

    private List<List<String>> parseCSV(List<String> records) {
        return records.stream()
                .map(entry -> Arrays.asList(entry.split(",")))
                .collect(Collectors.toList());
    }

}
