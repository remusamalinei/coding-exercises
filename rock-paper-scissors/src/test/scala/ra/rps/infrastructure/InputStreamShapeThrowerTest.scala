package ra.rps.infrastructure

import ra.rps.domain.Shape
import ra.rps.domain.Shape.{Paper, Rock, Scissors}
import ra.rps.domain.service.GameStrategy

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, PrintStream}

import org.scalamock.scalatest.MockFactory
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}

/**
  * @author Remus Amalinei
  */
class InputStreamShapeThrowerTest extends FlatSpec with Matchers with MockFactory with BeforeAndAfterAll {

  override def beforeAll(): Unit = {
    System.setOut(new PrintStream(new ByteArrayOutputStream()))
  }

  "InputStreamShapeThrower" should "offer all possible next shapes" in {
    val inputStream = new ByteArrayInputStream("0 1 2".getBytes)

    val gameStrategyMocked = mock[GameStrategy]
    (gameStrategyMocked.shapes _).expects().returning(Set[Shape](Rock, Paper, Scissors))

    val shapeThrower = new InputStreamShapeThrower(gameStrategyMocked, inputStream)

    val shapes = 0 to 2 map {
      i => shapeThrower.next
    }

    shapes.toSet should be(Set(Rock, Paper, Scissors))
  }

  it should "retry until the input is an expected integer" in {
    class TestExpectedException extends Exception

    val inputStream = new ByteArrayInputStream("abc -1 123 0".getBytes)

    val gameStrategyMocked = mock[GameStrategy]
    (gameStrategyMocked.shapes _).expects().returning(Set(Rock))

    val shapeThrower = new InputStreamShapeThrower(gameStrategyMocked, inputStream)

    shapeThrower.next should be(Rock)
  }
}
