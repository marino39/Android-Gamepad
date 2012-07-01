package marino39.agamepad;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import marino39.agamepad.R;
import marino39.ui.*;
import marino39.ui.components.Image;
import marino39.ui.components.MouseController;
import marino39.ui.main.UIMain;
import marino39.agamepad.conf.Configuration;
import marino39.agamepad.protocol.*;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;

public class AndroidGamepadActivity extends Activity {
    
	private PadView diablo3Pad = null;
	private WakeLock mWakeLock;
	private Socket server = null;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return false;
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UIMain main = new UIMain(this);
        setContentView(main);
        
        // Configuration
        Configuration c = Configuration.getDefaultConfiguration(getResources());
        c.populate(main);
        
        // Server Connection
        /*Bundle extras = getIntent().getExtras();
		if (extras == null) {
			return;
		}
        String serverr = extras.getString("server");
		final String[] splited = serverr.split(":");
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					server = new Socket(splited[0], Integer.parseInt(splited[1]));
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
        
        // UI
        Bitmap bg = BitmapFactory.decodeResource(getResources(), R.drawable.bg1);
        Bitmap msc = BitmapFactory.decodeResource(getResources(), R.drawable.mv_ball);
        Bitmap m = BitmapFactory.decodeResource(getResources(), R.drawable.button);
        Bitmap m2 = BitmapFactory.decodeResource(getResources(), R.drawable.button_pressed);
        
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenW = displaymetrics.widthPixels;
        int screenH = displaymetrics.heightPixels;
        
        marino39.ui.components.Image background = new Image(bg);
        background.setSize(new Point(screenW, screenH)); 
        main.addUIComponent(background);
        
        final marino39.ui.components.MouseController mouseController = new MouseController(new Point(50, 460), msc);
        mouseController.setAlpha(150);
        mouseController.setListener(new UITouchEventListener() {
			
			@Override
			public void onUp(MotionEvent e) {
				process(e);
			}
			
			@Override
			public void onMove(MotionEvent e) {
				process(e);
				Log.e("Activity", "MOVE EVENT");
			}
			
			@Override
			public void onDown(MotionEvent e) {
				process(e);
			}
			
			private void process(MotionEvent e) {
				float x = e.getAxisValue(MotionEvent.AXIS_X,
						mouseController.getPointerID());
				float y = e.getAxisValue(MotionEvent.AXIS_Y,
						mouseController.getPointerID());
				Point pos = mouseController.getPosition();
				final Point size = mouseController.getSize();

				if (x > pos.x && x < (pos.x + size.x) && y > pos.y
						&& y < (pos.y + size.y)) {
					final float translatedX = (pos.x + size.x / 2 - x) * 2;
					final float translatedY = (pos.y + size.y / 2 - y) * 2;

					new Thread(new Runnable() {

						@Override
						public void run() {
							try {
								OutputStream os = server.getOutputStream();						
								MouseMovePacket mmp = new MouseMovePacket(
										Packet.OPERATION_MOUSE_MOVE,
										(byte) 0x08, (float) translatedX / (float) size.x,
										(float) translatedY / (float) size.y);

								os.write(mmp.getBytes());
								os.flush();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}

					}).start();
				}
			}
		});
        main.addUIComponent(mouseController);
        
        marino39.ui.components.Button b = new marino39.ui.components.Button(new Point(820 + 50, 420 + 50), m, m2, "1");
        Point size = b.getSize();
        b.setAlpha(175);
        b.setSize(new Point((int) (size.x * 0.5f) ,(int) (size.y * 0.5f)));
        b.setListener(new UITouchEventListener() {
        	
			@Override
			public void onUp(MotionEvent e) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							OutputStream os = server.getOutputStream();
							KeyUpPacket kup = new KeyUpPacket(new byte[] {Packet.OPERATION_KEY_UP, 1, KeyEvent.VK_1});
							os.write(kup.getBytes());
							os.flush();
						} catch (IOException e) {
							e.printStackTrace();
						}			
					}
					
				}).start();
			}
			
			@Override
			public void onMove(MotionEvent e) {
				Log.e("Activity", "move");	
			}
			
			@Override
			public void onDown(MotionEvent e) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							OutputStream os = server.getOutputStream();
							KeyDownPacket kdp = new KeyDownPacket(new byte[] {Packet.OPERATION_KEY_DOWN, 1, KeyEvent.VK_1});					
							os.write(kdp.getBytes());
							os.flush();
						} catch (IOException e) {
							e.printStackTrace();
						}			
					}
					
				}).start();
			}
		});
        main.addUIComponent(b);
        
        marino39.ui.components.Button b1 = new marino39.ui.components.Button(new Point(820 + 145, 420 + 50), m, m2, "2");
        size = b1.getSize();
        b1.setAlpha(175);
        b1.setSize(new Point((int) (size.x * 0.5f) ,(int) (size.y * 0.5f)));
        b1.setListener(new UITouchEventListener() {
			
			@Override
			public void onUp(MotionEvent e) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							OutputStream os = server.getOutputStream();
							KeyUpPacket kup = new KeyUpPacket(new byte[] {Packet.OPERATION_KEY_UP, 1, KeyEvent.VK_2});
							os.write(kup.getBytes());
							os.flush();
						} catch (IOException e) {
							e.printStackTrace();
						}			
					}
					
				}).start();
			}
			
			@Override
			public void onMove(MotionEvent e) {
				Log.e("Activity", "move");	
			}
			
			@Override
			public void onDown(MotionEvent e) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							OutputStream os = server.getOutputStream();
							KeyDownPacket kdp = new KeyDownPacket(new byte[] {Packet.OPERATION_KEY_DOWN, 1, KeyEvent.VK_2});					
							os.write(kdp.getBytes());
							os.flush();
						} catch (IOException e) {
							e.printStackTrace();
						}			
					}
					
				}).start();
			}
		});
        main.addUIComponent(b1);
        
        marino39.ui.components.Button b2 = new marino39.ui.components.Button(new Point(820 + 240, 420 + 50), m, m2, "3");
        size = b2.getSize();
        b2.setAlpha(175);
        b2.setSize(new Point((int) (size.x * 0.5f) ,(int) (size.y * 0.5f)));
        b2.setListener(new UITouchEventListener() {
			
			@Override
			public void onUp(MotionEvent e) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							OutputStream os = server.getOutputStream();
							KeyUpPacket kup = new KeyUpPacket(new byte[] {Packet.OPERATION_KEY_UP, 1, KeyEvent.VK_3});
							os.write(kup.getBytes());
							os.flush();
						} catch (IOException e) {
							e.printStackTrace();
						}			
					}
					
				}).start();
			}
			
			@Override
			public void onMove(MotionEvent e) {
				Log.e("Activity", "move");	
			}
			
			@Override
			public void onDown(MotionEvent e) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							OutputStream os = server.getOutputStream();
							KeyDownPacket kdp = new KeyDownPacket(new byte[] {Packet.OPERATION_KEY_DOWN, 1, KeyEvent.VK_3});					
							os.write(kdp.getBytes());
							os.flush();
						} catch (IOException e) {
							e.printStackTrace();
						}			
					}
					
				}).start();
			}
		});
        main.addUIComponent(b2);
        
        marino39.ui.components.Button b3 = new marino39.ui.components.Button(new Point(820 + 335, 420 + 50), m, m2, "4");
        size = b3.getSize();
        b3.setAlpha(175);
        b3.setSize(new Point((int) (size.x * 0.5f) ,(int) (size.y * 0.5f)));
        b3.setListener(new UITouchEventListener() {
			
			@Override
			public void onUp(MotionEvent e) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							OutputStream os = server.getOutputStream();
							KeyUpPacket kup = new KeyUpPacket(new byte[] {Packet.OPERATION_KEY_UP, 1, KeyEvent.VK_4});
							os.write(kup.getBytes());
							os.flush();
						} catch (IOException e) {
							e.printStackTrace();
						}			
					}
					
				}).start();
			}
			
			@Override
			public void onMove(MotionEvent e) {
				Log.e("Activity", "move");	
			}
			
			@Override
			public void onDown(MotionEvent e) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							OutputStream os = server.getOutputStream();
							KeyDownPacket kdp = new KeyDownPacket(new byte[] {Packet.OPERATION_KEY_DOWN, 1, KeyEvent.VK_4});					
							os.write(kdp.getBytes());
							os.flush();
						} catch (IOException e) {
							e.printStackTrace();
						}			
					}
					
				}).start();
			}
		});
        main.addUIComponent(b3);
        
        marino39.ui.components.Button b4 = new marino39.ui.components.Button(new Point(820 + 47, 420 + 150), m, m2, "m1");
        size = b4.getSize();
        b4.setAlpha(175);
        b4.setSize(new Point((int) (size.x * 1.0f) ,(int) (size.y * 0.75f)));
        b4.setListener(new UITouchEventListener() {
			
			@Override
			public void onUp(MotionEvent e) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							OutputStream os = server.getOutputStream();
							KeyUpPacket kup = new KeyUpPacket(new byte[] {Packet.OPERATION_KEY_UP, 1, KeyEvent.BUTTON1_MASK});
							os.write(kup.getBytes());
							os.flush();
						} catch (IOException e) {
							e.printStackTrace();
						}			
					}
					
				}).start();
			}
			
			@Override
			public void onMove(MotionEvent e) {
				Log.e("Activity", "move");	
			}
			
			@Override
			public void onDown(MotionEvent e) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							OutputStream os = server.getOutputStream();
							KeyDownPacket kdp = new KeyDownPacket(new byte[] {Packet.OPERATION_KEY_DOWN, 1, KeyEvent.BUTTON1_MASK});					
							os.write(kdp.getBytes());
							os.flush();
						} catch (IOException e) {
							e.printStackTrace();
						}			
					}
					
				}).start();
			}
		});
        main.addUIComponent(b4);
        
        marino39.ui.components.Button b5 = new marino39.ui.components.Button(new Point(820 + 237, 420 + 150), m, m2, "m2");
        size = b5.getSize();
        b5.setAlpha(175);
        b5.setSize(new Point((int) (size.x * 1.0f) ,(int) (size.y * 0.75f)));
        b5.setListener(new UITouchEventListener() {
			
			@Override
			public void onUp(MotionEvent e) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							OutputStream os = server.getOutputStream();
							KeyUpPacket kup = new KeyUpPacket(new byte[] {Packet.OPERATION_KEY_UP, 1, KeyEvent.BUTTON3_MASK});
							os.write(kup.getBytes());
							os.flush();
						} catch (IOException e) {
							e.printStackTrace();
						}			
					}
					
				}).start();
			}
			
			@Override
			public void onMove(MotionEvent e) {
				Log.e("Activity", "move");	
			}
			
			@Override
			public void onDown(MotionEvent e) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							OutputStream os = server.getOutputStream();
							KeyDownPacket kdp = new KeyDownPacket(new byte[] {Packet.OPERATION_KEY_DOWN, 1, KeyEvent.BUTTON3_MASK});					
							os.write(kdp.getBytes());
							os.flush();
						} catch (IOException e) {
							e.printStackTrace();
						}			
					}
					
				}).start();
			}
		});
        main.addUIComponent(b5);*/
        
        final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        this.mWakeLock.acquire();
    }
    
    @Override
    public void onDestroy() {
        this.mWakeLock.release();
        super.onDestroy();
    }
}