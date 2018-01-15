### Status
[![Build Status](https://api.travis-ci.org/codehackerr/if-to-not-if.png)](https://api.travis-ci.org/codehackerr/if-to-not-if.png)

Beautiful Client API with immutable objects
===========================================
Compare the following snippets for two different approaches on designing objects.
See the difference of client APIs and look at the code to see how it's implemented.


Usign Immutable Objects
========================
Immutable objects give a neater client api and allows only valid state transitions.
ie; more realistic, you cannot switch on a light which is already on. The `on` interface is limited to only `OffLight` and similarly the `off` interface is limited to `OnLight`
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

Using Mutable Objects
=====================
In this option the same object `LightBulb` has both `on` and `off` states, forcing you to handle the states at run time, leading to messy client API

```
LightBulb light = new LightBulb();
try { 
     bulb.on();
} catch (IllegalStateException e){
     //....
}

try { 
     bulb.off();
} catch (IllegalStateException e){
     //....
}


```

Approach:

1. Mutable state, prevent invalid transitions by conditional checks and exception. com.light.with_if_and_mutable_state
2. No mutable state, prevent invalid transition by interface separation. com.light.no_if_no_mutable_state


