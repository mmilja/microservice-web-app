FROM mmilja/webapp:os-base-0.0.1

ARG MAVEN_VERSION=3.6.1
ARG MAVEN_URI=https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries
ARG USER_HOME_DIR="/root"

RUN mkdir -p /usr/lib/jvm

WORKDIR /usr/lib/jvm

RUN yum install -y curl \
  tar \
  gzip \
  wget \
  which \
  procps \
  lsof \
  && yum clean all -y

RUN curl -s https://download.java.net/java/GA/jdk12.0.1/69cfe15208a647278a19ef0990eea691/12/GPL/openjdk-12.0.1_linux-x64_bin.tar.gz -o openjdk-12.0.1_linux-x64_bin.tar.gz && \
    tar -xvzf openjdk-12.0.1_linux-x64_bin.tar.gz && \
	rm -f openjdk-12.0.1_linux-x64_bin.tar.gz

ENV JAVA_HOME /usr/lib/jvm/jdk-12.0.1
ENV PATH="${JAVA_HOME}/bin:${PATH}"

RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
  && curl -fsSL -o /tmp/apache-maven.tar.gz ${MAVEN_URI}/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
  && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
  && rm -f /tmp/apache-maven.tar.gz \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "${USER_HOME_DIR}/.m2"

RUN mkdir -p "${USER_HOME_DIR}/.m2" \
  mkdir -p /usr/src/app

WORKDIR /usr/src/app