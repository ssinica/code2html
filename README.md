[code2html](http://ssinica.github.com/code2html/)
=========== 

Code2Html is library which converts code to HTML.

When you may need it?
---------------------

Assume you have code which you want to share on your blog. Code2Html helps
to resolve this task in very simple yet beautiful manner. Copy your code to
clipboard (Ctrl + C), press predefined key combination, and paste (Ctrl + V)
HTML snippet to your blog. That is it!

Supported code types
--------------------

For now following code types are supported:

* CSS
* HTML

Quick Start
-----------

* Download latest version from https://github.com/ssinica/code2html/downloads.
* Unzip and run code2html.exe

Prerequisites
-------------

* JRE 6.0
* Windows 64bit

Under the hood
--------------

Code2Html is written in Java. In [code2html.zip](https://github.com/ssinica/code2html/downloads) you will find engine jar
and client application. Client application uses [JIntellitype](http://melloware.com/products/jintellitype/index.html) to 
register global hotkeys, so for now it can be used only from Windows (but it can be easy rewritten to use alternative libraries
for other OS).

![code2html is ready to work!](https://github.com/downloads/ssinica/code2html/code2html.png)

Client application resides in system tray and listens for predefined key press. Distinct hot key are defined for each code type:
* Ctrl + Shift + 1  for  CSS
* Ctrl + Shift + 2  for  HTML
               
You can assign different formatting themes for each code type in file '/props/code2html.properties'. Two themes are available for the moment:

* default
* dark
