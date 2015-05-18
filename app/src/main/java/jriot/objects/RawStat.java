package jriot.objects;

import java.io.Serializable;

public class RawStat implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private int value;

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

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "RawStat [id=" + id + ", name=" + name + ", value=" + value
				+ "]";
	}

}
