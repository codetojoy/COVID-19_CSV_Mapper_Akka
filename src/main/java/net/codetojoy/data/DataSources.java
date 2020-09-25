package net.codetojoy.data;

interface DataSourcesProfile {
    DataSource getProdDataSource();
    DataSource getProdDataSource(String inputCsvFilename);
}

class SimpleDataSources implements DataSourcesProfile {
    @Override
    public DataSource getProdDataSource() {
        return new SimpleDataSource();
    }

    @Override
    public DataSource getProdDataSource(String inputCsvFilename) {
        return new SimpleDataSource();
    }
}

class ProdDataSources implements DataSourcesProfile {
    private static DataSource prodDataSource = null;

    @Override
    public DataSource getProdDataSource() {
        if (prodDataSource == null) {
            throw new IllegalStateException("internal error prodDataSource");
        }
        return prodDataSource;
    }

    @Override
    public DataSource getProdDataSource(String inputCsvFilename) {
        prodDataSource = new CsvDataSource(inputCsvFilename);
        return prodDataSource;
    }
}

public class DataSources {
    // NOTE: change this to switch from Simple to PROD
    // private static DataSourcesProfile dataSourcesProfile = new SimpleDataSources();
    private static DataSourcesProfile dataSourcesProfile = new ProdDataSources();

    public static DataSource getProdDataSource() {
        return dataSourcesProfile.getProdDataSource();
    }

    public static DataSource getProdDataSource(String inputCsvFilename) {
        return dataSourcesProfile.getProdDataSource(inputCsvFilename);
    }

    public static DataSource getSimpleDataSource() {
        return new SimpleDataSource();
    }

    public static RowFilter getRowFilter() {
        return new CsvRowFilter();
    }
}
