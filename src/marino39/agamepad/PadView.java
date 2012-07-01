package marino39.agamepad;


import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import marino39.agamepad.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class PadView extends View {
	
	private Logger log = Logger.getLogger("PadView");
	private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private List<String> consoleLines = new ArrayList<String>();
	private MovementController movementController = new MovementController(190, 550, 120);
	private List<PadButton> buttons = new ArrayList<PadButton>();
	private Socket server = null;
	private Bitmap moveBall = null;
	private Bitmap bg = null;
	private int[] buttonClearer = new int[1200];

	public PadView(Context context, final String ip, final int port) {
		super(context);
		moveBall = BitmapFactory.decodeResource(getResources(), R.drawable.mv_ball);
		bg = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					server = new Socket(ip, port);
					consoleAdd("Connected to server: " + ip + ":" + port);
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
		buttons.add(new PadButton("Mouse 1", 950, 550, 150, 100,
				new EventHandler() {

					@Override
					public void processEvent(MotionEvent event, PadButton pb,
							int pointer) {

						if (event.getAction() == MotionEvent.ACTION_DOWN
								|| event.getAction() == MotionEvent.ACTION_POINTER_1_DOWN
								|| event.getAction() == MotionEvent.ACTION_POINTER_2_DOWN
								|| event.getAction() == MotionEvent.ACTION_POINTER_3_DOWN) {
							consoleAdd(pb.name + ": ACTION_DOWN");
							buttonClearer[event.getAction()] =  KeyEvent.BUTTON1_MASK; 
							new Thread(new Runnable() {
								
								@Override
								public void run() {
									try {
										OutputStream os = server.getOutputStream();
										KeyDownPacket kdp = new KeyDownPacket(new byte[] {Packet.OPERATION_KEY_DOWN, 1, KeyEvent.BUTTON1_MASK});					
										os.write(kdp.getBytes());
										//consoleAdd(":: " + kdp.toString());
										os.flush();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}			
								}
								
							}).start();
						} 
					}

					@Override
					public void processEvent(MotionEvent event, int pointer) {
						// TODO Auto-generated method stub

					}

				}));
		
		buttons.add(new PadButton("Mouse 2", 1110, 550, 150, 100, new EventHandler() {
			
			@Override
			public void processEvent(MotionEvent event, PadButton pb,
					int pointer) {

				if (event.getAction() == MotionEvent.ACTION_DOWN
						|| event.getAction() == MotionEvent.ACTION_POINTER_1_DOWN
						|| event.getAction() == MotionEvent.ACTION_POINTER_2_DOWN
						|| event.getAction() == MotionEvent.ACTION_POINTER_3_DOWN) {
					consoleAdd(pb.name + ": ACTION_DOWN");
					buttonClearer[event.getAction()] =  KeyEvent.BUTTON3_MASK; 
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							try {
								OutputStream os = server.getOutputStream();
								KeyDownPacket kdp = new KeyDownPacket(new byte[] {Packet.OPERATION_KEY_DOWN, 1, KeyEvent.BUTTON3_MASK});					
								os.write(kdp.getBytes());
								consoleAdd(":: " + kdp.toString());
								os.flush();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}			
						}
						
					}).start();
				} 
			}
			
			@Override
			public void processEvent(MotionEvent event, int pointer) {
				// TODO Auto-generated method stub
				
			}
		}));
		
		buttons.add(new PadButton("Ability 1", 950, 450, 70, 70,
				new EventHandler() {

					@Override
					public void processEvent(MotionEvent event, PadButton pb,
							int pointer) {

						if (event.getAction() == MotionEvent.ACTION_DOWN
								|| event.getAction() == MotionEvent.ACTION_POINTER_1_DOWN
								|| event.getAction() == MotionEvent.ACTION_POINTER_2_DOWN
								|| event.getAction() == MotionEvent.ACTION_POINTER_3_DOWN) {
							consoleAdd(pb.name + ": ACTION_DOWN");
							buttonClearer[event.getAction()] =  KeyEvent.VK_1; 
							new Thread(new Runnable() {
								
								@Override
								public void run() {
									try {
										OutputStream os = server.getOutputStream();
										KeyDownPacket kdp = new KeyDownPacket(new byte[] {Packet.OPERATION_KEY_DOWN, 1, KeyEvent.VK_1});					
										os.write(kdp.getBytes());
										//consoleAdd(":: " + kdp.toString());
										os.flush();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}			
								}
								
							}).start();
						}

					}

					@Override
					public void processEvent(MotionEvent event, int pointer) {
						// TODO Auto-generated method stub

					}

				}));
		
				buttons.add(new PadButton("Ability 2", 1030, 450, 70, 70,
				new EventHandler() {

					@Override
					public void processEvent(MotionEvent event, PadButton pb,
							int pointer) {

						if (event.getAction() == MotionEvent.ACTION_DOWN
								|| event.getAction() == MotionEvent.ACTION_POINTER_1_DOWN
								|| event.getAction() == MotionEvent.ACTION_POINTER_2_DOWN
								|| event.getAction() == MotionEvent.ACTION_POINTER_3_DOWN) {
							consoleAdd(pb.name + ": ACTION_DOWN");
							buttonClearer[event.getAction()] =  KeyEvent.VK_2; 
							new Thread(new Runnable() {
								
								@Override
								public void run() {
									try {
										OutputStream os = server.getOutputStream();
										KeyDownPacket kdp = new KeyDownPacket(new byte[] {Packet.OPERATION_KEY_DOWN, 1, KeyEvent.VK_2});					
										os.write(kdp.getBytes());
										//consoleAdd(":: " + kdp.toString());
										os.flush();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}			
								}
								
							}).start();
						}

					}

					@Override
					public void processEvent(MotionEvent event, int pointer) {
						// TODO Auto-generated method stub

					}

				}));
				
				buttons.add(new PadButton("Ability 3", 1110, 450, 70, 70,
						new EventHandler() {

							@Override
							public void processEvent(MotionEvent event, PadButton pb,
									int pointer) {

								if (event.getAction() == MotionEvent.ACTION_DOWN
										|| event.getAction() == MotionEvent.ACTION_POINTER_1_DOWN
										|| event.getAction() == MotionEvent.ACTION_POINTER_2_DOWN
										|| event.getAction() == MotionEvent.ACTION_POINTER_3_DOWN) {
									consoleAdd(pb.name + ": ACTION_DOWN");
									buttonClearer[event.getAction()] =  KeyEvent.VK_3; 
									new Thread(new Runnable() {
										
										@Override
										public void run() {
											try {
												OutputStream os = server.getOutputStream();
												KeyDownPacket kdp = new KeyDownPacket(new byte[] {Packet.OPERATION_KEY_DOWN, 1, KeyEvent.VK_3});					
												os.write(kdp.getBytes());
												//consoleAdd(":: " + kdp.toString());
												os.flush();
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}			
										}
										
									}).start();
								}

							}

							@Override
							public void processEvent(MotionEvent event, int pointer) {
								// TODO Auto-generated method stub

							}

						}));
				
				buttons.add(new PadButton("Ability 4", 1190, 450, 70, 70,
						new EventHandler() {

							@Override
							public void processEvent(MotionEvent event, PadButton pb,
									int pointer) {

								if (event.getAction() == MotionEvent.ACTION_DOWN
										|| event.getAction() == MotionEvent.ACTION_POINTER_1_DOWN
										|| event.getAction() == MotionEvent.ACTION_POINTER_2_DOWN
										|| event.getAction() == MotionEvent.ACTION_POINTER_3_DOWN) {
									consoleAdd(pb.name + ": ACTION_DOWN");
									buttonClearer[event.getAction()] =  KeyEvent.VK_4; 
									new Thread(new Runnable() {
										
										@Override
										public void run() {
											try {
												OutputStream os = server.getOutputStream();
												KeyDownPacket kdp = new KeyDownPacket(new byte[] {Packet.OPERATION_KEY_DOWN, 1, KeyEvent.VK_4});					
												os.write(kdp.getBytes());
												//consoleAdd(":: " + kdp.toString());
												os.flush();
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}			
										}
										
									}).start();
								}

							}

							@Override
							public void processEvent(MotionEvent event, int pointer) {
								// TODO Auto-generated method stub

							}

						}));
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		/* Draw BG */
		Matrix m = new Matrix();
		m.setTranslate((getWidth()-bg.getWidth())/2, 0);
		canvas.drawBitmap(bg, m, paint);
		
		/* Console */
		drawConsole(10, 10, canvas);
		
		/* Movment controler */;
		movementController.drawController(canvas);
		
		/* Buttons */
		for (int i = 0; i < buttons.size(); i++) {
			buttons.get(i).drawButton(canvas);
		}
	}
	
	@Override
	public boolean onTouchEvent(final MotionEvent event) {
		movementController.processEvent(event, 0);
		
		for (int i = 0; i < buttons.size(); i++) {
			buttons.get(i).processEvent(event);
		}
		
		/**
		 * When you click button and move finger out of its bonds then pick it up. Button will stuck. Its fix.
		 */
		if(event.getAction() == MotionEvent.ACTION_UP
				|| event.getAction() == MotionEvent.ACTION_POINTER_1_UP
				|| event.getAction() == MotionEvent.ACTION_POINTER_2_UP
				|| event.getAction() == MotionEvent.ACTION_POINTER_3_UP) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						if ((byte) buttonClearer[event.getAction()-1] != 0) {
							OutputStream os = server.getOutputStream();
							KeyUpPacket kup = new KeyUpPacket(new byte[] {Packet.OPERATION_KEY_UP, 1, (byte) buttonClearer[event.getAction()-1]});
							os.write(kup.getBytes());
							//consoleAdd(":: " + kup.toString());
							os.flush();
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}			
				}
				
			}).start();
		}
		
		this.invalidate();
		return true;
	}
	
	public void consoleAdd(String text) {
		consoleLines.add(text);
	}
	
	private void drawConsole(float x, float y, Canvas canvas) {
		paint.setColor(Color.WHITE);
		for (int i = 0; i < 33; i++) {
			if (consoleLines.size() -1 - i >= 0) {
				canvas.drawText(consoleLines.get(consoleLines.size() -1 - i), 10.0f, 405.0f - (12 * i), paint);
			}
		}
	}
	
	class MovementController implements EventHandler {
		
		private float x;
		private float y;
		private float radius;
		private boolean drawControlerArea = false;
		private long lastPositionUpdate = 0;
		
		public MovementController(float x, float y, float radius) {
			this.x = x;
			this.y = y;
			this.radius = radius;
		}
		
		public void drawController(Canvas canvas) {
			Matrix m = new Matrix();
			m.setTranslate(62, getHeight()-moveBall.getHeight()-20);
			canvas.drawBitmap(moveBall, m, paint);
			if (drawControlerArea) {
				canvas.drawCircle(x, y, radius, paint);
			}
		}

		@Override
		public void processEvent(MotionEvent event, int pointer) {
			for (int i = 0; i < event.getPointerCount(); i++) {
				if (event.getAxisValue(event.AXIS_X, i) > x - radius && event.getAxisValue(event.AXIS_X, i) < x + radius) {
					if (event.getAxisValue(event.AXIS_Y, i) > y - radius && event.getAxisValue(event.AXIS_Y, i) < y + radius) {
						final float translatedX = x - event.getAxisValue(event.AXIS_X, i);
						final float translatedY = y - event.getAxisValue(event.AXIS_Y, i);
						
						if (Math.sqrt((int) (Math.abs(translatedX)) ^ 2 + (int) (Math.abs(translatedY)) ^ 2) < radius) {
							if (event.getAction() == MotionEvent.ACTION_DOWN || 
									event.getAction() == MotionEvent.ACTION_UP || 
									event.getAction() == MotionEvent.ACTION_MOVE) {
								//consoleAdd("MovementController::ACTION_MOVE x: " + translatedX + " y: " + translatedY);
								new Thread(new Runnable() {
									
									@Override
									public void run() {
										try {
											//if(Calendar.getInstance().getTimeInMillis() > lastPositionUpdate + 1) {
											//	lastPositionUpdate = Calendar.getInstance().getTimeInMillis();
												OutputStream os = server.getOutputStream();
												MouseMovePacket mmp = new MouseMovePacket(Packet.OPERATION_MOUSE_MOVE, (byte)0x08, (float) translatedX / (float) radius, (float) translatedY / (float) radius);				
												os.write(mmp.getBytes());
												//consoleAdd(":: " + mmp.toString());
												os.flush();
											//}
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}			
									}
									
								}).start();
							} 
						}
					}
				}
			}
		}

		@Override
		public void processEvent(MotionEvent event, PadButton pb, int pointer) {
			// TODO Auto-generated method stub
			
		}	
	}
	
	class PadButton {

		private float x;
		private float y;
		private float width;
		private float height;
		private EventHandler e = null;
		private String name;
		
		public PadButton(String name, float x, float y, float width, float height) {
			this.name = name;
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}
		
		public PadButton(String name, float x, float y, float width, float height, EventHandler ev) {
			this.name = name;
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.e = ev;
		}
		
		public void processEvent(MotionEvent event) {
			
			if (getEventHandler() == null) 
				return;
			
			int pointer = event.getActionIndex();
			if (event.getAxisValue(MotionEvent.AXIS_X, pointer) > x && event.getAxisValue(MotionEvent.AXIS_X, pointer) < x + width) {
				if (event.getAxisValue(MotionEvent.AXIS_Y, pointer) > y && event.getAxisValue(MotionEvent.AXIS_Y, pointer) < y + height) {
					getEventHandler().processEvent(event, this, pointer);
				}
			}
		}
		
		public void drawButton(Canvas canvas) {
			paint.setColor(Color.WHITE);
			canvas.drawRect(x, y, x + width, y + height, paint);
		}
		
		public void setEventHandler(EventHandler e) {
			this.e = e;
		}
		
		public EventHandler getEventHandler() {
			return e;
		}
	}

}
