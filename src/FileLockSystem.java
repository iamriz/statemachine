import java.util.concurrent.ConcurrentHashMap;

public class FileLockSystem {

	private ConcurrentHashMap<String, FileInfo> lockMap;

	public FileLockSystem() {
		lockMap = new ConcurrentHashMap<String, FileInfo>();
	}

	public static void main(String[] args) {
		FileInfo file1 = new FileInfo(FileState.READ, "/home/reader.txt");
		FileInfo file2 = new FileInfo(FileState.READ, "/home/retriever.txt");
		FileInfo file3 = new FileInfo(FileState.PRE_UPLOAD, "/home/uploader.txt");
		FileInfo file4 = new FileInfo(FileState.WRITE, "/home/writer.txt");
	
		FileLockSystem fileLock = new FileLockSystem();
		fileLock.addFile(file1);
		fileLock.addFile(file2);
		fileLock.addFile(file3);
		fileLock.addFile(file4);
		
		Threading R1 = new Threading("Thread-PRE_UPLOAD", fileLock, FileState.PRE_UPLOAD, "/home/writer.txt");
		Thread thread1 = new Thread(R1);
		thread1.setDaemon(true);
		thread1.start();

		Threading R2 = new Threading("Thread-RELEASED", fileLock, FileState.RELEASED, "/home/writer.txt");
		Thread thread2 = new Thread(R2);
		thread2.setPriority(Thread.MIN_PRIORITY);
		thread2.start();
		
//		cRunnable R2 = new cRunnable("Thread-PRE_UPLOAD", fileLock, FileState.WRITE, "/home/preuploader.txt");
//		R2.start();
	}

	//enter
	public void lock(String filePath, FileState state) {
		if(!lockMap.containsKey(filePath)) {
			FileInfo file = lockMap.get(filePath);
			file.setState(state);
			lockMap.put(file.getFilePath(), file);
		}
	}

	//exit
	public void release(String filePath, FileState state) {
		if(lockMap.containsKey(filePath)) {
			lockMap.remove(filePath);
		}
	}

	public ConcurrentHashMap<String, FileInfo> getLockMap() {
		return lockMap;
	}

	public void setLockMap(ConcurrentHashMap<String, FileInfo> lock) {
		this.lockMap = lock;
	}
	
	public void addFile(FileInfo file) {
		this.lockMap.put(file.getFilePath(), file);
	}
}