package edu.pace.mouse.biometric.data;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.pace.mouse.biometric.core.FeatureResult;
import edu.pace.mouse.biometric.util.Util;
/**
 * 
 * @author Venugopala C
 *
 */

public class MouseUserProfile {
	
	/*
	 * <user>
	 * 	<username>Dummy</username>
	 * 	<password>Dummy</password>
	 * 	<lastname>Bakelman</lastname>
	 * 	<firstname>Ned</firstname>
	 * 	<keyboard>Dell</keyboard>
	 * 	<computer>Desktop</computer>
	 * 	<age>26-50</age>
	 * 	<gender>M</gender>
	 * 	<handed>L</handed>
	 * 	<type>WordProcessor</type>
	 * </user>
	 */
	private String username;
	private String password;
	private String lastName;
	private String firstName;
	private String keyboard;
	private String computer;
	private String age;
	private String gender;
	private String handed;
	private String type;
	public MouseUserProfile(Node n){
		parse(n);
	}
	
	public void parse(Node n){
		if (null != n){
			NodeList _list = n.getChildNodes();
			for (int i=0;i<_list.getLength();i++){
				String name = _list.item(i).getNodeName();
				NodeList _l = _list.item(i).getChildNodes();
				if (0 == _l.getLength()) continue;
				String value = _l.item(0).getNodeValue();
				if ("username".equals(name))
					username = value;
				else if ("password".equals(name))
					password = value;
				else if ("lastname".equals(name))
					lastName = value;
				else if ("firstname".equals(name))
					firstName = value;
				else if ("keyboard".equals(name))
					keyboard = value;
				else if ("computer".equals(name))
					computer = value;
				else if ("age".equals(name))
					age = value;
				else if ("gender".equals(name))
					gender = value;
				else if ("handed".equals(name))
					handed = value;
				else if ("type".equals(name))
					type = value;

			}
		}
	}
	public void print(){
		System.out.println("--------------------------------------------");
		System.out.println("username: " + username);
		System.out.println("lastname: " + lastName);
		System.out.println("firstname: " + firstName);
		System.out.println("keyboard: " + keyboard);
		System.out.println("computer: " + computer);
		System.out.println("age: " + age);
		System.out.println("gender: " + gender);
		System.out.println("handed: " + handed);
		System.out.println("type: " + type);
		System.out.println("--------------------------------------------");
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getKeyboard() {
		return keyboard;
	}

	public String getComputer() {
		return computer;
	}

	public String getAge() {
		return age;
	}

	public String getGender() {
		return gender;
	}

	public String getHanded() {
		return handed;
	}

	public String getType() {
		return type;
	}
	public FeatureResult[] extract(){
		FeatureResult[] results = new FeatureResult[7];
		results[0] = new FeatureResult(getClass().getSimpleName(), "Username", username, "");
		results[1] = new FeatureResult(getClass().getSimpleName(), "Hand Click", handed, "");
		results[2] = new FeatureResult(getClass().getSimpleName(), "Type of Mouse",type, "");
		results[3] = new FeatureResult(getClass().getSimpleName(), "Age",age, "");
		results[4] = new FeatureResult(getClass().getSimpleName(), "Gender",gender, "");
		results[5] = new FeatureResult(getClass().getSimpleName(), "Computer",computer, "");
		results[6] = new FeatureResult(getClass().getSimpleName(), "Keyboard",keyboard, "");
		return results;
	}

}
