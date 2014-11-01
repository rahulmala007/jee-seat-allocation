install:
	@tar -xf SeatAllocation_group29_project.tar.gz; cd SeatAllocation_group29_project;	cd lab10; javac Main.java; cd ../JavaDoc ;  javadoc -private Main.java; cd ../presentation ;pdflatex presentation.tex; bibtex presentation; pdflatex presentation.tex;	pdflatex presentation.tex; mv presentation.tex 1.tex; mv presentation.pdf 1.pdf;rm presentation.* ;mv 1.tex presentation.tex;mv 1.pdf presentation.pdf;	cd ../..; rm SeatAllocation_group29_project.tar.gz;
dist:
	@cd SeatAllocation_group29_project;cd lab10; rm *.class;cd ..;cd JavaDoc;rm *.html;rm package-list;rm script.js;rm *.css;cd ..;cd presentation;rm presentation.pdf;cd ../..;tar -zcvf SeatAllocation_group29_project.tar.gz SeatAllocation_group29_project;rm -r SeatAllocation_group29_project;
