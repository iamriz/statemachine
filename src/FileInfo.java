public class FileInfo {
	FileState state;
	String filePath;
	
	public FileInfo(FileState state, String filePath) {
		this.state = state;
		this.filePath = filePath;
	}

	public FileState getState() {
		return state;
	}

	public void setState(FileState state) {
		this.state = state;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
