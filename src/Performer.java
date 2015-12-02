public class Performer implements Runnable {
	private Thread t;
	private String threadName;
	private FileLockSystem fileLock;
	private FileState state;
	private String filePath;

	public Performer(String name, FileLockSystem fileLock, FileState state, String filePath) {
		this.threadName = name;
		this.fileLock = fileLock;
		this.state = state;
		this.filePath = filePath;
	}

	public void run() {
		System.out.println("Performer: Trying to run " + threadName);
		boolean haslocked = false;
		for (int i = 50; i > 0; i--) {			
			if(!haslocked && fileLock.isLocked(filePath, this.state)) {
				System.out.println("------------------------- Performer: File is currently LOCKED. " + threadName + " is put on HOLD.");
				
				Interrupter R1 = new Interrupter(threadName, fileLock, this.state, this.filePath);
				Thread thread1 = new Thread(R1);
				thread1.start();
				break;
			} else if(!haslocked) {
				fileLock.lock(filePath, state);
				haslocked = true;
				System.out.println("Performer: " + threadName + " successfully LOCKED file: " + filePath + ".");	
				System.out.println("Performer: Running " + threadName + "... " + i);
			} else { // if this thread locked the file (haslocked == true)
				System.out.println("Performing " + threadName + "... " + i);
			}
			try {
				//delay current state
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.out.println("Thread interrupted.");
			}
		}
		fileLock.release(filePath, state);
		if(haslocked)
			System.out.println("Performer: " + threadName + " execution DONE. Successfully UNLOCKED.");
	}

	public void start() {
		System.out.println("Starting Performer " + threadName);
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}

}
