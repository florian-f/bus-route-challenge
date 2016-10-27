package routes;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(final Map<Integer, Set<Integer>> routes) {
        return args -> {
            if (args.length > 0) {
                buildRoutes(routes, args);
            }
        };
    }

    private void buildRoutes(Map<Integer, Set<Integer>> routes, String[] args) throws FileNotFoundException {
        File file = new File(args[0]);
        Scanner input = new Scanner(file);

        if (input.hasNextLine()) {
            input.nextLine();
        }

        while (input.hasNextLine()) {
            String line = input.nextLine();
            String[] busLine = line.split(" ");
            Integer id = Integer.parseInt(busLine[0]);

            for (int i = 1; i < busLine.length - 1; i++) {
                Integer stop = Integer.parseInt(busLine[i]);
                if (!routes.containsKey(stop)) {
                    Set<Integer> lines = new HashSet<>();
                    routes.put(stop, lines);
                } else {
                    if (!routes.get(stop).contains(id)) {
                        routes.get(stop).add(id);
                    }
                }
            }
        }
        input.close();
    }

    @Bean
    public Map<Integer, Set<Integer>> getRoutes() {
        return new HashMap<>();
    }

}
