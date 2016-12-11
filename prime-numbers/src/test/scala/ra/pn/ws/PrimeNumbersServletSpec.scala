package ra.pn.ws

import org.scalatest.FunSuiteLike

/**
  * @author Remus Amalinei
  */
class PrimeNumbersServletSpec extends FunSuiteLike {

  info("As a web service client")
  info("I want to invoke prime-numbers web services")
  info("So I can provide an initial number")
  info("And get all the prime numbers up to that initial number")

  test("a GET retrieving a previously constructed PrimeNumbers should return 200 OK") {}
  test("a GET retrieving an under construction PrimeNumbers should return 202 Accepted") {}
  test("a GET retrieving PrimeNumbers which was not previously constructed should return 404 Not Found") {}
  test("a GET retrieving PrimesNumbers providing an invalid path parameter should return 400 Bad Request") {}

  test("a POST constructing PrimeNumbers providing an invalid body should return 400 Bad Request") {}
}
