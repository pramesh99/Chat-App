JAVASRC = *.java
SOURCES = README Makefile $(JAVASRC)
MAINCLASS = Server
CLASSES = *.class
JARFILE = finalv3.jar

all: $(JARFILE)

$(JARFILE): $(CLASSES)
	echo Main-class: $(MAINCLASS) > Manifest
	jar cvfm $(JARFILE) Manifest $(CLASSES)
	rm Manifest

$(CLASSES): $(JAVASRC)
	javac -Xlint $(JAVASRC)

clean:
	rm $(CLASSES) $(JARFILE)