package main;

import com.google.common.collect.ComparisonChain;

public class RecordComparator {
    static public int compare(Record record01, Record record02) {
        return ComparisonChain.start()
                .compare(record01.getBook(), record02.getBook())
                .compare(record02.getSignature(), record01.getSignature())
                .result();

    }
}
