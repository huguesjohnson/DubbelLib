# DubbelLib
Various bits of reusable code I've written over the years. 

Most of this code is used to support projects like:

* ROM editors
* File type conversion 
* Sega Genesis code generation

There tends to be a lot of overlap between these three things.

**Motivation**

One of my worst development habits is copying files into multiple projects rather than creating libraries. This project is an attempt to assemble reusable code that can be referenced across my various projects. Maybe someone else will find something in here remotely helpful. There is no common theme to the things in here other than being code I keep reusing.

I'm sure it's an anti-pattern to cram a bunch of semi-related code into one library. Most of the things in here are small enough that making dozens of single-purpose libraries also seems wrong. 

This also includes small single-use applications that aren't significant enough to warrant an entire project. 

**Everything here so far**

* Java
	* converters - utilities to convert one file type to another
	* file - things to make some file and path resolution tasks easier
	* file.filter - file filters used for open/save dialogs or listing files by type
	* fx - JavaFX helpers
	* retailclerk - tools to build games in the Retail Clerk series or other games using the same engine
	* util - general utility classes that don't fit anywhere else

**Stuff I still want to add**

* Some .NET code
* Some 68k code
* Possibly VB6 code because why not?

**Links**

My homepage: https://HuguesJohnson.com/
