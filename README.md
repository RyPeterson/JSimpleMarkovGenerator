A Simple Markov Chain generator in Java
========================================

What is a Markov Chain?
------------------------

"to generate superficially real-looking text given a drink"

Markov Chains are a random process that that undergoes transitions from one state to another, and applied
to text generation, produces a seemingly real sentence based off of random generation from its internal state;
the example given at the start of this section was generated from a sentence from the [Wikipedia](http://en.wikipedia.org/wiki/Markov_chain) article about
Markov Chains, adjusted to make tense sense: "Markov processes can be used to generate superficially real-looking text given a sample document"
and the movie intermission song: "lets all go to the lobby and get ourselves a drink", with the actual result of the generator
ironically summarizing what it does.

-------------------------------------------------

Couple notes about this:
Yes, I know the pom file is probably useless. 
Yes, I know the concurrent classes probably would fail epically.
If this bothers you, fork the heck out of this and fix it. :)
