### Status
[![Build Status](https://app.travis-ci.com/codehackerr/poka-yoke.svg?branch=master)](https://app.travis-ci.com/codehackerr/poka-yoke)
### [Poka-yoke](https://en.wikipedia.org/wiki/Poka-yoke)  (Mistake Proofing) 

Have you heard of [Poka-yoke](https://en.wikipedia.org/wiki/Poka-yoke)? It's a Japanese concept that means "mistake-proofing," and it's all about preventing errors before they happen. Think of it as a safety net to keep us from making blunders.

Imagine an electric plug that can only fit into a power socket one way - that's Poka-yoke in action, making it impossible to plug it in the wrong way.

Now, here's the cool part:
`
Poka-yoke isn't just for the physical world; we can apply it to software too! In software, we're the "operators" when we use APIs. By designing APIs carefully, we can stop ourselves from making accidental mistakes.
`

To show you how this works, let's dive into a simple example: 
`
A perpetual light bulb. It's a bulb that never burns out; it just switches on and off as you control it. It might sound trivial, but it's the perfect way to illustrate the power of Poka-yoke in software.
`

So, join me as we explore different ways to implement this, with and without the magic of mistake-proofing (Poka-yoke).

### Client API without the Poka-Yoke Magic

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

```java
/** Client Code */
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

With this approach the client can call `on` or `off` on the `LightBulb` irrespective of it's current state.
When the client requests an invalid transition, an exception is thrown.
There are few problems with this approach.

- **With all the invalid state conditionals, the implementation is unnecessarily complicated**
- **The client code is exposed to unnecessary details about the implementation, violating the law of least knowledge**
- **The client code is more complicated with the unnecessary exception handling blocks**


## Applying the Poka-Yoke Magic (Mistake proofed Client API)

`The fundamental idea in this design is clients can request only valid transitions, just like an electric plug that can only fit into a power socket one way`

There are two ways we can implement this namely
 - interface segregation based on state
 - common interface

### With interface segregation

These are the important aspects of this design.
- Each state (this is an FSM) is modeled as an object, namely `OffLight` and `OnLight`
- Interfaces are segregated by state, `OffLight.on()` and  `OnLight.off()`
- Neither `OffLight` or `OnLight` are directly instantiable
- `LightBulb.newLight()` is the factory method that returns the initial state `OffLight`

See the source directory for a complete code.

```java

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

The above approach has one limitation. The state transition code needs to be static.
ie; if you want to parameterize the number of blinks it's not possible in an elegant way.
That is because transitions returns different interfaces `on` or `off`.
This problem can be addressed with a common interface.

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
```java

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

### Practice Use cases
Some other example use cases are. Give it a try. Think state transitions.
Eg: It's invalid to run a query until you have an open database connection.

- Sockets 
- Files 
- Database connections and queries
- Workflows

### Discovering Mistake-Proof Software Design

If you've heard of encapsulation and data hiding, you're already on the road to understanding  - Poka-yoke. This fancy term is like having a safety net in your code. It means not exposing your data, so you can't accidentally mess up an object's state. It's like protecting your code from friendly fire!

Now, imagine you're diving deeper into good software design, and you stumble upon the Law of Least Knowledge. It's like a close cousin of Poka-yoke - all about keeping things tidy and avoiding messy situations.

And hey, what about SOLID? These are like the superheroes of software design principles, making your code robust and clean.

Here's the thing, though - trying to remember all these principles in every design can be a bit overwhelming. But don't worry, you'll get the hang of it over time. It's like learning to ride a bike; it feels tricky at first, but soon it becomes second nature.

In the meantime, start with a clear intention - that's Poka-yoke. Decide what you want to achieve first, and then pick the right principle or technique to get you there. It's like setting your GPS before a road trip - makes the journey smoother!

Keep exploring, keep coding, and soon enough, you'll be a pro at creating "Mistake-Proofed" software.