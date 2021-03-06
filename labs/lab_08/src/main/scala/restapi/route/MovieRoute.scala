package restapi.route

import akka.http.scaladsl.server.Directives.{as, complete, delete, entity, get, handleExceptions, path, pathEndOrSingleSlash, pathPrefix, post, put, _}
import restapi.model.service.MovieService
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives.{complete, get, path, post}
import spray.json.{DefaultJsonProtocol, RootJsonFormat}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.ExceptionHandler
import restapi.model.dao.{Movie, MovieNotFoundException, MovieWithoutId}

class MovieRoute(movieService : MovieService) extends SprayJsonSupport {
  import DefaultJsonProtocol._

  implicit val movieSerializer: RootJsonFormat[Movie] = jsonFormat4(Movie)
  implicit val movieWithoutIdSerializer: RootJsonFormat[MovieWithoutId] = jsonFormat3(MovieWithoutId)

  val movieRoute =
    pathPrefix("v1") {
      handleExceptions(globalExceptionHandler) {
        pathPrefix("movie") {
          pathEndOrSingleSlash{
            get {
              complete(movieService.getAllMovies)
            } ~
              post { entity(as[MovieWithoutId]) { newMovie =>
                complete(movieService.addMovie(newMovie))
              }}
          } ~
            path(Segment) { id : String =>
              get {
                complete(movieService.getMovie(id))
              } ~
                put {entity(as[MovieWithoutId]) { newMovie =>
                  complete(movieService.updateMovie(id, newMovie))
                }} ~
                delete {
                  complete(movieService.deleteMovie(id))
                }
            }
        }
      }
    }

  val globalExceptionHandler = ExceptionHandler {
    case ex: MovieNotFoundException => {
      println(s"Not Found Movie ${ex.movieId}")
      complete(HttpResponse(StatusCodes.NotFound, entity = ex.getMessage()))
    }
    case ex: Exception => {
      println(s"General Exception : ${ex.getMessage}")
      complete(HttpResponse(StatusCodes.InternalServerError, entity = ex.getMessage()))
    }
  }
}
