package edu.training.news_portal.dao.impl;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import edu.training.news_portal.dao.DaoException;
import edu.training.news_portal.dao.FileRepository;

public class FileRepositoryImpl implements FileRepository {

	@Override
	public String saveNews(String news) throws DaoException {
		try {
			URL location = FileRepositoryImpl.class.getProtectionDomain().getCodeSource().getLocation();
			Path path = Paths.get(location.toURI());

			Path projectRoot = path.getParent().getParent().getParent().getParent().getParent().getParent().getParent()
					.getParent(); // просто хотелось чтоб оно попало в нужную папку, выглядит страшненько)

			Path contentDir = projectRoot
					.resolve(Paths.get("my_project", "src", "main", "resources", "news", "content"));

			File dir = contentDir.toFile();
			if (!dir.exists()) {
				dir.mkdirs();
			}

			String fileName = news.hashCode() + ".txt";
			File file = new File(dir, fileName);

			try (PrintWriter writer = new PrintWriter(file)) {
				writer.print(news);
				return file.getAbsolutePath();
			}

		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	@Override
	public String getNews(String path) throws DaoException {
		Path filePath = Paths.get(path);
		try {
			return Files.readString(filePath);
		} catch (IOException e) {
			throw new DaoException("Ошибка при чтении файла новости: " + path, e);
		}
	}

}
