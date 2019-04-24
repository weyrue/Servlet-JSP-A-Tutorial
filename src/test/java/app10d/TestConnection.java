package app10d;

import app10d.dao.DataSourceCache;

public class TestConnection {
    public static void main(String[] args) {
        DataSourceCache.getInstance().getDataSource();
    }
}
