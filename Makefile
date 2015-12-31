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


run: $(LIB_FILE) $(JAR_FILE)
	lein run

jar: $(JAR_FILE)

$(JAR_FILE): $(CLASS_FILE) lib
	lein uberjar

$(CLASS_FILE): $(JAVA_FILE)
	lein javac

$(C_HEADER): $(CLASS_FILE)
	mkdir -p $(C_HEADER_DIR)
	javah -o $(C_HEADER) -cp $(CLASS_DIR) $(CLASS_NAME)

lib: $(LIB_FILE)

$(LIB_FILE): $(C_SOURCE) $(C_HEADER)
	mkdir -p $(LIB_DIR)
	$(CC) $(C_SOURCE) $(INCLUDE_ARGS) -shared -o $(LIB_FILE) -fPIC

clean:
	lein clean
	rm -rf $(TARGET) $(LIB_DIR)
