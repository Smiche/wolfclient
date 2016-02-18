
public class HuffImport {
	 private native void print();

	 public static void main(String[] args) {
	     new HuffImport().print();
	 }

	 static {
	     System.loadLibrary("Huffport");
	 } 
}
