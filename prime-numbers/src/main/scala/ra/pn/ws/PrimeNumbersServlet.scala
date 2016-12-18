package ra.pn.ws

import ra.pn.application.{PrimeNumbersService, PrimeNumbersServiceImpl}
import ra.pn.domain.PrimeNumbers

import scala.collection.concurrent.TrieMap

import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json.JacksonJsonSupport
import org.scalatra.{Accepted, BadRequest, NotFound, Ok, ScalatraServlet}

/**
  * @author Remus Amalinei
  */
class PrimeNumbersServlet(primeNumbersService: PrimeNumbersService) extends ScalatraServlet with JacksonJsonSupport {

  private final val primeNumbersCache = new TrieMap[Int, Option[PrimeNumbers]]
  private final implicit val ec = PrimeNumbersServiceImpl.ExeCtx

  override protected implicit lazy val jsonFormats: Formats = DefaultFormats

  before() {
    contentType = formats("json")
  }

  post("/") {
    (parsedBody \ "number").extractOpt[Int] match {
      case Some(number) =>
        if (2 <= number) {
          cachePrimeNumbersFor(number)

          Accepted(headers = Map("Location" -> locationFor(number)))
        } else {
          BadRequest(body = s"the provided number = $number must be greater than or equal to 2, the smallest prime number")
        }
      case None =>
        BadRequest(body = """the expected JSON body is {"number": <an integer>}""")
    }
  }

  private def cachePrimeNumbersFor(number: Int): Unit = {
    if (primeNumbersCache.putIfAbsent(number, None).isDefined) {
      return
    }

    val future = primeNumbersService.constructFor(number)

    val callback: PartialFunction[PrimeNumbers, _] = {
      case primeNumbers =>
        primeNumbersCache.replace(number, Some(primeNumbers))
    }

    future.onSuccess(callback)(PrimeNumbersServiceImpl.ExeCtx)
  }

  private def locationFor(number: Int): String =
    s"${request.getScheme}://${request.getServerName}:${request.getServerPort}${request.getContextPath}/$number"

  get("/:number") {
    val numberParam = params("number")
    try {
      val number = numberParam.toInt
      primeNumbersCache.get(number) match {
        case Some(maybePrimeNumbers) => maybePrimeNumbers match {
          case Some(primeNumbers) => Ok(body = primeNumbers)
          case None => Accepted()
        }
        case None => NotFound()
      }
    } catch {
      case nfe: NumberFormatException => BadRequest(body = s"the provided path parameter must be an integer, was $numberParam")
    }
  }
}
