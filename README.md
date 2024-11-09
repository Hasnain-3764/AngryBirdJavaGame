// restarting again
# AngryBirds
This is an attempt to clone the static GUI of Classic Angry Bird Game with some modifications.

A [libGDX](https://libgdx.com/) project generated with [gdx-liftoff](https://github.com/libgdx/gdx-liftoff).

This project was generated with a template including simple application launchers and an `ApplicationAdapter` extension that draws libGDX logo.

## Platforms

- `core`: Main module with the application logic shared by all platforms.
- `lwjgl3`: Primary desktop platform using LWJGL3; was called 'desktop' in older docs.
- `html`: Web platform using GWT and WebGL. Supports only Java projects.

## Project Setup
### Method 1: Using Gradle (Recommended)
Gradle is already integrated into the project, so you can use it to manage dependencies and run tasks. The Gradle wrapper is included, so you can run Gradle tasks without installing it manually.

Step-by-step instructions to set up, run, and test:
1- Clone the repository: 
                      git clone https://github.com/your-repository/angrybirdsproject.git
                      cd angrybirdsproject

2- Build the project:
                     ./gradlew build
                      
3- Run the game:
                     ./gradlew lwjgl3:run

4- Run Tests
                     ./gradlew test
                      
5- Create a runnable JAR:
                      ./gradlew lwjgl3:jar



## Method 2: Using IntelliJ or Eclipse
1- Download or clone the repository:

                    git clone https://github.com/your-repository/angrybirdsproject.git
2- Open the project in IntelliJ IDEA or Eclipse.

3- Configure a Run Configuration:

4- Set the Java SDK version to 11 (recommended for compatibility).
5- Create a configuration for running the desktop app (lwjgl3 module).
6- Debug Configuration:

7- To debug the game, go to DebugPages.java in the lwjgl3 module and uncomment the code for the screen you want to debug.
8- Set up a debugging configuration in your IDE.
9- Run the configuration to debug.

## UML
- Although UML is attached as UML.png in the parent folder as well as in assets, still for visual clarity, i attach [MIRO link](https://miro.com/welcomeonboard/d1F0bFJGU2dvaUswaXhwMjc5c2laNmR2ZTNCVk9mbXdJbFM1NlFZTzF4YkoyMmFqOUhuUzRGV2VMT1lxVm12UHwzNDU4NzY0NTY0MTY5OTE0NzI1fDI=?share_link_id=107595965682)

## Gradle Commands

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

## References and Elements Credits
1. Canva Elements for the GUI Buttons, Backgrounds, and Images.
2. Youtube videos converted to MP3 for sounds (all sounds except "Hello" by OMFG are license-free).
3. "Hello" by OMFG is licensed under Creative Commons Attribution 3.0 License.  
   You can find the original work here: https://www.youtube.com/watch?v=ih2xubMaZWI 
   License details: https://creativecommons.org/licenses/by/3.0/
4. [LibGDX Documentation](https://libgdx.com/wiki) for game development framework references.
5. [gamefromscratch youtube playlist](https://youtube.com/watch?v=ih2xubMaZWI) for tutorials on Box2D and Scene2D.
6. GIMP and ImageMagick were used for editing PNG images.
7. [Liftoff](https://github.com/libgdx/gdx-liftoff) for project generation.












