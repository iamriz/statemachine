public class StateMachineDemo {
	public static void main(String[] args) {
		FileInfo file1 = new FileInfo(FileState.READ, "/home/reader.txt");
		FileInfo file2 = new FileInfo(FileState.READ, "/home/retriever.txt");
		FileInfo file3 = new FileInfo(FileState.PRE_UPLOAD, "/home/uploader.txt");

		FileLockSystem fileLock = new FileLockSystem();
		fileLock.addFile(file1);
		fileLock.addFile(file2);
		fileLock.addFile(file3);

		// performer 1
		Performer READ1 = new Performer("READ1", fileLock, FileState.READ, "/home/writer.txt");
		Thread threadREAD1 = new Thread(READ1);
		threadREAD1.start();
		
		// performer 2
		Performer WRITE1 = new Performer("WRITE1", fileLock, FileState.WRITE, "/home/writer.txt");
		Thread threadWRITE1 = new Thread(WRITE1);
		threadWRITE1.start();

		// performer 3 - interrupter 1
		Interrupter R1 = new Interrupter("PRE_UPLOAD2", fileLock, FileState.PRE_UPLOAD, "/home/writer.txt");
		Thread thread1 = new Thread(R1);
		thread1.start();

		// performer 4 - interrupter 2
		Interrupter R2 = new Interrupter("WRITE2", fileLock, FileState.WRITE, "/home/writer.txt");
		Thread thread2 = new Thread(R2);
		thread2.start();
		
		// performer 5 - interrupter 3
		Interrupter R3 = new Interrupter("READ2", fileLock, FileState.READ, "/home/writer.txt");
		Thread thread3 = new Thread(R3);
		thread3.start();
	}
}
