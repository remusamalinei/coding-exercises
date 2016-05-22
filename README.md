# coding-exercises

A collection of coding exercises solved using BDD and TDD, Scala and Scala-based testing frameworks.
The acceptance/integration tests are written first, as automated validations for the scenarios that bring business
value. Using DDD best practices, the domain is at the core of the solution. Unit tests are used to prove the logic
correctness and the right usage of dependencies.

##rock-paper-scissors
A console-based implementation of the [rock-paper-scissors game](https://en.wikipedia.org/wiki/Rock-paper-scissors)

##roulette
In early development phases (may be completely redesigned).

##top-ranker
A concurrent solution, using the actor model, that uses a simple data structure to maintain a the top-n list of
players sorted by scores.