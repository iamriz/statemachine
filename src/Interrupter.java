public class Interrupter implements Runnable {
	private Thread t;
	private String threadName;
	private FileLockSystem fileLock;
	private FileState state;
	private String filePath;

	public Interrupter(String name, FileLockSystem fileLock, FileState state,
			String filePath) {
		this.threadName = name;
		this.fileLock = fileLock;
		this.state = state;
		this.filePath = filePath;
	}

	public void run() {
		System.out.println("Interrupter: Trying to run Interrupter " + threadName);
		int i = 1;
		while (true) {
			if (fileLock.isLocked(filePath, this.state)) {
				System.out.println("------------------------- Interrupter: File is currently LOCKED. " + threadName + " is put on HOLD.");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					System.out.println("Thread interrupted.");
				}
			} else {
				System.out.println("Interrupter: File is in good state.");
				System.out.println("Interrupter: " + threadName + " is STARTED... " + i++);				
				break;
			}
		}
		System.out.println("Interrupter: " + threadName + " recognized. Trying to RUN.");
		
		//Perform the queued Interrupter action.
		Performer P1 = new Performer("Interrupter-" + threadName, fileLock, state, filePath);
		Thread thread0 = new Thread(P1);
		thread0.start();
	}

	public void start() {
		System.out.println("Starting Interrupter " + threadName);
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}
}
