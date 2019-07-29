FROM mmilja/webapp:react-base-0.0.1 as react-builder

RUN mkdir /build && \
  mkdir /build/public && \
  mkdir /build/src

COPY source/front-end/public /build/public/
COPY source/front-end/src /build/src/
COPY source/front-end/package.json /build/
COPY source/front-end/package-lock.json /build/

WORKDIR /build

RUN npm install -y && \
  npm run build -y

FROM mmilja/webapp:nginx-base-0.0.1

COPY --from=react-builder /build/build /usr/share/nginx/html
COPY source/front-end/default.conf /etc/nginx/conf.d

EXPOSE 80/tcp

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