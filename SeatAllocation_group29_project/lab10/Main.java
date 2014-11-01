//package com.java2novice.hashtable;
import java.io.*;
//import java.io.BufferedReader;
import java.util.*;
//import java.lang.Object;
//import java.util.HashTable;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.PriorityQueue;

/**
  * Objects of this class stores the necessary information about a candidate(like, gen. rank, cat.rank, pd rank uniqueId etc.)
  *   Created for quick(stores less memory space than a object of Candidate Class) accessing of Candidates;
  */


class Tuple{
	private String uniqueID;
	public String category;
	public boolean isdisabled;
	private float rank1;
	private float rank2;
	private float rank3;
  	public Tuple(){
    	uniqueID="";
    	rank1=rank3=rank2=0;
		category="";
		isdisabled=false;
  	}
	public Tuple(String s, float a,float b,float c){
		uniqueID=s;
		category="";
		isdisabled=false;
		rank1=a;	//for gen
		rank2=b;	//for cat
		rank3=c;	//for pd
	}
	public String getID(){
		return uniqueID;
	}
  	public float getrank1(){
    	return rank1;
  	}
  	public float getrank2(){
    	return rank2;
  	}
  	public float getrank3(){
    	return rank3;
  	}
  	public boolean isdis(){
  		return isdisabled;
  	}
  	public String getcat(){
  		return category;
  	}
}

/**
*A comparator class to sort the tuples of virtual program gen
*/

class TupleComparatorgen implements Comparator<Tuple>{
	@Override
	public int compare(Tuple p,Tuple q){
		float r1=p.getrank1();
		float r2=q.getrank1();
		if(r1>r2){
			return 1;
		}
		else if(r1<r2){
			return -1;
		}
		else{
			return 0;
		}
	}
}
/**
*A comparator class to sort the tuples of virtual program genpd
*/

class TupleComparatorgenpd implements Comparator<Tuple>{
	@Override
	public int compare(Tuple p,Tuple q){
		float r1=p.getrank1();
		float r2=q.getrank1();
		float r3=p.getrank3();
		float r4=q.getrank3();
		if( ( (!p.isdisabled) || p.category!="GE" ) && (q.isdisabled) && q.category.equals("GE")){
			return 1;
		}
		else if( ( (!q.isdisabled) || q.category!="GE" ) && (p.isdisabled) && p.category.equals("GE")){
			return -1;
		}
		else{	//both candidate's disability is same
			if( (p.isdisabled) && (q.isdisabled) && q.category.equals("GE")){		//both are pd gen candidates, sort by gen pd rank 
				if(r3>r4){
					return 1;
				}
				else if(r3<r4){
					return -1;
				}
				else{
					return 0;
				}
			}
			else{		//sort by gen rank
				if(r1>r2){
					return 1;
				}
				else if(r1<r2){
					return -1;
				}
				else{
					return 0;
				}
			}
		}
	}
}
/**
*A comparator class to sort the tuples of virtual program obcpd
*/

class TupleComparatorobcpd implements Comparator<Tuple>{
	@Override
	public int compare(Tuple p,Tuple q){
		float r1=p.getrank1();
		float r2=q.getrank1();
		float r3=p.getrank3();
		float r4=q.getrank3();
		if( ( (!p.isdisabled) || p.category!="OBC" ) && (q.isdisabled) && q.category.equals("OBC") ){
			return 1;
		}
		else if( ( (!q.isdisabled) || q.category!="OBC" ) && (p.isdisabled) && p.category.equals("OBC") ){
			return -1;
		}
		else{	
			if( (p.isdisabled) && (q.isdisabled) && q.category.equals("OBC")){		//both are pd obc candidates, sort by obc pd rank 
				if(r3>r4){
					return 1;
				}
				else if(r3<r4){
					return -1;
				}
				else{
					return 0;
				}
			}
			else{		//sort by gen rank
				if(r1>r2){
					return 1;
				}
				else if(r1<r2){
					return -1;
				}
				else{
					return 0;
				}
			}
		}
	}
}
/**
*A comparator class to sort the tuples of virtual program obc
*/

class TupleComparatorobc implements Comparator<Tuple>{
	@Override
	public int compare(Tuple p,Tuple q){
		float r1=p.getrank1();
		float r2=q.getrank1();
		float r3=p.getrank2();
		float r4=q.getrank2();
		if(  p.category!="OBC" && q.category.equals("OBC") ){
			return 1;
		}
		else if( q.category!="OBC" && p.category.equals("OBC") ){
			return -1;
		}
		else{	
			if( p.category.equals("OBC") && q.category.equals("OBC")){		//both are obc candidates, sort by obc rank 
				if(r3>r4){
					return 1;
				}
				else if(r3<r4){
					return -1;
				}
				else{
					return 0;
				}
			}
			else{		//sort by gen rank
				if(r1>r2){
					return 1;
				}
				else if(r1<r2){
					return -1;
				}
				else{
					return 0;
				}
			}
		}
	}
}

/**
*A comparator class to sort the tuples of virtual program 
*/

class TupleComparatorscst implements Comparator<Tuple>{
	@Override
	public int compare(Tuple p,Tuple q){
		float r1=p.getrank2();
		float r2=q.getrank2();
		if(r1>r2){
			return 1;
		}
		else if(r1<r2){
			return -1;
		}
		else{
			return 0;
		}
	}
}
/**
*A comparator class to sort the tuples of virtual program 
*/

class TupleComparatorscstpd implements Comparator<Tuple>{
	@Override
	public int compare(Tuple p,Tuple q){
		float r1=p.getrank2();
		float r2=q.getrank2();
		float r3=p.getrank3();
		float r4=q.getrank3();
		String c1=p.category;
		String c2=q.category;
		if( (!p.isdisabled) && (q.isdisabled) ){
			return 1;
		}
		else if( (p.isdisabled) && (!q.isdisabled) ){
			return -1;
		}
		else{	//both candidate's disability is same
			if( (p.isdisabled) && (q.isdisabled)){		//both are pd candidates
				if(r3>r4){
					return 1;
				}
				else if(r3<r4){
					return -1;
				}
				else{
					return 0;
				}
			}
			else if( (!p.isdisabled) && (!q.isdisabled)){		//both are non-pd candidates
				if(r1>r2){
					return 1;
				}
				else if(r1<r2){
					return -1;
				}
				else{
					return 0;
				}
			}
			return 0;	//for same rank
		}
	}
}


class Candidate{
	public String uniqueID;
	public String category;
	public boolean isdisabled;
	public boolean nation;			//true if Indian
	public int virtualProgLen;
	public boolean status;	//true if selected
	public ArrayList <String> virtualProg;	//lists the preferences of the students
	public ArrayList <String> newvirtualProg;	//lists the expanded preferences of the students
    public String allocatedProg;
	public int currentVirtualProg;
	public boolean isDeleted;   //stores true if the student is no longer been waitlisted in the current virtual program
	public boolean isWait;		//stores true if the student is been waitlisted in the current virtual program
	public float rank1;		//nonzero if gen present or a ds
	public float rank2;		//nonzero if category student
	public float rank3;		//nonzero if student is a pd
	public String prog;
	public boolean isdone; 

	public Candidate(){
		status=false;
		isWait=false;
		prog="-1";
		virtualProgLen=0;
		currentVirtualProg=0;
		virtualProg = new ArrayList <String>(); 
		newvirtualProg = new ArrayList <String>(); 
		rank1=rank2=rank3=0;
		uniqueID="";
		category="";
		isdone=false;
		allocatedProg="-1";
	}

	public void expandpref(){
		if( category.equals("GE") || category.equals("OBC") ){
			if(isdisabled==false){			//if not disabled
				for (int i=0;i< virtualProg.size();i++ ) {
					String tmp=virtualProg.get(i);
					newvirtualProg.add("0"+tmp);
					newvirtualProg.add("1"+tmp);
					newvirtualProg.add("4"+tmp);
					newvirtualProg.add("5"+tmp);
				}				
			}
			else{
				for (int i=0;i< virtualProg.size();i++ ) {
					String tmp=virtualProg.get(i);
					newvirtualProg.add("0"+tmp);
					newvirtualProg.add("4"+tmp);
					newvirtualProg.add("1"+tmp);
					newvirtualProg.add("5"+tmp);
				}
			}
		}
		if(category.equals("SC")){
			if(isdisabled==false){			//if not disabled
				for (int i=0;i< virtualProg.size();i++ ) {
					String tmp=virtualProg.get(i);
					newvirtualProg.add("0"+tmp);
					newvirtualProg.add("2"+tmp);
					newvirtualProg.add("1"+tmp);
					newvirtualProg.add("4"+tmp);
					newvirtualProg.add("5"+tmp);
					newvirtualProg.add("6"+tmp);
				}				
			}
			else{
				for (int i=0;i< virtualProg.size();i++ ) {
					String tmp=virtualProg.get(i);
					newvirtualProg.add("0"+tmp);
					newvirtualProg.add("2"+tmp);
					newvirtualProg.add("4"+tmp);
					newvirtualProg.add("6"+tmp);
					newvirtualProg.add("1"+tmp);
					newvirtualProg.add("5"+tmp);
				}
			}
		}
		if(category.equals("ST")){
			if(!isdisabled){			//if not disabled
				for (int i=0;i< virtualProg.size();i++ ) {
					String tmp=virtualProg.get(i);
					newvirtualProg.add("0"+tmp);
					newvirtualProg.add("3"+tmp);
					newvirtualProg.add("1"+tmp);
					newvirtualProg.add("4"+tmp);
					newvirtualProg.add("5"+tmp);
					newvirtualProg.add("7"+tmp);
				}				
			}
			else{
				for (int i=0;i< virtualProg.size();i++ ) {
					String tmp=virtualProg.get(i);
					newvirtualProg.add("0"+tmp);
					newvirtualProg.add("3"+tmp);
					newvirtualProg.add("4"+tmp);
					newvirtualProg.add("7"+tmp);
					newvirtualProg.add("1"+tmp);
					newvirtualProg.add("5"+tmp);
				}
			}
		}


		if( category.equals("DS") || category.equals("F") ) {
			for (int i=0;i< virtualProg.size();i++ ) {
				String tmp=virtualProg.get(i);
				newvirtualProg.add("0"+tmp);
				newvirtualProg.add("1"+tmp);
				newvirtualProg.add("4"+tmp);
				newvirtualProg.add("5"+tmp);
			}
		}


		return;
	}
/**
 * Gives the Tuple object corresponding to the Candidate
  */
	public Tuple getTuple(){
		Tuple out=new Tuple(uniqueID,rank1,rank2,rank3);
		out.category=category;
		out.isdisabled=isdisabled;
		return out;
	}

  /**
  * Expands the preferanceList of candidates.
  *Each programme in a institute has category wise seat quota. 
  *For one student to get different seat preferences for the given programme we needed to store his/her category along with programme code.
   *Expandpref() expands the preference by adding a prefix in course code(ex. if the Candidate is of SC category,
    *he is eligible for both general seats and category seats of that programme.
     *so we appended course code string to nos(0 to 7) for respective birth-categories with help of this function).
  */

public void expandpref1(){                                                                            //for phase 1
    if( category.equals("GE")  ){
      if(isdisabled==false){      //if not disabled
        for (int i=0;i< virtualProg.size();i++ ) {
          
          String tmp=virtualProg.get(i);
          newvirtualProg.add("0"+tmp);
          
        }       
      }
      else{
        for (int i=0;i< virtualProg.size();i++ ) {
          String tmp=virtualProg.get(i);

         newvirtualProg.add("0"+tmp);
          
          newvirtualProg.add("4"+tmp);
          
        }
      }
    }
    if( category.equals("DS")  ){
      if(isdisabled==false){      //if not disabled
        for (int i=0;i< virtualProg.size();i++ ) {
          
          String tmp=virtualProg.get(i);
          newvirtualProg.add("0"+tmp);
          
        }       
      }
      else{
        for (int i=0;i< virtualProg.size();i++ ) {
          String tmp=virtualProg.get(i);

         newvirtualProg.add("0"+tmp);
          
          newvirtualProg.add("4"+tmp);
          
        }
      }
    }
    if( category.equals("F")  ){
      if(isdisabled==false){      //if not disabled
        for (int i=0;i< virtualProg.size();i++ ) {
          
          String tmp=virtualProg.get(i);
          newvirtualProg.add("0"+tmp);
          
        }       
      }
      else{
        for (int i=0;i< virtualProg.size();i++ ) {
          String tmp=virtualProg.get(i);

         newvirtualProg.add("0"+tmp);
         newvirtualProg.add("4"+tmp);
          
          
          
        }
      }
    }


    if( category.equals("OBC")  ){
      if(isdisabled==false){      //if not disabled
        for (int i=0;i< virtualProg.size();i++ ) {
          String tmp=virtualProg.get(i);
         if(rank1!=0) newvirtualProg.add("0"+tmp);
          newvirtualProg.add("1"+tmp);

          
        }       
      }
      else{
        for (int i=0;i< virtualProg.size();i++ ) {
          String tmp=virtualProg.get(i);
          if(rank1!=0) newvirtualProg.add("0"+tmp);
          newvirtualProg.add("1"+tmp);
           if(rank1!=0) newvirtualProg.add("4"+tmp);
          newvirtualProg.add("5"+tmp);
          
        }
      }
    }

    if(category.equals("SC")){
      if(isdisabled==false){      //if not disabled
        for (int i=0;i< virtualProg.size();i++ ) {
          String tmp=virtualProg.get(i);
          if(rank1!=0) newvirtualProg.add("0"+tmp);
          newvirtualProg.add("2"+tmp);
          
        }       
      }
      else{
        for (int i=0;i< virtualProg.size();i++ ) {
          String tmp=virtualProg.get(i);
        if(rank1!=0)   newvirtualProg.add("0"+tmp);
          newvirtualProg.add("2"+tmp);
        if(rank1!=0)  newvirtualProg.add("4"+tmp);
          newvirtualProg.add("6"+tmp);
          
        }
      }
    }
    if(category.equals("ST")){
      if(!isdisabled){      //if not disabled
        for (int i=0;i< virtualProg.size();i++ ) {
          String tmp=virtualProg.get(i);
       	 if(rank1!=0)   newvirtualProg.add("0"+tmp);
          newvirtualProg.add("3"+tmp);
          
        }       
      }
      else{
        for (int i=0;i< virtualProg.size();i++ ) {
          String tmp=virtualProg.get(i);
         if(rank1!=0)  newvirtualProg.add("0"+tmp);
          newvirtualProg.add("3"+tmp);
          if(rank1!=0) newvirtualProg.add("4"+tmp);
          newvirtualProg.add("7"+tmp);
          
        }
      }
    }
    return;
  }

	public String getpref(){
		if(newvirtualProg.size()==0)
			return "";
		else{
			if(currentVirtualProg>=newvirtualProg.size())
				return "null";
			return newvirtualProg.get(currentVirtualProg);
		}
	}

	public void incpref(){
		currentVirtualProg++;
		if(currentVirtualProg==newvirtualProg.size())	//bound has been exceeded
			isdone=true;
		return;
	}

	void insertProg(String s){
		virtualProg.add(s);
	}
/*	public int getp(){
		return virtualProg.size();
	}
*/

  /**
  * Expands the preferanceList of candidates used for de-reservation.
  * At the time of de-reservation candidates who failed to get a seat in 1st round participate in de-reservation round
  * And vacant seats in their preference scope are opened for them
  */


public void expandpref2(){
    if( category.equals("GE") ){
      if(isdisabled==false){      //if not disabled
        for (int i=0;i< virtualProg.size();i++ ) {
          String tmp=virtualProg.get(i);
          newvirtualProg.add("0"+tmp);
          newvirtualProg.add("1"+tmp);
          newvirtualProg.add("4"+tmp);
          newvirtualProg.add("5"+tmp);
        }       
      }
      else{
        for (int i=0;i< virtualProg.size();i++ ) {
          String tmp=virtualProg.get(i);
          newvirtualProg.add("0"+tmp);
          newvirtualProg.add("4"+tmp);
          newvirtualProg.add("1"+tmp);
          newvirtualProg.add("5"+tmp);
        }
      }
    }
    if( category.equals("DS") ){
      if(isdisabled==false){      //if not disabled
        for (int i=0;i< virtualProg.size();i++ ) {
          String tmp=virtualProg.get(i);
          newvirtualProg.add("0"+tmp);
          newvirtualProg.add("1"+tmp);
          newvirtualProg.add("4"+tmp);
          newvirtualProg.add("5"+tmp);
        }       
      }
      else{
        for (int i=0;i< virtualProg.size();i++ ) {
          String tmp=virtualProg.get(i);
          newvirtualProg.add("0"+tmp);
          newvirtualProg.add("4"+tmp);
          newvirtualProg.add("1"+tmp);
          newvirtualProg.add("5"+tmp);
        }
      }
    }

    if( category.equals("OBC") ){
      if(isdisabled==false){      //if not disabled
        for (int i=0;i< virtualProg.size();i++ ) {
          String tmp=virtualProg.get(i);
        if(rank1!=0)  newvirtualProg.add("0"+tmp);
          newvirtualProg.add("1"+tmp);
         if(rank1!=0) newvirtualProg.add("4"+tmp);
          newvirtualProg.add("5"+tmp);
        }       
      }
      else{
        for (int i=0;i< virtualProg.size();i++ ) {
          String tmp=virtualProg.get(i);
        if(rank1!=0)  newvirtualProg.add("0"+tmp);
         if(rank1!=0) newvirtualProg.add("4"+tmp);
          newvirtualProg.add("1"+tmp);
          newvirtualProg.add("5"+tmp);
        }
      }
    }
    if( category.equals("F")  ){
      if(isdisabled==false){      //if not disabled
        for (int i=0;i< virtualProg.size();i++ ) {
          String tmp=virtualProg.get(i);
          newvirtualProg.add("0"+tmp);
          newvirtualProg.add("1"+tmp);
          newvirtualProg.add("4"+tmp);
          newvirtualProg.add("5"+tmp);
        }       
      }
      else{
        for (int i=0;i< virtualProg.size();i++ ) {
          String tmp=virtualProg.get(i);
          newvirtualProg.add("0"+tmp);
          newvirtualProg.add("4"+tmp);
          newvirtualProg.add("1"+tmp);
          newvirtualProg.add("5"+tmp);
        }
      }
    }
    if(category.equals("SC")){
      if(isdisabled==false){      //if not disabled
        for (int i=0;i< virtualProg.size();i++ ) {
          String tmp=virtualProg.get(i);
         if(rank1!=0) newvirtualProg.add("0"+tmp);
          newvirtualProg.add("2"+tmp);
         if(rank1!=0) newvirtualProg.add("1"+tmp);
         if(rank1!=0) newvirtualProg.add("4"+tmp);
         if(rank1!=0) newvirtualProg.add("5"+tmp);
          newvirtualProg.add("6"+tmp);
        }       
      }
      else{
        for (int i=0;i< virtualProg.size();i++ ) {
          String tmp=virtualProg.get(i);
         if(rank1!=0) newvirtualProg.add("0"+tmp);
          newvirtualProg.add("2"+tmp);
         if(rank1!=0) newvirtualProg.add("4"+tmp);
          newvirtualProg.add("6"+tmp);
         if(rank1!=0) newvirtualProg.add("1"+tmp);
          if(rank1!=0) newvirtualProg.add("5"+tmp);
        }
      }
    }
    if(category.equals("ST")){
      if(!isdisabled){      //if not disabled
        for (int i=0;i< virtualProg.size();i++ ) {
          String tmp=virtualProg.get(i);
         if(rank1!=0) newvirtualProg.add("0"+tmp);
          newvirtualProg.add("3"+tmp);
         if(rank1!=0) newvirtualProg.add("1"+tmp);
        if(rank1!=0)  newvirtualProg.add("4"+tmp);
        if(rank1!=0)  newvirtualProg.add("5"+tmp);
          newvirtualProg.add("7"+tmp);
        }       
      }
      else{
        for (int i=0;i< virtualProg.size();i++ ) {
          String tmp=virtualProg.get(i);
         if(rank1!=0) newvirtualProg.add("0"+tmp);
          newvirtualProg.add("3"+tmp);
        if(rank1!=0)  newvirtualProg.add("4"+tmp);
          newvirtualProg.add("7"+tmp);
         if(rank1!=0) newvirtualProg.add("1"+tmp);
         if(rank1!=0) newvirtualProg.add("5"+tmp);
        }
      }
    }
    return;
  }



	public int getpp(){		//returns newvirtualProg size
		return newvirtualProg.size();
	}
	public void allot(String s){
		isWait=true;
		prog=s;
	}
	public void foreignallot(String s){
		isdone=true;
		prog=s;
	}
	public void del(){
		isWait=false;
		prog="-1";
	}
	public void printc(){
		System.out.println(uniqueID+","+prog);
	}
	
}

/**
  * class for sorting 2 Candidate objects with respect to their rank
  *
  */


class CandComparatorgen implements Comparator<Candidate>{
  @Override
  public int compare(Candidate p,Candidate q){
    float r1=p.rank1;
    float r2=q.rank1;
    if(r1>r2){
      return 1;
    }
    else if(r1<r2){
      return -1;
    }
    else return 0;
  }
}


class CandComparatorcat implements Comparator<Candidate>{
  @Override
  public int compare(Candidate p,Candidate q){
    float r1=p.rank2;
    float r2=q.rank2;
    if(r1>r2){
      return 1;
    }
    else if(r1<r2){
      return -1;
    }
    else return 0;
  }
}

class CandComparatorcatpd implements Comparator<Candidate>{
  @Override
  public int compare(Candidate p,Candidate q){
    float r1=p.rank3;
    float r2=q.rank3;
    if(r1>r2){
      return 1;
    }
    else if(r1<r2){
      return -1;
    }
    else return 0;
  }

}

/**	
*   [GS version]
*	Implements a Hashmap with key unique id of candidate and object Tuple.
*	Used for fast access of ranks.
*	They are not appended, rather candidates (tuples) are stored in an array and are sorted accordingly as defined by the comparator.
*/

class MeritList1{
	private HashMap <String,Tuple> hm;

	public MeritList1(){
		hm = new HashMap <String,Tuple>();
	}

	public void addObject(Tuple t){
		hm.put(t.getID(),t);
		return;
	}

	public Tuple getObject(String s){
		return hm.get(s);
	}
	public int size(){
		return hm.size();
	}

}
/**
  * Objects of this class are the programmes candidates filling preferances for.
  * Almost all the data members store the progress of programme bar
  *(how much seats are left, no. of candidates and there details that have been already allocated this programme)
  */

class VirtualProgramme{
	public String code;
	private String newcode;		//used for hashmap purposes
	private String elabCode;		//stores fullform of code, eg Electrical Engineering for code B2
	public String category;
	private boolean isdis;		//disability status
	private int quota;
	public ArrayList <Tuple> currentWaitList;
    public float closingrank;
    public int currentWaitListLen;
//	private int currentWaitListLen;

	public VirtualProgramme(){
		currentWaitList=new ArrayList<Tuple>();
//		currentWaitListLen=0;
		quota=0;
		code="";
		newcode="";
		category="";
		isdis=false;
		closingrank=100000.0f;
	}
	public VirtualProgramme(String code,String newcode,String elabCode,String category,boolean isdis,int quota){
		this.code=code;
		this.newcode=newcode;
		this.elabCode=elabCode;
		this.category=category;
		this.isdis=isdis;
		this.quota=quota;
		currentWaitList=new ArrayList<Tuple>();
		closingrank=100000.0f;
	}
	public float getlastrank(){
		if(currentWaitList.size()==0){
			return 1000000;		//virtual infinity
		}
		float rankout = currentWaitList.get(currentWaitList.size()-1).getrank1();
		if(rankout==0) {
			return 1000000;			
		}
		else
			return rankout;
	}

/**
  * Checks whether the programme has vacant seats in it, 
  *adding to this this also checks if one candidate who is on border and disqualified for the programme by just 1 rank,
  * has rank equal to closing rank( equal rank case), in that case it gives that candidate seat and increase the programme quota by 1;
  */

  public boolean isVacant(Candidate c){
        if(quota>currentWaitListLen)   //for an indian student
        {
          if(c.category.equals("F")){
            quota++;
          }
          return true;
        }
        if(quota == currentWaitListLen){
          if(category.equals("GE") && (!isdis)){
            if(c.rank1==closingrank)return true;
          }
          if(category.equals("OBC") && (!isdis)||category.equals("SC") && (!isdis)||category.equals("ST") && (!isdis)){
            if(c.rank2==closingrank)return true;
          }
          if(category.equals("GE") && (isdis)||category.equals("OBC") && (isdis)||category.equals("SC") && (isdis)||category.equals("ST") && (isdis)){
            if(c.rank3==closingrank)return true;
          }
          return false;
        }

        return false;
  }


	public boolean isVacant1(){
		if(quota>currentWaitList.size())
			return true;
		return false;
	}
	public boolean isdisabled(){
		return isdis;
	}
	public void incquota(){
		quota++;
	}

  public boolean iseligible(Candidate c){
    if(category.equals("GE")){
      if(c.rank1==0)
        return false;
      return true;
    }
    return true;
  }

  /**
  * Checks whether the candidate eligible to take different seats 
  *(ex. Candidate, having general rank 0 but has got rank in his own Category, can not qualify for general seat).
  */
	boolean isCandidateok(Tuple t){		//returns true if a given Candidate is eligible to be added.
		if(category.equals("GE") && (!isdis)){
			if(t.getrank1()==0)
				return false;
			return true;
		}
		if( t.getcat().equals("DS") || t.getcat().equals("F") ){
			if(t.getrank1()==0)
				return false;
			return true;
		}
		else if(category.equals("GE") && (isdis)){		//ge pd virtual program
			if(t.category.equals("GE") && (t.isdisabled))
				return true;
			else{
				if(t.getrank1()!=0)
					return true;
				return false;
			}
		}
		else if(( category.equals("SC") || category.equals("ST") )&& (!isdis)){		//sc or st non pd
			if(t.getrank2()==0)
				return false;
			return true;
		}

		else if( category.equals("OBC") && (!isdis)){		//obc non pd virtual program
			if(t.category.equals("OBC") && (t.getrank2()!=0))
				return true;
			else{
				if(t.getrank1()!=0)
					return true;
				return false;
			}
		}
		else if( category.equals("OBC") && (isdis)){		//obc pd virtual program
			if(t.category.equals("OBC") && (t.isdisabled) )
				return true;
			else{
				if(t.getrank1()!=0)
					return true;
				return false;
			}
		}

		return true;
	}

  public void lenProgress(){
    currentWaitListLen++;
    return;
  }

  public int getquota(){
    return quota;
  }


/**
  * Returns a bool, true if candidate is already allocated(to prevent repeating) 
  */

  public boolean isAllocated(Candidate c){
      Tuple t = c.getTuple();
      if(currentWaitList.contains(t))return true;
      return false;

  }
/**
*A function of Candidate class which assigns a comparator depending upon the cat of VP
*/

	public void sortc(){
		if(category.equals(""))
			return;
		if(category.equals("GE")){
			if(!isdis)
				Collections.sort(currentWaitList, new TupleComparatorgen());
			else
				Collections.sort(currentWaitList,new TupleComparatorgenpd());
		}
		if(category.equals("OBC")){
			if(!isdis)
				Collections.sort(currentWaitList, new TupleComparatorobc());
			else
				Collections.sort(currentWaitList,new TupleComparatorobcpd());
		}
		if(category.equals("SC")){
			if(!isdis)
				Collections.sort(currentWaitList, new TupleComparatorscst());
			else
				Collections.sort(currentWaitList,new TupleComparatorscstpd());
		}
		if(category.equals("ST")){
			if(!isdis)
				Collections.sort(currentWaitList, new TupleComparatorscst());
			else
				Collections.sort(currentWaitList,new TupleComparatorscstpd());
		}
	}

	public void addCand(Tuple t){
		if(currentWaitList==null)
			return;
		currentWaitList.add(t);
	}
/**
*Keeps the first q tuples and deletes the rest
*/
	public void filter(){
		if(currentWaitList.size()==0){
			return;
		}
		else if(currentWaitList.size()<=quota)
			return;
		int len=currentWaitList.size();
		for (int i=len-1;i>=0;i--) {
			if(i>=quota){
				currentWaitList.remove(i);
			}
		}
		return;
	}

	public boolean isthere(String s)
	{
		for (int i=0;i<currentWaitList.size() ;i++ ) {
			if(s.equals(currentWaitList.get(i).getID()))
				return true;
		}
		return false;
	}
//	public String getcode(){
//		return code;
//	}

	public String getnewcode(){
		return code;
	}

}


class DsProgramme{
  private int quota;
  public char name;
  public ArrayList <Tuple> dscurrentWaitList;
  public int dscurrentWaitListLen;
  public float dsclosingrank;

  public DsProgramme(){
    quota = 2;
    dscurrentWaitList = new ArrayList <Tuple>();
    dscurrentWaitListLen = 0;
    dsclosingrank = 100000.0f;
  }

  public DsProgramme(char name){
    quota = 2;
    this.name = name;
    dscurrentWaitList = new ArrayList <Tuple>();
    dscurrentWaitListLen = 0;
    dsclosingrank = 100000.0f;
  }

  public boolean isAllocated(Candidate c){
      Tuple t = c.getTuple();
      if(dscurrentWaitList.contains(t)) {
         return true;
       }
      else 
        return false;
  }
  public boolean isVacant1(){
	       if(quota>dscurrentWaitListLen)   
          return true;
         return false;
      }
  void decquota(){
    quota--;
    return;
  }

  public int getquota(){
    return quota;
  }

  public boolean isVacant(Candidate c){
       if(quota>dscurrentWaitListLen)  
       { 
          return true;
        }
      if(quota==dscurrentWaitListLen){
        if(c.rank1==dsclosingrank)return true;
        return false;
      }
         return false;
      }

  public void dslenProgress(){
    dscurrentWaitListLen++;
    return;
  }

  	public void sortc(){
		Collections.sort(dscurrentWaitList,new TupleComparatorgen());
		return;
  	}

  	public boolean isCandidateok(Tuple t){
  		if(t.getrank1()!=0)
  			return true;
  		return false;
  	}
	public void addCand(Tuple t){
		if(dscurrentWaitList==null)
			return;
		dscurrentWaitList.add(t);
		return;
	}

	public void filter(){
		if(dscurrentWaitList.size()==0){
			return;
		}
		else if(dscurrentWaitList.size()<=quota)
			return;
		int len=dscurrentWaitList.size();
		for (int i=len-1;i>=0;i--) {
			if(i>=quota){
				dscurrentWaitList.remove(i);
			}
		}
		return;
	}
	public boolean isthere(String s)
	{
		for (int i=0;i<dscurrentWaitList.size() ;i++ ) {
			if(s.equals(dscurrentWaitList.get(i).getID()))
				return true;
		}
		return false;
	}

//	public char getnewcode(){
//		return name;
//	}

}


class VirtualProgrammeMap{
	private HashMap <String,VirtualProgramme> hm;
	public VirtualProgrammeMap(){
		hm=new HashMap <String,VirtualProgramme> ();
	}

	public VirtualProgramme getObject(String s){
		return hm.get(s);
	}
	public void putObject(String s, VirtualProgramme v){
		hm.put(s,v);
	}
	public int size(){
		return hm.size();
	}

	void filtermap(){
		Set<String> keys=hm.keySet();
		for ( String key : keys ) {
			hm.get(key).sortc();
			hm.get(key).filter();
		}
	}
}


class DsProgrammeMap{
  private HashMap <Character,DsProgramme> hm;
  public DsProgrammeMap(){
    hm=new HashMap <Character,DsProgramme> ();
  }

  public DsProgramme getObject(Character c){
    return hm.get(c);
  }
  public void putObject(char c, DsProgramme v){
    hm.put(c,v);
  }
	void filtermap(){
		Set<Character> keys=hm.keySet();
		for ( Character key : keys ) {
			hm.get(key).sortc();
			hm.get(key).filter();
		}
	}
}

class MeritList{
  ArrayList <Candidate> merit;                        //final merit list after appending all categories
  ArrayList <Candidate> mlds; 
  ArrayList <Candidate> mlgen;
  ArrayList <Candidate> mlobc;
  ArrayList <Candidate> mlsc;
  ArrayList <Candidate> mlst;
  ArrayList <Candidate> mlgenpd;
  ArrayList <Candidate> mlobcpd;
  ArrayList <Candidate> mlscpd;
  ArrayList <Candidate> mlstpd;
  
  public MeritList(){
    merit = new ArrayList <Candidate>();
    mlds = new ArrayList <Candidate>();
    mlgen = new ArrayList <Candidate>();
    mlobc = new ArrayList <Candidate>();
    mlsc = new ArrayList <Candidate>();
    mlst = new ArrayList <Candidate>();
    mlgenpd = new ArrayList <Candidate>();
    mlobcpd = new ArrayList <Candidate>();
    mlscpd = new ArrayList <Candidate>();
    mlstpd = new ArrayList <Candidate>();
  }
  public void addObject(Candidate cand){
  if(cand.category.equals("DS")) mlds.add(cand);
  if(cand.rank1 != 0) mlgen.add(cand);
  if(cand.rank2 != 0 && cand.category.equals("OBC")) mlobc.add(cand);
  if(cand.rank2 != 0 && cand.category.equals("SC"))mlsc.add(cand);
  if(cand.rank2 != 0 && cand.category.equals("ST")) mlst.add(cand);
   if(cand.rank3 != 0 && cand.category.equals("GE")) mlgenpd.add(cand);
  if(cand.rank3 != 0 && cand.category.equals("OBC")) mlobcpd.add(cand);
  if(cand.rank3 != 0 && cand.category.equals("SC")) mlscpd.add(cand);
  if(cand.rank3 != 0 && cand.category.equals("ST")) mlstpd.add(cand);
 

    return;
  }
  public void complete(){
    merit.addAll(mlgen);
    merit.addAll(mlobc);
    merit.addAll(mlsc);
    merit.addAll(mlst);
    merit.addAll(mlgenpd);
    merit.addAll(mlobcpd);
    merit.addAll(mlscpd);
    merit.addAll(mlstpd);

    return;

  }


  
}

class GetRankMap{
  private HashMap <String,Tuple> rn;
  public GetRankMap(){
    rn = new HashMap <String, Tuple> ();
  }
  public Tuple getTuple(String s){
    return rn.get(s);
  }
  public void putTuple(String s, Tuple t){
    rn.put(s,t);
  }
}










class MeritOrderAdmission{
	MeritList ml;
  VirtualProgrammeMap vmap;
  DsProgrammeMap dmap;
  GetRankMap rn;
  ArrayList <Candidate> arrC = new ArrayList <Candidate>();

  

	public MeritOrderAdmission(){
		ArrayList <Tuple> al=new ArrayList <Tuple>();
    vmap=new VirtualProgrammeMap();
    dmap=new DsProgrammeMap();
    rn = new GetRankMap();
    ArrayList <Character> college = new ArrayList <Character>();
		//ml=new MeritList();
		File file=new File("choices.csv");
		File file1=new File("programs.csv");
		File file2=new File("ranklist.csv");
		try{


      //starts ranklist input stored as tuple
			BufferedReader in2 = new BufferedReader(new FileReader(file2));
			String s=in2.readLine();
			while(in2.ready()){
				s=in2.readLine();
				int k=s.indexOf(',');
  				String id=s.substring(0,k);
  				s=s.substring(k+1,s.length());
  				k=s.indexOf(',');
  				s=s.substring(k+1,s.length());//skips gender			ask abt this
  				k=s.indexOf(',');
  				s=s.substring(k+1,s.length());//skips cml			ask abt this
   				float[] val=new float[9];
   				for (int i=0;i<8 ;i++ ) {
		  			k=s.indexOf(',');
		  			val[i]=Float.parseFloat(s.substring(0,k));
	  				s=s.substring(k+1,s.length());
  				}
  				val[8]=Float.parseFloat(s);
          float r1=val[0];
          float r2=0;
          float r3=0;
          if(val[5]!=0) //a gen pd can
          {
            r2=0;
            r3=val[5];
          }
          else if(val[1]!=0){   // an obc candidate
            r2=val[1];
            if(val[6]!=0){    // an obc pd candidate
              r3=val[6];
            }
          }
          else if(val[2]!=0){   //a sc candidate
            r2=val[2];
            if(val[7]!=0){    // a sc pd candidate
              r3=val[7];
            }
          }
          else if(val[3]!=0){   //a st candidate
            r2=val[3];
            if(val[7]!=0){    // a st pd candidate
              r3=val[8];
            }
          }
  				Tuple tmp = new Tuple(id,r1,r2,r3);
          rn.putTuple(tmp.getID(), tmp);
  				al.add(tmp);
			}
      in2.close();
BufferedReader in1 = new BufferedReader(new FileReader(file1)); //programs
      s=in1.readLine();   //skips the first line read.
      while (in1.ready()){
          s = in1.readLine();
//          System.out.println(s);

          int k2=s.indexOf(',');
          s=s.substring(k2+1,s.length());
          k2=s.indexOf(',');
          String code1=s.substring(0,k2);
          s=s.substring(k2+1,s.length());
          k2=s.indexOf(',');
          String elabCode1=s.substring(0,k2);
          s=s.substring(k2+1,s.length());
          String[] q = new String[8];   //q is an array which stores the quota for all categories as a string.
          for (int i=0;i<7;i++) {
            k2=s.indexOf(',');
            q[i]=s.substring(0,k2);
            s=s.substring(k2+1,s.length());
          }
          q[7]=s;
          for (int i=0;i<8 ;i++ ) {
            String category1="";    //initialisation
            boolean isdis1=false;   //initialisation
            int quota1=0;       //initialisation
            String newcode1="";     //initialisation
          switch (i){
            case 0: {
              category1="GEN";
              newcode1="0"+code1;
              isdis1=false;
              quota1=Integer.parseInt(q[i]);
            }
            break;
            case 1: {
              category1="OBC";
              newcode1="1"+code1;
              isdis1=false;
              quota1=Integer.parseInt(q[i]);
            }   
            break;        
            case 2: {
              category1="SC";
              newcode1="2"+code1;
              isdis1=false;
              quota1=Integer.parseInt(q[i]);
            }   
            break;        
            case 3: {
              category1="ST";
              isdis1=false;
              newcode1="3"+code1;
              quota1=Integer.parseInt(q[i]);
            }     
            break;      
            case 4: {
              category1="GEN";
              isdis1=true;
              newcode1="4"+code1;
              quota1=Integer.parseInt(q[i]);
            }     
            break;      
            case 5: {
              category1="OBC";
              isdis1=true;
              newcode1="5"+code1;
              quota1=Integer.parseInt(q[i]);
            }     
            break;      
            case 6: {
              category1="SC";
              isdis1=true;
              newcode1="6"+code1;
              quota1=Integer.parseInt(q[i]);
            }     
            break;      
            case 7: {
              category1="ST";
              isdis1=true;
              newcode1="7"+code1;
              quota1=Integer.parseInt(q[i]);
            }     
            break;      
          }
          VirtualProgramme tmp = new VirtualProgramme(code1,newcode1,elabCode1,category1,isdis1,quota1);            
            vmap.putObject(newcode1,tmp);
            
          }
          char c = code1.charAt(0);
          if(!college.contains(c))
          college.add(c);
        DsProgramme dp = new DsProgramme(c);
        dmap.putObject(c, dp);


      }
      in1.close();
      



      //starts choices input stored as Cand

      BufferedReader in = new BufferedReader(new FileReader(file));
      String s1=in.readLine();   //skips the first line read.
      while (in.ready()){
          s1 = in.readLine();
          //System.out.println(s);
          Candidate tmp=new Candidate();
          int k1=s1.indexOf(',');
          tmp.uniqueID=s1.substring(0,k1);
          s1=s1.substring(k1+1,s1.length());
          k1=s1.indexOf(',');
          tmp.category=s1.substring(0,k1);
          s1=s1.substring(k1+1,s1.length());
          if(s1.substring(0,1).equals("Y")){
            tmp.isdisabled=true;
          }
          else{
            tmp.isdisabled=false;
          }
          s1=s1.substring(2,s1.length());
          //System.out.println(s);
          //to be decided how to add programs to a candidate..
          while(s1.length()>2){
            k1=s1.indexOf('_');
            String p=s1.substring(0,k1);
            tmp.virtualProg.add(p);     //adds the codes of choices as strings in the ArrayList
            s1=s1.substring(k1+1,s1.length());
          }
          tmp.virtualProg.add(s1);   //adds the last choice
          arrC.add(tmp);

//          System.out.println(s);

      }
      in.close();

      //Assigning ranks to candidates


      for(int i=0; i < arrC.size(); i++){
        Candidate tmp = arrC.get(i);
        Tuple t = rn.getTuple(tmp.uniqueID);
        tmp.rank1 = t.getrank1();
        tmp.rank2 = t.getrank2();
        tmp.rank3 = t.getrank3();
       // System.out.println(tmp.rank1);
      }
      for(int i=0; i < arrC.size(); i++){
        Candidate tmp = arrC.get(i);
        tmp.expandpref1();
      }
      //creating meritlists

      ml = new MeritList();
      for(int i=0; i <arrC.size(); i++){
        Candidate tmp = arrC.get(i);
        ml.addObject(tmp);
      }
     //sorting meritlist
      Collections.sort(ml.mlds, new CandComparatorgen());
     Collections.sort(ml.mlgen, new CandComparatorgen());
     Collections.sort(ml.mlobc, new CandComparatorcat());
     Collections.sort(ml.mlsc, new CandComparatorcat());
     Collections.sort(ml.mlst, new CandComparatorcat());
     Collections.sort(ml.mlgenpd, new CandComparatorcatpd());
     Collections.sort(ml.mlobcpd, new CandComparatorcatpd());
     Collections.sort(ml.mlscpd, new CandComparatorcatpd());
     Collections.sort(ml.mlstpd, new CandComparatorcatpd());
     ml.complete();

     //DS seat allocation
     /**
  * Implementation of allocating seats to DS students:
        *When storing choices of programmes, first character of string programme code represents the college. 
        *So we are storing first character in a Arraylist of characters(excluding repeatation).
        *now every college wrt each character has DS quota of 2 in total.
    *We created DsProgrammeMap to map college programme object to character string.
    *and then allocate the seats to all DS students untill quota fills up.
     *unallocated students will now go through the same algo as general category students.
      *And allocated DS students will not participate in that allocation as they have alread been allocated
  */


     
     for(int i = 0;i <(ml.mlds).size(); i++){

        Candidate tmp = (ml.mlds).get(i);
        Tuple t = tmp.getTuple(); 
        for(int j=0; j<((tmp.virtualProg).size());j++){

          String str = (tmp.virtualProg).get(j);
          char ch = str.charAt(0);
          DsProgramme dp = dmap.getObject(ch);
          if(dp.isVacant(tmp)){
            
            tmp.allocatedProg = tmp.virtualProg.get(j);
            dp.dslenProgress();
            dp.dscurrentWaitList.add(t);
            if(dp.getquota() == dp.dscurrentWaitListLen) dp.dsclosingrank= tmp.rank1;
            tmp.status = true;
            ml.merit.remove(tmp);
            ml.mlgen.remove(tmp);
            break;

          }


        }
      }
      
      for(int i = 0;i <(ml.mlds).size(); i++){                             //DS candidates who are not allocated seats
        Candidate tmp = (ml.mlds).get(i);
        if(tmp.category.equals("DS")){tmp.category = "GE";};
      }

//System.out.println((ml.merit).size());
/*
for(int i=0; i <arrC.size(); i++){
        Candidate tmp = arrC.get(i);
        System.out.println(tmp.uniqueID+" "+tmp.category);
      }
 */      

      
      for(int i = 0;i <(ml.merit).size(); i++){

        Candidate tmp = (ml.merit).get(i);
        Tuple t = tmp.getTuple(); 
        



       for(int j=0; j<((tmp.newvirtualProg).size());j++){
     //  VirtualProgramme vp = new VirtualProgramme();
       VirtualProgramme vp = vmap.getObject((tmp.newvirtualProg).get(j));
       if(vp.isAllocated(tmp)) break;                                       //checks whether the candidate has already been allocated
       else{

        if(vp.isVacant(tmp) && !(tmp.status) && vp.iseligible(tmp)){
          tmp.allocatedProg = vp.code; 
          vp.lenProgress();
            vp.currentWaitList.add(t);
          if(vp.currentWaitListLen == vp.getquota()){
            if(vp.category.equals("GEN") && (!vp.isdisabled()))
            {
            vp.closingrank = tmp.rank1;
            }
            if(vp.category.equals("OBC") && (!vp.isdisabled())|| vp.category.equals("SC") && (!vp.isdisabled())|| vp.category.equals("ST") && (!vp.isdisabled()) )
            {
            vp.closingrank = tmp.rank2;
            }
            if((vp.category.equals("OBC") && (vp.isdisabled()))  || (vp.category.equals("SC") && (vp.isdisabled()))|| (vp.category.equals("ST") && (vp.isdisabled())))
            {vp.closingrank = tmp.rank3;
            }
           }
          
          
          tmp.status = true;
          break;
        }
      }
      }


}
      /* 
      for(int i=0; i <arrC.size(); i++){                               //testing before dereservaton
        Candidate tmp = arrC.get(i);
        System.out.println(tmp.uniqueID+" "+tmp.allocatedProg+ "   "+ tmp.category+ " "+tmp.rank1 + " "+tmp.rank2 + " "+tmp.rank3);
      }
*/
        //De-reservation

      for(int i=0; i < arrC.size(); i++){
        Candidate tmp = arrC.get(i);
        if(tmp.allocatedProg.equals("-1"))tmp.expandpref2();
      }

        //Allocation after changing preferances

      for(int i = 0;i <(ml.merit).size(); i++){

        Candidate tmp = (ml.merit).get(i);
        Tuple t = tmp.getTuple();
        if(tmp.allocatedProg.equals("-1")){
        
        for(int j=0; j<((tmp.newvirtualProg).size());j++){
     //  VirtualProgramme vp = new VirtualProgramme();
       VirtualProgramme vp = vmap.getObject((tmp.newvirtualProg).get(j));
       if(vp.isAllocated(tmp)) break;                                       //checks whether the candidate has already been allocated
       else{

        if(vp.isVacant(tmp) && !(tmp.status) && vp.iseligible(tmp)){
          tmp.allocatedProg = vp.code; 
          vp.lenProgress();
          vp.currentWaitList.add(t);
       if(vp.currentWaitListLen == vp.getquota()){
            if(vp.currentWaitListLen == vp.getquota()){
            if(vp.category.equals("GEN") && (!vp.isdisabled()))
            {
            vp.closingrank = tmp.rank1;
            }
            if(vp.category.equals("OBC") && (!vp.isdisabled())|| vp.category.equals("SC") && (!vp.isdisabled())|| vp.category.equals("ST") && (!vp.isdisabled()) )
            {
            vp.closingrank = tmp.rank2;
            }
            if((vp.category.equals("OBC") && (vp.isdisabled()))  || (vp.category.equals("SC") && (vp.isdisabled()))|| (vp.category.equals("ST") && (vp.isdisabled())))
            {vp.closingrank = tmp.rank3;
            }
           }
           }
            
          tmp.status = true;
          break;
        }
      }
      }
    }


}
System.out.println("CandidateUniqueId,ProgrammeID");
for(int i=0; i <arrC.size(); i++){                               //testing after dereservation
        Candidate tmp = arrC.get(i);

        System.out.println(tmp.uniqueID+","+tmp.allocatedProg);
      }

		}
		catch(FileNotFoundException exception)		//Exception Handling
    	{
        	System.out.println("The file " + file.getPath() + " was not found.");
 	    }

		catch(IOException exception)
    	{
        	System.out.println(exception);
 	    }
	}
	
}











class GaleShapleyAdmission {

	ArrayList <Candidate> arrC=new ArrayList <Candidate>();		//kinda vector of candidates
	//stores the arraylist of candidates
	VirtualProgrammeMap vmap=new VirtualProgrammeMap();
	//stores 
	MeritList1 ml = new MeritList1();
	String s;
	ArrayList <Character> college = new ArrayList <Character>();
	DsProgrammeMap dmap;

	public GaleShapleyAdmission(){
		vmap=new VirtualProgrammeMap();
		dmap=new DsProgrammeMap();
		File file=new File("choices.csv");
		File file1=new File("programs.csv");
		File file2=new File("ranklist.csv");
		try
		{
			BufferedReader in = new BufferedReader(new FileReader(file));	//choices
			String s=in.readLine();		//skips the first line read.
			while (in.ready()){
  				s = in.readLine();
  				Candidate tmp=new Candidate();
  				int k1=s.indexOf(',');
  				tmp.uniqueID=s.substring(0,k1);
  				s=s.substring(k1+1,s.length());
  				k1=s.indexOf(',');
  				tmp.category=(s.substring(0,k1));
   				
   				s=s.substring(k1+1,s.length());
  				if(s.substring(0,1).equals("Y")){
  					tmp.isdisabled=true;
  				}
  				else{
  					tmp.isdisabled=false;
  				}
  				s=s.substring(2,s.length());
  				while(s.length()>2){
	  				k1=s.indexOf('_');
	  				String p=s.substring(0,k1);
	  				tmp.virtualProg.add(p);			//adds the codes of choices as strings in the ArrayList
	  				s=s.substring(k1+1,s.length());
  				}
  				tmp.virtualProg.add(s);			//adds the last choice
  				arrC.add(tmp);
			}
			in.close();

			BufferedReader in1 = new BufferedReader(new FileReader(file1));	//programs
			String s2=in1.readLine();		//skips the first line read.
			while (in1.ready()){
  				s2 = in1.readLine();

	  			int k2=s2.indexOf(',');
	  			s2=s2.substring(k2+1,s2.length());
	  			k2=s2.indexOf(',');
	  			String code1=s2.substring(0,k2);
  				s2=s2.substring(k2+1,s2.length());
  				k2=s2.indexOf(',');
  				String elabCode1=s2.substring(0,k2);
   				s2=s2.substring(k2+1,s2.length());
   				String[] q = new String[8];		//q is an array which stores the quota for all categories as a string.
   				for (int i=0;i<7;i++) {
		  			k2=s2.indexOf(',');
		  			q[i]=s2.substring(0,k2);
	  				s2=s2.substring(k2+1,s2.length());
   				}
   				q[7]=s2;
   				for (int i=0;i<8 ;i++ ) {
   					String category1="";		//initialisation
   					boolean isdis1=false;		//initialisation
   					int quota1=0;				//initialisation
   					String newcode1="";			//initialisation
	 					if(i==0) {
	 						category1="GE";
	 						newcode1="0"+code1;
	 						isdis1=false;
	 						quota1=Integer.parseInt(q[i]);
	 					}
	 					if(i==1) {
	 						category1="OBC";
	 						newcode1="1"+code1;
	 						isdis1=false;
	 						quota1=Integer.parseInt(q[i]);
	 					}	 					
	 					if(i==2) {
	 						category1="SC";
	 						newcode1="2"+code1;
	 						isdis1=false;
	 						quota1=Integer.parseInt(q[i]);
	 					}	 					
	 					if(i==3) {
	 						category1="ST";
	 						isdis1=false;
	 						newcode1="3"+code1;
	 						quota1=Integer.parseInt(q[i]);
	 					}	 					
	 					if(i==4) {
	 						category1="GEN";
	 						isdis1=true;
	 						newcode1="4"+code1;
	 						quota1=Integer.parseInt(q[i]);
	 					}	 					
	 					if(i==5) {
	 						category1="OBC";
	 						isdis1=true;
	 						newcode1="5"+code1;
	 						quota1=Integer.parseInt(q[i]);
	 					}	 					
	 					if(i==6) {
	 						category1="SC";
	 						isdis1=true;
	 						newcode1="6"+code1;
	 						quota1=Integer.parseInt(q[i]);
	 					}	 					
	 					if(i==7) {
	 						category1="ST";
	 						isdis1=true;
	 						newcode1="7"+code1;
	 						quota1=Integer.parseInt(q[i]);
	 					}	 					
//	 				}
	 				VirtualProgramme tmp=new VirtualProgramme(code1,newcode1,elabCode1,category1,isdis1,quota1); 
   					vmap.putObject(newcode1,tmp);
   				}
   				char c = code1.charAt(0);
          		if(!college.contains(c)){
          			college.add(c);
        			DsProgramme dp = new DsProgramme(c);
        			dmap.putObject(c, dp);
        		}

			}
			in1.close();

			BufferedReader in2 = new BufferedReader(new FileReader(file2));			//ranklist
			String s1=in2.readLine();
			while(in2.ready()){
				s1=in2.readLine();
				int k=s1.indexOf(',');
  				String id=s1.substring(0,k);
  				s1=s1.substring(k+1,s1.length());
  				k=s1.indexOf(',');
  				s1=s1.substring(k+1,s1.length());//skips gender			ask abt this
  				k=s1.indexOf(',');
  				s1=s1.substring(k+1,s1.length());//skips cml			ask abt this
   				float[] val=new float[9];
   				for (int i=0;i<8 ;i++ ) {
		  			k=s1.indexOf(',');
		  			val[i]=Float.parseFloat(s1.substring(0,k));
	  				s1=s1.substring(k+1,s1.length());
  				}
  				val[8]=Float.parseFloat(s1);
         		float r1=val[0];
          		float r2=0;
          		float r3=0;
          		if(val[5]!=0) //a gen pd can
          		{
            		r2=0;
            		r3=val[5];
          		}
          		else if(val[1]!=0){   // an obc candidate
            		r2=val[1];
            		if(val[6]!=0)
            		{    // an obc pd candidate
              			r3=val[6];
            		}
          		}
          		else if(val[2]!=0){   //a sc candidate
            		r2=val[2];
            		if(val[7]!=0){    // a sc pd candidate
              			r3=val[7];
            		}
          		}
          		else if(val[3]!=0){   //a st candidate
            		r2=val[3];
            		if(val[7]!=0){    // a st pd candidate
              			r3=val[8];
            		}
          		}
  				Tuple tmp = new Tuple(id,r1,r2,r3);
  				ml.addObject(tmp);
			}
      		in2.close();

		}
		catch(FileNotFoundException exception)		//Exception Handling
    	{
        	System.out.println("The file " + file.getPath() + " was not found.");
 	    }

		catch(IOException exception)
    	{
        	System.out.println(exception);
 	    }

	}
	//---------------------------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------------------------
	public void algo(){

		int cs=arrC.size();

		//This procedure assigns ranks of candidates in their object itself through hashmap MeritList1
		for (int i=0;i<cs;i++) {
			Tuple tmp=new Tuple();
			tmp=ml.getObject(arrC.get(i).uniqueID);
			arrC.get(i).rank1=tmp.getrank1();
			arrC.get(i).rank2=tmp.getrank2();
			arrC.get(i).rank3=tmp.getrank3();
		}
		//This expands the MeritList1s of all the candidates
		for (int i=0;i<cs ;i++ ) {
			arrC.get(i).expandpref();
		}
		int dscount=0;
		int fcount=0;

		for(int i=0;i<cs;i++){
			if(arrC.get(i).category.equals("DS")){
				dscount++;
			}
			if(arrC.get(i).category.equals("F")){
				fcount++;
			}
		}
   				
		//checking for ds candidates
   		if(dscount!=0){
		while(true){

		int k=0;
		//This procedure puts tuples in their current virtual program
		for (int i=0; i<cs; i++ ) {
			if(arrC.get(i).category.equals("DS")){
				if( (arrC.get(i).isWait) || (arrC.get(i).isdone) )
					k++;
				else{
					String prefme=arrC.get(i).getpref();
					DsProgramme dp=new DsProgramme();
					dp=dmap.getObject(prefme.charAt(1));
					if(dp.isCandidateok(arrC.get(i).getTuple())){		//given candidate is eligible for application
						dp.addCand(arrC.get(i).getTuple());
					}
				}
			}
		}
		if(k==dscount)
			break;
		//sorts and filters the candidates

		dmap.filtermap();
		//this procedure checks whether a candidate is waitlisted or not
		for (int i=0;i<cs ;i++ ) {
			if(arrC.get(i).category.equals("DS")){
				if(!arrC.get(i).isdone){
					String s=arrC.get(i).uniqueID;
					String pref=arrC.get(i).getpref();
					DsProgramme dp=new DsProgramme();
					dp=dmap.getObject(pref.charAt(1));
					//if the cadidate is waitlisted
					if(dp.isthere(s)){
						arrC.get(i).allot(pref.substring(1));
					}	
					//if the cadidate is not waitlisted
					else{
						arrC.get(i).incpref();
						arrC.get(i).del();
					}
				}	
			}
		}
	}
	}
	int gg=0;
	for(int i=0;i<cs;i++){
		if( arrC.get(i).category.equals("DS") && (arrC.get(i).isdone) ){
			arrC.get(i).category="GE";
			arrC.get(i).isdone=false;
			gg++;
		}
	}

	while(true){

		int k=0;
		//This procedure puts tuples in their current virtual program
		for (int i=0; i<cs; i++ ) {
			if(!arrC.get(i).category.equals("F")) {
			if( (arrC.get(i).isWait) || (arrC.get(i).isdone) ){
				k++;
			}
			else{
				String prefme=arrC.get(i).getpref();
				VirtualProgramme vp=new VirtualProgramme();
				vp=vmap.getObject(prefme);
				if(vp.isCandidateok(arrC.get(i).getTuple())){		//given candidate is eligible for application
					vp.addCand(arrC.get(i).getTuple());
				}
			}
		}
		}
		if( k == ( cs - fcount) )
			break;
		//sorts and filters the candidates

		vmap.filtermap();
		//this procedure checks which candidate is still present in the vp list after filtering
		for (int i=0;i<cs ;i++ ) {
			if(!arrC.get(i).category.equals("F")){
			if(!arrC.get(i).isdone){
				String s=arrC.get(i).uniqueID;
				String pref=arrC.get(i).getpref();
				VirtualProgramme vp=new VirtualProgramme();
				vp=vmap.getObject(pref);
				//if the cadidate is waitlisted
				if(vp.isthere(s)){
					arrC.get(i).allot(vp.getnewcode());
				}	
				//if the cadidate is not waitlisted
				else{
					arrC.get(i).incpref();
				}
			}
			}	
		}
	}

	if(fcount!=0){
		for(int i=0;i<cs;i++){
			if(arrC.get(i).category.equals("F")){
				while(!arrC.get(i).isdone) { 
					VirtualProgramme vp=new VirtualProgramme();
					String pref=arrC.get(i).getpref();
					vp=vmap.getObject(pref);
					if(vp.isCandidateok(arrC.get(i).getTuple())) {
						if(vp.getlastrank()>=arrC.get(i).getTuple().getrank1()){
							arrC.get(i).foreignallot(vp.getnewcode());
						}
						else{
							arrC.get(i).incpref();
						}
					}
				}
			}
		}
	}
		//counting the number of finished candidates

	System.out.println("CandidateUniqueId,ProgrammeID");		
	for(int i=0;i<cs;i++) {
		arrC.get(i).printc();
	}


	}
}

public class Main{
	public static void main(String[] args) throws IOException
	 {
		GaleShapleyAdmission g = new GaleShapleyAdmission();
				
    PrintStream out1 = null;
    PrintStream out2 = null;
    
  try{
    	out1 = new PrintStream("outputMeritOrderAdmission.csv");
    	System.setOut(out1);
		MeritOrderAdmission md=new MeritOrderAdmission(); 
		}
	finally{
		if(out1 != null)out1.close();
	}

    try{
    	out2 = new PrintStream("outputGaleShapleyAdmission.csv");
    	System.setOut(out2);
    	g.algo();
	 	}
	   finally{
		 if(out2 != null)
       out2.close();
	   }


	}
}

 