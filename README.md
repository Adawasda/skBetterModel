````md
# skBetterModel

A simple Skript addon that adds syntax support for BetterModel.

---

## Features

- Create and manage BetterModel EntityTrackers inside Skript
- Control model properties (scale, brightness, view range, tint)
- Play animations with flexible syntax
- Supports both Entity and EntityTracker inputs

---

## Example: Creating an Entity Tracker

```skript
command /testmodel <string>:
    trigger:
        spawn armor stand at player:
            set {_e} to entity

        create entity tracker with model arg-1:
            entity: {_e}
            scale: vector(2, 1, 1)
            brightness: 15
            view range: 1000
            tint: rgb(255, 255, 255)

        set {_tracker} to last created entity tracker
        
````

---

## Playing Animations

```text
play [bm|bettermodel] animation %string% for %object%
play [bm|bettermodel] animation %string% for %object% with play once

play [bm|bettermodel] animation %string% for %entity%
play [bm|bettermodel] animation %string% for %entity% with play once
```

---

## Planned Features

* Full EntityTracker property manipulation
* ModelRenderer (Blueprint) editing support
* RenderedBone control system
* Advanced animation configuration system
* Player animation support
* More improvements and API expansions

---

## Notes

This addon is still in development and may change frequently.

```
```
