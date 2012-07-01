package marino39.d3gamepad;

public interface Packet {
	
	public final static byte OPERATION_KEY_DOWN = 0x00; 
	public final static byte OPERATION_KEY_UP = 0x01;
	public final static byte OPERATION_MOUSE_MOVE = 0x02;
	
	public void setBytes(byte[] b);
	public byte[] getBytes();

}
