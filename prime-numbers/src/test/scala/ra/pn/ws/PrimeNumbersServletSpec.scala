package ra.pn.ws

import ra.pn.application.PrimeNumbersServiceImpl
import ra.pn.domain.PrimeNumbers

import javax.servlet.http.HttpServletResponse

import org.json4s.jackson.JsonMethods.parse
import org.json4s.{DefaultFormats, Formats}
import org.scalatest.{FunSuiteLike, GivenWhenThen}
import org.scalatra.test.scalatest.ScalatraSuite

/**
  * @author Remus Amalinei
  */
class PrimeNumbersServletSpec extends ScalatraSuite with FunSuiteLike with GivenWhenThen {

  implicit lazy val jsonFormats: Formats = DefaultFormats

  addServlet(new PrimeNumbersServlet(new PrimeNumbersServiceImpl), "/*")

  info("As a web service client")
  info("I want to invoke prime-numbers web services")
  info("So I can provide an initial number")
  info("And get all the prime numbers up to that initial number")

  test("a GET retrieving a previously constructed PrimeNumbers should return 200 OK") {

    post("/", """{"number": 9}""".getBytes) {
      status should equal(HttpServletResponse.SC_ACCEPTED)
      val regex = "\\Ahttp:\\/\\/localhost:\\d+\\/9\\z".r
      val location = header("Location")
      regex.findAllMatchIn(location).size should equal(1)

      Thread.sleep(1000)

      get("/9") {
        status should equal(HttpServletResponse.SC_OK)
        parse(body).extract[PrimeNumbers] should equal(PrimeNumbers(9, Seq(2, 3, 5, 7)))
      }
    }
  }

  test("a GET retrieving an under construction PrimeNumbers should return 202 Accepted") {

    post("/", """{"number": 10000}""".getBytes) {
      get("/10000") {
        status should equal(HttpServletResponse.SC_ACCEPTED)
      }
    }
  }

  test("a GET retrieving PrimeNumbers which was not previously constructed should return 404 Not Found") {
    get("/2") {
      status should equal(HttpServletResponse.SC_NOT_FOUND)
    }
  }

  test("a GET retrieving PrimesNumbers providing an invalid path parameter should return 400 Bad Request") {
    get("/text") {
      status should equal(HttpServletResponse.SC_BAD_REQUEST)
    }
  }

  test("a POST constructing PrimeNumbers providing an invalid body should return 400 Bad Request") {
    post("/", """{"notTheExpectedField": 9}""".getBytes) {
      status should equal(HttpServletResponse.SC_BAD_REQUEST)
    }

    post("/", """{"number": "abc"}""".getBytes) {
      status should equal(HttpServletResponse.SC_BAD_REQUEST)
    }

    post("/", """{"number": 1}""".getBytes) {
      status should equal(HttpServletResponse.SC_BAD_REQUEST)
    }
  }
}
