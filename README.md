#Coding Exercises
A collection of coding exercises solved using BDD and TDD, Scala and Scala-based frameworks.  
The acceptance/integration tests were written first, as automated validations for the scenarios that bring business
value. Using DDD best practices, the domain is at the core of the solution. Unit tests are used to prove the logic
correctness and the right usage of dependencies.

* [exchange](#exchange)  
* [maze](#maze)  
* [prime-numbers](#prime-numbers)  
* [river-crossing](#river-crossing)  
* [rock-paper-scissors](#rock-paper-scissors)  
* [top-ranker](#top-ranker)  

##exchange
A simple exchange engine that matches market orders.

##maze
Automatic maze exploration.

##prime-numbers
A microservice which, given an initial number, computes all prime numbers up to that initial number. Employing
non-blocking concurrency, the application can accept large numbers to compute primes for and responds immediately while
it continues the computations.

**Limitations and Extensions**  
- Make the application deployable to a servlet container like [Tomcat](http://tomcat.apache.org). It needs:
  - `ScalatraBootstrap.scala` and `web.xml` [files](http://www.scalatra.org/2.4/guides/deployment/configuration.html)
  - A plugin added in _coding-exercises/project/plugins.sbt_  
  `addSbtPlugin("com.earldouglas" % "xsbt-web-plugin" % "2.0.5")`  
  - `enablePlugins(JettyPlugin)` appended to the method chain of `prime-numbers` configuration in
  _coding-exercises/build.sbt_
  - Check libraries packed into the WAR file and remove any unnecessary ones.
- Support versioning and multiple formats (JSON, XML) using custom content-types and content negotiation.
- Measure and improve performances.

##river-crossing
A solution for the River Crossing puzzle, see [my blog post](https://remusamalinei.blogspot.com/2015/07/river-crossing-with-scala.html) too.

##rock-paper-scissors
A console-based implementation of the [rock-paper-scissors game](https://en.wikipedia.org/wiki/Rock-paper-scissors)

**Build and Run the Project**  
Prerequisites: Java, Scala and sbt installed.  
From the root directory _coding-exercises_ (where the _build.sbt_ file is), run the following 2 commands:  
`sbt "project rock-paper-scissors" clean package`  
`scala rock-paper-scissors/target/scala-2.11/rock-paper-scissors_2.11-1.0.0.jar`

**Design Considerations**  
The `GameService` provides the business value playing the game by orchestrating the interactions between two domain
`Player` objects.  
The `GameController` is responsible to manage the construction of the `GameService` based on the user decision. The play
functionality is delegated to `GameService`. If further decoupling from the domain `RoundResult` is needed, VOs can be
added.  
The `ConsoleGameView` provides a minimal yet completely functional UI for the game.

**Extension Support**  
The game can be extended to **rock-paper-scissor-spock-lizard** writing only a few lines of code:
- Mix in `Shape` for the two extra shapes `Spock` and `Lizard`.
- Change the values of `Shape.WinsAgainstShapeMap` into sets as a shape wins against two shapes. Modify `Shape.against`
accordingly.
- Provide a new implementation for `GameStrategy` that will return the five shapes.
- Inject the `GameStrategy` implementation into the `ShapeThrower` implementation when the latter is constructed in
`GameController`.

##top-ranker
A concurrent solution, using the actor model, that uses a simple data structure to maintain a the top-n list of
players sorted by score.
