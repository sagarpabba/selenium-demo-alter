Do one of the following:

  1. Checkout the wiki from svn. From the command line, this is
svn checkout https://code.google.com/p/selenium-demo-alter/svn/wiki HeeksCAD/wiki
  1. Checkout ALL of heekscad (not just the source). From the command line, this is
svn checkout https://code.google.com/p/selenium-demo-alter/svn HeeksCAD
  1. Put your image in the folder HeeksCAD/wiki/images
Add it to svn, using svn add 

&lt;filename&gt;


  1. Change its mime-type so that it will show up. For a PNG image:
svn propset svn:mime-type image/png myimage.png, **this step is very important**;
Link to your image from a wiki page - use this url:
https://code.google.com/p/selenium-demo-alter/svn/
  1. check in the updated code then;