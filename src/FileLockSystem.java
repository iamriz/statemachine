import java.util.concurrent.ConcurrentHashMap;

public class FileLockSystem {

	private ConcurrentHashMap<String, FileInfo> lockMap;

	public FileLockSystem() {
		lockMap = new ConcurrentHashMap<String, FileInfo>();
	}

	// enter
	public void lock(String filePath, FileState state) {
		//if file is already in the lockMap
		if (lockMap.containsKey(filePath)) {
			FileInfo file = lockMap.get(filePath);
			file.addState(state);
			lockMap.put(filePath, file);
		} else {
			lockMap.put(filePath, new FileInfo(state, filePath));
		}
	}

	// exit
	public void release(String filePath, FileState state) {
		if (lockMap.containsKey(filePath)) {
			FileInfo file = lockMap.get(filePath);
			if(file.hasState(state)) 
				file.removeState(state);
			if(file.getState().size() == 0)
				lockMap.remove(filePath);
		}
	}

	/**
	 * If file is currently in READ state, then it is not locked.
	 * Otherwise, it is locked.
	 * @param filePath
	 * @return
	 */
	public boolean isLocked(String filePath, FileState newState) {
		FileInfo file = lockMap.get(filePath);
		if(file != null) {
			if(FileState.READ.code().equals(newState.code())) {
				return false;
			} else if(file.hasState(FileState.DELETE) ||
			   file.hasState(FileState.PRE_UPLOAD) ||
			   file.hasState(FileState.UPLOAD) ||
			   file.hasState(FileState.WRITE)) {
				return true;
			} 
		}
		return false;
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