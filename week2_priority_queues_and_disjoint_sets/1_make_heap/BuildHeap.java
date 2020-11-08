import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/*Code that is coded outside of the method was created by: Robert Johns
 * 
 */
public class BuildHeap {
	private int[] data;
	private List<Swap> swaps;

	private FastScanner in;
	private PrintWriter out;

	public static void main(String[] args) throws IOException {
		new BuildHeap().solve();
	}

	private void readData() throws IOException {
		int n = in.nextInt();
		data = new int[n];
		for (int i = 0; i < n; ++i) {
			data[i] = in.nextInt();
		}
	}

	private void writeResponse() {
		out.println(swaps.size());
		for (Swap swap : swaps) {
			out.println(swap.index1 + " " + swap.index2);
		}
	}

	/*This method gets each value of the index passed in generateSwaps.
	 * It puts that index in the minIndex then gets right and left potential values.
	 * It checks to see if each value is less than the size of the array and if the 
	 * position relative to those values are less than the min index.
	 * If not it swaps the data, other than that it keeps it in!
	 */
	private void SiftDown(int index) {
		int minIndex = index;
		int right = 2 * index + 2;
		int left = 2 * index + 1;
		if (right < data.length && data[right] < data[minIndex]) {
			minIndex = right;
		}
		if (left < data.length && data[left] < data[minIndex]) {
			minIndex = left;
		}
		if (minIndex != index) {
			Swap(data, index, minIndex);
			swaps.add(new Swap(index, minIndex));
			SiftDown(minIndex);
		}
	}
	
	/* Does the actual swapping process with the first and second values.
	 */
	private static void Swap(int[] data, int first, int second) {
		int temp = data[first];
		data[first] = data[second];
		data[second] = temp;
	}
	
	private void generateSwaps() {
		swaps = new ArrayList<Swap>();
		/*
		// The following naive implementation just sorts
		// the given sequence using selection sort algorithm
		// and saves the resulting sequence of swaps.
		// This turns the given array into a heap,
		// but in the worst case gives a quadratic number of swaps.
		//
		// TODO: replace by a more efficient implementation
		for (int i = 0; i < data.length; ++i) {
			for (int j = i + 1; j < data.length; ++j) {
				if (data[i] > data[j]) {
					swaps.add(new Swap(i, j));
					int tmp = data[i];
					data[i] = data[j];
					data[j] = tmp;
				}
			}
		}
		*/
		
		for( int x = ( data.length - 2) >> 1; x >= 0; x--) {
            SiftDown(x);
		}
	}
	

	public void solve() throws IOException {
		in = new FastScanner();
		out = new PrintWriter(new BufferedOutputStream(System.out));
		readData();
		generateSwaps();
		writeResponse();
		out.close();
	}

	static class Swap {
		int index1;
		int index2;

		public Swap(int index1, int index2) {
			this.index1 = index1;
			this.index2 = index2;
		}
	}

	static class FastScanner {
		private BufferedReader reader;
		private StringTokenizer tokenizer;

		public FastScanner() {
			reader = new BufferedReader(new InputStreamReader(System.in));
			tokenizer = null;
		}

		public String next() throws IOException {
			while (tokenizer == null || !tokenizer.hasMoreTokens()) {
				tokenizer = new StringTokenizer(reader.readLine());
			}
			return tokenizer.nextToken();
		}

		public int nextInt() throws IOException {
			return Integer.parseInt(next());
		}
	}
}
