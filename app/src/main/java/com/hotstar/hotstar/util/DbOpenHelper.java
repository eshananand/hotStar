package com.hotstar.hotstar.util;

import android.content.Context;
import android.util.Log;


import com.hotstar.hotstar.models.DaoMaster;

import org.greenrobot.greendao.database.Database;

/**
 * Created by Eshan on 11/16/17.
 */

public class DbOpenHelper  extends DaoMaster.OpenHelper
{
    public DbOpenHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        Log.d("DEBUG", "DB_OLD_VERSION : " + oldVersion + ", DB_NEW_VERSION : " + newVersion);
        switch (oldVersion) {
            case 1:
            case 2:
                //db.execSQL("ALTER TABLE " + UserDao.TABLENAME + " ADD COLUMN " + UserDao.Properties.Name.columnName + " TEXT DEFAULT 'DEFAULT_VAL'");
        }
    }
}
