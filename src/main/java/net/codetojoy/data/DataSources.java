package net.codetojoy.data;

public class DataSources {
    private static DataSource prodDataSource = null;

    public static DataSource getProdDataSource() {
        if (prodDataSource == null) {
            throw new IllegalStateException("internal error prodDataSource");
        }
        return prodDataSource;
    }

    public static DataSource getProdDataSource(String inputCsvFilename) {
        prodDataSource = new CsvDataSource(inputCsvFilename);
        return prodDataSource;
    }

    public static DataSource getSimpleDataSource() {
        return new SimpleDataSource();
    }
}
