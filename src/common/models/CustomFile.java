package common.models;

import java.io.File;
import java.util.ArrayList;

public abstract class CustomFile {
	protected File file;

	protected CustomFile(File file) {
		this.file = file;
	}
	protected CustomFile(String filePath) {
		this.file = new File(filePath);
	}
}
