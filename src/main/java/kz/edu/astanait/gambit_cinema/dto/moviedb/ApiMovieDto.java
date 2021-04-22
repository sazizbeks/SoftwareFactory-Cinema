package kz.edu.astanait.gambit_cinema.dto.moviedb;

public class ApiMovieDto {
    private String title;
    private String release_date;
    private Long[] genre_ids;
    private String overview;
    private String poster_path;
    private Boolean adult;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public Long[] getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(Long[] genre_ids) {
        this.genre_ids = genre_ids;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }
}
