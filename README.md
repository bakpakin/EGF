EGF - Entity Game Framework
===========================

EGF is an entity-component game framework written Java that uses the
lwjgl and slick libraries. The goal of creating EGF was making a framework
for creating my own 2d games to avoid re-writing the main loop and 
other trivial annoying things for every game. EGF also provides
functionality to easily and efficiently load assets, draw graphics,
move objects around on the screen, particles, tilemaps, and so on. The project 
quickly became a lot bigger than I initially intended, and as of writing this has over
one hundred classes.

The Framework
=============

EGF is based off of the entity-component model, which is a way to model a program that
happens to work very well for games. The main idea of an entity-component model is to
break up entities, or game objects, or whatever you want to call the 'actors' of your 
game, into sperate components. These components are then processed by systems, which
execute the majority of the actual logic in the game.  For example, an entity representing
a car might have a Position component, a Velocity component, a RigidBody component, and
possibly other components. Every step in the main game loop, a Movement System might
process the Position component and the Velocity component on the car to move the car.
This same Movement system could then also move around any other entity that also has
a Position component and a Velocity component.  The ability to process broad sets of 
entities is what makes systems and the entity-component model so powerful. Here's a good 
article that goes into more detail:
http://gameprogrammingpatterns.com/component.html