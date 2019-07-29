FROM mmilja/webapp:java-base-0.0.1 as java-builder

COPY pom.xml /usr/src/app/
COPY mvn-resources /usr/src/app/mvn-resources
COPY source/back-end/src /usr/src/app/source/back-end/src
COPY source/back-end/pom.xml /usr/src/app/source/back-end/pom.xml

WORKDIR /usr/src/app/source/back-end/

RUN mvn clean install

FROM mmilja/webapp:java-base-0.0.1

RUN mkdir /images

COPY --from=java-builder /usr/src/app/source/back-end/target/dependency/*.jar /usr/src/app/dependency/
COPY --from=java-builder /usr/src/app/source/back-end/target/server-*.jar /usr/src/app/
COPY --from=java-builder /usr/src/app/source/back-end/src/main/resources/images/* /images/

#CMD java -cp /usr/src/app/*:/usr/src/app/dependency/*.jar webapp.backend.server.Application
CMD java -jar /usr/src/app/*.jar

#Labeling the container
ARG PRODUCT_NAME="default product name"
ARG PRODUCT_BUILD_DATE="default build date"
ARG PRODUCT_VCS_REF="default vcs ref"
ARG PRODUCT_VENDOR="default vendor"
ARG PRODUCT_VERSION="default product version"
ARG PRODUCT_SCHEMA_VERSION="1.0.0-rc1"

ENV PRODUCT_NAME "${PRODUCT_NAME}"
ENV PRODUCT_BUILD_DATE "${PRODUCT_BUILD_DATE}"
ENV PRODUCT_VCS_REF "${PRODUCT_VCS_REF}"
ENV PRODUCT_VENDOR "${PRODUCT_VENDOR}"
ENV PRODUCT_VERSION "${PRODUCT_VERSION}"
ENV PRODUCT_SCHEMA_VERSION "${PRODUCT_SCHEMA_VERSION}"

LABEL org.lable-schema.name="${PRODUCT_NAME}"
LABEL org.lable-schema.build-date="${PRODUCT_BUILD_DATE}"
LABEL org.lable-schema.vcs-ref="${PRODUCT_VCS_REF}"
LABEL org.lable-schema.vendor="${PRODUCT_VENDOR}"
LABEL org.lable-schema.version="${PRODUCT_VERSION}"
LABEL org.lable-schema.schema-version="${PRODUCT_SCHEMA_VERSION}"