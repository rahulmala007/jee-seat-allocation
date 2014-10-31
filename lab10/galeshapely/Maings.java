//package com.java2novice.hashtable;
import java.io.*;
//import java.io.BufferedReader;
import java.util.*;
//import java.lang.Object;
//import java.util.HashTable;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.PriorityQueue;

class Couple{				//to represent the pair rank-id of a student ot be stored in class VirtualProgramme
	private String uniqueID;
	private int rank;
	public Couple(){
		rank=0;
	}
	public Couple(String a,int b){
		this.uniqueID=a;
		this.rank=b;
	}
	public String getID(){
		return uniqueID;
	}
	public int getrank(){
		return rank;
	}
}

class Tuple{
	private String uniqueID;
	public String category;
	public boolean isdisabled;
	private int rank1;
	private int rank2;
	private int rank3;
  	public Tuple(){
    	uniqueID="";
    	rank1=rank3=rank2=0;
		category="";
		isdisabled=false;
  	}
	public Tuple(String s, int a,int b,int c){
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
  	public int getrank1(){
    	return rank1;
  	}
  	public int getrank2(){
    	return rank2;
  	}
  	public int getrank3(){
    	return rank3;
  	}
  	public boolean isdis(){
  		return isdisabled;
  	}
  	public String getcat(){
  		return category;
  	}
}

class TupleComparatorgen implements Comparator<Tuple>{
	@Override
	public int compare(Tuple p,Tuple q){
		int r1=p.getrank1();
		int r2=q.getrank1();
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

class TupleComparatorgenpd implements Comparator<Tuple>{
	@Override
	public int compare(Tuple p,Tuple q){
		int r1=p.getrank1();
		int r2=q.getrank1();
		int r3=p.getrank3();
		int r4=q.getrank3();
		String c1=p.category;
		String c2=q.category;
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

class TupleComparatorobcpd implements Comparator<Tuple>{
	@Override
	public int compare(Tuple p,Tuple q){
		int r1=p.getrank1();
		int r2=q.getrank1();
		int r3=p.getrank3();
		int r4=q.getrank3();
		String c1=p.category;
		String c2=q.category;
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

class TupleComparatorobc implements Comparator<Tuple>{
	@Override
	public int compare(Tuple p,Tuple q){
		int r1=p.getrank1();
		int r2=q.getrank1();
		int r3=p.getrank2();
		int r4=q.getrank2();
		String c1=p.category;
		String c2=q.category;
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


class TupleComparatorscst implements Comparator<Tuple>{
	@Override
	public int compare(Tuple p,Tuple q){
		int r1=p.getrank2();
		int r2=q.getrank2();
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

class TupleComparatorscstpd implements Comparator<Tuple>{
	@Override
	public int compare(Tuple p,Tuple q){
		int r1=p.getrank2();
		int r2=q.getrank2();
		int r3=p.getrank3();
		int r4=q.getrank3();
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
	public ArrayList <String> virtualProg;	//lists the preferences of the students
	public ArrayList <String> newvirtualProg;	//lists the expanded preferences of the students
	public int currentVirtualProg;
	public boolean isWait;		//stores true if the student is been waitlisted in the current virtual program
	public int rank1;		//nonzero if gen present or a ds
	public int rank2;		//nonzero if category student
	public int rank3;		//nonzero if student is a pd
	private String prog;
	public boolean isdone; 

	public Candidate(){
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
			System.out.println("hi");
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
			System.out.println("hi");
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
		return;
	}
	public Tuple getTuple(){
		Tuple out=new Tuple(uniqueID,rank1,rank2,rank3);
		out.category=category;
		out.isdisabled=isdisabled;
		return out;
	}

//	if()

	public String getpref(){
		if(newvirtualProg.size()==0)
			return "";
		else{			
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

	}
	public int getp(){
		return virtualProg.size();
	}
	public int getpp(){		//returns newvirtualProg size
		return newvirtualProg.size();
	}
	public void allot(String s){
		isWait=true;
		prog=s;
	}
	public void del(){
		isWait=false;
		prog="-1";
	}
	public void printc(){
		System.out.println(uniqueID+" "+prog);
	}
	
}

class MeritList{
	private HashMap <String,Tuple> hm;

	public MeritList(){
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

class VirtualProgramme{
	private String code;
	private String newcode;		//used for hashmap purposes
	private String elabCode;		//stores fullform of code, eg Electrical Engineering for code B2
	private String category;
	private boolean isdis;		//disability status
	private int quota;
	private ArrayList <Tuple> currentWaitList;
//	private int currentWaitListLen;

	public VirtualProgramme(){
		currentWaitList=new ArrayList<Tuple>();
//		currentWaitListLen=0;
		quota=0;
		code="";
		newcode="myass";
		category="";
		isdis=false;
	}
	public VirtualProgramme(String code,String newcode,String elabCode,String category,boolean isdis,int quota){
		this.code=code;
		this.newcode=newcode;
		this.elabCode=elabCode;
		this.category=category;
		this.isdis=isdis;
		this.quota=quota;
		currentWaitList=new ArrayList<Tuple>();
//		System.out.println("new code is "+newcode);

	}
	public boolean isVacant(){
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
	boolean isCandidateok(Tuple t){		//returns true if a given Candidate is eligible to be added.
		if(category.equals("GE") && (!isdis)){
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

/*		else if(( category.equals("SC") || category.equals("ST") )&& (isdis)){		//sc or st pd
			if( (t.category.equals("SC") || t.category.equals("ST")) )
				return true;
			return false;
		}
*/
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

	public void filter(){
//		System.out.println(currentWaitList.size());
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
//			if(i<quota)
//				break;
		}
//			System.out.println("hi");
//		for (int i=0;i<currentWaitList.size() ;i++ ) {
//			System.out.println("id is "+currentWaitList.get(i).getID());
//		}
		System.out.println(quota+" "+currentWaitList.size()+" "+newcode);
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
	public String getcode(){
		return code;
	}

	public String getnewcode(){
		return newcode;
	}
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
//			System.out.println("hi "+key);
			hm.get(key).sortc();
			hm.get(key).filter();
		}
	}
}
class GaleShapleyAdmission {

	ArrayList <Candidate> arrC=new ArrayList <Candidate>();		//kinda vector of candidates
	//stores the arraylist of candidates
	VirtualProgrammeMap vmap=new VirtualProgrammeMap();
	//stores 
	MeritList ml = new MeritList();
	String s;

	public GaleShapleyAdmission(){
		vmap=new VirtualProgrammeMap();
		File file=new File("choices.csv");
		File file1=new File("programs.csv");
		File file2=new File("ranklist.csv");
		try
		{
//			String line = null;
			BufferedReader in = new BufferedReader(new FileReader(file));	//choices
			String s=in.readLine();		//skips the first line read.
			while (in.ready()){
  				s = in.readLine();
  				//System.out.println(s);
  				Candidate tmp=new Candidate();
  				int k1=s.indexOf(',');
  				tmp.uniqueID=s.substring(0,k1);
  				s=s.substring(k1+1,s.length());
  				k1=s.indexOf(',');
  				tmp.category=s.substring(0,k1);
//  				System.out.println(tmp.category)
   				s=s.substring(k1+1,s.length());
  				if(s.substring(0,1).equals("Y")){
  					tmp.isdisabled=true;
  				}
  				else{
  					tmp.isdisabled=false;
  				}
  				s=s.substring(2,s.length());
  				//System.out.println(s);
  				//to be decided how to add programs to a candidate..
  				while(s.length()>2){
	  				k1=s.indexOf('_');
	  				String p=s.substring(0,k1);
	  				tmp.virtualProg.add(p);			//adds the codes of choices as strings in the ArrayList
	  				s=s.substring(k1+1,s.length());
  				}
  				tmp.virtualProg.add(s);			//adds the last choice
  				arrC.add(tmp);
//  				System.out.println(s);
			}
			in.close();

			BufferedReader in1 = new BufferedReader(new FileReader(file1));	//programs
			String s2=in1.readLine();		//skips the first line read.
			while (in1.ready()){
  				s2 = in1.readLine();
//  				System.out.println(s);

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
//	 				switch (i){

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
   				int[] val=new int[9];
   				for (int i=0;i<8 ;i++ ) {
		  			k=s1.indexOf(',');
		  			val[i]=Integer.parseInt(s1.substring(0,k));
	  				s1=s1.substring(k+1,s1.length());
  				}
  				val[8]=Integer.parseInt(s1);
         		int r1=val[0];
          		int r2=0;
          		int r3=0;
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
/*  				System.out.println(mlgen.hm.size());
  				System.out.println(mlobc.hm.size());
  				System.out.println(mlsc.hm.size());
  				System.out.println(mlst.hm.size());
  				System.out.println(mlgen_pd.hm.size());
  				System.out.println(mlobc_pd.hm.size());
  				System.out.println(mlsc_pd.hm.size());
  				System.out.println(mlst_pd.hm.size());

  				String key="G111100105";
  				Tuple value=mlgen.hm.get(key);
  				System.out.println(value.getID());
  				System.out.println(value.getrank());
*/
//  				System.out.println(s1);


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
//	//final part left...
	public void algo(){

		int cs=arrC.size();

//		System.out.println("rank is "+ (arrC.get(0)).rank1);
//This procedure assigns ranks of candidates in their object itself through hashmap meritlist
		for (int i=0;i<cs;i++) {
			Tuple tmp=new Tuple();
			tmp=ml.getObject(arrC.get(i).uniqueID);
			arrC.get(i).rank1=tmp.getrank1();
			arrC.get(i).rank2=tmp.getrank2();
			arrC.get(i).rank3=tmp.getrank3();
		}
		//This expands the meritlists of all the candidates
		for (int i=0;i<cs ;i++ ) {
			arrC.get(i).expandpref();
		}

		while(true){


		int k=0;
		//This procedure puts tuples in their current virtual program
		for (int i=0; i<cs; i++ ) {
			if( (arrC.get(i).isWait) || (arrC.get(i).isdone) )
				k++;
			else{
			String prefme=arrC.get(i).getpref();
			VirtualProgramme vp=new VirtualProgramme();
			vp=vmap.getObject(prefme);
//			Tuple tmp=new Tuple();
//			tmp=arrC.get(i).getTuple();
			if(vp.isCandidateok(arrC.get(i).getTuple())){		//given candidate is eligible for application
				vp.addCand(arrC.get(i).getTuple());
			}
		}
	}
		if(k==cs)
			break;
		//sorts and filters the candidates

		vmap.filtermap();
		//this procedure 
		for (int i=0;i<cs ;i++ ) {
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
				arrC.get(i).del();
			}	
		}
	}
		//counting the number of finished candidates

	for (int i=0;i<cs ;i++ ) {
		arrC.get(i).printc();
	}


/*		for (int i=0;i<cs ;i++ ) {
			if( (arrC.get(i).isWait) || (arrC.get(i).isdone) ){
				k++;
			}
		}

*/

	}
}

public class Maings{
	public static void main(String[] args) {
		GaleShapleyAdmission g = new GaleShapleyAdmission();
		g.algo();
	}
}

 