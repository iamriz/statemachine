public enum FileState {
	WRITE 		("1"),
	READ  		("2"),
	PRE_UPLOAD	("3"),
	UPLOAD		("4"),
	DELETE		("5");
	private final String code;

	FileState(String code) {
        this.code = code;
    }

	public String code() {
		return code;
	}
}
