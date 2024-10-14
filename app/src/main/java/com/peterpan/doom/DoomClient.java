package com.peterpan.doom;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.File;
import com.peterpan.util.audio.AudioManager;
import doom.jni.Natives;
import com.peterpan.util.DialogTool;
import com.peterpan.util.DoomTools;

public class DoomClient extends BaseActivity implements Natives.EventListener
{
    private static final String TAG = "DoomClient";
    private static boolean installed = false;
    private static boolean first_time = true;

    private boolean pressed[] = {false, false};
    private final int TOTAL_TRICK_KEYS = 2;

    static private Bitmap mBitmap;
    static private ImageView mView;

    private int mWidth;
    private int mHeight;
    private static int mOrientation = 0; // portrait

    public static final Handler mHandler = new Handler();

    private int wadIdx = 0;

    // Audio Cache Manager
    private AudioManager mAudioMgr;

    // Sound?
    private boolean mSound = true;

    private static boolean mGameStarted = false;

    // multi
    private static boolean mMultiPlayer = false;

    // for Multi player
    private String mServerPort;

    // Navigation
    public static enum eNavMethod  {KBD, PANEL, ACC};
    public static eNavMethod mNavMethod = eNavMethod.KBD;

    // Screen size
    public int height;
    public int pad_height;
    public int width;

    // Virtual Screen Size
    public final int HEIGHT     = 480;
    public final int PAD_HEIGHT = 240;
    public final int WIDTH      = 320;

    // Api level
    public int api_level= 0;

    public boolean portrait = true;

    private DoomClient dClient = this;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // No title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.doom);

        mView = (ImageView)findViewById(R.id.doom_iv);

        api_level =  android.os.Build.VERSION.SDK_INT;
        Log.d (TAG,"API Level Detected = " + api_level);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        if (dm.widthPixels < dm.heightPixels ) {
            portrait = true;
            width   = dm.widthPixels;
            height  = dm.heightPixels;
            pad_height = (int) (width * 0.75);
        }
        else {
            portrait = false;
            width = dm.heightPixels;
            height = dm.widthPixels;
            pad_height = (int) (width * 0.75);
        }

        Log.d (TAG,"Screen height is " + height + " width is " + width);

        if (isPortrait()) {
            // resize controls for portrait
            // resize snes layout
            resizeLayout (R.id.pan_ctls,pad_height,width);

            // relocate/resize objects within it
            relocateAndResize (R.id.btn_up     , 60, 48,48,48);
            relocateAndResize (R.id.btn_down   ,141, 48,48,48);
            relocateAndResize (R.id.btn_left   , 99,  0,48,48);
            relocateAndResize (R.id.btn_right  , 99, 96,48,48);
            relocateAndResize (R.id.btn_select ,192,110,48,48);
            relocateAndResize (R.id.btn_open   ,192,160,48,48);
            relocateAndResize (R.id.btn_stright, 63,223,48,48);
            relocateAndResize (R.id.btn_stleft , 99,174,48,48);
            relocateAndResize (R.id.btn_run    , 99,270,48,48);
            relocateAndResize (R.id.btn_fire   ,141,223,48,48);
            relocateAndResize (R.id.btn_menu   ,192,  2,48,48);
            relocateAndResize (R.id.btn_map    ,192,272,48,48);
            relocateAndResize (R.id.btn_trans_1,  3,  2,40,40);
            relocateAndResize (R.id.btn_trans_2,  3, 56,40,40);
            relocateAndResize (R.id.btn_trans_3,  3,110,40,40);
            relocateAndResize (R.id.btn_trans_4,  3,164,40,40);
            relocateAndResize (R.id.btn_trans_5,  3,218,40,40);
            relocateAndResize (R.id.btn_trans_6,  3,272,40,40);
            relocateAndResize (R.id.btn_trans_7, 45,135,40,40);

            // resize image layout
            resizeLayout (R.id.top_panel, pad_height, width);

            // resize objects on image layout
            absoluteResize    (R.id.doom_iv       ,pad_height	,width);
            relocateAndResize (R.id.btn_exit      ,48			,48);
            relocateAndResize (R.id.btn_quicksave ,48			,48);
            relocateAndResize (R.id.btn_trick     ,48			,48);
        }
        else {
            // resize objects within it
            relocateAndResize (R.id.btn_up     ,48,48);
            relocateAndResize (R.id.btn_down   ,48,48);
            relocateAndResize (R.id.btn_left   ,48,48);
            relocateAndResize (R.id.btn_right  ,48,48);
            relocateAndResize (R.id.btn_select ,48,48);
            relocateAndResize (R.id.btn_open   ,48,48);
            relocateAndResize (R.id.btn_stright,48,48);
            relocateAndResize (R.id.btn_stleft ,48,48);
            relocateAndResize (R.id.btn_map    ,48,48);
            relocateAndResize (R.id.btn_fire   ,48,48);
            relocateAndResize (R.id.btn_menu   ,48,48);
            relocateAndResize (R.id.btn_run    ,48,48);
            relocateAndResize (R.id.btn_trans_1,48,48);
            relocateAndResize (R.id.btn_trans_2,48,48);
            relocateAndResize (R.id.btn_trans_3,48,48);
            relocateAndResize (R.id.btn_trans_4,48,48);
            relocateAndResize (R.id.btn_trans_5,48,48);
            relocateAndResize (R.id.btn_trans_6,48,48);
            relocateAndResize (R.id.btn_trans_7,48,48);
            relocateAndResize (R.id.btn_exit      ,48,48);
            relocateAndResize (R.id.btn_quicksave ,48,48);
            relocateAndResize (R.id.btn_trick     ,48,48);

            absoluteResize (R.id.doom_iv,width,height);
            setImageSize   (height,width);
        }

        if (mGameStarted) {
            setGameUI();
            setupPanControls();
            return;
        }

        // Pan controls
        setupPanControls();

        if (!installed) {
            Thread thrL = new Thread(null, new installWorker(), "Install Worker");
            thrL.start();
        }

        final View v0 = findViewById(R.id.pan_ctls);
        final View v1 = findViewById(R.id.other_ctls);
        final View v2 = findViewById(R.id.another_ctls);
        //final View v3 = findViewById(R.id.guns_ctls);
        final View v4 = findViewById(R.id.trick_ctls);
        DoomClient.mNavMethod = eNavMethod.PANEL;

        // show controls
        v0.setVisibility(View.VISIBLE);
        v1.setVisibility(View.VISIBLE);
        v2.setVisibility(View.VISIBLE);
        //v3.setVisibility(View.VISIBLE);
        v4.setVisibility(View.VISIBLE);

        //AdBuddiz.setTestModeActive();
        //AdBuddiz.setPublisherKey("TEST_PUBLISHER_KEY");
        //AdBuddiz.cacheAds(this); // this = current Activity
        //AdBuddiz.showAd(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
//        loadSensors();
    }

    @Override
    protected void onStop() {
        super.onStop();
//    	unLoadSensors();
    }


    /**
     * App menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
//    	menu.add(0, 0, 0, "Start").setIcon(R.drawable.icon);
//    	menu.add(0, 1, 1, "Multi Player").setIcon(R.drawable.icon);

//    	menu.add(0, 2, 2, "Install Game").setIcon(R.drawable.install);
        menu.add(0, 3, 3, "Navigation").setIcon(R.drawable.nav);
//    	menu.add(0, 4, 4, "Help").setIcon(R.drawable.help);
//    	menu.add(0, 5, 5, "Cleanup").setIcon(R.drawable.cleanup);
        menu.add(0, 6, 6, "Exit").setIcon(R.drawable.exit);
        return true;
    }

    /**
     * Menu selection
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case 0:
                if ( mGameStarted) {
                    MessageBox("Game already in progress.");
                    return true;
                }
                mMultiPlayer = false;
                showLauncherDialog(this, mMultiPlayer);
                return true;

            case 1:
                // multi-player
                if ( mGameStarted) {
                    MessageBox("Game already in progress.");
                    return true;
                }
                mMultiPlayer = true;
                showLauncherDialog(this, mMultiPlayer);
                return true;

            case 2:
                if ( mGameStarted) {
                    MessageBox("Can't install while game in progress.");
                    return true;
                }

                // sdcard required
                if ( ! DoomTools.checkSDCard(this) ) return true;

                // Download Game file
                DialogTool.showDownloadDialog(this);
                return true;

            case 3:
                DialogTool.showNavMethodDialog(this); //, mSensorManager, mSensor );
                return true;

            case 4:
                // Help
                DialogTool.launchBrowser(DoomClient.this, "http://playerx.sf.net/doom/controls.html");
                return true;
            case 5:
                // Cleanup
                if ( mGameStarted) {
                    MessageBox("Can't cleanup while game in progress.");
                    return true;
                }

                DoomTools.cleanUp(DoomClient.this, wadIdx);
                return true;

            case 6:
                // Exit
                DoomTools.hardExit(0);
                return true;

        }

        return false;
    }

    /**
     * Option dialog
     * @param ctx
     */
    /*
	public void showLauncherDialog(final Context ctx, final boolean multiPlayer)
	{
        //LayoutInflater factory = LayoutInflater.from(ctx);
        //final View view = factory.inflate(R.layout.options, null);

        //if (Natives.wad_chosen != null) {
        //	Log.d(TAG,"wad chosen is " + Natives.wad_chosen);
        //	play (Natives.wad_chosen);
        //}

        // load GUI data
        DialogTool.setLauncherDlgOptionsUI(ctx , view, multiPlayer);

        AlertDialog dialog = new AlertDialog.Builder(ctx)
	        .setIcon(R.drawable.icon)
	        .setTitle("Choose Doom game:")
	        .setView(view)
	        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) {
	        		wadIdx = ((Spinner)view.findViewById(R.id.s_files)).getSelectedItemPosition();
	        		String wad;

	        		if ( wadIdx == DoomTools.TOTAL_WADS ) {
	        			// Other wad, use text box, wad must live in /sdcard/doom
	        			wad = ((EditText)view.findViewById(R.id.t_wad)).getText().toString();
	        			Log.d(TAG,"wad chosen is " + wad);
	        		}
	        		else{
	        			wad = DoomTools.DOOM_WADS[wadIdx];
	        		}

	        		// Sound 0 = y, 1 == n
	        		//mSound = ((Spinner)view.findViewById(R.id.s_sound)).getSelectedItemPosition() == 0;

	        		// Size P = 320 x 320 L: 320 x 200
	        		//mPortrait = ((Spinner)view.findViewById(R.id.s_size)).getSelectedItemPosition() == 1;

	        		if ( multiPlayer) {
	        			mServerPort = ((EditText)view.findViewById(R.id.t_server)).getText().toString();

	        			if ( ! DoomTools.validateServerIP(mServerPort)) {
	        				DialogTool.Toast(mHandler, DoomClient.this, "Invalid Server IP: " + mServerPort);

	        				showLauncherDialog(ctx, multiPlayer);
	        				return;
	        			}
	        		}


	        		play(wad);

	            }
	        })
	        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) {
	                dialog.dismiss();
	            }
	        })
	        .create();

        dialog.show();


	}
	*/

    private void showLauncherDialog(final Context ctx, final boolean multiPlayer) {
        final String[] mListItems = DoomTools.WAD_NAMES;

        AlertDialog dialog;

        AlertDialog.Builder dialogbld = new AlertDialog.Builder(this);
        dialogbld.setIcon(R.drawable.icon);
        dialogbld.setTitle("Choose which game to start");

        dialogbld.setItems(mListItems, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    default:
                        DoomTools.wad_chosen = DoomTools.DOOM_WADS[which];
                        Log.d (TAG,"Game picked " + which + " wad is " + DoomTools.wad_chosen);
						/*
		        		if ( multiPlayer) {
		        			mServerPort = ((EditText)view.findViewById(R.id.t_server)).getText().toString();

		        			if ( ! DoomTools.validateServerIP(mServerPort)) {
		        				DialogTool.Toast(mHandler, DoomClient.this, "Invalid Server IP: " + mServerPort);

		        				showLauncherDialog(ctx, multiPlayer);
		        				return;
		        			}
		        		}
	                    */
                        play (DoomTools.wad_chosen);
                        break;

                    case DoomTools.TOTAL_WADS:
                        // TODO
                        break;
                }
                dialog.dismiss();
            }
        });

        dialogbld.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,	int whichButton) {
                dialog.dismiss();
            }
        });

        dialog = dialogbld.create();
        dialog.show();

    }


    /**
     * Play
     */
    private void play (final String wad)
    {
        int count = 0;
        if ( ! DoomTools.checkSDCard(this) ) return;

        while (!installed && ++count < 30) {
            DoomTools.sleep(1000);
        }

        // Make sure all required files are in place
        if  ( ! checkSanity(wad) ) {
            return;
        }

        // Load lib
        if (!loadLibrary()) {
            // this should not happen
            return;
        }

        if ( ! DoomTools.hasSound() )
            DialogTool.Toast(this, "Warning: Soundtrack is missing.");

        // Hide download widgets/ show game IV
        setGameUI();

        // start doom!
        startGame(wad);
    }

    /**
     * Make sure all required stuff is in /sdcard/doom
     * @return
     */
    private boolean checkSanity(final String wadName) {
        // check for game file
        if ( ! DoomTools.wadExists(wadName)) {
            MessageBox("Missing Game file " + DoomTools.DOOM_FOLDER + wadName + ". Try installing a game.");
            return false;
        }


        // check 4 prboom.wad
        File prboom = new File(DoomTools.DOOM_FOLDER + DoomTools.REQUIRED_DOOM_WAD);

        if ( ! prboom.exists()) {
            MessageBox("Missing required Game file "  + prboom);
            return false;
        }

        return true;
    }

    /**
     * Hide main layout/show image vire (game)
     */
    private void setGameUI() {
        ((ImageView)findViewById(R.id.doom_iv)).setBackgroundDrawable(null);
        switch (mNavMethod) {
            case KBD:
                findViewById(R.id.pan_ctls).setVisibility(View.GONE);
                findViewById(R.id.other_ctls).setVisibility(View.GONE);
                findViewById(R.id.another_ctls).setVisibility(View.GONE);
                findViewById(R.id.guns_ctls).setVisibility(View.GONE);
                findViewById(R.id.trick_ctls).setVisibility(View.GONE);
                findViewById(R.id.btn_quicksave).setVisibility(View.GONE);
                findViewById(R.id.btn_menu).setVisibility(View.GONE);
                findViewById(R.id.btn_run).setVisibility(View.GONE);
                findViewById(R.id.btn_fire).setVisibility(View.GONE);
                findViewById(R.id.btn_up).setVisibility(View.GONE);
                findViewById(R.id.btn_down).setVisibility(View.GONE);
                findViewById(R.id.btn_left).setVisibility(View.GONE);
                findViewById(R.id.btn_right).setVisibility(View.GONE);
                findViewById(R.id.btn_open).setVisibility(View.GONE);
                findViewById(R.id.btn_stleft).setVisibility(View.GONE);
                findViewById(R.id.btn_stright).setVisibility(View.GONE);
                findViewById(R.id.btn_map).setVisibility(View.GONE);
                break;
            case PANEL:
                findViewById(R.id.pan_ctls).setVisibility(View.VISIBLE);
                findViewById(R.id.other_ctls).setVisibility(View.VISIBLE);
                findViewById(R.id.another_ctls).setVisibility(View.VISIBLE);
                findViewById(R.id.guns_ctls).setVisibility(View.VISIBLE);
                findViewById(R.id.trick_ctls).setVisibility(View.VISIBLE);
                findViewById(R.id.btn_quicksave).setVisibility(View.VISIBLE);
                findViewById(R.id.btn_menu).setVisibility(View.VISIBLE);
                findViewById(R.id.btn_run).setVisibility(View.VISIBLE);
                findViewById(R.id.btn_fire).setVisibility(View.VISIBLE);
                findViewById(R.id.btn_up).setVisibility(View.VISIBLE);
                findViewById(R.id.btn_down).setVisibility(View.VISIBLE);
                findViewById(R.id.btn_left).setVisibility(View.VISIBLE);
                findViewById(R.id.btn_right).setVisibility(View.VISIBLE);
                findViewById(R.id.btn_open).setVisibility(View.VISIBLE);
                findViewById(R.id.btn_stleft).setVisibility(View.VISIBLE);
                findViewById(R.id.btn_stright).setVisibility(View.VISIBLE);
                findViewById(R.id.btn_map).setVisibility(View.VISIBLE);
                break;
            case ACC:

                break;
        }
    }


    void MessageBox (String text) {
        DialogTool.MessageBox(this, getString(R.string.app_name), text);
    }

    void MessageBox (String title, String text) {
        DialogTool.MessageBox(this, title, text);
    }


    /******************************************************************
     * GAME subs
     ******************************************************************/
    private void startGame(final String wad)
    {
        if ( wad == null) {
            MessageBox(this, "Invalid game file! This is a bug.");
            return;
        }

        if (mMultiPlayer && mServerPort == null) {
            MessageBox(this, "Invalid Server Name for multi player game.");
            return;
        }

        // Audio?
        if ( mSound)
            mAudioMgr = AudioManager.getInstance(this, wadIdx);

        // Doom args
        final String[] argv;

        // Window size: P 320x320 L: 320x200 (will autoscale to fit the screen)
        //mOrientation = getWindowManager().getDefaultDisplay().getOrientation();
        if (portrait) {
            if ( mMultiPlayer)
                argv = new String[]{"doom" , "-width", "480", "-height", "360", "-iwad", wad , "-net", mServerPort};
            else
                argv = new String[]{"doom" , "-width", "480", "-height", "360", "-iwad", wad};
        }
        else {
            if ( mMultiPlayer)
                argv = new String[]{"doom" , "-width", "480", "-height", "320", "-iwad", wad, "-net", mServerPort};
            else
                argv = new String[]{"doom" , "-width", "480", "-height", "320", "-iwad", wad};
        }

        Log.d(TAG, "Starting doom thread with wad " + wad + " sound enabled? " + mSound  		+ " Orientation:" + mOrientation );
        //DialogTool.Toast(dClient, "Game is loading.... \nPress the Android Back button for the game menu.");
        new Thread(new Runnable() {
            public void run() {
                mGameStarted = true;
                Natives.DoomMain(argv);
            }
        }).start();
    }


    /**
     * Message Box
     * @param ctx
     * @param text
     */
    static void MessageBox( final Context ctx, final String text) {
        Toast.makeText(ctx, text, Toast.LENGTH_LONG).show();
    }

    boolean initialized = false;

    /**
     * Load JNI library. Lib must exist in /data/data/APP_PKG/files
     */
    private boolean loadLibrary() {
        // if ( initialized ) return true;


        Log.d(TAG, "Loading JNI library from '" + DoomTools.DOOM_LIB + "'"); //lib);
        Natives.load(DoomTools.DOOM_LIB);

        // Listen for Doom events
        Natives.setListener(this);
        return true;
    }

    /*************************************************************
     * Android Events
     *************************************************************/
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // Toggle nav ctls visibility when the menu key is pressed
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return false;
        }

        int sym = DoomTools.keyCodeToKeySym(keyCode);

        try {
            Natives.keyEvent(Natives.EV_KEYUP, sym);
            Log.d(TAG, "onKeyUp sent key " +  keyCode + " sym " + sym);

        } catch (UnsatisfiedLinkError e) {
            // Should not happen
            Log.e(TAG, e.toString());
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Gotta ignore menu key (used to toggle pan ctls)
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return false;
        }

        int sym = DoomTools.keyCodeToKeySym(keyCode);

        try {
            Natives.keyEvent(Natives.EV_KEYDOWN, sym);
            Log.d(TAG, "onKeyDown sent  key " + keyCode );
        }
        catch (UnsatisfiedLinkError e) {
            // Should not happen
            Log.e(TAG, e.toString());
        }
        return false;
    }

    /*************************************************************
     * Doom Event callbacks
     *************************************************************/

    /**
     * Fires when there an image update from Doom lib
     */
    public void OnImageUpdate(int[] pixels) {

        mBitmap.setPixels(pixels, 0, mWidth, 0, 0, mWidth, mHeight);

        if (first_time) {
            if (isPortrait()) {
                setImageSize   (width, pad_height);
            }
            else {
                setImageSize   (height,width);
            }
            first_time = false;
        }


        mHandler.post(new Runnable() {
            public void run() {
                mView.setImageBitmap( mBitmap);
            }
        });
    }

    /**
     * Fires on LIB message
     */
    public void OnMessage(String text, int level) {

        if ( level > 0)
            DialogTool.Toast(mHandler, DoomClient.this, text);
        else
            Log.d(TAG, "**Doom Message: " + text);
    }

    public void OnInitGraphics(int w, int h) {
        Log.d(TAG, "OnInitGraphics creating Bitmap of " + w + " by " + h);
        mBitmap = Bitmap.createBitmap(w, h, Config.ARGB_8888);
        LayoutParams lp =  mView.getLayoutParams();
        mWidth = w;
        mHeight = h;
        lp.width = w;
        lp.height = h;
    }

    public void OnFatalError(final String text) {
        mHandler.post(new Runnable() {
            public void run() {
                MessageBox("Fatal Error", "Doom has terminated. " + "Reason: " + text + " - Please report this error.");
            }
        });

        // Wait for the user to read the box
        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {

        }
        // Must quit here or the LIB will crash
        DoomTools.hardExit(-1);
    }

    public void OnStartSound(String name, int vol)
    {
        if ( mSound && mAudioMgr == null)
            Log.e(TAG, "Bug: Audio Mgr is NULL but sound is enabled!");

        try {
            if ( mSound && mAudioMgr != null)
                mAudioMgr.startSound( name, vol);

        } catch (Exception e) {
            Log.e(TAG, "OnStartSound: " +  e.toString());
        }
    }

    /**
     * Fires on background music
     */
    public void OnStartMusic(String name, int loop) {
        if ( mSound && mAudioMgr != null)
            mAudioMgr.startMusic(DoomClient.this, name, loop);
    }

    /**
     * Stop bg music
     */
    public void OnStopMusic(String name) {
        if ( mSound &&  mAudioMgr != null)
            mAudioMgr.stopMusic( name);
    }

    public void OnSetMusicVolume(int volume) {
        if ( mSound &&  mAudioMgr != null)
            mAudioMgr.setMusicVolume(volume);
    }


    /**
     * Setup pan controls
     */
    private void setupPanControls() {
        //quicksave
        findViewById(R.id.btn_quicksave).setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent evt) {
                int action = evt.getAction();
                if ( action == MotionEvent.ACTION_DOWN) {
                    Natives.sendNativeKeyEvent(Natives.EV_KEYDOWN, DoomTools.KEY_F6 );
                }
                else
                if ( action == MotionEvent.ACTION_UP) {
                    Natives.sendNativeKeyEvent(Natives.EV_KEYUP, DoomTools.KEY_F6);
                }
                return true;
            }
        });

        //trick
        findViewById(R.id.btn_trick).setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent evt) {
                int action = evt.getAction();
                if ( action == MotionEvent.ACTION_UP) {
                    checkTrick (true);
                }
                return true;
            }
        });

        //fire
        findViewById(R.id.btn_fire).setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent evt) {
                int action = evt.getAction();
                if ( action == MotionEvent.ACTION_DOWN)
                    Natives.sendNativeKeyEvent(Natives.EV_KEYDOWN, DoomTools.KEY_RCTRL);
                else if ( action == MotionEvent.ACTION_UP) {
                    if (!mGameStarted) {
                        mMultiPlayer = false;
                        showLauncherDialog(dClient, mMultiPlayer);
                    }
                    else {
                        Natives.sendNativeKeyEvent(Natives.EV_KEYUP, DoomTools.KEY_RCTRL);
                    }
                }
                return true;
            }
        });

        //select
        findViewById(R.id.btn_select).setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent evt) {
                int action = evt.getAction();
                if ( action == MotionEvent.ACTION_DOWN) // TODO put in the right CTL
                    Natives.sendNativeKeyEvent(Natives.EV_KEYDOWN, DoomTools.KEY_ENTER);
                else if ( action == MotionEvent.ACTION_UP) {
                    if (!mGameStarted) {
                        mMultiPlayer = false;
                        showLauncherDialog(dClient, mMultiPlayer);
                    }
                    else
                        Natives.sendNativeKeyEvent(Natives.EV_KEYUP, DoomTools.KEY_ENTER);
                }
                return true;
            }
        });

        //exit
        findViewById(R.id.btn_exit).setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent evt) {
                int action = evt.getAction();
                if ( action == MotionEvent.ACTION_UP) {
                    if (mGameStarted)
                        DialogTool.showExitDialog(dClient);
                    else
                        DoomTools.hardExit(0);
                }
                return true;
            }
        });

        // Down
        findViewById(R.id.btn_down).setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent evt) {
                int action = evt.getAction();
                if ( action == MotionEvent.ACTION_DOWN) {
                    pressed[0] = true;
                    checkTrick(false);
                    Natives.sendNativeKeyEvent(Natives.EV_KEYDOWN, DoomTools.KEY_DOWNARROW);
                }
                else if ( action == MotionEvent.ACTION_UP) {
                    pressed[0] = false;
                    Natives.sendNativeKeyEvent(Natives.EV_KEYUP, DoomTools.KEY_DOWNARROW);
                }
                return true;
            }
        });
        // Right
        findViewById(R.id.btn_right).setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent evt) {
                int action = evt.getAction();
                if ( action == MotionEvent.ACTION_DOWN) {
                    Natives.sendNativeKeyEvent(Natives.EV_KEYDOWN, DoomTools.KEY_RIGHTARROW);
                }
                else if ( action == MotionEvent.ACTION_UP) {
                    Natives.sendNativeKeyEvent(Natives.EV_KEYUP, DoomTools.KEY_RIGHTARROW);
                }
                return true;
            }
        });
        // Left
        findViewById(R.id.btn_left).setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent evt) {
                int action = evt.getAction();
                if ( action == MotionEvent.ACTION_DOWN) {
                    Natives.sendNativeKeyEvent(Natives.EV_KEYDOWN, DoomTools.KEY_LEFTARROW);
                }
                else if ( action == MotionEvent.ACTION_UP) {
                    Natives.sendNativeKeyEvent(Natives.EV_KEYUP, DoomTools.KEY_LEFTARROW);
                }
                return true;
            }
        });

        //up
        findViewById(R.id.btn_up).setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent evt) {
                int action = evt.getAction();
                if ( action == MotionEvent.ACTION_DOWN) {
                    pressed[1] = true;
                    checkTrick(false);
                    Natives.sendNativeKeyEvent(Natives.EV_KEYDOWN, DoomTools.KEY_UPARROW);
                }
                else if ( action == MotionEvent.ACTION_UP) {
                    pressed[1] = false;
                    Natives.sendNativeKeyEvent(Natives.EV_KEYUP, DoomTools.KEY_UPARROW);
                }
                return true;
            }
        });


        // Open/pick up
        findViewById(R.id.btn_open).setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent evt) {
                int action = evt.getAction();
                if ( action == MotionEvent.ACTION_DOWN) {
                    Natives.sendNativeKeyEvent(Natives.EV_KEYDOWN, DoomTools.KEY_SPACE);
                }
                else if ( action == MotionEvent.ACTION_UP) {
                    if (!mGameStarted) {
                        mMultiPlayer = false;
                        showLauncherDialog(dClient, mMultiPlayer);
                    }
                    else
                        Natives.sendNativeKeyEvent(Natives.EV_KEYUP, DoomTools.KEY_SPACE);
                }
                return true;
            }
        });

        // Menu
        findViewById(R.id.btn_menu).setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent evt) {
                int action = evt.getAction();
                if ( action == MotionEvent.ACTION_DOWN) {
                    Natives.sendNativeKeyEvent(Natives.EV_KEYDOWN, DoomTools.KEY_ESCAPE);
                }
                else if ( action == MotionEvent.ACTION_UP) {
                    if (!mGameStarted) {
                        mMultiPlayer = false;
                        showLauncherDialog(dClient, mMultiPlayer);
                    }
                    else {
                        Natives.sendNativeKeyEvent(Natives.EV_KEYUP, DoomTools.KEY_ESCAPE);
                    }
                }
                return true;
            }
        });

        // Run
        findViewById(R.id.btn_run).setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent evt) {
                int action = evt.getAction();
                if ( action == MotionEvent.ACTION_DOWN) {
                    Natives.sendNativeKeyEvent(Natives.EV_KEYDOWN, DoomTools.KEY_LSHIFT);
                }
                else if ( action == MotionEvent.ACTION_UP) {
                    if (!mGameStarted) {
                        mMultiPlayer = false;
                        showLauncherDialog(dClient, mMultiPlayer);
                    }
                    else {
                        Natives.sendNativeKeyEvent(Natives.EV_KEYUP, DoomTools.KEY_LSHIFT);
                    }
                }
                return true;
            }
        });


        // Map
        findViewById(R.id.btn_map).setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent evt) {
                int action = evt.getAction();
                if ( action == MotionEvent.ACTION_DOWN)
                    Natives.sendNativeKeyEvent(Natives.EV_KEYDOWN, DoomTools.KEY_TAB);
                else if ( action == MotionEvent.ACTION_UP) {
                    Natives.sendNativeKeyEvent(Natives.EV_KEYUP, DoomTools.KEY_TAB);
                }
                return true;
            }
        });

        // Strafe left
        findViewById(R.id.btn_stleft).setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent evt) {
                int action = evt.getAction();
                if ( action == MotionEvent.ACTION_DOWN)
                    Natives.sendNativeKeyEvent(Natives.EV_KEYDOWN, DoomTools.KEY_COMMA);
                else if ( action == MotionEvent.ACTION_UP) {
                    Natives.sendNativeKeyEvent(Natives.EV_KEYUP, DoomTools.KEY_COMMA);
                }
                return true;
            }
        });

        // Strafe right
        findViewById(R.id.btn_stright).setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent evt) {
                int action = evt.getAction();
                if ( action == MotionEvent.ACTION_DOWN)
                    Natives.sendNativeKeyEvent(Natives.EV_KEYDOWN, DoomTools.KEY_PERIOD);
                else if ( action == MotionEvent.ACTION_UP) {
                    Natives.sendNativeKeyEvent(Natives.EV_KEYUP, DoomTools.KEY_PERIOD);
                }
                return true;
            }
        });

        // gun 1
        findViewById(R.id.btn_trans_1).setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent evt) {
                int action = evt.getAction();
                if ( action == MotionEvent.ACTION_DOWN)
                    Natives.sendNativeKeyEvent(Natives.EV_KEYDOWN, '1');
                else if ( action == MotionEvent.ACTION_UP) {
                    Natives.sendNativeKeyEvent(Natives.EV_KEYUP, '1');
                }
                return true;
            }
        });

        // gun 2
        findViewById(R.id.btn_trans_2).setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent evt) {
                int action = evt.getAction();
                if ( action == MotionEvent.ACTION_DOWN)
                    Natives.sendNativeKeyEvent(Natives.EV_KEYDOWN, '2');
                else if ( action == MotionEvent.ACTION_UP) {
                    Natives.sendNativeKeyEvent(Natives.EV_KEYUP, '2');
                }
                return true;
            }
        });

        // gun 3
        findViewById(R.id.btn_trans_3).setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent evt) {
                int action = evt.getAction();
                if ( action == MotionEvent.ACTION_DOWN)
                    Natives.sendNativeKeyEvent(Natives.EV_KEYDOWN, '3');
                else if ( action == MotionEvent.ACTION_UP) {
                    Natives.sendNativeKeyEvent(Natives.EV_KEYUP, '3');
                }
                return true;
            }
        });


        // gun 4
        findViewById(R.id.btn_trans_4).setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent evt) {
                int action = evt.getAction();
                if ( action == MotionEvent.ACTION_DOWN)
                    Natives.sendNativeKeyEvent(Natives.EV_KEYDOWN, '4');
                else if ( action == MotionEvent.ACTION_UP) {
                    Natives.sendNativeKeyEvent(Natives.EV_KEYUP, '4');
                }
                return true;
            }
        });

        // gun 5
        findViewById(R.id.btn_trans_5).setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent evt) {
                int action = evt.getAction();
                if ( action == MotionEvent.ACTION_DOWN)
                    Natives.sendNativeKeyEvent(Natives.EV_KEYDOWN, '5');
                else if ( action == MotionEvent.ACTION_UP) {
                    Natives.sendNativeKeyEvent(Natives.EV_KEYUP, '5');
                }
                return true;
            }
        });

        // gun 6
        findViewById(R.id.btn_trans_6).setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent evt) {
                int action = evt.getAction();
                if ( action == MotionEvent.ACTION_DOWN)
                    Natives.sendNativeKeyEvent(Natives.EV_KEYDOWN, '6');
                else if ( action == MotionEvent.ACTION_UP) {
                    Natives.sendNativeKeyEvent(Natives.EV_KEYUP, '6');
                }
                return true;
            }
        });

        // gun 7
        findViewById(R.id.btn_trans_7).setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent evt) {
                int action = evt.getAction();
                if ( action == MotionEvent.ACTION_DOWN)
                    Natives.sendNativeKeyEvent(Natives.EV_KEYDOWN, '7');
                else if ( action == MotionEvent.ACTION_UP) {
                    Natives.sendNativeKeyEvent(Natives.EV_KEYUP, '7');
                }
                return true;
            }
        });


    }

    public void checkTrick(boolean option) {
        int i;
        for (i = 0; i < TOTAL_TRICK_KEYS && pressed[i]; i++);
        if (i == TOTAL_TRICK_KEYS || option) {
            Log.d(TAG,"TRICKED!");
            Natives.sendNativeKeyEvent(Natives.EV_KEYDOWN, 'i');
            Natives.sendNativeKeyEvent(Natives.EV_KEYUP, 'i');
            DoomTools.sleep (250);
            Natives.sendNativeKeyEvent(Natives.EV_KEYDOWN, 'd');
            Natives.sendNativeKeyEvent(Natives.EV_KEYUP, 'd');
            DoomTools.sleep (250);
            Natives.sendNativeKeyEvent(Natives.EV_KEYDOWN, 'k');
            Natives.sendNativeKeyEvent(Natives.EV_KEYUP, 'k');
            DoomTools.sleep (250);
            Natives.sendNativeKeyEvent(Natives.EV_KEYDOWN, 'f');
            Natives.sendNativeKeyEvent(Natives.EV_KEYUP, 'f');
            DoomTools.sleep (250);
            Natives.sendNativeKeyEvent(Natives.EV_KEYDOWN, 'a');
            Natives.sendNativeKeyEvent(Natives.EV_KEYUP, 'a');
            DoomTools.sleep (250);
        }
        else
            Log.d(TAG,"NOT TRICKED!");
    }

    public void OnQuit(int code) {
        // TODO Not yet implemented in the JNI lib
        Log.d(TAG, "Doom Hard Stop.");
        DoomTools.hardExit(0);
    }

    private void setImageSize(int w, int h ) {
        LayoutParams lp = mView.getLayoutParams();
        lp.width  = w;
        lp.height = h;
    }

    public void resizeLayout (int l, int h, int w) {
        try {
            findViewById(l).getLayoutParams().height = h;
            findViewById(l).getLayoutParams().width  = w;
        }  catch (Exception e) {
            Log.d (TAG,"Cannot resize window");
        }
    }

    public void relocateAndResize (int l, int y, int x, int h, int w) {
        View v = findViewById(l);

        // adjust positions
        h = (h * pad_height ) / PAD_HEIGHT;
        y = (y * pad_height ) / PAD_HEIGHT;

        w = (w * width) / WIDTH;
        x = (x * width) / WIDTH;

        // relocate
        ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
        p.setMargins(x,y,0,0);

        absoluteResize (l,h,w);
    }

    public void relocateAndResize (int l, int h, int w) {
        float p = w / h;

        // adjust positions
        w = (w * width) / WIDTH;
        h = (int) (w * p);
        absoluteResize (l,h,w);
    }

    public void absoluteResize (int l, int h, int w) {
        View v = findViewById(l);
        // resize
        LayoutParams lp = v.getLayoutParams();
        lp.height = h; lp.width=w;
        v.setLayoutParams(lp);
        Log.d (TAG,"Resized to " +w + "," + h);
    }

    public boolean isPortrait() {
        return portrait;
        //int ori = getWindowManager().getDefaultDisplay().getOrientation();
        //Log.e(TAG, "orientation is " + ori);
        //if (ori == Surface.ROTATION_0 || ori == Surface.ROTATION_180)
        //	return true;
        //return false;
    }

    private class installWorker implements Runnable {
        public void run() {
            Log.d(TAG,"Inside thread");
            Looper.prepare();
            // create directories & install
            DoomTools.createFolders();
            DoomTools.copyGameFiles(dClient.getBaseContext());
            DoomTools.copySavedGames(dClient.getBaseContext());
            DoomTools.copySoundTrack (dClient.getBaseContext());
            installed = true;
        }
    }



}