JAVAC := javac
STRINGS := PBStrings
PACKAGE := pb
QUESTIONS := PBQuestions

define FILES
$(QUESTIONS).java \
$(STRINGS).java \
Distance.java \
Game.java \
Note.java \
PBServer.java \
Player.java \
Program.java \
Question.java
endef

all: $(STRINGS).java $(QUESTIONS).java
	$(JAVAC) -d . $(FILES)

clean:
	rm -rf *.class $(STRINGS).java $(QUESTIONS).java $(PACKAGE)/ *.jar final.txt

$(STRINGS).java: client/*
	utils/strings.py $(STRINGS) $(PACKAGE) > $(STRINGS).java

$(QUESTIONS).java: questions.txt
	utils/questions.py $(QUESTIONS) $(PACKAGE) > $(QUESTIONS).java

.PHONY: all clean
