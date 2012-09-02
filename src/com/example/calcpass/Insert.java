package com.example.calcpass;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Insert extends Activity {
	
	EditText et1;
	EditText et2;
	EditText et3;
	EditText et4;
	EditText et5;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.entry);
		
		this.et1 = (EditText)findViewById(R.id.category);
		this.et2 = (EditText)findViewById(R.id.title);
		this.et3 = (EditText)findViewById(R.id.user);
		this.et4 = (EditText)findViewById(R.id.pass);
		this.et5 = (EditText)findViewById(R.id.memo);
	}
	
	//保存ボタン
	public void onSaveClick(View v){
		//DBの準備
		DatabaseHelper dbhelper = new DatabaseHelper(this);
		SQLiteDatabase db = dbhelper.getWritableDatabase();	//読み書き用
		
//		//（使い方がよくわからない）
//		//データの準備
//		ContentValues values = new ContentValues();
//		values.put("category", et1.getText().toString());
//		values.put("title", et2.getText().toString());
//		values.put("user", et3.getText().toString());
//		values.put("pass", et4.getText().toString());
//		values.put("memo", et5.getText().toString());
//		
//		//insert文
//		db.insertOrThrow("pw_table", null, values);
		

		String sql = "insert into pw_table(category, title, user, pass, memo) values("
				+ "'" + this.et1.getText().toString() + "', "
				+ "'" + this.et2.getText().toString() + "', "
				+ "'" + this.et3.getText().toString() + "', "
				+ "'" + this.et4.getText().toString() + "', "
				+ "'" + this.et5.getText().toString() + "');";
		
		db.execSQL(sql);
		dbhelper.close();
		
//		Log.d("insert後", this.et1.getText().toString());
		
		Intent intent = new Intent();
		this.setResult(RESULT_OK, intent);
		this.finish();
	}
	
	//キャンセルボタン
	public void onCancelClick(View v){
		Intent intent = new Intent();
		this.setResult(RESULT_CANCELED, intent);
		this.finish();
	}
}
