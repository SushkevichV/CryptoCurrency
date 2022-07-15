package by.sva.CryptoCurrency.Entity;

import java.time.LocalDateTime;

public class User {
	private String username;
	private String symbol;
	private Double priceUsd;
	private LocalDateTime date;
	
	public User() {
	}

	public User(String name, String symbol, Double priceUsd, LocalDateTime date) {
		this.username = name;
		this.symbol = symbol;
		this.priceUsd = priceUsd;
		this.date = date;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String name) {
		this.username = name;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Double getPriceUsd() {
		return priceUsd;
	}

	public void setPriceUsd(Double priceUsd) {
		this.priceUsd = priceUsd;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	} 

}
