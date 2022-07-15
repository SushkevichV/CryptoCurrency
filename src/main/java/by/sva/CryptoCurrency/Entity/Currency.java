package by.sva.CryptoCurrency.Entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Currency {
	@Id
	private Long id;
	private String symbol;
	private String name;
	private Double priceUsd;
	private LocalDateTime date;
	
	public Currency() {
	}

	public Currency(Long id, String symbol, String name, Double priceUsd, LocalDateTime date) {
		this.id = id;
		this.symbol = symbol;
		this.name = name;
		this.priceUsd = priceUsd;
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
