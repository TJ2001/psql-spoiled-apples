import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Movie {
  private String title;
  private String genre;
  private int id;

  public Movie(String title, String genre) {
    this.title = title;
    this.genre = genre;
  }

  public String getTitle() {
    return title;
  }

  public String getGenre() {
    return genre;
  }

  public int getId() {
    return id;
  }

  public static List<Movie> all() {
    String sql = "SELECT * FROM review;";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Movie.class);
    }
  }

  public List<Review> getReviews() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM review where movieId=:id;";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Review.class);
    }
  }

  public static Movie find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM movie WHERE id=:id;";
      Movie movie = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Movie.class);
      return movie;
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO movie(title, genre) VALUES (:title, :genre);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("title", this.title)
        .addParameter("genre", this.genre)
        .executeUpdate()
        .getKey();
    }
  }

  @Override
  public boolean equals(Object otherMovie) {
    if(!(otherMovie instanceof Movie)) {
      return false;
    } else {
      Movie newMovie = (Movie) otherMovie;
      return this.getTitle().equals(newMovie.getTitle()) &&
             this.getGenre().equals(newMovie.getGenre()) &&
             this.getId() == newMovie.getId();
    }
  }
}
