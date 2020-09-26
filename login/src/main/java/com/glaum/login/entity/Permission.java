package com.glaum.login.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Permission")
public class Permission {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
	private int id;
	
	@Column(name="bit")
	private int bit;
	
	@Column(name="name")
	private String name;
	
	public int getid()
	{
		return id;
	}
	
	public void setid(int id)
	{
		this.id=id;
	}
	
	public int getbit()
	{
		return bit;
	}
	public void setbit(int bit) {
		this.bit=bit;
	}
	public String getname()
	{
		return name;
	}
	public void setname(String name)
	{
		this.name=name;
	}
	
}
