package kz.edu.astanait.gambit_cinema.parser;

import java.util.List;

public class MovieResults {
    private List<Object> results;

    public List<Object> getResults() {
        return results;
    }

    public void setResults(List<Object> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "MovieResults{" +
                "results=" + results +
                '}';
    }
}
