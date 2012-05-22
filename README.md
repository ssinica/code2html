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

Examples
--------


<div>
<div style='font-size:12px;line-height:1.3;padding:7px;border:1px solid #E1E1E8;-moz-border-radius: 3px;-webkit-border-radius: 3px;border-radius: 3px;position:relative;background-color: #F7F7F9;'>
<div style='font-size:10px;color:#999;font-style:italic;position:absolute;top:5px;right:10px;'><span>Powered by</span>&nbsp;<a style='color:#999;' href='https://github.com/ssinica/code2html'>https://github.com/ssinica/code2html</a></div><span style='color:#3F7F7F;'>.jumbotron</span>&nbsp;<span style='color:#3F7F7F;'>.btn-large</span>&nbsp;<span style='color:#5490FF;'>{</span><br>&nbsp;&nbsp;<span style='color:#D66674;'>font-size</span><span style='color:#5490FF;'>:</span>&nbsp;<span style='color:#5490FF;'>20px</span><span style='color:#5490FF;'>;</span><br>&nbsp;&nbsp;<span style='color:#D66674;'>font-weight</span><span style='color:#5490FF;'>:</span>&nbsp;<span style='color:#5490FF;'>normal</span><span style='color:#5490FF;'>;</span><br>&nbsp;&nbsp;<span style='color:#D66674;'>padding</span><span style='color:#5490FF;'>:</span>&nbsp;<span style='color:#5490FF;'>14px</span>&nbsp;<span style='color:#5490FF;'>24px</span><span style='color:#5490FF;'>;</span><br>&nbsp;&nbsp;<span style='color:#D66674;'>margin-right</span><span style='color:#5490FF;'>:</span>&nbsp;<span style='color:#5490FF;'>10px</span><span style='color:#5490FF;'>;</span><br>&nbsp;&nbsp;<span style='color:#D66674;'>-webkit-border-radius</span><span style='color:#5490FF;'>:</span>&nbsp;<span style='color:#5490FF;'>6px</span><span style='color:#5490FF;'>;</span><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style='color:#D66674;'>-moz-border-radius</span><span style='color:#5490FF;'>:</span>&nbsp;<span style='color:#5490FF;'>6px</span><span style='color:#5490FF;'>;</span><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style='color:#D66674;'>border-radius</span><span style='color:#5490FF;'>:</span>&nbsp;<span style='color:#5490FF;'>6px</span><span style='color:#5490FF;'>;</span><br><span style='color:#5490FF;'>}</span><br>
</div>
</div>






