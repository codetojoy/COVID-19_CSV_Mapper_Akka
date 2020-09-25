package net.codetojoy.data;

public class DataSources {
    public static DataSource getProdDataSource() {
        return new SimpleDataSource();
    }

    public static DataSource getSimpleDataSource() {
        return new SimpleDataSource();
    }
}
