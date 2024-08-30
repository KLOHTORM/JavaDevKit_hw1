package repository;

import java.io.*;

public class FileRepository implements Repository<String>{
    private final String fileName;

    public FileRepository(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void save(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String load() {
        File file = new File(fileName);
        StringBuilder builder = new StringBuilder();
        if (file.exists() && file.isFile()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                while (reader.ready()) {
                    builder.append(reader.readLine()).append('\n');
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return builder.toString();
    }
}
