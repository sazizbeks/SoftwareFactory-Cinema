package kz.edu.astanait.gambit_cinema.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import kz.edu.astanait.gambit_cinema.tools.StaticValues;
import org.springframework.web.client.RestTemplate;

import java.io.FileWriter;
import java.io.IOException;

public class ApiParser {
    private final RestTemplate restTemplate = new RestTemplate();

    private final String url = "https://api.themoviedb.org/3";

    public void parseList(String file, String url) throws IOException {
        MovieResults objects = restTemplate.getForObject(this.url + url, MovieResults.class);

        String json = new Gson().toJson(objects.getResults());
        json = beautify(json);
        FileWriter fileWriter = new FileWriter(StaticValues.jsonFolder + file, true);

        byte[] bytes = json.getBytes();
        int last = bytes.length - 1;
        for (int i = bytes.length - 1, ind = 1; i > 0; i -= 8192) {
            int min = Math.min(i, 8192);
            for (int j = 0; j < min; j++, ind++) {
                if (ind != last) {
                    fileWriter.write(bytes[ind]);
                } else {
                    fileWriter.write(',');
                }
                fileWriter.flush();
            }
        }
    }

    public void parseList(String file, String url, int pages) throws IOException {
        for (int i = 1; i <= 1; i++)
            parseList(file, url + "&page=" + i);

    }


    private String beautify(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Object obj = mapper.readValue(json, Object.class);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    }
}
