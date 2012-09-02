package com.example.calcpass;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Updata extends Activity {
	
	EditText et1;
	EditText et2;
	EditText et3;
	EditText et4;
	EditText et5;
	
	int _id;
	String category = "";
	String title = "";
	String user = "";
	String pass = "";
	String memo = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.entry);
		
		this.et1 = (EditText)findViewById(R.id.category);
		this.et2 = (EditText)findViewById(R.id.title);
		this.et3 = (EditText)findViewById(R.id.user);
		this.et4 = (EditText)findViewById(R.id.pass);
		this.et5 = (EditText)findViewById(R.id.memo);
		
		//Passwordクラスからデータを受け取る
		Bundle extras = getIntent().getExtras();
		if(extras != null){
			this._id = extras.getInt("_id");
			this.category = extras.getString("category");
			this.title = extras.getString("title");
			this.user = extras.getString("user");
			this.pass = extras.getString("pass");
			this.memo = extras.getString("memo");
		}
		
		//画面にセットする
		this.et1.setText(category);
		this.et2.setText(title);
		this.et3.setText(user);
		this.et4.setText(pass);
		this.et5.setText(memo);	
	}
	
	//保存ボタン
	public void onSaveClick(View v){
		//DBの準備
		DatabaseHelper dbhelper = new DatabaseHelper(this);
		SQLiteDatabase db = dbhelper.getWritableDatabase();	//読み書き用

		String sql = "update pw_table set "
				+ "category  = '" + this.et1.getText().toString() + "', "
				+ "title     = '" + this.et2.getText().toString() + "', "
				+ "user      = '" + this.et3.getText().toString() + "', "
				+ "pass      = '" + this.et4.getText().toString() + "', "
				+ "memo      = '" + this.et5.getText().toString() + "' "
				+ "where _id =  " + this._id + ";";
			
		db.execSQL(sql);
		dbhelper.close();
		
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
