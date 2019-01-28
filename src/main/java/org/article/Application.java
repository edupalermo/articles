package org.article;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

@SpringBootApplication
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    private static final String[] ENGLISH_ARTICLES = new String[] {
            "001_guardian.txt",
            "002_guardian.txt",
            "003_guardian.txt",
    };


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) throws IOException {

//        Set ignoreSet = loadSet("ignore.txt");


//        Map<String, Integer> map = new TreeMap<String, Integer>();

//        for (String filename : ENGLISH_ARTICLES) {
//            processArticle(filename, map, ignoreSet);
//        }

//        dump(sort(map), map);

        return args -> {
            logger.info("Start up hook executed.");
        };
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addDialect(new LayoutDialect());
        return templateEngine;
    }

    private void processArticle(String filename, Map<String, Integer> map, Set<String> ignoreSet) {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(new ClassPathResource(filename).getInputStream()));
            String line = null;

            while ((line = br.readLine()) != null) {
                process(map, ignoreSet, line);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    private void process(Map<String, Integer> map, Set<String> ignoreSet, String line) {
        for (String word : line.split("\\s|“|”|\\.|,|’|–|'|‘|:|\\(|\\)")) {
            if ((word != null) && (word.trim().length() > 0) && (!ignoreSet.contains(word.trim().toUpperCase()))) {
                String treated = word.trim().toUpperCase();
                if (map.containsKey(treated)) {
                    map.put(treated, Integer.valueOf(map.get(treated).intValue() + 1));
                }
                else {
                    map.put(treated, Integer.valueOf(1));
                }
            }
        }
    }

    private List<String> sort(Map<String, Integer> map) {
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, Integer> each : map.entrySet()) {

            int pos = 0;

            while ((pos < list.size()) &&
                    (map.get(each.getKey()).intValue() <= map.get(list.get(pos)).intValue()) &&
                    (each.getKey().compareTo(list.get(pos)) > 0)) {
                pos++;
            }

            list.add(pos, each.getKey());
        }
        return list;
    }

    public void dump(List<String> list, Map<String, Integer> map) {
        for (String word : list) {
            System.out.println(String.format("%03d - %s", map.get(word).intValue(), word));
        }
    }

    public void dump(Map<String, Integer> map) {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(String.format("%03d - %s", entry.getValue().intValue(), entry.getKey()));
        }
    }

    private Set<String> loadSet(String filename) {
        Set<String> set = new TreeSet<>();
        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(new ClassPathResource(filename).getInputStream()));

            String line = null;

            while ((line = br.readLine()) != null) {
                if (line.trim().length() > 0) {
                    set.add(line.trim().toUpperCase());
                }
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return set;
    }

    private void stat(String[] articles) {
        for (String filename : articles) {
            statFile(filename);
        }
    }

    private void statFile(String filename) {
        System.out.println("File: " + filename);
    }


    private void countWords(Map<String, Integer> map, String line) {
        for (String word : line.split("\\s|“|”|\\.|,|’|–|'|‘|:|\\(|\\)")) {
            if ((word != null) && (word.trim().length() > 0)) {
                String treated = word.trim().toUpperCase();
                if (map.containsKey(treated)) {
                    map.put(treated, Integer.valueOf(map.get(treated).intValue() + 1));
                }
                else {
                    map.put(treated, Integer.valueOf(1));
                }
            }
        }
    }

}
