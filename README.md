jcunit
======

Unit testing framework for JavaCard


Why JCUnit?

JUnit wouldn't work on Java Card.
Java Card has no reflection.
Java Card has no support for runtime annotations.
And Java Card does not record stack traces for exceptions.
This requires a different approach in implementing a unit testing framework for Java Card.

JCUnit provides an Assertion facility, similar to that of JUnit.
The main difference is how JCUnit reports the line number of the error.
In normal JUnit, you get the failing class and line number from the stack trace.
The stack trace is not available on Java Card.
JCUnit instead makes the card respond with a status word that contains the line number information.

The Status Word responses are:
62XX: fail in line number XX.
63XX: fail in line number 0x1XX.
64XX: fail in line number 0x2XX.
65XX: fail in line number 0x3XX.

When the line number is 0x400 or bigger, there is an overflow which wraps.
I do not think this is an issue, I've never seen a good test applet that would've needed wrapping there.
The main test and verification driver should always fit in the first 1024 lines of the source file.
Have a look at the sample applet to see how it works.

JCUnit does not provide a test runner or test driver (yet?).
One of the reasons is that different cards need different types of communication.
Even if it were always T=0, different types of loading standards and secure messaging need to be taken into account.
