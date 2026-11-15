package com.borrowbox.app.data.local;

import androidx.room.TypeConverter;
import com.borrowbox.app.model.PriceUnit;
import com.borrowbox.app.model.RentalStatus;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Converters {

    @TypeConverter
    public static String fromStringList(List<String> list) {
        if (list == null || list.isEmpty()) return "";
        return String.join(",", list);
    }

    @TypeConverter
    public static List<String> toStringList(String data) {
        if (data == null || data.trim().isEmpty()) return new ArrayList<>();
        return new ArrayList<>(Arrays.asList(data.split(",")));
    }

    @TypeConverter
    public static String fromPriceUnit(PriceUnit unit) {
        return unit == null ? PriceUnit.HOUR.name() : unit.name();
    }

    @TypeConverter
    public static PriceUnit toPriceUnit(String name) {
        try {
            return name == null ? PriceUnit.HOUR : PriceUnit.valueOf(name);
        } catch (IllegalArgumentException e) {
            return PriceUnit.HOUR;
        }
    }

    @TypeConverter
    public static String fromRentalStatus(RentalStatus status) {
        return status == null ? RentalStatus.REQUESTED.name() : status.name();
    }

    @TypeConverter
    public static RentalStatus toRentalStatus(String name) {
        try {
            return name == null ? RentalStatus.REQUESTED : RentalStatus.valueOf(name);
        } catch (IllegalArgumentException e) {
            return RentalStatus.REQUESTED;
        }
    }
}
