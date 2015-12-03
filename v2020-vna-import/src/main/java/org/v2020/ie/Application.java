package org.v2020.ie;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.v2020.cli.ie.CommandLineOptions;
import org.v2020.data.DataNeo4jConfiguration;
import org.v2020.data.dao.iso.RelationshipDao;
import org.v2020.service.ie.IVnaImport;
import org.v2020.util.time.TimeFormatter;

@Configuration
@ComponentScan(basePackages = { "org.v2020" })
@EnableTransactionManagement
@Import(DataNeo4jConfiguration.class)
public class Application implements CommandLineRunner {

    private static Logger LOG = LoggerFactory.getLogger(RelationshipDao.class);

    private static final String JAR_NAME = "v2020-vna-import-<VERSION>.jar";

    public Application() {
    }

    @Autowired
    IVnaImport vnaImport;

    @Override
    public void run(String... args) throws Exception {
        CommandLineParser parser = new BasicParser();
        try {
            // parse the command line arguments
            CommandLine line = parser.parse(CommandLineOptions.get(), args);
            String filePath = line.getOptionValue(CommandLineOptions.FILE);
            String numberOfThreads = line.getOptionValue(CommandLineOptions.THREADS, CommandLineOptions.THREADS_DEFAULT);
            long start = System.currentTimeMillis();
            logFilePath(filePath);
            logNumberOfThreads(numberOfThreads);
            byte[] vnaFileData = Files.readAllBytes(Paths.get(filePath));
            vnaImport.setNumberOfThreads(Integer.valueOf(numberOfThreads));
            vnaImport.importVna(vnaFileData);
            long ms = System.currentTimeMillis() - start;
            logRuntime(ms);

        } catch (ParseException exp) {
            // oops, something went wrong
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -jar " + JAR_NAME, CommandLineOptions.get());
        }
    }

    private void logFilePath(String filePath) {
        String message = "Importing: " + filePath + "...";
        logMessage(message);
    }

    private void logNumberOfThreads(String numberOfThreads) {
        if (!"1".equals(numberOfThreads)) {
            String message = "Number of parallel threads: " + numberOfThreads;
            logMessage(message);
        }
    }

    private void logRuntime(long ms) {
        String message = "Import finished, runtime: " + TimeFormatter.getHumanRedableTime(ms);
        logMessage(message);
    }

    private void logMessage(String message) {
        System.out.println(message);
        if (LOG.isInfoEnabled()) {
            LOG.info(message);
        }
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

}
