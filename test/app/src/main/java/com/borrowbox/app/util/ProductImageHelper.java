package com.borrowbox.app.util;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import com.borrowbox.app.R;
import com.borrowbox.app.model.Product;
import java.util.Locale;

public final class ProductImageHelper {

    private ProductImageHelper() {
    }

    @DrawableRes
    public static int getDrawableResForProduct(@Nullable Product product) {
        if (product == null) {
            return R.drawable.prod_laptop_charger;
        }
        return getDrawableResByName(product.getName(), product.getCategory());
    }

    @DrawableRes
    public static int getDrawableResByName(@Nullable String title, @Nullable String category) {
        String t = title != null ? title.toLowerCase(Locale.ROOT) : "";
        String c = category != null ? category.toLowerCase(Locale.ROOT) : "";

        if (t.contains("charger")) {
            return R.drawable.prod_laptop_charger;
        } else if (t.contains("calculator") || t.contains("calc")) {
            return R.drawable.prod_scientific_calc;
        } else if (t.contains("power bank") || t.contains("powerbank")) {
            return R.drawable.prod_power_bank;
        } else if (t.contains("camera") || t.contains("dslr")) {
            return R.drawable.prod_camera;
        } else if (t.contains("hdmi") || t.contains("cable")) {
            return R.drawable.prod_hdmi_cable;
        } else if (t.contains("mouse")) {
            return R.drawable.prod_mouse;
        } else if (t.contains("tablet") || t.contains("wacom") || t.contains("drawing")) {
            return R.drawable.prod_drawing_tablet;
        } else if (t.contains("coat") || t.contains("apron") || t.contains("lab")) {
            return R.drawable.prod_lab_coat;
        } else if (t.contains("umbrella")) {
            return R.drawable.prod_umbrella;
        } else if (t.contains("hub") || t.contains("adapter")) {
            return R.drawable.prod_usb_hub;
        } else if (t.contains("clicker") || t.contains("pointer")) {
            return R.drawable.prod_clicker;
        } else if (t.contains("headset") || t.contains("headphone") || t.contains("earphone")) {
            return R.drawable.prod_headset;
        }

        // Category-based fallback
        if (c.contains("camera")) {
            return R.drawable.prod_camera;
        } else if (c.contains("study")) {
            return R.drawable.prod_scientific_calc;
        } else if (c.contains("accessories")) {
            return R.drawable.prod_hdmi_cable;
        } else if (c.contains("emergency")) {
            return R.drawable.prod_power_bank;
        }

        return R.drawable.prod_laptop_charger;
    }
}
