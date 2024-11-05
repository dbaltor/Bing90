# Bing90 Ticket Generator

A small challenge that involves building a [Bingo 90](https://en.wikipedia.org/wiki/Bingo_(United_Kingdom)) ticket generator.

**Requirements:**

* Generate a strip of 6 tickets
    - Tickets are created as strips of 6, because this allows every number from 1 to 90 to appear across all 6 tickets. If they buy a full strip of six it means that players are guaranteed to mark off a number every time a number is called.
* A bingo ticket consists of 9 columns and 3 rows.
* Each ticket row contains five numbers and four blank spaces
* Each ticket column consists of one, two or three numbers and never three blanks.
    - The first column contains numbers from 1 to 9 (only nine),
    - The second column numbers from 10 to 19 (ten), the third, 20 to 29 and so on up until
    - The last column, which contains numbers from 80 to 90 (eleven).
* Numbers in the ticket columns are ordered from top to bottom (ASC).
* There can be **no duplicate** numbers between 1 and 90 **in the strip** (since you generate 6 tickets with 15 numbers each)
* Generate 10k strips within 1s

**Solution:**

I tried to design this solution bottom-up, but it didn't work out because of the requirements on strips.  
I switched over to a top-down approach and everything fitted together.

As usual, I have used OOP and TDD to develop this code which gave me a good test coverage.  

I have decided to create each strip in a single thread to avoid contention and to use multiple threads when creating multiple strips.  
Each strip creation runs completely independent, apart from the Pseudorandom Number Generator which is shared to ensure randomness 
during the whole process. I used "Xoroshiro128PlusPlus" which is one of the fastest "jumpable" PRNG available.

**How to build:**

You need java 21 to build the application. Run: `./gradlew build`

**How to run:**

You have two options to run the application:
- `./gradlew run --args=<number of strips>`
- `java -jar app/build/libs/app.jar <number of strips` , after having built it

The application is going to print the strips on the terminal.