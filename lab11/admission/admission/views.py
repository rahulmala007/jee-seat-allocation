from django.http import HttpResponse
from django.http import HttpResponseRedirect
from Candidate.models import Candidate,course
from django.contrib.sessions.models import Session
from datetime import datetime
import math
import csv
from django.shortcuts import render
import decimal


def updatecl(request):
	try:
		val=request.POST['update']
		if val=="yes":
			updateCollegeList()
		return HttpResponse("College List Updated")	
	except:
		return HttpResponseRedirect('/homepage/')


def updaterl(request):
	try:
		val=request.POST['update']
		if val=="yes":
			updateRankList()
		return HttpResponse("Rank List Updated")	
	except:
		return HttpResponseRedirect('/homepage/')
		 

def updateRankList():

	file=open("admission/data/ranklist.csv",'r')
	reader=csv.reader(file)
	rownum=0
	Candidate.objects.all().delete()
	for row in reader:
		if rownum!=0:
			Candidate.objects.create(user_name=row[0],password=row[0],roll_no=row[0],rankGE=row[3],rankOBC=row[4],rankSC=row[5],rankST=row[6],rankGEPD=row[8],rankOBCPD=row[9],rankSCPD=row[10],rankSTPD=row[11]) 
		rownum+=1

	file.close()
	message=rownum
	return (HttpResponse(message))

def updateCollegeList():
	file=open("admission/data/combined_data.csv",'r')
	reader=csv.reader(file)
	rownum=0
	course.objects.all().delete()
	for row in reader:
		if rownum!=0:
			course.objects.create(code=row[0],go=row[1],gc=row[2],oo=row[3],oc=row[4],sco=row[5],scc=row[6],sto=row[7],stc=row[8],gpo=row[9],gpc=row[10],opo=row[11],opc=row[12],scpo=row[13],scpc=row[14],stpo=row[15],stpc=row[16],branch_name=row[18],college_name=row[17]) 
		rownum+=1

	file.close()

	message=rownum
	return (HttpResponse(message))

def showrl(request):
	p=Candidate.objects.all()
	display=""
	for cand in p:
		display += cand.user_name + "  " + cand.password+ "\n"
	return (HttpResponse(display))

def showcl(request):
	p=course.objects.all()
	display=""
	for cand in p:
		display += cand.code + "\n"
	return (HttpResponse(display))


def login(request):

	try:
		if request.session.get('candidate_id',''):
			return HttpResponseRedirect("/homepage/")
			
		if not 'userID' in request.POST:
			return render(request,"login_page.html")
		if not 'passwd' in request.POST:
			return render(request,"login_page.html")

		
		user=request.POST['userID']
		passwd=request.POST['passwd']
			
		if Candidate.objects.filter(user_name=user):
			C=Candidate.objects.get(user_name=user)
			rank=C.rankGE
			if passwd==C.password:
				if 'remember' in request.POST:
					pass
				else:		
					request.session.set_expiry(0)
				request.session['candidate_id'] = user
				return HttpResponseRedirect("/homepage/")
			else:
				return render(request,"login_page.html")								
		else:
			return render(request,"login_page.html")
	except Exception as e:
		return 	render(request,'test.html',{'c':e})			


def logout(request):
	try:
		del request.session['candidate_id']
		return HttpResponseRedirect("/login/")
	except:
		return HttpResponseRedirect("/login/")

def homepage(request):
	try:
		user=request.session['candidate_id']
		C=Candidate.objects.get(user_name=user)
		rank=C.rankGE	
		stats=get_all_logged_in_users()
		return render(request,'home_page.html',{'user_name':C.user_name,'users':stats[0],'avg':stats[1],'std':stats[2]})
	except Exception as e:
		return HttpResponseRedirect('/login/')

def updateit(request):
	
	user=request.session['candidate_id']
	C=Candidate.objects.get(user_name=user)
	C.user_name=request.POST['name']
	C.password=request.POST['password']
	C.question=request.POST['question']
	C.answer=request.POST['answer']
	C.save()
	request.session['candidate_id']=C.user_name
	return HttpResponseRedirect("/homepage/")

def updateProfile(request):

	user=request.session['candidate_id']
	C=Candidate.objects.get(user_name=user)
	return render(request,"updateProfile.html",{'name':C.user_name,'password':C.password,'question':C.question,'answer':C.answer})					

def security(request):
		
	user=request.POST['id']
	request.session['id1']=user
	C=Candidate.objects.get(user_name=user)
	question=C.question
	return render(request,"security.html",{'question':question})	

def securitycheck(request):

	answer=request.POST['answer']
	password=request.POST['password']	
	C=Candidate.objects.get(user_name=request.session['id1'])
	if C.answer==answer:
		C.password=password
		C.save()
		del request.session['id1']
		return HttpResponseRedirect("/login/")
	else:
		del request.session['id1']
		return HttpResponseRedirect("/login/")

def CourseCol(request):
	
	user=request.session['candidate_id']
	C=Candidate.objects.get(user_name=user)

	college=request.GET['college']
	if(college=="none"):
		return HttpResponseRedirect('/homepage/')

	list=[]
	list1=[]

	l1=[C.rankGE,C.rankOBC,C.rankSC,C.rankST,C.rankGEPD,C.rankOBCPD,C.rankSCPD,C.rankSTPD]
	clist=[]
	if college=="IIT Bhubaneswar":
		clist = course.objects.filter(code__icontains='A')
	elif college=="IIT Bombay":
		clist = course.objects.filter(code__icontains='B')
	elif college=="IIT Delhi":
		clist = course.objects.filter(code__icontains='D')
	elif college=="IIT Gandhinagar":
		clist = course.objects.filter(code__icontains='N')
	elif college=="IIT Guwahati":
		clist = course.objects.filter(code__icontains='W')
	elif college=="IIT Hyderabad":
		clist = course.objects.filter(code__icontains='H')
	elif college=="IIT Indore":
		clist = course.objects.filter(code__icontains='E')
	elif college=="IIT Kanpur":
		clist = course.objects.filter(code__icontains='K')
	elif college=="IIT Kharagpur":
		clist = course.objects.filter(code__icontains='G')
	elif college=="IIT Madras":
		clist = course.objects.filter(code__icontains='M')
	elif college=="IIT Mandi":
		clist = course.objects.filter(code__icontains='C')
	elif college=="IIT Patna":
		clist = course.objects.filter(code__icontains='P')
	elif college=="IIT Rajasthan":
		clist = course.objects.filter(code__icontains='J')
	elif college=="IIT Roorkee":
		clist = course.objects.filter(code__icontains='R')
	elif college=="IIT Ropar":
		clist = course.objects.filter(code__icontains='U')
	elif college=="ISM Dhanbad":
		clist = course.objects.filter(code__icontains='S')
	elif college=="IT BHU":
		clist = course.objects.filter(code__icontains='V')


	for cor in clist:
		l2=[cor.go,cor.gc,cor.oo,cor.oc,cor.sco,cor.scc,cor.sto,cor.stc,cor.gpo,cor.gpc,cor.opo,cor.opc,cor.scpo,cor.scpc,cor.stpo,cor.stpc]
		for i in range(8):
			cand=int(l1[i])
			colo=int(l2[2*i])
			colc=int(l2[2*i+1]) 
			if colc >= cand and cand != 0 :
				if not cor in list:
					list.append(cor)
				if colo <= cand :
					if not cor in list1:
						list1.append(cor)	
	stats=get_all_logged_in_users()
	return render(request,'home_page.html',{'line1':'Probable branches which you can get with your rank (source:JEE Data:2012)','line2':'The branches which were preferred in year 2012 with your rank', 'list':list,'list1':list1,'college':college,'users':stats[0],'avg':stats[1],'std':stats[2]})	

def CourseBra(request):
	user=request.session['candidate_id']
	C=Candidate.objects.get(user_name=user)

	branch=request.GET['branch']
	if(branch=="none"):
		return HttpResponseRedirect('/homepage/')

	list=[]
	list1=[]

	l1=[C.rankGE,C.rankOBC,C.rankSC,C.rankST,C.rankGEPD,C.rankOBCPD,C.rankSCPD,C.rankSTPD]
	clist=[]

	if branch=='Aerospace Engineering':
		clist = course.objects.filter(branch_name__icontains='Aerospace Engineering')
	elif branch=='Agricultural and Food Engineering':
		clist = course.objects.filter(branch_name__icontains='Agricultural and Food Engineering')
	elif branch=='Applied Geology':
		clist = course.objects.filter(branch_name__icontains='Applied Geology')
	elif branch=='Applied Geophysics':
		clist = course.objects.filter(branch_name__icontains='Applied Geophysics')
	elif branch=='Applied Mathematics':
		clist = course.objects.filter(branch_name__icontains='Applied Mathematics')
	elif branch=='Architecture':
		clist = course.objects.filter(branch_name='Architecture')
	elif branch=='Biochemical Engineering':
		clist = course.objects.filter(branch_name__icontains='Biochemical Engineering')
	elif branch=='Bioengineering':
		clist = course.objects.filter(branch_name__icontains='Bioengineering')
	elif branch=='Biological Engineering':
		clist = course.objects.filter(branch_name__icontains='Biological Engineering')
	elif branch=='Biological Sciences':
		clist = course.objects.filter(branch_name__icontains='Biological Sciences')
	elif branch=='Biotechnology':
		clist = course.objects.filter(branch_name__icontains='Biotechnology')
	elif branch=='Ceramic Engineering':
		clist = course.objects.filter(branch_name__icontains='Ceramic Engineering')
	elif branch=='Chemical Engineering':
		clist = course.objects.filter(branch_name__icontains='Chemical Engineering')
	elif branch=='Chemical Science and Technology':
		clist = course.objects.filter(branch_name__icontains='Chemical Science and Technology')
	elif branch=='Chemistry':
		clist = course.objects.filter(branch_name__icontains='Chemistry')
	elif branch=='Civil Engineering':
		clist = course.objects.filter(branch_name__icontains='Civil Engineering')
	elif branch=='Computer Science and Engineering':
		clist = course.objects.filter(branch_name__icontains='Computer Science and Engineering')
	elif branch=='Design':
		clist = course.objects.filter(branch_name__icontains='Design')
	elif branch=='Economics':
		clist = course.objects.filter(branch_name__icontains='Economics')
	elif branch=='Electrical Engineering':
		clist = course.objects.filter(branch_name__icontains='Electrical Engineering')
	elif branch=='Electronics and Communication Engin':
		clist = course.objects.filter(branch_name__icontains='Electronics and Communication Engin')
	elif branch=='Electronics and Electrical Communic':
		clist = course.objects.filter(branch_name__icontains='Electronics and Electrical Communic')
	elif branch=='Electronics and Electrical Engineer':
		clist = course.objects.filter(branch_name__icontains='Electronics and Electrical Engineer')
	elif branch=='Electronics Engineering':
		clist = course.objects.filter(branch_name__icontains='Electronics Engineering')
	elif branch=='Energy Engineering':
		clist = course.objects.filter(branch_name__icontains='Energy Engineering')
	elif branch=='Engineering Design':
		clist = course.objects.filter(branch_name__icontains='Engineering Design')
	elif branch=='Engineering Physics':
		clist = course.objects.filter(branch_name__icontains='Engineering Physics')
	elif branch=='Engineering Science':
		clist = course.objects.filter(branch_name__icontains='Engineering Science')
	elif branch=='Environmental Engineering':
		clist = course.objects.filter(branch_name__icontains='Environmental Engineering')
	elif branch=='Exploration Geophysics':
		clist = course.objects.filter(branch_name__icontains='Exploration Geophysics')
	elif branch=='Geological Technology':
		clist = course.objects.filter(branch_name__icontains='Geological Technology')
	elif branch=='Geophysical Technology':
		clist = course.objects.filter(branch_name__icontains='Geophysical Technology')
	elif branch=='Industrial Chemistry':
		clist = course.objects.filter(branch_name__icontains='Industrial Chemistry')
	elif branch=='Industrial Engineering':
		clist = course.objects.filter(branch_name__icontains='Industrial Engineering')
	elif branch=='Instrumentation Engineering':
		clist = course.objects.filter(branch_name__icontains='Instrumentation Engineering')
	elif branch=='Manufacturing Science and Engineeri':
		clist = course.objects.filter(branch_name__icontains='Manufacturing Science and Engineeri')
	elif branch=='Material Science and Technology':
		clist = course.objects.filter(branch_name__icontains='Material Science and Technology')
	elif branch=='Materials Science and Engineering':
		clist = course.objects.filter(branch_name__icontains='Materials Science and Engineering')
	elif branch=='Mathematics and Computing':
		clist = course.objects.filter(branch_name__icontains='Mathematics and Computing')
	elif branch=='Mathematics and Scientific Computin':
		clist = course.objects.filter(branch_name__icontains='Mathematics and Scientific Computin')
	elif branch=='Mechanical Engineering':
		clist = course.objects.filter(branch_name__icontains='Mechanical Engineering')
	elif branch=='Metallurgical and Materials Enginee':
		clist = course.objects.filter(branch_name__icontains='Metallurgical and Materials Enginee')
	elif branch=='Metallurgical Engineering':
		clist = course.objects.filter(branch_name__icontains='Metallurgical Engineering')
	elif branch=='Metallurgical Engineering and Mater':
		clist = course.objects.filter(branch_name__icontains='Metallurgical Engineering and Mater')
	elif branch=='Mineral Engineering':
		clist = course.objects.filter(branch_name__icontains='Mineral Engineering')
	elif branch=='Mining Engineering':
		clist = course.objects.filter(branch_name__icontains='Mining Engineering')
	elif branch=='Mining Machinery Engineering':
		clist = course.objects.filter(branch_name__icontains='Mining Machinery Engineering')
	elif branch=='Mining Safety Engineering':
		clist = course.objects.filter(branch_name__icontains='Mining Safety Engineering')
	elif branch=='Naval Architecture and Ocean Engine':
		clist = course.objects.filter(branch_name__icontains='Naval Architecture and Ocean Engine')
	elif branch=='Ocean Engineering and Naval Archite':
		clist = course.objects.filter(branch_name__icontains='Ocean Engineering and Naval Archite')
	elif branch=='Petroleum Engineering':
		clist = course.objects.filter(branch_name__icontains='Petroleum Engineering')
	elif branch=='Pharmaceutics':
		clist = course.objects.filter(branch_name__icontains='Pharmaceutics')
	elif branch=='Physics':
		clist = course.objects.filter(branch_name__icontains='Physics')
	elif branch=='Polymer Science and Technology':
		clist = course.objects.filter(branch_name__icontains='Polymer Science and Technology')
	elif branch=='Process Engineering with MBA':
		clist = course.objects.filter(branch_name__icontains='Process Engineering with MBA')
	elif branch=='Production and Industrial Engineeri':
		clist = course.objects.filter(branch_name__icontains='Production and Industrial Engineeri')
	elif branch=='Pulp and Paper Engineering':
		clist = course.objects.filter(branch_name__icontains='Pulp and Paper Engineering')
	elif branch=='Quality Engineering Design and Manu':
		clist = course.objects.filter(branch_name__icontains='Quality Engineering Design and Manu')
	elif branch=='Systems Science':
		clist = course.objects.filter(branch_name__icontains='Systems Science')
	elif branch=='Textile Technology':
		clist = course.objects.filter(branch_name__icontains='Textile Technology')

	for cor in clist:
			l2=[cor.go,cor.gc,cor.oo,cor.oc,cor.sco,cor.scc,cor.sto,cor.stc,cor.gpo,cor.gpc,cor.opo,cor.opc,cor.scpo,cor.scpc,cor.stpo,cor.stpc]
			for i in range(8):
				cand=int(l1[i])
				colo=int(l2[2*i])
				colc=int(l2[2*i+1]) 
				if colc >= cand and cand != 0 :
					if not cor in list:
						list.append(cor)
					if colo <= cand :
						if not cor in list1:
							list1.append(cor)	
	stats=get_all_logged_in_users()
	return render(request,'home_page.html',{'line1':'Probable branches which you can get with your rank (source:JEE Data:2012)','line2':'The branches which were preferred in year 2012 with your rank', 'list':list,'list1':list1,'branch':branch,'users':stats[0],'avg':stats[1],'std':stats[2]})	


def rank_based(request):
	return render(request,'rank_based.html')

def rank_based_College_calc(request):

	college=request.GET['college']
	category=request.GET['category']
	rank=request.GET['rank']
	if rank =='':
		return HttpResponseRedirect('/rank_based/')		
	if college=="none":
		return HttpResponseRedirect('/rank_based/')
	#rank=request.POST['rank']
	list=[]
	list1=[]

	#l1=[C.rankGE,C.rankOBC,C.rankSC,C.rankST,C.rankGEPD,C.rankOBCPD,C.rankSCPD,C.rankSTPD]
	clist=[]
	if college=="IIT Bhubaneswar":
		clist = course.objects.filter(code__icontains='A')
	elif college=="IIT Bombay":
		clist = course.objects.filter(code__icontains='B')
	elif college=="IIT Delhi":
		clist = course.objects.filter(code__icontains='D')
	elif college=="IIT Gandhinagar":
		clist = course.objects.filter(code__icontains='N')
	elif college=="IIT Guwahati":
		clist = course.objects.filter(code__icontains='W')
	elif college=="IIT Hyderabad":
		clist = course.objects.filter(code__icontains='H')
	elif college=="IIT Indore":
		clist = course.objects.filter(code__icontains='E')
	elif college=="IIT Kanpur":
		clist = course.objects.filter(code__icontains='K')
	elif college=="IIT Kharagpur":
		clist = course.objects.filter(code__icontains='G')
	elif college=="IIT Madras":
		clist = course.objects.filter(code__icontains='M')
	elif college=="IIT Mandi":
		clist = course.objects.filter(code__icontains='C')
	elif college=="IIT Patna":
		clist = course.objects.filter(code__icontains='P')
	elif college=="IIT Rajasthan":
		clist = course.objects.filter(code__icontains='J')
	elif college=="IIT Roorkee":
		clist = course.objects.filter(code__icontains='R')
	elif college=="IIT Ropar":
		clist = course.objects.filter(code__icontains='U')
	elif college=="ISM Dhanbad":
		clist = course.objects.filter(code__icontains='S')
	elif college=="IT BHU":
		clist = course.objects.filter(code__icontains='V')

#	return render(request,'rank_based.html',{'line1':'Probable branches which you can get with your rank (source:JEE Data:2012)','line2':'The branches which were preferred in year 2012 with your rank', 'list':clist,'list1':list1,'BH':'selected','college':college,})	

	for cor in clist:
		if category=="GEN":
			calo=cor.go
			calc=cor.gc
		elif category=="OBC":
			calo=cor.oo
			calc=cor.oc
		elif category=="SC":
			calo=cor.sco
			calc=cor.scc
		elif category=="ST":
			calo=cor.sto
			calc=cor.stc
		elif category=="GEN-PD":
			calo=cor.gpo
			calc=cor.gpc
		elif category=="OBC-PD":
			calo=cor.opo
			calc=cor.opc
		elif category=="SC-PD":
			calo=cor.scpo
			calc=cor.scpc
		elif category=="ST-PD":
			calo=cor.stpo
			calc=cor.stpc

#		l2=[cor.go,cor.gc,cor.oo,cor.oc,cor.sco,cor.scc,cor.sto,cor.stc,cor.gpo,cor.gpc,cor.opo,cor.opc,cor.scpo,cor.scpc,cor.stpo,cor.stpc]
#		for i in range(8):
		cand=int(rank)
		colo=int(calo)
		colc=int(calc) 
		if colc >= cand and cand != 0 :
			if not cor in list:
				list.append(cor)
			if colo <= cand :
				if not cor in list1:
					list1.append(cor)	
	stats=get_all_logged_in_users()
	return render(request,'rank_based.html',{'line1':'Probable branches which you can get with your rank (source:JEE Data:2012)','line2':'The branches which were preferred in year 2012 with your rank', 'list':list,'list1':list1,'college':college,'users':stats[0],'avg':stats[1],'std':stats[2]})	
	
def rank_based_Branch_calc(request):

	branch=request.GET['branch']
	category=request.GET['category']
	rank=request.GET['rank']
	if rank =='':
		return HttpResponseRedirect('/rank_based/')		
	if branch=="none":
		return HttpResponseRedirect('/rank_based/')
	#rank=request.POST['rank']
	list=[]
	list1=[]

	#l1=[C.rankGE,C.rankOBC,C.rankSC,C.rankST,C.rankGEPD,C.rankOBCPD,C.rankSCPD,C.rankSTPD]
	clist=[]
	if branch=='Aerospace Engineering':
		clist = course.objects.filter(branch_name__icontains='Aerospace Engineering')
	elif branch=='Agricultural and Food Engineering':
		clist = course.objects.filter(branch_name__icontains='Agricultural and Food Engineering')
	elif branch=='Applied Geology':
		clist = course.objects.filter(branch_name__icontains='Applied Geology')
	elif branch=='Applied Geophysics':
		clist = course.objects.filter(branch_name__icontains='Applied Geophysics')
	elif branch=='Applied Mathematics':
		clist = course.objects.filter(branch_name__icontains='Applied Mathematics')
	elif branch=='Architecture':
		clist = course.objects.filter(branch_name='Architecture')
	elif branch=='Biochemical Engineering':
		clist = course.objects.filter(branch_name__icontains='Biochemical Engineering')
	elif branch=='Bioengineering':
		clist = course.objects.filter(branch_name__icontains='Bioengineering')
	elif branch=='Biological Engineering':
		clist = course.objects.filter(branch_name__icontains='Biological Engineering')
	elif branch=='Biological Sciences':
		clist = course.objects.filter(branch_name__icontains='Biological Sciences')
	elif branch=='Biotechnology':
		clist = course.objects.filter(branch_name__icontains='Biotechnology')
	elif branch=='Ceramic Engineering':
		clist = course.objects.filter(branch_name__icontains='Ceramic Engineering')
	elif branch=='Chemical Engineering':
		clist = course.objects.filter(branch_name__icontains='Chemical Engineering')
	elif branch=='Chemical Science and Technology':
		clist = course.objects.filter(branch_name__icontains='Chemical Science and Technology')
	elif branch=='Chemistry':
		clist = course.objects.filter(branch_name__icontains='Chemistry')
	elif branch=='Civil Engineering':
		clist = course.objects.filter(branch_name__icontains='Civil Engineering')
	elif branch=='Computer Science and Engineering':
		clist = course.objects.filter(branch_name__icontains='Computer Science and Engineering')
	elif branch=='Design':
		clist = course.objects.filter(branch_name__icontains='Design')
	elif branch=='Economics':
		clist = course.objects.filter(branch_name__icontains='Economics')
	elif branch=='Electrical Engineering':
		clist = course.objects.filter(branch_name__icontains='Electrical Engineering')
	elif branch=='Electronics and Communication Engin':
		clist = course.objects.filter(branch_name__icontains='Electronics and Communication Engin')
	elif branch=='Electronics and Electrical Communic':
		clist = course.objects.filter(branch_name__icontains='Electronics and Electrical Communic')
	elif branch=='Electronics and Electrical Engineer':
		clist = course.objects.filter(branch_name__icontains='Electronics and Electrical Engineer')
	elif branch=='Electronics Engineering':
		clist = course.objects.filter(branch_name__icontains='Electronics Engineering')
	elif branch=='Energy Engineering':
		clist = course.objects.filter(branch_name__icontains='Energy Engineering')
	elif branch=='Engineering Design':
		clist = course.objects.filter(branch_name__icontains='Engineering Design')
	elif branch=='Engineering Physics':
		clist = course.objects.filter(branch_name__icontains='Engineering Physics')
	elif branch=='Engineering Science':
		clist = course.objects.filter(branch_name__icontains='Engineering Science')
	elif branch=='Environmental Engineering':
		clist = course.objects.filter(branch_name__icontains='Environmental Engineering')
	elif branch=='Exploration Geophysics':
		clist = course.objects.filter(branch_name__icontains='Exploration Geophysics')
	elif branch=='Geological Technology':
		clist = course.objects.filter(branch_name__icontains='Geological Technology')
	elif branch=='Geophysical Technology':
		clist = course.objects.filter(branch_name__icontains='Geophysical Technology')
	elif branch=='Industrial Chemistry':
		clist = course.objects.filter(branch_name__icontains='Industrial Chemistry')
	elif branch=='Industrial Engineering':
		clist = course.objects.filter(branch_name__icontains='Industrial Engineering')
	elif branch=='Instrumentation Engineering':
		clist = course.objects.filter(branch_name__icontains='Instrumentation Engineering')
	elif branch=='Manufacturing Science and Engineeri':
		clist = course.objects.filter(branch_name__icontains='Manufacturing Science and Engineeri')
	elif branch=='Material Science and Technology':
		clist = course.objects.filter(branch_name__icontains='Material Science and Technology')
	elif branch=='Materials Science and Engineering':
		clist = course.objects.filter(branch_name__icontains='Materials Science and Engineering')
	elif branch=='Mathematics and Computing':
		clist = course.objects.filter(branch_name__icontains='Mathematics and Computing')
	elif branch=='Mathematics and Scientific Computin':
		clist = course.objects.filter(branch_name__icontains='Mathematics and Scientific Computin')
	elif branch=='Mechanical Engineering':
		clist = course.objects.filter(branch_name__icontains='Mechanical Engineering')
	elif branch=='Metallurgical and Materials Enginee':
		clist = course.objects.filter(branch_name__icontains='Metallurgical and Materials Enginee')
	elif branch=='Metallurgical Engineering':
		clist = course.objects.filter(branch_name__icontains='Metallurgical Engineering')
	elif branch=='Metallurgical Engineering and Mater':
		clist = course.objects.filter(branch_name__icontains='Metallurgical Engineering and Mater')
	elif branch=='Mineral Engineering':
		clist = course.objects.filter(branch_name__icontains='Mineral Engineering')
	elif branch=='Mining Engineering':
		clist = course.objects.filter(branch_name__icontains='Mining Engineering')
	elif branch=='Mining Machinery Engineering':
		clist = course.objects.filter(branch_name__icontains='Mining Machinery Engineering')
	elif branch=='Mining Safety Engineering':
		clist = course.objects.filter(branch_name__icontains='Mining Safety Engineering')
	elif branch=='Naval Architecture and Ocean Engine':
		clist = course.objects.filter(branch_name__icontains='Naval Architecture and Ocean Engine')
	elif branch=='Ocean Engineering and Naval Archite':
		clist = course.objects.filter(branch_name__icontains='Ocean Engineering and Naval Archite')
	elif branch=='Petroleum Engineering':
		clist = course.objects.filter(branch_name__icontains='Petroleum Engineering')
	elif branch=='Pharmaceutics':
		clist = course.objects.filter(branch_name__icontains='Pharmaceutics')
	elif branch=='Physics':
		clist = course.objects.filter(branch_name__icontains='Physics')
	elif branch=='Polymer Science and Technology':
		clist = course.objects.filter(branch_name__icontains='Polymer Science and Technology')
	elif branch=='Process Engineering with MBA':
		clist = course.objects.filter(branch_name__icontains='Process Engineering with MBA')
	elif branch=='Production and Industrial Engineeri':
		clist = course.objects.filter(branch_name__icontains='Production and Industrial Engineeri')
	elif branch=='Pulp and Paper Engineering':
		clist = course.objects.filter(branch_name__icontains='Pulp and Paper Engineering')
	elif branch=='Quality Engineering Design and Manu':
		clist = course.objects.filter(branch_name__icontains='Quality Engineering Design and Manu')
	elif branch=='Systems Science':
		clist = course.objects.filter(branch_name__icontains='Systems Science')
	elif branch=='Textile Technology':
		clist = course.objects.filter(branch_name__icontains='Textile Technology')

#	return render(request,'rank_based.html',{'line1':'Probable branches which you can get with your rank (source:JEE Data:2012)','line2':'The branches which were preferred in year 2012 with your rank', 'list':clist,'list1':list1,'BH':'selected','college':college,})	

	for cor in clist:
		if category=="GEN":
			calo=cor.go
			calc=cor.gc
		elif category=="OBC":
			calo=cor.oo
			calc=cor.oc
		elif category=="SC":
			calo=cor.sco
			calc=cor.scc
		elif category=="ST":
			calo=cor.sto
			calc=cor.stc
		elif category=="GEN-PD":
			calo=cor.gpo
			calc=cor.gpc
		elif category=="OBC-PD":
			calo=cor.opo
			calc=cor.opc
		elif category=="SC-PD":
			calo=cor.scpo
			calc=cor.scpc
		elif category=="ST-PD":
			calo=cor.stpo
			calc=cor.stpc

#		l2=[cor.go,cor.gc,cor.oo,cor.oc,cor.sco,cor.scc,cor.sto,cor.stc,cor.gpo,cor.gpc,cor.opo,cor.opc,cor.scpo,cor.scpc,cor.stpo,cor.stpc]
#		for i in range(8):
		cand=int(rank)
		colo=int(calo)
		colc=int(calc) 
		if colc >= cand and cand != 0 :
			if not cor in list:
				list.append(cor)
			if colo <= cand :
				if not cor in list1:
					list1.append(cor)	
	stats=get_all_logged_in_users()
	return render(request,'rank_based.html',{'line1':'Probable branches which you can get with your rank (source:JEE Data:2012)','line2':'The branches which were preferred in year 2012 with your rank', 'list':list,'list1':list1,'branch':branch,'users':stats[0],'avg':stats[1],'std':stats[2]})	

def get_all_logged_in_users():
    # Query all non-expired sessions
    sessions = Session.objects.filter(expire_date__gte=datetime.now())
    uid_list = []

    # Build a list of user ids from that query
    for session in sessions:
        data = session.get_decoded()
        uid_list.append(data.get('candidate_id', None))
    # Query all logged in users based on id list
    list_users= Candidate.objects.filter(user_name__in=uid_list)
    avg=0
    for user in list_users:
    	avg+=int(user.rankGE)
    avg=avg/len(list_users)
    std=0
    for user in list_users:
    	std+=(int(user.rankGE)-int(avg))**2
    std=math.sqrt(std/len(list_users))		
    std=round(decimal.Decimal(std),2)
    avg=round(decimal.Decimal(avg),2)
    return [list_users,avg,std]