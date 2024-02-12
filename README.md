# ConnectFour
<p>
  <img src="https://i.imgur.com/mndOctL.png" alt="Logo" width="400" height="300"/>
</p>

## Introduction

The project is a Java-based application that uses Maven for dependency management. It is a representation of the classic Connect Four game, with additional features and capabilities. The game can be played in two modes: Command Line Interface (CLI) and Graphical User Interface (GUI). The mode is determined based on the environment in which the application is launched.  The game offers several launch options, including solo play, hosting a game for people in the local network, joining a game in the local network, and playing against a computer (AI). The launch options can be specified as command-line arguments or chosen from a dialog in the GUI mode.  The project uses several dependencies, including commons-cli for command line interfaces, tyrus-server and tyrus-container-grizzly-server for WebSocket server implementation, and gson for JSON processing. These dependencies are specified in the pom.xml file.  The project is organized into several packages, including org.Griffty.UserInterface, org.Griffty.Util.Dialogs, and org.Griffty.Controllers. The UserInterface package contains interfaces for user interaction, the Dialogs package contains classes for creating and managing dialogs in the GUI mode, and the Controllers package contains classes for controlling the game based on the chosen launch option.  The Main class is the entry point of the application. It determines the game type, parses the launch option, and starts the appropriate game controller. The InputDialog class is used to create input dialogs in the GUI mode. The IUserInterface interface is used to define methods for user interaction.  The project is designed with extensibility in mind, allowing for potential enhancements such as different board sizes, and more.
## Contribution

Contributions to this project are very welcome and appreciated. You are free to fork, modify, and use this project as you see fit, even for commercial purposes. Please ensure to adhere to the existing code style and provide tests where necessary.

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
## Contact

If you have any questions, suggestions, or would like to contribute to the project, feel free to reach out:

- Email: kotelyakvv@gmail.com
- Discord: griffty