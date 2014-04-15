package com.selenium.utilities.recorder;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.testng.annotations.Test;

import com.selenium.utilities.GlobalDefinition;

public class ScreenRecorderTest {

  @Test
  public void startRecording() {
  
      
	 
	  try {
		  RecordConfig recConfig=new RecordConfig();
		  recConfig.setFramesRate(24);
		  recConfig.setVideoFile(new File(GlobalDefinition.DESKTOP_SCREEN_RECORDER));
		  Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		  recConfig.setFrameDimension(new Rectangle(dim));
		  recConfig.setCursorImage(ImageIO.read(new File(GlobalDefinition.MOUSE_CURSE_ICON)));
		  ScreenRecorder recorder=new ScreenRecorder();
		  recorder.startRecording(recConfig);
		  Thread.sleep(50000);
		  recorder.stopRecording();
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
}
