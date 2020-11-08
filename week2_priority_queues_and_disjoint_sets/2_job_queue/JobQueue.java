import java.io.*;
import java.util.*;

public class JobQueue {
    private int numWorkers;
    private int[] jobs;

    private int[] assignedWorker;
    private long[] startTime;

    private FastScanner in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        new JobQueue().solve();
    }

    private void readData() throws IOException {
        numWorkers = in.nextInt();
        int m = in.nextInt();
        jobs = new int[m];
        for (int i = 0; i < m; ++i) {
            jobs[i] = in.nextInt();
        }
    }

    private void writeResponse() {
        for (int i = 0; i < jobs.length; ++i) {
            out.println(assignedWorker[i] + " " + startTime[i]);
        }
    }

    private void assignJobs() {
        // TODO: replace this code with a faster algorithm.
    	Queue<Thread> threadQueue = new PriorityQueue<>();
        assignedWorker = new int[jobs.length];
        startTime = new long[jobs.length];
        
        for (int x = 0; x < numWorkers; x++) {
        	Thread t  = new Thread(x, 0);
        	threadQueue.offer(t);
        }
        for (int i = 0; i < jobs.length; i++) {
        	int duration = jobs[i];
            Thread thread = threadQueue.poll();
            assignedWorker[i] = thread.getName();
            startTime[i] = thread.getNextFreeTime();
            thread.setNextFreeTime(duration);
            threadQueue.offer(thread);
        }
        
    }

    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        readData();
        assignJobs();
        writeResponse();
        out.close();
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
    
    public class Thread implements Comparable<Thread>{
    	private int name;
    	private long nextFreeTime;
    	
    	public Thread(int n, long nFT) {
    		name = n;
    		nextFreeTime = nFT;
    	}
		public long getNextFreeTime() {
			return nextFreeTime;
		}
		public void setNextFreeTime(long nextFreeTime) {
			this.nextFreeTime += nextFreeTime;
		}
		public int getName() {
			return name;
		}
		
		/*Allows Priority queue to know which thread is at the top (to poll, peek, ect.) 
		 * based on nextFreeTime
		 */
		@Override
		public int compareTo(Thread t2){
    		if (this.getNextFreeTime() < t2.getNextFreeTime()) {
    			return -1;
    		} else if (this.getNextFreeTime() > t2.getNextFreeTime()) {
    			return 1;
    		} else if (this.getNextFreeTime() == t2.getNextFreeTime()) {
    			return this.getName() - t2.getName();
    		} else {
    			return 0;
    		}
    	}
		
    }
}