import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;

import com.adaptivehuffman.AdaptiveHuffman;

public class Receiver {
	static InetAddress addr;
	static int port = 27960;
    public static void main(String[] args) {
        addr = null;
        try {
            addr = InetAddress.getByName("et.etjump.com");
        } catch (UnknownHostException e) {
            System.out.println("couldn't find target host");
        }
        
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(0);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        long challenge;
        DatagramPacket receivedPacket = new DatagramPacket(new byte[256], 256);
        try {
        	socket.send(makePacket("getchallenge"));
        	socket.receive(receivedPacket);
            System.out.println(new String(receivedPacket.getData()));
            challenge = getChallenge(new String(receivedPacket.getData()));
        	socket.send(test());
            socket.receive(receivedPacket);
            System.out.println(new String(receivedPacket.getData()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
     
    }
    
    static DatagramPacket makePacket(String message){        
    	byte[] prefix = { (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff };
    	byte[] msg = message.getBytes(Charset.forName("ASCII"));
    	byte[] fullMsg = new byte[prefix.length + msg.length];
    	System.arraycopy(prefix, 0, fullMsg, 0, prefix.length);
    	System.arraycopy(msg, 0, fullMsg, prefix.length, msg.length);
		return new DatagramPacket(fullMsg,fullMsg.length, addr, port);
    }
    
    static DatagramPacket makePacketConnect(String infoString){
    	String message = "connect ";
    	byte[] prefix = { (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff };
    	
    	
    	byte[] msg = message.getBytes(Charset.forName("ASCII"));
    	byte[] infoByte = infoString.getBytes(Charset.forName("ASCII"));
    	
    	byte[] fullMsg = new byte[prefix.length + msg.length+infoByte.length];
    	
    	System.arraycopy(prefix, 0, fullMsg, 0, prefix.length);
    	System.arraycopy(msg, 0, fullMsg, prefix.length, msg.length);
    	
    	System.arraycopy(infoByte, 0, fullMsg, prefix.length+msg.length, infoByte.length);
		return new DatagramPacket(fullMsg,fullMsg.length, addr, port);
    }
    
    static long getChallenge(String message){
    	byte[] prefix = { (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff };
    	System.out.println(new String(prefix));
    	message = message.replaceAll(new String(prefix),"");   	
    	message = message.replaceAll("\"","");
    	message = message.replaceAll(" ", "");
    	message = message.trim();
    	return Long.parseLong(message.replaceAll("challengeResponse", ""));
    }
    static DatagramPacket test(){
    	
    	
    	
    	byte peer0_1[] = {
    			 (byte)0x89, (byte)0x44, (byte)0x74, 
    			(byte)0x30, (byte)0x8f, (byte)0x3e, (byte)0x1c, (byte)0xc6, (byte)0x30, (byte)0x9c, (byte)0x55, 
    			(byte)0xdc, (byte)0x8f, (byte)0xfd, (byte)0x70, (byte)0x2a, (byte)0x26, (byte)0x1f, (byte)0xec, 
    			(byte)0x5a, (byte)0x0a, (byte)0x53, (byte)0x3f, (byte)0xc6, (byte)0x87, (byte)0x4d, (byte)0x1c, 
    			(byte)0xd0, (byte)0x8b, (byte)0x67, (byte)0x60, (byte)0x2b, (byte)0xc6, (byte)0x15, (byte)0x14, 
    			(byte)0x18, (byte)0x88, (byte)0xe6, (byte)0xc3, (byte)0x3e, (byte)0x4c, (byte)0xfe, (byte)0xee, 
    			(byte)0xbe, (byte)0xb8, (byte)0x7c, (byte)0x1d, (byte)0x30, (byte)0x8f, (byte)0x87, (byte)0xa3, 
    			(byte)0x5e, (byte)0x9a, (byte)0x9f, (byte)0xd0, (byte)0x1b, (byte)0x1c, (byte)0x75, (byte)0xd3, 
    			(byte)0xe0, (byte)0xa8, (byte)0x20, (byte)0xd4, (byte)0x42, (byte)0x56, (byte)0xd8, (byte)0x46, 
    			(byte)0xd1, (byte)0x53, (byte)0x0c, (byte)0x7d, (byte)0x3c, (byte)0x0d, (byte)0x22, (byte)0x22, 
    			(byte)0x17, (byte)0xaf, (byte)0xe8, (byte)0x25, (byte)0x27, (byte)0x8c, (byte)0xd1, (byte)0x94, 
    			(byte)0xd4, (byte)0x7c, (byte)0x93, (byte)0xba, (byte)0xc2, (byte)0x71, (byte)0x87, (byte)0xa5, 
    			(byte)0x97, (byte)0xff, (byte)0x5e, (byte)0x9a, (byte)0x38, (byte)0x9c, (byte)0x9e, (byte)0xea, 
    			(byte)0x12, (byte)0x2c, (byte)0x34, (byte)0x2f, (byte)0x31, (byte)0x5e, (byte)0x57, (byte)0x0f, 
    			(byte)0xda, (byte)0xa4, (byte)0x04, (byte)0x6b, (byte)0xdc, (byte)0x37, (byte)0xe0, (byte)0xe4, 
    			(byte)0x7f, (byte)0xdd, (byte)0x34, (byte)0x00 };
    	byte peer1[] = {
    			(byte)0x89, (byte)0x44, (byte)0x74, 
    			(byte)0x30, (byte)0x8f, (byte)0x3e, (byte)0x1c, (byte)0xc6, (byte)0x30, (byte)0x9c, (byte)0x55, 
    			(byte)0xdc, (byte)0x8f, (byte)0xfd, (byte)0x70, (byte)0x2a, (byte)0x26, (byte)0x1f, (byte)0xec, 
    			(byte)0x5a, (byte)0x0a, (byte)0x53, (byte)0x3f, (byte)0xc6, (byte)0x87, (byte)0x4d, (byte)0x1c, 
    			(byte)0xd0, (byte)0x8b, (byte)0x67, (byte)0x60, (byte)0x2b, (byte)0xc6, (byte)0x15, (byte)0x14, 
    			(byte)0x18, (byte)0x88, (byte)0xe6, (byte)0xc3, (byte)0x3e, (byte)0x4c, (byte)0xfe, (byte)0xee, 
    			(byte)0xbe, (byte)0xb8, (byte)0x7c, (byte)0x1d, (byte)0x30, (byte)0x8f, (byte)0x87, (byte)0xa3, 
    			(byte)0x5e, (byte)0x9a, (byte)0x9f, (byte)0xd0, (byte)0x1b, (byte)0x1c, (byte)0x75, (byte)0xd3, 
    			(byte)0xe0, (byte)0xa8, (byte)0x20, (byte)0xd4, (byte)0x42, (byte)0x56, (byte)0xd8, (byte)0x46, 
    			(byte)0xd1, (byte)0x53, (byte)0x0c, (byte)0x7d, (byte)0x3c, (byte)0x0d, (byte)0x22, (byte)0x22, 
    			(byte)0x17, (byte)0xaf, (byte)0xe8, (byte)0x25, (byte)0x27, (byte)0x8c, (byte)0xd1, (byte)0x94, 
    			(byte)0xd4, (byte)0x7c, (byte)0x93, (byte)0xba, (byte)0xc2, (byte)0x71, (byte)0x87, (byte)0xa5, 
    			(byte)0x97, (byte)0xff, (byte)0x5e, (byte)0x9a, (byte)0xc0, (byte)0x26, (byte)0xc2, (byte)0x4a, 
    			(byte)0x7e, (byte)0x0f, (byte)0xf6, (byte)0x9e, (byte)0x01, (byte)0x16, (byte)0x9a, (byte)0x27, 
    			(byte)0xdd, (byte)0x84, (byte)0x6b, (byte)0xce, (byte)0x32, (byte)0x9c, (byte)0x72, (byte)0x76, 
    			(byte)0xa2, (byte)0x5f, (byte)0xe8, (byte)0xb4, (byte)0x03 };
    	
    	char peer2[] = {
    			0xaa, 0x44, 0x74,
    			0x30, 0x8f, 0x3e, 0x1c, 0xc6, 0x30, 0x9c, 0x55,
    			0xdc, 0x8f, 0xfd, 0x70, 0x2a, 0x26, 0x1f, 0xec,
    			0x5a, 0x0a, 0x53, 0x3f, 0xc6, 0x87, 0x4d, 0xc4,
    			0xe2, 0x5a, 0x58, 0x16, 0x67, 0x24, 0xd6, 0x44,
    			0xa1, 0xb9, 0x25, 0x9c, 0x17, 0x44, 0xc6, 0xb9,
    			0x2b, 0x97, 0x0e, 0x8c, 0xca, 0x1c, 0xd8, 0x1e,
    			0xf5, 0xe8, 0x1d, 0x11, 0xf5, 0xbb, 0xc6, 0xf1,
    			0x85, 0xd0, 0x05, 0xe9, 0x9a, 0x06, 0x66, 0x3e,
    			0xbe, 0x56, 0x20, 0xb8, 0x14, 0x7f, 0x3f, 0x98,
    			0x1e, 0x18, 0x5a, 0xaf, 0x70, 0x01, 0x96, 0xdc,
    			0x6b, 0xae, 0x90, 0x58, 0xac, 0x1b, 0xcf, 0x2f,
    			0xff, 0xa7, 0xbb, 0x6e, 0x4d, 0x20, 0xa2, 0x06,
    			0x8c, 0xd2, 0x9f, 0x92, 0x3d, 0x2f, 0xe8, 0x2c,
    			0x30, 0x9b, 0x37, 0x25, 0x1c, 0x75, 0x58, 0x62,
    			0x68, 0x4f, 0x91, 0xb0, 0x54, 0x82, 0x73, 0x09,
    			0x2b, 0xdf, 0xc4, 0x60, 0xf1, 0xee, 0xb7, 0xb0,
    			0x5d, 0x86, 0xd0, 0x8e, 0x18, 0x6c, 0x5c, 0x53,
    			0xbc, 0x2f, 0x41, 0xa8, 0x5a, 0x01 };
    	String txt = new String(peer2);
    	byte[] peer3 = txt.getBytes(Charset.forName("ASCII"));
    		AdaptiveHuffman huff = null;
			huff = new AdaptiveHuffman(peer2);
    		System.out.println(new String(peer1));
    		String ah = huff.decode();
    		System.out.println(ah);
    	
    	return new DatagramPacket(ah.getBytes(Charset.forName("ASCII")),ah.getBytes(Charset.forName("ASCII")).length,addr,port);
    }
    
    
}