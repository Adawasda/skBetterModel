# skBetterModel

    Skript addon that adds shitty syntax support for BetterModel.

    It allows you to create and control EntityTrackers directly inside Skript with simple, flexible syntax.


    ## Requirements

    - Skript 2.14+
    - Java 21+
    - Paper 1.19.4+ (recommended 1.20+ / 1.21+)


## Features

    skBetterModel provides a simple bridge between Skript and BetterModel API:

    - Create EntityTrackers directly in Skript
    - Attach models to entities
    - Modify model properties (scale, brightness, view range, tint, glow, etc.)
    - Control animations from Skript
    - Store and reuse created trackers


## Example: Creating an Entity Tracker

    ```
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


## Animations

    
    play [bm|bettermodel] animation %string% for %object%
    play [bm|bettermodel] animation %string% for %object% with play once

    play [bm|bettermodel] animation %string% for %entity%
    play [bm|bettermodel] animation %string% for %entity% with play once
    


## Planned Features

    * Full EntityTracker property editing 
    * Full ModelRenderer (Blueprint) property editing
    * RenderedBone manipulation
    * Full animation property editing
    * Player limb animation support
    * Performance optimizations (no)
    * More BetterModel API coverage maybe


This addon is in active development and may change frequently.

