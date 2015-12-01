import java.util.concurrent.ConcurrentHashMap;

public class FileLockSystem {

	private ConcurrentHashMap<String, FileInfo> fileMap;

	public FileLockSystem() {
		fileMap = new ConcurrentHashMap<String, FileInfo>();
	}

	public static void main(String[] args) {
		// run test threads here
	}

	public void lock(String filePath, FileState state) {
	}

	public void release(String filePath, FileState state) {
	}
}
