package orm.hibernate.entities;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**	Temporal means relating to time... 
 * Transient means passing by, temporary [not permanent]
 */
@Entity(name="person")	//Entity&Table name-> person [Entity name also changed]
@Table(name="person")	//Table name-> person
public class Person {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Column(name="name")
	private String name;
	@Basic(optional=true, fetch=FetchType.EAGER)
	private String address;
	@Transient					//Don't persist
	private String wifeName;
	private LocalDate date;
	//@Temporal(TemporalType.TIME)	//Don't persist
	private LocalTime time;
	@Lob
	private String description;
	
	public Person() {}
	
	public Person(int id, String name, String address) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate localDate) {
		this.date = localDate;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public String getWifeName() {
		return wifeName;
	}

	public void setWifeName(String wifeName) {
		this.wifeName = wifeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Person{id: " + id + ", name: " + name + ", address: " + address + "}";
	}
}
