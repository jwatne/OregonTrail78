# Oregon Trail 78
> This is a Java port of the original 1978 version of the BASIC source code for
> Oregon Trail - **extremely** retro gaming.

## Table of Contents
* [General Info](#general-information)
* [Technologies Used](#technologies-used)
* [Setup](#setup)
* [Usage](#usage)
* [Project Status](#project-status)
* [Room for Improvement](#room-for-improvement)
* [Acknowledgements](#acknowledgements)



## General Information
Sometime around 1980, I attended a computer programming summer school course, programming in BASIC
on timeshare computers linked via analog couplers to the TIES system. One day, I typed the command
to print the most recently loaded program, which I mistakenly thought was one of those consisting
of a couple dozen lines that I would write for the course. Instead, I got page after page of the
complete BASIC source code of the 1978 version of 
[Oregon Trail](https://mncomputinghistory.com/oregon-trail-computer-game/). This was essentially
the same as 
[the version appearing in the May 1978 issue of "Creative Computing" magazine](https://archive.org/details/creativecomputing-1978-05/page/n139/mode/2up).

Rather than waste it,
I took it home, and spent several hours doing my first port of the code - to ATARI BASIC, typing
it in on the membrane keyboard of my Atari 400 computer. I kept the printout ever since.

I rediscovered the printout when cleaning out my basement a few weeks ago, and decided to try a
new port of the code, to Java. The code is a simple command line Java application. While I have
broken the single basic file into multiple objects, used more descriptive variable names, and 
modified the code in other ways to use more modern coding practices not supported by the computer
languages available when the original program was written, I have tried to keep the end user
experience as identical as possible. This includes the same use of random numbers, writing all
text in ALL CAPITAL LETTERS, and doing non-case-sensitive text comparisons of what the user
enters to the word to be typed when "shooting". 

## Technologies Used
- OpenJDK 17 - though it should run on any JDK version 11 or greater.
- Apache Maven 3.8.2

## Setup
Setup standard JDK and Maven environment. Import Maven project into preferred 
Integrated Development Environment (IDE), such as Eclipse, if desired.
Ensure JDK and Maven are in classpath.
To compile and create executable .jar file, type the following at a command
prompt [or choose maven clean install from IDE options]:

`cd [path to parent source folder, containing pom.xml]`

`maven clean install`


## Usage
To run the program, type the following while in the same directory in which
the maven clean install was done:
 
`cd target`

`java -jar OregonTrail78-0.0.1-SNAPSHOT.jar`


## Project Status
Project is: **complete**.


## Room for Improvement
- Only a few of the constants from the original source code were given
meaningful constant names. I wanted to get this done fairly rapidly, and
ran out of patience trying to come up with names for each constant
encountered. Reviewing the formulas laid out in the "Creative Computing"
article might offer some guidance on appropriate constant names for those
constants in the more complicated formulas.


## Acknowledgements
- This project was a straight port of the original Oregon Trail code. The
original program provided many hours of enjoyment to me, and millions of
others, as a child.
- Many thanks to Don Rawitsch, Paul Dillenberger, and Bill Heinemann for
writing the original program, the Minnesota Educational Computing Consortium (MECC)
for making it available to schools, and the Roseville (Minnesota) school district
and the taxpayers of Roseville and the state of Minnesota in the 1970's who made
this available to me to learn from and enjoy.
