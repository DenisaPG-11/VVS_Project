# VVS_Project
Denisa, PAP-GHERASIM
Anul 4, CTI-Ro

Create a web server that would make it possible for its clients to view, in a browser, the web pages from a location predetermined by the server's administrator. The web pages may have relative links one to another, but also separate .css files. It should also be able to serve files from the tree file structure relative to the predetermined location.

In order to facilitate the server administration, it should have a graphical user interface that would display the current state of the server, together with various configuration and management options. Also, the web server should be able to work in standalone mode (without its graphical interface).

In order to access it, build and start the server, and open address (http://locahost:10008 or) http://127.0.0.1:10008 in your browser.

Requirements

WebServer: The web server must serve .html pages together with their .css styling files, stored in paths relative to a root folder (usually www, htdocs or public_html), persistent and configurable. At a moment in time, the web server can be in any of its 3 possible states: Stopped, Running and Maintenance. The server's initial state is Stopped, when the server is shut down: while in this state, any request on the machine on which the server is running will result in a connection timeout. The server will be in state Running when it is correctly configured and properly started: while in this state, to each received request, the server will answer with the required page, if it is found, or with the standard error message HTTP 404: Not Found otherwise. Also, if the request happens to not specify any particular resource, the server will try to answer with one of the default pages: index.html, index.htm, default.html. When the server administrator wishes to perform some changes in the website structure, the server may switch to its Maintenance state, and while in this state it will answer all requests with a predefined page from a predetermined location, configured by its administrator. When starting the server one can configure the port on which it will accept new connections. All the web server configuration should be persistent between sessions.

GUI (WebServerGUI): build a graphical user interface that will allow you to start/stop and configure the web server. The graphical user interface should also display the current state of the server (Stopped/Running/Maintenance), the port at which it accepts connections and its IP address (or host name). The graphical user interface can be used to configure the server's port, its root folder, its maintenance folder, and it will also have the on/off control buttons, as well as the switch to maintenance state. More details with respect to the WebServer GUI requierements can be found here.
