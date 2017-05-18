Before start to launch mobile app you should run server. 
Go to borrowland_server and read README file for this.

First way:
Download and install Android Studio. Create device at emulator, run project.

Second way:
If you want to use another emulator you should go to Borrowland/app/build/outputs/apk folder and use app-debug.apk

NB!: You can't use application without server. Besides, if you use another emulator you
should open Borrowland/app/src/main/res/values/strings.xml and change "server_address" 
field to necessary variant. 
