package com.selenium.utilities.autoitx;

import java.io.File;

import org.testng.annotations.Test;

import com.jacob.com.LibraryLoader;

public class AutoItXUtilsTest {

  @Test
  public void AutoItXUtils() {
  //  throw new RuntimeException("Test not implemented");
	  File file = new File("test-resources", "jacob-1.17-x86.dll"); //path to the jacob dll
      System.setProperty(LibraryLoader.JACOB_DLL_PATH, file.getAbsolutePath());

      AutoItX x = new AutoItX();
    //  String notepad = "Untitled - Notepad";
    //  String testString = "this is a test.";
      x.run("notepad.exe");
  }
}
