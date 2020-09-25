package net.codetojoy.data;

import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.ImmutablePair;

public interface RowFilter {
    boolean doExclude(DataInfo dataInfo);
}
