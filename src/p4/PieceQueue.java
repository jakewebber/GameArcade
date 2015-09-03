package p4;
import java.util.*;
public class PieceQueue {
	public static Queue<Integer> queue = new LinkedList<Integer>();
	private static Random generator = new Random();
	
	public PieceQueue() {
		for(int i = 0; i < 200; i++) {
			int temp = generator.nextInt(7); //number 0-6 (7 pieces)
			queue.add(temp);
		}
	}
}