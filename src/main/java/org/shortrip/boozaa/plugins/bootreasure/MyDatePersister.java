package org.shortrip.boozaa.plugins.bootreasure;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.DateType;
import com.j256.ormlite.support.DatabaseResults;

public class MyDatePersister extends DateType {

	private static final MyDatePersister singleTon = new MyDatePersister();
    @SuppressWarnings("deprecation")
    private static final Timestamp ZERO_TIMESTAMP = new Timestamp(1970, 0, 0, 0, 0, 0, 0);

    private MyDatePersister() {
            super(SqlType.DATE, new Class<?>[] { Date.class });
    }

    public static MyDatePersister getSingleton() {
            return singleTon;
    }

    @Override
    public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) throws SQLException {
            Timestamp timestamp = results.getTimestamp(columnPos);
            if (timestamp == null || ZERO_TIMESTAMP.after(timestamp)) {
                    return null;
            } else {
                    return timestamp.getTime();
            }
    }

    @Override
    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
            if (sqlArg == null) {
                    return null;
            } else {
                    return new Date((Long) sqlArg);
            }
    }

}
