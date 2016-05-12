.PHONY: run clean jar lib

JAVA_HOME=/usr/lib/jvm/default

TARGET=target

JAR_FILE=$(TARGET)/ironmaiden-standalone.jar

CLASS_NAME=ironmaiden.Native
CLASS_DIR=$(TARGET)/classes
CLASS_FILE=$(CLASS_DIR)/ironmaiden/Native.class
JAVA_FILE=src/java/ironmaiden/Native.java

LIB_DIR=lib
LIB_FILE=$(LIB_DIR)/libNative.so
C_HEADER_DIR=$(TARGET)/header
C_SOURCE=src/c/Native.c
C_HEADER=$(TARGET)/header/Native.h
INCLUDE_DIRS=$(shell find $(JAVA_HOME)/include -type d)
INCLUDE_ARGS=$(INCLUDE_DIRS:%=-I%) -I$(C_HEADER_DIR)


run: $(CLASS_FILE) $(LIB_FILE)
	lein run

jar: $(JAR_FILE)

$(JAR_FILE): $(CLASS_FILE)
	lein uberjar

$(CLASS_FILE): $(JAVA_FILE) $(CLASS_DIR)
	javac -d $(CLASS_DIR) src/java/ironmaiden/Native.java

$(C_HEADER): $(CLASS_FILE)
	mkdir -p $(C_HEADER_DIR)
	javah -o $(C_HEADER) -cp $(CLASS_DIR) $(CLASS_NAME)

$(LIB_FILE): $(C_SOURCE) $(C_HEADER) $(LIB_DIR)
	mkdir -p $(LIB_DIR)
	$(CC) $(C_SOURCE) $(INCLUDE_ARGS) -shared -o $(LIB_FILE) -fPIC

$(CLASS_DIR):
	mkdir -p $(CLASS_DIR)

$(LIB_DIR):
	mkdir $(LIB_DIR)

clean:
	lein clean
	rm -rf $(TARGET) $(LIB_DIR)
