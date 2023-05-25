### Status
[![Build Status](https://app.travis-ci.com/codehackerr/poka-yoke.svg?branch=master)](https://app.travis-ci.com/codehackerr/poka-yoke)
### Mistake Proofing (Poka-yoke) 

[Poka-yoke](https://en.wikipedia.org/wiki/Poka-yoke) is a Japanese term that means "mistake-proofing" or "inadvertent error prevention".
It's a mechanism, to prevent equipment operator to avoid mistakes.

A simple and easy to relate example is, an electric plug that cannot fit the wrong way into a power socket.

`Poka-yoke` can be applied to software design as well.
In software, the operator is the client developer using the APIs.
Through proper API design, we can prevent inadvertent errors in API usage by a client developer.



I will demonstrate this principle with a simple domain of a `Light Bulb` which transitions between `ON` and `OFF` state perpetually(never fuses).
Though this example is trivial, it's good enough to demonstrate the core idea.

Let's see few ways of implementing with and without  mistake proofing (`Poka-yoke`).

### Error prone Client API

```java
class LightBulb {

    private static boolean OFF = false;
    private static boolean ON = !OFF;
    
    private boolean state = OFF;
    
    public void on() {
        change_state(this.state, ON);
    }
    
    public void off() {
        change_state(this.state, OFF);
    }

    void change_state(boolean current_state, boolean transition) throws InvalidTransition {
        if (current_state == OFF && transition == ON) {
            state = ON;
        } else if (current_state == OFF && transition == OFF) {
            throw new InvalidTransition(INVALID_TRANSITION_OFF_to_OFF);
        } else if (current_state == ON && transition == OFF) {
            state = OFF;
        } else if (current_state == ON && transition == ON) {
            throw new InvalidTransition(INVALID_TRANSITION_ON_to_ON);
        }
    }
}

```

With this approach the client can call `on` or `off` on the `LightBulb` irrespective of it's current state.
When the client requests an invalid transition, it can be silently ignored or signalled to the client through an exception.
In the above design the client is signalled about the invalid transition request.

```
LightBulb light = new LightBulb();
try { 
     light.on();
} catch (InvalidTransition e){
     //....
}

try { 
     light.off();
} catch (InvalidTransition e){
     //....
}
```

With the above design
- clients can make an invalid call (API allows a client side programmatic error)
- client code is more complex (with the exception handling block).


## Mistake proofed Client API

### With interface segregation

These are the important aspects of this design.
- Each state (this is an FSM) is modeled as an object, namely `OffLight` and `OnLight`
- Interfaces are segregated by state, `OffLight.on()` and  `OnLight.off()`
- Neither `OffLight` or `OnLight` are directly instantiable
- `LightBulb.newLight()` is the factory method that returns the initial state `OffLight`

```

OffLight offLight = LightBulb.newLight();
OnLight onLight = offLight.on();

// And a blinker pattern

LightBulb.newLight()
 .on()
 .off()
 .on()
 .off()
 
```

In the above design
- clients can never make an invalid call (`Poka-yoke`)
- client code is simple (No invalid scenario handling)
- the transitions are very clear to the client

One issue though, with the above approach is the client code needs to be static.
ie; if you want to parameterize the number of blinks it's not possible in an elegant way.
That is because transitions returns different interfaces `on` or `off`.

### With common interface

If you have a dynamic use case, 
- the trick is to hide the `Offlight` and `OnLight` states inside an `LightBulb` interface.
- and replace the `on` and `off` interface with `toggle`. Thus there is a common interface.

```java

abstract class LightBulb {
    public abstract LightBulb toggle();

    public LightBulb newLight() {
        return new OffLight();
    }

    public boolean is_on() {
        return this instanceof OnLight;
    }

    public boolean is_off() {
        return this instanceof OffLight;
    }
    
    private static class OffLight extends LightBulb {
        public LightBulb toggle(){
            //.....
            return new OnLight();
        }
    }
    private static class OnLight extends LightBulb {
        public LightBulb toggle(){
            //.....
            return new OffLight();
        }
    }
}

```
With the above design
- clients can never make an invalid call (`Poka-yoke`)
- client code is simple (No invalid call handling)
- both `static` and `dynamic` use cases are supported

### Static use case
```

LightBulb.newLight()
.toggle()
.toggle()
.toggle()
 
```


### Dynamic use case
```java
LightBulb light = LightBulb.newLight();
for(int i = 0, reps = 10; i < reps; i++) {
    light = light.toggle();
}
```

### Which style to use?
The choice between the  `common interface` and `interface segregation` options, is a trade-off and a situational choice.

The `common interface` approach is useful if there is a cycle/loop through states.
Eg: A traffic light.
The `common interface` approach is useful when state transitions are not completely driven by the client.
Eg: An external event, timer etc changing the internal state.

`Interface segregation` on the other hand makes the transitions clear to the client API.
This helps readability and reasoning of the code. This style is favorable when the state transitions are fully driven by the client.


### Applications
There are different techniques to implement `Poka-yoke` in software design.
The above patterns are applicable to any Finite State Machines.

Some examples use cases are:

- sockets
- files
- database connections
- workflows

