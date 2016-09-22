import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      model.put("movies", Movie.all());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/movies/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/movie-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/movies/:movie_id/reviews/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Movie movie = Movie.find(Integer.parseInt(request.params(":movie_id")));
      model.put("movie", movie);
      model.put("template", "templates/review-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/movies/:movie_id/reviews/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Movie movie = Movie.find(Integer.parseInt(request.params(":movie_id")));
      Review review = Review.find(Integer.parseInt(request.params(":id")));
      model.put("movie", movie);
      model.put("review", review);
      model.put("template", "templates/review.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/movies/:movie_id/reviews/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Review review = Review.find(Integer.parseInt(request.params("id")));
      String reviewer = request.queryParams("reviewer");
      Movie movie = Movie.find(review.getMovieId());
      review.update(reviewer);
      String url = String.format("/movies/%d/reviews/%d", movie.getId(), review.getId());
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/movies/:movie_id/reviews/:id/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Review review = Review.find(Integer.parseInt(request.params("id")));
      Movie movie = Movie.find(review.getMovieId());
      review.delete();
      model.put("movie", movie);
      model.put("template", "templates/movie.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/reviews", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Movie movie = Movie.find(Integer.parseInt(request.queryParams("movieId")));
      String reviewer = request.queryParams("reviewer");
      String content = request.queryParams("content");
      Review newReview = new Review(reviewer, content, movie.getId());
      newReview.save();
      model.put("movie", movie);
      model.put("template", "templates/movie-review-success.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/reviews/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Review review = Review.find(Integer.parseInt(request.params(":id")));
      model.put("review", review);
      model.put("template", "templates/review.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/movies", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String title = request.queryParams("title");
      String genre = request.queryParams("genre");
      Movie newMovie = new Movie(title, genre);
      newMovie.save();
      model.put("template", "templates/movie-success.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/movies", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("movies", Movie.all());
      model.put("template", "templates/movies.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/movies/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Movie movie = Movie.find(Integer.parseInt(request.params(":id")));
      model.put("movie", movie);
      model.put("template", "templates/movie.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }
}
