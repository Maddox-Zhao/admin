package com.autocreate;



/**
 * @author Mr_Yang   2016-7-26 下午02:13:25
 **/

public class Apple
{
	    public static long counter=0;//会被赋值为0  
	    private  final long id=counter++;//  
	    public long id(){  
	        return id;  
	    }  
	    public static void main(String[] args)
		{
	    	 Apple apple1 = new Apple();  
	         Apple apple2 = new Apple();
	         Apple apple3 = new Apple();  
	   
	         System.out.println(apple1.id());  
	         System.out.println(apple1.id());  

	   
	         System.out.println(apple2.id());  
	         System.out.println(apple2.id());  
	         
	         
	         System.out.println(apple3.id());  
	         System.out.println(apple3.id());  
 
	   
		}
}
 
