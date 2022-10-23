package com.bezkoder.spring.jpa.h2.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.spring.jpa.h2.model.TestCoin;

import com.bezkoder.spring.jpa.h2.repository.TestCoinRepository;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class TestCoinController {

	@Autowired
	TestCoinRepository testCoinRepository;

	// 顯示資料表
	@GetMapping("/testCoin")
	public ResponseEntity<List<TestCoin>> getAllTestCoins() {
		try {
			List<TestCoin> testCoin = new ArrayList<TestCoin>();

			testCoinRepository.findAll().forEach(testCoin::add);


			if (testCoin.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(testCoin, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	// 顯示單筆
	@GetMapping("/testCoin/{id}")
	public ResponseEntity<TestCoin> getTestCoinById(@PathVariable("id") long id) {
		Optional<TestCoin> tutorialData = testCoinRepository.findById(id);

		if (tutorialData.isPresent()) {
			return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// 增加
	@PostMapping("/addtestCoin")
	public ResponseEntity<TestCoin> createTestCoin(@RequestBody TestCoin testCoin) {
		try {
			TestCoin _testCoin = testCoinRepository
					.save(new TestCoin(testCoin.getCoinName() , testCoin.getCoinChName(),testCoin.getExchangeRate(),new Date()));
			return new ResponseEntity<>(_testCoin, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//修改
	@PostMapping("/testCoin/{id}")
	public ResponseEntity<TestCoin> updateTestCoin(@PathVariable("id") long id, @RequestBody TestCoin testCoin) {
		Optional<TestCoin> testCoinData = testCoinRepository.findById(id);

		if (testCoinData.isPresent()) {
			TestCoin _testCoin = testCoinData.get();
			_testCoin.setCoinChName(testCoin.getCoinName());
			_testCoin.setCoinName(testCoin.getCoinName());
			_testCoin.setExchangeRate(testCoin.getExchangeRate());
			_testCoin.setUpdateDate(new Date());
			return new ResponseEntity<>(testCoinRepository.save(_testCoin), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	//　刪除
	@DeleteMapping("/detestCoin/{id}")
	public ResponseEntity<HttpStatus> deleteTestCoin(@PathVariable("id") long id) {
		try {
			testCoinRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 刪除所有
	@DeleteMapping("/testCoin")
	public ResponseEntity<HttpStatus> deleteAllTestCoins() {
		try {
			testCoinRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	// 顯示api資料
	@GetMapping("/getOldUrlData")
	public HashMap<String,Object> getOldApi() throws IOException {
		HashMap<String,Object> data = new HashMap<String,Object>();
		String s = getJSON("https://api.coindesk.com/v1/bpi/currentprice.json",8000);
	     try {
	    	 JSONObject jsonObject = JSONObject.fromObject(s);
	    	 data.put("data",jsonObject);
	   } catch (JSONException e) {
			 // TODO Auto-generated catch block
		    e.printStackTrace();
	   }
		return data;
	}
	
	// 顯示轉換資料
	@PostMapping("/showData")
	public HashMap<String,Object> showData() throws IOException {
		HashMap<String,Object> data = new HashMap<String,Object>();
		ArrayList result = new ArrayList<TestCoin>();
		String s = getJSON("https://api.coindesk.com/v1/bpi/currentprice.json",8000);
	     try {
	    	 JSONObject jsonObject = JSONObject.fromObject(s);
	    	 Iterator<String> keys = jsonObject.getJSONObject("bpi").keys();
	    	 
	    	 //解析json 幣制做轉換，回傳資料
	    	 while(keys.hasNext()) {
	    	     String key = keys.next();
	    	     TestCoin t = testCoinRepository.findByCoinName(key);
	    	     if(t != null) {
	    	    	 result.add(t);
	    	     }
	    	 }       
		  data.put("data",result);
	   } catch (JSONException e) {
			 // TODO Auto-generated catch block
		    e.printStackTrace();
	   }
		return data;
	}
	
	
	 public static String getJSON(String url, int timeout) throws IOException {

	     URL u = new URL(url);
	     HttpURLConnection c = (HttpURLConnection) u.openConnection();
	     c.setRequestMethod("GET");
	     c.setUseCaches(false);
	     c.setAllowUserInteraction(false);
	     c.setConnectTimeout(timeout);    
	     c.setReadTimeout(timeout);      
	     c.setRequestProperty("User-Agent","Mozilla/5.0");
	     c.connect();
	     int status = c.getResponseCode();

	     switch (status) {
	         case 200:
	         case 201:
	             BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream(),"utf-8"));
	             StringBuilder sb = new StringBuilder();
	             String line;
	             while ((line = br.readLine()) != null) {
	                 sb.append(line + "\n");
	             }
	             br.close();
	             return sb.toString();
	     }

	     return null;
	 }
	

}
