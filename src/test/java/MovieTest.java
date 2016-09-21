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

//new tests

@Test
 public void find_returnsMovieWithSameId_secondMovie() {
   Movie secondMovie = new Movie("Happy Gilmore", "comedy");
   secondMovie.save();
   assertEquals(Movie.find(secondMovie.getId()), secondMovie);
 }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Movie secondMovie = new Movie("Indian Jones", "Parody");
    assertTrue(firstMovie.equals(secondMovie));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    myMovie.save();
    assertTrue(Movie.all().get(0).equals(mMovie));
  }

  @Test
  public void save_assignsIdToObject() {
    mMovie.save();
    Movie savedMovie = Movie.all().get(0);
    assertEquals(mMovie.getId(), savedMovie.getId());
  }

  @Test
  public void getReviews_retrievesALlReviewsFromDatabase_reviewsList() {
    mMovie.save();
    Review firstReview = new Review("Reviewer123", "This movie is awesome.", mMovie.getId());
    firstReview.save();
    Review secondReview = new Review("Lane21", "This movie sucks.", myMovie.getId());
    secondReview.save();
    Review[] reviews = new Review[] { firstReview, secondReview };
    assertTrue(myMovie.getReviews().containsAll(Arrays.asList(reviews)));
  }
}

//We were going to change "review" to "reviews" (see console)
