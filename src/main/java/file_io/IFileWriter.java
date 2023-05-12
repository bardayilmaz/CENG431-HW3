package file_io;

public interface IFileWriter<T> {
	
	void write(T entry, String filePath);
}
