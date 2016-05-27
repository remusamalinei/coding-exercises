# coding-exercises
  A collection of coding exercises solved using BDD and TDD, Scala and Scala-based testing frameworks.
  The acceptance/integration tests were written first, as automated validations for the scenarios that bring business
value. Using DDD best practices, the domain is at the core of the solution. Unit tests are used to prove the logic
correctness and the right usage of dependencies.

##maze
  Automatic maze exploration.

##rock-paper-scissors
  A console-based implementation of the [rock-paper-scissors game](https://en.wikipedia.org/wiki/Rock-paper-scissors)

**Build and Run the Project**
  Prerequisites: Java, Scala and Gradle installed.
  From the root directory (where the _build.gradle_ file is), run the following 2 commands:
  `gradle rock-paper-scissors:clean rock-paper-scissors:build`
  `java -cp rock-paper-scissors/build/libs/rock-paper-scissors.jar:$SCALA_HOME/lib/* ra.rps.GameStarter`

**Design Considerations**
  The _GameService_ provides the business value playing the game by orchestrating the interactions between two domain
_Player_ objects.
  The _GameController_ is responsible to manage the construction of the _GameService_ based on the user decision. The play
functionality is delegated to _GameService_. If further decoupling from the domain _RoundResult_ is needed, VOs can be
added.
  The _ConsoleGameView_ provides a minimal yet completely functional UI for the game.

**Extension Support**
  The game can be extended to **rock-paper-scissor-spock-lizard** writing only a few lines of code:
- Mix in _Shape_ for the two extra shapes _Spock_ and _Lizard_.
- Change the values of _Shape.WinsAgainstShapeMap_ into sets as a shape wins against two shapes. Modify _Shape.against_
accordingly.
- Provide a new implementation for _GameStrategy_ that will return the five shapes.
- Inject the _GameStrategy_ implementation into the _ShapeThrower_ implementation when the latter is constructed in
_GameController_.

##roulette
  In early development phases (may be completely redesigned, no recent development).

##top-ranker
  A concurrent solution, using the actor model, that uses a simple data structure to maintain a the top-n list of
players sorted by score.
