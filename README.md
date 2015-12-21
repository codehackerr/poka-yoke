### Status
[![Build Status](https://api.travis-ci.org/codehackerr/if-to-not-if.png)](https://api.travis-ci.org/codehackerr/if-to-not-if.png)

if-to-not-if
=============

The domain of this problem is a Light Bulb.

A new Light Bulb is always in the OFF state.

Valid transitions:

 ON to OFF

 OFF to ON

Invalid Transitions:

 OFF when already OFF,

 ON when already ON

Approach:

1. Mutable state, prevent invalid transitions by conditional checks and exception. com.light.with_if_and_mutable_state
2. No mutable state, prevent invalid transition by interface separation. com.light.no_if_no_mutable_state
3. Conceptual. Just a named Boolean, if java had a Boolean.toggle method that would toggle between Boolean.TRUE and Boolean.FALSE


