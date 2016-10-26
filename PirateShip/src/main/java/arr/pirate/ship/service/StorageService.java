package arr.pirate.ship.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import arr.pirate.ship.model.Content;

@Component
public class StorageService {

	private String docStorageRootProperty = "../pirate/contentStorage";

	private Path documentStorageRoot;

	private Path getDocumentStorageRoot() {
		if (documentStorageRoot == null) {
			synchronized (this) {
				if (documentStorageRoot == null) {
					documentStorageRoot = Paths.get(docStorageRootProperty);
				}
			}
		}

		return documentStorageRoot;
	}

	public void storeContent(Content doc, byte[] contents) throws RuntimeException {
		Path path = getDocumentStorageRoot().resolve(resolveDocumentFileSystemLocation(doc));
		try {
			Files.createDirectories(path.getParent());
			try (FileOutputStream cos = new FileOutputStream(path.toFile())) {
				IOUtils.write(contents, cos);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public byte[] getContentAsBytes(Content doc) throws RuntimeException {
		return getContentAsBytes(resolveDocumentFileSystemLocation(doc));
	}

	public byte[] getContentAsBytes(String location) throws RuntimeException {
		try {
			Path path = getDocumentStorageRoot().resolve(location);
			try (FileInputStream cis = new FileInputStream(path.toFile())) {
				byte[] data = IOUtils.toByteArray(cis);
				return data;
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void deleteDocument(Content doc) throws RuntimeException {
		deleteDocument(resolveDocumentFileSystemLocation(doc));
	}

	public void deleteDocument(String location) throws RuntimeException {
		Path path = getDocumentStorageRoot().resolve(location);
		try {
			Files.delete(path);
			Files.delete(path.getParent()); // also delete the directory which
											// holds the file.
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private String resolveDocumentFileSystemLocation(Content doc) {
		StringBuilder builder = new StringBuilder();
		builder.append(doc.getId()).append("/");
		builder.append("content");
		return builder.toString();
	}
}
