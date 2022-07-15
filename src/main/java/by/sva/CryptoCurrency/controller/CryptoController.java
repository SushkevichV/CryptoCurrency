package by.sva.CryptoCurrency.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import by.sva.CryptoCurrency.Entity.Currency;
import by.sva.CryptoCurrency.Entity.User;
import by.sva.CryptoCurrency.repository.CurrencyRepository;

@RestController
public class CryptoController {
	
	private CurrencyRepository currencyRepository;
	private List<Currency> c;
	private User user;
	private static final Logger LOGGER = Logger.getLogger(CryptoController.class);;
	
	public CryptoController(CurrencyRepository currencyRepository) {
		this.currencyRepository = currencyRepository;
		c = currencyRepository.findAll();
		new Thread(() -> {
			while(true) {
				try {
					loadCurrency();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	@GetMapping("")
	public ResponseEntity showALLCurrencies(@RequestParam(required = false) String symbol) {
		if(symbol == null) {
			c = currencyRepository.findAll();
			return ResponseEntity.ok(c);
		} else {
			Currency currency = currencyRepository.findFirstBySymbol(symbol);
			return ResponseEntity.ok(currency);
		}
	}
	
	@PostMapping("/notify")
	public ResponseEntity notifyUser(@RequestBody User user) {
		Currency currency = currencyRepository.findFirstBySymbol(user.getSymbol());
		user.setPriceUsd(currency.getPriceUsd());
		user.setDate(LocalDateTime.now());
		this.user = user;
		return ResponseEntity.ok(user);
	}
	
	private void check(double priceUsd) {
		if(Math.abs(priceUsd/user.getPriceUsd()*100-100)>1) {
			LOGGER.warn("Symbol - " + user.getSymbol() + ", User - " + user.getUsername() + ", Rate change for " + Math.abs(priceUsd/user.getPriceUsd()*100-100) + "%");
		}			
	}
	
	public void loadCurrency() throws InterruptedException {		
		for(Currency currency : c) {
			
			JSONArray json = new JSONArray();
			try {
				json = readJsonArrayFromUrl("https://api.coinlore.net/api/ticker/?id="+currency.getId());
			} catch (IOException | JSONException e) {
				e.printStackTrace();
			}
			
			for (Object item: json) {
		    	JSONObject element = (JSONObject) item;
		    	currency.setPriceUsd(Double.valueOf(element.get("price_usd").toString()));
		    }			
			currency.setDate(LocalDateTime.now());
			currencyRepository.save(currency);
			
			if(user != null) {
				if(currency.getSymbol().equals(user.getSymbol())) {
					check(currency.getPriceUsd());
				}
			}
		}
		Thread.sleep(60000);
	}

	private JSONArray readJsonArrayFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
	    try {
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String jsonText = readAll(rd);
	      JSONArray json = new JSONArray(jsonText);
	      return json;
	    } finally {
	      is.close();
	    }
	}
	
	private String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	}
	
}
