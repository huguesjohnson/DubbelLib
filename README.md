# DubbelLib
Various bits of reusable code I've written over the years. 

Most of this code is used to support projects like:

* ROM editors
* Password generators
* File type conversion
* Managing my little insignificant web site
* Sega Genesis (Mega Drive) development

There tends to be a lot of overlap between these things. Yes, even the last bullet. 

**Motivation**

One of my worst development habits is copying files into multiple projects rather than creating libraries. This project is an attempt to assemble reusable code that can be referenced across my various projects. Maybe someone else will find something in here remotely helpful. There is no common theme to the things in here other than being code I keep reusing.

I'm sure it's an anti-pattern to cram a bunch of vaguely-related code into one library. Most of the things in here are small enough that making dozens of single-purpose libraries also seems wrong. A more direct explanation though... this is code that supports a number of projects where I am the only developer and this is how I like to organize things.

This also includes small single-use applications that aren't significant enough to warrant an entire project. 

**Everything here so far**

* Java
    * audio: A couple simple classes to play audio (really just midi files).
    * aws: Data structures and some helper classes for projects that integrate with AWS.
    * converters: Various utilities to convert one file type to another.
	* example: Reference implementations of data structures and algorithms, the kind of stuff that shows up in development interviews that we all forgot after graduating college.
	* file: Things to make some file and path resolution tasks easier.
	* file.filter: File filters used for open/save dialogs or listing files by type.
	* fx: JavaFX helper classes.
	* retailclerk: Tools to build games in the Retail Clerk series or other games using the same engine.
	* util: General utility classes that don't fit anywhere else.
	* webpublisher: Things to manage the aforementioned little insignificant web site.
* x68
	* retailclerk: A library of code used to build Retail Clerk like games. Calling it an 'engine' is giving it too much credit. I can't stand when projects call themselves 'frameworks' because they rarely are so I'm not doing that either. It's really just a bunch of code - that's as fancy a name as I'm willing to give it.

There are three build scripts:
* build-java-lib.sh: Builds a non-executable .jar with the library Java classes
	* converters, example, file, file.filter, util
* build-java-lib+fx.sh: Same as previous, but with JavaFX helper classes.
* build-rctools.sh: Build an executable .jar for the Retail Clerk build tools and dependencies.

Yes, I'm aware of Ant and Gradle and Maven and so. I've even administered build servers at various points in my career using all of them (and the .NET equivalents). This is how I roll when I work solo.

**Stuff I still want to add**

* Some .NET code - possibly moving some of my archived MDLib project into this repository.
* Possibly VB6 code because why not?

**Links**

My homepage: https://HuguesJohnson.com/
