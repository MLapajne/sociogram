Windows install:

- update export folder via mvn package
- build Sociogram.exe via launch4j (if needed)
- run CreateWinSetupScript.iss using Inno Setup
- result is a setup file

ALL PLATFORMS:

- zip export folder to Sociogram X.X-ALL-PLATFORMS.zip

Mac:

- copy from export:
  - folder data
  - folder icons
  - .jar file renamed to sociogram.jar
  - sociogram5_splash.bmp
  - socioLogo.ico
to Sociogram.app/Contents/MacOS/
- rename to Sociogram-X.X.app
