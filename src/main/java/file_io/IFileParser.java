package file_io;

import java.io.File;

public interface IFileParser<T> {

	T parse(String filePath);
}
