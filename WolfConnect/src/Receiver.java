import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import com.adaptivehuffman.AdaptiveHuffman;

public class Receiver {
	static InetAddress addr;
	static int port = 27960;
    public static void main(String[] args) {
    	writePacket();
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
        	test();
            System.out.println(new String(receivedPacket.getData()));
            challenge = getChallenge(new String(receivedPacket.getData()));
            //System.out.println(challengeResponse(challenge));
            Scanner input = new Scanner(System.in);
            String toSend = input.nextLine();
           
            socket.send(emptyPack(toSend));
        	//socket.send(test());
            socket.receive(receivedPacket);
            System.out.println(new String(receivedPacket.getData()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
     
    }
    
    static String challengeResponse(long challengeToken){
    	byte[] prefix = { (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff };
    	String crafted = "\"\\g_password\\none\\cl_guid\\unknown\\cl_wwwDownload\\1\\name\\^pW^q!^pnn^q3^pr\\rate\\32000\\snaps\\20\\cl_anonymous\\0\\cl_punkbuster\\0\\protocol\\84\\qport\\18951\\challenge\\-1686583615\\\"";
    	
		return new String(prefix)+"connect "+crafted;
    	
    }
    
    static DatagramPacket emptyPack(String message){        
    	//byte[] prefix = { (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff };
    	byte[] msg = message.getBytes(Charset.forName("ASCII"));
    	//byte[] fullMsg = new byte[prefix.length + msg.length];
    	//System.arraycopy(prefix, 0, fullMsg, 0, prefix.length);
    	//System.arraycopy(msg, 0, fullMsg, prefix.length, msg.length);
		return new DatagramPacket(msg,msg.length, addr, port);
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
    	//System.out.println(new String(prefix));
    	message = message.replaceAll(new String(prefix),"");   	
    	message = message.replaceAll("\"","");
    	message = message.replaceAll(" ", "");
    	message = message.trim();
    	return Long.parseLong(message.replaceAll("challengeResponse", ""));
    }
    
    
    static void writePacket(){
    	char peer0_1[] = {
    			(char)0xff, (char)0xff, (char)0xff, (char)0xff, (char)0x63, (char)0x6f, (char)0x6e, (char)0x6e,
    			(char)0x65, (char)0x63, (char)0x74,(char) 0x20,(char) 0x00,(char) 0xaa,(char) 0x44,(char) 0x74,(char)
    			0x30,(char) 0x8f,(char) 0x3e,(char) 0x1c,(char) 0xc6,(char) 0x30,(char) 0x9c,(char) 0x55,(char)
    			0xdc,(char) 0x8f,(char) 0xfd,(char) 0x70,(char) 0x2a,(char) 0x26,(char) 0x1f,(char) 0xec,(char)
    			0x5a,(char) 0x0a,(char) 0x53,(char) 0x3f,(char) 0xc6,(char) 0x87,(char) 0x4d,(char) 0xc4,(char)
    			0xe2,(char) 0x5a,(char) 0x58,(char) 0x16,(char) 0x67,(char) 0x24,(char) 0xd6,(char) 0x44,(char)
    			0xa1,(char) 0xb9,(char) 0x25,(char) 0x9c,(char) 0x17,(char) 0x44,(char) 0xc6,(char) 0xb9,(char)
    			0x2b,(char) 0x97,(char) 0x0e,(char) 0x8c,(char) 0xca,(char) 0x1c,(char) 0xd8,(char) 0x1e,(char)
    			0xf5,(char) 0xe8,(char) 0x1d,(char) 0x11,(char) 0xf5,(char) 0xbb,(char) 0xc6,(char) 0xf1,(char)
    			0x85,(char) 0xd0,(char) 0x05,(char) 0xe9,(char) 0x9a,(char) 0x06,(char) 0x66,(char) 0x3e,(char)
    			0xbe,(char) 0x56,(char) 0x20,(char) 0xb8,(char) 0x14,(char) 0x7f,(char) 0x3f,(char) 0x98,(char)
    			0x1e,(char) 0x18,(char) 0x5a,(char) 0xaf,(char) 0x70,(char) 0x01,(char) 0x96,(char) 0xdc,(char)
    			0x6b,(char) 0xae,(char) 0x90,(char) 0x58,(char) 0xac,(char) 0x1b,(char) 0xcf,(char) 0x2f,(char)
    			0xff,(char) 0xa7,(char) 0xbb,(char) 0x6e,(char) 0x4d,(char) 0x20,(char) 0xa2,(char) 0x06,(char)
    			0x8c,(char) 0xd2,(char) 0x9f,(char) 0x92,(char) 0x3d,(char) 0x2f,(char) 0xe8,(char) 0x2c,(char)
    			0x30,(char) 0x9b,(char) 0x37,(char) 0x25,(char) 0x1c,(char) 0x75,(char) 0x58,(char) 0x62,(char)
    			0x68,(char) 0x4f,(char) 0x91,(char) 0xb0,(char) 0x54,(char) 0x82,(char) 0x73,(char) 0x09,(char)
    			0x2b,(char) 0xdf,(char) 0xc4,(char) 0x60,(char) 0xf1,(char) 0xee,(char) 0xb7,(char) 0xb0,(char)
    			0x5d,(char) 0x86,(char) 0xd0,(char) 0x8e,(char) 0x18,(char) 0x6c,(char) 0x5c,(char) 0x53,(char)
    			0xbc,(char) 0x2f,(char) 0x41,(char) 0xa8,(char) 0x5a,(char) 0x01 };
    	

        try
    {
       File f=new File("WriteChar.txt");

       FileWriter out=new FileWriter(f);
       out.write(peer0_1);
       System.out.println("Done ..........");
       out.close();
    }

        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
    		
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
    		System.out.println(new String(peer1)+""+peer1.length);
    		String ah = huff.decode();
    		//System.out.println(ah);
    	
    	return new DatagramPacket(ah.getBytes(Charset.forName("ASCII")),ah.getBytes(Charset.forName("ASCII")).length,addr,port);
    }
    
    
}