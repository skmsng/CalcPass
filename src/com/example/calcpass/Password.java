package com.example.calcpass;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Password extends Activity {
	
	String sql;
	
	int _id;
	String category = "";
	String title = "";
	String user = "";
	String pass = "";
	String memo = "";
	
	TextView tv1;
	TextView tv2;
	TextView tv3;
	TextView tv4;
	TextView tv5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.password);
		
		Bundle extras = getIntent().getExtras();
		if(extras != null){
			this._id = extras.getInt("_id");
			this.category = extras.getString("category");
			this.title = extras.getString("title");
			this.user = extras.getString("user");
			this.pass = extras.getString("pass");
			this.memo = extras.getString("memo");
		}
		
		this.tv1 = (TextView)findViewById(R.id.category);
		this.tv2 = (TextView)findViewById(R.id.title);
		this.tv3 = (TextView)findViewById(R.id.user);
		this.tv4 = (TextView)findViewById(R.id.pass);
		this.tv5 = (TextView)findViewById(R.id.memo);
		
		this.tv1.setText(category);
		this.tv2.setText(title);
		this.tv3.setText(user);
		this.tv4.setText(pass);
		this.tv5.setText(memo);
		
	}
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.getMenuInflater().inflate(R.menu.password, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		
		switch(item.getItemId()){
		case R.id.menu_new:
			intent = new Intent(this, Insert.class);
			startActivityForResult(intent, 0);
			break;
		case R.id.menu_delete:
			this.alertDialog();
			//this.delete();
			//this.finish();
			break;
		case R.id.menu_edit:
			intent = new Intent(this, Updata.class);
			intent.putExtra("_id", this._id);
			intent.putExtra("category", this.category);
			intent.putExtra("title", this.title);
			intent.putExtra("user", this.user);
			intent.putExtra("pass", this.pass);
			intent.putExtra("memo", this.memo);
			startActivityForResult(intent, this._id);
			break;
		case R.id.menu_back:
			this.finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * インテント先のアクティビティが終了して戻ってきたとき
	 * @param requestCode インテント発行時にstartActivityForResultの第2引数にセットした値
	 * @param resultCode インテント先の終了直前でsetResultの第1引数にセットした値
	 * @param data
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		//保存したとき
		if(resultCode == RESULT_OK){
			
			//DBの準備
			DatabaseHelper dbhelper = new DatabaseHelper(this);
			SQLiteDatabase db = dbhelper.getReadableDatabase();	//読み込み用
			
			//新規保存のとき
			if(requestCode == 0){
				
//				"SELECT * FROM pw_table ORDER BY _id DESC LIMIT 1;";
				Cursor cursor = db.query("pw_table", TitleListActivity.COLUMNS, null, null, null, null, "_id desc", "1");
								
				this.startManagingCursor(cursor);
				cursor.moveToNext();
				
				//カラム（列）番号がわかっているので直接数字を入れている
				this.tv1.setText(cursor.getString(1));
				this.tv2.setText(cursor.getString(2));
				this.tv3.setText(cursor.getString(3));
				this.tv4.setText(cursor.getString(4));
				this.tv5.setText(cursor.getString(5));
				
//				Log.d("select後", this.tv1.getText().toString());
			}
			
			//編集保存のとき（編集するもののidをチェック）
			if(requestCode == this._id){

				String where = "_id = " + String.valueOf(this._id);
				Cursor cursor = db.query("pw_table", TitleListActivity.COLUMNS, where, null, null, null, null);
				
				this.startManagingCursor(cursor);
				cursor.moveToNext();
				
//				this.tv1.setText(cursor.getString(cursor.getColumnIndex("category")));
//				this.tv2.setText(cursor.getString(cursor.getColumnIndex("title")));
//				this.tv3.setText(cursor.getString(cursor.getColumnIndex("user")));
//				this.tv4.setText(cursor.getString(cursor.getColumnIndex("pass")));
//				this.tv5.setText(cursor.getString(cursor.getColumnIndex("memo")));
				
				//カラム（列）番号がわかっているので直接数字を入れている
				this.tv1.setText(cursor.getString(1));
				this.tv2.setText(cursor.getString(2));
				this.tv3.setText(cursor.getString(3));
				this.tv4.setText(cursor.getString(4));
				this.tv5.setText(cursor.getString(5));
				
				dbhelper.close();
			}
	
		//新規・編集でキャンセルしたとき
		}else if(resultCode == RESULT_CANCELED){
		}
	}
	
	//delete文
	private void delete(){
		//DBの準備
		DatabaseHelper dbhelper = new DatabaseHelper(this);
		SQLiteDatabase db = dbhelper.getWritableDatabase();	//読み書き用
		
		String sql = "delete from pw_table where _id = " + this._id;
		db.execSQL(sql);
		dbhelper.close();
	}
	
	//確認ダイアログ
	public void alertDialog(){
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		
//		adb.setTitle("削除");
		adb.setMessage("削除しますか？");
		
		//ポジティブボタン
		adb.setPositiveButton("はい", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    	Password.this.delete();
                    	Password.this.finish();
                    }
		});
//		//ナチュラルボタン
//		adb.setNeutralButton("",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                    }
//		});
		//ネガティブボタン
		adb.setNegativeButton("ちょっと考える",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
		});
		
//		//ダイアログキャンセルの可否（記述しなくてもよさそう）
//		adb.setCancelable(true);

		AlertDialog ad = adb.create();
		ad.show();
	}
}
