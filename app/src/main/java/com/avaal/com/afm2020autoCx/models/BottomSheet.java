package com.avaal.com.afm2020autoCx.models;

import com.avaal.com.afm2020autoCx.R;

public class BottomSheet {
    public enum BottomSheetMenuType {
        EMAIL(android.R.drawable.ic_menu_edit, "Mail"), ACCOUNT(android.R.drawable.ic_dialog_map,
                "Acount"), SETTING(android.R.drawable.ic_input_delete, "Setitng");

        int resId;

        String name;

        BottomSheetMenuType(int resId, String name) {
            this.resId = resId;
            this.name = name;
        }

        public int getResId() {
            return resId;
        }

        public String getName() {
            return name;
        }
    }

    BottomSheetMenuType bottomSheetMenuType;

    public static BottomSheet to() {
        return new BottomSheet();
    }

    public BottomSheetMenuType getBottomSheetMenuType() {
        return bottomSheetMenuType;
    }

    public BottomSheet setBottomSheetMenuType(BottomSheetMenuType bottomSheetMenuType) {
        this.bottomSheetMenuType = bottomSheetMenuType;
        return this;
    }

}
