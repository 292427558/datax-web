FROM openjdk:8

ENV LANG=C.UTF-8 \
    DATA_WEB=2.1.2 \
    DATAX_VERSION=2.1.2 \
    TIME_ZONE=Asia/Shanghai \
    DATAX_NAME=datax-executor

ARG DATAX_ADMIN_HOST=127.0.0.1
ARG DATAX_ADMIN_PORT=9527
ARG PYTHON_PATH=/opt/${DATAX_NAME}/python
ARG DATA_PATH=/opt/${DATAX_NAME}/data
ARG SERVICE_LOG_PATH=/opt/${DATAX_NAME}/logs
ARG JSON_PATH=/opt/${DATAX_NAME}/json

RUN  apt-get update && apt-get install -y python && mkdir -p   /opt/${DATAX_NAME}/json /opt/${DATAX_NAME}/python /opt/${DATAX_NAME}/data /opt/${DATAX_NAME}/logs
ADD  target/datax-executor-2.1.2.jar  /opt/${DATAX_NAME}/
ADD  target/datax.tar.gz  /${PYTHON_PATH}/

ENV SERVER_PORT=${SERVER_PORT} \
    DATAX_ADMIN_HOST=${DATAX_ADMIN_HOST} \
    DATAX_ADMIN_PORT=${DATAX_ADMIN_PORT} \
    EXECUTOR_PORT=${EXECUTOR_PORT} \
    CLASSPATH=/opt/${DATAX_NAME}/lib/*:/opt/${DATAX_NAME}/conf:.  \
    JSON_PATH=${JSON_PATH} \
    PYTHON_PATH=${PYTHON_PATH}/datax/bin/ \
    DATA_PATH=${DATA_PATH} \
    SERVICE_LOG_PATH=${SERVICE_LOG_PATH}

WORKDIR /opt/${DATAX_NAME}/

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /opt/${DATAX_NAME}/datax-executor-2.1.2.jar"]
