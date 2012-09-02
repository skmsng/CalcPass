package com.example.calcpass;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	//パスワード用
	private boolean flag = true;
	private int count;
	
	public String sNum1 = new String();
    public String strTemp = "";
    public String strResult = "0";
    public Integer operator = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    /**
     * 数字が押されたとき（テキスト）
     */
    public void numKeyOnClick(View v){
    	
    	//CharSequence（親）をString（子）にキャストできる？androidの変な仕様？
    	String strInKey = (String)((Button)v).getText();
    	
    	if(strInKey.equals(".")){
    		if(this.strTemp.length()==0){
	    		this.strTemp = "0.";
	    	}else{
	    		if(this.strTemp.indexOf(".") == -1){
	    			this.strTemp = this.strTemp + ".";
	    		}
	    	}
	    }else{
	    	this.strTemp = this.strTemp + strInKey;
	    }

	    this.showNumber(this.strTemp);
	    
	    //パスワード用
		this.flag = false;
    }
    
    /**
     * ディスプレイに表示するメソッド
     */
    private void showNumber(String strNum) {
    	
    	DecimalFormat form = new DecimalFormat("#,##0");
    	String strDecimal = "";
    	String strInt = "";
    	String fText = "";
    	
    	if(strNum.length() > 0){
    		int decimalPoint = strNum.indexOf(".");
    		if(decimalPoint > -1){
    			strDecimal = strNum.substring(decimalPoint);
    			strInt = strNum.substring(0,decimalPoint);
    		}else{
    			strInt = strNum;
    		}
    		fText = form.format(Double.parseDouble(strInt)) + strDecimal;
    		
    	}else{
    		fText = "0";
    	}
    	
    	((TextView)findViewById(R.id.displayPanel)).setText(fText);
	}
    
    /**
     * +,-,*,/,= が押されたとき
     */
    public void operatorKeyOnClick(View v){
    	
    	if(operator != 0){
    		if(this.strTemp.length() > 0){
    			this.strResult = this.doCalc();
    			this.showNumber(this.strResult);
    		}
    	}else{
    		if(this.strTemp.length() > 0){
    			this.strResult = this.strTemp;
    		}
    	}
    	
    	this.strTemp = "";
    	
    	if(v.getId() == R.id.keypadEq){
    		this.operator = 0;
    		
    		//パスワード用
    		if(flag){
    			this.count++;
    		}

    	}else{
    		this.operator = v.getId();
    		
    		//パスワード用
    		this.flag = false;
    	}
    }
    
    /**
     * 計算
     */
	private String doCalc() {
		BigDecimal bd1 = new BigDecimal(strResult);
		BigDecimal bd2 = new BigDecimal(strTemp);
		BigDecimal result = BigDecimal.ZERO;

		switch(this.operator){
		case R.id.keypadAdd:
			result = bd1.add(bd2);
			break;
		case R.id.keypadSub:
			result = bd1.subtract(bd2);
			break;
		case R.id.keypadMulti:
			result = bd1.multiply(bd2);
			break;
		case R.id.keypadDiv:
			if(!bd2.equals(BigDecimal.ZERO)){
				result = bd1.divide(bd2, 12, 3);
			}else{
				Toast toast = Toast.makeText(this, R.string.toast_div_by_zero, 1000);
				toast.show();
			}
			break;
		}

		if(result.toString().indexOf(".") >= 0){
			return result.toString().replaceAll("¥¥.0+$|0+$", "");
		}else{
			return result.toString();
		}
	}
	
	public void functionKeyOnClick(View v){
		
		//パスワード用
		if(v.getId() == R.id.keypadCopy && this.flag && count == 2){
			this.password();
		}
		this.flag = false;
	}
	
	private void password(){
		Intent intent = new Intent(this, TitleListActivity.class);
		startActivity(intent);
		this.finish();
	}
	
	
}
