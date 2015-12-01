package org.v2020.ie;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.v2020.cli.ie.CommandLineOptions;
import org.v2020.data.DataNeo4jConfiguration;
import org.v2020.service.ie.IVnaImport;
import org.v2020.util.time.TimeFormatter;

@Configuration
@ComponentScan(basePackages = {"org.v2020"})
@Import(DataNeo4jConfiguration.class)
public class Application implements CommandLineRunner {

    private static final String JAR_NAME = "v2020-vna-import-<VERSION>.jar";

    public Application() {
    }
    
 
    @Autowired
    IVnaImport vnaImport;

    public void run(String... args) throws Exception {   
        CommandLineParser parser = new BasicParser();
        try {
            // parse the command line arguments
            CommandLine line = parser.parse( CommandLineOptions.get(), args );
            String filePath = line.getOptionValue(CommandLineOptions.FILE);
            long start = System.currentTimeMillis();
            System.out.println("Importing " + filePath + "...");
            byte[] vnaFileData = Files.readAllBytes(Paths.get(filePath));
            vnaImport.setNumberOfThreads(1);
            vnaImport.importVna(vnaFileData);
            long ms = System.currentTimeMillis() - start;
            System.out.println("Import finished, runtime: " + TimeFormatter.getHumanRedableTime(ms));

        }
        catch( ParseException exp ) {
            // oops, something went wrong
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp( "java -jar " + JAR_NAME, CommandLineOptions.get() );
        }
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);       
    }

}
