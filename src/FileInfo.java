import java.util.ArrayList;
import java.util.List;

public class FileInfo {
	List<FileState> state = null;
	String filePath;
	
	public FileInfo(FileState state, String filePath) {
		if(this.state != null) {
			this.state.add(state);
		} else {
			this.state = new ArrayList<FileState>();
			this.state.add(state);
		}
		this.filePath = filePath;
	}

	public List<FileState> getState() {
		return state;
	}

	public void setState(List<FileState> state) {
		this.state = state;
	}
	
	public void addState(FileState state) {
		this.state.add(state);
	}
	
	public void removeState(FileState state) {
		this.state.remove(state);
	}

	public boolean hasState(FileState state) {
		return this.state.contains(state);
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if(this.state != null && !this.state.isEmpty()) {			
			int i = 0;
			sb.append("File " + filePath + ": States >>>");
			for(FileState s : state) {
				sb.append("\n(" + i + "): " + s);
			}
		}
		return sb.toString();
	}
}
