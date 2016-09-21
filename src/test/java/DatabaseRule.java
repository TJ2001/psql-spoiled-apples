import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  @Override
  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/spoiled_apples_test", null, null);
  }

  @Override
  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteReviewsQuery = "DELETE FROM reviews *;";
      String deleteMoviesQuery = "DELETE FROM movies *;";
      con.createQuery(deleteReviewsQuery).executeUpdate();
      con.createQuery(deleteMoviesQuery).executeUpdate();
    }
  }

}
