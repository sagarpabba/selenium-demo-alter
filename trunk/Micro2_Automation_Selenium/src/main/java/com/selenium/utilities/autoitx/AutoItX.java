package com.selenium.utilities.autoitx;

import com.selenium.utilities.GlobalDefinition;
import com.sun.jna.*;


public interface AutoItX extends com.sun.jna.Library  {
	
	//TODO: document and implement a way of avoiding the hard coding of a path to autoitx.dll - or at least make it relative
	//https://jna.dev.java.net/
	// AutoITx can be downloaded from  http://www.autoitscript.com/autoit3/downloads.shtml
	// The line below uses the system property to load in the dll from a resource folder in the source workspace
    //Autoitx INSTANCE = (Autoitx) Native.loadLibrary(System.getProperty("user.dir") + "/src/test/resources/autoit/AutoItX3.dll", Autoitx.class);
	//By default AutoIT installs itself to C:\Program Files\AutoIt3\AutoItX\AutoItX3.dll
	AutoItX INSTANCE = (AutoItX) Native.loadLibrary(GlobalDefinition.AUTOITX_FILE, AutoItX.class);
   
	public static class LPPOINT extends Structure{
	    public int X;
	    public int Y;
	}
	
	public static int AU3_INTDEFAULT = -2147483647;
	

    public void  AU3_Init();
    public int AU3_error();
    public int  AU3_AutoItSetOption(String szOption, int nValue);
    public void  AU3_BlockInput(int nFlag); //1 = disable user input, 0 enable user input (to have auto it run without interference)
    public void  AU3_CDTray(String szDrive, String szAction); // drive:    ,"open" or "closed"
    public void  AU3_ClipGet(byte [] szClip, int nBufSize);
    public void  AU3_ClipPut(String szClip);
    public int  AU3_ControlClick(String szTitle, String szText, String szControl, String szButton, int nNumClicks,  int nX,  int nY);
    public void  AU3_ControlCommand(String szTitle, String szText, String szControl, String szCommand, String szExtra, byte [] szResult, int nBufSize);
    public void  AU3_ControlListView(String szTitle, String szText, String szControl, String szCommand, String szExtra1, String szExtra2, byte [] szResult, int nBufSize);
    public int  AU3_ControlDisable(String szTitle, String szText, String szControl);
    public int  AU3_ControlEnable(String szTitle, String szText, String szControl);
    public int  AU3_ControlFocus(String szTitle, String szText, String szControl);
    public void  AU3_ControlGetFocus(String szTitle, String szText, byte [] szControlWithFocus, int nBufSize);
    public void  AU3_ControlGetHandle(String szTitle,  String szText, String szControl, byte [] szRetText, int nBufSize);
    public int  AU3_ControlGetPosX(String szTitle, String szText, String szControl);
    public int  AU3_ControlGetPosY(String szTitle, String szText, String szControl);
    public int  AU3_ControlGetPosHeight(String szTitle, String szText, String szControl);
    public int  AU3_ControlGetPosWidth(String szTitle, String szText, String szControl);
    public void  AU3_ControlGetText(String szTitle, String szText, String szControl, byte [] szControlText, int nBufSize);
    public int  AU3_ControlHide(String szTitle, String szText, String szControl);
    public int  AU3_ControlMove(String szTitle, String szText, String szControl, int nX, int nY,  int nWidth,  int nHeight);
    public int  AU3_ControlSend(String szTitle, String szText, String szControl, String szSendText,  int nMode);
    public int  AU3_ControlSetText(String szTitle, String szText, String szControl, String szControlText);
    public int  AU3_ControlShow(String szTitle, String szText, String szControl);
    public void  AU3_DriveMapAdd(String szDevice, String szShare, int nFlags,  String szUser,  String szPwd, byte [] szResult, int nBufSize);
    public int  AU3_DriveMapDel(String szDevice);
    public void  AU3_DriveMapGet(String szDevice, byte [] szMapping, int nBufSize);
    public int  AU3_IniDelete(String szFilename, String szSection, String szKey);
    public void  AU3_IniRead(String szFilename, String szSection, String szKey, String szDefault, byte [] szValue, int nBufSize);
    public int  AU3_IniWrite(String szFilename, String szSection, String szKey, String szValue);
    public int  AU3_IsAdmin();
    public int  AU3_MouseClick( String szButton,  int nX,  int nY,  int nClicks,  int nSpeed);
    public int  AU3_MouseClickDrag(String szButton, int nX1, int nY1, int nX2, int nY2,  int nSpeed);
    public void  AU3_MouseDown( String szButton);
    public int  AU3_MouseGetCursor();
    public int  AU3_MouseGetPosX();
    public int  AU3_MouseGetPosY();
    public int  AU3_MouseMove(int nX, int nY,  int nSpeed);
    public void  AU3_MouseUp( String szButton);
    public void  AU3_MouseWheel(String szDirection, int nClicks);
    public int  AU3_Opt(String szOption, int nValue);
    public int  AU3_PixelChecksum(int nLeft, int nTop, int nRight, int nBottom,  int nStep);
    public int  AU3_PixelGetColor(int nX, int nY);
    public void  AU3_PixelSearch(int nLeft, int nTop, int nRight, int nBottom, int nCol,  int nVar,  int nStep, LPPOINT pPointResult);
    public int  AU3_ProcessClose(String szProcess);
    public int  AU3_ProcessExists(String szProcess);
    public int  AU3_ProcessSetPriority(String szProcess, int nPriority);
    public int  AU3_ProcessWait(String szProcess,  int nTimeout);
    public int  AU3_ProcessWaitClose(String szProcess,  int nTimeout);
    public int  AU3_RegDeleteKey(String szKeyname);
    public int  AU3_RegDeleteVal(String szKeyname, String szValuename);
    public void  AU3_RegEnumKey(String szKeyname, int nInstance, byte [] szResult, int nBufSize);
    public void  AU3_RegEnumVal(String szKeyname, int nInstance, byte [] szResult, int nBufSize);
    public void  AU3_RegRead(String szKeyname, String szValuename, byte [] szRetText, int nBufSize);
    public int  AU3_RegWrite(String szKeyname, String szValuename, String szType, String szValue);
    public int  AU3_Run(String szRun,  String szDir,  int nShowFlags);
    public int  AU3_RunAsSet(String szUser, String szDomain, String szPassword, int nOptions);
    public int  AU3_RunWait(String szRun,  String szDir,  int nShowFlags);
    public void  AU3_Send(String szSendText,  int nMode);
    public int  AU3_Shutdown(int nFlags);
    public void  AU3_Sleep(int nMilliseconds);
    public void  AU3_StatusbarGetText(String szTitle,  String szText,  int nPart, byte [] szStatusText, int nBufSize);
    public void  AU3_ToolTip(String szTip,  int nX,  int nY);
    public int  AU3_WinActive(String szTitle,  String szText);
	public void AU3_WinActivate(String szTitle, String szText);
    public int  AU3_WinClose(String szTitle,  String szText);
    public int  AU3_WinExists(String szTitle,  String szText);
    public int  AU3_WinGetCaretPosX();
    public int  AU3_WinGetCaretPosY();
    public void  AU3_WinGetClassList(String szTitle,  String szText, byte [] szRetText, int nBufSize);
    public int  AU3_WinGetClientSizeHeight(String szTitle,  String szText);
    public int  AU3_WinGetClientSizeWidth(String szTitle,  String szText);
    public void  AU3_WinGetHandle(String szTitle,  String szText, byte [] szRetText, int nBufSize);
    public int  AU3_WinGetPosX(String szTitle,  String szText);
    public int  AU3_WinGetPosY(String szTitle,  String szText);
    public int  AU3_WinGetPosHeight(String szTitle,  String szText);
    public int  AU3_WinGetPosWidth(String szTitle,  String szText);
    public void  AU3_WinGetProcess(String szTitle,  String szText, byte [] szRetText, int nBufSize);
    public int  AU3_WinGetState(String szTitle,  String szText);
    public void  AU3_WinGetText(String szTitle,  String szText, byte [] szRetText, int nBufSize);
    public void  AU3_WinGetTitle(String szTitle,  String szText, byte [] szRetText, int nBufSize);
    public int  AU3_WinKill(String szTitle,  String szText);
    public int  AU3_WinMenuSelectItem(String szTitle,  String szText, String szItem1, String szItem2, String szItem3, String szItem4, String szItem5, String szItem6, String szItem7, String szItem8);
    public void  AU3_WinMinimizeAll();
    public void  AU3_WinMinimizeAllUndo();
    public int  AU3_WinMove(String szTitle,  String szText, int nX, int nY,  int nWidth,  int nHeight);
    public int  AU3_WinSetOnTop(String szTitle,  String szText, int nFlag);
    public int  AU3_WinSetState(String szTitle,  String szText, int nFlags);
    public int  AU3_WinSetTitle(String szTitle,  String szText, String szNewTitle);
    public int  AU3_WinSetTrans(String szTitle,  String szText, int nTrans);
    public int  AU3_WinWait(String szTitle,  String szText,  int nTimeout);
    public int  AU3_WinWaitActive(String szTitle,  String szText,  int nTimeout);
    public int  AU3_WinWaitClose(String szTitle,  String szText,  int nTimeout);
    public int  AU3_WinWaitNotActive(String szTitle,  String szText,  int nTimeout);


}
