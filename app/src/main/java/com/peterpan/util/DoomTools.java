package com.peterpan.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.KeyEvent;
import com.peterpan.doom.DoomClientActivity;
import com.peterpan.doom.DoomClientActivity.eNavMethod;
import com.peterpan.doom.R;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class DoomTools 
{
    static final String TAG = "DoomTools";

	public static String DOOM_FOLDER = "/sdcard/doom" + File.separator ;
	
	// Soundtrack
	public static String DOOM_SOUND_FOLDER = "/sdcard/doom" + File.separator + "sound";
	
	// Base server URL
	public static final String URL_BASE =  "http://playerx.sf.net/";
	
	// Url prefix that has all Doom files: WADs, Sound + JNI lib
	public static final String DOWNLOAD_BASE = URL_BASE + "gwad/" ;
	
	// Url prefix that has all Sounds
	public static final String SOUND_BASE = DOWNLOAD_BASE + "sound/" ;
	
	// Game files we can handle
	public static final String[] WAD_NAMES = {"Doom Shareware",
											  "Doom",
											  "Ultimate Doom - Thy Flesh Consumed",
											  "Doom 2 - Hell on Earth",
											  "Final Doom - TNT: Evilution",
											  "Final Doom - The Plutonia Experiment",
											  "Primary Free Doom",
											  "Ultimate Free Doom",
											  "Free Doom Death Match"};
	public static final String[] DOOM_WADS = {"dooms.wad",   
		                                      "doom1.wad",
		                                      "doomu.wad",
		                                      "doom2.wad",
		                                      "tnt.wad",
		                                      "plutonia.wad",
		                                      "freedoom1.wad",
		                                      "freedoom2.wad",
		                                      "freedm.wad"};
	public static int[] WAD_RESOURCE = {R.raw.a0_dooms,  
										R.raw.a1_doom1, 
										R.raw.a2_doomu,
										R.raw.a3_doom2,
										R.raw.a4_tnt,
										R.raw.a5_plutonia,
										R.raw.a7_freedoom1,
										R.raw.a8_freedoom2,
										R.raw.a9_freedm
										}; 
	public static final int TOTAL_WADS = 9;
	public static String wad_chosen =  null;
	

	// SoundTrack
	private static final String SOUND_TRACK = "sound.zip";
	private static final String MUSIC_TRACK = "doom1_music.zip";

	/**
	 * Doom lib. To be downloaded into /data/data/APP_PKG/files
	 */
	public static final String DOOM_LIB = "peterpan.doom";
	
	// Compressed Lib in assests folder 
//	public static fina  l String DOOM_LIB_GZ = "lib.gzip";

	// These are required for the game to run
	public static final String REQUIRED_DOOM_WAD = "prboom.wad";  

	/*
	 * ASCII key symbols
	 */
	public static final int KEY_F6      	= 0x80 + 0x40;
	public static final int KEY_RIGHTARROW	= 0xae; // 174
	public static final int KEY_LEFTARROW	= 0xac; // 172
	public static final int KEY_UPARROW		= 0xad; // 173
	public static final int KEY_DOWNARROW	= 0xaf; // 175
	public static final int KEY_ESCAPE		= 27;
	public static final int KEY_ENTER		= 13;
	public static final int KEY_TAB			= 9;

	public static final int KEY_BACKSPACE	= 127;
	public static final int KEY_PAUSE		= 0xff;

	public static final int KEY_EQUALS		= 0x3d;
	public static final int KEY_MINUS		= 0x2d;

	public static final int KEY_RSHIFT		= (0x80+0x36);
	public static final int KEY_LSHIFT		= 182;
	public static final int KEY_RCTRL		= (0x80+0x1d);
	public static final int KEY_RALT		= (0x80+0x38);

	public static final int KEY_LALT		= KEY_RALT;
	public static final int KEY_SPACE		= 32;
	public static final int KEY_COMMA		= 44;
	public static final int KEY_PERIOD		= 46;

	static public void init(String appDirectory) {
		DOOM_FOLDER = appDirectory;
		DOOM_SOUND_FOLDER = DOOM_FOLDER;
	}

	/**
	 * Convert an android key to a Doom ASCII
	 * @param key
	 * @return
	 */
	static public int keyCodeToKeySym( int key ) {
		switch (key) {
		
		// Left
//		case 84: // SYM
		case KeyEvent.KEYCODE_DPAD_LEFT:
			return KEY_LEFTARROW;
		
		// Right
//		case KeyEvent.KEYCODE_AT:	
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			return KEY_RIGHTARROW;
		
		// Up
		case KeyEvent.KEYCODE_DPAD_UP:
			return KEY_UPARROW;
		
		// Down
		case KeyEvent.KEYCODE_DPAD_DOWN:
			return KEY_DOWNARROW;

		// Run?
		case KeyEvent.KEYCODE_SHIFT_RIGHT:
		case KeyEvent.KEYCODE_SHIFT_LEFT:
			return KEY_RSHIFT;

		// Alt strafe?
		case KeyEvent.KEYCODE_ALT_LEFT:
			return KEY_RALT;
			
		case 23: // DPAD
		case KeyEvent.KEYCODE_ENTER:
			return KEY_ENTER;
			
		case KeyEvent.KEYCODE_SPACE:
			return KEY_SPACE;

		case 4:	// ESC
			return KEY_ESCAPE;
		
		// Doom Map

		case KeyEvent.KEYCODE_ALT_RIGHT:
		case KeyEvent.KEYCODE_TAB:	
			return KEY_TAB;
		
		// Strafe left
		case KeyEvent.KEYCODE_COMMA:
			return KEY_COMMA;

		// Strafe right
		case KeyEvent.KEYCODE_PERIOD:
			return KEY_PERIOD;

		case KeyEvent.KEYCODE_DEL:
			return KEY_BACKSPACE;
			
		default:
			// Nav 1AQW
			if ( key == KeyEvent.KEYCODE_1) {
				key = ( DoomClientActivity.mNavMethod == eNavMethod.KBD) ? KEY_UPARROW : key + 41;
			}
			else if ( key == KeyEvent.KEYCODE_A) {
				key = ( DoomClientActivity.mNavMethod == eNavMethod.KBD) ? KEY_DOWNARROW : key + 68;
			}
			else if ( key == KeyEvent.KEYCODE_Q) {
				key = ( DoomClientActivity.mNavMethod == eNavMethod.KBD) ? KEY_LEFTARROW : key + 68;
			}
			else if ( key == KeyEvent.KEYCODE_W) {
				key = ( DoomClientActivity.mNavMethod == eNavMethod.KBD) ? KEY_RIGHTARROW : key + 68;
			}
			// A..Z
			else if (key >= 29 && key <= 54) {
	  			key += 68;
	  		}
	    	// 0..9
	  		else if (key >= 7 && key <= 16) {
	  			key += 41;
	  		}
	  		else {
	  			// Fire
	  			key = KEY_RCTRL;
	  		}
			break;
		}
		return key;
	}
	
	static public void sleep(int v) {
		try {
			Thread.sleep(v);
		}
		catch (InterruptedException e ) {
			 ;
		}
	}
	
	/**
	 * SDCARD?
	 * @return
	 */
	static public boolean hasSDCard() {
		try {
			File f = new File(DOOM_FOLDER);
			
			// Does /sdcard/doom exist?
			if ( f.exists()) return true;
			
			// Can we write into it?
			return  f.mkdir();
		} catch (Exception e) {
			System.err.println(e.toString());
			return false;
		}
	}
	
	/**
	 * Ping the download server
	 * @return
	 */
	static public boolean pingServer() {
		try {
			WebDownload wd = new WebDownload(URL_BASE);
			wd.doGet();
			int rc = wd.getResponseCode();
			Log.d(TAG, "PingServer Response:" + rc);
			return  rc == 200;
		} catch (Exception e) {
			Log.e(TAG, "PingServer: " + e.toString());
			return false;
		}
	}
	
	/**
	 * WAD exist?
	 * @param idx
	 * @return
	 */
	static public boolean wadExists ( String wadName) { //int idx) {
		final String path = DOOM_FOLDER + File.separator +  wadName; //DOOM_WADS[idx];
		Log.d(TAG,"WAD name is '" + path + "'");
		return new File(path).exists();
	}
	
	static public void hardExit ( int code) {
		System.exit(code);
	}
	

    /**
     * Sound present for a WAD?
     * @param wadName
     * @return
     */
    public static boolean hasSound() { 
    	Log.d(TAG, "Sound folder: " + DOOM_SOUND_FOLDER);
    	return new File(DOOM_SOUND_FOLDER).exists(); 
    }
    
    /**
     * Get the sound folder name for a game file 
     * @param wadName
     * @return
     */
    public static File getSoundFolder() { 
    	return new File(DOOM_SOUND_FOLDER);
    }
    
    
    /**
     * Unzip utility
     * @param is
     * @param dest
     * @throws IOException
     */
    public static void unzip (InputStream is, File dest) throws IOException
    {
    	if ( !dest.isDirectory()) 
    		throw new IOException("Invalid Unzip destination " + dest );
    	
    	ZipInputStream zip = new ZipInputStream(is);
    	
    	ZipEntry ze;
    	
    	while ( (ze = zip.getNextEntry()) != null ) {
    		final String path = dest.getAbsolutePath() + File.separator + ze.getName();
    		
    		//System.out.println(path);
    		
    		FileOutputStream fout = new FileOutputStream(path);
    		byte[] bytes = new byte[1024];
    		
            for (int c = zip.read(bytes); c != -1; c = zip.read(bytes)) {
              fout.write(bytes,0, c);
            }
            zip.closeEntry();
            fout.close();    		
    	}
    }
 
    /**
     * Write to a stream
     * @param in
     * @param out
     * @throws IOException
     */
    public static void writeToStream(InputStream in , OutputStream out) throws IOException {
		byte[] bytes = new byte[2048];
		
        for (int c = in.read(bytes); c != -1; c = in.read(bytes)) {
          out.write(bytes,0, c);
        }
        in.close();
        out.close();    		

    }
    
	
    
	/**
	 * Ckech 4 sdcard
	 * @return
	 */
	public static  boolean checkSDCard(Context ctx) {
		boolean sdcard = DoomTools.hasSDCard();
		
		if ( ! sdcard) {
			DialogTool.MessageBox(ctx, "No SDCARD", "An SDCARD is required to store game files.");
			return false;
		}		
		return true;
	}

	/**
	 * Make sure you have a web cn
	 * @param ctx
	 * @return
	 */
	public static  boolean checkServer(Context ctx) {
		boolean alive = DoomTools.pingServer();
		
		if ( ! alive) {
			DialogTool.MessageBox(ctx, "Sever Ping Failed", "Make sure you have a web connection.");
			return false;
		}		
		return true;
	}
	
	
	/**
	 * Clean old JNI libs & other files. 
	 */
	public static void cleanUp (final Context ctx, final int wadIdx) {
		AlertDialog d = DialogTool.createAlertDialog(ctx, "Clean Up?"
				, "This will remove game files from the sdcard. Use this option if you are experiencing problems.");
		
		d.setButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
        		// remove JNI lib
            	Log.d(TAG, "Removing " + DOOM_LIB);
            	
        		File f = ctx.getFileStreamPath(DOOM_LIB);
        		if ( f.exists()) f.delete();
        		
        		// clean sounds
        		deleteSounds(); //wadIdx);
        		
        		// game files
        		deleteWads();
            }
        });
		d.setButton2("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                /* User clicked OK so do some stuff */
            }
        });
		d.show();
	}

	/**
	 * Clean sounds
	 * @param wadIdx
	 */
	private static void deleteSounds() //int wadIdx) 
	{
		File folder = getSoundFolder(); //DoomTools.DOOM_WADS[wadIdx]);
		
		if ( !folder.exists()) {
			Log.e(TAG, "Error: Sound folder " + folder + " not found.");
			return;
		}
		
		File[] files = folder.listFiles();
		
		for (int i = 0; i < files.length; i++) {
			
			if (files[i].exists() )
				files[i].delete();
		}
		if ( folder.exists() ) folder.delete();
	}
	
	/**
	 * Cleanup game files
	 */
	private static void deleteWads() {
		for (int i = 0; i < DOOM_WADS.length; i++) {
			final File f = new File(DOOM_FOLDER + File.separator + DOOM_WADS[i]);
			
			if ( f.exists())
				f.delete();
		}
	}

	/**
	 * Install JNI game lib
	 * @param ctx
	 * @param dir
	 * @throws IOException
	 */
	/*
	public static void installJniLib(Context ctx, boolean force) throws IOException 
	{
		// JNI lib /data/data/game.doom/files
		File lib = ctx.getFileStreamPath(DoomTools.DOOM_LIB);

		if ( lib.exists() && ! force ) {
			Log.d(TAG, "Install Lib:" + lib + " already exists!" );
			return;
		}
		
		Log.d(TAG, "Installing game lib in /data/data/<PKG>/files");
		
		writeToStream( 
				new GZIPInputStream( ctx.getAssets().open(DOOM_LIB_GZ))
				, ctx.openFileOutput(DOOM_LIB, 0)
		);
		
	}
	*/
	
	public static void createFolders () {
		Log.d(TAG, "Creating directories ");
		File pgmFolder = new File(DoomTools.DOOM_FOLDER);
        pgmFolder.mkdir();
        File sndFolder = new File(DoomTools.DOOM_SOUND_FOLDER);
        sndFolder.mkdir();
	}
	
	public static void copyGameFiles (Context ctx) {
		copyFileToAppFolder(ctx,"prboom.wad",0);
		for (int i =0; i < TOTAL_WADS; i++)
			copyFileToAppFolder (ctx,DOOM_WADS[i], WAD_RESOURCE[i]);
		DialogTool.Toast(ctx, "All game files installed");
	}
	
	public static void copyFileToAppFolder (Context ctx,String name, int resource) {
		 File file = new File(DOOM_FOLDER, name);
		 if (file.exists()) {
			 Log.d(TAG,name + " is already installed");
			 return;
 		 }
		 Log.d (TAG, "Installing " + name + "...");
		 if (resource != 0 ) {
			unzipFileFromRawToAppFolder(ctx, name, resource);
			return;
		 }
		 boolean failed = false;
		 try {
			 FileOutputStream os = new FileOutputStream(file);
			 if (copyFile(ctx.getAssets().open(name), os) == -1)
				failed = true;
		 } catch (Exception e) {
			 Log.d(TAG,"Could not install file " + name);
			 failed = true;
	     }
		 if (failed)
			 file.delete();
	}
	
	
	
	public static void unzipFileFromRawToAppFolder (Context ctx, String name, int resource) {
		 Log.d (TAG, "Unziping and Installing the game file " + name + " from zipfile." );
		 DialogTool.Toast(ctx, "First time installation of game " + name + " ... please wait.");
		 File folder = new File(DoomTools.DOOM_FOLDER);
		 try {
			 InputStream is = ctx.getResources().openRawResource(resource); 
			 unzip(is, folder);
		 } catch (Exception e) {
			 Log.d(TAG,"Could not install file " + name);
	     }
	}
	
	public static void copySavedGames (Context ctx) {
		 for (int i = 0; i < 10; i++) {
			 String name = "prbmsav" + i + ".dsg"; 
			 boolean failed = false;
			 File file = new File(DOOM_FOLDER, name);
			 if (file.exists()) {
				 Log.d(TAG,name + " is already installed");
				 continue;
			 }
			 Log.d (TAG, "Installing " + name + "...");
			 try {
				 FileOutputStream os = new FileOutputStream(file);
				 if (copyFile(ctx.getAssets().open("prbmsav.dsg"), os) == -1)
					failed = true;
			 } catch (Exception e) {
				 Log.d(TAG,"Could not install file " + name);
				 failed = true;
		     }
			 if (failed)
				 file.delete();
		 }
		 DialogTool.Toast(ctx, "Saved game files restored");
	}
	
	
	public static void copyMusicFiles (Context ctx, String name) {
		 File file = new File(DOOM_SOUND_FOLDER, name);
		 boolean failed = false;
		 if (file.exists()) {
			 Log.d(TAG,name + " is already installed");
			 return;
		 }
		 Log.d(TAG,"installing " + name);
		 try {
			 FileOutputStream os = new FileOutputStream(file);
			 if (copyFile(ctx.getAssets().open(name), os) == -1)
				failed = true;
		 } catch (Exception e) {
			 Log.d(TAG,"Could not install file " + name);
			 e.printStackTrace();
			 failed = true;
	     }
		 if (failed)
			 file.delete();
	}
	
	public static int copyFile(InputStream in, OutputStream out)
		    throws IOException {
			byte[] bytes = new byte[2048];
			int bc = 0;

			try {
				for (int c = in.read(bytes); c != -1; c = in.read(bytes)) {
					out.write(bytes, 0, c);
					bc += 2048;
				}
				in.close();
				out.close();
			} catch (Exception e) {
				Log.d(TAG,"Error copying file. BC=" + bc);
				e.printStackTrace();
				return -1;
			}
			return 0;
	}
	
	
	public static void copySoundTrack(Context ctx) {
		File folder = new File( DoomTools.DOOM_SOUND_FOLDER);
		try {
			installSoundTrack (ctx,folder);
			DialogTool.Toast(ctx, "Sound track installled");
		}
		catch (Exception e) {
			//DialogTool.PostMessageBox(ctx, "Soundtrack install failed: " + e.getMessage());
			Log.d(TAG,"Soundtrack install failed: " + e.getMessage());
		}
	}

	public static void installSoundTrack(Context ctx, File dir) throws IOException {
		File file = new File(DOOM_SOUND_FOLDER, "DSBAREXP.wav");
		if (!file.exists()) {
			Log.d(TAG, "Installing sound track " + SOUND_TRACK + " in " + dir);
			unzip(ctx.getAssets().open(SOUND_TRACK), dir);
		} 
		else {
			Log.d(TAG, "Already installed: sound track " + SOUND_TRACK + " in " + dir);
		}
		
		file = new File(DOOM_SOUND_FOLDER, "d1e1m1.ogg");
		if (!file.exists()) {
			Log.d(TAG, "Installing sound track " + MUSIC_TRACK + " in " + dir);
			unzip(ctx.getAssets().open(MUSIC_TRACK), dir);
		}
		else { 
			Log.d(TAG, "Already installed: sound track " + MUSIC_TRACK + " in " + dir);
		}
	}
	
	/**
	 * TODO: Validate server IP for multiplayer
	 * @param serverPort
	 * @return
	 */
	public static boolean validateServerIP(String serverPort) {
		return serverPort != null && serverPort.length() > 0 ;
	}
	
	/**
	 * Add a default config to DOOM_FOLDER with default key bindings
	 * @param ctx
	 */
//	static final String DEFAULT_CFG = "prboom.cfg";
//	
//	public static void createDefaultDoomConfig(Handler handler, Context ctx) {
//		File dest = new File(DOOM_FOLDER + DEFAULT_CFG);
//		
//		// Skip if config exists
//		if ( dest.exists()) {
//			Log.w(TAG, "A default Doom config already exists in " + dest);
//			return;
//		}
//		
//		try {
//			BufferedInputStream is = new BufferedInputStream( ctx.getAssets().open(DEFAULT_CFG));
//			FileOutputStream fos = new FileOutputStream(dest);
//			
//			byte[] buf = new byte [1024];
//			
//			// let the user know
//			Toast(handler, ctx, "Default movement keys: 1AQW");
//			
//			Log.d(TAG, "Writing a default DOOM config to " + dest);
//			
//            for (int read = is.read(buf); read != -1; read = is.read(buf)) {
//                fos.write(buf,0, read);
//            }
//
//		} catch (Exception e) {
//			Log.e(TAG, "Error saving default DOOm config: " + e.toString());
//		}
//	}
	
}
