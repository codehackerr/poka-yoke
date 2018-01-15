### Status
[![Build Status](https://api.travis-ci.org/codehackerr/if-to-not-if.png)](https://api.travis-ci.org/codehackerr/if-to-not-if.png)

Beautiful Client API with immutable objects
===========================================
Compare the following snippets for two different approaches on designing objects.
See the difference of client APIs and look at the code to see how it's implemented.

Immutable Objects
```

Offlight offLight = LightBulb.newLight();
OnLight onLight = offLight.on();

// And a blinker pattern

LightBulb.newLight()
 .on()
 .off()
 .on()
 .off()
 
```
Mutable Objects
```
```

Approach:

1. Mutable state, prevent invalid transitions by conditional checks and exception. com.light.with_if_and_mutable_state
2. No mutable state, prevent invalid transition by interface separation. com.light.no_if_no_mutable_state
3. Conceptual. Just a named Boolean, if java had a Boolean.toggle method that would toggle between Boolean.TRUE and Boolean.FALSE


