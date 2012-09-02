package com.example.calcpass;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class TitleListActivity extends ListActivity {
	
//	ArrayList<String> list = new ArrayList<String>();
	
	
	public static final String[] COLUMNS = {"_id", "category", "title", "user", "pass", "memo"};
//	private static final String[] COLUMNS = {"category", "title", "user", "pass", "memo"};
//	private static final int[] TO = {R.id._id, R.id.category, R.id.title, R.id.user, R.id.pass, R.id.memo};
//	private static final int[] TO = {R.id.category, R.id.title, R.id.user, R.id.pass, R.id.memo};
	private DatabaseHelper dbhelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.list);
    }

	@Override
	protected void onResume() {
		super.onResume();
		
		//全件取得
		Cursor cursor = this.selectAll();
		//タイトル一覧
        this.showTitle(cursor);

        dbhelper.close();
	}	

	//全件取得表示（タイトル）
	private Cursor selectAll() {
		//DBの準備
		this.dbhelper = new DatabaseHelper(this);
		SQLiteDatabase db = dbhelper.getReadableDatabase();	//select用
		
		//select文
		//query(テーブル名, カラム（列名）, where, whereパラメータ, GroupBy, Having, OrderBy)
		Cursor cursor = db.query("pw_table", TitleListActivity.COLUMNS, null, null, null, null, null);
		
//		this.startManagingCursor(cursor);
//		while(cursor.moveToNext()){
//			list.add(cursor.getString(2));
//		}
		
		return cursor;
	}
	
//	//カテゴリ一覧
//    private void showGroup(Cursor cursor) {
//    	if(cursor != null){
//    		String[] from = {"category"};
//    		int[] TO = {R.id.category};
//    		SimpleCursorAdapter adapter = new SimpleCursorAdapter(
//    				this, R.layout.category, cursor, from, TO);
//    		setListAdapter(adapter);
//    	}
//	}

    //タイトル一覧
    private void showTitle(Cursor cursor) {
    	if(cursor != null){
    		String[] from = {"title"};
    		int[] TO = {R.id.title};
    		SimpleCursorAdapter adapter = new SimpleCursorAdapter(
    				this, R.layout.title, cursor, from, TO);
    		setListAdapter(adapter);
    	}
	}
    
//    //個別詳細
//    private void showDetail(Cursor cursor) {
//    	if(cursor != null){
//    		String[] from = {"category", "title", "user", "pass", "memo"};
//    		int[] TO = {R.id.category, R.id.title, R.id.user, R.id.pass, R.id.memo};
//    		SimpleCursorAdapter adapter = new SimpleCursorAdapter(
//    				this, R.layout.detail, cursor, from, TO);
//    		setListAdapter(adapter);
//    	}
//    }
    
    
	//リストから１つを選択したとき
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		this.dbhelper = new DatabaseHelper(this);
		SQLiteDatabase db = this.dbhelper.getReadableDatabase();
		Cursor cursor = db.query("pw_table", TitleListActivity.COLUMNS, "_id="+String.valueOf(id), null, null, null, null);
		
		this.startManagingCursor(cursor);
		cursor.moveToNext();

		//カテゴリ一覧の場合
//		Intent intent = new Intent(this, TitleList.class);
//		intent.putExtra("category", cursor.getString(cursor.getColumnIndex("category")));
		
		Intent intent = new Intent(this, Password.class);
		
		intent.putExtra("_id", cursor.getInt(cursor.getColumnIndex("_id")));
		intent.putExtra("category", cursor.getString(cursor.getColumnIndex("category")));
		intent.putExtra("title", cursor.getString(cursor.getColumnIndex("title")));
		intent.putExtra("user", cursor.getString(cursor.getColumnIndex("user")));
		intent.putExtra("pass", cursor.getString(cursor.getColumnIndex("pass")));
		intent.putExtra("memo", cursor.getString(cursor.getColumnIndex("memo")));

		startActivity(intent);
	}

	
	
	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.getMenuInflater().inflate(R.menu.list, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		
		switch(item.getItemId()){
		case R.id.menu_new:
			intent = new Intent(this, Insert.class);
			startActivity(intent);
			break;
		case R.id.menu_search:
			alertDialog();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	//検索入力画面
	EditText et;
	
	//検索ダイアログ
	public void alertDialog(){
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		
//		adb.setTitle("キーワード検索");
		adb.setMessage("タイトル検索");
		
		this.et = new EditText(this);
		adb.setView(this.et);
		
		//ポジティブボタン
		adb.setPositiveButton("検索", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    	Cursor cursor = TitleListActivity.this.search();
                    	TitleListActivity.this.showTitle(cursor);
                    }
		});
		//ネガティブボタン
		adb.setNegativeButton("キャンセル",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
		});

		AlertDialog ad = adb.create();
		ad.show();
	}
	
	//タイトル検索
	private Cursor search(){
		//DBの準備
		DatabaseHelper dbhelper = new DatabaseHelper(this);
		SQLiteDatabase db = dbhelper.getWritableDatabase();	//読み書き用
		
		String where = "title like '%" + et.getText().toString() + "%'";
		Cursor cursor = db.query("pw_table", TitleListActivity.COLUMNS, where, null, null, null, null);
		
		return cursor;
	}
}
