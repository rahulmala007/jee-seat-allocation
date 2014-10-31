import java.io.*;
//import java.io.BufferedReader;
import java.util.*;
import java.util.Collections;

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
    rank1=a;  //for gen
    rank2=b;  //for cat
    rank3=c;  //for pd
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
/*
class TupleComparator implements Comparator<Tuple>{
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
*/


class Candidate{
  public String uniqueID;
  public String category;
  public boolean isdisabled;
  public boolean nation;      //true if Indian
  public int virtualProgLen;
  public boolean status;   //true if selected
  public String allocatedProg;
  public ArrayList <String> virtualProg;
  public ArrayList <String> newvirtualProg;
  public int currentVirtualProg;
  public boolean isDeleted;   //stores true if the student is no longer been waitlisted in the current virtual program
  public int rank1;   //nonzero if gen present or a ds
  public int rank2;   //nonzero if category student
  public int rank3;   //nonzero if student is a pd

  public Candidate(){
    status= false;
    virtualProgLen=0;
    currentVirtualProg=0;
    virtualProg = new ArrayList <String>();
    newvirtualProg = new ArrayList <String>(); 
    allocatedProg = "-1";

  }

  void insertProg(String s){
    virtualProg.add(s);
}
public Tuple getTuple(){
    Tuple out=new Tuple(uniqueID,rank1,rank2,rank3);
    out.category=category;
    out.isdisabled=isdisabled;
    return out;
  }

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
          newvirtualProg.add("0"+tmp);
          newvirtualProg.add("1"+tmp);

          
        }       
      }
      else{
        for (int i=0;i< virtualProg.size();i++ ) {
          String tmp=virtualProg.get(i);
          newvirtualProg.add("0"+tmp);
          newvirtualProg.add("1"+tmp);
          newvirtualProg.add("4"+tmp);
          newvirtualProg.add("5"+tmp);
          
        }
      }
    }

    if(category.equals("SC")){
      if(isdisabled==false){      //if not disabled
        for (int i=0;i< virtualProg.size();i++ ) {
          String tmp=virtualProg.get(i);
          newvirtualProg.add("0"+tmp);
          newvirtualProg.add("2"+tmp);
          
        }       
      }
      else{
        for (int i=0;i< virtualProg.size();i++ ) {
          String tmp=virtualProg.get(i);
          newvirtualProg.add("0"+tmp);
          newvirtualProg.add("2"+tmp);
         newvirtualProg.add("4"+tmp);
          newvirtualProg.add("6"+tmp);
          
        }
      }
    }
    if(category.equals("ST")){
      if(!isdisabled){      //if not disabled
        for (int i=0;i< virtualProg.size();i++ ) {
          String tmp=virtualProg.get(i);
          newvirtualProg.add("0"+tmp);
          newvirtualProg.add("3"+tmp);
          
        }       
      }
      else{
        for (int i=0;i< virtualProg.size();i++ ) {
          String tmp=virtualProg.get(i);
          newvirtualProg.add("0"+tmp);
          newvirtualProg.add("3"+tmp);
          newvirtualProg.add("4"+tmp);
          newvirtualProg.add("7"+tmp);
          
        }
      }
    }
    return;
  }


public void expandpref(){
    if( category.equals("GE") || category.equals("OBC") ){
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
      if(!isdisabled){      //if not disabled
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

}

class CandComparatorgen implements Comparator<Candidate>{
  @Override
  public int compare(Candidate p,Candidate q){
    int r1=p.rank1;
    int r2=q.rank1;
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
    int r1=p.rank2;
    int r2=q.rank2;
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
    int r1=p.rank3;
    int r2=q.rank3;
    if(r1>r2){
      return 1;
    }
    else if(r1<r2){
      return -1;
    }
    else return 0;
  }

}




class VirtualProgramme{
  public String code;
  public String newcode;
  public String elabCode;   //stores fullform of code, eg Electrical Engineering for code B2
  public String category;
  private boolean isdis;    //disability status
  private int quota;
  public ArrayList <Tuple> currentWaitList;
  public int closingrank;
  public int currentWaitListLen;


  public VirtualProgramme(){
    currentWaitList = new ArrayList <Tuple>();
    
    quota=0;
    closingrank=100000;
  }
  public VirtualProgramme(String code,String newcode,String elabCode,String category,boolean isdis,int quota){
    this.code=code;
    this.newcode=newcode;
    this.elabCode=elabCode;
    this.category=category;
    this.isdis=isdis;
    this.quota=quota;
    currentWaitList = new ArrayList <Tuple>();
//    if(category=="O")
  }

/*  public boolean isVacant(Candidate c){
      if(c.category.equals("F")){
        int myrank=c.rank1;
        if(myrank<=lastrank){
          quota++;
          return true;
        }
        return false;
      }
      else{
        if(quota>currentWaitListLen)   //for an indian student
          return true;
        return false;
      }
  }
*/
  public boolean isVacant(Candidate c){
        if(quota>currentWaitListLen)   //for an indian student
        {
          if(c.category.equals("F")){
            quota++;
          }
          return true;
        }
        //if()

        return false;
  }


  public void addquota(int q){
    quota=q;
    return;
  }
  public void lenProgress(){
    currentWaitListLen++;
    return;
  }
  public int getquota(){
    return quota;
  }
 

  public void incquota(){
    quota++;
  }

  public void addis(boolean b){
    isdis=b;
  }

  public boolean isdisabled(){
    return isdis;
  }

  public boolean iseligible(Candidate c){
    if(category.equals("GE")){
      if(c.rank1==0)
        return false;
      return true;
    }
    return true;
  }
  public boolean isAllocated(Candidate c){
      Tuple t = c.getTuple();
      if(currentWaitList.contains(t))return true;
      return false;

  }

 //  boolean addCandidate(Candidate c)   returns true if a given Candidate is added.
//  {
//  }
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

}
class DsProgramme{
  private int quota;
  public char name;
  public ArrayList <Tuple> dscurrentWaitList;
  public int dscurrentWaitListLen;
  public DsProgramme(){
    quota = 2;
    dscurrentWaitList = new ArrayList <Tuple>();
    dscurrentWaitListLen = 0;
  }
  public DsProgramme(char name){
    quota = 2;
    this.name = name;
    dscurrentWaitList = new ArrayList <Tuple>();
    dscurrentWaitListLen = 0;
  }

  public boolean isAllocated(Candidate c){
      Tuple t = c.getTuple();
      if(dscurrentWaitList.contains(t)) {
        System.out.println("lelu");
         return true;
       }
      else 
        return false;

  }
  public boolean isVacant(){
       if(quota>dscurrentWaitListLen)   
          return true;
         return false;
        

      }
  public void dslenProgress(){
    dscurrentWaitListLen++;
    return;
  }
  void decquota(){
    quota--;
    return;
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
   				int[] val=new int[9];
   				for (int i=0;i<8 ;i++ ) {
		  			k=s.indexOf(',');
		  			val[i]=Integer.parseInt(s.substring(0,k));
	  				s=s.substring(k+1,s.length());
  				}
  				val[8]=Integer.parseInt(s);
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
              category1="GEN_PD";
              isdis1=true;
              newcode1="4"+code1;
              quota1=Integer.parseInt(q[i]);
            }     
            break;      
            case 5: {
              category1="OBC_PD";
              isdis1=true;
              newcode1="5"+code1;
              quota1=Integer.parseInt(q[i]);
            }     
            break;      
            case 6: {
              category1="SC_PD";
              isdis1=true;
              newcode1="6"+code1;
              quota1=Integer.parseInt(q[i]);
            }     
            break;      
            case 7: {
              category1="ST_PD";
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
     
     for(int i = 0;i <(ml.mlds).size(); i++){

        Candidate tmp = (ml.mlds).get(i);
        Tuple t = tmp.getTuple(); 
        for(int j=0; j<((tmp.virtualProg).size());j++){

          String str = (tmp.virtualProg).get(j);
          char ch = str.charAt(0);
          DsProgramme dp = dmap.getObject(ch);
          if(dp.isVacant()){
            ;
            tmp.allocatedProg = tmp.virtualProg.get(j);
            dp.dslenProgress();
            dp.dscurrentWaitList.add(t);
            tmp.status = true;
            ml.merit.remove(tmp);
            ml.mlgen.remove(tmp);
            break;

          }


        }
      }
      
      for(int i = 0;i <(ml.mlgen).size(); i++){                             //DS candidates who are not allocated seats
        Candidate tmp = (ml.mlgen).get(i);
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
       /*   if(vp.currentWaitListLen == vp.getquota()){
            if(vp.category.equals("GEN"))vp.closingrank = tmp.rank1;
            if(vp.category.equals("OBC")|| vp.category.equals("SC")|| vp.category.equals("ST"))vp.closingrank = tmp.rank2;
            if((vp.category.equals("OBC"))&& (vp.isdisabled()) || (vp.category.equals("SC"))&& (vp.isdisabled())|| (vp.category.equals("ST"))&& (vp.isdisabled()))vp.closingrank = tmp.rank3;
           }
          */
            vp.currentWaitList.add(t);
          tmp.status = true;
          break;
        }
      }
      }


}
       
      for(int i=0; i <arrC.size(); i++){                               //testing before dereservaton
        Candidate tmp = arrC.get(i);
        System.out.println(tmp.uniqueID+" "+tmp.allocatedProg+ "   "+ tmp.category+ " "+tmp.rank1 + " "+tmp.rank2 + " "+tmp.rank3);
      }

        //De-reservation

      for(int i=0; i < arrC.size(); i++){
        Candidate tmp = arrC.get(i);
        if(tmp.allocatedProg.equals("-1"))tmp.expandpref();
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
       /*   if(vp.currentWaitListLen == vp.getquota()){
            if(vp.category.equals("GEN"))vp.closingrank = tmp.rank1;
            if(vp.category.equals("OBC")|| vp.category.equals("SC")|| vp.category.equals("ST"))vp.closingrank = tmp.rank2;
            if((vp.category.equals("OBC"))&& (vp.isdisabled()) || (vp.category.equals("SC"))&& (vp.isdisabled())|| (vp.category.equals("ST"))&& (vp.isdisabled()))vp.closingrank = tmp.rank3;
           }
          */
            vp.currentWaitList.add(t);
          tmp.status = true;
          break;
        }
      }
      }
    }


}
System.out.println(" ");
for(int i=0; i <arrC.size(); i++){                               //testing after dereservation
        Candidate tmp = arrC.get(i);
        
        System.out.println(tmp.uniqueID+" "+tmp.allocatedProg+ "   "+ tmp.category+ " "+tmp.rank1 + " "+tmp.rank2 + " "+tmp.rank3);
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
      
public class Mainup{
	public static void main(String[] args) {
//		GaleShapleyAdmission g = new GaleShapleyAdmission();
    MeritOrderAdmission md=new MeritOrderAdmission();
		//MeritList m=new MeritList();

	}
}

