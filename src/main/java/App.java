package main.java;

import java.util.List;

public class App {

    public static final String dbUrl = "jdbc:postgresql://localhost:5432/postgres";
    public static final String dbUser = "postgres";
    public static final String dbPassword = "ylevol";
    public static final long N = 1000000;

    public static void main(String[] args) throws Exception {
        try {
            //Создаем базу данных и вставляем fields со значениями от 1 до N
            DB db = new DB();
            db.setUrl(dbUrl);
            db.setUser(dbUser);
            db.setPassword(dbPassword);
            db.insertFields(N);

            //записываем fields в XML
            List<Integer> fields = db.getFields();
            Writer writer = new Writer();
            writer.setFields(fields);
            writer.setFilePath("src/main/resources");
            writer.writeXml();

            //Парсим fields из XML
            Parser parser = new Parser();
            parser.setFilePath("src/main/resources");
            parser.transformXml();

            //Считаем искомую суммы fields
            List<Integer> integers = parser.parseXml();
            long sumFields = 0;
            for (int i : integers) {
                sumFields = sumFields + i;
            }

            System.out.println(sumFields);

        }catch (Exception e) {
            e.getMessage();
        }
    }
}
