package net.codetojoy.data;

public class CsvRowFilter implements RowFilter {
    @Override
    public boolean doExclude(DataInfo dataInfo) {
        boolean result = false;

        try {
            String caseIdStr = dataInfo.caseId;
            int dummy = Integer.parseInt(caseIdStr);
        } catch (Exception ex) {
            result = true;
        }

        return result;
    }
}
