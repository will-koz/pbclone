JAVAC := javac
STRINGS := PBStrings
PACKAGE := pb

define FILES
$(STRINGS).java \
Distance.java \
Game.java \
PBServer.java \
Player.java \
Program.java \
Question.java
endef

all: $(STRINGS).java
	$(JAVAC) -d . $(FILES)

clean:
	rm -rf *.class $(STRINGS).java $(PACKAGE)/

$(STRINGS).java: client/*
	utils/strings.py $(STRINGS) $(PACKAGE) > $(STRINGS).java

.PHONY: all clean
