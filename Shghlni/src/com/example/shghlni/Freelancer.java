package com.example.shghlni;

public class Freelancer {
	int _id;
    String _name;
    int _areaID;
    String _phone1;
    String _phone2;
    String _ImageID;
	public Freelancer(){}
	public Freelancer(int ID, String name, int areaID, String phone1,
			String phone2,String ImageID) {
		// TODO Auto-generated constructor stub
		this._id = ID;
		this._name = name;
		this._areaID = areaID;
		this._phone1 = phone1;
		this._phone2 = phone2;
		this._ImageID = ImageID;
	}
	public int getID(){
        return this._id;
    }
     
    public String getName(){
        return this._name;
    }
    public String getImageID(){
    	return this._ImageID;
    }
    public int getAreaID() {
		return this._areaID;
	}
     
    public String getPhone1(){
        return this._phone1;
    }
    public String getPhone2(){
        return this._phone2;
    }
   
	

}
