import os
os.system('pdftotext programmeCode.pdf code.txt')
os.system('touch data_u-2012.csv')
file = open("code.txt", "r")
out = open('data_u-2012.csv',"w+")
oldline="\n"
oldline1=""
oldline2=""
while True:                            # Keep reading forever
	newline = file.readline()  
	 # Try to read next line
	if len(newline) == 0:              # If there are no more lines
		break                          #     leave the loop

	if (len(newline)==6 and newline[0] in ['A','B','D','N','W','H','E','K','G','M','C','P','J','R','U','S','V'] and newline[1:5].isdigit()) :
		out.write(newline[0:5]+", ")				
		if(newline[0:5]=="M4109"):
			out.write("Civil Engineering"+", ")
		elif(newline[0:5]=="S4107"):
			out.write(oldline1[4:-1]+", ")			
		elif(len(oldline)!=1):
			if(oldline[0].isdigit()):
				out.write(oldline[4:-1]+" ")
				out.write(oldline1[0:-1]+", ")
			else:	
				out.write(oldline[0:-1]+" ")
				out.write(oldline1[0:-1]+", ")
		elif(oldline1[0].isdigit()):
			out.write(oldline1[4:-1]+", ")
		else:
			out.write(oldline1[0:-1]+", ")
				
		if newline[0]=='A':
			out.write("Indian Institute of Technology Bhubaneswar\n")
		if newline[0]=='B':
			out.write("Indian Institute of Technology Bombay\n")
		if newline[0]=='D':
			out.write("Indian Institute of Technology Delhi\n")
		if newline[0]=='N':
			out.write("Indian Institute of Technology Gandhinagar\n")
		if newline[0]=='W':
			out.write("Indian Institute of Technology Guwahati\n")
		if newline[0]=='H':
			out.write("Indian Institute of Technology Hyderabad\n")
		if newline[0]=='E':
			out.write("Indian Institute of Technology Indore\n")
		if newline[0]=='K':
			out.write("Indian Institute of Technology Kanpur\n")
		if newline[0]=='G':
			out.write("Indian Institute of Technology Kharagpur\n")
		if newline[0]=='M':
			out.write("Indian Institute of Technology Madras\n")
		if newline[0]=='C':
			out.write("Indian Institute of Technology Mandi\n")
		if newline[0]=='P':
			out.write("Indian Institute of Technology Patna\n")
		if newline[0]=='J':
			out.write("Indian Institute of Technology Rajasthan\n")
		if newline[0]=='R':
			out.write("Indian Institute of Technology Roorkee\n")
		if newline[0]=='U':
			out.write("Indian Institute of Technology Ropar\n")
		if newline[0]=='S':
			out.write("Indian School of Mines Dhanbad\n")
		if newline[0]=='V':
			out.write("Institute of Technology Banaras Hindu University Varanasi\n")
	
	elif len(oldline2)>7:
		if (oldline2[-6] in ['A','B','D','N','W','H','E','K','G','M','C','P','J','R','U','S','V'] and oldline2[-5:-1].isdigit()): 
			out.write(oldline2[-6:-1]+", ")
			out.write(oldline2[4:-7]+" ")
			out.write(newline[0:-1]+", ")
			if oldline2[-6]=='A':
				out.write("Indian Institute of Technology Bhubaneswar\n")
			if oldline2[-6]=='B':
				out.write("Indian Institute of Technology Bombay\n")
			if oldline2[-6]=='D':
				out.write("Indian Institute of Technology Delhi\n")
			if oldline2[-6]=='N':
				out.write("Indian Institute of Technology Gandhinagar\n")
			if oldline2[-6]=='W':
				out.write("Indian Institute of Technology Guwahati\n")
			if oldline2[-6]=='H':
				out.write("Indian Institute of Technology Hyderabad\n")
			if oldline2[-6]=='E':
				out.write("Indian Institute of Technology Indore\n")
			if oldline2[-6]=='K':
				out.write("Indian Institute of Technology Kanpur\n")
			if oldline2[-6]=='G':
				out.write("Indian Institute of Technology Kharagpur\n")
			if oldline2[-6]=='M':
				out.write("Indian Institute of Technology Madras\n")
			if oldline2[-6]=='C':
				out.write("Indian Institute of Technology Mandi\n")
			if oldline2[-6]=='P':
				out.write("Indian Institute of Technology Patna\n")
			if oldline2[-6]=='J':
				out.write("Indian Institute of Technology Rajasthan\n")
			if oldline2[-6]=='R':
				out.write("Indian Institute of Technology Roorkee\n")
			if oldline2[-6]=='U':
				out.write("Indian Institute of Technology Ropar\n")
			if oldline2[-6]=='S':
				out.write("Indian School of Mines Dhanbad\n")
			if oldline2[-6]=='V':
				out.write("Institute of Technology Banaras Hindu University Varanasi\n")	
			
	
	oldline=oldline1
	oldline1=oldline2
	oldline2=newline
	
file.close()
out.close()
os.system("rm code.txt")

