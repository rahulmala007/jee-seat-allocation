from django.db import models

class Candidate(models.Model):

	user_name=models.CharField(max_length=20,default="user")
	password=models.CharField(max_length=15,default="user")

	question=models.CharField(max_length=100,default="What_is_Your_Birth_Date?")
	answer=models.CharField(max_length=100,default="")

	roll_no=models.CharField(max_length=10,default="user")						
	category=models.CharField(max_length=3,default="user")
	pdStatus=models.BooleanField(default=False)

	preferences=models.CharField(max_length=1000,default="")
	def setpreferences(self,x):
		self.preferences = ",".join(x)

	def getpreferences(self):
		return (self.preferences).split(',')	

	rankGE=models.CharField(max_length=5,default="0")
	rankOBC=models.CharField(max_length=5,default="0")
	rankSC=models.CharField(max_length=5,default="0")
	rankST=models.CharField(max_length=5,default="0")
	rankGEPD=models.CharField(max_length=5,default="0")
	rankOBCPD=models.CharField(max_length=5,default="0")
	rankSCPD=models.CharField(max_length=5,default="0")
	rankSTPD=models.CharField(max_length=5,default="0")
	
	final_seat=models.CharField(max_length=10,default="0")
	dob=models.CharField(max_length=10,default="01-01-95")


	def __str__(self):
		return self.roll_no


class course(models.Model):

	code=models.CharField(max_length=5,default="")
	
	go=models.CharField(max_length=10,default="")
	gc=models.CharField(max_length=10,default="")
	oo=models.CharField(max_length=10,default="")
	oc=models.CharField(max_length=10,default="")
	sco=models.CharField(max_length=10,default="")
	scc=models.CharField(max_length=10,default="")
	sto=models.CharField(max_length=10,default="")
	stc=models.CharField(max_length=10,default="")
	gpo=models.CharField(max_length=10,default="")
	gpc=models.CharField(max_length=10,default="")
	opo=models.CharField(max_length=10,default="")
	opc=models.CharField(max_length=10,default="")
	scpo=models.CharField(max_length=10,default="")
	scpc=models.CharField(max_length=10,default="")
	stpo=models.CharField(max_length=10,default="")
	stpc=models.CharField(max_length=10,default="")

	branch_name=models.CharField(max_length=100,default="")
	college_name=models.CharField(max_length=100,default="")

	def __str__(self):
		return self.code
