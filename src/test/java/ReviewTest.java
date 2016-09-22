import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
// import java.util.Date;
import java.time.LocalDateTime;

public class ReviewTest {
  private Review mReview;

  @Before
  public void initialize(){
    mReview = new Review("Reviewer123", "This movie is awesome.", 1);
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Review_instantiatesCorrectly_true() {
    assertEquals(true, mReview instanceof Review);
  }

  @Test
  public void Review_instantiesWithReviewer_String(){
    assertEquals("Reviewer123", mReview.getReviewer());
  }

  @Test
  public void Review_instantiesWithContent_String(){
    assertEquals("This movie is awesome.", mReview.getContent());
  }

  @Test
  public void getId_instantiesWithID_1(){
    mReview.save();
    assertTrue(mReview.getId() > 0);
  }

  // @Test
  // public void isApple_isFalseAfterInstantiation_false(){
  //   assertEquals(false, mReview.isApple());
  // }

  // @Test
  // public void getCreatedAt_instantiatesWithCurrentTime_Today() {
  //   assertEquals(Date.now().getDayOfWeek(), mReview.getCreatedAt().getDayOfWeek());
  // }

  @Test
  public void all_returnsAllInstancesOfReview_true() {
    mReview.save();
    Review secondReview = new Review("Lane21", "This movie sucks.", 1);
    secondReview.save();
    assertEquals(true, Review.all().get(0).equals(mReview));
    assertEquals(true, Review.all().get(1).equals(secondReview));
  }

  @Test
  public void clear_emptiesAllReviewsFromArrayList_0() {
    assertEquals(Review.all().size(), 0);
  }

  @Test
  public void find_returnsReviewWithSameId_secondReview() {
    mReview.save();
    Review secondReview = new Review("Lane21", "This movie sucks.", 1);
    secondReview.save();
    assertEquals(mReview.find(secondReview.getId()), secondReview);
  }

  @Test
  public void equals_returnsTrueIfReviewersAreTheSame() {
    Review secondReview = new Review("Reviewer123", "This movie is awesome.", 1);
    assertTrue(mReview.equals(secondReview));
  }

  @Test
  public void save_returnsTrueIfReviewersAretheSame() {
    mReview.save();
    assertTrue(Review.all().get(0).equals(mReview));
  }

  @Test
  public void save_assignsIdToObject() {
    mReview.save();
    Review savedReview = Review.all().get(0);
    assertEquals(mReview.getId(), savedReview.getId());
  }

  // @Test
  // public void save_savesMoviesIdintoDB_true() {
  //   Movie myMovie = new Movie
  // }

  @Test
  public void update_updatesReview_true() {
    mReview.save();
    mReview.update("This is movie is okay.");
    assertEquals("This is movie is okay.", Review.find(mReview.getId()).getContent());
  }

  @Test
  public void delete_deletesReview_true() {
    mReview.save();
    int mReviewId = mReview.getId();
    mReview.delete();
    assertEquals(null, Review.find(mReviewId));
  }
}
