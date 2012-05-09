# [Rumor Simulation](http://nullprogram.com/blog/2012/03/09/)

Write a program that simulates the spreading of a rumor among a group
of people. At any given time, each person in the group is in one of
three categories:

 * IGNORANT - the person has not yet heard the rumor
 * SPREADER - the person has heard the rumor and is eager to spread it
 * STIFLER  - the person has heard the rumor but considers it old news
              and will not spread it

At the very beginning, there is one spreader; everyone else is
ignorant. Then people begin to encounter each other.

So the encounters go like this:

* If a SPREADER and an IGNORANT meet, IGNORANT becomes a SPREADER.
* If a SPREADER and a STIFLER meet, the SPREADER becomes a STIFLER.
* If a SPREADER and a SPREADER meet, they both become STIFLERS.
* In all other encounters nothing changes.

Your program should simulate this by repeatedly selecting two people
randomly and having them "meet."

There are three questions we want to answer:

Will everyone eventually hear the rumor, or will it die out before
everyone hears it?  If it does die out, what percentage of the
population hears it?  How long does it take? i.e. How many encounters
occur before the rumor dies out?
