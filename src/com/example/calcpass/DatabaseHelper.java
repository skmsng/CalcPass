package com.example.calcpass;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "password.db";
	private static final CursorFactory FACTORY = null;
	private static final Integer DB_VERSION = 1;
	
	//コンストラクタ
	public DatabaseHelper(Context context) {
		super(context, DB_NAME, FACTORY, DB_VERSION);
	}
	
	//DBが作成されていない場合、コンストラクタに続けて呼び出される
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = 
				"CREATE TABLE pw_table ("
//				+ "_id   INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ android.provider.BaseColumns._ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
//				+ "_id   INTEGER,"
				+ "category TEXT NOT NULL,"
				+ "title TEXT NOT NULL,"
				+ "user  TEXT,"
				+ "pass  TEXT,"
				+ "memo  TEXT);";
		db.execSQL(sql);
		
		db.execSQL("insert into pw_table(category, title, user, pass, memo)"
				+  " values('Webサービス', 'Facebook', 'aaa@gmail.com', 'abc', 'メモ1');");
		db.execSQL("insert into pw_table(category, title, user, pass, memo)"
				+  " values('Webサービス', 'GitHub', 'gitID', 'abc', 'メモ2');");
		db.execSQL("insert into pw_table(category, title, user, pass, memo)"
				+  " values('Webサービス', 'ドロップボックス', 'bbb@gmail.com', 'abc', 'メモ3');");
		db.execSQL("insert into pw_table(category, title, user, pass, memo)"
				+  " values('銀行', '福岡銀行', '', '1234', 'メモ4');");
		db.execSQL("insert into pw_table(category, title, user, pass, memo)"
				+  " values('銀行', 'ネット銀行', 'ユーザーID', '1234', 'メモ5');");
		db.execSQL("insert into pw_table(category, title, user, pass, memo)"
				+  " values('公共機関', '運転免許証', 'ユーザーID', '1234　5678', 'メモ6');");
		db.execSQL("insert into pw_table(category, title, user, pass, memo)"
				+  " values('公共機関', '住基カード', 'ユーザーID', '1234', 'メモ7');");
		db.execSQL("insert into pw_table(category, title, user, pass, memo)"
				+  " values('その他', '献血カード', 'ユーザーID', '1234', 'メモ8');");
	}
	
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//データベースのバージョンアップ処理を記述する
	}

}
