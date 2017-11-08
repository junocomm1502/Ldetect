package com.example.lgtrans;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import com.example.lgtrans.ApiKeys;
import com.example.lgtrans.Detect;
import com.example.lgtrans.YandexTranslatorAPI;
import com.example.lgtrans.Language;
import com.example.lgtrans.Translate;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.app.Activity;
import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothHealth;
import android.bluetooth.BluetoothProfile;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements BluetoothBroadcastReceiver.Callback, BluetoothA2DPRequester.Callback {
	
	
	private static final String TAG = "BluetoothActivity";

    /**
     * This is the name of the device to connect to. You can replace this with the name of
     * your device.
     */
  //  private static final String HTC_MEDIA = "SBH20";
      private static String HTC_MEDIA ;
    /**
     * Local reference to the device's BluetoothAdapter
     */
    private BluetoothAdapter mAdapter;
    
    
    private boolean mIsConnect = true;
    private BluetoothDevice mDevice;
    private BluetoothA2dp mBluetoothA2DP;
    private BluetoothHeadset mBluetoothHeadset;
    private BluetoothHealth mBluetoothHealth;
	
	public ListView mList;
	public Button speakButton;
	public Button hseta;
	public Button hsetb;
	public static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
	TextToSpeech t1;
	String OutputString = "";
	TextView MyOutputText;
	Locale lc= new Locale("hi","IN");
	Locale lc1= new Locale("bn","IN");
	Locale lc2= new Locale("pa","IN");
	
	//BluetoothActivity ba= new BluetoothActivity();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		speakButton = (Button) findViewById(R.id.btn_speak);
		speakButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				startVoiceRecognitionActivity();
				}
			});
		
		hseta = (Button) findViewById(R.id.hs1);
		hseta.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				startVoicehseta();
				}
			});
		
		hsetb = (Button) findViewById(R.id.hs2);
		hsetb.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				startVoicehsetb();
				}
			});
		
		MyOutputText = (TextView)findViewById(R.id.OutputText);
		
		t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
	         @Override
	         public void onInit(int status) {
	            if(status != TextToSpeech.ERROR) {
	               t1.setLanguage(lc);
	            }
	         }
	      });
		
		//Log.e("lgtrans_bt", ""+isBluetoothHeadsetConnected());
		
		voiceinputbuttons();
		
		/*PackageManager pm = getPackageManager();
		List activities = pm.queryIntentActivities(new Intent(
		RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
		if (activities.size() != 0) {
		    voiceButton.setOnClickListener(this);
		} else {
		    voiceButton.setEnabled(false);
		    voiceButton.setText("Recognizer not present");
		}*/
	}

	public void voiceinputbuttons() {
	    speakButton = (Button) findViewById(R.id.btn_speak);
	    mList = (ListView) findViewById(R.id.list);
	}
	
	
	
	public void startVoicehseta() {
		
		HTC_MEDIA = "BS19C";
		mIsConnect=false;
		mAdapter = BluetoothAdapter.getDefaultAdapter();
		mAdapter.getProfileProxy(getApplicationContext() , mProfileListener, BluetoothProfile.A2DP);

		mAdapter.getProfileProxy(getApplicationContext() , mProfileListener, BluetoothProfile.HEADSET);
		
		Log.e(TAG, "startVoicehseta()");
		HTC_MEDIA = "SBH20";
		mIsConnect=true;
		mAdapter = BluetoothAdapter.getDefaultAdapter();
		mAdapter.getProfileProxy(getApplicationContext() , mProfileListener, BluetoothProfile.A2DP);
		mAdapter.getProfileProxy(getApplicationContext() , mProfileListener, BluetoothProfile.HEADSET);
		
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
		    public void run() {
		     // Actions to do after 10 seconds
		    	AudioManager audioManager;

				audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

				audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
				audioManager.startBluetoothSco();
				audioManager.setBluetoothScoOn(true);
				
			    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
			    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
			  //  intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
			   //     "Speech recognition demo");
			    startActivityForResult(intent, 2);
		    }
		}, 10000);
		
		
		/*    HTC_MEDIA = "SBH20";
		
		mAdapter = BluetoothAdapter.getDefaultAdapter();
		//private boolean mIsConnect = true;
        //Already connected, skip the rest
        if (mAdapter.isEnabled()) {
            onBluetoothConnected();
           // return;
        }
        
       
		if(isBluetoothHeadsetConnected()==true)
			{
			
			Log.e(TAG, "isBluetoothHeadsetConnected()_if_block");
			
			AudioManager audioManager;

			audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

			audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
			audioManager.startBluetoothSco();
			audioManager.setBluetoothScoOn(true);
			
		    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
		  //  intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
		   //     "Speech recognition demo");
		    startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
			}
		else
		{
			Toast.makeText(getApplicationContext(), "wait to connect",Toast.LENGTH_LONG).show();
			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {
			    public void run() {
			     // Actions to do after 10 seconds
			    	AudioManager audioManager;

					audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

					audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
					audioManager.startBluetoothSco();
					audioManager.setBluetoothScoOn(true);
					
				    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
				    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
				  //  intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
				   //     "Speech recognition demo");
				    startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
			    }
			}, 10000);
		}*/
			
			
		    
		    
		    
		}
	
	public void startVoicehsetb() {
		
		
		HTC_MEDIA = "SBH20";
		mIsConnect=false;
		mAdapter = BluetoothAdapter.getDefaultAdapter();
		mAdapter.getProfileProxy(getApplicationContext() , mProfileListener, BluetoothProfile.A2DP);
		mAdapter.getProfileProxy(getApplicationContext() , mProfileListener, BluetoothProfile.HEADSET);
		
		Log.e(TAG, "startVoicehsetb()");
		HTC_MEDIA = "BS19C";
		mIsConnect=true;
		mAdapter = BluetoothAdapter.getDefaultAdapter();
		mAdapter.getProfileProxy(getApplicationContext() , mProfileListener, BluetoothProfile.A2DP);
		mAdapter.getProfileProxy(getApplicationContext() , mProfileListener, BluetoothProfile.HEADSET);
		
		Handler handler = new Handler();
			handler.postDelayed(new Runnable() {
			    public void run() {
			     // Actions to do after 10 seconds
			    	AudioManager audioManager;

					audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

					audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
					audioManager.startBluetoothSco();
					audioManager.setBluetoothScoOn(true);
					
				    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
				    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
				  //  intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
				   //     "Speech recognition demo");
				    startActivityForResult(intent, 3);
			    }
			}, 10000);
		
       /*   HTC_MEDIA = "BS19C";
		
          mAdapter = BluetoothAdapter.getDefaultAdapter();

          //Already connected, skip the rest
          if (mAdapter.isEnabled()) {
              onBluetoothConnected();
             // return;
          }
          
         
  		if(isBluetoothHeadsetConnected()==true)
  			{
  			
  			Log.e(TAG, "isBluetoothHeadsetConnected()_if_block");
  			
  			AudioManager audioManager;

  			audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

  			audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
  			audioManager.startBluetoothSco();
  			audioManager.setBluetoothScoOn(true);
  			
  		    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
  		    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
  		  //  intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
  		   //     "Speech recognition demo");
  		    startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
  			}
  		else
  		{
  			Toast.makeText(getApplicationContext(), "wait to connect",Toast.LENGTH_LONG).show();
  			Handler handler = new Handler();
  			handler.postDelayed(new Runnable() {
  			    public void run() {
  			     // Actions to do after 10 seconds
  			    	AudioManager audioManager;

  					audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

  					audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
  					audioManager.startBluetoothSco();
  					audioManager.setBluetoothScoOn(true);
  					
  				    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
  				    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
  				  //  intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
  				   //     "Speech recognition demo");
  				    startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
  			    }
  			}, 10000);
  		}*/
		
	}
	
	
	public void startVoiceRecognitionActivity() {
		
		HTC_MEDIA = "SBH20";
		mIsConnect=false;
		mAdapter = BluetoothAdapter.getDefaultAdapter();
		mAdapter.getProfileProxy(getApplicationContext() , mProfileListener, BluetoothProfile.A2DP);
		mAdapter.getProfileProxy(getApplicationContext() , mProfileListener, BluetoothProfile.HEADSET);
		
		
		HTC_MEDIA = "BS19C";
		mIsConnect=false;
		mAdapter = BluetoothAdapter.getDefaultAdapter();
		mAdapter.getProfileProxy(getApplicationContext() , mProfileListener, BluetoothProfile.A2DP);
		mAdapter.getProfileProxy(getApplicationContext() , mProfileListener, BluetoothProfile.HEADSET);
	
			AudioManager audioManager;
			audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
			audioManager.setMode(AudioManager.MODE_NORMAL);
			audioManager.stopBluetoothSco();
			audioManager.setBluetoothScoOn(false);
			
			Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
		    intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
		        "Speech recognition demo");
		    startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
	//	}
	}
	
	public static boolean isBluetoothHeadsetConnected() {
		
		Log.e(TAG, "isBluetoothHeadsetConnected()");
	    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	    return mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()
	            && mBluetoothAdapter.getProfileConnectionState(BluetoothHeadset.HEADSET) == BluetoothHeadset.STATE_CONNECTED;
	}
	
	public void informationMenu() {
	    startActivity(new Intent("android.intent.action.INFOSCREEN"));
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);

	    Log.e(TAG, "onActivityResult");
	    
	    if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
	    	
	    	 Log.e(TAG, "onActivityResult_if_block");
	        // Fill the list view with the strings the recognizer thought it
	        // could have heard
	        final ArrayList matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
	      
	        class bgStuff extends AsyncTask<Void, Void, Void>{
	            
	           // String OutputString = "";
	            
	           
	            @Override
	            protected Void doInBackground(Void... params) {
	                // TODO Auto-generated method stub
	                try {
	                   // String text = ((EditText) findViewById(R.id.etUserText)).getText().toString();
	                   // translatedText = translate(text);
	                    
	                	
	                	
	                   Translate.setKey(ApiKeys.YANDEX_API_KEY);
	                	 OutputString = Translate.execute(matches.get(0).toString(), Language.ENGLISH, Language.HINDI);
	                	// OutputString = Translate.execute("how are you".toString(), Language.ENGLISH, Language.HINDI);
	                } catch (Exception e) {
	                    // TODO Auto-generated catch block
	                    e.printStackTrace();
	                    OutputString = e.toString();
	                }
	                 
	                return null;
	            }
	        
	            @Override
	            protected void onPostExecute(Void result) {
	                // TODO Auto-generated method stub
	          	  MyOutputText.setText(OutputString);
	          	final String toSpeak = OutputString;
		       // t1.setLanguage(Locale.GER);
	          		    	  	
	          	
        		
    			t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
	            Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_LONG).show();

	         
	            }       
	        }
	         
	        new bgStuff().execute();
	        
	        
	        
	        
	        if (matches.contains("information")) {
	            informationMenu();
	            
	        }
	    }
	    
	    
	    if (requestCode == 2 && resultCode == RESULT_OK) {
	    	
	    	 Log.e(TAG, "onActivityResult_if_block");
	        // Fill the list view with the strings the recognizer thought it
	        // could have heard
	        final ArrayList matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
	      
	        class bgStuff extends AsyncTask<Void, Void, Void>{
	            
	           // String OutputString = "";
	            
	           
	            @Override
	            protected Void doInBackground(Void... params) {
	                // TODO Auto-generated method stub
	                try {
	                   // String text = ((EditText) findViewById(R.id.etUserText)).getText().toString();
	                   // translatedText = translate(text);
	                	HTC_MEDIA = "SBH20";
	               		mIsConnect=false;
	               		mAdapter = BluetoothAdapter.getDefaultAdapter();
	               		mAdapter.getProfileProxy(getApplicationContext() , mProfileListener, BluetoothProfile.A2DP);
	               		mAdapter.getProfileProxy(getApplicationContext() , mProfileListener, BluetoothProfile.HEADSET);
	        	          	
	        	          	HTC_MEDIA = "BS19C";
	               		mIsConnect=true;
	               		mAdapter = BluetoothAdapter.getDefaultAdapter();
	               		mAdapter.getProfileProxy(getApplicationContext() , mProfileListener, BluetoothProfile.A2DP);
	               		mAdapter.getProfileProxy(getApplicationContext() , mProfileListener, BluetoothProfile.HEADSET);  
	                	
	                	
	                   Translate.setKey(ApiKeys.YANDEX_API_KEY);
	                	 OutputString = Translate.execute(matches.get(0).toString(), Language.ENGLISH, Language.HINDI);
	                	// OutputString = Translate.execute("how are you".toString(), Language.ENGLISH, Language.HINDI);
	                } catch (Exception e) {
	                    // TODO Auto-generated catch block
	                    e.printStackTrace();
	                    OutputString = e.toString();
	                }
	                 
	                return null;
	            }
	        
	            @Override
	            protected void onPostExecute(Void result) {
	                // TODO Auto-generated method stub
	          	  MyOutputText.setText(OutputString);
	          	final String toSpeak = OutputString;
		       // t1.setLanguage(Locale.GER);
	          		    	  	
	          	
       		Toast.makeText(getApplicationContext(), "wait to connect headset 2",Toast.LENGTH_LONG).show();
   			Handler handler = new Handler();
   			handler.postDelayed(new Runnable() {
   			    public void run() {
   			     // Actions to do after 15 seconds
   			    	t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
   		            Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_LONG).show();
   			    }
   			}, 10000);
       		
	          	
	         
	            }       
	        }
	         
	        new bgStuff().execute();
	        
	        
	        
	        
	        if (matches.contains("information")) {
	            informationMenu();
	            
	        }
	    }
	    
	    
	    if (requestCode == 3 && resultCode == RESULT_OK) {
	    	
	    	 Log.e(TAG, "onActivityResult_if_block");
	        // Fill the list view with the strings the recognizer thought it
	        // could have heard
	        final ArrayList matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
	      
	        class bgStuff extends AsyncTask<Void, Void, Void>{
	            
	           // String OutputString = "";
	            
	           
	            @Override
	            protected Void doInBackground(Void... params) {
	                // TODO Auto-generated method stub
	                try {
	                   // String text = ((EditText) findViewById(R.id.etUserText)).getText().toString();
	                   // translatedText = translate(text);
	                	HTC_MEDIA = "BS19C";
	               		mIsConnect=false;
	               		mAdapter = BluetoothAdapter.getDefaultAdapter();
	               		mAdapter.getProfileProxy(getApplicationContext() , mProfileListener, BluetoothProfile.A2DP);
	               		mAdapter.getProfileProxy(getApplicationContext() , mProfileListener, BluetoothProfile.HEADSET);
	        	          	
	        	          	HTC_MEDIA = "SBH20";
	               		mIsConnect=true;
	               		mAdapter = BluetoothAdapter.getDefaultAdapter();
	               		mAdapter.getProfileProxy(getApplicationContext() , mProfileListener, BluetoothProfile.A2DP);
	               		mAdapter.getProfileProxy(getApplicationContext() , mProfileListener, BluetoothProfile.HEADSET);
	               		  
	                	
	                	
	                   Translate.setKey(ApiKeys.YANDEX_API_KEY);
	                	 OutputString = Translate.execute(matches.get(0).toString(), Language.ENGLISH, Language.HINDI);
	                	// OutputString = Translate.execute("how are you".toString(), Language.ENGLISH, Language.HINDI);
	                } catch (Exception e) {
	                    // TODO Auto-generated catch block
	                    e.printStackTrace();
	                    OutputString = e.toString();
	                }
	                 
	                return null;
	            }
	        
	            @Override
	            protected void onPostExecute(Void result) {
	                // TODO Auto-generated method stub
	          	  MyOutputText.setText(OutputString);
	          	final String toSpeak = OutputString;
		       // t1.setLanguage(Locale.GER);
	          		    	  	
	          	
   			Handler handler = new Handler();
   			handler.postDelayed(new Runnable() {
   			    public void run() {
   			     // Actions to do after 15 seconds
   			    	t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
   		            Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_LONG).show();
   			    }
   			}, 10000);
       		
	          	
	         
	            }       
	        }
	         
	        new bgStuff().execute();
	        
	        
	        
	        
	        if (matches.contains("information")) {
	            informationMenu();
	            
	        }
	    }
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	@Override
    public void onBluetoothError () {
        Log.e(TAG, "There was an error enabling the Bluetooth Adapter.");
    }

    @Override
    public void onBluetoothConnected () {
    	Log.e(TAG, "onBluetoothConnected");
        new BluetoothA2DPRequester(this).request(this, mAdapter);
        
        Log.e(TAG, "after_connection");
        
        
    }

    
    
    
    @Override
    public void onA2DPProxyReceived (BluetoothA2dp proxy) {
    	
    	Log.e(TAG, "onA2DPProxyReceived");
        Method connect = getConnectMethod();
        BluetoothDevice device = findBondedDeviceByName(mAdapter, HTC_MEDIA);
        Log.e(TAG, "connect "+connect);
      //If either is null, just return. The errors have already been logged
        if (connect == null || device == null) {
            return;
        }
        
        if(mIsConnect==true)
        {
        try {
        	Log.e(TAG, "onA2DPProxyReceived_try");
            connect.setAccessible(true);
            connect.invoke(proxy, device);
            
        } catch (InvocationTargetException ex) {
            Log.e(TAG, "Unable to invoke connect(BluetoothDevice) method on proxy. " + ex.toString());
        } catch (IllegalAccessException ex) {
            Log.e(TAG, "Illegal Access! " + ex.toString());
        }
        }
        
        else
        {
        	try {
                
                    Method disconnect = BluetoothHeadset.class.getDeclaredMethod("disconnect", BluetoothDevice.class);
                    disconnect.setAccessible(true);
                    disconnect.invoke(proxy, device);
                
            }catch (Exception e){
            }
        }
    }

    /**
     * Wrapper around some reflection code to get the hidden 'connect()' method
     * @return the connect(BluetoothDevice) method, or null if it could not be found
     */
    private Method getConnectMethod () {
    	
    	Log.e(TAG, "getConnectMethod");
        try {
            return BluetoothA2dp.class.getDeclaredMethod("connect", BluetoothDevice.class);
        } catch (NoSuchMethodException ex) {
            Log.e(TAG, "Unable to find connect(BluetoothDevice) method in BluetoothA2dp proxy.");
            return null;
        }
    }

    /**
     * Search the set of bonded devices in the BluetoothAdapter for one that matches
     * the given name
     * @param adapter the BluetoothAdapter whose bonded devices should be queried
     * @param name the name of the device to search for
     * @return the BluetoothDevice by the given name (if found); null if it was not found
     */
    private static BluetoothDevice findBondedDeviceByName (BluetoothAdapter adapter, String name) {
    	Log.e(TAG, "BluetoothDevice");
        for (BluetoothDevice device : getBondedDevices(adapter)) {
            if (name.matches(device.getName())) {
                Log.e(TAG, String.format("Found device with name %s and address %s.", device.getName(), device.getAddress()));
                return device;
            }
        }
        Log.e(TAG, String.format("Unable to find device with name %s.", name));
        return null;
    }

    /**
     * Safety wrapper around BluetoothAdapter#getBondedDevices() that is guaranteed
     * to return a non-null result
     * @param adapter the BluetoothAdapter whose bonded devices should be obtained
     * @return the set of all bonded devices to the adapter; an empty set if there was an error
     */
    private static Set<BluetoothDevice> getBondedDevices (BluetoothAdapter adapter) {
    	Log.e(TAG, "getBondedDevices");
        Set<BluetoothDevice> results = adapter.getBondedDevices();
        if (results == null) {
            results = new HashSet<BluetoothDevice>();
        }
        
        return results;
    }
    
    private BluetoothProfile.ServiceListener mProfileListener = new BluetoothProfile.ServiceListener() {
        public void onServiceConnected(int profile, BluetoothProfile proxy) {
        	
        	Log.e(TAG, "mProfileListener");
        	BluetoothDevice device = findBondedDeviceByName(mAdapter, HTC_MEDIA);
        	
            if (profile == BluetoothProfile.A2DP) {
                mBluetoothA2DP = (BluetoothA2dp) proxy;
                try {
                    if (mIsConnect) {
                        Method connect = BluetoothA2dp.class.getDeclaredMethod("connect", BluetoothDevice.class);
                        connect.setAccessible(true);
                        connect.invoke(mBluetoothA2DP, device);
                    } else {
                        Method disconnect = BluetoothA2dp.class.getDeclaredMethod("disconnect", BluetoothDevice.class);
                        disconnect.setAccessible(true);
                        disconnect.invoke(mBluetoothA2DP, device);
                    }
                }catch (Exception e){
                } finally {
                }
            } else if (profile == BluetoothProfile.HEADSET) {
            	Log.e(TAG, "mProfileListener_if_block");
                mBluetoothHeadset = (BluetoothHeadset) proxy;
                try {
                    if (mIsConnect) {
                        Method connect = BluetoothHeadset.class.getDeclaredMethod("connect", BluetoothDevice.class);
                        connect.setAccessible(true);
                        connect.invoke(mBluetoothHeadset, device);
                    } else {
                        Method disconnect = BluetoothHeadset.class.getDeclaredMethod("disconnect", BluetoothDevice.class);
                        disconnect.setAccessible(true);
                        disconnect.invoke(mBluetoothHeadset, device);
                    }
                }catch (Exception e){
                } finally {
                }
            } else if (profile == BluetoothProfile.HEALTH) {
                mBluetoothHealth = (BluetoothHealth) proxy;
                try {
                    if (mIsConnect) {
                        Method connect = BluetoothHealth.class.getDeclaredMethod("connect", BluetoothDevice.class);
                        connect.setAccessible(true);
                        connect.invoke(mBluetoothHealth, mDevice);
                    } else {
                        Method disconnect = BluetoothHealth.class.getDeclaredMethod("disconnect", BluetoothDevice.class);
                        disconnect.setAccessible(true);
                        disconnect.invoke(mBluetoothHealth, mDevice);
                    }
                }catch (Exception e){
                } finally {
                }
            }
        }
        public void onServiceDisconnected(int profile) {
        }
    };

	
   /* public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE){
            //handle click
        	Log.e(TAG, "onKeyDown");
        	startVoicehsetb();
        	
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/
/*	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		startVoiceRecognitionActivity();
		
	}*/

	
}
