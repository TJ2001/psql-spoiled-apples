import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
// import java.util.Date;
import java.time.LocalDateTime;

public class MovieTest {
  private Movie mMovie;
  //
  // @Before
  // public void openConnection() {
  //   DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/spoiled_apples_test", null, null);
  // }

  @Before
  public void initialize(){
    mMovie = new Movie("Indian Jones", "Parody");
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Movie_instantiatesCorrectly_true() {
    assertEquals(true, mMovie instanceof Movie);
  }

  @Test
  public void Movie_instantiatesWithTitle_String() {
    assertEquals("Indian Jones", mMovie.getTitle());
  }

  @Test
  public void Movie_instantiatesWithGenre_String() {
    assertEquals("Parody", mMovie.getGenre());
  }

  @Test
  public void Movie_instantiatesWithID_1() {
    mMovie.save();
    assertTrue(mMovie.getId() > 0);
  }
}

//We were going to change "review" to "reviews" (see console)
