package tacos.model.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;

/**
 * <code>TacoOrder</code> class defines customers specify the tacos that they
 * want to order, along with payment and delivery information. This is a simple
 * domain class with eleven properties: 5 for delivery information, 3 for
 * payment information, and 1 that is the list of <tt>Taco</tt> objects that
 * make up the order. Also, there are two more fields: <tt>id</tt> field which
 * uniquely identifies the object and <tt>daterOrderPlaced</tt> field which
 * keeps the time of the order creation.<br>
 * {@linkplain #addTaco(Taco)} method adds an </tt>taco</tt> object to the
 * <code>tacos</code> List. <br>
 * 
 * @author Dejan Smiljić - dej4n.s@gmail.com
 *
 */
@Entity
public class TacoOrder implements Serializable {

	private static final long serialVersionUID = 3L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank(message = "Delivery name is required")
	private String deliveryName;
	@NotBlank(message = "Street is required")
	private String deliveryStreet;
	@NotBlank(message = "City is required")
	private String deliveryCity;
	@NotBlank(message = "State is required")
	private String deliveryState;
	@NotBlank(message = "Zip code is required")
	private String deliveryZip;
	@CreditCardNumber(message = "Not a valid credit card number")
	private String ccNumber;
	@Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$", message = "Must be formatted MM/YY")
	private String ccExpiration;
	@Digits(fraction = 0, integer = 3, message = "Invalid CVV")
	private String ccCVV;

	@ManyToMany(targetEntity = Taco.class)
	private List<Taco> tacos = new ArrayList<>();

	private Date dateOrderPlaced;
	// private Date dateOrderPlaced = new Date();

	// constructors; id parameter is not mandatory
	public TacoOrder() {
	}

	public TacoOrder(String deliveryName, String deliveryStreet, String deliveryCity, String deliveryState,
			String deliveryZip, String ccNumber, String ccExpiration, String ccCVV, List<Taco> tacos,
			Date dateOrderPlaced) {
		this.deliveryName = deliveryName;
		this.deliveryStreet = deliveryStreet;
		this.deliveryCity = deliveryCity;
		this.deliveryState = deliveryState;
		this.deliveryZip = deliveryZip;
		this.ccNumber = ccNumber;
		this.ccExpiration = ccExpiration;
		this.ccCVV = ccCVV;
		this.tacos = tacos;
		this.dateOrderPlaced = dateOrderPlaced;
	}

	// getters and setters || accessors and mutators
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeliveryName() {
		return deliveryName;
	}

	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}

	public String getDeliveryStreet() {
		return deliveryStreet;
	}

	public void setDeliveryStreet(String deliveryStreet) {
		this.deliveryStreet = deliveryStreet;
	}

	public String getDeliveryCity() {
		return deliveryCity;
	}

	public void setDeliveryCity(String deliveryCity) {
		this.deliveryCity = deliveryCity;
	}

	public String getDeliveryState() {
		return deliveryState;
	}

	public void setDeliveryState(String deliveryState) {
		this.deliveryState = deliveryState;
	}

	public String getDeliveryZip() {
		return deliveryZip;
	}

	public void setDeliveryZip(String deliveryZip) {
		this.deliveryZip = deliveryZip;
	}

	public String getCcNumber() {
		return ccNumber;
	}

	public void setCcNumber(String ccNumber) {
		this.ccNumber = ccNumber;
	}

	public String getCcExpiration() {
		return ccExpiration;
	}

	public void setCcExpiration(String ccExpiration) {
		this.ccExpiration = ccExpiration;
	}

	public String getCcCVV() {
		return ccCVV;
	}

	public void setCcCVV(String ccCVV) {
		this.ccCVV = ccCVV;
	}

	public List<Taco> getTacos() {
		return tacos;
	}

	public void setTacos(List<Taco> tacos) {
		this.tacos = tacos;
	}

	public Date getDateOrderPlaced() {
		return dateOrderPlaced;
	}

	public void setDateOrderPlaced(Date dateOrderPlaced) {
		this.dateOrderPlaced = dateOrderPlaced;
	}

	// custom methods
	/**
	 * Adds a <tt>taco</tt> object to the <code>tacos</code> List.
	 * 
	 * @param taco
	 */
	public void addTaco(Taco taco) {
		this.tacos.add(taco);
	}

	@PrePersist
	public void orderPlaced() {
		this.dateOrderPlaced = new Date();
	}

	@Override
	public String toString() {
		return String.format(
				"TacoOrder [id=%s, deliveryName=%s, deliveryStreet=%s, deliveryCity=%s, deliveryState=%s, deliveryZip=%s, ccNumber=%s, ccExpiration=%s, ccCVV=%s, tacos=%s, dateOrderPlaced=%s]",
				id, deliveryName, deliveryStreet, deliveryCity, deliveryState, deliveryZip, ccNumber, ccExpiration,
				ccCVV, tacos, dateOrderPlaced);
	}

}
