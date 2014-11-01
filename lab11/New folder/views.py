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
			Candidate.objects.create(user_name=row[0],password=row[12],roll_no=row[0],rankGE=row[3],rankOBC=row[4],rankSC=row[5],rankST=row[6],rankGEPD=row[8],rankOBCPD=row[9],rankSCPD=row[10],rankSTPD=row[11],dob=row[12]) 
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
		course.objects.create(code=row[0],go=row[1],gc=row[2],oo=row[3],oc=row[4],sco=row[5],scc=row[6],sto=row[7],stc=row[8],gpo=row[9],gpc=row[10],opo=row[11],opc=row[12],scpo=row[13],scpc=row[14],stpo=row[15],stpc=row[16],branch_name=row[18],college_name=row[17]) 
		rownum+=1

	file.close()

	message=rownum
	return (HttpResponse(message))

def showrl(request):
	p=Candidate.objects.all()
	display=""
	for cand in p:
		display += cand.user_name + "  " + cand.password +"  " +cand.preferences +"\n"
	return (HttpResponse(display))

def showcl(request):
	p=course.objects.all()
	display=""
	for cand in p:
		display += cand.code + "\n"
	return (HttpResponse(display))


def login(request):

	#try:
		if request.session.get('candidate_id',''):
			return HttpResponseRedirect("/homepage/")
			
		if not 'userID' in request.POST:
			return render(request,"login_page.html")
		if not 'passwd' in request.POST:
			return render(request,"login_page.html")

		
		user=request.POST['userID']
		passwd=request.POST['passwd']
			
		if 1:#Candidate.objects.filter(user_name=user):
			#C=Candidate.objects.get(user_name=user)
			#rank=C.rankGE
			if 1:#passwd==C.password:
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
	#except Exception as e:
	#	return 	render(request,'test.html',{'c':e})			


def logout(request):
	try:
		del request.session['candidate_id']
		return HttpResponseRedirect("/login/")
	except:
		return HttpResponseRedirect("/login/")

def homepage(request):
	#try:
		user=request.session['candidate_id']
		#C=Candidate.objects.get(user_name=user)
		#rank=C.rankGE	
		stats=get_all_logged_in_users()
		return render(request,'home_page.html',{'user_name':'siddharth','users':stats[0],'avg':stats[1],'std':stats[2]})
	#except Exception as e:
	#	return HttpResponseRedirect('/login/')

def updateit(request):
	try:	
		user=request.session['candidate_id']
		C=Candidate.objects.get(user_name=user)
		C.user_name=request.POST['name']
		C.password=request.POST['password']
		C.question=request.POST['question']
		C.answer=request.POST['answer']
		d=request.POST['day']
		m=request.POST['month']
		y=request.POST['year']
		if int(d)>31 or int(d) <1 or int(m)>12 or int(d) <1 or int(y)>99 or int(d) <0 :
			return HttpResponseRedirect("/updateProfile/")
		C.dob=d+'-'+'m'+'y'
		C.save()
		request.session['candidate_id']=C.user_name
		return HttpResponseRedirect("/homepage/")
	except:
		return HttpResponseRedirect("/updateProfile/")
def updateProfile(request):

	user=request.session['candidate_id']
	C=Candidate.objects.get(user_name=user)
	return render(request,"updateProfile.html",{'name':C.user_name,'password':C.password,'question':C.question,'answer':C.answer,'dob':C.dob})					

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
	return render(request,'home_page.html',{'user_name':C.user_name,'line1':'Probable branches which you can get with your rank (source:JEE Data:2012)','line2':'The branches which were preferred in year 2012 with your rank', 'list':list,'list1':list1,'college':college,'users':stats[0],'avg':stats[1],'std':stats[2]})	

def CourseBra(request):
	user=request.session['candidate_id']
	C=Candidate.objects.get(user_name=user)

	p1=request.GET['branch']
	if(p1=="none"):
		return HttpResponseRedirect('/homepage/')

	list=[]
	list1=[]

	l1=[C.rankGE,C.rankOBC,C.rankSC,C.rankST,C.rankGEPD,C.rankOBCPD,C.rankSCPD,C.rankSTPD]
	clist=[]

	if p1=="p3":
		clist = course.objects.filter(branch_name='Aerospace Engineering')
	elif p1=="p4":
		clist = course.objects.filter(branch_name='Aerospace Engineering with M.Tech. in Applied Mechanics with specialization in Biomedical Engineering')
	elif p1=="p5":
		clist = course.objects.filter(branch_name='Agricultural and Food Engineering')
	elif p1=="p6":
		clist = course.objects.filter(branch_name='Agricultural and Food Engineering with M.Tech. in any of the listed specializations')
	elif p1=="p7":
		clist = course.objects.filter(branch_name='Applied Geology')
	elif p1=="p8":
		clist = course.objects.filter(branch_name='Applied Geophysics')
	elif p1=="p9":
		clist = course.objects.filter(branch_name='Applied Mathematics')
	elif p1=="p10":
		clist = course.objects.filter(branch_name='Architecture')
	elif p1=="p11":
		clist = course.objects.filter(branch_name='Biochemical Engineering')
	elif p1=="p12":
		clist = course.objects.filter(branch_name='Biochemical Engineering and Biotechnology')
	elif p1=="p13":
		clist = course.objects.filter(branch_name='Bioengineering with M.Tech in Biomedical Technology')
	elif p1=="p14":
		clist = course.objects.filter(branch_name='Biological Engineering')
	elif p1=="p15":
		clist = course.objects.filter(branch_name='Biological Sciences')
	elif p1=="p16":
		clist = course.objects.filter(branch_name='Biological Sciences and Bioengineering')
	elif p1=="p17":
		clist = course.objects.filter(branch_name='Biotechnology')
	elif p1=="p18":
		clist = course.objects.filter(branch_name='Biotechnology and Biochemical Engineering')
	elif p1=="p19":
		clist = course.objects.filter(branch_name='Ceramic Engineering')
	elif p1=="p20":
		clist = course.objects.filter(branch_name='Chemical Engineering')
	elif p1=="p21":
		clist = course.objects.filter(branch_name='Chemical Engineering with M.Tech. in Hydrocarbon Engineering')
	elif p1=="p22":
		clist = course.objects.filter(branch_name='Chemical Science and Technology')
	elif p1=="p23":
		clist = course.objects.filter(branch_name='Chemistry')
	elif p1=="p24":
		clist = course.objects.filter(branch_name='Civil Engineering')
	elif p1=="p25":
		clist = course.objects.filter(branch_name='Civil Engineering (Infrastructural Civil Engineering)')
	elif p1=="p26":
		clist = course.objects.filter(branch_name='Civil Engineering with any of the listed specialization')
	elif p1=="p27":
		clist = course.objects.filter(branch_name='Civil Engineering with M.Tech. in Applied Mechanics in any of the listed specializations')
	elif p1=="p28":
		clist = course.objects.filter(branch_name='Civil Engineering with M.Tech. in Structural Engineering')
	elif p1=="p29":
		clist = course.objects.filter(branch_name='Computer Science and Engineering')
	elif p1=="p30":
		clist = course.objects.filter(branch_name='Design')
	elif p1=="p31":
		clist = course.objects.filter(branch_name='Economics')
	elif p1=="p32":
		clist = course.objects.filter(branch_name='Electrical Engineering')
	elif p1=="p33":
		clist = course.objects.filter(branch_name='Electrical Engineering (Power)')
	elif p1=="p34":
		clist = course.objects.filter(branch_name='Electrical Engineering with M.Tech in Applied Mechanics with specialization in Biomedical Engineering')
	elif p1=="p35":
		clist = course.objects.filter(branch_name='Electrical Engineering with M.Tech. in any of the listed specializations')
	elif p1=="p36":
		clist = course.objects.filter(branch_name='Electrical Engineering with M.Tech. in Communications and Signal Processing')
	elif p1=="p37":
		clist = course.objects.filter(branch_name='Electrical Engineering with M.Tech. in Information and Communication Technology')
	elif p1=="p38":
		clist = course.objects.filter(branch_name='Electrical Engineering with M.Tech. in Microelectronics')
	elif p1=="p39":
		clist = course.objects.filter(branch_name='Electrical Engineering with M.Tech. in Power Electronics')
	elif p1=="p40":
		clist = course.objects.filter(branch_name='Electronics and Communication Engineering')
	elif p1=="p41":
		clist = course.objects.filter(branch_name='Electronics and Communication Engineering with M.Tech. in Wireless Communication')
	elif p1=="p42":
		clist = course.objects.filter(branch_name='Electronics and Electrical Communication Engineering')
	elif p1=="p43":
		clist = course.objects.filter(branch_name='Electronics and Electrical Communication Engineering with M.Tech. in any of the listed specializations')
	elif p1=="p44":
		clist = course.objects.filter(branch_name='Electronics and Electrical Engineering')
	elif p1=="p45":
		clist = course.objects.filter(branch_name='Electronics Engineering')
	elif p1=="p46":
		clist = course.objects.filter(branch_name='Energy Engineering with M.Tech. in Energy Systems Engineering')
	elif p1=="p47":
		clist = course.objects.filter(branch_name='Engineering Design (Automotive Engineering)')
	elif p1=="p48":
		clist = course.objects.filter(branch_name='Engineering Design (Biomedical Design)')
	elif p1=="p49":
		clist = course.objects.filter(branch_name='Engineering Physics')																							
	elif p1=="p50":
		clist = course.objects.filter(branch_name='Engineering Physics with M.Tech. in Engineering Physics with specialization in Nano Science')	
	elif p1=="p51":
		clist = course.objects.filter(branch_name='Engineering Science')
	elif p1=="p52":
		clist = course.objects.filter(branch_name='Environmental Engineering')
	elif p1=="p53":
		clist = course.objects.filter(branch_name='Exploration Geophysics')
	elif p1=="p54":
		clist = course.objects.filter(branch_name='Geological Technology')
	elif p1=="p55":
		clist = course.objects.filter(branch_name='Geophysical Technology')
	elif p1=="p56":
		clist = course.objects.filter(branch_name='Industrial Chemistry')
	elif p1=="p57":
		clist = course.objects.filter(branch_name='Industrial Engineering')
	elif p1=="p58":
		clist = course.objects.filter(branch_name='Industrial Engineering with M.Tech. in Industrial Engineering and Management')
	elif p1=="p59":
		clist = course.objects.filter(branch_name='Instrumentation Engineering')
	elif p1=="p60":
		clist = course.objects.filter(branch_name='Manufacturing Science and Engineering')
	elif p1=="p61":
		clist = course.objects.filter(branch_name='Manufacturing Science and Engineering with M.Tech. in Industrial Engineering and Management')
	elif p1=="p62":
		clist = course.objects.filter(branch_name='Material Science and Technology')
	elif p1=="p63":
		clist = course.objects.filter(branch_name='Materials Science and Engineering')
	elif p1=="p64":
		clist = course.objects.filter(branch_name='Mathematics and Computing')
	elif p1=="p65":
		clist = course.objects.filter(branch_name='Mathematics and Scientific Computing')
	elif p1=="p66":
		clist = course.objects.filter(branch_name='Mechanical Engineering')
	elif p1=="p67":
		clist = course.objects.filter(branch_name='Mechanical Engineering (Intelligent Manufacturing)')
	elif p1=="p68":
		clist = course.objects.filter(branch_name='Mechanical Engineering (Product design)')
	elif p1=="p69":
		clist = course.objects.filter(branch_name='Mechanical Engineering (Thermal Engineering)')
	elif p1=="p70":
		clist = course.objects.filter(branch_name='Mechanical Engineering with M.Tech. in any of the listed specializations')
	elif p1=="p71":
		clist = course.objects.filter(branch_name='Mechanical Engineering with M.Tech. in Computer Aided Design and Automation')
	elif p1=="p72":
		clist = course.objects.filter(branch_name='Mechanical Engineering with M.Tech. in Computer Integrated Manufacturing')
	elif p1=="p73":
		clist = course.objects.filter(branch_name='Metallurgical and Materials Engineering')
	elif p1=="p74":
		clist = course.objects.filter(branch_name='Metallurgical and Materials Engineering with M.Tech. in Materials Engineering')
	elif p1=="p75":
		clist = course.objects.filter(branch_name='Metallurgical and Materials Engineering with M.Tech. in Metallurgical and Materials Engineering')
	elif p1=="p76":
		clist = course.objects.filter(branch_name='Metallurgical Engineering')
	elif p1=="p77":
		clist = course.objects.filter(branch_name='Metallurgical Engineering and Materials Science')
	elif p1=="p78":
		clist = course.objects.filter(branch_name='Metallurgical Engineering and Materials Science with M.Tech. in Ceramics and Composites')
	elif p1=="p79":
		clist = course.objects.filter(branch_name='Metallurgical Engineering and Materials Science with M.Tech. in Metallurgical Process Engineering')
	elif p1=="p80":
		clist = course.objects.filter(branch_name='Mineral Engineering')
	elif p1=="p81":
		clist = course.objects.filter(branch_name='Mineral Engineering with M.Tech in Mineral Engineering')
	elif p1=="p82":
		clist = course.objects.filter(branch_name='Mineral Engineering with MBA')
	elif p1=="p83":
		clist = course.objects.filter(branch_name='Mining Engineering')
	elif p1=="p84":
		clist = course.objects.filter(branch_name='Mining Engineering with M.Tech. in Mining Engineering')
	elif p1=="p85":
		clist = course.objects.filter(branch_name='Mining Engineering with MBA')
	elif p1=="p86":
		clist = course.objects.filter(branch_name='Mining Machinery Engineering')
	elif p1=="p87":
		clist = course.objects.filter(branch_name='Mining Safety Engineering')
	elif p1=="p88":
		clist = course.objects.filter(branch_name='Naval Architecture and Ocean Engineering')
	elif p1=="p89":
		clist = course.objects.filter(branch_name='Naval Architecture and Ocean Engineering with M.Tech in Applied Mechanics in any of the listed specializations')
	elif p1=="p90":
		clist = course.objects.filter(branch_name='Ocean Engineering and Naval Architecture')
	elif p1=="p91":
		clist = course.objects.filter(branch_name='Petroleum Engineering')
	elif p1=="p92":
		clist = course.objects.filter(branch_name='Petroleum Engineering with M.Tech in Petroleum Management')
	elif p1=="p93":
		clist = course.objects.filter(branch_name='Pharmaceutics')
	elif p1=="p94":
		clist = course.objects.filter(branch_name='Physics')
	elif p1=="p95":
		clist = course.objects.filter(branch_name='Polymer Science and Technology')
	elif p1=="p96":
		clist = course.objects.filter(branch_name='Process Engineering with MBA')
	elif p1=="p97":
		clist = course.objects.filter(branch_name='Production and Industrial Engineering')
	elif p1=="p98":
		clist = course.objects.filter(branch_name='Pulp and Paper Engineering')
	elif p1=="p99":
		clist = course.objects.filter(branch_name='Quality Engineering Design and Manufacturing')
	elif p1=="p100":
		clist = course.objects.filter(branch_name='Systems Science')
	elif p1=="p101":
		clist = course.objects.filter(branch_name='Aerospace Engineering')																																																

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
	return render(request,'home_page.html',{'user_name':C.user_name,'line1':'Probable branches which you can get with your rank (source:JEE Data:2012)','line2':'The branches which were preferred in year 2012 with your rank', 'list':list,'list1':list1,'branch':cor.branch_name,'users':stats[0],'avg':stats[1],'std':stats[2]})	


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

	p1=request.GET['branch']
	category=request.GET['category']
	rank=request.GET['rank']
	if rank =='':
		return HttpResponseRedirect('/rank_based/')		
	if p1=="none":
		return HttpResponseRedirect('/rank_based/')
	#rank=request.POST['rank']
	list=[]
	list1=[]

	#l1=[C.rankGE,C.rankOBC,C.rankSC,C.rankST,C.rankGEPD,C.rankOBCPD,C.rankSCPD,C.rankSTPD]
	clist=[]
	if p1=="p3":
		clist = course.objects.filter(branch_name='Aerospace Engineering')
	elif p1=="p4":
		clist = course.objects.filter(branch_name='Aerospace Engineering with M.Tech. in Applied Mechanics with specialization in Biomedical Engineering')
	elif p1=="p5":
		clist = course.objects.filter(branch_name='Agricultural and Food Engineering')
	elif p1=="p6":
		clist = course.objects.filter(branch_name='Agricultural and Food Engineering with M.Tech. in any of the listed specializations')
	elif p1=="p7":
		clist = course.objects.filter(branch_name='Applied Geology')
	elif p1=="p8":
		clist = course.objects.filter(branch_name='Applied Geophysics')
	elif p1=="p9":
		clist = course.objects.filter(branch_name='Applied Mathematics')
	elif p1=="p10":
		clist = course.objects.filter(branch_name='Architecture')
	elif p1=="p11":
		clist = course.objects.filter(branch_name='Biochemical Engineering')
	elif p1=="p12":
		clist = course.objects.filter(branch_name='Biochemical Engineering and Biotechnology')
	elif p1=="p13":
		clist = course.objects.filter(branch_name='Bioengineering with M.Tech in Biomedical Technology')
	elif p1=="p14":
		clist = course.objects.filter(branch_name='Biological Engineering')
	elif p1=="p15":
		clist = course.objects.filter(branch_name='Biological Sciences')
	elif p1=="p16":
		clist = course.objects.filter(branch_name='Biological Sciences and Bioengineering')
	elif p1=="p17":
		clist = course.objects.filter(branch_name='Biotechnology')
	elif p1=="p18":
		clist = course.objects.filter(branch_name='Biotechnology and Biochemical Engineering')
	elif p1=="p19":
		clist = course.objects.filter(branch_name='Ceramic Engineering')
	elif p1=="p20":
		clist = course.objects.filter(branch_name='Chemical Engineering')
	elif p1=="p21":
		clist = course.objects.filter(branch_name='Chemical Engineering with M.Tech. in Hydrocarbon Engineering')
	elif p1=="p22":
		clist = course.objects.filter(branch_name='Chemical Science and Technology')
	elif p1=="p23":
		clist = course.objects.filter(branch_name='Chemistry')
	elif p1=="p24":
		clist = course.objects.filter(branch_name='Civil Engineering')
	elif p1=="p25":
		clist = course.objects.filter(branch_name='Civil Engineering (Infrastructural Civil Engineering)')
	elif p1=="p26":
		clist = course.objects.filter(branch_name='Civil Engineering with any of the listed specialization')
	elif p1=="p27":
		clist = course.objects.filter(branch_name='Civil Engineering with M.Tech. in Applied Mechanics in any of the listed specializations')
	elif p1=="p28":
		clist = course.objects.filter(branch_name='Civil Engineering with M.Tech. in Structural Engineering')
	elif p1=="p29":
		clist = course.objects.filter(branch_name='Computer Science and Engineering')
	elif p1=="p30":
		clist = course.objects.filter(branch_name='Design')
	elif p1=="p31":
		clist = course.objects.filter(branch_name='Economics')
	elif p1=="p32":
		clist = course.objects.filter(branch_name='Electrical Engineering')
	elif p1=="p33":
		clist = course.objects.filter(branch_name='Electrical Engineering (Power)')
	elif p1=="p34":
		clist = course.objects.filter(branch_name='Electrical Engineering with M.Tech in Applied Mechanics with specialization in Biomedical Engineering')
	elif p1=="p35":
		clist = course.objects.filter(branch_name='Electrical Engineering with M.Tech. in any of the listed specializations')
	elif p1=="p36":
		clist = course.objects.filter(branch_name='Electrical Engineering with M.Tech. in Communications and Signal Processing')
	elif p1=="p37":
		clist = course.objects.filter(branch_name='Electrical Engineering with M.Tech. in Information and Communication Technology')
	elif p1=="p38":
		clist = course.objects.filter(branch_name='Electrical Engineering with M.Tech. in Microelectronics')
	elif p1=="p39":
		clist = course.objects.filter(branch_name='Electrical Engineering with M.Tech. in Power Electronics')
	elif p1=="p40":
		clist = course.objects.filter(branch_name='Electronics and Communication Engineering')
	elif p1=="p41":
		clist = course.objects.filter(branch_name='Electronics and Communication Engineering with M.Tech. in Wireless Communication')
	elif p1=="p42":
		clist = course.objects.filter(branch_name='Electronics and Electrical Communication Engineering')
	elif p1=="p43":
		clist = course.objects.filter(branch_name='Electronics and Electrical Communication Engineering with M.Tech. in any of the listed specializations')
	elif p1=="p44":
		clist = course.objects.filter(branch_name='Electronics and Electrical Engineering')
	elif p1=="p45":
		clist = course.objects.filter(branch_name='Electronics Engineering')
	elif p1=="p46":
		clist = course.objects.filter(branch_name='Energy Engineering with M.Tech. in Energy Systems Engineering')
	elif p1=="p47":
		clist = course.objects.filter(branch_name='Engineering Design (Automotive Engineering)')
	elif p1=="p48":
		clist = course.objects.filter(branch_name='Engineering Design (Biomedical Design)')
	elif p1=="p49":
		clist = course.objects.filter(branch_name='Engineering Physics')																							
	elif p1=="p50":
		clist = course.objects.filter(branch_name='Engineering Physics with M.Tech. in Engineering Physics with specialization in Nano Science')	
	elif p1=="p51":
		clist = course.objects.filter(branch_name='Engineering Science')
	elif p1=="p52":
		clist = course.objects.filter(branch_name='Environmental Engineering')
	elif p1=="p53":
		clist = course.objects.filter(branch_name='Exploration Geophysics')
	elif p1=="p54":
		clist = course.objects.filter(branch_name='Geological Technology')
	elif p1=="p55":
		clist = course.objects.filter(branch_name='Geophysical Technology')
	elif p1=="p56":
		clist = course.objects.filter(branch_name='Industrial Chemistry')
	elif p1=="p57":
		clist = course.objects.filter(branch_name='Industrial Engineering')
	elif p1=="p58":
		clist = course.objects.filter(branch_name='Industrial Engineering with M.Tech. in Industrial Engineering and Management')
	elif p1=="p59":
		clist = course.objects.filter(branch_name='Instrumentation Engineering')
	elif p1=="p60":
		clist = course.objects.filter(branch_name='Manufacturing Science and Engineering')
	elif p1=="p61":
		clist = course.objects.filter(branch_name='Manufacturing Science and Engineering with M.Tech. in Industrial Engineering and Management')
	elif p1=="p62":
		clist = course.objects.filter(branch_name='Material Science and Technology')
	elif p1=="p63":
		clist = course.objects.filter(branch_name='Materials Science and Engineering')
	elif p1=="p64":
		clist = course.objects.filter(branch_name='Mathematics and Computing')
	elif p1=="p65":
		clist = course.objects.filter(branch_name='Mathematics and Scientific Computing')
	elif p1=="p66":
		clist = course.objects.filter(branch_name='Mechanical Engineering')
	elif p1=="p67":
		clist = course.objects.filter(branch_name='Mechanical Engineering (Intelligent Manufacturing)')
	elif p1=="p68":
		clist = course.objects.filter(branch_name='Mechanical Engineering (Product design)')
	elif p1=="p69":
		clist = course.objects.filter(branch_name='Mechanical Engineering (Thermal Engineering)')
	elif p1=="p70":
		clist = course.objects.filter(branch_name='Mechanical Engineering with M.Tech. in any of the listed specializations')
	elif p1=="p71":
		clist = course.objects.filter(branch_name='Mechanical Engineering with M.Tech. in Computer Aided Design and Automation')
	elif p1=="p72":
		clist = course.objects.filter(branch_name='Mechanical Engineering with M.Tech. in Computer Integrated Manufacturing')
	elif p1=="p73":
		clist = course.objects.filter(branch_name='Metallurgical and Materials Engineering')
	elif p1=="p74":
		clist = course.objects.filter(branch_name='Metallurgical and Materials Engineering with M.Tech. in Materials Engineering')
	elif p1=="p75":
		clist = course.objects.filter(branch_name='Metallurgical and Materials Engineering with M.Tech. in Metallurgical and Materials Engineering')
	elif p1=="p76":
		clist = course.objects.filter(branch_name='Metallurgical Engineering')
	elif p1=="p77":
		clist = course.objects.filter(branch_name='Metallurgical Engineering and Materials Science')
	elif p1=="p78":
		clist = course.objects.filter(branch_name='Metallurgical Engineering and Materials Science with M.Tech. in Ceramics and Composites')
	elif p1=="p79":
		clist = course.objects.filter(branch_name='Metallurgical Engineering and Materials Science with M.Tech. in Metallurgical Process Engineering')
	elif p1=="p80":
		clist = course.objects.filter(branch_name='Mineral Engineering')
	elif p1=="p81":
		clist = course.objects.filter(branch_name='Mineral Engineering with M.Tech in Mineral Engineering')
	elif p1=="p82":
		clist = course.objects.filter(branch_name='Mineral Engineering with MBA')
	elif p1=="p83":
		clist = course.objects.filter(branch_name='Mining Engineering')
	elif p1=="p84":
		clist = course.objects.filter(branch_name='Mining Engineering with M.Tech. in Mining Engineering')
	elif p1=="p85":
		clist = course.objects.filter(branch_name='Mining Engineering with MBA')
	elif p1=="p86":
		clist = course.objects.filter(branch_name='Mining Machinery Engineering')
	elif p1=="p87":
		clist = course.objects.filter(branch_name='Mining Safety Engineering')
	elif p1=="p88":
		clist = course.objects.filter(branch_name='Naval Architecture and Ocean Engineering')
	elif p1=="p89":
		clist = course.objects.filter(branch_name='Naval Architecture and Ocean Engineering with M.Tech in Applied Mechanics in any of the listed specializations')
	elif p1=="p90":
		clist = course.objects.filter(branch_name='Ocean Engineering and Naval Architecture')
	elif p1=="p91":
		clist = course.objects.filter(branch_name='Petroleum Engineering')
	elif p1=="p92":
		clist = course.objects.filter(branch_name='Petroleum Engineering with M.Tech in Petroleum Management')
	elif p1=="p93":
		clist = course.objects.filter(branch_name='Pharmaceutics')
	elif p1=="p94":
		clist = course.objects.filter(branch_name='Physics')
	elif p1=="p95":
		clist = course.objects.filter(branch_name='Polymer Science and Technology')
	elif p1=="p96":
		clist = course.objects.filter(branch_name='Process Engineering with MBA')
	elif p1=="p97":
		clist = course.objects.filter(branch_name='Production and Industrial Engineering')
	elif p1=="p98":
		clist = course.objects.filter(branch_name='Pulp and Paper Engineering')
	elif p1=="p99":
		clist = course.objects.filter(branch_name='Quality Engineering Design and Manufacturing')
	elif p1=="p100":
		clist = course.objects.filter(branch_name='Systems Science')
	elif p1=="p101":
		clist = course.objects.filter(branch_name='Aerospace Engineering')																																																		

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
	return render(request,'rank_based.html',{'line1':'Probable branches which you can get with your rank (source:JEE Data:2012)','line2':'The branches which were preferred in year 2012 with your rank', 'list':list,'list1':list1,'branch':cor.branch_name,'users':stats[0],'avg':stats[1],'std':stats[2]})	

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


def givepref(request):
		
	college=request.GET['college']
	
	user=request.session['candidate_id']

	C=Candidate.objects.get(user_name=user)

	college=request.GET['college']
	if(college=="none"):
		return HttpResponseRedirect('/BranchPref/')

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

	pre=[]
	if C.preferences :
		for x in C.getpreferences():
			pr=course.objects.get(code=x)
			pre.append(pr)

	stats=get_all_logged_in_users()
	return render(request,'BranchPref.html',{'user_name':C.user_name,'clist':clist,'users':stats[0],'avg':stats[1],'std':stats[2],'currentpre':pre})	
  
def updatebranches(request):
	user=request.session['candidate_id']  
	C=Candidate.objects.get(user_name=user)
	 
	preferences=request.POST["branches"]
	if C.preferences:
		z=C.getpreferences()
		if preferences not in z:
			z.append(preferences)
	else:
		z=preferences
		C.preferences=preferences
		C.save()
		return HttpResponseRedirect('/updateProfile/')
	C.setpreferences(z)
	C.save()
	y=C.preferences
	return HttpResponseRedirect('/updateProfile/')

def deletebranches(request):
	user=request.session['candidate_id']  
	C=Candidate.objects.get(user_name=user)

	preferences=request.POST.getlist('branches')
	z=C.getpreferences()
	for i in preferences:
		if i in z:
			z.remove(i)

	C.setpreferences(z)
	C.save()
	y=C.preferences
	return HttpResponseRedirect('/updateProfile/')
