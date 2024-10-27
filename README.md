# AngryBirds

A [libGDX](https://libgdx.com/) project generated with [gdx-liftoff](https://github.com/libgdx/gdx-liftoff).

This project was generated with a template including simple application launchers and an `ApplicationAdapter` extension that draws libGDX logo.

## Platforms

- `core`: Main module with the application logic shared by all platforms.
- `lwjgl3`: Primary desktop platform using LWJGL3; was called 'desktop' in older docs.
- `html`: Web platform using GWT and WebGL. Supports only Java projects.

## Gradle

This project uses [Gradle](https://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.
Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `eclipse`: generates Eclipse project data.
- `html:dist`: compiles GWT sources. The compiled application can be found at `html/build/dist`: you can use any HTTP server to deploy it.
- `html:superDev`: compiles GWT sources and runs the application in SuperDev mode. It will be available at [localhost:8080/html](http://localhost:8080/html). Use only during development.
- `idea`: generates IntelliJ project data.
- `lwjgl3:jar`: builds application's runnable jar, which can be found at `lwjgl3/build/libs`.
- `lwjgl3:run`: starts the application.
- `test`: runs unit tests (if any).

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.

## README
This is an attempt to clone the static GUI of Classic Angry Bird Game with some modifications.
The game is written in Java using Libgdx.

To effectively run this project do the following steps below

- Method 1 - Follow the steps given above that uses gradle commands.
- Method 2 - Follow the instructions given below.
- download the code from github and set it up in intellij(or eclipse)
- configure a run configuration (try to use java 11)
- configure another debugging configuration for the desktop application. The DebugPages.java file is in Lwjgl3 module.(try java 11)
- Once the configuration is set up, run the project using gradle
- Once it runs for the first time, the project is set up and can be run anytime in that IDE.

To debug this project do the following steps.
- To debug any screen in the project, go to the DebugPages.java in lwjgl3 modules and uncomment the code written for that screen.
- run the debug configuration which was set in the steps above.
- gradle commands are given above.

## UML
- Although UML is attached as UML.png in the parent folder as well as in assets, still for visual clarity, i attach [MIRO link](https://miro.com/welcomeonboard/d1F0bFJGU2dvaUswaXhwMjc5c2laNmR2ZTNCVk9mbXdJbFM1NlFZTzF4YkoyMmFqOUhuUzRGV2VMT1lxVm12UHwzNDU4NzY0NTY0MTY5OTE0NzI1fDI=?share_link_id=107595965682)

## References
1. Canva Elements for the GUI Buttons, Backgrounds, and Images.
2. Youtube videos converted to MP3 for sounds (all sounds except "Hello" by OMFG are license-free).
3. "Hello" by OMFG is licensed under Creative Commons Attribution 3.0 License.  
   You can find the original work here: https://www.youtube.com/watch?v=ih2xubMaZWI 
   License details: https://creativecommons.org/licenses/by/3.0/
4. [LibGDX Documentation](https://libgdx.com/wiki) for game development framework references.
5. [gamefromscratch youtube playlist](https://youtube.com/watch?v=ih2xubMaZWI) for tutorials on Box2D and Scene2D.
6. GIMP and ImageMagick were used for editing PNG images.
7. Liftoff for project generation.












